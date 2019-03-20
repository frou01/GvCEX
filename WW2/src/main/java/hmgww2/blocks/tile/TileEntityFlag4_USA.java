package hmgww2.blocks.tile;
 
import hmgww2.mod_GVCWW2;
import hmgww2.entity.EntityUSABase;
import hmgww2.entity.EntityUSA_S;
import hmgww2.entity.EntityUSA_ShipB;
import hmgww2.entity.EntityUSA_ShipD;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
 
/*
 * TileEntityのクラスです。
 * TileEntityは、Tick毎に特殊な動作をしたり、複雑なモデルを持ったり、
 * NBTを使ってデータを格納したり、色々な用途に使えます。
 * 
 * ただしこのクラス内で行われた処理やデータは基本的にサーバ側にしかないので、
 * 同期処理についてよく考えて実装する必要があります。
 */
public class TileEntityFlag4_USA extends TileEntityBase
{
		
	public void updateEntity() {
		this.maxs = mod_GVCWW2.cfg_spawnblock_limit_s;
		this.maxv = mod_GVCWW2.cfg_spawnblock_limit_ship;
		this.friend = new EntityUSABase(this.worldObj);
		this.fre = 2;
		this.sorv = false;

		this.spawntime = 600;
		this.spawntimev = 900;
		this.range = 80;
		this.blocklevel = 4;
		this.helmet = mod_GVCWW2.armor_usa;

		super.updateEntity();
	}
	protected void SpawnEntity(World par1World, int par1, int par2, int par3, int par4, int par5, int par6) {
		
		if(!par1World.isRemote && this.spawn){
			
			for (int ii = 0; ii < 5; ++ii){
				int ix = par1World.rand.nextInt(10);
				int iz = par1World.rand.nextInt(10);
				
				EntityUSA_S entityskeleton = new EntityUSA_S(par1World);
				entityskeleton.setLocationAndAngles(par1+0.5 + ix - 5, par2+2, par3+0.5 + iz - 5, entityskeleton.renderYawOffset, 0.0F);
	            entityskeleton.setFlagMode(1);
				entityskeleton.addRandomArmor();
	            entityskeleton.setCurrentItemOrArmor(4, new ItemStack(mod_GVCWW2.armor_usa));
	        if(!this.worldObj.isRemote){
	            par1World.spawnEntityInWorld(entityskeleton);
	        }
			}
		}
		
	}
		
		protected void SpawnEntity2(World par1World, int par1, int par2, int par3, int par4, int par5, int par6) {
			
			if(!par1World.isRemote && this.spawn){
				int i = par1World.rand.nextInt(2);
				int ix = par1World.rand.nextInt(20);
				int iz = par1World.rand.nextInt(20);
				if(par1World.getBlock(par1+ix, par2-1, par3+iz) == Blocks.water
						||par1World.getBlock(par1+ix, par2-2, par3+iz) == Blocks.water
						||par1World.getBlock(par1+ix, par2-3, par3+iz) == Blocks.water
						||par1World.getBlock(par1+ix, par2-4, par3+iz) == Blocks.water){
					if(i == 0)
					{
						EntityUSA_ShipB entityskeleton = new EntityUSA_ShipB(par1World);
				            entityskeleton.setLocationAndAngles(par1+0.5 + ix - 5, par2+2, par3+0.5 + iz - 5, entityskeleton.renderYawOffset, 0.0F);
				            entityskeleton.setFlagMode(1);
				        if(!this.worldObj.isRemote){
				            par1World.spawnEntityInWorld(entityskeleton);
				        }
					}else {
						EntityUSA_ShipD entityskeleton = new EntityUSA_ShipD(par1World);
				            entityskeleton.setLocationAndAngles(par1+0.5 + ix - 5, par2+2, par3+0.5 + iz - 5, entityskeleton.renderYawOffset, 0.0F);
				            entityskeleton.setFlagMode(1);
				        if(!this.worldObj.isRemote){
				            par1World.spawnEntityInWorld(entityskeleton);
				        }
					}
				}
			}
			/*if(!par1World.isRemote){
				int i = par1World.rand.nextInt(2);
				int ix = par1World.rand.nextInt(20);
				int iz = par1World.rand.nextInt(20);
				if(par1World.getBlock(par1+ix, par2-1, par3+iz) == Blocks.water
						||par1World.getBlock(par1+ix, par2-2, par3+iz) == Blocks.water
						||par1World.getBlock(par1+ix, par2-3, par3+iz) == Blocks.water
						||par1World.getBlock(par1+ix, par2-4, par3+iz) == Blocks.water){
					if(i == 0)
					{
						EntityUSA_ShipB entityskeleton = new EntityUSA_ShipB(par1World);
				            entityskeleton.setLocationAndAngles(par1+0.5 + ix - 5, par2+2, par3+0.5 + iz - 5, entityskeleton.renderYawOffset, 0.0F);
				            entityskeleton.setMobMode(1);
				        if(!this.worldObj.isRemote){
				            par1World.spawnEntityInWorld(entityskeleton);
				        }
					}else {
						EntityUSA_ShipD entityskeleton = new EntityUSA_ShipD(par1World);
				            entityskeleton.setLocationAndAngles(par1+0.5 + ix - 5, par2+2, par3+0.5 + iz - 5, entityskeleton.renderYawOffset, 0.0F);
				            entityskeleton.setMobMode(1);
				        if(!this.worldObj.isRemote){
				            par1World.spawnEntityInWorld(entityskeleton);
				        }
					}
				}
			}*/
		}
		
}