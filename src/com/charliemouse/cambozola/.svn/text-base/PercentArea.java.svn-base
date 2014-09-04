/**
 ** com/charliemouse/cambozola/PercentArea.java
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

import java.awt.Rectangle;

public
class PercentArea
{
    public static final int LEFT  = -1;
    public static final int RIGHT =  1;
    public static final int UP    = -1;
    public static final int DOWN  =  1;

    double p1x, p1y;
    double p2x,p2y;
    double centx,centy;

    public PercentArea()
    {
        reset();
    }

    public boolean setBounds(double ax1, double ay1, double ax2, double ay2)
    {
        boolean mod = true;
        p1x = Math.max(Math.min(ax1,ax2), 0.0);
        p2x = Math.min(Math.max(ax1,ax2), 100.0);
        p1y = Math.max(Math.min(ay1,ay2), 0.0);
        p2y = Math.min(Math.max(ay1,ay2), 100.0);
        centx = (p1x+p2x)/2.0;
        centy = (p1y+p2y)/2.0;
        return mod;
    }

    public boolean setBoundsAspect(double cx, double cy, double w, double h)
    {
        boolean mod = true;
        double mw = Math.min(w, 100.0)/2.0;
        double mh = Math.min(h, 100.0)/2.0;

        double cxd = cx;
        if (cx <= mw) {
            cxd = mw;
        } else if ((cx + mw) > 100.0) {
            cxd = 100.0-mw;
        }
        double cyd = cy;
        if (cy <= mh) {
            cyd = mh;
        } else if ((cy + mh) > 100.0) {
            cyd = 100.0-mh;
        }

        p1x = cxd - mw;
        p1y = cyd - mh;
        p2x = cxd + mw;
        p2y = cyd + mh;
        centx = cxd;
        centy = cyd;
        return mod;
    }

    public void reset()
    {
        setBounds(0.0,0.0,100.0,100.0);
    }

    public double getWidth()
    {
        return (p2x-p1x);
    }

    public double getHeight()
    {
        return (p2y-p1y);
    }

    public Rectangle getArea(int width, int height)
    {
        double x = (p1x * width)/100.0;
        double y = (p1y * height)/100.0;

        double w = (getWidth() * width)/100.0;
        double h = (getHeight() * height)/100.0;
        return new Rectangle((int)x,(int)y,(int)w,(int)h);
    }

    public boolean zoomIn()
    {
        double w = getWidth()/4.0;
        double h = getHeight()/4.0;
        if (w < 1 || h < 1) return false; // too far in.
        return setBounds(centx-w,centy-h, centx+w, centy+h);
    }

    public boolean zoomOut()
    {
        double w = getWidth()*2.0;
        double h = getHeight()*2.0;
        return setBoundsAspect(centx,centy,w,h);
    }

    public boolean panHorizontal(int dir)
    {
        double jump = Math.max(getWidth()/5.0,1.0) * dir;
        return setBoundsAspect(centx+jump,centy,getWidth(),getHeight());
    }

    public boolean panVertical(int dir)
    {
        double jump = Math.max(getHeight()/5.0,1.0) * dir;
        return setBoundsAspect(centx,centy+jump,getWidth(),getHeight());
    }


    public String toString()
    {
        return "[" + p1x + "," + p1y + "] ==> [" + p2x + "," + p2y + "] (w=" + getWidth() + ", h=" + getHeight() + ")";
    }
}
