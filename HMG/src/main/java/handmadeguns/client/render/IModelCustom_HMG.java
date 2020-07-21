package handmadeguns.client.render;

import cpw.mods.fml.relauncher.SideOnly;
import handmadeguns.obj_modelloaderMod.obj.HMGGroupObject;
import net.minecraftforge.client.model.IModelCustom;

import static cpw.mods.fml.relauncher.Side.CLIENT;

public interface IModelCustom_HMG extends IModelCustom {
	@SideOnly(CLIENT)
	HMGGroupObject renderPart_getInstance();
}
