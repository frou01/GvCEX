package hmvehicle;

import handmadeguns.client.audio.BulletSoundHMG;
import hmvehicle.audio.VehicleSound;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class CLProxy extends CMProxy {
	
	@Override
	public void playsoundasVehicle(float maxdist, Entity attached){
		Minecraft.getMinecraft().getSoundHandler().playSound(new VehicleSound(attached,maxdist));
	}
}
