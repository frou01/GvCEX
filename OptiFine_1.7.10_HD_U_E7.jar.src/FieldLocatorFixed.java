/*    */ import java.lang.reflect.Field;
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
/*    */ public class FieldLocatorFixed
/*    */   implements IFieldLocator
/*    */ {
/*    */   private Field field;
/*    */   
/*    */   public FieldLocatorFixed(Field field) {
/* 22 */     this.field = field;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Field getField() {
/* 31 */     return this.field;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\FieldLocatorFixed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */