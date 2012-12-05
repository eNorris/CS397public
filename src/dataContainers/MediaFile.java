package dataContainers;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import util.SpaceTimeInt;
import util.SpaceTimeListener;
import util.Util;
import util.World;

public class MediaFile extends Component{
	

	private static final long serialVersionUID = -5359135092454708630L;

	/**Enumeration of possible file size types (Byte, kByte, MByte, GByte)*/
	public static enum FileSizeEnum{
		BYTE, KB, MB, GB
	}
	
	public File file = null;
	public Image thumbnail = null;
	public String description = null;
	public int fileSize = 0;
	public FileSizeEnum fileSizeType = FileSizeEnum.BYTE;
	
	public MediaLibrary owner = null;
	
	protected MediaFilePopUp m_popUp = new MediaFilePopUp();
	protected InfoPopup m_infoPopup = new InfoPopup();
	
	/**Name of the file without extension ("C:\temp\file.txt" => "file")*/
	public String fileName = null;
	
	/**Extension of the file ("C:\temp\file.txt" => "txt")*/
	public String fileExt = null;
	
	public int x = 0, y = 0, w = 0, h = 0;
	public SpaceTimeListener spaceListener = new SpaceTimeListener(this);
	
	// This is the main constructor that everyone else calls
	public MediaFile(File file, MediaLibrary owner){
		setFile(file);
		this.owner = owner;
	}
	
	public MediaFile(String filePath, MediaLibrary owner){
		this(new File(filePath), owner);
	}
	
	public MediaFile(String filePath, String imgFilePath, MediaLibrary owner){
		this(new File(filePath), new File(imgFilePath), owner);
	}
	
	public MediaFile(File file, File imgFile, MediaLibrary owner){
		this(file, owner);
		loadImg(imgFile);
	}
	
	public void setThumbnail(Image thumbnail){
		this.thumbnail = thumbnail;
	}
	
	public void setFile(File file){
		this.file = file;
		setThumbnail(null);
		resolveFileSize();
		
		description = null;
		fileName = null;
		fileExt = null;
	}
	
	public void fetchMetaData(){
		// FIXME - not yet implemented
	}
	
	protected void resolveFileSize(){
		long sizeB = file.length();
		long sizeKB = sizeB / 1024;
		long sizeMB = sizeKB / 1024;
		long sizeGB = sizeMB / 1024;
		
		if(sizeKB < 10){
			fileSize = (int) sizeB;
			fileSizeType = FileSizeEnum.BYTE;
		}else if(sizeMB < 10){
			fileSize = (int) sizeKB;
			fileSizeType = FileSizeEnum.KB;
		}else if(sizeGB < 10){
			fileSize = (int) sizeMB;
			fileSizeType = FileSizeEnum.MB;
		}else{
			fileSize = (int) sizeGB;
			fileSizeType = FileSizeEnum.GB;
		}
	}
	
	public void loadImg(String filePath){
		loadImg(new File(filePath));
	}
	
	public void loadImg(File file){
		
		try{
			BufferedImage originalImage = ImageIO.read(file);
			int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
	 
			BufferedImage resizedImage;
			
			if(World.config.useRenderHint){
				resizedImage = resizeImageWithHint(originalImage, type);
			}else{
				resizedImage = resizeImage(originalImage, type);
			}
			thumbnail = resizedImage;
	 
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
		
		w = thumbnail.getWidth(null);
		h = thumbnail.getHeight(null);
	}
	
	public void setPos(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x + owner.space.cx;
	}
	
	public int getY(){
		return y + owner.space.cy;
	}
	
	public boolean isInBoundsLocal(int x, int y){
		return (x >= this.x && 			// left bound
				x <= this.x + w && 		// right bound
				y >= this.y && 			// top bound
				y <= this.y + this.h);	// bottom bound
	}
	
	public boolean isInBounds(int x, int y){
//		return (x >= this.x + owner.space.cx && 			// left bound
//				x <= this.x + owner.space.cx + w && 		// right bound
//				y >= this.y + owner.space.cy && 			// top bound
//				y <= this.y + owner.space.cy + this.h);		// bottom bound
		return (x >= this.x + owner.space.ix && 			// left bound
				x <= this.x + owner.space.ix + w && 		// right bound
				y >= this.y + World.space.iy && 			// top bound
				y <= this.y + World.space.iy + this.h);		// bottom bound
	}
	
	public class MediaFilePopUp extends JPopupMenu{
		
		protected JMenuItem m_open = new JMenuItem("Open");
		protected JMenuItem m_edit = new JMenuItem("Edit");
		protected JMenuItem m_delete = new JMenuItem("Delete");

		private static final long serialVersionUID = -8357288887563917285L;
		
		public MediaFilePopUp(){
			add(m_open);
			m_open.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.print("Action: " + e.getActionCommand() + "\n");
				}
			});
			
			add(m_edit);
			m_edit.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.print("Action: " + e.getActionCommand() + "\n");
				}
			});
			
			add(m_delete);
			m_delete.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.print("Action: " + e.getActionCommand() + "\n");
				}
			});
		}
	}
	
	public void popUp(Component caller, int x, int y){	
		m_popUp.setEnabled(true);
		m_popUp.setVisible(true);
		m_popUp.show(caller, x, y);
	}
	
	public void popUpInfo(Component caller, int x, int y){
		m_infoPopup.setEnabled(true);
		m_infoPopup.setVisible(true);
		m_infoPopup.show(caller, x, y);
	}
	
	//Author: Mkyong
	//http://www.mkyong.com/java/how-to-resize-an-image-in-java/
	private static BufferedImage resizeImage(BufferedImage originalImage, int type){
		BufferedImage resizedImage = new BufferedImage(World.config.getDimension().width, World.config.getDimension().height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, World.config.getDimension().width, World.config.getDimension().height, null);
		g.dispose();
		return resizedImage;
	}

	//Author: Mkyong
	//http://www.mkyong.com/java/how-to-resize-an-image-in-java/
	private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type){
	 
		BufferedImage resizedImage = new BufferedImage(World.config.getDimension().width, World.config.getDimension().height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, World.config.getDimension().width, World.config.getDimension().height, null);
		g.dispose();	
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		return resizedImage;
	}
	
	public void draw(Graphics g){
		g.drawImage(thumbnail, x + owner.space.ix, y + World.space.iy, null);
	}
	
	public class InfoPopup extends JPopupMenu{

		private static final long serialVersionUID = -3564833458604078898L;
		
		public SpaceTimeInt space = new SpaceTimeInt();
		
		public boolean active = false;

		public InfoPopup(){
			setSize(200, 200);
			
			add(new JMenuItem("MediaFile::InfoPopup::cat"));
			add(new JMenuItem("dog"));
			add(new InfoPanel());
			
//			add
		}
		
		public void setVisible(boolean vis){
			super.setVisible(vis);
		}
		
		public void update(int x, int y){
			if(active){
				space.universalUpdate(x, y);
			}
		}
		
		public void activate(int x, int y){
			space.wormHole(x, y);
	System.out.print("activating to x = " + x + "\n");
			active = true;
			setVisible(true);
		}
		
		public void deactivate(){
			active = false;
			setVisible(false);
		}
		
		public void paint(Graphics g){
			g.fillRect(0, 0, 200, 200);
			super.paint(g);
		}
		
		public class InfoPanel extends JPanel{
			private static final long serialVersionUID = 359115013335470480L;
			
			private Image m_playOn = null;
			private Image m_playOff = null;
			private Image m_stopOn = null;
			private Image m_stopOff = null;
			
			private boolean m_playing = false;
			private boolean m_hovering = false;

			InfoPanel(){
				setLayout(new FlowLayout());
				add(new JLabel("what?"));
				add(new JLabel("vox!"));
				setOpaque(false);
				
				m_playOn = Util.loadImgRes("/graphics/play1Large.png");
				m_playOff = Util.loadImgRes("/graphics/play2Large.png");
				m_stopOn = Util.loadImgRes("/graphics/stop1Large.png");
				m_stopOff = Util.loadImgRes("/graphics/stop2Large.png");
				
				setSize(500, 500);
				setBackground(Color.RED);
			}
			
			public void paint(Graphics g){
				super.paint(g);
				Image drawer = null;
				if(m_playing){
					if(m_hovering){
						drawer = m_stopOn;
					}else{
						drawer = m_stopOff;
					}
				}else{
					if(m_hovering){
						drawer = m_playOn;
					}else{
						drawer = m_playOff;
					}
				}
				g.drawImage(drawer, 0, 0, 50, 50, this);
			}
		}
	}
}












