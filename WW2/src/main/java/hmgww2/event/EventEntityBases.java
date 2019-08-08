package hmgww2.event;

import hmgww2.Nation;
import hmgww2.entity.*;
import hmgww2.items.ItemIFFArmor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hmgww2.mod_GVCWW2;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EventEntityBases {
	
	private static final ResourceLocation m0 = new ResourceLocation("hmgww2:textures/marker/marker.png");
	private static final ResourceLocation m2 = new ResourceLocation("hmgww2:textures/marker/marker2.png");
	private static final ResourceLocation m3 = new ResourceLocation("hmgww2:textures/marker/marker3.png");
	private static final ResourceLocation m4 = new ResourceLocation("hmgww2:textures/marker/marker4.png");
	private static final IModelCustom tankk = AdvancedModelLoader
			                                          .loadModel(new ResourceLocation("hmgww2:textures/marker/marker.obj"));
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderEntity(RenderLivingEvent.Pre event) {
		GL11.glPushMatrix();
		EntityLivingBase entity = (EntityLivingBase) event.entity;
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer entityplayer = minecraft.thePlayer;
		if (entity != null && entity instanceof EntityBases) {
			GL11.glTranslatef((float)-entityplayer.posX,(float)-entityplayer.posY,(float)-entityplayer.posZ);
			GL11.glTranslatef((float)entity.posX,(float)entity.posY,(float)entity.posZ);
			float lastBrightnessX = OpenGlHelper.lastBrightnessX;
			float lastBrightnessY = OpenGlHelper.lastBrightnessY;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
//			RenderHelper.disableStandardItemLighting();
			if (entityplayer.getEquipmentInSlot(4) != null
					    && entityplayer.getEquipmentInSlot(4).getItem() instanceof ItemIFFArmor) {
				Nation playernation = ((ItemIFFArmor) entityplayer.getEquipmentInSlot(4).getItem()).nation;
				if (playernation == ((EntityBases) entity).getnation()) {
					switch (((EntityBases) entity).getMobMode()) {
						case 1:
							GL11.glTranslatef(0, entity.height + 0.5F, 0);
							Minecraft.getMinecraft().renderEngine.bindTexture(m0);
							tankk.renderPart("mat1");
							break;
						case 2:
							GL11.glTranslatef(0, entity.height + 0.5F, 0);
							Minecraft.getMinecraft().renderEngine.bindTexture(m2);
							tankk.renderPart("mat1");
							break;
						case 3:
							GL11.glTranslatef(0, entity.height + 0.5F, 0);
							Minecraft.getMinecraft().renderEngine.bindTexture(m2);
							tankk.renderPart("mat1");
							break;
					}
					if(((EntityBases) entity).holdFire){
						GL11.glTranslatef(0,  + 0.5F, 0);
						Minecraft.getMinecraft().renderEngine.bindTexture(m3);
						tankk.renderPart("mat1");
					}else {
						GL11.glTranslatef(0,  + 0.5F, 0);
						Minecraft.getMinecraft().renderEngine.bindTexture(m4);
						tankk.renderPart("mat1");
					}
				}
			}
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
//			RenderHelper.enableStandardItemLighting();
		}
		GL11.glPopMatrix();
	}
}
