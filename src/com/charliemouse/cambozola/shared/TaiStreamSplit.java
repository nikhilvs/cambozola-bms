package com.charliemouse.cambozola.shared;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Placeholder for Title Block
 */
public class TaiStreamSplit extends StreamSplit
{
    public TaiStreamSplit(DataInputStream dis)
    {
        super(dis);
        System.err.println("// Note: Using TAI Stream Splitter");
    }

    public Hashtable readHeaders() throws IOException
    {
        Hashtable ht = new Hashtable();
        String ref = null;
        while ((ref = m_dis.readLine()) != null)
        {
            System.err.println(ref);
        }
//        addPropValue(contentType, ht);
//        addPropValue(contentLength, ht);
//        addPropValue(contentAuth, ht);

        return ht;
    }

    public byte[] readToBoundary(String boundary) throws IOException
    {
        byte[] rawdata = super.readToBoundary(boundary);
        int junkPoint = 0;
        for (int i = 0; i < Math.min(10, rawdata.length);i++) {
            if ((rawdata[i] & 0xff) == 0xff && (rawdata[i+1] & 0xff) == 0xd8)
            {
                junkPoint = i;
                break;
            }
        }
        if (junkPoint == 0) {
            return rawdata;
        }
        byte[] cleaned = new byte[rawdata.length-junkPoint];
        System.arraycopy(rawdata, junkPoint, cleaned, 0, rawdata.length-junkPoint);
        return cleaned;
    }
}
