package handmadeguns.client.render;

import handmadeguns.HandmadeGunsCore;
import handmadeguns.event.RenderTickSmoothing;
import handmadeguns.items.*;
import handmadeguns.items.guns.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

import javax.script.Invocable;
import javax.script.ScriptException;
import javax.vecmath.Vector3d;
import java.nio.FloatBuffer;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;
import static handmadeguns.event.HMGEventZoom.setUp3DView;
import static handmadeguns.event.RenderTickSmoothing.smooth;
import static java.lang.Math.abs;
import static org.lwjgl.opengl.GL11.*;

public class HMGRenderItemGun_U_NEW implements IItemRenderer {
	public static boolean firstPerson_SprintState = false;
	public static boolean prevSprintState = false;
	public static boolean firstPerson_ADSState = false;
	public static boolean prevADSState = false;
	public static boolean firstPerson_ReloadState = false;
	public static boolean prevReloadState = false;
	private static FloatBuffer colorBuffer = GLAllocation.createDirectFloatBuffer(16);
	private IModelCustom model;
	private ResourceLocation guntexture;
	public static float smoothing;
	public float modelscala;
	public static boolean isUnder;
	public static boolean isfirstperson;







	private float modelPosX;
	private float modelPosX_Normal;
	private float modelPosX_Red;
	private float modelPosX_Scope;

	private float modelPosY;
	private float modelPosY_Normal;
	private float modelPosY_Red;
	private float modelPosY_Scope;

	private float modelPosZ;
	private float modelPosZ_Normal;
	private float modelPosZ_Red;
	private float modelPosZ_Scope;

	private float thirdmodelPosX;
	private float thirdmodelPosX_Normal;
	private float thirdmodelPosX_Red;
	private float thirdmodelPosX_Scope;

	private float thirdmodelPosY;
	private float thirdmodelPosY_Normal;
	private float thirdmodelPosY_Red;
	private float thirdmodelPosY_Scope;

	private float thirdmodelPosZ;
	private float thirdmodelPosZ_Normal;
	private float thirdmodelPosZ_Red;
	private float thirdmodelPosZ_Scope;


	private float thirdmodelRotationX;
	private float thirdmodelRotationX_Normal;
	private float thirdmodelRotationX_Red;
	private float thirdmodelRotationX_Scope;

	private float thirdmodelRotationY;
	private float thirdmodelRotationY_Normal;
	private float thirdmodelRotationY_Red;
	private float thirdmodelRotationY_Scope;

	private float thirdmodelRotationZ;
	private float thirdmodelRotationZ_Normal;
	private float thirdmodelRotationZ_Red;
	private float thirdmodelRotationZ_Scope;

	private float modelRotationX;
	private float modelRotationX_Normal;
	private float modelRotationX_Red;
	private float modelRotationX_Scope;

	private float modelRotationY;
	private float modelRotationY_Normal;
	private float modelRotationY_Red;
	private float modelRotationY_Scope;

	private float modelRotationZ;
	private float modelRotationZ_Normal;
	private float modelRotationZ_Red;
	private float modelRotationZ_Scope;





	public float Sprintrotationx = 20F;
	public float Sprintrotationy = 60F;
	public float Sprintrotationz = 0F;
	public float Sprintoffsetx = 0.5F;
	public float Sprintoffsety = 0.0F;
	public float Sprintoffsetz = 0.5F;


	private float onads_modelPosY;

	private float onads_modelPosY_Normal;
	private float onads_modelPosY_Red;
	private float onads_modelPosY_Scope;

	private float onads_modelPosX;

	private float onads_modelPosX_Normal;
	private float onads_modelPosX_Red;
	private float onads_modelPosX_Scope;

	private float onads_modelPosZ;
	private float onads_modelPosZ_Normal;
	private float onads_modelPosZ_Red;
	private float onads_modelPosZ_Scope;

	private float onads_modelRotationX;
	private float onads_modelRotationX_Normal;
	private float onads_modelRotationX_Red;
	private float onads_modelRotationX_Scope;

	private float onads_modelRotationY;
	private float onads_modelRotationY_Normal;
	private float onads_modelRotationY_Red;
	private float onads_modelRotationY_Scope;

	private float onads_modelRotationZ;
	private float onads_modelRotationZ_Normal;
	private float onads_modelRotationZ_Red;
	private float onads_modelRotationZ_Scope;


	public float jump = 0;



	private boolean armtrue;



	public static NBTTagCompound nbt;
	public static ItemStack[] items = new ItemStack[6];
	public static HMGItem_Unified_Guns gunitem;



	public static boolean isPlacedGun = false;
	public static float turretYaw = 0;
	public static float turretPitch = 0;

	public PartsRender_Gun partsRender_gun;
	public HMGRenderItemGun_U_NEW(IModelCustom modelgun, ResourceLocation texture, float scala) {
		partsRender_gun = new PartsRender_Gun();
		partsRender_gun.model = model = modelgun;
		partsRender_gun.texture = guntexture = texture;

		partsRender_gun.modelscala = this.modelscala = scala;
	}
	public void reloadConstructor(IModelCustom modelgun, ResourceLocation texture, float scala) {
		partsRender_gun = new PartsRender_Gun();
		partsRender_gun.model = model = modelgun;
		partsRender_gun.texture = guntexture = texture;

		partsRender_gun.modelscala = this.modelscala = scala;
	}

