package hmggvcmob.network;
 
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;


public class GVCMPacketHandler {
 
    //このMOD用のSimpleNetworkWrapperを生成。チャンネルの文字列は固有であれば何でも良い。MODIDの利用を推奨。
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("GVCMR");
 
 
    public static void init() {
        int id=0;
        INSTANCE.registerMessage(GVCMMessageKeyPressedHandler.class, GVCMMessageKeyPressed.class, ++id, Side.SERVER);
        INSTANCE.registerMessage(GVCXMMessageGKdata.class, GVCXMPacketSyncGKdata.class, ++id, Side.CLIENT);
        INSTANCE.registerMessage(GVCMHandleSyncFlagdata.class, GVCMPacketSyncFlagdata.class, ++id, Side.CLIENT);
        INSTANCE.registerMessage(GVCMHandleSyncHeliData.class, GVCXMPacketSyncPMCHeliData.class, ++id, Side.SERVER);
        INSTANCE.registerMessage(GVCMHandleVehicleState.class, GVCPakcetVehicleState.class, ++id, Side.SERVER);
        INSTANCE.registerMessage(GVCMHandleVehicleState.class, GVCPakcetVehicleState.class, ++id, Side.CLIENT);
        INSTANCE.registerMessage(GVCHandleSeatData.class, GVCPacketSeatData.class, ++id, Side.CLIENT);
        INSTANCE.registerMessage(GVCHandleSeatData.class, GVCPacketSeatData.class, ++id, Side.SERVER);
        INSTANCE.registerMessage(GVCHandleMGControl.class, GVCPacketMGControl.class, ++id, Side.SERVER);
        INSTANCE.registerMessage(GVCHandleMGControl.class, GVCPacketMGControl.class, ++id, Side.CLIENT);
        INSTANCE.registerMessage(GVCMHandleHeliGunnerTriger.class, GVCPakcetHeliGunnerTrigger.class, ++id, Side.SERVER);
        INSTANCE.registerMessage(GVCHandleHudEntityTracking.class, GVCPacket_HudEntitytracking.class, ++id, Side.CLIENT);
        INSTANCE.registerMessage(GVCHandleSpawnSpotCircle.class, GVCPacketSpawnSpotCircle.class, ++id, Side.CLIENT);

    	
    }
}