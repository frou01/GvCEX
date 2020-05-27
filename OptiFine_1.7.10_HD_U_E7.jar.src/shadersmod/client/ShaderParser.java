/*     */ package shadersmod.client;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShaderParser
/*     */ {
/*  15 */   public static Pattern PATTERN_UNIFORM = Pattern.compile("\\s*uniform\\s+\\w+\\s+(\\w+).*");
/*  16 */   public static Pattern PATTERN_ATTRIBUTE = Pattern.compile("\\s*attribute\\s+\\w+\\s+(\\w+).*");
/*  17 */   public static Pattern PATTERN_CONST_INT = Pattern.compile("\\s*const\\s+int\\s+(\\w+)\\s*=\\s*([-+.\\w]+)\\s*;.*");
/*  18 */   public static Pattern PATTERN_CONST_FLOAT = Pattern.compile("\\s*const\\s+float\\s+(\\w+)\\s*=\\s*([-+.\\w]+)\\s*;.*");
/*  19 */   public static Pattern PATTERN_CONST_BOOL = Pattern.compile("\\s*const\\s+bool\\s+(\\w+)\\s*=\\s*(\\w+)\\s*;.*");
/*  20 */   public static Pattern PATTERN_PROPERTY = Pattern.compile("\\s*(/\\*|//)?\\s*([A-Z]+):\\s*(\\w+)\\s*(\\*/.*|\\s*)");
/*     */   
/*  22 */   public static Pattern PATTERN_DEFERRED_FSH = Pattern.compile(".*deferred[0-9]?\\.fsh");
/*  23 */   public static Pattern PATTERN_COMPOSITE_FSH = Pattern.compile(".*composite[0-9]?\\.fsh");
/*  24 */   public static Pattern PATTERN_FINAL_FSH = Pattern.compile(".*final\\.fsh");
/*     */   
/*  26 */   public static Pattern PATTERN_DRAW_BUFFERS = Pattern.compile("[0-7N]*");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ShaderLine parseLine(String line) {
/*  32 */     Matcher mu = PATTERN_UNIFORM.matcher(line);
/*  33 */     if (mu.matches()) {
/*  34 */       return new ShaderLine(1, mu.group(1), "", line);
/*     */     }
/*  36 */     Matcher ma = PATTERN_ATTRIBUTE.matcher(line);
/*  37 */     if (ma.matches()) {
/*  38 */       return new ShaderLine(2, ma.group(1), "", line);
/*     */     }
/*  40 */     Matcher mc = PATTERN_PROPERTY.matcher(line);
/*  41 */     if (mc.matches()) {
/*  42 */       return new ShaderLine(6, mc.group(2), mc.group(3), line);
/*     */     }
/*  44 */     Matcher mci = PATTERN_CONST_INT.matcher(line);
/*  45 */     if (mci.matches()) {
/*  46 */       return new ShaderLine(3, mci.group(1), mci.group(2), line);
/*     */     }
/*  48 */     Matcher mcf = PATTERN_CONST_FLOAT.matcher(line);
/*  49 */     if (mcf.matches()) {
/*  50 */       return new ShaderLine(4, mcf.group(1), mcf.group(2), line);
/*     */     }
/*  52 */     Matcher mcb = PATTERN_CONST_BOOL.matcher(line);
/*  53 */     if (mcb.matches()) {
/*  54 */       return new ShaderLine(5, mcb.group(1), mcb.group(2), line);
/*     */     }
/*  56 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getIndex(String uniform, String prefix, int minIndex, int maxIndex) {
/*  66 */     if (uniform.length() != prefix.length() + 1) {
/*  67 */       return -1;
/*     */     }
/*  69 */     if (!uniform.startsWith(prefix)) {
/*  70 */       return -1;
/*     */     }
/*  72 */     int index = uniform.charAt(prefix.length()) - 48;
/*     */     
/*  74 */     if (index < minIndex || index > maxIndex) {
/*  75 */       return -1;
/*     */     }
/*  77 */     return index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getShadowDepthIndex(String uniform) {
/*  85 */     if (uniform.equals("shadow"))
/*  86 */       return 0; 
/*  87 */     if (uniform.equals("watershadow")) {
/*  88 */       return 1;
/*     */     }
/*  90 */     return getIndex(uniform, "shadowtex", 0, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getShadowColorIndex(String uniform) {
/*  98 */     if (uniform.equals("shadowcolor")) {
/*  99 */       return 0;
/*     */     }
/* 101 */     return getIndex(uniform, "shadowcolor", 0, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getDepthIndex(String uniform) {
/* 110 */     return getIndex(uniform, "depthtex", 0, 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getColorIndex(String uniform) {
/* 119 */     int gauxIndex = getIndex(uniform, "gaux", 1, 4);
/* 120 */     if (gauxIndex > 0) {
/* 121 */       return gauxIndex + 3;
/*     */     }
/* 123 */     return getIndex(uniform, "colortex", 4, 7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isDeferred(String filename) {
/* 130 */     return PATTERN_DEFERRED_FSH.matcher(filename).matches();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isComposite(String filename) {
/* 137 */     return PATTERN_COMPOSITE_FSH.matcher(filename).matches();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFinal(String filename) {
/* 144 */     return PATTERN_FINAL_FSH.matcher(filename).matches();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isValidDrawBuffers(String str) {
/* 151 */     return PATTERN_DRAW_BUFFERS.matcher(str).matches();
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\ShaderParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */