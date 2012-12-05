package gui;

//import java.awt.Graphics;
//
//import javax.swing.JPanel;
//import javax.swing.JPopupMenu;
//
//import util.SpaceTimeInt;

//public class InfoPopup extends JPopupMenu{
//
//	private static final long serialVersionUID = -3564833458604078898L;
//	
//	public SpaceTimeInt space = new SpaceTimeInt();
//	
//	public boolean active = false;
//
//	public InfoPopup(){
//		setSize(200, 200);
//	}
//	
//	public void setVisible(boolean vis){
//		super.setVisible(vis);
//	}
//	
//	public void update(int x, int y){
//		if(active){
//			space.universalUpdate(x, y);
//		}
//	}
//	
//	public void activate(int x, int y){
//		space.wormHole(x, y);
//System.out.print("activating to x = " + x + "\n");
//		active = true;
//		setVisible(true);
//	}
//	
//	public void deactivate(){
//		active = false;
//		setVisible(false);
//	}
//	
//	public void paint(Graphics g){
//		g.fillRect(0, 0, 200, 200);
//	}
//}
