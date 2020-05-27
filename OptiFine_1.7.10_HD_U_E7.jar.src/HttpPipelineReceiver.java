/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.Charset;
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
/*     */ public class HttpPipelineReceiver
/*     */   extends Thread
/*     */ {
/*  19 */   private HttpPipelineConnection httpPipelineConnection = null;
/*     */   
/*  21 */   private static final Charset ASCII = Charset.forName("ASCII");
/*     */ 
/*     */   
/*     */   private static final String HEADER_CONTENT_LENGTH = "Content-Length";
/*     */ 
/*     */   
/*     */   private static final char CR = '\r';
/*     */   
/*     */   private static final char LF = '\n';
/*     */ 
/*     */   
/*     */   public HttpPipelineReceiver(HttpPipelineConnection httpPipelineConnection) {
/*  33 */     super("HttpPipelineReceiver");
/*     */ 
/*     */     
/*  36 */     this.httpPipelineConnection = httpPipelineConnection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*  44 */     while (!Thread.interrupted()) {
/*     */       
/*  46 */       HttpPipelineRequest currentRequest = null;
/*     */       
/*     */       try {
/*  49 */         currentRequest = this.httpPipelineConnection.getNextRequestReceive();
/*     */         
/*  51 */         InputStream in = this.httpPipelineConnection.getInputStream();
/*  52 */         HttpResponse resp = readResponse(in);
/*     */         
/*  54 */         this.httpPipelineConnection.onResponseReceived(currentRequest, resp);
/*     */       }
/*  56 */       catch (InterruptedException e) {
/*     */ 
/*     */         
/*     */         return;
/*     */       }
/*  61 */       catch (Exception e) {
/*     */         
/*  63 */         this.httpPipelineConnection.onExceptionReceive(currentRequest, e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private HttpResponse readResponse(InputStream in) throws IOException {
/*  74 */     String statusLine = readLine(in);
/*     */     
/*  76 */     String[] parts = Config.tokenize(statusLine, " ");
/*  77 */     if (parts.length < 3) {
/*  78 */       throw new IOException("Invalid status line: " + statusLine);
/*     */     }
/*  80 */     String http = parts[0];
/*  81 */     int status = Config.parseInt(parts[1], 0);
/*  82 */     String message = parts[2];
/*     */     
/*  84 */     Map<String, String> headers = new LinkedHashMap<String, String>();
/*     */     
/*     */     while (true) {
/*  87 */       String line = readLine(in);
/*  88 */       if (line.length() <= 0) {
/*     */         break;
/*     */       }
/*  91 */       int pos = line.indexOf(":");
/*  92 */       if (pos > 0) {
/*     */         
/*  94 */         String key = line.substring(0, pos).trim();
/*  95 */         String val = line.substring(pos + 1).trim();
/*  96 */         headers.put(key, val);
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     byte[] body = null;
/* 101 */     String lenStr = headers.get("Content-Length");
/* 102 */     if (lenStr != null) {
/*     */       
/* 104 */       int len = Config.parseInt(lenStr, -1);
/* 105 */       if (len > 0)
/*     */       {
/* 107 */         body = new byte[len];
/* 108 */         readFull(body, in);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 113 */       String enc = headers.get("Transfer-Encoding");
/* 114 */       if (Config.equals(enc, "chunked")) {
/* 115 */         body = readContentChunked(in);
/*     */       }
/*     */     } 
/* 118 */     return new HttpResponse(status, statusLine, headers, body);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] readContentChunked(InputStream in) throws IOException {
/*     */     int len;
/* 125 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*     */ 
/*     */     
/*     */     do {
/* 129 */       String line = readLine(in);
/* 130 */       String[] parts = Config.tokenize(line, "; ");
/* 131 */       len = Integer.parseInt(parts[0], 16);
/* 132 */       byte[] buf = new byte[len];
/*     */       
/* 134 */       readFull(buf, in);
/* 135 */       baos.write(buf);
/*     */       
/* 137 */       readLine(in);
/*     */     }
/* 139 */     while (len != 0);
/*     */ 
/*     */ 
/*     */     
/* 143 */     return baos.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readFull(byte[] buf, InputStream in) throws IOException {
/* 150 */     int pos = 0;
/* 151 */     while (pos < buf.length) {
/*     */       
/* 153 */       int len = in.read(buf, pos, buf.length - pos);
/* 154 */       if (len < 0) {
/* 155 */         throw new EOFException();
/*     */       }
/* 157 */       pos += len;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String readLine(InputStream in) throws IOException {
/* 166 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*     */     
/* 168 */     int prev = -1;
/* 169 */     boolean hasCRLF = false;
/*     */     
/*     */     while (true) {
/* 172 */       int i = in.read();
/* 173 */       if (i < 0) {
/*     */         break;
/*     */       }
/* 176 */       baos.write(i);
/*     */       
/* 178 */       if (prev == 13 && i == 10) {
/*     */         
/* 180 */         hasCRLF = true;
/*     */         
/*     */         break;
/*     */       } 
/* 184 */       prev = i;
/*     */     } 
/*     */     
/* 187 */     byte[] bytes = baos.toByteArray();
/*     */     
/* 189 */     String str = new String(bytes, ASCII);
/*     */     
/* 191 */     if (hasCRLF) {
/* 192 */       str = str.substring(0, str.length() - 2);
/*     */     }
/* 194 */     return str;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\HttpPipelineReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */