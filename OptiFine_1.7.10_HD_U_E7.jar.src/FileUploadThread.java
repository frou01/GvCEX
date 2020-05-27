/*    */ import java.util.Map;
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
/*    */ public class FileUploadThread
/*    */   extends Thread
/*    */ {
/*    */   private String urlString;
/*    */   private Map headers;
/*    */   private byte[] content;
/*    */   private IFileUploadListener listener;
/*    */   
/*    */   public FileUploadThread(String urlString, Map headers, byte[] content, IFileUploadListener listener) {
/* 30 */     this.urlString = urlString;
/* 31 */     this.headers = headers;
/* 32 */     this.content = content;
/* 33 */     this.listener = listener;
/*    */   }
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
/* 45 */       HttpUtils.post(this.urlString, this.headers, this.content);
/*    */       
/* 47 */       this.listener.fileUploadFinished(this.urlString, this.content, null);
/*    */     }
/* 49 */     catch (Exception e) {
/*    */ 
/*    */       
/* 52 */       this.listener.fileUploadFinished(this.urlString, this.content, e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUrlString() {
/* 61 */     return this.urlString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] getContent() {
/* 69 */     return this.content;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IFileUploadListener getListener() {
/* 77 */     return this.listener;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\FileUploadThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */