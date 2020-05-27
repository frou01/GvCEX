/*     */ package shadersmod.client;
/*     */ 
/*     */ import Config;
/*     */ import StrUtils;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ShaderOption
/*     */ {
/*  18 */   private String name = null;
/*  19 */   private String description = null;
/*     */   
/*  21 */   private String value = null;
/*  22 */   private String[] values = null;
/*  23 */   private String valueDefault = null;
/*     */   
/*  25 */   private String[] paths = null;
/*     */ 
/*     */   
/*     */   private boolean enabled = true;
/*     */ 
/*     */   
/*     */   private boolean visible = true;
/*     */ 
/*     */   
/*     */   public static final String COLOR_GREEN = "§a";
/*     */ 
/*     */   
/*     */   public static final String COLOR_RED = "§c";
/*     */   
/*     */   public static final String COLOR_BLUE = "§9";
/*     */ 
/*     */   
/*     */   public ShaderOption(String name, String description, String value, String[] values, String valueDefault, String path) {
/*  43 */     this.name = name;
/*  44 */     this.description = description;
/*  45 */     this.value = value;
/*  46 */     this.values = values;
/*  47 */     this.valueDefault = valueDefault;
/*  48 */     if (path != null) {
/*  49 */       this.paths = new String[] { path };
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  56 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  63 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescriptionText() {
/*  70 */     String desc = Config.normalize(this.description);
/*     */     
/*  72 */     desc = StrUtils.removePrefix(desc, "//");
/*     */     
/*  74 */     desc = Shaders.translate("option." + getName() + ".comment", desc);
/*     */     
/*  76 */     return desc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDescription(String description) {
/*  83 */     this.description = description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/*  90 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setValue(String value) {
/*  98 */     int index = getIndex(value, this.values);
/*  99 */     if (index < 0) {
/* 100 */       return false;
/*     */     }
/* 102 */     this.value = value;
/*     */     
/* 104 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValueDefault() {
/* 111 */     return this.valueDefault;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetValue() {
/* 118 */     this.value = this.valueDefault;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void nextValue() {
/* 125 */     int index = getIndex(this.value, this.values);
/* 126 */     if (index < 0) {
/*     */       return;
/*     */     }
/* 129 */     index = (index + 1) % this.values.length;
/*     */     
/* 131 */     this.value = this.values[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prevValue() {
/* 138 */     int index = getIndex(this.value, this.values);
/* 139 */     if (index < 0) {
/*     */       return;
/*     */     }
/* 142 */     index = (index - 1 + this.values.length) % this.values.length;
/*     */     
/* 144 */     this.value = this.values[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getIndex(String str, String[] strs) {
/* 153 */     for (int i = 0; i < strs.length; i++) {
/*     */       
/* 155 */       String s = strs[i];
/* 156 */       if (s.equals(str)) {
/* 157 */         return i;
/*     */       }
/*     */     } 
/* 160 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getPaths() {
/* 167 */     return this.paths;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPaths(String[] newPaths) {
/* 173 */     List<String> pathList = Arrays.asList(this.paths);
/* 174 */     for (int i = 0; i < newPaths.length; i++) {
/*     */       
/* 176 */       String newPath = newPaths[i];
/*     */       
/* 178 */       if (!pathList.contains(newPath))
/*     */       {
/*     */         
/* 181 */         this.paths = (String[])Config.addObjectToArray((Object[])this.paths, newPath);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/* 189 */     return this.enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/* 196 */     this.enabled = enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChanged() {
/* 203 */     return !Config.equals(this.value, this.valueDefault);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVisible() {
/* 210 */     return this.visible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 217 */     this.visible = visible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidValue(String val) {
/* 226 */     return (getIndex(val, this.values) >= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNameText() {
/* 233 */     return Shaders.translate("option." + this.name, this.name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValueText(String val) {
/* 241 */     return Shaders.translate("value." + this.name + "." + val, val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValueColor(String val) {
/* 249 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesLine(String line) {
/* 257 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkUsed() {
/* 264 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsedInLine(String line) {
/* 272 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSourceLine() {
/* 279 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getValues() {
/* 286 */     return (String[])this.values.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getIndexNormalized() {
/* 294 */     if (this.values.length <= 1) {
/* 295 */       return 0.0F;
/*     */     }
/* 297 */     int index = getIndex(this.value, this.values);
/* 298 */     if (index < 0) {
/* 299 */       return 0.0F;
/*     */     }
/* 301 */     float f = 1.0F * index / (this.values.length - 1.0F);
/*     */     
/* 303 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIndexNormalized(float f) {
/* 311 */     if (this.values.length <= 1) {
/*     */       return;
/*     */     }
/* 314 */     f = Config.limit(f, 0.0F, 1.0F);
/*     */     
/* 316 */     int index = Math.round(f * (this.values.length - 1));
/*     */     
/* 318 */     this.value = this.values[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 325 */     return "" + this.name + ", value: " + this.value + ", valueDefault: " + this.valueDefault + ", paths: " + Config.arrayToString((Object[])this.paths);
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\ShaderOption.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */