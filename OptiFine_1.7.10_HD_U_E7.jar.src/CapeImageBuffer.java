/*    */ import java.awt.image.BufferedImage;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CapeImageBuffer
/*    */   extends bmc
/*    */ {
/*    */   private blg player;
/*    */   private bqx resourceLocation;
/*    */   
/*    */   public CapeImageBuffer(blg player, bqx resourceLocation) {
/* 27 */     this.player = player;
/* 28 */     this.resourceLocation = resourceLocation;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BufferedImage a(BufferedImage var1) {
/* 35 */     return CapeUtils.parseCape(var1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void a() {
/* 42 */     if (this.player != null) {
/* 43 */       this.player.setLocationOfCape(this.resourceLocation);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void cleanup() {
/* 52 */     this.player = null;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\CapeImageBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */