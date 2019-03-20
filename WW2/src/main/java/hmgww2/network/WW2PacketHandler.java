package hmgww2.network;
 
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
 
 
public class WW2PacketHandler {
 
    //このMOD用のSimpleNetworkWrapperを生成。チャンネルの文字列は固有であれば何でも良い。MODIDの利用を推奨。
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("HMGGVCWW2");
 
 
    public static void init() {
        INSTANCE.registerMessage(WW2MessageKeyPressedHandler.class, WW2MessageKeyPressed.class, 0, Side.SERVER);
    	
    	
    	
    	
    }
}