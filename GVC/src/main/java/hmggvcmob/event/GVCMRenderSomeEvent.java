package hmggvcmob.event;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.gameevent.TickEvent;
import hmggvcmob.entity.*;
import hmggvcmob.entity.friend.EntitySoBases;
import hmggvcmob.entity.friend.GVCEntityPMCHeli;
import hmggvcmob.entity.friend.GVCEntityPMCT90Tank;
import hmggvcmob.entity.friend.GVCEntityPlane;
import hmggvcmob.util.Calculater;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;
import java.util.ArrayList;

import static hmggvcmob.GVCMobPlus.proxy;
import static java.lang.Math.*;

public class GVCMRenderSomeEvent {

	public boolean zoomtype;
	public static float mouseSensO = -1;
	public static boolean zooming;
	static boolean needrest = true;
	private double zLevel = 0;

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderridding(RenderPlayerEvent event)
	{
		event.setCanceled(true);
	}
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderfov(FOVUpdateEvent event)
	{
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer entityplayer = minecraft.thePlayer;
		if (entityplayer.ridingEntity != null) {
			if(entityplayer.ridingEntity instanceof EntitySoBases){
				boolean rc = proxy.zoomclick();
				if(rc)zooming = !zooming;

				if(zooming) {
					event.newfov = event.fov / 2;
				}
			}
		}else {
			zooming = false;
		}
	}
//	@SideOnly(Side.CLIENT)
//    @SubscribeEvent
//	  public void renderoffset(EntityViewRenderEvent.RenderFogEvent event)
//	  {
//		Minecraft minecraft = FMLClientHandler.instance().getClient();
//		World world = FMLClientHandler.instance().getWorldClient();
//		EntityLivingBase entityLiving = event.entity;
//		EntityPlayer entityplayer = minecraft.thePlayer;
//		ItemStack itemstack = ((EntityPlayer) (entityplayer)).getCurrentEquippedItem();
//		if (entityplayer.ridingEntity instanceof EntityPMCBase && entityplayer.ridingEntity != null) {//1
//			EntityPMCBase balaam = (EntityPMCBase) entityplayer.ridingEntity;
//			if(minecraft.gameSettings.thirdPersonView == 1){
//				float rotep = entityplayer.rotationPitch * (2 * (float) Math.PI / 360);
//				float x = (float) (2 * Math.cos(rotep));
//				float y = (float) (2 * Math.sin(rotep)) * balaam.overlayhight_3;
//
//				float ix2 = 0;
//				float iz2 = 0;
//				float f12 = entityplayer.rotationYawHead * (2 * (float) Math.PI / 360);
//				ix2 += (float) (MathHelper.sin(f12) * balaam.overlaywidth_3);
//				iz2 -= (float) (MathHelper.cos(f12) * balaam.overlaywidth_3);
//
//				//float ix3 = 0;
//				//float iz3 = 0;
//				//ix3 += (float) (MathHelper.sin(f12) * balaam.overlaywidth_3*x);
//				//iz3 -= (float) (MathHelper.cos(f12) * balaam.overlaywidth_3*x);
//				{
//				GL11.glTranslatef(-ix2, -balaam.overlayhight_3-y, -iz2);
//				}
//			}else if(minecraft.gameSettings.thirdPersonView == 0){
//				GL11.glTranslatef(0, -balaam.overlayhight, 0);
//			}
//		}//1
//	  }

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void tickEvent(TickEvent.WorldTickEvent event) {
		try {
			if (event.phase == TickEvent.Phase.START && !GVCMXEntityEvent.soundedentity.isEmpty()) {
				ArrayList tempremove = new ArrayList();
				for (int i = 0; i < GVCMXEntityEvent.soundedentity.size(); i++) {
					if (GVCMXEntityEvent.soundedentity.get(i) != null) {
						GVCMXEntityEvent.soundedentity.get(i).getEntityData().setFloat("GunshotLevel", GVCMXEntityEvent.soundedentity.get(i).getEntityData().getFloat("GunshotLevel") * 0.95f);
						if (GVCMXEntityEvent.soundedentity.get(i).getEntityData().getFloat("GunshotLevel") < 0.1) {
							tempremove.add(GVCMXEntityEvent.soundedentity.get(i));
						}
					}
				}
				GVCMXEntityEvent.soundedentity.removeAll(tempremove);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	@SubscribeEvent
	public void soundevent(PlaySoundAtEntityEvent event) {
		if(event.entity.getEntityData().getFloat("GunshotLevel")<event.volume) {
			event.entity.getEntityData().setFloat("GunshotLevel", event.volume);
		}
		GVCMXEntityEvent.soundedentity.add(event.entity);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderPlayer(RenderPlayerEvent.Pre event){
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer entityplayer = minecraft.thePlayer;
		if(event.entityLiving != entityplayer)return;
		if(entityplayer.ridingEntity instanceof GVCEntityChild && FMLClientHandler.instance().getClient().gameSettings.thirdPersonView == 0){
			event.setCanceled(true);
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderTick(TickEvent.RenderTickEvent event)
	{
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer entityplayer = minecraft.thePlayer;
		switch(event.phase)
		{
			case START :
				if(event.renderTickTime<1){
					if (entityplayer != null) {
						if (entityplayer.ridingEntity instanceof GVCEntityChild) {
							ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, ((GVCEntityChild) entityplayer.ridingEntity).thirddist, "thirdPersonDistance", "E", "field_78490_B");
							if( ((GVCEntityChild) entityplayer.ridingEntity).master instanceof GVCEntityPMCHeli) {
								GVCEntityPMCHeli heli = (GVCEntityPMCHeli) ((GVCEntityChild) entityplayer.ridingEntity).master;
								Vector3d bodyvector = Calculater.transformVecByQuat(new Vector3d(0, 0, 1), heli.bodyRot);
								Vector3d tailwingvector = Calculater.transformVecByQuat(new Vector3d(0, 1, 0), heli.bodyRot);
								Vector3d mainwingvector = Calculater.transformVecByQuat(new Vector3d(1, 0, 0), heli.bodyRot);
								if (((GVCEntityChild) entityplayer.ridingEntity).idinmasterEntityt == 5) {
									Quat4d tempquat = new Quat4d(
											heli.prevbodyRot.x * (1-event.renderTickTime) + heli.bodyRot.x * event.renderTickTime ,
											heli.prevbodyRot.y * (1-event.renderTickTime) + heli.bodyRot.y * event.renderTickTime ,
											heli.prevbodyRot.z * (1-event.renderTickTime) + heli.bodyRot.z * event.renderTickTime ,
											heli.prevbodyRot.w * (1-event.renderTickTime) + heli.bodyRot.w * event.renderTickTime );

									double[] xyz = Calculater.eulerfrommatrix(Calculater.matrixfromQuat(tempquat));
									xyz[0] = toDegrees(xyz[0]);
									xyz[1] = toDegrees(xyz[1]);
									xyz[2] = toDegrees(xyz[2]);
//							System.out.println("y" + xyz[0] + " , x" + xyz[1] + " , z" + xyz[2] + " , renderTickTime" + event.renderTickTime);
									bodyvector = Calculater.transformVecByQuat(new Vector3d(0, 0, 1), tempquat);
									tailwingvector = Calculater.transformVecByQuat(new Vector3d(0, 1, 0), tempquat);
									mainwingvector = Calculater.transformVecByQuat(new Vector3d(1, 0, 0), tempquat);
									Calculater.transformVecforMinecraft(tailwingvector);
									Calculater.transformVecforMinecraft(bodyvector);
									Calculater.transformVecforMinecraft(mainwingvector);
									mainwingvector.scale(0.1075);
									tailwingvector.scale(0.07);
									bodyvector.scale(-4);
									if (heli.camera != null) {
										heli.camera.setLocationAndAngles(
												heli.prevPosX + (heli.posX - heli.prevPosX) * event.renderTickTime + bodyvector.x + tailwingvector.x + mainwingvector.x,
												heli.prevPosY + (heli.posY - heli.prevPosY) * event.renderTickTime + bodyvector.y + tailwingvector.y + mainwingvector.y + 2.5 - entityplayer.yOffset,
												heli.prevPosZ + (heli.posZ - heli.prevPosZ) * event.renderTickTime + bodyvector.z + tailwingvector.z + mainwingvector.z,
												(float) xyz[1], (float) xyz[0]);
										minecraft.renderViewEntity = heli.camera;
										heli.camera.rotationYaw = (float) xyz[1];
										heli.camera.prevRotationYaw = (float) xyz[1];
										heli.camera.rotationYawHead = (float) xyz[1];
										heli.camera.prevRotationYawHead = (float) xyz[1];
										heli.camera.rotationPitch = (float) xyz[0];
										heli.camera.prevRotationPitch = (float) xyz[0];
									}
									heli.bodyrotationYaw = (float) xyz[1];
									heli.prevbodyrotationYaw = (float) xyz[1];
									heli.rotationYaw = (float) xyz[1];
									heli.prevRotationYaw = (float) xyz[1];
									heli.bodyrotationPitch = (float) xyz[0];
									heli.prevbodyrotationPitch = (float) xyz[0];
									heli.rotationPitch = (float) xyz[0];
									heli.prevRotationPitch = (float) xyz[0];
									heli.bodyrotationRoll = (float) xyz[2];
									heli.prevbodyrotationRoll = (float) xyz[2];
									ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, (float) xyz[2], "camRoll", "R", "field_78495_O");
									ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, (float) xyz[2], "prevCamRoll", "R", "field_78495_O");
									ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, 24, "thirdPersonDistance", "E", "field_78490_B");
									entityplayer.rotationYaw = heli.bodyrotationYaw;
									entityplayer.prevRotationYaw = heli.bodyrotationYaw;
									entityplayer.rotationPitch = heli.bodyrotationPitch;
									entityplayer.prevRotationPitch = heli.bodyrotationPitch;
									bodyvector = Calculater.transformVecByQuat(new Vector3d(0, 0, 1), tempquat);
									tailwingvector = Calculater.transformVecByQuat(new Vector3d(0, 1, 0), tempquat);
									mainwingvector = Calculater.transformVecByQuat(new Vector3d(1, 0, 0), tempquat);
								}

								tailwingvector.normalize();
								bodyvector.normalize();
								mainwingvector.normalize();
								Calculater.transformVecforMinecraft(tailwingvector);
								Calculater.transformVecforMinecraft(bodyvector);
								Calculater.transformVecforMinecraft(mainwingvector);
								needrest = true;


								for (int i = 0; i < heli.childEntities.length; i++) {
									GVCEntityChild aseat = heli.childEntities[i];
									if (aseat != null) {
										aseat.setLocationAndAngles(
												heli.prevPosX + (heli.posX - heli.prevPosX) * event.renderTickTime + mainwingvector.x * heli.childInfo[i].pos[0] + tailwingvector.x * (heli.childInfo[i].pos[1] - 2.5) - bodyvector.x * heli.childInfo[i].pos[2]
												, heli.prevPosY + (heli.posY - heli.prevPosY) * event.renderTickTime + mainwingvector.y * heli.childInfo[i].pos[0] + 2 + tailwingvector.y * (heli.childInfo[i].pos[1] - 2.5) - bodyvector.y * heli.childInfo[i].pos[2]
												, heli.prevPosZ + (heli.posZ - heli.prevPosZ) * event.renderTickTime + mainwingvector.z * heli.childInfo[i].pos[0] + tailwingvector.z * (heli.childInfo[i].pos[1] - 2.5) - bodyvector.z * heli.childInfo[i].pos[2]
												, heli.bodyrotationYaw, heli.bodyrotationPitch);
										aseat.prevPosX = heli.prevPosX + (heli.posX - heli.prevPosX) * event.renderTickTime + mainwingvector.x * heli.childInfo[i].pos[0] + tailwingvector.x * (heli.childInfo[i].pos[1] - 2.5) - bodyvector.x * heli.childInfo[i].pos[2];
										aseat.prevPosY = heli.prevPosY + (heli.posY - heli.prevPosY) * event.renderTickTime + mainwingvector.y * heli.childInfo[i].pos[0] + 2 + tailwingvector.y * (heli.childInfo[i].pos[1] - 2.5) - bodyvector.y * heli.childInfo[i].pos[2];
										aseat.prevPosZ = heli.prevPosZ + (heli.posZ - heli.prevPosZ) * event.renderTickTime + mainwingvector.z * heli.childInfo[i].pos[0] + tailwingvector.z * (heli.childInfo[i].pos[1] - 2.5) - bodyvector.z * heli.childInfo[i].pos[2];
										if (aseat.riddenByEntity != null) {
											if (aseat.riddenByEntity == entityplayer) {
												aseat.riddenByEntity.posX = heli.prevPosX + (heli.posX - heli.prevPosX) * event.renderTickTime + mainwingvector.x * heli.childInfo[i].pos[0] + tailwingvector.x * (heli.childInfo[i].pos[1] - 2.5) - bodyvector.x * heli.childInfo[i].pos[2];
												aseat.riddenByEntity.posY = heli.prevPosY + (heli.posY - heli.prevPosY) * event.renderTickTime + mainwingvector.y * heli.childInfo[i].pos[0] + 2 + tailwingvector.y * (heli.childInfo[i].pos[1] - 2.5) - bodyvector.y * heli.childInfo[i].pos[2] + aseat.riddenByEntity.yOffset;
												aseat.riddenByEntity.posZ = heli.prevPosZ + (heli.posZ - heli.prevPosZ) * event.renderTickTime + mainwingvector.z * heli.childInfo[i].pos[0] + tailwingvector.z * (heli.childInfo[i].pos[1] - 2.5) - bodyvector.z * heli.childInfo[i].pos[2];
												aseat.riddenByEntity.prevPosX = heli.prevPosX + (heli.posX - heli.prevPosX) * event.renderTickTime + mainwingvector.x * heli.childInfo[i].pos[0] + tailwingvector.x * (heli.childInfo[i].pos[1] - 2.5) - bodyvector.x * heli.childInfo[i].pos[2];
												aseat.riddenByEntity.prevPosY = heli.prevPosY + (heli.posY - heli.prevPosY) * event.renderTickTime + mainwingvector.y * heli.childInfo[i].pos[0] + 2 + tailwingvector.y * (heli.childInfo[i].pos[1] - 2.5) - bodyvector.y * heli.childInfo[i].pos[2] + aseat.riddenByEntity.yOffset;
												aseat.riddenByEntity.prevPosZ = heli.prevPosZ + (heli.posZ - heli.prevPosZ) * event.renderTickTime + mainwingvector.z * heli.childInfo[i].pos[0] + tailwingvector.z * (heli.childInfo[i].pos[1] - 2.5) - bodyvector.z * heli.childInfo[i].pos[2];
											} else {
												aseat.riddenByEntity.posX = heli.prevPosX + (heli.posX - heli.prevPosX) * event.renderTickTime + mainwingvector.x * heli.childInfo[i].pos[0] + tailwingvector.x * (heli.childInfo[i].pos[1] - 2.5) - bodyvector.x * heli.childInfo[i].pos[2];
												aseat.riddenByEntity.posY = heli.prevPosY + (heli.posY - heli.prevPosY) * event.renderTickTime + mainwingvector.y * heli.childInfo[i].pos[0] + 2 + tailwingvector.y * (heli.childInfo[i].pos[1] - 2.5) - bodyvector.y * heli.childInfo[i].pos[2] + aseat.riddenByEntity.yOffset;
												aseat.riddenByEntity.posZ = heli.prevPosZ + (heli.posZ - heli.prevPosZ) * event.renderTickTime + mainwingvector.z * heli.childInfo[i].pos[0] + tailwingvector.z * (heli.childInfo[i].pos[1] - 2.5) - bodyvector.z * heli.childInfo[i].pos[2];
												aseat.riddenByEntity.prevPosX = heli.prevPosX + (heli.posX - heli.prevPosX) * event.renderTickTime + mainwingvector.x * heli.childInfo[i].pos[0] + tailwingvector.x * (heli.childInfo[i].pos[1] - 2.5) - bodyvector.x * heli.childInfo[i].pos[2];
												aseat.riddenByEntity.prevPosY = heli.prevPosY + (heli.posY - heli.prevPosY) * event.renderTickTime + mainwingvector.y * heli.childInfo[i].pos[0] + 2 + tailwingvector.y * (heli.childInfo[i].pos[1] - 2.5) - bodyvector.y * heli.childInfo[i].pos[2] + aseat.riddenByEntity.yOffset;
												aseat.riddenByEntity.prevPosZ = heli.prevPosZ + (heli.posZ - heli.prevPosZ) * event.renderTickTime + mainwingvector.z * heli.childInfo[i].pos[0] + tailwingvector.z * (heli.childInfo[i].pos[1] - 2.5) - bodyvector.z * heli.childInfo[i].pos[2];
											}
										}
										aseat.master = heli;
									}
								}
							}else if(((GVCEntityChild) entityplayer.ridingEntity).master instanceof GVCEntityPlane){
								GVCEntityPlane plane = (GVCEntityPlane) ((GVCEntityChild) entityplayer.ridingEntity).master;
								Vector3d bodyvector = Calculater.transformVecByQuat(new Vector3d(0, 0, 1), plane.bodyRot);
								Vector3d tailwingvector = Calculater.transformVecByQuat(new Vector3d(0, 1, 0), plane.bodyRot);
								Vector3d mainwingvector = Calculater.transformVecByQuat(new Vector3d(1, 0, 0), plane.bodyRot);
								if (((GVCEntityChild) entityplayer.ridingEntity).idinmasterEntityt == 0) {
									Quat4d tempquat = new Quat4d(
											plane.prevbodyRot.x * (1-event.renderTickTime) + plane.bodyRot.x * event.renderTickTime ,
											plane.prevbodyRot.y * (1-event.renderTickTime) + plane.bodyRot.y * event.renderTickTime ,
											plane.prevbodyRot.z * (1-event.renderTickTime) + plane.bodyRot.z * event.renderTickTime ,
											plane.prevbodyRot.w * (1-event.renderTickTime) + plane.bodyRot.w * event.renderTickTime );
//									if (abs(plane.pitchladder) > 0.001) {
//										Vector3d axisx = Calculater.transformVecByQuat(new Vector3d(1, 0, 0), tempquat);
//										AxisAngle4d axisxangled = new AxisAngle4d(axisx, toRadians(-plane.pitchladder * event.renderTickTime / 4));
//										tempquat = Calculater.quatRotateAxis(tempquat, axisxangled);
//									}
//									if (abs(plane.yawladder) > 0.001) {
//										Vector3d axisy = Calculater.transformVecByQuat(new Vector3d(0, 1, 0), tempquat);
//										AxisAngle4d axisyangled = new AxisAngle4d(axisy, toRadians(plane.yawladder * event.renderTickTime / 4));
//										tempquat = Calculater.quatRotateAxis(tempquat, axisyangled);
//									}
//									if (abs(plane.rollladder) > 0.001) {
//										Vector3d axisz = Calculater.transformVecByQuat(new Vector3d(0, 0, 1), tempquat);
//										AxisAngle4d axiszangled = new AxisAngle4d(axisz, toRadians(plane.rollladder * event.renderTickTime / 4));
//										tempquat = Calculater.quatRotateAxis(tempquat, axiszangled);
//									}

									double[] xyz = Calculater.eulerfrommatrix(Calculater.matrixfromQuat(tempquat));
									xyz[0] = toDegrees(xyz[0]);
									xyz[1] = toDegrees(xyz[1]);
									xyz[2] = toDegrees(xyz[2]);
//							System.out.println("y" + xyz[0] + " , x" + xyz[1] + " , z" + xyz[2] + " , renderTickTime" + event.renderTickTime);
									bodyvector = Calculater.transformVecByQuat(new Vector3d(0, 0, 1), tempquat);
									tailwingvector = Calculater.transformVecByQuat(new Vector3d(0, 1, 0), tempquat);
									mainwingvector = Calculater.transformVecByQuat(new Vector3d(1, 0, 0), tempquat);
									Calculater.transformVecforMinecraft(tailwingvector);
									Calculater.transformVecforMinecraft(bodyvector);
									Calculater.transformVecforMinecraft(mainwingvector);
									mainwingvector.scale(0);
									tailwingvector.scale(-0.21);
									bodyvector.scale(-3.1);
									if (plane.camera != null) {
										plane.camera.setLocationAndAngles(
												plane.prevPosX + (plane.posX - plane.prevPosX) * event.renderTickTime + bodyvector.x + tailwingvector.x + mainwingvector.x,
												plane.prevPosY + (plane.posY - plane.prevPosY) * event.renderTickTime + bodyvector.y + tailwingvector.y + mainwingvector.y + 2.5 - entityplayer.yOffset,
												plane.prevPosZ + (plane.posZ - plane.prevPosZ) * event.renderTickTime + bodyvector.z + tailwingvector.z + mainwingvector.z,
												(float) xyz[1], (float) xyz[0]);
										minecraft.renderViewEntity = plane.camera;
										plane.camera.rotationYaw = (float) xyz[1];
										plane.camera.prevRotationYaw = (float) xyz[1];
										plane.camera.rotationYawHead = (float) xyz[1];
										plane.camera.prevRotationYawHead = (float) xyz[1];
										plane.camera.rotationPitch = (float) xyz[0];
										plane.camera.prevRotationPitch = (float) xyz[0];
									}
									plane.bodyrotationYaw = (float) xyz[1];
									plane.prevbodyrotationYaw = (float) xyz[1];
									plane.rotationYaw = (float) xyz[1];
									plane.prevRotationYaw = (float) xyz[1];
									plane.bodyrotationPitch = (float) xyz[0];
									plane.prevbodyrotationPitch = (float) xyz[0];
									plane.rotationPitch = (float) xyz[0];
									plane.prevRotationPitch = (float) xyz[0];
									plane.bodyrotationRoll = (float) xyz[2];
									plane.prevbodyrotationRoll = (float) xyz[2];
									ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, (float) xyz[2], "camRoll", "R", "field_78495_O");
									ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, (float) xyz[2], "prevCamRoll", "R", "field_78495_O");
									ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, 24, "thirdPersonDistance", "E", "field_78490_B");
									entityplayer.rotationYaw = plane.bodyrotationYaw;
									entityplayer.prevRotationYaw = plane.bodyrotationYaw;
									entityplayer.rotationPitch = plane.bodyrotationPitch;
									entityplayer.prevRotationPitch = plane.bodyrotationPitch;
									bodyvector = Calculater.transformVecByQuat(new Vector3d(0, 0, 1), tempquat);
									tailwingvector = Calculater.transformVecByQuat(new Vector3d(0, 1, 0), tempquat);
									mainwingvector = Calculater.transformVecByQuat(new Vector3d(1, 0, 0), tempquat);
								}

								tailwingvector.normalize();
								bodyvector.normalize();
								mainwingvector.normalize();
								Calculater.transformVecforMinecraft(tailwingvector);
								Calculater.transformVecforMinecraft(bodyvector);
								Calculater.transformVecforMinecraft(mainwingvector);
								needrest = true;
							}
							needrest = true;

						}else{
							if (entityplayer.ridingEntity == null && needrest) {
								ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, 0, "camRoll", "R", "field_78495_O");
								ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, 0, "prevCamRoll", "R", "field_78495_O");
								ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, 4, "thirdPersonDistance", "E", "field_78490_B");
								needrest = false;
								if ((minecraft.renderViewEntity instanceof EntityCameraDummy)) minecraft.renderViewEntity = entityplayer;
							}
						}
					}
				}
				break;
			case END:
				if ((minecraft.renderViewEntity instanceof EntityCameraDummy)) minecraft.renderViewEntity = entityplayer;
				break;
		}
	}
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderover(RenderGameOverlayEvent.Text event) {
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer entityplayer = minecraft.thePlayer;

		ScaledResolution scaledresolution = new ScaledResolution(minecraft, minecraft.displayWidth,
				minecraft.displayHeight);
		int i = scaledresolution.getScaledWidth();
		int j = scaledresolution.getScaledHeight();
		if(entityplayer.ridingEntity instanceof GVCEntityChild) {
			if (((GVCEntityChild) entityplayer.ridingEntity).master instanceof EntityMGAX55) {
				EntityMGAX55 gear = (EntityMGAX55) ((GVCEntityChild) entityplayer.ridingEntity).master;
				FontRenderer fontrenderer = minecraft.fontRenderer;
				String weaponmode;
				int color = 0xFFFFFF;
				switch (gear.weaponMode) {
					case 0:
						if (gear.railGunChargecnt > 0) {
							color = 0xFF0000;
							weaponmode = "Rail Gun : CHRG..." + gear.railGunChargecnt;

						} else if (gear.railGunCoolcnt > 0) {
							color = 0xFFFF00;
							weaponmode = "Rail Gun : LD..." + gear.railGunCoolcnt;
						} else {
							weaponmode = "Rail Gun : RDY :EN " + gear.railGunMagazine;
						}
						break;
					case 1:
						if (gear.rocketMagazine <= 0) color = 0xFF0000;
						weaponmode = "AT Missile : NUM " + gear.rocketMagazine;
						break;
					case 2:
						if (gear.normalGunHeat < EntityMGAX55.normalGunHeat_Max)
							weaponmode = "Machine Gun : RDY :HEAT" + gear.normalGunHeat;
						else {
							color = 0xFF0000;
							weaponmode = "Machine Gun : OH COOLING... HEAT" + gear.normalGunHeat;
						}
						break;
					default:
						weaponmode = "Error";
				}
				fontrenderer.drawStringWithShadow("Weapon Mode : " + weaponmode, i - 300, j - 20 - 10, color);
				color = 0x00FF00;
				if (gear.health < 100) {
					color = 0xFF0000;
				}
				fontrenderer.drawStringWithShadow("Armor : " + gear.health, i - 300, j - 40 - 10, color);
			}else
			if(((GVCEntityChild) entityplayer.ridingEntity).master instanceof GVCEntityPlane){
				FontRenderer fontrenderer = minecraft.fontRenderer;
				GVCEntityPlane plane = (GVCEntityPlane) ((GVCEntityChild) entityplayer.ridingEntity).master;

				displayFlyersHUD(plane.prevbodyRot,plane.bodyRot,plane,plane.prevmotionVec,event);
//				Quat4d tempquat = new Quat4d(
//						plane.prevbodyRot.x * (1-event.partialTicks) + plane.bodyRot.x * event.partialTicks ,
//						plane.prevbodyRot.y * (1-event.partialTicks) + plane.bodyRot.y * event.partialTicks ,
//						plane.prevbodyRot.z * (1-event.partialTicks) + plane.bodyRot.z * event.partialTicks ,
//						plane.prevbodyRot.w * (1-event.partialTicks) + plane.bodyRot.w * event.partialTicks );
////									if (abs(plane.pitchladder) > 0.001) {
////										Vector3d axisx = Calculater.transformVecByQuat(new Vector3d(1, 0, 0), tempquat);
////										AxisAngle4d axisxangled = new AxisAngle4d(axisx, toRadians(-plane.pitchladder * event.renderTickTime / 4));
////										tempquat = Calculater.quatRotateAxis(tempquat, axisxangled);
////									}
////									if (abs(plane.yawladder) > 0.001) {
////										Vector3d axisy = Calculater.transformVecByQuat(new Vector3d(0, 1, 0), tempquat);
////										AxisAngle4d axisyangled = new AxisAngle4d(axisy, toRadians(plane.yawladder * event.renderTickTime / 4));
////										tempquat = Calculater.quatRotateAxis(tempquat, axisyangled);
////									}
////									if (abs(plane.rollladder) > 0.001) {
////										Vector3d axisz = Calculater.transformVecByQuat(new Vector3d(0, 0, 1), tempquat);
////										AxisAngle4d axiszangled = new AxisAngle4d(axisz, toRadians(plane.rollladder * event.renderTickTime / 4));
////										tempquat = Calculater.quatRotateAxis(tempquat, axiszangled);
////									}
//
//				GL11.glPushMatrix();
//				double width = scaledresolution.getScaledWidth_double();
//				double height = scaledresolution.getScaledHeight_double();
//				//HUDは300×650
//				//一度3.6px
//				//横幅を合わせる
//				double scale = width/300;
//				double sizeW = width;
//				double sizeH = sizeW * 650/300;
//				double[] xyz = Calculater.eulerfrommatrix(Calculater.matrixfromQuat(tempquat));
//				xyz[0] = toDegrees(xyz[0]);
//				xyz[1] = toDegrees(xyz[1]);
//				xyz[2] = toDegrees(xyz[2]);
//				GL11.glEnable(GL11.GL_BLEND);
//				GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
//				GL11.glTranslatef((float)width/2,(float) height/2,0);
//				GL11.glRotatef((float) -xyz[2],0,0,1);
//				GL11.glTranslatef(-(float)width/2,-(float) height/2,0);
//				minecraft.renderEngine.bindTexture(new ResourceLocation("gvcmob:textures/items/HUD.png"));
//				drawTexturedModalRect(0,height/2 - 325 * scale - xyz[0] * 3.6*scale,sizeW,sizeH);
//				GL11.glPopMatrix();
//
//				GL11.glPushMatrix();
//				scale = width/300;
//				sizeW = width;
//				sizeH = width;
//				minecraft.renderEngine.bindTexture(new ResourceLocation("gvcmob:textures/items/HUD2.png"));
//
//				drawTexturedModalRect(0,height/2 - 150 * scale,sizeW,sizeH);
//				GL11.glPopMatrix();
//
//
//				GL11.glPushMatrix();
//				Vector3d forDisplayPlaneMotion = new Vector3d(
//						plane.posX - plane.prevPosX,
//						plane.posY - plane.prevPosY,
//						-(plane.posZ - plane.prevPosZ));
//
//				Quat4d quat4d = new Quat4d();
//				quat4d.inverse(tempquat);
//				forDisplayPlaneMotion = Calculater.transformVecByQuat(forDisplayPlaneMotion,quat4d);
//				forDisplayPlaneMotion.scale(-1);
//				double angle = toDegrees(forDisplayPlaneMotion.angle(new Vector3d(0,0,1)));
//
//				forDisplayPlaneMotion.z = 0;
//				forDisplayPlaneMotion.normalize();
//
//				GL11.glRotatef((float) xyz[2],0,0,1);
//				GL11.glTranslatef((float)( forDisplayPlaneMotion.x * angle * 3.6 * scale), (float)( forDisplayPlaneMotion.y * angle * 3.6 * scale),0);
//				GL11.glRotatef((float) -xyz[2],0,0,1);
//
//				minecraft.renderEngine.bindTexture(new ResourceLocation("gvcmob:textures/items/HUD3.png"));
//				drawTexturedModalRect(0,height/2 - 150 * scale,sizeW,sizeH);
//
//				GL11.glPopMatrix();

				int color = 0xFFFFFF;
				color = 0xFFFFFF;
				fontrenderer.drawStringWithShadow("Speed : " + (int)(sqrt(plane.motionX * plane.motionX + plane.motionY * plane.motionY + plane.motionZ * plane.motionZ)*72), i - 300, j - 80 - 10, color);
				color = 0xFFFFFF;
				fontrenderer.drawStringWithShadow("Throttle : " + (int)(plane.throttle*10) + (plane.throttle<2.5?"Gear Down":"") + "   Flap position : " + plane.flaplevel, i - 300, j - 60, color);
				color = 0x00FF00;
				if (plane.health < 100) {
					color = 0xFF0000;
				}
				fontrenderer.drawStringWithShadow("Armor : " + plane.health, i - 300, j - 40 - 10, color);
				color = 0x74AAFF;
				fontrenderer.drawStringWithShadow("Missile : " + plane.rocket + " : " + (plane.missile != null ? "Continue radar irradiation" : plane.illuminated != null?"LOCK":""), i - 300, j - 20 - 10, color);
				GL11.glDisable(GL11.GL_BLEND);
			}else
			if(((GVCEntityChild) entityplayer.ridingEntity).master instanceof GVCEntityPMCHeli){
				FontRenderer fontrenderer = minecraft.fontRenderer;
				GVCEntityPMCHeli heli = (GVCEntityPMCHeli) ((GVCEntityChild) entityplayer.ridingEntity).master;
				displayFlyersHUD(heli.prevbodyRot,heli.bodyRot,heli,heli.prevmotionVec,event);

				int color = 0xFFFFFF;
				color = 0x00FF00;
				if (heli.health < 100) {
					color = 0xFF0000;
				}
				fontrenderer.drawStringWithShadow("Armor : " + heli.health, i - 300, j - 40 - 10, color);
				color = 0x00FF00;
				fontrenderer.drawStringWithShadow("rocket : " + heli.rocket, i - 300, j - 20 - 10, color);
			}
		}
		if (entityplayer.ridingEntity instanceof IdriveableVehicle && entityplayer.ridingEntity instanceof EntityLivingBase){//1
			EntityLiving balaam = (EntityLiving) entityplayer.ridingEntity;
			IdriveableVehicle vehicle = (IdriveableVehicle) entityplayer.ridingEntity;
			FontRenderer fontrenderer = minecraft.fontRenderer;
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			int am1 = vehicle.getfirecyclesettings1() - vehicle.getfirecycleprogress1();
			int am2 = vehicle.getfirecyclesettings2() - vehicle.getfirecycleprogress2();
			String d1 = String.format("%1$3d", am1);
			String d2 = String.format("%1$3d", am2);
			String hp = String.format("%1$3d", (int)balaam.getHealth());
			String mhp = String.format("%1$3d", (int)balaam.getMaxHealth());
			String th = String.valueOf(vehicle.getthrottle());
			String rotep = String.format("%1$3d", (int)vehicle.getturretrotationYaw());
			String rote = String.format("%1$3d", (int)vehicle.getbodyrotationYaw());

			if(balaam instanceof GVCEntityPMCT90Tank){
				if(((GVCEntityPMCT90Tank) balaam).fireCycle1 > 0)
				{
					fontrenderer.drawStringWithShadow("Weapon1 " + ((GVCEntityPMCT90Tank) balaam).fireCycle1, i - 180, j - 20 - 10, 0xFFFFFF);
				}else{
					fontrenderer.drawStringWithShadow("Weapon1 " + "Ready", i - 180, j - 20 - 10, 0xFFFFFF);
				}
				if (((GVCEntityPMCT90Tank) balaam).mgMagazine > 0) {
					fontrenderer.drawStringWithShadow("Kord " + ((GVCEntityPMCT90Tank) balaam).mgMagazine, i - 180, j - 20 - 20, 0xFFFFFF);
				} else {
					fontrenderer.drawStringWithShadow("Kord " + "reloading", i - 200, j - 20 - 20, 0xFFFFFF);
				}
			}else {
				am1 = vehicle.getfirecyclesettings1() - vehicle.getfirecycleprogress1();
				if(am1 < vehicle.getfirecyclesettings1())
				{
					fontrenderer.drawStringWithShadow("Weapon1 " + am1, i - 70, j - 20 - 10, 0xFFFFFF);
				}else{
					fontrenderer.drawStringWithShadow("Weapon1 " + "Ready", i - 70, j - 20 - 10, 0xFFFFFF);
				}
				if (vehicle.getfirecyclesettings2() != 0) {
					if (am2 > 0) {
						fontrenderer.drawStringWithShadow("Weapon2 " + d2, i - 70, j - 20 - 20, 0xFFFFFF);
					} else {
						fontrenderer.drawStringWithShadow("Weapon2 " + "Ready", i - 70, j - 20 - 20, 0xFFFFFF);
					}
				}
			}
			fontrenderer.drawStringWithShadow("HP "+hp+"/"+mhp, i - 70, j - 20 + 0, 0xFFFFFF);
			//fontrenderer.drawStringWithShadow("TH" + th, (i/2) - 80, j/2 + 0, 0xFFFFFF);
			//fontrenderer.drawStringWithShadow("Speed"+ speed, (i/2) - 80, j/2 +20, 0xFFFFFF);
			fontrenderer.drawStringWithShadow("Turret"+ rotep, (i/2) + 40, j/2 +0, 0xFFFFFF);
			fontrenderer.drawStringWithShadow("Body"+ rote, (i/2) + 40, j/2 +10, 0xFFFFFF);


			GuiIngame g  = minecraft.ingameGUI;
			minecraft.getTextureManager().bindTexture(TextureMap.locationItemsTexture);
			//g.drawTexturedModelRectFromIcon(i-70, j-63, armor.getIconFromDamage(0), 16, 16);
			GL11.glPopMatrix();


			{
				GL11.glPushMatrix();//21
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
				minecraft.renderEngine.bindTexture(new ResourceLocation("gvcmob:textures/items/hou1.png"));
				GL11.glTranslatef(32, scaledresolution.getScaledHeight()-32, 0F);
				GL11.glRotatef(vehicle.getbodyrotationYaw()-entityplayer.rotationYawHead, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(-32, -(scaledresolution.getScaledHeight()-32), 0F);
				//drawTexturedModalRect(scaledresolution.getScaledWidth()/2 -0,  scaledresolution.getScaledHeight()/2 +24, 0,0, 256, 256);
				GL11.glScalef(0.25f, 0.25f, 1);
				g.drawTexturedModalRect(0, (scaledresolution.getScaledHeight()-64)*4, 0,0, 256, 256);
				GL11.glScalef(4, 4, 1);

				minecraft.renderEngine.bindTexture(new ResourceLocation("gvcmob:textures/items/hou2.png"));
				GL11.glTranslatef(32, scaledresolution.getScaledHeight()-32, 0F);
				GL11.glRotatef(vehicle.getturretrotationYaw(), 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(-32, -(scaledresolution.getScaledHeight()-32), 0F);
				GL11.glScalef(0.25f, 0.25f, 1);
				g.drawTexturedModalRect(0, (scaledresolution.getScaledHeight()-64)*4, 0,0, 256, 256);
				GL11.glPopMatrix();//22
			}
		}
	}

	public void displayFlyersHUD(Quat4d prevbodyRot,Quat4d bodyRot,Entity plane,Vector3d prevmotionVec,RenderGameOverlayEvent.Text event){
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer entityplayer = minecraft.thePlayer;

		ScaledResolution scaledresolution = new ScaledResolution(minecraft, minecraft.displayWidth,
				minecraft.displayHeight);
		Quat4d tempquat = new Quat4d(
				prevbodyRot.x * (1-event.partialTicks) + bodyRot.x * event.partialTicks ,
				prevbodyRot.y * (1-event.partialTicks) + bodyRot.y * event.partialTicks ,
				prevbodyRot.z * (1-event.partialTicks) + bodyRot.z * event.partialTicks ,
				prevbodyRot.w * (1-event.partialTicks) + bodyRot.w * event.partialTicks );


		Vector3d motionvec = new Vector3d(
				  plane.motionX * event.partialTicks + prevmotionVec.x * (1-event.partialTicks),
				  plane.motionY * event.partialTicks + prevmotionVec.y * (1-event.partialTicks),
				-(plane.motionZ * event.partialTicks + prevmotionVec.z * (1-event.partialTicks)));
		Vector3d forDisplayplaneMotion;
		double angle = 0;
		if(motionvec.length()>0.01) {
			Quat4d quat4d = new Quat4d();
			quat4d.inverse(bodyRot);
			forDisplayplaneMotion = Calculater.transformVecByQuat(motionvec, quat4d);
			forDisplayplaneMotion.scale(-1);
			angle = toDegrees(forDisplayplaneMotion.angle(new Vector3d(0, 0, 1)));
			forDisplayplaneMotion.normalize();
			forDisplayplaneMotion.z = 0;
		}else {
			forDisplayplaneMotion = new Vector3d(0, 0, 1);
		}

		GL11.glPushMatrix();
		double width = scaledresolution.getScaledWidth_double();
		double height = scaledresolution.getScaledHeight_double();
		//HUDは300×650
		//一度3.6px
		//横幅を合わせる
		double scale = width/300;
		double sizeW = width;
		double sizeH = sizeW * 650/300;
		double[] xyz = Calculater.eulerfrommatrix(Calculater.matrixfromQuat(tempquat));
		xyz[0] = toDegrees(xyz[0]);
		xyz[1] = toDegrees(xyz[1]);
		xyz[2] = toDegrees(xyz[2]);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);

//		GL11.glRotatef((float) xyz[2],0,0,1);
//		GL11.glTranslatef((float)( forDisplayplaneMotion.x * angle * 3.6 * scale), (float)( forDisplayplaneMotion.y * angle * 3.6 * scale),0);
//		GL11.glRotatef((float) -xyz[2],0,0,1);

		GL11.glTranslatef((float)width/2,(float) height/2,0);
		GL11.glRotatef((float) -xyz[2],0,0,1);
		GL11.glTranslatef(-(float)width/2,-(float) height/2,0);


		Vector3d motionvecE = new Vector3d();
		if(motionvec.length()>0.01) {
			motionvecE.normalize(motionvec);
		}else {
			motionvecE.set(0,0,1);
		}
		minecraft.renderEngine.bindTexture(new ResourceLocation("gvcmob:textures/items/HUD.png"));
//		drawTexturedModalRect(0,height/2 - 325 * scale - (xyz[0] - toDegrees(asin(motionvecE.y))) * 1.8 * scale,sizeW,sizeH);
		drawTexturedModalRect(0,height/2 - 325 * scale - xyz[0] * 3.6 * scale,sizeW,sizeH);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		scale = width/300;
		sizeW = width;
		sizeH = width;
		minecraft.renderEngine.bindTexture(new ResourceLocation("gvcmob:textures/items/HUD2.png"));

		drawTexturedModalRect(0,height/2 - 150 * scale,sizeW,sizeH);
		GL11.glPopMatrix();


		GL11.glPushMatrix();
		scale = width/300;
		sizeW = width;
		sizeH = width;

		GL11.glTranslatef((float)( forDisplayplaneMotion.x * angle * 3.6 * scale), (float)( forDisplayplaneMotion.y * angle * 3.6 * scale),0);

		minecraft.renderEngine.bindTexture(new ResourceLocation("gvcmob:textures/items/HUD3.png"));
		drawTexturedModalRect(0,height/2 - 150 * scale,sizeW,sizeH);

		GL11.glPopMatrix();
	}
	/**
	 * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
	 */
	public void drawTexturedModalRect(double p_73729_1_, double p_73729_2_, double p_73729_5_, double p_73729_6_)
	{
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double)(p_73729_1_), (double)(p_73729_2_ + p_73729_6_), (double)this.zLevel			, 0, 1);
		tessellator.addVertexWithUV((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_ + p_73729_6_), (double)this.zLevel	, 1, 1);
		tessellator.addVertexWithUV((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_), (double)this.zLevel			, 1, 0);
		tessellator.addVertexWithUV((double)(p_73729_1_), (double)(p_73729_2_), (double)this.zLevel						, 0, 0);
		tessellator.draw();
	}
}
