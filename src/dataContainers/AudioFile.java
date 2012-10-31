package dataContainers;

import java.io.File;

import javax.swing.JLabel;
import javax.swing.JMenuItem;

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
			add(new JLabel("  == TEST LABEL =="));
			add(m_soundStuff);
		}
	}

}
