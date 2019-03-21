package hmgww2.blocks.tile;

import hmgww2.Nation;
import hmgww2.entity.*;
import hmgww2.mod_GVCWW2;
import hmgww2.entity.EntityUSSR_TankSPG;
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
public class TileEntityFlag2_RUS extends TileEntityBase
{
	
	public void updateEntity() {
		this.maxs = mod_GVCWW2.cfg_spawnblock_limit_s;
		this.maxv = mod_GVCWW2.cfg_spawnblock_limit_tank;
		this.friend = new EntityUSSRBase(this.worldObj);
		this.fre = 4;
		this.sorv = false;
		
		this.spawntime = 600;
		this.spawntimev = 900;
		this.range = 30;
		this.blocklevel = 2;
		this.helmet = mod_GVCWW2.armor_rus;
		
		super.updateEntity();
	}
	protected void SpawnEntity(World par1World, int par1, int par2, int par3, int par4, int par5, int par6) {
		
		if(!par1World.isRemote && this.spawn){
			
			for (int ii = 0; ii < 5; ++ii){
				int ix = par1World.rand.nextInt(10);
				int iz = par1World.rand.nextInt(10);
				
				EntityUSSR_S entityskeleton = new EntityUSSR_S(par1World);
				entityskeleton.setLocationAndAngles(par1+0.5 + ix - 5, par2+2, par3+0.5 + iz - 5, entityskeleton.renderYawOffset, 0.0F);
				entityskeleton.setFlagMode(1);
				entityskeleton.addRandomArmor();
				if(!this.worldObj.isRemote){
					par1World.spawnEntityInWorld(entityskeleton);
				}
				
			}
		}
		
	}
	
	protected void SpawnEntity2(World par1World, int par1, int par2, int par3, int par4, int par5, int par6) {
		
		if(!par1World.isRemote && this.spawn){
			int i = par1World.rand.nextInt(5);
			if(i == 0)
			{
				int ix = par1World.rand.nextInt(10);
				int iz = par1World.rand.nextInt(10);
				EntityUSSR_TankAA entityskeleton = new EntityUSSR_TankAA(par1World);
				entityskeleton.setLocationAndAngles(par1+0.5 + ix - 5, par2+2, par3+0.5 + iz - 5, entityskeleton.renderYawOffset, 0.0F);
				entityskeleton.setFlagMode(1);
				if(!this.worldObj.isRemote){
					par1World.spawnEntityInWorld(entityskeleton);
				}
			}else if(i == 1){
				int ix = par1World.rand.nextInt(10);
				int iz = par1World.rand.nextInt(10);
				EntityUSSR_TankSPG entityskeleton = new EntityUSSR_TankSPG(par1World);
				entityskeleton.setLocationAndAngles(par1+0.5 + ix - 5, par2+2, par3+0.5 + iz - 5, entityskeleton.renderYawOffset, 0.0F);
				entityskeleton.setFlagMode(1);
				if(!this.worldObj.isRemote){
					par1World.spawnEntityInWorld(entityskeleton);
				}
			}else if(i == 2){
				int ix = par1World.rand.nextInt(10);
				int iz = par1World.rand.nextInt(10);
				EntityUSSR_TankH entityskeleton = new EntityUSSR_TankH(par1World);
				entityskeleton.setLocationAndAngles(par1+0.5 + ix - 5, par2+2, par3+0.5 + iz - 5, entityskeleton.renderYawOffset, 0.0F);
				entityskeleton.setFlagMode(1);
				if(!this.worldObj.isRemote){
					par1World.spawnEntityInWorld(entityskeleton);
				}
			}else {
				int ix = par1World.rand.nextInt(10);
				int iz = par1World.rand.nextInt(10);
				EntityUSSR_Tank entityskeleton = new EntityUSSR_Tank(par1World);
				entityskeleton.setLocationAndAngles(par1+0.5 + ix - 5, par2+2, par3+0.5 + iz - 5, entityskeleton.renderYawOffset, 0.0F);
				entityskeleton.setFlagMode(1);
				if(!this.worldObj.isRemote){
					par1World.spawnEntityInWorld(entityskeleton);
				}
			}
		}
		if(!par1World.isRemote && this.spawn){
			int i = par1World.rand.nextInt(5);
			if(i == 0)
			{
				int ix = par1World.rand.nextInt(10);
				int iz = par1World.rand.nextInt(10);
				EntityUSSR_TankAA entityskeleton = new EntityUSSR_TankAA(par1World);
				entityskeleton.setLocationAndAngles(par1+0.5 + ix - 5, par2+2, par3+0.5 + iz - 5, entityskeleton.renderYawOffset, 0.0F);
				entityskeleton.setMobMode(1);
				if(!this.worldObj.isRemote){
					par1World.spawnEntityInWorld(entityskeleton);
				}
			}else if(i == 1){
				int ix = par1World.rand.nextInt(10);
				int iz = par1World.rand.nextInt(10);
				EntityUSSR_TankSPG entityskeleton = new EntityUSSR_TankSPG(par1World);
				entityskeleton.setLocationAndAngles(par1+0.5 + ix - 5, par2+2, par3+0.5 + iz - 5, entityskeleton.renderYawOffset, 0.0F);
				entityskeleton.setMobMode(1);
				if(!this.worldObj.isRemote){
					par1World.spawnEntityInWorld(entityskeleton);
				}
			}else if(i == 2){
				int ix = par1World.rand.nextInt(10);
				int iz = par1World.rand.nextInt(10);
				EntityUSSR_TankH entityskeleton = new EntityUSSR_TankH(par1World);
				entityskeleton.setLocationAndAngles(par1+0.5 + ix - 5, par2+2, par3+0.5 + iz - 5, entityskeleton.renderYawOffset, 0.0F);
				entityskeleton.setMobMode(1);
				if(!this.worldObj.isRemote){
					par1World.spawnEntityInWorld(entityskeleton);
				}
			}else {
				int ix = par1World.rand.nextInt(10);
				int iz = par1World.rand.nextInt(10);
				EntityUSSR_Tank entityskeleton = new EntityUSSR_Tank(par1World);
				entityskeleton.setLocationAndAngles(par1+0.5 + ix - 5, par2+2, par3+0.5 + iz - 5, entityskeleton.renderYawOffset, 0.0F);
				entityskeleton.setMobMode(1);
				if(!this.worldObj.isRemote){
					par1World.spawnEntityInWorld(entityskeleton);
				}
			}
		}
	}
	
	@Override
	public Nation getnation() {
		return Nation.USSR;
	}
}