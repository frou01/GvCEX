package handmadeguns.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import handmadeguns.entity.bullets.HMGEntityBulletBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
//import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import static handmadeguns.HMGAddBullets.modellist;

@SideOnly(Side.CLIENT)
public class HMGRenderBullet extends Render
{
//    private static final ResourceLocation arrowTextures = new ResourceLocation("handmadeguns:textures/entity/bullet.png");
    private static final ResourceLocation trailtexture = new ResourceLocation("handmadeguns:textures/entity/laser.png");
    private ResourceLocation arrowTextures = new ResourceLocation("handmadeguns:textures/entity/bulletmat.png");
    private IModelCustom modeling = AdvancedModelLoader.loadModel(new ResourceLocation("handmadeguns:textures/entity/bullet3d.obj"));

    private static final String __OBFID = "CL_00000978";

   

    public void doRender(HMGEntityBulletBase entity, double p_180551_2_, double p_180551_4_, double p_180551_6_, float p_180551_8_, float partialTicks)
    {
        if(entity!=null) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) p_180551_2_, (float) p_180551_4_+0.1f, (float) p_180551_6_);
            GL11.glRotatef( - (entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks), 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 1.0F, 0.0F, 0.0F);
//            System.out.println("debug " + p_180551_1_.rotationPitch);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            if(entity.modelid == -1) {
                this.bindEntityTexture(entity);
                modeling.renderAll();
            }
            else {
                ModelSetAndData modelSetAndData = modellist.get(entity.modelid);
                if (modelSetAndData != null) {
                    GL11.glScalef(0.1f,0.1f,0.1f);
                    this.bindTexture(modelSetAndData.texture);
                    IModelCustom custom = modelSetAndData.model;
                    GL11.glScalef(modelSetAndData.scale,modelSetAndData.scale,modelSetAndData.scale);
                    if (custom != null) {
                        custom.renderAll();
                    }
                }else {
                    this.bindEntityTexture(entity);
                    GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                    modeling.renderAll();
                }
            }
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
        }
    }

    protected ResourceLocation getEntityTexture(HMGEntityBulletBase p_180550_1_)
    {
        return arrowTextures;
    }

    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return this.getEntityTexture((HMGEntityBulletBase)p_110775_1_);
    }

    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((HMGEntityBulletBase)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}