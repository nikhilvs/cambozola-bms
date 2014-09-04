package com.charliemouse.cambozola.accessories;

import java.awt.Point;

import com.charliemouse.cambozola.Accessory;
import com.charliemouse.cambozola.ViewerAttributeInterface;

public class StorageFolderAccessory extends Accessory{
	public StorageFolderAccessory()
	{
		super();
	}
	public String getName()
    {
        return "StorageFolder";
    }

    public String getIconLocation()
    {
    		return "StorageFolder.png";
    }

    public String getDescription()
    {
    		return "Set Storage Folder";
    }
    
    public void actionPerformed(Point p)
    {
       ViewerAttributeInterface vfi = getViewerAttributes();
        vfi.ChooseStorageFolder();
        //vfi.getViewArea().zoomIn();
    	
    }
}
