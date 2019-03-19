package hmggvcutil.client;
import hmggvcutil.CommonSideProxyGVC;
import hmggvcutil.GVCUtils;
import hmggvcutil.entity.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
//import net.java.games.input.Keyboard;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

import cpw.mods.fml.common.registry.VillagerRegistry;

import static hmggvcutil.GVCUtils.GVCVillagerProfession;

public class ClientProxyGVC extends CommonSideProxyGVC {
	//public static final EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;


	@Override
	public World getCilentWorld(){
		return FMLClientHandler.instance().getClient().theWorld;
	}

	@Override
	public void registerClientInfo() {
		//ClientRegistry.registerKeyBinding(Speedreload);
	}

	@Override
	public Minecraft getCilentMinecraft(){
		return FMLClientHandler.instance().getClient();
	}

	@Override
	public void reisterRenderers(){

		RenderingRegistry.registerEntityRenderingHandler(GVCEntityC4.class, new RenderSnowball(GVCUtils.fn_c4));
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityBox.class, new GVCRenderBox());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityBoxSpawner.class, new GVCRenderBox_Spawned());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityTarget.class, new GVCRenderTarget());
		VillagerRegistry.instance().registerVillagerSkin(GVCVillagerProfession, new ResourceLocation("gvcguns:textures/entity/mob/gvcVillager.png"));
		RenderingRegistry.registerEntityRenderingHandler(GVCEntitySentry.class, new GVCRenderSentry());




	}


}