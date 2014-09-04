/**
 ** com/charliemouse/cambozola/ViewerAttributeInterface.java
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

import java.awt.Dimension;
import java.net.URL;
import java.util.Vector;

import com.charliemouse.cambozola.shared.CamStream;

public interface ViewerAttributeInterface
{
    public Dimension getSize();
    public PercentArea getViewArea();
    public CamStream getStream();
    public boolean ControlRecord();
    public void ControlSnapshot();
    public void SetMaxRecordSize();
    public void ChooseStorageFolder();
    public boolean isStandalone();
    public Vector getAccessories();
	public Vector getAlternateURLs();
	public void setAlternateURLs(Vector v);
	public void setCurrentURL(URL u);

    public void displayURL(URL url, String target);
    public void repaint();
}
