package handmadeguns.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;


public class HMGItemCustomMagazine extends HMGItemAttachmentBase {
    public float damagemodify = 1;//古いものを活かすために倍率方式を取らざるを得ない
    public float speedmodify = 1;
    public int fuse = -1;
    public boolean autoDestroy = true;
    public int bullettype = -1;//-1でデフォルト
    public boolean hasRoundOption = false;
    public int round = 0;
    public boolean hasReloadOption = false;
    public int reloadTime = 0;
    public float explosionlevel = -1;
    public boolean blockdestroyex = true;
    public String bulletmodel = null;
    public String magmodel = null;
    public String cartridgeModelName = null;
    
    public double knockback = Double.NaN;
    public double knockbackY = Double.NaN;
    public float  bouncerate = Float.NaN;
    public float  bouncelimit = Float.NaN;
    public float  resistance = Float.NaN;
    public float  acceleration = Float.NaN;
    public float  gra = Float.NaN;
    
    public String bulletItemName;
    public Item bulletItem;
    public String cartridgeItemName = null;
    public Item cartridgeItem = null;
    public Item getCartridgeItem(){
        if(cartridgeItemName != null && (cartridgeItem == null)) {
            String[] splited = cartridgeItemName.split(":");
            String modID = splited[0];
            String itemName = splited[1];
            cartridgeItem = GameRegistry.findItem(modID, itemName);
        }
        return cartridgeItem;
    }
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int slot, boolean flag) {
        if(flag && !world.isRemote && entity instanceof EntityPlayer && itemStack.getItemDamage()>0){
            EntityPlayer entityPlayer = (EntityPlayer) entity;
            if(entityPlayer.isUsingItem()){
                if(itemStack.stackSize>1){
                    ItemStack copied = itemStack.copy();
                    copied.stackSize--;
                    world.spawnEntityInWorld(new EntityItem(world,entityPlayer.posX,entityPlayer.posY,entityPlayer.posZ,copied));
                    itemStack.stackSize = 1;
                }
                if(entityPlayer.inventory.consumeInventoryItem(bulletItem)){
                    itemStack.setItemDamage(itemStack.getItemDamage() -1);
                    entityPlayer.stopUsingItem();
                }
            }
        }
    }
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 72000;
    }
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if(bulletItemName != null) {
            if (bulletItem == null) {
                String[] splited = bulletItemName.split(":");
                String modID = splited[0];
                String itemName = splited[1];
                bulletItem = GameRegistry.findItem(modID, itemName);
            }
        }
        if(bulletItem != null)par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
    public void onCreated(ItemStack p_77622_1_, World p_77622_2_, EntityPlayer p_77622_3_) {
        if(bulletItemName != null)p_77622_1_.setItemDamage(p_77622_1_.getMaxDamage());
    }
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        if(bulletItemName != null) {
            p_150895_3_.add(new ItemStack(p_150895_1_, 1, 0));
            p_150895_3_.add(new ItemStack(p_150895_1_, 1, getMaxDamage()));
        }else {
            p_150895_3_.add(new ItemStack(p_150895_1_, 1, 0));
        }
    }
}
