package hmggvcmob.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import hmggvcmob.tile.TileEntityFlag;
import hmggvcmob.tile.TileEntityMobSpawner_Extend;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class CommonSideProxyGVCM {
	static Field nextstepdistance;
	Field boundingboxField = null;
	Field modifiersField = null;
	public void reisterRenderers(){}
	
	public World getCilentWorld(){
		return null;}
	
	public void registerClientInfo(){}

	public void registerTileEntity() {
//		GameRegistry.registerTileEntity(GVCTileEntityMobSpawner.class, "GVCTile_MobSpawner");
		GameRegistry.registerTileEntity(TileEntityFlag.class, "FlagTile");
		GameRegistry.registerTileEntity(TileEntityMobSpawner_Extend.class, "MobspawnerEX");
		//������
		try {
			nextstepdistance = ReflectionHelper.findField(Entity.class,"field_70150_b");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				nextstepdistance = ReflectionHelper.findField(Entity.class,"nextStepDistance");
			}catch (Exception ea){
				ea.printStackTrace();
			}
		}
	}
	public void setNextstepdistance(Entity instance, int value){
		try {
			nextstepdistance.set(instance, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	public int getNextstepdistance(Entity instance){
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
	public boolean reload(){
		return false;
	}
	public boolean reload_Semi(){
		return false;
	}
	
	public boolean jumped(){
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

	public int mcbow(){
		return 1;
	}
	public EntityPlayer getEntityPlayerInstance() {return null;}
	public boolean iszooming(){
		return false;
	}
}
