/*     */ package optifine.json;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.io.Writer;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JSONValue
/*     */ {
/*     */   public static Object parse(Reader in) {
/*     */     try {
/*  39 */       JSONParser parser = new JSONParser();
/*  40 */       return parser.parse(in);
/*     */     }
/*  42 */     catch (Exception e) {
/*  43 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Object parse(String s) {
/*  48 */     StringReader in = new StringReader(s);
/*  49 */     return parse(in);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object parseWithException(Reader in) throws IOException, ParseException {
/*  70 */     JSONParser parser = new JSONParser();
/*  71 */     return parser.parse(in);
/*     */   }
/*     */   
/*     */   public static Object parseWithException(String s) throws ParseException {
/*  75 */     JSONParser parser = new JSONParser();
/*  76 */     return parser.parse(s);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeJSONString(Object value, Writer out) throws IOException {
/*  94 */     if (value == null) {
/*  95 */       out.write("null");
/*     */       
/*     */       return;
/*     */     } 
/*  99 */     if (value instanceof String) {
/* 100 */       out.write(34);
/* 101 */       out.write(escape((String)value));
/* 102 */       out.write(34);
/*     */       
/*     */       return;
/*     */     } 
/* 106 */     if (value instanceof Double) {
/* 107 */       if (((Double)value).isInfinite() || ((Double)value).isNaN()) {
/* 108 */         out.write("null");
/*     */       } else {
/* 110 */         out.write(value.toString());
/*     */       } 
/*     */       return;
/*     */     } 
/* 114 */     if (value instanceof Float) {
/* 115 */       if (((Float)value).isInfinite() || ((Float)value).isNaN()) {
/* 116 */         out.write("null");
/*     */       } else {
/* 118 */         out.write(value.toString());
/*     */       } 
/*     */       return;
/*     */     } 
/* 122 */     if (value instanceof Number) {
/* 123 */       out.write(value.toString());
/*     */       
/*     */       return;
/*     */     } 
/* 127 */     if (value instanceof Boolean) {
/* 128 */       out.write(value.toString());
/*     */       
/*     */       return;
/*     */     } 
/* 132 */     if (value instanceof JSONStreamAware) {
/* 133 */       ((JSONStreamAware)value).writeJSONString(out);
/*     */       
/*     */       return;
/*     */     } 
/* 137 */     if (value instanceof JSONAware) {
/* 138 */       out.write(((JSONAware)value).toJSONString());
/*     */       
/*     */       return;
/*     */     } 
/* 142 */     if (value instanceof Map) {
/* 143 */       JSONObject.writeJSONString((Map)value, out);
/*     */       
/*     */       return;
/*     */     } 
/* 147 */     if (value instanceof List) {
/* 148 */       JSONArray.writeJSONString((List)value, out);
/*     */       
/*     */       return;
/*     */     } 
/* 152 */     out.write(value.toString());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toJSONString(Object value) {
/* 170 */     if (value == null) {
/* 171 */       return "null";
/*     */     }
/* 173 */     if (value instanceof String) {
/* 174 */       return "\"" + escape((String)value) + "\"";
/*     */     }
/* 176 */     if (value instanceof Double) {
/* 177 */       if (((Double)value).isInfinite() || ((Double)value).isNaN()) {
/* 178 */         return "null";
/*     */       }
/* 180 */       return value.toString();
/*     */     } 
/*     */     
/* 183 */     if (value instanceof Float) {
/* 184 */       if (((Float)value).isInfinite() || ((Float)value).isNaN()) {
/* 185 */         return "null";
/*     */       }
/* 187 */       return value.toString();
/*     */     } 
/*     */     
/* 190 */     if (value instanceof Number) {
/* 191 */       return value.toString();
/*     */     }
/* 193 */     if (value instanceof Boolean) {
/* 194 */       return value.toString();
/*     */     }
/* 196 */     if (value instanceof JSONAware) {
/* 197 */       return ((JSONAware)value).toJSONString();
/*     */     }
/* 199 */     if (value instanceof Map) {
/* 200 */       return JSONObject.toJSONString((Map)value);
/*     */     }
/* 202 */     if (value instanceof List) {
/* 203 */       return JSONArray.toJSONString((List)value);
/*     */     }
/* 205 */     return value.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String escape(String s) {
/* 214 */     if (s == null)
/* 215 */       return null; 
/* 216 */     StringBuffer sb = new StringBuffer();
/* 217 */     escape(s, sb);
/* 218 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void escape(String s, StringBuffer sb) {
/* 226 */     for (int i = 0; i < s.length(); i++) {
/* 227 */       char ch = s.charAt(i);
/* 228 */       switch (ch) {
/*     */         case '"':
/* 230 */           sb.append("\\\"");
/*     */           break;
/*     */         case '\\':
/* 233 */           sb.append("\\\\");
/*     */           break;
/*     */         case '\b':
/* 236 */           sb.append("\\b");
/*     */           break;
/*     */         case '\f':
/* 239 */           sb.append("\\f");
/*     */           break;
/*     */         case '\n':
/* 242 */           sb.append("\\n");
/*     */           break;
/*     */         case '\r':
/* 245 */           sb.append("\\r");
/*     */           break;
/*     */         case '\t':
/* 248 */           sb.append("\\t");
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         default:
/* 255 */           if ((ch >= '\000' && ch <= '\037') || (ch >= '' && ch <= '') || (ch >= ' ' && ch <= '⃿')) {
/* 256 */             String ss = Integer.toHexString(ch);
/* 257 */             sb.append("\\u");
/* 258 */             for (int k = 0; k < 4 - ss.length(); k++) {
/* 259 */               sb.append('0');
/*     */             }
/* 261 */             sb.append(ss.toUpperCase());
/*     */             break;
/*     */           } 
/* 264 */           sb.append(ch);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\json\JSONValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */