/*    */ import java.io.InputStream;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.URL;
/*    */ import net.minecraft.client.ClientBrandRetriever;
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
/*    */ public class VersionCheckThread
/*    */   extends Thread
/*    */ {
/*    */   public void run() {
/* 20 */     HttpURLConnection conn = null;
/*    */     
/*    */     try {
/* 23 */       Config.dbg("Checking for new version");
/* 24 */       URL url = new URL("http://optifine.net/version/1.7.10/HD_U.txt");
/*    */ 
/*    */ 
/*    */       
/* 28 */       conn = (HttpURLConnection)url.openConnection();
/*    */       
/* 30 */       if ((Config.getGameSettings()).r) {
/*    */         
/* 32 */         conn.setRequestProperty("OF-MC-Version", "1.7.10");
/* 33 */         conn.setRequestProperty("OF-MC-Brand", "" + ClientBrandRetriever.getClientModName());
/* 34 */         conn.setRequestProperty("OF-Edition", "HD_U");
/* 35 */         conn.setRequestProperty("OF-Release", "E7");
/* 36 */         conn.setRequestProperty("OF-Java-Version", "" + System.getProperty("java.version"));
/* 37 */         conn.setRequestProperty("OF-CpuCount", "" + Config.getAvailableProcessors());
/* 38 */         conn.setRequestProperty("OF-OpenGL-Version", "" + Config.openGlVersion);
/* 39 */         conn.setRequestProperty("OF-OpenGL-Vendor", "" + Config.openGlVendor);
/*    */       } 
/*    */       
/* 42 */       conn.setDoInput(true);
/* 43 */       conn.setDoOutput(false);
/* 44 */       conn.connect();
/*    */       
/*    */       try {
/* 47 */         InputStream in = conn.getInputStream();
/* 48 */         String verStr = Config.readInputStream(in);
/* 49 */         in.close();
/*    */         
/* 51 */         String[] verLines = Config.tokenize(verStr, "\n\r");
/* 52 */         if (verLines.length < 1) {
/*    */           return;
/*    */         }
/* 55 */         String newVer = verLines[0].trim();
/* 56 */         Config.dbg("Version found: " + newVer);
/*    */         
/* 58 */         if (Config.compareRelease(newVer, "E7") <= 0) {
/*    */           return;
/*    */         }
/* 61 */         Config.setNewRelease(newVer);
/*    */       }
/*    */       finally {
/*    */         
/* 65 */         if (conn != null) {
/* 66 */           conn.disconnect();
/*    */         }
/*    */       } 
/* 69 */     } catch (Exception e) {
/*    */ 
/*    */       
/* 72 */       Config.dbg(e.getClass().getName() + ": " + e.getMessage());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\VersionCheckThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */