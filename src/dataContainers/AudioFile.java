package dataContainers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JMenuItem;

import util.Util;
import util.World;

public class AudioFile extends MediaFile{

	private static final long serialVersionUID = 7906650163065032773L;

	public AudioFile(File file, MediaLibrary owner) {
		super(file, owner);
		m_popUp = new AudioFilePopUp();
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
	}

	public class AudioFilePopUp extends MediaFilePopUp{

		private static final long serialVersionUID = -15185330828987922L;
		protected JMenuItem m_soundStuff = new JMenuItem("Sound stuff...");

		public AudioFilePopUp(){
			add(new JLabel(" == TEST LABEL =="));
			add(m_soundStuff);
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
		
		String filepath = null;
		try {
			filepath = dbResult.getString("Path");
		} catch (SQLException e) {
			System.out.print("DB ERROR: Path = '" + filepath + "' could not be resolved\n");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		AudioFile toReturn = new AudioFile(filepath, Util.relPath("/pic1.bmp"), parent);
		
//		World.dbc.Q
		
		return toReturn;
	}

}