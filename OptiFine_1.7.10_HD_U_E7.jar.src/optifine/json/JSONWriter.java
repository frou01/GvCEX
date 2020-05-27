/*     */ package optifine.json;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ public class JSONWriter
/*     */ {
/*  20 */   private Writer writer = null;
/*  21 */   private int indentStep = 2;
/*  22 */   private int indent = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONWriter(Writer writer) {
/*  29 */     this.writer = writer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONWriter(Writer writer, int indentStep) {
/*  39 */     this.writer = writer;
/*  40 */     this.indentStep = indentStep;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONWriter(Writer writer, int indentStep, int indent) {
/*  51 */     this.writer = writer;
/*  52 */     this.indentStep = indentStep;
/*  53 */     this.indent = indent;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeObject(Object obj) throws IOException {
/*  59 */     if (obj instanceof JSONObject) {
/*     */       
/*  61 */       JSONObject jObj = (JSONObject)obj;
/*  62 */       writeJsonObject(jObj);
/*     */       return;
/*     */     } 
/*  65 */     if (obj instanceof JSONArray) {
/*     */       
/*  67 */       JSONArray jArr = (JSONArray)obj;
/*  68 */       writeJsonArray(jArr);
/*     */       
/*     */       return;
/*     */     } 
/*  72 */     this.writer.write(JSONValue.toJSONString(obj));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeJsonArray(JSONArray jArr) throws IOException {
/*  80 */     writeLine("[");
/*  81 */     indentAdd();
/*     */     
/*  83 */     int num = jArr.size();
/*  84 */     for (int i = 0; i < num; i++) {
/*     */       
/*  86 */       Object val = jArr.get(i);
/*     */       
/*  88 */       writeIndent();
/*     */       
/*  90 */       writeObject(val);
/*     */       
/*  92 */       if (i < jArr.size() - 1)
/*     */       {
/*  94 */         write(",");
/*     */       }
/*     */       
/*  97 */       writeLine("");
/*     */     } 
/*  99 */     indentRemove();
/* 100 */     writeIndent();
/* 101 */     this.writer.write("]");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeJsonObject(JSONObject jObj) throws IOException {
/* 109 */     writeLine("{");
/* 110 */     indentAdd();
/*     */     
/* 112 */     Set<K> keys = jObj.keySet();
/* 113 */     int keyNum = keys.size();
/* 114 */     int count = 0;
/* 115 */     for (Iterator<K> it = keys.iterator(); it.hasNext(); ) {
/*     */       
/* 117 */       String key = (String)it.next();
/* 118 */       Object val = jObj.get(key);
/*     */       
/* 120 */       writeIndent();
/* 121 */       this.writer.write(JSONValue.toJSONString(key));
/* 122 */       this.writer.write(": ");
/*     */       
/* 124 */       writeObject(val);
/*     */       
/* 126 */       count++;
/* 127 */       if (count < keyNum) {
/* 128 */         writeLine(","); continue;
/*     */       } 
/* 130 */       writeLine("");
/*     */     } 
/* 132 */     indentRemove();
/* 133 */     writeIndent();
/* 134 */     this.writer.write("}");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeLine(String str) throws IOException {
/* 139 */     this.writer.write(str);
/*     */     
/* 141 */     this.writer.write("\n");
/*     */   }
/*     */ 
/*     */   
/*     */   private void write(String str) throws IOException {
/* 146 */     this.writer.write(str);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeIndent() throws IOException {
/* 152 */     for (int i = 0; i < this.indent; i++)
/*     */     {
/* 154 */       this.writer.write(32);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void indentAdd() {
/* 162 */     this.indent += this.indentStep;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void indentRemove() {
/* 169 */     this.indent -= this.indentStep;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\json\JSONWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */