/*    */ package shadersmod.client;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScreenShaderOptions
/*    */ {
/*    */   private String name;
/*    */   private ShaderOption[] shaderOptions;
/*    */   private int columns;
/*    */   
/*    */   public ScreenShaderOptions(String name, ShaderOption[] shaderOptions, int columns) {
/* 24 */     this.name = name;
/* 25 */     this.shaderOptions = shaderOptions;
/* 26 */     this.columns = columns;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 33 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ShaderOption[] getShaderOptions() {
/* 40 */     return this.shaderOptions;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getColumns() {
/* 47 */     return this.columns;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\ScreenShaderOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */