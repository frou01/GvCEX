/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReflectorFields
/*    */ {
/*    */   private ReflectorClass reflectorClass;
/*    */   private Class fieldType;
/*    */   private int fieldCount;
/*    */   private ReflectorField[] reflectorFields;
/*    */   
/*    */   public ReflectorFields(ReflectorClass reflectorClass, Class fieldType, int fieldCount) {
/* 25 */     this.reflectorClass = reflectorClass;
/* 26 */     this.fieldType = fieldType;
/*    */     
/* 28 */     if (!reflectorClass.exists())
/*    */       return; 
/* 30 */     if (fieldType == null) {
/*    */       return;
/*    */     }
/* 33 */     this.reflectorFields = new ReflectorField[fieldCount];
/* 34 */     for (int i = 0; i < this.reflectorFields.length; i++)
/*    */     {
/* 36 */       this.reflectorFields[i] = new ReflectorField(reflectorClass, fieldType, i);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ReflectorClass getReflectorClass() {
/* 44 */     return this.reflectorClass;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class getFieldType() {
/* 51 */     return this.fieldType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getFieldCount() {
/* 58 */     return this.fieldCount;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ReflectorField getReflectorField(int index) {
/* 66 */     if (index < 0 || index >= this.reflectorFields.length) {
/* 67 */       return null;
/*    */     }
/* 69 */     return this.reflectorFields[index];
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\ReflectorFields.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */