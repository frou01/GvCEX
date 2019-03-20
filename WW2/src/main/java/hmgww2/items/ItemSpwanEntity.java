package hmgww2.items;

import hmgww2.mod_GVCWW2;
import hmgww2.entity.EntityGER_Fighter;
import hmgww2.entity.EntityGER_FighterA;
import hmgww2.entity.EntityGER_S;
import hmgww2.entity.EntityGER_Tank;
import hmgww2.entity.EntityGER_TankAA;
import hmgww2.entity.EntityGER_TankH;
import hmgww2.entity.EntityGER_TankSPG;
import hmgww2.entity.EntityJPN_Fighter;
import hmgww2.entity.EntityJPN_FighterA;
import hmgww2.entity.EntityJPN_S;
import hmgww2.entity.EntityJPN_ShipB;
import hmgww2.entity.EntityJPN_ShipD;
import hmgww2.entity.EntityJPN_Tank;
import hmgww2.entity.EntityJPN_TankAA;
import hmgww2.entity.EntityJPN_TankSPG;
import hmgww2.entity.EntityRUS_Fighter;
import hmgww2.entity.EntityRUS_FighterA;
import hmgww2.entity.EntityRUS_S;
import hmgww2.entity.EntityRUS_Tank;
import hmgww2.entity.EntityRUS_TankAA;
import hmgww2.entity.EntityRUS_TankH;
import hmgww2.entity.EntityRUS_TankSPG;
import hmgww2.entity.EntityUSA_Fighter;
import hmgww2.entity.EntityUSA_FighterA;
import hmgww2.entity.EntityUSA_S;
import hmgww2.entity.EntityUSA_ShipB;
import hmgww2.entity.EntityUSA_ShipD;
import hmgww2.entity.EntityUSA_Tank;
import hmgww2.entity.EntityUSA_TankAA;
import hmgww2.entity.EntityUSA_TankSPG;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemSpwanEntity extends Item
{
	public int mob ;
	
	public ItemSpwanEntity(int i)
    {
        super();
        this.mob = i;
        this.maxStackSize = 64;
    }

	public void SpawnEntity(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6)
	{
		if(this.mob == 0){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityJPN_S entityskeleton = new EntityJPN_S(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            par3World.spawnEntityInWorld(entityskeleton);
        }else if(this.mob == 1){
                ++par5;
                int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
                EntityJPN_S entityskeleton = new EntityJPN_S(par3World);
                entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
                int iii = par3World.rand.nextInt(10);
                if(iii == 0){
                	entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_type99));
                }else if(iii == 1){
                	entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_type99));
                }else if(iii == 2){
                	entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_type99lmg));
                }else if(iii == 3){
                	entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_type99lmg));
                }else if(iii == 4){
                	entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_type89));
                }else if(iii == 5){
                	entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_type89));
                }else
                {
                	entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_type38));
                }
                entityskeleton.setCurrentItemOrArmor(4, new ItemStack(mod_GVCWW2.armor_jpn));
                par3World.spawnEntityInWorld(entityskeleton);
                //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 2){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityJPN_Tank entityskeleton = new EntityJPN_Tank(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            entityskeleton.setMobMode(1);
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 3){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityJPN_Fighter entityskeleton = new EntityJPN_Fighter(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            entityskeleton.setMobMode(1);
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 4){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityJPN_TankAA entityskeleton = new EntityJPN_TankAA(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            entityskeleton.setMobMode(1);
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 5){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityJPN_TankSPG entityskeleton = new EntityJPN_TankSPG(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            entityskeleton.setMobMode(1);
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 6){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityJPN_FighterA entityskeleton = new EntityJPN_FighterA(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            entityskeleton.setMobMode(1);
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 7){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityJPN_ShipB entityskeleton = new EntityJPN_ShipB(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            entityskeleton.setMobMode(1);
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 8){
        	++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityJPN_ShipD entityskeleton = new EntityJPN_ShipD(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            entityskeleton.setMobMode(1);
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 21){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityUSA_S entityskeleton = new EntityUSA_S(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            int iii = par3World.rand.nextInt(10);
            if(iii == 0){
            	entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_m1904));
            }else if(iii == 1){
            	entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_m1904));
            }else if(iii == 2){
            	entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_bar));
            }else if(iii == 3){
            	entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_m1b));
            }else if(iii == 4){
            	entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_m1t));
            }else if(iii == 5){
            	entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_m1t));
            }else{
            	entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_m1g));
            }
            entityskeleton.setCurrentItemOrArmor(4, new ItemStack(mod_GVCWW2.armor_usa));
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 22){
        ++par5;
        int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        EntityUSA_Tank entityskeleton = new EntityUSA_Tank(par3World);
        entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
        entityskeleton.setMobMode(1);
        par3World.spawnEntityInWorld(entityskeleton);
        //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 23){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityUSA_Fighter entityskeleton = new EntityUSA_Fighter(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            entityskeleton.setMobMode(1);
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 24){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityUSA_TankAA entityskeleton = new EntityUSA_TankAA(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            entityskeleton.setMobMode(1);
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 25){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityUSA_TankSPG entityskeleton = new EntityUSA_TankSPG(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            entityskeleton.setMobMode(1);
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 26){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityUSA_FighterA entityskeleton = new EntityUSA_FighterA(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            entityskeleton.setMobMode(1);
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 27){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityUSA_ShipB entityskeleton = new EntityUSA_ShipB(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            entityskeleton.setMobMode(1);
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 28){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityUSA_ShipD entityskeleton = new EntityUSA_ShipD(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            entityskeleton.setMobMode(1);
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 41){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityGER_S entityskeleton = new EntityGER_S(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            int iii = par3World.rand.nextInt(10);
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
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 42){
        ++par5;
        int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        EntityGER_Tank entityskeleton = new EntityGER_Tank(par3World);
        entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
        entityskeleton.setMobMode(1);
        par3World.spawnEntityInWorld(entityskeleton);
        //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 43){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityGER_Fighter entityskeleton = new EntityGER_Fighter(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            entityskeleton.setMobMode(1);
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 44){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityGER_TankAA entityskeleton = new EntityGER_TankAA(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            entityskeleton.setMobMode(1);
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 45){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityGER_TankSPG entityskeleton = new EntityGER_TankSPG(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            entityskeleton.setMobMode(1);
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 46){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityGER_FighterA entityskeleton = new EntityGER_FighterA(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            entityskeleton.setMobMode(1);
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 47){
                ++par5;
                int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
                EntityGER_TankH entityskeleton = new EntityGER_TankH(par3World);
                entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
                entityskeleton.setMobMode(1);
                par3World.spawnEntityInWorld(entityskeleton);
                //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 61){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityRUS_S entityskeleton = new EntityRUS_S(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            int iii = par3World.rand.nextInt(10);
            	 if(iii == 0){
            		 entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_ppsh41));
                 }else if(iii == 1){
                	 entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_ppsh41));
                 }else if(iii == 2){
                	 entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_dp28));
                 }else if(iii == 3){
                	 entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_m1891sr));
                 }else if(iii == 4){
                	 entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_grenadet));
                 }else if(iii == 5){
                	 entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_grenadet));
                 }else
                 {
                	 entityskeleton.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_m1891));
                 }
            entityskeleton.setCurrentItemOrArmor(4, new ItemStack(mod_GVCWW2.armor_rus));
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 62){
        ++par5;
        int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        EntityRUS_Tank entityskeleton = new EntityRUS_Tank(par3World);
        entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
        entityskeleton.setMobMode(1);
        par3World.spawnEntityInWorld(entityskeleton);
        //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 63){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityRUS_Fighter entityskeleton = new EntityRUS_Fighter(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            entityskeleton.setMobMode(1);
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 64){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityRUS_TankAA entityskeleton = new EntityRUS_TankAA(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            entityskeleton.setMobMode(1);
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 65){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityRUS_TankSPG entityskeleton = new EntityRUS_TankSPG(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            entityskeleton.setMobMode(1);
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }else if(this.mob == 66){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityRUS_FighterA entityskeleton = new EntityRUS_FighterA(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            entityskeleton.setMobMode(1);
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }
        else if(this.mob == 67){
            ++par5;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EntityRUS_TankH entityskeleton = new EntityRUS_TankH(par3World);
            entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
            entityskeleton.setMobMode(1);
            par3World.spawnEntityInWorld(entityskeleton);
            //entityskeleton.mountEntity(entityskeleton1);
        }
	}
	
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par3World.isRemote)
        {
            return true;
        }
        else
        {
            Block block = par3World.getBlock(par4, par5, par6);
            par4 += Facing.offsetsXForSide[par7];
            par5 += Facing.offsetsYForSide[par7];
            par6 += Facing.offsetsZForSide[par7];
            double d0 = 0.0D;

            if (par7 == 1 && block.getRenderType() == 11)
            {
                d0 = 0.5D;
            }
            {
            	this.SpawnEntity(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6);

                if (!par2EntityPlayer.capabilities.isCreativeMode)
                {
                    --par1ItemStack.stackSize;
                }
            }

            return true;
        }
    }
	
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
        if (p_77659_2_.isRemote)
        {
            return p_77659_1_;
        }
        else
        {
            MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(p_77659_2_, p_77659_3_, true);

            if (movingobjectposition == null)
            {
                return p_77659_1_;
            }
            else
            {
                if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
                {
                    int i = movingobjectposition.blockX;
                    int j = movingobjectposition.blockY;
                    int k = movingobjectposition.blockZ;

                    if (!p_77659_2_.canMineBlock(p_77659_3_, i, j, k))
                    {
                        return p_77659_1_;
                    }

                    if (!p_77659_3_.canPlayerEdit(i, j, k, movingobjectposition.sideHit, p_77659_1_))
                    {
                        return p_77659_1_;
                    }

                    if (p_77659_2_.getBlock(i, j, k) instanceof BlockLiquid)
                    {
                    	{
                        	this.SpawnEntity(p_77659_1_, p_77659_3_, p_77659_2_, i, j, k);

                            if (!p_77659_3_.capabilities.isCreativeMode)
                            {
                                --p_77659_1_.stackSize;
                            }
                        }
                    }
                }

                return p_77659_1_;
            }
        }
    }
}