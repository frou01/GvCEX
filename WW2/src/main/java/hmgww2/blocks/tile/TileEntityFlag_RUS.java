package hmgww2.blocks.tile;

import hmgww2.Nation;
import hmgww2.entity.EntityUSSRBase;
import hmgww2.entity.EntityUSSR_S;
import hmgww2.mod_GVCWW2;
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
public class TileEntityFlag_RUS extends TileEntityBase
{
	
	public void updateEntity()
	{
		this.maxs = mod_GVCWW2.cfg_spawnblock_limit_s;
		this.maxv =  mod_GVCWW2.cfg_spawnblock_limit_tank;
		this.friend = new EntityUSSRBase(this.worldObj);
		this.fre = 4;
		this.sorv = true;
		
		this.spawntime = 600;
		this.range = 15;
		this.blocklevel = 1;
		this.helmet = mod_GVCWW2.armor_rus;
		
		super.updateEntity();
	}
	protected void SpawnEntity2(World par1World, int par1, int par2, int par3, int par4, int par5, int par6) {
	
	}
	
	@Override
	public Nation getnation() {
		return Nation.USSR;
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
				entityskeleton.setCurrentItemOrArmor(4, new ItemStack(mod_GVCWW2.armor_rus));
				if(!this.worldObj.isRemote){
					par1World.spawnEntityInWorld(entityskeleton);
				}
				
			}
		}
		
	}
}