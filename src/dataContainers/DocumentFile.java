package dataContainers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JMenuItem;

public class DocumentFile extends MediaFile {

	private static final long serialVersionUID = -432259262007877412L;

	public DocumentFile(File file, MediaLibrary owner) {
		super(file, owner);
		m_popUp = new DocumentFilePopUp();
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
	}

	public class DocumentFilePopUp extends MediaFilePopUp{

		private static final long serialVersionUID = 7864855181664320942L;
		protected JMenuItem m_documentStuff = new JMenuItem("doc stuff...");

		public DocumentFilePopUp(){
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