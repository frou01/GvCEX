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
/*    */ public class ShaderPackNone
/*    */   implements IShaderPack
/*    */ {
/*    */   public void close() {}
/*    */   
/*    */   public InputStream getResourceAsStream(String resName) {
/* 20 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasDirectory(String name) {
/* 29 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 38 */     return Shaders.packNameNone;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\ShaderPackNone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */