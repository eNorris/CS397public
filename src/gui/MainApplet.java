package gui;

import java.awt.Graphics;

import javax.swing.JApplet;


public class MainApplet extends JApplet{
	
	private static final long serialVersionUID = 3536666737653440246L;

	public void init(){
		setJMenuBar(new CoreMenu());
		getContentPane().add(new Core());
	}
	
	public void paint(Graphics g){
		super.paint(g);
	}
}
