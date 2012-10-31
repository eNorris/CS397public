package gui;

import javax.swing.JFrame;


public class MainApplication extends JFrame{
	
	private static final long serialVersionUID = 4648172894076113183L;

	MainApplication(){}
	
	public static void main(String[] args){
		System.out.print("Running...\n\n");
		
		JFrame frame = new JFrame();
		
		Core core = new Core();
		frame.getContentPane().add(core);
		
		frame.setJMenuBar(new CoreMenu());
		
		frame.setTitle("title");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setVisible(true);
	}
}
