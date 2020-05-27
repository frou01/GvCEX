/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Proxy;
/*     */ import java.net.Socket;
/*     */ import java.nio.charset.Charset;
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
/*     */ public class HttpPipelineSender
/*     */   extends Thread
/*     */ {
/*  21 */   private HttpPipelineConnection httpPipelineConnection = null;
/*     */   
/*     */   private static final String CRLF = "\r\n";
/*  24 */   private static Charset ASCII = Charset.forName("ASCII");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpPipelineSender(HttpPipelineConnection httpPipelineConnection) {
/*  34 */     super("HttpPipelineSender");
/*     */     
/*  36 */     this.httpPipelineConnection = httpPipelineConnection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*  44 */     HttpPipelineRequest hpr = null;
/*     */ 
/*     */     
/*     */     try {
/*  48 */       connect();
/*     */       
/*  50 */       while (!Thread.interrupted())
/*     */       {
/*     */         
/*  53 */         hpr = this.httpPipelineConnection.getNextRequestSend();
/*     */         
/*  55 */         HttpRequest req = hpr.getHttpRequest();
/*     */         
/*  57 */         OutputStream out = this.httpPipelineConnection.getOutputStream();
/*  58 */         writeRequest(req, out);
/*     */         
/*  60 */         this.httpPipelineConnection.onRequestSent(hpr);
/*     */       }
/*     */     
/*  63 */     } catch (InterruptedException e) {
/*     */ 
/*     */       
/*     */       return;
/*     */     }
/*  68 */     catch (Exception e) {
/*     */       
/*  70 */       this.httpPipelineConnection.onExceptionSend(hpr, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void connect() throws IOException {
/*  78 */     String host = this.httpPipelineConnection.getHost();
/*  79 */     int port = this.httpPipelineConnection.getPort();
/*     */     
/*  81 */     Proxy proxy = this.httpPipelineConnection.getProxy();
/*  82 */     Socket socket = new Socket(proxy);
/*  83 */     socket.connect(new InetSocketAddress(host, port), 5000);
/*     */     
/*  85 */     this.httpPipelineConnection.setSocket(socket);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeRequest(HttpRequest req, OutputStream out) throws IOException {
/*  94 */     write(out, req.getMethod() + " " + req.getFile() + " " + req.getHttp() + "\r\n");
/*     */     
/*  96 */     Map<String, String> headers = req.getHeaders();
/*  97 */     Set<String> keySet = headers.keySet();
/*  98 */     for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
/*     */       
/* 100 */       String key = it.next();
/* 101 */       String val = req.getHeaders().get(key);
/* 102 */       write(out, key + ": " + val + "\r\n");
/*     */     } 
/*     */     
/* 105 */     write(out, "\r\n");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void write(OutputStream out, String str) throws IOException {
/* 114 */     byte[] bytes = str.getBytes(ASCII);
/* 115 */     out.write(bytes);
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\HttpPipelineSender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */