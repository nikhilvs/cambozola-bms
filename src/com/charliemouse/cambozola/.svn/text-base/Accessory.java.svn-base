/**
 ** com/charliemouse/cambozola/Accessory.java
 **  Copyright (C) Andy Wilcock, 2003.
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

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;


public abstract class Accessory
{
    public static final int ICON_SIZE   = 24;
    public static final int ICON_INDENT = 3;
    public static final int BUTTON_SIZE = ICON_SIZE + (ICON_INDENT * 2);

    public ViewerAttributeInterface m_viewerAttribs = null;

    private String m_key = "";

    public void setViewerAttributes(ViewerAttributeInterface v)
    {
        m_viewerAttribs = v;
    }

    public ViewerAttributeInterface getViewerAttributes()
    {
        return m_viewerAttribs;
    }

    public Accessory()
    {
        String fullClazz = this.getClass().getName();
        String clazzName = fullClazz.substring(fullClazz.lastIndexOf(".")+1);
        if (clazzName.endsWith("Accessory")) {
            m_key = clazzName.substring(0, clazzName.length() - 9);
        }
    }

    public String getKey()
    {
        return m_key;
    }

    public String getName()
    {
        return "NoName";
    }

    public String getDescription()
    {
        return null;
    }
    public void controlFlag()
    {
    	
    }
    /**
     ** This is only checked at creation time
     ** @return True if the accessory is enabled
     **/
    public boolean isEnabled()
    {
        return true;
    }

    public String getIconLocation()
    {
        return null;
    }

    public Image getIconImage()
    {
        String loc = getIconLocation();
        if (loc == null) return null;

        try {
            InputStream is = getClass().getResourceAsStream(loc);
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);
            byte[] byBuf = new byte[dis.available()];
            dis.readFully(byBuf);
            Image img = Toolkit.getDefaultToolkit().createImage(byBuf);
            if (img != null) {
                MediaTracker mt = new MediaTracker(new Panel());
                mt.addImage(img, 0);
                try {
                    mt.waitForID(0);
                } catch (InterruptedException ie) {
                }
                return img;
            }
        } catch (IOException ie)
        {
            ie.printStackTrace();
        }
        return null;
    }

    public void terminate()
    {
    }

    public void actionPerformed(Point p)
    {
        System.err.println("<Default handler> " + getName() + " accessory called");
    }
}
