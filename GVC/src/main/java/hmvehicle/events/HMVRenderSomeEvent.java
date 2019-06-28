package hmvehicle.events;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hmvehicle.CLProxy;
import hmvehicle.Utils;
import hmvehicle.entity.EntityCameraDummy;
import hmvehicle.entity.EntityChild;
import hmvehicle.entity.parts.IControlable;
import hmvehicle.entity.parts.IShip;
import hmvehicle.entity.parts.IVehicle;
import hmvehicle.entity.parts.Iplane;
import hmvehicle.entity.parts.logics.PlaneBaseLogic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;
import java.util.ArrayList;

import static handmadeguns.event.HMGEventZoom.renderPumpkinBlur;
import static hmvehicle.HMVehicle.proxy_HMVehicle;
import static java.lang.Math.sqrt;
import static java.lang.Math.toDegrees;
import static org.lwjgl.opengl.GL11.*;

public class HMVRenderSomeEvent {

	static boolean needrest = true;
	private double zLevel = 0;
	private static final IModelCustom attitude_indicator = AdvancedModelLoader.loadModel(new ResourceLocation("gvcmob:textures/model/Attitude indicator.mqo"));
	private static final ResourceLocation attitude_indicator_texture = new ResourceLocation("gvcmob:textures/model/Attitude indicator.png");

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderridding(RenderPlayerEvent.Pre event)
	{
		if(event.entityPlayer.ridingEntity instanceof IVehicle && FMLClientHandler.instance().getClient().gameSettings.thirdPersonView == 0)event.setCanceled(true);
	}
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderfov(FOVUpdateEvent event)
	{
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer entityplayer = minecraft.thePlayer;
		if (entityplayer.ridingEntity != null) {
			if(entityplayer.ridingEntity instanceof IControlable){
				boolean rc = proxy_HMVehicle.zoomclick();
				if(rc) CLProxy.zooming = !CLProxy.zooming;

				if(CLProxy.zooming) {
					event.newfov = event.fov / 2;
				}
			}
		}else {
			CLProxy.zooming = false;
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
	public void renderPlayer(RenderPlayerEvent.Pre event){
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer entityplayer = minecraft.thePlayer;
		if(event.entityLiving != entityplayer)return;
		if(entityplayer.ridingEntity instanceof EntityChild && FMLClientHandler.instance().getClient().gameSettings.thirdPersonView == 0){
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
						if(entityplayer.ridingEntity instanceof IVehicle){
							ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, ((IVehicle) entityplayer.ridingEntity).getthirdDist(), "thirdPersonDistance", "E", "field_78490_B");
							needrest = true;
						}
						if (entityplayer.ridingEntity instanceof Iplane) {
							ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, ((Iplane) entityplayer.ridingEntity).getthirdDist(), "thirdPersonDistance", "E", "field_78490_B");
							{
								Entity panebody = entityplayer.ridingEntity;
								Iplane iplanebody = (Iplane) entityplayer.ridingEntity;
								PlaneBaseLogic plane = (PlaneBaseLogic) iplanebody.getBaseLogic();
								Vector3d bodyvector = Utils.transformVecByQuat(new Vector3d(0, 0, 1), plane.bodyRot);
								Vector3d tailwingvector = Utils.transformVecByQuat(new Vector3d(0, 1, 0), plane.bodyRot);
								Vector3d mainwingvector = Utils.transformVecByQuat(new Vector3d(1, 0, 0), plane.bodyRot);
								if (plane.ispilot(entityplayer)) {
									Quat4d tempquat = new Quat4d(
											plane.prevbodyRot.x * (1-event.renderTickTime) + plane.bodyRot.x * event.renderTickTime ,
											plane.prevbodyRot.y * (1-event.renderTickTime) + plane.bodyRot.y * event.renderTickTime ,
											plane.prevbodyRot.z * (1-event.renderTickTime) + plane.bodyRot.z * event.renderTickTime ,
											plane.prevbodyRot.w * (1-event.renderTickTime) + plane.bodyRot.w * event.renderTickTime );
//									if (abs(plane.pitchladder) > 0.001) {
//										Vector3d axisx = Utils.transformVecByQuat(new Vector3d(1, 0, 0), tempquat);
//										AxisAngle4d axisxangled = new AxisAngle4d(axisx, toRadians(-plane.pitchladder * event.renderTickTime / 4));
//										tempquat = Utils.quatRotateAxis(tempquat, axisxangled);
//									}
//									if (abs(plane.yawladder) > 0.001) {
//										Vector3d axisy = Utils.transformVecByQuat(new Vector3d(0, 1, 0), tempquat);
//										AxisAngle4d axisyangled = new AxisAngle4d(axisy, toRadians(plane.yawladder * event.renderTickTime / 4));
//										tempquat = Utils.quatRotateAxis(tempquat, axisyangled);
//									}
//									if (abs(plane.rollladder) > 0.001) {
//										Vector3d axisz = Utils.transformVecByQuat(new Vector3d(0, 0, 1), tempquat);
//										AxisAngle4d axiszangled = new AxisAngle4d(axisz, toRadians(plane.rollladder * event.renderTickTime / 4));
//										tempquat = Utils.quatRotateAxis(tempquat, axiszangled);
//									}

									double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(tempquat));
									xyz[0] = toDegrees(xyz[0]);
									xyz[1] = toDegrees(xyz[1]);
									xyz[2] = toDegrees(xyz[2]);
//							System.out.println("y" + xyz[0] + " , x" + xyz[1] + " , z" + xyz[2] + " , renderTickTime" + event.renderTickTime);
									bodyvector = Utils.transformVecByQuat(new Vector3d(0, 0, 1), tempquat);
									tailwingvector = Utils.transformVecByQuat(new Vector3d(0, 1, 0), tempquat);
									mainwingvector = Utils.transformVecByQuat(new Vector3d(1, 0, 0), tempquat);
									Utils.transformVecforMinecraft(tailwingvector);
									Utils.transformVecforMinecraft(bodyvector);
									Utils.transformVecforMinecraft(mainwingvector);
									mainwingvector.scale(plane.camerapos[0]-plane.rotcenter[0]);
									tailwingvector.scale(plane.camerapos[1]-plane.rotcenter[1]);
									bodyvector.scale(plane.camerapos[2]-plane.rotcenter[2]);
									if (plane.camera != null) {
										plane.camera.setLocationAndAngles(
												panebody.prevPosX + (panebody.posX - panebody.prevPosX) * event.renderTickTime + bodyvector.x + tailwingvector.x + mainwingvector.x + plane.rotcenter[0],
												panebody.prevPosY + (panebody.posY - panebody.prevPosY) * event.renderTickTime + bodyvector.y + tailwingvector.y + mainwingvector.y + plane.rotcenter[1] - entityplayer.yOffset,
												panebody.prevPosZ + (panebody.posZ - panebody.prevPosZ) * event.renderTickTime + bodyvector.z + tailwingvector.z + mainwingvector.z + plane.rotcenter[2],
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
									panebody.rotationYaw = (float) xyz[1];
									panebody.prevRotationYaw = (float) xyz[1];
									plane.bodyrotationPitch = (float) xyz[0];
									plane.prevbodyrotationPitch = (float) xyz[0];
									panebody.rotationPitch = (float) xyz[0];
									panebody.prevRotationPitch = (float) xyz[0];
									plane.bodyrotationRoll = (float) xyz[2];
									plane.prevbodyrotationRoll = (float) xyz[2];
									ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, (float) xyz[2], "camRoll", "R", "field_78495_O");
									ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, (float) xyz[2], "prevCamRoll", "R", "field_78495_O");
									ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, 24, "thirdPersonDistance", "E", "field_78490_B");
									entityplayer.rotationYaw = plane.bodyrotationYaw;
									entityplayer.prevRotationYaw = plane.bodyrotationYaw;
									entityplayer.rotationPitch = plane.bodyrotationPitch;
									entityplayer.prevRotationPitch = plane.bodyrotationPitch;
									bodyvector = Utils.transformVecByQuat(new Vector3d(0, 0, 1), tempquat);
									tailwingvector = Utils.transformVecByQuat(new Vector3d(0, 1, 0), tempquat);
									mainwingvector = Utils.transformVecByQuat(new Vector3d(1, 0, 0), tempquat);
								}

								tailwingvector.normalize();
								bodyvector.normalize();
								mainwingvector.normalize();
								Utils.transformVecforMinecraft(tailwingvector);
								Utils.transformVecforMinecraft(bodyvector);
								Utils.transformVecforMinecraft(mainwingvector);
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
	public void renderover(RenderGameOverlayEvent.Pre event) {
		GuiIngameForge.renderCrosshairs = true;
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer entityplayer = minecraft.thePlayer;

		ScaledResolution scaledresolution = new ScaledResolution(minecraft, minecraft.displayWidth,
				minecraft.displayHeight);
		int i = scaledresolution.getScaledWidth();
		int j = scaledresolution.getScaledHeight();
		if(entityplayer.ridingEntity instanceof Iplane && ((PlaneBaseLogic)((Iplane) entityplayer.ridingEntity).getBaseLogic()).ispilot(entityplayer)){
			FontRenderer fontrenderer = minecraft.fontRenderer;
			Entity planebody = entityplayer.ridingEntity;
			Iplane iplane = (Iplane) entityplayer.ridingEntity;
			PlaneBaseLogic plane = (PlaneBaseLogic) iplane.getBaseLogic();
			if(plane.displayModernHud)displayFlyersHUD_AftGen2(plane.prevbodyRot,plane.bodyRot,planebody,plane.prevmotionVec,event);
			else displayFlyersHUD(plane.prevbodyRot,plane.bodyRot,planebody,plane.prevmotionVec,event);
//				Quat4d tempquat = new Quat4d(
//						plane.prevbodyRot.x * (1-event.partialTicks) + plane.bodyRot.x * event.partialTicks ,
//						plane.prevbodyRot.y * (1-event.partialTicks) + plane.bodyRot.y * event.partialTicks ,
//						plane.prevbodyRot.z * (1-event.partialTicks) + plane.bodyRot.z * event.partialTicks ,
//						plane.prevbodyRot.w * (1-event.partialTicks) + plane.bodyRot.w * event.partialTicks );
////									if (abs(plane.pitchladder) > 0.001) {
////										Vector3d axisx = Utils.transformVecByQuat(new Vector3d(1, 0, 0), tempquat);
////										AxisAngle4d axisxangled = new AxisAngle4d(axisx, toRadians(-plane.pitchladder * event.renderTickTime / 4));
////										tempquat = Utils.quatRotateAxis(tempquat, axisxangled);
////									}
////									if (abs(plane.yawladder) > 0.001) {
////										Vector3d axisy = Utils.transformVecByQuat(new Vector3d(0, 1, 0), tempquat);
////										AxisAngle4d axisyangled = new AxisAngle4d(axisy, toRadians(plane.yawladder * event.renderTickTime / 4));
////										tempquat = Utils.quatRotateAxis(tempquat, axisyangled);
////									}
////									if (abs(plane.rollladder) > 0.001) {
////										Vector3d axisz = Utils.transformVecByQuat(new Vector3d(0, 0, 1), tempquat);
////										AxisAngle4d axiszangled = new AxisAngle4d(axisz, toRadians(plane.rollladder * event.renderTickTime / 4));
////										tempquat = Utils.quatRotateAxis(tempquat, axiszangled);
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
//				double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(tempquat));
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
//				forDisplayPlaneMotion = Utils.transformVecByQuat(forDisplayPlaneMotion,quat4d);
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
			fontrenderer.drawStringWithShadow("Speed : " + (int)(sqrt(planebody.motionX * planebody.motionX + planebody.motionY * planebody.motionY + planebody.motionZ * planebody.motionZ)*72), i - 300, j - 80 - 10, color);
			color = 0xFFFFFF;
			fontrenderer.drawStringWithShadow("Throttle : " + (int)(plane.throttle*10) + (plane.throttle<plane.throttle_gearDown?"Gear Down":"") + "   Flap position : " + plane.flaplevel, i - 300, j - 60, color);
			color = 0x00FF00;
			if (plane.health < 100) {
				color = 0xFF0000;
			}
			fontrenderer.drawStringWithShadow("Armor : " + plane.health, i - 300, j - 40 - 10, color);
			color = 0x74AAFF;
//				fontrenderer.drawStringWithShadow("Missile : " + plane.rocket + " : " + (plane.missile != null ? "Continue radar irradiation" : plane.illuminated != null?"LOCK":""), i - 300, j - 20 - 10, color);
			GL11.glDisable(GL11.GL_BLEND);
		}else
		if (entityplayer.ridingEntity instanceof IVehicle && entityplayer.ridingEntity instanceof EntityLivingBase){//1
			EntityLiving vehicleBody = (EntityLiving) entityplayer.ridingEntity;
			IVehicle vehicle = (IVehicle) entityplayer.ridingEntity;
			FontRenderer fontrenderer = minecraft.fontRenderer;
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			int am1 = vehicle.getfirecyclesettings1() - vehicle.getfirecycleprogress1();
			int am2 = vehicle.getfirecyclesettings2() - vehicle.getfirecycleprogress2();
			String d1 = String.format("%1$3d", am1);
			String d2 = String.format("%1$3d", am2);
			String hp = String.format("%1$3d", (int)vehicleBody.getHealth());
			String mhp = String.format("%1$3d", (int)vehicleBody.getMaxHealth());
			String th = String.valueOf(vehicle.getthrottle());
			String rotep = String.format("%1$3d", (int)vehicle.getturretrotationYaw());
			String rote = String.format("%1$3d", (int)vehicle.getbodyrotationYaw());

			{
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
			
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			int color = 0xFFFFFF;
			fontrenderer.drawStringWithShadow("Speed : " + (int)(sqrt(vehicleBody.motionX * vehicleBody.motionX + vehicleBody.motionY * vehicleBody.motionY + vehicleBody.motionZ * vehicleBody.motionZ)*72), i - 300, j - 80 - 10, color);

			if(vehicleBody instanceof IShip){
				fontrenderer.drawStringWithShadow("Draft : " + ((IShip) vehicleBody).getDraft(), i - 300, j - 160 - 10, color);
			}
			GuiIngame g  = minecraft.ingameGUI;
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
			
			if(vehicleBody instanceof IControlable && vehicle.getsightTex() != null){
				
				if(CLProxy.zooming) {
					float screenposX = 0;
					float screenposY = 0;
					float screenWidth = scaledresolution.getScaledWidth();
					float screenHeight = scaledresolution.getScaledHeight();
					if (screenWidth / screenHeight < 2) {
						screenWidth = screenHeight * 2;
						screenposX = scaledresolution.getScaledWidth() - screenWidth;
						screenposX /= 2;
					} else {
						screenHeight = screenWidth / 2;
						screenposY = (scaledresolution.getScaledHeight() - screenHeight)/2;
					}
					renderPumpkinBlur(minecraft, screenposX, screenposY, screenWidth, screenHeight, new ResourceLocation(vehicle.getsightTex()));
					GuiIngameForge.renderCrosshairs = false;
				}
			}
		}
		minecraft.getTextureManager().bindTexture(Gui.icons);
		boolean rc = proxy_HMVehicle.zoomclick();
		if(rc) CLProxy.zooming = !CLProxy.zooming;
	}
	public void displayFlyersHUD(Quat4d prevbodyRot, Quat4d bodyRot, Entity plane, Vector3d prevmotionVec, RenderGameOverlayEvent.Pre event){
		GL11.glColor4f(1,1,1,1);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glEnable(GL_BLEND);
		GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		FontRenderer fontrenderer = minecraft.fontRenderer;
		
		ScaledResolution scaledresolution = new ScaledResolution(minecraft, minecraft.displayWidth,
				                                                        minecraft.displayHeight);
		Quat4d tempquat = new Quat4d(
				                            prevbodyRot.x * (1-event.partialTicks) + bodyRot.x * event.partialTicks ,
				                            prevbodyRot.y * (1-event.partialTicks) + bodyRot.y * event.partialTicks ,
				                            prevbodyRot.z * (1-event.partialTicks) + bodyRot.z * event.partialTicks ,
				                            prevbodyRot.w * (1-event.partialTicks) + bodyRot.w * event.partialTicks );
		
		
		GL11.glPushMatrix();
		double width = scaledresolution.getScaledWidth_double();
		double height = scaledresolution.getScaledHeight_double();
		//HUDは300×650
		//一度3.6px
		//横幅を合わせる
		double scale = width/300;
		float sizeW = (float) width;
		float sizeH = (float) height;
		double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(tempquat));
		xyz[0] = toDegrees(xyz[0]);
		xyz[1] = toDegrees(xyz[1]);
		xyz[2] = toDegrees(xyz[2]);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_CULL_FACE);
		minecraft.renderEngine.bindTexture(attitude_indicator_texture);
		GL11.glTranslatef(sizeW - sizeW/6,sizeH - sizeH/6, 0);
		GL11.glScalef(30,-30,30);
		attitude_indicator.renderPart("obj2");
		GL11.glRotatef((float) xyz[2],0,0,1);
		GL11.glRotatef((float) -xyz[0],1,0,0);
		attitude_indicator.renderPart("obj1");
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glPopMatrix();
	}

	public void displayFlyersHUD_AftGen2(Quat4d prevbodyRot, Quat4d bodyRot, Entity plane, Vector3d prevmotionVec, RenderGameOverlayEvent.Pre event){
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glEnable(GL_BLEND);
		GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		FontRenderer fontrenderer = minecraft.fontRenderer;

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
			forDisplayplaneMotion = Utils.transformVecByQuat(motionvec, quat4d);
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
		double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(tempquat));
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
//		System.out.println(xyz[0]);
		double offset = -xyz[0] % 5;
		int currentLineNum = (int) (xyz[0]/5);
		if(offset<0)offset+=5;
		if(xyz[0]>0)currentLineNum +=1;
		float lineWidh = 0.5f;
		float lineLength = (float) (sizeW/6);
		float midBlankLength = (float) (sizeW/24);
		float lineLength2 = 0.5f;
//		for(int num = 0;num + currentLineNum < 18&& num < 5;num ++){
//			drawHudLine(sizeW/2 + midBlankLength/2 + (lineLength - midBlankLength)/4 * 3 ,height/2 + offset * 3.6 * scale + num * 5 * 3.6 * scale,(lineLength - midBlankLength)/2,lineWidh * scale);
//			drawHudLine(sizeW/2 - midBlankLength/2 - (lineLength - midBlankLength)/4 * 3 ,height/2 + offset * 3.6 * scale + num * 5 * 3.6 * scale,(lineLength - midBlankLength)/2,lineWidh * scale);
//			drawHudLine(sizeW/2 + lineLength + lineWidh * scale/2,
//					height/2 + offset * 3.6 * scale + num * 5 * 3.6 * scale + (num + currentLineNum) * scale/2,
//					lineWidh * scale,
//					(num + currentLineNum) * scale * lineLength2);
//
//			fontrenderer.drawString(String.valueOf((num + currentLineNum) * 5),
//					(int)(sizeW/2 + lineLength + lineWidh * scale/2),
//					(int)(height/2 + offset * 3.6 * scale + num * 5 * 3.6 * scale + (num + currentLineNum) * scale/2),
//					0xFAFF9E);
//
//			drawHudLine(sizeW/2 - lineLength + lineWidh * scale/2,
//					height/2 + offset * 3.6 * scale + num * 5 * 3.6 * scale + (num + currentLineNum) * scale/2,
//					lineWidh * scale,
//					(num + currentLineNum) * scale * lineLength2);
//
//			fontrenderer.drawString(String.valueOf((num + currentLineNum) * 5),
//					(int)(sizeW/2 - lineLength + lineWidh * scale/2),
//					(int)(height/2 + offset * 3.6 * scale + num * 5 * 3.6 * scale + (num + currentLineNum) * scale/2),
//					0xFAFF9E);
//		}
		int num = -4;
		if(num + currentLineNum < -18){
			num = currentLineNum - 18;
		}
		for(;num + currentLineNum < 18 && num < 4;num ++){
			drawHudLine(
					sizeW/2 + midBlankLength/2,
					height/2 + offset * 3.6 * scale + num * 5 * 3.6 * scale,
					sizeW/2 + lineLength/2,
					height/2 + offset * 3.6 * scale + num * 5 * 3.6 * scale + lineWidh * scale);
			drawHudLine(
					sizeW/2 + lineLength/2,
					height/2 + offset * 3.6 * scale + num * 5 * 3.6 * scale,
					sizeW/2 + lineLength/2 - lineWidh * scale,
					height/2 + offset * 3.6 * scale + num * 5 * 3.6 * scale - (currentLineNum + num) * lineLength2 * scale);
			
			
			drawHudLine(
					sizeW/2 - lineLength/2,
					height/2 + offset * 3.6 * scale + num * 5 * 3.6 * scale,
					sizeW/2 - midBlankLength/2,
					height/2 + offset * 3.6 * scale + num * 5 * 3.6 * scale + lineWidh * scale);
			drawHudLine(
					sizeW/2 - lineLength/2,
					height/2 + offset * 3.6 * scale + num * 5 * 3.6 * scale,
					sizeW/2 - lineLength/2 + lineWidh * scale,
					height/2 + offset * 3.6 * scale + num * 5 * 3.6 * scale - (currentLineNum + num) * lineLength2 * scale);
			
			fontrenderer.drawString(String.valueOf((num + currentLineNum) * 5),
					(int)(sizeW/2 + lineLength + lineWidh * scale/2),
					(int)(height/2 + offset * 3.6 * scale + num * 5 * 3.6 * scale + (num + currentLineNum) * scale/2),
					0xFAFF9E);
			
//			drawHudLine(sizeW/2 - lineLength + lineWidh * scale/2,
//					height/2 + offset * 3.6 * scale + num * 5 * 3.6 * scale + (num + currentLineNum) * scale/2,
//					lineWidh * scale,
//					(num + currentLineNum) * scale * lineLength2);
			
			fontrenderer.drawString(String.valueOf((num + currentLineNum) * 5),
					(int)(sizeW/2 - lineLength + lineWidh * scale/2),
					(int)(height/2 + offset * 3.6 * scale + num * 5 * 3.6 * scale + (num + currentLineNum) * scale/2),
					0xFAFF9E);
		}
//		drawTexturedModalRect(0,height/2 - 325 * scale - xyz[0] * 3.6 * scale,sizeW,sizeH);
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
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double)(p_73729_1_), (double)(p_73729_2_ + p_73729_6_), (double)this.zLevel			, 0, 1);
		tessellator.addVertexWithUV((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_ + p_73729_6_), (double)this.zLevel	, 1, 1);
		tessellator.addVertexWithUV((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_), (double)this.zLevel			, 1, 0);
		tessellator.addVertexWithUV((double)(p_73729_1_), (double)(p_73729_2_), (double)this.zLevel						, 0, 0);
		tessellator.draw();
	}
	
	public void drawHudLine(double p_73729_1_, double p_73729_2_, double p_73729_5_, double p_73729_6_){
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_CULL_FACE);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_I(0xFAFF9E);
		tessellator.addVertex((double)(p_73729_1_), (double)(p_73729_6_), (double)this.zLevel);
		tessellator.addVertex((double)(p_73729_5_), (double)(p_73729_6_), (double)this.zLevel);
		tessellator.addVertex((double)(p_73729_5_), (double)(p_73729_2_), (double)this.zLevel);
		tessellator.addVertex((double)(p_73729_1_), (double)(p_73729_2_), (double)this.zLevel);
		tessellator.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
}
