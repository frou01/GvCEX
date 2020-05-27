/*    */ package shadersmod.client;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShaderOptionScreen
/*    */   extends ShaderOption
/*    */ {
/*    */   public ShaderOptionScreen(String name) {
/* 12 */     super(name, null, null, new String[0], null, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getNameText() {
/* 17 */     return Shaders.translate("screen." + getName(), getName());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDescriptionText() {
/* 25 */     return Shaders.translate("screen." + getName() + ".comment", null);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\ShaderOptionScreen.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */