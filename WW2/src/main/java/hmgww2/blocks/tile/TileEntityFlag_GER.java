package hmgww2.blocks.tile;
 
import hmgww2.mod_GVCWW2;
import hmgww2.entity.EntityGERBase;
import hmgww2.entity.EntityGER_S;
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
public class TileEntityFlag_GER extends TileEntityBase
{
		
	public void updateEntity()
    {
        this.maxs = mod_GVCWW2.cfg_spawnblock_limit_s;
        this.maxv =  mod_GVCWW2.cfg_spawnblock_limit_tank;
        this.friend = new EntityGERBase(this.worldObj);
        this.fre = 3;
        this.sorv = true;
        
        this.spawntime = 600;
        this.range = 15;
        this.blocklevel = 1;
        this.helmet = mod_GVCWW2.armor_ger;
        
        super.updateEntity();
    }
	protected void SpawnEntity2(World par1World, int par1, int par2, int par3, int par4, int par5, int par6) {
		
	}
        protected void SpawnEntity(World par1World, int par1, int par2, int par3, int par4, int par5, int par6) {
			
			if(!par1World.isRemote && this.spawn){
				
				for (int ii = 0; ii < 5; ++ii){
					int ix = par1World.rand.nextInt(10);
					int iz = par1World.rand.nextInt(10);
					
					EntityGER_S entityskeleton = new EntityGER_S(par1World);
			            entityskeleton.setLocationAndAngles(par1+0.5 + ix - 5, par2+2, par3+0.5 + iz - 5, entityskeleton.renderYawOffset, 0.0F);
			            entityskeleton.setFlagMode(1);
			            int iii = par1World.rand.nextInt(10);
			            if(iii == 0){
			            	entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_mp40));
			            }else if(iii == 1){
			            	entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_mp40));
			            }else if(iii == 2){
			            	entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_mp40));
			            }else if(iii == 3){
			            	entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_fg42));
			            }else if(iii == 4){
			            	entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_kar98sr));
			            }else if(iii == 5){
			            	entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_rpzb54));
			            }else
			            {
			            	entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_kar98));
			            }
			            entityskeleton.setCurrentItemOrArmor(4, new ItemStack(mod_GVCWW2.armor_ger));
			        if(!this.worldObj.isRemote){
			            par1World.spawnEntityInWorld(entityskeleton);
			        }
					
				}
			}
			
		}
}