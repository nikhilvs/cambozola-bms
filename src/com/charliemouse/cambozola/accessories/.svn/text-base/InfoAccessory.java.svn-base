/**
 ** com/charliemouse/cambozola/accessories/InfoAccessory.java
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

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

import com.charliemouse.cambozola.Accessory;
import com.charliemouse.cambozola.shared.AppID;

public class InfoAccessory extends Accessory
{
    protected InfoFrame m_infoFrame = null;
    protected Point m_frameLoc = new Point(20,20);

	public InfoAccessory()
	{
		super();
	}

    public String getName()
    {
        return "Information";
    }

    public String getDescription()
    {
        return "Display information on the stream";
    }

    public String getIconLocation()
    {
        return "info.gif";
    }

    public void actionPerformed(Point p)
    {
        if (m_infoFrame == null) {
            m_infoFrame = new InfoFrame();
        } else {
            m_infoFrame.close();
        }
    }



    class InfoFrame extends Frame implements ActionListener, Runnable
    {
        TextField fpsValue;

        public InfoFrame()
        {
            super();
            AppID props = AppID.getAppID();
            setTitle(props.getAppName());
	        setLayout(new BorderLayout());

	        Panel appPanel = new Panel();
	        appPanel.setLayout(new GridLayout(3,1));
	        appPanel.add(new Label(props.getAppName() + " " + props.getFullVersion(), Label.CENTER));
	        appPanel.add(new Label(props.getCopyright(), Label.CENTER));
	        appPanel.add(new Label(props.getLocURL(), Label.CENTER));
	        add(appPanel, BorderLayout.NORTH);


            Panel mp = new Panel();
            mp.setLayout(new GridLayout(2, 1));

            Panel url_panel = new Panel();
            url_panel.setLayout(new FlowLayout());
            url_panel.add(new Label("URL:"));
            TextField urlField = new TextField("" + getViewerAttributes().getStream().getStreamURL(), 30);
            urlField.setEditable(false);
            url_panel.add(urlField);
	        mp.add(url_panel);

            Panel fps_panel = new Panel();
            fps_panel.add(new Label("FPS:"));
            fps_panel.setLayout(new FlowLayout());
            fpsValue = new TextField(30);
            fpsValue.setEditable(false);
            updateFPS();
            fps_panel.add(fpsValue);
            mp.add(fps_panel);
            add(mp, BorderLayout.CENTER);
            Button closeButt = new Button("Close");
            closeButt.addActionListener(this);
            Panel buttPanel = new Panel();
            buttPanel.setLayout(new FlowLayout());
            buttPanel.add(closeButt);
            add(buttPanel, BorderLayout.SOUTH);
            pack();
            validate();
            if (m_frameLoc != null) {
                setLocation(m_frameLoc);
            }
            setVisible(true);
            addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent we)
                {
                    close();
                }
            });
            new Thread(this).start();
        }


        public void run()
        {
            do {
                updateFPS();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ie) {
                }
            } while (isVisible());
        }


        private void updateFPS()
        {
            String fps = null;
            if (getViewerAttributes().getStream() == null) {
                fps = "No image stream yet.";
            } else {
                fps = NumberFormat.getInstance().format(getViewerAttributes().getStream().getFPS());
            }
            fpsValue.setText(fps);
        }


        public void actionPerformed(ActionEvent ae)
        {
            close();
        }


        public void close()
        {
            m_frameLoc = getLocation();
            setVisible(false);
            m_infoFrame = null;
            this.dispose();
        }
    }
}
