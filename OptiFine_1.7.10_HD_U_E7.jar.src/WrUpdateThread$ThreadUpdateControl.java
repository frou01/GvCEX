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
/*    */ class ThreadUpdateControl
/*    */   implements IWrUpdateControl
/*    */ {
/* 39 */   private IWrUpdateControl updateControl = null;
/*    */   private boolean paused = false;
/*    */   
/*    */   public void pause() {
/* 43 */     if (!this.paused) {
/*    */       
/* 45 */       this.paused = true;
/* 46 */       this.updateControl.pause();
/*    */       
/* 48 */       bmh.a = WrUpdateThread.access$000(WrUpdateThread.this);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void resume() {
/* 53 */     if (this.paused) {
/*    */       
/* 55 */       this.paused = false;
/*    */       
/* 57 */       bmh.a = WrUpdateThread.access$100(WrUpdateThread.this);
/* 58 */       this.updateControl.resume();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setUpdateControl(IWrUpdateControl updateControl) {
/* 63 */     this.updateControl = updateControl;
/*    */   }
/*    */   
/*    */   private ThreadUpdateControl() {}
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\WrUpdateThread$ThreadUpdateControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */