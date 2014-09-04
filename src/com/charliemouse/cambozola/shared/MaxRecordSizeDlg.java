package com.charliemouse.cambozola.shared;
import java.awt.*;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class MaxRecordSizeDlg extends Dialog implements ActionListener {
	public boolean id = false;
	Button ok,can;
	public WholeNumberField minFreeSpace;


	public MaxRecordSizeDlg(Frame frame,int currentSize){
		 super(frame, "Set Max Record Size", true);
		  JPanel labelPane = new JPanel();
	      labelPane.setLayout(new FlowLayout());
	      JPanel buttonPane = new JPanel();
	      buttonPane.setLayout(new GridLayout(0,3));
		  //setLayout(new FlowLayout());
		  minFreeSpace = new WholeNumberField(currentSize,10);
		  labelPane.add(new Label("Max Record Size :"));
		  labelPane.add(minFreeSpace);
		  labelPane.add(new Label("(MB)"));
		  buttonPane.add(ok = new Button("OK"));
		  buttonPane.add(new Label(""));
		  ok.addActionListener(this); 
		  buttonPane.add(can = new Button("Cancel"));
		  can.addActionListener(this);
		  //addOKCancelPanel();
	      
		  JPanel contentPane = new JPanel();
	      contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	      contentPane.setLayout(new BorderLayout());
	      contentPane.add(labelPane, BorderLayout.NORTH);
	      contentPane.add(buttonPane, BorderLayout.EAST);
	      add(contentPane);
	      
		  createFrame();
		  pack();
		  setVisible(true);
 }

 void addOKCancelPanel() {
  Panel p = new Panel();
  p.setLayout(new FlowLayout());
  createButtons( p );
  add( p );
  }

 void createButtons(Panel p) {
  p.add(ok = new Button("OK"));
  ok.addActionListener(this); 
  p.add(can = new Button("Cancel"));
  can.addActionListener(this);
  }

 void createFrame() {
  Dimension d = getToolkit().getScreenSize();
  setLocation(d.width/4,d.height/3);
  }

 public void actionPerformed(ActionEvent ae){
  if(ae.getSource() == ok) {
    id = true;
    setVisible(false);
    }
  else if(ae.getSource() == can) {
    id = false;
    setVisible(false);
    }
  }
}
