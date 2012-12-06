package util;

import java.awt.AlphaComposite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Util {
	public static Util util = new Util();
	public static String relPath(String relativePath){
		URL resURL = util.getClass().getResource(relativePath);
		if(resURL == null)
			System.out.print("WARNING: Util::relPath(): null URL from '" + relativePath + "'\n");
		String k = resURL.getPath();
		if(k == null){
			System.out.print("WARNING: Util::relPath(): null path\n");
		}
		return k;
	}
	
	public static void drawGradientBackground(JPanel panel, Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		int w = panel.getWidth();
		int h = panel.getHeight();
	
		GradientPaint gp = new GradientPaint(0, 0, Constants.GRADTOPCOLOR, 0, h, Constants.GRADBOTTOMCOLOR);

		g2d.setPaint(gp);
		g2d.fillRect(0, 0, w, h);
	}
	
	
	public static Image loadImgRes(String resPath){
		File file = new File(Util.relPath(resPath));
		try{
			BufferedImage originalImage = ImageIO.read(file);
			int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

			BufferedImage resizedImage;

			if(World.config.useRenderHint){
				resizedImage = resizeImageWithHint(originalImage, type);
			}else{
				resizedImage = resizeImage(originalImage, type);
			}
			return resizedImage;
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
		return null;
	}

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
	

	
}
