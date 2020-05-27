/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonParser;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerConfigurationReceiver
/*    */   implements IFileDownloadListener
/*    */ {
/* 15 */   private String player = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PlayerConfigurationReceiver(String player) {
/* 22 */     this.player = player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void fileDownloadFinished(String url, byte[] bytes, Throwable exception) {
/* 29 */     if (bytes == null) {
/*    */       return;
/*    */     }
/*    */     
/*    */     try {
/* 34 */       String str = new String(bytes, "ASCII");
/* 35 */       JsonParser jp = new JsonParser();
/* 36 */       JsonElement je = jp.parse(str);
/* 37 */       PlayerConfigurationParser pcp = new PlayerConfigurationParser(this.player);
/* 38 */       PlayerConfiguration pc = pcp.parsePlayerConfiguration(je);
/*    */ 
/*    */       
/* 41 */       if (pc != null)
/*    */       {
/* 43 */         pc.setInitialized(true);
/* 44 */         PlayerConfigurations.setPlayerConfiguration(this.player, pc);
/*    */       }
/*    */     
/* 47 */     } catch (Exception e) {
/*    */       
/* 49 */       Config.dbg("Error parsing configuration: " + url + ", " + e.getClass().getName() + ": " + e.getMessage());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\PlayerConfigurationReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */