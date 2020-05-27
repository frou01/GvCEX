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
/*    */ class ThreadUpdateListener
/*    */   implements IWrUpdateListener
/*    */ {
/* 69 */   private WrUpdateThread.ThreadUpdateControl tuc = new WrUpdateThread.ThreadUpdateControl(WrUpdateThread.this, null);
/*    */   
/*    */   public void updating(IWrUpdateControl uc) {
/* 72 */     this.tuc.setUpdateControl(uc);
/* 73 */     WrUpdateThread.access$300(WrUpdateThread.this, this.tuc);
/*    */   }
/*    */   
/*    */   private ThreadUpdateListener() {}
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\WrUpdateThread$ThreadUpdateListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */