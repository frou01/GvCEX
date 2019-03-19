
package hmggvcmob.render;


import hmggvcmob.entity.guerrilla.GVCEntityHeli;
import hmggvcmob.render.model.GVCModelHeli;
import hmggvcmob.render.model.WZ10;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;


public class GVCRenderWZ10AttackHeli extends Render
{
	
	private static final ResourceLocation skeletonTextures = new ResourceLocation("gvcmob:textures/mob/WZ-10.png");
	private float scale;
	private ModelBase model;
	private float modelscale;
 
 	public GVCRenderWZ10AttackHeli()
	{
		model = new WZ10();
		modelscale = 1;
 		this.scale = 1;
	}
	
	@Override
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks) {
		
		GL11.glPushMatrix();
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glEnable(GL_BLEND);
		GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glTranslatef((float) x, (float) y + 1.5f, (float) z);
		GL11.glScalef(0.1f,0.1f,0.1f);
		bindEntityTexture(entity);
		GL11.glDepthMask(true);
		model.render(entity,0,0,0,0,partialTicks,modelscale);
		GL11.glPopMatrix();
	}
	
	protected void preRenderScale(Entity par1EntityGhast, float par2)
    {
    }

 	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
    }
 	
	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return this.skeletonTextures; 
	} 
 } 
