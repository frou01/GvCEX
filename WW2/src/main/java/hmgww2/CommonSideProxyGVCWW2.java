package hmgww2;

import cpw.mods.fml.common.registry.GameRegistry;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

//ClientProxy???K?v?????A?g???????l??CommonProxy???p??BGUI???????????AIGuiHandler???????????B
public class CommonSideProxyGVCWW2 {
 
	public void registerClientInfo(){}
	
	public void IGuiHandler(){}
	
    public void reisterRenderers(){
    	GameRegistry.registerTileEntity(TileEntityFlag_JPN.class, "Flag_JPN");
		GameRegistry.registerTileEntity(TileEntityFlag2_JPN.class, "Flag2_JPN");
		GameRegistry.registerTileEntity(TileEntityFlag3_JPN.class, "Flag3_JPN");
		GameRegistry.registerTileEntity(TileEntityFlag4_JPN.class, "Flag4_JPN");
    	
		GameRegistry.registerTileEntity(TileEntityFlag_USA.class, "Flag_USA");
		GameRegistry.registerTileEntity(TileEntityFlag2_USA.class, "Flag2_USA");
		GameRegistry.registerTileEntity(TileEntityFlag3_USA.class, "Flag3_USA");
		GameRegistry.registerTileEntity(TileEntityFlag4_USA.class, "Flag4_USA");
		
		GameRegistry.registerTileEntity(TileEntityFlag_GER.class, "Flag_GER");
		GameRegistry.registerTileEntity(TileEntityFlag2_GER.class, "Flag2_GER");
		GameRegistry.registerTileEntity(TileEntityFlag3_GER.class, "Flag3_GER");
		
		GameRegistry.registerTileEntity(TileEntityFlag_RUS.class, "Flag_RUS");
		GameRegistry.registerTileEntity(TileEntityFlag2_RUS.class, "Flag2_RUS");
		GameRegistry.registerTileEntity(TileEntityFlag3_RUS.class, "Flag3_RUS");
    }
	
	public World getCilentWorld(){
		return null;}

	public void InitRendering() {
		
	}

	public void registerTileEntity() {}
	
	public boolean reload(){
		return false;
	}
	
	public boolean jumped(){
		return false;
	}
	
	public boolean leftclick(){
		return false;
	}
	
	public boolean rightclick(){
		return false;
	}
	
	public boolean xclick(){
		return false;
	}
	
	public int mcbow(){
		return 1;
	}

	public EntityPlayer getEntityPlayerInstance() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
 
}