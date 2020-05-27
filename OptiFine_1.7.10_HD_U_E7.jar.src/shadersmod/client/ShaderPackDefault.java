/*    */ package shadersmod.client;
/*    */ 
/*    */ import java.io.InputStream;
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
/*    */ public class ShaderPackDefault
/*    */   implements IShaderPack
/*    */ {
/*    */   public void close() {}
/*    */   
/*    */   public InputStream getResourceAsStream(String resName) {
/* 20 */     return ShaderPackDefault.class.getResourceAsStream(resName);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 29 */     return Shaders.packNameDefault;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasDirectory(String name) {
/* 38 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\ShaderPackDefault.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */