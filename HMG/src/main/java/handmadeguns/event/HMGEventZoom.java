package handmadeguns.event;

import handmadeguns.HandmadeGunsCore;
import handmadeguns.Util.EntityLinkedPos_Motion;
import handmadeguns.entity.PlacedGunEntity;
import handmadeguns.items.*;
import handmadeguns.items.guns.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;
import org.lwjgl.opengl.GL11;


import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import org.lwjgl.util.glu.Project;

import javax.vecmath.Vector3d;

import java.util.ArrayList;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;
import static handmadevehicle.Utils.RotateVectorAroundX;
import static handmadevehicle.Utils.RotateVectorAroundY;
import static java.lang.Math.sin;
import static net.minecraft.client.gui.Gui.icons;
import static org.lwjgl.opengl.GL11.*;

public class HMGEventZoom {
	static boolean updated = false;

	// public HMGItemGunBase gunbase;

	public boolean zoomtype;
	public Item itemss;
	public boolean needreset = false;
	public boolean slot;
	public int targetEntityID = -1;
	ResourceLocation crosstex = new ResourceLocation("handmadeguns:textures/items/crosshair.png");
	ResourceLocation pointer = new ResourceLocation("handmadeguns:textures/entity/laser.png");
	public ItemStack previtemstack;
	public static boolean isSlowdowned;
	//1tick遅れて低速化が有効になるためフラグを3つ使う
	public static boolean isSlowdowned2;
	public static boolean isSlowdowned3;
	private double premotion;

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderfov(FOVUpdateEvent event)
	{
		//Minecraft minecraft = FMLClientHandler.instance().getClient();
		//EntityPlayer entityplayer = minecraft.thePlayer;
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			EntityPlayer entityplayer = event.entity;
			ItemStack itemstack = entityplayer.getCurrentEquippedItem();
			Entity ridingEntity = entityplayer.ridingEntity;
			if(ridingEntity instanceof PlacedGunEntity){
				if(((PlacedGunEntity) ridingEntity).gunStack != null  && ((PlacedGunEntity) ridingEntity).gunStack.getItem() instanceof HMGItem_Unified_Guns){
					itemstack = ((PlacedGunEntity) ridingEntity).gunStack;
				}
			}
			if (itemstack != null && itemstack.getItem() instanceof HMGItem_Unified_Guns) {
				HMGItem_Unified_Guns gunbase = (HMGItem_Unified_Guns) itemstack.getItem();
				if (HandmadeGunsCore.Key_ADS(entityplayer))
				{
					((HMGItem_Unified_Guns) itemstack.getItem()).checkTags(itemstack);
					ItemStack[] items = new ItemStack[6];
					ItemStack itemstackSight =null;
					NBTTagList tags = (NBTTagList) itemstack.getTagCompound().getTag("Items");
					if (tags != null) {
						for (int i = 0; i < 7; i++)//133
						{
							NBTTagCompound tagCompound = tags.getCompoundTagAt(i);
							int slot = tagCompound.getByte("Slot");
							if (slot >= 0 && slot < items.length) {
								items[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
//                    if(items[slot] != null) {
//                        System.out.println(""+ i + "" + items[slot].getItem().getUnlocalizedName());
//                    }
							}
						}
					}
					itemstackSight = items[1];
					if(itemstackSight != null) {
						if (itemstackSight.getItem() instanceof HMGItemAttachment_reddot) {
							if (gunbase.gunInfo.canobj && gunbase.gunInfo.zoomrer) {
								event.newfov = event.fov / gunbase.gunInfo.scopezoomred;
							}
						} else if (itemstackSight.getItem() instanceof HMGItemAttachment_scope) {
							if (gunbase.gunInfo.canobj && gunbase.gunInfo.zoomres) {
								event.newfov = event.fov / gunbase.gunInfo.scopezoomscope;
							}
						} else if (itemstackSight.getItem() instanceof HMGItemSightBase) {
							if (gunbase.gunInfo.canobj && !((HMGItemSightBase) itemstackSight.getItem()).scopeonly) {
								event.newfov = event.fov / ((HMGItemSightBase) itemstackSight.getItem()).zoomlevel;
							}
						}else {
							if (gunbase.gunInfo.canobj && gunbase.gunInfo.zoomren) {
								event.newfov = event.fov / gunbase.gunInfo.scopezoombase;
							}
						}
					} else {
						if (gunbase.gunInfo.canobj && gunbase.gunInfo.zoomren) {
							event.newfov = event.fov / gunbase.gunInfo.scopezoombase;
						}
					}
				}
				if(entityplayer.capabilities.isFlying){
					event.newfov /=1.1F;
				}
//				System.out.println(entityplayer.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
//				if(isSlowdowned3)event.newfov /= ((gunbase.motion <= 0 ? 0 : gunbase.motion) + 1 )/2;
//				premotion = gunbase.motion;
			}else {
				event.newfov = event.fov;
//				if(isSlowdowned3)event.newfov /= ((premotion <= 0 ? 0 : premotion) + 1 )/2;
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void displayHUD(RenderGameOverlayEvent.Post event) {
		if (event.type == RenderGameOverlayEvent.ElementType.ALL) {
			Minecraft minecraft = FMLClientHandler.instance().getClient();
			ScaledResolution scaledresolution = new ScaledResolution(minecraft, minecraft.displayWidth,
					minecraft.displayHeight);
			float screenWidth = scaledresolution.getScaledWidth();
			float screenHeight = scaledresolution.getScaledHeight();
			// Entity entity = minecraft.pointedEntity;
			EntityPlayer entityplayer = minecraft.thePlayer;
			// EntityPlayer entityplayer = event.player;




			ItemStack gunstack = ((entityplayer)).getCurrentEquippedItem();

			Entity ridingEntity = entityplayer.ridingEntity;
			if(ridingEntity instanceof PlacedGunEntity) {
				if (((PlacedGunEntity) ridingEntity).gunStack != null && ((PlacedGunEntity) ridingEntity).gunStack.getItem() instanceof HMGItem_Unified_Guns) {
					gunstack = ((PlacedGunEntity) ridingEntity).gunStack;
				}
			}
			if (gunstack != previtemstack) {
				ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer,
						1.0d, "cameraZoom", "field_78503_V");
				needreset = true;
			}
			// FontRenderer fontrenderer = minecraft.fontRenderer;
			// minecraft.entityRenderer.setupOverlayRendering();
			// OpenGlHelper.
			GL11.glEnable(GL11.GL_BLEND);
			if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
				if (gunstack != null && gunstack.getItem() instanceof HMGItem_Unified_Guns) {
					HMGItem_Unified_Guns gunItem = (HMGItem_Unified_Guns) gunstack.getItem();
					String ads = gunItem.gunInfo.adstexture;
					String adsr = gunItem.gunInfo.adstexturer;
					String adss = gunItem.gunInfo.adstextures;
					((HMGItem_Unified_Guns) gunstack.getItem()).checkTags(gunstack);
					NBTTagCompound nbt = gunstack.getTagCompound();
					//String ads = nbt.getString("adstexture");
					EntityRenderer entityrender = minecraft.entityRenderer;
					ItemRenderer itemrender = entityrender.itemRenderer;
					//itemrender.
					targetEntityID = -1;
					boolean recoiled = nbt.getBoolean("Recoiled");
					if (nbt.getBoolean("islockedentity")) {
						targetEntityID = nbt.getInteger("TGT");
					}
					float spreadDiffusion = nbt.getFloat("Diffusion");
					float bure = gunItem.gunInfo.spread_setting;
					bure *= HandmadeGunsCore.Key_ADS(entityplayer) ? gunItem.gunInfo.ads_spread_cof : 1;
					bure += gunItem.gunInfo.spread_setting * spreadDiffusion;
					((HMGItem_Unified_Guns) gunstack.getItem()).checkTags(gunstack);
					ItemStack[] items = new ItemStack[6];
					ItemStack itemstackSight = null;
					NBTTagList tags = (NBTTagList) gunstack.getTagCompound().getTag("Items");
					if (tags != null) {
						for (int i1 = 0; i1 < 7; i1++)//133
						{
							NBTTagCompound tagCompound = tags.getCompoundTagAt(i1);
							int slot = tagCompound.getByte("Slot");
							if (slot >= 0 && slot < items.length) {
								items[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
//                    if(items[slot] != null) {
//                        System.out.println(""+ screenWidth + "" + items[slot].getItem().getUnlocalizedName());
//                    }
							}
						}
					}
					itemstackSight = items[1];


					setUp3DView(minecraft,event);
					GL11.glPushMatrix();
					{
						GL11.glRotatef(0, 1.0F, 0.0F, 0.0F);
						GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
						// this.modelArmor.aimedBow = true;

						//if (entityplayer.isSneaking())

						if (HandmadeGunsCore.Key_ADS(entityplayer)) {
							if (itemstackSight != null) {
								if (itemstackSight.getItem() instanceof HMGItemAttachment_reddot) {
									if (!gunItem.gunInfo.canobj || !gunItem.gunInfo.zoomrer) {
										ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer,
												gunItem.gunInfo.scopezoomred, "cameraZoom", "field_78503_V");
										needreset = true;
									}
									if (gunItem.gunInfo.zoomrert) {
										renderPumpkinBlur(minecraft, adsr);
									}
								} else if (itemstackSight.getItem() instanceof HMGItemAttachment_scope) {
									if (!gunItem.gunInfo.canobj || !gunItem.gunInfo.zoomres) {
										ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer,
												gunItem.gunInfo.scopezoomscope, "cameraZoom", "field_78503_V");
										needreset = true;
									}
									if (gunItem.gunInfo.zoomrest) {
										renderPumpkinBlur(minecraft, adss);
									}
								} else if (itemstackSight.getItem() instanceof HMGItemSightBase) {
									if (!gunItem.gunInfo.canobj || ((HMGItemSightBase) itemstackSight.getItem()).scopeonly) {
										ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer,
												((HMGItemSightBase) itemstackSight.getItem()).zoomlevel, "cameraZoom", "field_78503_V");
										needreset = true;
									}
									if (((HMGItemSightBase) itemstackSight.getItem()).scopetexture != null) {
										renderPumpkinBlur(minecraft, ((HMGItemSightBase) itemstackSight.getItem()).scopetexture);
									}
								}
							} else {
								if (!gunItem.gunInfo.canobj || !gunItem.gunInfo.zoomren) {
									ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer,
											gunItem.gunInfo.scopezoombase, "cameraZoom", "field_78503_V");
									needreset = true;
								}
								if (gunItem.gunInfo.zoomrent) {
									renderPumpkinBlur(minecraft, ads);
								}
							}
							if (gunItem.gunInfo.renderMCcross) {
								GuiIngameForge.renderCrosshairs = true;
							} else {
								GuiIngameForge.renderCrosshairs = false;
								GL11.glEnable(GL11.GL_BLEND);
							}
							if (gunItem.gunInfo.renderHMGcross && spreadDiffusion > gunItem.gunInfo.spreadDiffusionmin)
								this.renderCrossHair(minecraft, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight(), bure);
						}
						else {
							// GuiIngameForge.renderCrosshairs = true;
							if (gunItem.gunInfo.renderMCcross) {
								GuiIngameForge.renderCrosshairs = true;
							} else {
								GuiIngameForge.renderCrosshairs = false;
								GL11.glEnable(GL11.GL_BLEND);
							}
							if (gunItem.gunInfo.renderHMGcross)
								this.renderCrossHair(minecraft, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight(), bure);
							if (itemstackSight != null) {
								if (itemstackSight.getItem() instanceof HMGItemAttachment_reddot) {
									if (!gunItem.gunInfo.canobj || !gunItem.gunInfo.zoomrer) {
										ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer,
												1.0d, "cameraZoom", "field_78503_V");
									}
								} else if (itemstackSight.getItem() instanceof HMGItemAttachment_scope) {
									if (!gunItem.gunInfo.canobj || !gunItem.gunInfo.zoomres) {
										ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer,
												1.0d, "cameraZoom", "field_78503_V");
									}
								} else if (itemstackSight.getItem() instanceof HMGItemSightBase) {
									if (!gunItem.gunInfo.canobj || ((HMGItemSightBase) itemstackSight.getItem()).scopeonly) {
										ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer,
												1.0d, "cameraZoom", "field_78503_V");
									}
								}
							} else {
								if (!gunItem.gunInfo.canobj || !gunItem.gunInfo.zoomren) {
									ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer,
											1.0d, "cameraZoom", "field_78503_V");
								}
							}
							// minecraft.gameSettings.fovSetting = 70.0F;

						}
						if (gunItem.gunInfo.canlock && nbt != null) {
							//todo 3Dにしたので根本から作り直し
							Vector3d vecToLockonPos = null;
							if (nbt.getBoolean("islockedentity")) {
								Entity TGT = entityplayer.worldObj.getEntityByID(nbt.getInteger("TGT"));
								if (TGT != null) {
									vecToLockonPos = new Vector3d();
									vecToLockonPos.set(TGT.posX, TGT.posY + TGT.height / 2, TGT.posZ);
									vecToLockonPos.sub(new Vector3d(new double[]{entityplayer.posX, entityplayer.posY, entityplayer.posZ}));
									vecToLockonPos.normalize();
								}
							} else if (nbt.getBoolean("islockedblock")) {
								vecToLockonPos = new Vector3d();
								vecToLockonPos.set(nbt.getDouble("LockedPosX"), nbt.getDouble("LockedPosY"), nbt.getDouble("LockedPosZ"));
								vecToLockonPos.sub(new Vector3d(new double[]{entityplayer.posX, entityplayer.posY, entityplayer.posZ}));
								vecToLockonPos.normalize();
							}
							if (vecToLockonPos != null) {
								RotateVectorAroundY(vecToLockonPos, entityplayer.rotationYawHead);
								RotateVectorAroundX(vecToLockonPos, entityplayer.rotationPitch);
								renderLockOnMarker(minecraft, gunItem.gunInfo.lockOnMarker, vecToLockonPos);
							}
						}


						// GuiIngameForge.renderCrosshairs = true;

						this.zoomtype = true;


						//event.
						//EntityRenderer.
						//minecraft.entityRenderer.
						//	entityplayer.eyeHeight = 10F;
						//minecraft.entityRenderer.

						//	GL11.glEnable(GL11.GL_BLEND);
					}
					GL11.glPopMatrix();
					setUp2DView(minecraft,event);

					screenWidth = scaledresolution.getScaledWidth();
					screenHeight = scaledresolution.getScaledHeight();
					if (tags != null) {
						{
							NBTTagCompound tagCompound = tags.getCompoundTagAt(5);
							ItemStack temp = ItemStack.loadItemStackFromNBT(tagCompound);
							if (temp != null) itemss = temp.getItem();
						}
					}

					FontRenderer fontrenderer = minecraft.fontRenderer;
					GL11.glPushMatrix();
					GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

					String d = String.format("%1$3d", gunItem.remain_Bullet(gunstack));
					String d1 = String.format("%1$3d", gunItem.max_Bullet(gunstack));
					fontrenderer.drawStringWithShadow(d + " /" + d1, (int)screenWidth - 70, (int)screenHeight - fontrenderer.FONT_HEIGHT * 4, 0xFFFFFF);

					GuiIngame g = minecraft.ingameGUI;
					//g.drawTexturedModelRectFromIcon(screenWidth-40, screenHeight-33, gunItem.magazine.getIconFromDamage(0), 16, 16);
					if (gunItem.getcurrentMagazine(gunstack) != null) {
						int stacksize = 0;
						minecraft.getTextureManager().bindTexture(TextureMap.locationItemsTexture);
						g.drawTexturedModelRectFromIcon((int)screenWidth - 70, (int)screenHeight - 53, gunItem.getcurrentMagazine(gunstack).getIconFromDamage(0), 16, 16);
						for (int is = 0; is < 36; ++is) {
							InventoryPlayer playerInv = entityplayer.inventory;
							ItemStack itemi = playerInv.getStackInSlot(is);
							if (itemi != null && itemi.getItem() == gunItem.getcurrentMagazine(gunstack)) {
								stacksize += itemi.stackSize;
							}
						}
						String d2 = String.format("%1$3d", stacksize);
						fontrenderer.drawStringWithShadow("x" + d2, (int)screenWidth - 50, (int)screenHeight - fontrenderer.FONT_HEIGHT * 5, 0xFFFFFF);
					}

					if (gunItem.get_selectingMagazine(gunstack) != null && gunItem.getcurrentMagazine(gunstack) != gunItem.get_selectingMagazine(gunstack)) {
						int stacksize = 0;
						minecraft.getTextureManager().bindTexture(TextureMap.locationItemsTexture);
						g.drawTexturedModelRectFromIcon((int)screenWidth - 120, (int)screenHeight - 53, gunItem.get_selectingMagazine(gunstack).getIconFromDamage(0), 16, 16);
						for (int is = 0; is < 36; ++is) {
							InventoryPlayer playerInv = entityplayer.inventory;
							ItemStack itemi = playerInv.getStackInSlot(is);
							if (itemi != null && itemi.getItem() == gunItem.get_selectingMagazine(gunstack)) {
								stacksize += itemi.stackSize;
							}
						}
						String d2 = String.format("%1$3d", stacksize);
						fontrenderer.drawStringWithShadow("x" + d2, (int)screenWidth - 100, (int)screenHeight - fontrenderer.FONT_HEIGHT * 5, 0xFFFFFF);
						fontrenderer.drawStringWithShadow("next", (int)screenWidth - 120, (int)screenHeight - fontrenderer.FONT_HEIGHT * 5 - 16, 0xFFFFFF);
					}
					this.renderBullet(fontrenderer, (int)screenWidth, (int)screenHeight, gunstack);
					if (gunItem.gunInfo.canlock) {
						if (nbt.getBoolean("SeekerOpened"))
							fontrenderer.drawStringWithShadow("Seekeropen", (int)screenWidth - 60, (int)screenHeight - fontrenderer.FONT_HEIGHT * 2, 0xFFFFFF);
					}
					GL11.glPopMatrix();
					GL11.glPopAttrib();

				} else {
					if (needreset) {
						ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer,
								1.0d, "cameraZoom", "field_78503_V");
						needreset = false;
					}
					if (this.zoomtype) {
						GuiIngameForge.renderCrosshairs = true;
					/*if (HandmadeGunsCore.cfg_ZoomRender == true) {
						minecraft.gameSettings.fovSetting = HandmadeGunsCore.cfg_FOV;
						ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer,
								1.0D, "cameraZoom", "field_78503_V");
					} else {
						ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer,
								1.0D, "cameraZoom", "field_78503_V");
					}*/
						this.zoomtype = false;
					}

				}
			}

			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
			{
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				NBTTagCompound nbts = entityplayer.getEntityData();
				int nb = nbts.getInteger("hitentity");
				if (nb >= 1) {
					GuiIngame g = minecraft.ingameGUI;
					minecraft.renderEngine.bindTexture(new ResourceLocation("handmadeguns:textures/items/hit.png"));
					GL11.glTranslatef(0.5F, 0F, 0F);
					GL11.glScalef(0.0625f, 0.0625f, 1);
					g.drawTexturedModalRect((scaledresolution.getScaledWidth() / 2 - 8) * 16,
							(scaledresolution.getScaledHeight() / 2 - 8) * 16, 0, 0, 256, 256);
					nbts.setInteger("hitentity", nb - 1);
				}
			}
			GL11.glPopAttrib();
			GL11.glPopMatrix();
			previtemstack = gunstack;
		}

		Minecraft.getMinecraft().renderEngine.bindTexture(icons);
	}

	
	@SideOnly(Side.CLIENT)
	protected void renderBullet(FontRenderer fontrenderer, int i, int j, ItemStack itemstack) {
		String sss = "null";
		String l2 = "null";
		if (itemstack != null && itemstack.getItem() instanceof HMGItem_Unified_Guns) {
			HMGItem_Unified_Guns gunbase = (HMGItem_Unified_Guns) itemstack.getItem();
			ItemStack[] items = new ItemStack[6];
			NBTTagList tags = (NBTTagList)  itemstack.getTagCompound().getTag("Items");
			if(tags != null) {
				for (int l = 0; l < 7; l++)//133
				{
					NBTTagCompound tagCompound = tags.getCompoundTagAt(l);
					int slot = tagCompound.getByte("Slot");
					if (slot >= 0 && slot < items.length) {
						items[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
//                    if(items[slot] != null) {
//                        System.out.println(""+ i + "" + items[slot].getItem().getUnlocalizedName());
//                    }
					}
				}
			}
			switch (gunbase.gunInfo.guntype) {
				case 4:
				case 0:
					sss = "normal";
					break;
				case 1:
					sss = "buckshot  " + gunbase.gunInfo.pellet + " pellet";
					break;
				case 2:
					sss = "grenade";
					break;
				case 3:
					sss = "rocket";
					break;
			}
			ItemStack itemstacka = items[5];
			if(itemstacka !=null){
				if(itemstacka.getItem() instanceof HMGItemBullet_AP){
					switch (gunbase.gunInfo.guntype) {
						case 0:
						case 4:
							sss = "AP";
							break;
						case 1:
							sss = "AP buckshot  " + gunbase.gunInfo.pellet + " pellet";
							break;
						case 2:
							sss = "grenade";
							break;
						case 3:
							sss = "rocket";
							break;
					}
				}else if(itemstacka.getItem() instanceof HMGItemBullet_AT){
					switch (gunbase.gunInfo.guntype) {
						case 0:
						case 4:
							sss = "Anesthesia";
							break;
						case 1:
							sss = "Anesthesia";
							break;
						case 2:
							sss = "grenade";
							break;
						case 3:
							sss = "rocket";
							break;
					}
				}else if(itemstacka.getItem() instanceof HMGItemBullet_Frag){
					switch (gunbase.gunInfo.guntype) {
						case 0:
						case 4:
							sss = "Frag";
							break;
						case 1:
							sss = "Frag";
							break;
						case 2:
							sss = "grenade";
							break;
						case 3:
							sss = "rocket";
							break;
					}
				}else if(itemstacka.getItem() instanceof HMGItemBullet_TE){
					switch (gunbase.gunInfo.guntype) {
						case 0:
						case 4:
							sss = "normal";
							break;
						case 1:
							sss = "buckshot  " + gunbase.gunInfo.pellet + " pellet";
							break;
						case 2:
							sss = "incendiary";
							break;
						case 3:
							sss = "incendiary";
							break;
					}
				}
			}
			int mode = itemstack.getTagCompound().getInteger("HMGMode");
			int bursts = gunbase.getburstCount(mode);
			if(bursts == -1){
				sss += " : full ";
			}else if(bursts == 0) {
				sss += " : safe ";
			}else if(bursts == 1){
				if(gunbase.gunInfo.needcock) {
					sss += " : one shot";
				}else{
					sss += " : semi";
				}
			}else {
				sss += " : " + gunbase.getburstCount(mode) + "burst ";
			}
			if(!gunbase.gunInfo.rates.isEmpty() && gunbase.gunInfo.rates.size()>mode) {
				if(gunbase.gunInfo.needcock) {
					sss += " : cocking time " + gunbase.gunInfo.cocktime;
				}else{
					sss += " : rate " + (int)(1200/gunbase.gunInfo.rates.get(mode));
				}
			}
			try {
				if (gunbase.gunInfo.elevationOffsets_info != null && itemstack.getTagCompound().getInteger("currentElevation")>=0 && itemstack.getTagCompound().getInteger("currentElevation")<gunbase.gunInfo.elevationOffsets_info.size())
					l2 = " Zero " + gunbase.gunInfo.elevationOffsets_info.get(itemstack.getTagCompound().getInteger("currentElevation"));
			}catch (Exception e){
				e.printStackTrace();
			}
		}

		fontrenderer.drawStringWithShadow(sss, i - fontrenderer.getStringWidth(sss)-5, j - fontrenderer.FONT_HEIGHT*3, 0xFFFFFF);
		fontrenderer.drawStringWithShadow(l2, i - fontrenderer.getStringWidth(sss)-5, j - fontrenderer.FONT_HEIGHT, 0xFFFFFF);
	}


	public static void renderPumpkinBlur(Minecraft minecraft,ResourceLocation adsr)
	{
		GL11.glPushMatrix();
		GL11.glDisable(2929);
		GL11.glDisable(3008);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

//		GL11.glTranslatef((float)scaledresolution.getScaledWidth() / 2, (float)scaledresolution.getScaledHeight() / 2, 0.0F);
//		IAttributeInstance iattributeinstance = minecraft.thePlayer.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
//		double f = ((iattributeinstance.getAttributeValue() / (double)minecraft.thePlayer.capabilities.getWalkSpeed() + 1.0D) / 2.0D);
//		double anti_fov = (0.81915204428D/*sin(55)*/ / sin(Math.toRadians(minecraft.gameSettings.fovSetting * f / 2.0F)));
//		anti_fov = anti_fov*anti_fov;
//		GL11.glScalef((float)anti_fov, (float)anti_fov, 1);
//		GL11.glTranslatef(-(float)scaledresolution.getScaledWidth() / 2, -(float)scaledresolution.getScaledHeight() / 2, 0.0F);
		minecraft.getTextureManager().bindTexture(adsr);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		float width = 256;
		float height = 128;
		float xoffset = -width/2f;
		float yoffset = -height/2f;

		for(int x = -1; x < 2 ;x++) for(int y = -1; y < 2 ;y++) {
			tessellator.addVertexWithUV(xoffset + width * x, yoffset + height * (y + 1)			, 40.0D, 1.0D, 0.0D);
			tessellator.addVertexWithUV(xoffset + width * (x + 1), yoffset + height * (y + 1)	, 40.0D, 0.0D, 0.0D);
			tessellator.addVertexWithUV(xoffset + width * (x + 1), yoffset + height * y			, 40.0D, 0.0D, 1.0D);
			tessellator.addVertexWithUV(xoffset + width * x, yoffset + height * y				, 40.0D, 1.0D, 1.0D);
		}
//		tessellator.addVertexWithUV(x + xoffset, y + yoffset + height*2			, 0.0D, 0.0D, 1.0D);
//		tessellator.addVertexWithUV(x + xoffset + width, y + yoffset + height*2	, 0.0D, 1.0D, 1.0D);
//		tessellator.addVertexWithUV(x + xoffset + width, y + yoffset + height		, 0.0D, 1.0D, 0.0D);
//		tessellator.addVertexWithUV(x + xoffset, y + yoffset + height				, 0.0D, 0.0D, 0.0D);
//
//		tessellator.addVertexWithUV(x + xoffset, y + yoffset						, 0.0D, 0.0D, 1.0D);
//		tessellator.addVertexWithUV(x + xoffset + width, y + yoffset				, 0.0D, 1.0D, 1.0D);
//		tessellator.addVertexWithUV(x + xoffset + width, y + yoffset - height		, 0.0D, 1.0D, 0.0D);
//		tessellator.addVertexWithUV(x + xoffset, y + yoffset - height				, 0.0D, 0.0D, 0.0D);

		tessellator.draw();
//		GL11.glScalef((float)(1/anti_fov), (float)(1/anti_fov), 1);
		GL11.glPopMatrix();
		GL11.glEnable(2929);
		GL11.glEnable(3008);
	}
	public static void renderPumpkinBlur(Minecraft minecraft, String adss)
	{
		renderPumpkinBlur(minecraft,new ResourceLocation(adss));
	}

	@SideOnly(Side.CLIENT)
	public static void renderLockOnMarker(Minecraft minecraft, ResourceLocation adsr,Vector3d markerPos)
	{
		GL11.glPushMatrix();
		GL11.glDisable(2929);
		GL11.glDisable(3008);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		double cameraZoom = ObfuscationReflectionHelper.getPrivateValue(EntityRenderer.class, minecraft.entityRenderer,
				"cameraZoom", "field_78503_V");
		GL11.glScaled(cameraZoom,cameraZoom, 1.0D);

//		GL11.glTranslatef((float)scaledresolution.getScaledWidth() / 2, (float)scaledresolution.getScaledHeight() / 2, 0.0F);
//		IAttributeInstance iattributeinstance = minecraft.thePlayer.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
//		double currentFov = minecraft.gameSettings.fovSetting * ((iattributeinstance.getAttributeValue() / (double)minecraft.thePlayer.capabilities.getWalkSpeed() + 1.0D) / 2.0D);
//		double anti_fov = (0.81915204428/*sin(55)*/ / sin(Math.toRadians(currentFov / 2.0F)));
//		anti_fov *= anti_fov;
//		GL11.glScalef((float)anti_fov, (float)anti_fov, 1);
//		GL11.glTranslatef(-(float)scaledresolution.getScaledWidth() / 2, -(float)scaledresolution.getScaledHeight() / 2, 0.0F);//左上に戻す
		//中央のままにしておく
		GL11.glRotatef(-(float)ObfuscationReflectionHelper.getPrivateValue(EntityRenderer.class, minecraft.entityRenderer, "camRoll", "R", "field_78495_O"),0,0,1);
		GL11.glTranslatef((float)markerPos.x,(float)markerPos.y,(float)markerPos.z);
		GL11.glRotatef((float)ObfuscationReflectionHelper.getPrivateValue(EntityRenderer.class, minecraft.entityRenderer, "camRoll", "R", "field_78495_O"),0,0,1);
		minecraft.getTextureManager().bindTexture(adsr);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-0.05/cameraZoom, 0.05 /cameraZoom, 0.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(0.05 /cameraZoom, 0.05 /cameraZoom, 0.0D, 0.0D, 0.0D);
		tessellator.addVertexWithUV(0.05 /cameraZoom, -0.05/cameraZoom, 0.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(-0.05/cameraZoom, -0.05/cameraZoom, 0.0D, 1.0D, 1.0D);

		tessellator.draw();
//		GL11.glScalef((float)(1/anti_fov), (float)(1/anti_fov), 1);
		GL11.glPopMatrix();
		GL11.glEnable(2929);
		GL11.glEnable(3008);
	}

	@SideOnly(Side.CLIENT)
	protected void renderCrossHair(Minecraft minecraft, int i, int j, float bure) {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glDepthMask(false);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
//		OpenGlHelper.glBlendFunc(GL_ONE_MINUS_DST_COLOR, GL_ONE_MINUS_SRC_COLOR, 1, 0);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		minecraft.getTextureManager().bindTexture(crosstex);
//		GL11.glTranslatef(i/2f, j/2f,0);
		double x =bure*2d/10d;
		double y =-1d/10d;
		double widthx = 16d/10d;
		double widthy = 2d/10d;
		for(int cnt = 0;cnt < 4;cnt ++ ) {
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(x + 0		, y + widthy	, 20.0D, 0.0D, 1.0D);
			tessellator.addVertexWithUV(x + widthx	, y + widthy	, 20.0D, 1.0D, 1.0D);
			tessellator.addVertexWithUV(x + widthx	, y + 0			, 20.0D, 1.0D, 0.0D);
			tessellator.addVertexWithUV(x + 0		, y + 0			, 20.0D, 0.0D, 0.0D);
			tessellator.draw();
			GL11.glRotatef(90, 0, 0, 1);
		}
//		tessellator.startDrawingQuads();
//		tessellator.addVertexWithUV(x + 0   ,y +widthy, 10.0D, 0.0D, 1.0D);
//		tessellator.addVertexWithUV(x+widthx,y +widthy, 10.0D, 1.0D, 1.0D);
//		tessellator.addVertexWithUV(x+widthx,y + 0, 10.0D, 1.0D, 0.0D);
//		tessellator.addVertexWithUV(x + 0   ,y + 0, 10.0D, 0.0D, 0.0D);
//		tessellator.draw();
//		GL11.glRotatef(90,0,0,1);
//		tessellator.startDrawingQuads();
//		tessellator.addVertexWithUV(x + 0   ,y +widthy, 10.0D, 0.0D, 1.0D);
//		tessellator.addVertexWithUV(x+widthx,y +widthy, 10.0D, 1.0D, 1.0D);
//		tessellator.addVertexWithUV(x+widthx,y + 0, 10.0D, 1.0D, 0.0D);
//		tessellator.addVertexWithUV(x + 0   ,y + 0, 10.0D, 0.0D, 0.0D);
//		tessellator.draw();
//		GL11.glRotatef(90,0,0,1);
//		tessellator.startDrawingQuads();
//		tessellator.addVertexWithUV(x + 0   ,y+widthy, 10.0D, 0.0D, 1.0D);
//		tessellator.addVertexWithUV(x+widthx,y +widthy, 10.0D, 1.0D, 1.0D);
//		tessellator.addVertexWithUV(x+widthx,y + 0, 10.0D, 1.0D, 0.0D);
//		tessellator.addVertexWithUV(x + 0   ,y + 0, 10.0D, 0.0D, 0.0D);
//		tessellator.draw();

		GL11.glPopMatrix();
		GL11.glPopAttrib();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		bind(Gui.icons);
	}
	private void bind(ResourceLocation res)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(res);
	}

	public static void setUp3DView(Minecraft minecraft,RenderGameOverlayEvent.Post event){

		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		Project.gluPerspective(HMG_proxy.getFOVModifier(minecraft,event.partialTicks,true), (float)minecraft.displayWidth / (float)minecraft.displayHeight, 0.05F, 300.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}
	public static void setUp2DView(Minecraft minecraft,RenderGameOverlayEvent.Post event){
		GL11.glViewport(0, 0, minecraft.displayWidth, minecraft.displayHeight);
		ScaledResolution scaledresolution = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 1000.0D, 3000.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
	}
}
