/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
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
/*     */ public class FontUtils
/*     */ {
/*     */   public static Properties readFontProperties(bqx locationFontTexture) {
/*  20 */     String fontFileName = locationFontTexture.a();
/*     */     
/*  22 */     Properties props = new Properties();
/*     */     
/*  24 */     String suffix = ".png";
/*  25 */     if (!fontFileName.endsWith(suffix))
/*  26 */       return props; 
/*  27 */     String fileName = fontFileName.substring(0, fontFileName.length() - suffix.length()) + ".properties";
/*     */ 
/*     */     
/*     */     try {
/*  31 */       bqx locProp = new bqx(locationFontTexture.b(), fileName);
/*  32 */       InputStream in = Config.getResourceStream(Config.getResourceManager(), locProp);
/*  33 */       if (in == null)
/*  34 */         return props; 
/*  35 */       Config.log("Loading " + fileName);
/*     */       
/*  37 */       props.load(in);
/*     */     }
/*  39 */     catch (FileNotFoundException e) {
/*     */ 
/*     */     
/*     */     }
/*  43 */     catch (IOException e) {
/*     */       
/*  45 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  48 */     return props;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void readCustomCharWidths(Properties props, float[] charWidth) {
/*  53 */     Set keySet = props.keySet();
/*  54 */     for (Iterator<String> iter = keySet.iterator(); iter.hasNext(); ) {
/*     */       
/*  56 */       String key = iter.next();
/*     */       
/*  58 */       String prefix = "width.";
/*  59 */       if (key.startsWith(prefix)) {
/*     */         
/*  61 */         String numStr = key.substring(prefix.length());
/*  62 */         int num = Config.parseInt(numStr, -1);
/*  63 */         if (num >= 0 && num < charWidth.length) {
/*     */           
/*  65 */           String value = props.getProperty(key);
/*  66 */           float width = Config.parseFloat(value, -1.0F);
/*  67 */           if (width >= 0.0F) {
/*  68 */             charWidth[num] = width;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static float readFloat(Properties props, String key, float defOffset) {
/*  76 */     String str = props.getProperty(key);
/*  77 */     if (str == null) {
/*  78 */       return defOffset;
/*     */     }
/*  80 */     float offset = Config.parseFloat(str, Float.MIN_VALUE);
/*  81 */     if (offset == Float.MIN_VALUE) {
/*     */       
/*  83 */       Config.warn("Invalid value for " + key + ": " + str);
/*  84 */       return defOffset;
/*     */     } 
/*     */     
/*  87 */     return offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean readBoolean(Properties props, String key, boolean defVal) {
/*  92 */     String str = props.getProperty(key);
/*  93 */     if (str == null) {
/*  94 */       return defVal;
/*     */     }
/*  96 */     String strLow = str.toLowerCase().trim();
/*  97 */     if (strLow.equals("true") || strLow.equals("on"))
/*  98 */       return true; 
/*  99 */     if (strLow.equals("false") || strLow.equals("off")) {
/* 100 */       return false;
/*     */     }
/* 102 */     Config.warn("Invalid value for " + key + ": " + str);
/* 103 */     return defVal;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static bqx getHdFontLocation(bqx fontLoc) {
/* 109 */     if (!Config.isCustomFonts()) {
/* 110 */       return fontLoc;
/*     */     }
/* 112 */     if (fontLoc == null) {
/* 113 */       return fontLoc;
/*     */     }
/* 115 */     if (!Config.isMinecraftThread()) {
/* 116 */       return fontLoc;
/*     */     }
/* 118 */     String fontName = fontLoc.a();
/* 119 */     String texturesStr = "textures/";
/* 120 */     String mcpatcherStr = "mcpatcher/";
/* 121 */     if (!fontName.startsWith(texturesStr)) {
/* 122 */       return fontLoc;
/*     */     }
/* 124 */     fontName = fontName.substring(texturesStr.length());
/* 125 */     fontName = mcpatcherStr + fontName;
/*     */     
/* 127 */     bqx fontLocHD = new bqx(fontLoc.b(), fontName);
/* 128 */     if (Config.hasResource(Config.getResourceManager(), fontLocHD)) {
/* 129 */       return fontLocHD;
/*     */     }
/* 131 */     return fontLoc;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\FontUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */