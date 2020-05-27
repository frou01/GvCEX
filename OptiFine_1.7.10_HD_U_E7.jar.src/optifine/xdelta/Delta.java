/*     */ package optifine.xdelta;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PushbackInputStream;
/*     */ import java.io.RandomAccessFile;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Delta
/*     */ {
/*     */   public static final int S = 16;
/*     */   public static final boolean debug = false;
/*     */   public static final int buff_size = 1024;
/*     */   
/*     */   public static void computeDelta(SeekableSource source, InputStream targetIS, int targetLength, DiffWriter output) throws IOException, DeltaException {
/*  51 */     int sourceLength = (int)source.length();
/*     */     
/*  53 */     Checksum checksum = new Checksum();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  61 */     checksum.generateChecksums(new SeekableSourceInputStream(source), sourceLength);
/*  62 */     source.seek(0L);
/*     */     
/*  64 */     PushbackInputStream target = 
/*  65 */       new PushbackInputStream(
/*  66 */         new BufferedInputStream(targetIS), 
/*  67 */         1024);
/*     */     
/*  69 */     boolean done = false;
/*  70 */     byte[] buf = new byte[16];
/*  71 */     long hashf = 0L;
/*  72 */     byte[] b = new byte[1];
/*  73 */     byte[] sourcebyte = new byte[16];
/*     */     
/*  75 */     if (targetLength <= 16 || sourceLength <= 16) {
/*     */       int readBytes;
/*     */       
/*  78 */       while ((readBytes = target.read(buf)) >= 0) {
/*  79 */         for (int i = 0; i < readBytes; i++) {
/*  80 */           output.addData(buf[i]);
/*     */         }
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  87 */     int bytesRead = target.read(buf, 0, 16);
/*  88 */     int targetidx = bytesRead;
/*     */     
/*  90 */     hashf = Checksum.queryChecksum(buf, 16);
/*     */ 
/*     */ 
/*     */     
/*  94 */     long alternativehashf = hashf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     boolean sourceOutofBytes = false;
/*     */     
/* 103 */     while (!done) {
/*     */       
/* 105 */       int index = checksum.findChecksumIndex(hashf);
/* 106 */       if (index != -1) {
/*     */         
/* 108 */         boolean match = true;
/* 109 */         int offset = index * 16;
/* 110 */         int length = 15;
/* 111 */         source.seek(offset);
/*     */ 
/*     */         
/* 114 */         if (!sourceOutofBytes && 
/* 115 */           source.read(sourcebyte, 0, 16) != -1) {
/* 116 */           for (int i = 0; i < 16; i++) {
/* 117 */             if (sourcebyte[i] != buf[i]) {
/* 118 */               match = false;
/*     */             }
/*     */           } 
/*     */         } else {
/* 122 */           sourceOutofBytes = true;
/*     */         } 
/*     */         
/* 125 */         if ((match & (sourceOutofBytes ? 0 : 1)) != 0) {
/*     */ 
/*     */           
/* 128 */           long start = System.currentTimeMillis();
/*     */           
/* 130 */           boolean ok = true;
/* 131 */           byte[] sourceBuff = new byte[1024];
/* 132 */           byte[] targetBuff = new byte[1024];
/* 133 */           int source_idx = 0;
/* 134 */           int target_idx = 0;
/* 135 */           int tCount = 0;
/*     */           
/*     */           do {
/* 138 */             source_idx = source.read(sourceBuff, 0, 1024);
/*     */             
/* 140 */             if (source_idx == -1) {
/*     */               
/* 142 */               sourceOutofBytes = true;
/*     */ 
/*     */               
/*     */               break;
/*     */             } 
/*     */             
/* 148 */             target_idx = target.read(targetBuff, 0, source_idx);
/*     */             
/* 150 */             if (target_idx == -1) {
/*     */               break;
/*     */             }
/*     */ 
/*     */ 
/*     */             
/* 156 */             int read_idx = Math.min(source_idx, target_idx);
/* 157 */             int i = 0;
/*     */             do {
/* 159 */               targetidx++;
/* 160 */               length++;
/* 161 */               ok = (sourceBuff[i] == targetBuff[i]);
/* 162 */               i++;
/* 163 */               if (ok)
/* 164 */                 continue;  b[0] = targetBuff[i - 1];
/*     */               
/* 166 */               if (target_idx == -1)
/* 167 */                 continue;  target.unread(
/* 168 */                   targetBuff, 
/* 169 */                   i, 
/* 170 */                   target_idx - i);
/*     */             
/*     */             }
/* 173 */             while (i < read_idx && ok);
/* 174 */             b[0] = targetBuff[i - 1];
/*     */           }
/* 176 */           while (ok && targetLength - targetidx > 0);
/*     */ 
/*     */ 
/*     */           
/* 180 */           output.addCopy(offset, length);
/*     */           
/* 182 */           if (targetLength - targetidx <= 15) {
/*     */ 
/*     */ 
/*     */             
/* 186 */             buf[0] = b[0];
/* 187 */             int remaining = targetLength - targetidx;
/* 188 */             int readStatus = target.read(buf, 1, remaining);
/* 189 */             targetidx += remaining;
/* 190 */             for (int i = 0; i < remaining + 1; i++)
/* 191 */               output.addData(buf[i]); 
/* 192 */             done = true; continue;
/*     */           } 
/* 194 */           buf[0] = b[0];
/* 195 */           target.read(buf, 1, 15);
/* 196 */           targetidx += 15;
/* 197 */           alternativehashf = 
/* 198 */             hashf = Checksum.queryChecksum(buf, 16);
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/*     */       
/* 204 */       if (targetLength - targetidx > 0) {
/*     */ 
/*     */         
/* 207 */         target.read(b, 0, 1);
/* 208 */         targetidx++;
/*     */ 
/*     */         
/* 211 */         output.addData(buf[0]);
/*     */         
/* 213 */         alternativehashf = 
/* 214 */           Checksum.incrementChecksum(alternativehashf, buf[0], b[0]);
/*     */         
/* 216 */         for (int j = 0; j < 15; j++)
/* 217 */           buf[j] = buf[j + 1]; 
/* 218 */         buf[15] = b[0];
/* 219 */         hashf = Checksum.queryChecksum(buf, 16);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 229 */       for (int ix = 0; ix < 16; ix++)
/* 230 */         output.addData(buf[ix]); 
/* 231 */       done = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void computeDelta(byte[] source, InputStream targetIS, int targetLength, DiffWriter output) throws IOException, DeltaException {
/* 239 */     computeDelta(new ByteArraySeekableSource(source), targetIS, targetLength, output);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void computeDelta(File sourceFile, File targetFile, DiffWriter output) throws IOException, DeltaException {
/* 246 */     int targetLength = (int)targetFile.length();
/* 247 */     SeekableSource source = new RandomAccessFileSeekableSource(new RandomAccessFile(sourceFile, "r"));
/* 248 */     InputStream targetIS = new FileInputStream(targetFile);
/*     */     try {
/* 250 */       computeDelta(source, targetIS, targetLength, output);
/* 251 */     } catch (IOException e) {
/* 252 */       throw e;
/* 253 */     } catch (DeltaException e) {
/* 254 */       throw e;
/*     */     } finally {
/* 256 */       output.flush();
/* 257 */       source.close();
/* 258 */       targetIS.close();
/* 259 */       output.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/* 265 */     Delta delta = new Delta();
/*     */     
/* 267 */     if (argv.length != 3) {
/* 268 */       System.err.println("usage Delta [-d] source target [output]");
/* 269 */       System.err.println(
/* 270 */           "either -d or an output filename must be specified.");
/* 271 */       System.err.println("aborting..");
/*     */       return;
/*     */     } 
/*     */     try {
/* 275 */       DiffWriter output = null;
/* 276 */       File sourceFile = null;
/* 277 */       File targetFile = null;
/* 278 */       if (argv[0].equals("-d")) {
/* 279 */         sourceFile = new File(argv[1]);
/* 280 */         targetFile = new File(argv[2]);
/* 281 */         output = new DebugDiffWriter();
/*     */       } else {
/* 283 */         sourceFile = new File(argv[0]);
/* 284 */         targetFile = new File(argv[1]);
/* 285 */         output = 
/* 286 */           new GDiffWriter(
/* 287 */             new DataOutputStream(
/* 288 */               new BufferedOutputStream(
/* 289 */                 new FileOutputStream(new File(argv[2])))));
/*     */       } 
/*     */       
/* 292 */       if (sourceFile.length() > 2147483647L || 
/* 293 */         targetFile.length() > 2147483647L) {
/* 294 */         System.err.println(
/* 295 */             "source or target is too large, max length is 2147483647");
/*     */         
/* 297 */         System.err.println("aborting..");
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 303 */       computeDelta(sourceFile, targetFile, output);
/*     */       
/* 305 */       output.flush();
/* 306 */       output.close();
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 311 */     catch (Exception e) {
/* 312 */       System.err.println("error while generating delta: " + e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\xdelta\Delta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */