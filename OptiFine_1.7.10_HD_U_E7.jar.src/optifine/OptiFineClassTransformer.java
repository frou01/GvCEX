/*     */ package optifine;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import net.minecraft.launchwrapper.IClassTransformer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OptiFineClassTransformer
/*     */   implements IClassTransformer, IResourceProvider
/*     */ {
/*  24 */   private ZipFile ofZipFile = null;
/*  25 */   private Map<String, String> patchMap = null;
/*  26 */   private Pattern[] patterns = null;
/*     */   
/*  28 */   public static OptiFineClassTransformer instance = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OptiFineClassTransformer() {
/*  35 */     instance = this;
/*     */ 
/*     */     
/*     */     try {
/*  39 */       dbg("OptiFine ClassTransformer");
/*  40 */       URL url = OptiFineClassTransformer.class.getProtectionDomain().getCodeSource().getLocation();
/*  41 */       URI uri = url.toURI();
/*  42 */       File file = new File(uri);
/*     */       
/*  44 */       this.ofZipFile = new ZipFile(file);
/*  45 */       dbg("OptiFine ZIP file: " + file);
/*     */       
/*  47 */       this.patchMap = Patcher.getConfigurationMap(this.ofZipFile);
/*  48 */       this.patterns = Patcher.getConfigurationPatterns(this.patchMap);
/*     */     }
/*  50 */     catch (Exception e) {
/*     */       
/*  52 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  55 */     if (this.ofZipFile == null) {
/*     */       
/*  57 */       dbg("*** Can not find the OptiFine JAR in the classpath ***");
/*  58 */       dbg("*** OptiFine will not be loaded! ***");
/*     */     } 
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
/*     */   public byte[] transform(String name, String transformedName, byte[] bytes) {
/*  71 */     String nameClass = String.valueOf(name) + ".class";
/*  72 */     byte[] ofBytes = getOptiFineResource(nameClass);
/*  73 */     if (ofBytes != null)
/*     */     {
/*     */       
/*  76 */       return ofBytes;
/*     */     }
/*     */     
/*  79 */     return bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getResourceStream(String path) {
/*  88 */     path = Utils.ensurePrefix(path, "/");
/*     */     
/*  90 */     return OptiFineClassTransformer.class.getResourceAsStream(path);
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
/*     */   public synchronized byte[] getOptiFineResource(String name) {
/* 102 */     name = Utils.removePrefix(name, "/");
/*     */     
/* 104 */     byte[] bytes = getOptiFineResourceZip(name);
/* 105 */     if (bytes != null) {
/* 106 */       return bytes;
/*     */     }
/* 108 */     bytes = getOptiFineResourcePatched(name, this);
/* 109 */     if (bytes != null) {
/* 110 */       return bytes;
/*     */     }
/* 112 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized byte[] getOptiFineResourceZip(String name) {
/* 121 */     if (this.ofZipFile == null) {
/* 122 */       return null;
/*     */     }
/* 124 */     name = Utils.removePrefix(name, "/");
/*     */     
/* 126 */     ZipEntry ze = this.ofZipFile.getEntry(name);
/* 127 */     if (ze == null) {
/* 128 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 132 */       InputStream in = this.ofZipFile.getInputStream(ze);
/*     */       
/* 134 */       byte[] bytes = readAll(in);
/*     */       
/* 136 */       in.close();
/*     */       
/* 138 */       if (bytes.length != ze.getSize()) {
/*     */         
/* 140 */         dbg("Invalid size, name: " + name + ", zip: " + ze.getSize() + ", stream: " + bytes.length);
/* 141 */         return null;
/*     */       } 
/*     */       
/* 144 */       return bytes;
/*     */     }
/* 146 */     catch (IOException e) {
/*     */       
/* 148 */       e.printStackTrace();
/*     */       
/* 150 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized byte[] getOptiFineResourcePatched(String name, IResourceProvider resourceProvider) {
/* 159 */     if (this.patterns == null || this.patchMap == null || resourceProvider == null) {
/* 160 */       return null;
/*     */     }
/* 162 */     name = Utils.removePrefix(name, "/");
/*     */     
/* 164 */     String patchName = "patch/" + name + ".xdelta";
/* 165 */     byte[] bytes = getOptiFineResourceZip(patchName);
/* 166 */     if (bytes == null) {
/* 167 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 173 */       byte[] bytesPatched = Patcher.applyPatch(name, bytes, this.patterns, this.patchMap, resourceProvider);
/*     */ 
/*     */       
/* 176 */       String fullMd5Name = "patch/" + name + ".md5";
/* 177 */       byte[] bytesMd5 = getOptiFineResourceZip(fullMd5Name);
/* 178 */       if (bytesMd5 != null) {
/*     */         
/* 180 */         String md5Str = new String(bytesMd5, "ASCII");
/* 181 */         byte[] md5Mod = HashUtils.getHashMd5(bytesPatched);
/* 182 */         String md5ModStr = HashUtils.toHexString(md5Mod);
/* 183 */         if (!md5Str.equals(md5ModStr)) {
/* 184 */           throw new IOException("MD5 not matching, name: " + name + ", saved: " + md5Str + ", patched: " + md5ModStr);
/*     */         }
/*     */       } 
/* 187 */       return bytesPatched;
/*     */     }
/* 189 */     catch (Exception e) {
/*     */       
/* 191 */       e.printStackTrace();
/* 192 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] readAll(InputStream is) throws IOException {
/* 201 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 202 */     byte[] buf = new byte[1024];
/*     */     
/*     */     while (true) {
/* 205 */       int len = is.read(buf);
/* 206 */       if (len < 0)
/*     */         break; 
/* 208 */       baos.write(buf, 0, len);
/*     */     } 
/*     */ 
/*     */     
/* 212 */     is.close();
/*     */     
/* 214 */     byte[] bytes = baos.toByteArray();
/*     */     
/* 216 */     return bytes;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void dbg(String str) {
/* 221 */     System.out.println(str);
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\OptiFineClassTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */