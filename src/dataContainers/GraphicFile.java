package dataContainers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JMenuItem;

public class GraphicFile extends MediaFile{

	private static final long serialVersionUID = -7780276326943050864L;

	public GraphicFile(File file, MediaLibrary owner) {
		super(file, owner);
		m_popUp = new GraphicFilePopUp();
	}
	
	public GraphicFile(String filePath, MediaLibrary owner){
		this(new File(filePath), owner);
	}
	
	public GraphicFile(String filePath, String imgFilePath, MediaLibrary owner){
		this(new File(filePath), new File(imgFilePath), owner);
	}
	
	public GraphicFile(File file, File imgFile, MediaLibrary owner){
		this(file, owner);
		loadImg(imgFile);
	}

	public class GraphicFilePopUp extends MediaFilePopUp{

		private static final long serialVersionUID = -3771804445786297498L;

		protected JMenuItem m_stuff = new JMenuItem("pic stuff...");
		
		public GraphicFilePopUp(){
			addSeparator();
			add(m_stuff);
			m_stuff.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.print("Action: " + e.getActionCommand() + "\n");
				}
			});
		}
		
	}
}







