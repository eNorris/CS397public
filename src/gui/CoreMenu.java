package gui;
import util.MiscSQL;
import util.SQLBuilder;
import util.World;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static gui.MainApplication.sout;

/**
 * Menu system at the top of the Application/Applet.
 */
public class CoreMenu extends JMenuBar{

    private static final long serialVersionUID = -6260232465922104398L;

    JTextField searchBox = new JTextField(20);
    JButton searchButton = new JButton("Search");
    JButton searchClearButton = new JButton("Clear Search");

    public Core owner = null;

    /**
     * Default Constructor - Adds the appropriate Menus
     */
    CoreMenu(Core owner){
        this.owner = owner;
        add(generateCrawlerMenu());
        add(generateHelpMenu());

        searchBox.addActionListener(new SearchTextHandler());
        searchButton.addActionListener(new SearchButtonHandler());
        searchClearButton.addActionListener(new SearchClearButtonHandler());
        add(searchBox);
        add(searchButton);
        add(searchClearButton);
    }

    private class SearchTextHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!searchBox.getText().isEmpty()) {
                Wall.showSearchLib = searchBox.getText();
                owner.wall.repaint();
            } else if(!Wall.showSearchLib.equals("")){
                Wall.showSearchLib = "";
                owner.wall.repaint();
            }
        }
    }

    private class SearchButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!searchBox.getText().isEmpty()) {
                Wall.showSearchLib = searchBox.getText();
                owner.wall.repaint();
            } else if(!Wall.showSearchLib.equals("")){
                Wall.showSearchLib = "";
                owner.wall.repaint();
            }
        }
    }

    private class SearchClearButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!searchBox.getText().isEmpty()) {
                searchBox.setText("");
                if(!Wall.showSearchLib.equals("")){
                    Wall.showSearchLib = "";
                    owner.wall.repaint();
                }
            }
        }
    }

    /**
     * Creates a JMenu headed by "File" and populates it.
     * @return The created JMenu
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
        public void doOnSelection(ActionEvent e) {

        }
    }

    /**
     * Creates and returns a JMenuItem for File->Remove
     * */
    private class MenuItemFileRemove extends MenuItemBase{
        private static final long serialVersionUID = 9216262717029878271L;
        public MenuItemFileRemove(){super("Remove");}

        @Override
        public void doOnSelection(ActionEvent e) {
            // TODO Auto-generated method stub
        }
    }

    /**
     * Creates and returns a JMenuItem for Edit->Change
     **/
    private class MenuItemEditChange extends MenuItemBase{
        private static final long serialVersionUID = -5554978003547610060L;
        public MenuItemEditChange(){super("Change");}

        @Override
        public void doOnSelection(ActionEvent e) {
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
        public void doOnSelection(ActionEvent e) {
            // TODO Auto-generated method stub

        }

    }

    /**
     * Creates and returns a JMenuItem for Help->About
     **/
    private class MenuItemHelpAbout extends MenuItemBase{
        private static final long serialVersionUID = 70274194970378772L;
        public MenuItemHelpAbout(){
            super("About");
        }
        @Override
        public void doOnSelection(ActionEvent e) {
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
        public void doOnSelection(ActionEvent ev) {
            launchCrawler();
        }

        private void launchCrawler() {
            TreePath[] treePath = World.treeManager.getSelectionModel().getSelectionPaths();

            World.crawlerDirs.clear();
            for(int i = 0; i < treePath.length; i++){
                StringBuilder s = new StringBuilder();
                for(int j = 0; j < treePath[i].getPathCount(); j++){
                    s.append((String) treePath[i].getPath()[j].toString() + "/");
                }
                World.crawlerDirs.add(new File(s.toString()));
            }

            for(File directory : World.crawlerDirs){
                String scriptToRun = System.getProperty("user.dir")+"\\scripts\\overarchingScript.pl";
                String cmd = "perl \"" + scriptToRun +  "\" \"" + directory.toString() + "\"";
                sout("@CoreMenu::MenuItemCrawlerLaunch::doOnSelection(): cmd = " + cmd);

                try {
                    Runtime.getRuntime().exec(cmd);
                } catch (IOException e1) {
                    sout("Could not run command: " + cmd);
                    e1.printStackTrace();
                }
            }
            World.dbc.execute(
                    SQLBuilder.modifyPaths(
                            MiscSQL.optimizePathModifications(World.crawlerDirs)));

            Executor exe = Executors.newCachedThreadPool();
            exe.execute(new ExecSQL());
        }
    }

    /**
     * Creates and returns a JMenuItem for Crawler->Launch
     **/
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
