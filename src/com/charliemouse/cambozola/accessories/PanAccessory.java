/**
 ** com/charliemouse/cambozola/accessories/PanAccessory.java
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
package com.charliemouse.cambozola.accessories;

import java.awt.Point;

import com.charliemouse.cambozola.Accessory;
import com.charliemouse.cambozola.PercentArea;
import com.charliemouse.cambozola.ViewerAttributeInterface;

public class PanAccessory extends Accessory
{
	public PanAccessory()
	{
		super();
	}
    public String getName()
    {
        return "Pan View";
    }

    public String getIconLocation()
    {
        return "pan.gif";
    }

    public String getDescription()
    {
        return "Pan around the image (must be zoomed in)";
    }

    public void actionPerformed(Point p)
    {
        ViewerAttributeInterface vfi = getViewerAttributes();
        int invx = Accessory.BUTTON_SIZE - p.x;
        int center = Accessory.BUTTON_SIZE/2;
        if (Math.abs(center - p.x) < 3 && Math.abs(center - p.y) < 3)
        {
            return; // in the middle - too close to call.
        }
        boolean ne_line = (p.x > p.y);
        boolean se_line = (invx > p.y);

        if (ne_line) {
            if (se_line) {
                vfi.getViewArea().panVertical(PercentArea.UP);
            } else {
                vfi.getViewArea().panHorizontal(PercentArea.RIGHT);
            }
        } else {
            if (se_line) {
                vfi.getViewArea().panHorizontal(PercentArea.LEFT);
            } else {
                vfi.getViewArea().panVertical(PercentArea.DOWN);
            }
        }
        vfi.repaint();
    }
}
