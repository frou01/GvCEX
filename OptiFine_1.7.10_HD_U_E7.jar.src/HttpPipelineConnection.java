/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.Proxy;
/*     */ import java.net.Socket;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class HttpPipelineConnection
/*     */ {
/*  23 */   private String host = null;
/*     */   
/*  25 */   private int port = 0;
/*     */   
/*  27 */   private Proxy proxy = Proxy.NO_PROXY;
/*     */   
/*  29 */   private List<HttpPipelineRequest> listRequests = new LinkedList<HttpPipelineRequest>();
/*  30 */   private List<HttpPipelineRequest> listRequestsSend = new LinkedList<HttpPipelineRequest>();
/*     */   
/*  32 */   private Socket socket = null;
/*  33 */   private InputStream inputStream = null;
/*  34 */   private OutputStream outputStream = null;
/*     */   
/*  36 */   private HttpPipelineSender httpPipelineSender = null;
/*  37 */   private HttpPipelineReceiver httpPipelineReceiver = null;
/*     */   
/*  39 */   private int countRequests = 0;
/*     */   
/*     */   private boolean responseReceived = false;
/*     */   
/*  43 */   private long keepaliveTimeoutMs = 5000L;
/*  44 */   private int keepaliveMaxCount = 1000;
/*     */   
/*  46 */   private long timeLastActivityMs = System.currentTimeMillis();
/*     */   
/*     */   private boolean terminated = false;
/*     */   
/*     */   private static final String LF = "\n";
/*     */   
/*     */   public static final int TIMEOUT_CONNECT_MS = 5000;
/*     */   public static final int TIMEOUT_READ_MS = 5000;
/*  54 */   private static final Pattern patternFullUrl = Pattern.compile("^[a-zA-Z]+://.*");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpPipelineConnection(String host, int port) {
/*  60 */     this(host, port, Proxy.NO_PROXY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpPipelineConnection(String host, int port, Proxy proxy) {
/*  67 */     this.host = host;
/*  68 */     this.port = port;
/*  69 */     this.proxy = proxy;
/*     */     
/*  71 */     this.httpPipelineSender = new HttpPipelineSender(this);
/*  72 */     this.httpPipelineSender.start();
/*     */     
/*  74 */     this.httpPipelineReceiver = new HttpPipelineReceiver(this);
/*  75 */     this.httpPipelineReceiver.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean addRequest(HttpPipelineRequest pr) {
/*  86 */     if (isClosed()) {
/*  87 */       return false;
/*     */     }
/*  89 */     addRequest(pr, this.listRequests);
/*  90 */     addRequest(pr, this.listRequestsSend);
/*     */     
/*  92 */     this.countRequests++;
/*     */     
/*  94 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addRequest(HttpPipelineRequest pr, List<HttpPipelineRequest> list) {
/* 103 */     list.add(pr);
/*     */     
/* 105 */     notifyAll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setSocket(Socket s) throws IOException {
/* 114 */     if (this.terminated) {
/*     */       return;
/*     */     }
/* 117 */     if (this.socket != null) {
/* 118 */       throw new IllegalArgumentException("Already connected");
/*     */     }
/* 120 */     this.socket = s;
/*     */     
/* 122 */     this.socket.setTcpNoDelay(true);
/*     */     
/* 124 */     this.inputStream = this.socket.getInputStream();
/*     */     
/* 126 */     this.outputStream = new BufferedOutputStream(this.socket.getOutputStream());
/*     */     
/* 128 */     onActivity();
/*     */     
/* 130 */     notifyAll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized OutputStream getOutputStream() throws IOException, InterruptedException {
/* 139 */     while (this.outputStream == null) {
/*     */       
/* 141 */       checkTimeout();
/* 142 */       wait(1000L);
/*     */     } 
/*     */     
/* 145 */     return this.outputStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized InputStream getInputStream() throws IOException, InterruptedException {
/* 155 */     while (this.inputStream == null) {
/*     */       
/* 157 */       checkTimeout();
/* 158 */       wait(1000L);
/*     */     } 
/*     */     
/* 161 */     return this.inputStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized HttpPipelineRequest getNextRequestSend() throws InterruptedException, IOException {
/* 171 */     if (this.listRequestsSend.size() <= 0 && this.outputStream != null) {
/* 172 */       this.outputStream.flush();
/*     */     }
/* 174 */     return getNextRequest(this.listRequestsSend, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized HttpPipelineRequest getNextRequestReceive() throws InterruptedException {
/* 183 */     return getNextRequest(this.listRequests, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private HttpPipelineRequest getNextRequest(List<HttpPipelineRequest> list, boolean remove) throws InterruptedException {
/* 192 */     while (list.size() <= 0) {
/*     */       
/* 194 */       checkTimeout();
/* 195 */       wait(1000L);
/*     */     } 
/*     */     
/* 198 */     onActivity();
/*     */     
/* 200 */     if (remove) {
/* 201 */       return list.remove(0);
/*     */     }
/* 203 */     return list.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkTimeout() {
/* 212 */     if (this.socket == null) {
/*     */       return;
/*     */     }
/* 215 */     long timeoutMs = this.keepaliveTimeoutMs;
/*     */     
/* 217 */     if (this.listRequests.size() > 0) {
/* 218 */       timeoutMs = 5000L;
/*     */     }
/* 220 */     long timeNowMs = System.currentTimeMillis();
/*     */     
/* 222 */     if (timeNowMs > this.timeLastActivityMs + timeoutMs) {
/* 223 */       terminate(new InterruptedException("Timeout " + timeoutMs));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void onActivity() {
/* 231 */     this.timeLastActivityMs = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void onRequestSent(HttpPipelineRequest pr) {
/* 241 */     if (this.terminated) {
/*     */       return;
/*     */     }
/* 244 */     onActivity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void onResponseReceived(HttpPipelineRequest pr, HttpResponse resp) {
/* 253 */     if (this.terminated) {
/*     */       return;
/*     */     }
/* 256 */     this.responseReceived = true;
/* 257 */     onActivity();
/*     */     
/* 259 */     if (this.listRequests.size() <= 0 || this.listRequests.get(0) != pr) {
/* 260 */       throw new IllegalArgumentException("Response out of order: " + pr);
/*     */     }
/* 262 */     this.listRequests.remove(0);
/*     */     
/* 264 */     pr.setClosed(true);
/*     */     
/* 266 */     String location = resp.getHeader("Location");
/* 267 */     if (resp.getStatus() / 100 == 3 && location != null && pr.getHttpRequest().getRedirects() < 5) {
/*     */       
/*     */       try
/*     */       {
/*     */         
/* 272 */         location = normalizeUrl(location, pr.getHttpRequest());
/* 273 */         HttpRequest hr2 = HttpPipeline.makeRequest(location, pr.getHttpRequest().getProxy());
/* 274 */         hr2.setRedirects(pr.getHttpRequest().getRedirects() + 1);
/* 275 */         HttpPipelineRequest hpr2 = new HttpPipelineRequest(hr2, pr.getHttpListener());
/* 276 */         HttpPipeline.addRequest(hpr2);
/*     */       }
/* 278 */       catch (IOException e)
/*     */       {
/* 280 */         pr.getHttpListener().failed(pr.getHttpRequest(), e);
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 286 */       HttpListener listener = pr.getHttpListener();
/*     */       
/* 288 */       listener.finished(pr.getHttpRequest(), resp);
/*     */     } 
/*     */     
/* 291 */     checkResponseHeader(resp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String normalizeUrl(String url, HttpRequest hr) {
/* 298 */     if (patternFullUrl.matcher(url).matches()) {
/* 299 */       return url;
/*     */     }
/* 301 */     if (url.startsWith("//")) {
/* 302 */       return "http:" + url;
/*     */     }
/* 304 */     String server = hr.getHost();
/* 305 */     if (hr.getPort() != 80) {
/* 306 */       server = server + ":" + hr.getPort();
/*     */     }
/* 308 */     if (url.startsWith("/")) {
/* 309 */       return "http://" + server + url;
/*     */     }
/* 311 */     String file = hr.getFile();
/* 312 */     int pos = file.lastIndexOf("/");
/* 313 */     if (pos >= 0) {
/* 314 */       return "http://" + server + file.substring(0, pos + 1) + url;
/*     */     }
/* 316 */     return "http://" + server + "/" + url;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkResponseHeader(HttpResponse resp) {
/* 325 */     String connStr = resp.getHeader("Connection");
/* 326 */     if (connStr != null)
/*     */     {
/* 328 */       if (!connStr.toLowerCase().equals("keep-alive"))
/*     */       {
/*     */         
/* 331 */         terminate(new EOFException("Connection not keep-alive"));
/*     */       }
/*     */     }
/*     */     
/* 335 */     String keepAliveStr = resp.getHeader("Keep-Alive");
/* 336 */     if (keepAliveStr != null) {
/*     */       
/* 338 */       String[] parts = Config.tokenize(keepAliveStr, ",;");
/* 339 */       for (int i = 0; i < parts.length; i++) {
/*     */         
/* 341 */         String part = parts[i];
/* 342 */         String[] tokens = split(part, '=');
/* 343 */         if (tokens.length >= 2) {
/*     */ 
/*     */           
/* 346 */           if (tokens[0].equals("timeout")) {
/*     */             
/* 348 */             int timeout = Config.parseInt(tokens[1], -1);
/* 349 */             if (timeout > 0) {
/* 350 */               this.keepaliveTimeoutMs = (timeout * 1000);
/*     */             }
/*     */           } 
/* 353 */           if (tokens[0].equals("max")) {
/*     */             
/* 355 */             int max = Config.parseInt(tokens[1], -1);
/* 356 */             if (max > 0) {
/* 357 */               this.keepaliveMaxCount = max;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String[] split(String str, char separator) {
/* 367 */     int pos = str.indexOf(separator);
/* 368 */     if (pos < 0) {
/* 369 */       return new String[] { str };
/*     */     }
/* 371 */     String str1 = str.substring(0, pos);
/* 372 */     String str2 = str.substring(pos + 1);
/*     */     
/* 374 */     return new String[] { str1, str2 };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void onExceptionSend(HttpPipelineRequest pr, Exception e) {
/* 382 */     terminate(e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void onExceptionReceive(HttpPipelineRequest pr, Exception e) {
/* 389 */     terminate(e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void terminate(Exception e) {
/* 396 */     if (this.terminated) {
/*     */       return;
/*     */     }
/* 399 */     this.terminated = true;
/*     */ 
/*     */ 
/*     */     
/* 403 */     terminateRequests(e);
/*     */     
/* 405 */     if (this.httpPipelineSender != null) {
/* 406 */       this.httpPipelineSender.interrupt();
/*     */     }
/* 408 */     if (this.httpPipelineReceiver != null) {
/* 409 */       this.httpPipelineReceiver.interrupt();
/*     */     }
/*     */     
/*     */     try {
/* 413 */       if (this.socket != null) {
/* 414 */         this.socket.close();
/*     */       }
/* 416 */     } catch (IOException ex) {}
/*     */ 
/*     */ 
/*     */     
/* 420 */     this.socket = null;
/* 421 */     this.inputStream = null;
/* 422 */     this.outputStream = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void terminateRequests(Exception e) {
/* 431 */     if (this.listRequests.size() <= 0) {
/*     */       return;
/*     */     }
/* 434 */     if (!this.responseReceived) {
/*     */ 
/*     */       
/* 437 */       HttpPipelineRequest pr = this.listRequests.remove(0);
/* 438 */       pr.getHttpListener().failed(pr.getHttpRequest(), e);
/* 439 */       pr.setClosed(true);
/*     */     } 
/*     */     
/* 442 */     while (this.listRequests.size() > 0) {
/*     */       
/* 444 */       HttpPipelineRequest pr = this.listRequests.remove(0);
/* 445 */       HttpPipeline.addRequest(pr);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean isClosed() {
/* 454 */     if (this.terminated) {
/* 455 */       return true;
/*     */     }
/* 457 */     if (this.countRequests >= this.keepaliveMaxCount) {
/* 458 */       return true;
/*     */     }
/* 460 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCountRequests() {
/* 467 */     return this.countRequests;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean hasActiveRequests() {
/* 476 */     if (this.listRequests.size() > 0) {
/* 477 */       return true;
/*     */     }
/* 479 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHost() {
/* 486 */     return this.host;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPort() {
/* 493 */     return this.port;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Proxy getProxy() {
/* 500 */     return this.proxy;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\HttpPipelineConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */