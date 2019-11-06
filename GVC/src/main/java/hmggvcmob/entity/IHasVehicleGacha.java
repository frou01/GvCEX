package hmggvcmob.entity;

import net.minecraft.tileentity.TileEntity;

public interface IHasVehicleGacha {
	VehicleSpawnGachaOBJ[] getVehicleGacha();
	int getVehicleGacha_rate_sum();
	void setVehicleName(String string);
}
