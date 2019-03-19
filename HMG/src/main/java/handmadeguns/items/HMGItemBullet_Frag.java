package handmadeguns.items;

import net.minecraft.item.Item;



	public class HMGItemBullet_Frag extends HMGItemBulletBase {

		public HMGItemBullet_Frag() {
			this.setMaxDamage(30);
			explosion = true;
			blockdestroyex = false;
			explosionlevel = 1;
		}
}
