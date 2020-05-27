/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
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
/*     */ public class Json
/*     */ {
/*     */   public static float getFloat(JsonObject obj, String field, float def) {
/*  24 */     JsonElement elem = obj.get(field);
/*  25 */     if (elem == null) {
/*  26 */       return def;
/*     */     }
/*  28 */     return elem.getAsFloat();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getBoolean(JsonObject obj, String field, boolean def) {
/*  39 */     JsonElement elem = obj.get(field);
/*  40 */     if (elem == null) {
/*  41 */       return def;
/*     */     }
/*  43 */     return elem.getAsBoolean();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getString(JsonObject jsonObj, String field) {
/*  48 */     return getString(jsonObj, field, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getString(JsonObject jsonObj, String field, String def) {
/*  53 */     JsonElement jsonElement = jsonObj.get(field);
/*  54 */     if (jsonElement == null) {
/*  55 */       return def;
/*     */     }
/*  57 */     return jsonElement.getAsString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] parseFloatArray(JsonElement jsonElement, int len) {
/*  66 */     return parseFloatArray(jsonElement, len, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] parseFloatArray(JsonElement jsonElement, int len, float[] def) {
/*  75 */     if (jsonElement == null) {
/*  76 */       return def;
/*     */     }
/*  78 */     JsonArray arr = jsonElement.getAsJsonArray();
/*  79 */     if (arr.size() != len) {
/*  80 */       throw new JsonParseException("Wrong array length: " + arr.size() + ", should be: " + len + ", array: " + arr);
/*     */     }
/*  82 */     float[] floatArr = new float[arr.size()];
/*  83 */     for (int i = 0; i < floatArr.length; i++)
/*     */     {
/*  85 */       floatArr[i] = arr.get(i).getAsFloat();
/*     */     }
/*     */     
/*  88 */     return floatArr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] parseIntArray(JsonElement jsonElement, int len) {
/*  97 */     return parseIntArray(jsonElement, len, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] parseIntArray(JsonElement jsonElement, int len, int[] def) {
/* 106 */     if (jsonElement == null) {
/* 107 */       return def;
/*     */     }
/* 109 */     JsonArray arr = jsonElement.getAsJsonArray();
/* 110 */     if (arr.size() != len) {
/* 111 */       throw new JsonParseException("Wrong array length: " + arr.size() + ", should be: " + len + ", array: " + arr);
/*     */     }
/* 113 */     int[] intArr = new int[arr.size()];
/* 114 */     for (int i = 0; i < intArr.length; i++)
/*     */     {
/* 116 */       intArr[i] = arr.get(i).getAsInt();
/*     */     }
/*     */     
/* 119 */     return intArr;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\Json.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */