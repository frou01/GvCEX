package hmgww2;

import cpw.mods.fml.common.registry.GameRegistry;
import hmgww2.blocks.tile.TileEntityBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

//ClientProxy???K?v?????A?g???????l??CommonProxy???p??BGUI???????????AIGuiHandler???????????B
public class CommonSideProxyGVCWW2 {
 
	public void registerClientInfo(){}
	
	public void IGuiHandler(){}
	
    public void reisterRenderers(){
    	GameRegistry.registerTileEntity(TileEntityBase.class, "Flag_GVCWW2");
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