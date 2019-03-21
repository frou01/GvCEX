package hmgww2.event;

import java.util.List;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import hmgww2.Nation;
import hmgww2.entity.*;
import hmgww2.items.ItemIFFArmor;
import org.lwjgl.opengl.GL11;

import hmgww2.mod_GVCWW2;
import hmgww2.entity.EntityUSSRBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public class RenderHUDEvent {
	
	public static void RenderHUD(Minecraft minecraft, EntityPlayer entityplayer, ScaledResolution scaledresolution){
		int i = scaledresolution.getScaledWidth();
		int j = scaledresolution.getScaledHeight();
		FontRenderer fontrenderer = minecraft.fontRenderer;
		
		int s = 0;
		int tank = 0;
		int tankaa = 0;
		int tankspg = 0;
		int f = 0;
		int a = 0;
				
		
		
		if(entityplayer.getEquipmentInSlot(4) != null && entityplayer.getEquipmentInSlot(4).getItem() instanceof ItemIFFArmor) {
			
			Nation playernation = ((ItemIFFArmor) entityplayer.getEquipmentInSlot(4).getItem()).nation;
			List llist1 = entityplayer.worldObj.getEntitiesWithinAABBExcludingEntity(null,
					entityplayer.boundingBox.addCoord(entityplayer.motionX, entityplayer.motionY, entityplayer.motionZ).expand(40D, 30D, 40D));
			if (llist1 != null) {
				switch (playernation) {
					case JPN:
						for (int lj = 0; lj < llist1.size(); lj++) {
							Entity entity1 = (Entity) llist1.get(lj);
							if (entity1.canBeCollidedWith()) {
								if (entity1 instanceof EntityJPNBase && entity1 != null) {
									if (entity1 instanceof EntityJPN_S) {
										++s;
									}
									if (entity1 instanceof EntityJPN_Tank) {
										++tank;
									}
									if (entity1 instanceof EntityJPN_TankAA) {
										++tankaa;
									}
									if (entity1 instanceof EntityJPN_TankSPG) {
										++tankspg;
									}
									if (entity1 instanceof EntityJPN_Fighter) {
										++f;
									}
									if (entity1 instanceof EntityJPN_FighterA) {
										++a;
									}
								}
							}
						}
						rendericon(minecraft, mod_GVCWW2.bi_flag_jpn, s + tank + tankaa + tankspg + f + a, 0);
						rendericon(minecraft, mod_GVCWW2.spawn_jpn_s, s, 16);
						rendericon(minecraft, mod_GVCWW2.spawn_jpn_tank, tank, 32);
						rendericon(minecraft, mod_GVCWW2.spawn_jpn_tankaa, tankaa, 48);
						rendericon(minecraft, mod_GVCWW2.spawn_jpn_tankspg, tankspg, 64);
						rendericon(minecraft, mod_GVCWW2.spawn_jpn_fighter, f, 80);
						rendericon(minecraft, mod_GVCWW2.spawn_jpn_attcker, a, 96);
						break;
					case USA:
						for (int lj = 0; lj < llist1.size(); lj++) {
							Entity entity1 = (Entity) llist1.get(lj);
							if (entity1.canBeCollidedWith()) {
								if (entity1 instanceof EntityUSABase && entity1 != null) {
									if (entity1 instanceof EntityUSA_S) {
										++s;
									}
									if (entity1 instanceof EntityUSA_Tank) {
										++tank;
									}
									if (entity1 instanceof EntityUSA_TankAA) {
										++tankaa;
									}
									if (entity1 instanceof EntityUSA_TankSPG) {
										++tankspg;
									}
									if (entity1 instanceof EntityUSA_Fighter) {
										++f;
									}
									if (entity1 instanceof EntityUSA_FighterA) {
										++a;
									}
								}
							}
						}
						rendericon(minecraft, mod_GVCWW2.bi_flag_usa, s + tank + tankaa + tankspg + f + a, 0);
						rendericon(minecraft, mod_GVCWW2.spawn_usa_s, s, 16);
						rendericon(minecraft, mod_GVCWW2.spawn_usa_tank, tank, 32);
						rendericon(minecraft, mod_GVCWW2.spawn_usa_tankaa, tankaa, 48);
						rendericon(minecraft, mod_GVCWW2.spawn_usa_tankspg, tankspg, 64);
						rendericon(minecraft, mod_GVCWW2.spawn_usa_fighter, f, 80);
						rendericon(minecraft, mod_GVCWW2.spawn_usa_attcker, a, 96);
						break;
					case GER:
						for (int lj = 0; lj < llist1.size(); lj++) {
							Entity entity1 = (Entity) llist1.get(lj);
							if (entity1.canBeCollidedWith()) {
								if (entity1 instanceof EntityGERBase && entity1 != null) {
									if (entity1 instanceof EntityGER_S) {
										++s;
									}
									if (entity1 instanceof EntityGER_Tank) {
										++tank;
									}
									if (entity1 instanceof EntityGER_TankAA) {
										++tankaa;
									}
									if (entity1 instanceof EntityGER_TankSPG) {
										++tankspg;
									}
									if (entity1 instanceof EntityGER_Fighter) {
										++f;
									}
									if (entity1 instanceof EntityGER_FighterA) {
										++a;
									}
								}
							}
						}
						rendericon(minecraft, mod_GVCWW2.bi_flag_ger, s + tank + tankaa + tankspg + f + a, 0);
						rendericon(minecraft, mod_GVCWW2.spawn_ger_s, s, 16);
						rendericon(minecraft, mod_GVCWW2.spawn_ger_tank, tank, 32);
						rendericon(minecraft, mod_GVCWW2.spawn_ger_tankaa, tankaa, 48);
						rendericon(minecraft, mod_GVCWW2.spawn_ger_tankspg, tankspg, 64);
						rendericon(minecraft, mod_GVCWW2.spawn_ger_fighter, f, 80);
						rendericon(minecraft, mod_GVCWW2.spawn_ger_attcker, a, 96);
						break;
					case USSR:
						for (int lj = 0; lj < llist1.size(); lj++) {
							Entity entity1 = (Entity) llist1.get(lj);
							if (entity1.canBeCollidedWith()) {
								if (entity1 instanceof EntityUSSRBase && entity1 != null) {
									if (entity1 instanceof EntityUSSR_S) {
										++s;
									}
									if (entity1 instanceof EntityUSSR_Tank) {
										++tank;
									}
									if (entity1 instanceof EntityUSSR_TankAA) {
										++tankaa;
									}
									if (entity1 instanceof EntityUSSR_TankSPG) {
										++tankspg;
									}
									if (entity1 instanceof EntityUSSR_Fighter) {
										++f;
									}
									if (entity1 instanceof EntityUSSR_FighterA) {
										++a;
									}
								}
							}
						}
						rendericon(minecraft, mod_GVCWW2.bi_flag_rus, s + tank + tankaa + tankspg + f + a, 0);
						rendericon(minecraft, mod_GVCWW2.spawn_rus_s, s, 16);
						rendericon(minecraft, mod_GVCWW2.spawn_rus_tank, tank, 32);
						rendericon(minecraft, mod_GVCWW2.spawn_rus_tankaa, tankaa, 48);
						rendericon(minecraft, mod_GVCWW2.spawn_rus_tankspg, tankspg, 64);
						rendericon(minecraft, mod_GVCWW2.spawn_rus_fighter, f, 80);
						rendericon(minecraft, mod_GVCWW2.spawn_rus_attcker, a, 96);
						break;
				}
			}
		}
	}
	
	public static void rendericon(Minecraft minecraft, Item item, int kazu, int width){
		GuiIngame g  = minecraft.ingameGUI;
		FontRenderer fontrenderer = minecraft.fontRenderer;
		GL11.glPushMatrix();//21
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
		minecraft.getTextureManager().bindTexture(TextureMap.locationItemsTexture);
		{
			g.drawTexturedModelRectFromIcon(width, 16, item.getIconFromDamage(0), 16, 16);
			fontrenderer.drawStringWithShadow(String.format("%1$3d", kazu), width, 32, 0xFFFFFF);
		}
		GL11.glPopMatrix();//22
	}

}
