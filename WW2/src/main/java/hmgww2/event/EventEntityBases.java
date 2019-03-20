package hmgww2.event;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gvclib.entity.EntityBases;
import hmgww2.mod_GVCWW2;
import hmgww2.entity.EntityGER_S;
import hmgww2.entity.EntityJPNBase;
import hmgww2.entity.EntityJPN_S;
import hmgww2.entity.EntityRUS_S;
import hmgww2.entity.EntityUSABase;
import hmgww2.entity.EntityUSA_S;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EventEntityBases {

	private static final ResourceLocation m0 = new ResourceLocation("hmgww2:textures/marker/marker.png");
	private static final ResourceLocation m2 = new ResourceLocation("hmgww2:textures/marker/marker2.png");
	private static final ResourceLocation m3 = new ResourceLocation("hmgww2:textures/marker/marker3.png");
	private static final ResourceLocation m4 = new ResourceLocation("hmgww2:textures/marker/marker4.png");
	private static final IModelCustom tankk = AdvancedModelLoader
			.loadModel(new ResourceLocation("hmgww2:textures/marker/marker.obj"));

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void rendergunthird(RenderLivingEvent.Post event) {
		EntityLivingBase entity = (EntityLivingBase) event.entity;
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer entityplayer = minecraft.thePlayer;
		if (entityplayer.getEquipmentInSlot(4) != null
				&& (entityplayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_jpn)) {//he
			if (entity != null && entity instanceof EntityJPN_S) {// item
				EntityJPN_S mob = (EntityJPN_S) entity;
				GL11.glPushMatrix();
				//GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
				GL11.glTranslatef((float) event.x, (float) event.y, (float) event.z);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				if (entity.getHealth() > 0.0F) {
					if (mob.getFlagMode() == 0) {
						GL11.glTranslatef(0, mob.height + 0.5F, 0);
						Minecraft.getMinecraft().renderEngine.bindTexture(m0);
						tankk.renderPart("mat1");
					}
					if (mob.getFlagMode() == 2) {
						GL11.glTranslatef(0, mob.height + 0.5F, 0);
						Minecraft.getMinecraft().renderEngine.bindTexture(m2);
						tankk.renderPart("mat1");
					}
					if (mob.getFlagMode() == 3) {
						GL11.glTranslatef(0, mob.height + 0.5F, 0);
						Minecraft.getMinecraft().renderEngine.bindTexture(m3);
						tankk.renderPart("mat1");
					}
					if (mob.getFlagMode() == 4) {
						GL11.glTranslatef(0, mob.height + 0.5F, 0);
						Minecraft.getMinecraft().renderEngine.bindTexture(m4);
						tankk.renderPart("mat1");
					}
				}
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
				//GL11.glPopAttrib();
				GL11.glPopMatrix();
			} // item
		}//he
		if (entityplayer.getEquipmentInSlot(4) != null
				&& (entityplayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_usa)) {//he
			if (entity != null && entity instanceof EntityUSA_S) {// item
				EntityUSA_S mob = (EntityUSA_S) entity;
				GL11.glPushMatrix();
				//GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
				GL11.glTranslatef((float) event.x, (float) event.y, (float) event.z);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				if (entity.getHealth() > 0.0F) {
					if (mob.getFlagMode() == 0) {
						GL11.glTranslatef(0, mob.height + 0.5F, 0);
						Minecraft.getMinecraft().renderEngine.bindTexture(m0);
						tankk.renderPart("mat1");
					}
					if (mob.getFlagMode() == 2) {
						GL11.glTranslatef(0, mob.height + 0.5F, 0);
						Minecraft.getMinecraft().renderEngine.bindTexture(m2);
						tankk.renderPart("mat1");
					}
					if (mob.getFlagMode() == 3) {
						GL11.glTranslatef(0, mob.height + 0.5F, 0);
						Minecraft.getMinecraft().renderEngine.bindTexture(m3);
						tankk.renderPart("mat1");
					}
					if (mob.getFlagMode() == 4) {
						GL11.glTranslatef(0, mob.height + 0.5F, 0);
						Minecraft.getMinecraft().renderEngine.bindTexture(m4);
						tankk.renderPart("mat1");
					}
				}
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
				//GL11.glPopAttrib();
				GL11.glPopMatrix();
			} // item
		}//he
		if (entityplayer.getEquipmentInSlot(4) != null
				&& (entityplayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_ger)) {//he
			if (entity != null && entity instanceof EntityGER_S) {// item
				EntityGER_S mob = (EntityGER_S) entity;
				GL11.glPushMatrix();
				//GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
				GL11.glTranslatef((float) event.x, (float) event.y, (float) event.z);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				if (entity.getHealth() > 0.0F) {
					if (mob.getFlagMode() == 0) {
						GL11.glTranslatef(0, mob.height + 0.5F, 0);
						Minecraft.getMinecraft().renderEngine.bindTexture(m0);
						tankk.renderPart("mat1");
					}
					if (mob.getFlagMode() == 2) {
						GL11.glTranslatef(0, mob.height + 0.5F, 0);
						Minecraft.getMinecraft().renderEngine.bindTexture(m2);
						tankk.renderPart("mat1");
					}
					if (mob.getFlagMode() == 3) {
						GL11.glTranslatef(0, mob.height + 0.5F, 0);
						Minecraft.getMinecraft().renderEngine.bindTexture(m3);
						tankk.renderPart("mat1");
					}
					if (mob.getFlagMode() == 4) {
						GL11.glTranslatef(0, mob.height + 0.5F, 0);
						Minecraft.getMinecraft().renderEngine.bindTexture(m4);
						tankk.renderPart("mat1");
					}
				}
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
				//GL11.glPopAttrib();
				GL11.glPopMatrix();
			} // item
		}//he
	if (entityplayer.getEquipmentInSlot(4) != null
			&& (entityplayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_rus)) {//he
		if (entity != null && entity instanceof EntityRUS_S) {// item
			EntityRUS_S mob = (EntityRUS_S) entity;
			GL11.glPushMatrix();
			//GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
			GL11.glTranslatef((float) event.x, (float) event.y, (float) event.z);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			if (entity.getHealth() > 0.0F) {
				if (mob.getFlagMode() == 0) {
					GL11.glTranslatef(0, mob.height + 0.5F, 0);
					Minecraft.getMinecraft().renderEngine.bindTexture(m0);
					tankk.renderPart("mat1");
				}
				if (mob.getFlagMode() == 2) {
					GL11.glTranslatef(0, mob.height + 0.5F, 0);
					Minecraft.getMinecraft().renderEngine.bindTexture(m2);
					tankk.renderPart("mat1");
				}
				if (mob.getFlagMode() == 3) {
					GL11.glTranslatef(0, mob.height + 0.5F, 0);
					Minecraft.getMinecraft().renderEngine.bindTexture(m3);
					tankk.renderPart("mat1");
				}
				if (mob.getFlagMode() == 4) {
					GL11.glTranslatef(0, mob.height + 0.5F, 0);
					Minecraft.getMinecraft().renderEngine.bindTexture(m4);
					tankk.renderPart("mat1");
				}
			}
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			//GL11.glPopAttrib();
			GL11.glPopMatrix();
		} // item
	}//he
	}
}
