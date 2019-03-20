package hmgww2.blocks.tile;
 
import java.util.List;

import gvclib.entity.EntityBases;
import hmgww2.entity.EntityUSSRBase;
import hmgww2.mod_GVCWW2;
import hmgww2.entity.EntityGERBase;
import hmgww2.entity.EntityJPNBase;
import hmgww2.entity.EntityUSABase;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
 
/*
 * TileEntityのクラスです。
 * TileEntityは、Tick毎に特殊な動作をしたり、複雑なモデルを持ったり、
 * NBTを使ってデータを格納したり、色々な用途に使えます。
 * 
 * ただしこのクラス内で行われた処理やデータは基本的にサーバ側にしかないので、
 * 同期処理についてよく考えて実装する必要があります。
 */
public abstract class TileEntityBase extends TileEntity
{
	
	
	private int invasionleve;
	private int invasion;
	public boolean spawn = true;
	
	//public int mobkazu;
    //public int soldierkazu;
    //public int vkazu;
	
    public int ticks;
    public int ticks2;
    public EntityBases en;
    public EntityBases friend;
    public int fre = 1;
    
    public int range = 15;
    public boolean sorv = true;
    public int maxs = 10;
    public int maxv = 3;
    public int spawntime = 600;
    public int spawntimev = 600;
    public Item helmet = null;
    
    public int blocklevel = 1;
    
    
	 public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	    {
	        super.readFromNBT(par1NBTTagCompound);
	        this.invasionleve = par1NBTTagCompound.getInteger("InvasionLevel");
	        this.invasion = par1NBTTagCompound.getInteger("InvasionSet");
	        this.spawn = par1NBTTagCompound.getBoolean("SpawnSoldier");
	    }
	
	 public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	    {
	        super.writeToNBT(par1NBTTagCompound);
	        par1NBTTagCompound.setInteger("InvasionLevel", getInvasionLevel());
	        par1NBTTagCompound.setInteger("InvasionSet", getInvasionSet());
	        par1NBTTagCompound.setBoolean("SpawnSoldier", spawn);
	    }
	    
	 /*
	     * パケットの送信・受信処理。
	     * カスタムパケットは使わず、バニラのパケット送受信処理を使用。
	     */
	 @Override
		public Packet getDescriptionPacket() {
	        NBTTagCompound nbtTagCompound = new NBTTagCompound();
	        this.writeToNBT(nbtTagCompound);
	        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTagCompound);
		}
	 
		@Override
	    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
	        this.readFromNBT(pkt.func_148857_g());
	    }/**/
	 
		
		public void updateEntity()
	    {
	        super.updateEntity();
	        int mobkazu = 0;
	        int soldierkazu = 0;
	         int vkazu = 0;
	        
	        ++ticks;
	        ++ticks2;
	        Block block = this.worldObj.getBlock(xCoord, yCoord, zCoord);
	        int k = xCoord;
	        int l = yCoord;
	        int i = zCoord;
	        AxisAlignedBB axisalignedbb2 = AxisAlignedBB.getBoundingBox(
	        		(double)(k), (double)(l), (double)(i), 
	        		(double)(k), (double)(l+1), (double)(i))
	        		.expand(50, 50, 50);
	        Entity entity2 = null;
	        List llist2 = this.worldObj.getEntitiesWithinAABBExcludingEntity(entity2, axisalignedbb2);
	        int enkazu = 0;
	        if(llist2!=null){
	            for (int lj = 0; lj < llist2.size(); lj++) {
	            	
	            	Entity entity1 = (Entity)llist2.get(lj);
	            	if (entity1.canBeCollidedWith() && entity1 != null)
	                {
	            		if(entity1 instanceof EntityLivingBase){
	            			++enkazu;
	            		}
	                }
	            }
	        }
	        
	        
	        AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(
	        		(double)(k), (double)(l), (double)(i), 
	        		(double)(k), (double)(l+1), (double)(i))
	        		.expand(range, range, range);
	        Entity entity = null;
	        List llist = this.worldObj.getEntitiesWithinAABBExcludingEntity(entity, axisalignedbb);
	        if(llist!=null){
	            for (int lj = 0; lj < llist.size(); lj++) {
	            	
	            	Entity entity1 = (Entity)llist.get(lj);
	            	if (entity1.canBeCollidedWith())
	                {
	            		
	            		if(fre == 1 && entity1 != null){
	            			if(entity1 instanceof EntityJPNBase){
	            				++soldierkazu;
		            			EntityBases en = (EntityBases) entity1;
		            			if(en.vehicle){
		            				++vkazu;
		            			}
	            			}else if(entity1 instanceof EntityBases && entity1 != null){
		                    	++mobkazu;
		                    	this.en = (EntityBases) entity1;
		                    }
	            		}else if(fre == 2 && entity1 != null){
	            			if(entity1 instanceof EntityUSABase){
	            				++soldierkazu;
		            			EntityBases en = (EntityBases) entity1;
		            			if(en.vehicle){
		            				++vkazu;
		            			}
	            			}else if(entity1 instanceof EntityBases && entity1 != null){
		                    	++mobkazu;
		                    	this.en = (EntityBases) entity1;
		                    }
	            		}else if(fre == 3 && entity1 != null){
	            			if(entity1 instanceof EntityGERBase){
	            				++soldierkazu;
		            			EntityBases en = (EntityBases) entity1;
		            			if(en.vehicle){
		            				++vkazu;
		            			}
	            			}else if(entity1 instanceof EntityBases && entity1 != null){
		                    	++mobkazu;
		                    	this.en = (EntityBases) entity1;
		                    }
	            		}else if(fre == 4 && entity1 != null){
	            			if(entity1 instanceof EntityUSSRBase){
	            				++soldierkazu;
		            			EntityBases en = (EntityBases) entity1;
		            			if(en.vehicle){
		            				++vkazu;
		            			}
	            			}else if(entity1 instanceof EntityBases && entity1 != null){
		                    	++mobkazu;
		                    	this.en = (EntityBases) entity1;
		                    }
	            		}
	            		
	            		if (entity instanceof EntityPlayer) {//player
	            			EntityPlayer entityplayer = (EntityPlayer) entity;
	            			if (entityplayer.getEquipmentInSlot(4) != null && (entityplayer.getEquipmentInSlot(4).getItem() != null)) {
	            				if (entityplayer.getEquipmentInSlot(4) != null
	            						&& (entityplayer.getEquipmentInSlot(4).getItem() == helmet)) {
	            					++soldierkazu;
	            				}
	            				if (entityplayer.getEquipmentInSlot(4) != null
	            						&& (entityplayer.getEquipmentInSlot(4).getItem() == Items.golden_helmet)) {
	            				} else {
	            					//++mobkazu;
	            				}
	            			}
	            		}//player
	                }
	            }
	        }
	        
	        if(enkazu < mod_GVCWW2.cfg_spawnblock_limit){
	        	
	        		if(sorv){
	        			if(ticks >= spawntime){
	        				if(soldierkazu <= maxs){
				        		{
									int x = 0;
									int y = 0;
									int z = 0;
									SpawnEntity(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.xCoord + x - 128, this.yCoord + y - 32, this.zCoord + z - 128);
						            ticks = 0;
								}
				        	}else{
				        		ticks = 0;
				        	}
	        			}
		        	}else{
		        		if(ticks >= spawntime){
	        				if(soldierkazu <= maxs){
				        		{
									int x = 0;
									int y = 0;
									int z = 0;
									SpawnEntity(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.xCoord + x - 128, this.yCoord + y - 32, this.zCoord + z - 128);
						            ticks = 0;
								}
				        	}else{
				        		ticks = 0;
				        	}
	        			}
		        		if(ticks2 >= spawntimev){
		        			if(vkazu <= maxv && soldierkazu <= maxs){
				        		{
									int x = 0;
									int y = 0;
									int z = 0;
									SpawnEntity2(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.xCoord + x - 128, this.yCoord + y - 32, this.zCoord + z - 128);
						            ticks2 = 0;
								}
				        	}else{
				        		ticks2 = 0;
				        	}
		        		}
		        	}
	        }
	        
	        //if(!this.worldObj.isRemote)
	        {
	        if(soldierkazu > mobkazu){
	        	//--invasionlevel;
	        	this.setInvasionLevel(this.getInvasionLevel() - 1);
	        	if(this.getInvasionSet() >= -1){
	        		this.setInvasionSet(getInvasionSet() - 1);
	        	}
	        }else if(mobkazu > soldierkazu){
	        	this.setInvasionLevel(this.getInvasionLevel() + 1);
	        	this.setInvasionSet(getInvasionSet() + 1);
	        }else{
	        	this.setInvasionLevel(0);
	        }
	        if(this.getInvasionSet() >= 400){
	        	this.setInvasionLevel(0);
	        	this.setInvasionSet(0);
	        	ticks = 0;
	        	ticks2 = 0;
	        	if(blocklevel == 1){
	        		if(this.en.flag != null)
		        	{
		        		this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, this.en.flag, 0, 2);
		        	}
	        	}else if(blocklevel == 2){
	        		if(this.en.flag2 != null)
		        	{
		        		this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, this.en.flag2, 0, 2);
		        	}
	        	}else if(blocklevel == 3){
	        		if(this.en.flag3 != null)
		        	{
		        		this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, this.en.flag3, 0, 2);
		        	}
	        	}else if(blocklevel == 3){
	        		if(this.en.flag4 != null)
		        	{
		        		this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, this.en.flag4, 0, 2);
		        	}
	        	}
	        	
	        	//this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, mod_MCWars_BF.flag_green, 0, 2);
	    	}
	        
	        soldierkazu = 0;
	    	mobkazu = 0;
	        }
	        
		}
		protected abstract void SpawnEntity(World par1World, int par1, int par2, int par3, int par4, int par5, int par6);
			 
		protected abstract void SpawnEntity2(World par1World, int par1, int par2, int par3, int par4, int par5, int par6);
		
		
	 public int getInvasionSet() {
		return invasion;
	}

	public int getInvasionLevel() {
		return invasionleve;
	}
	
	public void setInvasionSet(int i) {
		this.invasion = i;
	}
	
	public void setInvasionLevel(int i) {
		this.invasionleve = i;
	}
	
	
}