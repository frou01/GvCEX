/*    */ package shadersmod.uniform;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LegacyUniforms
/*    */ {
/* 14 */   private static Map<String, Number> map = new HashMap<String, Number>();
/*    */   
/* 16 */   private static Map<String, String> mapKeysX = new HashMap<String, String>();
/* 17 */   private static Map<String, String> mapKeysY = new HashMap<String, String>();
/* 18 */   private static Map<String, String> mapKeysZ = new HashMap<String, String>();
/* 19 */   private static Map<String, String> mapKeysR = new HashMap<String, String>();
/* 20 */   private static Map<String, String> mapKeysG = new HashMap<String, String>();
/* 21 */   private static Map<String, String> mapKeysB = new HashMap<String, String>();
/*    */ 
/*    */ 
/*    */   
/*    */   public static Number getNumber(String name) {
/* 26 */     return map.get(name);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setFloat(String name, float val) {
/* 32 */     map.put(name, Float.valueOf(val));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setInt(String name, int val) {
/* 38 */     map.put(name, Integer.valueOf(val));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setIntXy(String name, int x, int y) {
/* 44 */     setInt(getCompoundKey(name, "x", mapKeysX), x);
/* 45 */     setInt(getCompoundKey(name, "y", mapKeysY), y);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setFloatXyz(String name, float x, float y, float z) {
/* 51 */     setFloat(getCompoundKey(name, "x", mapKeysX), x);
/* 52 */     setFloat(getCompoundKey(name, "y", mapKeysY), y);
/* 53 */     setFloat(getCompoundKey(name, "z", mapKeysZ), z);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setFloatRgb(String name, float x, float y, float z) {
/* 59 */     setFloat(getCompoundKey(name, "r", mapKeysR), x);
/* 60 */     setFloat(getCompoundKey(name, "g", mapKeysG), y);
/* 61 */     setFloat(getCompoundKey(name, "b", mapKeysB), z);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static String getCompoundKey(String name, String suffix, Map<String, String> mapKeys) {
/* 68 */     String key = mapKeys.get(name);
/* 69 */     if (key != null) {
/* 70 */       return key;
/*    */     }
/* 72 */     key = name + "." + suffix;
/* 73 */     mapKeys.put(name, key);
/*    */     
/* 75 */     return key;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void reset() {
/* 81 */     map.clear();
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmo\\uniform\LegacyUniforms.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */