package handmadeguns.items;

import java.io.File;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;



	public class HMGItemBullet_TE extends HMGItemBulletBase {
		
		public HMGItemBullet_TE(String s) {
			this.setMaxDamage(30);
			flame = true;
			explosion = true;
			blockdestroyex = false;
			explosionlevel = 1;
		}
}
