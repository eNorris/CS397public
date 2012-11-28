package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Menu system at the top of the Application/Applet
 * 
 * @author Edward
 *
 */
public class CoreMenu extends JMenuBar{

	/** Generated via Eclipse */
	private static final long serialVersionUID = -6260232465922104398L;
	
	/**
	 * Default Constructor - Adds the appropriate Menus
	 */
	CoreMenu(){
		add(generateFileMenu());
		add(generateEditMenu());
		add(generateHelpMenu());
	}
	
	/**
	 * Creates a Menu headed by "File" and populates it
	 * @return The Menu created
	 */
	public JMenu generateFileMenu(){
		JMenu toReturn = new JMenu("File");
		
		toReturn.add(new MenuItemFileAdd());
		toReturn.add(new MenuItemFileRemove());
		
		return toReturn;
	}
	
	/**
	 * Creates a Menu headed by "Edit" and populates it
	 * @return The Menu created
	 */
	public JMenu generateEditMenu(){
		JMenu toReturn = new JMenu("Edit");
		
		toReturn.add(new MenuItemEditChange());
		toReturn.add(new MenuItemEditModify());
		
		return toReturn;
	}
	
	/**
	 * Creates a Menu headed by "Help" and populates it
	 * @return The Menu created
	 */
	public JMenu generateHelpMenu(){
		JMenu toReturn = new JMenu("Help");
		
		toReturn.add(new MenuItemHelpAbout());
		
		return toReturn;
	}

	private class MenuItemFileAdd extends JMenuItem{
		private static final long serialVersionUID = 2614146146651816448L;
		public MenuItemFileAdd(){
			super("Add");
			this.setEnabled(true);
			this.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.print("Action: " + e.getActionCommand() + "\n");
				}
			});
		}
	}
	
	/** Creates and returns a JMenuItem for File->Remove */
	private class MenuItemFileRemove extends JMenuItem{
		private static final long serialVersionUID = 9216262717029878271L;

		public MenuItemFileRemove(){
			super("Remove");
			this.setEnabled(true);
			this.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.print("Action: " + e.getActionCommand() + "\n");
				}
			});
		}
	}
	
	/** Creates and returns a JMenuItem for Edit->Change */
	private class MenuItemEditChange extends JMenuItem{
		private static final long serialVersionUID = -5554978003547610060L;
		public MenuItemEditChange(){
			super("Change");
			this.setEnabled(true);
			this.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.print("Action: " + e.getActionCommand() + "\n");
				}
			});
		}
	}
	
	/** Creates and returns a JMenuItem for Edit->Modify */
	private class MenuItemEditModify extends JMenuItem{
		private static final long serialVersionUID = 1508756105178959414L;
		public MenuItemEditModify(){
			super("Modify");
			this.setEnabled(true);
			this.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.print("Action: " + e.getActionCommand() + "\n");
				}
			});
		}
	}
	
	/** Creates and returns a JMenuItem for Help->About */
	private class MenuItemHelpAbout extends JMenuItem{
		private static final long serialVersionUID = 70274194970378772L;
		public MenuItemHelpAbout(){
			super("About");
			this.setEnabled(true);
			this.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.print("Action: " + e.getActionCommand() + "\n");
				}
			});
		}
	}
	
}


























