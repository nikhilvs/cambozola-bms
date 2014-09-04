/**
 ** com/charliemouse/cambozola/shared/StreamSplit.java
 **  Copyright (C) Andy Wilcock, 2001.
 **  Available from http://www.charliemouse.com
 **
 ** This file m_inputStream part of the CamViewer package (c) Andy Wilcock, 2001.
 ** Available from http://www.charliemouse.com
 **
 **  Cambozola m_inputStream free software; you can redistribute it and/or modify
 **  it under the terms of the GNU General Public License as published by
 **  the Free Software Foundation; either version 2 of the License, or
 **  (at your option) any later version.
 **
 **  Cambozola m_inputStream distributed in the hope that it will be useful,
 **  but WITHOUT ANY WARRANTY; without even the implied warranty of
 **  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 **  GNU General Public License for more details.
 **
 **  You should have received a copy of the GNU General Public License
 **  along with Cambozola; if not, write to the Free Software
 **  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 **/
package com.charliemouse.cambozola.shared;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.Hashtable;
import java.net.URLConnection;

public class StreamSplit {
    public static final String BOUNDARY_MARKER_PREFIX  = "--";
    public static final String BOUNDARY_MARKER_TERM    = BOUNDARY_MARKER_PREFIX;

	protected DataInputStream m_dis;
	private boolean m_streamEnd;


	public StreamSplit(DataInputStream dis)
	{
		m_dis = dis;
		m_streamEnd = false;
	}


	public Hashtable readHeaders() throws IOException
	{
		Hashtable ht = new Hashtable();
		String response = null;
		boolean satisfied = false;

		do {
			response = m_dis.readLine();
			if (response == null) {
				m_streamEnd = true;
				break;
			} else if (response.equals("")) {
                if (satisfied) {
				    break;
                } else {
                    // Carry on...
                }
			} else {
                satisfied = true;
            }
            addPropValue(response, ht);
        } while (true);
		return ht;
	}

    protected static void addPropValue(String response, Hashtable ht)
    {
        int idx = response.indexOf(":");
        if (idx == -1) {
            return;
        }
        String tag = response.substring(0, idx);
        String val = response.substring(idx + 1).trim();
        ht.put(tag.toLowerCase(), val);
    }


    public static Hashtable readHeaders(URLConnection conn)
    {
        Hashtable ht = new Hashtable();
        boolean foundAny = false;
        int i = 0;
        do {
            String key = conn.getHeaderFieldKey(i);
            if (key == null) {
                if (i == 0) {
                    i++;
                    continue;
                } else {
                    break;
                }
            }
            String val = conn.getHeaderField(i);
            ht.put(key.toLowerCase(), val);
            i++;
        } while (true);
        return ht;
    }


	public void skipToBoundary(String boundary) throws IOException
	{
		readToBoundary(boundary);
	}


	public byte[] readToBoundary(String boundary) throws IOException
	{
		ResizableByteArrayOutputStream baos = new ResizableByteArrayOutputStream();
		StringBuffer lastLine = new StringBuffer();
		int lineidx = 0;
		int chidx = 0;
		byte ch;
		do {
			try {
				ch = m_dis.readByte();
			} catch (EOFException e) {
				m_streamEnd = true;
				break;
			}
			if (ch == '\n' || ch == '\r') {
				//
				// End of line... Note, this will now look for the boundary
                // within the line - more flexible as it can habdle
                // arfle--boundary\n  as well as
                // arfle\n--boundary\n
				//
				String lls = lastLine.toString();
                int idx = lls.indexOf(BOUNDARY_MARKER_PREFIX);
                if (idx != -1) {
                    lls = lastLine.substring(idx);
                    if (lls.startsWith(boundary)) {
                        //
                        // Boundary found - check for termination
                        //
                        String btest = lls.substring(boundary.length());
                        if (btest.equals(BOUNDARY_MARKER_TERM)) {
                            m_streamEnd = true;
                        }
                        chidx = lineidx+idx;
                        break;
                    }
				}
				lastLine = new StringBuffer();
				lineidx = chidx + 1;
			} else {
				lastLine.append((char) ch);
			}
			chidx++;
			baos.write(ch);
		} while (true);
		//
		baos.close();
		baos.resize(chidx);
		return baos.toByteArray();
	}


	public boolean isAtStreamEnd()
	{
		return m_streamEnd;
	}
}


class ResizableByteArrayOutputStream extends ByteArrayOutputStream {
	public ResizableByteArrayOutputStream()
	{
		super();
	}


	public void resize(int size)
	{
		count = size;
	}
}
