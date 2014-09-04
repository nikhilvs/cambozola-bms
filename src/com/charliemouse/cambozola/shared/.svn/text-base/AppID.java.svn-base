/**
 ** com/charliemouse/cambozola/shared/AppID.java
 **  Copyright (C) Andy Wilcock, 2001.
 **  Available from http://www.charliemouse.com
 **
 **  Cambozola m_inputStream free software; you can redistribute it and/or modify
 **  it under the terms of the GNU General Public License as published by
 **  the Free Software Foundation; either version 2 of the License, or
 **  (at your option) any later version.
 **
 **  Cambozola m_inputStream distributed in the hope that it will be useful,
 **  but WITHOUT ANY WARRANTY; without even the implied warranty of
 **  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 **  GNU General Public License for more details.
 **
 **  You should have received a copy of the GNU General Public License
 **  along with Cambozola; if not, write to the Free Software
 **  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 **/
package com.charliemouse.cambozola.shared;

import java.io.InputStream;
import java.util.Properties;

public class AppID {
	private static AppID m_appID = null;
	private Properties m_props = null;


	private AppID()
	{
		m_props = new Properties();
		try {
			InputStream istr = this.getClass().getResourceAsStream("/application.properties");
			m_props.load(istr);
			istr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static synchronized AppID getAppID()
	{
		if (m_appID == null) {
			m_appID = new AppID();
		}
		return m_appID;
	}


	public String getProperty(String key)
	{
		return m_props.getProperty(key, "<" + key + ">");
	}

	public String getAppNameVersion()
	{
		return getAppName() + " V" + getFullVersion();
	}


	public String getAppName()
	{
		return getProperty("app.name");
	}

    public String getBuildDate()
    {
        return getProperty("build.date");
    }

    public String getBuildTick()
    {
        return getProperty("build.tick");
    }

	public String getVersion()
	{
		return getProperty("app.version");
	}

    public String getFullVersion()
    {
        return getVersion() + "-" + getBuildTick();
    }

	public String getCopyright()
	{
		return getProperty("app.copyright");
	}

	public String getLocURL()
	{
		return getProperty("app.locURL");
	}

    public String getHelpURL()
    {
        return getProperty("app.helpURL");
    }

}
