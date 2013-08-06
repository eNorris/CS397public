package dataContainers;

import util.Util;
import util.World;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    protected MediaFilePopUp m_popUp = new MediaFilePopUp(this);

    /**Name of the file without extension ("C:\temp\file.txt" => "file")*/
    public String fileName = null;

    /**Extension of the file ("C:\temp\file.txt" => "txt")*/
    public String fileExt = null;

    public int x = 0, y = 0, w = 0, h = 0;

    // Uncomment this when InfoPopUp is working
//	public SpaceTimeListener spaceListener = new SpaceTimeListener(this);

    /**
     * Base constructor
     * @param file - The file that this mediafile links to
     * @param owner - medialibrary that owns the file
     */
    public MediaFile(File file, MediaLibrary owner){
        setFile(file);
        this.owner = owner;
    }

    /**
     * String constructor
     * @param filePath - fully qualified path to the file
     * @param owner - medialibrary that owns the file
     */
    public MediaFile(String filePath, MediaLibrary owner){
        this(new File(filePath), owner);
    }

    /**
     * String with image constructor
     * @param filePath - path to the file
     * @param imgFilePath - path to the associated image
     * @param owner - medialibrary that owns the file
     */
    public MediaFile(String filePath, String imgFilePath, MediaLibrary owner){
        this(new File(filePath), new File(imgFilePath), owner);
    }

    /**
     * File based image constructor
     * @param file - file to link to
     * @param imgFile - associated image
     * @param owner - medialibrary that owns the file
     */
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

        } catch(IOException e) {
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
        return (x >= this.x + owner.space.ix && 			// left bound
                x <= this.x + owner.space.ix + w && 		// right bound
                y >= this.y + World.space.iy && 			// top bound
                y <= this.y + World.space.iy + this.h);		// bottom bound
    }

    public static MediaFile createFromDB(MediaLibrary parent, ResultSet dbResult){

        String filepath = null;
        try {
            filepath = dbResult.getString("Path") + dbResult.getString("Filename");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if(filepath != null)
            return new MediaFile(filepath, Util.relPath("graphics/mediafile.png"), parent);
        return new MediaFile("Unknown", Util.relPath("grpahics/mediafile.png"), parent);

//		if(imgPath != null){
//			toReturn = new VideoFile(filepath, imgPath, parent);
//		}else{
//			toReturn = new VideoFile(filepath, Util.relPath("graphics/video.png"), parent);
//		}
//		return toReturn;
    }

    public class MediaFilePopUp extends JPopupMenu{

        protected JMenuItem m_open = new JMenuItem("Open");
        protected JMenuItem m_edit = new JMenuItem("Edit");
        protected JMenuItem m_delete = new JMenuItem("Delete");

        protected MediaFilePopUp self = this;
        protected MediaFile owner = null;

        private static final long serialVersionUID = -8357288887563917285L;

        public MediaFilePopUp(MediaFile mediaFile){
            owner = mediaFile;

            add(m_open);
            m_open.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if(self.owner.file != null)
                            Desktop.getDesktop().open(self.owner.file);
                        else
                            System.out.print("Could not open file.\n");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    System.out.print("Action: " + e.getActionCommand() + "\n");
                }
            });

            add(m_edit);
            m_edit.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if(self.owner.file != null)
                            Desktop.getDesktop().edit(self.owner.file);
                        else
                            System.out.print("Could not open file.\n");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    System.out.print("Action: " + e.getActionCommand() + "\n");
                }
            });

            add(m_delete);
            m_delete.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Delete file
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

/*
    public void popUpInfo(Component caller, int x, int y){
        m_infoPopup.setEnabled(true);
        m_infoPopup.setVisible(true);
        m_infoPopup.show(caller, x, y);
    }
*/

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

// Note: When you 
/*
	public class InfoPopup extends JPopupMenu{

		private static final long serialVersionUID = -3564833458604078898L;
		
		public SpaceTimeInt space = new SpaceTimeInt();
		
		public boolean active = false;

		public InfoPopup(){
			setSize(200, 200);
			
			add(new JMenuItem("MediaFile::InfoPopup::cat"));
			add(new JMenuItem("dog"));
			// Add this back in when we have the InfoPopup working
//			add(new InfoPanel());
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
		
		/ **
		 * This is the popup menu that should show up when you hover over a mediafile in the Wall
		 * @author Edward
		 *
		 * /
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
				
				m_playOn = Util.loadImgRes("/play1Large.png");
				m_playOff = Util.loadImgRes("/play2Large.png");
				m_stopOn = Util.loadImgRes("/stop1Large.png");
				m_stopOff = Util.loadImgRes("/stop2Large.png");
				
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
*/
}
