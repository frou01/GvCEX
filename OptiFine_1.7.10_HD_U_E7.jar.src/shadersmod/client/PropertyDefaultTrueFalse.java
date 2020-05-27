/*    */ package shadersmod.client;
/*    */ 
/*    */ import Lang;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PropertyDefaultTrueFalse
/*    */   extends Property
/*    */ {
/* 10 */   public static final String[] PROPERTY_VALUES = new String[] { "default", "true", "false" };
/* 11 */   public static final String[] USER_VALUES = new String[] { "Default", "ON", "OFF" };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PropertyDefaultTrueFalse(String propertyName, String userName, int defaultValue) {
/* 17 */     super(propertyName, PROPERTY_VALUES, userName, USER_VALUES, defaultValue);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUserValue() {
/* 26 */     if (isDefault())
/* 27 */       return Lang.getDefault(); 
/* 28 */     if (isTrue())
/* 29 */       return Lang.getOn(); 
/* 30 */     if (isFalse()) {
/* 31 */       return Lang.getOff();
/*    */     }
/* 33 */     return super.getUserValue();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isDefault() {
/* 39 */     return (getValue() == 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isTrue() {
/* 45 */     return (getValue() == 1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFalse() {
/* 51 */     return (getValue() == 2);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\PropertyDefaultTrueFalse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */