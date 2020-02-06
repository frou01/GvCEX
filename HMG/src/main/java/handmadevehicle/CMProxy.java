package handmadevehicle;

import cpw.mods.fml.common.FMLCommonHandler;
import handmadeguns.client.render.ModelSetAndData;
import handmadevehicle.command.HMV_CommandReloadparm;
import handmadevehicle.entity.parts.HasLoopSound;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.command.CommandHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static handmadevehicle.HMVehicle.hmv_commandReloadparm;

public class CMProxy {
	Field boundingboxField = null;
	Field modifiersField = null;
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
	public float getZaxis2(){
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
	
	public boolean throttle_BrakeKeyDown(){
		return false;
	}
	
	public boolean leftclick(){
		return false;
	}
	
	public boolean rightclick(){
		return false;
	}
	public boolean air_Brake_click(){
		return false;
	}
	public boolean throttle_up_click(){
		return false;
	}
	public boolean yaw_Left_click(){
		return false;
	}
	public boolean throttle_down_click(){
		return false;
	}
	public boolean yaw_Right_click(){
		return false;
	}
	public boolean zoomclick(){
		return false;
	}
	public boolean flap_click(){
		return false;
	}
	public boolean flare_Smoke_click(){
		return false;
	}
	public boolean gear_Down_Up_click(){
		return false;
	}
	public boolean weapon_Mode_click(){
		return false;
	}

	public boolean allow_Entity_Ride_click(){
		return false;
	}

	public boolean next_Seatclick(){
		return false;
	}
	public boolean previous_Seatclick(){
		return false;
	}
	public boolean changeControlclick(){
		return false;
	}
	public boolean changeEasyControlMode(){
		return false;
	}
	public boolean resetCamrotclick(){
		return false;
	}
	public boolean reloadConfigclick(){
		return false;
	}
	public boolean openGUIKeyDown(){
		return false;
	}
	public boolean iszooming(){
		return false;
	}
	
	
	public static Field nextstepdistance;
	public void initSome(){
		try {
			((CommandHandler)FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager()).registerCommand(hmv_commandReloadparm);
			nextstepdistance = Entity.class.getDeclaredField("field_70150_b");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				nextstepdistance = Entity.class.getDeclaredField("nextStepDistance");
			}catch (Exception ea){
				ea.printStackTrace();
			}
		}
		nextstepdistance.setAccessible(true);
	}
	
	public void setNextstepdistance(Entity instance, int value){
		if(nextstepdistance == null){
			initSome();
		}
		try {
			nextstepdistance.set(instance, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	public int getNextstepdistance(Entity instance){
		if(nextstepdistance == null){
			initSome();
		}
		try {
			return nextstepdistance.getInt(instance);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public void replaceBoundingbox(Entity instance,AxisAlignedBB value){
		if(boundingboxField == null){
			try {
				boundingboxField = Entity.class.getDeclaredField("field_70121_D");
				modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.set(boundingboxField,
						boundingboxField.getModifiers() & ~Modifier.PRIVATE & ~Modifier.FINAL);
			} catch (NoSuchFieldException e) {
				try {
					boundingboxField = Entity.class.getDeclaredField("boundingBox");
					modifiersField = Field.class.getDeclaredField("modifiers");
					modifiersField.setAccessible(true);
					modifiersField.set(boundingboxField,
							boundingboxField.getModifiers() & ~Modifier.PRIVATE & ~Modifier.FINAL);
				} catch (NoSuchFieldException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		try {
			boundingboxField.set(instance, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public ModelSetAndData loadResource_model(String resourceName_model,String resourceName_Texture,float scale){
		return null;
	}
	
	public void setPlayerSeatID(int id){
	}

	public boolean isSneaking(){
		return false;
	}
}
