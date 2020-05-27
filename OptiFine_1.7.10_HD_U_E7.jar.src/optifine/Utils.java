/*     */ package optifine;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.swing.JOptionPane;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Utils
/*     */ {
/*     */   public static final String MAC_OS_HOME_PREFIX = "Library/Application Support";
/*     */   
/*     */   public enum OS
/*     */   {
/*  38 */     LINUX, SOLARIS, WINDOWS, MACOS, UNKNOWN;
/*     */   }
/*     */   
/*  41 */   private static final char[] hexTable = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static File getWorkingDirectory() {
/*  50 */     return getWorkingDirectory("minecraft");
/*     */   }
/*     */ 
/*     */   
/*     */   public static File getWorkingDirectory(String applicationName) {
/*  55 */     String applicationData, userHome = System.getProperty("user.home", ".");
/*  56 */     File workingDirectory = null;
/*  57 */     switch (getPlatform()) {
/*     */       
/*     */       case null:
/*     */       case SOLARIS:
/*  61 */         workingDirectory = new File(userHome, String.valueOf('.') + applicationName + '/');
/*     */         break;
/*     */       case WINDOWS:
/*  64 */         applicationData = System.getenv("APPDATA");
/*  65 */         if (applicationData != null) {
/*  66 */           workingDirectory = new File(applicationData, "." + applicationName + '/'); break;
/*     */         } 
/*  68 */         workingDirectory = new File(userHome, String.valueOf('.') + applicationName + '/');
/*     */         break;
/*     */       case MACOS:
/*  71 */         workingDirectory = new File(userHome, "Library/Application Support/" + applicationName);
/*     */         break;
/*     */       default:
/*  74 */         workingDirectory = new File(userHome, String.valueOf(applicationName) + '/'); break;
/*     */     } 
/*  76 */     if (!workingDirectory.exists() && !workingDirectory.mkdirs()) {
/*  77 */       throw new RuntimeException("The working directory could not be created: " + workingDirectory);
/*     */     }
/*  79 */     return workingDirectory;
/*     */   }
/*     */ 
/*     */   
/*     */   public static OS getPlatform() {
/*  84 */     String osName = System.getProperty("os.name").toLowerCase();
/*  85 */     if (osName.contains("win"))
/*  86 */       return OS.WINDOWS; 
/*  87 */     if (osName.contains("mac"))
/*  88 */       return OS.MACOS; 
/*  89 */     if (osName.contains("solaris"))
/*  90 */       return OS.SOLARIS; 
/*  91 */     if (osName.contains("sunos"))
/*  92 */       return OS.SOLARIS; 
/*  93 */     if (osName.contains("linux"))
/*  94 */       return OS.LINUX; 
/*  95 */     if (osName.contains("unix"))
/*  96 */       return OS.LINUX; 
/*  97 */     return OS.UNKNOWN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int find(byte[] buf, byte[] pattern) {
/* 106 */     return find(buf, 0, pattern);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int find(byte[] buf, int index, byte[] pattern) {
/* 116 */     for (int i = index; i < buf.length - pattern.length; i++) {
/*     */       
/* 118 */       boolean found = true;
/*     */       
/* 120 */       for (int pos = 0; pos < pattern.length; pos++) {
/*     */         
/* 122 */         if (pattern[pos] != buf[i + pos]) {
/*     */           
/* 124 */           found = false;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 129 */       if (found) {
/* 130 */         return i;
/*     */       }
/*     */     } 
/* 133 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] readAll(InputStream is) throws IOException {
/* 141 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 142 */     byte[] buf = new byte[1024];
/*     */     
/*     */     while (true) {
/* 145 */       int len = is.read(buf);
/* 146 */       if (len < 0)
/*     */         break; 
/* 148 */       baos.write(buf, 0, len);
/*     */     } 
/*     */ 
/*     */     
/* 152 */     is.close();
/*     */     
/* 154 */     byte[] bytes = baos.toByteArray();
/*     */     
/* 156 */     return bytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void dbg(String str) {
/* 161 */     System.out.println(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] tokenize(String str, String delim) {
/* 170 */     List<String> list = new ArrayList();
/* 171 */     StringTokenizer tok = new StringTokenizer(str, delim);
/* 172 */     while (tok.hasMoreTokens()) {
/*     */       
/* 174 */       String token = tok.nextToken();
/* 175 */       list.add(token);
/*     */     } 
/* 177 */     String[] tokens = list.<String>toArray(new String[list.size()]);
/* 178 */     return tokens;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getExceptionStackTrace(Throwable e) {
/* 187 */     StringWriter swr = new StringWriter();
/* 188 */     PrintWriter pwr = new PrintWriter(swr);
/*     */     
/* 190 */     e.printStackTrace(pwr);
/*     */     
/* 192 */     pwr.close();
/*     */     
/*     */     try {
/* 195 */       swr.close();
/*     */     }
/* 197 */     catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     return swr.getBuffer().toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void copyFile(File fileSrc, File fileDest) throws IOException {
/* 212 */     if (fileSrc.getCanonicalPath().equals(fileDest.getCanonicalPath())) {
/*     */       return;
/*     */     }
/* 215 */     FileInputStream fin = new FileInputStream(fileSrc);
/* 216 */     FileOutputStream fout = new FileOutputStream(fileDest);
/* 217 */     copyAll(fin, fout);
/*     */     
/* 219 */     fout.flush();
/*     */     
/* 221 */     fin.close();
/* 222 */     fout.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void copyAll(InputStream is, OutputStream os) throws IOException {
/* 230 */     byte[] buf = new byte[1024];
/*     */     
/*     */     while (true) {
/* 233 */       int len = is.read(buf);
/* 234 */       if (len < 0)
/*     */         break; 
/* 236 */       os.write(buf, 0, len);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void showMessage(String msg) {
/* 245 */     JOptionPane.showMessageDialog(null, msg, "OptiFine", 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void showErrorMessage(String msg) {
/* 252 */     JOptionPane.showMessageDialog(null, msg, "Error", 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String readFile(File file) throws IOException {
/* 260 */     return readFile(file, "ASCII");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String readFile(File file, String encoding) throws IOException {
/* 269 */     FileInputStream fin = new FileInputStream(file);
/* 270 */     return readText(fin, encoding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String readText(InputStream in, String encoding) throws IOException {
/* 278 */     InputStreamReader inr = new InputStreamReader(in, encoding);
/* 279 */     BufferedReader br = new BufferedReader(inr);
/* 280 */     StringBuffer sb = new StringBuffer();
/*     */     
/*     */     while (true) {
/* 283 */       String line = br.readLine();
/* 284 */       if (line == null)
/*     */         break; 
/* 286 */       sb.append(line);
/* 287 */       sb.append("\n");
/*     */     } 
/*     */ 
/*     */     
/* 291 */     br.close();
/* 292 */     inr.close();
/* 293 */     in.close();
/*     */     
/* 295 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] readLines(InputStream in, String encoding) throws IOException {
/* 305 */     String str = readText(in, encoding);
/* 306 */     String[] strs = tokenize(str, "\n\r");
/* 307 */     return strs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void centerWindow(Component c, Component par) {
/*     */     Rectangle parRect;
/* 317 */     if (c == null) {
/*     */       return;
/*     */     }
/* 320 */     Rectangle rect = c.getBounds();
/*     */     
/* 322 */     if (par != null && par.isVisible()) {
/*     */       
/* 324 */       parRect = par.getBounds();
/*     */     }
/*     */     else {
/*     */       
/* 328 */       Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
/* 329 */       parRect = new Rectangle(0, 0, scrDim.width, scrDim.height);
/*     */     } 
/*     */     
/* 332 */     int newX = parRect.x + (parRect.width - rect.width) / 2;
/* 333 */     int newY = parRect.y + (parRect.height - rect.height) / 2;
/*     */     
/* 335 */     if (newX < 0)
/* 336 */       newX = 0; 
/* 337 */     if (newY < 0) {
/* 338 */       newY = 0;
/*     */     }
/* 340 */     c.setBounds(newX, newY, rect.width, rect.height);
/*     */   }
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
/*     */   public static String byteArrayToHexString(byte[] bytes) {
/* 354 */     if (bytes == null) {
/* 355 */       return "";
/*     */     }
/* 357 */     StringBuffer buf = new StringBuffer();
/* 358 */     for (int i = 0; i < bytes.length; i++) {
/*     */ 
/*     */       
/* 361 */       byte b = bytes[i];
/* 362 */       buf.append(hexTable[b >> 4 & 0xF]);
/* 363 */       buf.append(hexTable[b & 0xF]);
/*     */     } 
/*     */     
/* 366 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String arrayToCommaSeparatedString(Object[] arr) {
/* 377 */     if (arr == null)
/* 378 */       return ""; 
/* 379 */     StringBuffer buf = new StringBuffer();
/* 380 */     for (int i = 0; i < arr.length; i++) {
/*     */       
/* 382 */       Object val = arr[i];
/* 383 */       if (i > 0) {
/* 384 */         buf.append(", ");
/*     */       }
/* 386 */       if (val == null) {
/*     */         
/* 388 */         buf.append("null");
/*     */ 
/*     */       
/*     */       }
/* 392 */       else if (val.getClass().isArray()) {
/*     */         
/* 394 */         buf.append("[");
/*     */         
/* 396 */         if (val instanceof Object[]) {
/*     */           
/* 398 */           Object[] valObjArr = (Object[])val;
/* 399 */           buf.append(arrayToCommaSeparatedString(valObjArr));
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 404 */           for (int ai = 0; ai < Array.getLength(val); ai++) {
/*     */             
/* 406 */             if (ai > 0) {
/* 407 */               buf.append(", ");
/*     */             }
/* 409 */             buf.append(Array.get(val, ai));
/*     */           } 
/*     */         } 
/* 412 */         buf.append("]");
/*     */       }
/*     */       else {
/*     */         
/* 416 */         buf.append(arr[i]);
/*     */       } 
/*     */     } 
/* 419 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removePrefix(String str, String prefix) {
/* 429 */     if (str == null || prefix == null) {
/* 430 */       return str;
/*     */     }
/* 432 */     if (str.startsWith(prefix)) {
/* 433 */       str = str.substring(prefix.length());
/*     */     }
/* 435 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String ensurePrefix(String str, String prefix) {
/* 445 */     if (str == null || prefix == null) {
/* 446 */       return str;
/*     */     }
/* 448 */     if (!str.startsWith(prefix)) {
/* 449 */       str = String.valueOf(prefix) + str;
/*     */     }
/* 451 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean equals(Object o1, Object o2) {
/* 461 */     if (o1 == o2) {
/* 462 */       return true;
/*     */     }
/* 464 */     if (o1 == null) {
/* 465 */       return false;
/*     */     }
/* 467 */     return o1.equals(o2);
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */