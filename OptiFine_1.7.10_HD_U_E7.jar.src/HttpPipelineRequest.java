/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HttpPipelineRequest
/*    */ {
/* 11 */   private HttpRequest httpRequest = null;
/* 12 */   private HttpListener httpListener = null;
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean closed = false;
/*    */ 
/*    */ 
/*    */   
/*    */   public HttpPipelineRequest(HttpRequest httpRequest, HttpListener httpListener) {
/* 21 */     this.httpRequest = httpRequest;
/* 22 */     this.httpListener = httpListener;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HttpRequest getHttpRequest() {
/* 29 */     return this.httpRequest;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HttpListener getHttpListener() {
/* 36 */     return this.httpListener;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isClosed() {
/* 43 */     return this.closed;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setClosed(boolean closed) {
/* 50 */     this.closed = closed;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\HttpPipelineRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */