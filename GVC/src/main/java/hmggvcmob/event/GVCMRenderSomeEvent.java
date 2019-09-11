package hmggvcmob.event;

import cpw.mods.fml.common.gameevent.TickEvent;
import hmggvcmob.entity.*;
import handmadevehicle.CLProxy;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.ArrayList;

import static handmadevehicle.HMVehicle.HMV_Proxy;

public class GVCMRenderSomeEvent {

	public boolean zoomtype;
	public static float mouseSensO = -1;
	static boolean needrest = true;
	private double zLevel = 0;
	private static final IModelCustom attitude_indicator = AdvancedModelLoader.loadModel(new ResourceLocation("gvcmob:textures/model/Attitude indicator.mqo"));
	private static final ResourceLocation attitude_indicator_texture = new ResourceLocation("gvcmob:textures/model/Attitude indicator.png");

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void tickEvent(TickEvent.WorldTickEvent event) {
		try {
			if (event.phase == TickEvent.Phase.START && !GVCMXEntityEvent.soundedentity.isEmpty()) {
				ArrayList tempremove = new ArrayList();
				for (int i = 0; i < GVCMXEntityEvent.soundedentity.size(); i++) {
					if (GVCMXEntityEvent.soundedentity.get(i) != null) {
						GVCMXEntityEvent.soundedentity.get(i).getEntityData().setFloat("GunshotLevel", GVCMXEntityEvent.soundedentity.get(i).getEntityData().getFloat("GunshotLevel") * 0.95f);
						if (GVCMXEntityEvent.soundedentity.get(i).getEntityData().getFloat("GunshotLevel") < 0.1) {
							tempremove.add(GVCMXEntityEvent.soundedentity.get(i));
						}
					}
				}
				GVCMXEntityEvent.soundedentity.removeAll(tempremove);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	@SubscribeEvent
	public void soundevent(PlaySoundAtEntityEvent event) {
		if(event.entity.getEntityData().getFloat("GunshotLevel")<event.volume) {
			event.entity.getEntityData().setFloat("GunshotLevel", event.volume);
		}
		GVCMXEntityEvent.soundedentity.add(event.entity);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderover(RenderGameOverlayEvent.Pre event) {
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer entityplayer = minecraft.thePlayer;
		
		ScaledResolution scaledresolution = new ScaledResolution(minecraft, minecraft.displayWidth,
				                                                        minecraft.displayHeight);
		int i = scaledresolution.getScaledWidth();
		int j = scaledresolution.getScaledHeight();
		if (entityplayer.ridingEntity instanceof EntityMGAX55) {
			EntityMGAX55 gear = (EntityMGAX55)entityplayer.ridingEntity;
			FontRenderer fontrenderer = minecraft.fontRenderer;
			String weaponmode;
			int color = 0xFFFFFF;
			switch (gear.weaponMode) {
				case 0:
					if (gear.railGunChargecnt > 0) {
						color = 0xFF0000;
						weaponmode = "Rail Gun : CHRG..." + gear.railGunChargecnt;
						
					} else if (gear.railGunCoolcnt > 0) {
						color = 0xFFFF00;
						weaponmode = "Rail Gun : LD..." + gear.railGunCoolcnt;
					} else {
						weaponmode = "Rail Gun : RDY :EN " + gear.railGunMagazine;
					}
					break;
				case 1:
					if (gear.rocketMagazine <= 0) color = 0xFF0000;
					weaponmode = "AT Missile : NUM " + gear.rocketMagazine;
					break;
				case 2:
					if (gear.normalGunHeat < EntityMGAX55.normalGunHeat_Max)
						weaponmode = "Machine Gun : RDY :HEAT" + gear.normalGunHeat;
					else {
						color = 0xFF0000;
						weaponmode = "Machine Gun : OH COOLING... HEAT" + gear.normalGunHeat;
					}
					break;
				default:
					weaponmode = "Error";
			}
			fontrenderer.drawStringWithShadow("Weapon Mode : " + weaponmode, i - 300, j - 20 - 10, color);
			color = 0x00FF00;
			if (gear.health < 100) {
				color = 0xFF0000;
			}
			fontrenderer.drawStringWithShadow("Armor : " + gear.health, i - 300, j - 40 - 10, color);
		}
		minecraft.getTextureManager().bindTexture(Gui.icons);
		boolean rc = HMV_Proxy.zoomclick();
		if(rc) CLProxy.zooming = !CLProxy.zooming;
	}
}
