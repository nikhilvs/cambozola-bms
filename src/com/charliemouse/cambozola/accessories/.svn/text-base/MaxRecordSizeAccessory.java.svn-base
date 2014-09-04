package com.charliemouse.cambozola.accessories;

import java.awt.Point;

import com.charliemouse.cambozola.Accessory;
import com.charliemouse.cambozola.ViewerAttributeInterface;

public class MaxRecordSizeAccessory extends Accessory{
	public MaxRecordSizeAccessory()
	{
		super();
	}
	public String getName()
    {
        return "MaxRecordSize";
    }

    public String getIconLocation()
    {
    		return "MaxRecordSize.png";
    }

    public String getDescription()
    {
    		return "Set Max Record Size";
    }
    
    public void actionPerformed(Point p)
    {
       ViewerAttributeInterface vfi = getViewerAttributes();
        vfi.SetMaxRecordSize();
        //vfi.getViewArea().zoomIn();
    	
    }
}
