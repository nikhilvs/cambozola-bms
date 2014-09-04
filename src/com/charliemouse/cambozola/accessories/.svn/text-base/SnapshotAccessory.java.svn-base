package com.charliemouse.cambozola.accessories;

import java.awt.Point;

import com.charliemouse.cambozola.Accessory;
import com.charliemouse.cambozola.ViewerAttributeInterface;

public class SnapshotAccessory extends Accessory
{
	public SnapshotAccessory()
	{
		super();
	}
	public String getName()
    {
        return "Snapshot";
    }

    public String getIconLocation()
    {
    		return "snapshot.png";
    }

    public String getDescription()
    {
    		return "Snapshot";
    }

    public void actionPerformed(Point p)
    {
       ViewerAttributeInterface vfi = getViewerAttributes();
        vfi.ControlSnapshot();
        //vfi.getViewArea().zoomIn();
    	
    }
}
