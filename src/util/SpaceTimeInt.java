package util;

import java.util.ArrayList;

/**
 * Class for tracking changes in space (2D) and time. Generally used as follows:<br>
 * call wormhole(x,y) to reinitialize the SpaceTimeInt<br>
 * call updateUniverse(newX, newY) any number of times to add coordinates<br>
 * the relative change since clicking the mouse will be stored in ix and iy <br>
 */
public class SpaceTimeInt {
	
	// FIXME - add another variable tx and ty to be total of ix and iy
	
	/**
	 * Current x and y positions in pixels
	 */
	public int cx = 0, cy = 0;
	
	/**
	 * x and y positions in pixels during last mouse movement
	 */
	public int lx = 0, ly = 0;
	
	/**
	 * (cx - lx) and (cy - ly) respectively <br>
	 * The change in mouse position since the last mouse movement
	 */
	public int dx = 0, dy = 0;
	
	/**
	 * sum of all dx and dy respectively since the last spacial collapse<br>
	 * <b>Currently this implementation does NOT reset ix and iy. They are preserved for the
	 * duration of the program. This should be reimplemented later</b>
	 */
	public int ix = 0, iy = 0;
	
	/**
	 * (dx/dt) and (dy/dt) respectively
	 * velocity components of the mouse
	 */
	public float vx = 0.0f, vy = 0.0f;
	
	/**
	 * current time, last time of mouse movement, and time since
	 */
	public long ct = 0, lt = 0, dt = 0;
	
	/**
	 * actual time in seconds since triggered
	 */
	public double t = 0.0;
	
	/**
	 * Declares an empty SpaceTimeInt
	 */
	public SpaceTimeInt(){}
	
	/**
	 * Declares a fully qualified SpaceTimeInt (except for t = 0.0)
	 * @param cx current x position
	 * @param cy current y position
	 * @param lx last x position
	 * @param ly last y position
	 * @param dx change in x position
	 * @param dy change in y position
	 * @param vx x velocity
	 * @param vy y velocity
	 * @param ct current time
	 * @param lt last time
	 * @param dt change in time
	 * @param t time (sec) since trigger
	 */
	public SpaceTimeInt(int cx, int cy, int lx, int ly, int dx, int dy, int ix, int iy,
			float vx, float vy, long ct, long lt, long dt, double t){
		this.cx = cx; this.cy = cy;
		this.lx = lx; this.ly = ly;
		this.dx = dx; this.dy = dy;
		this.ix = ix; this.iy = iy;
		this.vx = vx; this.vy = vy;
		this.ct = ct; this.lt = lt; this.dt = dt;
		this.t = t;
	}
	
	/**
	 * An array of old SpaceTimeInts that can be used to build a path
	 */
	public ArrayList<SpaceTimeInt> history = new ArrayList<SpaceTimeInt>();
	
	/**
	 * reinitializes all values to zero, clears history, and updates ct
	 * @return An empty SpaceTimeInt
	 */
	public SpaceTimeInt bigBang(){
		temporalCollapse();
		spacialCollapse();
		history.clear();
		ct = System.currentTimeMillis();
		t = 0.0;
		return this;
	}
	
	/**
	 * reinitializes all values to zero, clears history, and updates ct, and sets cx and cy to the specified values
	 * @param x current x position to jump to
	 * @param y current y position to jump to
	 * @return An empty SpaceTimeInt at the coordinates (x,y)
	 */
	public SpaceTimeInt wormHole(int x, int y){
		bigBang();
		cx = x; cy = y;
		return this;
	}
	
	/**
	 * resets time to 0
	 * @return The calling SpaceTimeInt after clearing ct, lt, dt, and t
	 */
	public SpaceTimeInt temporalCollapse(){
		ct = lt = dt = 0;
		t = 0.0;
		return this;
	}
	
	/**
	 * resets space to 0
	 * @return The calling SpaceTimeInt after clearing cx, cy, lx, ly, dx, dy, vx, and vy
	 */
	public SpaceTimeInt spacialCollapse(){
		// FIXME - Find a better way to do this than not resetting ix and iy (implement sum(ix))
		cx = cy = lx = ly = dx = dy = 0;//ix = iy = 0;
		vx = vy = 0.0f;
		return this;
	}
	
	/**
	 * Updates time (does NOT record history)
	 * @return The calling SpaceTimeInt after having time updated
	 */
	public SpaceTimeInt temporalUpdate(){
		lt = ct;
		ct = System.currentTimeMillis();
		dt = ct - lt;
		t += ((double) dt)/1000.0;
		return this;
	}
	
	/**
	 * Updates space (does NOT record history)
	 * @param x the new x position
	 * @param y the new y position
	 * @return
	 */
	public SpaceTimeInt spacialUpdate(int x, int y){
		lx = cx;
		ly = cy;
		cx = x;
		cy = y;
		dx = cx - lx;
		dy = cy - ly;
		ix += dx;
		iy += dy;
		if(dt != 0){
			vx = dx / (float)dt;
			vy = dy / (float)dt;
		} else {
			System.out.print("WARNING: SpaceTimeInt::spacialUpdate(): Divide by zero!\n\n");
			vx = vy = Float.POSITIVE_INFINITY;
		}
		return this;
	}
	
	/**
	 * Updates both space and time
	 * @param x the new x position
	 * @param y the new y position
	 * @return The calling object after all updates are made
	 */
	public SpaceTimeInt universalUpdate(int x, int y){
		temporalUpdate();
		return spacialUpdate(x, y);
	}
	
	/**
	 * Adds an instance of the calling object to its own history
	 */
	public void chronicle(){
		history.add(instantiate());
	}
	
	/**
	 * Creates an identical copy of the calling object omitting its history
	 * @return An instance of the calling object with an empty history
	 */
	public SpaceTimeInt instantiate(){
		return new SpaceTimeInt(cx, cy, lx, ly, dx, dy, ix, iy, vx, vy, ct, lt, dt, t);
	}
	
	/**
	 * Gets a old instance of the calling object from its history
	 * @param index the index of the object to return in history
	 * @return The SpaceTimeInt corresponding to this.history[index]
	 */
	public SpaceTimeInt reflect(int index){
		return history.get(index);
	}
	
	/**
	 * Formats a SpaceTimeInt for output to the screen
	 */
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.ensureCapacity(100);
		
		builder.append("[(" + cx + ", " + cy + ") <- (" + lx + ", " + ly + ") ~ (");
		builder.append(dx + ", " + dy + " $ " + ix + ", " + iy + ") @ (" + vx + ", " + vy + ") : <");
		builder.append(ct + " <- " + lt + " ~ " + dt + " => " + t + ">]");
		
		return builder.toString();
	}
	
}
