/**
 ** com/charliemouse/cambozola/WatermarkCollection.java
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
package com.charliemouse.cambozola.watermark;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.ImageProducer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.io.IOException;


public class WatermarkCollection
{
	public static final String NO_URL      = null;
	public static final int DEFAULT_INDENT = 5;

	private Vector m_watermarks;

	public WatermarkCollection()
	{
        m_watermarks = new Vector();
	}

    public void populate(String wmstr, URL docBase)
    {
        //
        // NOTE: Currently just takes the URL and the location. DOES NOT
        // do the hyperlink.
        //
        Toolkit dt = Toolkit.getDefaultToolkit();
        MediaTracker mt = new MediaTracker(new Panel());
        //
        int wmid = 0;
        if (wmstr.length() == 0)
        {
            return;
        }
        StringTokenizer wmst = new StringTokenizer(wmstr, "| ");
        try {
            do {
                String imgurl = wmst.nextToken();
                String corner = "BottomRight";
                if (wmst.hasMoreTokens()) {
                    corner = wmst.nextToken();
                }
                String clickTarget = null;
                if (wmst.hasMoreTokens()) {
                    clickTarget = wmst.nextToken();
                }
                corner = corner.toLowerCase();
                //
                // Work out what's what.
                //
                int horiz = Watermark.H_CENTER;
                int vert  = Watermark.V_MIDDLE;
                //
                if (corner.indexOf("top") != -1 || corner.indexOf("north") != -1)
                {
                    vert = Watermark.V_TOP;
                } else if (corner.indexOf("bottom") != -1 || corner.indexOf("south") != -1)
                {
                    vert = Watermark.V_BOTTOM;
                }
                if (corner.indexOf("left") != -1 || corner.indexOf("west") != -1)
                {
                    horiz = Watermark.H_LEFT;
                } else if (corner.indexOf("right") != -1 || corner.indexOf("east") != -1)
                {
                    horiz = Watermark.H_RIGHT;
                }
                try {
                    URL wmImgURL = new URL(docBase, imgurl);
                    //
                    // Load [should do in parallel...]
                    //
                    Image img = dt.createImage((ImageProducer)wmImgURL.getContent());
                    mt.addImage(img, wmid);
                    try {
                        mt.waitForID(wmid);
                        int stat = mt.statusID(wmid, true);
                        if (stat != MediaTracker.COMPLETE) {
                            System.err.println("Not all watermarks could be loaded - status=" + stat);
                        }
                    } catch (InterruptedException ie) { }
                    wmid++;
                    //
                    // Create the watermark itself...
                    //
                    m_watermarks.addElement(new Watermark(img,
                                                   horiz, vert,
                                                   DEFAULT_INDENT, DEFAULT_INDENT, clickTarget));
                } catch (MalformedURLException mfe) {
                    System.err.println("Unable to access watermark URL - '" + imgurl + "' : " + mfe);
                } catch (IOException ie) {
                    System.err.println("Unable to access image data for '" + imgurl + "' : " + ie);
                }
            } while (wmst.hasMoreTokens());
        } catch (NoSuchElementException nsee) {
            System.err.println("Invalid watermark string '" + wmstr + "'");
        }
    }


	public Watermark isOverClickableWatermark(Point p)
	{
		Watermark wm = getWatermarkAtPoint(p);
		if (wm != null && wm.isClickable())
		{
			return wm;
		}
		return null;
	}

	public Watermark getWatermarkAtPoint(Point p)
	{
		for (Enumeration itr = m_watermarks.elements();itr.hasMoreElements();)
		{
			Watermark wm = (Watermark)itr.nextElement();
			if (wm.hitsPoint(p) && wm.isVisible())
			{
				return wm;
			}
		}
		return null;
	}

	public void recalculateLocations(Dimension d)
	{
		for (Enumeration itr = m_watermarks.elements();itr.hasMoreElements();)
		{
			Watermark wm = (Watermark)itr.nextElement();
			wm.recalculateLocation(d);
		}
	}

	public void paint(Graphics g)
	{
		for (Enumeration itr = m_watermarks.elements();itr.hasMoreElements();)
		{
			Watermark wm = (Watermark)itr.nextElement();
			wm.paint(g);
		}
	}
}