
package hmgww2.render;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import hmgww2.entity.EntityJPN_ShipB;
import hmgww2.entity.EntityJPN_Tank;
import hmgww2.entity.EntityUSA_ShipB;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.MinecraftForge;

public class RenderUSA_ShipB extends Render {

	private static final ResourceLocation skeletonTexturesz = new ResourceLocation("hmgww2:textures/mob/usa/BattleShipUSA.png");
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/mob/usa/BattleShipUSA.obj"));
	private float scale;

	public RenderUSA_ShipB() {
		this.scale = 2;
	}

	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		this.doRender((EntityUSA_ShipB) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
	float xsxs;
	public void doRender(EntityUSA_ShipB entity, double p_76986_2_, double p_76986_4_, double p_76986_6_,
			float entityYaw, float partialTicks) {
		this.bindEntityTexture(entity);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		if(entity.deathTicks > 0){
			GL11.glColor4f(0.1F, 0.1F, 0.1F, 1F);
		}
		
		GL11.glRotatef(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(0, 2.5F, 0);
		this.renderAngle(entity, 1);
		GL11.glTranslatef(0, -2.5F, 0);
			tankk.renderPart("mat1");
			if(entity.getMobMode() == 0){
				tankk.renderPart("mat30");
			}
			{
				GL11.glPushMatrix();//glstart
				GL11.glTranslatef(0F, 5.9F, -8F);
//				if(entity.combattask_2)
//				{
//				GL11.glRotatef(180.0F - entity.rotation_2 - (180.0F - entityYaw), 0.0F, 1.0F, 0.0F);
//				}
//				else{
//					GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
//				}
				GL11.glTranslatef(-0F, -5.9F, 8F);
					tankk.renderPart("mat7");
					GL11.glTranslatef(0F, 5.9F, -7F);
//					GL11.glRotatef(entity.rotationp_2, 1.0F, 0.0F, 0.0F);
					GL11.glTranslatef(-0F, -5.9F, 7F);
					tankk.renderPart("mat8");
				GL11.glPopMatrix();//glend
			}
			{
				GL11.glPushMatrix();//glstart
				GL11.glTranslatef(4F, 5.5F, 0.25F);
//				if(entity.combattask_3)
//				{
//				GL11.glRotatef(180.0F - entity.rotation_3 - (180.0F - entityYaw), 0.0F, 1.0F, 0.0F);
//				}
//				else{
//					GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
//				}
				GL11.glTranslatef(-4F, -5.5F, -0.25F);
					tankk.renderPart("mat14");
					GL11.glTranslatef(4F, 5.5F, 1.5F);
//					GL11.glRotatef(entity.rotationp_3, 1.0F, 0.0F, 0.0F);
					GL11.glTranslatef(-4F, -5.5F, -1.5F);
					tankk.renderPart("mat15");
				GL11.glPopMatrix();//glend
			}
			{
				GL11.glPushMatrix();//glstart
				GL11.glTranslatef(-4F, 5.5F, 0.25F);
//				if(entity.combattask_4)
//				{
//				GL11.glRotatef(180.0F - entity.rotation_4 - (180.0F - entityYaw), 0.0F, 1.0F, 0.0F);
//				}
//				else{
//					GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
//				}
				GL11.glTranslatef(4F, -5.5F, -0.25F);
					tankk.renderPart("mat16");
					GL11.glTranslatef(-4F, 5.5F, 1.5F);
//					GL11.glRotatef(entity.rotationp_4, 1.0F, 0.0F, 0.0F);
					GL11.glTranslatef(4F, -5.5F, -1.5F);
					tankk.renderPart("mat17");
				GL11.glPopMatrix();//glend
			}
			{
				GL11.glPushMatrix();//glstart
				GL11.glTranslatef(3.5F, 7.2F, -5F);
//				if(entity.combattask_5)
//				{
//				GL11.glRotatef(180.0F - entity.rotation_5 - (180.0F - entityYaw), 0.0F, 1.0F, 0.0F);
//				}
//				else{
//					GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
//				}
				GL11.glTranslatef(-3.5F, -7.2F, 5F);
					tankk.renderPart("mat10");
					GL11.glTranslatef(3.5F, 7.2F, -5F);
//					GL11.glRotatef(entity.rotationp_5, 1.0F, 0.0F, 0.0F);
					GL11.glTranslatef(-3.5F, -7.2F, 5F);
					tankk.renderPart("mat11");
				GL11.glPopMatrix();//glend
			}
			{
				GL11.glPushMatrix();//glstart
				GL11.glTranslatef(-3.5F, 7.2F, -5F);
//				if(entity.combattask_6)
//				{
//				GL11.glRotatef(180.0F - entity.rotation_6 - (180.0F - entityYaw), 0.0F, 1.0F, 0.0F);
//				}
//				else{
//					GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
//				}
				GL11.glTranslatef(3.5F, -7.2F, 5F);
					tankk.renderPart("mat12");
					GL11.glTranslatef(-3.5F, 7.2F, -5F);
//					GL11.glRotatef(entity.rotationp_5, 1.0F, 0.0F, 0.0F);
					GL11.glTranslatef(3.5F, -7.2F, 5F);
					tankk.renderPart("mat13");
				GL11.glPopMatrix();//glend
			}
	//	GL11.glRotatef(-(180.0F - entityYaw), 0.0F, 1.0F, 0.0F);
		
		{
			GL11.glTranslatef(0F, 5.9F, 5F);
//			GL11.glRotatef(180.0F -entity.rotation - (180.0F - entityYaw), 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0F, -5.9F, -5F);
			tankk.renderPart("mat5");
			GL11.glTranslatef(0F, 5.9F, 6F);
//			GL11.glRotatef(entity.rotationp, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0F, -5.9F, -6F);
			tankk.renderPart("mat6");
		}
		
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		
		
	}

	private void renderAngle(EntityUSA_ShipB p_76986_1_, int i){
		{
//			GL11.glRotatef(-(p_76986_1_.angletime), 1.0F, 0.0F, 0.0F);
		}
	}
	
	public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_,
			float p_76986_8_, float p_76986_9_) {
		this.doRender((EntityUSA_ShipB) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving) {
		return this.skeletonTexturesz;
	}

}
