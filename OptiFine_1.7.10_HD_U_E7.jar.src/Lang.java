/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Iterables;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.io.Charsets;
/*     */ import org.apache.commons.io.IOUtils;
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
/*     */ public class Lang
/*     */ {
/*  30 */   private static final Splitter splitter = Splitter.on('=').limit(2);
/*  31 */   private static final Pattern pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void resourcesReloaded() {
/*  38 */     Map localeProperties = brp.getLocaleProperties();
/*     */     
/*  40 */     List<String> listFiles = new ArrayList<String>();
/*  41 */     String PREFIX = "optifine/lang/";
/*  42 */     String EN_US = "en_US";
/*  43 */     String SUFFIX = ".lang";
/*  44 */     listFiles.add(PREFIX + EN_US + SUFFIX);
/*  45 */     if (!(Config.getGameSettings()).aK.equals(EN_US)) {
/*  46 */       listFiles.add(PREFIX + (Config.getGameSettings()).aK + SUFFIX);
/*     */     }
/*  48 */     String[] files = listFiles.<String>toArray(new String[listFiles.size()]);
/*     */     
/*  50 */     loadResources(Config.getDefaultResourcePack(), files, localeProperties);
/*     */     
/*  52 */     bra[] resourcePacks = Config.getResourcePacks();
/*  53 */     for (int i = 0; i < resourcePacks.length; i++) {
/*     */       
/*  55 */       bra rp = resourcePacks[i];
/*  56 */       loadResources(rp, files, localeProperties);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadResources(bra rp, String[] files, Map localeProperties) {
/*     */     try {
/*  68 */       for (int i = 0; i < files.length; i++) {
/*     */         
/*  70 */         String file = files[i];
/*  71 */         bqx loc = new bqx(file);
/*  72 */         if (rp.b(loc)) {
/*     */           
/*  74 */           InputStream in = rp.a(loc);
/*  75 */           if (in != null)
/*     */           {
/*     */             
/*  78 */             loadLocaleData(in, localeProperties); } 
/*     */         } 
/*     */       } 
/*  81 */     } catch (IOException e) {
/*     */       
/*  83 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void loadLocaleData(InputStream is, Map<String, String> localeProperties) throws IOException {
/*  93 */     Iterator<String> it = IOUtils.readLines(is, Charsets.UTF_8).iterator();
/*     */     
/*  95 */     while (it.hasNext()) {
/*     */       
/*  97 */       String line = it.next();
/*  98 */       if (!line.isEmpty() && line.charAt(0) != '#') {
/*     */         
/* 100 */         String[] parts = (String[])Iterables.toArray(splitter.split(line), String.class);
/* 101 */         if (parts != null && parts.length == 2) {
/*     */           
/* 103 */           String key = parts[0];
/* 104 */           String value = pattern.matcher(parts[1]).replaceAll("%$1s");
/* 105 */           localeProperties.put(key, value);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String get(String key) {
/* 116 */     return brp.a(key, new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String get(String key, String def) {
/* 123 */     String str = brp.a(key, new Object[0]);
/*     */     
/* 125 */     if (str == null || str.equals(key)) {
/* 126 */       return def;
/*     */     }
/* 128 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getOn() {
/* 134 */     return brp.a("options.on", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getOff() {
/* 140 */     return brp.a("options.off", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getFast() {
/* 146 */     return brp.a("options.graphics.fast", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getFancy() {
/* 152 */     return brp.a("options.graphics.fancy", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getDefault() {
/* 158 */     return brp.a("generator.default", new Object[0]);
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\Lang.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */