/**
 ** com/charliemouse/cambozola/shared/CamStream.java
 **  Copyright (C) Andy Wilcock, 2001.
 **  Available from http://www.charliemouse.com
 **
 ** This file is part of the Cambozola package (c) Andy Wilcock, 2001.
 ** Available from http://www.charliemouse.com
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
package com.charliemouse.cambozola.shared;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;


public class CamStream extends Thread {
	public static final int CONNECT_STYLE_SOCKET = 1;
	public static final int CONNECT_STYLE_HTTP   = 2;


	private static final int IMG_FLUFF_FACTOR = 1;

	private ExceptionReporter m_reporter = null;
	private Vector<ImageChangeListener> m_listeners;
	private Object m_lock = new Object();
	private Image m_imain = null;
	private Toolkit m_tk;
	private URL m_stream;
	private String m_userpassEncoded;
	private URL m_docBase;
	private DataInputStream m_inputStream = null;
	private boolean m_isDefunct = false;
	private boolean m_collecting = false;
	private byte[] m_rawImage;
	private int m_ImageDataLen;
	private byte[] m_Audio;
	private int m_audioLength;
	private int m_AudioFlag = 0;
	private int m_imgBegin = 0;
	private int m_imgCurrentIndex = 0;
	private int m_ResetFlag = 0;
	private String m_imageType = "image/jpeg";
	private long m_startTime = 0;
	private int m_imgidx = 0;
	private int m_retryCount = 1;
	private int m_retryDelay = 1000;
	private String m_appName = "";
	private boolean m_defaultStreamProcessor = true;
	private boolean m_debug = true;
	private int m_count_test = 0;
	private boolean m_haveAudio = false;
	private int m_resolutionJpeg;
	private int m_ResetAudioBufferCount = 0;
	public CamStream(URL strm, String app, URL docBase, int retryCount, int retryDelay, ExceptionReporter reporter, boolean debug,
			boolean haveAudio,int resolutionJpeg,String usr_pass)
	{
		m_tk = Toolkit.getDefaultToolkit();
		m_listeners = new Vector();
		//
		// Pull open stream - look for user/password.
		//
		m_stream = strm;
		//String userPass = strm.getUserInfo(); //commented by vandana
		String userPass = usr_pass;
		//
		// Encode if needed...
		//
		m_userpassEncoded = null;
		if (userPass != null && userPass.length() > 0)
		{
			m_userpassEncoded = Base64.encode(userPass.getBytes());
		}
		m_appName = app;
		m_reporter = reporter;
		m_isDefunct = false;
		m_docBase = docBase;
		m_retryCount = retryCount;
		m_retryDelay = retryDelay;
		m_debug = false;
		m_audioLength = 0;
		m_ImageDataLen = 0;
		m_Audio = new byte[16160];
		m_resolutionJpeg = resolutionJpeg;
		m_haveAudio = haveAudio;
	}


	public Image getCurrent()
	{
		synchronized (m_lock) {
			return m_imain;
		}
	}
	public byte[] getAudio()
	{
		synchronized (m_lock)
		{
			return m_Audio;
		}
	}
	public int getAudioLength()
	{
		synchronized(m_lock)
		{
			int length = m_audioLength;
			m_audioLength = 0;
			return length;
		}
	}

	public byte[] getRawImage()
	{
		synchronized (m_lock) {
			return m_rawImage;
		}
	}
	public int getImgBegin()
	{
		synchronized (m_lock) {
			return m_imgBegin;
		}
	}
	public int getImgCurrentIndex()
	{
		synchronized (m_lock) {
			return m_imgCurrentIndex;
		}
	}
	public int getIndex()
	{
		synchronized (m_lock) {
			return m_imgidx;
		}
	}
	public int getResetFlag()
	{
		synchronized (m_lock) {
			return m_ResetFlag;
		}
	}
	public int getResolutionJpeg()
	{
		synchronized (m_lock) {
			return m_resolutionJpeg;
		}
	}
	public int getResetAudioBufferCount()
	{
		synchronized (m_lock) {
			return m_ResetAudioBufferCount;
		}
	}
	public String getType()
	{
		synchronized (m_lock) {
			return m_imageType;
		}
	}

	public URL getStreamURL()
	{
		return m_stream;
	}

	public double getFPS()
	{
		if (m_startTime == 0) {
			return 0.0;
		}
		long currTime = System.currentTimeMillis();
		return (1000.0 * (m_imgidx - IMG_FLUFF_FACTOR)) / (currTime - m_startTime);
	}

	public void addImageChangeListener(ImageChangeListener cl)
	{
		m_listeners.addElement(cl);
	}


	public void removeImageChangeListener(ImageChangeListener cl)
	{
		m_listeners.removeElement(cl);
	}


	
	private void fireImageChange() {
		ImageChangeEvent ce = new ImageChangeEvent(this);
		
			for (Enumeration<ImageChangeListener> e = m_listeners.elements(); e.hasMoreElements();) {
//				System.out.println("e :"+e.toString());
				((ImageChangeListener) e.nextElement()).imageChanged(ce);
			}
			
			
		
		
	}


	public void run()
	{
		//int retries = 5;
		
		
		int test = -1;
		
		while(true)
		{
			StreamSplit ssplit = null;
			try {
				Thread.sleep(500);
				//
				// Loop for a while until we either give up (hit m_retryCount), or
				// get a connection.... Sleep inbetween.
				//
				String connectionError = null;
				String ctype = null;
				Hashtable headers = null;
				int tryIndex = 0;
				int retryCount = m_retryCount;
				int retryDelay = m_retryDelay;
				//
				do {
					//
					// Keep track of how many times we tried.
					//
					tryIndex++;

					if (m_debug)
					{
						System.err.println("// Connection URL = " + m_stream);
					}
					//
					// Better method - access via URL Connection
					//
					URLConnection conn = m_stream.openConnection();
					if (m_docBase != null) 
					{
						conn.setRequestProperty("Referer", m_docBase.toString());
					}
					conn.setRequestProperty("User-Agent", m_appName);
					conn.setRequestProperty("Host", m_stream.getHost());
					if (m_userpassEncoded != null) {
						conn.setRequestProperty("Authorization", "Basic " + m_userpassEncoded);
					}
					m_inputStream = new DataInputStream(new BufferedInputStream(conn.getInputStream()));
					//
					// Read Headers for the main thing...
					//
					headers = StreamSplit.readHeaders(conn);
					//
					// Choose [based on colpile flag] what stresm splitter
					// to use.
					//
					if (m_defaultStreamProcessor) {
						ssplit = new StreamSplit(m_inputStream);
					} 
					else 
					{
						ssplit = new TaiStreamSplit(m_inputStream);
					}

					if (m_debug)
					{
						System.err.println("// Request sent; Main Response headers:");
						for (Enumeration enm = headers.keys();enm.hasMoreElements();)
						{
							String hkey = (String)enm.nextElement();
							System.err.println("//   " + hkey + " = " + headers.get(hkey));
						}
					}

					test = 0xdead0001;
					m_collecting = true;

					connectionError = null;
					ctype = (String) headers.get("content-type");
					if (ctype == null) 
					{
						connectionError = "No main content type";
					} 
					else if (ctype.indexOf("text") != -1) 
					{
						String response = null;
						while ((response = m_inputStream.readLine()) != null) 
						{
							System.out.println(response);
						}
						connectionError = "Failed to connect to server (denied?)";
					}
					test = 0xdead0002;
					
					if (connectionError == null)
					{
						break; 
					} else if (m_isDefunct) 
					{

						return;
					} else 
					{

						if (m_debug)
						{
							System.err.println("// Waiting for " + retryDelay + " ms");
						}
						m_reporter.reportFailure(connectionError);
						sleep(retryDelay);
					}
				} while (tryIndex < retryCount);

				test = 0xdead0003;
				if (connectionError != null)
				{
					return;
				}

				int bidx = ctype.indexOf("boundary=");
				String boundary = StreamSplit.BOUNDARY_MARKER_PREFIX;
				if (bidx != -1) {
					boundary = ctype.substring(bidx + 9);
					ctype = ctype.substring(0, bidx);
					//
					// Remove quotes around boundary string [if present]
					//
					if (boundary.startsWith("\"") && boundary.endsWith("\""))
					{
						boundary = boundary.substring(1, boundary.length()-1);
					}
					if (!boundary.startsWith(StreamSplit.BOUNDARY_MARKER_PREFIX)) {
						boundary = StreamSplit.BOUNDARY_MARKER_PREFIX + boundary;
					}
				}
				
				test = 0xdead0004;

				if (ctype.startsWith("multipart/x-mixed-replace")) {
					if (m_debug)
					{
						System.err.println("// Reading up to boundary");
					}
					ssplit.skipToBoundary(boundary);
				}

				test = 0xdead0005;
				
				
				do {
					if (m_collecting) 
					{

						if (boundary != null) 
						{
							
							headers = ssplit.readHeaders();
							
							test = 0xdead0006;
							if (m_debug) 
							{
								System.err.println("// Chunk Headers recieved:");
								for (Enumeration enm = headers.keys();enm.hasMoreElements();)
								{
									String hkey = (String)enm.nextElement();
									System.err.println("//   " + hkey + " = " + headers.get(hkey));
								}
							}
							
							test = 0xdead0007;
							//
							// Are we at the end of the m_stream?
							//
							if (ssplit.isAtStreamEnd()) 
							{
								break;
							}
							ctype = (String) headers.get("content-type");
							if (ctype == null) {
								throw new Exception("No part content type");
							}
						}
						//
						// Mixed Type -> just skip...
						//
						if (ctype.startsWith("multipart/x-mixed-replace")) 
						{
							//
							// Skip
							//
							bidx = ctype.indexOf("boundary=");
							boundary = ctype.substring(bidx + 9);
							//
							if (m_debug) {
								System.err.println("// Skipping to boundary");
							}
							ssplit.skipToBoundary(boundary);
						} 
						else /* ctype.startsWith("image/jpeg")*/
						{
							test = 0xdead0008;
							ctype = (String) headers.get("content-length");
							int frame_size = Integer.parseInt(ctype);
							
							if (m_debug) {
								System.err.println("// Reading to boundary");
							}
							byte[] data = ssplit.readToBoundary(boundary);
							if (data.length == 0) {
								break;
							}
							
							if ( data.length < frame_size)
							{
								if (m_debug) {
									System.err.println("// data read from stream is smaller than frame_size");
								}
								continue;
							}
							
							
							if (m_imgidx > IMG_FLUFF_FACTOR && m_startTime == 0) {
								m_startTime = System.currentTimeMillis();
							}
							
							test = 0xdead0009;
							m_haveAudio=true;
							if(m_haveAudio == true)
							{
								byte[] img;
								m_ResetFlag = data[0];
								int iLength = Util.byteArrayToInt_MSB(data, 1);
								m_imgCurrentIndex =  Util.byteArrayToInt_MSB(data, 5);
								m_resolutionJpeg = data[9];
								m_ResetAudioBufferCount = data[10];
								
								test = 0xdead000a;
								if(iLength > 0)
								{								
									byte[] adpcm = new byte[iLength];
									System.arraycopy(data,1 + 4 + 4 + 1 + 1 +4, adpcm, 0, iLength);

									byte[] outPCM = ADPCMDecoder.decode(adpcm);

									test = 0xdead000b;
									if (m_Audio.length < outPCM.length)
									{
										System.err.println("expanding m_audio.. to " + outPCM.length );
										m_Audio = new byte[outPCM.length];
									}
									System.arraycopy(outPCM,0, m_Audio, 0, outPCM.length);
									m_audioLength = outPCM.length;
									m_ImageDataLen =data.length - (1 + 4 + 4 + 1 + 1 +4 +iLength); 
									img = new byte[m_ImageDataLen];
									
									test = 0xdead001b;
									System.arraycopy(data,1 + 4 + 4 + 1 + 1 +4 + iLength,img,0,img.length);
									m_count_test = 0;
								}
								else
								{
									m_ImageDataLen = data.length - (1 + 4 + 4 + 1 + 1 +4);
									img = new byte[m_ImageDataLen];
									test = 0xdead000c;
									System.arraycopy(data,1 + 4 + 4 + 1 + 1 +4,img,0,img.length);
									
								}
								m_count_test++;
								updateImage(ctype, img);
							}
							else
							{
								m_resolutionJpeg = data[0];
								byte[] img = new byte[data.length - 1];
								System.arraycopy(data,1,img,0,img.length);
								updateImage(ctype,img);
							}

						}
						
						
					} //if (m_collecting) 
					
					try {
						Thread.sleep(20);
					} 
					catch (InterruptedException ie) {
					}
				} while (!m_isDefunct);
			} catch (Exception e) {
				if (!m_collecting)
				{
					m_reporter.reportFailure(e.toString());
				} else if (!m_isDefunct) {
					m_reporter.reportError(e);
				}

				if (e.toString().contains("Server returned HTTP response code:"))
				{
					unhook();
					return;
				}
				try {
					if (m_inputStream != null)
					{
						m_inputStream.close();
					}
					m_inputStream = null;
				} catch (Exception ex) {
				}
				m_collecting = false;

			} finally {
			}
		}
		//
		// At this point, the m_stream m_inputStream done
		// [could dispplay a that's all folks - leaving it as it m_inputStream
		//  will leave the last frame up]
		//
	}

	private void updateImage(String ctype, byte[] img)
	{
		try{
			m_imageType = ctype;
			m_imain = m_tk.createImage(img);
			m_rawImage = img;
			m_imgidx++;
			fireImageChange();
		}catch(Exception ex)
		{
			
			System.out.println("error :"+ex.fillInStackTrace());
		}

	}

	public void finalize()
	{
		unhook();
	}


	public void unhook()
	{
		m_collecting = false;
		m_isDefunct = true;
		try {
			if (m_inputStream != null)
			{
				m_inputStream.close();
			}
			m_inputStream = null;
		} catch (Exception e) {
		}
	}
}