package handmadevehicle.events;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import handmadeguns.Util.EntityLinkedPos_Motion;
import handmadeguns.event.RenderTickSmoothing;
import handmadeguns.items.GunInfo;
import handmadevehicle.entity.EntityCameraDummy;
import handmadevehicle.entity.parts.IVehicle;
import handmadevehicle.entity.parts.SeatInfo;
import handmadevehicle.entity.parts.logics.BaseLogic;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import javax.script.ScriptException;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import java.util.ArrayList;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;
import static handmadeguns.event.HMGEventZoom.*;
import static handmadevehicle.CLProxy.zooming;
import static handmadevehicle.HMVehicle.HMV_Proxy;
import static handmadevehicle.Utils.*;
import static java.lang.Math.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

public class HMVRenderSomeEvent {

	public static int playerSeatID;
	public static ArrayList<EntityLinkedPos_Motion> missile_Pos_Motion = new ArrayList<EntityLinkedPos_Motion>();
	public static ArrayList<EntityLinkedPos_Motion>  target_Pos_Motion = new ArrayList<EntityLinkedPos_Motion>();
	static boolean needrest = true;
	private double zLevel = 0;
	private static final IModelCustom attitude_indicator = AdvancedModelLoader.loadModel(new ResourceLocation("handmadevehicle:textures/model/Attitude indicator.mqo"));
	private static final ResourceLocation attitude_indicator_texture = new ResourceLocation("handmadevehicle:textures/model/Attitude indicator.png");

	public static boolean receivedTargetData;

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
		if (entityplayer.ridingEntity instanceof IVehicle){
			boolean rc = HMV_Proxy.zoomclick();
			if(rc) zooming = !zooming;

			if(HMV_Proxy.iszooming()) {

				TurretObj turretObj = getPlayerUsingMainTurret(entityplayer);
				if(turretObj != null) {
					if (turretObj.gunItem != null && turretObj.gunItem.gunInfo.scopezoombase != 1) {
						event.newfov = event.fov / turretObj.gunItem.gunInfo.scopezoombase;
					} else if (((IVehicle) entityplayer.ridingEntity).getBaseLogic().prefab_vehicle.prefab_seats.length > playerSeatID) {
						event.newfov = event.fov / ((IVehicle) entityplayer.ridingEntity).getBaseLogic().prefab_vehicle.prefab_seats[playerSeatID].zoomLevel;
					}
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
	public void renderPlayer(RenderPlayerEvent.Pre event){
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer entityplayer = minecraft.thePlayer;
		if(event.entityLiving != entityplayer)return;
	}
	public static double renderTickTime;
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderTick(TickEvent.RenderTickEvent event)
	{
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer entityplayer = minecraft.thePlayer;
		renderTickTime = event.renderTickTime;
		if(entityplayer != null)switch(event.phase)
		{
			case START :
				if (needrest) {
					ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, 0, "camRoll", "R", "field_78495_O");
					ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, 0, "prevCamRoll", "R", "field_78495_O");
					ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, 4, "thirdPersonDistance", "E", "field_78490_B");
					needrest = false;
					if ((minecraft.renderViewEntity instanceof EntityCameraDummy)) minecraft.renderViewEntity = entityplayer;
				}
				{
					if(entityplayer.ridingEntity instanceof IVehicle){
						needrest = true;
						BaseLogic logic = ((IVehicle) entityplayer.ridingEntity).getBaseLogic();
						Entity vehicleBody = entityplayer.ridingEntity;
						{
							Vector3d nowPos = new Vector3d();
							nowPos.interpolate(
									new Vector3d(
											vehicleBody.prevPosX,
											vehicleBody.prevPosY,
											vehicleBody.prevPosZ),
									new Vector3d(
											vehicleBody.posX,
											vehicleBody.posY,
											vehicleBody.posZ),
									renderTickTime);

							Quat4d currentquat = new Quat4d(0,0,0,1);
							if(getQuat4DLength(logic.prevbodyRot)>0 && getQuat4DLength(logic.bodyRot)>0)
								currentquat.interpolate(logic.prevbodyRot,logic.bodyRot, (double) renderTickTime);
//									if (abs(logic.pitchladder) > 0.001) {
//										Vector3d axisx = transformVecByQuat(new Vector3d(1, 0, 0), currentquat);
//										AxisAngle4d axisxangled = new AxisAngle4d(axisx, toRadians(-logic.pitchladder * renderTickTime / 4));
//										currentquat = quatRotateAxis(currentquat, axisxangled);
//									}
//									if (abs(logic.yawladder) > 0.001) {
//										Vector3d axisy = transformVecByQuat(new Vector3d(0, 1, 0), currentquat);
//										AxisAngle4d axisyangled = new AxisAngle4d(axisy, toRadians(logic.yawladder * renderTickTime / 4));
//										currentquat = quatRotateAxis(currentquat, axisyangled);
//									}
//									if (abs(logic.rollladder) > 0.001) {
//										Vector3d axisz = transformVecByQuat(new Vector3d(0, 0, 1), currentquat);
//										AxisAngle4d axiszangled = new AxisAngle4d(axisz, toRadians(logic.rollladder * renderTickTime / 4));
//										currentquat = quatRotateAxis(currentquat, axiszangled);
//									}

							double[] xyz = eulerfromQuat((currentquat));
							xyz[0] = toDegrees(xyz[0]);
							xyz[1] = toDegrees(xyz[1]);
							xyz[2] = toDegrees(xyz[2]);

							if (logic.ispilot(entityplayer)) {
								if (!logic.mouseStickMode || logic.prefab_vehicle.T_Land_F_Plane) {
									float f1 = HMG_proxy.getMCInstance().gameSettings.mouseSensitivity * 0.6F + 0.2F;
									float f2 = f1 * f1 * f1 * 8.0F;
									float f3 = (float) HMG_proxy.getMCInstance().mouseHelper.deltaX * f2 * 2;
									float f4 = (float) HMG_proxy.getMCInstance().mouseHelper.deltaY * f2 * 2;
									logic.cameraYaw += f3 * 0.15D;
									logic.cameraPitch += f4 * 0.15D;
									if (logic.cameraPitch > 90) logic.cameraPitch = 90;
									if (logic.cameraPitch < -90) logic.cameraPitch = -90;
								}

								Quat4d Headrot = new Quat4d(0,0,0,1);
								Headrot = quatRotateAxis(Headrot, new AxisAngle4d(unitX, toRadians(logic.cameraPitch) / 2));
								Headrot = quatRotateAxis(Headrot, new AxisAngle4d(unitY, toRadians(logic.cameraYaw) / 2));
								logic.camerarot.set(Headrot);
								logic.camerarot_current.set(logic.camerarot);

//							System.out.println("y" + xyz[0] + " , x" + xyz[1] + " , z" + xyz[2] + " , renderTickTime" + renderTickTime);

//								Vector3d bodyvector = transformVecByQuat(new Vector3d(0, 0, 1), currentquat);
//								Vector3d tailwingvector = transformVecByQuat(new Vector3d(0, 1, 0), currentquat);
//								Vector3d mainwingvector = transformVecByQuat(new Vector3d(1, 0, 0), currentquat);
//
//								transformVecforMinecraft(tailwingvector);
//								transformVecforMinecraft(bodyvector);
//								transformVecforMinecraft(mainwingvector);
//								mainwingvector.scale(logic.getCamerapos()[0]-logic.prefab_vehicle.rotcenter[0]);
//								tailwingvector.scale(logic.getCamerapos()[1]-logic.prefab_vehicle.rotcenter[1]);
//								bodyvector.scale(logic.getCamerapos()[2]-logic.prefab_vehicle.rotcenter[2]);
								if (logic.camera != null) {
									Quat4d currentcamRot = new Quat4d(currentquat);
									currentcamRot.mul(zooming && logic.prefab_vehicle.camerarot_zoom != null ?logic.prefab_vehicle.camerarot_zoom: logic.camerarot_current);
									double[] cameraxyz = eulerfromQuat((currentcamRot));
									cameraxyz[0] = toDegrees(cameraxyz[0]);
									cameraxyz[1] = toDegrees(cameraxyz[1]);
									cameraxyz[2] = toDegrees(cameraxyz[2]);
									if(!logic.seatInfos[logic.getpilotseatid()].prefab_seat.seatOnTurret) {
										Vector3d cameraPos_Global = new Vector3d(logic.getCamerapos());
										cameraPos_Global.sub(new Vector3d(logic.prefab_vehicle.rotcenter));
										cameraPos_Global = transformVecByQuat(cameraPos_Global, currentquat);
										cameraPos_Global.add(new Vector3d(logic.prefab_vehicle.rotcenter));
										transformVecforMinecraft(cameraPos_Global);
										logic.camera.setLocationAndAngles(
												vehicleBody.prevPosX + (vehicleBody.posX - vehicleBody.prevPosX) * renderTickTime + cameraPos_Global.x,
												vehicleBody.prevPosY + (vehicleBody.posY - vehicleBody.prevPosY) * renderTickTime + cameraPos_Global.y - entityplayer.yOffset,
												vehicleBody.prevPosZ + (vehicleBody.posZ - vehicleBody.prevPosZ) * renderTickTime + cameraPos_Global.z,
												(float) cameraxyz[1], (float) cameraxyz[0]);
									}else {
										logic.riderPosUpdate_camera(nowPos, currentquat,renderTickTime);
//										logic.camera.setLocationAndAngles(
//												entityplayer.posX,
//												entityplayer.posY - entityplayer.yOffset,
//												entityplayer.posZ,
//												(float) cameraxyz[1], (float) cameraxyz[0]);
									}
									minecraft.renderViewEntity = logic.camera;
									logic.camera.rotationYaw = (float) cameraxyz[1];
									logic.camera.prevRotationYaw = (float) cameraxyz[1];
									logic.camera.rotationYawHead = (float) cameraxyz[1];
									logic.camera.prevRotationYawHead = (float) cameraxyz[1];
									if(Double.isNaN(cameraxyz[0])){
										cameraxyz[0] = 0;
									}
									logic.camera.rotationPitch = (float) cameraxyz[0];
									logic.camera.prevRotationPitch = (float) cameraxyz[0];
									ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, (float) cameraxyz[2], "camRoll", "R", "field_78495_O");
									ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, (float) cameraxyz[2], "prevCamRoll", "R", "field_78495_O");
								}
							}
//							logic.riderPosUpdate_forRender(nowPos, currentquat);
							logic.bodyrotationYaw = (float) xyz[1];
							logic.prevbodyrotationYaw = (float) xyz[1];
							logic.bodyrotationPitch = (float) xyz[0];
							logic.prevbodyrotationPitch = (float) xyz[0];
							vehicleBody.rotationPitch = (float) xyz[0];
							vehicleBody.prevRotationPitch = (float) xyz[0];
							logic.bodyrotationRoll = (float) xyz[2];
							logic.prevbodyrotationRoll = (float) xyz[2];
							ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer, logic.prefab_vehicle.thirdPersonDistance, "thirdPersonDistance", "E", "field_78490_B");
							needrest = true;
							
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
	public void drawScreenEvent(GuiScreenEvent.DrawScreenEvent.Pre event) {
	}
	boolean NeedReset;
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderover(RenderGameOverlayEvent.Post event) {

		if (event.type == RenderGameOverlayEvent.ElementType.ALL) {
			GL11.glEnable(GL_BLEND);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0);
			ArrayList<EntityLinkedPos_Motion> tempDel = new ArrayList<>();
			for (EntityLinkedPos_Motion a_target_pos_motion : target_Pos_Motion) {
				if (a_target_pos_motion.for_aliveCnt > event.partialTicks) a_target_pos_motion.livingTime++;
				if (a_target_pos_motion.livingTime > 1) tempDel.add(a_target_pos_motion);
				a_target_pos_motion.for_aliveCnt = event.partialTicks;
			}
			target_Pos_Motion.removeAll(tempDel);
			tempDel = new ArrayList<>();
			for (EntityLinkedPos_Motion a_missile_pos_motion : missile_Pos_Motion) {
				if (a_missile_pos_motion.for_aliveCnt > event.partialTicks) a_missile_pos_motion.livingTime++;
				if (a_missile_pos_motion.livingTime > 20) tempDel.add(a_missile_pos_motion);
				a_missile_pos_motion.for_aliveCnt = event.partialTicks;
			}
			missile_Pos_Motion.removeAll(tempDel);

			Minecraft minecraft = FMLClientHandler.instance().getClient();
			EntityPlayer entityplayer = minecraft.thePlayer;

			if (entityplayer.ridingEntity instanceof IVehicle) {

				GuiIngameForge.renderCrosshairs = true;
				NeedReset = true;
				BaseLogic logic = ((IVehicle) entityplayer.ridingEntity).getBaseLogic();
				Entity vehicleBody = entityplayer.ridingEntity;
				ScaledResolution scaledresolution = new ScaledResolution(minecraft, minecraft.displayWidth,
						minecraft.displayHeight);
				int i = scaledresolution.getScaledWidth();
				int j = scaledresolution.getScaledHeight();

				boolean skip_HUD = false;
				if (logic.prefab_vehicle.script_global != null) {
					try {
						skip_HUD = (boolean) logic.prefab_vehicle.script_global.invokeFunction("GUI_rendering_HUD", this, vehicleBody, i, j);
					} catch (NoSuchMethodException | ScriptException e) {
						e.printStackTrace();
					}
				}
				if (!skip_HUD && logic.ispilot(entityplayer)) {
					if (!logic.prefab_vehicle.T_Land_F_Plane) {
						Entity planebody = entityplayer.ridingEntity;
						if (logic.prefab_vehicle.displayModernHud)
							displayFlyersHUD_AftGen2(logic, logic.prevbodyRot, logic.bodyRot, planebody, logic.forVapour_PrevMotionVec, event);
						else
							displayFlyersHUD(logic, logic.prevbodyRot, logic.bodyRot, planebody, logic.forVapour_PrevMotionVec, event);
//				Quat4d tempquat = new Quat4d(
//						logic.prevbodyRot.x * (1-event.partialTicks) + logic.bodyRot.x * event.partialTicks ,
//						logic.prevbodyRot.y * (1-event.partialTicks) + logic.bodyRot.y * event.partialTicks ,
//						logic.prevbodyRot.z * (1-event.partialTicks) + logic.bodyRot.z * event.partialTicks ,
//						logic.prevbodyRot.w * (1-event.partialTicks) + logic.bodyRot.w * event.partialTicks );
////									if (abs(logic.pitchladder) > 0.001) {
////										Vector3d axisx = transformVecByQuat(new Vector3d(1, 0, 0), tempquat);
////										AxisAngle4d axisxangled = new AxisAngle4d(axisx, toRadians(-logic.pitchladder * renderTickTime / 4));
////										tempquat = quatRotateAxis(tempquat, axisxangled);
////									}
////									if (abs(logic.yawladder) > 0.001) {
////										Vector3d axisy = transformVecByQuat(new Vector3d(0, 1, 0), tempquat);
////										AxisAngle4d axisyangled = new AxisAngle4d(axisy, toRadians(logic.yawladder * renderTickTime / 4));
////										tempquat = quatRotateAxis(tempquat, axisyangled);
////									}
////									if (abs(logic.rollladder) > 0.001) {
////										Vector3d axisz = transformVecByQuat(new Vector3d(0, 0, 1), tempquat);
////										AxisAngle4d axiszangled = new AxisAngle4d(axisz, toRadians(logic.rollladder * renderTickTime / 4));
////										tempquat = quatRotateAxis(tempquat, axiszangled);
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
//				double[] xyz = eulerfromQuat((tempquat));
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
//						logic.posX - logic.prevPosX,
//						logic.posY - logic.prevPosY,
//						-(logic.posZ - logic.prevPosZ));
//
//				Quat4d quat4d = new Quat4d(0,0,0,1);
//				quat4d.inverse(tempquat);
//				forDisplayPlaneMotion = transformVecByQuat(forDisplayPlaneMotion,quat4d);
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

//				fontrenderer.drawStringWithShadow("Missile : " + logic.rocket + " : " + (logic.missile != null ? "Continue radar irradiation" : logic.illuminated != null?"LOCK":""), i - 300, j - 20 - 10, color);
					}
				}
				{
					IVehicle vehicle = (IVehicle) entityplayer.ridingEntity;
					FontRenderer fontrenderer = minecraft.fontRenderer;
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					{
						GL11.glPushMatrix();
						boolean skip = false;
						if (vehicle.getBaseLogic().prefab_vehicle.script_global != null) {
							try {
								skip = (boolean) vehicle.getBaseLogic().prefab_vehicle.script_global.invokeFunction("GUI_rendering_2D", this, vehicle, i, j);
							} catch (NoSuchMethodException | ScriptException e) {
								e.printStackTrace();
							}
						}
						if (!skip) {

							{


								String hp = String.format("%1$3d", (int) logic.health);
								String mhp = String.format("%1$3d", (int) logic.prefab_vehicle.maxhealth);
								String th = String.valueOf(((int) (vehicle.getBaseLogic().throttle * 10f)) / 10f);

								SeatInfo playerSeatInfo = vehicle.getBaseLogic().seatInfos[playerSeatID];
								if (playerSeatInfo.maingun != null) {
									if (playerSeatInfo.currentWeaponMode >= playerSeatInfo.maingun.length)
										playerSeatInfo.currentWeaponMode = 0;
									TurretObj turretObj = playerSeatInfo.maingun[playerSeatInfo.currentWeaponMode];
									turretObj = getActiveTurret(turretObj);
									String name = "MAIN";
									if (playerSeatInfo.maingun.length > 1) {
										name = name.concat("-" + playerSeatInfo.currentWeaponMode);
									}
									if (turretObj != null)
										displayGunState(turretObj, fontrenderer, i, j, 240, 60, name);
								}
								if (playerSeatInfo.subgun != null) {
									TurretObj turretObj = playerSeatInfo.subgun;
									turretObj = getActiveTurret(turretObj);
									if (turretObj != null)
										displayGunState(turretObj, fontrenderer, i, j, 240, 50, "SUB ");
								}


								fontrenderer.drawStringWithShadow("HP " + hp + "/" + mhp + " : throttle " + th, i - 240, j - 90, 0xFFFFFF);
								//fontrenderer.drawStringWithShadow("TH" + th, (i/2) - 80, j/2 + 0, 0xFFFFFF);
								//fontrenderer.drawStringWithShadow("Speed"+ speed, (i/2) - 80, j/2 +20, 0xFFFFFF);
								GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
								int color = 0xFFFFFF;
								fontrenderer.drawStringWithShadow("Speed : " + (int) (-vehicle.getBaseLogic().localMotionVec.z * 72), i - 300, j - 80 - 10, color);
								//g.drawTexturedModelRectFromIcon(i-70, j-63, armor.getIconFromDamage(0), 16, 16);

							}
							GL11.glPopMatrix();

							GuiIngame g = minecraft.ingameGUI;
							GL11.glPushMatrix();//21
							{
								GL11.glEnable(GL11.GL_BLEND);
								GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
								minecraft.renderEngine.bindTexture(new ResourceLocation("handmadevehicle:textures/items/bodyIcon2.png"));
								GL11.glTranslatef(32, scaledresolution.getScaledHeight() - 32, 0F);
								GL11.glRotatef(vehicle.getBaseLogic().bodyrotationYaw - entityplayer.rotationYawHead, 0.0F, 0.0F, 1.0F);
								GL11.glTranslatef(-32, -(scaledresolution.getScaledHeight() - 32), 0F);
								//drawTexturedModalRect(scaledresolution.getScaledWidth()/2 -0,  scaledresolution.getScaledHeight()/2 +24, 0,0, 256, 256);
								GL11.glScalef(0.25f, 0.25f, 1);
								g.drawTexturedModalRect(0, (scaledresolution.getScaledHeight() - 64) * 4, 0, 0, 256, 256);
								GL11.glScalef(4, 4, 1);

								minecraft.renderEngine.bindTexture(new ResourceLocation("handmadevehicle:textures/items/bodyIcon1.png"));
								GL11.glTranslatef(32, scaledresolution.getScaledHeight() - 32, 0F);
								GL11.glTranslatef(-32, -(scaledresolution.getScaledHeight() - 32), 0F);
								GL11.glScalef(0.25f, 0.25f, 1);
								g.drawTexturedModalRect(0, (scaledresolution.getScaledHeight() - 64) * 4, 0, 0, 256, 256);
							}
						}
						GL11.glPopMatrix();
					}

					setUp3DView(minecraft, event.partialTicks);
					GL11.glPushMatrix();
					GL11.glRotatef(0, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
					boolean skip = false;
					if (vehicle.getBaseLogic().prefab_vehicle.script_global != null) {
						try {
							skip = (boolean) vehicle.getBaseLogic().prefab_vehicle.script_global.invokeFunction("GUI_rendering_3D", this, vehicle);
						} catch (NoSuchMethodException | ScriptException e) {
							e.printStackTrace();
						}
					}
					if (!skip) {
						if (!vehicle.getBaseLogic().detectedList.isEmpty()) {
							BaseLogic baseLogic = vehicle.getBaseLogic();
							for (EntityLinkedPos_Motion a_pos_motion : baseLogic.detectedList) {
								Vector3d vecToLockTargetPos = new Vector3d(a_pos_motion.posX - minecraft.renderViewEntity.posX
										, a_pos_motion.posY - minecraft.renderViewEntity.posY - minecraft.renderViewEntity.getEyeHeight()
										, a_pos_motion.posZ - minecraft.renderViewEntity.posZ
								);
								vecToLockTargetPos.normalize();
								RotateVectorAroundY(vecToLockTargetPos, minecraft.renderViewEntity.rotationYawHead);
								RotateVectorAroundX(vecToLockTargetPos, minecraft.renderViewEntity.rotationPitch);
								renderLockOnMarker(minecraft, vehicle.getBaseLogic().prefab_vehicle.searchedMarker, vecToLockTargetPos);
							}
						}

						for (EntityLinkedPos_Motion a_pos_motion : target_Pos_Motion) {
							TurretObj turretObj = getPlayerUsingMainTurret(entityplayer);
							if (turretObj != null && turretObj.gunItem == null) {
								turretObj = getActiveTurret(turretObj);
							}
							if (turretObj == null || turretObj.gunItem == null) {
								turretObj = getPlayerUsingSubTurret(entityplayer);
							}
							if (turretObj != null && turretObj.gunItem == null) {
								turretObj = getActiveTurret(turretObj);
							}
							if (turretObj != null && turretObj.gunItem != null) {
								Vector3d vecToLockTargetPos = new Vector3d(a_pos_motion.posX - minecraft.renderViewEntity.posX
										, a_pos_motion.posY - minecraft.renderViewEntity.posY - minecraft.renderViewEntity.getEyeHeight()
										, a_pos_motion.posZ - minecraft.renderViewEntity.posZ
								);
								vecToLockTargetPos.normalize();
								RotateVectorAroundY(vecToLockTargetPos, minecraft.renderViewEntity.rotationYawHead);
								RotateVectorAroundX(vecToLockTargetPos, minecraft.renderViewEntity.rotationPitch);
								renderLockOnMarker(minecraft, turretObj.gunItem.gunInfo.lockOnMarker, vecToLockTargetPos);

								if (a_pos_motion.displayPredict) {
									if (turretObj.gunItem.gunInfo.displayPredict_MoveSight) {
										Vector3d toTGT = new Vector3d(
												a_pos_motion.posX - minecraft.renderViewEntity.posX,
												a_pos_motion.posY - minecraft.renderViewEntity.posY,
												a_pos_motion.posZ - minecraft.renderViewEntity.posZ);
										Vector3d motionVec = new Vector3d(
												minecraft.renderViewEntity.motionX - a_pos_motion.motionX,
												minecraft.renderViewEntity.motionY - a_pos_motion.motionY,
												minecraft.renderViewEntity.motionZ - a_pos_motion.motionZ);
//									RotateVectorAroundY(motionVec, minecraft.renderViewEntity.rotationYawHead);
										RotateVectorAroundY(motionVec, -toDegrees(atan2(toTGT.x, toTGT.z)));
//									System.out.println("" + motionVec);
//									RotateVectorAroundX(motionVec, minecraft.renderViewEntity.rotationPitch);
										RotateVectorAroundX(motionVec, -toDegrees(atan2(toTGT.y, sqrt(toTGT.x * toTGT.x + toTGT.z * toTGT.z))));
//									System.out.println("" + motionVec);
										Vector3d vecTo_Target = new Vector3d(0, 0, toTGT.length());
										Vector3d PredictedTargetPos =
												LinePrediction(new Vector3d(),
														vecTo_Target,
														motionVec,
														turretObj.getTerminalspeed());

										PredictedTargetPos.normalize();
										renderLockOnMarker(minecraft, turretObj.gunItem.gunInfo.predictMarker, PredictedTargetPos);
									} else {
										Vector3d PredictedTargetPos =
												LinePrediction(new Vector3d(minecraft.renderViewEntity.posX,
																minecraft.renderViewEntity.posY,
																minecraft.renderViewEntity.posZ),
														new Vector3d(a_pos_motion.posX,
																a_pos_motion.posY,
																a_pos_motion.posZ),
														new Vector3d(
																a_pos_motion.motionX - minecraft.renderViewEntity.motionX,
																a_pos_motion.motionY - minecraft.renderViewEntity.motionY,
																a_pos_motion.motionZ - minecraft.renderViewEntity.motionZ),
														turretObj.getTerminalspeed());
										PredictedTargetPos.sub(new Vector3d(
												minecraft.renderViewEntity.posX,
												minecraft.renderViewEntity.posY,
												minecraft.renderViewEntity.posZ));
										RotateVectorAroundY(vecToLockTargetPos, minecraft.renderViewEntity.rotationYawHead);
										RotateVectorAroundX(vecToLockTargetPos, minecraft.renderViewEntity.rotationPitch);
										renderLockOnMarker(minecraft, turretObj.gunItem.gunInfo.predictMarker, PredictedTargetPos);
									}
								}
							}
						}

						if (vehicle.getBaseLogic().prefab_vehicle.sightTex[playerSeatID] != null) {
							if (zooming) {
								renderPumpkinBlur(minecraft, new ResourceLocation(vehicle.getBaseLogic().prefab_vehicle.sightTex[playerSeatID]));
								GuiIngameForge.renderCrosshairs = false;
								NeedReset = true;
							}
						}

					}
					GL11.glPopMatrix();
					setUp2DView(minecraft);
				}
			} else {
				if (NeedReset) GuiIngameForge.renderCrosshairs = true;
				NeedReset = false;
			}
			minecraft.getTextureManager().bindTexture(Gui.icons);
			boolean rc = HMV_Proxy.zoomclick();
			if (rc) zooming = !zooming;
		}
	}
	public void displayGunState(TurretObj turretObj,FontRenderer fontrenderer,int i,int j,int posx,int posy,String name){
		if(turretObj.gunStack != null) {
			if (turretObj.readytoFire()) {
				name = name.concat("  " + turretObj.getName());
				fontrenderer.drawStringWithShadow(name + " Ready remain:" + turretObj.gunItem.remain_Bullet(turretObj.gunStack) + "/" + turretObj.max_Bullet(),
						i - posx, j - posy, 0xFFFFFF);
			} else if (turretObj.isreloading()) {
				fontrenderer.drawStringWithShadow(name + "       Reloading" + turretObj.getDummyStackTag().getInteger("RloadTime") + "/" + turretObj.gunItem.reloadTime(turretObj.gunStack),
						i - posx, j - posy, 0xFFFFFF);
			} else if (turretObj.isLoading()) {
				if (turretObj.prefab_turret.gunInfo.cycle > 5)
					fontrenderer.drawStringWithShadow(name + "         Loading" + turretObj.getDummyStackTag().getByte("Bolt") + "/" + turretObj.prefab_turret.gunInfo.cycle,
							i - posx, j - posy, 0xFFFFFF);
				else
					fontrenderer.drawStringWithShadow(name + " Ready remain:" + turretObj.gunItem.remain_Bullet(turretObj.gunStack) + "/" + turretObj.max_Bullet(),
							i - posx, j - posy, 0xFFFFFF);
			}
		}
	}
	public void displayFlyersHUD(BaseLogic logic,Quat4d prevbodyRot, Quat4d bodyRot, Entity plane, Vector3d prevmotionVec, RenderGameOverlayEvent.Post event){
		GL11.glColor4f(1,1,1,1);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glEnable(GL_BLEND);
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		FontRenderer fontrenderer = minecraft.fontRenderer;
		
		ScaledResolution scaledresolution = new ScaledResolution(minecraft, minecraft.displayWidth,
				                                                        minecraft.displayHeight);
		Quat4d tempquat = new Quat4d(
				                            prevbodyRot.x * (1-event.partialTicks) + bodyRot.x * event.partialTicks ,
				                            prevbodyRot.y * (1-event.partialTicks) + bodyRot.y * event.partialTicks ,
				                            prevbodyRot.z * (1-event.partialTicks) + bodyRot.z * event.partialTicks ,
				                            prevbodyRot.w * (1-event.partialTicks) + bodyRot.w * event.partialTicks );
		double[] xyz = eulerfromQuat((tempquat));
		xyz[0] = toDegrees(xyz[0]);
		xyz[1] = toDegrees(xyz[1]);
		xyz[2] = toDegrees(xyz[2]);
		
		
		GL11.glPushMatrix();
		double width = scaledresolution.getScaledWidth_double();
		double height = scaledresolution.getScaledHeight_double();
		//HUDは300×650
		//一度3.6px
		//横幅を合わせる
		double scale = width/300;
		float sizeW = (float) width;
		float sizeH = (float) height;
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_CULL_FACE);
		minecraft.renderEngine.bindTexture(attitude_indicator_texture);
		GL11.glTranslatef(sizeW - sizeW/6,sizeH - sizeH/6, 0);
		GL11.glScalef(30,-30,30);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GUI_HUD_Cut(1,1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		attitude_indicator.renderPart("obj2");
		GL11.glTranslatef(0,0, -3.0000f);
		GL11.glRotatef((float) xyz[2],0,0,1);
		GL11.glRotatef((float) -xyz[0]/5,1,0,0);
		GL11.glTranslatef(0,0, 3.0000f);
		attitude_indicator.renderPart("obj1");
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glPopMatrix();
	}
	public void GUI_HUD_Cut(float width,float height){
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDepthMask(true);
		GL11.glDepthFunc(GL11.GL_ALWAYS);
		GL11.glColorMask(false,false,false,false);
		GL11.glBegin(GL11.GL_POLYGON);
		GL11.glVertex3d( width + 8	,  height + 8	, 1);
		GL11.glVertex3d(-width - 8	,  height + 8	, 1);
		GL11.glVertex3d(-width		,  height		, 1);
		GL11.glVertex3d( width		,  height		, 1);
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		GL11.glVertex3d(-width - 8	,  -height - 8	, 1);
		GL11.glVertex3d( width + 8	,  -height - 8	, 1);
		GL11.glVertex3d( width		,  -height		, 1);
		GL11.glVertex3d(-width		,  -height		, 1);
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		GL11.glVertex3d( width + 8	,  height + 8	, 1);
		GL11.glVertex3d( width		,  height		, 1);
		GL11.glVertex3d( width		,  -height		, 1);
		GL11.glVertex3d( width + 8	,  -height - 8	, 1);
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		GL11.glVertex3d( -width - 8	,  -height - 8	, 1);
		GL11.glVertex3d( -width		,  -height		, 1);
		GL11.glVertex3d( -width		,  height		, 1);
		GL11.glVertex3d( -width - 8	,  height + 8	, 1);
		GL11.glEnd();
		GL11.glColorMask(true,true,true,true);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
	}

	public void displayFlyersHUD_AftGen2(BaseLogic logic,Quat4d prevbodyRot, Quat4d bodyRot, Entity plane, Vector3d prevmotionVec, RenderGameOverlayEvent.Post event){
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glEnable(GL_BLEND);
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		FontRenderer fontrenderer = minecraft.fontRenderer;

		ScaledResolution scaledresolution = new ScaledResolution(minecraft, minecraft.displayWidth,
				minecraft.displayHeight);
		Quat4d tempquat = new Quat4d(
				prevbodyRot.x * (1-event.partialTicks) + bodyRot.x * event.partialTicks ,
				prevbodyRot.y * (1-event.partialTicks) + bodyRot.y * event.partialTicks ,
				prevbodyRot.z * (1-event.partialTicks) + bodyRot.z * event.partialTicks ,
				prevbodyRot.w * (1-event.partialTicks) + bodyRot.w * event.partialTicks );


		Vector3d forDisplayplaneMotion = logic.localMotionVec;

		GL11.glPushMatrix();
		double width = scaledresolution.getScaledWidth_double();
		double height = scaledresolution.getScaledHeight_double();
		//HUDは300×650
		//一度3.6px
		//横幅を合わせる
		double scale = width/300;
		double sizeW = width;
		double sizeH = sizeW * 650/300;
		double[] xyz = eulerfromQuat((tempquat));
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

		minecraft.renderEngine.bindTexture(new ResourceLocation("handmadevehicle:textures/items/HUD.png"));
//		drawTexturedModalRect(0,height/2 - 325 * scale - (xyz[0] - toDegrees(asin(motionvecE.y))) * 1.8 * scale,sizeW,sizeH);
//		System.out.println(xyz[0]);
		double offset = -xyz[0] % 5;
		int currentLineNum = (int) ((xyz[0])/5);
//		if(offset<0)offset+=5;
//		if(xyz[0]>0)currentLineNum +=1;
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
		int num = -3;
		if(num + currentLineNum < -18){
			num = currentLineNum - 18;
		}
		for(;num + currentLineNum < 18 && num < 4;num ++){
			double lineupperPosY = height / 2 + offset * 3.6 * scale + num * 5 * 3.6 * scale;
			double lineunderPosY = height / 2 + offset * 3.6 * scale + num * 5 * 3.6 * scale + lineWidh * scale;
			drawHudLine(
					sizeW/2 + midBlankLength/2,
					lineupperPosY,
					sizeW/2 + lineLength/2,
					lineunderPosY);
			double lineunderPosY2 = lineupperPosY - (currentLineNum + num) * lineLength2 * scale;
			drawHudLine(
					sizeW/2 + lineLength/2,
					lineupperPosY,
					sizeW/2 + lineLength/2 - lineWidh * scale,
					lineunderPosY2);
			
			
			drawHudLine(
					sizeW/2 - lineLength/2,
					lineupperPosY,
					sizeW/2 - midBlankLength/2,
					lineunderPosY);
			drawHudLine(
					sizeW/2 - lineLength/2,
					lineupperPosY,
					sizeW/2 - lineLength/2 + lineWidh * scale,
					lineunderPosY2);

			double numberPosY = height / 2 + offset * 3.6 * scale + num * 5 * 3.6 * scale + (num + currentLineNum) * scale / 2;
			fontrenderer.drawString(String.valueOf((num + currentLineNum) * 5),
					(int)(sizeW/2 + lineLength + lineWidh * scale/2),
					(int) numberPosY,
					0xFAFF9E);
			
//			drawHudLine(sizeW/2 - lineLength + lineWidh * scale/2,
//					height/2 + offset * 3.6 * scale + num * 5 * 3.6 * scale + (num + currentLineNum) * scale/2,
//					lineWidh * scale,
//					(num + currentLineNum) * scale * lineLength2);
			
			fontrenderer.drawString(String.valueOf((num + currentLineNum) * 5),
					(int)(sizeW/2 - lineLength + lineWidh * scale/2),
					(int) numberPosY,
					0xFAFF9E);
		}
//		drawTexturedModalRect(0,height/2 - 325 * scale - xyz[0] * 3.6 * scale,sizeW,sizeH);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		scale = width/300;
		sizeW = width;
		sizeH = width;
		minecraft.renderEngine.bindTexture(new ResourceLocation("handmadevehicle:textures/items/HUD2.png"));

		drawTexturedModalRect(0,height/2 - 150 * scale,sizeW,sizeH);
		GL11.glPopMatrix();


		GL11.glPushMatrix();
		scale = width/300;
		sizeW = width;
		sizeH = width;
		
		GL11.glTranslatef((float)(-forDisplayplaneMotion.x * 300 * scale), (float)(-forDisplayplaneMotion.y * 300 * scale),0);

		minecraft.renderEngine.bindTexture(new ResourceLocation("handmadevehicle:textures/items/HUD3.png"));
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
	private TurretObj getPlayerUsingMainTurret(EntityPlayer entityplayer){
		SeatInfo playerSeatInfo = ((IVehicle) entityplayer.ridingEntity).getBaseLogic().seatInfos[playerSeatID];
		TurretObj turretObj = null;
		if (playerSeatInfo.maingun != null) {
			turretObj = playerSeatInfo.maingun[playerSeatInfo.currentWeaponMode];
			turretObj = getActiveTurret(turretObj);
		}
		return turretObj;
	}

	private TurretObj getPlayerUsingSubTurret(EntityPlayer entityplayer){
		SeatInfo playerSeatInfo = ((IVehicle) entityplayer.ridingEntity).getBaseLogic().seatInfos[playerSeatID];
		TurretObj turretObj = null;
		if (playerSeatInfo.subgun != null) {
			turretObj = playerSeatInfo.subgun;
			turretObj = getActiveTurret(turretObj);
		}
		return turretObj;
	}

	private static TurretObj getActiveTurret(TurretObj turretObj){
		if(turretObj.gunStack != null){
			return turretObj;
		}else{
			TurretObj temp;
			if(!turretObj.getChilds().isEmpty())for(TurretObj child:turretObj.getChilds()){
				temp = getActiveTurret(child);
				if(temp != null)
				return temp;
			}
			if(!turretObj.getChildsOnBarrel().isEmpty())for(TurretObj child:turretObj.getChildsOnBarrel()){
				temp = getActiveTurret(child);
				if(temp != null)
					return temp;
			}
		}
		return null;
	}
	private static TurretObj getActiveTurret2(TurretObj turretObj){

		GunInfo currentGunInfo = turretObj.getCurrentGuninfo();
		if(currentGunInfo != null){
//			System.out.println(turretObj.turretID_OnVehicle);
			return turretObj;
		}else{
			TurretObj temp;
			if(!turretObj.getChilds().isEmpty())for(TurretObj child:turretObj.getChilds()){
				temp = getActiveTurret(child);
				if(temp != null)
				return temp;
			}
			if(!turretObj.getChildsOnBarrel().isEmpty())for(TurretObj child:turretObj.getChildsOnBarrel()){
				temp = getActiveTurret(child);
				if(temp != null)
					return temp;
			}
		}
		return null;
	}

	public void rotationToVehicleNose(IVehicle vehicle){
		EntityLivingBase renderViewEntity = FMLClientHandler.instance().getClient().renderViewEntity;
		BaseLogic vehicleBaseLogic = vehicle.getBaseLogic();
		Vector3f next_ypr = new Vector3f(vehicleBaseLogic.bodyrotationYaw,vehicleBaseLogic.bodyrotationPitch,vehicleBaseLogic.bodyrotationRoll);
		Vector3f prev_ypr = new Vector3f(vehicleBaseLogic.prevbodyrotationYaw,vehicleBaseLogic.prevbodyrotationPitch,vehicleBaseLogic.prevbodyrotationRoll);
		Vector3f current_ypr = new Vector3f();
		current_ypr.interpolate(next_ypr,prev_ypr, RenderTickSmoothing.smooth);

		float roll = ObfuscationReflectionHelper.getPrivateValue(EntityRenderer.class, FMLClientHandler.instance().getClient().entityRenderer, "camRoll", "R", "field_78495_O");
		GL11.glRotatef(-roll,0,0,1);
		GL11.glRotatef(-renderViewEntity.rotationPitch,1,0,0);
		GL11.glRotatef(renderViewEntity.rotationYawHead,0,1,0);
		GL11.glRotatef(-current_ypr.x,0,1,0);
		GL11.glRotatef(current_ypr.y,1,0,0);
		GL11.glRotatef(current_ypr.z,0,0,1);
	}

	public int renderFont(String string,int color,boolean dropShadow){
		Minecraft minecraft = FMLClientHandler.instance().getClient();
//		0xFFFFFF
		return minecraft.fontRenderer.drawString(string, 0, 0, color,dropShadow);
	}

	public int renderFont_getWidth(String string){
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		return minecraft.fontRenderer.getStringWidth(string);
	}
	public Minecraft getMinecraft(){
		return FMLClientHandler.instance().getClient();
	}
}
