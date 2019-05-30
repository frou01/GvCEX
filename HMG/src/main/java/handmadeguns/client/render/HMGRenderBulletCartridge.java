package handmadeguns.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import handmadeguns.entity.bullets.HMGEntityBulletCartridge;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
//import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import static handmadeguns.HMGAddBullets.modellist;
import static org.lwjgl.opengl.GL11.*;

@SideOnly(Side.CLIENT)
public class HMGRenderBulletCartridge extends Render
{
    private static final ResourceLocation arrowTextures = new ResourceLocation("handmadeguns:textures/entity/bulletcartridge.png");
    private static final IModelCustom modeling = AdvancedModelLoader.loadModel(new ResourceLocation("handmadeguns:textures/entity/bulletcartridge.obj"));
    private static final ResourceLocation arrowTextures2 = new ResourceLocation("handmadeguns:textures/entity/bulletcartridge2.png");
    private static final IModelCustom modeling2 = AdvancedModelLoader.loadModel(new ResourceLocation("handmadeguns:textures/entity/bulletcartridge2.obj"));
    private static final ResourceLocation arrowTextures3 = new ResourceLocation("handmadeguns:textures/entity/bulletcartridge3.png");
    private static final IModelCustom modeling3 = AdvancedModelLoader.loadModel(new ResourceLocation("handmadeguns:textures/entity/bulletcartridge3.obj"));
    private static final ResourceLocation arrowTextures4 = new ResourceLocation("handmadeguns:textures/entity/bulletcartridge4.png");
    private static final IModelCustom modeling4 = AdvancedModelLoader.loadModel(new ResourceLocation("handmadeguns:textures/entity/bulletcartridge4.obj"));
    private static final ResourceLocation arrowTextures5 = new ResourceLocation("handmadeguns:textures/entity/magazine.png");
    private static final IModelCustom modeling5 = AdvancedModelLoader.loadModel(new ResourceLocation("handmadeguns:textures/entity/magazine.obj"));

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(HMGEntityBulletCartridge p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glRotatef(p_76986_1_.prevRotationYaw + (p_76986_1_.rotationYaw - p_76986_1_.prevRotationYaw) * p_76986_9_ + 180, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(p_76986_1_.prevRotationPitch + (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_, 1.0F, 0.0F, 0.0F);
        GL11.glEnable(GL_BLEND);
        GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        this.bindEntityTexture(p_76986_1_);


        //GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);

        int pass = MinecraftForgeClient.getRenderPass();
        if(pass == 0) {
            GL11.glNormal3f(0,0,0);

            int type = p_76986_1_.cartType;

            if (type == 1) {
                GL11.glScalef(0.5f,0.5f,0.5f);
                modeling.renderAll();
            } else if (type == 2) {
                GL11.glScalef(0.5f,0.5f,0.5f);
                modeling2.renderAll();
            } else if (type == 3) {
                GL11.glScalef(0.5f,0.5f,0.5f);
                modeling3.renderAll();
            } else if (type == 4) {
                GL11.glScalef(0.5f,0.5f,0.5f);
                modeling4.renderAll();
            } else if (type == 5) {
                GL11.glScalef(0.5f,0.5f,0.5f);
                modeling5.renderAll();
            } else if (type == -1) {
//            System.out.println("" + p_76986_1_.modelid);
                GL11.glScalef(0.1f,0.1f,0.1f);
                ModelSetAndData modelSetAndData = modellist.get(p_76986_1_.modelid);
                if (modelSetAndData != null) {
                    this.bindTexture(modelSetAndData.texture);
                    IModelCustom custom = modelSetAndData.model;
                    GL11.glScalef(modelSetAndData.scale,modelSetAndData.scale,modelSetAndData.scale);
                    if (custom != null) {
                        custom.renderAll();
                    }
                }
            } else {
                modeling.renderAll();
            }
        }
        GL11.glPopMatrix();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(HMGEntityBulletCartridge p_110775_1_)
    {
    	int type = p_110775_1_.cartType;
    	if(type == 3){
        return arrowTextures3;
    	}else if(type == 2){
    		return arrowTextures2;
    	}else if(type == 4){
    		return arrowTextures4;
    	}else if(type == 5){
    		return arrowTextures5;
    	}else{
    		return arrowTextures;
    	}
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return this.getEntityTexture((HMGEntityBulletCartridge)p_110775_1_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((HMGEntityBulletCartridge)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}