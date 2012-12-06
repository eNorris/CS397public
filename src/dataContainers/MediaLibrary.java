package dataContainers;

import java.awt.Component;
import java.awt.Graphics;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.SpaceTimeInt;
import util.World;

public class MediaLibrary extends ArrayList<MediaFile>{

	private static final long serialVersionUID = -7147719263849293488L;
	
	public ArrayList<MediaLibrary> m_subLibrary = new ArrayList<MediaLibrary>();
	public SpaceTimeInt space = new SpaceTimeInt();
	
	public Component owner = null;
	
	public MediaLibrary(){
		// Do Nothing
	}
	
	public MediaLibrary(Component owner){
		this.owner = owner;
	}

	public MediaLibrary addLibrary(){
		MediaLibrary newLib = new MediaLibrary();
		m_subLibrary.add(newLib);
		return newLib;
	}
	
	public MediaLibrary addLibrary(MediaLibrary library){
		m_subLibrary.add(library);
		return library;
	}
	
	public void draw(Graphics g){
		for(int i = 0; i < size(); i++){
			get(i).draw(g);
		}

		for(int i = 0; i < m_subLibrary.size(); i++){
			m_subLibrary.get(i).draw(g);
		}
	}
	
	/**
	 * Places the media files on the screen
	 */
	public void distribute(int height){
		
//		System.out.print("@ paint(): h = " + height + "\n");
		
		// FIXME - calculate this
		int rows = height / World.config.getDimension().height;
		if(rows == 0)
			rows = 1;
		
		for(int i = 0; i < size(); i++){
			
			int x = World.config.bufferSpace;
			int y = i*(World.config.getDimension().height + World.config.bufferSpace) + World.config.bufferSpace;//150;
			
			while(y >= (World.config.getDimension().height + World.config.bufferSpace)*rows){
				y -= (World.config.getDimension().height + World.config.bufferSpace)*rows;
				x += World.config.getDimension().width + World.config.bufferSpace;
			}
			get(i).setPos(x, y);
		}
	}
	
	public void constructFromDB(){
		ResultSet results = World.dbc.Query("SELECT * FROM File");
		boolean success = false;
		try {
			while(results != null && results.next()){
				success = true;
				String filetype = results.getString("Type");
				String filepath = results.getString("Path");
				// A => Audio, I => Image, M => Movie, T => TV, O => Other
				if(filetype.equals("A")){
					add(AudioFile.createFromDB(this, results));
				}//else if(filetype.equals("I")){
				//	add(GraphicFile.createFromDB(this, results));
				//}
			}
			if(!success){
				System.out.print("No files found!\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public static MediaLibrary searchLib(MediaLibrary lib, String searchStr){
//		MediaLibrary toReturn = new MediaLibrary();
//		for(MediaFile file : lib){
//			if(file.file.getName().contains(searchStr))
//				toReturn.add(file);
//		}
//		return toReturn;
//	}
	
	public MediaLibrary searchLib(MediaLibrary lib, String searchStr){
		//MediaLibrary toReturn = new MediaLibrary();
		for(MediaFile file : lib){
			if(file.file.getName().contains(searchStr))
				add(file);
		}
		return this;
	//	return toReturn;
	}
}
