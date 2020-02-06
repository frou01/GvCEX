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
	public static Field nextstepdistance;
	public void initSome(){
		try {
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
	
	public World getCilentWorld(){
		return null;}
	
	public void registerClientInfo(){}

	public void registerTileEntity() {
//		GameRegistry.registerTileEntity(GVCTileEntityMobSpawner.class, "GVCTile_MobSpawner");
		GameRegistry.registerTileEntity(TileEntityFlag.class, "FlagTile");
		GameRegistry.registerTileEntity(TileEntityMobSpawner_Extend.class, "MobspawnerEX");
	}
	public void setNextstepdistance(Entity instance, int value){
		try {
			nextstepdistance.set(instance, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
