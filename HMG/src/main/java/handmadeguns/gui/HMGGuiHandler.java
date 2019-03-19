package handmadeguns.gui;
 
import java.util.List;

import cpw.mods.fml.common.network.IGuiHandler;
import handmadeguns.entity.HMGEntityItemMount;
import handmadeguns.entity.HMGEntityItemMount2;
import handmadeguns.inventory.ContainerHolder;
import handmadeguns.tile.TileMounter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
 
public class HMGGuiHandler implements IGuiHandler
{
    /*
        ServerでGUIが開かれたときに呼ばれる
        通常はContainerを生成する。
     */
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
    	ItemStack itemstack = player.getCurrentEquippedItem();
        if(ID == 0){
            return new HMGContainerInventoryItem(player.inventory, itemstack);
        }
        if(ID == 1){
        	HMGEntityItemMount entity = null;
            List list = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, 
            		player.boundingBox.addCoord(player.motionX, player.motionY, player.motionZ).expand(2D, 2D, 2D));
            for (int j = 0; j < list.size(); ++j)
            {
                Entity entity1 = (Entity)list.get(j);

                if (entity1.canBeCollidedWith() && entity1 instanceof HMGEntityItemMount)
                {
                	NBTTagCompound nbt = player.getEntityData();
                	int rackid = nbt.getInteger("rackid");
                	if(rackid == entity1.getEntityId()){
                	entity = (HMGEntityItemMount) entity1;
                	nbt.setInteger("rackid", 0);
                	}
                }
            }
            if(entity != null){
            return new HMGContainerInventoryItemMount(player.inventory, itemstack, entity);
            }
        }
        if(ID == 2){
        	HMGEntityItemMount2 entity = null;
            List list = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, 
            		player.boundingBox.addCoord(player.motionX, player.motionY, player.motionZ).expand(2D, 2D, 2D));
            for (int j = 0; j < list.size(); ++j)
            {
                Entity entity1 = (Entity)list.get(j);

                if (entity1.canBeCollidedWith() && entity1 instanceof HMGEntityItemMount2)
                {
                	NBTTagCompound nbt = player.getEntityData();
                	int rackid = nbt.getInteger("rackid");
                	if(rackid == entity1.getEntityId()){
                	entity = (HMGEntityItemMount2) entity1;
                	nbt.setInteger("rackid", 0);
                	}
                }
            }
            if(entity != null){
            return new HMGContainerInventoryItemMount2(player.inventory, itemstack, entity);
            }
        }
        if(ID == 3){
            TileEntity tile = null;
            tile = world.getTileEntity(x,y,z);
            if(tile instanceof TileMounter){
                return new ContainerHolder(player.inventory, (TileMounter)tile);
            }
        }
        return null;
    }
 
    /*
        ClientでGUIが開かれたときに呼ばれる
        通常はGUIを生成する
     */
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
    	ItemStack itemstack = player.getCurrentEquippedItem();
        if(ID == 0){
            return new HMGGuiInventoryItem(player.inventory, itemstack);
        }
        if(ID == 1){
        	HMGEntityItemMount entity = null;
            List list = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, 
            		player.boundingBox.addCoord(player.motionX, player.motionY, player.motionZ).expand(2D, 2D, 2D));
            for (int j = 0; j < list.size(); ++j)
            {
                Entity entity1 = (Entity)list.get(j);

                if (entity1.canBeCollidedWith() && entity1 instanceof HMGEntityItemMount)
                {
                	NBTTagCompound nbt = player.getEntityData();
                	int rackid = nbt.getInteger("rackid");
                	if(rackid == entity1.getEntityId()){
                	entity = (HMGEntityItemMount) entity1;
                	nbt.setInteger("rackid", 0);
                	}
                }
            }
            if(entity != null){
            return new HMGGuiInventoryItemMount(player.inventory, itemstack, entity);
            }
        }
        if(ID == 2){
        	HMGEntityItemMount2 entity = null;
            List list = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, 
            		player.boundingBox.addCoord(player.motionX, player.motionY, player.motionZ).expand(2D, 2D, 2D));
            for (int j = 0; j < list.size(); ++j)
            {
                Entity entity1 = (Entity)list.get(j);

                if (entity1.canBeCollidedWith() && entity1 instanceof HMGEntityItemMount2)
                {
                	NBTTagCompound nbt = player.getEntityData();
                	int rackid = nbt.getInteger("rackid");
                	if(rackid == entity1.getEntityId()){
                	entity = (HMGEntityItemMount2) entity1;
                	nbt.setInteger("rackid", 0);
                	}
                }
            }
            if(entity != null){
            return new HMGGuiInventoryItemMount2(player.inventory, itemstack, entity);
            }
        }
        if(ID == 3){
        	TileEntity tile = null;
            tile = world.getTileEntity(x,y,z);
            if(tile instanceof TileMounter){
            return new HMGGuiInventoryItemMountnew(player.inventory, (TileMounter)tile);
            }
        }
        return null;
    }
}