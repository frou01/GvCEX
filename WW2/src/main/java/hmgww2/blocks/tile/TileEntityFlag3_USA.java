package hmgww2.blocks.tile;
 
import hmgww2.Nation;
import hmgww2.mod_GVCWW2;
import hmgww2.entity.EntityUSABase;
import hmgww2.entity.EntityUSA_Fighter;
import hmgww2.entity.EntityUSA_FighterA;
import hmgww2.entity.EntityUSA_S;
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
public class TileEntityFlag3_USA extends TileEntityBase
{
		
	public void updateEntity() {
		this.maxs = mod_GVCWW2.cfg_spawnblock_limit_s;
		this.maxv = mod_GVCWW2.cfg_spawnblock_limit_air;
		this.friend = new EntityUSABase(this.worldObj);
		this.fre = 2;
		this.sorv = false;

		this.spawntime = 600;
		this.spawntimev = 900;
		this.range = 80;
		this.blocklevel = 3;
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
				if(i == 0)
				{
					int ix = par1World.rand.nextInt(10);
					int iz = par1World.rand.nextInt(10);
					EntityUSA_FighterA entityskeleton = new EntityUSA_FighterA(par1World);
			            entityskeleton.setLocationAndAngles(par1+0.5 + ix - 5, par2+12, par3+0.5 + iz - 5, 90F, 0.0F);
			            entityskeleton.setFlagMode(1);
			        if(!this.worldObj.isRemote){
			            par1World.spawnEntityInWorld(entityskeleton);
			        }
				}else {
					int ix = par1World.rand.nextInt(10);
					int iz = par1World.rand.nextInt(10);
					EntityUSA_Fighter entityskeleton = new EntityUSA_Fighter(par1World);
			            entityskeleton.setLocationAndAngles(par1+0.5 + ix - 5, par2+12, par3+0.5 + iz - 5, 90F, 0.0F);
			            entityskeleton.setFlagMode(1);
			        if(!this.worldObj.isRemote){
			            par1World.spawnEntityInWorld(entityskeleton);
			        }
				}
			}
			if(!par1World.isRemote && this.spawn){
				int i = par1World.rand.nextInt(4);
				if(i == 0)
				{
					int ix = par1World.rand.nextInt(10);
					int iz = par1World.rand.nextInt(10);
					EntityUSA_FighterA entityskeleton = new EntityUSA_FighterA(par1World);
			            entityskeleton.setLocationAndAngles(par1+0.5 + ix - 5, par2+2, par3+0.5 + iz - 5, 90F, 0.0F);
			            entityskeleton.setMobMode(1);
			        if(!this.worldObj.isRemote){
			            par1World.spawnEntityInWorld(entityskeleton);
			        }
				}else {
					int ix = par1World.rand.nextInt(10);
					int iz = par1World.rand.nextInt(10);
					EntityUSA_Fighter entityskeleton = new EntityUSA_Fighter(par1World);
			            entityskeleton.setLocationAndAngles(par1+0.5 + ix - 5, par2+2, par3+0.5 + iz - 5, 90F, 0.0F);
			            entityskeleton.setMobMode(1);
			        if(!this.worldObj.isRemote){
			            par1World.spawnEntityInWorld(entityskeleton);
			        }
				}
			}
		}
	
	@Override
	public Nation getnation() {
		return Nation.USA;
	}
}