	public void setarmOffsetAndRotationR(float px,float py,float pz,float rx,float ry,float rz){
		partsRender_gun.armoffsetxr = px;
		partsRender_gun.armoffsetyr = py;
		partsRender_gun.armoffsetzr = pz;
		partsRender_gun.armrotationxr = rx;
		partsRender_gun.armrotationyr = ry;
		partsRender_gun.armrotationzr = rz;
	}
	public void setSprintOffsetAndRotation(float px,float py,float pz,float rx,float ry,float rz){
		Sprintrotationx = rx;
		Sprintrotationy = ry;
		Sprintrotationz = rz;
		Sprintoffsetx = px;
		Sprintoffsety = py;
		Sprintoffsetz = pz;
	}
	public void setarmOffsetAndRotationL(float px,float py,float pz,float rx,float ry,float rz){
		partsRender_gun.armoffsetxl = px;
		partsRender_gun.armoffsetyl = py;
		partsRender_gun.armoffsetzl = pz;
		partsRender_gun.armrotationxl = rx;
		partsRender_gun.armrotationyl = ry;
		partsRender_gun.armrotationzl = rz;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {

		switch (type) {
			// case 1: //entity third person
			case INVENTORY:
			case FIRST_PERSON_MAP:
				return false;
			case EQUIPPED_FIRST_PERSON:
			case ENTITY:
			case EQUIPPED:
				return true;
		}
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		switch (type) {
			// case 1:
			case INVENTORY:
			case FIRST_PERSON_MAP:
				return false;
			case EQUIPPED_FIRST_PERSON:
			case ENTITY:
			case EQUIPPED:
				return true;
		}
		return false;
	}

	float rotez;
	float rotey;
	float rotex;

	static Object[] datas;
	int pass = 0;
	@Override
	public void renderItem(ItemRenderType type, ItemStack gunstack, Object... data) {

//		glMaterialf(GL_FRONT_AND_BACK,GL_SHININESS,120);
		HMGItem_Unified_Guns gunitem;
		if (gunstack.getItem() instanceof HMGItem_Unified_Guns)
			gunitem = (HMGItem_Unified_Guns) gunstack.getItem();
		else {

			GL11.glDepthMask(true);
			GL11.glDisable(GL_BLEND);
			return;
		}

		boolean skipAfter = false;
		Invocable invocable = (Invocable) gunitem.gunInfo.renderscript;
		if(invocable != null){
			try {
				skipAfter = (boolean) ((Invocable)gunitem.gunInfo.script).invokeFunction("GunModelRender_New", this,gunitem,gunstack);
			} catch (ScriptException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		if(!skipAfter)rendering(type,gunstack,data);
		if(invocable != null){
			try {
				((Invocable)gunitem.gunInfo.script).invokeFunction("GunModelRender_New_post", this,gunitem,gunstack);
			} catch (ScriptException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}
	public void rendering(ItemRenderType type, ItemStack gunstack, Object... data){
		HMGItem_Unified_Guns gunitem = null;
		if (gunstack.getItem() instanceof HMGItem_Unified_Guns)
			gunitem = (HMGItem_Unified_Guns) gunstack.getItem();
		HMGRenderItemGun_U_NEW.gunitem = gunitem;
		datas = data;
		float scala = this.modelscala;
		gunitem.checkTags(gunstack);
		nbt = gunstack.getTagCompound();
		items[0] = null;
		items[1] = null;
		items[2] = null;
		items[3] = null;
		items[4] = null;
		items[5] = null;
		NBTTagList tags = (NBTTagList) nbt.getTag("Items");
		if (tags != null) {
			for (int i = 0; i < tags.tagCount(); i++)//133
			{
				NBTTagCompound tagCompound = tags.getCompoundTagAt(i);
				int slot = tagCompound.getByte("Slot");
				if (slot >= 0 && slot < items.length) {
					items[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
				}
			}
		}

		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glPushMatrix();
		GL11.glEnable(GL_BLEND);
		GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1, 1, 1, 1F);
		switch (type) {
			case INVENTORY:
				glMatrixForRenderInInventory();
				break;
			case EQUIPPED_FIRST_PERSON://first
			{

				for(pass = 0;pass<2;pass++) {
					if (pass == 1) {
						glEnable(GL_BLEND);
						glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
						GL11.glDepthMask(false);
						glAlphaFunc(GL_LEQUAL, 1);
					} else {
						GL11.glDepthMask(true);
						glAlphaFunc(GL_EQUAL, 1);
					}
					partsRender_gun.pass = pass;
					isfirstperson = true;
					EntityLivingBase entity = (EntityLivingBase) data[1];
					PartsRender_Gun.curretnEntity = entity;
					Minecraft.getMinecraft().renderEngine.bindTexture(guntexture);
					GL11.glPushMatrix();
					ItemStack itemstackSight = items[1];
					if (firstPerson_ADSState && prevADSState) {
						if (itemstackSight != null && itemstackSight.getItem() instanceof HMGItemSightBase) {
							if (((HMGItemSightBase) itemstackSight.getItem()).scopeonly) {
								GL11.glPopMatrix();//glend1
								break;
							} else if (itemstackSight.getItem() instanceof HMGItemAttachment_reddot) {
								if (!gunitem.gunInfo.zoomrer) {
									GL11.glPopMatrix();//glend1
									break;
								}
							} else if (itemstackSight.getItem() instanceof HMGItemAttachment_scope) {
								if (!gunitem.gunInfo.zoomres) {
									GL11.glPopMatrix();//glend1
									break;
								}
							}
						} else {
							if (!gunitem.gunInfo.zoomren) {
								GL11.glPopMatrix();//glend1
								break;
							}
						}
					}
					thirdmodelPosX = thirdmodelPosX_Normal;
					thirdmodelPosY = thirdmodelPosY_Normal;
					thirdmodelPosZ = thirdmodelPosZ_Normal;
					if (itemstackSight == null) {
						modelPosX = modelPosX_Normal;
						modelPosY = modelPosY_Normal;
						modelPosZ = modelPosZ_Normal;

						modelRotationX = modelRotationX_Normal;
						modelRotationY = modelRotationY_Normal;
						modelRotationZ = modelRotationZ_Normal;

						onads_modelPosX = onads_modelPosX_Normal;
						onads_modelPosY = onads_modelPosY_Normal;
						onads_modelPosZ = onads_modelPosZ_Normal;

						onads_modelRotationX = onads_modelRotationX_Normal;
						onads_modelRotationY = onads_modelRotationY_Normal;
						onads_modelRotationZ = onads_modelRotationZ_Normal;
					} else {
						if (itemstackSight.getItem() instanceof HMGItemSightBase && ((HMGItemSightBase) itemstackSight.getItem()).needgunoffset) {
							modelPosX = modelPosX_Normal - ((HMGItemSightBase) itemstackSight.getItem()).gunoffset[0] * scala / 2;
							modelPosY = modelPosY_Normal - ((HMGItemSightBase) itemstackSight.getItem()).gunoffset[1] * scala / 2;
							modelPosZ = modelPosZ_Normal - ((HMGItemSightBase) itemstackSight.getItem()).gunoffset[2] * scala / 2;

							modelRotationX = modelRotationX_Normal + ((HMGItemSightBase) itemstackSight.getItem()).gunrotation[0];
							modelRotationY = modelRotationY_Normal + ((HMGItemSightBase) itemstackSight.getItem()).gunrotation[1];
							modelRotationZ = modelRotationZ_Normal + ((HMGItemSightBase) itemstackSight.getItem()).gunrotation[2];
//
//						onads_modelPosX = onads_modelPosX_Normal - ((HMGItemSightBase) itemstackSight.getItem()).gunoffset[0]*scala/2;
//						onads_modelPosY = onads_modelPosY_Normal - ((HMGItemSightBase) itemstackSight.getItem()).gunoffset[1]*scala/2;
//						onads_modelPosZ = onads_modelPosZ_Normal - ((HMGItemSightBase) itemstackSight.getItem()).gunoffset[2]*scala/2;
							onads_modelPosX = -(partsRender_gun.sightattachoffset[0] + ((HMGItemSightBase) itemstackSight.getItem()).gunoffset[0]) * scala;
							onads_modelPosY = -(partsRender_gun.sightattachoffset[1] + ((HMGItemSightBase) itemstackSight.getItem()).gunoffset[1]) * scala;
							onads_modelPosZ = -(partsRender_gun.sightattachoffset[2] + ((HMGItemSightBase) itemstackSight.getItem()).gunoffset[2]) * scala;
							onads_modelRotationX = onads_modelRotationX_Normal + ((HMGItemSightBase) itemstackSight.getItem()).gunrotation[0];
							onads_modelRotationY = onads_modelRotationY_Normal + ((HMGItemSightBase) itemstackSight.getItem()).gunrotation[1];
							onads_modelRotationZ = onads_modelRotationZ_Normal + ((HMGItemSightBase) itemstackSight.getItem()).gunrotation[2];
						} else if (itemstackSight.getItem() instanceof HMGItemAttachment_reddot) {
							modelPosX = modelPosX_Red;
							modelPosY = modelPosY_Red;
							modelPosZ = modelPosZ_Red;

							modelRotationX = modelRotationX_Red;
							modelRotationY = modelRotationY_Red;
							modelRotationZ = modelRotationZ_Red;

							onads_modelPosX = onads_modelPosX_Red;
							onads_modelPosY = onads_modelPosY_Red;
							onads_modelPosZ = onads_modelPosZ_Red;

							onads_modelRotationX = onads_modelRotationX_Red;
							onads_modelRotationY = onads_modelRotationY_Red;
							onads_modelRotationZ = onads_modelRotationZ_Red;
						} else if (itemstackSight.getItem() instanceof HMGItemAttachment_scope) {
							modelPosX = modelPosX_Scope;
							modelPosY = modelPosY_Scope;
							modelPosZ = modelPosZ_Scope;

							modelRotationX = modelRotationX_Scope;
							modelRotationY = modelRotationY_Scope;
							modelRotationZ = modelRotationZ_Scope;

							onads_modelPosX = onads_modelPosX_Scope;
							onads_modelPosY = onads_modelPosY_Scope;
							onads_modelPosZ = onads_modelPosZ_Scope;

							onads_modelRotationX = onads_modelRotationX_Scope;
							onads_modelRotationY = onads_modelRotationY_Scope;
							onads_modelRotationZ = onads_modelRotationZ_Scope;
						}
					}
					setUp3DView(Minecraft.getMinecraft(),smoothing);

					EntityPlayer entityplayer = (EntityPlayer)Minecraft.getMinecraft().thePlayer;
					float f1 = entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified;
					float f2 = -(entityplayer.distanceWalkedModified + f1 * smoothing);
					float f3 = entityplayer.prevCameraYaw + (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * smoothing;
					float f4 = entityplayer.prevCameraPitch + (entityplayer.cameraPitch - entityplayer.prevCameraPitch) * smoothing;
					GL11.glTranslatef(MathHelper.sin(f2 * (float)Math.PI) * f3 * 0.5F, -Math.abs(MathHelper.cos(f2 * (float)Math.PI) * f3), 0.0F);
					GL11.glRotatef(MathHelper.sin(f2 * (float)Math.PI) * f3 * 3.0F, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(Math.abs(MathHelper.cos(f2 * (float)Math.PI - 0.2F) * f3) * 5.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(f4, 1.0F, 0.0F, 0.0F);

					boolean isreloading = this.getbooleanfromnbt("IsReloading");
					if(firstPerson_ReloadState) {
						if (prevReloadState) {//リロード中
							this.setUpGunPos_equipe(0);
						} else if (prevSprintState) {//スプリント中にリロード開始
							this.setUpGunPos_equipe_sprint(0,1 - smoothing);
						} else if (prevADSState) {//ADS中にリロード開始
							this.setUpGunPos_ADS(-1.4f, 1 - smoothing);
						}
					}else if (firstPerson_ADSState && prevADSState) {//リロードは初期位置で行っているのでリロード終了時移動の必要は無し
						this.setUpGunPos_ADS(-1.4f);
					} else if(firstPerson_ADSState){//走り始め
						this.setUpGunPos_ADS(-1.4f,smoothing);
					} else if(prevADSState){//走り終わり
						this.setUpGunPos_ADS(-1.4f,1-smoothing);
					}else if (firstPerson_SprintState && prevSprintState) {
						this.setUpGunPos_equipe_sprint(0,1);
					} else if(firstPerson_SprintState){//走り始め
						this.setUpGunPos_equipe_sprint(0,smoothing);
					} else if(prevSprintState){//走り終わり
						this.setUpGunPos_equipe_sprint(0,1-smoothing);
					}else {
						this.setUpGunPos_equipe(0);
					}
					GL11.glScalef(this.modelscala, this.modelscala, this.modelscala);
					rendering_situation(gunstack,entity);
					GL11.glPopMatrix();
					isfirstperson = false;
				}
				break;
			}
			case EQUIPPED: {//thrid
				pass = MinecraftForgeClient.getRenderPass();
				if(pass == 1) {
					glEnable(GL_BLEND);
					glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
					GL11.glDepthMask(false);
					glAlphaFunc(GL_LEQUAL, 1);
				}else {
					GL11.glDepthMask(true);
					glAlphaFunc(GL_EQUAL, 1);
				}
				partsRender_gun.pass = pass;
				isfirstperson = false;
				EntityLivingBase entity = (EntityLivingBase) data[1];
				PartsRender_Gun.curretnEntity = entity;
				Minecraft.getMinecraft().renderEngine.bindTexture(guntexture);
				GL11.glPushMatrix();
				GL11.glScalef(1f / 2f, 1f / 2f, 1f / 2f);
				if (entity instanceof EntityPlayer) {
					this.glMatrixForRenderInEntityPlayer(-0.75f);
					GL11.glScalef(scala, scala, scala);
				} else {
					this.glMatrixForRenderInEntity(-0.2f);
					GL11.glScalef(-scala * 0.6f, scala * 0.6f, scala * 0.6f);
				}
				GL11.glScalef(gunitem.gunInfo.inworldScale, gunitem.gunInfo.inworldScale, gunitem.gunInfo.inworldScale);
				rendering_situation(gunstack,entity);
				GL11.glPopMatrix();
				break;
			}
			case ENTITY: {
				pass = MinecraftForgeClient.getRenderPass();
				if(pass == 1) {
					glEnable(GL_BLEND);
					glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
					GL11.glDepthMask(false);
					glAlphaFunc(GL_LEQUAL, 1);
				}else {
					GL11.glDepthMask(true);
					glAlphaFunc(GL_EQUAL, 1);
				}
				partsRender_gun.pass = pass;
				isfirstperson = false;
				Minecraft.getMinecraft().renderEngine.bindTexture(guntexture);
				GL11.glPushMatrix();
				GL11.glScalef(0.4f * scala * gunitem.gunInfo.inworldScale * (isPlacedGun ? gunitem.gunInfo.onTurretScale : 1), 0.4f * scala * gunitem.gunInfo.inworldScale * (isPlacedGun ? gunitem.gunInfo.onTurretScale : 1), 0.4f * scala * gunitem.gunInfo.inworldScale * (isPlacedGun ? gunitem.gunInfo.onTurretScale : 1));
				rendering_situation(gunstack,null);
				GL11.glPopMatrix();
				smoothing = RenderTickSmoothing.smooth;
				break;
			}
			case FIRST_PERSON_MAP:
				break;
		}

		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
		GL11.glPopMatrix();
		GL11.glPopAttrib();
	}


	public void glMatrixForRenderInInventory() {
		GL11.glRotatef(15F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(-30F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(1900F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-0.8F, -1.2F, -0.1F);
	}

	public void setUpGunPos_equipe(float reco) {
		GL11.glRotatef(180f, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(180f, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(modelPosX, modelPosY, modelPosZ + 1.4f);// -0.2F//-0.7,0.7,0
	}

	public void setUpGunPos_equipe_sprint(float reco, float interPole) {
		GL11.glRotatef(180f, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(180f, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(modelPosX, modelPosY, modelPosZ + 1.4f);// -0.2F//-0.7,0.7,0

		GL11.glRotatef(Sprintrotationx * interPole, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(Sprintrotationy * interPole, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(Sprintrotationz * interPole, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(Sprintoffsetx * interPole, Sprintoffsety * interPole, Sprintoffsetz * interPole);
	}

	//ADS
	public void setUpGunPos_ADS(float reco) {
		GL11.glRotatef(180f, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(180f, 0.0F, 0.0F, 1.0F);
		if(gunitem != null && nbt != null && gunitem.gunInfo.sightOffset_zeroIn != null && nbt.getInteger("currentElevation") >= 0 && gunitem.gunInfo.sightOffset_zeroIn.length>nbt.getInteger("currentElevation")) {
			Vector3d sightOffset_zeroIn = gunitem.gunInfo.sightOffset_zeroIn[nbt.getInteger("currentElevation")];
			GL11.glTranslatef((float) sightOffset_zeroIn.x / modelscala,
					(float) sightOffset_zeroIn.y / modelscala,
					(float) sightOffset_zeroIn.z / modelscala);// 0.694,1.03,-1.0//-1.4F
		}
		GL11.glTranslatef(onads_modelPosX,
				onads_modelPosY,
				onads_modelPosZ);// 0.694,1.03,-1.0//-1.4F
		GL11.glRotatef(onads_modelRotationY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(onads_modelRotationX, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(onads_modelRotationZ, 0.0F, 0.0F, 1.0F);
	}
	public void setUpGunPos_ADS(float reco,float interPole) {
		GL11.glRotatef(180f, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(180f, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(modelPosX * (1 - interPole), modelPosY * (1 - interPole), (modelPosZ + 1.4f) * (1 - interPole));// -0.2F//-0.7,0.7,0

		if(gunitem != null && nbt != null && gunitem.gunInfo.sightOffset_zeroIn != null && nbt.getInteger("currentElevation") >= 0 && gunitem.gunInfo.sightOffset_zeroIn.length>nbt.getInteger("currentElevation")) {
			Vector3d sightOffset_zeroIn = gunitem.gunInfo.sightOffset_zeroIn[nbt.getInteger("currentElevation")];
			GL11.glTranslatef((float) sightOffset_zeroIn.x / modelscala * interPole,
					(float) sightOffset_zeroIn.y / modelscala * interPole,
					(float) sightOffset_zeroIn.z / modelscala * interPole);// 0.694,1.03,-1.0//-1.4F
		}
		GL11.glTranslatef(onads_modelPosX * interPole,
				onads_modelPosY * interPole,
				onads_modelPosZ * interPole);// 0.694,1.03,-1.0//-1.4F
		GL11.glRotatef(onads_modelRotationY * interPole, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(onads_modelRotationX * interPole, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(onads_modelRotationZ * interPole, 0.0F, 0.0F, 1.0F);
	}

	public void glMatrixForRenderInEntity(float reco) {
		GL11.glRotatef(190F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-1.3f + thirdmodelPosX/2, 1.55f + thirdmodelPosY/2, 0.3f + reco + thirdmodelPosZ/2);//-0.4
	}

	public void glMatrixForRenderInEntityPlayer(float reco) {
		GL11.glRotatef(110F, 1.0F, 0.0F, 0.0F);// 90//110
		GL11.glRotatef(-20F, 0.0F, 1.0F, 0.0F);// 0
		GL11.glRotatef(135F, 0.0F, 0.0F, 1.0F);// 130
		GL11.glRotatef(thirdmodelRotationY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(thirdmodelRotationX, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(thirdmodelRotationZ, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(0.2F + thirdmodelPosX, -1.75F + thirdmodelPosY, -0.8f + reco + thirdmodelPosZ);
	}

	public ResourceLocation getEntityTexture(AbstractClientPlayer p_110775_1_)
	{
		return p_110775_1_.getLocationSkin();
	}
	public Minecraft getminecraft(){
		return Minecraft.getMinecraft();
	}
	public float getfloatfromnbt(String name){
		return nbt.getFloat(name);
	}
	public int getintfromnbt(String name){
		return nbt.getInteger(name);
	}
	public boolean getbooleanfromnbt(String name){
		return nbt.getBoolean(name);
	}
	public byte getbytefromnbt(String name){
		return nbt.getByte(name);
	}

	public float getSmoothing(){
		return smoothing;
	}

	public void bindPlayertexture(Entity entityplayer) {
		if (entityplayer instanceof AbstractClientPlayer) {
			ResourceLocation resourcelocation = this.getEntityTexture((AbstractClientPlayer) entityplayer);
			if (resourcelocation == null) {
				resourcelocation = AbstractClientPlayer.getLocationSkin("default");
			}
			Minecraft.getMinecraft().renderEngine.bindTexture(resourcelocation);
		}
	}

//	public void renderarm(float armrotationxl,float armrotationyl,float armrotationzl,float armoffsetxl,float armoffsetyl,float armoffsetzl,
//						  float armrotationxr,float armrotationyr,float armrotationzr,float armoffsetxr,float armoffsetyr,float armoffsetzr){
//		GL11.glScalef(1/modelscala,1/modelscala,1/modelscala);
//		modelBipedMain.bipedLeftArm.rotateAngleX = armrotationxl;
//		modelBipedMain.bipedLeftArm.rotateAngleY = armrotationyl;
//		modelBipedMain.bipedLeftArm.rotateAngleZ = armrotationzl;
//		modelBipedMain.bipedLeftArm.offsetX = armoffsetxl*armoffsetscale;
//		modelBipedMain.bipedLeftArm.offsetY = armoffsetyl*armoffsetscale;
//		modelBipedMain.bipedLeftArm.offsetZ = armoffsetzl*armoffsetscale;
//
//
//		modelBipedMain.bipedRightArm.rotateAngleX = armrotationxr;
//		modelBipedMain.bipedRightArm.rotateAngleY = armrotationyr;
//		modelBipedMain.bipedRightArm.rotateAngleZ = armrotationzr;
//		modelBipedMain.bipedRightArm.offsetX = armoffsetxr*armoffsetscale;
//		modelBipedMain.bipedRightArm.offsetY = armoffsetyr*armoffsetscale;
//		modelBipedMain.bipedRightArm.offsetZ = armoffsetzr*armoffsetscale;
//		GL11.glPushMatrix();
//		modelBipedMain.bipedRightArm.render(0.0625f);
//		modelBipedMain.bipedLeftArm.render(0.0625f);
//		GL11.glPopMatrix();
//	}
//
//	public void renderarmL(){
//		if(isfirstperson) {
//			GL11.glPushMatrix();
//			ResourceLocation resourcelocation = this.getEntityTexture(HMG_proxy.getMCInstance().thePlayer);
//			if (resourcelocation == null) {
//				resourcelocation = AbstractClientPlayer.getLocationSkin("default");
//			}
//			Minecraft.getMinecraft().renderEngine.bindTexture(resourcelocation);
//			GL11.glScalef(1 / modelscala, 1 / modelscala, 1 / modelscala);
//			GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
//			GL11.glTranslatef(0F, -0.5F, 0F);
//			modelBipedMain.bipedLeftArm.rotateAngleX = armrotationzl;
//			modelBipedMain.bipedLeftArm.rotateAngleY = armrotationyl;
//			modelBipedMain.bipedLeftArm.rotateAngleZ = 0;
//			modelBipedMain.bipedLeftArm.offsetX = armoffsetxl * armoffsetscale;
//			modelBipedMain.bipedLeftArm.offsetY = armoffsetyl * armoffsetscale;
//			modelBipedMain.bipedLeftArm.offsetZ = armoffsetzl * armoffsetscale;
//			modelBipedMain.bipedLeftArm.render(0.0625f);
//			Minecraft.getMinecraft().renderEngine.bindTexture(guntexture);
//			GL11.glScalef(modelscala, modelscala, modelscala);
//			GL11.glPopMatrix();
//		}
//	}
//	public void renderarmR(){
//		if(isfirstperson) {
//			GL11.glPushMatrix();
//			ResourceLocation resourcelocation = this.getEntityTexture(HMG_proxy.getMCInstance().thePlayer);
//			if (resourcelocation == null) {
//				resourcelocation = AbstractClientPlayer.getLocationSkin("default");
//			}
//			Minecraft.getMinecraft().renderEngine.bindTexture(resourcelocation);
//			GL11.glScalef(1 / modelscala, 1 / modelscala, 1 / modelscala);
//			GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
//			GL11.glTranslatef(0F, -0.5F, 0F);
//			modelBipedMain.bipedRightArm.rotateAngleX = armrotationzr;
//			modelBipedMain.bipedRightArm.rotateAngleY = armrotationyr;
//			modelBipedMain.bipedRightArm.rotateAngleZ = 0;
//			modelBipedMain.bipedRightArm.offsetX = armoffsetxr * armoffsetscale;
//			modelBipedMain.bipedRightArm.offsetY = armoffsetyr * armoffsetscale;
//			modelBipedMain.bipedRightArm.offsetZ = armoffsetzr * armoffsetscale;
//			modelBipedMain.bipedRightArm.render(0.0625f);
//			Minecraft.getMinecraft().renderEngine.bindTexture(guntexture);
//			GL11.glScalef(modelscala, modelscala, modelscala);
//			GL11.glPopMatrix();
//		}
//	}

	public static boolean isentitysprinting(Entity entity){
		return entity != null && (entity.isSprinting() && !nbt.getBoolean("set_up"));
	}
	private static FloatBuffer setColorBuffer(float p_74521_0_, float p_74521_1_, float p_74521_2_, float p_74521_3_) {
		colorBuffer.clear();
		colorBuffer.put(p_74521_0_).put(p_74521_1_).put(p_74521_2_).put(p_74521_3_);
		colorBuffer.flip();
		/** Float buffer used to set OpenGL material colors */
		return colorBuffer;
	}

	public void rendering_situation(ItemStack gunstack,Entity entity){
		boolean isreloading = this.getbooleanfromnbt("IsReloading");
		int remainbullets = gunitem.remain_Bullet(gunstack);
		if (nbt == null) gunitem.checkTags(gunstack);
		boolean recoiled = this.getbooleanfromnbt("Recoiled");
		int mode = nbt.getInteger("HMGMode");
		if (!gunitem.gunInfo.rates.isEmpty() && gunitem.gunInfo.rates.size() > mode)
			gunitem.gunInfo.cycle = gunitem.gunInfo.rates.get(mode);
		float boltPos = nbt.getByte("Bolt");
		float recoileprogress = 10 - 10 * (boltPos - smoothing) / (gunitem.gunInfo.cycle);
		if(boltPos == 0){
			recoiled = true;
		}
		if(recoileprogress > 10){
			recoiled = true;
		}
		if(recoileprogress < 0){
			recoiled = true;
		}

		if (isreloading) {
			float reloadprogress = this.getintfromnbt("RloadTime") + smoothing;
			if(reloadprogress + smoothing >= gunitem.reloadTime(gunstack)-1)reloadprogress = gunitem.reloadTime(gunstack);
			partsRender_gun.partSidentification(new GunState[]{GunState.Reload}, (float) reloadprogress, remainbullets);
		} else if ((entity != HMG_proxy.getEntityPlayerInstance() && HandmadeGunsCore.Key_ADS(entity)) || firstPerson_ADSState || prevADSState) {
			GunState[] state = new GunState[2];
			state[1] = GunState.ADS;
			int cockingtime = this.getintfromnbt("CockingTime");
			if (cockingtime > 0) {
				float cockingprogress = cockingtime + smooth;
				state[0] = GunState.Cock;
				partsRender_gun.partSidentification(state, cockingprogress, remainbullets);
			} else if (!recoiled) {
				GL11.glRotatef(jump * (10 - recoileprogress), 1.0f, 0.0f, 0.0f);
				state[0] = GunState.Recoil;
				partsRender_gun.partSidentification(state, recoileprogress, remainbullets);
			} else {
				state[0] = GunState.ADS;
				partsRender_gun.partSidentification(state, (float) 0, remainbullets);
			}
		} else {
			if (this.isentitysprinting(entity)) {
				partsRender_gun.partSidentification(new GunState[]{GunState.Default}, (float) 0, remainbullets);
			} else {
				int cockingtime = this.getintfromnbt("CockingTime");
				if (cockingtime > 0) {
					float cockingprogress = cockingtime + smooth;
					partsRender_gun.partSidentification(new GunState[]{GunState.Cock}, cockingprogress, remainbullets);
				} else if (!recoiled) {
					GL11.glRotatef(jump * (10 - recoileprogress), 1.0f, 0.0f, 0.0f);
					partsRender_gun.partSidentification(new GunState[]{GunState.Recoil}, recoileprogress, remainbullets);
				} else {
					partsRender_gun.partSidentification(new GunState[]{GunState.Default}, (float) 0, remainbullets);
				}
			}
//						renderpartsNormal(this,gunitem,gunstack,model,entity,type,data);
		}
	}

//	public void GunPartSidentification(HMGGunParts part, GunState[] states, float flame, int remainbullets){//��Ԃŏ�������
//		for(GunState state : states) {
//			switch (state) {
//				case Recoil:
//					if (part.rendering_Recoil) {
//						if (part.hasMotionRecoil) {
//							HMGGunParts_Motion_PosAndRotation OffsetAndRotation = part.getRecoilmotion(flame + smooth);
//
//							GunPartSidentification_Attach(part, state, flame, remainbullets, OffsetAndRotation);
//						} else {
//							HMGGunParts_Motion_PosAndRotation OffsetAndRotation = part.getRenderinfOfRecoil();
//
//							GunPartSidentification_Attach(part, state, flame, remainbullets, OffsetAndRotation);
//						}
//						return;
//					}
//					break;
//				case ADS:
//					if (part.rendering_Ads) {
//						GunPartSidentification_Attach(part, state, flame, remainbullets, part.getRenderinfOfADS());
//						return;
//					}
//					break;
//				case Cock:
//					if (part.rendering_Cock) {
//						if (part.hasMotionCock) {
//							HMGGunParts_Motion_PosAndRotation OffsetAndRotation = part.getcockmotion(flame + smooth);
//
//							GunPartSidentification_Attach(part, state, flame, remainbullets, OffsetAndRotation);
//						} else {
//							HMGGunParts_Motion_PosAndRotation OffsetAndRotation = part.getRenderinfOfCock();
//
//							GunPartSidentification_Attach(part, state, flame, remainbullets, OffsetAndRotation);
//						}
//						return;
//					}
//					break;
//				case Reload:
//					if (part.rendering_Reload) {
//						HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation = part.getRenderinfCenter();
//						if (part.hasMotionReload) {
//							HMGGunParts_Motion_PosAndRotation OffsetAndRotation = part.getReloadmotion(flame + smooth);
//
//							GunPartSidentification_Attach(part, state, flame, remainbullets, OffsetAndRotation);
//						} else {
//							HMGGunParts_Motion_PosAndRotation OffsetAndRotation = part.getRenderinfOfReload();
//
//							GunPartSidentification_Attach(part, state, flame, remainbullets, OffsetAndRotation);
//						}
//						return;
//					}
//					break;
//				case Default:
//					if (part.rendering_Def) {
//						GunPartSidentification_Attach(part, state, flame, remainbullets, part.getRenderinfDefault_offset());
//						return;
//					}
//					break;
//			}
//		}
//	}
//
//	public void GunPartSidentification_Attach(HMGGunParts part, GunState state, float flame, int remainbullets, HMGGunParts_Motion_PosAndRotation OffsetAndRotation){
//		if(gunitem.gunInfo.magazine.length >1) {
//			if (part.current_magazineType != null) {
//				int currentmagazineid = nbt.getInteger("getcurrentMagazine");
//				if (!part.current_magazineType.get(currentmagazineid)) return;
//			}
//			if (part.select_magazineType != null) {
//				int selectmagazineid = nbt.getInteger("get_selectingMagazine");
//				if (!part.select_magazineType.get(selectmagazineid)) return;
//			}
//		}
//		if(OffsetAndRotation != null)
//			if(part.isattachpart) {
//				if (items[1] != null) {//�T�C�g
//					if (part.issightbase && items[1].getItem() instanceof HMGItemSightBase) {
//						IItemRenderer attachrender = MinecraftForgeClient.getItemRenderer(items[1], ItemRenderType.EQUIPPED);
//						if (attachrender instanceof HMGRenderItemCustom) {
//							GunPart_Render_attach(part, state, flame, remainbullets, OffsetAndRotation,sightattachoffset,sightattachrotation,((HMGRenderItemCustom) attachrender));
//							return;
//						}
//					} else if (part.isscope && items[1].getItem() instanceof HMGItemAttachment_scope) {
//						GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
//						return;
//					} else if (part.isdot && items[1].getItem() instanceof HMGItemAttachment_reddot) {
//						GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
//						return;
//					}
//				} else if (part.issight) {
//					GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
//					return;
//				}
//				if (items[2] != null) {//�I�[�o�[�o�����n
//					if (part.islight && items[2].getItem() instanceof HMGItemAttachment_light) {
//						GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
//						return;
//					} else if (part.islasersight && items[2].getItem() instanceof HMGItemAttachment_laser) {
//						GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
//						return;
//					} else if (part.isoverbarrelbase && items[2].getItem() instanceof HMGItemAttachmentBase) {
//						IItemRenderer attachrender = MinecraftForgeClient.getItemRenderer(items[2], ItemRenderType.EQUIPPED);
//						if (attachrender instanceof HMGRenderItemCustom) {
//							GunPart_Render_attach(part, state, flame, remainbullets, OffsetAndRotation,overbarrelattachoffset,overbarrelattachrotation,((HMGRenderItemCustom) attachrender));
//							return;
//						}
//					}
//				}
//				if (items[3] != null) {//�o�����n
//					if (part.ismuzzlepart && items[3].getItem() instanceof HMGItemAttachment_Suppressor) {
//						GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
//						return;
//					} else if (part.ismuzzulebase && items[3].getItem() instanceof HMGItemAttachmentBase) {
//						IItemRenderer attachrender = MinecraftForgeClient.getItemRenderer(items[3], ItemRenderType.EQUIPPED);
//						if (attachrender instanceof HMGRenderItemCustom) {
//							GunPart_Render_attach(part, state, flame, remainbullets, OffsetAndRotation,muzzleattachoffset,muzzleattachrotation,((HMGRenderItemCustom) attachrender));
//							return;
//						}
//					} else if (part.issword && items[3].getItem() instanceof HMGItemSwordBase) {
//						GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
//					} else if (part.isswordbase && items[3].getItem() instanceof HMGItemSwordBase) {
//						GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
//						IItemRenderer attachrender = MinecraftForgeClient.getItemRenderer(items[3], ItemRenderType.EQUIPPED);
//						if (attachrender != null) {
//							GL11.glPushMatrix();
//							GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
//							glTranslatef(gunitem.gunInfo.underoffsetpx, gunitem.gunInfo.underoffsetpy, gunitem.gunInfo.underoffsetpz);
//							GL11.glRotatef(gunitem.gunInfo.underrotationy, 0, 1, 0);
//							GL11.glRotatef(gunitem.gunInfo.underrotationx, 1, 0, 0);
//							GL11.glRotatef(gunitem.gunInfo.underrotationz, 0, 0, 1);
//							GL11.glScalef(1/modelscala,1/modelscala,1/modelscala);
//							GL11.glScalef(1/(0.4f),1/(0.4f),1/(0.4f));
//							attachrender.renderItem(ItemRenderType.ENTITY, items[3], datas);
//							Minecraft.getMinecraft().renderEngine.bindTexture(guntexture);
//							GL11.glPopMatrix();
//						}
//					}
//				}
//				if (items[4] != null) {//�A���_�[�o�����n
//					if (part.isgripBase && items[4].getItem() instanceof HMGItemAttachmentBase) {
//						IItemRenderer attachrender = MinecraftForgeClient.getItemRenderer(items[4], ItemRenderType.EQUIPPED);
//						if (attachrender instanceof HMGRenderItemCustom) {
//							GunPart_Render_attach(part, state, flame, remainbullets, OffsetAndRotation,gripattachoffset,gripattachrotation,((HMGRenderItemCustom) attachrender));
//						}
//					} else if (part.isgrip && items[4].getItem() instanceof HMGItemAttachment_grip) {
//						GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
//					} else if (part.issword && items[4].getItem() instanceof HMGItemSwordBase) {
//						GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
//					} else if (part.isswordbase && items[4].getItem() instanceof HMGItemSwordBase) {
//						GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
//						IItemRenderer attachrender = MinecraftForgeClient.getItemRenderer(items[4], ItemRenderType.EQUIPPED);
//						if (attachrender != null) {
//							GL11.glPushMatrix();
//							GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
//							glTranslatef(gunitem.gunInfo.underoffsetpx, gunitem.gunInfo.underoffsetpy, gunitem.gunInfo.underoffsetpz);
//							GL11.glRotatef(gunitem.gunInfo.underrotationy, 0, 1, 0);
//							GL11.glRotatef(gunitem.gunInfo.underrotationx, 1, 0, 0);
//							GL11.glRotatef(gunitem.gunInfo.underrotationz, 0, 0, 1);
//							GL11.glScalef(1/modelscala,1/modelscala,1/modelscala);
//							GL11.glScalef(1/(0.4f),1/(0.4f),1/(0.4f));
//							attachrender.renderItem(ItemRenderType.ENTITY, items[4], datas);
//							Minecraft.getMinecraft().renderEngine.bindTexture(guntexture);
//							GL11.glPopMatrix();
//						}
//					}else if (items[4].getItem() instanceof HMGItem_Unified_Guns) {
//						if (part.isunderGunbase) {
//							IItemRenderer attachrender = MinecraftForgeClient.getItemRenderer(items[4], ItemRenderType.EQUIPPED);
//							if (attachrender != null) {
//								GL11.glPushMatrix();
//								GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
//								glTranslatef(gunitem.gunInfo.underoffsetpx, gunitem.gunInfo.underoffsetpy, gunitem.gunInfo.underoffsetpz);
//								GL11.glRotatef(gunitem.gunInfo.underrotationy, 0, 1, 0);
//								GL11.glRotatef(gunitem.gunInfo.underrotationx, 1, 0, 0);
//								GL11.glRotatef(gunitem.gunInfo.underrotationz, 0, 0, 1);
//								HMGItem_Unified_Guns undergun = (HMGItem_Unified_Guns) items[4].getItem();
//								glTranslatef(gunitem.gunInfo.onunderoffsetpx, gunitem.gunInfo.onunderoffsetpy, gunitem.gunInfo.onunderoffsetpz);
//								GL11.glRotatef(gunitem.gunInfo.onunderrotationy, 0, 1, 0);
//								GL11.glRotatef(gunitem.gunInfo.onunderrotationx, 1, 0, 0);
//								GL11.glRotatef(gunitem.gunInfo.onunderrotationz, 0, 0, 1);
//								if(attachrender instanceof HMGRenderItemGun_U_NEW){
//									((HMGRenderItemGun_U_NEW) attachrender).isUnder = true;
//								}
//								GL11.glScalef(1/(0.4f * modelscala * gunitem.gunInfo.inworldScale),1/(0.4f * modelscala * gunitem.gunInfo.inworldScale),1/(0.4f * modelscala * gunitem.gunInfo.inworldScale));
//
//								attachrender.renderItem(ItemRenderType.ENTITY, items[4], datas);
//								if(attachrender instanceof HMGRenderItemGun_U_NEW){
//									((HMGRenderItemGun_U_NEW) attachrender).isUnder = false;
//								}
//								Minecraft.getMinecraft().renderEngine.bindTexture(guntexture);
//								GL11.glPopMatrix();
//							}
//						} else if (part.isunderGL && gunitem.gunInfo.guntype == 2) {
//							GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
//						} else if (part.isunderSG && gunitem.gunInfo.guntype == 1) {
//							GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
//						}
//					}
//				}else{
//					if (part.isgripcover) {
//						GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
//					}
//				}
//			}else {
//				GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
//			}
//	}
//	public void GunPart_Render_attach(HMGGunParts part, GunState state, float flame, int remainbullets, HMGGunParts_Motion_PosAndRotation OffsetAndRotation,float[] attachoffset,float[] attachrotation,HMGRenderItemCustom attachrender){
//		GL11.glPushMatrix();
//		glTranslatef(attachoffset[0], attachoffset[1], attachoffset[2]);
//		GL11.glRotatef(attachrotation[0], 0, 1, 0);
//		GL11.glRotatef(attachrotation[1], 1, 0, 0);
//		GL11.glRotatef(attachrotation[2], 0, 0, 1);
//		((HMGRenderItemCustom) attachrender).renderaspart();
//		GL11.glPopMatrix();
//		HMG_proxy.getMCInstance().getTextureManager().bindTexture(guntexture);
//		GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
//	}
//	public void GunPart_Render(HMGGunParts part, GunState state, float flame, int remainbullets, HMGGunParts_Motion_PosAndRotation OffsetAndRotation){
//		HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation = part.getRenderinfCenter();
//		GL11.glPushMatrix();
//		if(isPlacedGun && !part.base && part.carryingHandle){
//			GL11.glPopMatrix();
//			return;
//		}
//		if(!isPlacedGun && !part.carryingHandle && part.base){
//			GL11.glPopMatrix();
//			return;
//		}
//		if(OffsetAndRotation != null) {
//			GL11.glTranslatef(OffsetAndRotation.posX, OffsetAndRotation.posY, OffsetAndRotation.posZ);
//			GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
//			GL11.glRotatef(OffsetAndRotation.rotationY, 0, 1, 0);
//			GL11.glRotatef(OffsetAndRotation.rotationX, 1, 0, 0);
//			GL11.glRotatef(OffsetAndRotation.rotationZ, 0, 0, 1);
//			GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
//		}else {
//			GL11.glTranslatef(rotationCenterAndRotation.posX,rotationCenterAndRotation.posY,rotationCenterAndRotation.posZ);
//			GL11.glRotatef(rotationCenterAndRotation.rotationY,0,1,0);
//			GL11.glRotatef(rotationCenterAndRotation.rotationX,1,0,0);
//			GL11.glRotatef(rotationCenterAndRotation.rotationZ,0,0,1);
//			GL11.glTranslatef(-rotationCenterAndRotation.posX,-rotationCenterAndRotation.posY,-rotationCenterAndRotation.posZ);
//		}
//
//		if(isPlacedGun && part.hasbaseYawInfo) {
//			HMGGunParts_Motion_PosAndRotation baserotationCenterAndRotation = part.getYawInfo(turretYaw);
//			if (baserotationCenterAndRotation != null) {
//				GL11.glTranslatef(baserotationCenterAndRotation.posX, baserotationCenterAndRotation.posY, baserotationCenterAndRotation.posZ);
//				GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
//				GL11.glRotatef(baserotationCenterAndRotation.rotationY, 0, 1, 0);
//				GL11.glRotatef(baserotationCenterAndRotation.rotationX, 1, 0, 0);
//				GL11.glRotatef(baserotationCenterAndRotation.rotationZ, 0, 0, 1);
//				GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
//			}
//		}
//		if(isPlacedGun && part.hasbasePitchInfo){
//			HMGGunParts_Motion_PosAndRotation baserotationCenterAndRotation = part.getPitchInfo(turretPitch);
//			if (baserotationCenterAndRotation != null) {
//				GL11.glTranslatef(baserotationCenterAndRotation.posX, baserotationCenterAndRotation.posY, baserotationCenterAndRotation.posZ);
//				GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
//				GL11.glRotatef(baserotationCenterAndRotation.rotationY, 0, 1, 0);
//				GL11.glRotatef(baserotationCenterAndRotation.rotationX, 1, 0, 0);
//				GL11.glRotatef(baserotationCenterAndRotation.rotationZ, 0, 0, 1);
//				GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
//			}
//		}
//
//		if (part.isbullet){
//			if(state == GunState.Recoil)
//				renderParts_Bullet(part,flame/10,remainbullets,rotationCenterAndRotation);
//			else
//				renderParts_Bullet(part,flame,remainbullets,rotationCenterAndRotation);
//		}else {
//			if(part.isLarm){
//				renderarmL();
//			}else if(part.isRarm){
//				renderarmR();
//			}else if((part.underOnly && isUnder) || !(part.underOnly_not && isUnder)) {
//				if(part.reticleAndPlate){
//					glClear(GL_STENCIL_BUFFER_BIT);
//					glEnable(GL_STENCIL_TEST);
//					glStencilMask(1);
//
//					glStencilFunc(
//							GL_ALWAYS,   // GLenum func
//							1,          // GLint ref
//							~0);// GLuint mask
//					glStencilOp(
//							GL_KEEP,
//							GL_KEEP,
//							GL_REPLACE);
//					if (pass != 1) {
//						GL11.glDepthMask(false);
//						glAlphaFunc(GL_ALWAYS, 1);
//						glColorMask(
//								false,   // GLboolean red
//								false,   // GLboolean green
//								false,   // GLboolean blue
//								false);
//					}
//					model.renderPart(part.partsname + "reticlePlate");
//					if (pass != 1) {
//						GL11.glDepthMask(true);
//						glAlphaFunc(GL_EQUAL, 1);
//						glColorMask(
//								true,   // GLboolean red
//								true,   // GLboolean green
//								true,   // GLboolean blue
//								true);
//					}
//
//					GL11.glDisable(GL11.GL_LIGHTING);
//					float lastBrightnessX = OpenGlHelper.lastBrightnessX;
//					float lastBrightnessY = OpenGlHelper.lastBrightnessY;
//					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
//					glDisable(GL_DEPTH_TEST);
//
//
//					glStencilFunc(
//							GL_EQUAL,   // GLenum func
//							1,          // GLint ref
//							~0);// GLuint mask
//
//					glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
//					glAlphaFunc(GL_ALWAYS, 1);
//					GL11.glDepthMask(false);
//
//					GL11.glDepthFunc(GL11.GL_ALWAYS);//強制描画
//					model.renderPart(part.partsname + "reticle");
//					GL11.glDepthFunc(GL11.GL_LEQUAL);
//					GL11.glDepthMask(true);
//					glDisable(GL_STENCIL_TEST);
//					glEnable(GL_DEPTH_TEST);
//					GL11.glEnable(GL11.GL_LIGHTING);
//					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
//
//					if(pass == 1) {
//						glEnable(GL_BLEND);
//						glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//						GL11.glDepthMask(false);
//						glAlphaFunc(GL_LEQUAL, 1);
//					}else {
//						GL11.glDepthMask(true);
//						glAlphaFunc(GL_EQUAL, 1);
//					}
//				}
//				model.renderPart(part.partsname);
//				float lastBrightnessX = OpenGlHelper.lastBrightnessX;
//				float lastBrightnessY = OpenGlHelper.lastBrightnessY;
//				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
//				model.renderPart(part.partsname + "light");
//				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
//			}
//		}
//		for(HMGGunParts parts:part.childs)
//			GunPartSidentification(parts,new GunState[]{state},flame,remainbullets);
//		GL11.glPopMatrix();
//	}


	public void renderParts_Bullet(HMGGunParts part,float flame,int remainbullets,HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation){
		if(part.isavatar){
			if(part.isbelt){
				//�c�e������e��`�悷��ʒu���擾����B
				for (int i = 0; (i < part.Maximum_number_of_bullets && i < remainbullets); i++) {
					HMGGunParts_Motion_PosAndRotation bulletoffset = part.getBulletposition(i - flame);
					if(bulletoffset != null) {
						GL11.glPushMatrix();
						//�����͐�������������߂��̂Ńv�b�V��
						GL11.glTranslatef(bulletoffset.posX, bulletoffset.posY, bulletoffset.posZ);
						GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
						GL11.glRotatef(bulletoffset.rotationY, 0, 1, 0);
						GL11.glRotatef(bulletoffset.rotationX, 1, 0, 0);
						GL11.glRotatef(bulletoffset.rotationZ, 0, 0, 1);
						GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
						model.renderPart(part.partsname);
						float lastBrightnessX = OpenGlHelper.lastBrightnessX;
						float lastBrightnessY = OpenGlHelper.lastBrightnessY;
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
						model.renderPart(part.partsname_light);
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
						GL11.glPopMatrix();
					}
				}
			}else {
				//�e�e�Ƃ��ĕ`��A�e�����J��Ԃ�
				//�e�͒e�����I�t�Z�b�g������
				HMGGunParts_Motion_PosAndRotation bulletoffset = part.getRenderinfOfBullet();
				if(bulletoffset != null) {
					GL11.glTranslatef(bulletoffset.posX * -flame, bulletoffset.posY * -flame, bulletoffset.posZ * -flame);
					GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
					GL11.glRotatef(bulletoffset.rotationY * -flame, 0, 1, 0);
					GL11.glRotatef(bulletoffset.rotationX * -flame, 1, 0, 0);
					GL11.glRotatef(bulletoffset.rotationZ * -flame, 0, 0, 1);
					GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
					for (int i = 0; (i < part.Maximum_number_of_bullets && i < remainbullets); i++) {
						GL11.glTranslatef(bulletoffset.posX, bulletoffset.posY, bulletoffset.posZ);
						GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
						GL11.glRotatef(bulletoffset.rotationY, 0, 1, 0);
						GL11.glRotatef(bulletoffset.rotationX, 1, 0, 0);
						GL11.glRotatef(bulletoffset.rotationZ, 0, 0, 1);
						GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
						model.renderPart(part.partsname);
						float lastBrightnessX = OpenGlHelper.lastBrightnessX;
						float lastBrightnessY = OpenGlHelper.lastBrightnessY;
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
						model.renderPart(part.partsname + "light");
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
					}
				}
			}
		}else {
			if(part.isbelt){
				//�c�e������e��`�悷��ʒu���擾����B
				HMGGunParts_Motion_PosAndRotation bulletoffset = part.getBulletposition(remainbullets + flame);
				if(bulletoffset != null) {
					GL11.glPushMatrix();
					GL11.glTranslatef(bulletoffset.posX, bulletoffset.posY, bulletoffset.posZ);
					GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
					GL11.glRotatef(bulletoffset.rotationY, 0, 1, 0);
					GL11.glRotatef(bulletoffset.rotationX, 1, 0, 0);
					GL11.glRotatef(bulletoffset.rotationZ, 0, 0, 1);
					GL11.glTranslatef(-bulletoffset.posX, -bulletoffset.posY, -bulletoffset.posZ);
					model.renderPart(part.partsname);
					float lastBrightnessX = OpenGlHelper.lastBrightnessX;
					float lastBrightnessY = OpenGlHelper.lastBrightnessY;
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
					model.renderPart(part.partsname + "light");
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
					GL11.glPopMatrix();
				}
			}else {
				//�e�����I�t�Z�b�g������
				HMGGunParts_Motion_PosAndRotation bulletoffset = part.getRenderinfOfBullet();
				if(bulletoffset != null) {
					GL11.glTranslatef(bulletoffset.posX * flame, bulletoffset.posY * flame, bulletoffset.posZ * flame);
					GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
					GL11.glRotatef(bulletoffset.rotationY * flame, 0, 1, 0);
					GL11.glRotatef(bulletoffset.rotationX * flame, 1, 0, 0);
					GL11.glRotatef(bulletoffset.rotationZ * flame, 0, 0, 1);
					GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
					for (int i = 0; (i < part.Maximum_number_of_bullets && i < remainbullets); i++) {
						GL11.glTranslatef(bulletoffset.posX, bulletoffset.posY, bulletoffset.posZ);
						GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
						GL11.glRotatef(bulletoffset.rotationY, 0, 1, 0);
						GL11.glRotatef(bulletoffset.rotationX, 1, 0, 0);
						GL11.glRotatef(bulletoffset.rotationZ, 0, 0, 1);
						GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
					}
					model.renderPart(part.partsname);
					float lastBrightnessX = OpenGlHelper.lastBrightnessX;
					float lastBrightnessY = OpenGlHelper.lastBrightnessY;
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
					model.renderPart(part.partsname + "light");
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
				}
			}
		}
	}

	public void setEqippedOffset_Normal(float x, float y, float z){
		modelPosX = x;
		modelPosY = y;
		modelPosZ = z;
		modelPosX_Normal = x;
		modelPosY_Normal = y;
		modelPosZ_Normal = z;

		modelPosX_Red = x;
		modelPosY_Red = y;
		modelPosZ_Red = z;

		modelPosX_Scope = x;
		modelPosY_Scope = y;
		modelPosZ_Scope = z;
	}
	public void setEqippedOffset_Third(float x, float y, float z){
		thirdmodelPosX = x;
		thirdmodelPosY = y;
		thirdmodelPosZ = z;

		thirdmodelPosX_Normal = x;
		thirdmodelPosY_Normal = y;
		thirdmodelPosZ_Normal = z;

		thirdmodelPosX_Red = x;
		thirdmodelPosY_Red = y;
		thirdmodelPosZ_Red = z;

		thirdmodelPosX_Scope = x;
		thirdmodelPosY_Scope = y;
		thirdmodelPosZ_Scope = z;
	}


	public void setEqippedRotation_Normal(float x,float y,float z){
		modelRotationX = x;
		modelRotationY = y;
		modelRotationZ = z;
		modelRotationX_Normal = x;
		modelRotationY_Normal = y;
		modelRotationZ_Normal = z;
	}


	public void setmodelADSPosAndRotation(float px,float py,float pz,float rx,float ry,float rz){
		onads_modelPosY = py;
		onads_modelPosY_Normal = py;
		onads_modelPosX = px;
		onads_modelPosX_Normal = px;
		onads_modelPosZ = pz;
		onads_modelPosZ_Normal = pz;
		onads_modelRotationX = rx;
		onads_modelRotationX_Normal = rx;
		onads_modelRotationY = ry;
		onads_modelRotationY_Normal = ry;
		onads_modelRotationZ = rz;
		onads_modelRotationZ_Normal = rz;
	}


	public void setADSoffsetRed(float px,float py,float pz){
		onads_modelPosX_Red = px;
		onads_modelPosY_Red = py;
		onads_modelPosZ_Red = pz;
	}


	public void setADSrotationRed(float rx,float ry,float rz){
		onads_modelRotationX_Red = rx;
		onads_modelRotationY_Red = ry;
		onads_modelRotationZ_Red = rz;
	}


	public void setADSoffsetScope(float px,float py,float pz){
		onads_modelPosX_Scope = px;
		onads_modelPosY_Scope = py;
		onads_modelPosZ_Scope = pz;
	}


	public void setADSrotationScope(float rx,float ry,float rz){
		onads_modelRotationX_Scope = rx;
		onads_modelRotationY_Scope = ry;
		onads_modelRotationZ_Scope = rz;
	}
	public void setArmoffsetScale(float value){
		partsRender_gun.armoffsetscale = value;
	}
}
