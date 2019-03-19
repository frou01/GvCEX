package hmggvcmob.proxy;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import hmggvcmob.entity.*;
import hmggvcmob.entity.friend.*;
import hmggvcmob.entity.guerrilla.*;
import hmggvcmob.render.*;
import hmggvcmob.tile.TileEntityFlag;
import hmggvcmob.tile.TileEntityMobSpawner_Extend;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
//import net.java.games.input.Keyboard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import org.lwjgl.input.Mouse;

import static hmggvcmob.event.GVCMRenderSomeEvent.zooming;

public class ClientProxyGVCM extends CommonSideProxyGVCM {
	public static final KeyBinding Zoom = new KeyBinding("CannonCamera", Keyboard.KEY_Z, "GVCMob");
	public static final KeyBinding F = new KeyBinding("Flare", Keyboard.KEY_F, "GVCMob");
	public static final KeyBinding Flapextension = new KeyBinding("Flap extension", Keyboard.KEY_F, "GVCMob");
	public static final KeyBinding Flapstorage = new KeyBinding("Flap storage", Keyboard.KEY_F, "GVCMob");
	static boolean zoomkey_stopper;
	static boolean fkey_stopper;
	static boolean reload_stopper;
    //�L�[��UnlocalizedName�A�o�C���h����L�[�̑Ή������l�iKeyboard�N���X�Q�Ƃ̂��Ɓj�A�J�e�S���[��
    //public static final KeyBinding Speedreload = new KeyBinding("Key.reload", Keyboard.KEY_R, "CategoryName");
	@Override
	public World getCilentWorld(){
		return FMLClientHandler.instance().getClient().theWorld;
		}
	
	@Override
    public EntityPlayer getEntityPlayerInstance() {
        return Minecraft.getMinecraft().thePlayer;
    }
	
	public void reisterRenderers(){
		ClientRegistry.registerKeyBinding(Zoom);
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityGuerrilla.class, new GVCRenderGuerrilla());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityGuerrilla_Flamer.class, GVCRenderGuerrilla.onlyoneTexture("guerrilla_f"));
		//RenderingRegistry.registerEntityRenderingHandler(GVCEntityGuerrilla.class, new GVCRenderGuerrillaobj());
		
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityGuerrillaSP.class, GVCRenderGuerrilla.onlyoneTexture("guerrillasp"));
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityGuerrillaRPG.class, GVCRenderGuerrilla.onlyoneTexture("guerrillarpg"));
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityGuerrillaSG.class, GVCRenderGuerrilla.onlyoneTexture("guerrillasg"));
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityGuerrillaMG.class, GVCRenderGuerrilla.onlyoneTexture("guerrillamg"));
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityGuerrillaBM.class, GVCRenderGuerrilla.onlyoneTexture("bommer"));
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityGuerrillaP.class, GVCRenderGuerrilla.onlyoneTexture("guerrillaP"));
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityGuerrillaSkeleton.class, GVCRenderGuerrilla.onlyoneTexture("skeleton_guerrilla"));
		
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityGuerrillaM.class, new GVCRenderGuerrillaM());
		
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityGK.class, new GVCRenderGK());

		RenderingRegistry.registerEntityRenderingHandler(GVCEntityTank.class, new GVCRenderTankobj());
		RenderingRegistry.registerEntityRenderingHandler(TU95.class, new GVCRenderTU95());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityTankT90.class, new GVCRenderTankT90());
		/*if(GVCMobPlus.cfg_modelobj){
			RenderingRegistry.registerEntityRenderingHandler(GVCEntityAPC.class, new GVCRenderAPCobj());
			RenderingRegistry.registerEntityRenderingHandler(GVCEntityHeli.class, new GVCRenderHeliobj());
		}else{
			RenderingRegistry.registerEntityRenderingHandler(GVCEntityAPC.class, new GVCRenderAPC());
			RenderingRegistry.registerEntityRenderingHandler(GVCEntityHeli.class, new GVCRenderHeli());
		}*/
		
		
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityAPC.class, new GVCRenderAPC());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityHeli.class, new GVCRenderHeliobj());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityWZ10AttackHeli.class, new GVCRenderWZ10AttackHeli());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityDrawn.class, new GVCRenderDrawn());

		
		
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityJeep.class, new GVCRenderJeep());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityAAG.class, new GVCRenderAAG());
		RenderingRegistry.registerEntityRenderingHandler(EntityParachute.class, new GVCRenderParachute());

		RenderingRegistry.registerEntityRenderingHandler(GVCEntityAA.class, new GVCRenderAA());
		
		//RenderingRegistry.registerEntityRenderingHandler(GVCEntityMobSpawner.class, new GVCRenderMobSpawner());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityMobSpawner.class, new RenderSnowball(Items.apple));
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityMobSpawner2.class, new GVCRenderMobSpawner2());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityFlag.class, new GVCRenderFlag());
		
		
		
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityPMC.class, new GVCRenderPMC());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityPMCSP.class, new GVCRenderPMCSP());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityPMCMG.class, new GVCRenderPMCMG());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityPMCRPG.class, new GVCRenderPMCRPG());
		
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityPMCTank.class, new GVCRenderPMCTankobj());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityPMCT90Tank.class, new GVCRenderTankT90PMC());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntitySoldierBMP.class, new GVCRenderBMP());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityPMCBMP.class, new GVCRenderPMCBMP());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityChild.class, new GVCRenderSeat());
		RenderingRegistry.registerEntityRenderingHandler(GVCdummyhitbox.class, new GVCRenderSeat());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityPMCHeli.class, new GVCRenderPMCHeliobj());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityPlane.class, new GVCRenderPlane());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityFriendGK.class, new GVCRenderFriendGK());
		
		
		RenderingRegistry.registerEntityRenderingHandler(GVCEntitySoldier.class, new GVCRenderSoldier());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntitySoldierSP.class, new GVCRenderSoldierSP());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntitySoldierMG.class, new GVCRenderSoldierMG());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntitySoldierRPG.class, new GVCRenderSoldierRPG());
		
		RenderingRegistry.registerEntityRenderingHandler(GVCEntitySoldierTank.class, new GVCRenderSoldierTankobj());
		RenderingRegistry.registerEntityRenderingHandler(GVCEntitySoldierHeli.class, new GVCRenderSoldierHeliobj());
		RenderingRegistry.registerEntityRenderingHandler(GVCXEntitySoldierSpawn.class, new GVCRenderSoldier());

		RenderingRegistry.registerEntityRenderingHandler(EntityMGAX55.class, new GVCRenderMGM());
	}
	
	
    @Override
    public void registerClientInfo() {
        //ClientRegistry.registerKeyBinding(Speedreload);
    }
    @Override
    public void registerTileEntity() {
		ClientRegistry.registerTileEntity(TileEntityFlag.class, "FlagTile", new RenderTileFlag());//※ついで
		GameRegistry.registerTileEntity(TileEntityMobSpawner_Extend.class, "MobspawnerEX");//※ついで
		try {
			nextstepdistance = ReflectionHelper.findField(Entity.class,"nextStepDistance");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				nextstepdistance = ReflectionHelper.findField(Entity.class,"nextStepDistance");
			}catch (Exception ea){
				ea.printStackTrace();
			}
		}
    }
 
    @Override
    public boolean reload(){
		//return Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown();
		return Keyboard.isCreated() && Keyboard.isKeyDown(Keyboard.KEY_R);
		//return false;
	}

    @Override
    public boolean reload_Semi(){

		boolean flag = Keyboard.isCreated() && Keyboard.isKeyDown(Keyboard.KEY_R);
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
    public boolean jumped(){
		return Minecraft.getMinecraft().gameSettings.keyBindJump.getIsKeyPressed();
		//return false;
	}
    
    @Override
    public boolean leftclick(){
		return Minecraft.getMinecraft().gameSettings.keyBindAttack.getIsKeyPressed();
		//return false;
	}
	@Override
    public boolean rightclick(){
		return Minecraft.getMinecraft().gameSettings.keyBindUseItem.getIsKeyPressed();
		//return false;
	}
	@Override
	public boolean wclick(){
		return Minecraft.getMinecraft().gameSettings.keyBindForward.getIsKeyPressed();
		//return false;
	}
	@Override
	public boolean aclick(){
		return Minecraft.getMinecraft().gameSettings.keyBindLeft.getIsKeyPressed();
		//return false;
	}
	@Override
	public boolean sclick(){
		return Minecraft.getMinecraft().gameSettings.keyBindBack.getIsKeyPressed();
		//return false;
	}
	@Override
	public boolean dclick(){
		return Minecraft.getMinecraft().gameSettings.keyBindRight.getIsKeyPressed();
		//return false;
	}
    @Override
    public boolean zoomclick(){
		boolean flag = Zoom.getIsKeyPressed();
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
    public boolean fclick(){
		boolean flag = F.getIsKeyPressed();
		if(flag){
			if(!fkey_stopper) {
				fkey_stopper = true;
			}else {
				flag = false;
			}
		}else {
			fkey_stopper = false;
		}
		return flag;
		//return false;
	}
    public boolean FalpEclick(){
		boolean flag = Flapextension.getIsKeyPressed();
		return flag;
		//return false;
	}
    public boolean FalpSclick(){
		boolean flag = Flapstorage.getIsKeyPressed();
		return flag;
		//return false;
	}
	public Minecraft getMCInstance() {
		return Minecraft.getMinecraft();
	}
	public boolean keyDown(int keyCode)
	{
		boolean state = false;
		if (getMCInstance().currentScreen == null || getMCInstance().currentScreen.allowUserInput) {
			state = (keyCode < 0 ? Mouse.isButtonDown(keyCode + 100) : Keyboard.isKeyDown(keyCode));
		}
		return state;
	}

	public boolean iszooming(){
		return zooming;
	}
}