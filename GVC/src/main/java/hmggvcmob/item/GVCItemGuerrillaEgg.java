package hmggvcmob.item;

import hmggvcutil.GVCUtils;
import hmggvcmob.entity.*;
import hmggvcmob.entity.guerrilla.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

import static hmggvcmob.GVCMobPlus.*;
import static hmggvcmob.GVCMobPlus.Guns_LMG;

public class GVCItemGuerrillaEgg extends Item
{
    public int mob ;

    public GVCItemGuerrillaEgg(int i)
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
                int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw));
                GVCEntityGuerrilla entityskeleton = new GVCEntityGuerrilla(par3World);
                entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
                Random rnd = new Random();
                entityskeleton.setCurrentItemOrArmor(0, new ItemStack((Item)Guns_AR.get(rnd.nextInt(Guns_AR.size()))));


                par3World.spawnEntityInWorld(entityskeleton);
                --par1ItemStack.stackSize;
                return true;

            }else if(this.mob == 1){
                ++par5;
                int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw));
                GVCEntityGuerrillaBM entityskeleton = new GVCEntityGuerrillaBM(par3World);
                entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
                entityskeleton.addRandomArmor();
                //entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_ak74));
                par3World.spawnEntityInWorld(entityskeleton);
                --par1ItemStack.stackSize;
                return true;
            }else if(this.mob == 2){
                ++par5;
                int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw));
                GVCEntityGuerrillaSP entityskeleton = new GVCEntityGuerrillaSP(par3World);
                entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
                entityskeleton.addRandomArmor();
                par3World.spawnEntityInWorld(entityskeleton);
                --par1ItemStack.stackSize;
                return true;
            }else if(this.mob == 3){
                ++par5;
                int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw));
                GVCEntityGuerrillaRPG entityskeleton = new GVCEntityGuerrillaRPG(par3World);
                entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
                if(par1ItemStack.hasDisplayName()){
                    entityskeleton.summoningVehicle = par1ItemStack.getDisplayName();
                }
                if(new Random().nextInt(10)!=-0) {
                    entityskeleton.setCurrentItemOrArmor(0, new ItemStack((Item) Guns_RR.get(new Random().nextInt(Guns_RR.size()))));
                }
                par3World.spawnEntityInWorld(entityskeleton);
                --par1ItemStack.stackSize;
                return true;
            }else if(this.mob == 4){
                ++par5;
                int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw));
                GVCEntityGuerrillaSG entityskeleton = new GVCEntityGuerrillaSG(par3World);
                entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
                entityskeleton.setCurrentItemOrArmor(0,new ItemStack((Item)Guns_SG.get(new Random().nextInt(Guns_SG.size()))));
                par3World.spawnEntityInWorld(entityskeleton);
                --par1ItemStack.stackSize;
                return true;
            }else if(this.mob == 5){
                ++par5;
                int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw));
                GVCEntityGuerrillaMG entityskeleton = new GVCEntityGuerrillaMG(par3World);
                entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
                entityskeleton.setCurrentItemOrArmor(0,new ItemStack((Item)Guns_LMG.get(new Random().nextInt(Guns_LMG.size()))));
                par3World.spawnEntityInWorld(entityskeleton);
                --par1ItemStack.stackSize;
                return true;
            }else if(this.mob == 6){
                ++par5;
                int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw));
                GVCEntityGK entityskeleton = new GVCEntityGK(par3World);
                entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
                //entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_ak74));
            
            /*GVCEntityparas entityskeleton1 = new GVCEntityparas(par3World);
            entityskeleton1.setLocationAndAngles(par4, par5, par6, var12, 0.0F);
            entityskeleton1.onSpawnWithEgg((IEntityLivingData)null);
            par3World.spawnEntityInWorld(entityskeleton1);*/


                par3World.spawnEntityInWorld(entityskeleton);
                //entityskeleton.mountEntity(entityskeleton1);
                --par1ItemStack.stackSize;
                return true;
            }else  if(this.mob == 11){
                ++par5;
                int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw));
                GVCEntityAAG entityskeleton = new GVCEntityAAG(par3World);
                entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
                entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_pkm));
                par3World.spawnEntityInWorld(entityskeleton);
                --par1ItemStack.stackSize;
                return true;
            }else if(this.mob == 12){
                ++par5;
                int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw));
                GVCEntityGuerrillaM entityskeleton = new GVCEntityGuerrillaM(par3World);
                entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
                entityskeleton.setCurrentItemOrArmor(0, new ItemStack(Items.iron_pickaxe));
                par3World.spawnEntityInWorld(entityskeleton);
                --par1ItemStack.stackSize;
                return true;
            }else if(this.mob == 13){
                ++par5;
                int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw));
                GVCEntityAA entityskeleton = new GVCEntityAA(par3World);
                entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
                par3World.spawnEntityInWorld(entityskeleton);
                --par1ItemStack.stackSize;
                return true;

            }else if(this.mob == 15){
                ++par5;
                int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw));
                GVCEntityDrawn entityskeleton = new GVCEntityDrawn(par3World);
                entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
                par3World.spawnEntityInWorld(entityskeleton);
                --par1ItemStack.stackSize;
                return true;
            }else if(this.mob == 16){
                ++par5;
                int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw));
                GVCEntityGuerrilla_Flamer entityskeleton = new GVCEntityGuerrilla_Flamer(par3World);
                entityskeleton.addRandomArmor();
                entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
                par3World.spawnEntityInWorld(entityskeleton);
                --par1ItemStack.stackSize;
                return true;
            }else if(this.mob == 17){
                ++par5;
                int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw));
                GVCEntityGuerrillaSkeleton entityskeleton = new GVCEntityGuerrillaSkeleton(par3World);
                entityskeleton.addRandomArmor();
                entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
                par3World.spawnEntityInWorld(entityskeleton);
                --par1ItemStack.stackSize;
                return true;
            }else if(this.mob == 19){
                ++par5;
                int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw));
                GVCEntityGuerrilla_ender entityskeleton = new GVCEntityGuerrilla_ender(par3World);
                entityskeleton.addRandomArmor();
                entityskeleton.setLocationAndAngles(par4+0.5, par5, par6+0.5, var12, 0.0F);
                par3World.spawnEntityInWorld(entityskeleton);
                --par1ItemStack.stackSize;
                return true;
            }else{
                return false;
            }

        }
    }
}