package util;

import dataContainers.MediaFile;

public class SpaceTimeListener extends SpaceTimeInt{
	
// This class should be uncommented when the InfoPopUp is working. This is the class that controls how it behaves
/*
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
		
		if(frozen)
			return;
		this.universalUpdate(x, y);
		if(updateStages())
			launchInfoPop();
	}
	
	public boolean updateStages(){
		if(owner.isInBounds(cx, cy)){
			stage1 = true;
		}
		else{
			return stage2 = stage1 = false;
		}
		if(t > 0.5){
			stage2 = true;
		}
		else
			return stage2 = false;
		// FIXME - Come up with something better than this!
		return vx <0.2 && vy < 0.2;
	}
	
	public void launchInfoPop(){
		// MediaFile -> MediaLibrary -> Wall
		owner.popUpInfo(owner.owner.owner, cx, cy);
		freeze();
	}
*/
}
