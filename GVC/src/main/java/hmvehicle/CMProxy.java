package hmvehicle;

import handmadeguns.client.audio.BulletSoundHMG;
import hmvehicle.entity.parts.HasLoopSound;
import hmvehicle.entity.parts.turrets.TurretObj;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.io.File;

public class CMProxy {
	public EntityPlayer getEntityPlayerInstance() {return null;}
	
	public File ProxyFile(){
		return new File(".");
	}
	
	public void playsoundasVehicle(float maxdist, Entity attached){
	
	}
	public void playsoundasTurret(float maxdist, TurretObj attached){
	
	}
	public void playsoundasVehicle_noRepeat(String name, float maxdist, Entity attached, HasLoopSound hasLoopSound,int time){
	
	}
	public boolean hasStick(){
		return false;
	}
	
	public float getXaxis(){
		return 0;
	}
	public float getYaxis(){
		return 0;
	}
	public float getZaxis(){
		return 0;
	}
	
	public boolean pitchUp(){
		return false;
	}
	public boolean pitchDown() {
		return false;
	}
	public boolean rollRight(){
		return false;
	}
	public boolean rollLeft(){
		return false;
	}
	public boolean reload(){
		return false;
	}
	
	public boolean reload_Semi(){
		return false;
	}
	
	public boolean spaceKeyDown(){
		return false;
	}
	
	public boolean leftclick(){
		return false;
	}
	public boolean leftclickreplacer(){
		return false;
	}
	
	public boolean rightclick(){
		return false;
	}
	public boolean xclick(){
		return false;
	}
	public boolean wclick(){
		return false;
	}
	public boolean aclick(){
		return false;
	}
	public boolean sclick(){
		return false;
	}
	public boolean dclick(){
		return false;
	}
	public boolean zoomclick(){
		return false;
	}
	public boolean fclick(){
		return false;
	}
	public boolean yclick(){
		return false;
	}
	public boolean hclick(){
		return false;
	}
	public boolean iszooming(){
		return false;
	}
}
