package DungeonGeneratorBase;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class EntityInfo {
	public NBTTagCompound entityData;
	public int xCoord;
	public int yCoord;
	public int zCoord;
	public double xPos;
	public double yPos;
	public double zPos;

	public EntityInfo(NBTTagCompound tileEntityCompound) {
		entityData = tileEntityCompound;
		NBTTagList nbttaglist = tileEntityCompound.getTagList("Pos", 6);
		this.xPos = nbttaglist.func_150309_d(0);
		this.yPos = nbttaglist.func_150309_d(1);
		this.zPos = nbttaglist.func_150309_d(2);
		this.xCoord = (int) this.xPos;
		this.yCoord = (int) this.yPos;
		this.zCoord = (int) this.zPos;
	}

	public Entity createAndLoadEntity(World worldObj) {
		return EntityList.createEntityFromNBT(entityData,worldObj);
	}
}
