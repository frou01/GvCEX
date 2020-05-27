/*     */ package shadersmod.client;
/*     */ 
/*     */ import Config;
/*     */ import Lang;
/*     */ import StrUtils;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShaderOptionSwitch
/*     */   extends ShaderOption
/*     */ {
/*  18 */   private static final Pattern PATTERN_DEFINE = Pattern.compile("^\\s*(//)?\\s*#define\\s+([A-Za-z0-9_]+)\\s*(//.*)?$");
/*  19 */   private static final Pattern PATTERN_IFDEF = Pattern.compile("^\\s*#if(n)?def\\s+([A-Za-z0-9_]+)(\\s*)?$");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShaderOptionSwitch(String name, String description, String value, String path) {
/*  25 */     super(name, description, value, new String[] { "false", "true" }, value, path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSourceLine() {
/*  34 */     if (isTrue(getValue())) {
/*  35 */       return "#define " + getName() + " // Shader option ON";
/*     */     }
/*  37 */     return "//#define " + getName() + " // Shader option OFF";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValueText(String val) {
/*  46 */     String valTextRes = super.getValueText(val);
/*     */     
/*  48 */     if (valTextRes != val) {
/*  49 */       return valTextRes;
/*     */     }
/*  51 */     if (isTrue(val)) {
/*  52 */       return Lang.getOn();
/*     */     }
/*  54 */     return Lang.getOff();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValueColor(String val) {
/*  62 */     if (isTrue(val)) {
/*  63 */       return "§a";
/*     */     }
/*  65 */     return "§c";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ShaderOption parseOption(String line, String path) {
/*  74 */     Matcher m = PATTERN_DEFINE.matcher(line);
/*     */     
/*  76 */     if (!m.matches()) {
/*  77 */       return null;
/*     */     }
/*  79 */     String comment = m.group(1);
/*  80 */     String name = m.group(2);
/*  81 */     String description = m.group(3);
/*     */     
/*  83 */     if (name == null || name.length() <= 0) {
/*  84 */       return null;
/*     */     }
/*  86 */     boolean commented = Config.equals(comment, "//");
/*  87 */     boolean enabled = !commented;
/*     */     
/*  89 */     path = StrUtils.removePrefix(path, "/shaders/");
/*     */     
/*  91 */     ShaderOption so = new ShaderOptionSwitch(name, description, String.valueOf(enabled), path);
/*     */     
/*  93 */     return so;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesLine(String line) {
/* 102 */     Matcher m = PATTERN_DEFINE.matcher(line);
/*     */     
/* 104 */     if (!m.matches()) {
/* 105 */       return false;
/*     */     }
/* 107 */     String defName = m.group(2);
/* 108 */     if (!defName.matches(getName())) {
/* 109 */       return false;
/*     */     }
/* 111 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkUsed() {
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsedInLine(String line) {
/* 129 */     Matcher mif = PATTERN_IFDEF.matcher(line);
/* 130 */     if (mif.matches()) {
/*     */ 
/*     */       
/* 133 */       String name = mif.group(2);
/* 134 */       if (name.equals(getName())) {
/* 135 */         return true;
/*     */       }
/*     */     } 
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isTrue(String val) {
/* 145 */     return Boolean.valueOf(val).booleanValue();
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\ShaderOptionSwitch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */