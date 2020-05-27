/*     */ package optifine;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ import optifine.xdelta.Delta;
/*     */ import optifine.xdelta.DeltaException;
/*     */ import optifine.xdelta.DiffWriter;
/*     */ import optifine.xdelta.GDiffWriter;
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
/*     */ public class Differ
/*     */ {
/*     */   public static void main(String[] args) throws Exception {
/*  35 */     if (args.length < 3) {
/*     */       
/*  37 */       Utils.dbg("Usage: Differ <base.jar> <mod.jar> <diff.jar>");
/*     */       return;
/*     */     } 
/*  40 */     File baseFile = new File(args[0]);
/*  41 */     File modFile = new File(args[1]);
/*  42 */     File diffFile = new File(args[2]);
/*     */     
/*  44 */     if (baseFile.getName().equals("AUTO")) {
/*  45 */       baseFile = detectBaseFile(modFile);
/*     */     }
/*  47 */     if (!baseFile.exists() || !baseFile.isFile())
/*  48 */       throw new IOException("Base file not found: " + baseFile); 
/*  49 */     if (!modFile.exists() || !modFile.isFile()) {
/*  50 */       throw new IOException("Mod file not found: " + modFile);
/*     */     }
/*  52 */     process(baseFile, modFile, diffFile);
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
/*     */   private static void process(File baseFile, File modFile, File diffFile) throws IOException, DeltaException, NoSuchAlgorithmException {
/*  65 */     ZipFile modZip = new ZipFile(modFile);
/*  66 */     Map<String, String> cfgMap = Patcher.getConfigurationMap(modZip);
/*  67 */     Pattern[] patterns = Patcher.getConfigurationPatterns(cfgMap);
/*     */     
/*  69 */     ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(diffFile));
/*     */     
/*  71 */     ZipFile baseZip = new ZipFile(baseFile);
/*  72 */     Enumeration<? extends ZipEntry> modZipEntries = modZip.entries();
/*  73 */     while (modZipEntries.hasMoreElements()) {
/*     */       
/*  75 */       ZipEntry modZipEntry = modZipEntries.nextElement();
/*  76 */       InputStream in = modZip.getInputStream(modZipEntry);
/*  77 */       byte[] bytes = Utils.readAll(in);
/*  78 */       String name = modZipEntry.getName();
/*     */       
/*  80 */       byte[] bytesDiff = makeDiff(name, bytes, patterns, cfgMap, baseZip);
/*     */       
/*  82 */       if (bytesDiff != bytes) {
/*     */ 
/*     */         
/*  85 */         ZipEntry zipEntryDiff = new ZipEntry("patch/" + name + ".xdelta");
/*  86 */         zipOut.putNextEntry(zipEntryDiff);
/*  87 */         zipOut.write(bytesDiff);
/*  88 */         zipOut.closeEntry();
/*  89 */         Utils.dbg("Delta: " + zipEntryDiff.getName());
/*     */         
/*  91 */         byte[] md5 = HashUtils.getHashMd5(bytes);
/*  92 */         String md5Str = HashUtils.toHexString(md5);
/*  93 */         byte[] bytesMd5Str = md5Str.getBytes("ASCII");
/*  94 */         ZipEntry zipEntryMd5 = new ZipEntry("patch/" + name + ".md5");
/*  95 */         zipOut.putNextEntry(zipEntryMd5);
/*  96 */         zipOut.write(bytesMd5Str);
/*  97 */         zipOut.closeEntry();
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 102 */       ZipEntry zipEntrySame = new ZipEntry(name);
/* 103 */       zipOut.putNextEntry(zipEntrySame);
/* 104 */       zipOut.write(bytes);
/* 105 */       zipOut.closeEntry();
/* 106 */       Utils.dbg("Same: " + zipEntrySame.getName());
/*     */     } 
/*     */ 
/*     */     
/* 110 */     zipOut.close();
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
/*     */   public static byte[] makeDiff(String name, byte[] bytesMod, Pattern[] patterns, Map<String, String> cfgMap, ZipFile zipBase) throws IOException, DeltaException {
/* 124 */     String baseName = Patcher.getPatchBase(name, patterns, cfgMap);
/*     */     
/* 126 */     if (baseName == null) {
/* 127 */       return bytesMod;
/*     */     }
/* 129 */     ZipEntry baseEntry = zipBase.getEntry(baseName);
/*     */     
/* 131 */     if (baseEntry == null) {
/* 132 */       throw new IOException("Base entry not found: " + baseName + " in: " + zipBase.getName());
/*     */     }
/* 134 */     InputStream baseIn = zipBase.getInputStream(baseEntry);
/* 135 */     byte[] baseBytes = Utils.readAll(baseIn);
/*     */     
/* 137 */     ByteArrayInputStream baisTarget = new ByteArrayInputStream(bytesMod);
/* 138 */     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/* 139 */     GDiffWriter gDiffWriter = new GDiffWriter(new DataOutputStream(outputStream));
/* 140 */     Delta.computeDelta(baseBytes, baisTarget, bytesMod.length, (DiffWriter)gDiffWriter);
/* 141 */     gDiffWriter.close();
/*     */     
/* 143 */     return outputStream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static File detectBaseFile(File modFile) throws IOException {
/* 152 */     ZipFile modZip = new ZipFile(modFile);
/* 153 */     String ofVer = Installer.getOptiFineVersion(modZip);
/* 154 */     if (ofVer == null)
/* 155 */       throw new IOException("Version not found"); 
/* 156 */     modZip.close();
/*     */     
/* 158 */     String mcVer = Installer.getMinecraftVersionFromOfVersion(ofVer);
/* 159 */     if (mcVer == null) {
/* 160 */       throw new IOException("Version not found");
/*     */     }
/* 162 */     File dirMc = Utils.getWorkingDirectory();
/* 163 */     File baseFile = new File(dirMc, "versions/" + mcVer + "/" + mcVer + ".jar");
/* 164 */     return baseFile;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\Differ.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */