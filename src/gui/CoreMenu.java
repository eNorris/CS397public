package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.tree.TreePath;

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
		add(generateFileMenu());
		add(generateEditMenu());
		add(generateHelpMenu());
		add(generateCrawlerMenu());
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
	
	/** Creates and returns a JMenuItem for Crawler->Launch */
	private class MenuItemCrawlerLaunch extends JMenuItem{
		private static final long serialVersionUID = 70274194970378772L;
		public MenuItemCrawlerLaunch(){
			super("Launch");
			this.setEnabled(true);
			this.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					
					TreePath[] treePath = World.treeManager.getSelectionModel().getSelectionPaths();
					
					World.crawlerDirs.clear();
					for(int i = 0; i < treePath.length; i++){
						StringBuilder s = new StringBuilder();
						for(int j = 0; j < treePath[i].getPathCount(); j++){
							s.append((String) treePath[i].getPath()[j].toString() + "/");
						}
						World.crawlerDirs.add(new File(s.toString()));
					}
					
					for(File dir : World.crawlerDirs)
						System.out.print("Will crawl " + dir.toString() + "\n");
					
					System.out.print("\n\n");
					
//					for(File directory : World.crawlerDirs){
//					// FIXME - Ask Thomas if the crawler will only take directories or if it will take indiv. files
//						if(!directory.isDirectory())
//							continue;
//						String cmd = "perl crawlDirectory.pl \"" + directory.toString() + "\"";
//						try {
//							Runtime.getRuntime().exec(cmd);
//						} catch (IOException e1) {
//							System.out.print("Could not run command: " + cmd);
//							e1.printStackTrace();
//						}
//						System.out.print("Action: " + e.getActionCommand() + "\n");
//					}
				}
			});
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
					
//					if(m_authorFrame == null || m_authorFrame.isClosed()){
//						m_authorFrame = new CS342Lab09AuthorIdFrame();
//						add(m_authorFrame);
//						m_authorFrame.toFront();
//					}
//					if(World.scheduleFrame == null || World.scheduleFrame.isClosed()){
//						World.scheduleFrame = new ScheduleFrame();
//						World.scheduleFrame.toFront();
//						
//						World.core.add(World.scheduleFrame);
//					}
//					World.core.
					
					
					// TODO Auto-generated method stub
					System.out.print("Action: " + e.getActionCommand() + "\n");
				}
			});
		}
	}
	
}


























