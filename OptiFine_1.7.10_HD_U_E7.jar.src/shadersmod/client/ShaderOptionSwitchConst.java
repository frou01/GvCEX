/*    */ package shadersmod.client;
/*    */ 
/*    */ import StrUtils;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShaderOptionSwitchConst
/*    */   extends ShaderOptionSwitch
/*    */ {
/* 13 */   private static final Pattern PATTERN_CONST = Pattern.compile("^\\s*const\\s*bool\\s*([A-Za-z0-9_]+)\\s*=\\s*(true|false)\\s*;\\s*(//.*)?$");
/*    */ 
/*    */ 
/*    */   
/*    */   public ShaderOptionSwitchConst(String name, String description, String value, String path) {
/* 18 */     super(name, description, value, path);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSourceLine() {
/* 26 */     return "const bool " + getName() + " = " + getValue() + "; // Shader option " + getValue();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ShaderOption parseOption(String line, String path) {
/* 35 */     Matcher m = PATTERN_CONST.matcher(line);
/*    */     
/* 37 */     if (!m.matches()) {
/* 38 */       return null;
/*    */     }
/* 40 */     String name = m.group(1);
/* 41 */     String value = m.group(2);
/* 42 */     String description = m.group(3);
/*    */     
/* 44 */     if (name == null || name.length() <= 0) {
/* 45 */       return null;
/*    */     }
/* 47 */     path = StrUtils.removePrefix(path, "/shaders/");
/*    */     
/* 49 */     ShaderOption so = new ShaderOptionSwitchConst(name, description, value, path);
/*    */     
/* 51 */     so.setVisible(false);
/*    */     
/* 53 */     return so;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matchesLine(String line) {
/* 62 */     Matcher m = PATTERN_CONST.matcher(line);
/*    */     
/* 64 */     if (!m.matches()) {
/* 65 */       return false;
/*    */     }
/* 67 */     String defName = m.group(1);
/* 68 */     if (!defName.matches(getName())) {
/* 69 */       return false;
/*    */     }
/* 71 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean checkUsed() {
/* 80 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\ShaderOptionSwitchConst.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */