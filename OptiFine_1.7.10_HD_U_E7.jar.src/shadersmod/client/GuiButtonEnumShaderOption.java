/*    */ package shadersmod.client;
/*    */ 
/*    */ import bcb;
/*    */ import brp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiButtonEnumShaderOption
/*    */   extends bcb
/*    */ {
/* 14 */   private EnumShaderOption enumShaderOption = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public GuiButtonEnumShaderOption(EnumShaderOption enumShaderOption, int x, int y, int widthIn, int heightIn) {
/* 19 */     super(enumShaderOption.ordinal(), x, y, widthIn, heightIn, getButtonText(enumShaderOption));
/* 20 */     this.enumShaderOption = enumShaderOption;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumShaderOption getEnumShaderOption() {
/* 27 */     return this.enumShaderOption;
/*    */   }
/*    */   
/*    */   private static String getButtonText(EnumShaderOption eso) {
/* 31 */     String nameText = brp.a(eso.getResourceKey(), new Object[0]) + ": ";
/*    */     
/* 33 */     switch (eso) {
/*    */       
/*    */       case ANTIALIASING:
/* 36 */         return nameText + GuiShaders.toStringAa(Shaders.configAntialiasingLevel);
/*    */       case NORMAL_MAP:
/* 38 */         return nameText + GuiShaders.toStringOnOff(Shaders.configNormalMap);
/*    */       case SPECULAR_MAP:
/* 40 */         return nameText + GuiShaders.toStringOnOff(Shaders.configSpecularMap);
/*    */       case RENDER_RES_MUL:
/* 42 */         return nameText + GuiShaders.toStringQuality(Shaders.configRenderResMul);
/*    */       case SHADOW_RES_MUL:
/* 44 */         return nameText + GuiShaders.toStringQuality(Shaders.configShadowResMul);
/*    */       case HAND_DEPTH_MUL:
/* 46 */         return nameText + GuiShaders.toStringHandDepth(Shaders.configHandDepthMul);
/*    */       case CLOUD_SHADOW:
/* 48 */         return nameText + GuiShaders.toStringOnOff(Shaders.configCloudShadow);
/*    */       case OLD_HAND_LIGHT:
/* 50 */         return nameText + Shaders.configOldHandLight.getUserValue();
/*    */       case OLD_LIGHTING:
/* 52 */         return nameText + Shaders.configOldLighting.getUserValue();
/*    */       
/*    */       case SHADOW_CLIP_FRUSTRUM:
/* 55 */         return nameText + GuiShaders.toStringOnOff(Shaders.configShadowClipFrustrum);
/*    */       case TWEAK_BLOCK_DAMAGE:
/* 57 */         return nameText + GuiShaders.toStringOnOff(Shaders.configTweakBlockDamage);
/*    */     } 
/*    */     
/* 60 */     return nameText + Shaders.getEnumShaderOption(eso);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateButtonText() {
/* 67 */     this.j = getButtonText(this.enumShaderOption);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\GuiButtonEnumShaderOption.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */