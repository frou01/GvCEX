/*    */ import java.lang.reflect.Field;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FieldLocatorType
/*    */   implements IFieldLocator
/*    */ {
/* 13 */   private ReflectorClass reflectorClass = null;
/* 14 */   private Class targetFieldType = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private int targetFieldIndex;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FieldLocatorType(ReflectorClass reflectorClass, Class targetFieldType) {
/* 25 */     this(reflectorClass, targetFieldType, 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FieldLocatorType(ReflectorClass reflectorClass, Class targetFieldType, int targetFieldIndex) {
/* 35 */     this.reflectorClass = reflectorClass;
/* 36 */     this.targetFieldType = targetFieldType;
/* 37 */     this.targetFieldIndex = targetFieldIndex;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Field getField() {
/* 47 */     Class cls = this.reflectorClass.getTargetClass();
/* 48 */     if (cls == null) {
/* 49 */       return null;
/*    */     }
/*    */     
/*    */     try {
/* 53 */       Field[] fileds = cls.getDeclaredFields();
/* 54 */       int fieldIndex = 0;
/* 55 */       for (int i = 0; i < fileds.length; i++) {
/*    */         
/* 57 */         Field field = fileds[i];
/* 58 */         if (field.getType() == this.targetFieldType)
/*    */         {
/*    */           
/* 61 */           if (fieldIndex != this.targetFieldIndex) {
/*    */             
/* 63 */             fieldIndex++;
/*    */           }
/*    */           else {
/*    */             
/* 67 */             field.setAccessible(true);
/*    */             
/* 69 */             return field;
/*    */           }  } 
/*    */       } 
/* 72 */       Config.log("(Reflector) Field not present: " + cls.getName() + ".(type: " + this.targetFieldType + ", index: " + this.targetFieldIndex + ")");
/*    */       
/* 74 */       return null;
/*    */     }
/* 76 */     catch (SecurityException e) {
/*    */       
/* 78 */       e.printStackTrace();
/* 79 */       return null;
/*    */     }
/* 81 */     catch (Throwable e) {
/*    */       
/* 83 */       e.printStackTrace();
/* 84 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\FieldLocatorType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */