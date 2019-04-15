package handmadeguns.gui;
 
import handmadeguns.items.*;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadeguns.items.guns.HMGXItemGun_Sword;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class HMGSlotInventoryItem extends Slot
{
    public ItemStack gun = null;
    public HMGSlotInventoryItem(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    }
    public HMGSlotInventoryItem(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_,ItemStack gun) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
        this.gun = gun;
    }
 
    /*
        このアイテムは動かせない、つかめないようにする。
     */
    @Override
    public boolean canTakeStack(EntityPlayer p_82869_1_)
    {

    	/*if(!(getHasStack() && getStack().getItem()instanceof HMGItemGun_SG)){
    		return !(getHasStack() && getStack().getItem()instanceof HMGItemGunBase);
    	}else if(!(getHasStack() && getStack().getItem()instanceof HMGItemGun_SG)){
    		return !(getHasStack() && getStack().getItem()instanceof HMGItemGunBase);
    	}else{
    		return !(getHasStack() && getStack().getItem()instanceof HMGItemGunBase);
    	}*/
    	return (getStack() != p_82869_1_.getHeldItem());
        
    }
    @Override
    public boolean isItemValid(ItemStack itemStack){
//        System.out.println("" + slotNumber + itemStack.getItem().getUnlocalizedName());
        if(gun != null && itemStack != null){
            if(gun.getItem() instanceof HMGItem_Unified_Guns && ((HMGItem_Unified_Guns)gun.getItem()).hasAttachRestriction) {

                for(String aa:((HMGItem_Unified_Guns)gun.getItem()).attachwhitelist) {
                    if("item.".concat(aa).equals(itemStack.getItem().getUnlocalizedName())){
                        return true;
                    }
                }
                return false;
            }else {
//                System.out.println("debug");
                return true;
            }
        }else if(slotNumber == 4 && itemStack != null){
    
            return true;
        }
        return true;
    }
    boolean canvalidthisItemtoSlot(ItemStack itemStack,int slot){
        switch (slot){
            case 0:
                return false;
            case 1:
                return itemStack.getItem() instanceof HMGItemSightBase;
            case 2:
                return itemStack.getItem() instanceof HMGItemAttachment_laser || itemStack.getItem() instanceof HMGItemAttachment_light;
            case 3:
                return itemStack.getItem() instanceof HMGItemAttachment_Suppressor;
            case 4:
                return itemStack.getItem() instanceof HMGItemAttachment_grip || itemStack.getItem() instanceof HMGItem_Unified_Guns || itemStack.getItem() instanceof HMGXItemGun_Sword;
            case 5:
                return itemStack.getItem() instanceof HMGItemSightBase;
        }
        return false;
    }
}