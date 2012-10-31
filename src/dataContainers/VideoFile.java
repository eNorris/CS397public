package dataContainers;

import java.io.File;

import javax.swing.JMenuItem;

public class VideoFile extends MediaFile {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5913804900450779489L;
	public String director = null;
	public String title = null;
	
	public String language = null;
	public boolean hasSubtitles = false;
	
	int durationTime = 0; // In seconds

	public VideoFile(File file, MediaLibrary owner) {
		super(file, owner);
		m_popUp = new VideoFilePopUp();
	}
	
	public VideoFile(String filePath, MediaLibrary owner){
		this(new File(filePath), owner);
	}
	
	public VideoFile(String filePath, String imgFilePath, MediaLibrary owner){
		this(new File(filePath), new File(imgFilePath), owner);
	}
	
	public VideoFile(File file, File imgFile, MediaLibrary owner){
		this(file, owner);
		loadImg(imgFile);
	}
	
	public class VideoFilePopUp extends MediaFilePopUp{

		private static final long serialVersionUID = -8325188779288235566L;
		protected JMenuItem m_vlc = new JMenuItem("Open in VLC");

		public VideoFilePopUp(){
//			add(m_vlc);
		}
	}

}
