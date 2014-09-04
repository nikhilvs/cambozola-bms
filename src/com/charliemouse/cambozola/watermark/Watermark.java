/**
 ** com/charliemouse/cambozola/Watermark.java
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
import java.awt.Point;
import java.awt.Rectangle;
import java.net.MalformedURLException;
import java.net.URL;

public class Watermark
{
	public static final int H_LEFT   = 0;
	public static final int H_CENTER = 1;
	public static final int H_RIGHT  = 2;

	public static final int V_TOP    = 0;
	public static final int V_MIDDLE = 1;
	public static final int V_BOTTOM = 2;

	public static final boolean INVISIBLE       = true;
	public static final boolean VISIBLE         = true;

	private Image m_image;
	private int m_imgWidth;
	private int m_imgHeight;
	private int m_horiz;
	private int m_vert;
	private int m_indentx;
	private int m_indenty;
	private boolean m_visible;
	private URL m_url;

	private Rectangle m_rect;

	public Watermark(Image img, int horiz, int vert, int indentx, int indenty)
	{
		this(img, horiz, vert, indentx, indenty, null);
	}

	public Watermark(Image img, int horiz, int vert, int indentx, int indenty, String url)
	{
		this(img, horiz, vert, indentx, indenty, url, true);
	}

	public Watermark(Image img, int horiz, int vert, int indentx, int indenty, String url, boolean visible)
	{
		m_image = img;
		m_imgWidth = m_image.getWidth(null);
		m_imgHeight = m_image.getHeight(null);
		//
		// Initially, don't know where it is.
		//
		m_rect = new Rectangle(0,0,0,0);
		//
		m_horiz = horiz;
		m_vert  = vert;
		m_indentx = indentx;
		m_indenty = indenty;
		m_visible = visible;
		if (url != null && !url.equals(""))
		{
			try {
				m_url = new URL(url);
			} catch (MalformedURLException mfe) {
				System.err.println("Unable to process watermark link URL - '" + url + "' - not clickable");
			}
		}
	}

	public void paint(Graphics g)
	{
		if (m_visible)
		{
			g.drawImage(m_image, m_rect.x, m_rect.y, null);
		}
	}

	public boolean hitsPoint(Point p)
	{
		return m_rect.contains(p);
	}

	public boolean isClickable()
	{
		return (m_visible && (m_url != null));
	}

	public boolean isVisible()
	{
		return m_visible;
	}

	public URL getURL()
	{
		return m_url;
	}

	public void recalculateLocation(Dimension d)
	{
		int x = 0;
		int y = 0;
		switch(m_horiz)
		{
			case H_LEFT:    x = m_indentx; break;
			case H_CENTER:  x = (d.width - m_imgWidth)/2 ; break;
			case H_RIGHT:   x = d.width - m_indentx - m_imgWidth; break;
			default:        throw new RuntimeException("Illegal Horizontal Anchor position - " + m_horiz);
		}
		switch(m_vert)
		{
			case V_TOP:     y = m_indenty; break;
			case V_MIDDLE:  y = (d.height- m_imgHeight)/2 ; break;
			case V_BOTTOM:  y = d.height - m_indenty - m_imgHeight; break;
			default:        throw new RuntimeException("Illegal Vertical Anchor position - " + m_horiz);
		}
		//
		// Set up the rectangle.
		//
		m_rect.setBounds(x,y, m_imgWidth, m_imgHeight);
	}
}
