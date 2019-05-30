package handmadeguns.event;

import handmadeguns.HandmadeGunsCore;
import handmadeguns.entity.PlacedGunEntity;
import handmadeguns.items.*;
import handmadeguns.items.guns.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.*;
import org.lwjgl.opengl.GL11;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
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

import static net.minecraft.client.gui.Gui.icons;

public class HMGEventZoom {

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
			float screenposX = 0;
			float screenposY = 0;
			float screenWidth = scaledresolution.getScaledWidth();
			float screenHeight = scaledresolution.getScaledHeight();
			if (screenWidth / screenHeight < 2) {
				screenWidth = screenHeight * 2;
				screenposX = scaledresolution.getScaledWidth() - screenWidth;
				screenposX /= 2;
			} else {
				screenHeight = screenWidth / 2;
				screenposY = (scaledresolution.getScaledHeight() - screenHeight)/2;
			}
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
					// this.modelArmor.aimedBow = true;
					HMGItem_Unified_Guns gunbase = (HMGItem_Unified_Guns) gunstack.getItem();
					String ads = gunbase.gunInfo.adstexture;
					String adsr = gunbase.gunInfo.adstexturer;
					String adss = gunbase.gunInfo.adstextures;
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
					float bure = gunbase.gunInfo.spread_setting;
					bure *= HandmadeGunsCore.Key_ADS(entityplayer) ? gunbase.gunInfo.ads_spread_cof:1;
					bure  += gunbase.gunInfo.spread_setting * spreadDiffusion;
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
					//if (entityplayer.isSneaking())
					if (HandmadeGunsCore.Key_ADS(entityplayer)) {
						if (itemstackSight != null) {
							if (itemstackSight.getItem() instanceof HMGItemAttachment_reddot) {
								if (!gunbase.gunInfo.canobj || !gunbase.gunInfo.zoomrer) {
									ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer,
											gunbase.gunInfo.scopezoomred, "cameraZoom", "field_78503_V");
									needreset = true;
								}
								if (gunbase.gunInfo.zoomrert) {
									this.renderPumpkinBlur(minecraft, screenposX, screenposY, screenWidth, screenHeight, adsr);
								}
							} else if (itemstackSight.getItem() instanceof HMGItemAttachment_scope) {
								if (!gunbase.gunInfo.canobj || !gunbase.gunInfo.zoomres) {
									ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer,
											gunbase.gunInfo.scopezoomscope, "cameraZoom", "field_78503_V");
									needreset = true;
								}
								if (gunbase.gunInfo.zoomrest) {
									this.renderPumpkinBlur(minecraft, screenposX, screenposY, screenWidth, screenHeight, adss);
								}
							} else if (itemstackSight.getItem() instanceof HMGItemSightBase) {
								if (!gunbase.gunInfo.canobj || ((HMGItemSightBase) itemstackSight.getItem()).scopeonly) {
									ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer,
											((HMGItemSightBase) itemstackSight.getItem()).zoomlevel, "cameraZoom", "field_78503_V");
									needreset = true;
								}
								if (((HMGItemSightBase) itemstackSight.getItem()).scopetexture != null) {
									this.renderPumpkinBlur(minecraft, screenposX, screenposY, screenWidth, screenHeight, ((HMGItemSightBase) itemstackSight.getItem()).scopetexture);
								}
							}
						} else {
							if (!gunbase.gunInfo.canobj || !gunbase.gunInfo.zoomren) {
								ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer,
										gunbase.gunInfo.scopezoombase, "cameraZoom", "field_78503_V");
								needreset = true;
							}
							if (gunbase.gunInfo.zoomrent) {
								this.renderPumpkinBlur(minecraft, screenposX, screenposY, screenWidth, screenHeight, ads);
							}
						}
						if (gunbase.gunInfo.renderMCcross) {
							GuiIngameForge.renderCrosshairs = true;
						} else {
							GuiIngameForge.renderCrosshairs = false;
							GL11.glEnable(GL11.GL_BLEND);
						}
						if(gunbase.gunInfo.renderHMGcross && spreadDiffusion > gunbase.gunInfo.spreadDiffusionmin)this.renderCrossHair(minecraft, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight(), bure);
					} else {
						// GuiIngameForge.renderCrosshairs = true;
						if (gunbase.gunInfo.renderMCcross) {
							GuiIngameForge.renderCrosshairs = true;
						} else {
							GuiIngameForge.renderCrosshairs = false;
							GL11.glEnable(GL11.GL_BLEND);
						}
						if(gunbase.gunInfo.renderHMGcross)this.renderCrossHair(minecraft, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight(), bure);
						if (itemstackSight != null) {
							if (itemstackSight.getItem() instanceof HMGItemAttachment_reddot) {
								if (!gunbase.gunInfo.canobj || !gunbase.gunInfo.zoomrer) {
									ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer,
											1.0d, "cameraZoom", "field_78503_V");
								}
							} else if (itemstackSight.getItem() instanceof HMGItemAttachment_scope) {
								if (!gunbase.gunInfo.canobj || !gunbase.gunInfo.zoomres) {
									ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer,
											1.0d, "cameraZoom", "field_78503_V");
								}
							} else if (itemstackSight.getItem() instanceof HMGItemSightBase) {
								if (!gunbase.gunInfo.canobj || ((HMGItemSightBase) itemstackSight.getItem()).scopeonly) {
									ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer,
											1.0d, "cameraZoom", "field_78503_V");
								}
							}
						} else {
							if (!gunbase.gunInfo.canobj || !gunbase.gunInfo.zoomren) {
								ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer,
										1.0d, "cameraZoom", "field_78503_V");
							}
						}
						// minecraft.gameSettings.fovSetting = 70.0F;
					}
					// GuiIngameForge.renderCrosshairs = true;

					this.zoomtype = true;


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

					String d = String.format("%1$3d", gunbase.remain_Bullet(gunstack));
					String d1 = String.format("%1$3d", gunbase.max_Bullet(gunstack));
					fontrenderer.drawStringWithShadow(d + " /" + d1, (int)screenWidth - 70, (int)screenHeight - fontrenderer.FONT_HEIGHT * 4, 0xFFFFFF);

					GuiIngame g = minecraft.ingameGUI;
					//g.drawTexturedModelRectFromIcon(screenWidth-40, screenHeight-33, gunbase.magazine.getIconFromDamage(0), 16, 16);
					if (gunbase.getcurrentMagazine(gunstack) != null) {
						int stacksize = 0;
						minecraft.getTextureManager().bindTexture(TextureMap.locationItemsTexture);
						g.drawTexturedModelRectFromIcon((int)screenWidth - 70, (int)screenHeight - 53, gunbase.getcurrentMagazine(gunstack).getIconFromDamage(0), 16, 16);
						for (int is = 0; is < 36; ++is) {
							InventoryPlayer playerInv = entityplayer.inventory;
							ItemStack itemi = playerInv.getStackInSlot(is);
							if (itemi != null && itemi.getItem() == gunbase.getcurrentMagazine(gunstack)) {
								stacksize += itemi.stackSize;
							}
						}
						String d2 = String.format("%1$3d", stacksize);
						fontrenderer.drawStringWithShadow("x" + d2, (int)screenWidth - 50, (int)screenHeight - fontrenderer.FONT_HEIGHT * 5, 0xFFFFFF);
					}
					
					if (gunbase.get_selectingMagazine(gunstack) != null && gunbase.getcurrentMagazine(gunstack) != gunbase.get_selectingMagazine(gunstack)) {
						int stacksize = 0;
						minecraft.getTextureManager().bindTexture(TextureMap.locationItemsTexture);
						g.drawTexturedModelRectFromIcon((int)screenWidth - 120, (int)screenHeight - 53, gunbase.get_selectingMagazine(gunstack).getIconFromDamage(0), 16, 16);
						for (int is = 0; is < 36; ++is) {
							InventoryPlayer playerInv = entityplayer.inventory;
							ItemStack itemi = playerInv.getStackInSlot(is);
							if (itemi != null && itemi.getItem() == gunbase.get_selectingMagazine(gunstack)) {
								stacksize += itemi.stackSize;
							}
						}
						String d2 = String.format("%1$3d", stacksize);
						fontrenderer.drawStringWithShadow("x" + d2, (int)screenWidth - 100, (int)screenHeight - fontrenderer.FONT_HEIGHT * 5, 0xFFFFFF);
						fontrenderer.drawStringWithShadow("next", (int)screenWidth - 120, (int)screenHeight - fontrenderer.FONT_HEIGHT * 5 - 16, 0xFFFFFF);
					}
					this.renderBullet(fontrenderer, (int)screenWidth, (int)screenHeight, gunstack);
					if (gunbase.gunInfo.canlock) {
						if (nbt.getBoolean("SeekerOpened"))
							fontrenderer.drawStringWithShadow("Seekeropen", (int)screenWidth - 60, (int)screenHeight - fontrenderer.FONT_HEIGHT * 2, 0xFFFFFF);
					}

					//event.
					//EntityRenderer.
					//minecraft.entityRenderer.
					//	entityplayer.eyeHeight = 10F;
					//minecraft.entityRenderer.

					//	GL11.glEnable(GL11.GL_BLEND);
					GL11.glPopMatrix();
					GL11.glPopAttrib();


				} else {
					if (needreset) {
						ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, minecraft.entityRenderer,
								1.0d, "cameraZoom", "field_78503_V");
						needreset = false;
					}
					if (this.zoomtype == true) {
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
			GL11.glPopMatrix();
			GL11.glPopAttrib();
			previtemstack = gunstack;
		}

		Minecraft.getMinecraft().renderEngine.bindTexture(icons);
	}

	@SideOnly(Side.CLIENT)
	protected void renderBullet(FontRenderer fontrenderer, int i, int j, ItemStack itemstack) {
		String sss = "null";
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
					sss += " : rate " + gunbase.gunInfo.rates.get(mode);
				}
			}
		}

		fontrenderer.drawStringWithShadow(sss, i - fontrenderer.getStringWidth(sss)-5, j - fontrenderer.FONT_HEIGHT*3, 0xFFFFFF);
	}


	@SideOnly(Side.CLIENT)
	protected void renderPumpkinBlur(Minecraft minecraft, float x, float y, float width, float height, String adss)
	{
		this.renderPumpkinBlur(minecraft,x,y,width,height,new ResourceLocation(adss));
	}

	@SideOnly(Side.CLIENT)
	public static void renderPumpkinBlur(Minecraft minecraft, float x, float y, float width, float height, ResourceLocation adsr)
	{
		GL11.glPushMatrix();
		GL11.glDisable(2929);
		GL11.glDisable(3008);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ScaledResolution scaledresolution = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);

		GL11.glTranslatef((float)scaledresolution.getScaledWidth() / 2, (float)scaledresolution.getScaledHeight() / 2, 0.0F);
		IAttributeInstance iattributeinstance = minecraft.thePlayer.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
		double f = ((iattributeinstance.getAttributeValue() / (double)minecraft.thePlayer.capabilities.getWalkSpeed() + 1.0D) / 2.0D);
		GL11.glScalef((float)(0.81915204428D / Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting * f / 2.0F))), (float)(0.81915204428D / Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting * f / 2.0F))), (float)(0.81915204428D / Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting * f / 2.0F))));
		GL11.glScalef((float)(0.81915204428D / Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting * f / 2.0F))), (float)(0.81915204428D / Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting * f / 2.0F))), (float)(0.81915204428D / Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting * f / 2.0F))));
		GL11.glTranslatef(-(float)scaledresolution.getScaledWidth() / 2, -(float)scaledresolution.getScaledHeight() / 2, 0.0F);
		minecraft.getTextureManager().bindTexture(adsr);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		float xoffset = 0;
		float yoffset = 0;
		tessellator.addVertexWithUV(x + xoffset, y + yoffset + height			, -90.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(x + xoffset + width, y + yoffset + height	, -90.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV(x + xoffset + width, y + yoffset			, -90.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(x + xoffset, y + yoffset					, -90.0D, 0.0D, 0.0D);

		tessellator.addVertexWithUV(x + xoffset, y + yoffset + height*2			, -90.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(x + xoffset + width, y + yoffset + height*2	, -90.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV(x + xoffset + width, y + yoffset + height		, -90.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(x + xoffset, y + yoffset + height				, -90.0D, 0.0D, 0.0D);

		tessellator.addVertexWithUV(x + xoffset, y + yoffset						, -90.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(x + xoffset + width, y + yoffset				, -90.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV(x + xoffset + width, y + yoffset - height		, -90.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(x + xoffset, y + yoffset - height				, -90.0D, 0.0D, 0.0D);

		tessellator.draw();
		GL11.glScalef((float)Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting * f / 2.0F)) / 0.81915206F, (float)Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting * f / 2.0F)) / 0.81915206F, (float)Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting * f / 2.0F)) / 0.81915206F);
		GL11.glScalef((float)Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting * f / 2.0F)) / 0.81915206F, (float)Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting * f / 2.0F)) / 0.81915206F, (float)Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting * f / 2.0F)) / 0.81915206F);
		GL11.glPopMatrix();
		GL11.glEnable(2929);
		GL11.glEnable(3008);

//		GL11.glPushMatrix();
//		GL11.glDisable(2929);
//		GL11.glDisable(3008);
//		GL11.glEnable(3042);
//		GL11.glBlendFunc(770, 771);
//		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//		ScaledResolution scaledresolution = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
//
//		GL11.glTranslatef((float)scaledresolution.getScaledWidth() / 2, (float)scaledresolution.getScaledHeight() / 2, 0.0F);
//		GL11.glScalef((float)(0.81915204428D / Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting / 2.0F))), (float)(0.81915204428D / Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting / 2.0F))), (float)(0.81915204428D / Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting / 2.0F))));
//		GL11.glScalef((float)(0.81915204428D / Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting / 2.0F))), (float)(0.81915204428D / Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting / 2.0F))), (float)(0.81915204428D / Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting / 2.0F))));
//		GL11.glTranslatef(-(float)scaledresolution.getScaledWidth() / 2, -(float)scaledresolution.getScaledHeight() / 2, 0.0F);
//		minecraft.getTextureManager().bindTexture(adsr);
//		Tessellator tessellator = Tessellator.instance;
//		tessellator.startDrawingQuads();
//		float xoffset = 0;
//		float yoffset = 0;
//		tessellator.addVertexWithUV(x + xoffset, y + yoffset + height			, -90.0D, 0.0D, 1.0D);
//		tessellator.addVertexWithUV(x + xoffset + width, y + yoffset + height	, -90.0D, 1.0D, 1.0D);
//		tessellator.addVertexWithUV(x + xoffset + width, y + yoffset			, -90.0D, 1.0D, 0.0D);
//		tessellator.addVertexWithUV(x + xoffset, y + yoffset					, -90.0D, 0.0D, 0.0D);
//		tessellator.draw();
//		GL11.glScalef((float)Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting / 2.0F)) / 0.81915206F, (float)Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting / 2.0F)) / 0.81915206F, (float)Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting / 2.0F)) / 0.81915206F);
//		GL11.glScalef((float)Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting / 2.0F)) / 0.81915206F, (float)Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting / 2.0F)) / 0.81915206F, (float)Math.sin(Math.toRadians(minecraft.gameSettings.fovSetting / 2.0F)) / 0.81915206F);
//		GL11.glPopMatrix();
//		GL11.glEnable(2929);
//		GL11.glEnable(3008);
	}

	@SideOnly(Side.CLIENT)
	protected void renderCrossHair(Minecraft minecraft, int i, int j, float bure) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glDepthMask(false);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		OpenGlHelper.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR, 1, 0);
		minecraft.getTextureManager().bindTexture(crosstex);
		Tessellator tessellator = Tessellator.instance;
		GL11.glTranslatef(i/2, j/2,0);
		double x =bure*2;
		double y =-1;
		double widthx = 16;
		double widthy = 2;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0   ,y+widthy, -90.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(x+widthx,y+widthy, -90.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV(x+widthx,y + 0, -90.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(x + 0   ,y + 0, -90.0D, 0.0D, 0.0D);
		tessellator.draw();
		tessellator = Tessellator.instance;
		GL11.glRotatef(90,0,0,1);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0   ,y+widthy, -90.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(x+widthx,y +widthy, -90.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV(x+widthx,y + 0, -90.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(x + 0   ,y + 0, -90.0D, 0.0D, 0.0D);
		tessellator.draw();
		GL11.glRotatef(90,0,0,1);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0   ,y+widthy, -90.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(x+widthx,y +widthy, -90.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV(x+widthx,y + 0, -90.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(x + 0   ,y + 0, -90.0D, 0.0D, 0.0D);
		tessellator.draw();
		GL11.glRotatef(90,0,0,1);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0   ,y+widthy, -90.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(x+widthx,y +widthy, -90.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV(x+widthx,y + 0, -90.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(x + 0   ,y + 0, -90.0D, 0.0D, 0.0D);
		tessellator.draw();

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
}
