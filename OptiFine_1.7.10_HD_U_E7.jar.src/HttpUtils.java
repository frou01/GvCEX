/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class HttpUtils
/*     */ {
/*  26 */   private static String playerItemsUrl = null;
/*     */ 
/*     */   
/*     */   public static final String SERVER_URL = "http://s.optifine.net";
/*     */   
/*     */   public static final String POST_URL = "http://optifine.net";
/*     */ 
/*     */   
/*     */   public static byte[] get(String urlStr) throws IOException {
/*  35 */     HttpURLConnection conn = null;
/*     */     
/*     */     try {
/*  38 */       URL url = new URL(urlStr);
/*  39 */       conn = (HttpURLConnection)url.openConnection(bao.B().O());
/*  40 */       conn.setDoInput(true);
/*  41 */       conn.setDoOutput(false);
/*  42 */       conn.connect();
/*     */       
/*  44 */       if (conn.getResponseCode() / 100 != 2) {
/*     */ 
/*     */         
/*  47 */         if (conn.getErrorStream() != null) {
/*  48 */           Config.readAll(conn.getErrorStream());
/*     */         }
/*  50 */         throw new IOException("HTTP response: " + conn.getResponseCode());
/*     */       } 
/*     */       
/*  53 */       InputStream in = conn.getInputStream();
/*     */       
/*  55 */       byte[] bytes = new byte[conn.getContentLength()];
/*  56 */       int pos = 0;
/*     */       
/*     */       do {
/*  59 */         int len = in.read(bytes, pos, bytes.length - pos);
/*     */         
/*  61 */         if (len < 0) {
/*  62 */           throw new IOException("Input stream closed: " + urlStr);
/*     */         }
/*  64 */         pos += len;
/*     */       }
/*  66 */       while (pos < bytes.length);
/*     */       
/*  68 */       return bytes;
/*     */     }
/*     */     finally {
/*     */       
/*  72 */       if (conn != null) {
/*  73 */         conn.disconnect();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String post(String urlStr, Map headers, byte[] content) throws IOException {
/*  81 */     HttpURLConnection conn = null;
/*     */     
/*     */     try {
/*  84 */       URL url = new URL(urlStr);
/*  85 */       conn = (HttpURLConnection)url.openConnection(bao.B().O());
/*  86 */       conn.setRequestMethod("POST");
/*     */       
/*  88 */       if (headers != null) {
/*     */         
/*  90 */         Set keys = headers.keySet();
/*  91 */         for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
/*     */           
/*  93 */           String key = it.next();
/*  94 */           String val = "" + headers.get(key);
/*  95 */           conn.setRequestProperty(key, val);
/*     */         } 
/*     */       } 
/*     */       
/*  99 */       conn.setRequestProperty("Content-Type", "text/plain");
/* 100 */       conn.setRequestProperty("Content-Length", "" + content.length);
/* 101 */       conn.setRequestProperty("Content-Language", "en-US");
/*     */       
/* 103 */       conn.setUseCaches(false);
/* 104 */       conn.setDoInput(true);
/* 105 */       conn.setDoOutput(true);
/*     */       
/* 107 */       OutputStream os = conn.getOutputStream();
/* 108 */       os.write(content);
/* 109 */       os.flush();
/* 110 */       os.close();
/*     */       
/* 112 */       InputStream in = conn.getInputStream();
/* 113 */       InputStreamReader isr = new InputStreamReader(in, "ASCII");
/* 114 */       BufferedReader br = new BufferedReader(isr);
/* 115 */       StringBuffer sb = new StringBuffer();
/*     */       String line;
/* 117 */       while ((line = br.readLine()) != null) {
/*     */         
/* 119 */         sb.append(line);
/* 120 */         sb.append('\r');
/*     */       } 
/* 122 */       br.close();
/* 123 */       return sb.toString();
/*     */     }
/*     */     finally {
/*     */       
/* 127 */       if (conn != null) {
/* 128 */         conn.disconnect();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized String getPlayerItemsUrl() {
/* 137 */     if (playerItemsUrl == null) {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 142 */         boolean local = Config.parseBoolean(System.getProperty("player.models.local"), false);
/* 143 */         if (local)
/*     */         {
/* 145 */           File dirMc = (bao.B()).w;
/* 146 */           File dirModels = new File(dirMc, "playermodels");
/* 147 */           playerItemsUrl = dirModels.toURI().toURL().toExternalForm();
/*     */         }
/*     */       
/* 150 */       } catch (Exception e) {
/*     */         
/* 152 */         Config.warn("" + e.getClass().getName() + ": " + e.getMessage());
/*     */       } 
/*     */       
/* 155 */       if (playerItemsUrl == null) {
/* 156 */         playerItemsUrl = "http://s.optifine.net";
/*     */       }
/*     */     } 
/* 159 */     return playerItemsUrl;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\HttpUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */