package handmadeguns.items.guns;

import handmadeguns.items.*;
import littleMaidMobX.LMM_IEntityLittleMaidAvatarBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

import static handmadeguns.HandmadeGunsCore.islmmloaded;

public class HMGItem_Gun extends Item {
	public static final UUID field_110179_h = UUID.fromString("254F543F-8B6F-407F-931B-4B76FEB8BA0D");
	
	GunInfo gunInfo;
	FireInfo defaultfireInfo;
	GunSightInfo gunSightInfo;
	
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		FireInfo currentfireinfo = getCurrentFireinfo(par1ItemStack);
		String powor = String
				               .valueOf(currentfireinfo.powor + EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack));
		String speed = String.valueOf(currentfireinfo.speed);
		String bure = String.valueOf(gunInfo.spread_setting);
		String recoil = String.valueOf(gunInfo.recoil);
		String retime = String.valueOf(currentfireinfo.reloadtime);
		String mgazineRound = String.valueOf(currentfireinfo.bulletRound);
		
		par3List.add(EnumChatFormatting.RED + "Magazine Round " + StatCollector.translateToLocal(mgazineRound));
		par3List.add(EnumChatFormatting.WHITE + "FireDamege " + "+" + StatCollector.translateToLocal(powor));
		par3List.add(EnumChatFormatting.WHITE + "BulletSpeed " + "+" + StatCollector.translateToLocal(speed));
		par3List.add(EnumChatFormatting.WHITE + "BulletSpread " + "+" + StatCollector.translateToLocal(bure));
		par3List.add(EnumChatFormatting.WHITE + "Recoil " + "+" + StatCollector.translateToLocal(recoil));
		par3List.add(EnumChatFormatting.YELLOW + "ReloadTime " + "+" + StatCollector.translateToLocal(retime));
		// par3List.add(EnumChatFormatting.YELLOW + "MagazimeType " +
		// StatCollector.translateToLocal("ARMagazine"));
		if (!(gunSightInfo.scopezoom[0] == 1.0f)) {
			String scopezoom = String.valueOf(gunSightInfo.scopezoom[0]);
			par3List.add(EnumChatFormatting.WHITE + "ScopeZoom " + "x" + StatCollector.translateToLocal(scopezoom));
		}
		if(gunInfo.needfix){
			par3List.add(EnumChatFormatting.WHITE + "cannot handhold Shot");
		}else
		if(gunInfo.canfix){
			par3List.add(EnumChatFormatting.WHITE + "can Fix");
		}
	}
	
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag){
		if(islmmloaded && entity instanceof LMM_IEntityLittleMaidAvatarBase){
			return;
		}
		if(entity!=null && flag){
		
		}
	}
	
	
	FireInfo getCurrentFireinfo(ItemStack itemStack){
		NBTBase nbttagcompound = itemStack.getTagCompound().getTag("Magazine");
		if(nbttagcompound instanceof NBTTagCompound) {
			ItemStack magazine = ItemStack.loadItemStackFromNBT((NBTTagCompound) nbttagcompound);
			Item item = magazine.getItem();
			if(item instanceof HMGItemBulletBase){
				return ((HMGItemBulletBase) item).fireInfo;
			}
		}
		return defaultfireInfo;
	}
}
