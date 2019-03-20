package hmgww2.event;

import hmgww2.entity.EntityBases;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hmgww2.mod_GVCWW2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class EventOverlay {

	@SideOnly(Side.CLIENT)
    @SubscribeEvent
	  public void renderfov(FOVUpdateEvent event)
	  {
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		World world = FMLClientHandler.instance().getWorldClient();
		EntityPlayer entityplayer = minecraft.thePlayer;
		ItemStack itemstack = ((EntityPlayer) (entityplayer)).getCurrentEquippedItem();
		if (entityplayer.ridingEntity instanceof EntityBases && entityplayer.ridingEntity != null) {//1
			EntityBases balaam = (EntityBases) entityplayer.ridingEntity;
			boolean rightc = mod_GVCWW2.proxy.rightclick();
			if(rightc){
				event.newfov = event.fov / 2;
			}
		}//1
	  }
	@SideOnly(Side.CLIENT)
    @SubscribeEvent
	  public void renderoffset(EntityViewRenderEvent.RenderFogEvent event)
	  {
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		World world = FMLClientHandler.instance().getWorldClient();
		EntityLivingBase entityLiving = event.entity;
		EntityPlayer entityplayer = minecraft.thePlayer;
		ItemStack itemstack = ((EntityPlayer) (entityplayer)).getCurrentEquippedItem();
		if (entityplayer.ridingEntity instanceof EntityBases && entityplayer.ridingEntity != null) {//1
			EntityBases r = (EntityBases) entityplayer.ridingEntity;
			if(minecraft.gameSettings.thirdPersonView == 1){
				float rotep = entityplayer.rotationPitch * (2 * (float) Math.PI / 360);
				float x = (float) (2 * Math.cos(rotep));
				float y = (float) (2 * Math.sin(rotep)) * r.overlayhight_3;
				//float y = (float) (2 * Math.sin(rotep));
				float ix2 = 0;
				float iz2 = 0;
				float f12 = entityplayer.rotationYawHead * (2 * (float) Math.PI / 360);
				ix2 += (float) (MathHelper.sin(f12) * r.overlaywidth_3);
				iz2 -= (float) (MathHelper.cos(f12) * r.overlaywidth_3);
				
				//float ix3 = 0;
				//float iz3 = 0;
				//ix3 += (float) (MathHelper.sin(f12) * r.overlaywidth_3*x);
				//iz3 -= (float) (MathHelper.cos(f12) * r.overlaywidth_3*x);
				{
				GL11.glTranslatef(-ix2,(-r.overlayhight_3 - y), -iz2);
				}
			}else if(minecraft.gameSettings.thirdPersonView == 0){
				GL11.glTranslatef(0, -r.overlayhight, 0);
			}
			boolean rightc = mod_GVCWW2.proxy.rightclick();
			if(rightc){}
		}//1
	  }
	/**/
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderover(RenderGameOverlayEvent.Text event) {
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer entityplayer = minecraft.thePlayer;
		if (entityplayer.ridingEntity instanceof EntityBases && entityplayer.ridingEntity != null) {//1
			{
				EntityBases heli = (EntityBases) entityplayer.ridingEntity;
			//	entityplayer.rotationYaw = heli.rotationYawHead;
			//	entityplayer.prevRotationYaw = heli.rotationYawHead;
			//	entityplayer.rotationPitch = heli.rotationPitch;
			//	entityplayer.prevRotationPitch = heli.rotationPitch;
			//	ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, 
			//			heli.rotationYawHead + (heli.rotationYawHead - heli.rotationYawHead) * event.partialTicks, "camRoll", "R", "field_78495_O");
			//	ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, 
			//			15, "thirdPersonDistance", "E", "field_78490_B");
			//	ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, 
			//			-30, "cameraZoom","field_78503_V ");
			//	ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, Minecraft.getMinecraft().entityRenderer, 50, 48);
			//	minecraft.renderViewEntity = heli;
			//	entityplayer.cameraPitch = 60;
			}
		}
		
		ScaledResolution scaledresolution = new ScaledResolution(minecraft, minecraft.displayWidth,
				minecraft.displayHeight);
		int i = scaledresolution.getScaledWidth();
		int j = scaledresolution.getScaledHeight();
		
		RenderHUDEvent.RenderHUD(minecraft, entityplayer, scaledresolution);
		
//		if (entityplayer.ridingEntity instanceof EntityBases && entityplayer.ridingEntity != null) {//1
//
//			EntityBases balaam = (EntityBases) entityplayer.ridingEntity;
//			FontRenderer fontrenderer = minecraft.fontRenderer;
//			GL11.glPushMatrix();
//			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//			int am1 = balaam.get - balaam.cooltime;
//			int am2 = balaam.ammo2 - balaam.cooltime2;
//			String d1 = String.format("%1$3d", am1);
//			String d2 = String.format("%1$3d", am2);
//			String hp = String.format("%1$3d", (int)balaam.getHealth());
//			String mhp = String.format("%1$3d", (int)balaam.getMaxHealth());
//			String th = String.format("%1$3d", (int)balaam.th);
//			String speed = String.format("%1$3d", (int)balaam.thpower);
//			String rotep = String.format("%1$3d", (int)entityplayer.rotationYawHead);
//			String rote = String.format("%1$3d", (int)balaam.rotationYawHead);
//			String rote2 = String.format("%1$3d", (int)balaam.rote);
//
//			if(balaam.ammo1 != 0){
//				String name = balaam.w1name;
//				String remain = String.format("%1$3d", balaam.getRemain_L());
//				String remainmax = String.format("%1$3d", balaam.magazine);
//				fontrenderer.drawStringWithShadow(name, i - 100, j - 70 + 0, 0xFFFFFF);
//				fontrenderer.drawStringWithShadow(remain + " /" + remainmax, i - 100, j - 60 + 0, 0xFFFFFF);
//			}
//			if(balaam.ammo2 != 0){
//				String name = balaam.w2name;
//				String remain = String.format("%1$3d", balaam.getRemain_R());
//				String remainmax = String.format("%1$3d", balaam.magazine2);
//				fontrenderer.drawStringWithShadow(name, i - 100, j - 50 + 0, 0xFFFFFF);
//				fontrenderer.drawStringWithShadow(remain + " /" + remainmax, i - 100, j - 40 + 0, 0xFFFFFF);
//			}
//			if(balaam.ammo4 != 0){
//				String name = balaam.w4name;
//				String remain = String.format("%1$3d", balaam.getRemain_S());
//				String remainmax = String.format("%1$3d", balaam.magazine4);
//				fontrenderer.drawStringWithShadow(name, i - 100, j - 30 + 0, 0xFFFFFF);
//				fontrenderer.drawStringWithShadow(remain + " /" + remainmax, i - 100, j - 20 + 0, 0xFFFFFF);
//			}
//
//			fontrenderer.drawStringWithShadow("HP "+hp+"/"+mhp, i - 100, j - 80  + 0, 0xFFFFFF);
//			fontrenderer.drawStringWithShadow("TH" + th, (i/2) - 80, j/2 + 0, 0xFFFFFF);
//			fontrenderer.drawStringWithShadow("Speed"+ speed, (i/2) - 80, j/2 +20, 0xFFFFFF);
//
//			fontrenderer.drawStringWithShadow("RotePlayer"+ rotep, (i/2) + 40, j/2 +0, 0xFFFFFF);
//			fontrenderer.drawStringWithShadow("RoteEntity"+ rote, (i/2) + 40, j/2 +10, 0xFFFFFF);
//
//			{
//				String y = String.format("%1$3d", (int)balaam.posY);
//				int genY = balaam.worldObj.getHeightValue((int)balaam.posX, (int)balaam.posZ);
//				String y2 = String.format("%1$3d", (int)balaam.posY - (int)genY);
//				fontrenderer.drawStringWithShadow("Hight" + y, (i/2) - 80, j/2 + 30 + 0, 0xFFFFFF);
//				fontrenderer.drawStringWithShadow("BlockHight" + y2, (i/2) - 80, j/2 + 40 + 0, 0xFFFFFF);
//			}
//
//			GuiIngame g  = minecraft.ingameGUI;
//			minecraft.getTextureManager().bindTexture(TextureMap.locationItemsTexture);
//			//g.drawTexturedModelRectFromIcon(i-70, j-63, armor.getIconFromDamage(0), 16, 16);
//			GL11.glPopMatrix();
//			{
//
//				GL11.glPushMatrix();//21
//				GL11.glEnable(GL11.GL_BLEND);
//				GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
//				minecraft.renderEngine.bindTexture(new ResourceLocation("hmgww2:textures/hud/hou1.png"));
//				GL11.glTranslatef(32, scaledresolution.getScaledHeight()-32, 0F);
//				GL11.glRotatef(balaam.prevRotationYawHead, 0.0F, 0.0F, 1.0F);
//				GL11.glTranslatef(-32, -(scaledresolution.getScaledHeight()-32), 0F);
//				//drawTexturedModalRect(scaledresolution.getScaledWidth()/2 -0,  scaledresolution.getScaledHeight()/2 +24, 0,0, 256, 256);
//				GL11.glScalef(0.25f, 0.25f, 1);
//				g.drawTexturedModalRect(0,(scaledresolution.getScaledHeight()-64)*4, 0,0, 256, 256);
//				GL11.glPopMatrix();//22
//
//				GL11.glPushMatrix();//21
//				GL11.glEnable(GL11.GL_BLEND);
//				GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
//				minecraft.renderEngine.bindTexture(new ResourceLocation("hmgww2:textures/hud/hou2.png"));
//				GL11.glTranslatef(32, scaledresolution.getScaledHeight()-32, 0F);
//				GL11.glRotatef(entityplayer.prevRotationYawHead, 0.0F, 0.0F, 1.0F);
//				GL11.glTranslatef(-32, -(scaledresolution.getScaledHeight()-32), 0F);
//				GL11.glScalef(0.25f, 0.25f, 1);
//				g.drawTexturedModalRect(0,(scaledresolution.getScaledHeight()-64)*4, 0,0, 256, 256);
//				GL11.glPopMatrix();//22
//			}
//
//		}//1
	}
}
