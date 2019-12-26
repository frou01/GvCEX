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
import static org.lwjgl.opengl.GL11.*;

public class HMGEntityParticles extends EntityFX
{
//    public static ResourceLocation icon = new ResourceLocation("handmadeguns", "textures/entity/fire");
	public int fuse;
	public int animationspeed = 1;
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
                if(worldObj.getChunkFromBlockCoords(xTile,zTile).isChunkLoaded) {
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
            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
        }else {
//            this.posX = this.lastTickPosX + this.motionX;
//            this.posY = this.lastTickPosY + this.motionY;
//            this.posZ = this.lastTickPosZ + this.motionZ;
            this.trailrotationyaw = (float) (atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.trailrotationpitch = (float) (atan2(this.motionY, (double) f2) * 180.0D / Math.PI);
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
        // ���Ԃ񐔒l���傫���قǎ�O�ɕ`�悳���H
        // 1or2�łȂ��Ɨ�O����������̂łƂ肠����2�ɐݒ�
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
        if(disable_DEPTH_TEST) {
            GL11.glColor4f(1,1,1,0.1f);
            setParticleAlpha(particleAlpha/2);
            GL11.glDisable(GL_DEPTH_TEST);
            glDepthMask(false);
        }
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
        for(int cnt = 0;cnt < (disable_DEPTH_TEST?2:1);cnt++) {
            GL11.glPushMatrix();
            GL11.glDisable(GL_ALPHA_TEST);
            GL11.glDisable(GL11.GL_CULL_FACE);
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
                    Vec3 motionVec = Vec3.createVectorHelper(motionX,motionY,motionZ);
                    Vec3 playerlook = FMLClientHandler.instance().getClientPlayerEntity().getLook(1.0f);
                    Vec3 renderVec = motionVec.crossProduct(playerlook);
                    renderVec = renderVec.normalize();
                    Vec3 crossrenderVec1 = motionVec.crossProduct(Vec3.createVectorHelper(0,1,0));
                    Vec3 crossrenderVec2 = crossrenderVec1.crossProduct(motionVec);
                    crossrenderVec1 = crossrenderVec1.normalize();
                    crossrenderVec2 = crossrenderVec2.normalize();
                    HMG_proxy.getMCInstance().getTextureManager().bindTexture(resourceLocation[fuse]);
                    GL11.glTranslatef(
                            (float) (this.posX - interpPosX),
                            (float) (this.posY - interpPosY),
                            (float) (this.posZ - interpPosZ));
                    GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                    GL11.glScalef(1.0F, 1.0F, 1.0F);
                    GL11.glNormal3f(1.0F, 0.0F, 0.0F);
                    tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
                    Vec3 postoprepos = Vec3.createVectorHelper(
                            this.motionX,
                            this.motionY,
                            this.motionZ);
                    double taildist2 = postoprepos.lengthVector();
                    if (isfirstflame) taildist2 *= p_70539_2_;
                    double taildist;
                    if (fuse == 0) taildist = postoprepos.lengthVector() * p_70539_2_;
                    else taildist = 0;
                    tessellator.addVertexWithUV(
                            renderVec.xCoord * trailwidth/2 + motionVec.xCoord * taildist2,
                            renderVec.yCoord * trailwidth/2 + motionVec.yCoord * taildist2,
                            renderVec.zCoord * trailwidth/2 + motionVec.zCoord * taildist2, (double) 0, (double) 0);
                    tessellator.addVertexWithUV(
                            renderVec.xCoord * trailwidth/2 + motionVec.xCoord * taildist,
                            renderVec.yCoord * trailwidth/2 + motionVec.yCoord * taildist,
                            renderVec.zCoord * trailwidth/2 + motionVec.zCoord * taildist, (double) 0.5, (double) 0);
                    tessellator.addVertexWithUV(
                            -renderVec.xCoord * trailwidth/2 + motionVec.xCoord * taildist,
                            -renderVec.yCoord * trailwidth/2 + motionVec.yCoord * taildist,
                            -renderVec.zCoord * trailwidth/2 + motionVec.zCoord * taildist, (double) 0.5, (double) 0.5);
                    tessellator.addVertexWithUV(
                            -renderVec.xCoord * trailwidth/2 + motionVec.xCoord * taildist2,
                            -renderVec.yCoord * trailwidth/2 + motionVec.yCoord * taildist2,
                            -renderVec.zCoord * trailwidth/2 + motionVec.zCoord * taildist2, (double) 0, (double) 0.5);
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.addVertexWithUV(
                            -renderVec.xCoord * trailwidth/2 + motionVec.xCoord * taildist2,
                            -renderVec.yCoord * trailwidth/2 + motionVec.yCoord * taildist2,
                            -renderVec.zCoord * trailwidth/2 + motionVec.zCoord * taildist2, (double) 0, (double) 0.5);
                    tessellator.addVertexWithUV(
                            -renderVec.xCoord * trailwidth/2 + motionVec.xCoord * taildist,
                            -renderVec.yCoord * trailwidth/2 + motionVec.yCoord * taildist,
                            -renderVec.zCoord * trailwidth/2 + motionVec.zCoord * taildist, (double) 0.5, (double) 0.5);
                    tessellator.addVertexWithUV(
                            renderVec.xCoord * trailwidth/2 + motionVec.xCoord * taildist,
                            renderVec.yCoord * trailwidth/2 + motionVec.yCoord * taildist,
                            renderVec.zCoord * trailwidth/2 + motionVec.zCoord * taildist, (double) 0.5, (double) 1);
                    tessellator.addVertexWithUV(
                            renderVec.xCoord * trailwidth/2 + motionVec.xCoord * taildist2,
                            renderVec.yCoord * trailwidth/2 + motionVec.yCoord * taildist2,
                            renderVec.zCoord * trailwidth/2 + motionVec.zCoord * taildist2, (double) 0, (double) 1);


                    tessellator.addVertexWithUV(
                            crossrenderVec1.xCoord * trailwidth/2 + motionVec.xCoord * taildist2,
                            crossrenderVec1.yCoord * trailwidth/2 + motionVec.yCoord * taildist2,
                            crossrenderVec1.zCoord * trailwidth/2 + motionVec.zCoord * taildist2, (double) 0.5, (double) 0);
                    tessellator.addVertexWithUV(
                            -crossrenderVec2.xCoord * trailwidth/2 + motionVec.xCoord * taildist2,
                            -crossrenderVec2.yCoord * trailwidth/2 + motionVec.yCoord * taildist2,
                            -crossrenderVec2.zCoord * trailwidth/2 + motionVec.zCoord * taildist2, (double) 1, (double) 0);
                    tessellator.addVertexWithUV(
                            -crossrenderVec1.xCoord * trailwidth/2 + motionVec.xCoord * taildist2,
                            -crossrenderVec1.yCoord * trailwidth/2 + motionVec.yCoord * taildist2,
                            -crossrenderVec1.zCoord * trailwidth/2 + motionVec.zCoord * taildist2, (double) 1, (double) 0.5);
                    tessellator.addVertexWithUV(
                            crossrenderVec2.xCoord * trailwidth/2 + motionVec.xCoord * taildist2,
                            crossrenderVec2.yCoord * trailwidth/2 + motionVec.yCoord * taildist2,
                            crossrenderVec2.zCoord * trailwidth/2 + motionVec.zCoord * taildist2, (double) 0.5, (double) 0.5);
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.addVertexWithUV(
                            -crossrenderVec1.xCoord * trailwidth/2 + motionVec.xCoord * taildist,
                            -crossrenderVec1.yCoord * trailwidth/2 + motionVec.yCoord * taildist,
                            -crossrenderVec1.zCoord * trailwidth/2 + motionVec.zCoord * taildist, (double) 0.5, (double) 0.5);
                    tessellator.addVertexWithUV(
                            crossrenderVec2.xCoord * trailwidth/2 + motionVec.xCoord * taildist,
                            crossrenderVec2.yCoord * trailwidth/2 + motionVec.yCoord * taildist,
                            crossrenderVec2.zCoord * trailwidth/2 + motionVec.zCoord * taildist, (double) 1, (double) 0.5);
                    tessellator.addVertexWithUV(
                            crossrenderVec1.xCoord * trailwidth/2 + motionVec.xCoord * taildist,
                            crossrenderVec1.yCoord * trailwidth/2 + motionVec.yCoord * taildist,
                            crossrenderVec1.zCoord * trailwidth/2 + motionVec.zCoord * taildist, (double) 1, (double) 1);
                    tessellator.addVertexWithUV(
                            -crossrenderVec2.xCoord * trailwidth/2 + motionVec.xCoord * taildist,
                            -crossrenderVec2.yCoord * trailwidth/2 + motionVec.yCoord * taildist,
                            -crossrenderVec2.zCoord * trailwidth/2 + motionVec.zCoord * taildist, (double) 0.5, (double) 1);
                    tessellator.draw();
                    tessellator.startDrawingQuads();
//                    GL11.glRotatef(trailrotationyaw, 0.0F, 1.0F, 0.0F);
//                    GL11.glRotatef(-trailrotationpitch, 1.0F, 0.0F, 0.0F);
////            System.out.println("debug " + p_180551_1_.rotationPitch);
//
//                    Vec3 postoprepos = Vec3.createVectorHelper(
//                            this.motionX,
//                            this.motionY,
//                            this.motionZ);
//
//                    double taildist2 = postoprepos.lengthVector();
//                    if (isfirstflame) taildist2 *= p_70539_2_;
//                    double taildist;
//                    if (fuse == 0) taildist = postoprepos.lengthVector() * (p_70539_2_ + 0.2);
//                    else taildist = 0;
//                    tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
//                    if (isglow || isrenderglow) {
//                        RenderHelper.disableStandardItemLighting();
//
//                        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
//                        lastBrightnessX = OpenGlHelper.lastBrightnessX;
//                        lastBrightnessY = OpenGlHelper.lastBrightnessY;
//                    }
//
//                    tessellator.addVertexWithUV(
//                            0,
//                            1 * trailwidth,
//                            taildist2, (double) 0, (double) 0);
//                    tessellator.addVertexWithUV(
//                            0,
//                            1 * trailwidth,
//                            taildist, (double) 0.5, (double) 0);
//                    tessellator.addVertexWithUV(
//                            0,
//                            -1 * trailwidth,
//                            taildist, (double) 0.5, (double) 0.5);
//                    tessellator.addVertexWithUV(
//                            0,
//                            -1 * trailwidth,
//                            taildist2, (double) 0, (double) 0.5);
//                    tessellator.draw();
//
//                    tessellator.startDrawingQuads();
//                    if (isglow || isrenderglow) {
//                        RenderHelper.disableStandardItemLighting();
//
//                        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
//                        lastBrightnessX = OpenGlHelper.lastBrightnessX;
//                        lastBrightnessY = OpenGlHelper.lastBrightnessY;
//                    }
//                    tessellator.addVertexWithUV(
//                            1 * trailwidth,
//                            0,
//                            taildist2, (double) 0, (double) 0);
//                    tessellator.addVertexWithUV(
//                            1 * trailwidth,
//                            0,
//                            taildist, (double) 0.5, (double) 0);
//                    tessellator.addVertexWithUV(
//                            -1 * trailwidth,
//                            0,
//                            taildist, (double) 0.5, (double) 0.5);
//                    tessellator.addVertexWithUV(
//                            -1 * trailwidth,
//                            0,
//                            taildist2, (double) 0, (double) 0.5);
//                    tessellator.draw();
//
//                    if (isfirstflame) {
//                        tessellator.startDrawingQuads();
//                        if (isglow || isrenderglow) {
//                            RenderHelper.disableStandardItemLighting();
//
//                            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
//                            lastBrightnessX = OpenGlHelper.lastBrightnessX;
//                            lastBrightnessY = OpenGlHelper.lastBrightnessY;
//                        }
//                        tessellator.addVertexWithUV(
//                                1 * trailwidth,
//                                0,
//                                taildist2, (double) 0, (double) 0);
//                        tessellator.addVertexWithUV(
//                                0,
//                                1 * trailwidth,
//                                taildist2, (double) 0.5, (double) 0);
//                        tessellator.addVertexWithUV(
//                                -1 * trailwidth,
//                                0,
//                                taildist2, (double) 0.5, (double) 0.5);
//                        tessellator.addVertexWithUV(
//                                0,
//                                -1 * trailwidth,
//                                taildist2, (double) 0, (double) 0.5);
//                        tessellator.draw();
//                    }
//                    if (fuse == 0) {
//                        tessellator.startDrawingQuads();
//                        if (isglow || isrenderglow) {
//                            RenderHelper.disableStandardItemLighting();
//
//                            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
//                            lastBrightnessX = OpenGlHelper.lastBrightnessX;
//                            lastBrightnessY = OpenGlHelper.lastBrightnessY;
//                        }
//                        tessellator.addVertexWithUV(
//                                1 * trailwidth,
//                                0,
//                                taildist, (double) 0, (double) 0);
//                        tessellator.addVertexWithUV(
//                                0,
//                                1 * trailwidth,
//                                taildist, (double) 0.5, (double) 0);
//                        tessellator.addVertexWithUV(
//                                -1 * trailwidth,
//                                0,
//                                taildist, (double) 0.5, (double) 0.5);
//                        tessellator.addVertexWithUV(
//                                0,
//                                -1 * trailwidth,
//                                taildist, (double) 0, (double) 0.5);
//                        tessellator.draw();
//                    }


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
            if (disable_DEPTH_TEST) {
                GL11.glEnable(GL_DEPTH_TEST);
                glDepthMask(false);
            }
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL_ALPHA_TEST);
            GL11.glColor4f(1,1,1,1f);
        }

        GL11.glPopAttrib();
        GL11.glPopAttrib();
    }
}
