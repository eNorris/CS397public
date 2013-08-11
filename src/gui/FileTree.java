package gui;
import santhoshTree.CheckTreeManager;
import util.Util;
import util.World;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.*;
import java.io.File;

public class FileTree extends JPanel{

	private static final long serialVersionUID = -2836582864395401650L;

	private JTree fileTree;
	private FileSystemModel fileSystemModel;
	private JTextArea fileDetailsTextArea = new JTextArea("");
    private static final String ROOT_DIRECTORY = "C:\\";

    public FileTree() {
//		setBackground(Color.RED);
//		setPreferredSize(new Dimension(250, 100));

        setLayout(new BorderLayout());

		fileDetailsTextArea.setEditable(false);
		fileDetailsTextArea.setPreferredSize(new Dimension(0, 50));
		fileSystemModel = new FileSystemModel(new File(ROOT_DIRECTORY));
//        if (World.dbc.isEstablished()) {
//            // TODO : Finish filling tree from database
//            ArrayList<TreePath> treePaths = InstantiateTree.checkTreeFromDatabase();
//        }
		fileTree = new JTree(fileSystemModel);
		
		World.treeManager = new CheckTreeManager(fileTree);

		fileTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent event) {
				File file = (File) fileTree.getLastSelectedPathComponent();
				fileDetailsTextArea.setText(getFileDetails(file));
				fileDetailsTextArea.setCaretPosition(0);
			}
		});
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, new JScrollPane(fileDetailsTextArea), 
				new JScrollPane(fileTree));
		
		add(splitPane);
	}

    public void paint(Graphics g){
		Util.drawGradientBackground(this, g);
		super.paint(g);
	}

	private String getFileDetails(File file) {
		if (file == null)
			return "";
		StringBuffer buffer = new StringBuffer();
		buffer.append("Name: " + file.getName() + "\n");
		int ypref = 1;
		int xpref = fileDetailsTextArea.getFontMetrics(fileDetailsTextArea.getFont()).stringWidth("Name: " + file.getName() + "\n");
		buffer.append("Path: " + file.getPath() + "\n");
		if(fileDetailsTextArea.getFontMetrics(fileDetailsTextArea.getFont()).stringWidth("Path: " + file.getPath() + "\n") > xpref)
			xpref = fileDetailsTextArea.getFontMetrics(fileDetailsTextArea.getFont()).stringWidth("Path: " + file.getPath() + "\n");
		ypref++;

		buffer.append("Size: " + file.length() + "\n");
		if(fileDetailsTextArea.getFontMetrics(fileDetailsTextArea.getFont()).stringWidth("Size: " + file.length() + "\n") > xpref)
			xpref = fileDetailsTextArea.getFontMetrics(fileDetailsTextArea.getFont()).stringWidth("Size: " + file.length() + "\n");
		ypref++;
		fileDetailsTextArea.setPreferredSize(new Dimension(xpref, ypref * fileDetailsTextArea.getFontMetrics(fileDetailsTextArea.getFont()).getHeight()));
		return buffer.toString();
	}
}
