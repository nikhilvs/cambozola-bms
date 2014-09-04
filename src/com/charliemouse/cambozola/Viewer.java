/**
 ** com/charliemouse/cambozola/Viewer.java
 **  Copyright (C) Andy Wilcock, 2001.
 **  Available from http://www.charliemouse.com
 **
 **  Cambozola is free software; you can redistribute it and/or modify
 **  it under the terms of the GNU General Public License as published by
 **  the Free Software Foundation; either version 2 of the License, or
 **  (at your option) any later version.
 **
 **  Cambozola is distributed in the hope that it will be useful,
 **  but WITHOUT ANY WARRANTY; without even the implied warranty of
 **  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 **  GNU General Public License for more details.
 **
 **  You should have received a copy of the GNU General Public License
 **  along with Cambozola; if not, write to the Free Software
 **  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 **/
package com.charliemouse.cambozola;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.charliemouse.cambozola.shared.AppID;
import com.charliemouse.cambozola.shared.AviRecord;
import com.charliemouse.cambozola.shared.CamStream;
import com.charliemouse.cambozola.shared.ExceptionReporter;
import com.charliemouse.cambozola.shared.ImageChangeEvent;
import com.charliemouse.cambozola.shared.ImageChangeListener;
import com.charliemouse.cambozola.shared.MaxRecordSizeDlg;
import com.charliemouse.cambozola.shared.PlayAudio;
import com.charliemouse.cambozola.shared.PublicDefine;
import com.charliemouse.cambozola.watermark.Watermark;
import com.charliemouse.cambozola.watermark.WatermarkCollection;

public class Viewer extends java.applet.Applet implements MouseListener, MouseMotionListener, KeyListener, ImageChangeListener, ExceptionReporter, ViewerAttributeInterface
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int DEFAULT_WIDTH  = 352;
    private static final int DEFAULT_HEIGHT = 300;

    private static final String PAR_FAILUREIMAGE    = "failureimage";
    private static final String PAR_DELAY           = "delay";
    private static final String PAR_RETRIES         = "retries";
    private static final String PAR_URL             = "url";
    private static final String PAR_ACCESSORIES     = "accessories";
    private static final String PAR_WATERMARK       = "watermark";
    private static final String PAR_REMOTESESION       = "remotesession";    
    private static final String PAR_ACCESSORYSTYLE  = "accessorystyle";
    private static final String PAR_DEBUG           = "debug";
    private static final String PAR_CLICK           = "clickURL";
    private static final String PAR_DOUBLECLICK     = "doubleClickURL";
    private static final String PAR_SHOW_COPYRIGHT  = "showCopyright";
    private static final String PAR_BACKGROUND      = "backgroundColor";
    private static final String PAR_TEXTCOLOR       = "textColor";
    private static final String PAR_USERAGENT       = "userAgent";
    private static final String USER_PASS = "usr_pass";
    
    private static final int IMG_TYPE = BufferedImage.TYPE_INT_RGB;

    private static final int VAL_STYLE_INDENT       = 0;
    private static final int VAL_STYLE_OVERLAY      = 1;
    private static final int VAL_STYLE_ALWAYSON     = 2;

	private static boolean      ms_standalone           = false;
	private Properties  m_parameters            = null;
	private URL         m_documentBase          = null;
	private URL         m_codeBase              = null;
	private URL         m_mainURL               = null;
	private Vector      m_alternateURLs         = null;
	private CamStream   m_imgStream             = null;
	
    private String      m_msg                   = null;
	private AppID       m_props                 = null;
    private boolean     m_displayAccessories    = false;
    private int         m_accessoryStyle        = VAL_STYLE_INDENT;
    private PercentArea m_area                  = new PercentArea();
    private Vector      m_accessories           = new Vector();
    private Image       m_offscreenAccBar       = null;
	private Image       m_backingStore          = null;
    private boolean     m_readingStream         = false;
	private int         m_retryCount            = 1;
	private int         m_retryDelay            = 1000;
	private Image       m_failureImage          = null;
	private boolean     m_loadFailure           = false;
	private Watermark   m_wmHit                 = null;
	private WatermarkCollection m_wmCollection  = null;
    private boolean     m_debug                 = false;
    private ClickCatcher m_clickCatcher         = null;
    private int m_imgWidth                      = 0;
    private int m_imgHeight                     = 0;
    private boolean m_showCopyright             = true;
    private Color   m_backgroundColor           = Color.white;
    private Color   m_textColor                 = Color.black;
    private String      m_userAgent             = null;
    
    private AudioFormat m_audfm;
	private SourceDataLine m_line;
    private PlayAudio m_playAudio = null;
    private int m_resolutionJpg;
    private boolean m_haveAudio;
    private AviRecord m_aviRecord = null;
    private boolean m_bSnapshotFlag = false;
    private int m_count_test;
    private boolean m_bChangeMode = false;
    public boolean m_bStopRecord = false;
    private String strSnapshotStatus = "";
    private int m_MaxRecordSize = 25;
    private String m_strStorageFolderPath = "";
    private String m_remotesession="";
    public Viewer()
	{
 		m_props = AppID.getAppID();
		m_alternateURLs = new Vector();
		m_parameters = new Properties();

        m_parameters.put(PAR_ACCESSORYSTYLE,    "indent");

		m_wmCollection = new WatermarkCollection();
        m_clickCatcher = new ClickCatcher();
        m_audfm = new AudioFormat(8000F, 16, 1, true, false);
		 try{
			 m_line = (SourceDataLine) AudioSystem.getSourceDataLine(m_audfm);
			 m_line.open(m_audfm);
			 m_line.start();
		 }catch(LineUnavailableException e)
		 {
       	
		 }
		 
		 
		 m_resolutionJpg = PublicDefine.RESOLUTON_VGA;
         m_count_test = 0;
         m_haveAudio = false;
    }

	public void init()
	{
		
		if (!ms_standalone) {
			m_documentBase = getDocumentBase();
			m_codeBase = getCodeBase();
			m_parameters.put(PAR_FAILUREIMAGE,      getHTMLParameterValue(PAR_FAILUREIMAGE));
			m_parameters.put(PAR_DELAY,             getHTMLParameterValue(PAR_DELAY));
			m_parameters.put(PAR_RETRIES,           getHTMLParameterValue(PAR_RETRIES));
			m_parameters.put(PAR_URL,               getHTMLParameterValue(PAR_URL));
			m_parameters.put(PAR_ACCESSORIES,       getHTMLParameterValue(PAR_ACCESSORIES));
            m_parameters.put(PAR_WATERMARK,         getHTMLParameterValue(PAR_WATERMARK));
            m_parameters.put(PAR_WATERMARK,         getHTMLParameterValue("watermarks"));
            m_parameters.put(PAR_ACCESSORYSTYLE,    getHTMLParameterValue(PAR_ACCESSORYSTYLE));
            m_parameters.put(PAR_DEBUG,             getHTMLParameterValue(PAR_DEBUG));
            m_parameters.put(PAR_CLICK,             getHTMLParameterValue(PAR_CLICK));
            m_parameters.put(PAR_DOUBLECLICK,       getHTMLParameterValue(PAR_DOUBLECLICK));
            m_parameters.put(PAR_SHOW_COPYRIGHT,    getHTMLParameterValue(PAR_SHOW_COPYRIGHT));
            m_parameters.put(PAR_BACKGROUND,        getHTMLParameterValue(PAR_BACKGROUND));
            m_parameters.put(PAR_TEXTCOLOR,         getHTMLParameterValue(PAR_TEXTCOLOR));
            m_parameters.put(PAR_REMOTESESION,      getHTMLParameterValue(PAR_REMOTESESION));
            m_parameters.put(USER_PASS, getHTMLParameterValue(USER_PASS));
            
//            m_parameters.put(PAR_FAILUREIMAGE,      "");
//			m_parameters.put(PAR_DELAY,             5);
//			m_parameters.put(PAR_RETRIES,           5);
//			m_parameters.put(PAR_URL,               "http://192.168.20.101/?action=appletvastream");
//			m_parameters.put(PAR_ACCESSORIES,       getHTMLParameterValue(PAR_ACCESSORIES));
//            m_parameters.put(PAR_WATERMARK,         getHTMLParameterValue(PAR_WATERMARK));
//            m_parameters.put(PAR_WATERMARK,         getHTMLParameterValue("watermarks"));
//            m_parameters.put(PAR_ACCESSORYSTYLE,    getHTMLParameterValue(PAR_ACCESSORYSTYLE));
//            m_parameters.put(PAR_DEBUG,             "true");
//            m_parameters.put(PAR_CLICK,             getHTMLParameterValue(PAR_CLICK));
//            m_parameters.put(PAR_DOUBLECLICK,       getHTMLParameterValue(PAR_DOUBLECLICK));
//            m_parameters.put(PAR_SHOW_COPYRIGHT,    getHTMLParameterValue(PAR_SHOW_COPYRIGHT));
//            m_parameters.put(PAR_BACKGROUND,        getHTMLParameterValue(PAR_BACKGROUND));
//            m_parameters.put(PAR_TEXTCOLOR,         getHTMLParameterValue(PAR_TEXTCOLOR));
//            m_parameters.put(PAR_REMOTESESION,      getHTMLParameterValue(PAR_REMOTESESION));
//            m_parameters.put(USER_PASS, getHTMLParameterValue(USER_PASS));
		}
//		m_documentBase = getDocumentBase();
		m_alternateURLs = new Vector();
		
        //
        String remotesession = getParameterValue(PAR_REMOTESESION);
        if (remotesession == null)
        {
            m_remotesession = "";
        }
        else
        {
            if (remotesession.length() != 64)
            {
                m_remotesession = "";
            }
            else
            {
                m_remotesession = "&remote_session="+remotesession;
            }
        }
        
        String wmarks = getParameterValue(PAR_WATERMARK);
        if (wmarks != null) {
            m_wmCollection.populate(wmarks, m_documentBase);
        }
        String mm = getParameterValue(PAR_SHOW_COPYRIGHT);
        if ("false".equalsIgnoreCase(mm)) {
            m_showCopyright = false;
        }
        m_clickCatcher.setSingleClick(getParameterValue(PAR_CLICK));
        m_clickCatcher.setDoubleClick(getParameterValue(PAR_DOUBLECLICK));
        //
		// Set up the initial message.
		//
        String appMsg = "Please wait ...";
        if (m_showCopyright) {
            //
            // Possibly set the message.
            //
    		setMessage(appMsg);
        }
        System.err.println("// " + appMsg);
		//
		// Load the failure Image.
		//
		String fistr = getParameterValue(PAR_FAILUREIMAGE);
		if (fistr != null && !fistr.equals("")) {
			try {
				URL fiurl = new URL(m_documentBase, fistr);
				setFailureImageURL(fiurl);
			} catch (MalformedURLException mfe) {
				System.err.println("Unable to access URL for failure image -" + fistr);
			}
		}
		//
		String delay = getParameterValue(PAR_DELAY);
		if (delay != null && !delay.equals("")) {
			try {
				int di= Integer.parseInt(delay);
				setRetryDelay(di);
			} catch (Exception e) {
				System.err.println("Unable to set retry delay");
			}
		}
        String debug = getParameterValue(PAR_DEBUG);
        m_debug = (debug != null && debug.equalsIgnoreCase("true"));

		String retries = getParameterValue(PAR_RETRIES);
		if (retries != null && !retries.equals("")) {
			try {
				int ri = Integer.parseInt(retries);
				setRetryCount(ri);
			} catch (Exception e) {
				System.err.println("Unable to set retry count");
			}
		}
		
		String appurl = getParameterValue(PAR_URL);
		if(getParameterValue(PAR_URL).trim().isEmpty())
			appurl=AppID.getAppID().getStreamingURL();
		System.out.println("url :"+appurl);
		if (appurl == null && !appurl.equals("")) {
			throw new IllegalArgumentException("Missing URL");
		}
		appurl += m_remotesession;
		m_mainURL = null;
		//
		// Break apart the URLs - separator is |
		//
		StringTokenizer st = new StringTokenizer(appurl, "|");
		while(st.hasMoreTokens())
		{
			try {
				URL alt = new URL(m_codeBase, st.nextToken());
				m_alternateURLs.addElement(alt);
				if (m_mainURL == null)
				{
					m_mainURL = alt;
				}
			} catch (MalformedURLException mfe) {
				reportError(mfe);
			}
		}
	    //
	    // Set up the identifying User-Agent
	    //
		String userAgent = getParameterValue(PAR_USERAGENT);
		if (userAgent != null && !userAgent.equals("") && !userAgent.equalsIgnoreCase("default")) {
			m_userAgent = userAgent;
		}
		else
		{
			System.err.println("XComented manythings for testing");
	    	m_userAgent = m_props.getAppNameVersion() + "/Java " + System.getProperty("java.version") + " " + System.getProperty("java.vendor");
		}
        //
		m_playAudio = new PlayAudio();
		m_haveAudio = true;
		m_playAudio.start();
		m_strStorageFolderPath = System.getProperty("user.home");
		m_aviRecord = new AviRecord(m_resolutionJpg,m_haveAudio,m_strStorageFolderPath,m_MaxRecordSize,this);
		m_aviRecord.start();
			setCurrentURL(m_mainURL);
        Color textCol = parseColor(getParameterValue(PAR_TEXTCOLOR));
        if (textCol != null) {
            setTextColor(textCol);
        }
        //
		configureAccessories(getParameterValue(PAR_ACCESSORIES));
        //
		setBackground(Color.white);
		addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        
	}

	public void destroy()
	{
		stop();
	}

	public static void main(String args[])
	{
		ms_standalone = true;
		Frame f = new Frame(AppID.getAppID().getAppName());
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we)
			{
				System.exit(0);
			}
		});

		f.setLayout(new BorderLayout());
        Viewer cv = new Viewer();
        //
        int width = DEFAULT_WIDTH;
        int height = DEFAULT_HEIGHT;
        //
        StringBuffer concatURL = new StringBuffer();
        for (int i=0;i< args.length;i++)
        {
            String arg = args[i];
            if (arg.startsWith("-")) {
                int eqidx = arg.indexOf("=")+1;
                if (arg.equals("-noaccessories")) {
                    cv.m_parameters.put(PAR_ACCESSORIES, "none");
                } else if (arg.startsWith("-accessories=")) {
	                cv.m_parameters.put(PAR_ACCESSORIES, arg.substring(eqidx));
                } else if (arg.startsWith("-retries=")) {
	                cv.m_parameters.put(PAR_RETRIES, arg.substring(eqidx));
                } else if (arg.startsWith("-delay=")) {
	                cv.m_parameters.put(PAR_DELAY, arg.substring(eqidx));
                } else if (arg.startsWith("-failureimage=")) {
	                cv.m_parameters.put(PAR_FAILUREIMAGE, arg.substring(eqidx));
                } else if (arg.startsWith("-watermark=") || arg.startsWith("-watermarks=")) {
                     cv.m_parameters.put(PAR_WATERMARK, arg.substring(eqidx));
                } else if (arg.startsWith("-accessorystyle=")) {
                     cv.m_parameters.put(PAR_ACCESSORYSTYLE, arg.substring(eqidx));
                } else if (arg.startsWith("-width=")) {
	                width = Integer.parseInt(arg.substring(eqidx));
                } else if (arg.startsWith("-height=")) {
	                height = Integer.parseInt(arg.substring(eqidx));
                } else if (arg.startsWith("-debug")) {
                     cv.m_parameters.put(PAR_DEBUG, "true");
                } else if (arg.startsWith("-showCopyright=")) {
                     cv.m_parameters.put(PAR_SHOW_COPYRIGHT, arg.substring(eqidx));
                } else if (arg.startsWith("-backgroundColor=")) {
                     cv.m_parameters.put(PAR_BACKGROUND, arg.substring(eqidx));
                } else if (arg.startsWith("-textColor=")) {
                     cv.m_parameters.put(PAR_TEXTCOLOR, arg.substring(eqidx));
                } else if (arg.startsWith("-userAgent=")) {
                     cv.m_parameters.put(PAR_USERAGENT, arg.substring(eqidx));
                 } else {
                    usage();
                    System.exit(0);
                }
            } else {
	            if (concatURL.length() != 0)
	            {
		            concatURL.append("|");
	            }
	            concatURL.append(arg.trim());
            }
        }
        f.setSize(width, height);
        if(concatURL.length()==0)
        concatURL.append(AppID.getAppID().getStreamingURL());
        if (concatURL.length() == 0) {
            usage();
            System.exit(0);
        }
		cv.m_parameters.put("url", concatURL.toString());
		f.add(BorderLayout.CENTER, cv);
		cv.init();
		//
		f.setVisible(true);
		cv.start();
	}

	private String getHTMLParameterValue(String key)
	{
		String s = getParameter(key);
		if (s == null)
		{
			return "";
		}
		return s;
	}

	private String getParameterValue(String key)
	{
		return m_parameters.getProperty(key, null);
	}

	void setFailureImageURL(URL fistr)
	{
		try {
			m_failureImage = createImage((ImageProducer)fistr.getContent());
			m_failureImage.getWidth(this);
		} catch (IOException ie) {
			System.err.println("Unable to access failure image contents - " + ie);
		}
	}

	void setRetryCount(int rc)
	{
		if (rc < 1)
		{
			return;
		}
		m_retryCount = rc;
	}

	void setRetryDelay(int delay)
	{
		if (delay < 0)
		{
			return;
		}
		m_retryDelay = delay;
	}

	private void configureAccessories(String acclist)
    {
        //
        // Set up the accessory style.
        //
        String as = getParameterValue(PAR_ACCESSORYSTYLE);
        if (as != null) {
            if (as.equalsIgnoreCase("indent"))
            {
                m_accessoryStyle = VAL_STYLE_INDENT;
            } else if (as.equalsIgnoreCase("overlay")) {
                m_accessoryStyle = VAL_STYLE_OVERLAY;
            } else if (as.equalsIgnoreCase("always")) {
                m_accessoryStyle = VAL_STYLE_ALWAYSON;
            }
        }
        //
        // Set up the accessories (the things on the LHS).
        //
        if (acclist == null || acclist.equals("") || acclist.equalsIgnoreCase("default"))
        {
            //
            // Default list.
            //
            acclist = "StorageFolder,MaxRecordSize,Record,Snapshot";
        } else if (acclist.equalsIgnoreCase("none")) {
            //
            // Explicitly none...
            //
            acclist = "";
        }
        StringTokenizer st = new StringTokenizer(acclist, ", ");
        while(st.hasMoreTokens())
        {
            String tok = st.nextToken();
            try {
                Class accClazz = Class.forName("com.charliemouse.cambozola.accessories." + tok + "Accessory");
                Accessory acc = (Accessory)accClazz.newInstance();
                acc.setViewerAttributes(this);
				//
                // MS JVM in IE is really picky about this code, and used to crash if
                // it used a 'continue'...
                //
                if (acc.isEnabled())
                {
               		acc.getIconImage();
                    m_accessories.addElement(acc);
                }
            } catch (Exception exc) {
                System.err.println("Unable to load accessory - " + tok);
                exc.printStackTrace();
            }
        }
    }

    public synchronized void reportError(Throwable t)
    {
	    reportNote(t.getMessage());
		m_loadFailure = true;
	    stop();
    }

	public synchronized void reportFailure(String s)
	{
		m_loadFailure = true;
		reportNote(s);
	}

	public synchronized void reportNote(String s)
	{
		System.err.println(s);
		setMessage(s);
		m_readingStream = false;
		repaint();
	}

	private synchronized void setMessage(String s)
	{
		m_msg = s;
	}

	public void start()
	{
	}

	public void stop()
	{
		if(m_playAudio != null)
		{
			
		}
        if (m_imgStream != null) {
            m_imgStream.unhook();
            m_imgStream = null;
        }
		m_readingStream = false;
        for (Enumeration e = m_accessories.elements();e.hasMoreElements();)
        {
            ((Accessory)e.nextElement()).terminate();
        }
	}

    public void setCurrentURL(URL loc)
    {
        String fullURL;
	    m_loadFailure = false;

        m_mainURL = loc;
	    if (m_imgStream != null) {
		    m_msg = "";
		    m_imgStream.removeImageChangeListener(this);
	        m_imgStream.unhook();
	    }
        
        
		m_imgStream = new CamStream(m_mainURL, m_userAgent, m_documentBase, m_retryCount, m_retryDelay, this, m_debug,
				m_haveAudio,m_resolutionJpg,getParameterValue(USER_PASS));
		m_imgStream.addImageChangeListener(this);
	    m_imgStream.start();
    }

    public void displayURL(URL url, String target)
    {
	    if (ms_standalone) return;

	     if (target == null)
	     {
	         getAppletContext().showDocument(url);
	     } else {
	         getAppletContext().showDocument(url, target);
	     }
    }

	public Vector getAlternateURLs()
	{
		return m_alternateURLs;
	}

	public void setAlternateURLs(Vector vector)
	{
		m_alternateURLs = vector;
	}

	public void imageChanged(ImageChangeEvent ce)
	{
//		System.out.println("receive data");
		int iAudioLen = m_imgStream.getAudioLength();
		int resetFlag = m_imgStream.getResetFlag();
		int imgBegin = m_imgStream.getImgBegin();
		int imgEnd = m_imgStream.getImgCurrentIndex();
		int resolutionJpeg = m_imgStream.getResolutionJpeg();
		int resetAudioBufferCount = m_imgStream.getResetAudioBufferCount();
		
		if(m_resolutionJpg != resolutionJpeg)
		{
			if(m_aviRecord!=null && m_aviRecord.getRecordFlag() == PublicDefine.RUN)
	    	{
	    		m_aviRecord.setRecordFlag(PublicDefine.STOP);
	    		m_bChangeMode = true;
	    		//System.out.println("Stop record");
	    	}
		}
		m_resolutionJpg = resolutionJpeg;
		if(m_aviRecord!=null)
		m_aviRecord.getResolutionJpg(resolutionJpeg);
		
		if(resetFlag == 1)
		{
			m_aviRecord.resetCounter();
			System.out.println("************ Reset Flag");
		}
		
		if(iAudioLen > 0)
		{
			byte[] arrAudioData = m_imgStream.getAudio();
			if((m_aviRecord!=null) && m_aviRecord.getRecordFlag() == PublicDefine.RUN)
			{
				System.out.println("receive audio");
				m_aviRecord.getAudio(arrAudioData, iAudioLen, resetAudioBufferCount);
			}
			if(m_playAudio!=null)
			m_playAudio.getAudio(arrAudioData, iAudioLen);
			m_count_test = 0;
			
		}
		else{
			//System.out.println("image * " + imgEnd );
		}
		
		if(m_aviRecord!=null &&  m_aviRecord.getRecordFlag() == PublicDefine.RUN)
		{
			byte[] imgBufTmp = m_imgStream.getRawImage();
			System.out.println("receive video");
			m_aviRecord.getImage(imgBufTmp, imgBufTmp.length,imgEnd);
			
		}
		if(m_bSnapshotFlag == true)
		{
			File checkFile = new File(m_strStorageFolderPath);
            if(!checkFile.exists())
            {
            	strSnapshotStatus = "Snapshot failed because the storage path is invalid. \nPlease click \"StorageFolder\" button to set up";
            }else{
				Date date = new Date();
	            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd_HHmmss");
	    		String m_pathSnapshot = m_strStorageFolderPath +
	    			System.getProperty("file.separator");
	    		String filename = simpledateformat.format(date) + ".jpg";
				File file = new File(m_pathSnapshot + filename);
	            try
	            {
	            	byte[] imgBufTmp = m_imgStream.getRawImage();
	                FileOutputStream fileoutputstream = new FileOutputStream(file);
	                fileoutputstream.write(imgBufTmp, 0, imgBufTmp.length);
	                fileoutputstream.close();
	                strSnapshotStatus = "Snapshot "+ filename + " success";
	                //showStatus("Snapshot "+ filename + " success");
	            }
	            catch(Exception exception)
	            {
	            	strSnapshotStatus = "Snapshot error";
	                System.err.println(exception);
	            }
            }
			m_bSnapshotFlag = false;
		}
		if(m_bChangeMode == true)
		{
			DrawControlBar();
			m_bChangeMode =false;
		}
        update(getGraphics());
        getToolkit().sync();
        
	}

	public void drawString(Graphics g, String s, int x, int y, int width)
	{
	        // FontMetrics gives us information about the width,
	        // height, etc. of the current Graphics object's Font.
	        FontMetrics fm = g.getFontMetrics();

	        int lineHeight = fm.getHeight();

	      
        int curX = x + 120;
        int curY = y;

        if (s.contains("601")) {
            g.drawString("Unable to communicate with camera, please restart the camera.", curX, curY); // does not match
            return;
        }
        if (s.contains("602")) {
            g.drawString("Streaming timeout, please reload. ", curX, curY);//Session Timeout
            return;
        }
        if (s.contains("603")) {
            g.drawString("Unable to communicate with camera, please restart the camera.", curX, curY);// is not correct
            return;
        }
        if (s.contains("604")) {
            g.drawString("Cannot connect to Babymonitor, please make sure that it's turn on.", curX, curY);//Camera not Available
            return;
        }
        if (s.contains("605")) {
            g.drawString("IP Blocked, please contact administrator.", curX, curY); //Error IP Block
            return;
        }

        if (s.contains("401")) {
            g.drawString("Authentication failed.", curX, curY); //Error IP Block
            return;
        }
	        
	        String[] words = s.split(" ");

	        for (String word : words)
	        {
	                // Find out thw width of the word.
	                int wordWidth = fm.stringWidth(word + " ");

	                // If text exceeds the width, then move to next line.
	                if (curX + wordWidth >= x + width)
	                {
	                        curY += lineHeight;
	                        curX = x;
	                }

	                g.drawString(word, curX, curY);

	                // Move over to the right for next word.
	                curX += wordWidth;
	        }
	}
	
	public void paint(Graphics g)
	{
		update(g);
	}

	public void update(Graphics g)
	{
        if (g == null) return;
		Dimension d = getSize();
		if (m_backingStore == null || m_backingStore.getWidth(this) != d.width || m_backingStore.getHeight(this) != d.height) {
		    m_backingStore = new BufferedImage(d.width, d.height, IMG_TYPE);
			//
			// Size has changed, recalculate the hit areas
			//
			m_wmCollection.recalculateLocations(d);
		}
		Graphics gg2 = m_backingStore.getGraphics();
		if (m_loadFailure && m_failureImage != null) {
			//
			// Draw the failure image.
			//
			paintFrame(gg2,m_failureImage, d, null);
		} else if (!m_readingStream) {
            gg2.setPaintMode();
            gg2.setColor(m_backgroundColor);
            if (isDisplayingAccessories() && m_accessoryStyle == VAL_STYLE_INDENT)
            {
            	System.out.println("Displaying accesories");
                gg2.fillRect(Accessory.BUTTON_SIZE, 0, d.width, d.height);
            } else {
            	System.out.println("not Displaying accesories");
                gg2.fillRect(0, 0, d.width, d.height);
            }
            //
            FontMetrics fm = gg2.getFontMetrics();
            if (m_msg != null) {
                int width = fm.stringWidth(m_msg);
                gg2.setColor(m_textColor);
                drawString(gg2,m_msg,10,d.height/2,d.width - 100);
                gg2.setColor(m_backgroundColor);
            }
            
            
            FontMetrics fm1 ;//= g.getFontMetrics();
        	fm1 = g.getFontMetrics();
        	String msg ="2";
            if (msg != null) {
                int width = fm1.stringWidth(msg);
                gg2.setColor(Color.BLUE);
                gg2.fillRect (d.width-55 ,0,d.width - width -1 , 15);
                gg2.setColor(Color.BLACK);
                drawString(gg2,msg,d.width-50 ,10,d.width - width -1 );
                gg2.setColor(Color.BLACK);
            }
            
            
			//
			// Draw the accessories...
			//
			paintAccessories(gg2);
        }
		if (m_imgStream != null) {
        	Image img = m_imgStream.getCurrent();
        	if (img != null) {
		        m_loadFailure = false;
           		m_readingStream = true;
           	 	paintFrame(gg2,img, d, m_wmCollection);
			}
        }
		g.drawImage(m_backingStore,0,0,null);
		gg2.dispose();
	}


    public void paintFrame(Graphics g, Image img, Dimension d, WatermarkCollection wmc)
    {
       
        int indent = 0;
        if (isDisplayingAccessories() && m_accessoryStyle == VAL_STYLE_INDENT)
        {
            indent = Accessory.BUTTON_SIZE;
        }
        m_imgWidth = img.getWidth(this);
        m_imgHeight = img.getHeight(this);
        if (m_imgWidth == -1 || m_imgHeight == -1) {
        	return; // No size for the image, no zoom.
        
        }
//        System.err.println("valid image");
	    //
        //
        // Work out the area to zoom into.
        //
        Rectangle imgarea = m_area.getArea(m_imgWidth, m_imgHeight);

        g.drawImage(img, indent, 0, d.width, d.height, imgarea.x, imgarea.y, imgarea.x + imgarea.width, imgarea.y + imgarea.height, this);
	    //
	    // Draw the watermark
	    //
	    if (wmc != null)
	    {
		    wmc.paint(g);
	    }
	    //
	    // Draw the accessories...
	    //
		paintAccessories(g);
    }

    private void paintAccessories(Graphics g)
    {
        Dimension d = getSize();
        int asize = m_accessories.size();
        if (isDisplayingAccessories() && Accessory.BUTTON_SIZE > 0 && asize > 0) {
            //
            // First time - build up, store in image, and reuse...
            //
            if (m_offscreenAccBar == null) {
                m_offscreenAccBar = createImage(Accessory.BUTTON_SIZE, m_accessories.size() * Accessory.BUTTON_SIZE);
                Graphics accessoryBar = m_offscreenAccBar.getGraphics();
                //
                int idx = 0;
                for (Enumeration accEnum = m_accessories.elements(); accEnum.hasMoreElements();)
                {
                    accessoryBar.setColor(Color.lightGray);
                    Accessory acc = (Accessory)accEnum.nextElement();
                    int yoffset = idx*Accessory.BUTTON_SIZE;
                    accessoryBar.fill3DRect(0,yoffset, Accessory.BUTTON_SIZE, Accessory.BUTTON_SIZE, true);
                    accessoryBar.drawImage(acc.getIconImage(), Accessory.ICON_INDENT, yoffset+Accessory.ICON_INDENT,
                            new ImageObserver()
                            {
                                public boolean imageUpdate(Image img, int infoflags,
                                                           int x, int y, int width, int height)
                                {
                                    return true;
                                }
                            });
                    idx++;
                }
                accessoryBar.dispose();
            }
            
           
            
            //
            g.drawImage(m_offscreenAccBar,0,0,null);
            //
            // Draw the white box only if we are indenting...
            //
            if (m_accessoryStyle == VAL_STYLE_INDENT)
            {
                int fluff = (m_accessories.size() * Accessory.BUTTON_SIZE);
                g.setColor(m_backgroundColor);
                g.fillRect(0,fluff,Accessory.BUTTON_SIZE,d.height);
            }
        }
    }


    public void keyPressed(KeyEvent ke)
    {
        if (!m_readingStream)
        {
            return;
        }
        if (ke.getKeyCode() == KeyEvent.VK_HOME)
        {
            m_area.reset();
        } else if (ke.getKeyCode() == KeyEvent.VK_PAGE_UP)
        {
            m_area.zoomIn();
        } else if (ke.getKeyCode() == KeyEvent.VK_PAGE_DOWN)
        {
            m_area.zoomOut();
        } else if (ke.getKeyCode() == KeyEvent.VK_LEFT)
        {
            m_area.panHorizontal(PercentArea.LEFT);
        } else if (ke.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            m_area.panHorizontal(PercentArea.RIGHT);
        } else if (ke.getKeyCode() == KeyEvent.VK_UP)
        {
            m_area.panVertical(PercentArea.UP);
        } else if (ke.getKeyCode() == KeyEvent.VK_DOWN)
        {
            m_area.panVertical(PercentArea.DOWN);
        }
    }

    public void keyTyped(KeyEvent e)
    {
    }

    public void keyReleased(KeyEvent e)
    {
    }

	public void mouseEntered(MouseEvent me)
	{
	}


	public void mouseExited(MouseEvent me)
	{
		if (isDisplayingAccessories())
		{
			setDisplayingAccessories(false);
			repaint();
		}
	}

    public boolean isDisplayingAccessories()
    {
        return m_displayAccessories || m_accessoryStyle == VAL_STYLE_ALWAYSON;
    }

    public void setDisplayingAccessories(boolean b)
    {
        m_displayAccessories = b;
    }

	public void mouseClicked(final MouseEvent me)
	{
		if (!ms_standalone && !isDisplayingAccessories() && m_wmHit != null)
		{
			//
			// Go to the url.
			//
			displayURL(m_wmHit.getURL(), null);
			return;
		}
        if (me.getX() >= Accessory.BUTTON_SIZE)
        {
            // Mouse Click [Calculate [point on image]
            if (m_imgWidth > 0 && m_imgHeight > 0 && !ms_standalone && getDocumentBase() != null) {
                double px = ((double)me.getX() / (double)getWidth());
                double py = ((double)me.getY() / (double)getHeight());
                // % of view
                Rectangle imgarea = m_area.getArea(m_imgWidth, m_imgHeight);

                int imgx = (int)(imgarea.getX() + (imgarea.getWidth() * px));
                int imgy = (int)(imgarea.getY() + (imgarea.getHeight() * py));

                final Point imgPoint = new Point(imgx, imgy);
                Runnable tr = new Runnable()
                {
                    public void run()
                    {
                        if (me.getClickCount() == 1) {
                            m_clickCatcher.mouseClicked(getDocumentBase(), imgPoint);
                        } else {
                            m_clickCatcher.mouseDoubleClicked(getDocumentBase(), imgPoint);
                        }
                    }
                };
                new Thread(tr).start();
            }
            return;
        }
        int idx = (int)(me.getY()/Accessory.BUTTON_SIZE);
        if (idx >= m_accessories.size())
        {
            // System.err.println("Out of range for accessories");
        } else {
            //
            // Get the local location...
            //
            Point p = new Point(me.getX(), me.getY() - (idx * Accessory.BUTTON_SIZE));
            ((Accessory)(m_accessories.elementAt(idx))).actionPerformed(p);
        }
	}


	public void mousePressed(MouseEvent me)
	{
	}


    public void mouseReleased(MouseEvent me)
    {
    }

    public void mouseDragged(MouseEvent me)
    {
    }

    public void mouseMoved(MouseEvent me)
    {
	    boolean needRepaint = false;
        Point p = me.getPoint();
        //
	    boolean previously = isDisplayingAccessories();
	    //
        if (p.x < Accessory.BUTTON_SIZE) {
            if (m_accessories.size() > 0) {
                setDisplayingAccessories(true);
                int idx = (int)(me.getY()/Accessory.BUTTON_SIZE);
	            //
	            String statusFeedback = "";
                if (idx < m_accessories.size())
                {
                    String desc = ((Accessory)(m_accessories.elementAt(idx))).getDescription();
                    if (desc != null) {
	                    statusFeedback = desc;
                    }
                    showStatus(statusFeedback);
                }
                
            }
        } else {
            setDisplayingAccessories(false);
        }
	    //
	    // Only clickable in a web page.
	    //
	    if (m_displayAccessories == false && !ms_standalone)
	    {
		    //
		    // Are we over a Clickable hit point?
		    //
		    Watermark pwnew = m_wmCollection.isOverClickableWatermark(p);
		    if (pwnew != m_wmHit)
		    {
			    m_wmHit = pwnew;
			    needRepaint = true;
			    setCursor((m_wmHit != null)?Cursor.getPredefinedCursor(Cursor.HAND_CURSOR):Cursor.getDefaultCursor());
		    }
	    }
	    if (isDisplayingAccessories() != previously)
	    {
		    needRepaint = true;
	    }
	    if (needRepaint)
	    {
	        repaint();
	    }
    }

    public void showStatus(String s)
    {
		if (!ms_standalone) super.showStatus(s);
    }

    public PercentArea getViewArea()
    {
        return m_area;
    }
    public boolean ControlRecord()
    {
    	
    	if(m_aviRecord.getRecordFlag() == PublicDefine.STOP)
    	{
    		//todosang
//    		File file = new File(m_strStorageFolderPath);
//    		long temp = file.getFreeSpace();
//    		System.out.println(temp);
//    		if(file.getFreeSpace() < ((m_MinFreeSpace + 1)*1024*1024))
//    		{
//            	JOptionPane.showMessageDialog(null, "Don't start record because not enough free space", "Notify", 2);
//            	return false;
//    		}
    		DrawControlBar();
    		m_aviRecord.resetRecordFlag();
    		m_aviRecord.setRecordFlag(PublicDefine.RUN);
    		return true;
    	}
    	else
    	{
    		DrawControlBar();
    		m_aviRecord.setRecordFlag(PublicDefine.STOP);
    		//m_aviRecord.stop();
    		return true;
    	}
    }
    public void ControlSnapshot()
    {
    	if(m_bSnapshotFlag == false)
    	{
    		m_bSnapshotFlag = true;
    		while(m_bSnapshotFlag == true)
    		{
    			try{
    			Thread.sleep(4);
    			}catch(Exception ex)
    			{}
    		}
    		JOptionPane.showMessageDialog(null, strSnapshotStatus, "Notify", 2);
    	}
    }
    public void SetMaxRecordSize()
    {
    	MaxRecordSizeDlg freeSpaceDlg = new MaxRecordSizeDlg(new Frame(""),m_MaxRecordSize);
   	 	if (freeSpaceDlg.id) {
	   		m_MaxRecordSize = freeSpaceDlg.minFreeSpace.getValue();
	   		
	   		m_aviRecord.setMaxRecordSize(m_MaxRecordSize);
   			
   	 	}
   		  
    }
    public void ChooseStorageFolder()
    {
    	JFileChooser chooser = new JFileChooser();
    	
   	 	chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
   	 	chooser.setSelectedFile(new File(m_strStorageFolderPath));
   	 	int returnVal = chooser.showOpenDialog(this);
   	    if(returnVal == JFileChooser.APPROVE_OPTION) {
   	    	String strTmp = chooser.getSelectedFile().getPath();
   	    	if(strTmp.length() > 255)
   	    	{
   	    		JOptionPane.showMessageDialog(null, "Storage path length has to be shorter than 256 characters, this path name is invalid.", "Notify", 2);
   	    		return;
   	    	}
   	    	m_strStorageFolderPath = strTmp;
    		m_aviRecord.setStoreFolder(m_strStorageFolderPath);
   	    }
    }
    public void DrawControlBar()
    {
            m_offscreenAccBar = createImage(Accessory.BUTTON_SIZE, m_accessories.size() * Accessory.BUTTON_SIZE);
            Graphics accessoryBar = m_offscreenAccBar.getGraphics();
            //
            int idx = 0;
            for (Enumeration accEnum = m_accessories.elements(); accEnum.hasMoreElements();)
            {
                accessoryBar.setColor(Color.lightGray);
                Accessory acc = (Accessory)accEnum.nextElement();
                int yoffset = idx*Accessory.BUTTON_SIZE;
                accessoryBar.fill3DRect(0,yoffset, Accessory.BUTTON_SIZE, Accessory.BUTTON_SIZE, true);
                if(m_bChangeMode == true|| m_bStopRecord == true)
                {
                	acc.controlFlag();

                }
                accessoryBar.drawImage(acc.getIconImage(), Accessory.ICON_INDENT, yoffset+Accessory.ICON_INDENT,
                        new ImageObserver()
                        {
                            public boolean imageUpdate(Image img, int infoflags,
                                                       int x, int y, int width, int height)
                            {
                                return true;
                            }
                        });
                idx++;
            }
            accessoryBar.dispose();
    }
    public CamStream getStream()
    {
        return m_imgStream;
    }

	public boolean isStandalone()
	{
		return ms_standalone;
	}

	public Vector getAccessories()
    {
        return m_accessories;
    }

    /**
     * Read a color definition of the format '#RRGGBB' where RR, GG  and BB
     * are 3 hex values.
     * @param s The string to check
     * @return null if there is an issue, or the color if not.
     */
    private static Color parseColor(String s) {
        if (s == null || !(s.startsWith("#") || s.length() == 7)) {
            return null;
        }
        int r = Integer.parseInt(s.substring(1,3), 16);
        int g = Integer.parseInt(s.substring(3,5), 16);
        int b = Integer.parseInt(s.substring(5,7), 16);
        //
        return new Color(r,g,b);
    }

    public void setBackgroundColor(Color col)
    {
        m_backgroundColor = col;
    }

    public void setTextColor(Color col)
    {
        m_textColor = col;
    }

    public static void usage()
    {
        System.err.println("Usage: WebCamURL [otherURLs] [-accessories=comma separated accessory list]");
        System.err.println("Current set of accessories are:");
        System.err.println(" o ZoomIn       - Zooms in to the image");
        System.err.println(" o ZoomOut      - Zooms out of the image");
        System.err.println(" o Home         - Shows all the image");
        System.err.println(" o Pan          - Pan around a zoomed-in image");
	    System.err.println(" o ChangeStream - Swap to a different stream (if > 1 listed)");
        System.err.println(" o Info         - Displays information about the stream");
        System.err.println(" o WWWHelp      - Displays a web page showing help");
        System.err.println("");
        System.err.println(" -debug                      Write debug information");
        System.err.println(" -width={width}              Sets the width of the application");
        System.err.println(" -height={height}            Sets the height of the application");
        System.err.println(" -noaccessories              Will not display any accessories");
        System.err.println(" -accessories=none           Will not display any accessories");
        System.err.println(" -accessories=default        Will display the default set of accessories");
        System.err.println(" -accessorystyle={see below} Defines how the accessories will appear on top-left");
        System.err.println("   indent                      Will squeeze the image [default]");
        System.err.println("   overlay                     Will overlay the accessories onto the image");
        System.err.println("   always                      Always display the accessories (overlaid)");
	    System.err.println(" -retries={num}              The number of retries (default = 1)");
	    System.err.println(" -delay={num}                The number of milliseconds between retries");
	    System.err.println(" -failureimage={url}         Image to display if failure to connect");
        System.err.println(" -backgroundColor=#RRGGBB    Background Color in hex - e.g. #FF0000 for red");
        System.err.println(" -textColor=#RRGGBB          Text Color in hex - e.g. #FFFFFF for white");
        System.err.println(" -userAgent={useragent}      Sets the user-agent string, which will be used in the HTTP-request (f.e. 'Mozilla/5.0'");
        System.err.println(" -watermark={see below}      List of watermarks, separated by '|'");
        System.err.println("   imageURL|corner|linkURL     Watermark information, separated by '|'");
    }
}
