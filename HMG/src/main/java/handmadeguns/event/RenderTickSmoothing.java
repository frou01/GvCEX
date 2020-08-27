package handmadeguns.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import handmadeguns.HandmadeGunsCore;
import handmadeguns.entity.HMGEntityParticles;
import handmadeguns.client.render.HMGRenderItemGun_U;
import handmadeguns.client.render.HMGRenderItemGun_U_NEW;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;
import static handmadeguns.client.render.HMGRenderItemGun_U_NEW.*;

public class RenderTickSmoothing {
    public static float smooth = 0;

    public static boolean test_ReCreate = false;
    //todo onRenderTickStartでマウス感度を下げ、onRenderTickEndで復帰させればズーム時の照準が楽になるだろう
    @SubscribeEvent
    public void renderTick(TickEvent.RenderTickEvent event)
    {
        switch(event.phase)
        {
            case START :
                if(event.renderTickTime<1)
                HMGRenderItemGun_U.smoothing = event.renderTickTime;
                HMGRenderItemGun_U_NEW.smoothing = event.renderTickTime;
                HMGEntityParticles.particaltick = event.renderTickTime;
                smooth = event.renderTickTime;
                break;
            case END :
                if(test_ReCreate) {
                    test_ReCreate = false;

                }
                break;
        }
    }
    @SubscribeEvent
    public void playerTickEvent(TickEvent.PlayerTickEvent event)
    {

        switch(event.phase) {
        }
    }
    @SubscribeEvent
    public void clientTickEvent(TickEvent.ClientTickEvent event)
    {
        switch(event.phase)
        {
            case START :
                if(HMG_proxy.getEntityPlayerInstance() != null) {
                    EntityPlayer entityPlayer = HMG_proxy.getEntityPlayerInstance();
                    prevReloadState = firstPerson_ReloadState;
                    if(HMG_proxy.getEntityPlayerInstance().getCurrentEquippedItem() != null &&
                            HMG_proxy.getEntityPlayerInstance().getHeldItem().getItem() instanceof HMGItem_Unified_Guns){
                        ((HMGItem_Unified_Guns) HMG_proxy.getEntityPlayerInstance().getHeldItem().getItem()).checkTags(HMG_proxy.getEntityPlayerInstance().getHeldItem());
                        firstPerson_ReloadState = HMG_proxy.getEntityPlayerInstance().getHeldItem().getTagCompound().getBoolean("IsReloading");
                    }
                    prevSprintState = firstPerson_SprintState;
                    if(!firstPerson_ReloadState)firstPerson_SprintState = isentitysprinting(HMG_proxy.getEntityPlayerInstance());
                    else firstPerson_SprintState = false;

                    prevADSState = firstPerson_ADSState;
                    if(!firstPerson_ReloadState && !firstPerson_SprintState)firstPerson_ADSState = HandmadeGunsCore.Key_ADS((HMG_proxy.getEntityPlayerInstance()));
                    else firstPerson_ADSState = false;

                }
                break;
            case END :
//                if (HMG_proxy.getEntityPlayerInstance() != null) {
//                    EntityPlayer entityPlayer = HMG_proxy.getEntityPlayerInstance();
//                    if (entityPlayer.getEquipmentInSlot(0) != null && entityPlayer.getEquipmentInSlot(0).getItem() instanceof HMGItem_Unified_Guns) {
////                        System.out.println("debug");
//                    } else if (HMG_proxy.getCurrentAttributeModifier() != null) {
//                        System.out.println("debug");
//                        entityPlayer.getAttributeMap().removeAttributeModifiers(HMG_proxy.getCurrentAttributeModifier());
//                        HMG_proxy.setMoveModify_ByGun(null);
//                    }
//                }
                break;
        }
    }
}
