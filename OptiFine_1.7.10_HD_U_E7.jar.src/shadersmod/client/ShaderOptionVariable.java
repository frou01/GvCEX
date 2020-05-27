/*     */ package shadersmod.client;
/*     */ 
/*     */ import Config;
/*     */ import StrUtils;
/*     */ import java.util.Arrays;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShaderOptionVariable
/*     */   extends ShaderOption
/*     */ {
/*  18 */   private static final Pattern PATTERN_VARIABLE = Pattern.compile("^\\s*#define\\s+(\\w+)\\s+(-?[0-9\\.Ff]+|\\w+)\\s*(//.*)?$");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShaderOptionVariable(String name, String description, String value, String[] values, String path) {
/*  24 */     super(name, description, value, values, value, path);
/*     */     
/*  26 */     setVisible(((getValues()).length > 1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSourceLine() {
/*  35 */     return "#define " + getName() + " " + getValue() + " // Shader option " + getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValueText(String val) {
/*  44 */     String prefix = Shaders.translate("prefix." + getName(), "");
/*  45 */     String text = super.getValueText(val);
/*  46 */     String suffix = Shaders.translate("suffix." + getName(), "");
/*  47 */     String textFull = prefix + text + suffix;
/*     */     
/*  49 */     return textFull;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValueColor(String val) {
/*  58 */     String valLow = val.toLowerCase();
/*  59 */     if (valLow.equals("false") || valLow.equals("off")) {
/*  60 */       return "§c";
/*     */     }
/*  62 */     return "§a";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesLine(String line) {
/*  72 */     Matcher m = PATTERN_VARIABLE.matcher(line);
/*     */     
/*  74 */     if (!m.matches()) {
/*  75 */       return false;
/*     */     }
/*  77 */     String defName = m.group(1);
/*  78 */     if (!defName.matches(getName())) {
/*  79 */       return false;
/*     */     }
/*  81 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ShaderOption parseOption(String line, String path) {
/*  91 */     Matcher m = PATTERN_VARIABLE.matcher(line);
/*     */     
/*  93 */     if (!m.matches()) {
/*  94 */       return null;
/*     */     }
/*  96 */     String name = m.group(1);
/*  97 */     String value = m.group(2);
/*  98 */     String description = m.group(3);
/*     */     
/* 100 */     String vals = StrUtils.getSegment(description, "[", "]");
/* 101 */     if (vals != null && vals.length() > 0) {
/* 102 */       description = description.replace(vals, "").trim();
/*     */     }
/* 104 */     String[] values = parseValues(value, vals);
/*     */     
/* 106 */     if (name == null || name.length() <= 0) {
/* 107 */       return null;
/*     */     }
/* 109 */     path = StrUtils.removePrefix(path, "/shaders/");
/*     */     
/* 111 */     ShaderOption so = new ShaderOptionVariable(name, description, value, values, path);
/*     */     
/* 113 */     return so;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] parseValues(String value, String valuesStr) {
/* 123 */     String[] values = { value };
/*     */     
/* 125 */     if (valuesStr == null) {
/* 126 */       return values;
/*     */     }
/* 128 */     valuesStr = valuesStr.trim();
/* 129 */     valuesStr = StrUtils.removePrefix(valuesStr, "[");
/* 130 */     valuesStr = StrUtils.removeSuffix(valuesStr, "]");
/* 131 */     valuesStr = valuesStr.trim();
/* 132 */     if (valuesStr.length() <= 0) {
/* 133 */       return values;
/*     */     }
/* 135 */     String[] parts = Config.tokenize(valuesStr, " ");
/*     */     
/* 137 */     if (parts.length <= 0) {
/* 138 */       return values;
/*     */     }
/* 140 */     if (!Arrays.<String>asList(parts).contains(value)) {
/* 141 */       parts = (String[])Config.addObjectToArray((Object[])parts, value, 0);
/*     */     }
/* 143 */     return parts;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\ShaderOptionVariable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */