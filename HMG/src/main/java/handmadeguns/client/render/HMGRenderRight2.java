package handmadeguns.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import handmadeguns.entity.HMGEntityLight2;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
//import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import static java.lang.Math.PI;
import static org.lwjgl.opengl.GL11.*;

@SideOnly(Side.CLIENT)
public class HMGRenderRight2 extends Render
{
    private static final ResourceLocation arrowTextures = new ResourceLocation("handmadeguns:textures/entity/right.png");
    private static final ResourceLocation enderDragonCrystalBeamTextures = new ResourceLocation("handmadeguns:textures/entity/beamright.png");
    private static final String __OBFID = "CL_00000978";

   

    public void doRender(HMGEntityLight2 p_180551_1_, double p_180551_2_, double p_180551_4_, double p_180551_6_, float p_180551_8_, float p_180551_9_)
    {
    	this.bindEntityTexture(p_180551_1_);
//    	System.out.println("debug");
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_180551_2_, (float)p_180551_4_, (float)p_180551_6_);
        GL11.glRotatef(p_180551_1_.rotationYaw- 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(p_180551_1_.rotationPitch, 0.0F, 0.0F, 1.0F);
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL_BLEND);
        OpenGlHelper.glBlendFunc(GL_SRC_ALPHA, GL_ONE,1,0);
        GL11.glDepthMask(false);
        GL11.glColor4f(1, 1, 1, 0.25F);


        double angleZ = 0F;
        double angleZold = -PI/6;
        float xPos = 0F;
        float yPos = 0F;
        float xPos2 = 0F;
        float yPos2 = 0F;
        //for (int i = 0; i < 4; ++i)
        for(int i = 0; i <= 11; i++)
        {
            xPos = (float)Math.cos(angleZ) * 3;
            yPos = (float)Math.sin(angleZ) * 3;
            xPos2 = (float)Math.cos(angleZold)* 3;
            yPos2 = (float)Math.sin(angleZold)* 3;
            tessellator.setColorRGBA_F(1,1,1,0.15f);
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, 0.0F);
            tessellator.addVertexWithUV(  0,0,0   ,  0.0F, 0.0F);
            tessellator.addVertexWithUV(  10 , xPos,yPos   , 0.0F, 1.0F);
            tessellator.addVertexWithUV(  10,  xPos2,yPos2     , 1.0F, 1.0F);
            tessellator.addVertexWithUV(  0,0,0   ,  1.0F, 0.0F);
//            tessellator.addVertex(  10,xPos2   , yPos2);
//            tessellator.addVertex(  0 , 0 , 0);
//            tessellator.addVertex(  0, 0, 0);
//            tessellator.addVertex(  10,xPos    , yPos);
            tessellator.draw();

            angleZold = angleZ;
            angleZ +=PI/6;
        }
        //GL11.glTranslatef(-4.0F, 0.0F, 20.0F);
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glDepthMask(true);
        GL11.glDisable(GL_BLEND);
        GL11.glPopMatrix();
//        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.15F);
    }


    /*
    public void doRender(HMGEntityRight p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
    	Minecraft minecraft = FMLClientHandler.instance().getClient();
		  World world = FMLClientHandler.instance().getWorldClient();
		  EntityPlayer entityplayer = minecraft.thePlayer;
    	
		  float f2 = (float)entityplayer.worldObj.rand.nextInt(100000) + p_76986_9_;
          float f3 = MathHelper.sin(f2 * 0.2F) / 2.0F + 0.5F;
          f3 = (f3 * f3 + f3) * 0.2F;
          float f4 = (float)(entityplayer.posX - p_76986_1_.posX - (p_76986_1_.prevPosX - p_76986_1_.posX) * (double)(1.0F - p_76986_9_));
          float f5 = (float)((double)f3 + entityplayer.posY - 1.0D - p_76986_1_.posY - (p_76986_1_.prevPosY - p_76986_1_.posY) * (double)(1.0F - p_76986_9_));
          float f6 = (float)(entityplayer.posZ - p_76986_1_.posZ - (p_76986_1_.prevPosZ - p_76986_1_.posZ) * (double)(1.0F - p_76986_9_));
          float f7 = MathHelper.sqrt_float(f4 * f4 + f6 * f6);
          float f8 = MathHelper.sqrt_float(f4 * f4 + f5 * f5 + f6 * f6);
          GL11.glPushMatrix();
            GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_ + 2.0F, (float)p_76986_6_);
            GL11.glRotatef((float)(-Math.atan2((double)f6, (double)f4)) * 180.0F / (float)Math.PI - 90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef((float)(-Math.atan2((double)f7, (double)f5)) * 180.0F / (float)Math.PI - 90.0F, 1.0F, 0.0F, 0.0F);
            Tessellator tessellator = Tessellator.instance;
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_CULL_FACE);
            this.bindTexture(enderDragonCrystalBeamTextures);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            float f9 = 0.0F - ((float)p_76986_1_.ticksExisted + p_76986_9_) * 0.01F;
            float f10 = MathHelper.sqrt_float(f4 * f4 + f5 * f5 + f6 * f6) / 32.0F - ((float)p_76986_1_.ticksExisted + p_76986_9_) * 0.01F;
            tessellator.startDrawing(5);
            byte b0 = 8;
            GL11.glTranslatef(-1.0F, 2.0F, 0.0F);
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            //GL11.glEnable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_CULL_FACE);
            //GL11.glShadeModel(GL11.GL_FLAT);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.15F);

            for (int i = 0; i <= b0; ++i)
            {
                float f11 = MathHelper.sin((float)(i % b0) * (float)Math.PI * 2.0F / (float)b0) * 0.75F;
                float f12 = MathHelper.cos((float)(i % b0) * (float)Math.PI * 2.0F / (float)b0) * 0.75F;
                float f13 = (float)(i % b0) * 1.0F / (float)b0;
                tessellator.setColorOpaque_I(0);
                tessellator.addVertexWithUV((double)(f11 * 0.2F), (double)(f12 * 0.2F), 0.0D, (double)f13, (double)f10);
                tessellator.setColorOpaque_I(16777215);
                tessellator.addVertexWithUV((double)f11, (double)f12, (double)f8, (double)f13, (double)f9);
            }

            tessellator.draw();
           
            RenderHelper.enableStandardItemLighting();
            GL11.glPopMatrix();
    }*/
    
    
    protected ResourceLocation getEntityTexture(HMGEntityLight2 p_180550_1_)
    {
        return arrowTextures;
    }

    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return this.getEntityTexture((HMGEntityLight2)p_110775_1_);
    }

    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((HMGEntityLight2)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}