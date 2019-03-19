package handmadeguns.client.render;

import cpw.mods.fml.client.FMLClientHandler;
import handmadeguns.entity.PlacedGunEntity;
import handmadeguns.items.HMGItemAttachment_reddot;
import handmadeguns.items.HMGItemAttachment_scope;
import handmadeguns.items.HMGItemSightBase;
import handmadeguns.HandmadeGunsCore;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import static net.minecraft.util.MathHelper.wrapAngleTo180_float;

public class PlacedGun_Render extends Render {
    @Override
    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        doRender((PlacedGunEntity)p_76986_1_,p_76986_2_,p_76986_4_,p_76986_6_,p_76986_8_,p_76986_9_);
    }
    public void doRender(PlacedGunEntity entity, double p_180551_2_, double p_180551_4_, double p_180551_6_, float p_180551_8_, float p_180551_9_) {
        float lastBrightnessX = OpenGlHelper.lastBrightnessX;
        float lastBrightnessY = OpenGlHelper.lastBrightnessY;
        if (entity.riddenByEntity == FMLClientHandler.instance().getClientPlayerEntity() && entity.gunStack != null && entity.gunItem != null) {
            ItemStack[] items = new ItemStack[6];
            NBTTagCompound nbt = entity.gunStack.getTagCompound();
            if (nbt == null) entity.gunItem.checkTags(entity.gunStack);
            nbt = entity.gunStack.getTagCompound();
            items[0] = null;
            items[1] = null;//サイト
            items[2] = null;//レーザーサイト他
            items[3] = null;//マズルアタッチメント
            items[4] = null;//アンダーバレル
            items[5] = null;//マガジン
            NBTTagList tags = (NBTTagList) nbt.getTag("Items");
            if (tags != null) {
                for (int i = 0; i < tags.tagCount(); i++)//133
                {
                    NBTTagCompound tagCompound = tags.getCompoundTagAt(i);
                    int slot = tagCompound.getByte("Slot");
                    if (slot >= 0 && slot < items.length) {
                        items[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
                    }
                }
            }
            ItemStack itemstackSight = items[1];
            if (HandmadeGunsCore.Key_ADS(entity.riddenByEntity)) {
                if (itemstackSight != null && itemstackSight.getItem() instanceof HMGItemSightBase) {
                    if (((HMGItemSightBase) itemstackSight.getItem()).scopeonly) {
                        return;
                    } else if (itemstackSight.getItem() instanceof HMGItemAttachment_reddot) {
                        if (!entity.gunItem.zoomrer) {
                            return;
                        }
                    } else if (itemstackSight.getItem() instanceof HMGItemAttachment_scope) {
                        if (!entity.gunItem.zoomres) {
                            return;
                        }
                    }
                } else {
                    if (!entity.gunItem.zoomren) {
                        return;
                    }
                }
            }
        }
        GL11.glPushMatrix();
        GL11.glTranslatef((float) p_180551_2_, (float) p_180551_4_ + entity.gunyoffset, (float) p_180551_6_);
        if (entity.gunStack != null) {
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            IItemRenderer gunrender = MinecraftForgeClient.getItemRenderer(entity.gunStack, IItemRenderer.ItemRenderType.EQUIPPED);
            if (gunrender instanceof HMGRenderItemGun_U_NEW) {
                GL11.glRotatef(-(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * p_180551_9_), 0.0F, 1.0F, 0.0F);
                ((HMGRenderItemGun_U_NEW) gunrender).isPlacedGun = true;
                ((HMGRenderItemGun_U_NEW) gunrender).turretYaw = wrapAngleTo180_float((entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * p_180551_9_) - (entity.prevrotationYawGun + (entity.rotationYawGun - entity.prevrotationYawGun) * p_180551_9_));
                ((HMGRenderItemGun_U_NEW) gunrender).turretPitch = wrapAngleTo180_float((entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * p_180551_9_));
                GL11.glScalef(0.5f, 0.5f, 0.5f);
                gunrender.renderItem(IItemRenderer.ItemRenderType.ENTITY, entity.gunStack);
                ((HMGRenderItemGun_U_NEW) gunrender).isPlacedGun = false;
            } else if (gunrender instanceof HMGRenderItemGun_U) {
                //base は matbase を利用してそれらしく描画可能
                GL11.glRotatef(-(entity.prevrotationYawGun + (entity.rotationYawGun - entity.prevrotationYawGun) * p_180551_9_), 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-(-entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * p_180551_9_), 1.0F, 0.0F, 0.0F);
                GL11.glScalef(0.5f, 0.5f, 0.5f);
                gunrender.renderItem(IItemRenderer.ItemRenderType.ENTITY, entity.gunStack);
            }
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
        GL11.glPopMatrix();
    }
    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return null;
    }
}
