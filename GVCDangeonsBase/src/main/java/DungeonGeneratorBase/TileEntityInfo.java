package DungeonGeneratorBase;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityInfo {
	public NBTTagCompound tileEntityData;
	public int xCoord;
	public int yCoord;
	public int zCoord;

	public TileEntityInfo(NBTTagCompound tileEntityCompound) {
		tileEntityData = tileEntityCompound;
		this.xCoord = tileEntityCompound.getInteger("x");
		this.yCoord = tileEntityCompound.getInteger("y");
		this.zCoord = tileEntityCompound.getInteger("z");
	}


	public TileEntity createAndLoadEntity()
	{
		return TileEntity.createAndLoadEntity(tileEntityData);
	}
}
