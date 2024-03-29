package gui;

import javax.swing.*;
import java.awt.*;

public class ScheduleDialog extends JDialog{

	private static final long serialVersionUID = 1847983443402711718L;
	
//	JButtonGroup howOftenGroup = new JRadioGroup();
	
	ScheduleDialog(){
		setTitle("Scheduler... (NOT WORKING YET)");
		setBounds(50, 50, 600, 75);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());
		
		JRadioButton hourly = new JRadioButton("Hourly");
		JRadioButton daily = new JRadioButton("Daily");
		JRadioButton weekly = new JRadioButton("Weekly");
		JLabel everySoOften = new JLabel("Every: ");
	//	JLabel atLabel = new JLabel("At: ");
		JTextField input = new JTextField(10);
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");
		
		ButtonGroup group = new ButtonGroup();
		group.add(hourly);
		group.add(daily);
		group.add(weekly);
		
		everySoOften.setSize(20, 20);
		
		add(hourly);
		add(daily);
		add(weekly);
		
		add(everySoOften);
		add(input);
//		add(atLabel);
		add(okButton);
		add(cancelButton);
	}
}
