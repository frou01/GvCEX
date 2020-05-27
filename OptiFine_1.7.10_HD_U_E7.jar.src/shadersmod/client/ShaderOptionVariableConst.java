/*    */ package shadersmod.client;
/*    */ 
/*    */ import StrUtils;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShaderOptionVariableConst
/*    */   extends ShaderOptionVariable
/*    */ {
/* 14 */   private String type = null;
/*    */   
/* 16 */   private static final Pattern PATTERN_CONST = Pattern.compile("^\\s*const\\s*(float|int)\\s*([A-Za-z0-9_]+)\\s*=\\s*(-?[0-9\\.]+f?F?)\\s*;\\s*(//.*)?$");
/*    */ 
/*    */ 
/*    */   
/*    */   public ShaderOptionVariableConst(String name, String type, String description, String value, String[] values, String path) {
/* 21 */     super(name, description, value, values, path);
/* 22 */     this.type = type;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSourceLine() {
/* 27 */     return "const " + this.type + " " + getName() + " = " + getValue() + "; // Shader option " + getValue();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matchesLine(String line) {
/* 36 */     Matcher m = PATTERN_CONST.matcher(line);
/*    */     
/* 38 */     if (!m.matches()) {
/* 39 */       return false;
/*    */     }
/* 41 */     String defName = m.group(2);
/* 42 */     if (!defName.matches(getName())) {
/* 43 */       return false;
/*    */     }
/* 45 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ShaderOption parseOption(String line, String path) {
/* 55 */     Matcher m = PATTERN_CONST.matcher(line);
/*    */     
/* 57 */     if (!m.matches()) {
/* 58 */       return null;
/*    */     }
/* 60 */     String type = m.group(1);
/* 61 */     String name = m.group(2);
/* 62 */     String value = m.group(3);
/* 63 */     String description = m.group(4);
/*    */     
/* 65 */     String vals = StrUtils.getSegment(description, "[", "]");
/* 66 */     if (vals != null && vals.length() > 0) {
/* 67 */       description = description.replace(vals, "").trim();
/*    */     }
/* 69 */     String[] values = parseValues(value, vals);
/*    */     
/* 71 */     if (name == null || name.length() <= 0) {
/* 72 */       return null;
/*    */     }
/* 74 */     path = StrUtils.removePrefix(path, "/shaders/");
/*    */     
/* 76 */     ShaderOption so = new ShaderOptionVariableConst(name, type, description, value, values, path);
/*    */     
/* 78 */     return so;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\ShaderOptionVariableConst.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */