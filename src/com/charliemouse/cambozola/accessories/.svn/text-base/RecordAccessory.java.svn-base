package com.charliemouse.cambozola.accessories;
import java.awt.Point;
import com.charliemouse.cambozola.Accessory;
import com.charliemouse.cambozola.ViewerAttributeInterface;

public class RecordAccessory extends Accessory{

	private boolean bRecordFlag = false;
	public RecordAccessory()
	{
		super();
	}
	public String getName()
    {
        return "Record";
    }

    public String getIconLocation()
    {
    	if(bRecordFlag == false)
    		return "record.gif";
    	else
    		return "norecord.gif";
    }

    public String getDescription()
    {
    	if(bRecordFlag == false)
    		return "Record Video";
    	else
    		return "Stop record video";
    }
    public void controlFlag()
    {
    	bRecordFlag = false;
    }
    public void actionPerformed(Point p)
    {
    	if(bRecordFlag == true)
    		bRecordFlag = false;
    	else
    		bRecordFlag = true;
        ViewerAttributeInterface vfi = getViewerAttributes();
        if(vfi.ControlRecord() == false)
        	bRecordFlag = (bRecordFlag == true)? false:true;
    	
    }
}
