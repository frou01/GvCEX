/*     */ package shadersmod.client;
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
/*     */ public class ShaderLine
/*     */ {
/*     */   private int type;
/*     */   private String name;
/*     */   private String value;
/*     */   private String line;
/*     */   public static final int TYPE_UNIFORM = 1;
/*     */   public static final int TYPE_ATTRIBUTE = 2;
/*     */   public static final int TYPE_CONST_INT = 3;
/*     */   public static final int TYPE_CONST_FLOAT = 4;
/*     */   public static final int TYPE_CONST_BOOL = 5;
/*     */   public static final int TYPE_PROPERTY = 6;
/*     */   
/*     */   public ShaderLine(int type, String name, String value, String line) {
/*  31 */     this.type = type;
/*  32 */     this.name = name;
/*  33 */     this.value = value;
/*  34 */     this.line = line;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/*  41 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  48 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/*  55 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUniform() {
/*  62 */     return (this.type == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUniform(String name) {
/*  70 */     return (isUniform() && name.equals(this.name));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAttribute() {
/*  78 */     return (this.type == 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAttribute(String name) {
/*  86 */     return (isAttribute() && name.equals(this.name));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isProperty() {
/*  94 */     return (this.type == 6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConstInt() {
/* 101 */     return (this.type == 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConstFloat() {
/* 108 */     return (this.type == 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConstBool() {
/* 116 */     return (this.type == 5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isProperty(String name) {
/* 123 */     return (isProperty() && name.equals(this.name));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isProperty(String name, String value) {
/* 130 */     return (isProperty(name) && value.equals(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConstInt(String name) {
/* 138 */     return (isConstInt() && name.equals(this.name));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConstIntSuffix(String suffix) {
/* 146 */     return (isConstInt() && this.name.endsWith(suffix));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConstFloat(String name) {
/* 154 */     return (isConstFloat() && name.equals(this.name));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConstBool(String name) {
/* 162 */     return (isConstBool() && name.equals(this.name));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConstBoolSuffix(String suffix) {
/* 170 */     return (isConstBool() && this.name.endsWith(suffix));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConstBoolSuffix(String suffix, boolean val) {
/* 178 */     return (isConstBoolSuffix(suffix) && getValueBool() == val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConstBool(String name1, String name2) {
/* 185 */     return (isConstBool(name1) || isConstBool(name2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConstBool(String name1, String name2, String name3) {
/* 192 */     return (isConstBool(name1) || isConstBool(name2) || isConstBool(name3));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConstBool(String name, boolean val) {
/* 199 */     return (isConstBool(name) && getValueBool() == val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConstBool(String name1, String name2, boolean val) {
/* 206 */     return (isConstBool(name1, name2) && getValueBool() == val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConstBool(String name1, String name2, String name3, boolean val) {
/* 213 */     return (isConstBool(name1, name2, name3) && getValueBool() == val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValueInt() {
/*     */     try {
/* 223 */       return Integer.parseInt(this.value);
/*     */     }
/* 225 */     catch (NumberFormatException e) {
/*     */       
/* 227 */       throw new NumberFormatException("Invalid integer: " + this.value + ", line: " + this.line);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getValueFloat() {
/*     */     try {
/* 238 */       return Float.parseFloat(this.value);
/*     */     }
/* 240 */     catch (NumberFormatException e) {
/*     */       
/* 242 */       throw new NumberFormatException("Invalid float: " + this.value + ", line: " + this.line);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getValueBool() {
/* 250 */     String valLow = this.value.toLowerCase();
/* 251 */     if (!valLow.equals("true") && !valLow.equals("false")) {
/* 252 */       throw new RuntimeException("Invalid boolean: " + this.value + ", line: " + this.line);
/*     */     }
/* 254 */     return Boolean.valueOf(this.value).booleanValue();
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\ShaderLine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */