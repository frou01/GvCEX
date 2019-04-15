package handmadeguns.client.render;

import handmadeguns.HandmadeGunsCore;
import handmadeguns.event.RenderTickSmoothing;
import handmadeguns.items.*;
import handmadeguns.items.guns.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static handmadeguns.event.RenderTickSmoothing.smooth;
import static handmadeguns.HandmadeGunsCore.proxy;
import static java.lang.Math.abs;
import static org.lwjgl.opengl.GL11.*;

public class HMGRenderItemGun_U_NEW implements IItemRenderer {
	private static FloatBuffer colorBuffer = GLAllocation.createDirectFloatBuffer(16);
	private IModelCustom model;
	private ResourceLocation guntexture;
	public static float smoothing;
	private ModelBiped modelBipedMain;
	public float modelscala;
	public boolean isUnder;
	private boolean isfirstperson;







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
	private float armrotationxr;
	private float armrotationyr;
	private float armrotationzr;
	private float armoffsetxr;
	private float armoffsetyr;
	private float armoffsetzr;

	private float armrotationxl;
	private float armrotationyl;
	private float armrotationzl;
	private float armoffsetxl;
	private float armoffsetyl;
	private float armoffsetzl;
	private float armoffsetscale = 1;

	public float muzzleattachoffset[] = new float[3];
	public float muzzleattachrotation[] = new float[3];
	public float sightattachoffset[] = new float[3];
	public float sightattachrotation[] = new float[3];
	public float gripattachoffset[] = new float[3];
	public float gripattachrotation[] = new float[3];
	public float overbarrelattachoffset[] = new float[3];
	public float overbarrelattachrotation[] = new float[3];

	public float turretBasepos[] = new float[3];

	public ArrayList<HMGGunParts> Partslist = new ArrayList<HMGGunParts>();
	private NBTTagCompound nbt;
	ItemStack[] items = new ItemStack[6];
	HMGItem_Unified_Guns gunitem;



	public boolean isPlacedGun = false;
	public float turretYaw = 0;
	public float turretPitch = 0;

	public HMGRenderItemGun_U_NEW(IModelCustom modelgun, ResourceLocation texture, float scala) {
		model = modelgun;
		guntexture = texture;
		this.modelBipedMain = new ModelBiped(0.5F);

		this.modelscala = scala;
	}

	public void setarmOffsetAndRotationR(float px,float py,float pz,float rx,float ry,float rz){
		armoffsetxr = px;
		armoffsetyr = py;
		armoffsetzr = pz;
		armrotationxr = rx;
		armrotationyr = ry;
		armrotationzr = rz;
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
		armoffsetxl = px;
		armoffsetyl = py;
		armoffsetzl = pz;
		armrotationxl = rx;
		armrotationyl = ry;
		armrotationzl = rz;
	}

	public void addParts(HMGGunParts part){
		Partslist.add(part);
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

	Object[] datas;
	@Override
	public void renderItem(ItemRenderType type, ItemStack gunstack, Object... data) {
		datas = data;
		GL11.glEnable(GL_BLEND);
		GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1, 1, 1, 1F);
		float f2 = 1.0F;
		f2 = 60.0F;
//		glMaterialf(GL_FRONT_AND_BACK,GL_SHININESS,120);
		float scala = this.modelscala;
		HMGItem_Unified_Guns gunitem;
		if (gunstack.getItem() instanceof HMGItem_Unified_Guns)
			gunitem = (HMGItem_Unified_Guns) gunstack.getItem();
		else {

			GL11.glDepthMask(true);
			GL11.glDisable(GL_BLEND);
			return;
		}
		nbt = gunstack.getTagCompound();
		if (nbt == null) gunitem.checkTags(gunstack);
		this.gunitem = gunitem;
		nbt = gunstack.getTagCompound();
		items[0] = null;
		items[1] = null;//�T�C�g
		items[2] = null;//���[�U�[�T�C�g��
		items[3] = null;//�}�Y���A�^�b�`�����g
		items[4] = null;//�A���_�[�o����
		items[5] = null;//�}�K�W��
		NBTTagList tags = (NBTTagList)nbt.getTag("Items");
		if(tags != null) {
			for (int i = 0; i < tags.tagCount(); i++)//133
			{
				NBTTagCompound tagCompound = tags.getCompoundTagAt(i);
				int slot = tagCompound.getByte("Slot");
				if (slot >= 0 && slot < items.length) {
					items[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
				}
			}
		}
		rotex = nbt.getFloat("rotex");
		rotey = nbt.getFloat("rotey");
		rotez = nbt.getFloat("rotez");
		switch (type) {
			case INVENTORY:
				glMatrixForRenderInInventory();
				break;
			case EQUIPPED_FIRST_PERSON://first
			{
				isfirstperson =true;
				EntityLivingBase entity = (EntityLivingBase) data[1];
				boolean recoiled = this.getbooleanfromnbt("Recoiled");//���R�C�������ǂ����i�e������u���˂�l�ɕ`�悷�邽�߂̃t���O�j
				boolean isreloading = this.getbooleanfromnbt("IsReloading");//�����[�h�����ǂ���
				int remainbullets = gunitem.getMaxDamage() - gunstack.getItemDamage();//�����[�h�����ǂ���
				Minecraft.getMinecraft().renderEngine.bindTexture(guntexture);
				GL11.glPushMatrix();
				ItemStack itemstackSight = items[1];
				if (HandmadeGunsCore.Key_ADS(entity)) {
					if(itemstackSight != null && itemstackSight.getItem() instanceof HMGItemSightBase) {
						if(((HMGItemSightBase)itemstackSight.getItem()).scopeonly){
							GL11.glPopMatrix();//glend1
							break;
						}else
						if (itemstackSight.getItem() instanceof HMGItemAttachment_reddot){
							if (!gunitem.zoomrer) {
								GL11.glPopMatrix();//glend1
								break;
							}
						} else
						if (itemstackSight.getItem() instanceof HMGItemAttachment_scope) {
							if (!gunitem.zoomres) {
								GL11.glPopMatrix();//glend1
								break;
							}
						}
					}else {
						if (!gunitem.zoomren) {
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
					if(itemstackSight.getItem() instanceof HMGItemSightBase && ((HMGItemSightBase) itemstackSight.getItem()).needgunoffset){
						modelPosX = modelPosX_Normal - ((HMGItemSightBase) itemstackSight.getItem()).gunoffset[0]*scala/2;
						modelPosY = modelPosY_Normal - ((HMGItemSightBase) itemstackSight.getItem()).gunoffset[1]*scala/2;
						modelPosZ = modelPosZ_Normal - ((HMGItemSightBase) itemstackSight.getItem()).gunoffset[2]*scala/2;

						modelRotationX = modelRotationX_Normal + ((HMGItemSightBase) itemstackSight.getItem()).gunrotation[0];
						modelRotationY = modelRotationY_Normal + ((HMGItemSightBase) itemstackSight.getItem()).gunrotation[1];
						modelRotationZ = modelRotationZ_Normal + ((HMGItemSightBase) itemstackSight.getItem()).gunrotation[2];
//
//						onads_modelPosX = onads_modelPosX_Normal - ((HMGItemSightBase) itemstackSight.getItem()).gunoffset[0]*scala/2;
//						onads_modelPosY = onads_modelPosY_Normal - ((HMGItemSightBase) itemstackSight.getItem()).gunoffset[1]*scala/2;
//						onads_modelPosZ = onads_modelPosZ_Normal - ((HMGItemSightBase) itemstackSight.getItem()).gunoffset[2]*scala/2;
						onads_modelPosX =0.694f-(sightattachoffset[0] + ((HMGItemSightBase) itemstackSight.getItem()).gunoffset[0])*scala;
						onads_modelPosY =1.8f-(sightattachoffset[1] + ((HMGItemSightBase) itemstackSight.getItem()).gunoffset[1])*scala;
						onads_modelPosZ =-(sightattachoffset[2] + ((HMGItemSightBase) itemstackSight.getItem()).gunoffset[2])*scala;
						onads_modelRotationX = onads_modelRotationX_Normal + ((HMGItemSightBase) itemstackSight.getItem()).gunrotation[0];
						onads_modelRotationY = onads_modelRotationY_Normal + ((HMGItemSightBase) itemstackSight.getItem()).gunrotation[1];
						onads_modelRotationZ = onads_modelRotationZ_Normal + ((HMGItemSightBase) itemstackSight.getItem()).gunrotation[2];
					}else if ( itemstackSight.getItem() instanceof HMGItemAttachment_reddot) {
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
					} else if ( itemstackSight.getItem() instanceof HMGItemAttachment_scope) {
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
				if (isreloading){
					//���[�h���ɌĂ΂�镔��
					this.glMatrixForRenderInEquipped(-0.2f);
					GL11.glScalef(scala,scala,scala);
					int reloadprogress = this.getintfromnbt("RloadTime");
					for(HMGGunParts parts:Partslist)
						GunPartSidentification(parts,GunState.Reload,reloadprogress,remainbullets);
				}else if (HandmadeGunsCore.Key_ADS(entity)){
					//ADS���ɌĂ΂�镔��
					int cockingtime = this.getintfromnbt("CockingTime");
					if (cockingtime > 0){
						float cockingprogress = cockingtime + this.getSmoothing() - 1;//�R�b�L���O�J�n����̎��ԁifloat�l�j
						this.glMatrixForRenderInEquippedADS(-1.4f);
						GL11.glScalef(scala,scala,scala);
						for(HMGGunParts parts:Partslist)
							GunPartSidentification(parts,GunState.Cock,cockingprogress,remainbullets);
					}else if (!recoiled) {
						this.glMatrixForRenderInEquippedADS(-1.4f);
						GL11.glRotatef(jump * (1 - this.getSmoothing()), 1.0f, 0.0f, 0.0f);//�e�����ˏオ��B���̏�Ԃ��ƃ��f����0,0,0�𒆐S�ɉ�]�B
						float recoileprogress = 10*((float)nbt.getByte("Bolt") + smoothing) / gunitem.cycle;
						GL11.glScalef(scala,scala,scala);
						for(HMGGunParts parts:Partslist)
							GunPartSidentification(parts,GunState.Recoil,recoileprogress,remainbullets);
					} else {
						this.glMatrixForRenderInEquippedADS(-1.4f);
						GL11.glScalef(scala,scala,scala);
						for(HMGGunParts parts:Partslist)
							GunPartSidentification(parts,GunState.ADS,0,remainbullets);
					}
				} else {
					//�ʏ펞�ɌĂ΂�镔��
					if (this.isentitysprinting(entity)) {
						//�����Ă��違�ˌ����łȂ��Ƃ��ɌĂ΂��B
						this.glMatrixForRenderInEquipped(0);
						GL11.glRotatef(Sprintrotationx, 1.0f, 0.0f, 0.0f);
						GL11.glRotatef(Sprintrotationy, 0.0f, 1.0f, 0.0f);
						GL11.glRotatef(Sprintrotationz, 0.0f, 0.0f, 1.0f);
						GL11.glTranslatef(Sprintoffsetx, Sprintoffsety, Sprintoffsetz);
						GL11.glScalef(scala,scala,scala);
						for(HMGGunParts parts:Partslist)
							GunPartSidentification(parts,GunState.Default,0,remainbullets);
					}else{
						int cockingtime = this.getintfromnbt("CockingTime");
						if (cockingtime > 0){
							float cockingprogress = cockingtime + this.getSmoothing() - 1;//�R�b�L���O�J�n����̎��ԁifloat�l�j
							this.glMatrixForRenderInEquipped(-0.2f);
							GL11.glScalef(scala,scala,scala);
							for(HMGGunParts parts:Partslist)
								GunPartSidentification(parts,GunState.Cock,cockingprogress,remainbullets);
						}else if (!recoiled) {
							//�ˌ����1tick���̂݌Ă΂�܂�
							this.glMatrixForRenderInEquipped(-0.2f);
							GL11.glRotatef(jump * (1 - this.getSmoothing()), 1.0f, 0.0f, 0.0f);//�e�����ˏオ��B���̏�Ԃ��ƃ��f����0,0,0�𒆐S�ɉ�]�B
							float recoileprogress = 10*(nbt.getByte("Bolt") + smoothing) / gunitem.cycle;
							GL11.glScalef(scala,scala,scala);
							for(HMGGunParts parts:Partslist)
								GunPartSidentification(parts,GunState.Recoil,recoileprogress,remainbullets);
						} else {
							//�ʏ���
							this.glMatrixForRenderInEquipped(-0.2f);
							GL11.glScalef(scala,scala,scala);
							for(HMGGunParts parts:Partslist)
								GunPartSidentification(parts,GunState.Default,0,remainbullets);
						}
					}
//						renderpartsNormal(this,gunitem,gunstack,model,entity,type,data);
				}
				GL11.glPopMatrix();//�I�����ɂ�����Ă�ōs����o�b�N�A�b�v����߂��܂��B
				isfirstperson =false;

				break;
			}
			case EQUIPPED: {//thrid
				isfirstperson =false;
				EntityLivingBase entity = (EntityLivingBase) data[1];
				boolean recoiled = this.getbooleanfromnbt("Recoiled");//���R�C�������ǂ����i�e������u���˂�l�ɕ`�悷�邽�߂̃t���O�j
				boolean isreloading = this.getbooleanfromnbt("IsReloading");//�����[�h�����ǂ���
				int remainbullets = gunitem.getMaxDamage() - gunstack.getItemDamage();//�����[�h�����ǂ���
				Minecraft.getMinecraft().renderEngine.bindTexture(guntexture);
				GL11.glPushMatrix();
				GL11.glScalef(1f/2f, 1f/2f, 1f/2f);
				if (entity instanceof EntityPlayer) {
					this.glMatrixForRenderInEntityPlayer(-0.75f);
					GL11.glScalef(scala, scala, scala);
				} else {
					this.glMatrixForRenderInEntity(-0.2f);
					GL11.glScalef(-scala * 0.6f, scala * 0.6f, scala * 0.6f);
				}
				GL11.glScalef(gunitem.inworldScale, gunitem.inworldScale, gunitem.inworldScale);
				if (isreloading) {
					//���[�h���ɌĂ΂�镔��
					int reloadprogress = this.getintfromnbt("RloadTime");
					for (HMGGunParts parts : Partslist)
						GunPartSidentification(parts, GunState.Reload, reloadprogress, remainbullets);
				} else if (HandmadeGunsCore.Key_ADS(entity)) {
					//ADS���ɌĂ΂�镔��
					int cockingtime = this.getintfromnbt("CockingTime");
					if (cockingtime > 0) {
						float cockingprogress = cockingtime + this.getSmoothing() - 1;//�R�b�L���O�J�n����̎��ԁifloat�l�j
						for (HMGGunParts parts : Partslist)
							GunPartSidentification(parts, GunState.Cock, cockingprogress, remainbullets);
					} else if (!recoiled) {
						GL11.glRotatef(jump * (1 - this.getSmoothing()), 1.0f, 0.0f, 0.0f);//�e�����ˏオ��B���̏�Ԃ��ƃ��f����0,0,0�𒆐S�ɉ�]�B
						float recoileprogress = 10 * (nbt.getByte("Bolt") + smoothing) / gunitem.cycle;
						for (HMGGunParts parts : Partslist)
							GunPartSidentification(parts, GunState.Recoil, recoileprogress, remainbullets);
					} else {
						for (HMGGunParts parts : Partslist)
							GunPartSidentification(parts, GunState.ADS, 0, remainbullets);
					}
				} else {
					//�ʏ펞�ɌĂ΂�镔��
					if (this.isentitysprinting(entity)) {
						//�����Ă��違�ˌ����łȂ��Ƃ��ɌĂ΂��B
						for (HMGGunParts parts : Partslist)
							GunPartSidentification(parts, GunState.Default, 0, remainbullets);
					} else {
						int cockingtime = this.getintfromnbt("CockingTime");
						if (cockingtime > 0) {
							float cockingprogress = cockingtime + this.getSmoothing() - 1;//�R�b�L���O�J�n����̎��ԁifloat�l�j
							for (HMGGunParts parts : Partslist)
								GunPartSidentification(parts, GunState.Cock, cockingprogress, remainbullets);
						} else if (!recoiled) {
							//�ˌ����1tick���̂݌Ă΂�܂�
							GL11.glRotatef(jump * (1 - this.getSmoothing()), 1.0f, 0.0f, 0.0f);//�e�����ˏオ��B���̏�Ԃ��ƃ��f����0,0,0�𒆐S�ɉ�]�B
							float recoileprogress = 10 * (nbt.getByte("Bolt") + smoothing) / gunitem.cycle;
							for (HMGGunParts parts : Partslist)
								GunPartSidentification(parts, GunState.Recoil, recoileprogress, remainbullets);
						} else {
							//�ʏ���
							for (HMGGunParts parts : Partslist)
								GunPartSidentification(parts, GunState.Default, 0, remainbullets);
						}
					}
//						renderpartsNormal(this,gunitem,gunstack,model,entity,type,data);
				}
				GL11.glPopMatrix();//�I�����ɂ�����Ă�ōs����o�b�N�A�b�v����߂��܂��B
				break;
			}
			case ENTITY: {
				isfirstperson =false;
				boolean recoiled = this.getbooleanfromnbt("Recoiled");//���R�C�������ǂ����i�e������u���˂�l�ɕ`�悷�邽�߂̃t���O�j
				boolean isreloading = this.getbooleanfromnbt("IsReloading");//�����[�h�����ǂ���
				int remainbullets = gunitem.getMaxDamage() - gunstack.getItemDamage();//�����[�h�����ǂ���
				smoothing = isPlacedGun ? RenderTickSmoothing.smooth : 0;
				Minecraft.getMinecraft().renderEngine.bindTexture(guntexture);
				GL11.glPushMatrix();
				GL11.glScalef(0.4f * scala * gunitem.inworldScale * (isPlacedGun ? gunitem.onTurretScale:1),0.4f * scala * gunitem.inworldScale * (isPlacedGun ? gunitem.onTurretScale:1),0.4f * scala * gunitem.inworldScale * (isPlacedGun ? gunitem.onTurretScale:1));
				if (isreloading) {
					//���[�h���ɌĂ΂�镔��
					int reloadprogress = this.getintfromnbt("RloadTime");
					for (HMGGunParts parts : Partslist)
						GunPartSidentification(parts, GunState.Reload, reloadprogress, remainbullets);
				} else {
					//�ʏ펞�ɌĂ΂�镔��
					int cockingtime = this.getintfromnbt("CockingTime");
					if (cockingtime > 0) {
						float cockingprogress = cockingtime + this.getSmoothing() - 1;//�R�b�L���O�J�n����̎��ԁifloat�l�j
						for (HMGGunParts parts : Partslist)
							GunPartSidentification(parts, GunState.Cock, cockingprogress, remainbullets);
					} else if (!recoiled) {
						//�ˌ����1tick���̂݌Ă΂�܂�
						GL11.glRotatef(jump * (1 - this.getSmoothing()), 1.0f, 0.0f, 0.0f);//�e�����ˏオ��B���̏�Ԃ��ƃ��f����0,0,0�𒆐S�ɉ�]�B
						float recoileprogress = 10 * (nbt.getByte("Bolt") + smoothing) / gunitem.cycle;
						for (HMGGunParts parts : Partslist)
							GunPartSidentification(parts, GunState.Recoil, recoileprogress, remainbullets);
					} else {
						//�ʏ���
						for (HMGGunParts parts : Partslist)
							GunPartSidentification(parts, GunState.Default, 0, remainbullets);
					}
//						renderpartsNormal(this,gunitem,gunstack,model,entity,type,data);
				}
				GL11.glPopMatrix();//�I�����ɂ�����Ă�ōs����o�b�N�A�b�v����߂��܂��B
				smoothing = RenderTickSmoothing.smooth;
				break;
			}
			case FIRST_PERSON_MAP:
				break;
		}

		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
		GL11.glShadeModel(GL11.GL_FLAT);
	}


	public void glMatrixForRenderInInventory() {
		GL11.glRotatef(15F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(-30F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(1900F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-0.8F, -1.2F, -0.1F);
	}

	public void glMatrixForRenderInEquipped(float reco) {
		GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(50F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(modelPosX+0.5f, modelPosY, modelPosZ +reco);// -0.2F//-0.7,0.7,0
	}

	public void glMatrixForRenderInEquipped_reload() {
		GL11.glRotatef(190F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(40F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(modelPosX+0.5f, modelPosY, modelPosZ);
	}

	//ADS
	public void glMatrixForRenderInEquippedADS(float reco) {
		GL11.glRotatef(onads_modelRotationX, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(onads_modelRotationY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(onads_modelRotationZ, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(onads_modelPosX, onads_modelPosY, -1.4f + onads_modelPosZ);// 0.694,1.03,-1.0//-1.4F
	}

	public void glMatrixForRenderInEntity(float reco) {
		GL11.glRotatef(190F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-1.3f +  + thirdmodelPosX, 1.55f + thirdmodelPosY, reco + thirdmodelPosZ);//-0.4
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
	public void renderarm(float armrotationxl,float armrotationyl,float armrotationzl,float armoffsetxl,float armoffsetyl,float armoffsetzl,
						  float armrotationxr,float armrotationyr,float armrotationzr,float armoffsetxr,float armoffsetyr,float armoffsetzr){
		GL11.glScalef(1/modelscala,1/modelscala,1/modelscala);
		modelBipedMain.bipedLeftArm.rotateAngleX = armrotationxl;
		modelBipedMain.bipedLeftArm.rotateAngleY = armrotationyl;
		modelBipedMain.bipedLeftArm.rotateAngleZ = armrotationzl;
		modelBipedMain.bipedLeftArm.offsetX = armoffsetxl*armoffsetscale;
		modelBipedMain.bipedLeftArm.offsetY = armoffsetyl*armoffsetscale;
		modelBipedMain.bipedLeftArm.offsetZ = armoffsetzl*armoffsetscale;


		modelBipedMain.bipedRightArm.rotateAngleX = armrotationxr;
		modelBipedMain.bipedRightArm.rotateAngleY = armrotationyr;
		modelBipedMain.bipedRightArm.rotateAngleZ = armrotationzr;
		modelBipedMain.bipedRightArm.offsetX = armoffsetxr*armoffsetscale;
		modelBipedMain.bipedRightArm.offsetY = armoffsetyr*armoffsetscale;
		modelBipedMain.bipedRightArm.offsetZ = armoffsetzr*armoffsetscale;
		GL11.glPushMatrix();
		modelBipedMain.bipedRightArm.render(0.0625f);
		modelBipedMain.bipedLeftArm.render(0.0625f);
		GL11.glPopMatrix();
	}
	public void renderarmL(){
		if(isfirstperson) {
			GL11.glPushMatrix();
			ResourceLocation resourcelocation = this.getEntityTexture(proxy.getMCInstance().thePlayer);
			if (resourcelocation == null) {
				resourcelocation = AbstractClientPlayer.getLocationSkin("default");
			}
			Minecraft.getMinecraft().renderEngine.bindTexture(resourcelocation);
			GL11.glScalef(1 / modelscala, 1 / modelscala, 1 / modelscala);
			GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0F, -0.5F, 0F);
			modelBipedMain.bipedLeftArm.rotateAngleX = armrotationzl;
			modelBipedMain.bipedLeftArm.rotateAngleY = armrotationyl;
			modelBipedMain.bipedLeftArm.rotateAngleZ = 0;
			modelBipedMain.bipedLeftArm.offsetX = armoffsetxl * armoffsetscale;
			modelBipedMain.bipedLeftArm.offsetY = armoffsetyl * armoffsetscale;
			modelBipedMain.bipedLeftArm.offsetZ = armoffsetzl * armoffsetscale;
			modelBipedMain.bipedLeftArm.render(0.0625f);
			Minecraft.getMinecraft().renderEngine.bindTexture(guntexture);
			GL11.glScalef(modelscala, modelscala, modelscala);
			GL11.glPopMatrix();
		}
	}
	public void renderarmR(){
		if(isfirstperson) {
			GL11.glPushMatrix();
			ResourceLocation resourcelocation = this.getEntityTexture(proxy.getMCInstance().thePlayer);
			if (resourcelocation == null) {
				resourcelocation = AbstractClientPlayer.getLocationSkin("default");
			}
			Minecraft.getMinecraft().renderEngine.bindTexture(resourcelocation);
			GL11.glScalef(1 / modelscala, 1 / modelscala, 1 / modelscala);
			GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0F, -0.5F, 0F);
			modelBipedMain.bipedRightArm.rotateAngleX = armrotationzr;
			modelBipedMain.bipedRightArm.rotateAngleY = armrotationyr;
			modelBipedMain.bipedRightArm.rotateAngleZ = 0;
			modelBipedMain.bipedRightArm.offsetX = armoffsetxr * armoffsetscale;
			modelBipedMain.bipedRightArm.offsetY = armoffsetyr * armoffsetscale;
			modelBipedMain.bipedRightArm.offsetZ = armoffsetzr * armoffsetscale;
			modelBipedMain.bipedRightArm.render(0.0625f);
			Minecraft.getMinecraft().renderEngine.bindTexture(guntexture);
			GL11.glScalef(modelscala, modelscala, modelscala);
			GL11.glPopMatrix();
		}
	}

	public boolean isentitysprinting(Entity entity){
		return entity.isSprinting() && !nbt.getBoolean("set_up");
	}
	private static FloatBuffer setColorBuffer(float p_74521_0_, float p_74521_1_, float p_74521_2_, float p_74521_3_) {
		colorBuffer.clear();
		colorBuffer.put(p_74521_0_).put(p_74521_1_).put(p_74521_2_).put(p_74521_3_);
		colorBuffer.flip();
		/** Float buffer used to set OpenGL material colors */
		return colorBuffer;
	}

	public void GunPartSidentification(HMGGunParts part, GunState state, float flame, int remainbullets){//��Ԃŏ�������
		switch (state){
			case Default:
				if(part.rendering_Def){
					GunPartSidentification_Attach(part,state,flame,remainbullets,part.getRenderinfDefault_offset());
				}
				break;
			case ADS:
				if(part.rendering_Ads){
					GunPartSidentification_Attach(part,state,flame,remainbullets,part.getRenderinfOfADS());
				}
				break;
			case Recoil:
				if(part.rendering_Recoil){
					if(part.hasMotionRecoil){
						HMGGunParts_Motion_PosAndRotation OffsetAndRotation = part.getRecoilmotion(flame + smooth);

						GunPartSidentification_Attach(part,state,flame,remainbullets,OffsetAndRotation);
					}else {
						HMGGunParts_Motion_PosAndRotation OffsetAndRotation = part.getRenderinfOfRecoil();

						GunPartSidentification_Attach(part,state,flame,remainbullets,OffsetAndRotation);
					}
				}
				break;
			case Cock:
				if(part.rendering_Cock){
					if(part.hasMotionCock){
						HMGGunParts_Motion_PosAndRotation OffsetAndRotation = part.getcockmotion(flame + smooth);

						GunPartSidentification_Attach(part,state,flame,remainbullets,OffsetAndRotation);
					}else {
						HMGGunParts_Motion_PosAndRotation OffsetAndRotation = part.getRenderinfOfCock();

						GunPartSidentification_Attach(part,state,flame,remainbullets,OffsetAndRotation);
					}
				}
				break;
			case Reload:
				if(part.rendering_Reload){
					HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation = part.getRenderinfOfDef();
					if(part.hasMotionReload){
						HMGGunParts_Motion_PosAndRotation OffsetAndRotation = part.getReloadmotion(flame + smooth);

						GunPartSidentification_Attach(part,state,flame,remainbullets,OffsetAndRotation);
					}else {
						HMGGunParts_Motion_PosAndRotation OffsetAndRotation = part.getRenderinfOfReload();

						GunPartSidentification_Attach(part,state,flame,remainbullets,OffsetAndRotation);
					}
				}
				break;
		}
	}

	public void GunPartSidentification_Attach(HMGGunParts part, GunState state, float flame, int remainbullets, HMGGunParts_Motion_PosAndRotation OffsetAndRotation){
		if(OffsetAndRotation != null)
			if(part.isattachpart) {
				if (items[1] != null) {//�T�C�g
					if (part.issightbase && items[1].getItem() instanceof HMGItemSightBase) {
						IItemRenderer attachrender = MinecraftForgeClient.getItemRenderer(items[1], ItemRenderType.EQUIPPED);
						if (attachrender instanceof HMGRenderItemCustom) {
							GunPart_Render_attach(part, state, flame, remainbullets, OffsetAndRotation,sightattachoffset,sightattachrotation,((HMGRenderItemCustom) attachrender));
							return;
						}
					} else if (part.isscope && items[1].getItem() instanceof HMGItemAttachment_scope) {
						GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
						return;
					} else if (part.isdot && items[1].getItem() instanceof HMGItemAttachment_reddot) {
						GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
						return;
					}
				} else if (part.issight) {
					GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
					return;
				}
				if (items[2] != null) {//�I�[�o�[�o�����n
					if (part.islight && items[2].getItem() instanceof HMGItemAttachment_light) {
						GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
						return;
					} else if (part.islasersight && items[2].getItem() instanceof HMGItemAttachment_laser) {
						GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
						return;
					} else if (part.isoverbarrelbase && items[2].getItem() instanceof HMGItemAttachmentBase) {
						IItemRenderer attachrender = MinecraftForgeClient.getItemRenderer(items[2], ItemRenderType.EQUIPPED);
						if (attachrender instanceof HMGRenderItemCustom) {
							GunPart_Render_attach(part, state, flame, remainbullets, OffsetAndRotation,overbarrelattachoffset,overbarrelattachrotation,((HMGRenderItemCustom) attachrender));
							return;
						}
					}
				}
				if (items[3] != null) {//�o�����n
					if (part.ismuzzlepart && items[3].getItem() instanceof HMGItemAttachment_Suppressor) {
						GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
						return;
					} else if (part.ismuzzulebase && items[3].getItem() instanceof HMGItemAttachmentBase) {
						IItemRenderer attachrender = MinecraftForgeClient.getItemRenderer(items[3], ItemRenderType.EQUIPPED);
						if (attachrender instanceof HMGRenderItemCustom) {
							GunPart_Render_attach(part, state, flame, remainbullets, OffsetAndRotation,muzzleattachoffset,muzzleattachrotation,((HMGRenderItemCustom) attachrender));
							return;
						}
					} else if (part.issword && items[3].getItem() instanceof HMGItemSwordBase) {
						GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
					} else if (part.isswordbase && items[3].getItem() instanceof HMGItemSwordBase) {
						GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
						IItemRenderer attachrender = MinecraftForgeClient.getItemRenderer(items[3], ItemRenderType.EQUIPPED);
						if (attachrender != null) {
							GL11.glPushMatrix();
							GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
							glTranslatef(gunitem.underoffsetpx, gunitem.underoffsetpy, gunitem.underoffsetpz);
							GL11.glRotatef(gunitem.underrotationy, 0, 1, 0);
							GL11.glRotatef(gunitem.underrotationx, 1, 0, 0);
							GL11.glRotatef(gunitem.underrotationz, 0, 0, 1);
							GL11.glScalef(1/modelscala,1/modelscala,1/modelscala);
							GL11.glScalef(1/(0.4f),1/(0.4f),1/(0.4f));
							attachrender.renderItem(ItemRenderType.ENTITY, items[3], datas);
							Minecraft.getMinecraft().renderEngine.bindTexture(guntexture);
							GL11.glPopMatrix();
						}
					}
				}
				if (items[4] != null) {//�A���_�[�o�����n
					if (part.isgripBase && items[4].getItem() instanceof HMGItemAttachmentBase) {
						IItemRenderer attachrender = MinecraftForgeClient.getItemRenderer(items[4], ItemRenderType.EQUIPPED);
						if (attachrender instanceof HMGRenderItemCustom) {
							GunPart_Render_attach(part, state, flame, remainbullets, OffsetAndRotation,gripattachoffset,gripattachrotation,((HMGRenderItemCustom) attachrender));
						}
					} else if (part.isgrip && items[4].getItem() instanceof HMGItemAttachment_grip) {
						GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
					} else if (part.issword && items[4].getItem() instanceof HMGItemSwordBase) {
						GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
					} else if (part.isswordbase && items[4].getItem() instanceof HMGItemSwordBase) {
						GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
						IItemRenderer attachrender = MinecraftForgeClient.getItemRenderer(items[4], ItemRenderType.EQUIPPED);
						if (attachrender != null) {
							GL11.glPushMatrix();
							GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
							glTranslatef(gunitem.underoffsetpx, gunitem.underoffsetpy, gunitem.underoffsetpz);
							GL11.glRotatef(gunitem.underrotationy, 0, 1, 0);
							GL11.glRotatef(gunitem.underrotationx, 1, 0, 0);
							GL11.glRotatef(gunitem.underrotationz, 0, 0, 1);
							GL11.glScalef(1/modelscala,1/modelscala,1/modelscala);
							GL11.glScalef(1/(0.4f),1/(0.4f),1/(0.4f));
							attachrender.renderItem(ItemRenderType.ENTITY, items[4], datas);
							Minecraft.getMinecraft().renderEngine.bindTexture(guntexture);
							GL11.glPopMatrix();
						}
					}else if (items[4].getItem() instanceof HMGItem_Unified_Guns) {
						if (part.isunderGunbase) {
							IItemRenderer attachrender = MinecraftForgeClient.getItemRenderer(items[4], ItemRenderType.EQUIPPED);
							if (attachrender != null) {
								GL11.glPushMatrix();
								GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
								glTranslatef(gunitem.underoffsetpx, gunitem.underoffsetpy, gunitem.underoffsetpz);
								GL11.glRotatef(gunitem.underrotationy, 0, 1, 0);
								GL11.glRotatef(gunitem.underrotationx, 1, 0, 0);
								GL11.glRotatef(gunitem.underrotationz, 0, 0, 1);
								HMGItem_Unified_Guns undergun = (HMGItem_Unified_Guns) items[4].getItem();
								glTranslatef(undergun.onunderoffsetpx, undergun.onunderoffsetpy, undergun.onunderoffsetpz);
								GL11.glRotatef(undergun.onunderrotationy, 0, 1, 0);
								GL11.glRotatef(undergun.onunderrotationx, 1, 0, 0);
								GL11.glRotatef(undergun.onunderrotationz, 0, 0, 1);
								if(attachrender instanceof HMGRenderItemGun_U_NEW){
									((HMGRenderItemGun_U_NEW) attachrender).isUnder = true;
								}
								GL11.glScalef(1/(0.4f * modelscala * gunitem.inworldScale),1/(0.4f * modelscala * gunitem.inworldScale),1/(0.4f * modelscala * gunitem.inworldScale));

								attachrender.renderItem(ItemRenderType.ENTITY, items[4], datas);
								if(attachrender instanceof HMGRenderItemGun_U_NEW){
									((HMGRenderItemGun_U_NEW) attachrender).isUnder = false;
								}
								Minecraft.getMinecraft().renderEngine.bindTexture(guntexture);
								GL11.glPopMatrix();
							}
						} else if (part.isunderGL && ((HMGItem_Unified_Guns) items[4].getItem()).guntype == 2) {
							GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
						} else if (part.isunderSG && ((HMGItem_Unified_Guns) items[4].getItem()).guntype == 1) {
							GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
						}
					}
				}else{
					if (part.isgripcover) {
						GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
					}
				}
			}else {
				GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
			}
	}
	public void GunPart_Render_attach(HMGGunParts part, GunState state, float flame, int remainbullets, HMGGunParts_Motion_PosAndRotation OffsetAndRotation,float[] attachoffset,float[] attachrotation,HMGRenderItemCustom attachrender){
		GL11.glPushMatrix();
		glTranslatef(attachoffset[0], attachoffset[1], attachoffset[2]);
		GL11.glRotatef(attachrotation[0], 0, 1, 0);
		GL11.glRotatef(attachrotation[1], 1, 0, 0);
		GL11.glRotatef(attachrotation[2], 0, 0, 1);
		((HMGRenderItemCustom) attachrender).renderaspart();
		GL11.glPopMatrix();
		proxy.getMCInstance().getTextureManager().bindTexture(guntexture);
		GunPart_Render(part, state, flame, remainbullets, OffsetAndRotation);
	}
	public void GunPart_Render(HMGGunParts part, GunState state, float flame, int remainbullets, HMGGunParts_Motion_PosAndRotation OffsetAndRotation){
		HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation = part.getRenderinfOfDef();
		GL11.glPushMatrix();
		if(isPlacedGun && !part.base && part.carryingHandle){
			GL11.glPopMatrix();
			return;
		}
		if(!isPlacedGun && !part.carryingHandle && part.base){
			GL11.glPopMatrix();
			return;
		}
		if(OffsetAndRotation != null) {
			GL11.glTranslatef(OffsetAndRotation.posX, OffsetAndRotation.posY, OffsetAndRotation.posZ);
			GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
			GL11.glRotatef(OffsetAndRotation.rotationY, 0, 1, 0);
			GL11.glRotatef(OffsetAndRotation.rotationX, 1, 0, 0);
			GL11.glRotatef(OffsetAndRotation.rotationZ, 0, 0, 1);
			GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
		}else {
			GL11.glTranslatef(rotationCenterAndRotation.posX,rotationCenterAndRotation.posY,rotationCenterAndRotation.posZ);
			GL11.glRotatef(rotationCenterAndRotation.rotationY,0,1,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationX,1,0,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationZ,0,0,1);
			GL11.glTranslatef(-rotationCenterAndRotation.posX,-rotationCenterAndRotation.posY,-rotationCenterAndRotation.posZ);
		}

		if(isPlacedGun && part.hasbasePitchInfo){
			HMGGunParts_Motion_PosAndRotation baserotationCenterAndRotation = part.getPitchInfo(turretPitch);
			if (baserotationCenterAndRotation != null) {
				GL11.glTranslatef(baserotationCenterAndRotation.posX, baserotationCenterAndRotation.posY, baserotationCenterAndRotation.posZ);
				GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
				GL11.glRotatef(baserotationCenterAndRotation.rotationY, 0, 1, 0);
				GL11.glRotatef(baserotationCenterAndRotation.rotationX, 1, 0, 0);
				GL11.glRotatef(baserotationCenterAndRotation.rotationZ, 0, 0, 1);
				GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
			}
		}
		if(isPlacedGun && part.hasbaseYawInfo) {
			HMGGunParts_Motion_PosAndRotation baserotationCenterAndRotation = part.getYawInfo(turretYaw);
			if (baserotationCenterAndRotation != null) {
				GL11.glTranslatef(baserotationCenterAndRotation.posX, baserotationCenterAndRotation.posY, baserotationCenterAndRotation.posZ);
				GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
				GL11.glRotatef(baserotationCenterAndRotation.rotationY, 0, 1, 0);
				GL11.glRotatef(baserotationCenterAndRotation.rotationX, 1, 0, 0);
				GL11.glRotatef(baserotationCenterAndRotation.rotationZ, 0, 0, 1);
				GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
			}
		}

		if (part.isbullet){
			if(state == GunState.Recoil)
				renderParts_Bullet(part,flame/10,remainbullets,rotationCenterAndRotation);
			else
				renderParts_Bullet(part,flame,remainbullets,rotationCenterAndRotation);
		}else {
			if(part.isLarm){
				renderarmL();
			}else if(part.isRarm){
				renderarmR();
			}else if((part.underOnly && isUnder) || !(part.underOnly_not && isUnder)) {
				model.renderPart(part.partsname);
				float lastBrightnessX = OpenGlHelper.lastBrightnessX;
				float lastBrightnessY = OpenGlHelper.lastBrightnessY;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
				model.renderPart(part.partsname + "light");
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
			}
		}
		for(HMGGunParts parts:part.childs)
			GunPartSidentification(parts,state,flame,remainbullets);
		GL11.glPopMatrix();
	}


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
						model.renderPart(part.partsname + "light");
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
		armoffsetscale = value;
	}
}
