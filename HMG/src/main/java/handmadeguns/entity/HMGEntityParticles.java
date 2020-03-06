package handmadeguns.entity;

import cpw.mods.fml.client.FMLClientHandler;
import handmadeguns.HMGParticles;
import handmadeguns.HandmadeGunsCore;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.*;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;
import static java.lang.Math.atan2;
import static java.lang.Math.sqrt;
import static org.lwjgl.opengl.GL11.*;

public class HMGEntityParticles extends EntityFX
{
//    public static ResourceLocation icon = new ResourceLocation("handmadeguns", "textures/entity/fire");
	public int fuse;
	public float animationspeed = 1;
	public boolean istrail;
	public static float particaltick;
    public EntityLivingBase thrower;
    public double power = 2.5D;
    public boolean flame = false;
    public boolean isglow = false;
    public boolean isrenderglow = false;
    public int id = 0;
    public String icon;
    public boolean disable_DEPTH_TEST = false;
    public boolean is3d = false;
    public boolean fixedsize = false;
    private ResourceLocation[] resourceLocation;

    /** Entity motion X */
    public double thisMotionX;
    /** Entity motion Y */
    public double thisMotionY;
    /** Entity motion Z */
    public double thisMotionZ;

    public float trailrotationyaw;
    public float trailrotationpitch;
    public float trailwidth;

    public boolean isfirstflame;

//	public HMGEntityParticles(World par1World)
//    {
//        super(par1World);
//        this.fuse = 1;
//    }

