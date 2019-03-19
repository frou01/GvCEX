
package handmadeguns.client.render;


import handmadeguns.entity.HMGEntityItemMount;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;



public class HMGRenderItemMount extends Render
{

	private static final ResourceLocation skeletonTextures = new ResourceLocation("gvcguns:textures/entity/target.png");
	private static final ResourceLocation skeletonTexturesz = new ResourceLocation("textures/blocks/planks_oak.png");
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("handmadeguns:textures/model/rack.obj"));


	public HMGRenderItemMount()
	{
		//super(new ModelBiped(), 0.5F);
	}
	public void doRender(HMGEntityItemMount p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		GL11.glPushMatrix();
		this.bindEntityTexture(p_76986_1_);
		GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		this.RoteMuki(p_76986_1_);
		tankk.renderPart("mat1");


		GL11.glPopMatrix();

		if (p_76986_1_.getDisplayedItem() != null) {
			GL11.glPushMatrix();
			GL11.glColor3f(1.0F, 1.0F, 1.0F);
			GL11.glColor4f(1, 1, 1, 1.0F);
			GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
			GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);

			switch (p_76986_1_.getMuki())
			{
				case 0:
					GL11.glTranslatef(0.1F, 0.3F, 0.3F);
					break;
				case 1:
					GL11.glTranslatef(0.3F, 0.3F, 0.1F);
					break;
				case 2:
					GL11.glTranslatef(-0.1F, 0.3F, -0.3F);
					break;
				case 3:
					GL11.glTranslatef(-0.3F, 0.3F, -0.1F);
			}
			GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);//-45
			GL11.glRotatef(5F, 1.0F, 0.0F, 0.0F);//-10
			GL11.glRotatef(-2.5F, 0.0F, 0.0F, 1.0F);//5
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			//GL11.glScalef(1, 1, -1);
			this.RoteMuki(p_76986_1_);
			this.renderManager.itemRenderer.renderItem(p_76986_1_, p_76986_1_.getDisplayedItem(), 0);
			GL11.glPopMatrix();
		}
		if (p_76986_1_.getDisplayedItem2() != null) {
			GL11.glPushMatrix();
			GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
			GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
			//GL11.glTranslatef(0.1F, 0.40F, -0.0F);
			switch (p_76986_1_.getMuki())
			{
				case 0:
					GL11.glTranslatef(0.1F, 0.4F, 0.0F);
					break;
				case 1:
					GL11.glTranslatef(0.0F, 0.4F, 0.0F);
					break;
				case 2:
					GL11.glTranslatef(-0.1F, 0.4F, -0.0F);
					break;
				case 3:
					GL11.glTranslatef(-0.0F, 0.4F, -0.0F);
			}
			GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);//-45
			GL11.glRotatef(5F, 1.0F, 0.0F, 0.0F);//-10
			GL11.glRotatef(-2.5F, 0.0F, 0.0F, 1.0F);//5
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			this.RoteMuki(p_76986_1_);
			
			this.renderManager.itemRenderer.renderItem(p_76986_1_, p_76986_1_.getDisplayedItem2(), 0);
			GL11.glPopMatrix();
		}
		if (p_76986_1_.getDisplayedItem3() != null) {
			GL11.glPushMatrix();
			GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
			GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
			//GL11.glTranslatef(0.1F, 0.50F, -0.3F);
			switch (p_76986_1_.getMuki())
			{
				case 0:
					GL11.glTranslatef(0.1F, 0.5F, -0.3F);
					break;
				case 1:
					GL11.glTranslatef(-0.3F, 0.5F, 0.1F);
					break;
				case 2:
					GL11.glTranslatef(0.1F, 0.5F, 0.3F);
					break;
				case 3:
					GL11.glTranslatef(0.3F, 0.5F, -0.1F);
			}
			GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);//-45
			GL11.glRotatef(5F, 1.0F, 0.0F, 0.0F);//-10
			GL11.glRotatef(-2.5F, 0.0F, 0.0F, 1.0F);//5
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			this.RoteMuki(p_76986_1_);
			
			this.renderManager.itemRenderer.renderItem(p_76986_1_, p_76986_1_.getDisplayedItem3(), 0);
			GL11.glPopMatrix();
		}

//		//this.renderManager.itemRenderer.renderItem(p_76986_1_, new ItemStack(GameRegistry.findItem("HandmadeGuns", "AR_sample")), 0);
//        /*for (int i1 = 0; i1 < p_76986_1_.getSizeInventory(); ++i1) {
//			ItemStack itemstacki = p_76986_1_.getStackInSlot(i1);
//			if (i1 == 1 && itemstacki != null) {
//				GL11.glPushMatrix();
//				GL11.glColor3f(1.0F, 1.0F, 1.0F);
//				GL11.glColor4f(1, 1, 1, 1.0F);
//				GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
//		        GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
//
//			switch (p_76986_1_.getMuki())
//	        {
//	            case 0:
//	            	GL11.glTranslatef(0.1F, 0.3F, 0.3F);
//	                break;
//	            case 1:
//	            	GL11.glTranslatef(0.3F, 0.3F, 0.1F);
//	                break;
//	            case 2:
//	            	GL11.glTranslatef(-0.1F, 0.3F, -0.3F);
//	                break;
//	            case 3:
//	            	GL11.glTranslatef(-0.3F, 0.3F, -0.1F);
//	        }
//				GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);//-45
//				GL11.glRotatef(5F, 1.0F, 0.0F, 0.0F);//-10
//				GL11.glRotatef(-2.5F, 0.0F, 0.0F, 1.0F);//5
//				 GL11.glScalef(0.5F, 0.5F, 0.5F);
//				 //GL11.glScalef(1, 1, -1);
//				 this.RoteMuki(p_76986_1_);
//					this.renderManager.itemRenderer.renderItem(p_76986_1_, itemstacki, 0);
//				GL11.glPopMatrix();
//			}
//			if (i1 == 2 && itemstacki != null) {
//				GL11.glPushMatrix();
//				GL11.glColor3f(1.0F, 1.0F, 1.0F);
//				GL11.glColor4f(1, 1, 1, 1.0F);
//				GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
//		        GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
//			//GL11.glTranslatef(0.1F, 0.40F, -0.0F);
//			switch (p_76986_1_.getMuki())
//	        {
//	            case 0:
//	            	GL11.glTranslatef(0.1F, 0.4F, 0.0F);
//	                break;
//	            case 1:
//	            	GL11.glTranslatef(0.0F, 0.4F, 0.0F);
//	                break;
//	            case 2:
//	            	GL11.glTranslatef(-0.1F, 0.4F, -0.0F);
//	                break;
//	            case 3:
//	            	GL11.glTranslatef(-0.0F, 0.4F, -0.0F);
//	        }
//				GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);//-45
//				GL11.glRotatef(5F, 1.0F, 0.0F, 0.0F);//-10
//				GL11.glRotatef(-2.5F, 0.0F, 0.0F, 1.0F);//5
//				 GL11.glScalef(0.5F, 0.5F, 0.5F);
//				 this.RoteMuki(p_76986_1_);
//					this.renderManager.itemRenderer.renderItem(p_76986_1_, itemstacki, 0);
//				GL11.glPopMatrix();
//		    }
//			if (i1 == 3 && itemstacki != null) {
//				GL11.glPushMatrix();
//				GL11.glColor3f(1.0F, 1.0F, 1.0F);
//				GL11.glColor4f(1, 1, 1, 1.0F);
//				GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
//		        GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
//			//GL11.glTranslatef(0.1F, 0.50F, -0.3F);
//			switch (p_76986_1_.getMuki())
//	        {
//	            case 0:
//	            	GL11.glTranslatef(0.1F, 0.5F, -0.3F);
//	                break;
//	            case 1:
//	            	GL11.glTranslatef(-0.3F, 0.5F, 0.1F);
//	                break;
//	            case 2:
//	            	GL11.glTranslatef(0.1F, 0.5F, 0.3F);
//	                break;
//	            case 3:
//	            	GL11.glTranslatef(0.3F, 0.5F, -0.1F);
//	        }
//				GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);//-45
//				GL11.glRotatef(5F, 1.0F, 0.0F, 0.0F);//-10
//				GL11.glRotatef(-2.5F, 0.0F, 0.0F, 1.0F);//5
//				 GL11.glScalef(0.5F, 0.5F, 0.5F);
//				 this.RoteMuki(p_76986_1_);
//					this.renderManager.itemRenderer.renderItem(p_76986_1_, itemstacki, 0);
//				GL11.glPopMatrix();
//		    }
//        }*/
	}

	public void RoteMuki(HMGEntityItemMount p_76986_1_)
	{
		switch (p_76986_1_.getMuki())
		{
			case 0:
				GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
				break;
			case 1:
				GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
				break;
			case 2:
				GL11.glRotatef(270F, 0.0F, 1.0F, 0.0F);
				break;
			case 3:
				GL11.glRotatef(0F, 0.0F, 1.0F, 0.0F);
		}
	}


	protected ResourceLocation getEntityTexture(HMGEntityItemMount par1EntityLiving)
	{
		return this.skeletonTexturesz;
	}
	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity p_110775_1_)
	{
		return this.getEntityTexture((HMGEntityItemMount)p_110775_1_);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((HMGEntityItemMount)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
}