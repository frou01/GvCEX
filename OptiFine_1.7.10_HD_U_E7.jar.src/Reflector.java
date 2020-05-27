/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Reflector
/*     */ {
/*  39 */   public static ReflectorClass ModLoader = new ReflectorClass("ModLoader");
/*  40 */   public static ReflectorMethod ModLoader_renderWorldBlock = new ReflectorMethod(ModLoader, "renderWorldBlock");
/*  41 */   public static ReflectorMethod ModLoader_renderInvBlock = new ReflectorMethod(ModLoader, "renderInvBlock");
/*  42 */   public static ReflectorMethod ModLoader_renderBlockIsItemFull3D = new ReflectorMethod(ModLoader, "renderBlockIsItemFull3D");
/*  43 */   public static ReflectorMethod ModLoader_registerServer = new ReflectorMethod(ModLoader, "registerServer");
/*  44 */   public static ReflectorMethod ModLoader_getCustomAnimationLogic = new ReflectorMethod(ModLoader, "getCustomAnimationLogic");
/*     */   
/*  46 */   public static ReflectorClass FMLRenderAccessLibrary = findReflectorClass(new String[] { "FMLRenderAccessLibrary", "net.minecraft.src.FMLRenderAccessLibrary" });
/*  47 */   public static ReflectorMethod FMLRenderAccessLibrary_renderWorldBlock = new ReflectorMethod(FMLRenderAccessLibrary, "renderWorldBlock");
/*  48 */   public static ReflectorMethod FMLRenderAccessLibrary_renderInventoryBlock = new ReflectorMethod(FMLRenderAccessLibrary, "renderInventoryBlock");
/*  49 */   public static ReflectorMethod FMLRenderAccessLibrary_renderItemAsFull3DBlock = new ReflectorMethod(FMLRenderAccessLibrary, "renderItemAsFull3DBlock");
/*     */   
/*  51 */   public static ReflectorClass LightCache = new ReflectorClass("LightCache");
/*  52 */   public static ReflectorField LightCache_cache = new ReflectorField(LightCache, "cache");
/*  53 */   public static ReflectorMethod LightCache_clear = new ReflectorMethod(LightCache, "clear");
/*     */   
/*  55 */   public static ReflectorClass BlockCoord = new ReflectorClass("BlockCoord");
/*  56 */   public static ReflectorMethod BlockCoord_resetPool = new ReflectorMethod(BlockCoord, "resetPool");
/*     */   
/*  58 */   public static ReflectorClass MinecraftForge = new ReflectorClass("net.minecraftforge.common.MinecraftForge");
/*  59 */   public static ReflectorField MinecraftForge_EVENT_BUS = new ReflectorField(MinecraftForge, "EVENT_BUS");
/*     */   
/*  61 */   public static ReflectorClass ForgeHooks = new ReflectorClass("net.minecraftforge.common.ForgeHooks");
/*  62 */   public static ReflectorMethod ForgeHooks_onLivingSetAttackTarget = new ReflectorMethod(ForgeHooks, "onLivingSetAttackTarget");
/*  63 */   public static ReflectorMethod ForgeHooks_onLivingUpdate = new ReflectorMethod(ForgeHooks, "onLivingUpdate");
/*  64 */   public static ReflectorMethod ForgeHooks_onLivingAttack = new ReflectorMethod(ForgeHooks, "onLivingAttack");
/*  65 */   public static ReflectorMethod ForgeHooks_onLivingHurt = new ReflectorMethod(ForgeHooks, "onLivingHurt");
/*  66 */   public static ReflectorMethod ForgeHooks_onLivingDeath = new ReflectorMethod(ForgeHooks, "onLivingDeath");
/*  67 */   public static ReflectorMethod ForgeHooks_onLivingDrops = new ReflectorMethod(ForgeHooks, "onLivingDrops");
/*  68 */   public static ReflectorMethod ForgeHooks_onLivingFall = new ReflectorMethod(ForgeHooks, "onLivingFall");
/*  69 */   public static ReflectorMethod ForgeHooks_onLivingJump = new ReflectorMethod(ForgeHooks, "onLivingJump");
/*     */   
/*  71 */   public static ReflectorClass MinecraftForgeClient = new ReflectorClass("net.minecraftforge.client.MinecraftForgeClient");
/*  72 */   public static ReflectorMethod MinecraftForgeClient_getRenderPass = new ReflectorMethod(MinecraftForgeClient, "getRenderPass");
/*  73 */   public static ReflectorMethod MinecraftForgeClient_getItemRenderer = new ReflectorMethod(MinecraftForgeClient, "getItemRenderer");
/*     */   
/*  75 */   public static ReflectorClass ForgeHooksClient = new ReflectorClass("net.minecraftforge.client.ForgeHooksClient");
/*  76 */   public static ReflectorMethod ForgeHooksClient_onDrawBlockHighlight = new ReflectorMethod(ForgeHooksClient, "onDrawBlockHighlight");
/*  77 */   public static ReflectorMethod ForgeHooksClient_orientBedCamera = new ReflectorMethod(ForgeHooksClient, "orientBedCamera");
/*  78 */   public static ReflectorMethod ForgeHooksClient_renderEquippedItem = new ReflectorMethod(ForgeHooksClient, "renderEquippedItem");
/*  79 */   public static ReflectorMethod ForgeHooksClient_dispatchRenderLast = new ReflectorMethod(ForgeHooksClient, "dispatchRenderLast");
/*  80 */   public static ReflectorMethod ForgeHooksClient_onTextureLoadPre = new ReflectorMethod(ForgeHooksClient, "onTextureLoadPre");
/*  81 */   public static ReflectorMethod ForgeHooksClient_setRenderPass = new ReflectorMethod(ForgeHooksClient, "setRenderPass");
/*  82 */   public static ReflectorMethod ForgeHooksClient_onTextureStitchedPre = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPre");
/*  83 */   public static ReflectorMethod ForgeHooksClient_onTextureStitchedPost = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPost");
/*  84 */   public static ReflectorMethod ForgeHooksClient_renderFirstPersonHand = new ReflectorMethod(ForgeHooksClient, "renderFirstPersonHand");
/*  85 */   public static ReflectorMethod ForgeHooksClient_setWorldRendererRB = new ReflectorMethod(ForgeHooksClient, "setWorldRendererRB");
/*  86 */   public static ReflectorMethod ForgeHooksClient_onPreRenderWorld = new ReflectorMethod(ForgeHooksClient, "onPreRenderWorld");
/*  87 */   public static ReflectorMethod ForgeHooksClient_onPostRenderWorld = new ReflectorMethod(ForgeHooksClient, "onPostRenderWorld");
/*     */   
/*  89 */   public static ReflectorClass FMLCommonHandler = new ReflectorClass("cpw.mods.fml.common.FMLCommonHandler");
/*  90 */   public static ReflectorMethod FMLCommonHandler_instance = new ReflectorMethod(FMLCommonHandler, "instance");
/*  91 */   public static ReflectorMethod FMLCommonHandler_handleServerStarting = new ReflectorMethod(FMLCommonHandler, "handleServerStarting");
/*  92 */   public static ReflectorMethod FMLCommonHandler_handleServerAboutToStart = new ReflectorMethod(FMLCommonHandler, "handleServerAboutToStart");
/*  93 */   public static ReflectorMethod FMLCommonHandler_enhanceCrashReport = new ReflectorMethod(FMLCommonHandler, "enhanceCrashReport");
/*     */   
/*  95 */   public static ReflectorClass FMLClientHandler = new ReflectorClass("cpw.mods.fml.client.FMLClientHandler");
/*  96 */   public static ReflectorMethod FMLClientHandler_instance = new ReflectorMethod(FMLClientHandler, "instance");
/*  97 */   public static ReflectorMethod FMLClientHandler_isLoading = new ReflectorMethod(FMLClientHandler, "isLoading");
/*  98 */   public static ReflectorMethod FMLClientHandler_trackBrokenTexture = new ReflectorMethod(FMLClientHandler, "trackBrokenTexture");
/*  99 */   public static ReflectorMethod FMLClientHandler_trackMissingTexture = new ReflectorMethod(FMLClientHandler, "trackMissingTexture");
/*     */   
/* 101 */   public static ReflectorClass ItemRenderType = new ReflectorClass("net.minecraftforge.client.IItemRenderer$ItemRenderType");
/* 102 */   public static ReflectorField ItemRenderType_EQUIPPED = new ReflectorField(ItemRenderType, "EQUIPPED");
/*     */   
/* 104 */   public static ReflectorClass ForgeWorldProvider = new ReflectorClass(aqo.class);
/* 105 */   public static ReflectorMethod ForgeWorldProvider_getSkyRenderer = new ReflectorMethod(ForgeWorldProvider, "getSkyRenderer");
/* 106 */   public static ReflectorMethod ForgeWorldProvider_getCloudRenderer = new ReflectorMethod(ForgeWorldProvider, "getCloudRenderer");
/* 107 */   public static ReflectorMethod ForgeWorldProvider_getWeatherRenderer = new ReflectorMethod(ForgeWorldProvider, "getWeatherRenderer");
/*     */   
/* 109 */   public static ReflectorClass ForgeWorld = new ReflectorClass(ahb.class);
/* 110 */   public static ReflectorMethod ForgeWorld_countEntities = new ReflectorMethod(ForgeWorld, "countEntities", new Class[] { sx.class, boolean.class });
/* 111 */   public static ReflectorMethod ForgeWorld_finishSetup = new ReflectorMethod(ForgeWorld, "finishSetup");
/*     */   
/* 113 */   public static ReflectorClass IRenderHandler = new ReflectorClass("net.minecraftforge.client.IRenderHandler");
/* 114 */   public static ReflectorMethod IRenderHandler_render = new ReflectorMethod(IRenderHandler, "render");
/*     */   
/* 116 */   public static ReflectorClass DimensionManager = new ReflectorClass("net.minecraftforge.common.DimensionManager");
/* 117 */   public static ReflectorMethod DimensionManager_getStaticDimensionIDs = new ReflectorMethod(DimensionManager, "getStaticDimensionIDs");
/*     */   
/* 119 */   public static ReflectorClass WorldEvent_Load = new ReflectorClass("net.minecraftforge.event.world.WorldEvent$Load");
/* 120 */   public static ReflectorConstructor WorldEvent_Load_Constructor = new ReflectorConstructor(WorldEvent_Load, new Class[] { ahb.class });
/*     */   
/* 122 */   public static ReflectorClass DrawScreenEvent_Pre = new ReflectorClass("net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Pre");
/* 123 */   public static ReflectorConstructor DrawScreenEvent_Pre_Constructor = new ReflectorConstructor(DrawScreenEvent_Pre, new Class[] { bdw.class, int.class, int.class, float.class });
/*     */   
/* 125 */   public static ReflectorClass DrawScreenEvent_Post = new ReflectorClass("net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Post");
/* 126 */   public static ReflectorConstructor DrawScreenEvent_Post_Constructor = new ReflectorConstructor(DrawScreenEvent_Post, new Class[] { bdw.class, int.class, int.class, float.class });
/*     */ 
/*     */   
/* 129 */   public static ReflectorClass EntityViewRenderEvent_FogColors = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$FogColors");
/* 130 */   public static ReflectorConstructor EntityViewRenderEvent_FogColors_Constructor = new ReflectorConstructor(EntityViewRenderEvent_FogColors, new Class[] { blt.class, sv.class, aji.class, double.class, float.class, float.class, float.class });
/*     */   
/* 132 */   public static ReflectorField EntityViewRenderEvent_FogColors_red = new ReflectorField(EntityViewRenderEvent_FogColors, "red");
/* 133 */   public static ReflectorField EntityViewRenderEvent_FogColors_green = new ReflectorField(EntityViewRenderEvent_FogColors, "green");
/* 134 */   public static ReflectorField EntityViewRenderEvent_FogColors_blue = new ReflectorField(EntityViewRenderEvent_FogColors, "blue");
/*     */   
/* 136 */   public static ReflectorClass EntityViewRenderEvent_FogDensity = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$FogDensity");
/* 137 */   public static ReflectorConstructor EntityViewRenderEvent_FogDensity_Constructor = new ReflectorConstructor(EntityViewRenderEvent_FogDensity, new Class[] { blt.class, sv.class, aji.class, double.class, float.class });
/*     */   
/* 139 */   public static ReflectorField EntityViewRenderEvent_FogDensity_density = new ReflectorField(EntityViewRenderEvent_FogDensity, "density");
/*     */   
/* 141 */   public static ReflectorClass EntityViewRenderEvent_RenderFogEvent = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$RenderFogEvent");
/* 142 */   public static ReflectorConstructor EntityViewRenderEvent_RenderFogEvent_Constructor = new ReflectorConstructor(EntityViewRenderEvent_RenderFogEvent, new Class[] { blt.class, sv.class, aji.class, double.class, int.class, float.class });
/*     */ 
/*     */   
/* 145 */   public static ReflectorClass RenderItemInFrameEvent = new ReflectorClass("net.minecraftforge.client.event.RenderItemInFrameEvent");
/* 146 */   public static ReflectorConstructor RenderItemInFrameEvent_Constructor = new ReflectorConstructor(RenderItemInFrameEvent, new Class[] { st.class, bnx.class });
/*     */   
/* 148 */   public static ReflectorClass RenderLivingEvent_Pre = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Pre");
/* 149 */   public static ReflectorConstructor RenderLivingEvent_Pre_Constructor = new ReflectorConstructor(RenderLivingEvent_Pre, new Class[] { sv.class, boh.class, double.class, double.class, double.class });
/*     */   
/* 151 */   public static ReflectorClass RenderLivingEvent_Post = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Post");
/* 152 */   public static ReflectorConstructor RenderLivingEvent_Post_Constructor = new ReflectorConstructor(RenderLivingEvent_Post, new Class[] { sv.class, boh.class, double.class, double.class, double.class });
/*     */ 
/*     */   
/* 155 */   public static ReflectorClass RenderLivingEvent_Specials_Pre = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Specials$Pre");
/* 156 */   public static ReflectorConstructor RenderLivingEvent_Specials_Pre_Constructor = new ReflectorConstructor(RenderLivingEvent_Specials_Pre, new Class[] { sv.class, boh.class, double.class, double.class, double.class });
/*     */   
/* 158 */   public static ReflectorClass RenderLivingEvent_Specials_Post = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Specials$Post");
/* 159 */   public static ReflectorConstructor RenderLivingEvent_Specials_Post_Constructor = new ReflectorConstructor(RenderLivingEvent_Specials_Post, new Class[] { sv.class, boh.class, double.class, double.class, double.class });
/*     */ 
/*     */   
/* 162 */   public static ReflectorClass EventBus = new ReflectorClass("cpw.mods.fml.common.eventhandler.EventBus");
/* 163 */   public static ReflectorMethod EventBus_post = new ReflectorMethod(EventBus, "post");
/*     */   
/* 165 */   public static ReflectorClass Event_Result = new ReflectorClass("cpw.mods.fml.common.eventhandler.Event$Result");
/* 166 */   public static ReflectorField Event_Result_DENY = new ReflectorField(Event_Result, "DENY");
/* 167 */   public static ReflectorField Event_Result_ALLOW = new ReflectorField(Event_Result, "ALLOW");
/* 168 */   public static ReflectorField Event_Result_DEFAULT = new ReflectorField(Event_Result, "DEFAULT");
/*     */   
/* 170 */   public static ReflectorClass ForgeEventFactory = new ReflectorClass("net.minecraftforge.event.ForgeEventFactory");
/* 171 */   public static ReflectorMethod ForgeEventFactory_canEntitySpawn = new ReflectorMethod(ForgeEventFactory, "canEntitySpawn");
/* 172 */   public static ReflectorMethod ForgeEventFactory_canEntityDespawn = new ReflectorMethod(ForgeEventFactory, "canEntityDespawn");
/*     */   
/* 174 */   public static ReflectorClass ChunkWatchEvent_UnWatch = new ReflectorClass("net.minecraftforge.event.world.ChunkWatchEvent$UnWatch");
/* 175 */   public static ReflectorConstructor ChunkWatchEvent_UnWatch_Constructor = new ReflectorConstructor(ChunkWatchEvent_UnWatch, new Class[] { agu.class, mw.class });
/*     */   
/* 177 */   public static ReflectorClass ForgeBlock = new ReflectorClass(aji.class);
/* 178 */   public static ReflectorMethod ForgeBlock_getBedDirection = new ReflectorMethod(ForgeBlock, "getBedDirection");
/* 179 */   public static ReflectorMethod ForgeBlock_isBed = new ReflectorMethod(ForgeBlock, "isBed");
/* 180 */   public static ReflectorMethod ForgeBlock_isBedFoot = new ReflectorMethod(ForgeBlock, "isBedFoot");
/* 181 */   public static ReflectorMethod ForgeBlock_canRenderInPass = new ReflectorMethod(ForgeBlock, "canRenderInPass");
/* 182 */   public static ReflectorMethod ForgeBlock_hasTileEntity = new ReflectorMethod(ForgeBlock, "hasTileEntity", new Class[] { int.class });
/* 183 */   public static ReflectorMethod ForgeBlock_canCreatureSpawn = new ReflectorMethod(ForgeBlock, "canCreatureSpawn");
/*     */   
/* 185 */   public static ReflectorClass ForgeEntity = new ReflectorClass(sa.class);
/* 186 */   public static ReflectorField ForgeEntity_captureDrops = new ReflectorField(ForgeEntity, "captureDrops");
/* 187 */   public static ReflectorField ForgeEntity_capturedDrops = new ReflectorField(ForgeEntity, "capturedDrops");
/* 188 */   public static ReflectorMethod ForgeEntity_shouldRenderInPass = new ReflectorMethod(ForgeEntity, "shouldRenderInPass");
/* 189 */   public static ReflectorMethod ForgeEntity_canRiderInteract = new ReflectorMethod(ForgeEntity, "canRiderInteract");
/*     */   
/* 191 */   public static ReflectorClass ForgeTileEntity = new ReflectorClass(aor.class);
/* 192 */   public static ReflectorMethod ForgeTileEntity_shouldRenderInPass = new ReflectorMethod(ForgeTileEntity, "shouldRenderInPass");
/* 193 */   public static ReflectorMethod ForgeTileEntity_getRenderBoundingBox = new ReflectorMethod(ForgeTileEntity, "getRenderBoundingBox");
/*     */   
/* 195 */   public static ReflectorClass ForgeItem = new ReflectorClass(adb.class);
/* 196 */   public static ReflectorMethod ForgeItem_onEntitySwing = new ReflectorMethod(ForgeItem, "onEntitySwing");
/*     */   
/* 198 */   public static ReflectorClass ForgePotionEffect = new ReflectorClass(rw.class);
/* 199 */   public static ReflectorMethod ForgePotionEffect_isCurativeItem = new ReflectorMethod(ForgePotionEffect, "isCurativeItem");
/*     */   
/* 201 */   public static ReflectorClass ForgeItemStack = new ReflectorClass(add.class);
/* 202 */   public static ReflectorMethod ForgeItemStack_hasEffect = new ReflectorMethod(ForgeItemStack, "hasEffect", new Class[] { int.class });
/*     */   
/* 204 */   public static ReflectorClass ForgeItemRecord = new ReflectorClass(adr.class);
/* 205 */   public static ReflectorMethod ForgeItemRecord_getRecordResource = new ReflectorMethod(ForgeItemRecord, "getRecordResource", new Class[] { String.class });
/*     */   
/* 207 */   public static ReflectorClass Launch = new ReflectorClass("net.minecraft.launchwrapper.Launch");
/* 208 */   public static ReflectorField Launch_blackboard = new ReflectorField(Launch, "blackboard");
/*     */   
/* 210 */   public static ReflectorClass SplashScreen = new ReflectorClass("cpw.mods.fml.client.SplashProgress");
/*     */   
/* 212 */   public static ReflectorClass OptiFineClassTransformer = new ReflectorClass("optifine.OptiFineClassTransformer");
/* 213 */   public static ReflectorField OptiFineClassTransformer_instance = new ReflectorField(OptiFineClassTransformer, "instance");
/* 214 */   public static ReflectorMethod OptiFineClassTransformer_getOptiFineResource = new ReflectorMethod(OptiFineClassTransformer, "getOptiFineResource");
/*     */   
/* 216 */   public static ReflectorClass ItemBlock = new ReflectorClass(abh.class);
/* 217 */   public static ReflectorField ItemBlock_block = new ReflectorField(ItemBlock, aji.class, 0);
/*     */   
/* 219 */   public static ReflectorClass ItemRenderer = new ReflectorClass(bly.class);
/* 220 */   public static ReflectorField ItemRenderer_itemToRender = new ReflectorField(ItemRenderer, add.class);
/*     */   
/* 222 */   public static ReflectorClass Loader = new ReflectorClass("net.minecraftforge.fml.common.Loader");
/* 223 */   public static ReflectorMethod Loader_getActiveModList = new ReflectorMethod(Loader, "getActiveModList");
/* 224 */   public static ReflectorMethod Loader_instance = new ReflectorMethod(Loader, "instance");
/*     */   
/* 226 */   public static ReflectorClass ModContainer = new ReflectorClass("net.minecraftforge.fml.common.ModContainer");
/* 227 */   public static ReflectorMethod ModContainer_getModId = new ReflectorMethod(ModContainer, "getModId");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void callVoid(ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 234 */       Method m = refMethod.getTargetMethod();
/* 235 */       if (m == null) {
/*     */         return;
/*     */       }
/* 238 */       m.invoke(null, params);
/*     */ 
/*     */     
/*     */     }
/* 242 */     catch (Throwable e) {
/*     */       
/* 244 */       handleException(e, null, refMethod, params);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ReflectorClass findReflectorClass(String[] strings) {
/* 253 */     ReflectorClass rcFirst = null;
/*     */     
/* 255 */     for (int i = 0; i < strings.length; i++) {
/*     */       
/* 257 */       String className = strings[i];
/* 258 */       ReflectorClass rc = new ReflectorClass(className);
/*     */       
/* 260 */       if (rcFirst == null) {
/* 261 */         rcFirst = rc;
/*     */       }
/* 263 */       if (rc.exists()) {
/* 264 */         return rc;
/*     */       }
/*     */     } 
/* 267 */     return rcFirst;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean callBoolean(ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 276 */       Method method = refMethod.getTargetMethod();
/* 277 */       if (method == null) {
/* 278 */         return false;
/*     */       }
/* 280 */       Boolean retVal = (Boolean)method.invoke(null, params);
/*     */ 
/*     */ 
/*     */       
/* 284 */       return retVal.booleanValue();
/*     */     }
/* 286 */     catch (Throwable e) {
/*     */       
/* 288 */       handleException(e, null, refMethod, params);
/* 289 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int callInt(ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 301 */       Method method = refMethod.getTargetMethod();
/* 302 */       if (method == null) {
/* 303 */         return 0;
/*     */       }
/* 305 */       Integer retVal = (Integer)method.invoke(null, params);
/*     */ 
/*     */ 
/*     */       
/* 309 */       return retVal.intValue();
/*     */     }
/* 311 */     catch (Throwable e) {
/*     */       
/* 313 */       handleException(e, null, refMethod, params);
/* 314 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float callFloat(ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 324 */       Method method = refMethod.getTargetMethod();
/* 325 */       if (method == null) {
/* 326 */         return 0.0F;
/*     */       }
/* 328 */       Float retVal = (Float)method.invoke(null, params);
/*     */ 
/*     */ 
/*     */       
/* 332 */       return retVal.floatValue();
/*     */     }
/* 334 */     catch (Throwable e) {
/*     */       
/* 336 */       handleException(e, null, refMethod, params);
/* 337 */       return 0.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double callDouble(ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 347 */       Method method = refMethod.getTargetMethod();
/* 348 */       if (method == null) {
/* 349 */         return 0.0D;
/*     */       }
/* 351 */       Double retVal = (Double)method.invoke(null, params);
/*     */ 
/*     */ 
/*     */       
/* 355 */       return retVal.doubleValue();
/*     */     }
/* 357 */     catch (Throwable e) {
/*     */       
/* 359 */       handleException(e, null, refMethod, params);
/* 360 */       return 0.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String callString(ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 372 */       Method method = refMethod.getTargetMethod();
/* 373 */       if (method == null) {
/* 374 */         return null;
/*     */       }
/* 376 */       String retVal = (String)method.invoke(null, params);
/*     */ 
/*     */ 
/*     */       
/* 380 */       return retVal;
/*     */     }
/* 382 */     catch (Throwable e) {
/*     */       
/* 384 */       handleException(e, null, refMethod, params);
/* 385 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object call(ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 395 */       Method method = refMethod.getTargetMethod();
/* 396 */       if (method == null) {
/* 397 */         return null;
/*     */       }
/* 399 */       Object retVal = method.invoke(null, params);
/*     */ 
/*     */ 
/*     */       
/* 403 */       return retVal;
/*     */     }
/* 405 */     catch (Throwable e) {
/*     */       
/* 407 */       handleException(e, null, refMethod, params);
/* 408 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void callVoid(Object obj, ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 421 */       if (obj == null)
/*     */         return; 
/* 423 */       Method method = refMethod.getTargetMethod();
/* 424 */       if (method == null) {
/*     */         return;
/*     */       }
/* 427 */       method.invoke(obj, params);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 432 */     catch (Throwable e) {
/*     */       
/* 434 */       handleException(e, obj, refMethod, params);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean callBoolean(Object obj, ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 444 */       Method method = refMethod.getTargetMethod();
/* 445 */       if (method == null) {
/* 446 */         return false;
/*     */       }
/* 448 */       Boolean retVal = (Boolean)method.invoke(obj, params);
/*     */ 
/*     */ 
/*     */       
/* 452 */       return retVal.booleanValue();
/*     */     }
/* 454 */     catch (Throwable e) {
/*     */       
/* 456 */       handleException(e, obj, refMethod, params);
/* 457 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int callInt(Object obj, ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 467 */       Method method = refMethod.getTargetMethod();
/* 468 */       if (method == null) {
/* 469 */         return 0;
/*     */       }
/* 471 */       Integer retVal = (Integer)method.invoke(obj, params);
/*     */ 
/*     */ 
/*     */       
/* 475 */       return retVal.intValue();
/*     */     }
/* 477 */     catch (Throwable e) {
/*     */       
/* 479 */       handleException(e, obj, refMethod, params);
/* 480 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float callFloat(Object obj, ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 490 */       Method method = refMethod.getTargetMethod();
/* 491 */       if (method == null) {
/* 492 */         return 0.0F;
/*     */       }
/* 494 */       Float retVal = (Float)method.invoke(obj, params);
/*     */ 
/*     */ 
/*     */       
/* 498 */       return retVal.floatValue();
/*     */     }
/* 500 */     catch (Throwable e) {
/*     */       
/* 502 */       handleException(e, obj, refMethod, params);
/* 503 */       return 0.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double callDouble(Object obj, ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 513 */       Method method = refMethod.getTargetMethod();
/* 514 */       if (method == null) {
/* 515 */         return 0.0D;
/*     */       }
/* 517 */       Double retVal = (Double)method.invoke(obj, params);
/*     */ 
/*     */ 
/*     */       
/* 521 */       return retVal.doubleValue();
/*     */     }
/* 523 */     catch (Throwable e) {
/*     */       
/* 525 */       handleException(e, obj, refMethod, params);
/* 526 */       return 0.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String callString(Object obj, ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 539 */       Method method = refMethod.getTargetMethod();
/* 540 */       if (method == null) {
/* 541 */         return null;
/*     */       }
/* 543 */       String retVal = (String)method.invoke(obj, params);
/*     */ 
/*     */ 
/*     */       
/* 547 */       return retVal;
/*     */     }
/* 549 */     catch (Throwable e) {
/*     */       
/* 551 */       handleException(e, obj, refMethod, params);
/* 552 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object call(Object obj, ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 563 */       Method method = refMethod.getTargetMethod();
/* 564 */       if (method == null) {
/* 565 */         return null;
/*     */       }
/* 567 */       Object retVal = method.invoke(obj, params);
/*     */ 
/*     */ 
/*     */       
/* 571 */       return retVal;
/*     */     }
/* 573 */     catch (Throwable e) {
/*     */       
/* 575 */       handleException(e, obj, refMethod, params);
/* 576 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object getFieldValue(ReflectorField refField) {
/* 585 */     return getFieldValue(null, refField);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object getFieldValue(Object obj, ReflectorField refField) {
/*     */     try {
/* 595 */       Field field = refField.getTargetField();
/* 596 */       if (field == null) {
/* 597 */         return null;
/*     */       }
/* 599 */       Object value = field.get(obj);
/*     */ 
/*     */ 
/*     */       
/* 603 */       return value;
/*     */     }
/* 605 */     catch (Throwable e) {
/*     */       
/* 607 */       e.printStackTrace();
/* 608 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getFieldValueFloat(Object obj, ReflectorField refField, float def) {
/* 617 */     Object val = getFieldValue(obj, refField);
/*     */     
/* 619 */     if (!(val instanceof Float)) {
/* 620 */       return def;
/*     */     }
/* 622 */     Float valFloat = (Float)val;
/* 623 */     return valFloat.floatValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setFieldValue(ReflectorField refField, Object value) {
/* 631 */     setFieldValue(null, refField, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean setFieldValue(Object obj, ReflectorField refField, Object value) {
/*     */     try {
/* 641 */       Field field = refField.getTargetField();
/* 642 */       if (field == null) {
/* 643 */         return false;
/*     */       }
/* 645 */       field.set(obj, value);
/*     */ 
/*     */ 
/*     */       
/* 649 */       return true;
/*     */     }
/* 651 */     catch (Throwable e) {
/*     */       
/* 653 */       e.printStackTrace();
/* 654 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean postForgeBusEvent(ReflectorConstructor constr, Object... params) {
/* 662 */     Object event = newInstance(constr, params);
/* 663 */     if (event == null) {
/* 664 */       return false;
/*     */     }
/* 666 */     return postForgeBusEvent(event);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean postForgeBusEvent(Object event) {
/* 673 */     if (event == null) {
/* 674 */       return false;
/*     */     }
/* 676 */     Object eventBus = getFieldValue(MinecraftForge_EVENT_BUS);
/* 677 */     if (eventBus == null) {
/* 678 */       return false;
/*     */     }
/* 680 */     Object ret = call(eventBus, EventBus_post, new Object[] { event });
/*     */     
/* 682 */     if (!(ret instanceof Boolean)) {
/* 683 */       return false;
/*     */     }
/* 685 */     Boolean retBool = (Boolean)ret;
/* 686 */     return retBool.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object newInstance(ReflectorConstructor constr, Object... params) {
/* 697 */     Constructor c = constr.getTargetConstructor();
/*     */     
/* 699 */     if (c == null) {
/* 700 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 704 */       Object obj = c.newInstance(params);
/* 705 */       return obj;
/*     */     }
/* 707 */     catch (Throwable e) {
/*     */       
/* 709 */       handleException(e, constr, params);
/* 710 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Method getMethod(Class cls, String methodName, Class[] paramTypes) {
/* 719 */     Method[] ms = cls.getDeclaredMethods();
/* 720 */     for (int i = 0; i < ms.length; i++) {
/*     */       
/* 722 */       Method m = ms[i];
/*     */       
/* 724 */       if (m.getName().equals(methodName)) {
/*     */ 
/*     */         
/* 727 */         Class[] types = m.getParameterTypes();
/* 728 */         if (matchesTypes(paramTypes, types))
/*     */         {
/*     */           
/* 731 */           return m; } 
/*     */       } 
/*     */     } 
/* 734 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Method[] getMethods(Class cls, String methodName) {
/* 743 */     List<Method> listMethods = new ArrayList();
/* 744 */     Method[] ms = cls.getDeclaredMethods();
/* 745 */     for (int i = 0; i < ms.length; i++) {
/*     */       
/* 747 */       Method m = ms[i];
/* 748 */       if (m.getName().equals(methodName))
/*     */       {
/*     */         
/* 751 */         listMethods.add(m);
/*     */       }
/*     */     } 
/* 754 */     Method[] methods = listMethods.<Method>toArray(new Method[listMethods.size()]);
/*     */     
/* 756 */     return methods;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean matchesTypes(Class[] pTypes, Class[] cTypes) {
/* 767 */     if (pTypes.length != cTypes.length) {
/* 768 */       return false;
/*     */     }
/* 770 */     for (int i = 0; i < cTypes.length; i++) {
/*     */       
/* 772 */       Class pType = pTypes[i];
/* 773 */       Class cType = cTypes[i];
/* 774 */       if (pType != cType) {
/* 775 */         return false;
/*     */       }
/*     */     } 
/* 778 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void dbgCall(boolean isStatic, String callType, ReflectorMethod refMethod, Object[] params, Object retVal) {
/* 788 */     String className = refMethod.getTargetMethod().getDeclaringClass().getName();
/* 789 */     String methodName = refMethod.getTargetMethod().getName();
/* 790 */     String staticStr = "";
/* 791 */     if (isStatic)
/* 792 */       staticStr = " static"; 
/* 793 */     Config.dbg(callType + staticStr + " " + className + "." + methodName + "(" + Config.arrayToString(params) + ") => " + retVal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void dbgCallVoid(boolean isStatic, String callType, ReflectorMethod refMethod, Object[] params) {
/* 803 */     String className = refMethod.getTargetMethod().getDeclaringClass().getName();
/* 804 */     String methodName = refMethod.getTargetMethod().getName();
/* 805 */     String staticStr = "";
/* 806 */     if (isStatic) {
/* 807 */       staticStr = " static";
/*     */     }
/* 809 */     Config.dbg(callType + staticStr + " " + className + "." + methodName + "(" + Config.arrayToString(params) + ")");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void dbgFieldValue(boolean isStatic, String accessType, ReflectorField refField, Object val) {
/* 819 */     String className = refField.getTargetField().getDeclaringClass().getName();
/* 820 */     String fieldName = refField.getTargetField().getName();
/* 821 */     String staticStr = "";
/* 822 */     if (isStatic)
/* 823 */       staticStr = " static"; 
/* 824 */     Config.dbg(accessType + staticStr + " " + className + "." + fieldName + " => " + val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void handleException(Throwable e, Object obj, ReflectorMethod refMethod, Object[] params) {
/* 835 */     if (e instanceof java.lang.reflect.InvocationTargetException) {
/*     */ 
/*     */       
/* 838 */       e.printStackTrace();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 843 */     if (e instanceof IllegalArgumentException) {
/*     */ 
/*     */       
/* 846 */       Config.warn("*** IllegalArgumentException ***");
/* 847 */       Config.warn("Method: " + refMethod.getTargetMethod());
/*     */       
/* 849 */       Config.warn("Object: " + obj);
/*     */       
/* 851 */       Config.warn("Parameter classes: " + Config.arrayToString(getClasses(params)));
/* 852 */       Config.warn("Parameters: " + Config.arrayToString(params));
/*     */     } 
/*     */     
/* 855 */     Config.warn("*** Exception outside of method ***");
/* 856 */     Config.warn("Method deactivated: " + refMethod.getTargetMethod());
/* 857 */     refMethod.deactivate();
/*     */     
/* 859 */     e.printStackTrace();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void handleException(Throwable e, ReflectorConstructor refConstr, Object[] params) {
/* 870 */     if (e instanceof java.lang.reflect.InvocationTargetException) {
/*     */ 
/*     */       
/* 873 */       e.printStackTrace();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 878 */     if (e instanceof IllegalArgumentException) {
/*     */ 
/*     */       
/* 881 */       Config.warn("*** IllegalArgumentException ***");
/* 882 */       Config.warn("Constructor: " + refConstr.getTargetConstructor());
/*     */       
/* 884 */       Config.warn("Parameter classes: " + Config.arrayToString(getClasses(params)));
/* 885 */       Config.warn("Parameters: " + Config.arrayToString(params));
/*     */     } 
/*     */     
/* 888 */     Config.warn("*** Exception outside of constructor ***");
/* 889 */     Config.warn("Constructor deactivated: " + refConstr.getTargetConstructor());
/* 890 */     refConstr.deactivate();
/*     */     
/* 892 */     e.printStackTrace();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object[] getClasses(Object[] objs) {
/* 900 */     if (objs == null) {
/* 901 */       return (Object[])new Class[0];
/*     */     }
/* 903 */     Class[] classes = new Class[objs.length];
/* 904 */     for (int i = 0; i < classes.length; i++) {
/*     */       
/* 906 */       Object obj = objs[i];
/* 907 */       if (obj != null)
/*     */       {
/*     */         
/* 910 */         classes[i] = obj.getClass(); } 
/*     */     } 
/* 912 */     return (Object[])classes;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field getField(Class cls, Class<?> fieldType) {
/*     */     try {
/* 919 */       Field[] fileds = cls.getDeclaredFields();
/* 920 */       for (int i = 0; i < fileds.length; ) {
/*     */         
/* 922 */         Field field = fileds[i];
/* 923 */         if (field.getType() != fieldType) {
/*     */           i++; continue;
/*     */         } 
/* 926 */         field.setAccessible(true);
/*     */         
/* 928 */         return field;
/*     */       } 
/*     */       
/* 931 */       return null;
/*     */     }
/* 933 */     catch (Exception e) {
/*     */ 
/*     */       
/* 936 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Field[] getFields(Class cls, Class<?> fieldType) {
/* 942 */     List<Field> list = new ArrayList();
/*     */     
/*     */     try {
/* 945 */       Field[] fileds = cls.getDeclaredFields();
/* 946 */       for (int i = 0; i < fileds.length; i++) {
/*     */         
/* 948 */         Field field = fileds[i];
/* 949 */         if (field.getType() == fieldType) {
/*     */ 
/*     */           
/* 952 */           field.setAccessible(true);
/*     */           
/* 954 */           list.add(field);
/*     */         } 
/*     */       } 
/* 957 */       Field[] fields = list.<Field>toArray(new Field[list.size()]);
/*     */       
/* 959 */       return fields;
/*     */     }
/* 961 */     catch (Exception e) {
/*     */ 
/*     */       
/* 964 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ReflectorField getReflectorField(Class cls, Class fieldType) {
/* 976 */     Field field = getField(cls, fieldType);
/* 977 */     if (field == null) {
/* 978 */       return null;
/*     */     }
/* 980 */     ReflectorClass rc = new ReflectorClass(cls);
/*     */     
/* 982 */     return new ReflectorField(rc, field.getName());
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\Reflector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */