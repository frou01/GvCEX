/*    */ package shadersmod.client;
/*    */ 
/*    */ import bpp;
/*    */ import bqy;
/*    */ 
/*    */ public class DefaultTexture
/*    */   extends bpp
/*    */ {
/*    */   public DefaultTexture() {
/* 10 */     a(null);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(bqy resourcemanager) {
/* 16 */     int[] aint = ShadersTex.createAIntImage(1, -1);
/* 17 */     ShadersTex.setupTexture(getMultiTexID(), aint, 1, 1, false, false);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\DefaultTexture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */