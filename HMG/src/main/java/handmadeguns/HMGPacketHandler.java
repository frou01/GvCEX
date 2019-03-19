package handmadeguns;
 
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import handmadeguns.Handler.*;
import handmadeguns.network.*;


public class HMGPacketHandler {
 
    //このMOD用のSimpleNetworkWrapperを生成。チャンネルの文字列は固有であれば何でも良い。MODIDの利用を推奨。
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("HandmadeGuns");
 
 
    public static void init() {
        INSTANCE.registerMessage(HMGMessageKeyPressedHandlerC.class, HMGMessageKeyPressedC.class, 1, Side.CLIENT);
        INSTANCE.registerMessage(MessageCatch_Reloadparm.class, PacketReloadparm.class, 2, Side.CLIENT);
        INSTANCE.registerMessage(MessageCatchEntityData.class, PacketFixClientbullet.class, 3, Side.CLIENT);
        INSTANCE.registerMessage(MessageCatcher_dropCartridge.class, PacketDropCartridge.class, 5, Side.SERVER);
        INSTANCE.registerMessage(MessageCatchRecoilOrder.class, PacketRecoil.class, 6, Side.CLIENT);
        INSTANCE.registerMessage(MessageCatcher_Spawnparticle.class, PacketSpawnParticle.class, 7, Side.CLIENT);
        INSTANCE.registerMessage(MessageCatcher_Playsound.class, PacketPlaysound.class, 8, Side.SERVER);
        INSTANCE.registerMessage(MessageCatcher_DamageHeldItem.class, PacketDamageHeldItem.class, 9, Side.SERVER);
        INSTANCE.registerMessage(MessageCatcher_FireEXP.class, PacketFireEXP.class, 10, Side.SERVER);
        INSTANCE.registerMessage(MessageCatcher_RecieveSpawnparticle.class,PacketRequestSpawnParticle.class,12,Side.SERVER);
        INSTANCE.registerMessage(MessageCatcher_ChangeModeHeldItem.class, PacketChangeModeHeldItem.class, 13, Side.SERVER);
        INSTANCE.registerMessage(MessageCatcher_returnMagazineItem.class, PacketreturnMgazineItem.class, 14, Side.SERVER);
        INSTANCE.registerMessage(MessageCatcher_OpenGui.class, PacketOpenGui.class, 15, Side.SERVER);
        INSTANCE.registerMessage(MessageCatcher_TriggerUnder.class, PacketTriggerUnder.class, 16, Side.SERVER);
        INSTANCE.registerMessage(MessageCatcher_SeekerOpen.class, PacketSeekerOpen.class, 17, Side.SERVER);
        INSTANCE.registerMessage(MessageCatcher_FixGun.class, PacketFixGun.class, 18, Side.SERVER);
        INSTANCE.registerMessage(MessageCatcher_PlacedGunFire.class, PacketPlacedGunShot.class, 19, Side.SERVER);
        INSTANCE.registerMessage(MessageCatcher_Playsound.class, PacketPlaysound.class, 20, Side.CLIENT);
    }
}