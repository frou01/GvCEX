/*    */ package shadersmod.client;
/*    */ 
/*    */ import Config;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PropertyDefaultFastFancyOff
/*    */   extends Property
/*    */ {
/* 10 */   public static final String[] PROPERTY_VALUES = new String[] { "default", "fast", "fancy", "off" };
/* 11 */   public static final String[] USER_VALUES = new String[] { "Default", "Fast", "Fancy", "OFF" };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PropertyDefaultFastFancyOff(String propertyName, String userName, int defaultValue) {
/* 17 */     super(propertyName, PROPERTY_VALUES, userName, USER_VALUES, defaultValue);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isDefault() {
/* 23 */     return (getValue() == 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFast() {
/* 29 */     return (getValue() == 1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFancy() {
/* 35 */     return (getValue() == 2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isOff() {
/* 41 */     return (getValue() == 3);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean setPropertyValue(String propVal) {
/* 47 */     if (Config.equals(propVal, "none")) {
/* 48 */       propVal = "off";
/*    */     }
/* 50 */     return super.setPropertyValue(propVal);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\PropertyDefaultFastFancyOff.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */