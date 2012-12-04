package util;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Util {
	public static Util util = new Util();
	public static String relPath(String relativePath){
		String k = util.getClass().getResource(relativePath).getPath();
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
}
