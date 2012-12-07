package dataContainers;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JMenuItem;

import util.Util;

public class DocumentFile extends MediaFile {

	private static final long serialVersionUID = -432259262007877412L;
	
	private static Image defaultImg= Util.loadImgRes("graphics/document.png");

	public DocumentFile(File file, MediaLibrary owner) {
		super(file, owner);
		m_popUp = new DocumentFilePopUp(this);
	}

	public DocumentFile(String filePath, MediaLibrary owner){
		this(new File(filePath), owner);
	}

	public DocumentFile(String filePath, String imgFilePath, MediaLibrary owner){
		this(new File(filePath), new File(imgFilePath), owner);
	}

	public DocumentFile(File file, File imgFile, MediaLibrary owner){
		this(file, owner);
		loadImg(imgFile);
		if(thumbnail == null || thumbnail.getWidth(null) == -1)
			thumbnail = defaultImg;
	}

	public class DocumentFilePopUp extends MediaFilePopUp{
		
//		public DocumentFilePopUp(DocumentFile file){
//			super(file);
//		}

		private static final long serialVersionUID = 7864855181664320942L;
		protected JMenuItem m_documentStuff = new JMenuItem("doc stuff...");

		public DocumentFilePopUp(DocumentFile file){
			super(file);
			
			add(m_documentStuff);
			m_documentStuff.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.print("Action: " + e.getActionCommand() + "\n");
				}
			});
		}
	}

}