package gui;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class ScheduleFrame extends JInternalFrame{

	private static final long serialVersionUID = -8744938888555087950L;
	
	ScheduleFrame(){
		add(new SchedulePanel());
	}
}

class SchedulePanel extends JPanel{

	private static final long serialVersionUID = -779895148438631558L;
	
	public void init(){
		final JButton button = new JButton("wh?");
		
		add(button);
	}
}
