package handmadeguns;

import java.io.File;
import java.lang.reflect.Field;

import cpw.mods.fml.common.registry.GameRegistry;
import handmadeguns.event.GunSoundEvent;
import handmadeguns.network.PacketSpawnParticle;
import handmadeguns.tile.TileMounter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class CommonSideProxyHMG {
	public File ProxyFile(){
		return new File(".");
	}

	public void setuprender(){

	}
	public EntityPlayer getEntityPlayerInstance() {return null;}
	
	public void registerClientInfo(){}
	
	public void IGuiHandler(){}
	
    public void reisterRenderers(){}
	
	public World getCilentWorld(){
		return null;}
	public Minecraft getMCInstance() {
		return null;
	}
	public void InitRendering() {
	}

	public void playsoundat(String sound, float soundLV, float soundSP, float tempsp, double posX, double posY, double posZ){
	}
	public void playsoundatEntity(String sound, float soundLV, float soundSP,Entity attached,boolean repeat){
	
	}
	public void playsoundatEntity_reload(String sound, float soundLV, float soundSP, Entity attached, boolean repeat){
	
	}
	public void playsoundatBullet(String sound, float soundLV, float soundSP,float mindsit,float maxdist,Entity attached,boolean repeat){
	
	}
	public void registerTileEntity() {
		GameRegistry.registerTileEntity(TileMounter.class, "TileItemMounter");
		//GameRegistry.registerTileEntity(GVCTileEntityItemG36.class, "GVCTileEntitysample");
	}
	public boolean jumped(){
		return false;
	}
	
	public boolean leftclick(){
		return false;
	}
	public boolean lightkeydown(){
		return false;
	}
	public boolean fixkeydown(){
		return false;
	}
	public boolean Lightkeyispressed_no_stopper(){
		return false;
	}
	public boolean Fclick(){
		return false;
	}
	public boolean ChangeMagazineTypeclick(){
		return false;
	}
	public boolean Fclick_no_stopper(){
		return false;
	}
	public boolean ADSclick(){
		return false;
	}
	public boolean Reloadkeyispressed(){
		return false;
	}
	public boolean Rightkeyispressed(){
		return false;
	}
	public boolean Attachmentkeyispressed(){
		return false;
	}
	public boolean Modekeyispressed(){
		return false;
	}
	public boolean Secondarykeyispressed(){
		return false;
	}
	public boolean keyDown(int keyCode)
	{
		return false;
	}
	public void force_render_item_position(ItemStack itemStack,int i){
	}
	public void resetRightclicktimer(){
	}
	public void spawnParticles(PacketSpawnParticle message) {
	}
	public void fixitemEquippedprogress(){
	}
	public boolean rightclick(){
		return false;
	}

	public void playerSounded(Entity entity){
		GunSoundEvent.post(entity);
	}
	
	public String getFixkey(){
		return null;
	}
}