    public HMGEntityParticles(World par1World, double par2, double par4,
                              double par6) {
        super(par1World, par2, par4, par6);
        this.particleMaxAge = 1200;
        this.particleAlpha = 0.7f;
        this.particleScale = 2.0f;
        this.fuse = 1;
        this.renderDistanceWeight = 16384;
        isfirstflame = true;
    }
    public void setIcon(String icon){
        this.icon = icon;
        resourceLocation = new ResourceLocation[1];
        resourceLocation[0] = new ResourceLocation(icon+0 + ".png");
    }
    public void setIcon(String icon,int Number_of_sheets){
        this.icon = icon;
        resourceLocation = new ResourceLocation[Number_of_sheets];
        for(int i = 0;i<Number_of_sheets;i++){
            resourceLocation[i] = new ResourceLocation(icon + i + ".png");
        }
    }
    public void onUpdate()
    {
        this.setParticleIcon(HMGParticles.getInstance().getIcon(icon+(fuse/animationspeed)));
        int xTile = (int) this.prevPosX - 1;
        int yTile = (int) this.prevPosY - 1;
        int zTile = (int) this.prevPosZ - 1;
        if(HandmadeGunsCore.cfg_Flash && isglow ) {
            if(worldObj.getChunkFromBlockCoords(xTile,zTile).isChunkLoaded) {
                worldObj.func_147451_t(xTile, yTile, zTile);
                worldObj.func_147451_t(xTile - 1, yTile, zTile);
                worldObj.func_147451_t(xTile + 1, yTile, zTile);
                worldObj.func_147451_t(xTile, yTile - 1, zTile);
                worldObj.func_147451_t(xTile, yTile + 1, zTile);
                worldObj.func_147451_t(xTile, yTile, zTile - 1);
                worldObj.func_147451_t(xTile, yTile, zTile + 1);
            }
        }
        isfirstflame = false;
    	if (this.fuse-- < 0)
        {
            this.setDead();

        }
        if(HandmadeGunsCore.cfg_Flash&& isglow) {
            if (!isDead) {
                xTile = (int) this.posX - 1;
                yTile = (int) this.posY - 1;
                zTile = (int) this.posZ - 1;
                if(HMG_proxy.getEntityPlayerInstance().getDistanceSqToEntity(this) < 256 && worldObj.getChunkFromBlockCoords(xTile,zTile).isChunkLoaded) {
                    worldObj.setLightValue(EnumSkyBlock.Block, xTile, yTile, zTile, 0x99);
                    worldObj.func_147451_t(xTile - 1, yTile, zTile);
                    worldObj.func_147451_t(xTile + 1, yTile, zTile);
                    worldObj.func_147451_t(xTile, yTile - 1, zTile);
                    worldObj.func_147451_t(xTile, yTile + 1, zTile);
                    worldObj.func_147451_t(xTile, yTile, zTile - 1);
                    worldObj.func_147451_t(xTile, yTile, zTile + 1);
                }
            }
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if(!istrail) {
            this.posX += this.thisMotionX;
            this.posY += this.thisMotionY;
            this.posZ += this.thisMotionZ;
        }
    }
    public void setDead()
    {
        this.isDead = true;
        if(HandmadeGunsCore.cfg_Flash&& isglow) {
            int xTile = (int) this.prevPosX - 1;
            int yTile = (int) this.prevPosY - 1;
            int zTile = (int) this.prevPosZ - 1;
            if(worldObj.getChunkFromBlockCoords(xTile,zTile).isChunkLoaded) {
                worldObj.func_147451_t(xTile, yTile, zTile);
                worldObj.func_147451_t(xTile - 1, yTile, zTile);
                worldObj.func_147451_t(xTile + 1, yTile, zTile);
                worldObj.func_147451_t(xTile, yTile - 1, zTile);
                worldObj.func_147451_t(xTile, yTile + 1, zTile);
                worldObj.func_147451_t(xTile, yTile, zTile - 1);
                worldObj.func_147451_t(xTile, yTile, zTile + 1);
            }
        }
    }
    @Override
    public int getFXLayer() {
        return 2;
    }
    public void setParticleScale(float value){
        this.particleScale = value;
    }
    public void setParticleAlpha(float value){
        this.particleAlpha = value;
    }
    @Override
    public boolean shouldRenderInPass(int pass)
    {
        return pass == 0;
    }
    public void renderParticle(Tessellator tessellator, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_)
    {
        tessellator.draw();
        tessellator.startDrawingQuads();
        GL11.glPushAttrib(GL_FOG);
        GL11.glPushAttrib(GL_ALPHA_TEST);
        GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL_FOG);
        if (isglow || isrenderglow) {
            RenderHelper.disableStandardItemLighting();
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
        } else {
            RenderHelper.disableStandardItemLighting();
            int i = this.getBrightnessForRender(particaltick);
            int j = i % 65536;
            int k = i / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j / 1.0F, (float) k / 1.0F);
        }
        for(int pass = 0;pass < 2;pass ++){
            if(pass == 1) {
                GL11.glDepthMask(true);
                glAlphaFunc(GL_GEQUAL, 1);
            }else {
                GL11.glDepthMask(false);
                glAlphaFunc(GL_LESS, 1);
            }
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_CULL_FACE);
            if (fuse >= 0) {
                if (is3d) {
                    HMG_proxy.getMCInstance().getTextureManager().bindTexture(resourceLocation[fuse]);
                    float posx = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) p_70539_2_ - interpPosX);
                    float posy = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) p_70539_2_ - interpPosY);
                    float posz = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) p_70539_2_ - interpPosZ);
                    GL11.glTranslatef(posx, posy, posz);
                    if (fixedsize) {
                        float toplayerdist = (float) HMG_proxy.getEntityPlayerInstance().getDistance(posX, posY, posZ);
                        GL11.glScalef(toplayerdist, toplayerdist, toplayerdist);
                    }
                    GL11.glRotatef(-this.rotationYaw + 90, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(this.rotationPitch, 0.0F, 0.0F, 1.0F);
                    byte b0 = 0;
                    float f2 = 0.5F;
                    float f3 = 1F;
                    float f4 = 0;
                    float f5 = 0.5f;
                    float f6 = 0.0F;
                    float f7 = 0.5F;
                    float f8 = 0;
                    float f9 = 0.5f;
                    float f10 = 0F;
                    float f11 = 0.5F;
                    float f12 = 0.5f;
                    float f13 = 1f;
                    float f14 = 0.1F;


                    GL11.glScalef(f14, f14, f14);
//            GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
                    GL11.glScalef(this.particleScale, this.particleScale, this.particleScale);
                    GL11.glNormal3f(f14, 0.0F, 0.0F);
                    tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
                    tessellator.addVertexWithUV(-2.0D, -2.0D, -2.0D, (double) f6, (double) f8);
                    tessellator.addVertexWithUV(-2.0D, -2.0D, 2.0D, (double) f7, (double) f8);
                    tessellator.addVertexWithUV(-2.0D, 2.0D, 2.0D, (double) f7, (double) f9);
                    tessellator.addVertexWithUV(-2.0D, 2.0D, -2.0D, (double) f6, (double) f9);
                    tessellator.draw();
                    GL11.glNormal3f(-f14, 0.0F, 0.0F);
                    tessellator.startDrawingQuads();
                    tessellator.addVertexWithUV(-2.0D, 2.0D, -2.0D, (double) f6, (double) f8);
                    tessellator.addVertexWithUV(-2.0D, 2.0D, 2.0D, (double) f7, (double) f8);
                    tessellator.addVertexWithUV(-2.0D, -2.0D, 2.0D, (double) f7, (double) f9);
                    tessellator.addVertexWithUV(-2.0D, -2.0D, -2.0D, (double) f6, (double) f9);
                    tessellator.draw();


                    GL11.glNormal3f(f14, 0.0F, 0.0F);
                    tessellator.startDrawingQuads();
                    tessellator.addVertexWithUV(2.0D, -2.0D, -2.0D, (double) f10, (double) f12);
                    tessellator.addVertexWithUV(2.0D, -2.0D, 2.0D, (double) f11, (double) f12);
                    tessellator.addVertexWithUV(2.0D, 2.0D, 2.0D, (double) f11, (double) f13);
                    tessellator.addVertexWithUV(2.0D, 2.0D, -2.0D, (double) f10, (double) f13);
                    tessellator.draw();
                    GL11.glNormal3f(-f14, 0.0F, 0.0F);
                    tessellator.startDrawingQuads();
                    tessellator.addVertexWithUV(2.0D, 2.0D, -2.0D, (double) f10, (double) f12);
                    tessellator.addVertexWithUV(2.0D, 2.0D, 2.0D, (double) f11, (double) f12);
                    tessellator.addVertexWithUV(2.0D, -2.0D, 2.0D, (double) f11, (double) f13);
                    tessellator.addVertexWithUV(2.0D, -2.0D, -2.0D, (double) f10, (double) f13);
                    tessellator.draw();

                    for (int i = 0; i < 4; ++i) {
                        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                        GL11.glNormal3f(0.0F, 0.0F, f14);
                        tessellator.startDrawingQuads();
                        tessellator.addVertexWithUV(-2.0D, -2.0D, 0.0D, (double) f2, (double) f4);
                        tessellator.addVertexWithUV(2.0D, -2.0D, 0.0D, (double) f3, (double) f4);
                        tessellator.addVertexWithUV(2.0D, 2.0D, 0.0D, (double) f3, (double) f5);
                        tessellator.addVertexWithUV(-2.0D, 2.0D, 0.0D, (double) f2, (double) f5);
                        tessellator.draw();
                    }
                    tessellator.startDrawingQuads();
                    GL11.glScalef(1 / this.particleScale, 1 / this.particleScale, 1 / this.particleScale);
                    GL11.glScalef(1 / f14, 1 / f14, 1 / f14);
                } else if (istrail) {
                    Vec3 toNextPos = Vec3.createVectorHelper(thisMotionX - posX,thisMotionY - posY,thisMotionZ - posZ);
                    Vec3 playerlook = FMLClientHandler.instance().getClientPlayerEntity().getLook(1.0f);
                    Vec3 renderVec = toNextPos.crossProduct(playerlook);
                    renderVec = renderVec.normalize();
                    HMG_proxy.getMCInstance().getTextureManager().bindTexture(resourceLocation[(int) (fuse/animationspeed)]);
                    GL11.glTranslatef(
                            (float) (this.posX - interpPosX),
                            (float) (this.posY - interpPosY),
                            (float) (this.posZ - interpPosZ));
                    GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                    GL11.glScalef(1, 1, 1);
                    GL11.glNormal3f(1.0F, 0.0F, 0.0F);
                    tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
                    double taildist2 = 1;
                    double taildist = 0;
                    if(fuse == 0){
                        taildist = p_70539_2_;
                    }
                    if (isfirstflame){
                        taildist2 = p_70539_2_;
                    }

                    tessellator.addVertexWithUV(
                            renderVec.xCoord * trailwidth/2 + toNextPos.xCoord * taildist2,
                            renderVec.yCoord * trailwidth/2 + toNextPos.yCoord * taildist2,
                            renderVec.zCoord * trailwidth/2 + toNextPos.zCoord * taildist2, (double) 0, (double) 0);
                    tessellator.addVertexWithUV(
                            renderVec.xCoord * trailwidth/2 + toNextPos.xCoord * taildist,
                            renderVec.yCoord * trailwidth/2 + toNextPos.yCoord * taildist,
                            renderVec.zCoord * trailwidth/2 + toNextPos.zCoord * taildist, (double) 0.5, (double) 0);
                    tessellator.addVertexWithUV(
                            -renderVec.xCoord * trailwidth/2 + toNextPos.xCoord * taildist,
                            -renderVec.yCoord * trailwidth/2 + toNextPos.yCoord * taildist,
                            -renderVec.zCoord * trailwidth/2 + toNextPos.zCoord * taildist, (double) 0.5, (double) 0.5);
                    tessellator.addVertexWithUV(
                            -renderVec.xCoord * trailwidth/2 + toNextPos.xCoord * taildist2,
                            -renderVec.yCoord * trailwidth/2 + toNextPos.yCoord * taildist2,
                            -renderVec.zCoord * trailwidth/2 + toNextPos.zCoord * taildist2, (double) 0, (double) 0.5);
                    tessellator.draw();


//                    tessellator.addVertexWithUV(
//                            crossrenderVec1.xCoord * trailwidth/2 + toNextPos.xCoord * taildist2,
//                            crossrenderVec1.yCoord * trailwidth/2 + toNextPos.yCoord * taildist2,
//                            crossrenderVec1.zCoord * trailwidth/2 + toNextPos.zCoord * taildist2, (double) 0.5, (double) 0);
//                    tessellator.addVertexWithUV(
//                            -crossrenderVec2.xCoord * trailwidth/2 + toNextPos.xCoord * taildist2,
//                            -crossrenderVec2.yCoord * trailwidth/2 + toNextPos.yCoord * taildist2,
//                            -crossrenderVec2.zCoord * trailwidth/2 + toNextPos.zCoord * taildist2, (double) 1, (double) 0);
//                    tessellator.addVertexWithUV(
//                            -crossrenderVec1.xCoord * trailwidth/2 + toNextPos.xCoord * taildist2,
//                            -crossrenderVec1.yCoord * trailwidth/2 + toNextPos.yCoord * taildist2,
//                            -crossrenderVec1.zCoord * trailwidth/2 + toNextPos.zCoord * taildist2, (double) 1, (double) 0.5);
//                    tessellator.addVertexWithUV(
//                            crossrenderVec2.xCoord * trailwidth/2 + toNextPos.xCoord * taildist2,
//                            crossrenderVec2.yCoord * trailwidth/2 + toNextPos.yCoord * taildist2,
//                            crossrenderVec2.zCoord * trailwidth/2 + toNextPos.zCoord * taildist2, (double) 0.5, (double) 0.5);
//                    tessellator.draw();
//                    tessellator.startDrawingQuads();
//                    tessellator.addVertexWithUV(
//                            -crossrenderVec1.xCoord * trailwidth/2 + toNextPos.xCoord * taildist,
//                            -crossrenderVec1.yCoord * trailwidth/2 + toNextPos.yCoord * taildist,
//                            -crossrenderVec1.zCoord * trailwidth/2 + toNextPos.zCoord * taildist, (double) 0.5, (double) 0.5);
//                    tessellator.addVertexWithUV(
//                            crossrenderVec2.xCoord * trailwidth/2 + toNextPos.xCoord * taildist,
//                            crossrenderVec2.yCoord * trailwidth/2 + toNextPos.yCoord * taildist,
//                            crossrenderVec2.zCoord * trailwidth/2 + toNextPos.zCoord * taildist, (double) 1, (double) 0.5);
//                    tessellator.addVertexWithUV(
//                            crossrenderVec1.xCoord * trailwidth/2 + toNextPos.xCoord * taildist,
//                            crossrenderVec1.yCoord * trailwidth/2 + toNextPos.yCoord * taildist,
//                            crossrenderVec1.zCoord * trailwidth/2 + toNextPos.zCoord * taildist, (double) 1, (double) 1);
//                    tessellator.addVertexWithUV(
//                            -crossrenderVec2.xCoord * trailwidth/2 + toNextPos.xCoord * taildist,
//                            -crossrenderVec2.yCoord * trailwidth/2 + toNextPos.yCoord * taildist,
//                            -crossrenderVec2.zCoord * trailwidth/2 + toNextPos.zCoord * taildist, (double) 0.5, (double) 1);
//                    tessellator.draw();
                    tessellator.startDrawingQuads();


                    GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                } else {
                    try {
                        HMG_proxy.getMCInstance().getTextureManager().bindTexture(resourceLocation[fuse]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    float f10 = 0.1F * this.particleScale;

                    if (fixedsize) {
                        f10 *= (float) HMG_proxy.getEntityPlayerInstance().getDistance(posX, posY, posZ);
                    }
                    float f11 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) p_70539_2_ - interpPosX);
                    float f12 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) p_70539_2_ - interpPosY);
                    float f13 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) p_70539_2_ - interpPosZ);
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
                    tessellator.addVertexWithUV(
                            (double) (f11 - p_70539_3_ * f10 - p_70539_6_ * f10),
                            (double) (f12 - p_70539_4_ * f10),
                            (double) (f13 - p_70539_5_ * f10 - p_70539_7_ * f10), (double) 1, (double) 1);
                    tessellator.addVertexWithUV((double) (f11 - p_70539_3_ * f10 + p_70539_6_ * f10), (double) (f12 + p_70539_4_ * f10), (double) (f13 - p_70539_5_ * f10 + p_70539_7_ * f10), (double) 1, (double) 0);
                    tessellator.addVertexWithUV((double) (f11 + p_70539_3_ * f10 + p_70539_6_ * f10), (double) (f12 + p_70539_4_ * f10), (double) (f13 + p_70539_5_ * f10 + p_70539_7_ * f10), (double) 0, (double) 0);
                    tessellator.addVertexWithUV((double) (f11 + p_70539_3_ * f10 - p_70539_6_ * f10), (double) (f12 - p_70539_4_ * f10), (double) (f13 + p_70539_5_ * f10 - p_70539_7_ * f10), (double) 0, (double) 1);
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                }
            }
            GL11.glPopMatrix();
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL_ALPHA_TEST);
            GL11.glColor4f(1,1,1,1f);
        }

        GL11.glPopAttrib();
        GL11.glPopAttrib();
    }
}
