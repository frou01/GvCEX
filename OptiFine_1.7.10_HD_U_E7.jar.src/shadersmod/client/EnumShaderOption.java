/*    */ package shadersmod.client;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum EnumShaderOption
/*    */ {
/* 12 */   ANTIALIASING("of.options.shaders.ANTIALIASING", "antialiasingLevel", "0"),
/* 13 */   NORMAL_MAP("of.options.shaders.NORMAL_MAP", "normalMapEnabled", "true"),
/* 14 */   SPECULAR_MAP("of.options.shaders.SPECULAR_MAP", "specularMapEnabled", "true"),
/* 15 */   RENDER_RES_MUL("of.options.shaders.RENDER_RES_MUL", "renderResMul", "1.0"),
/* 16 */   SHADOW_RES_MUL("of.options.shaders.SHADOW_RES_MUL", "shadowResMul", "1.0"),
/* 17 */   HAND_DEPTH_MUL("of.options.shaders.HAND_DEPTH_MUL", "handDepthMul", "0.125"),
/* 18 */   CLOUD_SHADOW("of.options.shaders.CLOUD_SHADOW", "cloudShadow", "true"),
/* 19 */   OLD_HAND_LIGHT("of.options.shaders.OLD_HAND_LIGHT", "oldHandLight", "default"),
/* 20 */   OLD_LIGHTING("of.options.shaders.OLD_LIGHTING", "oldLighting", "default"),
/* 21 */   SHADER_PACK("of.options.shaders.SHADER_PACK", "shaderPack", ""),
/*    */   
/* 23 */   TWEAK_BLOCK_DAMAGE("of.options.shaders.TWEAK_BLOCK_DAMAGE", "tweakBlockDamage", "false"),
/* 24 */   SHADOW_CLIP_FRUSTRUM("of.options.shaders.SHADOW_CLIP_FRUSTRUM", "shadowClipFrustrum", "true"),
/* 25 */   TEX_MIN_FIL_B("of.options.shaders.TEX_MIN_FIL_B", "TexMinFilB", "0"),
/* 26 */   TEX_MIN_FIL_N("of.options.shaders.TEX_MIN_FIL_N", "TexMinFilN", "0"),
/* 27 */   TEX_MIN_FIL_S("of.options.shaders.TEX_MIN_FIL_S", "TexMinFilS", "0"),
/* 28 */   TEX_MAG_FIL_B("of.options.shaders.TEX_MAG_FIL_B", "TexMagFilB", "0"),
/* 29 */   TEX_MAG_FIL_N("of.options.shaders.TEX_MAG_FIL_N", "TexMagFilN", "0"),
/* 30 */   TEX_MAG_FIL_S("of.options.shaders.TEX_MAG_FIL_S", "TexMagFilS", "0");
/*    */ 
/*    */   
/* 33 */   private String resourceKey = null;
/* 34 */   private String propertyKey = null;
/* 35 */   private String valueDefault = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   EnumShaderOption(String resourceKey, String propertyKey, String valueDefault) {
/* 41 */     this.resourceKey = resourceKey;
/* 42 */     this.propertyKey = propertyKey;
/* 43 */     this.valueDefault = valueDefault;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getResourceKey() {
/* 50 */     return this.resourceKey;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPropertyKey() {
/* 57 */     return this.propertyKey;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getValueDefault() {
/* 64 */     return this.valueDefault;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\EnumShaderOption.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */