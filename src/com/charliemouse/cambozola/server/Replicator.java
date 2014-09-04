/**
 ** com/charliemouse/cambozola/Replicator.java
 **  Copyright (C) Andy Wilcock, 2001.
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
package com.charliemouse.cambozola.server;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.charliemouse.cambozola.shared.AppID;
import com.charliemouse.cambozola.shared.CamStream;
import com.charliemouse.cambozola.shared.ExceptionReporter;
import com.charliemouse.cambozola.shared.ImageChangeEvent;
import com.charliemouse.cambozola.shared.ImageChangeListener;


public class Replicator {
	public final static int DEFAULT_PORT = 3030;
	protected URL m_src;
	protected String m_name;
	protected int m_port;
	protected ServerSocket m_listen_socket;


	public static void main(String args[]) throws IOException
	{
		if (args.length == 0) {
			System.err.println("Test code - for replicating a stream");
			System.err.println("Usage: <replication source> [<port>]");
			System.exit(0);
		}
		URL source = new URL(args[0]);
		int port = DEFAULT_PORT;
		if (args.length > 1) {
			port = Integer.valueOf(args[1]).intValue();
		}
		System.out.println("Replicating " + source);
		new Replicator(source, AppID.getAppID().getAppName(), port);
	}


	public Replicator(URL source, String name, int port)
	{
		m_src = source;
		m_name = name;
		m_port = port;
		try {
			m_listen_socket = new ServerSocket(port);
			System.out.println(name + " replicator listening on port " + port);
			while (true) {
				Socket client_socket = m_listen_socket.accept();
				ReplicatingConnection.addConnection(m_src, m_name, client_socket);
			}
		} catch (Exception e) {
			System.err.println("Exception occurred: " + e);
		}
	}
}


class ReplicatingConnection implements ImageChangeListener {
	private static final String BOUNDARY = "arflebarfle";

	private static Hashtable m_in = new Hashtable();
	private static Hashtable m_out = new Hashtable();
	private static Vector m_clients = new Vector();
	private static CamStream imgStream = null;


	public static void addConnection(URL source, String appname, Socket client)
	{
		synchronized (m_clients) {
			boolean toStart = false;
			if (imgStream == null) {
                ExceptionReporter reporter = new ExceptionReporter()
                {
                    public void reportError(Throwable t)
                    {
	                    reportNote("Error:" + t.toString());
	                }
	                public void reportFailure(String s)
	                {
		                reportNote("Failure:" + s);
		            }
	                public void reportNote(String s)
	                {
		                System.err.println(s);
		            }
                };
				imgStream = new CamStream(source, appname, null, 1, 1000, reporter, false,false,0,"");
				imgStream.addImageChangeListener(new ReplicatingConnection());
				toStart = true;
			}
			DataInputStream in;
			DataOutputStream out;
			try {
				in = new DataInputStream(client.getInputStream());
				out = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
			} catch (IOException ie) {
				System.err.println("Unable to start new connection: " + ie);
				try {
					client.close();
				} catch (IOException ie2) {
				}
				return;
			}
			//
			// Write Header to client.
			//
			try {
				out.writeBytes("HTTP/1.0 200 OK\r\n");
				out.writeBytes("Server: " + AppID.getAppID().getAppName() + " Replicator\r\n");
				out.writeBytes("Content-Type: multipart/x-mixed-replace;boundary=" + BOUNDARY + "\r\n");
				out.writeBytes("\r\n");
				out.writeBytes("--" + BOUNDARY + "\n");
			} catch (IOException ie) {
				return;
			}
			//
			// All OK - add to hash..
			//
			System.out.println("New connection from :" + client);
			m_clients.addElement(client);
			m_in.put(client, in);
			m_out.put(client, out);
			if (toStart) {
				imgStream.start();
			}
		}
	}


	private static void disconnect(Socket sock)
	{
		//
		// Time to stop...
		//
		try {
			DataOutputStream out = (DataOutputStream) m_out.get(sock);
			DataInputStream in = (DataInputStream) m_in.get(sock);
			in.close();
			out.close();
			sock.close();
			m_in.remove(sock);
			m_out.remove(sock);
			m_clients.removeElement(sock);
			if (m_clients.size() == 0) {
				System.err.println("Removing connection");
				imgStream.unhook();
				imgStream = null;
			}
		} catch (IOException ie2) {
		}
	}


	public void imageChanged(ImageChangeEvent ce)
	{
		if (imgStream == null) return;
		final byte[] rawimg = imgStream.getRawImage();
		for (Enumeration e = m_clients.elements(); e.hasMoreElements();) {
			final Socket sock = (Socket) e.nextElement();
			Thread t = new Thread() {
				public void run()
				{
					try {
						DataOutputStream out = (DataOutputStream) m_out.get(sock);
						out.writeBytes("Content-Type: " + imgStream.getType() + "\n\n");
						out.write(rawimg, 0, rawimg.length);

						out.writeBytes("\n--" + BOUNDARY + "\n");
					} catch (IOException ie) {
						disconnect(sock);
					}
				}
			};
			t.start();
		}
	}
}
