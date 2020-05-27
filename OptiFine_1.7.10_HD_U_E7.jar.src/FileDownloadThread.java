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
/*    */ public class FileDownloadThread
/*    */   extends Thread
/*    */ {
/* 15 */   private String urlString = null;
/*    */   
/* 17 */   private IFileDownloadListener listener = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FileDownloadThread(String urlString, IFileDownloadListener listener) {
/* 26 */     this.urlString = urlString;
/* 27 */     this.listener = listener;
/*    */   }
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
/*    */   public void run() {
/*    */     try {
/* 41 */       byte[] bytes = HttpPipeline.get(this.urlString, bao.B().O());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 48 */       this.listener.fileDownloadFinished(this.urlString, bytes, null);
/*    */     }
/* 50 */     catch (Exception e) {
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 55 */       this.listener.fileDownloadFinished(this.urlString, null, e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUrlString() {
/* 64 */     return this.urlString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IFileDownloadListener getListener() {
/* 72 */     return this.listener;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\FileDownloadThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */