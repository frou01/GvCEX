/*    */ import java.lang.reflect.Constructor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReflectorConstructor
/*    */ {
/* 11 */   private ReflectorClass reflectorClass = null;
/* 12 */   private Class[] parameterTypes = null;
/*    */   
/*    */   private boolean checked = false;
/* 15 */   private Constructor targetConstructor = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ReflectorConstructor(ReflectorClass reflectorClass, Class[] parameterTypes) {
/* 22 */     this.reflectorClass = reflectorClass;
/* 23 */     this.parameterTypes = parameterTypes;
/*    */     
/* 25 */     Constructor c = getTargetConstructor();
/*    */   }
/*    */ 
/*    */   
/*    */   public Constructor getTargetConstructor() {
/* 30 */     if (this.checked) {
/* 31 */       return this.targetConstructor;
/*    */     }
/* 33 */     this.checked = true;
/*    */     
/* 35 */     Class cls = this.reflectorClass.getTargetClass();
/* 36 */     if (cls == null) {
/* 37 */       return null;
/*    */     }
/*    */ 
/*    */     
/*    */     try {
/* 42 */       this.targetConstructor = findConstructor(cls, this.parameterTypes);
/*    */       
/* 44 */       if (this.targetConstructor == null) {
/* 45 */         Config.dbg("(Reflector) Constructor not present: " + cls.getName() + ", params: " + Config.arrayToString((Object[])this.parameterTypes));
/*    */       }
/* 47 */       if (this.targetConstructor != null)
/*    */       {
/*    */         
/* 50 */         this.targetConstructor.setAccessible(true);
/*    */       }
/*    */     }
/* 53 */     catch (Throwable e) {
/*    */       
/* 55 */       e.printStackTrace();
/*    */     } 
/*    */     
/* 58 */     return this.targetConstructor;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static Constructor findConstructor(Class cls, Class[] paramTypes) {
/* 64 */     Constructor[] cs = (Constructor[])cls.getDeclaredConstructors();
/* 65 */     for (int i = 0; i < cs.length; i++) {
/*    */       
/* 67 */       Constructor c = cs[i];
/* 68 */       Class[] types = c.getParameterTypes();
/*    */       
/* 70 */       if (Reflector.matchesTypes(paramTypes, types)) {
/* 71 */         return c;
/*    */       }
/*    */     } 
/* 74 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean exists() {
/* 82 */     if (this.checked) {
/* 83 */       return (this.targetConstructor != null);
/*    */     }
/* 85 */     return (getTargetConstructor() != null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void deactivate() {
/* 92 */     this.checked = true;
/* 93 */     this.targetConstructor = null;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\ReflectorConstructor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */