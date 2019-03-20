package hmgww2;


import hmgww2.entity.*;
import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import hmgww2.blocks.tile.TileEntityFlag2_GER;
import hmgww2.blocks.tile.TileEntityFlag2_JPN;
import hmgww2.blocks.tile.TileEntityFlag2_RUS;
import hmgww2.blocks.tile.TileEntityFlag2_USA;
import hmgww2.blocks.tile.TileEntityFlag3_GER;
import hmgww2.blocks.tile.TileEntityFlag3_JPN;
import hmgww2.blocks.tile.TileEntityFlag3_RUS;
import hmgww2.blocks.tile.TileEntityFlag3_USA;
import hmgww2.blocks.tile.TileEntityFlag4_JPN;
import hmgww2.blocks.tile.TileEntityFlag4_USA;
import hmgww2.blocks.tile.TileEntityFlag_GER;
import hmgww2.blocks.tile.TileEntityFlag_JPN;
import hmgww2.blocks.tile.TileEntityFlag_RUS;
import hmgww2.blocks.tile.TileEntityFlag_USA;
import hmgww2.entity.EntityUSSR_TankSPG;
import hmgww2.render.RenderGER_Fighter;
import hmgww2.render.RenderGER_FighterA;
import hmgww2.render.RenderGER_S;
import hmgww2.render.RenderGER_Tank;
import hmgww2.render.RenderGER_TankAA;
import hmgww2.render.RenderGER_TankH;
import hmgww2.render.RenderGER_TankSPG;
import hmgww2.render.RenderJPN_Fighter;
import hmgww2.render.RenderJPN_FighterA;
import hmgww2.render.RenderJPN_S;
import hmgww2.render.RenderJPN_ShipB;
import hmgww2.render.RenderJPN_ShipD;
import hmgww2.render.RenderJPN_Tank;
import hmgww2.render.RenderJPN_TankAA;
import hmgww2.render.RenderJPN_TankSPG;
import hmgww2.render.RenderUSSR_Fighter;
import hmgww2.render.RenderUSSR_FighterA;
import hmgww2.render.RenderUSSR_S;
import hmgww2.render.RenderUSSR_Tank;
import hmgww2.render.RenderUSSR_TankAA;
import hmgww2.render.RenderUSSR_TankH;
import hmgww2.render.RenderUSSR_TankSPG;
import hmgww2.render.RenderUSA_Fighter;
import hmgww2.render.RenderUSA_FighterA;
import hmgww2.render.RenderUSA_S;
import hmgww2.render.RenderUSA_ShipB;
import hmgww2.render.RenderUSA_ShipD;
import hmgww2.render.RenderUSA_Tank;
import hmgww2.render.RenderUSA_TankAA;
import hmgww2.render.RenderUSA_TankSPG;
import hmgww2.render.tile.TileRenderFlag_JPN;
import net.minecraft.client.Minecraft;
//import net.java.games.input.Keyboard;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
public class ClientProxyGVCWW2 extends CommonSideProxyGVCWW2 {
	
	public static final KeyBinding Follow = new KeyBinding("hmgww2.followjpn.name", Keyboard.KEY_UP, "HMGGVCWW2");
	public static final KeyBinding Wait = new KeyBinding("hmgww2.waitjpn.name", Keyboard.KEY_DOWN, "HMGGVCWW2");
	public static final KeyBinding Free = new KeyBinding("hmgww2.freejpn.name", Keyboard.KEY_LEFT, "HMGGVCWW2");
	public static final KeyBinding Flag = new KeyBinding("hmgww2.flagjpn.name", Keyboard.KEY_RIGHT, "HMGGVCWW2");
	
	public static final KeyBinding ShipFree = new KeyBinding("hmgww2.vfreejpn.name", Keyboard.KEY_NUMPAD1, "HMGGVCWW2");
	public static final KeyBinding ShipWait = new KeyBinding("hmgww2.vwaitjpn.name", Keyboard.KEY_NUMPAD7, "HMGGVCWW2");
	public static final KeyBinding RidingShip = new KeyBinding("hmgww2.ridejpn.name", Keyboard.KEY_NUMPAD5, "HMGGVCWW2");
	
    @Override
	public World getCilentWorld(){
		return FMLClientHandler.instance().getClient().theWorld;
		}
    
    @Override
    public EntityPlayer getEntityPlayerInstance() {
        return Minecraft.getMinecraft().thePlayer;
    }
    
    @Override
    public void registerClientInfo() {
        //ClientRegistry.registerKeyBinding(Speedreload);
    }
    
    @Override
	public void reisterRenderers(){
		ClientRegistry.registerKeyBinding(Follow);
		ClientRegistry.registerKeyBinding(Wait);
		ClientRegistry.registerKeyBinding(Free);
		ClientRegistry.registerKeyBinding(Flag);
		ClientRegistry.registerKeyBinding(ShipFree);
		ClientRegistry.registerKeyBinding(ShipWait);
		ClientRegistry.registerKeyBinding(RidingShip);
		
    	RenderingRegistry.registerEntityRenderingHandler(EntityJPN_S.class, new RenderJPN_S());
    	RenderingRegistry.registerEntityRenderingHandler(EntityJPN_Tank.class, new RenderJPN_Tank());
    	RenderingRegistry.registerEntityRenderingHandler(EntityJPN_Fighter.class, new RenderJPN_Fighter());
    	RenderingRegistry.registerEntityRenderingHandler(EntityJPN_TankAA.class, new RenderJPN_TankAA());
    	RenderingRegistry.registerEntityRenderingHandler(EntityJPN_TankSPG.class, new RenderJPN_TankSPG());
    	RenderingRegistry.registerEntityRenderingHandler(EntityJPN_FighterA.class, new RenderJPN_FighterA());
    	RenderingRegistry.registerEntityRenderingHandler(EntityJPN_ShipB.class, new RenderJPN_ShipB());
    	RenderingRegistry.registerEntityRenderingHandler(EntityJPN_ShipD.class, new RenderJPN_ShipD());
    	
    	
    	RenderingRegistry.registerEntityRenderingHandler(EntityUSA_S.class, new RenderUSA_S());
    	RenderingRegistry.registerEntityRenderingHandler(EntityUSA_Tank.class, new RenderUSA_Tank());
    	RenderingRegistry.registerEntityRenderingHandler(EntityUSA_Fighter.class, new RenderUSA_Fighter());
    	RenderingRegistry.registerEntityRenderingHandler(EntityUSA_TankAA.class, new RenderUSA_TankAA());
    	RenderingRegistry.registerEntityRenderingHandler(EntityUSA_TankSPG.class, new RenderUSA_TankSPG());
    	RenderingRegistry.registerEntityRenderingHandler(EntityUSA_FighterA.class, new RenderUSA_FighterA());
    	RenderingRegistry.registerEntityRenderingHandler(EntityUSA_ShipB.class, new RenderUSA_ShipB());
    	RenderingRegistry.registerEntityRenderingHandler(EntityUSA_ShipD.class, new RenderUSA_ShipD());
    	
    	
    	RenderingRegistry.registerEntityRenderingHandler(EntityGER_S.class, new RenderGER_S());
    	RenderingRegistry.registerEntityRenderingHandler(EntityGER_Tank.class, new RenderGER_Tank());
    	RenderingRegistry.registerEntityRenderingHandler(EntityGER_Fighter.class, new RenderGER_Fighter());
    	RenderingRegistry.registerEntityRenderingHandler(EntityGER_TankAA.class, new RenderGER_TankAA());
    	RenderingRegistry.registerEntityRenderingHandler(EntityGER_TankSPG.class, new RenderGER_TankSPG());
    	RenderingRegistry.registerEntityRenderingHandler(EntityGER_FighterA.class, new RenderGER_FighterA());
    	RenderingRegistry.registerEntityRenderingHandler(EntityGER_TankH.class, new RenderGER_TankH());
    	
    	RenderingRegistry.registerEntityRenderingHandler(EntityUSSR_S.class, new RenderUSSR_S());
    	RenderingRegistry.registerEntityRenderingHandler(EntityUSSR_Tank.class, new RenderUSSR_Tank());
    	RenderingRegistry.registerEntityRenderingHandler(EntityUSSR_Fighter.class, new RenderUSSR_Fighter());
    	RenderingRegistry.registerEntityRenderingHandler(EntityUSSR_TankAA.class, new RenderUSSR_TankAA());
    	RenderingRegistry.registerEntityRenderingHandler(EntityUSSR_TankSPG.class, new RenderUSSR_TankSPG());
    	RenderingRegistry.registerEntityRenderingHandler(EntityUSSR_FighterA.class, new RenderUSSR_FighterA());
    	RenderingRegistry.registerEntityRenderingHandler(EntityUSSR_TankH.class, new RenderUSSR_TankH());
    	
    	
    	ClientRegistry.registerTileEntity(TileEntityFlag_JPN.class, "Flag_JPN", 
    			new TileRenderFlag_JPN("hmgww2:textures/blocks/tile/flag_jpn.png"));
    	ClientRegistry.registerTileEntity(TileEntityFlag2_JPN.class, "Flag2_JPN", 
    			new TileRenderFlag_JPN("hmgww2:textures/blocks/tile/flag2_jpn.png"));
    	ClientRegistry.registerTileEntity(TileEntityFlag3_JPN.class, "Flag3_JPN", 
    			new TileRenderFlag_JPN("hmgww2:textures/blocks/tile/flag3_jpn.png"));
    	ClientRegistry.registerTileEntity(TileEntityFlag4_JPN.class, "Flag4_JPN", 
    			new TileRenderFlag_JPN("hmgww2:textures/blocks/tile/flag4_jpn.png"));
    	
    	ClientRegistry.registerTileEntity(TileEntityFlag_USA.class, "Flag_USA", 
    			new TileRenderFlag_JPN("hmgww2:textures/blocks/tile/flag_usa.png"));
    	ClientRegistry.registerTileEntity(TileEntityFlag2_USA.class, "Flag2_USA", 
    			new TileRenderFlag_JPN("hmgww2:textures/blocks/tile/flag2_usa.png"));
    	ClientRegistry.registerTileEntity(TileEntityFlag3_USA.class, "Flag3_USA", 
    			new TileRenderFlag_JPN("hmgww2:textures/blocks/tile/flag3_usa.png"));
    	ClientRegistry.registerTileEntity(TileEntityFlag4_USA.class, "Flag4_USA", 
    			new TileRenderFlag_JPN("hmgww2:textures/blocks/tile/flag4_usa.png"));
    	
    	ClientRegistry.registerTileEntity(TileEntityFlag_GER.class, "Flag_GER", 
    			new TileRenderFlag_JPN("hmgww2:textures/blocks/tile/flag_ger.png"));
    	ClientRegistry.registerTileEntity(TileEntityFlag2_GER.class, "Flag2_GER", 
    			new TileRenderFlag_JPN("hmgww2:textures/blocks/tile/flag2_ger.png"));
    	ClientRegistry.registerTileEntity(TileEntityFlag3_GER.class, "Flag3_GER", 
    			new TileRenderFlag_JPN("hmgww2:textures/blocks/tile/flag3_ger.png"));
    	
    	ClientRegistry.registerTileEntity(TileEntityFlag_RUS.class, "Flag_RUS", 
    			new TileRenderFlag_JPN("hmgww2:textures/blocks/tile/flag_rus.png"));
    	ClientRegistry.registerTileEntity(TileEntityFlag2_RUS.class, "Flag2_RUS", 
    			new TileRenderFlag_JPN("hmgww2:textures/blocks/tile/flag2_rus.png"));
    	ClientRegistry.registerTileEntity(TileEntityFlag3_RUS.class, "Flag3_RUS", 
    			new TileRenderFlag_JPN("hmgww2:textures/blocks/tile/flag3_rus.png"));
    }
    
    @Override
    public boolean reload(){
		//return Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown();
		return Keyboard.isCreated() && Keyboard.isKeyDown(Keyboard.KEY_R);
		//return false;
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
    public boolean xclick(){
		return Keyboard.isCreated() && Keyboard.isKeyDown(Keyboard.KEY_X);
	}
    
}