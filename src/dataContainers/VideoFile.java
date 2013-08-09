package dataContainers;

import util.Util;
import util.World;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VideoFile extends MediaFile {

	private static final long serialVersionUID = -5913804900450779489L;
	public String director = null;
	public String title = null;
	
	public String language = null;
	public boolean hasSubtitles = false;
	
	int durationTime = 0; // In seconds
	
	// Load the default at the very beginning
	private static Image defaultImg = Util.loadImgRes("graphics/video.png");

	public VideoFile(File file, MediaLibrary owner) {
		super(file, owner);
		m_popUp = new VideoFilePopUp(this);
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
		if(thumbnail == null || thumbnail.getWidth(null) == -1)
			thumbnail = defaultImg;
	}
	
	public class VideoFilePopUp extends MediaFilePopUp{

		private static final long serialVersionUID = -8325188779288235566L;
		protected JMenuItem m_vlc = new JMenuItem("Open in VLC");

		public VideoFilePopUp(VideoFile file){
			super(file);
		}
	}
	
	public static VideoFile createMovieFromDB(MediaLibrary parent, ResultSet dbResult){
		
		String filepath = null;
		String imgPath = null;
		try {
			filepath = dbResult.getString("Path") + dbResult.getString("Filename");
			
			ResultSet result = World.dbc.query("SELECT * FROM Movie WHERE Path=" + filepath);
			if(result == null){
				System.out.print("ERROR: Could not find Movie matching key Path=" + filepath);
			}else{
				imgPath = result.getString("RTID");
			}
			if(imgPath != null)
				imgPath = imgPath + "thumb.jpg";
			
		} catch (SQLException e) {
			System.out.print("DB ERROR: Path = '" + filepath + "' could not be resolved\n");
			e.printStackTrace();
		}
		
		VideoFile toReturn;
		if(imgPath != null){
			toReturn = new VideoFile(filepath, imgPath, parent);
		}else{
			toReturn = new VideoFile(filepath, Util.relPath("graphics/video.png"), parent);
		}
		return toReturn;
	}
	
	public static VideoFile createTVFromDB(MediaLibrary parent, ResultSet dbResult){
		
		String filepath = null;
		String imgPath = null;
		try {
			filepath = dbResult.getString("Path") + dbResult.getString("Filename");
			
			ResultSet r = World.dbc.query("SELECT * FROM Movie WHERE Path=" + filepath);
			if(r == null){
				System.out.print("ERROR: Could not find Movie matching key Path=" + filepath);
			}else{
				imgPath = r.getString("RTID");
			}
			if(imgPath != null)
				imgPath = imgPath + "thumb.jpg";
			
		} catch (SQLException e) {
			System.out.print("DB ERROR: Path = '" + filepath + "' could not be resolved\n");
			e.printStackTrace();
		}
		
		VideoFile toReturn;
		if(imgPath != null){
			toReturn = new VideoFile(filepath, imgPath, parent);
		}else{
			toReturn = new VideoFile(filepath, Util.relPath("graphics/video.png"), parent);
		}
		return toReturn;
	}

}
