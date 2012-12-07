package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public abstract class MenuItemBase extends JMenuItem{
	private static final long serialVersionUID = 2614146146651816448L;
	public MenuItemBase(String str){
		super(str);
		this.setEnabled(true);
		this.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// allow only enter or left mouse click
				if(e.getModifiers() == 0 || e.getModifiers() == 16){
					System.out.print("Action: " + e.getActionCommand() + "\n");
					doOnSelection(e);
				}
			}
		});
	}
	
	public abstract void doOnSelection(ActionEvent e);
}
