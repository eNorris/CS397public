package dataContainers;

import java.awt.Graphics;
import java.util.ArrayList;

import util.SpaceTimeInt;
import util.World;

public class MediaLibrary extends ArrayList<MediaFile>{

	private static final long serialVersionUID = -7147719263849293488L;
	
	private ArrayList<MediaLibrary> m_subLibrary = new ArrayList<MediaLibrary>();
	public SpaceTimeInt space = new SpaceTimeInt();
	
	public MediaLibrary(){
		// Do Nothing
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
	
}
