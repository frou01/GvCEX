/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
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
/*     */ public class HttpPipeline
/*     */ {
/*  23 */   private static Map mapConnections = new HashMap<Object, Object>();
/*     */   
/*     */   public static final String HEADER_USER_AGENT = "User-Agent";
/*     */   
/*     */   public static final String HEADER_HOST = "Host";
/*     */   
/*     */   public static final String HEADER_ACCEPT = "Accept";
/*     */   
/*     */   public static final String HEADER_LOCATION = "Location";
/*     */   
/*     */   public static final String HEADER_KEEP_ALIVE = "Keep-Alive";
/*     */   
/*     */   public static final String HEADER_CONNECTION = "Connection";
/*     */   
/*     */   public static final String HEADER_VALUE_KEEP_ALIVE = "keep-alive";
/*     */   
/*     */   public static final String HEADER_TRANSFER_ENCODING = "Transfer-Encoding";
/*     */   
/*     */   public static final String HEADER_VALUE_CHUNKED = "chunked";
/*     */   
/*     */   public static void addRequest(String urlStr, HttpListener listener) throws IOException {
/*  44 */     addRequest(urlStr, listener, Proxy.NO_PROXY);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRequest(String urlStr, HttpListener listener, Proxy proxy) throws IOException {
/*  49 */     HttpRequest hr = makeRequest(urlStr, proxy);
/*  50 */     HttpPipelineRequest hpr = new HttpPipelineRequest(hr, listener);
/*     */     
/*  52 */     addRequest(hpr);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HttpRequest makeRequest(String urlStr, Proxy proxy) throws IOException {
/*  57 */     URL url = new URL(urlStr);
/*     */     
/*  59 */     if (!url.getProtocol().equals("http")) {
/*  60 */       throw new IOException("Only protocol http is supported: " + url);
/*     */     }
/*  62 */     String file = url.getFile();
/*  63 */     String host = url.getHost();
/*  64 */     int port = url.getPort();
/*     */     
/*  66 */     if (port <= 0) {
/*  67 */       port = 80;
/*     */     }
/*  69 */     String method = "GET";
/*  70 */     String http = "HTTP/1.1";
/*     */     
/*  72 */     Map<String, String> headers = new LinkedHashMap<String, String>();
/*  73 */     headers.put("User-Agent", "Java/" + System.getProperty("java.version"));
/*  74 */     headers.put("Host", host);
/*  75 */     headers.put("Accept", "text/html, image/gif, image/png");
/*  76 */     headers.put("Connection", "keep-alive");
/*     */     
/*  78 */     byte[] body = new byte[0];
/*     */     
/*  80 */     HttpRequest req = new HttpRequest(host, port, proxy, method, file, http, headers, body);
/*     */     
/*  82 */     return req;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRequest(HttpPipelineRequest pr) {
/*  87 */     HttpRequest hr = pr.getHttpRequest();
/*  88 */     HttpPipelineConnection conn = getConnection(hr.getHost(), hr.getPort(), hr.getProxy());
/*  89 */     while (!conn.addRequest(pr)) {
/*     */       
/*  91 */       removeConnection(hr.getHost(), hr.getPort(), hr.getProxy(), conn);
/*  92 */       conn = getConnection(hr.getHost(), hr.getPort(), hr.getProxy());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized HttpPipelineConnection getConnection(String host, int port, Proxy proxy) {
/* 103 */     String key = makeConnectionKey(host, port, proxy);
/* 104 */     HttpPipelineConnection conn = (HttpPipelineConnection)mapConnections.get(key);
/*     */     
/* 106 */     if (conn == null) {
/*     */       
/* 108 */       conn = new HttpPipelineConnection(host, port, proxy);
/* 109 */       mapConnections.put(key, conn);
/*     */     } 
/*     */     
/* 112 */     return conn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized void removeConnection(String host, int port, Proxy proxy, HttpPipelineConnection hpc) {
/* 121 */     String key = makeConnectionKey(host, port, proxy);
/* 122 */     HttpPipelineConnection conn = (HttpPipelineConnection)mapConnections.get(key);
/*     */     
/* 124 */     if (conn == hpc) {
/* 125 */       mapConnections.remove(key);
/*     */     }
/*     */   }
/*     */   
/*     */   private static String makeConnectionKey(String host, int port, Proxy proxy) {
/* 130 */     String hostPort = host + ":" + port + "-" + proxy;
/* 131 */     return hostPort;
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] get(String urlStr) throws IOException {
/* 136 */     return get(urlStr, Proxy.NO_PROXY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] get(String urlStr, Proxy proxy) throws IOException {
/* 142 */     if (urlStr.startsWith("file:")) {
/*     */       
/* 144 */       URL urlFile = new URL(urlStr);
/* 145 */       InputStream in = urlFile.openStream();
/* 146 */       byte[] bytes = Config.readAll(in);
/* 147 */       return bytes;
/*     */     } 
/*     */     
/* 150 */     HttpRequest req = makeRequest(urlStr, proxy);
/* 151 */     HttpResponse resp = executeRequest(req);
/* 152 */     if (resp.getStatus() / 100 != 2) {
/* 153 */       throw new IOException("HTTP response: " + resp.getStatus());
/*     */     }
/* 155 */     return resp.getBody();
/*     */   }
/*     */ 
/*     */   
/*     */   public static HttpResponse executeRequest(HttpRequest req) throws IOException {
/* 160 */     final Map<String, Object> map = new HashMap<String, Object>();
/* 161 */     String KEY_RESPONSE = "Response";
/* 162 */     String KEY_EXCEPTION = "Exception";
/*     */     
/* 164 */     HttpListener l = new HttpListener()
/*     */       {
/*     */         
/*     */         public void finished(HttpRequest req, HttpResponse resp)
/*     */         {
/* 169 */           synchronized (map) {
/*     */             
/* 171 */             map.put("Response", resp);
/* 172 */             map.notifyAll();
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public void failed(HttpRequest req, Exception e) {
/* 178 */           synchronized (map) {
/*     */             
/* 180 */             map.put("Exception", e);
/* 181 */             map.notifyAll();
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 186 */     synchronized (map) {
/*     */ 
/*     */       
/* 189 */       HttpPipelineRequest hpr = new HttpPipelineRequest(req, l);
/* 190 */       addRequest(hpr);
/*     */ 
/*     */       
/*     */       try {
/* 194 */         map.wait();
/*     */       }
/* 196 */       catch (InterruptedException e1) {
/*     */         
/* 198 */         throw new InterruptedIOException("Interrupted");
/*     */       } 
/*     */       
/* 201 */       Exception e = (Exception)map.get("Exception");
/* 202 */       if (e != null) {
/*     */         
/* 204 */         if (e instanceof IOException)
/* 205 */           throw (IOException)e; 
/* 206 */         if (e instanceof RuntimeException) {
/* 207 */           throw (RuntimeException)e;
/*     */         }
/* 209 */         throw new RuntimeException(e.getMessage(), e);
/*     */       } 
/*     */       
/* 212 */       HttpResponse resp = (HttpResponse)map.get("Response");
/* 213 */       if (resp == null) {
/* 214 */         throw new IOException("Response is null");
/*     */       }
/* 216 */       return resp;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasActiveRequests() {
/* 225 */     Collection conns = mapConnections.values();
/* 226 */     for (Iterator<HttpPipelineConnection> it = conns.iterator(); it.hasNext(); ) {
/*     */       
/* 228 */       HttpPipelineConnection conn = it.next();
/* 229 */       if (conn.hasActiveRequests()) {
/* 230 */         return true;
/*     */       }
/*     */     } 
/* 233 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\HttpPipeline.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */