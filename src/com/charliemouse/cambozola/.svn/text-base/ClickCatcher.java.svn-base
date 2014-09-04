package com.charliemouse.cambozola;

import java.awt.Point;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: andyw
 * Date: Sep 7, 2008
 * Time: 11:32:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClickCatcher
{
    public String m_singleClick;
    public String m_doubleClick;

    public ClickCatcher()
    {
    }

    public void setSingleClick(String single)
    {
        if (single == null || single.length() == 0)
        {
            single = null;
        }
        m_singleClick = single;
    }

    public void setDoubleClick(String dbl)
    {
        if (dbl == null || dbl.length() == 0)
        {
            dbl = null;
        }
        m_doubleClick = dbl;
    }

    public void mouseClicked(URL base, Point p)
    {
        if (m_singleClick == null)
        {
            return;
        }
        fireRequestTo(base, m_singleClick, p);
    }

    public void mouseDoubleClicked(URL base, Point p)
    {
        if (m_doubleClick == null)
        {
            return;
        }
        fireRequestTo(base, m_doubleClick, p);
    }

    void fireRequestTo(URL base, String mainString, Point p)
    {
        String xr = replaceString(mainString, "$x", "" + p.x);
        String req = replaceString(xr, "$y", "" + p.y);
        //
        // Make HTTP Request to 'req'
        //
        try {
            URL target = new URL(base, req);
            System.err.println("Click request to: " + target);
            URLConnection uc = target.openConnection();
        } catch (MalformedURLException e) {
            System.err.println("Failed to make URL for click request to " + req + ": " + e);
        } catch (IOException e) {
            System.err.println("Failed to make HTTP click request to " + req + ": " + e);
        }
    }

    String replaceString(String source, String match, String replaceWith)
    {
        StringBuffer result = new StringBuffer();
        int startIdx = 0;
        int idxOld = 0;
        while ((idxOld = source.indexOf(match, startIdx)) >= 0) {
            result.append(source.substring(startIdx, idxOld));
            result.append(replaceWith);
            startIdx = idxOld + match.length();
        }
        // Add the remaining stuff.
        result.append(source.substring(startIdx));
        return result.toString();
    }
}
