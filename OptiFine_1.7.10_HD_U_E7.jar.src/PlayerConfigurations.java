/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerConfigurations
/*     */ {
/*  19 */   private static Map mapConfigurations = null;
/*     */   
/*  21 */   private static boolean reloadPlayerItems = Boolean.getBoolean("player.models.reload");
/*  22 */   private static long timeReloadPlayerItemsMs = System.currentTimeMillis();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderPlayerItems(bhm modelBiped, blg player, float scale, float partialTicks) {
/*  31 */     PlayerConfiguration cfg = getPlayerConfiguration(player);
/*     */     
/*  33 */     if (cfg != null)
/*     */     {
/*  35 */       cfg.renderPlayerItems(modelBiped, player, scale, partialTicks);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized PlayerConfiguration getPlayerConfiguration(blg player) {
/*  46 */     if (reloadPlayerItems)
/*     */     {
/*  48 */       if (System.currentTimeMillis() > timeReloadPlayerItemsMs + 5000L) {
/*     */         
/*  50 */         bjk bjk = (bao.B()).h;
/*  51 */         if (bjk != null) {
/*     */           
/*  53 */           setPlayerConfiguration(bjk.getNameClear(), null);
/*  54 */           timeReloadPlayerItemsMs = System.currentTimeMillis();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*  59 */     String name = player.getNameClear();
/*     */     
/*  61 */     if (name == null) {
/*  62 */       return null;
/*     */     }
/*  64 */     PlayerConfiguration pc = (PlayerConfiguration)getMapConfigurations().get(name);
/*     */     
/*  66 */     if (pc == null) {
/*     */ 
/*     */       
/*  69 */       pc = new PlayerConfiguration();
/*  70 */       getMapConfigurations().put(name, pc);
/*     */       
/*  72 */       PlayerConfigurationReceiver pcl = new PlayerConfigurationReceiver(name);
/*  73 */       String url = HttpUtils.getPlayerItemsUrl() + "/users/" + name + ".cfg";
/*  74 */       FileDownloadThread fdt = new FileDownloadThread(url, pcl);
/*  75 */       fdt.start();
/*     */     } 
/*     */     
/*  78 */     return pc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void setPlayerConfiguration(String player, PlayerConfiguration pc) {
/*  87 */     getMapConfigurations().put(player, pc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map getMapConfigurations() {
/*  95 */     if (mapConfigurations == null)
/*     */     {
/*  97 */       mapConfigurations = new HashMap<Object, Object>();
/*     */     }
/*     */     
/* 100 */     return mapConfigurations;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\PlayerConfigurations.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */