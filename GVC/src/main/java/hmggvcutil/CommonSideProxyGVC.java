package hmggvcutil;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import hmggvcutil.Item.GVCItemForTranslate;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static hmggvcutil.GVCUtils.*;

public class CommonSideProxyGVC {

	public void registerClientInfo(){}

	public void IGuiHandler(){}

    public void reisterRenderers(){}

	public World getCilentWorld(){
		return null;}

	public Minecraft getCilentMinecraft(){
		return null;}

	public void registerGuns(FMLPreInitializationEvent pEvent) {
		{
			{
				fn_ak74 = new GVCItemForTranslate().setUnlocalizedName("AK74").setTextureName("gvcguns:AK74")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_ak74;
//					gun.setMaxDamage(30);
//					gun.powor = 8;
//					gun.speed = 45/4;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 4.0F;
//					gun.ads_spread_cof = 0.5F;
//					gun.recoil = 2.5D;
//					gun.recoil_sneak = gun.recoil / 2;
//					gun.reloadtime = 50;
//					gun.cycle = 2;
//					gun.soundbase = "gvcguns:gvcguns.fire";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 1.2F;
//					gun.magazine = fn_magazine_545;
//					gun.motion = 1.0D;
//					gun.scopezoom = 1.5F;
//					gun.scopezoomscope = 2F;
//					gun.scopezoombase = 1.5F;
//					gun.scopezoomred = 1.25F;
//					gun.texture = new ResourceLocation("gvcguns:textures/model/obj/AK74.png");
//					gun.adstexture = "gvcguns:textures/misc/acog_ak74.png";
//					gun.adstexturer = "gvcguns:textures/misc/reddot.png";
//					gun.adstextures = "gvcguns:textures/misc/scope_svd.png";
//
//					gun.hasAttachRestriction = true;
//					gun.attachwhitelist.add("reddot");
//					gun.attachwhitelist.add("scope");
//					gun.zoomren = true;
//					gun.zoomrer = true;
//					gun.zoomres = false;
//					gun.zoomrent = false;
//					gun.zoomrert = false;
//					gun.zoomrest = true;
//					gun.canobj = true;
//					gun.dropcart = true;
//					gun.reloadanim = true;
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 1f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							2f, -2f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(1, new Float[]{10f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(2, new Float[]{20f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(3, new Float[]{40f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.3f, 1.10f, 1.0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(4, new Float[]{50f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.canuseclass = 0;
//					modelhigh = 1.10415f;
//					modelhighr = 0.9866f;
//
//					armrotationxr = -1.57f;
//					armrotationyr = 0f;
//					armrotationzr = -1.57f;
//					armoffsetxr = 0.4f;
//					armoffsetyr = 0.3f;
//					armoffsetzr = 0.5f;
//					armrotationxl = -0.0f;
//					armrotationyl = -2.3f;
//					armrotationzl = 2.0f;
//					armoffsetxl = 0.1f;
//					armoffsetyl = 0.3f;
//					armoffsetzl = -1.2f;
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/obj/AK74.mqo"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhighr, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_ak74, "AK74");
//				Guns.add(fn_ak74);
//				

			}
			{
				fn_rpk74 = new GVCItemForTranslate().setUnlocalizedName("RPK74").setTextureName("gvcguns:RPK74")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_rpk74;
//					gun.setMaxDamage(45);
//                    gun.canuseclass = 3;
//					gun.powor = 8;
//					gun.speed = 45/4;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 16.0F;
//					gun.ads_spread_cof = 0.1F;
//					gun.recoil = 1.5D;
//					gun.recoil_sneak = gun.recoil / 8;
//					gun.reloadtime = 75;
//					gun.cycle = 2;
//					gun.soundbase = "gvcguns:gvcguns.fire";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 1.2F;
//					gun.magazine = fn_magazine_545_long;
//					gun.motion = 0.9D;
//					gun.scopezoom = 1;
//					gun.scopezoomscope = 4F;
//					gun.scopezoombase = 1;
//					gun.scopezoomred = 1;
//					gun.texture = new ResourceLocation("gvcguns:textures/model/obj/AK74.png");
//					gun.adstexture = "gvcguns:textures/misc/acog_ak74.png";
//					gun.adstexturer = "gvcguns:textures/misc/reddot.png";
//					gun.adstextures = "gvcguns:textures/misc/scope_svd.png";
//
//					gun.canfix = true;
//					gun.fixAsEntity = true;
//
//					gun.hasAttachRestriction = true;
//					gun.attachwhitelist.add("reddot");
//					gun.attachwhitelist.add("scope");
//					gun.zoomren = true;
//					gun.zoomrer = true;
//					gun.zoomres = false;
//					gun.zoomrent = false;
//					gun.zoomrert = false;
//					gun.zoomrest = true;
//					gun.canobj = true;
//					gun.bulletmodelN = "byfrou01_GreenTracer";
//					gun.reloadanim = true;
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 1f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							2f, -2f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(1, new Float[]{15f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(2, new Float[]{30f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(3, new Float[]{60f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.3f, 1.10f, 1.0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(4, new Float[]{75f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
////					modelwidthz = 1.0F;
//					modelhigh = 1.10415f;
//					modelhighr = 0.9866f;
//					modelhighs = 0.8f;
//					gun.modelscale = modelscala;
//					gun.setmodelADSPosAndRotation(modelwidthx ,modelhigh,modelwidthz + 2);
//					gun.setADSoffsetRed(modelwidthxr,modelhighr,modelwidthzr + 2);
//					gun.setADSoffsetScope(modelwidthxs,modelhighs,modelwidthzs + 2);
//
//					armrotationxr = -1.57f;
//					armrotationyr = 0f;
//					armrotationzr = -1.57f;
//					armoffsetxr = 0.4f;
//					armoffsetyr = 0.3f;
//					armoffsetzr = 0.5f;
//					armrotationxl = -0.0f;
//					armrotationyl = -2.3f;
//					armrotationzl = 2.0f;
//					armoffsetxl = 0.1f;
//					armoffsetyl = 0.3f;
//					armoffsetzl = -1.2f;
//
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/obj/RPK74.mqo"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhigh, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_rpk74, "RPK74");
//				Guns.add(fn_rpk74);
//				
			}
			{
				fn_aks74u = new GVCItemForTranslate().setUnlocalizedName("AKS74U").setTextureName("gvcguns:AKS74U")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_aks74u;
//					gun.setMaxDamage(30);
//                    gun.canuseclass = 0;
//					gun.powor = 7;
//					gun.speed = 45/4;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 8.0F;
//					gun.ads_spread_cof = 0.75F;
//					gun.recoil = 3.5D;
//					gun.recoil_sneak = gun.recoil / 2;
//					gun.reloadtime = 40;
//					gun.cycle = 2;
//					gun.soundbase = "gvcguns:gvcguns.fire";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 1.2F;
//					gun.magazine = fn_magazine_545;
//					gun.motion = 1.0D;
//					gun.scopezoom = 1.5F;
//					gun.scopezoomscope = 2F;
//					gun.scopezoombase = 1.5F;
//					gun.scopezoomred = 1.25F;
//					gun.texture = new ResourceLocation("gvcguns:textures/model/obj/AKS74U.png");
//					gun.adstexture = "gvcguns:textures/misc/acog_ak74.png";
//					gun.adstexturer = "gvcguns:textures/misc/reddot.png";
//					gun.adstextures = "gvcguns:textures/misc/scope_svd.png";
//
//					gun.hasAttachRestriction = true;
//					gun.attachwhitelist.add("reddot");
//					gun.attachwhitelist.add("scope");
//					gun.zoomren = true;
//					gun.zoomrer = true;
//					gun.zoomres = false;
//					gun.zoomrent = false;
//					gun.zoomrert = false;
//					gun.zoomrest = true;
//					gun.canobj = true;
//					gun.reloadanim = true;
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 1f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							2f, -2f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(1, new Float[]{5f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(2, new Float[]{15f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(3, new Float[]{35f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.3f, 1.10f, 1.0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(4, new Float[]{40f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					//	gun.modelwidthz = 1.0F;
//					modelhigh = 1.10415f;
//					modelhighr = 0.9866f;
//
//					armrotationxr = -1.57f;
//					armrotationyr = 0f;
//					armrotationzr = -1.57f;
//					armoffsetxr = 0.4f;
//					armoffsetyr = 0.3f;
//					armoffsetzr = 0.5f;
//					armrotationxl = -0.0f;
//					armrotationyl = -2.3f;
//					armrotationzl = 2.0f;
//					armoffsetxl = 0.1f;
//					armoffsetyl = 0.3f;
//					armoffsetzl = -1.2f;
////				if(pEvent.getSide().isClient())
////				{
////					MinecraftForgeClient.registerItemRenderer(fn_aks74u,
////							new GVCRenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("gvcguns:textures/model/obj/AKS74U.obj"))));
////				}
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/obj/AKS74U.mqo"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhigh, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_aks74u, "AKS74U");
//				Guns.add(fn_aks74u);
//				
			}


			{
				fn_m10 = new GVCItemForTranslate().setUnlocalizedName("M10").setTextureName("gvcguns:M10")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_m10;
//					gun.setMaxDamage(32);
//                    gun.canuseclass = 0;
//					gun.powor = 5;
//					gun.speed = 18F/4;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 20.0F;
//					gun.ads_spread_cof = 0.9F;
//					gun.recoil = 3D;
//					gun.recoil_sneak = gun.recoil / 2;
//					gun.reloadtime = 30;
//					gun.cycle = 1;
//					gun.soundbase = "gvcguns:gvcguns.firehg";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 1.2F;
//					gun.magazine = fn_magazinehg;
//					gun.motion = 0.75D;
//					gun.scopezoom = 1.25F;
//					gun.scopezoomscope = 2.0F;
//					gun.scopezoomred = 1.5F;
//					gun.scopezoombase = 1.25F;
//					gun.texture = new ResourceLocation("gvcguns:textures/model/kai/M10.png");
//					gun.adstexture = "gvcguns:textures/misc/acog_ak74.png";
//					gun.adstexturer = "gvcguns:textures/misc/reddot.png";
//					gun.adstextures = "gvcguns:textures/misc/scope_svd.png";
//
//
//					gun.hasAttachRestriction = true;
//					gun.attachwhitelist.add("Suppressor");
//					gun.zoomren = true;
//					gun.zoomrer = true;
//					gun.zoomres = true;
//					gun.zoomrent = false;
//					gun.zoomrert = false;
//					gun.zoomrest = true;
//					gun.canobj = true;
//					gun.reloadanim = true;
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 1f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							2f, -2f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(1, new Float[]{10f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(2, new Float[]{15f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(3, new Float[]{20f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.3f, 1.10f, 1.0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(4, new Float[]{30f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					modelwidthz = 1.0F;
//					modelhigh = 1.07F;
//
//					armrotationxr = -1.57f;
//					armrotationyr = 0f;
//					armrotationzr = -1.57f;
//					armoffsetxr = 0.38F;
//					armoffsetyr = 0.4F;
//					armoffsetzr = 0.5F;
//					armrotationxl = -0.0f;
//					armrotationyl = -2.3f;
//					armrotationzl = 2.0f;
//					armoffsetxl = 0.1F;
//					armoffsetyl = 0.6F;
//					armoffsetzl = 0.2F;
//
////				rotationxs = -40;
////				rotationys = 0;
////				if(pEvent.getSide().isClient())
////				{
////					MinecraftForgeClient.registerItemRenderer(fn_m10,
////							new GVCRenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("gvcguns:textures/model/kai/M10.obj"))));
////				}
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/kai/M10.obj"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhigh, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_m10, "M10");
//				Guns.add(fn_m10);
//				
			}


			//fn_m1911	= new GVCItemM1911().setUnlocalizedName("M1911").setTextureName("gvcguns:m1911");
			//GameRegistry.registerItem(fn_m1911, "M1911");
			{
				fn_m1911 = new GVCItemForTranslate().setUnlocalizedName("M1911").setTextureName("gvcguns:M1911")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_m1911;
//                    gun.canuseclass = 0;
//					gun.setMaxDamage(8);
//					gun.powor = 8;
//					gun.speed = 13.5f/4;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 15.0F;
//					gun.ads_spread_cof = 0.9F;
//					gun.recoil = 2D;
//					gun.recoil_sneak = gun.recoil / 1.5;
//					gun.reloadtime = 30;
//					gun.cycle = 2;
//					gun.soundbase = "gvcguns:gvcguns.firehg";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 0.9F;
//
//					gun.magazine = fn_magazinehg;
//					gun.motion = 1.0D;
//					gun.scopezoom = 1.25F;
//					gun.scopezoomred = 1.5F;
//					gun.scopezoombase = 1.25F;
//					gun.scopezoomscope = 2F;
//					gun.adstexture = "gvcguns:textures/misc/scope.png";
//					gun.adstexturer = "gvcguns:textures/misc/reddot.png";
//					gun.adstextures = "gvcguns:textures/misc/scope.png";
//					//
//					gun.texture = new ResourceLocation("gvcguns:textures/model/kai/M1911.png");
//
//					gun.hasAttachRestriction = true;
//					gun.attachwhitelist.add("Suppressor");
//					gun.attachwhitelist.add("reddot");
//					gun.attachwhitelist.add("right");
//					gun.attachwhitelist.add("Bullet_AT");
//
//					gun.zoomren = true;
//					gun.zoomrer = true;
//					gun.zoomres = true;
//					gun.zoomrent = false;
//					gun.zoomrert = false;
//					gun.zoomrest = false;
//					gun.canobj = true;
//					gun.reloadanim = true;
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 1f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							2f, -2f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(1, new Float[]{8f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(2, new Float[]{15f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(3, new Float[]{24f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.3f, 1.10f, 1.0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(4, new Float[]{30f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					modelhigh = 1.27F;
//					modelhighr = 1.22F;
//					modelwidthz = 0.98F;
//
//					armrotationxr = -1.57f;
//					armrotationyr = 0f;
//					armrotationzr = -1.57f;
//					armoffsetxr = 0.38F;
//					armoffsetyr = 0.4F;
//					armoffsetzr = 0.5F;
//					armrotationxl = -0.0f;
//					armrotationyl = -2.3f;
//					armrotationzl = 2.0f;
//					armoffsetxl = 0.1F;
//					armoffsetyl = 0.6F;
//					armoffsetzl = 0.2F;
//					gun.Sprintrotationx = -40;
//					gun.Sprintrotationy = 0;
//					gun.burstcount.add(1);
//					gun.rates.add(2);
//
//					gun.jump = -10F;
////				if(pEvent.getSide().isClient())
////				{
////					MinecraftForgeClient.registerItemRenderer(fn_m1911,
////							new GVCRenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("gvcguns:textures/model/kai/M1911.obj"))));
////				}
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/kai/M1911.obj"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhighr, modelhighr, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_m1911, "M1911");
//				Guns.add(fn_m1911);
//				
			}

			//fn_m870	    = new GVCItemM870().setUnlocalizedName("M870").setTextureName("gvcguns:M870");
			//GameRegistry.registerItem(fn_m870, "M870");
			{
				fn_m870 = new GVCItemForTranslate().setUnlocalizedName("M870").setTextureName("gvcguns:M870")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun =  (HMGItem_Unified_Guns) fn_m870;
//                    gun.canuseclass = 1;
//					gun.setMaxDamage(8);
//					gun.powor = 4;
//					gun.speed = 10/4;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 35.0F;
//					gun.ads_spread_cof = 1F;
//					gun.recoil = 3D;
//					gun.recoil_sneak = gun.recoil / 1.5;
//					gun.reloadtime = 50;
//					gun.cycle = 2;
//					gun.soundbase = "gvcguns:gvcguns.fireSR";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundco = "gvcguns:gvcguns.cocking";
//					gun.soundspeed = 0.8F;
//					gun.cocktime = 10;
//					gun.magazine = fn_shell;
//					gun.magazineItemCount = 8;
//					gun.motion = 1.0D;
//					gun.scopezoom = 1.5F;
//					gun.scopezoomred = 1.5F;
//					gun.scopezoombase = 1.0F;
//					gun.scopezoomscope = 2F;
//					gun.adstexture = "gvcguns:textures/misc/scope.png";
//					gun.adstexturer = "gvcguns:textures/misc/reddot.png";
//					gun.adstextures = "gvcguns:textures/misc/scope.png";
//					//
//					gun.texture = new ResourceLocation("gvcguns:textures/model/kai/M870.png");
//					gun.hasAttachRestriction = true;
//					gun.attachwhitelist.add("Bullet_Dart");
//					gun.attachwhitelist.add("Bullet_Frag");
//					modelhigh = 1.3F;
//					//gun.modelwidthz = 0.99F;
//					gun.mat25rotationz = 0;
//					gun.mat25offsetx = 0;
//					gun.mat25offsety = 0.55F;
//					gun.mat25offsetz = 3.1F;
//					gun.cock_left = true;
//
//					gun.zoomren = true;
//					gun.zoomrer = true;
//					gun.zoomres = true;
//					gun.zoomrent = false;
//					gun.zoomrert = false;
//					gun.zoomrest = false;
//					gun.canobj = true;
//					gun.reloadanim = true;
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0f, 0.5f, -0.25f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(1, new Float[]{5f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0f, 0.5f, -0.5f, 0.5f, 0.5f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(2, new Float[]{10f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0f, 0.5f, -0.25f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(3, new Float[]{15f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0f, 0.5f, -0.25f, 0.5f, 0.5f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(4, new Float[]{20f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0f, 0.5f, -0.25f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(5, new Float[]{25f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0f, 0.5f, -0.25f, 0.5f, 0.5f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(6, new Float[]{30f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0f, 0.5f, -0.25f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(7, new Float[]{35f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0f, 0.5f, -0.25f, 0.5f, 0.5f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(8, new Float[]{40f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0f, 0.5f, -0.25f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(9, new Float[]{45f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0f, 0.5f, -0.25f, 0.5f, 0.5f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(10, new Float[]{50f,
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					armrotationxr = -1.57f;
//					armrotationyr = 0f;
//					armrotationzr = -1.57f;
//					armoffsetxr = 0.4f;
//					armoffsetyr = 0.3f;
//					armoffsetzr = 0.5f;
//					armrotationxl = -0.0f;
//					armrotationyl = -2.3f;
//					armrotationzl = 2.0f;
//					armoffsetxl = 0.1f;
//					armoffsetyl = 0.6f;
//					armoffsetzl = -1.8f;
//					gun.burstcount.add(1);
//					gun.rates.add(2);
//                    gun.shotgun_pellet = 9;
//                    gun.guntype = 1;
//					gun.needcock = true;
//
//					gun.jump = -5F;
////				if(pEvent.getSide().isClient())
////				{
////					MinecraftForgeClient.registerItemRenderer(fn_m870,
////							new GVCRenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("gvcguns:textures/model/kai/M870.obj"))));
////				}
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/kai/M870.obj"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhigh, modelhigh, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_m870, "M870");
//				Guns.add(fn_m870);
//				
			}
			{
				fn_svd = new GVCItemForTranslate().setUnlocalizedName("SVD").setTextureName("gvcguns:SVD")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_svd;
//                    gun.canuseclass = 2;
//					gun.setMaxDamage(10);
//					gun.powor = 12;
//					gun.speed = 10;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 4.0F;
//					gun.ads_spread_cof = 0.05F;
//					gun.recoil = 1.5D;
//					gun.recoil_sneak = gun.recoil / 4;
//					gun.reloadtime = 50;
//					gun.cycle = 2;
//					gun.semi = true;
//					gun.soundbase = "gvcguns:gvcguns.fire";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 0.8F;
//
//					gun.magazine = fn_magazine_762;
//					gun.motion = 0.9D;
//					gun.scopezoom = 4.0F;
//					gun.scopezoomscope = 4F;
//					gun.scopezoombase = 4.0F;
//					gun.scopezoomred = 4.0F;
//					gun.adstexture = "gvcguns:textures/misc/scope_svd.png";
//					gun.adstexturer = "gvcguns:textures/misc/scope_svd.png";
//					gun.adstextures = "gvcguns:textures/misc/scope_svd.png";
//					gun.hasAttachRestriction = true;
//					gun.attachwhitelist.add("Bullet_AT");
//
//					gun.zoomren = false;
//					gun.zoomrer = false;
//					gun.zoomres = false;
//					gun.zoomrent = true;
//					gun.zoomrert = true;
//					gun.zoomrest = true;
//					gun.texture = new ResourceLocation("gvcguns:textures/model/obj/SVD.png");
//					gun.reloadanim = true;
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 1f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							2f, -2f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(1, new Float[]{10f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(2, new Float[]{20f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(3, new Float[]{40f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.3f, 1.10f, 1.0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(4, new Float[]{50f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					modelhigh = 0.99F;
//					//gun.modelwidthz = 0.99F;
//
//					armrotationxr = -1.57f;
//					armrotationyr = 0f;
//					armrotationzr = -1.57f;
//					armoffsetxr = 0.4f;
//					armoffsetyr = 0.3f;
//					armoffsetzr = 0.5f;
//					armrotationxl = -0.0f;
//					armrotationyl = -2.3f;
//					armrotationzl = 2.0f;
//					armoffsetxl = 0.1f;
//					armoffsetyl = 0.3f;
//					armoffsetzl = -1.2f;
//					gun.burstcount.add(1);
//					gun.rates.add(2);
//
//					gun.jump = -5F;
////				if(pEvent.getSide().isClient())
////				{
////					MinecraftForgeClient.registerItemRenderer(fn_svd,
////							new GVCRenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("gvcguns:textures/model/obj/SVD.obj"))));
////				}
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/obj/SVD.mqo"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhighr, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_svd, "SVD");
//				Guns.add(fn_svd);
//				
			}
			{
				fn_pkm = new GVCItemForTranslate().setUnlocalizedName("PKM").setTextureName("gvcguns:PKM")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_pkm;
//                    gun.canuseclass = 3;
//					gun.setMaxDamage(100);
//					gun.powor = 12;
//					gun.speed = 41F/4;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 32.0F;
//					gun.ads_spread_cof = 0.05F;
//					gun.recoil = 2.5D;
//					gun.recoil_sneak = gun.recoil / 16;
//					gun.reloadtime = 100;
//					gun.cycle = 2;
//					gun.soundbase = "gvcguns:gvcguns.fire";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 0.8F;
//					gun.magazine = fn_magazinemg;
//					gun.motion = 0.75D;
//					gun.scopezoom = 1.5F;
//					gun.scopezoombase = 1.0F;
//					gun.scopezoomred = 1.5F;
//					gun.scopezoomscope = 1.5F;
//					gun.texture = new ResourceLocation("gvcguns:textures/model/kai/PKM.png");
//					gun.adstexture = "gvcguns:textures/misc/acog_ak74.png";
//					gun.adstexturer = "gvcguns:textures/misc/reddot.png";
//					gun.adstextures = "gvcguns:textures/misc/scope_svd.png";
//					gun.hasAttachRestriction = true;
//					gun.attachwhitelist.add("reddot");
//					gun.attachwhitelist.add("scope");
//
//					gun.zoomren = true;
//					gun.zoomrer = true;
//					gun.zoomres = false;
//					gun.zoomrent = false;
//					gun.zoomrert = false;
//					gun.zoomrest = true;
//					gun.canobj = true;
//					gun.reloadanim = true;
//					gun.bulletmodelN = "byfrou01_GreenTracer";
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 1f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							2f, -2f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(1, new Float[]{10f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(2, new Float[]{20f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(3, new Float[]{40f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.3f, 1.10f, 1.0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(4, new Float[]{50f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					//	gun.modelwidthz = 1.0F;
//					modelhigh = 0.59F;
//					modelhighr = 0.59f;
//					armrotationxr = -1.57f;
//					armrotationyr = 0f;
//					armrotationzr = -1.57f;
//					armoffsetxr = 0.4f;
//					armoffsetyr = 0.3f;
//					armoffsetzr = 0.5f;
//					armrotationxl = -0.0f;
//					armrotationyl = -2.3f;
//					armrotationzl = 2.0f;
//					armoffsetxl = 0.1f;
//					armoffsetyl = 0.3f;
//					armoffsetzl = -1.2f;
////				if(pEvent.getSide().isClient())
////				{
////					MinecraftForgeClient.registerItemRenderer(fn_pkm,
////							new GVCRenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("gvcguns:textures/model/kai/PKM.obj"))));
////				}
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/kai/PKM.obj"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhigh, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_pkm, "PKM");
//				Guns.add(fn_pkm);
//				
			}

			//
//	    fn_pkmsp	= new GVCItemForTranslate().setUnlocalizedName("PKMsp").setTextureName("gvcguns:PKMSP");
//	    GameRegistry.registerItem(fn_pkmsp, "PKM PSO-1");


			{
				fn_m16a4 = new GVCItemForTranslate().setUnlocalizedName("M16A4").setTextureName("gvcguns:M16A4")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_m16a4;
//                    gun.canuseclass = 0;
//					gun.setMaxDamage(30);
//					gun.powor = 8;
//					gun.speed = 44F/4;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 3.5F;
//					gun.ads_spread_cof = 0.4F;
//					gun.recoil = 1.8D;
//					gun.recoil_sneak = gun.recoil / 2;
//					gun.reloadtime = 50;
//					gun.cycle = 2;
//					gun.soundbase = "gvcguns:gvcguns.fire";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 1.2F;
//					gun.magazine = fn_magazine_556;
//					gun.motion = 1.0D;
//					gun.scopezoom = 1.5F;
//					gun.scopezoomred = 1.5F;
//					gun.scopezoombase = 1.0F;
//					gun.scopezoomscope = 2F;
//					gun.texture = new ResourceLocation("gvcguns:textures/model/obj/M16A4.png");
//					gun.adstexture = "gvcguns:textures/misc/acog_ak74.png";
//					gun.adstexturer = "gvcguns:textures/misc/reddot.png";
//					gun.adstextures = "gvcguns:textures/misc/scope_m110.png";
//					gun.hasAttachRestriction = true;
//					gun.attachwhitelist.add("Suppressor");
//					gun.attachwhitelist.add("reddot");
//					gun.attachwhitelist.add("scope");
//					gun.attachwhitelist.add("laser");
//					gun.attachwhitelist.add("grip");
//
//					gun.zoomren = true;
//					gun.zoomrer = true;
//					gun.zoomres = false;
//					gun.zoomrent = false;
//					gun.zoomrert = false;
//					gun.zoomrest = true;
//					gun.canobj = true;
//					gun.reloadanim = true;
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 1f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							2f, -2f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(1, new Float[]{10f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(2, new Float[]{20f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(3, new Float[]{40f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.3f, 1.10f, 1.0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(4, new Float[]{50f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					//	gun.modelwidthz = 1.0F;
//					modelhigh = 0.87325f;
//					modelhighr = 0.85f;
//
//					armrotationxr = -1.57f;
//					armrotationyr = 0f;
//					armrotationzr = -1.57f;
//					armoffsetxr = 0.4f;
//					armoffsetyr = 0.3f;
//					armoffsetzr = 0.5f;
//					armrotationxl = -0.0f;
//					armrotationyl = -2.3f;
//					armrotationzl = 2.0f;
//					armoffsetxl = 0.1f;
//					armoffsetyl = 0.3f;
//					armoffsetzl = -1.2f;
//					gun.burstcount.add(1);
//					gun.rates.add(2);
//					gun.burstcount.add(3);
//					gun.rates.add(2);
//					gun.burstcount.add(-1);
//					gun.rates.add(2);
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/obj/M16A4.mqo"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhigh, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_m16a4, "M16A4");
//				Guns.add(fn_m16a4);
//				
			}


			{
				fn_m4a1 = new GVCItemForTranslate().setUnlocalizedName("M4A1").setTextureName("gvcguns:M4A1")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_m4a1;
//                    gun.canuseclass = 0;
//					gun.setMaxDamage(30);
//					gun.powor = 8;
//					gun.speed = 45F/4;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 4.0F;
//					gun.ads_spread_cof = 0.6F;
//					gun.recoil = 1.2D;
//					gun.recoil_sneak = gun.recoil / 2;
//					gun.reloadtime = 50;
//					gun.cycle = 2;
//					gun.soundbase = "gvcguns:gvcguns.fire";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 1.2F;
//					gun.magazine = fn_magazine_556;
//					gun.motion = 1.0D;
//					gun.scopezoom = 1.5F;
//					gun.scopezoombase = 1.0F;
//					gun.scopezoomscope = 2F;
//					gun.scopezoomred = 1.5F;
//					gun.texture = new ResourceLocation("gvcguns:textures/model/obj/M4A1.png");
//					gun.adstexture = "gvcguns:textures/misc/acog_ak74.png";
//					gun.adstexturer = "gvcguns:textures/misc/reddot.png";
//					gun.adstextures = "gvcguns:textures/misc/scope_m110.png";
//					gun.hasAttachRestriction = true;
//					gun.attachwhitelist.add("Suppressor");
//					gun.attachwhitelist.add("reddot");
//					gun.attachwhitelist.add("scope");
//					gun.attachwhitelist.add("laser");
//					gun.attachwhitelist.add("grip");
//
//					gun.zoomren = true;
//					gun.zoomrer = true;
//					gun.zoomres = false;
//					gun.zoomrent = false;
//					gun.zoomrert = false;
//					gun.zoomrest = true;
//					gun.canobj = true;
//					gun.reloadanim = true;
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 1f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							2f, -2f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(1, new Float[]{10f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(2, new Float[]{20f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(3, new Float[]{40f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.3f, 1.10f, 1.0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(4, new Float[]{50f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					//	gun.modelwidthz = 1.0F;
//					modelhigh = 0.87325f;
//					modelhighr = 0.85f;
//
//					armrotationxr = -1.57f;
//					armrotationyr = 0f;
//					armrotationzr = -1.57f;
//					armoffsetxr = 0.4f;
//					armoffsetyr = 0.3f;
//					armoffsetzr = 0.5f;
//					armrotationxl = -0.0f;
//					armrotationyl = -2.3f;
//					armrotationzl = 2.0f;
//					armoffsetxl = 0.1f;
//					armoffsetyl = 0.3f;
//					armoffsetzl = -1.2f;
//					gun.burstcount.add(1);
//					gun.rates.add(2);
//					gun.burstcount.add(3);
//					gun.rates.add(2);
//					gun.burstcount.add(-1);
//					gun.rates.add(2);
////				if(pEvent.getSide().isClient())
////				{
////					MinecraftForgeClient.registerItemRenderer(fn_m4a1,
////							new GVCRenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("gvcguns:textures/model/obj/M4A1.obj"))));
////				}
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/obj/M4A1.mqo"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhigh, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_m4a1, "M4A1");
//				Guns.add(fn_m4a1);
//				
			}
  		/*fn_m249 = new HMGItemGun_LMG(8,4.0F,4.0F,1.5D,80,2.0F,1.0F,1.2F,4.0F,2,"gvcguns:gvcguns.fire","gvcguns:gvcguns.supu",
				"gvcguns:gvcguns.reload",false,0,new ResourceLocation("gvcguns:textures/model/obj/M4A1.png"),
				"gvcguns:textures/misc/ironsight_m16.png","gvcguns:textures/misc/reddot.png","gvcguns:textures/misc/acog_m16.png",
				fn_magazinemg,true).setUnlocalizedName("M249").setTextureName("gvcguns:M249")
				  .setMaxDamage(200).setCreativeTab(tabgvc);
		GameRegistry.registerItem(fn_m249, "M249");
		*/
			{
				fn_m249 = new GVCItemForTranslate().setUnlocalizedName("M249").setTextureName("gvcguns:M249")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_m249;
//                    gun.canuseclass = 3;
//					gun.setMaxDamage(200);
//					gun.powor = 8;
//					gun.speed = 41.5F/4;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 12.0F;
//					gun.ads_spread_cof = 0.1F;
//					gun.recoil = 2.0D;
//					gun.recoil_sneak = gun.recoil / 16;
//					gun.reloadtime = 100;
//					gun.cycle = 2;
//					gun.soundbase = "gvcguns:gvcguns.fire";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 0.8F;
//					gun.magazine = fn_magazinemg;
//					gun.motion = 0.75D;
//					gun.scopezoom = 1.5F;
//					gun.scopezoombase = 1.5F;
//					gun.scopezoomscope = 2F;
//					gun.scopezoomred = 2F;
//					gun.texture = new ResourceLocation("gvcguns:textures/model/kai/M249.png");
//					gun.adstexture = "gvcguns:textures/misc/acog_ak74.png";
//					gun.adstexturer = "gvcguns:textures/misc/reddot.png";
//					gun.adstextures = "gvcguns:textures/misc/scope_m110.png";
//					gun.hasAttachRestriction = true;
//					gun.attachwhitelist.add("reddot");
//					gun.attachwhitelist.add("scope");
//
//					gun.zoomren = true;
//					gun.zoomrer = true;
//					gun.zoomres = false;
//					gun.zoomrent = false;
//					gun.zoomrert = false;
//					gun.zoomrest = true;
//					gun.canobj = true;
//					gun.bulletmodelN = "byfrou01_PinkTracer";
//					gun.reloadanim = true;
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 1f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							2f, -2f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(1, new Float[]{10f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(2, new Float[]{20f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(3, new Float[]{40f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.3f, 1.10f, 1.0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(4, new Float[]{50f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					modelwidthz = 0.2F;
//					modelhigh = 0.81F;
//					armrotationxr = -1.57f;
//					armrotationyr = 0f;
//					armrotationzr = -1.57f;
//					armoffsetxr = 0.38F;
//					armoffsetyr = 0.4F;
//					armoffsetzr = 0.5F;
//					armrotationxl = -0.0f;
//					armrotationyl = -2.3f;
//					armrotationzl = 2.0f;
//					armoffsetxl = 0.1F;
//					armoffsetyl = 0.6F;
//					armoffsetzl = 0.2F;
////				if(pEvent.getSide().isClient())
////				{
////					MinecraftForgeClient.registerItemRenderer(fn_m249,
////							new GVCRenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("gvcguns:textures/model/kai/M249.obj"))));
////				}
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/kai/M249.obj"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhigh + 0.05f, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_m249, "M249");
//				Guns.add(fn_m249);
//				
			}

			//


		/*fn_m9	    = new GVCItemM9(6,3f,15f,1.5d,30,2.0f,1.0f).setUnlocalizedName("M9").setTextureName("gvcguns:M9")
				.setMaxDamage(16);
	    GameRegistry.registerItem(fn_m9, "M9");*/
			{
				fn_m9 = new GVCItemForTranslate().setUnlocalizedName("M9").setTextureName("gvcguns:M9")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_m9;
//					gun.setMaxDamage(16);
//                    gun.canuseclass = 0;
//					gun.powor = 6;
//					gun.speed = 18/4;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 15.0F;
//					gun.ads_spread_cof = 0.9F;
//					gun.recoil = 2D;
//					gun.recoil_sneak = gun.recoil / 2;
//					gun.reloadtime = 30;
//					gun.cycle = 2;
//					gun.soundbase = "gvcguns:gvcguns.firehg";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 1.0F;
//					gun.cocktime = 3;
//
//					gun.magazine = fn_magazinehg;
//					gun.motion = 1.0D;
//					gun.scopezoom = 1.25F;
//					gun.scopezoombase = 1.0F;
//					gun.scopezoomscope = 1.25F;
//					gun.scopezoomred = 1F;
//					gun.adstexture = "gvcguns:textures/misc/scope.png";
//					gun.adstexturer = "gvcguns:textures/misc/reddot.png";
//					gun.adstextures = "gvcguns:textures/misc/scope.png";
//					gun.hasAttachRestriction = true;
//					gun.attachwhitelist.add("Suppressor");
//					gun.attachwhitelist.add("reddot");
//					gun.attachwhitelist.add("laser");
//					gun.attachwhitelist.add("Bullet_AT");
//					//
//					gun.texture = new ResourceLocation("gvcguns:textures/model/kai/M9.png");
//
//					gun.zoomren = true;
//					gun.zoomrer = true;
//					gun.zoomres = true;
//					gun.zoomrent = false;
//					gun.zoomrert = false;
//					gun.zoomrest = false;
//					gun.canobj = true;
//					gun.reloadanim = true;
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 1f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							2f, -2f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(1, new Float[]{10f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(2, new Float[]{20f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(3, new Float[]{40f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.3f, 1.10f, 1.0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(4, new Float[]{50f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					modelhigh = 1.27F;
//					modelhighr = 1.22F;
//					modelwidthz = 0.98F;
//					armrotationxr = -1.57f;
//					armrotationyr = 0f;
//					armrotationzr = -1.57f;
//					armoffsetxr = 0.38F;
//					armoffsetyr = 0.4F;
//					armoffsetzr = 0.5F;
//					armrotationxl = -0.0f;
//					armrotationyl = -2.3f;
//					armrotationzl = 2.0f;
//					armoffsetxl = 0.1F;
//					armoffsetyl = 0.6F;
//					armoffsetzl = 0.2F;
//					gun.Sprintrotationx = -40;
//					gun.Sprintrotationy = 0;
//					gun.jump = -10F;
//					gun.burstcount.add(1);
//					gun.rates.add(2);
////				if(pEvent.getSide().isClient())
////				{
////					MinecraftForgeClient.registerItemRenderer(fn_m9,
////							new GVCRenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("gvcguns:textures/model/kai/M9.obj"))));
////				}
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/kai/M9.obj"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhighr, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_m9, "M9");
//				Guns.add(fn_m9);
//				
			}
//	    fn_p226	    = new GVCItemM9(6,3.5f,10f,1.2d,30,2.0f,1.0f).setUnlocalizedName("P226").setTextureName("gvcguns:P226")
//	    		.setMaxDamage(16);
//	    GameRegistry.registerItem(fn_p226, "P226");

	    /*fn_g17	    = new GVCItemM9(6,3f,15f,1.5d,30,2.0f,1.0f).setUnlocalizedName("G17").setTextureName("gvcguns:G17")
	    		.setMaxDamage(20);
	    GameRegistry.registerItem(fn_g17, "G17");*/
			{
				fn_g17 = new GVCItemForTranslate().setUnlocalizedName("G17").setTextureName("gvcguns:G17")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_g17;
//					gun.setMaxDamage(20);
//                    gun.canuseclass = 0;
//					gun.powor = 6;
//					gun.speed = 18/4;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 20.0F;
//					gun.ads_spread_cof = 0.9F;
//					gun.recoil = 2.5D;
//					gun.recoil_sneak = gun.recoil / 2;
//					gun.reloadtime = 30;
//					gun.cycle = 2;
//					gun.soundbase = "gvcguns:gvcguns.firehg";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 1.0F;
//					gun.cocktime = 3;
//
//					gun.magazine = fn_magazinehg;
//					gun.motion = 1.0D;
//					gun.scopezoom = 1.25F;
//					gun.scopezoombase = 1.0F;
//					gun.scopezoomscope = 1.25F;
//					gun.scopezoomred = 1F;
//					gun.adstexture = "gvcguns:textures/misc/scope.png";
//					gun.adstexturer = "gvcguns:textures/misc/reddot.png";
//					gun.adstextures = "gvcguns:textures/misc/scope.png";
//					gun.hasAttachRestriction = true;
//					gun.attachwhitelist.add("Bullet_AT");
//					//
//					gun.texture = new ResourceLocation("gvcguns:textures/model/kai/G17.png");
//
//					gun.zoomren = true;
//					gun.zoomrer = true;
//					gun.zoomres = true;
//					gun.zoomrent = false;
//					gun.zoomrert = false;
//					gun.zoomrest = false;
//					gun.canobj = true;
//					gun.reloadanim = true;
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 1f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							2f, -2f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(1, new Float[]{10f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(2, new Float[]{20f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(3, new Float[]{40f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.3f, 1.10f, 1.0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(4, new Float[]{50f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					modelhigh = 1.27F;
//					modelwidthz = 0.98F;
//					armrotationxr = -1.57f;
//					armrotationyr = 0f;
//					armrotationzr = -1.57f;
//					armoffsetxr = 0.38F;
//					armoffsetyr = 0.4F;
//					armoffsetzr = 0.5F;
//					armrotationxl = -0.0f;
//					armrotationyl = -2.3f;
//					armrotationzl = 2.0f;
//					armoffsetxl = 0.1F;
//					armoffsetyl = 0.6F;
//					armoffsetzl = 0.2F;
//					gun.Sprintrotationx = -40;
//					gun.Sprintrotationy = 0;
//					gun.jump = -10F;
//					gun.burstcount.add(1);
//					gun.rates.add(2);
////				if(pEvent.getSide().isClient())
////				{
////					MinecraftForgeClient.registerItemRenderer(fn_g17,
////							new GVCRenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("gvcguns:textures/model/kai/G17.obj"))));
////				}
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/kai/G17.obj"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhigh, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_g17, "G17");
//				Guns.add(fn_g17);
//				
			}

	    /*fn_g18	    = new GVCItemM10(6,3f,15f,1.5d,30,2.0f,1.0f).setUnlocalizedName("G18").setTextureName("gvcguns:G18")
	    		.setMaxDamage(20);
	    GameRegistry.registerItem(fn_g18, "G18C");*/
			{
				fn_g18 = new GVCItemForTranslate().setUnlocalizedName("G18").setTextureName("gvcguns:G18")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_g18;
//					gun.setMaxDamage(20);
//                    gun.canuseclass = 0;
//					gun.powor = 5;
//					gun.speed = 18F/4;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 20.0F;
//					gun.ads_spread_cof = 0.9F;
//					gun.recoil = 3.5D;
//					gun.recoil_sneak = gun.recoil / 1.5;
//					gun.reloadtime = 30;
//					gun.cycle = 1;
//					gun.soundbase = "gvcguns:gvcguns.firehg";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 1.0F;
//					gun.magazine = fn_magazinehg;
//					gun.motion = 1.0D;
//					gun.scopezoom = 1.25F;
//					gun.scopezoombase = 1.0F;
//					gun.scopezoomscope = 1.25F;
//					gun.scopezoomred = 1F;
//					gun.texture = new ResourceLocation("gvcguns:textures/model/kai/G17.png");
//					gun.adstexture = "gvcguns:textures/misc/acog_ak74.png";
//					gun.adstexturer = "gvcguns:textures/misc/reddot.png";
//					gun.adstextures = "gvcguns:textures/misc/scope.png";
//					gun.hasAttachRestriction = true;
//					gun.attachwhitelist.add("Bullet_AT");
//
//					gun.zoomren = true;
//					gun.zoomrer = true;
//					gun.zoomres = false;
//					gun.zoomrent = false;
//					gun.zoomrert = false;
//					gun.zoomrest = true;
//					gun.canobj = true;
//					gun.reloadanim = true;
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 1f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							2f, -2f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(1, new Float[]{10f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(2, new Float[]{20f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(3, new Float[]{40f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.3f, 1.10f, 1.0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(4, new Float[]{50f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					modelhigh = 1.27F;
//					modelwidthz = 0.98F;
//					armrotationxr = -1.57f;
//					armrotationyr = 0f;
//					armrotationzr = -1.57f;
//					armoffsetxr = 0.38F;
//					armoffsetyr = 0.4F;
//					armoffsetzr = 0.5F;
//					armrotationxl = -0.0f;
//					armrotationyl = -2.3f;
//					armrotationzl = 2.0f;
//					armoffsetxl = 0.1F;
//					armoffsetyl = 0.6F;
//					armoffsetzl = 0.2F;
//					gun.Sprintrotationx = -40;
//					gun.Sprintrotationy = 0;
//					gun.jump = -10F;
//					gun.burstcount.add(-1);
//					gun.rates.add(0);
//					gun.burstcount.add(1);
//					gun.rates.add(2);
////				if(pEvent.getSide().isClient())
////				{
////					MinecraftForgeClient.registerItemRenderer(fn_g18,
////							new GVCRenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("gvcguns:textures/model/kai/G17.obj"))));
////				}
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/kai/G17.obj"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhigh, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_g18, "G18C");
//				Guns.add(fn_g18);
//				
			}

	    /*fn_m110	    = new GVCItemSVD(14,4f,20f,1.5d,50,2.0f,8.0f).setUnlocalizedName("M110").setTextureName("gvcguns:M110")
	    		.setMaxDamage(20);;
	    GameRegistry.registerItem(fn_m110, "M110");*/
	    /*fn_m110 = new HMGItemGun_SR(14,4.0F,1.0F,1.5D,50,2.0F,4.0F,1.1F,8.0F,"gvcguns:gvcguns.fire","gvcguns:gvcguns.supu",
  				"gvcguns:gvcguns.reload",false, 0,new ResourceLocation("gvcguns:textures/model/obj/SVD.png"),true,"gvcguns:gvcguns.cooking",
  				"gvcguns:textures/misc/acog_m16.png","gvcguns:textures/misc/reddot.png","gvcguns:textures/misc/scope.png",
  				fn_magazine,true).setUnlocalizedName("M110").setTextureName("gvcguns:M110")
				  .setMaxDamage(20).setCreativeTab(tabgvc);
		GameRegistry.registerItem(fn_m110, "M110");
  		/*if(pEvent.getSide().isClient()){
  			MinecraftForgeClient.registerItemRenderer(GVCUtils.fn_m110, new HMGRenderItemGun_U(
					AdvancedModelLoader.loadModel(new ResourceLocation("gvcguns:textures/model/obj/SVD.obj")),
					new ResourceLocation("gvcguns:textures/model/obj/SVD.png"), 0.5F, 0.99F,0.70F,0.86F));
  		}*/

			{
				fn_m110 = new GVCItemForTranslate().setUnlocalizedName("M110").setTextureName("gvcguns:M110")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_m110;
//					gun.setMaxDamage(20);
//                    gun.canuseclass = 2;
//					gun.powor = 12;
//					gun.speed = 39/4;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 2.0F;
//					gun.ads_spread_cof = 0.05F;
//					gun.recoil = 1.5D;
//					gun.recoil_sneak = gun.recoil / 4;
//					gun.reloadtime = 50;
//					gun.cycle = 2;
//					gun.soundbase = "gvcguns:gvcguns.fire";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 0.8F;
//					gun.cocktime = 4;
//					gun.hasAttachRestriction = true;
//					gun.attachwhitelist.add("reddot");
//					gun.attachwhitelist.add("scope");
//
//					gun.magazine = fn_magazine_762;
//					gun.motion = 0.75D;
//					gun.scopezoom = 4F;
//					gun.scopezoombase = 4.0F;
//					gun.scopezoomscope = 2F;
//					gun.scopezoomred = 1F;
//					gun.adstexture = "gvcguns:textures/misc/scope_m110.png";
//					gun.adstexturer = "gvcguns:textures/misc/scope_m110.png";
//					gun.adstextures = "gvcguns:textures/misc/scope_m110.png";
//					gun.zoomren = false;
//					gun.zoomrer = true;
//					gun.zoomres = false;
//					gun.zoomrent = true;
//					gun.zoomrert = false;
//					gun.zoomrest = true;
//					gun.semi = true;
//					gun.texture = new ResourceLocation("gvcguns:textures/model/obj/M110.png");
//					gun.reloadanim = true;
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 1f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							2f, -2f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(1, new Float[]{10f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(2, new Float[]{20f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(3, new Float[]{40f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.3f, 1.10f, 1.0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(4, new Float[]{50f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					modelhigh = 0.99F;
//					modelhighr = 0.84f;
//					//gun.modelwidthz = 0.99F;
//
//					armrotationxr = -1.57f;
//					armrotationyr = 0f;
//					armrotationzr = -1.57f;
//					armoffsetxr = 0.4f;
//					armoffsetyr = 0.3f;
//					armoffsetzr = 0.5f;
//					armrotationxl = -0.0f;
//					armrotationyl = -2.3f;
//					armrotationzl = 2.0f;
//					armoffsetxl = 0.1f;
//					armoffsetyl = 0.3f;
//					armoffsetzl = -1.2f;
//
//					gun.jump = -5F;
//					gun.burstcount.add(1);
//					gun.rates.add(2);
////				if(pEvent.getSide().isClient())
////				{
////					MinecraftForgeClient.registerItemRenderer(fn_m110,
////							new GVCRenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("gvcguns:textures/model/kai/M110.obj"))));
////				}
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/obj/M110.mqo"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhighr, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_m110, "M110");
//				Guns.add(fn_m110);
//				
			}

	    /*fn_m240b = new HMGItemGun_LMG(12,4.0F,5.0F,1.5D,100,2.0F,1.0F,1.2F,4.0F,2,"gvcguns:gvcguns.fire","gvcguns:gvcguns.supu",
				"gvcguns:gvcguns.reload",false,0,new ResourceLocation("gvcguns:textures/model/obj/M4A1.png"),
				"gvcguns:textures/misc/ironsight_m16.png","gvcguns:textures/misc/reddot.png","gvcguns:textures/misc/acog_m16.png",
				fn_magazinemg,true).setUnlocalizedName("M240B").setTextureName("gvcguns:M240B")
				  .setMaxDamage(100).setCreativeTab(tabgvc);
		GameRegistry.registerItem(fn_m240b, "M240B");*/
			{
				fn_m240b = new GVCItemForTranslate().setUnlocalizedName("M240B").setTextureName("gvcguns:M240B")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_m240b;
//                    gun.canuseclass = 3;
//					gun.setMaxDamage(100);
//					gun.powor = 12;
//					gun.speed = 45F/4;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 24.0F;
//					gun.ads_spread_cof = 0.03F;
//					gun.recoil = 2.5D;
//					gun.recoil_sneak = gun.recoil / 16;
//					gun.reloadtime = 100;
//					gun.cycle = 2;
//					gun.soundbase = "gvcguns:gvcguns.fire";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 0.8F;
//					gun.magazine = fn_magazinemg;
//					gun.motion = 0.75D;
//					gun.scopezoom = 1.5F;
//					gun.scopezoombase = 1.5F;
//					gun.scopezoomscope = 2F;
//					gun.scopezoomred = 1.5F;
//					gun.texture = new ResourceLocation("gvcguns:textures/model/kai/M240B.png");
//					gun.adstexture = "gvcguns:textures/misc/acog_ak74.png";
//					gun.adstexturer = "gvcguns:textures/misc/reddot.png";
//					gun.adstextures = "gvcguns:textures/misc/scope_m110.png";
//					gun.hasAttachRestriction = true;
//					gun.attachwhitelist.add("reddot");
//					gun.attachwhitelist.add("scope");
//
//					gun.zoomren = true;
//					gun.zoomrer = true;
//					gun.zoomres = false;
//					gun.zoomrent = false;
//					gun.zoomrert = false;
//					gun.zoomrest = true;
//					gun.canobj = true;
//					gun.bulletmodelN = "byfrou01_PinkTracer";
//					gun.reloadanim = true;
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 1f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							2f, -2f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(1, new Float[]{10f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(2, new Float[]{20f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(3, new Float[]{40f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.3f, 1.10f, 1.0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(4, new Float[]{50f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					modelwidthz = 0.2F;
//					modelhigh = 0.81f;
//
//					armrotationxr = -1.57f;
//					armrotationyr = 0f;
//					armrotationzr = -1.57f;
//					armoffsetxr = 0.4f;
//					armoffsetyr = 0.3f;
//					armoffsetzr = 0.5f;
//					armrotationxl = -0.0f;
//					armrotationyl = -2.3f;
//					armrotationzl = 2.0f;
//					armoffsetxl = 0.1f;
//					armoffsetyl = 0.3f;
//					armoffsetzl = -1.2f;
////				if(pEvent.getSide().isClient())
////				{
////					MinecraftForgeClient.registerItemRenderer(fn_m240b,
////							new GVCRenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("gvcguns:textures/model/kai/M240B.obj"))));
////				}
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/kai/M240B.obj"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhigh, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_m240b, "M240B");
//				Guns.add(fn_m240b);
//				
			}

			//
//	    fn_m240bsp	= new GVCItemForTranslate().setUnlocalizedName("M240Bsp").setTextureName("gvcguns:M240Bsp");
//	    GameRegistry.registerItem(fn_m240bsp, "M240B ACOG");
	    /*fn_r700   	= new GVCItemR700(16,4f,20f,1.5d,50,2.0f,8.0f).setUnlocalizedName("R700").setTextureName("gvcguns:R700")
	    		.setMaxDamage(10);;
	    GameRegistry.registerItem(fn_r700, "R700");*/
	    /*fn_r700 = new HMGItemGun_SR(18,4.0F,1.0F,1.5D,50,2.0F,4.0F,1.1F,8.0F,"gvcguns:gvcguns.fire","gvcguns:gvcguns.supu",
  				"gvcguns:gvcguns.reload",false, 0,new ResourceLocation("gvcguns:textures/model/obj/SVD.png"),false,"gvcguns:gvcguns.cooking",
  				"gvcguns:textures/misc/scope.png","gvcguns:textures/misc/reddot.png","gvcguns:textures/misc/scope.png",
  				fn_magazine,true).setUnlocalizedName("R700").setTextureName("gvcguns:R700")
				  .setMaxDamage(20).setCreativeTab(tabgvc);
		GameRegistry.registerItem(fn_r700, "R700");*/
			{
				fn_r700 = new GVCItemForTranslate().setUnlocalizedName("R700").setTextureName("gvcguns:R700")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_r700;
//					gun.setMaxDamage(5);
//                    gun.canuseclass = 2;
//					gun.powor = 18;
//					gun.speed = 43/4;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 1.0F;
//					gun.ads_spread_cof = 0.05F;
//					gun.recoil = 1.5D;
//					gun.recoil_sneak = gun.recoil / 4;
//					gun.reloadtime = 50;
//					gun.cycle = 2;
//					gun.soundbase = "gvcguns:gvcguns.fire";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 0.8F;
//					gun.cocktime = 20;
//					gun.soundco = "gvcguns:gvcguns.cocking";
//					gun.magazine = fn_magazine_762_clip;
//					gun.motion = 0.85D;
//					gun.scopezoom = 4F;
//					gun.scopezoombase = 4.0F;
//					gun.scopezoomscope = 2F;
//					gun.adstexture = "gvcguns:textures/misc/scope_r700.png";
//					gun.adstexturer = "gvcguns:textures/misc/scope_r700.png";
//					gun.adstextures = "gvcguns:textures/misc/scope_r700.png";
//					gun.hasAttachRestriction = true;
//					gun.attachwhitelist.add("Bullet_AT");
//					gun.zoomren = false;
//					gun.zoomrer = false;
//					gun.zoomres = false;
//					gun.zoomrent = true;
//					gun.zoomrert = true;
//					gun.zoomrest = true;
//					gun.texture = new ResourceLocation("gvcguns:textures/model/kai/R700.png");
//					gun.reloadanim = true;
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, 0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							-0.2f, -1f, 0.75f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, 2f, -0.5f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(1, new Float[]{20f,
//							0f,
//							0f, 0.3f, 0.3f, -20f, 0f, -30f,
//
//							0f,
//
//
//							0.2f, -0.8f, 0.75f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, 1f, -0.5f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(2, new Float[]{40f,
//							0f,
//							0f, 0.3f, 0.3f, -20f, 0f, -30f,
//
//							0f,
//
//
//							0.2f, -0.3f, 0.75f, -1.0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(3, new Float[]{50f,
//							0f,
//							0f, 0.3f, 0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					modelhigh = 1.25F;
//					armrotationxr = -1.57f;
//					armrotationyr = 0f;
//					armrotationzr = -1.57f;
//					armoffsetxr = 0.4f;
//					armoffsetyr = 0.3f;
//					armoffsetzr = 0.5f;
//					armrotationxl = -0.0f;
//					armrotationyl = -1.8f;
//					armrotationzl = 2.0f;
//					armoffsetxl = 0.1f;
//					armoffsetyl = 0.3f;
//					armoffsetzl = -1.2f;
//					gun.burstcount.add(1);
//					gun.rates.add(2);
//					gun.needcock = true;
////				if(pEvent.getSide().isClient())
////				{
////					MinecraftForgeClient.registerItemRenderer(fn_r700,
////							new GVCRenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("gvcguns:textures/model/kai/R700.obj"))));
////				}
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/kai/R700.obj"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhigh, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_r700, "R700");
//				Guns.add(fn_r700);
//				
			}
	    /*fn_m82a3	= new GVCItemSVD(30,4f,30f,3.0d,60,2.0f,8.0f).setUnlocalizedName("M82A3").setTextureName("gvcguns:M82A3")
	    		.setMaxDamage(10);;
	    GameRegistry.registerItem(fn_m82a3, "M82A3");*/
	    /*fn_m82a3 = new HMGItemGun_AMR(30,4.0F,1.0F,1.5D,50,2.0F,4.0F,1.1F,8.0F,"gvcguns:gvcguns.fire","gvcguns:gvcguns.supu",
  				"gvcguns:gvcguns.reload",false, 0,new ResourceLocation("gvcguns:textures/model/obj/SVD.png"),true,"gvcguns:gvcguns.cooking",
  				"gvcguns:textures/misc/acog_m16.png","gvcguns:textures/misc/reddot.png","gvcguns:textures/misc/scope.png",
  				fn_magazine,true).setUnlocalizedName("M82A3").setTextureName("gvcguns:M82A3")
				  .setMaxDamage(10).setCreativeTab(tabgvc);
		GameRegistry.registerItem(fn_m82a3, "M82A3");*/
			{
				fn_m82a3 = new GVCItemForTranslate().setUnlocalizedName("M82A3").setTextureName("gvcguns:M82A3")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_m82a3;
//                    gun.canuseclass = 2;
//					gun.setMaxDamage(10);
//					gun.powor = 30;
//					gun.speed = 42.5f/4;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 8.0F;
//					gun.ads_spread_cof = 0.05F;
//					gun.recoil = 1.5D;
//					gun.recoil_sneak = gun.recoil / 8;
//					gun.reloadtime = 100;
//					gun.cycle = 2;
//					gun.soundbase = "gvcguns:gvcguns.fireSR";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 1.0F;
//					gun.cocktime = 4;
//
//					gun.magazine = fn_magazine_1270;
//					gun.motion = 0.5D;
//					gun.scopezoom = 4F;
//					gun.scopezoombase = 8.0F;
//					gun.scopezoomscope = 16.0F;
//					gun.adstexture = "gvcguns:textures/misc/scope_m82a3.png";
//					gun.adstexturer = "gvcguns:textures/misc/scope_m82a3.png";
//					gun.adstextures = "gvcguns:textures/misc/scope_m82a3_16.png";
//					gun.hasAttachRestriction = true;
//					gun.attachwhitelist.add("Bullet_Frag");
//
//					gun.zoomren = false;
//					gun.zoomrer = false;
//					gun.zoomres = false;
//					gun.zoomrent = true;
//					gun.zoomrert = true;
//					gun.zoomrest = true;
//					gun.semi = true;
//					gun.texture = new ResourceLocation("gvcguns:textures/model/kai/M82A3.png");
//					gun.reloadanim = true;
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 1f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							2f, -2f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(1, new Float[]{20f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(2, new Float[]{40f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(3, new Float[]{80f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.3f, 1.10f, 1.0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(4, new Float[]{100f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					modelhigh = 0.99F;
////				modelwidthz = 0.99F;
//
//					armrotationxr = -1.57f;
//					armrotationyr = 0f;
//					armrotationzr = -1.57f;
//					armoffsetxr = 0.4f;
//					armoffsetyr = 0.3f;
//					armoffsetzr = 0.5f;
//					armrotationxl = -0.0f;
//					armrotationyl = -2.3f;
//					armrotationzl = 2.0f;
//					armoffsetxl = 0.1f;
//					armoffsetyl = 0.3f;
//					armoffsetzl = -1.2f;
//					gun.burstcount.add(1);
//					gun.rates.add(2);
//
//					gun.jump = -5F;
////				if(pEvent.getSide().isClient())
////				{
////					MinecraftForgeClient.registerItemRenderer(fn_m82a3,
////							new GVCRenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("gvcguns:textures/model/kai/M82A3.obj"))));
////				}
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/kai/M82A3.obj"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhighr, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_m82a3, "M82A3");
//				Guns.add(fn_m82a3);
//				
			}
			{
				fn_g36 = new GVCItemForTranslate().setUnlocalizedName("G36").setTextureName("gvcguns:G36")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_g36;
//                    gun.canuseclass = 0;
//					gun.setMaxDamage(30);
//					gun.powor = 8;
//					gun.speed = 45F/4;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 3.5F;
//					gun.ads_spread_cof = 0.6F;
//					gun.recoil = 1.1D;
//					gun.recoil_sneak = gun.recoil / 3;
//					gun.reloadtime = 55;
//					gun.cycle = 2;
//					gun.soundbase = "gvcguns:gvcguns.fire";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 1.2F;
//					gun.magazine = fn_magazine_556;
//					gun.motion = 1.0D;
//					gun.scopezoom = 2F;
//					gun.scopezoombase = 1.5F;
//					gun.scopezoomscope = 2F;
//					gun.scopezoomred = 1F;
//					gun.texture = new ResourceLocation("gvcguns:textures/model/kai/G36.png");
//					gun.adstexture = "gvcguns:textures/misc/acog_ak74.png";
//					gun.adstexturer = "gvcguns:textures/misc/reddot.png";
//					gun.adstextures = "gvcguns:textures/misc/scope_r700.png";
//					gun.hasAttachRestriction = true;
//					gun.attachwhitelist.add("Suppressor");
//					gun.attachwhitelist.add("reddot");
//					gun.attachwhitelist.add("M320");
//					gun.attachwhitelist.add("laser");
//					gun.attachwhitelist.add("grip");
//
//					gun.zoomren = true;
//					gun.zoomrer = true;
//					gun.zoomres = false;
//					gun.zoomrent = false;
//					gun.zoomrert = false;
//					gun.zoomrest = true;
//					gun.canobj = true;
//					gun.reloadanim = true;
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 1f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							2f, -2f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(1, new Float[]{10f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(2, new Float[]{20f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(3, new Float[]{40f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.3f, 1.10f, 1.0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(4, new Float[]{50f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					//	gun.modelwidthz = 1.0F;
//					modelhigh = 0.725F;
//
//					armrotationxr = -1.57f;
//					armrotationyr = 0f;
//					armrotationzr = -1.57f;
//					armoffsetxr = 0.4f;
//					armoffsetyr = 0.3f;
//					armoffsetzr = 0.5f;
//					armrotationxl = -0.0f;
//					armrotationyl = -2.3f;
//					armrotationzl = 2.0f;
//					armoffsetxl = 0.1f;
//					armoffsetyl = 0.3f;
//					armoffsetzl = -1.2f;
//					gun.burstcount.add(1);
//					gun.rates.add(2);
//					gun.burstcount.add(3);
//					gun.rates.add(2);
//					gun.burstcount.add(-1);
//					gun.rates.add(2);
////				if(pEvent.getSide().isClient())
////				{
////					MinecraftForgeClient.registerItemRenderer(fn_g36,
////							new GVCRenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("gvcguns:textures/model/kai/G36.obj"))));
////				}
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/kai/G36.obj"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhigh, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_g36, "G36");
//				Guns.add(fn_g36);
				
			}
			{
				fn_g36c = new GVCItemForTranslate().setUnlocalizedName("G36C").setTextureName("gvcguns:G36C")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_g36c;
//					gun.setMaxDamage(30);
//                    gun.canuseclass = 0;
//					gun.powor = 6;
//					gun.speed = 45F/4;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 4.8F;
//					gun.ads_spread_cof = 0.75F;
//					gun.recoil = 1.8D;
//					gun.recoil_sneak = gun.recoil / 3;
//					gun.reloadtime = 45;
//					gun.cycle = 2;
//					gun.soundbase = "gvcguns:gvcguns.fire";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 1.2F;
//					gun.magazine = fn_magazine_556;
//					gun.motion = 1.0D;
//					gun.scopezoom = 2F;
//					gun.scopezoombase = 1.5F;
//					gun.scopezoomscope = 2F;
//					gun.scopezoomred = 1F;
//					gun.texture = new ResourceLocation("gvcguns:textures/model/obj/G36.png");
//					gun.adstexture = "gvcguns:textures/misc/acog_ak74.png";
//					gun.adstexturer = "gvcguns:textures/misc/reddot.png";
//					gun.adstextures = "gvcguns:textures/misc/scope_r700.png";
//					gun.hasAttachRestriction = true;
//
//					gun.zoomren = true;
//					gun.zoomrer = true;
//					gun.zoomres = false;
//					gun.zoomrent = false;
//					gun.zoomrert = false;
//					gun.zoomrest = true;
//					gun.canobj = true;
//					gun.reloadanim = true;
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 1f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							2f, -2f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(1, new Float[]{10f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(2, new Float[]{20f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(3, new Float[]{40f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.3f, 1.10f, 1.0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(4, new Float[]{50f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					//	gun.modelwidthz = 1.0F;
//					modelhigh = 0.725F;
//
//					armrotationxr = -1.57f;
//					armrotationyr = 0f;
//					armrotationzr = -1.57f;
//					armoffsetxr = 0.4f;
//					armoffsetyr = 0.3f;
//					armoffsetzr = 0.5f;
//					armrotationxl = -0.0f;
//					armrotationyl = -2.3f;
//					armrotationzl = 2.0f;
//					armoffsetxl = 0.1f;
//					armoffsetyl = 0.3f;
//					armoffsetzl = -1.2f;
//					gun.burstcount.add(1);
//					gun.rates.add(2);
//					gun.burstcount.add(3);
//					gun.rates.add(2);
//					gun.burstcount.add(-1);
//					gun.rates.add(2);
////				if(pEvent.getSide().isClient())
////				{
////					MinecraftForgeClient.registerItemRenderer(fn_g36c,
////							new GVCRenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("gvcguns:textures/model/obj/G36C.obj"))));
////				}
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/obj/G36C.obj"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhigh, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_g36c, "G36C");
//				Guns.add(fn_g36c);
				
			}
			{
				fn_mg36 = new GVCItemForTranslate().setUnlocalizedName("MG36").setTextureName("gvcguns:MG36")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_mg36;
//					gun.setMaxDamage(50);
//                    gun.canuseclass = 3;
//					gun.powor = 6;
//					gun.speed = 45F/4;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 20.0F;
//					gun.ads_spread_cof = 0.07F;
//					gun.recoil = 1.1D;
//					gun.recoil_sneak = gun.recoil / 12;
//					gun.reloadtime = 60;
//					gun.cycle = 2;
//					gun.soundbase = "gvcguns:gvcguns.fire";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 1.2F;
//					gun.magazine = fn_magazinemg;
//					gun.motion = 0.85D;
//					gun.scopezoom = 2F;
//					gun.scopezoombase = 1.5F;
//					gun.scopezoomscope = 2F;
//					gun.scopezoomred = 1F;
//					gun.texture = new ResourceLocation("gvcguns:textures/model/kai/MG36.png");
//					gun.adstexture = "gvcguns:textures/misc/acog_ak74.png";
//					gun.adstexturer = "gvcguns:textures/misc/reddot.png";
//					gun.adstextures = "gvcguns:textures/misc/scope_r700.png";
//					gun.hasAttachRestriction = true;
//
//					gun.zoomren = true;
//					gun.zoomrer = true;
//					gun.zoomres = false;
//					gun.zoomrent = false;
//					gun.zoomrert = false;
//					gun.zoomrest = true;
//					gun.canobj = true;
//					gun.bulletmodelN = "byfrou01_PinkTracer";
//					gun.reloadanim = true;
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 1f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							2f, -2f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(1, new Float[]{10f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(2, new Float[]{20f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(3, new Float[]{40f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.3f, 1.10f, 1.0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(4, new Float[]{50f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					//	gun.modelwidthz = 1.0F;
//					modelhigh = 0.725F;
//
//					armrotationxr = -1.57f;
//					armrotationyr = 0f;
//					armrotationzr = -1.57f;
//					armoffsetxr = 0.4f;
//					armoffsetyr = 0.3f;
//					armoffsetzr = 0.5f;
//					armrotationxl = -0.0f;
//					armrotationyl = -2.3f;
//					armrotationzl = 2.0f;
//					armoffsetxl = 0.1f;
//					armoffsetyl = 0.3f;
//					armoffsetzl = -1.2f;
//					gun.burstcount.add(1);
//					gun.rates.add(2);
//					gun.burstcount.add(-1);
//					gun.rates.add(2);
////				if(pEvent.getSide().isClient())
////				{
////					MinecraftForgeClient.registerItemRenderer(fn_mg36,
////							new GVCRenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("gvcguns:textures/model/kai/MG36.obj"))));
////				}
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/kai/MG36.obj"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhigh, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_mg36, "MG36");
//				Guns.add(fn_mg36);
				
			}


			{
				fn_rpg7 = new GVCItemForTranslate().setUnlocalizedName("RPG7").setTextureName("gvcguns:RPG7")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_rpg7;
//                    gun.canuseclass = 4;
//					gun.setMaxDamage(1);
//					gun.powor = 75;
//					gun.speed = 5.5F/4;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 8.0F;
//					gun.ads_spread_cof = 1F;
//					gun.recoil = 1.5D;
//					gun.recoil_sneak = gun.recoil / 2;
//					gun.reloadtime = 60;
//					gun.cycle = 2;
//					gun.soundbase = "gvcguns:gvcguns.fire";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 1.0F;
//					gun.cocktime = 5;
//
//					gun.magazine = fn_rpg;
//					gun.motion = 0.9D;
//					gun.scopezoom = 2F;
//					gun.scopezoombase = 1.5F;
//					gun.scopezoomscope = 2F;
//					gun.scopezoomred = 1F;
//					gun.adstexture = "gvcguns:textures/misc/scope.png";
//					gun.adstexturer = "gvcguns:textures/misc/reddot.png";
//					gun.adstextures = "gvcguns:textures/misc/scope.png";
//					gun.hasAttachRestriction = true;
//					//
//					gun.ex = 3.5F;
//					gun.canex = cfg_exprotion;
//					gun.gra = 0.025F;
//					gun.texture = new ResourceLocation("gvcguns:textures/model/kai/RPG7.png");
//
//					gun.zoomren = true;
//					gun.zoomrer = true;
//					gun.zoomres = false;
//					gun.zoomrent = false;
//					gun.zoomrert = false;
//					gun.zoomrest = true;
//					gun.canobj = true;
//					gun.dropcart = false;
//					gun.reloadanim = true;
//					gun.acceleration = 0.4f;
//					gun.bulletmodelRPG = "byfrou01_Rocket";
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							-0.2f, 1.2f, -1.8f, 0f, -1.27f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -2f, 2f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(1, new Float[]{10f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							-0.2f, 0.4f, -1.8f, 0f, -1.27f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 2f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(2, new Float[]{20f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							-0.2f, -1.0f, -1.8f, 0f, -1.27f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, 0f, 2f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(3, new Float[]{40f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0f, -0.8f, -1f, 0f, -1.27f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(4, new Float[]{50f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					modelhigh = 0.90F;
//					//gun.modelwidthz = 0.99F;
//
//					armrotationxr = -1.57f;
//					armrotationyr = 0f;
//					armrotationzr = -1.57f;
//					armoffsetxr = 0.4f;
//					armoffsetyr = 0.3f;
//					armoffsetzr = 0.5f;
//					armrotationxl = -0.0f;
//					armrotationyl = -0.73f;
//					armrotationzl = 2.0f;
//					armoffsetxl = 0.1f;
//					armoffsetyl = 0.3f;
//					armoffsetzl = 0f;
//
//					gun.Sprintrotationx = 40F;
//					gun.Sprintrotationy = 10F;
//					//	gun.Sprintoffsetz = -2.0F;
//					gun.jump = -5F;
//					gun.guntype = 3;
//					gun.burstcount.add(1);
//					gun.rates.add(2);
////				if(pEvent.getSide().isClient())
////				{
////					MinecraftForgeClient.registerItemRenderer(fn_rpg7,
////							new GVCRenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("gvcguns:textures/model/kai/RPG7.obj"))));
////				}
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/kai/RPG7.obj"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhigh, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_rpg7, "RPG7");
//				Guns.add(fn_rpg7);
				
			}
			{
				fn_smaw = new GVCItemForTranslate().setUnlocalizedName("SMAW").setTextureName("gvcguns:smaw");
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_smaw;
//                    gun.canuseclass = 4;
//					gun.setMaxDamage(1);
//					gun.powor = 120;
//					gun.speed = 1.5f;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 4.0F;
//					gun.ads_spread_cof = 1F;
//					gun.recoil = 0D;
//					gun.recoil_sneak = gun.recoil / 2;
//					gun.reloadtime = 120;
//					gun.cycle = 2;
//					gun.soundbase = "gvcguns:gvcguns.cannon";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 1.0F;
//					gun.cocktime = 5;
//
//					gun.magazine = fn_missile;
//					gun.motion = 0.6D;
//					gun.scopezoom = 2F;
//					gun.scopezoombase = 1.5F;
//					gun.scopezoomscope = 2F;
//					gun.scopezoomred = 1.5F;
//					gun.adstexture = "gvcguns:textures/misc/scope_mbtlaw.png";
//					gun.adstexturer = "gvcguns:textures/misc/reddot.png";
//					gun.adstextures = "gvcguns:textures/misc/scope_mbtlaw.png";
//					//
//					gun.ex = 3.5F;
//					gun.canex = cfg_exprotion;
//					gun.gra = 0.025F;
//					gun.texture = new ResourceLocation("gvcguns:textures/model/kai/SMAW.png");
//					gun.hasAttachRestriction = true;
//					gun.attachwhitelist.add("Bullet_TE");
//
//					gun.zoomren = false;
//					gun.zoomrer = true;
//					gun.zoomres = false;
//					gun.zoomrent = true;
//					gun.zoomrert = false;
//					gun.zoomrest = true;
//					gun.canobj = true;
//					gun.dropcart = false;
//					gun.reloadanim = true;
//					gun.guerrila_can_use = false;
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, -0.3f, -0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							-0.2f, 1.2f, 3.8f, 0f, -1.27f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -2f, -1f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(1, new Float[]{20f,
//							0f,
//							0f, -0.3f, -0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							-0.2f, 0.4f, 3.8f, 0f, -1.27f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, -2f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(2, new Float[]{40f,
//							0f,
//							0f, -0.3f, -0.3f, 40f, 0f, 0f,
//
//							0f,
//
//
//							-0.2f, -1.0f, 3.8f, 0f, -1.27f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, 0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(3, new Float[]{80f,
//							0f,
//							0f, -0.3f, -0.3f, 40f, 0f, 0f,
//
//							0f,
//
//
//							0f, -0.8f, 1.5f, 0f, -1.27f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(4, new Float[]{100f,
//							0f,
//							0f, -0.3f, -0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(5, new Float[]{120f,
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					modelhigh = 0.90F;
//					//gun.modelwidthz = 0.99F;
//
//					armrotationxr = -1.57f;
//					armrotationyr = 0f;
//					armrotationzr = -1.57f;
//					armoffsetxr = 0.4f;
//					armoffsetyr = 0.3f;
//					armoffsetzr = 0.5f;
//					armrotationxl = -0.0f;
//					armrotationyl = -2.3f;
//					armrotationzl = 2.0f ;
//					armoffsetxl =  0.1f;
//					armoffsetyl =  0.3f;
//					armoffsetzl = -1.2f;
//
//					gun.Sprintrotationx = 40F;
//					gun.Sprintrotationy = 10F;
//					//	gun.Sprintoffsetz = -2.0F;
//					gun.jump = 0F;
//					gun.guntype = 3;
//					gun.canlock = true;
//					gun.induction_precision = 7.5f;
//					gun.acceleration = 0.5f;
//					gun.bulletmodelRPG = "byfrou01_Rocket";
//					gun.resistance = 0.9f;
//					gun.burstcount.add(1);
//					gun.rates.add(2);
//					modelscala = 5;
////				if(pEvent.getSide().isClient())
////				{
////					MinecraftForgeClient.registerItemRenderer(fn_rpg7,
////							new GVCRenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("gvcguns:textures/model/kai/RPG7.obj"))));
////				}
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/kai/SMAW.obj"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhigh, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox+0.2f, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_smaw, "SMAW");
//				Guns.add(fn_smaw);
				
				modelscala = 0.5f;
			}


//		fn_fim92	    = new GVCItemFIM92().setUnlocalizedName("FIM92").setTextureName("gvcguns:FIM92");
//		GameRegistry.registerItem(fn_fim92, "FIM92");
//		fn_mbtlaw	    = new GVCItemMBTLAW().setUnlocalizedName("MBTLAW").setTextureName("gvcguns:MBTLAW");
//		GameRegistry.registerItem(fn_mbtlaw, "MBTLAW");


			//fn_m134	= new GVCItemM134().setUnlocalizedName("M134").setTextureName("gvcguns:M134");
			//GameRegistry.registerItem(fn_m134, "M134");
			{
				fn_m134 = new GVCItemForTranslate().setUnlocalizedName("M134").setTextureName("gvcguns:M134")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_m134;
//                    gun.canuseclass = 3;
//                    gun.guerrila_can_use = false;
//					gun.setMaxDamage(300);
//					gun.powor = 16;
//					gun.speed = 40F/4;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 8.0f;
//					gun.ads_spread_cof = 0.8f;
//					gun.recoil = 3D;
//					gun.recoil_sneak = gun.recoil / 32;
//					gun.reloadtime = 60;
//					gun.cycle = 1;
//					gun.soundbase = "gvcguns:gvcguns.fire";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 0.8F;
//					gun.magazine = fn_magazinemg;
//					gun.motion = 0.3D;
//					gun.scopezoom = 1F;
//					gun.scopezoombase = 2.0F;
//					gun.scopezoomscope = 2F;
//					gun.scopezoomred = 2F;
//					gun.texture = new ResourceLocation("gvcguns:textures/model/kai/M134.png");
//					gun.adstexture = "gvcguns:textures/misc/acog_ak74.png";
//					gun.adstexturer = "gvcguns:textures/misc/reddot.png";
//					gun.adstextures = "gvcguns:textures/misc/scope.png";
//					gun.hasAttachRestriction = true;
//
//					gun.zoomren = true;
//					gun.zoomrer = true;
//					gun.zoomres = true;
//					gun.zoomrent = false;
//					gun.zoomrert = true;
//					gun.zoomrest = true;
//					gun.canobj = true;
//					gun.reloadanim = true;
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 1f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							2f, -2f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(1, new Float[]{10f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(2, new Float[]{20f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(3, new Float[]{40f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.3f, 1.10f, 1.0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(4, new Float[]{50f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					mat31posy = 0.5f;
//					mat31rotex = 0;
//					mat31rotey = 0;
//					mat31rotez = 60;
////				modelwidthz = 0F;
//					modelhighr = 1F;
//					modelwidthx = -0.1F;
//					rotationx = 180F;
//					rotationy = 50F;
//					rotationz = 180F;
//					armrotationxr =-1.57f;
//					armrotationxr =0f;
//					armrotationxr =-1.57f;
//					armoffsetxr = 0.5f;
//					armoffsetyr = 0.25f;
//					armoffsetzr = 0.5f;
//
//					armrotationxl =0.0f;
//					armrotationxl =2.3f;
//					armrotationxl =-2.0f;
//					armoffsetxl = 0.1f;
//					armoffsetyl = -0.4f;
//					armoffsetzl = -1.3f;
//
//					gun.guntype = 1;
//					gun.shotgun_pellet = 3;
//					gun.bulletmodelN = "byfrou01_PinkTracer";
////				if(pEvent.getSide().isClient())
////				{
////					MinecraftForgeClient.registerItemRenderer(fn_m134,
////							new GVCRenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("gvcguns:textures/model/kai/M134.obj"))));
////				}
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/kai/M134.obj"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhigh, modelhigh, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.registerItem(fn_m134, "M134");
//				Guns.add(fn_m134);
				
			}
			{
				fn_m2f = new GVCItemForTranslate().setUnlocalizedName("M2F").setTextureName("gvcguns:M2F")
						.setCreativeTab(tabgvc).setMaxStackSize(1);
//				{
//					HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) fn_m2f;
//					gun.canuseclass = 5;
//					gun.setMaxDamage(300);
//					gun.powor = 16;
//					gun.speed = 10/4;
//					gun.fuse = 12;
//					gun.gra = (float) (0.0485*10);
//					gun.spread_setting = 1.0f;
//					gun.ads_spread_cof = 1;
//					gun.recoil = 3D;
//					gun.recoil_sneak = gun.recoil / 32;
//					gun.reloadtime = 60;
//					gun.cycle = 1;
//					gun.muzzleflash = false;
//					gun.dropcart = false;
//					gun.soundbase = "gvcguns:gvcguns.fireee";
//					gun.soundre = "gvcguns:gvcguns.reload";
//					gun.soundspeed = 0.8F;
//					gun.magazine = Items.lava_bucket;
//					gun.motion = 0.8f;
//					gun.scopezoom = 1F;
//					gun.scopezoombase = 2.0F;
//					gun.texture = new ResourceLocation("gvcguns:textures/model/kai/M2F.png");
//					gun.hasAttachRestriction = true;
//
//					gun.zoomren = true;
//					gun.zoomrer = true;
//					gun.zoomres = true;
//					gun.zoomrent = false;
//					gun.zoomrert = true;
//					gun.zoomrest = true;
//					gun.canobj = true;
//					gun.reloadanim = true;
//					gun.reloadanimation.add(0, new Float[]{0f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 1f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							2f, -2f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(1, new Float[]{10f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(2, new Float[]{20f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.8f, 1.10f, 0f, 0f, 0f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//
//							0f,
//							0f, -1f, 0f, 0f, 0f, 0f, 0f, 0f, 40f});
//					gun.reloadanimation.add(3, new Float[]{40f,
//							0f,
//							0f, -0.3f, 0.3f, -20f, 0f, 0f,
//
//							0f,
//
//
//							0.2f, 0.3f, 1.10f, 1.0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					gun.reloadanimation.add(4, new Float[]{50f,
//							0f,
//							0f, -0.3f, 0.3f, 0f, 0f, 0f,
//
//							1f,
//
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f, 0f, 0f, 0f, 0f, 0f,
//
//							0f,
//							0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
//					mat31posy = 0.5f;
//					mat31rotex = 0;
//					mat31rotey = 0;
//					mat31rotez = 60;
////				modelwidthz = 0F;
//					modelhighr = 1F;
//					modelwidthx = -0.1F;
//					rotationx = 180F;
//					rotationy = 50F;
//					rotationz = 180F;
//					gun.guntype = 5;
//					gun.shotgun_pellet = 1;
//					gun.bulletmodelN = "byfrou01_Flame";
//					armoffsetxl += 0.5;
//					armoffsetyl -= 0.1f;
//					armoffsetzl += 0.8f;
//					armoffsetyr -=0.2f;
//					if (pEvent.getSide().isClient()) {
//						IModelCustom gunobj = AdvancedModelLoader
//								.loadModel(new ResourceLocation("gvcguns:textures/model/kai/M2F.obj"));
//						MinecraftForgeClient.registerItemRenderer(gun, new HMGRenderItemGun_U(gunobj, gun.texture,
//								modelscala, modelhigh, modelhigh, modelhigh, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
//								, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
//								, rotationz, rotationzr, rotationzs,
//								arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
//								, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
//								nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
//								, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
//					}
//				}
				GameRegistry.addRecipe(new ItemStack(fn_m2f, 1),
						"abb",
						"bb ",
						'a', Items.flint,'b',Items.iron_ingot
				);
				GameRegistry.registerItem(fn_m2f, "M2F");
//				Guns.add(fn_m2f);
				
			}
		}
	}

	public void registerTileEntity() {
		//GameRegistry.registerTileEntity(GVCTileEntityBlockIED.class, "GVCTileEntityBlockIED");
	}

	public boolean reload(){
		return false;
	}

}