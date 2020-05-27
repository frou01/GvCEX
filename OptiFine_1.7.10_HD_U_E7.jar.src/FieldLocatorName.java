/*    */ import java.lang.reflect.Field;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FieldLocatorName
/*    */   implements IFieldLocator
/*    */ {
/* 13 */   private ReflectorClass reflectorClass = null;
/* 14 */   private String targetFieldName = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FieldLocatorName(ReflectorClass reflectorClass, String targetFieldName) {
/* 23 */     this.reflectorClass = reflectorClass;
/* 24 */     this.targetFieldName = targetFieldName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Field getField() {
/* 33 */     Class cls = this.reflectorClass.getTargetClass();
/* 34 */     if (cls == null) {
/* 35 */       return null;
/*    */     }
/*    */ 
/*    */     
/*    */     try {
/* 40 */       Field targetField = getDeclaredField(cls, this.targetFieldName);
/*    */       
/* 42 */       targetField.setAccessible(true);
/*    */       
/* 44 */       return targetField;
/*    */     }
/* 46 */     catch (NoSuchFieldException e) {
/*    */       
/* 48 */       Config.log("(Reflector) Field not present: " + cls.getName() + "." + this.targetFieldName);
/* 49 */       return null;
/*    */     }
/* 51 */     catch (SecurityException e) {
/*    */       
/* 53 */       e.printStackTrace();
/* 54 */       return null;
/*    */     }
/* 56 */     catch (Throwable e) {
/*    */       
/* 58 */       e.printStackTrace();
/* 59 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Field getDeclaredField(Class<Object> cls, String name) throws NoSuchFieldException {
/* 68 */     Field[] fields = cls.getDeclaredFields();
/* 69 */     for (int i = 0; i < fields.length; i++) {
/*    */       
/* 71 */       Field field = fields[i];
/* 72 */       if (field.getName().equals(name)) {
/* 73 */         return field;
/*    */       }
/*    */     } 
/* 76 */     if (cls == Object.class) {
/* 77 */       throw new NoSuchFieldException(name);
/*    */     }
/* 79 */     return getDeclaredField(cls.getSuperclass(), name);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\FieldLocatorName.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */