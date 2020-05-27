/*     */ import java.net.Proxy;
/*     */ import java.util.LinkedHashMap;
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
/*     */ public class HttpRequest
/*     */ {
/*  16 */   private String host = null;
/*     */   
/*  18 */   private int port = 0;
/*     */   
/*  20 */   private Proxy proxy = Proxy.NO_PROXY;
/*     */   
/*  22 */   private String method = null;
/*     */   
/*  24 */   private String file = null;
/*     */   
/*  26 */   private String http = null;
/*     */   
/*  28 */   private Map<String, String> headers = new LinkedHashMap<String, String>();
/*     */   
/*  30 */   private byte[] body = null;
/*     */   
/*  32 */   private int redirects = 0;
/*     */ 
/*     */   
/*     */   public static final String METHOD_GET = "GET";
/*     */ 
/*     */   
/*     */   public static final String METHOD_HEAD = "HEAD";
/*     */ 
/*     */   
/*     */   public static final String METHOD_POST = "POST";
/*     */ 
/*     */   
/*     */   public static final String HTTP_1_0 = "HTTP/1.0";
/*     */ 
/*     */   
/*     */   public static final String HTTP_1_1 = "HTTP/1.1";
/*     */ 
/*     */   
/*     */   public HttpRequest(String host, int port, Proxy proxy, String method, String file, String http, Map<String, String> headers, byte[] body) {
/*  51 */     this.host = host;
/*  52 */     this.port = port;
/*  53 */     this.proxy = proxy;
/*  54 */     this.method = method;
/*  55 */     this.file = file;
/*  56 */     this.http = http;
/*  57 */     this.headers = headers;
/*  58 */     this.body = body;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHost() {
/*  65 */     return this.host;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPort() {
/*  72 */     return this.port;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMethod() {
/*  79 */     return this.method;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFile() {
/*  86 */     return this.file;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHttp() {
/*  93 */     return this.http;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> getHeaders() {
/* 100 */     return this.headers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getBody() {
/* 107 */     return this.body;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRedirects() {
/* 114 */     return this.redirects;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRedirects(int redirects) {
/* 121 */     this.redirects = redirects;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Proxy getProxy() {
/* 128 */     return this.proxy;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\HttpRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */