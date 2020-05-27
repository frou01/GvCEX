/*     */ package shadersmod.client;
/*     */ 
/*     */ import Config;
/*     */ import java.util.Properties;
/*     */ import org.apache.commons.lang3.ArrayUtils;
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
/*     */ public class Property
/*     */ {
/*  18 */   private int[] values = null;
/*  19 */   private int defaultValue = 0;
/*     */   
/*  21 */   private String propertyName = null;
/*  22 */   private String[] propertyValues = null;
/*     */   
/*  24 */   private String userName = null;
/*  25 */   private String[] userValues = null;
/*     */   
/*  27 */   private int value = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Property(String propertyName, String[] propertyValues, String userName, String[] userValues, int defaultValue) {
/*  38 */     this.propertyName = propertyName;
/*  39 */     this.propertyValues = propertyValues;
/*  40 */     this.userName = userName;
/*  41 */     this.userValues = userValues;
/*  42 */     this.defaultValue = defaultValue;
/*     */     
/*  44 */     if (propertyValues.length != userValues.length)
/*  45 */       throw new IllegalArgumentException("Property and user values have different lengths: " + propertyValues.length + " != " + userValues.length); 
/*  46 */     if (defaultValue < 0 || defaultValue >= propertyValues.length) {
/*  47 */       throw new IllegalArgumentException("Invalid default value: " + defaultValue);
/*     */     }
/*  49 */     this.value = defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setPropertyValue(String propVal) {
/*  57 */     if (propVal == null) {
/*     */       
/*  59 */       this.value = this.defaultValue;
/*  60 */       return false;
/*     */     } 
/*     */     
/*  63 */     this.value = ArrayUtils.indexOf((Object[])this.propertyValues, propVal);
/*     */     
/*  65 */     if (this.value < 0 || this.value >= this.propertyValues.length) {
/*     */       
/*  67 */       this.value = this.defaultValue;
/*  68 */       return false;
/*     */     } 
/*     */     
/*  71 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void nextValue() {
/*  78 */     this.value++;
/*     */     
/*  80 */     if (this.value < 0 || this.value >= this.propertyValues.length) {
/*  81 */       this.value = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(int val) {
/*  88 */     this.value = val;
/*     */     
/*  90 */     if (this.value < 0 || this.value >= this.propertyValues.length) {
/*  91 */       this.value = this.defaultValue;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValue() {
/*  98 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUserValue() {
/* 105 */     return this.userValues[this.value];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPropertyValue() {
/* 112 */     return this.propertyValues[this.value];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUserName() {
/* 119 */     return this.userName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPropertyName() {
/* 126 */     return this.propertyName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetValue() {
/* 134 */     this.value = this.defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean loadFrom(Properties props) {
/* 141 */     resetValue();
/*     */     
/* 143 */     if (props == null) {
/* 144 */       return false;
/*     */     }
/* 146 */     String str = props.getProperty(this.propertyName);
/* 147 */     if (str == null) {
/* 148 */       return false;
/*     */     }
/* 150 */     return setPropertyValue(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveTo(Properties props) {
/* 157 */     if (props == null) {
/*     */       return;
/*     */     }
/* 160 */     props.setProperty(getPropertyName(), getPropertyValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 168 */     return "" + this.propertyName + "=" + getPropertyValue() + " [" + Config.arrayToString((Object[])this.propertyValues) + "], value: " + this.value;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\Property.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */