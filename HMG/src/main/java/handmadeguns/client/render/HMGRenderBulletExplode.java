package handmadeguns.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import handmadeguns.entity.bullets.HMGEntityBulletBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
//import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import static handmadeguns.HMGAddBullets.modellist;

@SideOnly(Side.CLIENT)
public class HMGRenderBulletExplode extends Render
{
    private static final ResourceLocation arrowTextures = new ResourceLocation("handmadeguns:textures/entity/bulletrpg.png");
    private static final String __OBFID = "CL_00000978";

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(HMGEntityBulletBase entity, double p_180551_2_, double p_180551_4_, double p_180551_6_, float p_180551_8_, float partialTicks)
    {
        GL11.glPushMatrix();
        if(entity.modelid == -1) {
            this.bindEntityTexture(entity);
            GL11.glTranslatef((float)p_180551_2_, (float)p_180551_4_, (float)p_180551_6_);
            GL11.glRotatef(-90 - (entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks), 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch)), 0.0F, 0.0F, 1.0F);
            Tessellator tessellator = Tessellator.instance;
            byte b0 = 0;
            float f2 = 0.0F;
            float f3 = 0.5F;
            float f4 = (float)(0 + b0 * 10) / 32.0F;
            float f5 = (float)(5 + b0 * 10) / 32.0F;
            float f6 = 0.0F;
            float f7 = 0.15625F;
            float f8 = (float)(5 + b0 * 10) / 32.0F;
            float f9 = (float)(10 + b0 * 10) / 32.0F;
            float f10 = 0.05625F;
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);


            GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(f10, f10, f10);
            GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
            GL11.glNormal3f(f10, 0.0F, 0.0F);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(-7.0D, -2.0D, -2.0D, (double)f6, (double)f8);
            tessellator.addVertexWithUV(-7.0D, -2.0D, 2.0D, (double)f7, (double)f8);
            tessellator.addVertexWithUV(-7.0D, 2.0D, 2.0D, (double)f7, (double)f9);
            tessellator.addVertexWithUV(-7.0D, 2.0D, -2.0D, (double)f6, (double)f9);
            tessellator.draw();
            GL11.glNormal3f(-f10, 0.0F, 0.0F);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(-7.0D, 2.0D, -2.0D, (double)f6, (double)f8);
            tessellator.addVertexWithUV(-7.0D, 2.0D, 2.0D, (double)f7, (double)f8);
            tessellator.addVertexWithUV(-7.0D, -2.0D, 2.0D, (double)f7, (double)f9);
            tessellator.addVertexWithUV(-7.0D, -2.0D, -2.0D, (double)f6, (double)f9);
            tessellator.draw();

            for (int i = 0; i < 4; ++i)
            {
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glNormal3f(0.0F, 0.0F, f10);
                tessellator.startDrawingQuads();
                tessellator.addVertexWithUV(-8.0D, -2.0D, 0.0D, (double)f2, (double)f4);
                tessellator.addVertexWithUV(8.0D, -2.0D, 0.0D, (double)f3, (double)f4);
                tessellator.addVertexWithUV(8.0D, 2.0D, 0.0D, (double)f3, (double)f5);
                tessellator.addVertexWithUV(-8.0D, 2.0D, 0.0D, (double)f2, (double)f5);
                tessellator.draw();
            }

            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        } else {
            ModelSetAndData modelSetAndData = modellist.get(entity.modelid);
            if (modelSetAndData != null) {
                GL11.glTranslatef((float)p_180551_2_, (float)p_180551_4_, (float)p_180551_6_);
                GL11.glRotatef( - (entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks), 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 1.0F, 0.0F, 0.0F);
                GL11.glScalef(0.1f,0.1f,0.1f);
                this.bindTexture(modelSetAndData.texture);
                IModelCustom custom = modelSetAndData.model;
                GL11.glScalef(modelSetAndData.scale,modelSetAndData.scale,modelSetAndData.scale);
                if (custom != null) {
                    custom.renderAll();
                }
            }else{
                GL11.glTranslatef((float)p_180551_2_, (float)p_180551_4_, (float)p_180551_6_);
                this.bindEntityTexture(entity);
                GL11.glRotatef( - (entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks), 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 1.0F, 0.0F, 0.0F);
                Tessellator tessellator = Tessellator.instance;
                byte b0 = 0;
                float f2 = 0.0F;
                float f3 = 0.5F;
                float f4 = (float)(0 + b0 * 10) / 32.0F;
                float f5 = (float)(5 + b0 * 10) / 32.0F;
                float f6 = 0.0F;
                float f7 = 0.15625F;
                float f8 = (float)(5 + b0 * 10) / 32.0F;
                float f9 = (float)(10 + b0 * 10) / 32.0F;
                float f10 = 0.05625F;
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);


                GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
                GL11.glScalef(f10, f10, f10);
                GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
                GL11.glNormal3f(f10, 0.0F, 0.0F);
                tessellator.startDrawingQuads();
                tessellator.addVertexWithUV(-7.0D, -2.0D, -2.0D, (double)f6, (double)f8);
                tessellator.addVertexWithUV(-7.0D, -2.0D, 2.0D, (double)f7, (double)f8);
                tessellator.addVertexWithUV(-7.0D, 2.0D, 2.0D, (double)f7, (double)f9);
                tessellator.addVertexWithUV(-7.0D, 2.0D, -2.0D, (double)f6, (double)f9);
                tessellator.draw();
                GL11.glNormal3f(-f10, 0.0F, 0.0F);
                tessellator.startDrawingQuads();
                tessellator.addVertexWithUV(-7.0D, 2.0D, -2.0D, (double)f6, (double)f8);
                tessellator.addVertexWithUV(-7.0D, 2.0D, 2.0D, (double)f7, (double)f8);
                tessellator.addVertexWithUV(-7.0D, -2.0D, 2.0D, (double)f7, (double)f9);
                tessellator.addVertexWithUV(-7.0D, -2.0D, -2.0D, (double)f6, (double)f9);
                tessellator.draw();

                for (int i = 0; i < 4; ++i)
                {
                    GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glNormal3f(0.0F, 0.0F, f10);
                    tessellator.startDrawingQuads();
                    tessellator.addVertexWithUV(-8.0D, -2.0D, 0.0D, (double)f2, (double)f4);
                    tessellator.addVertexWithUV(8.0D, -2.0D, 0.0D, (double)f3, (double)f4);
                    tessellator.addVertexWithUV(8.0D, 2.0D, 0.0D, (double)f3, (double)f5);
                    tessellator.addVertexWithUV(-8.0D, 2.0D, 0.0D, (double)f2, (double)f5);
                    tessellator.draw();
                }

                GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            }

        }

        GL11.glPopMatrix();

//        if(entity.trail){
//            GL11.glPushMatrix();
//            GL11.glDisable(GL11.GL_CULL_FACE);
//            GL11.glTranslatef((float) p_180551_2_, (float) p_180551_4_+0.1f, (float) p_180551_6_);
//            GL11.glRotatef(entity.rotationYaw, 0.0F, 1.0F, 0.0F);
//            GL11.glRotatef(-entity.rotationPitch, 1.0F, 0.0F, 0.0F);
////            System.out.println("debug " + entity.rotationPitch);
//            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
//            GL11.glScalef(1.0F, 1.0F, 1.0F);
//            GL11.glNormal3f(1.0F, 0.0F, 0.0F);
//
//
//            this.bindTexture(trailtexture);
//            Tessellator tessellator = Tessellator.instance;
//
//            Vec3 postoprepos = Vec3.createVectorHelper(
//                    entity.posX - entity.prevPosX,
//                    entity.posY - entity.prevPosY,
//                    entity.posZ - entity.prevPosZ);
//
//            double taildist = postoprepos.lengthVector();
//            taildist *= p_180551_9_;
//
//            tessellator.startDrawingQuads();
//            tessellator.addVertexWithUV(
//                    0,
//                    0.2,
//                    0, (double)0, (double)0);
//            tessellator.addVertexWithUV(
//                    0,
//                    -0.2,
//                    0, (double)0, (double)1);
//            tessellator.addVertexWithUV(
//                    0,
//                    -0.2,
//                    -taildist, (double)1, (double)1);
//            tessellator.addVertexWithUV(
//                    0,
//                    0.2,
//                    -taildist, (double)1, (double)0);
//            tessellator.draw();
//
//
//
//            tessellator.startDrawingQuads();
//            tessellator.addVertexWithUV(
//                    0.2,
//                    0,
//                    0, (double)0, (double)0);
//            tessellator.addVertexWithUV(
//                    -0.2,
//                    0,
//                    0, (double)0, (double)1);
//            tessellator.addVertexWithUV(
//                    -0.2,
//                    0,
//                    -taildist, (double)1, (double)1);
//            tessellator.addVertexWithUV(
//                    0.2,
//                    0,
//                    -taildist, (double)1, (double)0);
//            tessellator.draw();
//
//            GL11.glTranslatef(0,0,(float)-taildist);
//            GL11.glRotatef(-entity.rotationYaw + entity.prevRotationYaw, 0.0F, 1.0F, 0.0F);
//            GL11.glRotatef(+entity.rotationPitch - entity.prevRotationPitch, 1.0F, 0.0F, 0.0F);
//
//            Vec3 prepostopre2pos = Vec3.createVectorHelper(
//                    entity.prevPosX - entity.prepos[0][0],
//                    entity.prevPosY - entity.prepos[0][1],
//                    entity.prevPosZ - entity.prepos[0][2]);
//
//            double taildist2 = prepostopre2pos.lengthVector();
//            taildist2 *= 1 - p_180551_9_;
//
//            tessellator.startDrawingQuads();
//            tessellator.addVertexWithUV(
//                    0,
//                    0.2,
//                    0, (double)0, (double)0);
//            tessellator.addVertexWithUV(
//                    0,
//                    -0.2,
//                    0, (double)0, (double)1);
//            tessellator.addVertexWithUV(
//                    0,
//                    -0.2,
//                    -taildist2, (double)1, (double)1);
//            tessellator.addVertexWithUV(
//                    0,
//                    0.2,
//                    -taildist2, (double)1, (double)0);
//            tessellator.draw();
//
//
//
//            tessellator.startDrawingQuads();
//            tessellator.addVertexWithUV(
//                    0.2,
//                    0,
//                    0, (double)0, (double)0);
//            tessellator.addVertexWithUV(
//                    -0.2,
//                    0,
//                    0, (double)0, (double)1);
//            tessellator.addVertexWithUV(
//                    -0.2,
//                    0,
//                    -taildist2, (double)1, (double)1);
//            tessellator.addVertexWithUV(
//                    0.2,
//                    0,
//                    -taildist2, (double)1, (double)0);
//            tessellator.draw();
//
//
//
//
//            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
//            GL11.glPopMatrix();
//            GL11.glEnable(GL11.GL_CULL_FACE);
//        }
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(HMGEntityBulletBase p_110775_1_)
    {
        return arrowTextures;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return this.getEntityTexture((HMGEntityBulletBase)p_110775_1_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((HMGEntityBulletBase)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}