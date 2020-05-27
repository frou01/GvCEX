/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Enumeration;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
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
/*     */ public class ResUtils
/*     */ {
/*     */   public static String[] collectFiles(String prefix, String suffix) {
/*  33 */     return collectFiles(new String[] { prefix }, new String[] { suffix });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] collectFiles(String[] prefixes, String[] suffixes) {
/*  40 */     Set<String> setPaths = new LinkedHashSet<String>();
/*  41 */     bra[] rps = Config.getResourcePacks();
/*  42 */     for (int i = 0; i < rps.length; i++) {
/*     */       
/*  44 */       bra rp = rps[i];
/*  45 */       String[] ps = collectFiles(rp, prefixes, suffixes, (String[])null);
/*  46 */       setPaths.addAll(Arrays.asList(ps));
/*     */     } 
/*  48 */     String[] paths = setPaths.<String>toArray(new String[setPaths.size()]);
/*  49 */     return paths;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] collectFiles(bra rp, String prefix, String suffix, String[] defaultPaths) {
/*  55 */     return collectFiles(rp, new String[] { prefix }, new String[] { suffix }, defaultPaths);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] collectFiles(bra rp, String[] prefixes, String[] suffixes) {
/*  61 */     return collectFiles(rp, prefixes, suffixes, (String[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] collectFiles(bra rp, String[] prefixes, String[] suffixes, String[] defaultPaths) {
/*  68 */     if (rp instanceof bqp) {
/*  69 */       return collectFilesFixed(rp, defaultPaths);
/*     */     }
/*  71 */     if (!(rp instanceof bqn)) {
/*     */       
/*  73 */       Config.warn("Unknown resource pack type: " + rp);
/*  74 */       return new String[0];
/*     */     } 
/*  76 */     bqn arp = (bqn)rp;
/*     */     
/*  78 */     File tpFile = ResourceUtils.getResourcePackFile(arp);
/*  79 */     if (tpFile == null) {
/*  80 */       return new String[0];
/*     */     }
/*  82 */     if (tpFile.isDirectory()) {
/*  83 */       return collectFilesFolder(tpFile, "", prefixes, suffixes);
/*     */     }
/*  85 */     if (tpFile.isFile()) {
/*  86 */       return collectFilesZIP(tpFile, prefixes, suffixes);
/*     */     }
/*  88 */     Config.warn("Unknown resource pack file: " + tpFile);
/*  89 */     return new String[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] collectFilesFixed(bra rp, String[] paths) {
/*  98 */     if (paths == null) {
/*  99 */       return new String[0];
/*     */     }
/* 101 */     List<String> list = new ArrayList();
/* 102 */     for (int i = 0; i < paths.length; i++) {
/*     */       
/* 104 */       String path = paths[i];
/* 105 */       bqx loc = new bqx(path);
/* 106 */       if (rp.b(loc)) {
/* 107 */         list.add(path);
/*     */       }
/*     */     } 
/* 110 */     String[] pathArr = list.<String>toArray(new String[list.size()]);
/* 111 */     return pathArr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] collectFilesFolder(File tpFile, String basePath, String[] prefixes, String[] suffixes) {
/* 121 */     List<String> list = new ArrayList();
/* 122 */     String prefixAssets = "assets/minecraft/";
/* 123 */     File[] files = tpFile.listFiles();
/* 124 */     if (files == null) {
/* 125 */       return new String[0];
/*     */     }
/* 127 */     for (int i = 0; i < files.length; i++) {
/*     */       
/* 129 */       File file = files[i];
/*     */       
/* 131 */       if (file.isFile()) {
/*     */         
/* 133 */         String name = basePath + file.getName();
/*     */         
/* 135 */         if (name.startsWith(prefixAssets))
/*     */         {
/*     */           
/* 138 */           name = name.substring(prefixAssets.length());
/*     */           
/* 140 */           if (StrUtils.startsWith(name, prefixes))
/*     */           {
/* 142 */             if (StrUtils.endsWith(name, suffixes))
/*     */             {
/*     */               
/* 145 */               list.add(name);
/*     */             }
/*     */           }
/*     */         }
/*     */       
/* 150 */       } else if (file.isDirectory()) {
/*     */         
/* 152 */         String dirPath = basePath + file.getName() + "/";
/* 153 */         String[] arrayOfString = collectFilesFolder(file, dirPath, prefixes, suffixes);
/* 154 */         for (int n = 0; n < arrayOfString.length; n++) {
/*     */           
/* 156 */           String name = arrayOfString[n];
/* 157 */           list.add(name);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 164 */     String[] names = list.<String>toArray(new String[list.size()]);
/* 165 */     return names;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] collectFilesZIP(File tpFile, String[] prefixes, String[] suffixes) {
/* 175 */     List<String> list = new ArrayList();
/* 176 */     String prefixAssets = "assets/minecraft/";
/*     */     
/*     */     try {
/* 179 */       ZipFile zf = new ZipFile(tpFile);
/* 180 */       Enumeration<? extends ZipEntry> en = zf.entries();
/* 181 */       while (en.hasMoreElements()) {
/*     */         
/* 183 */         ZipEntry ze = en.nextElement();
/* 184 */         String name = ze.getName();
/*     */         
/* 186 */         if (!name.startsWith(prefixAssets)) {
/*     */           continue;
/*     */         }
/* 189 */         name = name.substring(prefixAssets.length());
/*     */         
/* 191 */         if (!StrUtils.startsWith(name, prefixes))
/*     */           continue; 
/* 193 */         if (!StrUtils.endsWith(name, suffixes)) {
/*     */           continue;
/*     */         }
/* 196 */         list.add(name);
/*     */       } 
/* 198 */       zf.close();
/*     */       
/* 200 */       String[] names = list.<String>toArray(new String[list.size()]);
/* 201 */       return names;
/*     */     }
/* 203 */     catch (IOException e) {
/*     */       
/* 205 */       e.printStackTrace();
/* 206 */       return new String[0];
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\ResUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */