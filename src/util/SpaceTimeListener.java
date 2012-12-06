package util;

import dataContainers.MediaFile;

public class SpaceTimeListener extends SpaceTimeInt{
	
	public MediaFile owner = null;
	public boolean frozen = false;
	public boolean stage1 = false;
	public boolean stage2 = false;
	
	public void freeze(){frozen = true;}
	public void unFreeze(){frozen = false;}
	
	public SpaceTimeListener(MediaFile media){
		owner = media;
	}
	
	public void update(int x, int y){
		
//System.out.print("SPListener::update(): \n");
		if(frozen)
			return;
		this.universalUpdate(x, y);
	//	this.wor
		if(updateStages())
			launchInfoPop();
	}
	
	public boolean updateStages(){
//System.out.print("SPListener::updateStages(): \n");
		if(owner.isInBounds(cx, cy)){
			stage1 = true;
//System.out.print("State 1 = true ");
		}
		else{
			return stage2 = stage1 = false;
		}
		if(t > 0.5){
//System.out.print("Stage 2 = true ");
			stage2 = true;
		}
		else
			return stage2 = false;
//System.out.print("vx = " + vx + "  vy = " + vy + "\n");
		// FIXME - Come up with something better than this!
		return vx <0.2 && vy < 0.2;
	}
	
	public void launchInfoPop(){
		// MediaFile -> MediaLibrary -> Wall
		owner.popUpInfo(owner.owner.owner, cx, cy);
		freeze();
	}
}
