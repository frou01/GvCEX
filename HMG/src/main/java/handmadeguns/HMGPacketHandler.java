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
        int id = 0;
        INSTANCE.registerMessage(HMGMessageKeyPressedHandlerC.class, HMGMessageKeyPressedC.class, ++id,
                Side.CLIENT);
        INSTANCE.registerMessage(MessageCatch_Reloadparm.class, PacketReloadparm.class, ++id,
                Side.CLIENT);
        INSTANCE.registerMessage(MessageCatchEntityData.class, PacketFixClientbullet.class, ++id,
                Side.CLIENT);
        INSTANCE.registerMessage(MessageCatcher_dropCartridge.class, PacketDropCartridge.class, ++id,
                Side.SERVER);
        INSTANCE.registerMessage(MessageCatchRecoilOrder.class, PacketRecoil.class, ++id,
                Side.CLIENT);
        INSTANCE.registerMessage(MessageCatcher_Spawnparticle.class, PacketSpawnParticle.class, ++id,
                Side.CLIENT);
        INSTANCE.registerMessage(MessageCatcher_Playsound.class, PacketPlaysound.class, ++id,
                Side.SERVER);
        INSTANCE.registerMessage(MessageCatcher_DamageHeldItem.class, PacketDamageHeldItem.class, ++id,
                Side.SERVER);
        INSTANCE.registerMessage(MessageCatcher_RecieveSpawnparticle.class,PacketRequestSpawnParticle.class,++id,
                Side.SERVER);
        INSTANCE.registerMessage(MessageCatcher_ChangeModeHeldItem.class, PacketChangeModeHeldItem.class, ++id,
                Side.SERVER);
        INSTANCE.registerMessage(MessageCatcher_returnMagazineItem.class, PacketreturnMgazineItem.class, ++id,
                Side.SERVER);
        INSTANCE.registerMessage(MessageCatcher_OpenGui.class, PacketOpenGui.class, ++id,
                Side.SERVER);
        INSTANCE.registerMessage(MessageCatcher_TriggerUnder.class, PacketTriggerUnder.class, ++id,
                Side.SERVER);
        INSTANCE.registerMessage(MessageCatcher_SeekerOpen.class, PacketSeekerOpen.class, ++id,
                Side.SERVER);
        INSTANCE.registerMessage(MessageCatcher_FixGun.class, PacketFixGun.class, ++id,
                Side.SERVER);
        INSTANCE.registerMessage(MessageCatcher_PlacedGunFire.class, PacketPlacedGunShot.class, ++id,
                Side.SERVER);
        INSTANCE.registerMessage(MessageCatcher_Playsound.class, PacketPlaysound.class, ++id,
                Side.CLIENT);
        INSTANCE.registerMessage(MessageCatcher_ChangeMagazineType.class, PacketChangeMagazineType.class, ++id,
                Side.SERVER);
        INSTANCE.registerMessage(MessageCatch_PlaySound_Gui.class, PacketPlaySound_Gui.class, ++id,
                Side.CLIENT);
    }
}