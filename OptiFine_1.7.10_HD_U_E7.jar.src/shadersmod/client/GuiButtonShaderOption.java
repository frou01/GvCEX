/*    */ package shadersmod.client;
/*    */ 
/*    */ import bcb;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiButtonShaderOption
/*    */   extends bcb
/*    */ {
/* 13 */   private ShaderOption shaderOption = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public GuiButtonShaderOption(int buttonId, int x, int y, int widthIn, int heightIn, ShaderOption shaderOption, String text) {
/* 18 */     super(buttonId, x, y, widthIn, heightIn, text);
/* 19 */     this.shaderOption = shaderOption;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ShaderOption getShaderOption() {
/* 26 */     return this.shaderOption;
/*    */   }
/*    */   
/*    */   public void valueChanged() {}
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\GuiButtonShaderOption.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */