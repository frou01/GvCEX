package handmadevehicle;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import handmadeguns.client.render.ModelSetAndData;
import handmadevehicle.audio.TurretSound;
import handmadevehicle.audio.VehicleEngineSound;
import handmadevehicle.audio.VehicleNoRepeatSound;
import handmadevehicle.entity.EntityVehicle;
import handmadevehicle.entity.parts.HasLoopSound;
import handmadevehicle.entity.parts.ModifiedBoundingBox;
import handmadevehicle.entity.parts.OBB;
import handmadevehicle.entity.parts.turrets.TurretObj;
import handmadevehicle.events.HMVRenderSomeEvent;
import handmadevehicle.render.RenderVehicle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import static handmadevehicle.HMVehicle.*;
import static java.lang.Math.toDegrees;
import static org.lwjgl.input.Keyboard.isKeyDown;

public class CLProxy extends CMProxy {
	public static final KeyBinding RButton = new KeyBinding("Fire1", Keyboard.KEY_NONE, "HMVehicle");
	public static final KeyBinding LButton = new KeyBinding("Fire2", Keyboard.KEY_NONE, "HMVehicle");
	public static final KeyBinding Throttle_up = new KeyBinding("throttle UP", Keyboard.KEY_NONE, "HMVehicle");
	public static final KeyBinding Throttle_down = new KeyBinding("throttle Down", Keyboard.KEY_NONE, "HMVehicle");
	public static final KeyBinding Yaw_Left = new KeyBinding("Yaw Left", Keyboard.KEY_NONE, "HMVehicle");
	public static final KeyBinding Yaw_Right = new KeyBinding("Yaw Right", Keyboard.KEY_NONE, "HMVehicle");
	public static final KeyBinding Throttle_Brake = new KeyBinding("Throttle Brake", Keyboard.KEY_NONE, "HMVehicle");


	public static final KeyBinding Zoom = new KeyBinding("CannonCamera", Keyboard.KEY_Z, "HMVehicle");
	
	public static final KeyBinding_withStopper Flap = new KeyBinding_withStopper("Flap", Keyboard.KEY_F, "HMVehicle");
	public static final KeyBinding Air_Brake = new KeyBinding("Air Brake", Keyboard.KEY_X, "HMVehicle");
	public static final KeyBinding Flare_Smoke = new KeyBinding("Flare/Smoke", Keyboard.KEY_COLON, "HMVehicle");
	public static final KeyBinding_withStopper Gear_Down_Up = new KeyBinding_withStopper("Gear Down/Up", Keyboard.KEY_G, "HMVehicle");
	public static final KeyBinding_withStopper Weapon_Mode = new KeyBinding_withStopper("Weapon Mode", Keyboard.KEY_ADD, "HMVehicle");
	public static final KeyBinding_withStopper Allow_Entity_Ride = new KeyBinding_withStopper("Allow Entity to Ride", Keyboard.KEY_LMENU, "HMVehicle");


	public static final KeyBinding_withStopper Next_Seat = new KeyBinding_withStopper("Change to Next Seat", Keyboard.KEY_Y, "HMVehicle");
	public static final KeyBinding_withStopper Previous_Seat = new KeyBinding_withStopper("Change to Previous Seat", Keyboard.KEY_H, "HMVehicle");
	public static final KeyBinding_withStopper ChangeControl = new KeyBinding_withStopper("Change Control", Keyboard.KEY_N, "HMVehicle");
	public static final KeyBinding_withStopper ChangeEasyControl = new KeyBinding_withStopper("Change to Easy/Normal Control", Keyboard.KEY_NONE, "HMVehicle");
	public static final KeyBinding_withStopper resetCamrot = new KeyBinding_withStopper("Reset Camera Rotation", Keyboard.KEY_V, "HMVehicle");
	public static final KeyBinding_withStopper reloadConfig = new KeyBinding_withStopper("Reload Config Settings", Keyboard.KEY_NONE, "HMVehicle");
	public static final KeyBinding_withStopper openGUI = new KeyBinding_withStopper("Open Vehicle Gui", Keyboard.KEY_COLON, "HMVehicle");
	
	public static final KeyBinding pitchUp = new KeyBinding("Pitch Up", Keyboard.KEY_I, "HMVehicle");
	public static final KeyBinding pitchDown = new KeyBinding("Pitch Down", Keyboard.KEY_K, "HMVehicle");
	public static final KeyBinding RollRight = new KeyBinding("Roll Right", Keyboard.KEY_L, "HMVehicle");
	public static final KeyBinding RollLeft = new KeyBinding("Roll Left", Keyboard.KEY_J, "HMVehicle");
	public static boolean zooming;
	static boolean zoomkey_stopper;
	static boolean fkey_stopper;
	static boolean reload_stopper;

	static boolean inited = false;

	static int currentStickControllerID;

	public CLProxy() {
		if(!inited) {
			net.minecraftforge.client.ClientCommandHandler.instance.registerCommand(hmv_commandReloadparm);
			ClientRegistry.registerKeyBinding(Throttle_up);
			ClientRegistry.registerKeyBinding(Throttle_down);
			ClientRegistry.registerKeyBinding(Yaw_Left);
			ClientRegistry.registerKeyBinding(Yaw_Right);
			ClientRegistry.registerKeyBinding(Throttle_Brake);
			ClientRegistry.registerKeyBinding(RButton);
			ClientRegistry.registerKeyBinding(LButton);
			ClientRegistry.registerKeyBinding(Zoom);
			ClientRegistry.registerKeyBinding(Flap.keyBinding);
			ClientRegistry.registerKeyBinding(Air_Brake);
			ClientRegistry.registerKeyBinding(Flare_Smoke);
			ClientRegistry.registerKeyBinding(Next_Seat.keyBinding);
			ClientRegistry.registerKeyBinding(Previous_Seat.keyBinding);
			ClientRegistry.registerKeyBinding(pitchUp);
			ClientRegistry.registerKeyBinding(pitchDown);
			ClientRegistry.registerKeyBinding(RollRight);
			ClientRegistry.registerKeyBinding(RollLeft);
			ClientRegistry.registerKeyBinding(Weapon_Mode.keyBinding);
			ClientRegistry.registerKeyBinding(Allow_Entity_Ride.keyBinding);
			ClientRegistry.registerKeyBinding(ChangeControl.keyBinding);
			ClientRegistry.registerKeyBinding(ChangeEasyControl.keyBinding);
			ClientRegistry.registerKeyBinding(resetCamrot.keyBinding);
			ClientRegistry.registerKeyBinding(reloadConfig.keyBinding);
			ClientRegistry.registerKeyBinding(openGUI.keyBinding);
			ClientRegistry.registerKeyBinding(Gear_Down_Up.keyBinding);
			inited = true;
			
			RenderingRegistry.registerEntityRenderingHandler(EntityVehicle.class,new RenderVehicle());
		}
		try {
			Controllers.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public ModelSetAndData loadResource_model(String resourceName_model,String resourceName_Texture,float scale){
		return new ModelSetAndData(AdvancedModelLoader.loadModel(new ResourceLocation("handmadevehicle:textures/model/" + resourceName_model)),new ResourceLocation("handmadevehicle:textures/model/" + resourceName_Texture),scale);
	}
	
	@Override
	public void playsoundasVehicle(float maxdist, Entity attached){
		Minecraft.getMinecraft().getSoundHandler().playSound(new VehicleEngineSound(attached,maxdist));
	}
	@Override
	public void playsoundasTurret(float maxdist, TurretObj attached){
		Minecraft.getMinecraft().getSoundHandler().playSound(new TurretSound(attached,maxdist));
	}
	@Override
	public void playsoundasVehicle_noRepeat(String name , float maxdist, Entity attached, HasLoopSound hasLoopSound,int time){
		Minecraft.getMinecraft().getSoundHandler().playSound(new VehicleNoRepeatSound(name,attached,hasLoopSound,maxdist,time));
	}
	public boolean hasStick(){
		return Controllers.getControllerCount() > 0;
	}
	
	public float getXaxis(){
		if(Controllers.getControllerCount() > 0){
			Controller stick = Controllers.getController(currentStickControllerID);
			if(stick != null && stick.getAxisCount() > cfgControl_axisXID)return stick.getAxisValue(cfgControl_axisXID);
			else currentStickControllerID++;
		}
		return 0;
	}
	public float getYaxis(){
		if(Controllers.getControllerCount() > 0){
			Controller stick = Controllers.getController(currentStickControllerID);
			if(stick != null && stick.getAxisCount() > cfgControl_axisYID)return stick.getAxisValue(cfgControl_axisYID);
			else currentStickControllerID++;
		}
		return 0;
	}
	public float getZaxis(){
		if(Controllers.getControllerCount() > 0){
			Controller stick = Controllers.getController(currentStickControllerID);
			if(stick != null && stick.getAxisCount() > cfgControl_axisZID)return stick.getAxisValue(cfgControl_axisZID);
			else currentStickControllerID++;
		}
		return 0;
	}
	public float getZaxis2(){
		if(Controllers.getControllerCount() > 0){
			Controller stick = Controllers.getController(currentStickControllerID);
			if(stick != null && stick.getAxisCount() > cfgControl_axisZ2ID)return stick.getAxisValue(cfgControl_axisZ2ID);
			else currentStickControllerID++;
		}
		return 0;
	}
	public boolean pitchUp(){
		return pitchUp.getIsKeyPressed();
	}
	public boolean pitchDown() {
		return pitchDown.getIsKeyPressed();
	}
	public boolean rollRight(){
		return RollRight.getIsKeyPressed();
	}
	public boolean rollLeft(){
		return RollLeft.getIsKeyPressed();
	}
	
	@Override
	public boolean reload(){
		//return Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown();
		return isKeyDown(Keyboard.KEY_R);
		//return false;
	}
	
	@Override
	public boolean reload_Semi(){
		
		boolean flag = isKeyDown(Keyboard.KEY_R);
		if(flag){
			if(!reload_stopper) {
				reload_stopper = true;
			}else {
				flag = false;
			}
		}else {
			reload_stopper = false;
		}
		return flag;
	}
	@Override
	public boolean throttle_BrakeKeyDown(){
		if(Keyboard.KEY_NONE == Throttle_Brake.getKeyCode()){
			return isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode());
		}
		return isKeyDown(Throttle_Brake.getKeyCode());
		//return false;
	}
	
	@Override
	public boolean leftclick(){
		if(Keyboard.KEY_NONE == LButton.getKeyCode()){
			return Minecraft.getMinecraft().gameSettings.keyBindAttack.getIsKeyPressed();
		}
		return isKeyDown(LButton.getKeyCode());
		//return false;
	}
	@Override
	public boolean rightclick(){
		if(Keyboard.KEY_NONE == RButton.getKeyCode()){
			return Minecraft.getMinecraft().gameSettings.keyBindUseItem.getIsKeyPressed();
		}
		return isKeyDown(RButton.getKeyCode());
		//return false;
	}
	@Override
	public boolean throttle_up_click(){
		if(Keyboard.KEY_NONE == Throttle_up.getKeyCode()){
			return Minecraft.getMinecraft().gameSettings.keyBindForward.getIsKeyPressed();
		}
		return isKeyDown(Throttle_up.getKeyCode());
		//return false;
	}
	@Override
	public boolean yaw_Left_click(){
		if(Keyboard.KEY_NONE == Yaw_Left.getKeyCode()){
			return Minecraft.getMinecraft().gameSettings.keyBindLeft.getIsKeyPressed();
		}
		return isKeyDown(Yaw_Left.getKeyCode());
		//return false;
	}
	@Override
	public boolean throttle_down_click(){
		if(Keyboard.KEY_NONE == Throttle_down.getKeyCode()){
			return Minecraft.getMinecraft().gameSettings.keyBindBack.getIsKeyPressed();
		}
		return isKeyDown(Throttle_down.getKeyCode());
		//return false;
	}
	@Override
	public boolean yaw_Right_click(){
		if(Keyboard.KEY_NONE == Yaw_Right.getKeyCode()){
			return Minecraft.getMinecraft().gameSettings.keyBindRight.getIsKeyPressed();
		}
		return isKeyDown(Yaw_Right.getKeyCode());
		//return false;
	}
	@Override
	public boolean zoomclick(){
		boolean flag = isKeyDown(Zoom.getKeyCode());
		if(flag){
			if(!zoomkey_stopper) {
				zoomkey_stopper = true;
			}else {
				flag = false;
			}
		}else {
			zoomkey_stopper = false;
		}
		return flag;
		//return false;
	}
	@Override
	public boolean flap_click(){
		return isKeyDown(Flap.keyBinding.getKeyCode());
		//return false;
	}
	@Override
	public boolean air_Brake_click(){
		return isKeyDown(Air_Brake.getKeyCode());
		//return false;
	}
	@Override
	public boolean flare_Smoke_click(){
		return isKeyDown(Flare_Smoke.getKeyCode());
	}
	@Override
	public boolean gear_Down_Up_click(){
		return Gear_Down_Up.isKeyDown();
	}
	@Override
	public boolean weapon_Mode_click() {
		return Weapon_Mode.isKeyDown();
	}
	@Override
	public boolean allow_Entity_Ride_click(){
		return Allow_Entity_Ride.isKeyDown();
	}

	@Override
	public boolean next_Seatclick(){
		return Next_Seat.isKeyDown();
	}
	@Override
	public boolean previous_Seatclick(){
		return Previous_Seat.isKeyDown();
	}
	@Override
	public boolean changeControlclick(){
		return ChangeControl.isKeyDown();
	}
	@Override
	public boolean changeEasyControlMode(){
		return ChangeEasyControl.isKeyDown();
	}
	@Override
	public boolean resetCamrotclick(){
		return resetCamrot.isKeyDown();
	}
	@Override
	public boolean reloadConfigclick(){
		return reloadConfig.isKeyDown();
	}
	@Override
	public boolean openGUIKeyDown(){
		return openGUI.isKeyDown();
	}
	
	public boolean iszooming(){
		return zooming;
	}
	
	
	public EntityPlayer getEntityPlayerInstance() {
		return Minecraft.getMinecraft().thePlayer;
	}
	
	
	public static void drawOutlinedBoundingBox(ModifiedBoundingBox p_147590_0_, int p_147590_1_)
	{
		
		GL11.glPushMatrix();
		for(OBB aobb : p_147590_0_.boxes){
			if(aobb != null)drawOutlinedOBB(aobb,p_147590_1_);
		}
		GL11.glPopMatrix();
	}
	
	public void setPlayerSeatID(int id){
		HMVRenderSomeEvent.playerSeatID = id;
	}
	
	public static void drawOutlinedOBB(OBB p_147590_0_, int p_147590_1_)
	{
		GL11.glPushMatrix();

		{
			double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(p_147590_0_.turretRotation));
			xyz[0] = toDegrees(xyz[0]);
			xyz[1] = toDegrees(xyz[1]);
			xyz[2] = toDegrees(xyz[2]);
			GL11.glTranslatef((float) p_147590_0_.turretRotCenter.x, (float) p_147590_0_.turretRotCenter.y, (float) -p_147590_0_.turretRotCenter.z);

			GL11.glRotatef(-(float) xyz[1], 0.0F, 1.0F, 0.0F);
			GL11.glRotatef((float) xyz[0], 1.0F, 0.0F, 0.0F);
			GL11.glRotatef((float) xyz[2], 0.0F, 0.0F, 1.0F);

			GL11.glTranslatef(-(float) p_147590_0_.turretRotCenter.x, -(float) p_147590_0_.turretRotCenter.y, (float) p_147590_0_.turretRotCenter.z);
		}

		{
			double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(p_147590_0_.info.boxRotation));
			xyz[0] = toDegrees(xyz[0]);
			xyz[1] = toDegrees(xyz[1]);
			xyz[2] = toDegrees(xyz[2]);
			GL11.glTranslatef((float) p_147590_0_.info.boxRotCenter.x, (float) p_147590_0_.info.boxRotCenter.y, (float) -p_147590_0_.info.boxRotCenter.z);

			GL11.glRotatef(-(float) xyz[1], 0.0F, 1.0F, 0.0F);
			GL11.glRotatef((float) xyz[0], 1.0F, 0.0F, 0.0F);
			GL11.glRotatef((float) xyz[2], 0.0F, 0.0F, 1.0F);

			GL11.glTranslatef(-(float) p_147590_0_.info.boxRotCenter.x, -(float) p_147590_0_.info.boxRotCenter.y, (float) p_147590_0_.info.boxRotCenter.z);
		}

		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawing(3);
		
		if (p_147590_1_ != -1)
		{
			tessellator.setColorOpaque_I(p_147590_1_);
		}
		
		tessellator.addVertex(p_147590_0_.minvertex.x, p_147590_0_.minvertex.y, -p_147590_0_.minvertex.z);
		tessellator.addVertex(p_147590_0_.maxvertex.x, p_147590_0_.minvertex.y, -p_147590_0_.minvertex.z);
		tessellator.addVertex(p_147590_0_.maxvertex.x, p_147590_0_.minvertex.y, -p_147590_0_.maxvertex.z);
		tessellator.addVertex(p_147590_0_.minvertex.x, p_147590_0_.minvertex.y, -p_147590_0_.maxvertex.z);
		tessellator.addVertex(p_147590_0_.minvertex.x, p_147590_0_.minvertex.y, -p_147590_0_.minvertex.z);
		tessellator.draw();
		tessellator.startDrawing(3);
		
		if (p_147590_1_ != -1)
		{
			tessellator.setColorOpaque_I(p_147590_1_);
		}
		
		tessellator.addVertex(p_147590_0_.minvertex.x, p_147590_0_.maxvertex.y, -p_147590_0_.minvertex.z);
		tessellator.addVertex(p_147590_0_.maxvertex.x, p_147590_0_.maxvertex.y, -p_147590_0_.minvertex.z);
		tessellator.addVertex(p_147590_0_.maxvertex.x, p_147590_0_.maxvertex.y, -p_147590_0_.maxvertex.z);
		tessellator.addVertex(p_147590_0_.minvertex.x, p_147590_0_.maxvertex.y, -p_147590_0_.maxvertex.z);
		tessellator.addVertex(p_147590_0_.minvertex.x, p_147590_0_.maxvertex.y, -p_147590_0_.minvertex.z);
		tessellator.draw();
		tessellator.startDrawing(1);
		
		if (p_147590_1_ != -1)
		{
			tessellator.setColorOpaque_I(p_147590_1_);
		}
		
		tessellator.addVertex(p_147590_0_.minvertex.x, p_147590_0_.minvertex.y, -p_147590_0_.minvertex.z);
		tessellator.addVertex(p_147590_0_.minvertex.x, p_147590_0_.maxvertex.y, -p_147590_0_.minvertex.z);
		tessellator.addVertex(p_147590_0_.maxvertex.x, p_147590_0_.minvertex.y, -p_147590_0_.minvertex.z);
		tessellator.addVertex(p_147590_0_.maxvertex.x, p_147590_0_.maxvertex.y, -p_147590_0_.minvertex.z);
		tessellator.addVertex(p_147590_0_.maxvertex.x, p_147590_0_.minvertex.y, -p_147590_0_.maxvertex.z);
		tessellator.addVertex(p_147590_0_.maxvertex.x, p_147590_0_.maxvertex.y, -p_147590_0_.maxvertex.z);
		tessellator.addVertex(p_147590_0_.minvertex.x, p_147590_0_.minvertex.y, -p_147590_0_.maxvertex.z);
		tessellator.addVertex(p_147590_0_.minvertex.x, p_147590_0_.maxvertex.y, -p_147590_0_.maxvertex.z);
		tessellator.draw();
		GL11.glPopMatrix();
	}
	public boolean isSneaking(){
		return getEntityPlayerInstance() != null && ((EntityPlayerSP) getEntityPlayerInstance()).movementInput.sneak;
	}
}
