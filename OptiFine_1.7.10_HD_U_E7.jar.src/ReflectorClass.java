/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReflectorClass
/*     */ {
/*   9 */   private String targetClassName = null;
/*     */   private boolean checked = false;
/*  11 */   private Class targetClass = null;
/*     */ 
/*     */   
/*     */   public ReflectorClass(String targetClassName) {
/*  15 */     this(targetClassName, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public ReflectorClass(String targetClassName, boolean lazyResolve) {
/*  20 */     this.targetClassName = targetClassName;
/*     */     
/*  22 */     if (!lazyResolve)
/*     */     {
/*  24 */       Class cls = getTargetClass();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ReflectorClass(Class targetClass) {
/*  30 */     this.targetClass = targetClass;
/*  31 */     this.targetClassName = targetClass.getName();
/*  32 */     this.checked = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class getTargetClass() {
/*  37 */     if (this.checked) {
/*  38 */       return this.targetClass;
/*     */     }
/*  40 */     this.checked = true;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  45 */       this.targetClass = Class.forName(this.targetClassName);
/*     */     }
/*  47 */     catch (ClassNotFoundException e) {
/*     */       
/*  49 */       Config.log("(Reflector) Class not present: " + this.targetClassName);
/*     */     }
/*  51 */     catch (Throwable e) {
/*     */       
/*  53 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  56 */     return this.targetClass;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean exists() {
/*  61 */     return (getTargetClass() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTargetClassName() {
/*  66 */     return this.targetClassName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInstance(Object obj) {
/*  75 */     if (getTargetClass() == null) {
/*  76 */       return false;
/*     */     }
/*  78 */     return getTargetClass().isInstance(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReflectorField makeField(String name) {
/*  85 */     return new ReflectorField(this, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReflectorMethod makeMethod(String name) {
/*  92 */     return new ReflectorMethod(this, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReflectorMethod makeMethod(String name, Class[] paramTypes) {
/*  99 */     return new ReflectorMethod(this, name, paramTypes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReflectorMethod makeMethod(String name, Class[] paramTypes, boolean lazyResolve) {
/* 106 */     return new ReflectorMethod(this, name, paramTypes, lazyResolve);
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\ReflectorClass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */