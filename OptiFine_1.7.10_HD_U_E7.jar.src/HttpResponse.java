/*    */ import java.util.LinkedHashMap;
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
/*    */ public class HttpResponse
/*    */ {
/* 15 */   private int status = 0;
/*    */   
/* 17 */   private String statusLine = null;
/*    */   
/* 19 */   private Map<String, String> headers = new LinkedHashMap<String, String>();
/*    */   
/* 21 */   private byte[] body = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HttpResponse(int status, String statusLine, Map<String, String> headers, byte[] body) {
/* 32 */     this.status = status;
/* 33 */     this.statusLine = statusLine;
/* 34 */     this.headers = headers;
/* 35 */     this.body = body;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getStatus() {
/* 42 */     return this.status;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getStatusLine() {
/* 49 */     return this.statusLine;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Map getHeaders() {
/* 56 */     return this.headers;
/*    */   }
/*    */   
/*    */   public String getHeader(String key) {
/* 60 */     return this.headers.get(key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] getBody() {
/* 67 */     return this.body;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\HttpResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */