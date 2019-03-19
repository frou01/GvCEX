package hmggvcmob.tile;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

public class TileEntityMobSpawner_Extend extends TileEntity {
    private final MobSpawnerBaseLogic_Extend field_145882_a = new MobSpawnerBaseLogic_Extend()
    {
        private static final String __OBFID = "CL_00000361";
        public void func_98267_a(int p_98267_1_)
        {
            TileEntityMobSpawner_Extend.this.worldObj.addBlockEvent(TileEntityMobSpawner_Extend.this.xCoord, TileEntityMobSpawner_Extend.this.yCoord, TileEntityMobSpawner_Extend.this.zCoord, Blocks.mob_spawner, p_98267_1_, 0);
        }
        public World getSpawnerWorld()
        {
            return TileEntityMobSpawner_Extend.this.worldObj;
        }
        public int getSpawnerX()
        {
            return TileEntityMobSpawner_Extend.this.xCoord;
        }
        public int getSpawnerY()
        {
            return TileEntityMobSpawner_Extend.this.yCoord;
        }
        public int getSpawnerZ()
        {
            return TileEntityMobSpawner_Extend.this.zCoord;
        }
        public void setRandomEntity(WeightedRandomMinecart p_98277_1_)
        {
            super.setRandomEntity(p_98277_1_);

            if (this.getSpawnerWorld() != null)
            {
                this.getSpawnerWorld().markBlockForUpdate(TileEntityMobSpawner_Extend.this.xCoord, TileEntityMobSpawner_Extend.this.yCoord, TileEntityMobSpawner_Extend.this.zCoord);
            }
        }
    };
    private static final String __OBFID = "CL_00000360";

    public void readFromNBT(NBTTagCompound p_145839_1_)
    {
        super.readFromNBT(p_145839_1_);
        this.field_145882_a.readFromNBT(p_145839_1_);
    }

    public void writeToNBT(NBTTagCompound p_145841_1_)
    {
        super.writeToNBT(p_145841_1_);
        this.field_145882_a.writeToNBT(p_145841_1_);
    }

    public void updateEntity()
    {
        this.field_145882_a.updateSpawner();
        super.updateEntity();
    }

    /**
     * Overriden in a sign to provide the text.
     */
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        nbttagcompound.removeTag("SpawnPotentials");
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbttagcompound);
    }

    /**
     * Called when a client event is received with the event number and argument, see World.sendClientEvent
     */
    public boolean receiveClientEvent(int p_145842_1_, int p_145842_2_)
    {
        return this.field_145882_a.setDelayToMin(p_145842_1_) ? true : super.receiveClientEvent(p_145842_1_, p_145842_2_);
    }

    public MobSpawnerBaseLogic_Extend func_145881_a()
    {
        return this.field_145882_a;
    }

    public TileEntityMobSpawner_Extend(){

    }
    public TileEntityMobSpawner_Extend(String mobname){
        func_145881_a().setEntityName(mobname);
    }
}
