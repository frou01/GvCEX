package handmadevehicle.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import handmadevehicle.gui.container.VehicleContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class HMVGuiHancler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == 0){
			return new VehicleContainer(player);
		}
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == 0){
			return new VehicleGui(new VehicleContainer(player));
		}
		return null;
	}
}
