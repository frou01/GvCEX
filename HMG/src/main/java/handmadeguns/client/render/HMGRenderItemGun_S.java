package handmadeguns.client.render;

import handmadeguns.HandmadeGunsCore;
import handmadeguns.items.*;
import handmadeguns.items.guns.HMGItemSwordBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import handmadeguns.gui.HMGInventoryItem;
import littleMaidMobX.LMM_EntityLittleMaid;

public class HMGRenderItemGun_S implements IItemRenderer {
	private IModelCustom modeling;
	private static ResourceLocation texturesz;
	// public ModelBiped modelBipedMain;
	// public ModelBiped modelArmorChestplate;
	// public ModelBiped modelArmor;
	public float modelscala;
	public float modelhigh;

	public float modelhigh0;
	public float modelhigh1;
	public float modelhigh2;

	public float modelwidthx0;
	public float modelwidthz0;

	public float rotationx0;
	public float rotationy0;
	public float rotationz0;

	public HMGRenderItemGun_S(IModelCustom modelgun, ResourceLocation texture, float scala, float high, float high1,
							  float high2, float widthx0, float widthx1, float widthx2, float widthz0, float widthz1, float widthz2, float rotax0
			, float rotax1, float rotax2, float rotay0, float rotay1, float rotay2, float rotaz0, float rotaz1, float rotaz2,
							  boolean arm, float armrxr, float armryr, float armrzr, float offxr, float offyr, float offzr
			, float armrxl, float armryl, float armrzl, float offxl, float offyl, float offzl, float nx, float ny, float nz,
							  float m31px, float m31py, float m31pz, float m31rx, float m31ry, float m31rz
			,float m32px, float m32py, float m32pz, float m32rx, float m32ry, float m32rz) {
		modeling = modelgun;
		texturesz = texture;
		// skeletonTexturesz = texture;
		// this.modelBipedMain = new ModelBiped(0.5F);
		// this.modelBipedMain = (ModelBiped)this.modelBipedMain;
		// this.modelArmorChestplate = new ModelBiped(1.0F);
		// this.modelArmor = new ModelBiped(0.5F);
		this.modelscala = scala;
		this.modelhigh = high;
		this.modelhigh0 = high;
		modelwidthx0 = widthx0;
		modelwidthz0 = widthz0;
	}

	/*
	 * public void doRender(EntityItem p_76986_1_, double p_76986_2_, double
	 * p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
	 * this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow =
	 * this.modelBipedMain.aimedBow = true;
	 * super.doRender((EntityItem)p_76986_1_, p_76986_2_, p_76986_4_,
	 * p_76986_6_, p_76986_8_, p_76986_9_); }
	 */
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {

		switch (type) {
			// case 1:
			case INVENTORY:
			case FIRST_PERSON_MAP:
				return false;
			case EQUIPPED_FIRST_PERSON:
			case ENTITY:
			case EQUIPPED:
				return true;
		}
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {

		switch (type) {
			// case 1: //entity third person
			case INVENTORY:
			case FIRST_PERSON_MAP:
				return false;
			case EQUIPPED_FIRST_PERSON:
			case ENTITY:
			case EQUIPPED:
				return true;
		}
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		float scala = this.modelscala;
		float scala2 = this.modelscala;

		HMGItemSwordBase gun = (HMGItemSwordBase) item.getItem();
		float reco;

		switch (type) {
			case EQUIPPED_FIRST_PERSON:
				// case 3:
				Entity entity = Minecraft.getMinecraft().thePlayer;
				EntityPlayer entityplayer = (EntityPlayer) entity;
				if (entityplayer.isSneaking()) {
					if (item.getItemDamage() == item.getMaxDamage()) {
						glMatrixForRenderInEquipped_reload();
					} else {

						// NBTTagCompound nbt = item.getTagCompound();
						// boolean re = nbt.getBoolean("recoil");
						{
							reco = -1.4F;
						}
						glMatrixForRenderInEquippedADS(reco);
					}

				} else {
					if (item.getItemDamage() == item.getMaxDamage()) {
						glMatrixForRenderInEquipped_reload();
					} else {
						{
							reco = -0.2F;
						}
						glMatrixForRenderInEquipped(reco);
					}

				}
				GL11.glPushMatrix();
				GL11.glScalef(scala, scala, scala);
//			GL11.glTranslatef(0.0F, 1.5F, 0.0F);
				if (item.getItemDamage() == item.getMaxDamage()) {
					Minecraft.getMinecraft().renderEngine.bindTexture(texturesz);
				} else {
					Minecraft.getMinecraft().renderEngine.bindTexture(texturesz);
				}

				if (item.getItemDamage() == item.getMaxDamage()) {
					// modeling.renderAll();
					modeling.renderPart("mat1");

					GL11.glTranslatef(0.0F, 0.0F, -0.4F);
					modeling.renderPart("mat2");
					// GL11.glRotatef(30F, 1.0F, 0.0F, 0.0F);
					// modeling.renderPart("mat2");
				} else {
					{
						modeling.renderPart("mat1");
						modeling.renderPart("mat2");
						modeling.renderPart("mat3");
					}
				}

				GL11.glPopMatrix();
				break;
			case EQUIPPED:
				if (item.getItemDamage() == item.getMaxDamage()) {
					Minecraft.getMinecraft().renderEngine.bindTexture(texturesz);
				} else {
					Minecraft.getMinecraft().renderEngine.bindTexture(texturesz);
				}

				if ((data[1] != null) && ((data[1] instanceof EntityPlayer))) {
					{
						reco = -0.7F;
					}
					glMatrixForRenderInEntityPlayer(reco);
					GL11.glPushMatrix();
					GL11.glScalef(1f/2f, 1f/2f, 1f/2f);
					GL11.glScalef(scala, scala, scala);

					Entity entity1 = Minecraft.getMinecraft().thePlayer;
					EntityPlayer entityplayer1 = (EntityPlayer) entity1;
					InventoryPlayer playerInv1 = entityplayer1.inventory;

					if (item.getItemDamage() == item.getMaxDamage()) {
						// modeling.renderAll();
						modeling.renderPart("mat1");

						GL11.glTranslatef(0.0F, 0.0F, -0.4F);
						modeling.renderPart("mat2");
						// GL11.glRotatef(30F, 1.0F, 0.0F, 0.0F);
						// modeling.renderPart("mat2");
					} else {
						{
							modeling.renderPart("mat1");
							modeling.renderPart("mat2");
							modeling.renderPart("mat3");
						}
					}

				} else {
					{
						reco = 0F;
					}
					glMatrixForRenderInEntity(reco);
					GL11.glPushMatrix();
					if (HandmadeGunsCore.cfg_RenderGunSizeLMM) {
						if ((data[1] != null) && ((data[1] instanceof LMM_EntityLittleMaid))) {
							GL11.glScalef(scala - 0.25F, scala - 0.25F, scala - 0.25F);
							GL11.glTranslatef(0.5F, 1.0F, -0.1F);
							// glMatrixForRenderInEntityLMM(reco);
						} else {
							GL11.glScalef(scala - 0.2F, scala - 0.2F, scala - 0.2F);
							// glMatrixForRenderInEntity(reco);
						}
					} else {
						GL11.glScalef(scala - 0.2F, scala - 0.2F, scala - 0.2F);
						// glMatrixForRenderInEntity(reco);
					}

					if (item.getItemDamage() == item.getMaxDamage()) {
						// modeling.renderAll();
						modeling.renderPart("mat1");
						GL11.glTranslatef(0.0F, 0.0F, -0.4F);
						modeling.renderPart("mat2");
						// GL11.glRotatef(30F, 1.0F, 0.0F, 0.0F);
						// modeling.renderPart("mat2");
					} else {
						modeling.renderPart("mat1");
						modeling.renderPart("mat2");
						modeling.renderPart("mat3");

					}

				}

				// modelBipedMain.aimedBow = true;
				GL11.glPopMatrix();
				break;

			case ENTITY:


				Minecraft.getMinecraft().renderEngine.bindTexture(texturesz);

				{
					{
						reco = 0F;
					}
					GL11.glPushMatrix();
					GL11.glScalef((0.4f * modelscala),(0.4f * modelscala),(0.4f * modelscala));
					GL11.glRotatef(90,1,0,0);

					if (item.getItemDamage() == item.getMaxDamage()) {
						// modeling.renderAll();
						modeling.renderPart("mat1");
						GL11.glTranslatef(0.0F, 0.0F, -0.4F);
						modeling.renderPart("mat2");
						// GL11.glRotatef(30F, 1.0F, 0.0F, 0.0F);
						// modeling.renderPart("mat2");
					} else {
						modeling.renderPart("mat1");
						modeling.renderPart("mat2");
						modeling.renderPart("mat3");

					}

				}

				// modelBipedMain.aimedBow = true;
				GL11.glPopMatrix();
				break;
		}

	}
	public void renderatunder(ItemRenderType type, ItemStack item, Object... data) {
		float scala = this.modelscala;

		HMGItemSwordBase gun = (HMGItemSwordBase) item.getItem();
		float reco;
		{
			// case 3:
			Entity entity = Minecraft.getMinecraft().thePlayer;
			EntityPlayer entityplayer = (EntityPlayer) entity;
			if (entityplayer.isSneaking()) {
				if (item.getItemDamage() == item.getMaxDamage()) {
				} else {

					// NBTTagCompound nbt = item.getTagCompound();
					// boolean re = nbt.getBoolean("recoil");
					{
						reco = -1.4F;
					}
				}

			} else {
				if (item.getItemDamage() == item.getMaxDamage()) {
				} else {
					{
						reco = -0.2F;
					}
				}

			}
			GL11.glScalef(scala, scala, scala);
//			GL11.glTranslatef(0.0F, 1.5F, 0.0F);
			if (item.getItemDamage() == item.getMaxDamage()) {
				Minecraft.getMinecraft().renderEngine.bindTexture(texturesz);
			} else {
				Minecraft.getMinecraft().renderEngine.bindTexture(texturesz);
			}

			InventoryPlayer playerInv = entityplayer.inventory;
			HMGInventoryItem inventory = new HMGInventoryItem(playerInv, item);
			ItemStack itemstack = ((EntityPlayer) (entityplayer)).getCurrentEquippedItem();

			for (int i1 = 0; i1 < inventory.getSizeInventory(); ++i1) {
				ItemStack itemstacki = inventory.getStackInSlot(i1);

				if (i1 == 1) {
					if (itemstacki == null) {
						modelhigh = modelhigh0;
					} else if (itemstacki != null && itemstacki.getItem() instanceof HMGItemAttachment_reddot) {
						modelhigh = modelhigh1;
					} else if (itemstacki != null && itemstacki.getItem() instanceof HMGItemAttachment_scope) {
						modelhigh = modelhigh2;
					}
				}
			}
			if (item.getItemDamage() == item.getMaxDamage()) {
				// modeling.renderAll();
				modeling.renderPart("mat1");

				for (int i1 = 0; i1 < inventory.getSizeInventory(); ++i1) {
					ItemStack itemstacki = inventory.getStackInSlot(i1);

					if (i1 == 1) {
						if (itemstacki != null && itemstacki.getItem() instanceof HMGItemAttachment_reddot) {
							modeling.renderPart("mat4");
						} else if (itemstacki != null && itemstacki.getItem() instanceof HMGItemAttachment_scope) {
							modeling.renderPart("mat5");
						}
					}
					if (i1 == 2) {
						if (itemstacki != null && itemstacki.getItem() instanceof HMGItemAttachment_laser) {
							modeling.renderPart("mat6");
						} else if (itemstacki != null && itemstacki.getItem() instanceof HMGItemAttachment_light) {
							modeling.renderPart("mat7");
						}
					}
					if (i1 == 3) {
						if (itemstacki != null && itemstacki.getItem() instanceof HMGItemAttachment_Suppressor) {
							modeling.renderPart("mat8");
						}
					}
					if (i1 == 4) {
						if (itemstacki != null && itemstacki.getItem() instanceof HMGItemAttachment_grip) {
							modeling.renderPart("mat9");
						}
					}
				}

				GL11.glTranslatef(0.0F, 0.0F, -0.4F);
				modeling.renderPart("mat2");
				// GL11.glRotatef(30F, 1.0F, 0.0F, 0.0F);
				// modeling.renderPart("mat2");
			} else {
				{
					modeling.renderPart("mat1");
					modeling.renderPart("mat2");
					modeling.renderPart("mat3");

					for (int i1 = 0; i1 < inventory.getSizeInventory(); ++i1) {
						ItemStack itemstacki = inventory.getStackInSlot(i1);

						if (i1 == 1) {
							if (itemstacki != null && itemstacki.getItem() instanceof HMGItemAttachment_reddot) {
								modeling.renderPart("mat4");
							} else if (itemstacki != null && itemstacki.getItem() instanceof HMGItemAttachment_scope) {
								modeling.renderPart("mat5");
							}
						}
						if (i1 == 2) {
							if (itemstacki != null && itemstacki.getItem() instanceof HMGItemAttachment_laser) {
								modeling.renderPart("mat6");
							} else if (itemstacki != null && itemstacki.getItem() instanceof HMGItemAttachment_light) {
								modeling.renderPart("mat7");
							}
						}
						if (i1 == 3) {
							if (itemstacki != null && itemstacki.getItem() instanceof HMGItemAttachment_Suppressor) {
								modeling.renderPart("mat8");
							}
						}
						if (i1 == 4) {
							if (itemstacki != null && itemstacki.getItem() instanceof HMGItemAttachment_grip) {
								modeling.renderPart("mat9");
							}
						}
					}
				}
			}

		}
	}

	public void glMatrixForRenderInInventory() {
		GL11.glRotatef(15F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(-30F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(1900F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-0.8F, -1.2F, -0.1F);
	}

	public void glMatrixForRenderInEquipped(float reco) {
		GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(50F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-0.7F, 0.7F, reco);// -0.2F
		// GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
		// GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
		// GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		// GL11.glTranslatef(-0.7F, 1.0F, -0.2F);
	}

	public void glMatrixForRenderInEquipped_reload() {
		GL11.glRotatef(190F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(40F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-0.7F, 0.4F, -0.2F);
		// GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
		// GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
		// GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		// GL11.glTranslatef(-0.7F, 1.0F, -0.2F);
	}

	public void glMatrixForRenderInEquippedADS(float reco) {
		// GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
		// GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
		// GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		// GL11.glTranslatef(0.694F, 1.2F, -0.2F);
		GL11.glRotatef(rotationx0, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(rotationy0, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(rotationz0, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(modelwidthx0, modelhigh, modelwidthz0);// 0.694,1.03,-1.0//-1.4F
	}

	public void glMatrixForRenderInEntity(float reco) {
		GL11.glRotatef(190F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-0.7F, 0.7F, reco);
		// GL11.glRotatef(110F, 1.0F, 0.0F, 0.0F);//90
		// GL11.glRotatef(0F, 0.0F, 1.0F, 0.0F);//0
		// GL11.glRotatef(120F, 0.0F, 0.0F, 1.0F);//130
		// GL11.glTranslatef(0.2F, -1.0F, -0.7F);
	}

	public void glMatrixForRenderInEntityPlayer(float reco) {
		GL11.glRotatef(110F, 1.0F, 0.0F, 0.0F);// 90
		GL11.glRotatef(0F, 0.0F, 1.0F, 0.0F);// 0
		GL11.glRotatef(120F, 0.0F, 0.0F, 1.0F);// 130
		GL11.glTranslatef(0.2F, -1.0F, reco);
	}
}
