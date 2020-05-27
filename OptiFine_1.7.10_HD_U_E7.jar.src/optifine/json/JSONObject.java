/*     */ package optifine.json;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ public class JSONObject
/*     */   extends LinkedHashMap
/*     */   implements Map, JSONAware, JSONStreamAware
/*     */ {
/*     */   private static final long serialVersionUID = -503443796854799292L;
/*     */   
/*     */   public static void writeJSONString(Map map, Writer out) throws IOException {
/*  32 */     if (map == null) {
/*  33 */       out.write("null");
/*     */       
/*     */       return;
/*     */     } 
/*  37 */     boolean first = true;
/*  38 */     Iterator<Map.Entry> iter = map.entrySet().iterator();
/*     */     
/*  40 */     out.write(123);
/*  41 */     while (iter.hasNext()) {
/*  42 */       if (first) {
/*  43 */         first = false;
/*     */       } else {
/*  45 */         out.write(44);
/*  46 */       }  Map.Entry entry = iter.next();
/*  47 */       out.write(34);
/*  48 */       out.write(escape(String.valueOf(entry.getKey())));
/*  49 */       out.write(34);
/*  50 */       out.write(58);
/*  51 */       JSONValue.writeJSONString(entry.getValue(), out);
/*     */     } 
/*  53 */     out.write(125);
/*     */   }
/*     */   
/*     */   public void writeJSONString(Writer out) throws IOException {
/*  57 */     writeJSONString(this, out);
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
/*     */   
/*     */   public static String toJSONString(Map map) {
/*  70 */     if (map == null) {
/*  71 */       return "null";
/*     */     }
/*  73 */     StringBuffer sb = new StringBuffer();
/*  74 */     boolean first = true;
/*  75 */     Iterator<Map.Entry> iter = map.entrySet().iterator();
/*     */     
/*  77 */     sb.append('{');
/*  78 */     while (iter.hasNext()) {
/*  79 */       if (first) {
/*  80 */         first = false;
/*     */       } else {
/*  82 */         sb.append(',');
/*     */       } 
/*  84 */       Map.Entry entry = iter.next();
/*  85 */       toJSONString(String.valueOf(entry.getKey()), entry.getValue(), sb);
/*     */     } 
/*  87 */     sb.append('}');
/*  88 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public String toJSONString() {
/*  92 */     return toJSONString(this);
/*     */   }
/*     */   
/*     */   private static String toJSONString(String key, Object value, StringBuffer sb) {
/*  96 */     sb.append('"');
/*  97 */     if (key == null) {
/*  98 */       sb.append("null");
/*     */     } else {
/* 100 */       JSONValue.escape(key, sb);
/* 101 */     }  sb.append('"').append(':');
/*     */     
/* 103 */     sb.append(JSONValue.toJSONString(value));
/*     */     
/* 105 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 109 */     return toJSONString();
/*     */   }
/*     */   
/*     */   public static String toString(String key, Object value) {
/* 113 */     StringBuffer sb = new StringBuffer();
/* 114 */     toJSONString(key, value, sb);
/* 115 */     return sb.toString();
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
/*     */   
/*     */   public static String escape(String s) {
/* 128 */     return JSONValue.escape(s);
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\json\JSONObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */