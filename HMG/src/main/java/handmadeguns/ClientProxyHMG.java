package handmadeguns;


import java.io.File;
import java.lang.reflect.Field;

import cpw.mods.fml.relauncher.ReflectionHelper;
import handmadeguns.client.audio.BulletSoundHMG;
import handmadeguns.client.audio.MovingSoundHMG;
import handmadeguns.client.audio.ReloadSoundHMG;
import handmadeguns.emb_modelloader.MQO_ModelLoader;
import handmadeguns.entity.*;
import handmadeguns.entity.bullets.*;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadeguns.network.PacketSpawnParticle;
import handmadeguns.client.render.*;
import handmadeguns.tile.TileMounter;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
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

import static cpw.mods.fml.relauncher.ReflectionHelper.setPrivateValue;
import static handmadeguns.HandmadeGunsCore.proxy;

public class ClientProxyHMG extends CommonSideProxyHMG {
	public static final KeyBinding Reload = new KeyBinding("Reload", Keyboard.KEY_R, "HandmadeGuns");
	public static final KeyBinding Light = new KeyBinding("Right ON/Off", Keyboard.KEY_C, "HandmadeGuns");
	public static final KeyBinding Attachment = new KeyBinding("Attachment", Keyboard.KEY_X, "HandmadeGuns");
	public static final KeyBinding Knife = new KeyBinding("Knife", Keyboard.KEY_F, "HandmadeGuns");
	public static final KeyBinding ADS = new KeyBinding("ADS_Key", Keyboard.KEY_V, "HandmadeGuns");
	public static final KeyBinding Mode = new KeyBinding("Mode_Key", Keyboard.KEY_GRAVE, "HandmadeGuns");
	public static final KeyBinding Fix = new KeyBinding("Fix Gun", Keyboard.KEY_H, "HandmadeGuns");
	private static final String trailtexture = ("handmadeguns:textures/entity/trail");
	static boolean stopper_modekey = false;
	static boolean stopper_Fkey = false;
	static boolean stopper_Lightkey = false;
	static boolean stopper_Fixkey = false;
	static Field equippedProgress;
	static Field prevEquippedProgress;
	static Field itemToRender;
	static Field rightClickDelayTimer = null;
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
		AdvancedModelLoader.registerModelHandler(new MQO_ModelLoader());
		System.out.println("forge.forceDisplayStencil  " + System.getProperty("forge.forceDisplayStencil"));
		System.setProperty("forge.forceDisplayStencil","true");
	}
	@Override
	public void playsoundat(String sound, float soundLV, float soundSP, float tempsp, double posX, double posY, double posZ){
		Minecraft.getMinecraft().getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(sound),soundLV,soundSP*tempsp,(float)posX,(float)posY,(float)posZ));
	}
	@Override
	public void playsoundatEntity(String sound, float soundLV, float soundSP,Entity attached,boolean repeat){
		Minecraft.getMinecraft().getSoundHandler().playSound(new MovingSoundHMG(attached,sound,repeat,soundLV,soundSP));
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
	public World getCilentWorld(){
		return FMLClientHandler.instance().getClient().theWorld;
		}
    
    @Override
    public void registerClientInfo() {
        //ClientRegistry.registerKeyBinding(Speedreload);
    }
    
    @Override
	public void reisterRenderers(){
    	Minecraft mc = FMLClientHandler.instance().getClient();
    	//RenderManager rendermanager = new RenderManager(mc.renderEngine, mc.getRenderItem());
    	//RenderItem renderitem = mc.getRenderItem();
    	
    	ClientRegistry.registerKeyBinding(Reload);
    	ClientRegistry.registerKeyBinding(Light);
    	ClientRegistry.registerKeyBinding(Attachment);
    	ClientRegistry.registerKeyBinding(Knife);
    	ClientRegistry.registerKeyBinding(ADS);
		ClientRegistry.registerKeyBinding(Mode);
		ClientRegistry.registerKeyBinding(Fix);
    	MinecraftForge.EVENT_BUS.register(new HMGParticles());

    	try {
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityItemMount.class, new HMGRenderItemMount());
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityItemMount2.class, new HMGRenderItemMount2());

			RenderingRegistry.registerEntityRenderingHandler(HMGEntityBullet.class, new HMGRenderBullet());
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityBulletRocket.class, new HMGRenderBulletRPG());
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityBulletExprode.class, new HMGRenderBulletGrenade());

			RenderingRegistry.registerEntityRenderingHandler(HMGEntityLight.class, new HMGRenderRight());
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityLight2.class, new HMGRenderRight2());
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityLaser.class, new HMGRenderLaser());

			RenderingRegistry.registerEntityRenderingHandler(HMGEntityBullet_AP.class, new HMGRenderBullet());
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityBullet_Frag.class, new HMGRenderBullet());
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityBullet_HE.class, new HMGRenderBullet());
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityBullet_TE.class, new HMGRenderBulletRPG());
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityBullet_AT.class, new HMGRenderBullet());
			RenderingRegistry.registerEntityRenderingHandler(HMGEntityBullet_Flame.class, new HMGRenderBullet());


			RenderingRegistry.registerEntityRenderingHandler(HMGEntityBulletCartridge.class, new HMGRenderBulletCartridge());
			RenderingRegistry.registerEntityRenderingHandler(PlacedGunEntity.class, new PlacedGun_Render());

			if (HandmadeGunsCore.cfg_RenderPlayer) {
				//RenderingRegistry.registerEntityRenderingHandler(EntityPlayer.class, new HMGRenderPlayer());
			}
			try {
				equippedProgress = ReflectionHelper.findField(proxy.getMCInstance().entityRenderer.itemRenderer.getClass(), "field_78454_c");
			} catch (Exception e) {
				e.printStackTrace();
				try {
					equippedProgress = ReflectionHelper.findField(proxy.getMCInstance().entityRenderer.itemRenderer.getClass(), "equippedProgress");
				} catch (Exception ea) {
					ea.printStackTrace();
				}
			}

			try {
				prevEquippedProgress = ReflectionHelper.findField(proxy.getMCInstance().entityRenderer.itemRenderer.getClass(), "field_78451_d");
			} catch (Exception e) {
				e.printStackTrace();
				try {
					prevEquippedProgress = ReflectionHelper.findField(proxy.getMCInstance().entityRenderer.itemRenderer.getClass(), "prevEquippedProgress");
				} catch (Exception ea) {
					ea.printStackTrace();
				}
			}
			try {
				itemToRender = ReflectionHelper.findField(proxy.getMCInstance().entityRenderer.itemRenderer.getClass(), "field_78453_b");
			} catch (Exception e) {
				e.printStackTrace();
				try {
					itemToRender = ReflectionHelper.findField(proxy.getMCInstance().entityRenderer.itemRenderer.getClass(), "itemToRender");
				} catch (Exception ea) {
					ea.printStackTrace();
				}
			}
			try {
				rightClickDelayTimer = ReflectionHelper.findField(proxy.getMCInstance().getClass(), "field_71467_ac");
			} catch (Exception e) {
				e.printStackTrace();
				try {
					rightClickDelayTimer = ReflectionHelper.findField(proxy.getMCInstance().getClass(), "rightClickDelayTimer");
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
			Object obj = itemToRender.get(proxy.getMCInstance().entityRenderer.itemRenderer);
			if(beforeSlot == i && obj != null && obj instanceof ItemStack && ((ItemStack)obj).getItem() instanceof HMGItem_Unified_Guns) {
				equippedProgress.set(proxy.getMCInstance().entityRenderer.itemRenderer, 1);
				prevEquippedProgress.set(proxy.getMCInstance().entityRenderer.itemRenderer, 1);
				if (itemToRender.get(proxy.getMCInstance().entityRenderer.itemRenderer) != itemStack) {
					itemToRender.set(proxy.getMCInstance().entityRenderer.itemRenderer, itemStack);
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
			rightClickDelayTimer.set(proxy.getMCInstance(), 0);
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
    public boolean jumped(){
		return keyDown(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode());
		//return false;
	}
    
    @Override
    public boolean leftclick(){
		return keyDown(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode());
		//return false;
	}
	@Override
	public boolean lightkeydown(){
		boolean flag =  keyDown(Light.getKeyCode());
    	if(flag){
    		if(!stopper_Lightkey){
    			stopper_Lightkey = true;
			}else{
    			flag = false;
			}
		}else {
    		stopper_Lightkey = false;
		}
		return flag;
		//return false;
	}
	@Override
	public boolean fixkeydown(){
		boolean flag =  keyDown(Fix.getKeyCode());
    	if(flag){
    		if(!stopper_Fixkey){
				stopper_Fixkey = true;
			}else{
    			flag = false;
			}
		}else {
			stopper_Fixkey = false;
		}
		return flag;
		//return false;
	}
	@Override
	public boolean Lightkeyispressed_no_stopper(){
		return keyDown(Light.getKeyCode());
	}
    @Override
    public boolean Fclick(){
		boolean flag = keyDown(Knife.getKeyCode());
		if(flag){
			if(!stopper_Fkey){
				stopper_Fkey = true;
			}else {
				flag = false;
			}
		}else {
			stopper_Fkey = false;
		}
		return flag;
	}
	@Override
	public boolean Fclick_no_stopper(){
		boolean flag = keyDown(Knife.getKeyCode());
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
		return keyDown(Attachment.getKeyCode());
	}
	@Override
	public boolean Modekeyispressed(){
		boolean flag = keyDown(Mode.getKeyCode());
		if(flag){
			if(!stopper_modekey) {
				stopper_modekey = true;
			}else {
				flag = false;
			}
		}else {
			stopper_modekey = false;
		}
		return flag;
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
						var10.motionX = message.motionX;
						var10.motionY = message.motionY;
						var10.motionZ = message.motionZ;
						var10.fuse = 5;
						var10.animationspeed = 2;
						var10.setIcon("handmadeguns:textures/items/smoke",10);
						var10.setParticleAlpha(1);
						var10.setParticleScale(3);
						break;
					case 2:
						
						var10.setParticleIcon(HMGParticles.getInstance().getIcon("handmadeguns:lockonmarker"));
						var10.setIcon("handmadeguns:textures/items/lockonmarker");
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
						var10.motionX = message.motionX;
						var10.motionY = message.motionY;
						var10.motionZ = message.motionZ;
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
						var10.setIcon("handmadeguns:textures/items/lockonmarker");
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
					var10.motionX = message.motionX;
					var10.motionY = message.motionY;
					var10.motionZ = message.motionZ;
					var10.fuse = 5;
					var10.animationspeed = 2;
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
			var10.isrenderglow = message.id/100 ==1;
			
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
}