/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReflectorMethod
/*     */ {
/*  13 */   private ReflectorClass reflectorClass = null;
/*  14 */   private String targetMethodName = null;
/*  15 */   private Class[] targetMethodParameterTypes = null;
/*     */   
/*     */   private boolean checked = false;
/*  18 */   private Method targetMethod = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReflectorMethod(ReflectorClass reflectorClass, String targetMethodName) {
/*  25 */     this(reflectorClass, targetMethodName, null, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReflectorMethod(ReflectorClass reflectorClass, String targetMethodName, Class[] targetMethodParameterTypes) {
/*  33 */     this(reflectorClass, targetMethodName, targetMethodParameterTypes, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReflectorMethod(ReflectorClass reflectorClass, String targetMethodName, Class[] targetMethodParameterTypes, boolean lazyResolve) {
/*  42 */     this.reflectorClass = reflectorClass;
/*  43 */     this.targetMethodName = targetMethodName;
/*  44 */     this.targetMethodParameterTypes = targetMethodParameterTypes;
/*     */     
/*  46 */     if (!lazyResolve)
/*     */     {
/*  48 */       Method m = getTargetMethod();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Method getTargetMethod() {
/*  54 */     if (this.checked) {
/*  55 */       return this.targetMethod;
/*     */     }
/*  57 */     this.checked = true;
/*     */     
/*  59 */     Class cls = this.reflectorClass.getTargetClass();
/*  60 */     if (cls == null) {
/*  61 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  66 */       if (this.targetMethodParameterTypes == null) {
/*     */         
/*  68 */         Method[] ms = getMethods(cls, this.targetMethodName);
/*     */         
/*  70 */         if (ms.length <= 0) {
/*     */           
/*  72 */           Config.log("(Reflector) Method not present: " + cls.getName() + "." + this.targetMethodName);
/*  73 */           return null;
/*     */         } 
/*     */         
/*  76 */         if (ms.length > 1) {
/*     */           
/*  78 */           Config.warn("(Reflector) More than one method found: " + cls.getName() + "." + this.targetMethodName);
/*  79 */           for (int i = 0; i < ms.length; i++) {
/*     */             
/*  81 */             Method m = ms[i];
/*  82 */             Config.warn("(Reflector)  - " + m);
/*     */           } 
/*  84 */           return null;
/*     */         } 
/*     */         
/*  87 */         this.targetMethod = ms[0];
/*     */       
/*     */       }
/*     */       else {
/*     */         
/*  92 */         this.targetMethod = getMethod(cls, this.targetMethodName, this.targetMethodParameterTypes);
/*     */       } 
/*     */       
/*  95 */       if (this.targetMethod == null) {
/*     */         
/*  97 */         Config.log("(Reflector) Method not present: " + cls.getName() + "." + this.targetMethodName);
/*  98 */         return null;
/*     */       } 
/*     */       
/* 101 */       this.targetMethod.setAccessible(true);
/*     */       
/* 103 */       return this.targetMethod;
/*     */     }
/* 105 */     catch (Throwable e) {
/*     */       
/* 107 */       e.printStackTrace();
/* 108 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean exists() {
/* 115 */     if (this.checked) {
/* 116 */       return (this.targetMethod != null);
/*     */     }
/* 118 */     return (getTargetMethod() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class getReturnType() {
/* 123 */     Method tm = getTargetMethod();
/*     */     
/* 125 */     if (tm == null) {
/* 126 */       return null;
/*     */     }
/* 128 */     return tm.getReturnType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deactivate() {
/* 135 */     this.checked = true;
/* 136 */     this.targetMethod = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Method getMethod(Class cls, String methodName, Class[] paramTypes) {
/* 143 */     Method[] ms = cls.getDeclaredMethods();
/* 144 */     for (int i = 0; i < ms.length; i++) {
/*     */       
/* 146 */       Method m = ms[i];
/*     */       
/* 148 */       if (m.getName().equals(methodName)) {
/*     */ 
/*     */         
/* 151 */         Class[] types = m.getParameterTypes();
/* 152 */         if (Reflector.matchesTypes(paramTypes, types))
/*     */         {
/*     */           
/* 155 */           return m; } 
/*     */       } 
/*     */     } 
/* 158 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Method[] getMethods(Class cls, String methodName) {
/* 167 */     List<Method> listMethods = new ArrayList();
/* 168 */     Method[] ms = cls.getDeclaredMethods();
/* 169 */     for (int i = 0; i < ms.length; i++) {
/*     */       
/* 171 */       Method m = ms[i];
/* 172 */       if (m.getName().equals(methodName))
/*     */       {
/*     */         
/* 175 */         listMethods.add(m);
/*     */       }
/*     */     } 
/* 178 */     Method[] methods = listMethods.<Method>toArray(new Method[listMethods.size()]);
/*     */     
/* 180 */     return methods;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\ReflectorMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */