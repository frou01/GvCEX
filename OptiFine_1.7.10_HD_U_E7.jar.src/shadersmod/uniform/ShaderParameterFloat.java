/*     */ package shadersmod.uniform;
/*     */ 
/*     */ import ahu;
/*     */ import azw;
/*     */ import net.optifine.entity.model.anim.ExpressionType;
/*     */ import net.optifine.entity.model.anim.IExpressionFloat;
/*     */ import shadersmod.client.Shaders;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum ShaderParameterFloat
/*     */   implements IExpressionFloat
/*     */ {
/*  18 */   BIOME("biome"),
/*     */   
/*  20 */   HELD_ITEM_ID("heldItemId"),
/*  21 */   HELD_BLOCK_LIGHT_VALUE("heldBlockLightValue"),
/*  22 */   HELD_ITEM_ID2("heldItemId2"),
/*  23 */   HELD_BLOCK_LIGHT_VALUE2("heldBlockLightValue2"),
/*  24 */   FOG_MODE("fogMode"),
/*  25 */   WORLD_TIME("worldTime"),
/*  26 */   WORLD_DAY("worldDay"),
/*  27 */   MOON_PHASE("moonPhase"),
/*  28 */   FRAME_COUNTER("frameCounter"),
/*  29 */   FRAME_TIME("frameTime"),
/*  30 */   FRAME_TIME_COUNTER("frameTimeCounter"),
/*  31 */   SUN_ANGLE("sunAngle"),
/*  32 */   SHADOW_ANGLE("shadowAngle"),
/*  33 */   RAIN_STRENGTH("rainStrength"),
/*  34 */   ASPECT_RATIO("aspectRatio"),
/*  35 */   VIEW_WIDTH("viewWidth"),
/*  36 */   VIEW_HEIGHT("viewHeight"),
/*  37 */   NEAR("near"),
/*  38 */   FAR("far"),
/*  39 */   WETNESS("wetness"),
/*  40 */   EYE_ALTITUDE("eyeAltitude"),
/*  41 */   EYE_BRIGHTNESS_X("eyeBrightness.x"),
/*  42 */   EYE_BRIGHTNESS_Y("eyeBrightness.y"),
/*  43 */   TERRAIN_TEXTURE_SIZE_X("terrainTextureSize.x"),
/*  44 */   TERRAIN_TEXTURE_SIZE_Y("terrainTextureSize.y"),
/*  45 */   TERRRAIN_ICON_SIZE("terrainIconSize"),
/*  46 */   IS_EYE_IN_WATER("isEyeInWater"),
/*  47 */   NIGHT_VISION("nightVision"),
/*  48 */   BLINDNESS("blindness"),
/*  49 */   SCREEN_BRIGHTNESS("screenBrightness"),
/*  50 */   HIDE_GUI("hideGUI"),
/*  51 */   CENTER_DEPT_SMOOTH("centerDepthSmooth"),
/*  52 */   ATLAS_SIZE_X("atlasSize.x"),
/*  53 */   ATLAS_SIZE_Y("atlasSize.y"),
/*  54 */   CAMERA_POSITION_X("cameraPosition.x"),
/*  55 */   CAMERA_POSITION_Y("cameraPosition.y"),
/*  56 */   CAMERA_POSITION_Z("cameraPosition.z"),
/*  57 */   PREVIOUS_CAMERA_POSITION_X("previousCameraPosition.x"),
/*  58 */   PREVIOUS_CAMERA_POSITION_Y("previousCameraPosition.y"),
/*  59 */   PREVIOUS_CAMERA_POSITION_Z("previousCameraPosition.z"),
/*  60 */   SUN_POSITION_X("sunPosition.x"),
/*  61 */   SUN_POSITION_Y("sunPosition.y"),
/*  62 */   SUN_POSITION_Z("sunPosition.z"),
/*  63 */   MOON_POSITION_X("moonPosition.x"),
/*  64 */   MOON_POSITION_Y("moonPosition.y"),
/*  65 */   MOON_POSITION_Z("moonPosition.z"),
/*  66 */   SHADOW_LIGHT_POSITION_X("shadowLightPosition.x"),
/*  67 */   SHADOW_LIGHT_POSITION_Y("shadowLightPosition.y"),
/*  68 */   SHADOW_LIGHT_POSITION_Z("shadowLightPosition.z"),
/*  69 */   UP_POSITION_X("upPosition.x"),
/*  70 */   UP_POSITION_Y("upPosition.y"),
/*  71 */   UP_POSITION_Z("upPosition.z"),
/*  72 */   FOG_COLOR_R("fogColor.r"),
/*  73 */   FOG_COLOR_G("fogColor.g"),
/*  74 */   FOG_COLOR_B("fogColor.b"),
/*  75 */   SKY_COLOR_R("skyColor.r"),
/*  76 */   SKY_COLOR_G("skyColor.g"),
/*  77 */   SKY_COLOR_B("skyColor.b");
/*     */ 
/*     */ 
/*     */   
/*     */   private String name;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ShaderParameterFloat(String name) {
/*  87 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  94 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExpressionType getExpressionType() {
/* 101 */     return ExpressionType.FLOAT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float eval() {
/*     */     azw posCamera;
/*     */     ahu biome;
/* 109 */     switch (this) {
/*     */       
/*     */       case BIOME:
/* 112 */         posCamera = Shaders.getCameraPosition();
/* 113 */         biome = Shaders.getCurrentWorld().a((int)posCamera.a, (int)posCamera.c);
/* 114 */         return biome.ay;
/*     */     } 
/*     */     
/* 117 */     Number valLegacy = LegacyUniforms.getNumber(this.name);
/* 118 */     if (valLegacy != null) {
/* 119 */       return valLegacy.floatValue();
/*     */     }
/* 121 */     return 0.0F;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmo\\uniform\ShaderParameterFloat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */