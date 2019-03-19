package hmggvcmob.item;

import hmggvcutil.GVCUtils;
import hmggvcmob.entity.friend.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import static hmggvcmob.GVCMobPlus.tabgvcm;

public class GVCItemGuerrillaSoldierEgg extends Item
{
    public int mob ;

    public GVCItemGuerrillaSoldierEgg(int i)
    {
        super();
        this.mob = i;
        this.maxStackSize = 64;
        this.setCreativeTab(tabgvcm);
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par3World.isRemote)
        {
            return true;
        }
        else if (par7 != 1)
        {
            return false;
        }
        else
        {
            if(this.mob == 0){
                ++par5;
                int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
                GVCEntitySoldier entityskeleton = new GVCEntitySoldier(par3World);
                entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, par2EntityPlayer.rotationYaw, 0.0F);
                entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_m16a4));
                par3World.spawnEntityInWorld(entityskeleton);
                --par1ItemStack.stackSize;
                return true;
            }else
            if(this.mob == 1){
                ++par5;
                int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
                GVCEntitySoldierSP entityskeleton = new GVCEntitySoldierSP(par3World);
                entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, par2EntityPlayer.rotationYaw, 0.0F);
                entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_m110));
                par3World.spawnEntityInWorld(entityskeleton);
                --par1ItemStack.stackSize;
                return true;
            }else
            if(this.mob == 2){
                ++par5;
                int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
                GVCEntitySoldierMG entityskeleton = new GVCEntitySoldierMG(par3World);
                entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, par2EntityPlayer.rotationYaw, 0.0F);
                entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_m240b));
                par3World.spawnEntityInWorld(entityskeleton);
                --par1ItemStack.stackSize;
                return true;
            }else
            if(this.mob == 3){
                ++par5;
                int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
                GVCEntitySoldierRPG entityskeleton = new GVCEntitySoldierRPG(par3World);
                entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, par2EntityPlayer.rotationYaw, 0.0F);
                entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_smaw));
                par3World.spawnEntityInWorld(entityskeleton);
                --par1ItemStack.stackSize;
                return true;
            }else
            if(this.mob == 4){
                ++par5;
                int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
                GVCEntitySoldierTank entityskeleton = new GVCEntitySoldierTank(par3World);
                entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, par2EntityPlayer.rotationYaw, 0.0F);
                par3World.spawnEntityInWorld(entityskeleton);
                --par1ItemStack.stackSize;
                return true;
            }else
            if(this.mob == 5){
                ++par5;
                int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
                GVCEntitySoldierHeli entityskeleton = new GVCEntitySoldierHeli(par3World);
                entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, par2EntityPlayer.rotationYaw, 0.0F);
                par3World.spawnEntityInWorld(entityskeleton);
                --par1ItemStack.stackSize;
                return true;

            }else if(this.mob == 6){
                ++par5;
                int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
                GVCEntitySoldierBMP entityskeleton = new GVCEntitySoldierBMP(par3World);
                entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, par2EntityPlayer.rotationYaw, 0.0F);
                par3World.spawnEntityInWorld(entityskeleton);
                --par1ItemStack.stackSize;
                return true;

            }else{
                return false;
            }
        }

    }
}