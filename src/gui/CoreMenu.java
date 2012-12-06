package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;

import util.Util;
import util.World;

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
//		add(generateFileMenu());
//		add(generateEditMenu());
		add(generateCrawlerMenu());
		add(generateHelpMenu());
		
		
//		JSearchBar searchBar = new JSearchBar();
//		add(searchBar);
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
	
	public JMenu generateCrawlerMenu(){
		JMenu toReturn = new JMenu("Crawler");
		
		toReturn.add(new MenuItemCrawlerLaunch());
		toReturn.add(new MenuItemCrawlerSchedule());
		
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

//	private class MenuItemFileAdd extends JMenuItem{
//		private static final long serialVersionUID = 2614146146651816448L;
//		public MenuItemFileAdd(){
//			super("Add");
//			this.setEnabled(true);
//			this.addActionListener(new ActionListener(){
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					// magic numbers
//					if(e.getModifiers() == 0 || e.getModifiers() == 16){
//					// TODO Auto-generated method stub
//						System.out.print("Action: " + e.getActionCommand() + "\n");
//					}
//				}
//			});
//		}
//	}
	
	private class MenuItemFileAdd extends MenuItemBase{
		private static final long serialVersionUID = -5792123978651236809L;
		public MenuItemFileAdd() {super("Add");}

		@Override
		public void doOnSelection() {
			
		}
	}
	
	/** Creates and returns a JMenuItem for File->Remove */
	private class MenuItemFileRemove extends MenuItemBase{
		private static final long serialVersionUID = 9216262717029878271L;
		public MenuItemFileRemove(){super("Remove");}

		@Override
		public void doOnSelection() {
			// TODO Auto-generated method stub
		}
	}
	
	/** Creates and returns a JMenuItem for Edit->Change */
	private class MenuItemEditChange extends MenuItemBase{
		private static final long serialVersionUID = -5554978003547610060L;
		public MenuItemEditChange(){super("Change");}
		
		@Override
		public void doOnSelection() {
			// TODO Auto-generated method stub
			
		}
	}
	
	/** Creates and returns a JMenuItem for Edit->Modify */
	private class MenuItemEditModify extends MenuItemBase{
		private static final long serialVersionUID = 1508756105178959414L;
		public MenuItemEditModify(){
			super("Modify");
		}
		@Override
		public void doOnSelection() {
			// TODO Auto-generated method stub
			
		}
			
	}
	
	/** Creates and returns a JMenuItem for Help->About */
	private class MenuItemHelpAbout extends MenuItemBase{
		private static final long serialVersionUID = 70274194970378772L;
		public MenuItemHelpAbout(){
			super("About");
		}
		@Override
		public void doOnSelection() {
			String helpText = "Media Manager Project Solution created for CS397 by:\n" + 
					"Ben Campbell, Gerald Hold, Edward Norris, and Thomas Reese\n";
			JOptionPane.showMessageDialog(new JFrame(), helpText, "About", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	/** Creates and returns a JMenuItem for Crawler->Launch */
	private class MenuItemCrawlerLaunch extends MenuItemBase{
		private static final long serialVersionUID = 70274194970378772L;
		public MenuItemCrawlerLaunch(){super("Launch");}
		
		@Override
		public void doOnSelection() {
			TreePath[] treePath = World.treeManager.getSelectionModel().getSelectionPaths();
			
			World.crawlerDirs.clear();
			for(int i = 0; i < treePath.length; i++){
				StringBuilder s = new StringBuilder();
				for(int j = 0; j < treePath[i].getPathCount(); j++){
					s.append((String) treePath[i].getPath()[j].toString() + "/");
				}
				World.crawlerDirs.add(new File(s.toString()));
			}
			
			System.out.print("\n\n");
					
			for(File directory : World.crawlerDirs){
				String imgDir = Util.relPath("/data/images");
//				if(!directory.isDirectory())
//					continue;
				String scriptToRun = Util.relPath("/overarchingScript.pl");
				String cmd = "perl " + scriptToRun + " \"" + directory.toString() + "\" \"" + imgDir + "\"";
System.out.print("@CoreMenu::MenuItemCrawlerLaunch::doOnSelection(): cmd = " + cmd + "\n");
				try {
					Runtime.getRuntime().exec(cmd);
				} catch (IOException e1) {
					System.out.print("Could not run command: " + cmd);
					e1.printStackTrace();
				}
			}
		}
	}
	
	/** Creates and returns a JMenuItem for Crawler->Launch */
	private class MenuItemCrawlerSchedule extends JMenuItem{
		private static final long serialVersionUID = 70274194970378772L;
		public MenuItemCrawlerSchedule(){
			super("Schedule...");
			this.setEnabled(true);
			this.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					ScheduleDialog d = new ScheduleDialog();
					d.setVisible(true);
					System.out.print("Action: " + e.getActionCommand() + "\n");
				}
			});
		}
	}
	
}


























