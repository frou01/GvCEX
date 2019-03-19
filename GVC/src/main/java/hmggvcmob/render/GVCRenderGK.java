
package hmggvcmob.render;

import hmggvcmob.entity.guerrilla.GVCEntityGK;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import static java.lang.Math.abs;
import static java.lang.Math.toRadians;
import static net.minecraft.util.MathHelper.cos;
import static net.minecraft.util.MathHelper.sin;

public class GVCRenderGK extends Render {

	private static final ResourceLocation skeletonTexturesz = new ResourceLocation("gvcmob:textures/mob/kai/gekkou.png");
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("gvcmob:textures/mob/kai/gekkoure.obj"));
	private float scale;

	public GVCRenderGK() {
		this.scale = 2;
	}

	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		this.doRender((GVCEntityGK) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
	
	float xsxs;
	public void doRender(GVCEntityGK entity, double p_76986_2_, double p_76986_4_, double p_76986_6_,
			float entityYaw, float partialTicks) {
		this.bindEntityTexture(entity);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		if(entity.deathTicks > 0){
			GL11.glColor4f(0.1F, 0.1F, 0.1F, 1F);
		}

		GL11.glRotatef(180 - (entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks), 0.0F, 1.0F, 0.0F);
		if(entity.isSneaking()){
			GL11.glRotatef(75, 1.0F, 0.0F, 0.0F);
			this.renderlegs(entity);
			GL11.glTranslatef(0F, 2.0F, 0.0F);
			GL11.glRotatef(-90, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0F, -2.0F, 0.0F);

			tankk.renderPart("mat1");
			GL11.glTranslatef(0F, 3.2F, 0.3F);
			GL11.glRotatef(180.0F - entity.rotationYawHead - (180.0F - entityYaw), 0.0F, 1.0F, 0.0F);
			if (entity.rotationPitch > 90) entity.rotationPitch = 180 - entity.rotationPitch;
			if (entity.rotationPitch < -90) entity.rotationPitch = -180 - entity.rotationPitch;
			GL11.glRotatef(entity.rotationPitch, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0F, -3.2F, -0.3F);
			tankk.renderPart("mat4");
			tankk.renderPart("mat5");
		}else if(entity.combattask_4){
//			GL11.glTranslatef(-0.5F, 0, -0.5F);
//			GL11.glRotatef(15, 1.0f, 0.0F, 0.0F);
//			GL11.glTranslatef(0.5F, 0, 0.5F);
			GL11.glTranslatef(-0.5F, 1.8F, -0.5F);
			GL11.glRotatef(0, 1.0f, 0.0F, 0.0F);
			GL11.glRotatef(0, 0.0f, 0.0F, 1.0F);
			GL11.glTranslatef(0.5F, -1.8F, 0.5F);
			tankk.renderPart("mat3");//右足(軸足)
			tankk.renderPart("mat7");
			GL11.glTranslatef(-0.5F, 1.8F, -0.5F);
			GL11.glRotatef(0, 1.0f, 0.0F, 0.0F);
			GL11.glRotatef(entity.kickprogeress *1.5f, 0.0f, 0.0F, 1.0F);
			GL11.glTranslatef(0.5F, -1.8F, 0.5F);

			GL11.glTranslatef(-0.5F, 2.95F, -0.5f);
			GL11.glRotatef(-entity.kickprogeress *9, 0.0f, 1.0F, 0.0F);
			GL11.glTranslatef(0.5F, -2.95F, 0.5f);
			tankk.renderPart("mat1");//腰
//			GL11.glTranslatef(-0.2F, 2.95F, 0);
//			GL11.glRotatef(entity.cooltime_3*9, 0.0f, 1.0F, 0.0F);
//			GL11.glTranslatef(0.2F, -2.95F, 0);


			GL11.glTranslatef(0.5F, 1.8F, -0.5F);
//			GL11.glRotatef(30, 0.0f, 0.0F, 1.0F);
			GL11.glRotatef((entity.kickprogeress * entity.kickprogeress *0.9f), cos((float) toRadians(entity.kickprogeress * 9)), 0, sin((float) toRadians(entity.kickprogeress * 9)));
			GL11.glRotatef((entity.kickprogeress) * 3, 0.0f, 1, 0.0f);
			GL11.glTranslatef(-0.5F, -1.8F, 0.5F);
			tankk.renderPart("mat2");//左足
			tankk.renderPart("mat21");

			GL11.glTranslatef(0.75F, 1.55F, 0);
			GL11.glRotatef(-(entity.kickprogeress) * 3, 1.0f, 0, 0.0f);
			GL11.glTranslatef(-0.75F, -1.55F, -0);
			tankk.renderPart("mat6");
			GL11.glTranslatef(0.75F, 1.55F, 0);
			GL11.glRotatef((entity.kickprogeress) * 3, 1.0f, 0, 0.0f);
			GL11.glTranslatef(-0.75F, -1.55F, -0);

			GL11.glTranslatef(0.5F, 1.8F, -0.5F);
			GL11.glRotatef(-(entity.kickprogeress) * 3, 0.0f, 1, 0.0f);
			GL11.glRotatef(-(entity.kickprogeress * entity.kickprogeress *0.9f), cos((float) toRadians(entity.kickprogeress * 9)), 0, sin((float) toRadians(entity.kickprogeress * 9)));
//			GL11.glRotatef(-30, 0.0f, 0.0F, 1.0F);
			GL11.glTranslatef(-0.5F, -1.8F, 0.5F);

			GL11.glTranslatef(0F, 3.2F, 0.3F);
			GL11.glRotatef(180.0F - entity.rotationYawHead - (180.0F - entityYaw)+entity.kickprogeress *9, 0.0F, 1.0F, 0.0F);
			if (entity.rotationPitch > 90) entity.rotationPitch = 180 - entity.rotationPitch;
			if (entity.rotationPitch < -90) entity.rotationPitch = -180 - entity.rotationPitch;
			GL11.glRotatef(entity.rotationPitch, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0F, -3.2F, -0.3F);
			tankk.renderPart("mat4");//頭部
			tankk.renderPart("mat5");
		} else {
			this.renderlegs(entity);
			tankk.renderPart("mat1");
			GL11.glTranslatef(0F, 3.2F, 0.3F);
			GL11.glRotatef(180.0F - entity.rotationYawHead - (180.0F - entityYaw), 0.0F, 1.0F, 0.0F);
			if (entity.rotationPitch > 90) entity.rotationPitch = 180 - entity.rotationPitch;
			if (entity.rotationPitch < -90) entity.rotationPitch = -180 - entity.rotationPitch;
			GL11.glRotatef(entity.rotationPitch, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0F, -3.2F, -0.3F);
			tankk.renderPart("mat4");
			tankk.renderPart("mat5");
		}
		
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		
		
	}
	private void renderlegs(GVCEntityGK p_76986_1_){
		//if (p_76986_1_.riddenByEntity != null && p_76986_1_.riddenByEntity instanceof EntityLivingBase)
		if(!p_76986_1_.isstanding && abs(p_76986_1_.motionY )< 0.3)
		{
			//EntityLivingBase entitylivingbase = (EntityLivingBase) p_76986_1_.riddenByEntity;
			float iii = p_76986_1_.angletime;
			//ブレンダーのモーションを適用したいレベルだぜ…
			//開始時
			//0-40  右足 接地-後退
			//      左足 接地-前進
			//40-80 右足 後退-空中
			//      左足 前進-接地
			//80-120右足 空中-前進
			//      左足 接地-後退
			//120-0 右足 前進-接地
			//      左足 後退-空中
			//歩行時
			//0-40  右足 接地-後退
			//      左足 空中-前進
			//40-80 右足 後退-空中
			//      左足 前進-接地
			//80-120右足 空中-前進
			//      左足 接地-後退
			//120-0 右足 前進-接地
			//      左足 後退-空中
			//終了時
			//0-40  右足 接地-後退
			//      左足 空中-前進
			//40-80 右足 後退-空中
			//      左足 前進-接地
			//80-120右足 空中-前進
			//      左足 接地-後退
			//120-0 右足 前進-接地
			//      左足 後退-接地
			if(p_76986_1_.onstarting) {
				//歩行
				if (iii < 40F)
				//0-40
				//      左足 接地-後進
				{
					GL11.glTranslatef(0, 0.2f * sin((float)toRadians(iii)), 0);
					this.renderlegleftH(0F, iii);//(0 -> 40)
					this.renderlegleftB(iii, iii/2);//(0 -> 40)
					this.renderlegrightH(0F, -iii*0.75f);//(0 -> -40)
					this.renderlegrightB(-iii*0.75f,iii/2);//(0 -> 20)
				} else if (iii < 80F)
				//40-80
				//      左足 後進-空中
				{
					GL11.glTranslatef(0, 0.2f * sin((float)toRadians(80-iii)), 0);
					this.renderlegleftH(0F, (80 - iii));//(40 -> 0)
					this.renderlegleftB((80 - iii), 20 + (80 - iii)/4);////(40 -> 40)
					this.renderlegrightH(0F, (iii-80)*0.75f);//(-40 -> 0)
					this.renderlegrightB((iii-80)*0.75f, -(iii-80)/2);//(20 -> 0)
				} else if (iii < 120F)
				//80-120
				//      左足 空中-前進
				{
					GL11.glTranslatef(0, 0.2f * sin((float)toRadians(iii-80)), 0);
					this.renderlegleftH(0F, (80 - iii) * 0.75f);//(0 -> -40)
					this.renderlegleftB((80 - iii) * 0.75f,20 + (80 - iii)/4);////(40 -> 40)
					this.renderlegrightH(0F, (iii-80));//(0 -> 40)
					this.renderlegrightB((iii-80), (iii-80)/2);//(0 -> 20)
				} else
				//120-0
				//      左足 前進-接地
				{
					GL11.glTranslatef(0, 0.2f * sin((float)toRadians(160-iii)), 0);
					this.renderlegleftH(0F, (iii - 160) * 0.75f);//(-40 -> 0)
					this.renderlegleftB((iii - 160) * 0.75f, -(iii - 160)/2);//(40 -> 0)
					this.renderlegrightH(0F, (160-iii));//(40 -> 0)
					this.renderlegrightB((160-iii), 20 + (160-iii)/4);//(20 -> 0)
				}
			}else if(p_76986_1_.onstopping){
				//歩行
				if (iii < 40F)
				//0-40
				//      左足 接地-後進
				{
					GL11.glTranslatef(0, 0.2f * sin((float)toRadians(iii)), 0);
					this.renderlegleftH(0F, iii);//(0 -> 40)
					this.renderlegleftB(iii, iii/2);//(0 -> 40)
					this.renderlegrightH(0F, -iii*0.75f);//(0 -> -40)
					this.renderlegrightB(-iii*0.75f,20);//(40 -> 40)
				} else if (iii < 80F)
				//40-80
				//      左足 後進-空中
				{
					GL11.glTranslatef(0, 0.2f * sin((float)toRadians(80 - iii)), 0);
					this.renderlegleftH(0F, (80 - iii));//(40 -> 0)
					this.renderlegleftB((80 - iii), 20);////(40 -> 40)
					this.renderlegrightH(0F, (iii-80)*0.75f);//(-40 -> 0)
					this.renderlegrightB((iii-80)*0.75f, -(iii-80)/2);//(20 -> 0)
				} else if (iii < 120F)
				//80-120
				//      左足 空中-前進
				{
					GL11.glTranslatef(0, 0.2f * sin((float)toRadians(iii-80)), 0);
					this.renderlegleftH(0F, (80 - iii) * 0.75f);//(0 -> -40)
					this.renderlegleftB((80 - iii) * 0.75f,20);////(40 -> 40)
					this.renderlegrightH(0F, (iii-80));//(0 -> 40)
					this.renderlegrightB((iii-80), (iii-80)/2);//(0 -> 20)
				} else
				//120-0
				//      左足 前進-接地
				{
					GL11.glTranslatef(0, 0.2f * sin((float)toRadians(160-iii)), 0);
					this.renderlegleftH(0F, (iii - 160) * 0.75f);//(-40 -> 0)
					this.renderlegleftB((iii - 160) * 0.75f, -(iii - 160)/2);//(40 -> 0)
					this.renderlegrightH(0F, (160-iii));//(40 -> 0)
					this.renderlegrightB((160-iii), (160-iii)/2);//(20 -> 0)
				}
			}else {
				//歩行
				if (iii < 40F)
				//0-40
				//      左足 接地-後進
				{
					GL11.glTranslatef(0, 0.2f * sin((float) toRadians(iii)), 0);
					this.renderlegleftH(0F, iii);//(0 -> 40)
					this.renderlegleftB(iii, iii / 2);//(0 -> 40)
					this.renderlegrightH(0F, -iii * 0.75f);//(0 -> -40)
					this.renderlegrightB(-iii * 0.75f, 20 - iii / 4);//(40 -> 40)
				} else if (iii < 80F)
				//40-80
				//      左足 後進-空中
				{
					GL11.glTranslatef(0, 0.2f * sin((float) toRadians(80 - iii)), 0);
					this.renderlegleftH(0F, (80 - iii));//(40 -> 0)
					this.renderlegleftB((80 - iii), 20 + (80 - iii) / 4);////(40 -> 40)
					this.renderlegrightH(0F, (iii - 80) * 0.75f);//(-40 -> 0)
					this.renderlegrightB((iii - 80) * 0.75f, -(iii - 80) / 2);//(20 -> 0)
				} else if (iii < 120F)
				//80-120
				//      左足 空中-前進
				{
					GL11.glTranslatef(0, 0.2f * sin((float) toRadians(iii - 80)), 0);
					this.renderlegleftH(0F, (80 - iii) * 0.75f);//(0 -> -40)
					this.renderlegleftB((80 - iii) * 0.75f, 20 + (80 - iii) / 4);////(40 -> 40)
					this.renderlegrightH(0F, (iii - 80));//(0 -> 40)
					this.renderlegrightB((iii - 80), (iii - 80) / 2);//(0 -> 20)
				} else
				//120-0
				//      左足 前進-接地
				{
					GL11.glTranslatef(0, 0.2f * sin((float) toRadians(160 - iii)), 0);
					this.renderlegleftH(0F, (iii - 160) * 0.75f);//(-40 -> 0)
					this.renderlegleftB((iii - 160) * 0.75f, -(iii - 160) / 2);//(40 -> 0)
					this.renderlegrightH(0F, (160 - iii));//(40 -> 0)
					this.renderlegrightB((160 - iii), 20 + (160 - iii) / 4);//(20 -> 0)
				}


////					System.out.println("debug");
//					if (iii < 40F)
//					//0-40  右足 空中-前進
//					{
//					} else if (iii < 80F)
//					//40-80 右足 前進-接地
//					{
//					} else if (iii < 120F)
//					//80-120右足 接地-後進
//					{
//					} else
//					//120-0 右足 後進-空中
//					{
//					}
			}
		}else{
			this.renderleg(20F, 5, 1.0F);
		}
	}
	private void renderlegleftH(float i0, float iii2){
    	GL11.glTranslatef(0.5F, 1.8F, -0.5F);
		GL11.glRotatef(iii2, 1.0f, 0.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -1.8F, 0.5F);
		tankk.renderPart("mat2");
		tankk.renderPart("mat21");
	}
	private void renderlegleftB(float i0, float iii2){
		GL11.glTranslatef(0.75F, 1.55F, 0);
		GL11.glRotatef(iii2, 1.0f, 0.0F, 0.0F);
		GL11.glTranslatef(-0.75F, -1.55F, 0);
		tankk.renderPart("mat6");
		GL11.glTranslatef(0.75F, 1.55F, 0);
		GL11.glRotatef(-iii2, 1.0f, 0.0F, 0.0F);
		GL11.glTranslatef(-0.75F, -1.55F, 0);

		GL11.glTranslatef(0.5F, 1.8F, -0.5F);
		GL11.glRotatef(-i0, 1.0f, 0.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -1.8F, 0.5F);
	}
	private void renderlegrightH(float i0, float iii2){
		GL11.glTranslatef(-0.5F, 1.8F, -0.5F);
		GL11.glRotatef(iii2, 1.0f, 0.0F, 0.0F);
		GL11.glTranslatef(0.5F, -1.8F, 0.5F);
		tankk.renderPart("mat3");
		tankk.renderPart("mat31");
	}
	private void renderlegrightB(float i0, float iii2){
		GL11.glTranslatef(-0.75F, 1.55F, 0);
		GL11.glRotatef(iii2, 1.0f, 0.0F, 0.0F);
		GL11.glTranslatef(0.75F, -1.55F, 0);
		tankk.renderPart("mat7");
		GL11.glTranslatef(-0.75F, 1.55F, 0);
		GL11.glRotatef(-iii2, 1.0f, 0.0F, 0.0F);
		GL11.glTranslatef(0.75F, -1.55F, 0);

		GL11.glTranslatef(-0.5F, 1.8F, -0.5F);
		GL11.glRotatef(-i0, 1.0f, 0.0F, 0.0F);
		GL11.glTranslatef(0.5F, -1.8F, 0.5F);
	}
	private void renderleg(float i0, float iii2, float ii){
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 1.8F, -0.5F);
		GL11.glRotatef(0F, ii, 0.0F, 0.0F);
		GL11.glTranslatef(0.0F, -1.8F, 0.5F);
		tankk.renderPart("mat2");
		tankk.renderPart("mat21");
		tankk.renderPart("mat3");
		tankk.renderPart("mat31");
		tankk.renderPart("mat6");
		tankk.renderPart("mat7");
		GL11.glPopMatrix();
	}
	/*{
		if (iii < 40F ) {
			//GL11.glTranslatef(0, iii/80, 0);
			this.renderlegleft(0F, iii, 1.0F);
		} else if (iii < 80F ) {
			//GL11.glTranslatef(0, -((iii-80)/80), 0);
			this.renderlegleft(40F,-(iii-40), 1.0F);
		}else if (iii < 120F ) {
			//GL11.glTranslatef(0, ((iii-120)/80), 0);
			this.renderlegleft(0F,(iii-80)/4, -1.0F);
		}else{
			//GL11.glTranslatef(0, -((iii-160)/80), 0);
			this.renderlegleft(-40F,(iii-30)/4, 1.0F);
		}
		if (iii < 40F ) {
			//GL11.glTranslatef(0, iii/80, 0);
			this.renderlegright(0F, iii/4, 1.0F);
		} else if (iii < 80F ) {
			//GL11.glTranslatef(0, -((iii-80)/80), 0);
			this.renderlegright(20F,-(iii-10)/4, 1.0F);
		}else if (iii < 120F ) {
			//GL11.glTranslatef(0, ((iii-120)/80), 0);
			this.renderlegright(0F,(iii-80), -1.0F);
		}else{
			//GL11.glTranslatef(0, -((iii-160)/80), 0);
			this.renderlegright(-40F,(iii-120), 1.0F);
		}
	}*/
	
	public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_,
			float p_76986_8_, float p_76986_9_) {
		this.doRender((GVCEntityGK) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving) {
		return this.skeletonTexturesz;
	}

}
