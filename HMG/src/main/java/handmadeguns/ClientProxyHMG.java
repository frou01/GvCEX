package handmadeguns;


import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import cpw.mods.fml.relauncher.ReflectionHelper;
import handmadeguns.client.audio.BulletSoundHMG;
import handmadeguns.client.audio.GunSoundHMG;
import handmadeguns.client.audio.MovingSoundHMG;
import handmadeguns.client.audio.ReloadSoundHMG;
import handmadeguns.emb_modelloader.MQO_ModelLoader;
import handmadeguns.entity.*;
import handmadeguns.entity.bullets.*;
import handmadeguns.event.RenderTickSmoothing;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadeguns.network.PacketSpawnParticle;
import handmadeguns.client.render.*;
import handmadeguns.obj_modelloaderMod.obj.HMGObjModelLoader;
import handmadeguns.tcn_modelloaderMod.TechneModelLoader;
import handmadeguns.tile.TileMounter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.AdvancedModelLoader;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

import org.lwjgl.input.Mouse;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;

public class ClientProxyHMG extends CommonSideProxyHMG {
	public static final KeyBinding				Reload				= new KeyBinding("Reload Magazine", Keyboard.KEY_R, "HandmadeGuns");
	public static final KeyBinding_withStopper	Fire_AttachedGun	= new KeyBinding_withStopper("Fire AttachedGun", Keyboard.KEY_F, "HandmadeGuns");
	public static final KeyBinding				ADS					= new KeyBinding("ADS_Key", Keyboard.KEY_V, "HandmadeGuns");

	public static final KeyBinding				gunPrepare_modification = new KeyBinding("Gun Prepare Modification Key", Keyboard.KEY_LMENU, "HandmadeGuns");
	public static final KeyBinding				Attachment			= new KeyBinding("[Gun Prepare]Attachment GUI", Keyboard.KEY_X, "HandmadeGuns");
	public static final KeyBinding_withStopper	ChangeMagazineType	= new KeyBinding_withStopper("[Gun Prepare]Change Magazine Type", Keyboard.KEY_B, "HandmadeGuns");
	public static final KeyBinding_withStopper	Fix					= new KeyBinding_withStopper("[Gun Prepare]Fix Gun", Keyboard.KEY_H, "HandmadeGuns");

	public static final KeyBinding				gunSetting_modification = new KeyBinding("Gun Setting Modification", Keyboard.KEY_NONE, "HandmadeGuns");
	public static final KeyBinding_withStopper	El_Up				= new KeyBinding_withStopper("[Gun Setting]Zero in : increase", Keyboard.KEY_Y, "HandmadeGuns");
	public static final KeyBinding_withStopper	El_Reset			= new KeyBinding_withStopper("[Gun Setting]Zero in : reset", Keyboard.KEY_H, "HandmadeGuns");
	public static final KeyBinding_withStopper	El_Down				= new KeyBinding_withStopper("[Gun Setting]Zero in : decrease", Keyboard.KEY_N, "HandmadeGuns");
	public static final KeyBinding_withStopper	SeekerOpen_Close	= new KeyBinding_withStopper("[Gun Setting]Seeker Open/Close", Keyboard.KEY_C, "HandmadeGuns");
	public static final KeyBinding_withStopper	Mode				=  new KeyBinding_withStopper("[Gun Setting]Cycle Selector", Keyboard.KEY_GRAVE, "HandmadeGuns");


	private static final String trailtexture = ("handmadeguns:textures/entity/trail");
	private static final String lockonmarker = ("handmadeguns:textures/items/lockonmarker");
	static boolean stopper_modekey = false;
	static boolean stopper_Fkey = false;
	static boolean stopper_ChangeMagazineTypekey = false;
	static boolean stopper_Lightkey = false;
	static boolean stopper_Fixkey = false;
	static Field equippedProgress;
	static Field prevEquippedProgress;
	static Field itemToRender;
	static Field rightClickDelayTimer = null;

	static Field fovModifierHandPrev;
	static Field fovModifierHand;
	static Field prevDebugCamFOV;
	static Field debugCamFOV;

	static int beforeSlot = -1;
//	public static final KeyBinding Fire2 = new KeyBinding("ADS_Key",-100 , "HandmadeGuns");
	//public static final KeyBinding Jump = new KeyBinding("Jump", Keyboard.KEY_X, "HandmadeGuns");

	//public static ModelBiped PlayerRender = new HMGPlayer();

	@Override
	public File ProxyFile(){
		return Minecraft.getMinecraft().mcDataDir;
	}

	@Override
	public void setuprender(){

		try {
			System.setProperty("forge.forceDisplayStencil", "true");
			Field stencilBits_F = ReflectionHelper.findField(ForgeHooksClient.class, "stencilBits");
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(stencilBits_F,
					stencilBits_F.getModifiers() & ~Modifier.PRIVATE); // 更新対象アクセス用のFieldオブジェクトのmodifiersからprivateとfinalを外す。
			stencilBits_F.set(null, 8);
			System.out.println("Debug stencil is    " + MinecraftForgeClient.getStencilBits());
			System.out.println("Debug stencil state " + Boolean.parseBoolean(System.getProperty("forge.forceDisplayStencil", "false")));
//			net.minecraftforge.client.ForgeHooksClient.createDisplay();

//			OpenGlHelper.initializeTextures();

//			Field framebufferMc_F = ReflectionHelper.findField(Minecraft.class, "framebufferMc");
//			framebufferMc_F.set(proxy.getMCInstance(),new Framebuffer(proxy.getMCInstance().displayWidth, proxy.getMCInstance().displayHeight, true));
//			proxy.getMCInstance().getFramebuffer().setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
		}catch (Exception e){
			e.printStackTrace();
		}


		AdvancedModelLoader.registerModelHandler(new MQO_ModelLoader());
		AdvancedModelLoader.registerModelHandler(new TechneModelLoader());
		AdvancedModelLoader.registerModelHandler(new HMGObjModelLoader());//怒りのオーバーライド
	}
	@Override
	public void playsoundat(String sound, float soundLV, float soundSP, float tempsp, double posX, double posY, double posZ){
		Minecraft.getMinecraft().getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(sound),soundLV,soundSP*tempsp,(float)posX,(float)posY,(float)posZ));
	}
	@Override
	public void playsound_Gun(String sound, float soundLV, float soundSP,float maxdist,Entity attached,
	                          double posX,
	                          double posY,
	                          double posZ){
		Minecraft.getMinecraft().getSoundHandler().playSound(new GunSoundHMG(attached,sound,soundLV,soundSP,maxdist,
				posX,
				posY,
				posZ));
	}
	@Override
	public void playsoundatEntity(String sound, float soundLV, float soundSP,Entity attached,boolean repeat,int time){
		Minecraft.getMinecraft().getSoundHandler().playSound(new MovingSoundHMG(attached,sound,repeat,soundLV,soundSP,time));
	}
	@Override
	public void playsoundatEntity_reload(String sound, float soundLV, float soundSP, Entity attached, boolean repeat){
		Minecraft.getMinecraft().getSoundHandler().playSound(new ReloadSoundHMG(attached,sound,repeat,soundLV,soundSP));
	}
	@Override
	public void playsoundatBullet(String sound, float soundLV, float soundSP,float mindspeed,float maxdist,Entity attached,boolean repeat){
		Minecraft.getMinecraft().getSoundHandler().playSound(new BulletSoundHMG(attached,sound,repeat,soundLV,soundSP,mindspeed,maxdist));
	}
	@Override
	public EntityPlayer getEntityPlayerInstance() {
	        return Minecraft.getMinecraft().thePlayer;
	    }
	@Override
	public Minecraft getMCInstance() {
		return Minecraft.getMinecraft();
	}
	@Override
	public void playGUISound(String sound,float speed){
		getMCInstance().getSoundHandler().playSound(PositionedSoundRecord.func_147673_a(new ResourceLocation(sound)));
	}
    @Override
	public World getCilentWorld(){
		return FMLClientHandler.instance().getClient().theWorld;
		}

    @Override
    public void registerClientInfo() {
        //ClientRegistry.registerKeyBinding(Speedreload);
    }

    @Override
	public void reisterSomething(){
    	Minecraft mc = FMLClientHandler.instance().getClient();
    	//RenderManager rendermanager = new RenderManager(mc.renderEngine, mc.getRenderItem());
    	//RenderItem renderitem = mc.getRenderItem();

    	ClientRegistry.registerKeyBinding(Reload);
	    ClientRegistry.registerKeyBinding(Fire_AttachedGun.keyBinding);
    	ClientRegistry.registerKeyBinding(ADS);
    	ClientRegistry.registerKeyBinding(gunPrepare_modification);
    	ClientRegistry.registerKeyBinding(Attachment);
    	ClientRegistry.registerKeyBinding(ChangeMagazineType.keyBinding);
		ClientRegistry.registerKeyBinding(Fix.keyBinding);
		ClientRegistry.registerKeyBinding(gunSetting_modification);
		ClientRegistry.registerKeyBinding(El_Up.keyBinding);
		ClientRegistry.registerKeyBinding(El_Reset.keyBinding);
		ClientRegistry.registerKeyBinding(El_Down.keyBinding);
		ClientRegistry.registerKeyBinding(SeekerOpen_Close.keyBinding);
		ClientRegistry.registerKeyBinding(Mode.keyBinding);
    	MinecraftForge.EVENT_BUS.register(new HMGParticles());

    	try {
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityItemMount.class, new HMGRenderItemMount());
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityItemMount2.class, new HMGRenderItemMount2());

			RenderingRegistry.registerEntityRenderingHandler(HMGEntityBullet.class, new HMGRenderBullet());
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityFallingBlockModified.class, new RenderFallingBlockMod());
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityBulletRocket.class, new HMGRenderBulletExplode());
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityBulletExprode.class, new HMGRenderBulletExplode());
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityBulletTorp.class, new HMGRenderBulletExplode());

			RenderingRegistry.registerEntityRenderingHandler(HMGEntityLight.class, new HMGRenderRight());
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityLight2.class, new HMGRenderRight2());
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityLaser.class, new HMGRenderLaser());

			RenderingRegistry.registerEntityRenderingHandler(HMGEntityBullet_AP.class, new HMGRenderBullet());
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityBullet_Frag.class, new HMGRenderBullet());
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityBullet_HE.class, new HMGRenderBullet());
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityBullet_TE.class, new HMGRenderBulletExplode());
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityBullet_AT.class, new HMGRenderBullet());
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityBullet_Flame.class, new HMGRenderBullet());


			RenderingRegistry.registerEntityRenderingHandler(HMGEntityBulletCartridge.class, new HMGRenderBulletCartridge());
			RenderingRegistry.registerEntityRenderingHandler(PlacedGunEntity.class, new PlacedGun_Render());

			if (HandmadeGunsCore.cfg_RenderPlayer) {
				//RenderingRegistry.registerEntityRenderingHandler(EntityPlayer.class, new HMGRenderPlayer());
			}

			try {
				equippedProgress = ReflectionHelper.findField(HMG_proxy.getMCInstance().entityRenderer.itemRenderer.getClass(), "field_78454_c");
			} catch (Exception e) {
				e.printStackTrace();
				try {
					equippedProgress = ReflectionHelper.findField(HMG_proxy.getMCInstance().entityRenderer.itemRenderer.getClass(), "equippedProgress");
				} catch (Exception ea) {
					ea.printStackTrace();
				}
			}

			try {
				prevEquippedProgress = ReflectionHelper.findField(HMG_proxy.getMCInstance().entityRenderer.itemRenderer.getClass(), "field_78451_d");
			} catch (Exception e) {
				e.printStackTrace();
				try {
					prevEquippedProgress = ReflectionHelper.findField(HMG_proxy.getMCInstance().entityRenderer.itemRenderer.getClass(), "prevEquippedProgress");
				} catch (Exception ea) {
					ea.printStackTrace();
				}
			}

			try {
				itemToRender = ReflectionHelper.findField(HMG_proxy.getMCInstance().entityRenderer.itemRenderer.getClass(), "field_78453_b");
			} catch (Exception e) {
				e.printStackTrace();
				try {
					itemToRender = ReflectionHelper.findField(HMG_proxy.getMCInstance().entityRenderer.itemRenderer.getClass(), "itemToRender");
				} catch (Exception ea) {
					ea.printStackTrace();
				}
			}

			try {
				rightClickDelayTimer = ReflectionHelper.findField(HMG_proxy.getMCInstance().getClass(), "field_71467_ac");
			} catch (Exception e) {
				e.printStackTrace();
				try {
					rightClickDelayTimer = ReflectionHelper.findField(HMG_proxy.getMCInstance().getClass(), "rightClickDelayTimer");
				} catch (Exception ea) {
					ea.printStackTrace();
				}
			}

			try {
				fovModifierHandPrev = ReflectionHelper.findField(EntityRenderer.class, "field_78506_S");
			} catch (Exception e) {
				e.printStackTrace();
				try {
					fovModifierHandPrev = ReflectionHelper.findField(EntityRenderer.class, "fovModifierHandPrev");
				} catch (Exception ea) {
					ea.printStackTrace();
				}
			}

			try {
				fovModifierHand = ReflectionHelper.findField(EntityRenderer.class, "field_78507_R");
			} catch (Exception e) {
				e.printStackTrace();
				try {
					fovModifierHand = ReflectionHelper.findField(EntityRenderer.class, "fovModifierHand");
				} catch (Exception ea) {
					ea.printStackTrace();
				}
			}

			try {
				prevDebugCamFOV = ReflectionHelper.findField(EntityRenderer.class, "field_78494_N");
			} catch (Exception e) {
				e.printStackTrace();
				try {
					prevDebugCamFOV = ReflectionHelper.findField(EntityRenderer.class, "prevDebugCamFOV");
				} catch (Exception ea) {
					ea.printStackTrace();
				}
			}

			try {
				debugCamFOV = ReflectionHelper.findField(EntityRenderer.class, "field_78493_M");
			} catch (Exception e) {
				e.printStackTrace();
				try {
					debugCamFOV = ReflectionHelper.findField(EntityRenderer.class, "debugCamFOV");
				} catch (Exception ea) {
					ea.printStackTrace();
				}
			}
//    	RenderingRegistry.registerEntityRenderingHandler(HMGEntityParticles.class, new HMGRenderParticles());
			//RenderingRegistry.registerEntityRenderingHandler(HMGEntityParticles.class, new HMGRenderParticles2());
			//RenderingRegistry.registerEntityRenderingHandler(HMGEntityHand.class, new HMGRenderHand());
		}catch (Exception e){
    		e.printStackTrace();
		}

    }

    @Override
    public void registerTileEntity() {
		RenderTileMounter renderTileMounter = new RenderTileMounter();
		ClientRegistry.registerTileEntity(TileMounter.class, "TileItemMounter", renderTileMounter);//������
    	//GameRegistry.registerTileEntity(GVCTileEntityItemG36.class, "GVCTileEntitysample");
    }
    @Override
	public void force_render_item_position(ItemStack itemStack,int i){
		try {
			Object obj = itemToRender.get(HMG_proxy.getMCInstance().entityRenderer.itemRenderer);
			if(beforeSlot == i && obj != null && obj instanceof ItemStack && ((ItemStack)obj).getItem() instanceof HMGItem_Unified_Guns) {
				equippedProgress.set(HMG_proxy.getMCInstance().entityRenderer.itemRenderer, 1);
				prevEquippedProgress.set(HMG_proxy.getMCInstance().entityRenderer.itemRenderer, 1);
				if (itemToRender.get(HMG_proxy.getMCInstance().entityRenderer.itemRenderer) != itemStack) {
					itemToRender.set(HMG_proxy.getMCInstance().entityRenderer.itemRenderer, itemStack);
				}
			}else {
				beforeSlot = i;
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void resetRightclicktimer(){
		try {
			rightClickDelayTimer.set(HMG_proxy.getMCInstance(), 0);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void setRightclicktimer(){
		try {
			ClientProxyHMG.rightClickDelayTimer.set(HMG_proxy.getMCInstance(), 10);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
    public void InitRendering()
    {
    	super.InitRendering();
		MinecraftForge.EVENT_BUS.register(new HMGParticles());
    	//ClientRegistry.bindTileEntitySpecialRenderer(GVCTileEntityItemG36.class, new GVCRenderItemG36());
    }
	@Override
	public boolean seekerOpenClose(){
		return SeekerOpen_Close.isKeyDown() && keyDown_except_null(gunSetting_modification.getKeyCode());
		//return false;
	}
	@Override
	public boolean fixkeydown(){
		return Fix.isKeyDown() && keyDown_except_null(gunPrepare_modification.getKeyCode());
		//return false;
	}

	@Override
	public boolean upElevationKeyDown(){
		return El_Up.isKeyDown() && keyDown_except_null(gunSetting_modification.getKeyCode());
	}
	@Override
	public boolean downElevationKeyDown(){
		return El_Down.isKeyDown() && keyDown_except_null(gunSetting_modification.getKeyCode());
	}
	@Override
	public boolean resetElevationKeyDown(){
		return El_Reset.isKeyDown() && keyDown_except_null(gunSetting_modification.getKeyCode());
	}
    @Override
    public boolean Fclick(){
		return Fire_AttachedGun.isKeyDown();
	}
    @Override
    public boolean ChangeMagazineTypeclick(){
		return ChangeMagazineType.isKeyDown() && keyDown_except_null(gunPrepare_modification.getKeyCode());
	}
	@Override
	public boolean Fclick_no_stopper(){
		boolean flag = keyDown(Fire_AttachedGun.keyBinding.getKeyCode());
		return flag;
	}
    @Override
    public boolean ADSclick(){
		return keyDown(ADS.getKeyCode());
	}
	@Override
	public boolean Reloadkeyispressed(){
		return keyDown(Reload.getKeyCode());
	}

	@Override
	public boolean Attachmentkeyispressed(){
		return keyDown(Attachment.getKeyCode()) && keyDown_except_null(gunPrepare_modification.getKeyCode());
	}
	@Override
	public boolean Modekeyispressed(){
		return Mode.isKeyDown() && keyDown_except_null(gunSetting_modification.getKeyCode());
	}
//	@Override
//	public boolean Secondarykeyispressed(){
//		return keyDown(Fire2.getKeyCode());
//	}

	public boolean keyDown(int keyCode)
	{
		boolean state = false;
		if (getMCInstance().currentScreen == null || getMCInstance().currentScreen.allowUserInput) {
			state = (keyCode < 0 ? Mouse.isButtonDown(keyCode + 100) : Keyboard.isKeyDown(keyCode));
		}
		return state;
	}

	public boolean keyDown_except_null(int keyCode)
	{
		if(keyCode == Keyboard.KEY_NONE)return true;
		boolean state = false;
		if (getMCInstance().currentScreen == null || getMCInstance().currentScreen.allowUserInput) {
			state = (keyCode < 0 ? Mouse.isButtonDown(keyCode + 100) : Keyboard.isKeyDown(keyCode));
		}
		return state;
	}
	public void spawnParticles(PacketSpawnParticle message){
		try {
			HMGEntityParticles var10 = new HMGEntityParticles(getCilentWorld(),
					message.posx, message.posy, message.posz);
			if(message.name == null || message.name.equals("")) {
				switch (message.id % 100) {
					case 0:
						var10.setParticleIcon(HMGParticles.getInstance().getIcon("handmadeguns:fire"));
						var10.setIcon("handmadeguns:textures/items/fire");
						var10.isglow = message.id/100 ==1;;
						var10.fuse = 1;
						break;
					case 1:
						var10.setParticleIcon(HMGParticles.getInstance().getIcon("handmadeguns:smoke"));
						var10.thisMotionX = message.motionX;
						var10.thisMotionY = message.motionY;
						var10.thisMotionZ = message.motionZ;
						var10.fuse = 5;
						var10.animationspeed = 2;
						var10.setIcon("handmadeguns:textures/items/smoke",10);
						var10.setParticleAlpha(1);
						var10.setParticleScale(3);
						break;
					case 2:

						var10.setParticleIcon(HMGParticles.getInstance().getIcon("handmadeguns:lockonmarker"));
						var10.setIcon(lockonmarker);
						var10.animationspeed = 2;
						var10.setParticleScale(1);
						var10.isglow = false;
						var10.fuse = 1;
						var10.fixedsize = true;
						var10.disable_DEPTH_TEST = true;
						var10.isrenderglow = true;
						message.id = 102;

						var10.prevPosX = message.posx;
						var10.prevPosY = message.posy;
						var10.prevPosZ = message.posz;
						break;
					case 3:
						var10.setParticleIcon(HMGParticles.getInstance().getIcon("handmadeguns:smoke"));
						var10.thisMotionX = message.motionX;
						var10.thisMotionY = message.motionY;
						var10.thisMotionZ = message.motionZ;
						var10.fuse = message.fuse;
						var10.animationspeed = 2;
						var10.setIcon(trailtexture,10);
						var10.setParticleAlpha(1);
						var10.istrail = true;
						var10.trailwidth = message.trailwidth;
						var10.setParticleScale(3);
						break;
					case 4:

						var10.setParticleIcon(HMGParticles.getInstance().getIcon("handmadeguns:lockonmarker"));
						var10.setIcon(lockonmarker);
						var10.setParticleScale(1);
						var10.isglow = false;
						var10.fuse = 0;
						var10.fixedsize = true;
						var10.disable_DEPTH_TEST = true;
						var10.isrenderglow = true;
						message.id = 102;

						var10.prevPosX = message.posx;
						var10.prevPosY = message.posy;
						var10.prevPosZ = message.posz;
						break;
//
//        bullet = message.bullet.setdata(bullet);
//        System.out.println("bullet "+ bullet);
				}
			}else {
				if(message.id % 100== 3 || message.id == 3){
					var10.setParticleIcon(HMGParticles.getInstance().getIcon("handmadeguns:smoke"));
					var10.thisMotionX = message.motionX;
					var10.thisMotionY = message.motionY;
					var10.thisMotionZ = message.motionZ;
					var10.fuse = message.fuse;
					var10.istrail = true;
					var10.trailwidth = message.trailwidth;
					var10.setIcon("handmadeguns:textures/items/" + message.name,10);
					var10.setParticleAlpha(1);
				}else if(message.id % 100== 1 || message.id == 1){
					var10.setParticleIcon(HMGParticles.getInstance().getIcon("handmadeguns:" + message.name));
					var10.rotationYaw = (float) message.motionX;
					var10.rotationPitch = (float) message.motionY;
					var10.setIcon("handmadeguns:textures/items/" + message.name, message.fuse+1);
					var10.isrenderglow = message.id/100 ==1;
					var10.setParticleScale(message.scale);
					var10.fuse = message.fuse;
					var10.is3d = message.is3d;
				}else {
					var10.setParticleIcon(HMGParticles.getInstance().getIcon("handmadeguns:" + message.name));
					var10.rotationYaw = (float) message.motionX;
					var10.rotationPitch = (float) message.motionY;
					var10.setIcon("handmadeguns:textures/items/" + message.name, message.fuse+1);
					var10.isglow = message.id/100 ==1;
					var10.setParticleScale(message.scale);
					var10.fuse = message.fuse;
					var10.is3d = message.is3d;
				}
			}

			var10.prevPosX = message.posx;
			var10.prevPosY = message.posy;
			var10.prevPosZ = message.posz;
			var10.isrenderglow = message.id/100 ==1;
			var10.animationspeed = message.animationspeed;

			FMLClientHandler.instance().getClient().effectRenderer.addEffect(var10);
		} catch (ClassCastException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void fixitemEquippedprogress(){
		getMCInstance().entityRenderer.itemRenderer.updateEquippedItem();
	}
	@Override
	public boolean rightclick(){
		return Minecraft.getMinecraft().gameSettings.keyBindUseItem.getIsKeyPressed();
		//return false;
	}

	public String getFixkey(){
		return GameSettings.getKeyDisplayString(Fix.keyBinding.getKeyCode()) + " + " + GameSettings.getKeyDisplayString(gunPrepare_modification.getKeyCode());
	}

	public float getFOVModifier(Minecraft mc,float p_78481_1_, boolean p_78481_2_)
	{
		try {
			EntityRenderer entityRenderer = mc.entityRenderer;
			if (entityRenderer.debugViewDirection > 0)
			{
				return 90.0F;
			}
			else
			{
				EntityLivingBase entityplayer = (EntityLivingBase)mc.renderViewEntity;
				float f1 = 70.0F;

				if (p_78481_2_)
				{
					f1 = mc.gameSettings.fovSetting;
					f1 *= fovModifierHandPrev.getFloat(entityRenderer) + (fovModifierHand.getFloat(entityRenderer) - fovModifierHandPrev.getFloat(entityRenderer)) * p_78481_1_;

				}

				if (entityplayer.getHealth() <= 0.0F)
				{
					float f2 = (float)entityplayer.deathTime + p_78481_1_;
					f1 /= (1.0F - 500.0F / (f2 + 500.0F)) * 2.0F + 1.0F;
				}

				Block block = ActiveRenderInfo.getBlockAtEntityViewpoint(mc.theWorld, entityplayer, p_78481_1_);

				if (block.getMaterial() == Material.water)
				{
					f1 = f1 * 60.0F / 70.0F;
				}

				return f1 + prevDebugCamFOV.getFloat(entityRenderer) + (debugCamFOV.getFloat(entityRenderer) - prevDebugCamFOV.getFloat(entityRenderer)) * p_78481_1_;
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
}