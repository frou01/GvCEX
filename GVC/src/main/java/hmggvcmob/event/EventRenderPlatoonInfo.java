package hmggvcmob.event;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import handmadeguns.event.RenderTickSmoothing;
import hmggvcmob.entity.IPlatoonable;
import hmggvcmob.entity.PlatoonInfoData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import static org.lwjgl.opengl.GL11.*;

public class EventRenderPlatoonInfo {
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderEntity(RenderLivingEvent.Pre event) {
		GL11.glPushMatrix();
		EntityLivingBase entity = (EntityLivingBase) event.entity;
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer entityplayer = minecraft.thePlayer;
		if (entity != null && entity instanceof IPlatoonable && ((IPlatoonable) entity).getPlatoonMemberInfo() != null) {
			PlatoonInfoData platoonInfoData = ((IPlatoonable) entity).getPlatoonMemberInfo();
			GL11.glTranslatef((float)-entityplayer.posX * (RenderTickSmoothing.smooth),(float)-entityplayer.posY * (RenderTickSmoothing.smooth),(float)-entityplayer.posZ * (RenderTickSmoothing.smooth));
			GL11.glTranslatef((float)-entityplayer.prevPosX * (1 - RenderTickSmoothing.smooth),(float)-entityplayer.prevPosY * (1 - RenderTickSmoothing.smooth),(float)-entityplayer.prevPosZ * (1 - RenderTickSmoothing.smooth));
			GL11.glTranslatef((float)entity.posX,(float)entity.posY,(float)entity.posZ);
			GL11.glRotatef(180-entityplayer.rotationYawHead,0,1,0);

			float lastBrightnessX = OpenGlHelper.lastBrightnessX;
			float lastBrightnessY = OpenGlHelper.lastBrightnessY;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
			GL11.glTranslatef(0, 2.5f, 0);
			renderString(platoonInfoData.platoonName + ((IPlatoonable) entity).getPlatoonMemberInfo().isOnPlatoon);
			GL11.glTranslatef(0,0.2f,0);
			renderString(platoonInfoData.target[0] + " , " + platoonInfoData.target[1] + " , " + platoonInfoData.target[2]);
			GL11.glTranslatef(0,0.2f,0);
			renderString("" + platoonInfoData.mode);
			if(platoonInfoData.isLeader) {
				GL11.glTranslatef(0, 0.2f, 0);
				renderString("leader");
			}
			GL11.glRotatef(-180 + entityplayer.rotationYawHead,0,1,0);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_LIGHTING);
			glLineWidth(0.1f);
			glBegin(GL_LINE_LOOP);
			glVertex3f(0 , 0,0);
			glVertex3d(platoonInfoData.target[0] - entity.posX,platoonInfoData.target[1] - entity.posY,platoonInfoData.target[2] - entity.posZ);
			glEnd();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glTranslatef((float)-entity.posX,(float)-entity.posY,(float)-entity.posZ);
			GL11.glTranslatef((float)platoonInfoData.target[0],(float)platoonInfoData.target[1] + 2,(float)platoonInfoData.target[2]);
			GL11.glRotatef(180-entityplayer.rotationYawHead,0,1,0);
			renderString("v");
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
		}
		GL11.glPopMatrix();
	}

	public void renderString(String renderObj){
		GL11.glPushMatrix();
		GL11.glDisable(GL_BLEND);
		GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(0.01f,-0.01f,0.01f);
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		FontRenderer fontrenderer = minecraft.fontRenderer;
		Tessellator tessellator = Tessellator.instance;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDepthMask(false);
		int width = fontrenderer.getStringWidth(renderObj) / 2;
		GL11.glEnable(GL11.GL_BLEND);
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
		tessellator.addVertex((double)(-width - 1), -1.0D, 0.0D);
		tessellator.addVertex((double)(-width - 1), 8.0D, 0.0D);
		tessellator.addVertex((double)(width + 1), 8.0D, 0.0D);
		tessellator.addVertex((double)(width + 1), -1.0D, 0.0D);
		tessellator.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		fontrenderer.drawString(renderObj,-width,0,0xFFFFFF);
		GL11.glPopMatrix();
	}
}
