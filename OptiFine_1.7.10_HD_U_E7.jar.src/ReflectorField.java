/*     */ import java.lang.reflect.Field;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReflectorField
/*     */ {
/*  11 */   private IFieldLocator fieldLocator = null;
/*     */   
/*     */   private boolean checked = false;
/*  14 */   private Field targetField = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReflectorField(ReflectorClass reflectorClass, String targetFieldName) {
/*  21 */     this(new FieldLocatorName(reflectorClass, targetFieldName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReflectorField(ReflectorClass reflectorClass, String targetFieldName, boolean lazyResolve) {
/*  29 */     this(new FieldLocatorName(reflectorClass, targetFieldName), lazyResolve);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReflectorField(ReflectorClass reflectorClass, Class targetFieldType) {
/*  37 */     this(reflectorClass, targetFieldType, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReflectorField(ReflectorClass reflectorClass, Class targetFieldType, int targetFieldIndex) {
/*  45 */     this(new FieldLocatorType(reflectorClass, targetFieldType, targetFieldIndex));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ReflectorField(Field field) {
/*  51 */     this(new FieldLocatorFixed(field));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReflectorField(IFieldLocator fieldLocator) {
/*  59 */     this(fieldLocator, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReflectorField(IFieldLocator fieldLocator, boolean lazyResolve) {
/*  67 */     this.fieldLocator = fieldLocator;
/*     */     
/*  69 */     if (!lazyResolve) {
/*  70 */       getTargetField();
/*     */     }
/*     */   }
/*     */   
/*     */   public Field getTargetField() {
/*  75 */     if (this.checked) {
/*  76 */       return this.targetField;
/*     */     }
/*  78 */     this.checked = true;
/*     */     
/*  80 */     this.targetField = this.fieldLocator.getField();
/*     */     
/*  82 */     if (this.targetField != null) {
/*  83 */       this.targetField.setAccessible(true);
/*     */     }
/*  85 */     return this.targetField;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValue() {
/*  93 */     return Reflector.getFieldValue(null, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(Object value) {
/* 102 */     Reflector.setFieldValue(null, this, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(Object obj, Object value) {
/* 111 */     Reflector.setFieldValue(obj, this, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean exists() {
/* 117 */     return (getTargetField() != null);
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\ReflectorField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */