package handmadevehicle.render;

import cpw.mods.fml.client.FMLClientHandler;
import handmadeguns.HandmadeGunsCore;
import handmadeguns.client.render.*;
import handmadeguns.items.HMGItemAttachment_reddot;
import handmadeguns.items.HMGItemAttachment_scope;
import handmadeguns.items.HMGItemSightBase;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.ArrayList;

import static handmadeguns.event.RenderTickSmoothing.smooth;
import static handmadevehicle.HMVehicle.HMV_Proxy;
import static handmadevehicle.render.RenderVehicle.currentBaseLogic;
import static handmadevehicle.render.RenderVehicle.currentEntity;
import static net.minecraft.util.MathHelper.wrapAngleTo180_float;
import static org.lwjgl.opengl.GL11.*;

public class PartsRender_Vehicle extends PartsRender {
	
	public void partSidentification(Object... data){
		float flame;
		int remainbullets = (int) currentBaseLogic.health;
		ArrayList<HMGGunParts> partslist_temp = (ArrayList<HMGGunParts>) data[0];
		for (HMGGunParts parts : partslist_temp) {
			GunState[] states;
			if(parts instanceof HMVVehicleParts && ((HMVVehicleParts) parts).isTurretParts){
				states = new GunState[2];
				TurretObj linkedTurret = currentBaseLogic.allturrets[((HMVVehicleParts) parts).linkedTurretID];
				if(linkedTurret.dummyGunItem != null && linkedTurret.dummyGunStack != null) {
					remainbullets = linkedTurret.dummyGunItem.remain_Bullet(linkedTurret.dummyGunStack);
					if (linkedTurret.isreloading()) {
						states[0] = GunState.Reload;
						flame = linkedTurret.getDummyStackTag().getInteger("RloadTime") - smooth;
					} else if (linkedTurret.isLoading()) {
						states[0] = GunState.Recoil;
						flame = (linkedTurret.getDummyStackTag().getByte("Bolt") - smooth) * 10;
					} else {
						states[0] = GunState.Default;
						flame = 0;
					}
					if (HMV_Proxy.iszooming()) {
						states[1] = GunState.ADS;
					} else {
						states[1] = GunState.Default;
					}
				}else {
					remainbullets = 0;
					states[0] = GunState.Reload;
					flame = 0;
				}
			}else {
				states = new GunState[1];
				if(HMV_Proxy.iszooming()){
					states[0] = GunState.ADS;
				}else {
					states[0] = GunState.Default;
				}
				flame = 0;
			}
			breakpoint:for (GunState state : states) {
				switch (state) {
					case Recoil:
						if (parts.rendering_Recoil) {
							if (parts.hasMotionRecoil) {
								HMGGunParts_Motion_PosAndRotation OffsetAndRotation = parts.getRecoilmotion(flame + smooth);
								
								PartSidentification_Vehicle(parts, state, flame, remainbullets, OffsetAndRotation);
							} else {
								HMGGunParts_Motion_PosAndRotation OffsetAndRotation = parts.getRenderinfOfRecoil();
								
								PartSidentification_Vehicle(parts, state, flame, remainbullets, OffsetAndRotation);
							}
							break breakpoint;
						}
						break;
					case ADS:
						if (parts.rendering_Ads) {
							PartSidentification_Vehicle(parts, state, flame, remainbullets, parts.getRenderinfOfADS());
							break breakpoint;
						}
						break;
					case Cock:
						if (parts.rendering_Cock) {
							if (parts.hasMotionCock) {
								HMGGunParts_Motion_PosAndRotation OffsetAndRotation = parts.getcockmotion(flame + smooth);
								
								PartSidentification_Vehicle(parts, state, flame, remainbullets, OffsetAndRotation);
							} else {
								HMGGunParts_Motion_PosAndRotation OffsetAndRotation = parts.getRenderinfOfCock();
								
								PartSidentification_Vehicle(parts, state, flame, remainbullets, OffsetAndRotation);
							}
							break breakpoint;
						}
						break;
					case Reload:
						if (parts.rendering_Reload) {
							if (parts.hasMotionReload) {
								HMGGunParts_Motion_PosAndRotation OffsetAndRotation = parts.getReloadmotion(flame + smooth);
								
								PartSidentification_Vehicle(parts, state, flame, remainbullets, OffsetAndRotation);
							} else {
								HMGGunParts_Motion_PosAndRotation OffsetAndRotation = parts.getRenderinfOfReload();
								
								PartSidentification_Vehicle(parts, state, flame, remainbullets, OffsetAndRotation);
							}
							break breakpoint;
						}
						break;
					case Default:
						if (parts.rendering_Def) {
							PartSidentification_Vehicle(parts, state, flame, remainbullets, parts.getRenderinfDefault_offset());
							break breakpoint;
						}
						break;
				}
			}
		}
	}
	
	public void PartSidentification_Vehicle(HMGGunParts parts, GunState state, float flame, int remainbullets, HMGGunParts_Motion_PosAndRotation OffsetAndRotation){
		if(OffsetAndRotation != null && OffsetAndRotation.renderOnOff) {
			part_Render(parts, state, flame, remainbullets, OffsetAndRotation);
		}
	}
	public void partModel_render(HMGGunParts parts, GunState state, float flame, int remainbullets, HMGGunParts_Motion_PosAndRotation OffsetAndRotation){
		HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation = parts.getRenderinfCenter();
		
		if(parts instanceof HMVVehicleParts) {
			if (((HMVVehicleParts) parts).isTurretParts && parts.hasbaseYawInfo) {
				HMGGunParts_Motion_PosAndRotation baserotationCenterAndRotation = parts.getYawInfo(-(float) currentBaseLogic.allturrets[((HMVVehicleParts) parts).linkedTurretID].turretrotationYaw);
				if (baserotationCenterAndRotation != null) {
					if(!baserotationCenterAndRotation.renderOnOff)return;
					transformParts(rotationCenterAndRotation,baserotationCenterAndRotation,parts);
				}
			}
			if (((HMVVehicleParts) parts).isTurretParts && parts.hasbasePitchInfo) {
				HMGGunParts_Motion_PosAndRotation baserotationCenterAndRotation = parts.getPitchInfo((float) currentBaseLogic.allturrets[((HMVVehicleParts) parts).linkedTurretID].turretrotationPitch);
				if (baserotationCenterAndRotation != null) {
					if(!baserotationCenterAndRotation.renderOnOff)return;
					transformParts(rotationCenterAndRotation,baserotationCenterAndRotation,parts);
				}
			}

			HMGGunParts_Motion_PosAndRotation yawLadderInfo = ((HMVVehicleParts) parts).getSomethingPositions(currentBaseLogic.yawrudder,0);
			if (yawLadderInfo != null) {
				if(!yawLadderInfo.renderOnOff)return;
				transformParts(rotationCenterAndRotation,yawLadderInfo,parts);
			}
			HMGGunParts_Motion_PosAndRotation pitchLadderInfo = ((HMVVehicleParts) parts).getSomethingPositions(currentBaseLogic.pitchrudder,1);
			if (pitchLadderInfo != null) {
				if(!pitchLadderInfo.renderOnOff)return;
				transformParts(rotationCenterAndRotation,pitchLadderInfo,parts);
			}
			HMGGunParts_Motion_PosAndRotation rollLadderInfo = ((HMVVehicleParts) parts).getSomethingPositions(currentBaseLogic.rollrudder,2);
			if (rollLadderInfo != null) {
				if(!rollLadderInfo.renderOnOff)return;
				transformParts(rotationCenterAndRotation,rollLadderInfo,parts);
			}
			HMGGunParts_Motion_PosAndRotation throttleInfo = ((HMVVehicleParts) parts).getSomethingPositions(currentBaseLogic.throttle,3);
			if (throttleInfo != null) {
				if(!throttleInfo.renderOnOff)return;
				transformParts(rotationCenterAndRotation,throttleInfo,parts);
			}
			HMGGunParts_Motion_PosAndRotation yawInfo = ((HMVVehicleParts) parts).getSomethingPositions(currentBaseLogic.bodyrotationYaw,4);
			if (yawInfo != null) {
				if(!yawInfo.renderOnOff)return;
				transformParts(rotationCenterAndRotation,yawInfo,parts);
			}
			HMGGunParts_Motion_PosAndRotation pitchInfo = ((HMVVehicleParts) parts).getSomethingPositions(currentBaseLogic.bodyrotationPitch,5);
			if (pitchInfo != null) {
				if(!pitchInfo.renderOnOff)return;
				transformParts(rotationCenterAndRotation,pitchInfo,parts);
			}
			HMGGunParts_Motion_PosAndRotation rollInfo = ((HMVVehicleParts) parts).getSomethingPositions(currentBaseLogic.bodyrotationRoll,6);
			if (rollInfo != null) {
				if(!rollInfo.renderOnOff)return;
				transformParts(rotationCenterAndRotation,rollInfo,parts);
			}
			HMGGunParts_Motion_PosAndRotation gearInfo = ((HMVVehicleParts) parts).getSomethingPositions(currentBaseLogic.gearprogress,7);
			if (gearInfo != null) {
				if(!gearInfo.renderOnOff)return;
				transformParts(rotationCenterAndRotation,gearInfo,parts);
			}
			HMGGunParts_Motion_PosAndRotation flapInfo = ((HMVVehicleParts) parts).getSomethingPositions(currentBaseLogic.flaplevel,8);
			if (flapInfo != null) {
				if(!flapInfo.renderOnOff)return;
				transformParts(rotationCenterAndRotation,flapInfo,parts);
			}

			if (parts.isbullet) {
				if (state == GunState.Recoil)
					renderParts_Bullet(parts, flame / 10, remainbullets, rotationCenterAndRotation);
				else
					renderParts_Bullet(parts, flame, remainbullets, rotationCenterAndRotation);
			} else if (((HMVVehicleParts) parts).isTrack) {
				renderParts_Track((HMVVehicleParts) parts, flame, remainbullets, rotationCenterAndRotation);
			} else {
				if (parts.reticleAndPlate) {
					glClear(GL_STENCIL_BUFFER_BIT);
					glEnable(GL_STENCIL_TEST);
					glStencilMask(1);

					glStencilFunc(
							GL_ALWAYS,   // GLenum func
							1,          // GLint ref
							~0);// GLuint mask
					glStencilOp(
							GL_KEEP,
							GL_KEEP,
							GL_REPLACE);
					if (pass != 1) {
						GL11.glDepthMask(false);
						glAlphaFunc(GL_ALWAYS, 1);
						glColorMask(
								false,   // GLboolean red
								false,   // GLboolean green
								false,   // GLboolean blue
								false);
					}
					model.renderPart(parts.partsname + "reticlePlate");
					if (pass != 1) {
						GL11.glDepthMask(true);
						glAlphaFunc(GL_EQUAL, 1);
						glColorMask(
								true,   // GLboolean red
								true,   // GLboolean green
								true,   // GLboolean blue
								true);
					}

					GL11.glDisable(GL11.GL_LIGHTING);
					float lastBrightnessX = OpenGlHelper.lastBrightnessX;
					float lastBrightnessY = OpenGlHelper.lastBrightnessY;
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
					glDisable(GL_DEPTH_TEST);


					glStencilFunc(
							GL_EQUAL,   // GLenum func
							1,          // GLint ref
							~0);// GLuint mask

					glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
					glAlphaFunc(GL_ALWAYS, 1);
					GL11.glDepthMask(false);

					GL11.glDepthFunc(GL11.GL_ALWAYS);//強制描画
					model.renderPart(parts.partsname + "reticle");
					GL11.glDepthFunc(GL11.GL_LEQUAL);
					GL11.glDepthMask(true);
					glDisable(GL_STENCIL_TEST);
					glEnable(GL_DEPTH_TEST);
					GL11.glEnable(GL11.GL_LIGHTING);
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) lastBrightnessX, (float) lastBrightnessY);

					if (pass == 1) {
						glEnable(GL_BLEND);
						glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
						GL11.glDepthMask(false);
						glAlphaFunc(GL_LESS, 1);
					} else {
						GL11.glDepthMask(true);
						glAlphaFunc(GL_EQUAL, 1);
					}
				}
				if(((HMVVehicleParts) parts).isTurret_linkedGunMount &&
						currentBaseLogic.allturrets[((HMVVehicleParts) parts).linkedTurretID]
						.dummyGunStack != null){
					renderLinkedStack(currentBaseLogic.allturrets[((HMVVehicleParts) parts).linkedTurretID]
							.dummyGunStack);
				}
				model.renderPart(parts.partsname);
				float lastBrightnessX = OpenGlHelper.lastBrightnessX;
				float lastBrightnessY = OpenGlHelper.lastBrightnessY;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
				model.renderPart(parts.partsname + "light");
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) lastBrightnessX, (float) lastBrightnessY);
			}
		}
	}

	public void renderLinkedStack(ItemStack gunStack) {
		GL11.glPushMatrix();
		if (gunStack != null) {
			IItemRenderer gunrender = MinecraftForgeClient.getItemRenderer(gunStack, IItemRenderer.ItemRenderType.EQUIPPED);
			if (gunrender instanceof HMGRenderItemGun_U_NEW) {
				GL11.glScalef(0.5f, 0.5f, 0.5f);
			} else if (gunrender instanceof HMGRenderItemGun_U) {
				//base は matbase を利用してそれらしく描画可能
				GL11.glScalef(0.5f, 0.5f, 0.5f);
				gunrender.renderItem(IItemRenderer.ItemRenderType.ENTITY, gunStack);
			}
		}
		GL11.glPopMatrix();
	}
	
	public void renderParts_Track(HMVVehicleParts parts,float flame,int remainbullets,HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation){
		flame = (currentBaseLogic.prev_pera_trackPos + (currentBaseLogic.pera_trackPos - currentBaseLogic.prev_pera_trackPos) * smooth)/currentBaseLogic.prefab_vehicle.max_pera_trackPos;
		if(parts.isavatar){
			if(parts.isbelt){
				for (int i = 0; (i < parts.trackPieceCount); i++) {
					HMGGunParts_Motion_PosAndRotation trackoffset = parts.getTrackPositions(i + flame);
					if(trackoffset != null && trackoffset.renderOnOff) {
						GL11.glPushMatrix();
						transformParts(rotationCenterAndRotation,trackoffset,parts);
						model.renderPart(parts.partsname);
						float lastBrightnessX = OpenGlHelper.lastBrightnessX;
						float lastBrightnessY = OpenGlHelper.lastBrightnessY;
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
						model.renderPart(parts.partsname + "light");
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
						GL11.glPopMatrix();
					}
				}
			}else {
				HMGGunParts_Motion_PosAndRotation trackoffset = parts.getRenderinfOfPeraPosAndRotation();
				if(trackoffset != null && trackoffset.renderOnOff) {
					GL11.glTranslatef(trackoffset.posX * -flame, trackoffset.posY * -flame, trackoffset.posZ * -flame);
					GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
					GL11.glRotatef(trackoffset.rotationY * -flame, 0, 1, 0);
					GL11.glRotatef(trackoffset.rotationX * -flame, 1, 0, 0);
					GL11.glRotatef(trackoffset.rotationZ * -flame, 0, 0, 1);
					GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
					for (int i = 0; (i < parts.trackPieceCount); i++) {
						transformParts(rotationCenterAndRotation,trackoffset,parts);
						model.renderPart(parts.partsname);
						float lastBrightnessX = OpenGlHelper.lastBrightnessX;
						float lastBrightnessY = OpenGlHelper.lastBrightnessY;
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
						model.renderPart(parts.partsname + "light");
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
					}
				}
			}
		}else {
			if(parts.isbelt){
				HMGGunParts_Motion_PosAndRotation trackoffset = parts.getTrackPositions(flame);
				if(trackoffset != null && trackoffset.renderOnOff) {
					GL11.glPushMatrix();
					transformParts(rotationCenterAndRotation,trackoffset,parts);
					model.renderPart(parts.partsname);
					float lastBrightnessX = OpenGlHelper.lastBrightnessX;
					float lastBrightnessY = OpenGlHelper.lastBrightnessY;
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
					model.renderPart(parts.partsname + "light");
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
					GL11.glPopMatrix();
				}
			}else {
				HMGGunParts_Motion_PosAndRotation trackoffset = parts.getRenderinfOfPeraPosAndRotation();
				if(trackoffset != null && trackoffset.renderOnOff) {
					GL11.glTranslatef(trackoffset.posX * flame, trackoffset.posY * flame, trackoffset.posZ * flame);
					GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
					GL11.glRotatef(trackoffset.rotationY * flame, 0, 1, 0);
					GL11.glRotatef(trackoffset.rotationX * flame, 1, 0, 0);
					GL11.glRotatef(trackoffset.rotationZ * flame, 0, 0, 1);
					GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
					model.renderPart(parts.partsname);
					float lastBrightnessX = OpenGlHelper.lastBrightnessX;
					float lastBrightnessY = OpenGlHelper.lastBrightnessY;
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
					model.renderPart(parts.partsname + "light");
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lastBrightnessX, (float)lastBrightnessY);
				}
			}
		}
	}
}
