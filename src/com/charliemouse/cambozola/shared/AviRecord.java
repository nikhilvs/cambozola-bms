package com.charliemouse.cambozola.shared;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import com.charliemouse.cambozola.Viewer;


public class AviRecord extends Thread{
	public class AVI_FILE_HEADER {
		byte ccRIFF[];
	    byte avisize[];
	    byte ccAVI[];
	    byte ccLIST1[];
	    byte List1Size[];
	    byte cchdrl[];
	    byte ccavih[];
	    int MainAVIHeaderSize;
	    MAIN_AVI_HEADER MainAVIHeader;
	    byte ccLIST2[];
	    byte List2Size[];
	    byte ccstrl1[];
	    byte ccstrh1[];
	    byte AVIStreamHeader1Size[];
	    AVI_STREAM_HEADER AVIStreamHeader1;
	    byte ccstrf1[];
	    byte AVIStreamFormatSize[];
	    AVI_STREAM_FORMAT AVIStreamFormat;
	    byte ccJUNK1[];
	    byte JUNK1Size[];
	    byte cJUNK1[];
	    byte ccLIST3[];
	    byte List3Size[];
	    byte ccstrl2[];
	    byte ccstrh2[];
	    byte AVIStreamHeader2Size[];
	    AVI_STREAM_HEADER AVIStreamHeader2;
	    byte ccstrf2[];
	    byte AVIAudioStreamFormatSize[];
	    AVI_AUDIO_STREAM_FORMAT AVIAudioStreamFormat;
	    byte ccJUNK2[];
	    byte JUNK2Size[];
	    byte cJUNK2[];
	    byte ccJUNK3[];
	    byte JUNK3Size[];
	    byte cJUNK3[];
	    byte ccLISTMOVI[];
	    int moviListSize;
	    byte FccmoviListSize[];
	    byte ccmovi[];

	    public AVI_FILE_HEADER()
	    {
	        ccRIFF = new byte[4];
	        avisize = new byte[4];
	        ccAVI = new byte[4];
	        ccLIST1 = new byte[4];
	        List1Size = new byte[4];
	        cchdrl = new byte[4];
	        ccavih = new byte[4];
	        MainAVIHeader = new MAIN_AVI_HEADER();
	        ccLIST2 = new byte[4];
	        List2Size = new byte[4];
	        ccstrl1 = new byte[4];
	        ccstrh1 = new byte[4];
	        AVIStreamHeader1Size = new byte[4];
	        AVIStreamHeader1 = new AVI_STREAM_HEADER();
	        ccstrf1 = new byte[4];
	        AVIStreamFormatSize = new byte[4];
	        AVIStreamFormat = new AVI_STREAM_FORMAT();
	        ccJUNK1 = new byte[4];
	        JUNK1Size = new byte[4];
	        cJUNK1 = new byte[128];
	        ccLIST3 = new byte[4];
	        List3Size = new byte[4];
	        ccstrl2 = new byte[4];
	        ccstrh2 = new byte[4];
	        AVIStreamHeader2Size = new byte[4];
	        AVIStreamHeader2 = new AVI_STREAM_HEADER();
	        ccstrf2 = new byte[4];
	        AVIAudioStreamFormatSize = new byte[4];
	        AVIAudioStreamFormat = new AVI_AUDIO_STREAM_FORMAT();
	        ccJUNK2 = new byte[4];
	        JUNK2Size = new byte[4];
	        cJUNK2 = new byte[1824];
	        ccJUNK3 = new byte[4];
	        JUNK3Size = new byte[4];
	        cJUNK3 = new byte[128];
	        ccLISTMOVI = new byte[4];
	        FccmoviListSize = new byte[4];
	        ccmovi = new byte[4];
	    }
	}

	public class AVI_AUDIO_STREAM_FORMAT {
        short wFormatTag;
        short nChannels;
        int nSamplesPerSec;
        int nAvgBytesPerSec;
        short nBlockAlign;
        short wBitsPerSample;
        short cbSize;
        short nSamplePerBlock;
        byte nNumCoef[];
        byte Coef1_0[];
        byte Coef2_0[];
        byte Coef1_1[];
        byte Coef2_1[];
        byte Coef1_2[];
        byte Coef2_2[];
        byte Coef1_3[];
        byte Coef2_3[];
        byte Coef1_4[];
        byte Coef2_4[];
        byte Coef1_5[];
        byte Coef2_5[];
        byte Coef1_6[];
        byte Coef2_6[];

        public AVI_AUDIO_STREAM_FORMAT()
        {
            nNumCoef = new byte[2];
            Coef1_0 = new byte[2];
            Coef2_0 = new byte[2];
            Coef1_1 = new byte[2];
            Coef2_1 = new byte[2];
            Coef1_2 = new byte[2];
            Coef2_2 = new byte[2];
            Coef1_3 = new byte[2];
            Coef2_3 = new byte[2];
            Coef1_4 = new byte[2];
            Coef2_4 = new byte[2];
            Coef1_5 = new byte[2];
            Coef2_5 = new byte[2];
            Coef1_6 = new byte[2];
            Coef2_6 = new byte[2];
        }
	}

	public class AVI_STREAM_FORMAT
    {

        int length;
        int Width;
        int Height;
        int Flags;
        byte DXTag[];
        int unknown;
        int Reserved[];

        public AVI_STREAM_FORMAT()
        {
            DXTag = new byte[4];
            Reserved = new int[4];
        }
    }
	
	public class AVI_INFO {
		char type;
	    int time;
	    int last;
	    int frames;
	    char fps;
	    char head[];
	    File fp;
	    File fp_idx;
	    RandomAccessFile fd;
	    int size;
	    int frate;
	    long first_video_frame_time_stamp;
	    long end_video_frame_time_stamp;
	    long vts;
	    AVI_FILE_HEADER AVIFileHeader;
	    MP4 mp4;
	    int mp4_vol_hdr_is_sent;
	    AVI_AUDIO_STREAM_FORMAT audio;

	    public AVI_INFO()
	    {
	        head = new char[40];
	        AVIFileHeader = new AVI_FILE_HEADER();
	        mp4 = new MP4();
	        audio = new AVI_AUDIO_STREAM_FORMAT();
	    }
	}

	public class AVI_STREAM_HEADER {
		byte ccType[];
	    byte ccHandler[];
	    int wFlags;
	    int wPriority;
	    int wInitialFrames;
	    int wScale;
	    int wRate;
	    int wStart;
	    int wLength;
	    int wSuggestedBufferSize;
	    int wQuality;
	    int wSampleSize;
	    int unknown1;
	    int unknown2;

	    public AVI_STREAM_HEADER()
	    {
	        ccType = new byte[4];
	        ccHandler = new byte[4];
	    }
	}

	public class AVI_INDEX_ENTRY
    {

        byte FourCC[];
        int dwFlags;
        int dwChunkOffset;
        int dwChunkLength;

        public AVI_INDEX_ENTRY()
        {
            FourCC = new byte[4];
        }
    }
	
	public class MAIN_AVI_HEADER {
		int dwMicroSecPerFrame;
	    int dwMaxBytesPerSec;
	    int dwPaddingGranularity;
	    int dwFlags;
	    int dwTotalFrames;
	    int dwInitialFrames;
	    int dwStreams;
	    int dwSuggestedBufferSize;
	    int dwWidth;
	    int dwHeight;
	    int dwReserved[];

	    public MAIN_AVI_HEADER()
	    {
	        dwReserved = new int[4];
	    }
	}
	
	public class MOVI_HEADER
    {

        byte FourCC[];
        byte FccFrameSize[];
        int FrameSize;

        public MOVI_HEADER()
        {
            FourCC = new byte[4];
            FccFrameSize = new byte[4];
        }
    }

	public class MP4
    {

        BITMAPINFO m_vih;
        SMP4Info m_info;
        MP4PARA m_mp4para;
        int m_NumCurrentVideo;
        int m_HeaderLen;
        int m_FrameBufLen;
        boolean m_bStartAVI;
        boolean m_bGotFirstI;
        int m_VideoType;
        char m_FrameType;
        int m_FAviSize;
        int m_FIdxSize;
        int m_FIdxWSize;
        int m_FinalSize;
        int m_FErrFlag;
        int m_Frate;
        int m_FOddFlag;
        byte m_VolHeader[];
        MOVI_HEADER m_MovHeader;
        AVI_INDEX_ENTRY m_AviIdxEntry;
        AVI_FILE_HEADER m_AviFHeader;
        byte m_tBuf[];
        byte m_AVIBuf[];
        byte m_FrameBuf[];
        byte m_iBuf[];
        byte m_oBuf[];
        byte m_idxBuf[];
        int m_iSize;
        int m_oSize;
        int m_oSizeWrited;
        int m_idxSizeWrited;

