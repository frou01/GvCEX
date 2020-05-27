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
/*    */ public class PlayerConfiguration
/*    */ {
/* 13 */   private PlayerItemModel[] playerItemModels = new PlayerItemModel[0];
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean initialized = false;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderPlayerItems(bhm modelBiped, blg player, float scale, float partialTicks) {
/* 24 */     if (!this.initialized) {
/*    */       return;
/*    */     }
/* 27 */     for (int i = 0; i < this.playerItemModels.length; i++) {
/*    */       
/* 29 */       PlayerItemModel model = this.playerItemModels[i];
/* 30 */       model.render(modelBiped, player, scale, partialTicks);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isInitialized() {
/* 38 */     return this.initialized;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setInitialized(boolean initialized) {
/* 46 */     this.initialized = initialized;
/*    */   }
/*    */ 
/*    */   
/*    */   public PlayerItemModel[] getPlayerItemModels() {
/* 51 */     return this.playerItemModels;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addPlayerItemModel(PlayerItemModel playerItemModel) {
/* 59 */     this.playerItemModels = (PlayerItemModel[])Config.addObjectToArray((Object[])this.playerItemModels, playerItemModel);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\PlayerConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */