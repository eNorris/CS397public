package dataContainers;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JMenuItem;

import util.Util;

public class AudioFile extends MediaFile{

	private static final long serialVersionUID = 7906650163065032773L;
	
	private static Image defaultImg = Util.loadImgRes("graphics/audio.png");

	public AudioFile(File file, MediaLibrary owner) {
		super(file, owner);
		m_popUp = new AudioFilePopUp(this);
	}

	public AudioFile(String filePath, MediaLibrary owner){
		this(new File(filePath), owner);
	}

	public AudioFile(String filePath, String imgFilePath, MediaLibrary owner){
		this(new File(filePath), new File(imgFilePath), owner);
	}

	public AudioFile(File file, File imgFile, MediaLibrary owner){
		this(file, owner);
		loadImg(imgFile);
		if(thumbnail == null || thumbnail.getWidth(null) == -1)
			thumbnail = defaultImg;
	}

	public class AudioFilePopUp extends MediaFilePopUp{

		private static final long serialVersionUID = -15185330828987922L;
		protected JMenuItem m_soundStuff = new JMenuItem("Sound stuff...");

		public AudioFilePopUp(AudioFile file){
			super(file);
			m_soundStuff.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.print("Action: " + e.getActionCommand() + "\n");
				}
			});
		}
	}
	
	public static AudioFile createFromDB(MediaLibrary parent, ResultSet dbResult){
		
		if(dbResult == null)
			return null;
		
		String filepath = null;
		String imgPath = null;
		try {
			filepath = dbResult.getString("Path") + dbResult.getString("Filename");
			imgPath = Util.relPath("graphics/audio.png");
		} catch (SQLException e) {
			System.out.print("DB ERROR: Path = '" + filepath + "' could not be resolved\n");
			e.printStackTrace();
		}
		AudioFile toReturn = new AudioFile(filepath, imgPath, parent);
		return toReturn;
	}

}