        public MP4()
        {
            m_vih = new BITMAPINFO();
            m_info = new SMP4Info();
            m_mp4para = new MP4PARA();
            m_VolHeader = new byte[20];
            m_MovHeader = new MOVI_HEADER();
            m_AviIdxEntry = new AVI_INDEX_ENTRY();
            m_AviFHeader = new AVI_FILE_HEADER();
            m_idxBuf = new byte[0x1000000];
        }
    }
	
	public class MP4PARA
    {

        File mp4_idx_fp;
        File mp4_avi_fp;
        int DisplayMode;
        int frame_rate;
        char Location[];
        char AviChunkFileName[];
        char IndexChunkFileName[];
        int TotalFrameNumber;
        int dwChunkOffset;
        int width;
        int height;

        public MP4PARA()
        {
            Location = new char[20];
            AviChunkFileName = new char[128];
            IndexChunkFileName = new char[128];
        }
    }

	public class SMP4Info
    {

        int short_video_header;
        int random_accessible_vol;
        int video_object_type_indication;
        int video_object_layer_verid;
        int video_object_layer_priority;
        int newpred_enable;
        int aspect_ratio_info;
        int par_width;
        int par_height;
        int scalability;
        int video_object_layer_shape;
        int video_object_layer_shape_extension;
        int vop_time_increment_resolution;
        int vop_time_increment;
        int fixed_vop_time_increment;
        int video_object_layer_width;
        int video_object_layer_height;
        int interlaced;
        int obmc_disable;
        int sprite_enable;
        int quant_precision;
        int quant_type;
        int bits_per_pixel;
        int quarter_sample;
        int resync_marker_disable;
        int data_partitioned;
        int reversible_vlc;
        int vop_coding_type;
        int vop_rounding_type;
        int reduced_resolution_vop_enable;
        int vop_reduced_resolution;
        int change_conv_ratio_disable;
        int vop_constant_alpha;
        int vop_constant_alpha_value;
        int intra_dc_vlc_thr;
        int vop_quant;
        int vop_fcode_forward;
        int vop_fcode_backward;
        int vop_shape_coding_type;
        int vop_id;
        int vop_id_for_prediction;
        int vop_id_for_prediction_indication;
        int temporal_reference;
        int split_screen_indicator;
        int document_camera_indicator;
        int full_picture_freeze_release;
        int source_format;
        int picture_coding_type;
        int pei;
        int psupp;
        int gob_number;
        int use_intra_dc_vlc;
        int time_code;
        int vop_time_increment_length;
        int newpred_length;
        int vop_totalsize_y;
        int vop_totalsize_c;
        int vop_totalsize;
        int video_object_layer_width_c;
        int video_object_layer_height_c;
        int mb_number;
        int mb_number_len;
        int resync_mark_len;
        int mb_width;
        int mb_height;
        int bits_processed;
        int p_vop_count;

        public SMP4Info()
        {
        }
    }

	public class BITMAPINFO
    {

        BITMAPINFOHEADER bmiHeader;
        RGBQUAD bmiColors;

        public BITMAPINFO()
        {
            bmiHeader = new BITMAPINFOHEADER();
            bmiColors = new RGBQUAD();
        }
    }

	public class RGBQUAD
    {

        byte rgbBlue;
        byte rgbGreen;
        byte rgbRed;
        byte rgbReserved;

        public RGBQUAD()
        {
        }
    }

	public class BITMAPINFOHEADER
    {

        long biSize;
        int biWidth;
        int biHeight;
        short biPlanes;
        short biBitCount;
        long biCompression;
        long biSizeImage;
        int biXPelsPerMeter;
        int biYPelsPerMeter;
        long biClrUsed;
        long biClrImportant;

        public BITMAPINFOHEADER()
        {
        }
    }
	
	
	
	private byte[][] m_ImageBuf;
	private int[] m_ImageBufLen;
	private int[] m_ImageBufStatus;
	private int  m_Image_idx;
	private int m_ImageRec_idx;
	private byte[][] m_AudioBuf;
	private int[] m_AudioBufLen;
	private int[] m_AudioBufStatus;
	private int m_Audio_idx;
	private int m_AudioRec_idx;
	private int m_pheight;
    private int m_pwidth;
    private int m_recordFlag;
    private String gsLocalPath;
    private int m_ImgCounter;
    private int m_AudioCounter;
    private boolean m_BeginRecAudioFlag = true;
    private boolean m_BeginRecImgFlag = true;
    private boolean m_AudioFlag = true;
    private int m_curResolutonJpg;
    private long m_MaxRecordSize = 200;
    //private boolean m_MinFreeStopFlag;
    private boolean m_FullArrayIndexFlag;
    private boolean m_overFlowHDDFlag;
    private File m_CheckFreeSpaceFile;
    private long m_RecordFileSize = 0;
    private Viewer m_parent;
	public AviRecord(int resolutionJpg,boolean audioFlag,String strStorageFolder,int maxRecordSize,Viewer parent)
	{
		m_curResolutonJpg = resolutionJpg; 
		setSizeOfJpgInResolution();
		m_ImageBuf = new byte[PublicDefine.MAX_IMAGE_BUF_NUMBER][PublicDefine.MAX_IMAGE_BUF_LEN];
		m_ImageBufLen = new int[PublicDefine.MAX_IMAGE_BUF_NUMBER];
		m_ImageBufStatus = new int[PublicDefine.MAX_IMAGE_BUF_NUMBER];
		
		m_AudioBuf = new byte[PublicDefine.MAX_AUDIO_BUR_NUMBER][PublicDefine.MAX_AUDIO_BUF_LEN];
		m_AudioBufLen = new int[PublicDefine.MAX_AUDIO_BUR_NUMBER];
		m_AudioBufStatus = new int[PublicDefine.MAX_AUDIO_BUR_NUMBER];
		resetBufferVideoAudio();
		
		m_recordFlag = PublicDefine.STOP;
		gsLocalPath = strStorageFolder;
		m_ImgCounter = 0;
		m_AudioCounter = 0;
		m_AudioFlag = audioFlag;
		m_MaxRecordSize = maxRecordSize;
		m_CheckFreeSpaceFile = new File(gsLocalPath);
		m_parent = parent;
	}
	public void setStoreFolder(String storeFolder)
	{
		gsLocalPath = storeFolder;
		m_CheckFreeSpaceFile = new File(gsLocalPath);
		//todosang
		//System.out.println("Free Space" + m_CheckFreeSpaceFile.getFreeSpace());
		
		
	}
	private void setSizeOfJpgInResolution()
	{
		switch (m_curResolutonJpg) {
		case PublicDefine.RESOLUTON_VGA:
		{
			m_pwidth = 640;
			m_pheight= 480;
			break;
		}
		case PublicDefine.RESOLUTON_QVGA:
		{
			m_pwidth = 320;
			m_pheight= 240;
			break;
		}
		case PublicDefine.RESOLUTON_QQVGA:
		{
			m_pwidth = 160;
			m_pheight= 120;
			break;
		}

		default:
			System.out.println("Resolution invalid" + m_curResolutonJpg);
			break;
		}
	}
	private void resetBufferVideoAudio()
	{
		for(int i = 0 ; i < PublicDefine.MAX_IMAGE_BUF_NUMBER;i++)
		{
			m_ImageBufStatus[i]= PublicDefine.BUFFER_EMPTY;
			m_ImageBufLen[i] = 0;
		}
		m_Image_idx = 0;
		m_ImageRec_idx = 0;
		
		for(int i = 0 ; i < PublicDefine.MAX_AUDIO_BUR_NUMBER;i++)
		{
			m_AudioBufStatus[i]= PublicDefine.BUFFER_EMPTY;
			m_AudioBufLen[i] = 0;
		}
		m_Audio_idx = 0;
		m_AudioRec_idx = 0;
	}
	private void receiveAudio(byte[] audioData, int audioLen)
	{
		int i = 0;
		while(i < audioLen)
		{
			if(m_overFlowHDDFlag == true || m_FullArrayIndexFlag == true)
				return;
			
			 int len;
			 if(i + PublicDefine.MAX_AUDIO_BUF_LEN <= audioLen)
				 len = PublicDefine.MAX_AUDIO_BUF_LEN;
			 else{
				 len = audioLen - i;
			 }
			 if(m_AudioBufStatus[m_AudioRec_idx] == PublicDefine.BUFFER_EMPTY)
			 {
				 m_AudioBufStatus[m_AudioRec_idx] = PublicDefine.BUFFER_PROCESSING;
				 System.arraycopy(audioData, i,m_AudioBuf[m_AudioRec_idx],0 , len);
				 m_AudioBufLen[m_AudioRec_idx] = len;
				 i += len;
				 
				 m_AudioBufStatus[m_AudioRec_idx] = PublicDefine.BUFFER_FULL;
				 m_AudioRec_idx = (m_AudioRec_idx + 1)% PublicDefine.MAX_AUDIO_BUR_NUMBER;
				 
			 }else{
				 try{
					 Thread.sleep(5);
					//todosang:test
					// System.out.println("Sleep receive audio");
				 }catch(Exception ex)
				 {}
			 }
		}
	}
	private int distanceFrame(int iBegin, int iEnd)
	{
		int iDistance;
		if(iEnd < iBegin)
		{
			iDistance  = (iEnd + 10000) - iBegin;
		}else{
			iDistance = iEnd - iBegin;
		}
		return iDistance;
	}
	public void resetCounter()
	{
		m_BeginRecAudioFlag = true;
		m_BeginRecAudioFlag = true;
	}
	public void resetRecordFlag()
	{
		m_BeginRecAudioFlag = true;
		m_BeginRecImgFlag = true;
	}
	public void getResolutionJpg(int resolution)
	{
		if(m_curResolutonJpg != resolution)
		{
			resetRecordFlag();
			resetBufferVideoAudio();
			m_curResolutonJpg = resolution;
			setSizeOfJpgInResolution();
			//System.out.println("Resolution " + m_curResolutonJpg + " * " + m_pwidth + " - " + m_pheight );
			
		}
	}
	public void getAudio(byte[] audioData, int audioLen, int resetAudioBufferCount)
	{
		if(audioLen > 5050)
			System.out.println("Audio Len " + audioLen);
		if(resetAudioBufferCount > 0)
		{
			byte[] audioExtractData = new byte[resetAudioBufferCount * 16160];
			receiveAudio(audioExtractData, audioExtractData.length);
			System.out.println("Reset Audio Buffer " + resetAudioBufferCount);
		}
		
		receiveAudio(audioData, audioLen);
	}
	public void getAudio(byte[] audioData, int audioLen,int imgBegin, int imgEnd)
	{
		 
		 if(m_BeginRecAudioFlag == true)
		 {
			 m_BeginRecAudioFlag = false;
		 }
		 else{
			 int missFrame = distanceFrame(m_AudioCounter,imgBegin) - 1;
			
			 if(missFrame > 0)
			 {
				 int distanFrame = distanceFrame(imgBegin,imgEnd) + 1;
				 int extractLen = (audioLen / distanFrame) * missFrame;
				 if(extractLen %2 != 0)
					 extractLen +=1;
				 byte[] audioExtractData = new byte[extractLen];
				 for(int i = 0 ; i < extractLen; i++)
				 {
					 audioExtractData[i] = 0; 
				 }
				 receiveAudio(audioExtractData,extractLen);
				 System.out.println("Duplicate audio" + extractLen);
			 }
		 }
		
		 receiveAudio(audioData,audioLen);
		 m_AudioCounter = imgEnd;
		 
	}
	
	public void receiveImage(byte[] imgData, int imgLen)
	{
		while(true)
		{
			if(m_overFlowHDDFlag == true || m_FullArrayIndexFlag == true)
				return;
			if(m_ImageBufStatus[m_ImageRec_idx] == PublicDefine.BUFFER_EMPTY)
			{
				m_ImageBufStatus[m_ImageRec_idx] = PublicDefine.BUFFER_PROCESSING;
				System.arraycopy(imgData, 0, m_ImageBuf[m_ImageRec_idx], 0, imgLen);
				m_ImageBufLen[m_ImageRec_idx] = imgLen;
				
				m_ImageBufStatus[m_ImageRec_idx] = PublicDefine.BUFFER_FULL;
				m_ImageRec_idx = (m_ImageRec_idx + 1) % PublicDefine.MAX_IMAGE_BUF_NUMBER;
				return;
			}else{
				try{
					//System.out.println("Sleep receive video" + m_ImageRec_idx + "*"+m_ImageBufStatus[m_ImageRec_idx]);
					Thread.sleep(5);
					 //todosang:test
					 
				}catch(Exception ex)
				 {}
			}
		}
	}
	
	public void getImage(byte[] imgData,int imgLen, int imgIndex)
	{
		if(m_BeginRecImgFlag == true)
		{
			m_BeginRecImgFlag = false;
		}else{
			int missFrame = distanceFrame(m_ImgCounter,imgIndex) - 1;

			for(int i = 0; i < missFrame;i++)
			{
				receiveImage(imgData, imgLen);
				System.out.println("Duplicate video");
			}
		}
		receiveImage(imgData, imgLen);
		m_ImgCounter = imgIndex;
	}
	public void setMaxRecordSize(long lMaxRecordSize)
	{
		m_MaxRecordSize = lMaxRecordSize;
	}
	public int getRecordFlag()
	{
		return m_recordFlag;
	}
	public void setRecordFlag(int recordFlag)
	{
		m_recordFlag = recordFlag; 
	}
	
