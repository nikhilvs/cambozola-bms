package com.charliemouse.cambozola.shared;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class PlayAudio extends Thread{

	 private AudioFormat m_audfm;
	 private SourceDataLine m_line;
	 private boolean m_bRunAudio;
	 private boolean m_bPlayAudio;
	 private int m_aud_idx;
	 private int m_audRec_idx;
	 private byte[][] m_AudBuf;
	 private int[] m_AudBufLen;
	 private int[] m_AudBufStatus;
	 
	 public PlayAudio()
	 {
		 m_bRunAudio = true;
		 m_bPlayAudio = true;
		 m_aud_idx = 0;
		 m_AudBuf = new byte[PublicDefine.MAX_AUDIO_BUR_NUMBER][PublicDefine.MAX_AUDIO_BUF_LEN*16];
		 m_AudBufLen = new int[PublicDefine.MAX_AUDIO_BUR_NUMBER];
		 m_AudBufStatus = new int[PublicDefine.MAX_AUDIO_BUR_NUMBER];
		 m_audRec_idx = 0;
		 m_audfm = new AudioFormat(8000F, 16, 1, true, false);
//		 m_audfm = new AudioFormat(4000F, 16, 1, true, false);
		 try{
        	m_line = (SourceDataLine) AudioSystem.getSourceDataLine(m_audfm);
        	m_line.open(m_audfm,16*1010);
		 }catch(LineUnavailableException e)
		 {
        	
		 }
		 
	 }
	 public void init()
	 {
		
	 }
	 public void setPlayAudio(boolean bPlayAudio)
	 {
		 m_bPlayAudio = false;
		 for(int i = 0 ; i < PublicDefine.MAX_AUDIO_BUR_NUMBER;i++)
		 {
			 if(m_AudBufStatus[i] != PublicDefine.BUFFER_EMPTY)
			 {
				 Util.memSet(m_AudBuf[i], PublicDefine.MAX_AUDIO_BUF_LEN, (byte)0);
			 }
		 }
	 }
	 public void getAudio(byte[] audioData, int audioLen)
	 {
		 int i = 0;
		 
		 if(m_bPlayAudio == false)
			 return;
//		 
//		  System.out.println("GetAudio: Audio len " + audioLen);
		 if(audioLen > (16160 / 4))
		 {
			 return;
//			 audioLen = 16160/4;
		 }
		 //audioLen = 0;//1010;
		 while(i < audioLen)
		 {
			 int len;
			 if(i + 1010*16 <= audioLen)
				 len = 1010*16;
			 else{
				 len = audioLen - i;
			 }
			 if(m_AudBufStatus[m_audRec_idx] == PublicDefine.BUFFER_EMPTY)
			 {
				 m_AudBufStatus[m_audRec_idx] = PublicDefine.BUFFER_PROCESSING;
				 System.arraycopy(audioData, i,m_AudBuf[m_audRec_idx],0 , len);
				 m_AudBufLen[m_audRec_idx] = len;
				 m_AudBufStatus[m_audRec_idx] = PublicDefine.BUFFER_FULL;
				 i += len;
				 m_audRec_idx = (m_audRec_idx + 1)%PublicDefine.MAX_AUDIO_BUR_NUMBER;
				 //System.out.printf("receive buffer %d \n", m_audRec_idx);
			 }
			 else
			 {
				 try {
						sleep(100);
					} catch (InterruptedException ie) {
						break;
					}
			 }
		 }
//		 System.out.println("-->GetAudio end! ");
	 }
	 public void run()
	 {
		 m_line.start();
		 while(m_bRunAudio)
		 {
			 if(m_bPlayAudio == true)
			 {
				 if(m_AudBufStatus[m_aud_idx] != PublicDefine.BUFFER_FULL)
				 {
					 try {
							Thread.sleep(20);
						} catch (InterruptedException ie) {
							break;
						}
					
				 }else
				 {
					m_AudBufStatus[m_aud_idx] = PublicDefine.BUFFER_PROCESSING;
					if(m_AudBufLen[m_aud_idx] > 0)
					{
//						System.out.println("AudioThread:write audio data");
						m_line.write(m_AudBuf[m_aud_idx],0,m_AudBufLen[m_aud_idx]);
						m_AudBufLen[m_aud_idx] = 0;
						m_AudBufStatus[m_aud_idx] = PublicDefine.BUFFER_EMPTY;
						m_aud_idx = (m_aud_idx +1) %PublicDefine.MAX_AUDIO_BUR_NUMBER;
					}
					else
					{
						m_AudBufStatus[m_aud_idx] = PublicDefine.BUFFER_EMPTY;
						m_aud_idx = (m_aud_idx +1) %PublicDefine.MAX_AUDIO_BUR_NUMBER;
				 
					}
				 }
			 }else{
				 try {
						Thread.sleep(1000);
					} catch (InterruptedException ie) {
						break;
					}
			 }
		 }
		 
		 m_line.drain();
		 m_line.flush();
		 m_line.close();
	 }
}
