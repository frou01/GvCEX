/*     */ package optifine;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Enumeration;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ import optifine.xdelta.PatchException;
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
/*     */ public class Patcher
/*     */ {
/*     */   public static final String CONFIG_FILE = "patch.cfg";
/*     */   public static final String PREFIX_PATCH = "patch/";
/*     */   public static final String SUFFIX_DELTA = ".xdelta";
/*     */   public static final String SUFFIX_MD5 = ".md5";
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/*  38 */     if (args.length < 3) {
/*     */       
/*  40 */       Utils.dbg("Usage: Patcher <base.jar> <diff.jar> <mod.jar>");
/*     */       return;
/*     */     } 
/*  43 */     File baseFile = new File(args[0]);
/*  44 */     File diffFile = new File(args[1]);
/*  45 */     File modFile = new File(args[2]);
/*     */     
/*  47 */     if (baseFile.getName().equals("AUTO")) {
/*  48 */       baseFile = Differ.detectBaseFile(diffFile);
/*     */     }
/*  50 */     if (!baseFile.exists() || !baseFile.isFile())
/*  51 */       throw new IOException("Base file not found: " + baseFile); 
/*  52 */     if (!diffFile.exists() || !diffFile.isFile()) {
/*  53 */       throw new IOException("Diff file not found: " + modFile);
/*     */     }
/*  55 */     process(baseFile, diffFile, modFile);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void process(File baseFile, File diffFile, File modFile) throws Exception {
/*  65 */     ZipFile diffZip = new ZipFile(diffFile);
/*  66 */     Map<String, String> cfgMap = getConfigurationMap(diffZip);
/*  67 */     Pattern[] patterns = getConfigurationPatterns(cfgMap);
/*     */     
/*  69 */     ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(modFile));
/*     */     
/*  71 */     ZipFile baseZip = new ZipFile(baseFile);
/*  72 */     ZipResourceProvider zrp = new ZipResourceProvider(baseZip);
/*  73 */     Enumeration<? extends ZipEntry> diffZipEntries = diffZip.entries();
/*  74 */     while (diffZipEntries.hasMoreElements()) {
/*     */       
/*  76 */       ZipEntry diffZipEntry = diffZipEntries.nextElement();
/*  77 */       InputStream in = diffZip.getInputStream(diffZipEntry);
/*  78 */       byte[] bytes = Utils.readAll(in);
/*  79 */       String name = diffZipEntry.getName();
/*     */       
/*  81 */       if (name.startsWith("patch/") && name.endsWith(".xdelta")) {
/*     */ 
/*     */         
/*  84 */         name = name.substring("patch/".length());
/*  85 */         name = name.substring(0, name.length() - ".xdelta".length());
/*     */         
/*  87 */         byte[] bytesMod = applyPatch(name, bytes, patterns, cfgMap, zrp);
/*     */         
/*  89 */         String nameMd5 = "patch/" + name + ".md5";
/*  90 */         ZipEntry diffZipEntryMd5 = diffZip.getEntry(nameMd5);
/*  91 */         if (diffZipEntryMd5 != null) {
/*     */           
/*  93 */           byte[] md5 = Utils.readAll(diffZip.getInputStream(diffZipEntryMd5));
/*  94 */           String md5Str = new String(md5, "ASCII");
/*  95 */           byte[] md5Mod = HashUtils.getHashMd5(bytesMod);
/*  96 */           String md5ModStr = HashUtils.toHexString(md5Mod);
/*  97 */           if (!md5Str.equals(md5ModStr)) {
/*  98 */             throw new Exception("MD5 not matching, name: " + name + ", saved: " + md5Str + ", patched: " + md5ModStr);
/*     */           }
/*     */         } 
/* 101 */         ZipEntry zipEntryMod = new ZipEntry(name);
/* 102 */         zipOut.putNextEntry(zipEntryMod);
/* 103 */         zipOut.write(bytesMod);
/* 104 */         zipOut.closeEntry();
/* 105 */         Utils.dbg("Mod: " + name);
/*     */         
/*     */         continue;
/*     */       } 
/* 109 */       if (name.startsWith("patch/") && name.endsWith(".md5")) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 115 */       ZipEntry zipEntrySame = new ZipEntry(name);
/* 116 */       zipOut.putNextEntry(zipEntrySame);
/* 117 */       zipOut.write(bytes);
/* 118 */       zipOut.closeEntry();
/* 119 */       Utils.dbg("Same: " + zipEntrySame.getName());
/*     */     } 
/*     */     
/* 122 */     zipOut.close();
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
/*     */   public static byte[] applyPatch(String name, byte[] bytesDiff, Pattern[] patterns, Map<String, String> cfgMap, IResourceProvider resourceProvider) throws IOException, PatchException {
/* 135 */     name = Utils.removePrefix(name, "/");
/*     */     
/* 137 */     String baseName = getPatchBase(name, patterns, cfgMap);
/* 138 */     if (baseName == null) {
/* 139 */       throw new IOException("No patch base, name: " + name + ", patterns: " + Utils.arrayToCommaSeparatedString(patterns));
/*     */     }
/* 141 */     InputStream baseIn = resourceProvider.getResourceStream(baseName);
/* 142 */     if (baseIn == null) {
/* 143 */       throw new IOException("Base resource not found: " + baseName);
/*     */     }
/* 145 */     byte[] baseBytes = Utils.readAll(baseIn);
/*     */     
/* 147 */     InputStream patchStream = new ByteArrayInputStream(bytesDiff);
/* 148 */     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/*     */     
/* 150 */     outputStream.close();
/*     */     
/* 152 */     return outputStream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Pattern[] getConfigurationPatterns(Map<String, String> cfgMap) {
/* 161 */     String[] cfgKeys = (String[])cfgMap.keySet().toArray((Object[])new String[0]);
/* 162 */     Pattern[] patterns = new Pattern[cfgKeys.length];
/* 163 */     for (int i = 0; i < cfgKeys.length; i++) {
/*     */       
/* 165 */       String key = cfgKeys[i];
/* 166 */       patterns[i] = Pattern.compile(key);
/*     */     } 
/* 168 */     return patterns;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<String, String> getConfigurationMap(ZipFile modZip) throws IOException {
/* 178 */     Map<String, String> cfgMap = new LinkedHashMap<String, String>();
/*     */     
/* 180 */     if (modZip == null) {
/* 181 */       return cfgMap;
/*     */     }
/* 183 */     ZipEntry entryPatch = modZip.getEntry("patch.cfg");
/* 184 */     if (entryPatch == null) {
/* 185 */       return cfgMap;
/*     */     }
/* 187 */     InputStream inPatch = modZip.getInputStream(entryPatch);
/* 188 */     String[] lines = Utils.readLines(inPatch, "ASCII");
/* 189 */     inPatch.close();
/* 190 */     for (int i = 0; i < lines.length; i++) {
/*     */       
/* 192 */       String line = lines[i].trim();
/*     */       
/* 194 */       if (!line.startsWith("#") && line.length() > 0) {
/*     */ 
/*     */         
/* 197 */         String[] parts = Utils.tokenize(line, "=");
/* 198 */         if (parts.length != 2) {
/* 199 */           throw new IOException("Invalid patch configuration: " + line);
/*     */         }
/* 201 */         String key = parts[0].trim();
/* 202 */         String val = parts[1].trim();
/*     */         
/* 204 */         cfgMap.put(key, val);
/*     */       } 
/*     */     } 
/* 207 */     return cfgMap;
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
/*     */   public static String getPatchBase(String name, Pattern[] patterns, Map<String, String> cfgMap) {
/* 219 */     name = Utils.removePrefix(name, "/");
/*     */     
/* 221 */     for (int i = 0; i < patterns.length; ) {
/*     */       
/* 223 */       Pattern pattern = patterns[i];
/* 224 */       Matcher matcher = pattern.matcher(name);
/* 225 */       if (!matcher.matches()) {
/*     */         i++; continue;
/* 227 */       }  String base = cfgMap.get(pattern.pattern());
/*     */       
/* 229 */       if (base != null && base.trim().equals("*")) {
/* 230 */         return name;
/*     */       }
/* 232 */       return base;
/*     */     } 
/*     */     
/* 235 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\Patcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */