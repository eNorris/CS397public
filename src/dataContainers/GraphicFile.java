package dataContainers;

import util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GraphicFile extends MediaFile{

	private static final long serialVersionUID = -7780276326943050864L;
	
	private static Image defaultImg = Util.loadImgRes("/graphics/graphic.png");

	public GraphicFile(File file, MediaLibrary owner) {
		super(file, owner);
		m_popUp = new GraphicFilePopUp(this);
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
		if(thumbnail == null || thumbnail.getWidth(null) == -1)
			thumbnail = defaultImg;
	}

	public class GraphicFilePopUp extends MediaFilePopUp{

		private static final long serialVersionUID = -3771804445786297498L;

		protected JMenuItem m_stuff = new JMenuItem("pic stuff...");

		public GraphicFilePopUp(GraphicFile file){
			super(file);
			
//			addSeparator();
//			add(m_stuff);
			m_stuff.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.print("Action: " + e.getActionCommand() + "\n");
				}
			});
		}
	}
	
	public static GraphicFile createFromDB(MediaLibrary parent, ResultSet dbResult){
		
		String filepath = null;
		try {
			// The filepath is itself the path to the image since the file is the image
			filepath = dbResult.getString("Path");
//			filepath = dbResult.getString("Path") + dbResult.getString("Filename");
		} catch (SQLException e) {
			System.out.print("DB ERROR: Path = '" + filepath + "' could not be resolved\n");
			e.printStackTrace();
		}
        System.out.println("createFromDB filename [" + filepath + "]");
		GraphicFile toReturn = new GraphicFile(filepath, filepath, parent);
		return toReturn;
	}
}
