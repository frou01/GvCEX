package handmadeguns.event;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;

public class GunSoundEvent extends Event{
    public Entity targetentity;
    public GunSoundEvent(Entity entity){
        targetentity = entity;
    }
    public static void post(Entity entity){
        MinecraftForge.EVENT_BUS.post(new GunSoundEvent(entity));
    }
}
