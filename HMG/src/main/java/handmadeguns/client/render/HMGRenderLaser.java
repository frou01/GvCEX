package handmadeguns.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import handmadeguns.entity.HMGEntityLaser;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
//import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import static java.lang.Math.PI;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;

@SideOnly(Side.CLIENT)
public class HMGRenderLaser extends Render
{
    private static final ResourceLocation arrowTextures = new ResourceLocation("handmadeguns:textures/entity/laser.png");
    private static final ResourceLocation enderDragonCrystalBeamTextures = new ResourceLocation("handmadeguns:textures/entity/beamright.png");
    private static final String __OBFID = "CL_00000978";

   

    public void doRender(HMGEntityLaser p_180551_1_, double p_180551_2_, double p_180551_4_, double p_180551_6_, float p_180551_8_, float p_180551_9_)
    {
        this.bindEntityTexture(p_180551_1_);
//    	System.out.println("debug");
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_180551_2_, (float)p_180551_4_, (float)p_180551_6_);
        GL11.glRotatef(180.0F - this.renderManager.playerViewY+90, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 0.0F, 0.0F, 1.0F);
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL_BLEND);
        OpenGlHelper.glBlendFunc(GL_SRC_ALPHA, GL_ONE,1,0);
        GL11.glDepthMask(false);
        GL11.glColor4f(1, 1, 1, 1f);


        double angleZ = PI/3;
        double angleZold = 0;
        double angleY = PI /3;
        double angleYold = 0;
        float xPos = 0F;
        float yPos = 0F;
        float xPos2 = 0F;
        float yPos2 = 0F;
        //for (int i = 0; i < 4; ++i)
        for (int i = 0; i < 6; i++) {
            xPos = (float) Math.cos(angleZ);
            yPos = (float) Math.sin(angleZ);
            xPos2 = (float) Math.cos(angleZold);
            yPos2 = (float) Math.sin(angleZold);
            tessellator.setColorRGBA_F(1, 1, 1, 0.8f);
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, 0.0F);
            tessellator.addVertexWithUV(  -0.1,0,0  ,  0.0F, 0.0F);
            tessellator.addVertexWithUV(  -0.1,xPos*0.05,yPos*0.05  , 0.0F, 1.0F);
            tessellator.addVertexWithUV(  -0.1,xPos2*0.05,yPos2*0.05, 1.0F, 1.0F);
            tessellator.addVertexWithUV(  -0.1,0,0,  1.0F, 0.0F);
//            tessellator.addVertex(  10,xPos2   , yPos2);
//            tessellator.addVertex(  0 , 0 , 0);
//            tessellator.addVertex(  0, 0, 0);
//            tessellator.addVertex(  10,xPos    , yPos);
            tessellator.draw();

            angleZold = angleZ;
            angleZ += PI / 3;
        }
        //GL11.glTranslatef(-4.0F, 0.0F, 20.0F);
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glDepthMask(true);
        GL11.glDisable(GL_BLEND);
        GL11.glPopMatrix();
    }

    protected ResourceLocation getEntityTexture(HMGEntityLaser p_180550_1_)
    {
        return arrowTextures;
    }

    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return this.getEntityTexture((HMGEntityLaser)p_110775_1_);
    }

    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((HMGEntityLaser)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}