/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerItemRenderer
/*    */ {
/* 11 */   private int attachTo = 0;
/* 12 */   private float scaleFactor = 0.0F;
/* 13 */   private bix modelRenderer = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PlayerItemRenderer(int attachTo, float scaleFactor, bix modelRenderer) {
/* 21 */     this.attachTo = attachTo;
/* 22 */     this.scaleFactor = scaleFactor;
/* 23 */     this.modelRenderer = modelRenderer;
/*    */   }
/*    */ 
/*    */   
/*    */   public bix getModelRenderer() {
/* 28 */     return this.modelRenderer;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(bhm modelBiped, float scale) {
/* 34 */     bix attachModel = PlayerItemModel.getAttachModel(modelBiped, this.attachTo);
/* 35 */     if (attachModel != null) {
/* 36 */       attachModel.c(scale);
/*    */     }
/* 38 */     this.modelRenderer.a(scale * this.scaleFactor);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\PlayerItemRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */