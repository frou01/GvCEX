/*    */ import java.lang.reflect.Field;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FieldLocatorTypes
/*    */   implements IFieldLocator
/*    */ {
/* 17 */   private Field field = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FieldLocatorTypes(Class cls, Class[] preTypes, Class<?> type, Class[] postTypes, String errorName) {
/* 23 */     Field[] fs = cls.getDeclaredFields();
/* 24 */     List<Class<?>> types = new ArrayList<Class<?>>();
/* 25 */     for (int i = 0; i < fs.length; i++) {
/*    */       
/* 27 */       Field field = fs[i];
/* 28 */       types.add(field.getType());
/*    */     } 
/* 30 */     List<Class<?>> typesMatch = new ArrayList<Class<?>>();
/* 31 */     typesMatch.addAll(Arrays.asList(preTypes));
/* 32 */     typesMatch.add(type);
/* 33 */     typesMatch.addAll(Arrays.asList(postTypes));
/*    */     
/* 35 */     int index = Collections.indexOfSubList(types, typesMatch);
/* 36 */     if (index < 0) {
/*    */       
/* 38 */       Config.warn("Field not found: " + errorName);
/*    */       
/*    */       return;
/*    */     } 
/* 42 */     int index2 = Collections.indexOfSubList(types.subList(index + 1, types.size()), typesMatch);
/* 43 */     if (index2 >= 0) {
/*    */       
/* 45 */       Config.warn("More than one match found for field: " + errorName);
/*    */       
/*    */       return;
/*    */     } 
/* 49 */     int indexField = index + preTypes.length;
/*    */     
/* 51 */     this.field = fs[indexField];
/*    */   }
/*    */ 
/*    */   
/*    */   public Field getField() {
/* 56 */     return this.field;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\FieldLocatorTypes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */