package hmggvcmob.proxy;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import hmggvcmob.entity.*;
import hmggvcmob.entity.friend.*;
import hmggvcmob.entity.guerrilla.*;
import hmggvcmob.render.*;
import hmggvcmob.tile.TileEntityFlag;
import hmggvcmob.tile.TileEntityMobSpawner_Extend;

import net.minecraft.client.renderer.entity.RenderSnowball;
//import net.java.games.input.Keyboard;
import net.minecraft.init.Items;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxyGVCM extends CommonSideProxyGVCM {
    //�L�[��UnlocalizedName�A�o�C���h����L�[�̑Ή������l�iKeyboard�N���X�Q�Ƃ̂��Ɓj�A�J�e�S���[��
    //public static final KeyBinding Speedreload = new KeyBinding("Key.reload", Keyboard.KEY_R, "CategoryName");
	@Override
	public World getCilentWorld(){
		return FMLClientHandler.instance().getClient().theWorld;
		}
	
	
	public void initSome(){
		super.initSome();
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
		RenderingRegistry.registerEntityRenderingHandler(GVCEntityGuerrilla_ender.class, GVCRenderGuerrilla.onlyoneTexture("guerrillasp_sky"));
		
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
		RenderingRegistry.registerEntityRenderingHandler(EntityChild.class, new GVCRenderSeat());
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
    }
}