	public void AviOpen(AVI_INFO avi_info)
    {
        byte abyte0[] = new byte[2];
        byte abyte1[] = new byte[4];
        AVI_FILE_HEADER avi_file_header = avi_info.AVIFileHeader;
        try
        {
            avi_file_header.ccRIFF[0] = 82;//R
            avi_file_header.ccRIFF[1] = 73;//I
            avi_file_header.ccRIFF[2] = 70;//F
            avi_file_header.ccRIFF[3] = 70;//F
            avi_info.fd.write(avi_file_header.ccRIFF, 0, 4);
            Util.intToByteArray_LSB(0x900422, avi_file_header.avisize);
            avi_info.fd.write(avi_file_header.avisize, 0, 4);
            avi_file_header.ccAVI[0] = 65;//A
            avi_file_header.ccAVI[1] = 86;//V
            avi_file_header.ccAVI[2] = 73;//I
            avi_file_header.ccAVI[3] = 32;//
            avi_info.fd.write(avi_file_header.ccAVI, 0, 4);
            avi_file_header.ccLIST1[0] = 76;//L
            avi_file_header.ccLIST1[1] = 73;//I
            avi_file_header.ccLIST1[2] = 83;//S
            avi_file_header.ccLIST1[3] = 84;//T
            avi_info.fd.write(avi_file_header.ccLIST1, 0, 4);
            Util.intToByteArray_LSB(596, avi_file_header.List1Size);
            avi_info.fd.write(avi_file_header.List1Size, 0, 4);
        }
        catch(Exception exception)
        {
            System.out.println("avi open fail 739 " + exception);
        }
        try
        {
            avi_file_header.cchdrl[0] = 104;//h
            avi_file_header.cchdrl[1] = 100;//d
            avi_file_header.cchdrl[2] = 114;//r
            avi_file_header.cchdrl[3] = 108;//l
            avi_info.fd.write(avi_file_header.cchdrl, 0, 4);
            avi_file_header.ccavih[0] = 97;//a
            avi_file_header.ccavih[1] = 118;//v
            avi_file_header.ccavih[2] = 105;//i
            avi_file_header.ccavih[3] = 104;//h
            avi_info.fd.write(avi_file_header.ccavih, 0, 4);
            avi_file_header.MainAVIHeaderSize = 56;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeaderSize, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.MainAVIHeader.dwMicroSecPerFrame = 0x1e848;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwMicroSecPerFrame, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.MainAVIHeader.dwMaxBytesPerSec = 0;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwMaxBytesPerSec, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.MainAVIHeader.dwPaddingGranularity = 0;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwPaddingGranularity, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.MainAVIHeader.dwFlags = 272;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwFlags, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.MainAVIHeader.dwTotalFrames = 600;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwTotalFrames, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.MainAVIHeader.dwInitialFrames = 0;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwInitialFrames, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
        	avi_file_header.MainAVIHeader.dwStreams = 2;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwStreams, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.MainAVIHeader.dwSuggestedBufferSize = 0;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwSuggestedBufferSize, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.MainAVIHeader.dwWidth = m_pwidth;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwWidth, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.MainAVIHeader.dwHeight = m_pheight;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwHeight, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            Util.intToByteArray_LSB(0, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_info.fd.write(abyte1, 0, 4);
            avi_info.fd.write(abyte1, 0, 4);
            avi_info.fd.write(abyte1, 0, 4);
        }
        catch(Exception exception1)
        {
            System.out.println("avi open fail 763 " + exception1);
        }
        try
        {
            avi_file_header.ccLIST2[0] = 76;//L
            avi_file_header.ccLIST2[1] = 73;//I
            avi_file_header.ccLIST2[2] = 83;//S
            avi_file_header.ccLIST2[3] = 84;//T
            avi_info.fd.write(avi_file_header.ccLIST2, 0, 4);
            Util.intToByteArray_LSB(116, avi_file_header.List2Size);
            avi_info.fd.write(avi_file_header.List2Size, 0, 4);
            avi_file_header.ccstrl1[0] = 115;//s
            avi_file_header.ccstrl1[1] = 116;//t
            avi_file_header.ccstrl1[2] = 114;//r
            avi_file_header.ccstrl1[3] = 108;//l
            avi_info.fd.write(avi_file_header.ccstrl1, 0, 4);
            avi_file_header.ccstrh1[0] = 115;//s
            avi_file_header.ccstrh1[1] = 116;//t
            avi_file_header.ccstrh1[2] = 114;//r
            avi_file_header.ccstrh1[3] = 104;//h
            avi_info.fd.write(avi_file_header.ccstrh1, 0, 4);
            Util.intToByteArray_LSB(56, avi_file_header.AVIStreamHeader1Size);
            avi_info.fd.write(avi_file_header.AVIStreamHeader1Size, 0, 4);
            avi_file_header.AVIStreamHeader1.ccType[0] = 118;//v
            avi_file_header.AVIStreamHeader1.ccType[1] = 105;//i
            avi_file_header.AVIStreamHeader1.ccType[2] = 100;//d
            avi_file_header.AVIStreamHeader1.ccType[3] = 115;//s
            avi_info.fd.write(avi_file_header.AVIStreamHeader1.ccType, 0, 4);
            avi_file_header.AVIStreamHeader1.ccHandler[0] = 77;//M
            avi_file_header.AVIStreamHeader1.ccHandler[1] = 74;//J
            avi_file_header.AVIStreamHeader1.ccHandler[2] = 80;//P
            avi_file_header.AVIStreamHeader1.ccHandler[3] = 71;//G
            
            avi_info.fd.write(avi_file_header.AVIStreamHeader1.ccHandler, 0, 4);
            avi_file_header.AVIStreamHeader1.wFlags = 256;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader1.wFlags, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader1.wPriority = 0;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader1.wPriority, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader1.wInitialFrames = 0;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader1.wInitialFrames, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader1.wScale = 1000;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader1.wScale, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader1.wRate = 8000;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader1.wRate, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader1.wStart = 0;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader1.wStart, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader1.wLength = 0;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader1.wLength, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader1.wSuggestedBufferSize = 0x100000;
            Util.intToByteArray_LSB(0x100000, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader1.wQuality = -1;//todosang
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader1.wQuality, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader1.wSampleSize = 0;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader1.wSampleSize, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            Util.intToByteArray_LSB(0, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_info.fd.write(abyte1, 0, 4);
        }
        catch(Exception exception2)
        {
            System.out.println("avi open fail 794 " + exception2);
        }
        try
        {
            avi_file_header.ccstrf1[0] = 115;//s
            avi_file_header.ccstrf1[1] = 116;//t
            avi_file_header.ccstrf1[2] = 114;//r
            avi_file_header.ccstrf1[3] = 102;//f
            avi_info.fd.write(avi_file_header.ccstrf1, 0, 4);
            Util.intToByteArray_LSB(40, avi_file_header.AVIStreamFormatSize);
            avi_info.fd.write(avi_file_header.AVIStreamFormatSize, 0, 4);
            avi_file_header.AVIStreamFormat.length = 40;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamFormat.length, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamFormat.Width = m_pwidth;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwWidth, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamFormat.Height = m_pheight;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwHeight, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamFormat.Flags = 0x180001;
            Util.intToByteArray_LSB(0x180001, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamFormat.DXTag[0] = 77;//M
            avi_file_header.AVIStreamFormat.DXTag[1] = 74;//J
            avi_file_header.AVIStreamFormat.DXTag[2] = 80;//P
            avi_file_header.AVIStreamFormat.DXTag[3] = 71;//G
            avi_info.fd.write(avi_file_header.AVIStreamFormat.DXTag, 0, 4);
            avi_file_header.AVIStreamFormat.unknown = 0xfd200;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamFormat.unknown, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            Util.intToByteArray_LSB(0, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_info.fd.write(abyte1, 0, 4);
            avi_info.fd.write(abyte1, 0, 4);
            avi_info.fd.write(abyte1, 0, 4);
        }
        catch(Exception exception3)
        {
            System.out.println("avi open fail 816 " + exception3);
        }
        try
        {
            avi_file_header.ccJUNK1[0] = 74;//J
            avi_file_header.ccJUNK1[1] = 85;//U
            avi_file_header.ccJUNK1[2] = 78;//N
            avi_file_header.ccJUNK1[3] = 75;//K
            avi_info.fd.write(avi_file_header.ccJUNK1, 0, 4);
            Util.intToByteArray_LSB(128, avi_file_header.JUNK1Size);
            avi_info.fd.write(avi_file_header.JUNK1Size, 0, 4);
            avi_info.fd.write(avi_file_header.cJUNK1, 0, 128);
            avi_file_header.ccLIST3[0] = 76;//L
            avi_file_header.ccLIST3[1] = 73;//I
            avi_file_header.ccLIST3[2] = 83;//S
            avi_file_header.ccLIST3[3] = 84;//T
            avi_info.fd.write(avi_file_header.ccLIST3, 0, 4);
            Util.intToByteArray_LSB(260, avi_file_header.List3Size);
            avi_info.fd.write(avi_file_header.List3Size, 0, 4);
            avi_file_header.ccstrl2[0] = 115;//s
            avi_file_header.ccstrl2[1] = 116;//t
            avi_file_header.ccstrl2[2] = 114;//r
            avi_file_header.ccstrl2[3] = 108;//l
            avi_info.fd.write(avi_file_header.ccstrl2, 0, 4);
            avi_file_header.ccstrh2[0] = 115;//s
            avi_file_header.ccstrh2[1] = 116;//t
            avi_file_header.ccstrh2[2] = 114;//r
            avi_file_header.ccstrh2[3] = 104;//h
            avi_info.fd.write(avi_file_header.ccstrh2, 0, 4);
            Util.intToByteArray_LSB(56, avi_file_header.AVIStreamHeader2Size);
            avi_info.fd.write(avi_file_header.AVIStreamHeader2Size, 0, 4);
            avi_file_header.AVIStreamHeader2.ccType[0] = 97;//a
            avi_file_header.AVIStreamHeader2.ccType[1] = 117;//u
            avi_file_header.AVIStreamHeader2.ccType[2] = 100;//d
            avi_file_header.AVIStreamHeader2.ccType[3] = 115;//s
            avi_info.fd.write(avi_file_header.AVIStreamHeader2.ccType, 0, 4);
            avi_info.fd.write(avi_file_header.AVIStreamHeader2.ccHandler, 0, 4);
            avi_file_header.AVIStreamHeader2.wFlags = 0;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader2.wFlags, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader2.wPriority = 0;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader2.wPriority, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader2.wInitialFrames = 0;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader2.wInitialFrames, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader2.wScale = 1;
            Util.intToByteArray_LSB(1, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader2.wRate = avi_info.audio.nSamplesPerSec;
            Util.intToByteArray_LSB(avi_info.audio.nSamplesPerSec, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader2.wStart = 0;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader2.wStart, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader2.wLength = 0;
            Util.intToByteArray_LSB(0, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader2.wSuggestedBufferSize = 1024;
            Util.intToByteArray_LSB(1010, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader2.wQuality = 2000;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader2.wQuality, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader2.wSampleSize = avi_info.audio.nBlockAlign;
            Util.intToByteArray_LSB(avi_info.audio.nBlockAlign, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            Util.intToByteArray_LSB(0, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_info.fd.write(abyte1, 0, 4);
        }
        catch(Exception exception4)
        {
            System.out.println("avi open fail 862 " + exception4);
        }
        try
        {
            avi_file_header.ccstrf2[0] = 115;//s
            avi_file_header.ccstrf2[1] = 116;//t
            avi_file_header.ccstrf2[2] = 114;//r
            avi_file_header.ccstrf2[3] = 102;//f
            avi_info.fd.write(avi_file_header.ccstrf2, 0, 4);
            Util.intToByteArray_LSB(50, avi_file_header.AVIAudioStreamFormatSize);
            avi_info.fd.write(avi_file_header.AVIAudioStreamFormatSize, 0, 4);
            avi_file_header.AVIAudioStreamFormat.wFormatTag = avi_info.audio.wFormatTag;
            Util.shortToByteArray_LSB(avi_info.audio.wFormatTag, abyte0);
            avi_info.fd.write(abyte0, 0, 2);
            avi_file_header.AVIAudioStreamFormat.nChannels = avi_info.audio.nChannels;
            Util.shortToByteArray_LSB(avi_info.audio.nChannels, abyte0);
            avi_info.fd.write(abyte0, 0, 2);
            avi_file_header.AVIAudioStreamFormat.nSamplesPerSec = avi_info.audio.nSamplesPerSec;
            Util.intToByteArray_LSB(avi_info.audio.nSamplesPerSec, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIAudioStreamFormat.nAvgBytesPerSec = avi_info.audio.nAvgBytesPerSec;
            Util.intToByteArray_LSB(avi_info.audio.nAvgBytesPerSec, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIAudioStreamFormat.nBlockAlign = avi_info.audio.nBlockAlign;
            Util.shortToByteArray_LSB(avi_info.audio.nBlockAlign, abyte0);
            avi_info.fd.write(abyte0, 0, 2);
            avi_file_header.AVIAudioStreamFormat.wBitsPerSample = avi_info.audio.wBitsPerSample;
            Util.shortToByteArray_LSB(avi_info.audio.wBitsPerSample, abyte0);
            avi_info.fd.write(abyte0, 0, 2);
            avi_file_header.AVIAudioStreamFormat.cbSize = avi_info.audio.cbSize;
            Util.shortToByteArray_LSB(avi_info.audio.cbSize, abyte0);
            avi_info.fd.write(abyte0, 0, 2);
            avi_file_header.AVIAudioStreamFormat.nSamplePerBlock = avi_info.audio.nSamplePerBlock;
            Util.shortToByteArray_LSB(avi_info.audio.nSamplePerBlock, abyte0);
            avi_info.fd.write(abyte0, 0, 2);
            avi_info.fd.write(avi_file_header.AVIAudioStreamFormat.nNumCoef, 0, 2);
            avi_info.fd.write(avi_file_header.AVIAudioStreamFormat.Coef1_0, 0, 2);
            avi_info.fd.write(avi_file_header.AVIAudioStreamFormat.Coef2_0, 0, 2);
            avi_info.fd.write(avi_file_header.AVIAudioStreamFormat.Coef1_1, 0, 2);
            avi_info.fd.write(avi_file_header.AVIAudioStreamFormat.Coef2_1, 0, 2);
            avi_info.fd.write(avi_file_header.AVIAudioStreamFormat.Coef1_2, 0, 2);
            avi_info.fd.write(avi_file_header.AVIAudioStreamFormat.Coef2_2, 0, 2);
            avi_info.fd.write(avi_file_header.AVIAudioStreamFormat.Coef1_3, 0, 2);
            avi_info.fd.write(avi_file_header.AVIAudioStreamFormat.Coef2_3, 0, 2);
            avi_info.fd.write(avi_file_header.AVIAudioStreamFormat.Coef1_4, 0, 2);
            avi_info.fd.write(avi_file_header.AVIAudioStreamFormat.Coef2_4, 0, 2);
            avi_info.fd.write(avi_file_header.AVIAudioStreamFormat.Coef1_5, 0, 2);
            avi_info.fd.write(avi_file_header.AVIAudioStreamFormat.Coef2_5, 0, 2);
            avi_info.fd.write(avi_file_header.AVIAudioStreamFormat.Coef1_6, 0, 2);
            avi_info.fd.write(avi_file_header.AVIAudioStreamFormat.Coef2_6, 0, 2);
        }
        catch(Exception exception5)
        {
            System.out.println("avi open fail 862 " + exception5);
        }

        try
        {
            avi_file_header.ccJUNK2[0] = 74;//J
            avi_file_header.ccJUNK2[1] = 85;//U
            avi_file_header.ccJUNK2[2] = 78;//N
            avi_file_header.ccJUNK2[3] = 75;//K
            avi_info.fd.write(avi_file_header.ccJUNK2, 0, 4);
            Util.intToByteArray_LSB(126, avi_file_header.JUNK2Size);
            avi_info.fd.write(avi_file_header.JUNK2Size, 0, 4);
            avi_info.fd.write(avi_file_header.cJUNK2, 0, 126);
            avi_file_header.ccJUNK3[0] = 74;//J
            avi_file_header.ccJUNK3[1] = 85;//U
            avi_file_header.ccJUNK3[2] = 78;//N
            avi_file_header.ccJUNK3[3] = 75;//K
            avi_info.fd.write(avi_file_header.ccJUNK3, 0, 4);
            Util.intToByteArray_LSB(128, avi_file_header.JUNK3Size);
            avi_info.fd.write(avi_file_header.JUNK3Size, 0, 4);
            avi_info.fd.write(avi_file_header.cJUNK3, 0, 128);
            avi_file_header.ccLISTMOVI[0] = 76;//L
            avi_file_header.ccLISTMOVI[1] = 73;//I
            avi_file_header.ccLISTMOVI[2] = 83;//S
            avi_file_header.ccLISTMOVI[3] = 84;//T
            avi_info.fd.write(avi_file_header.ccLISTMOVI, 0, 4);
            avi_file_header.moviListSize = 0;
            Util.intToByteArray_LSB(avi_file_header.moviListSize, avi_file_header.FccmoviListSize);
            avi_info.fd.write(avi_file_header.FccmoviListSize, 0, 4);
            avi_file_header.ccmovi[0] = 109;//m
            avi_file_header.ccmovi[1] = 111;//o
            avi_file_header.ccmovi[2] = 118;//v
            avi_file_header.ccmovi[3] = 105;//i
            avi_info.fd.write(avi_file_header.ccmovi, 0, 4);
            avi_info.mp4.m_idxSizeWrited = 0;
            avi_info.mp4.m_mp4para.dwChunkOffset = 4;
            avi_file_header.moviListSize = 4;
            Util.intToByteArray_LSB(4, avi_file_header.FccmoviListSize);
            avi_info.mp4.m_mp4para.TotalFrameNumber = 0;
        }
        catch(Exception exception6)
        {
            System.out.println("avi open fail 942" + exception6);
        }
    }
	public void VideoOnlyAviOpen(AVI_INFO avi_info)
	{
		byte abyte0[] = new byte[2];
        byte abyte1[] = new byte[4];
        AVI_FILE_HEADER avi_file_header = avi_info.AVIFileHeader;
        try
        {
            avi_file_header.ccRIFF[0] = 82;//R
            avi_file_header.ccRIFF[1] = 73;//I
            avi_file_header.ccRIFF[2] = 70;//F
            avi_file_header.ccRIFF[3] = 70;//F
            avi_info.fd.write(avi_file_header.ccRIFF, 0, 4);
            Util.intToByteArray_LSB(0x900422, avi_file_header.avisize);
            avi_info.fd.write(avi_file_header.avisize, 0, 4);
            avi_file_header.ccAVI[0] = 65;//A
            avi_file_header.ccAVI[1] = 86;//V
            avi_file_header.ccAVI[2] = 73;//I
            avi_file_header.ccAVI[3] = 32;//
            avi_info.fd.write(avi_file_header.ccAVI, 0, 4);
            avi_file_header.ccLIST1[0] = 76;//L
            avi_file_header.ccLIST1[1] = 73;//I
            avi_file_header.ccLIST1[2] = 83;//S
            avi_file_header.ccLIST1[3] = 84;//T
            avi_info.fd.write(avi_file_header.ccLIST1, 0, 4);
            Util.intToByteArray_LSB(596, avi_file_header.List1Size);
            avi_info.fd.write(avi_file_header.List1Size, 0, 4);
        }
        catch(Exception exception)
        {
            System.out.println("avi open fail 739 " + exception);
        }
        try
        {
            avi_file_header.cchdrl[0] = 104;//h
            avi_file_header.cchdrl[1] = 100;//d
            avi_file_header.cchdrl[2] = 114;//r
            avi_file_header.cchdrl[3] = 108;//l
            avi_info.fd.write(avi_file_header.cchdrl, 0, 4);
            avi_file_header.ccavih[0] = 97;//a
            avi_file_header.ccavih[1] = 118;//v
            avi_file_header.ccavih[2] = 105;//i
            avi_file_header.ccavih[3] = 104;//h
            avi_info.fd.write(avi_file_header.ccavih, 0, 4);
            avi_file_header.MainAVIHeaderSize = 56;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeaderSize, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.MainAVIHeader.dwMicroSecPerFrame = 0x1e848;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwMicroSecPerFrame, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.MainAVIHeader.dwMaxBytesPerSec = 0;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwMaxBytesPerSec, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.MainAVIHeader.dwPaddingGranularity = 0;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwPaddingGranularity, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.MainAVIHeader.dwFlags = 272;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwFlags, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.MainAVIHeader.dwTotalFrames = 600;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwTotalFrames, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.MainAVIHeader.dwInitialFrames = 0;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwInitialFrames, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
        	avi_file_header.MainAVIHeader.dwStreams = 1;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwStreams, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.MainAVIHeader.dwSuggestedBufferSize = 0;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwSuggestedBufferSize, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.MainAVIHeader.dwWidth = m_pwidth;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwWidth, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.MainAVIHeader.dwHeight = m_pheight;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwHeight, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            Util.intToByteArray_LSB(0, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_info.fd.write(abyte1, 0, 4);
            avi_info.fd.write(abyte1, 0, 4);
            avi_info.fd.write(abyte1, 0, 4);
        }
        catch(Exception exception1)
        {
            System.out.println("avi open fail 763 " + exception1);
        }
        try
        {
            avi_file_header.ccLIST2[0] = 76;//L
            avi_file_header.ccLIST2[1] = 73;//I
            avi_file_header.ccLIST2[2] = 83;//S
            avi_file_header.ccLIST2[3] = 84;//T
            avi_info.fd.write(avi_file_header.ccLIST2, 0, 4);
            Util.intToByteArray_LSB(116, avi_file_header.List2Size);
            avi_info.fd.write(avi_file_header.List2Size, 0, 4);
            avi_file_header.ccstrl1[0] = 115;//s
            avi_file_header.ccstrl1[1] = 116;//t
            avi_file_header.ccstrl1[2] = 114;//r
            avi_file_header.ccstrl1[3] = 108;//l
            avi_info.fd.write(avi_file_header.ccstrl1, 0, 4);
            avi_file_header.ccstrh1[0] = 115;//s
            avi_file_header.ccstrh1[1] = 116;//t
            avi_file_header.ccstrh1[2] = 114;//r
            avi_file_header.ccstrh1[3] = 104;//h
            avi_info.fd.write(avi_file_header.ccstrh1, 0, 4);
            Util.intToByteArray_LSB(56, avi_file_header.AVIStreamHeader1Size);
            avi_info.fd.write(avi_file_header.AVIStreamHeader1Size, 0, 4);
            avi_file_header.AVIStreamHeader1.ccType[0] = 118;//v
            avi_file_header.AVIStreamHeader1.ccType[1] = 105;//i
            avi_file_header.AVIStreamHeader1.ccType[2] = 100;//d
            avi_file_header.AVIStreamHeader1.ccType[3] = 115;//s
            avi_info.fd.write(avi_file_header.AVIStreamHeader1.ccType, 0, 4);
            avi_file_header.AVIStreamHeader1.ccHandler[0] = 77;//M
            avi_file_header.AVIStreamHeader1.ccHandler[1] = 74;//J
            avi_file_header.AVIStreamHeader1.ccHandler[2] = 80;//P
            avi_file_header.AVIStreamHeader1.ccHandler[3] = 71;//G
           
            avi_info.fd.write(avi_file_header.AVIStreamHeader1.ccHandler, 0, 4);
            avi_file_header.AVIStreamHeader1.wFlags = 256;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader1.wFlags, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader1.wPriority = 0;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader1.wPriority, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader1.wInitialFrames = 0;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader1.wInitialFrames, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader1.wScale = 1000;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader1.wScale, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader1.wRate = 8000;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader1.wRate, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader1.wStart = 0;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader1.wStart, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader1.wLength = 0;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader1.wLength, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader1.wSuggestedBufferSize = 0x100000;
            Util.intToByteArray_LSB(0x100000, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader1.wQuality = -1;//todosang
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader1.wQuality, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamHeader1.wSampleSize = 0;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader1.wSampleSize, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            Util.intToByteArray_LSB(0, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_info.fd.write(abyte1, 0, 4);
        }
        catch(Exception exception2)
        {
            System.out.println("avi open fail 794 " + exception2);
        }
        try
        {
            avi_file_header.ccstrf1[0] = 115;//s
            avi_file_header.ccstrf1[1] = 116;//t
            avi_file_header.ccstrf1[2] = 114;//r
            avi_file_header.ccstrf1[3] = 102;//f
            avi_info.fd.write(avi_file_header.ccstrf1, 0, 4);
            Util.intToByteArray_LSB(40, avi_file_header.AVIStreamFormatSize);
            avi_info.fd.write(avi_file_header.AVIStreamFormatSize, 0, 4);
            avi_file_header.AVIStreamFormat.length = 40;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamFormat.length, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamFormat.Width = m_pwidth;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwWidth, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamFormat.Height = m_pheight;
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwHeight, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamFormat.Flags = 0x180001;
            Util.intToByteArray_LSB(0x180001, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_file_header.AVIStreamFormat.DXTag[0] = 77;//M
            avi_file_header.AVIStreamFormat.DXTag[1] = 74;//J
            avi_file_header.AVIStreamFormat.DXTag[2] = 80;//P
            avi_file_header.AVIStreamFormat.DXTag[3] = 71;//G

            avi_info.fd.write(avi_file_header.AVIStreamFormat.DXTag, 0, 4);
            avi_file_header.AVIStreamFormat.unknown = 0xfd200;
            Util.intToByteArray_LSB(avi_file_header.AVIStreamFormat.unknown, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            Util.intToByteArray_LSB(0, abyte1);
            avi_info.fd.write(abyte1, 0, 4);
            avi_info.fd.write(abyte1, 0, 4);
            avi_info.fd.write(abyte1, 0, 4);
            avi_info.fd.write(abyte1, 0, 4);
        }
        catch(Exception exception3)
        {
            System.out.println("avi open fail 816 " + exception3);
        }
        try
        {
            avi_file_header.ccJUNK2[0] = 74;//J
            avi_file_header.ccJUNK2[1] = 85;//U
            avi_file_header.ccJUNK2[2] = 78;//N
            avi_file_header.ccJUNK2[3] = 75;//K
            avi_info.fd.write(avi_file_header.ccJUNK2, 0, 4);
            Util.intToByteArray_LSB(1816, avi_file_header.JUNK2Size);
            avi_info.fd.write(avi_file_header.JUNK2Size, 0, 4);
            avi_info.fd.write(avi_file_header.cJUNK2, 0, 1816);
           
            avi_file_header.ccLISTMOVI[0] = 76;//L
            avi_file_header.ccLISTMOVI[1] = 73;//I
            avi_file_header.ccLISTMOVI[2] = 83;//S
            avi_file_header.ccLISTMOVI[3] = 84;//T
            avi_info.fd.write(avi_file_header.ccLISTMOVI, 0, 4);
            avi_file_header.moviListSize = 0;
            Util.intToByteArray_LSB(avi_file_header.moviListSize, avi_file_header.FccmoviListSize);
            avi_info.fd.write(avi_file_header.FccmoviListSize, 0, 4);
            avi_file_header.ccmovi[0] = 109;//m
            avi_file_header.ccmovi[1] = 111;//o
            avi_file_header.ccmovi[2] = 118;//v
            avi_file_header.ccmovi[3] = 105;//i
            avi_info.fd.write(avi_file_header.ccmovi, 0, 4);
            avi_info.mp4.m_idxSizeWrited = 0;
            avi_info.mp4.m_mp4para.dwChunkOffset = 4;
            avi_file_header.moviListSize = 4;
            Util.intToByteArray_LSB(4, avi_file_header.FccmoviListSize);
            avi_info.mp4.m_mp4para.TotalFrameNumber = 0;
        }
        catch(Exception exception6)
        {
            System.out.println("avi open fail 942" + exception6);
        }
	}
	public void AviClose(AVI_INFO avi_info)
    {
		if(m_overFlowHDDFlag == true)
			return;
        MP4 mp4 = avi_info.mp4;
        MOVI_HEADER movi_header = new MOVI_HEADER();
        AVI_FILE_HEADER avi_file_header = avi_info.AVIFileHeader;
        byte abyte0[] = new byte[4];
        movi_header.FourCC[0] = 105;//i
        movi_header.FourCC[1] = 100;//d
        movi_header.FourCC[2] = 120;//x
        movi_header.FourCC[3] = 49;//1
        movi_header.FrameSize = avi_info.mp4.m_idxSizeWrited;
        try
        {
            avi_info.fd.write(movi_header.FourCC, 0, 4);
            Util.intToByteArray_LSB(movi_header.FrameSize, movi_header.FccFrameSize);
            avi_info.fd.write(movi_header.FccFrameSize, 0, 4);
            avi_info.fd.write(avi_info.mp4.m_idxBuf, 0, movi_header.FrameSize);
        }
        catch(Exception exception)
        {
            System.out.println("Avi close fail" + exception);
            m_overFlowHDDFlag = true;
        }
        avi_file_header.MainAVIHeader.dwTotalFrames = mp4.m_mp4para.TotalFrameNumber;
        if(m_AudioFlag == true)
        {
        	Util.intToByteArray_LSB(752 + avi_file_header.moviListSize + 8 + movi_header.FrameSize, avi_file_header.avisize);
        }else{
        	Util.intToByteArray_LSB(2036 + avi_file_header.moviListSize + 8 + movi_header.FrameSize, avi_file_header.avisize);
        }
        //System.out.println("1st ts = " + avi_info.first_video_frame_time_stamp + "end ts = " + avi_info.end_video_frame_time_stamp);
        double d = (double)(avi_file_header.MainAVIHeader.dwTotalFrames * 1000) / (avi_info.end_video_frame_time_stamp - avi_info.first_video_frame_time_stamp);
        //System.out.println("****************frame rate" + d);
        avi_file_header.AVIStreamHeader1.wRate = (int)(d * 1000);
        double d1 = (1000D) / d;
        avi_file_header.MainAVIHeader.dwMicroSecPerFrame = (int)d1;
        avi_file_header.AVIStreamHeader1.wLength = avi_file_header.MainAVIHeader.dwTotalFrames;
        try
        {
            avi_info.fd.seek(4L);
            avi_info.fd.write(avi_file_header.avisize, 0, 4);
            avi_info.fd.seek(32L);
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwMicroSecPerFrame, abyte0);
            avi_info.fd.write(abyte0, 0, 4);
            avi_info.fd.seek(48L);
            Util.intToByteArray_LSB(avi_file_header.MainAVIHeader.dwTotalFrames, abyte0);
            avi_info.fd.write(abyte0, 0, 4);
            avi_info.fd.seek(132L);
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader1.wRate, abyte0);
            avi_info.fd.write(abyte0, 0, 4);
            avi_info.fd.seek(140L);
            Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader1.wLength, abyte0);
            avi_info.fd.write(abyte0, 0, 4);
            //avi_info.fd.seek(396L);//todosang
            if(m_AudioFlag == true)
            {
            	avi_info.fd.seek(400L);
            	Util.intToByteArray_LSB(avi_file_header.AVIStreamHeader2.wLength, abyte0);
            	avi_info.fd.write(abyte0, 0, 4);
            	avi_info.fd.seek(756L);
            }else{
            	avi_info.fd.seek(2040L);
            }
                        
            Util.intToByteArray_LSB(avi_file_header.moviListSize, abyte0);
            avi_info.fd.write(abyte0, 0, 4);
            //System.out.println("close recording file");
            avi_info.fd.close();
        }
        catch(Exception exception1)
        {
            System.out.println("Avi close fail" + exception1);
            m_overFlowHDDFlag = true;
        }
    }

	public void AviWriteVideo(AVI_INFO avi_info, byte abyte0[], int i)
    {
		if(m_overFlowHDDFlag == true)
			return;
        MP4 mp4 = avi_info.mp4;
        AVI_FILE_HEADER avi_file_header = avi_info.AVIFileHeader;
        MOVI_HEADER movi_header = new MOVI_HEADER();
        byte abyte1[] = new byte[4];
        byte abyte2[] = new byte[4];
        byte abyte3[] = new byte[4];
        byte abyte4[] = new byte[4];
        movi_header.FourCC[0] = 48;//0
        movi_header.FourCC[1] = 48;//0
        movi_header.FourCC[2] = 100;//d
        movi_header.FourCC[3] = 98;//b
        abyte1[0] = 48;
        abyte1[1] = 48;
        abyte1[2] = 100;
        abyte1[3] = 98;
        movi_header.FrameSize = i;
        Util.intToByteArray_LSB(16, abyte2);
        Util.intToByteArray_LSB(mp4.m_mp4para.dwChunkOffset, abyte3);
        Util.intToByteArray_LSB(movi_header.FrameSize, abyte4);
        mp4.m_mp4para.TotalFrameNumber++;
        mp4.m_NumCurrentVideo++;
        avi_file_header.moviListSize += movi_header.FrameSize + 8;
        mp4.m_mp4para.dwChunkOffset = mp4.m_mp4para.dwChunkOffset + movi_header.FrameSize + 8;
        try
        {
            avi_info.fd.write(movi_header.FourCC, 0, 4);
            Util.intToByteArray_LSB(movi_header.FrameSize, movi_header.FccFrameSize);
            avi_info.fd.write(movi_header.FccFrameSize, 0, 4);
            avi_info.fd.write(abyte0, 0, movi_header.FrameSize);
        }
        catch(Exception exception)
        {
            System.out.println("AviWriteVideo fail" + exception);
            m_overFlowHDDFlag = true;
        }
        if(avi_info.mp4.m_idxSizeWrited <= 0xffff0)
        {
        	//System.out.println("Idx Size Writed :" + avi_info.mp4.m_idxSizeWrited);
            System.arraycopy(abyte1, 0, avi_info.mp4.m_idxBuf, avi_info.mp4.m_idxSizeWrited, 4);
            System.arraycopy(abyte2, 0, avi_info.mp4.m_idxBuf, avi_info.mp4.m_idxSizeWrited + 4, 4);
            System.arraycopy(abyte3, 0, avi_info.mp4.m_idxBuf, avi_info.mp4.m_idxSizeWrited + 8, 4);
            System.arraycopy(abyte4, 0, avi_info.mp4.m_idxBuf, avi_info.mp4.m_idxSizeWrited + 12, 4);
            avi_info.mp4.m_idxSizeWrited += 16;
        }
        
    }
	
	public void AviWriteAudio(AVI_INFO avi_info, byte abyte0[], int i)
    {
		if(m_overFlowHDDFlag == true)
			return;
        MOVI_HEADER movi_header = new MOVI_HEADER();
        AVI_FILE_HEADER avi_file_header = avi_info.AVIFileHeader;
        MP4 mp4 = avi_info.mp4;
        byte abyte1[] = new byte[4];
        byte abyte2[] = new byte[4];
        byte abyte3[] = new byte[4];
        byte abyte4[] = new byte[4];
        movi_header.FourCC[0] = 48;//0
        movi_header.FourCC[1] = 49;//1
        movi_header.FourCC[2] = 119;//w
        movi_header.FourCC[3] = 98;//b
        movi_header.FrameSize = i;
        abyte1[0] = 48;
        abyte1[1] = 49;
        abyte1[2] = 119;
        abyte1[3] = 98;
        Util.intToByteArray_LSB(16, abyte2);
        Util.intToByteArray_LSB(mp4.m_mp4para.dwChunkOffset, abyte3);
        Util.intToByteArray_LSB(movi_header.FrameSize, abyte4);
        avi_file_header.moviListSize += movi_header.FrameSize + 8;
        mp4.m_mp4para.dwChunkOffset = mp4.m_mp4para.dwChunkOffset + movi_header.FrameSize + 8;
        avi_file_header.AVIStreamHeader2.wLength += movi_header.FrameSize / avi_file_header.AVIStreamHeader2.wSampleSize;
        try
        {
            avi_info.fd.write(movi_header.FourCC, 0, 4);
            Util.intToByteArray_LSB(movi_header.FrameSize, movi_header.FccFrameSize);
            avi_info.fd.write(movi_header.FccFrameSize, 0, 4);
            avi_info.fd.write(abyte0, 0, i);
        }
        catch(Exception exception)
        {
            System.out.println("AviWriteAudio fail" + exception);
            m_overFlowHDDFlag = true;
        }
        if(avi_info.mp4.m_idxSizeWrited <= 0xffff0)
        {
        	//System.out.println("Idx Size Writed :" + avi_info.mp4.m_idxSizeWrited + " * audio =" + movi_header.FrameSize);
            System.arraycopy(abyte1, 0, avi_info.mp4.m_idxBuf, avi_info.mp4.m_idxSizeWrited, 4);
            System.arraycopy(abyte2, 0, avi_info.mp4.m_idxBuf, avi_info.mp4.m_idxSizeWrited + 4, 4);
            System.arraycopy(abyte3, 0, avi_info.mp4.m_idxBuf, avi_info.mp4.m_idxSizeWrited + 8, 4);
            System.arraycopy(abyte4, 0, avi_info.mp4.m_idxBuf, avi_info.mp4.m_idxSizeWrited + 12, 4);
            avi_info.mp4.m_idxSizeWrited += 16;
        }
    }

	
	public void init()
	{
		
	}
	
	public void run()
	{
		
		AVI_INFO avi_info = new AVI_INFO();
		while(true)
		{
	        if(m_recordFlag == PublicDefine.RUN) 
	        {
//	        	JOptionPane.showMessageDialog(null, "Stop recording because not enough free space", "Notify", 2);	            //m_FinishRecord = false;
	            int currentResolution = m_curResolutonJpg;
	            avi_info.fps = '\036';
	            m_RecordFileSize = 0;
	            m_overFlowHDDFlag = false;
	            m_FullArrayIndexFlag = false;
	            avi_info.audio.wFormatTag = 1;
	            avi_info.audio.nSamplesPerSec = 8000;
	            avi_info.audio.nChannels = 1;
	            avi_info.audio.wBitsPerSample = 16;
	            avi_info.audio.nBlockAlign = (short)((avi_info.audio.nChannels * avi_info.audio.wBitsPerSample) / 8);
	            avi_info.audio.nAvgBytesPerSec = avi_info.audio.nSamplesPerSec * avi_info.audio.nBlockAlign;
	            avi_info.audio.cbSize = 32;
	            Date date = new Date();
                SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd_HHmmss");
	            String pathFile = gsLocalPath + System.getProperty("file.separator") + simpledateformat.format(date) + ".avi";
	            try
	            {
	                File checkFile = new File(gsLocalPath);
	                if(!checkFile.exists())
	                {
	                	m_recordFlag = PublicDefine.STOP;
		                JOptionPane.showMessageDialog(null, "Stop recording because the storage path is invalid. \nPlease click \"StorageFolder\" button to set up", "Notify", 2);
		                
		                m_parent.m_bStopRecord = true;
		                m_parent.DrawControlBar();
		                m_parent.m_bStopRecord = false;
		                continue;
	                }
	                avi_info.fd = new RandomAccessFile(pathFile, "rw");
	                
	            }
	            catch(Exception exception)
	            {
	                //JOptionPane.showMessageDialog(null, "Stop recording because path save record file invalid", "Notify", 2);
	                continue;
	            }
	            if(m_AudioFlag == true)
	            {
	            	AviOpen(avi_info);
	            }else{
	            	VideoOnlyAviOpen(avi_info);
	            }
	            int l = 0;
	            //m_MinFreeStopFlag = false;
	            
	            //System.out.println("Min Free Space"  + m_MinFreeSize);
	            do
	            {
	            	while(m_AudioBufStatus[m_Audio_idx] == PublicDefine.BUFFER_FULL)
	                {
	            		if(avi_info.mp4.m_idxSizeWrited + 16 > avi_info.mp4.m_idxBuf.length)
	            		{
	            			m_FullArrayIndexFlag = true;
	            			System.out.print(avi_info.mp4.m_idxSizeWrited);
	            			break;
	            		}
	            		//todosang
//	            		if( m_CheckFreeSpaceFile.getFreeSpace() < ((m_MinFreeSize + 1) * 1024*1024))
//	            		{
//	            			m_MinFreeStopFlag = true;
//	            			break;
//	            		}
            			m_AudioBufStatus[m_AudioRec_idx] = PublicDefine.BUFFER_PROCESSING;
            			m_RecordFileSize += (m_AudioBufLen[m_Audio_idx] + 24);
                        AviWriteAudio(avi_info, m_AudioBuf[m_Audio_idx], m_AudioBufLen[m_Audio_idx]);
                        m_AudioBufStatus[m_Audio_idx] = 0;
                        m_AudioBufStatus[m_AudioRec_idx] = PublicDefine.BUFFER_EMPTY;
                        m_Audio_idx = (m_Audio_idx + 1) % 16;
	                } 
	            	
	                if(l == 0)
	                {
	                    avi_info.first_video_frame_time_stamp = System.currentTimeMillis();
	                    l = 1;
	                }
	                if(avi_info.mp4.m_idxSizeWrited + 16 > avi_info.mp4.m_idxBuf.length)
            		{
	                	m_FullArrayIndexFlag = true;
            			break;
            		}
	                //todosang
//	                if( m_MinFreeStopFlag == true || m_CheckFreeSpaceFile.getFreeSpace() < ((m_MinFreeSize + 1) * 1024*1024))
//            		{
//            			m_MinFreeStopFlag = true;
//            			break;
//            		}
	                if(m_ImageBufStatus[m_Image_idx] == PublicDefine.BUFFER_FULL)
	                {
	                    m_ImageBufLen[m_Image_idx] = (4 - m_ImageBufLen[m_Image_idx] % 4) + m_ImageBufLen[m_Image_idx];
	                    m_RecordFileSize += (m_ImageBufLen[m_Image_idx] + 24);
	                    AviWriteVideo(avi_info, m_ImageBuf[m_Image_idx], m_ImageBufLen[m_Image_idx]);
	                    
	                    m_ImageBufStatus[m_Image_idx] = PublicDefine.BUFFER_EMPTY;
	                    m_Image_idx = (m_Image_idx + 1) % 10;
	                    
	                    //System.out.println("Run Video:"+m_Image_idx);
	                }else{
	                	try{
	                		Thread.sleep(1);
	                	}catch(Exception ex)
	                	{}
	                }
	                if(currentResolution != m_curResolutonJpg)
	                	break;
	            } while((m_recordFlag == PublicDefine.RUN) &&((m_MaxRecordSize* 1024*1024) > m_RecordFileSize)
	            		&&(m_overFlowHDDFlag == false));
	            avi_info.end_video_frame_time_stamp = System.currentTimeMillis();
	            System.out.print(avi_info.mp4.m_idxSizeWrited);
	            AviClose(avi_info);
	            if(m_overFlowHDDFlag == true)
	            {
	            	try{
	            	 avi_info.fd.close();
	            	 File curFile = new File(pathFile);
	            	 curFile.delete();
	            	}catch(Exception ex){};
	            	//File.
	            	m_recordFlag = PublicDefine.STOP;
	            	JOptionPane.showMessageDialog(null, "Stop recording because overflow hdd", "Notify", 2);
	                
	                m_parent.m_bStopRecord = true;
	                m_parent.DrawControlBar();
	                m_parent.m_bStopRecord = false;
	            	 
	            }
//	            if(m_MinFreeStopFlag == true)
//	            {
//	            	m_recordFlag = PublicDefine.STOP;
//	            	JOptionPane.showMessageDialog(null, "Stop recording because not enough free space", "Notify", 2);
//	                
//	                m_parent.m_bStopRecord = true;
//	                m_parent.DrawControlBar();
//	                m_parent.m_bStopRecord = false;
//	            }
	            if(m_FullArrayIndexFlag == true)
	            {
	            	m_recordFlag = PublicDefine.STOP;
	            	JOptionPane.showMessageDialog(null, "Stop recording because movi entry array full", "Notify", 2);
	                
	                m_parent.m_bStopRecord = true;
	                m_parent.DrawControlBar();
	                m_parent.m_bStopRecord = false;
	            }
	        }else{
		        try{
		        	Thread.sleep(50);
		        }catch(Exception ex)
		        {}
	        }
		}
	}
}
