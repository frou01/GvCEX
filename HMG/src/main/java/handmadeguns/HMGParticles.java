package handmadeguns;
 
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;

import javax.swing.Icon;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
 
/**
 * パーティクル用の画像登録クラス
 */
public class HMGParticles {
	private static HMGParticles instance;
 
	// 画像パス。eclipseの場合はforge/mcp/src/minecraft/assets/addparticle/textures/items/smokecircle.pngに展開される
	// 実環境では%ziproot%/assets/addparticle/textures/items/smokecircle.png
	String[] iconNames = {"handmadeguns:fire0","handmadeguns:fire1","handmadeguns:smoke5","handmadeguns:smoke4","handmadeguns:smoke3","handmadeguns:smoke2","handmadeguns:smoke1","handmadeguns:smoke0","handmadeguns:lockonmarker0"};
	IIcon icons[];
 
	public static HMGParticles getInstance() {
		if (instance == null) {
			instance = new HMGParticles();
		}
 
		return instance;
	}
 
	/**
	 * ブロックやアイテムと異なりEntityFXはregisterIconメソッドがないのでTextureStitchEvent.Preイベントをフックして登録します
	 */
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void handleTextureRemap(TextureStitchEvent.Pre event) {
		if (event.map.getTextureType() == 1) {
			this.getInstance().registerIcons(event.map);
		}
	}
 
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		icons = new IIcon[iconNames.length];
		for(int i = 0; i < icons.length; ++i) {
			icons[i] = par1IconRegister.registerIcon(iconNames[i]);
		}
	}
 
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(String iconName) {
		for(int i = 0; i < iconNames.length; ++i) {
			if(iconName.equalsIgnoreCase(iconNames[i])) {
				return icons[i];
			}
		}
		return null;
	}
 
}