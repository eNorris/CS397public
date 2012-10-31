package util;

import java.awt.Dimension;

public class Config {
	public final static int SMALL = 0;
	public final static int MED = 1;
	public final static int LARGE = 2;
	
	public int sizeCode = MED;
	public boolean useRenderHint = true;
	public int bufferSpace = 5;
	
	public Dimension getDimension(){
		return dimSize(sizeCode);
	}
	
	public static Dimension dimSize(int sizeCode){
		if(sizeCode == SMALL)
			return new Dimension(64, 64);
		if(sizeCode == MED)
			return new Dimension(128, 128);
		if(sizeCode == LARGE)
			return new Dimension(256, 256);
		System.out.print("WARNING: Config::dimSize(): Illegal sizeCode(" + sizeCode + ")\n\n");
		return new Dimension(0,0);
	}
}
