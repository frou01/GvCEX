/*     */ package optifine.xdelta;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
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
/*     */ 
/*     */ public class GDiffPatcher
/*     */ {
/*     */   public GDiffPatcher(File sourceFile, File patchFile, File outputFile) throws IOException, PatchException {
/*  47 */     RandomAccessFileSeekableSource source = new RandomAccessFileSeekableSource(new RandomAccessFile(sourceFile, "r"));
/*  48 */     InputStream patch = new FileInputStream(patchFile);
/*  49 */     OutputStream output = new FileOutputStream(outputFile);
/*     */     try {
/*  51 */       runPatch(source, patch, output);
/*  52 */     } catch (IOException e) {
/*  53 */       throw e;
/*  54 */     } catch (PatchException e) {
/*  55 */       throw e;
/*     */     } finally {
/*  57 */       source.close();
/*  58 */       patch.close();
/*  59 */       output.close();
/*     */     } 
/*     */   }
/*     */   public GDiffPatcher(byte[] source, InputStream patch, OutputStream output) throws IOException, PatchException {
/*  63 */     this(new ByteArraySeekableSource(source), patch, output);
/*     */   }
/*     */   
/*     */   public GDiffPatcher(SeekableSource source, InputStream patch, OutputStream out) throws IOException, PatchException {
/*  67 */     runPatch(source, patch, out);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void runPatch(SeekableSource source, InputStream patch, OutputStream out) throws IOException, PatchException {
/*  72 */     DataOutputStream outOS = new DataOutputStream(out);
/*  73 */     DataInputStream patchIS = new DataInputStream(patch);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  78 */       byte[] buf = new byte[256];
/*     */ 
/*     */ 
/*     */       
/*  82 */       int i = 0;
/*     */ 
/*     */       
/*  85 */       if (patchIS.readUnsignedByte() != 209 || 
/*  86 */         patchIS.readUnsignedByte() != 255 || 
/*  87 */         patchIS.readUnsignedByte() != 209 || 
/*  88 */         patchIS.readUnsignedByte() != 255 || 
/*  89 */         patchIS.readUnsignedByte() != 4) {
/*     */         
/*  91 */         System.err.println("magic string not found, aborting!");
/*     */         return;
/*     */       } 
/*  94 */       i += 5;
/*     */ 
/*     */       
/*  97 */       while (patchIS.available() > 0) {
/*     */         long loffset;
/*  99 */         int command = patchIS.readUnsignedByte();
/* 100 */         int length = 0, offset = 0;
/*     */         
/* 102 */         switch (command) {
/*     */           case 0:
/*     */             continue;
/*     */           case 1:
/* 106 */             append(1, patchIS, outOS); i += 2;
/*     */             continue;
/*     */           case 2:
/* 109 */             append(2, patchIS, outOS); i += 3;
/*     */             continue;
/*     */           case 246:
/* 112 */             append(246, patchIS, outOS); i += 247;
/*     */             continue;
/*     */           case 247:
/* 115 */             length = patchIS.readUnsignedShort();
/* 116 */             append(length, patchIS, outOS); i += length + 3;
/*     */             continue;
/*     */           case 248:
/* 119 */             length = patchIS.readInt();
/* 120 */             append(length, patchIS, outOS); i += length + 5;
/*     */             continue;
/*     */           case 249:
/* 123 */             offset = patchIS.readUnsignedShort();
/* 124 */             length = patchIS.readUnsignedByte();
/* 125 */             copy(offset, length, source, outOS); i += 4;
/*     */             continue;
/*     */           case 250:
/* 128 */             offset = patchIS.readUnsignedShort();
/* 129 */             length = patchIS.readUnsignedShort();
/* 130 */             copy(offset, length, source, outOS); i += 5;
/*     */             continue;
/*     */           case 251:
/* 133 */             offset = patchIS.readUnsignedShort();
/* 134 */             length = patchIS.readInt();
/* 135 */             copy(offset, length, source, outOS); i += 7;
/*     */             continue;
/*     */           case 252:
/* 138 */             offset = patchIS.readInt();
/* 139 */             length = patchIS.readUnsignedByte();
/* 140 */             copy(offset, length, source, outOS); i += 8;
/*     */             continue;
/*     */           case 253:
/* 143 */             offset = patchIS.readInt();
/* 144 */             length = patchIS.readUnsignedShort();
/* 145 */             copy(offset, length, source, outOS); i += 7;
/*     */             continue;
/*     */           case 254:
/* 148 */             offset = patchIS.readInt();
/* 149 */             length = patchIS.readInt();
/* 150 */             copy(offset, length, source, outOS); i += 9;
/*     */             continue;
/*     */           case 255:
/* 153 */             loffset = patchIS.readLong();
/* 154 */             length = patchIS.readInt();
/* 155 */             copy(loffset, length, source, outOS); i += 13;
/*     */             continue;
/*     */         } 
/* 158 */         append(command, patchIS, outOS); i += command + 1;
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 164 */     catch (PatchException e) {
/*     */       
/* 166 */       throw e;
/*     */     }
/*     */     finally {
/*     */       
/* 170 */       outOS.flush();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void copy(long offset, int length, SeekableSource source, OutputStream output) throws IOException, PatchException {
/* 179 */     if (offset + length > source.length())
/*     */     {
/* 181 */       throw new PatchException("truncated source file, aborting");
/*     */     }
/* 183 */     byte[] buf = new byte[256];
/* 184 */     source.seek(offset);
/* 185 */     while (length > 0) {
/* 186 */       int len = (length > 256) ? 256 : length;
/* 187 */       int res = source.read(buf, 0, len);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 197 */       output.write(buf, 0, res);
/* 198 */       length -= res;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static void append(int length, InputStream patch, OutputStream output) throws IOException {
/* 203 */     byte[] buf = new byte[256];
/* 204 */     while (length > 0) {
/* 205 */       int len = (length > 256) ? 256 : length;
/* 206 */       int res = patch.read(buf, 0, len);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 216 */       output.write(buf, 0, res);
/* 217 */       length -= res;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/* 224 */     if (argv.length != 3) {
/* 225 */       System.err.println("usage GDiffPatch source patch output");
/* 226 */       System.err.println("aborting..");
/*     */       return;
/*     */     } 
/*     */     try {
/* 230 */       File sourceFile = new File(argv[0]);
/* 231 */       File patchFile = new File(argv[1]);
/* 232 */       File outputFile = new File(argv[2]);
/*     */       
/* 234 */       if (sourceFile.length() > 2147483647L || 
/* 235 */         patchFile.length() > 2147483647L) {
/* 236 */         System.err.println("source or patch is too large, max length is 2147483647");
/* 237 */         System.err.println("aborting..");
/*     */         return;
/*     */       } 
/* 240 */       GDiffPatcher patcher = new GDiffPatcher(sourceFile, patchFile, outputFile);
/*     */       
/* 242 */       System.out.println("finished patching file");
/*     */     }
/* 244 */     catch (Exception ioe) {
/* 245 */       System.err.println("error while patching: " + ioe);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\xdelta\GDiffPatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */