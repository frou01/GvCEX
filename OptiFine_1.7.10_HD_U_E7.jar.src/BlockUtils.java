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
/*    */ public class BlockUtils
/*    */ {
/* 18 */   private static ReflectorClass ForgeBlock = new ReflectorClass(aji.class);
/* 19 */   private static ReflectorMethod ForgeBlock_setLightOpacity = new ReflectorMethod(ForgeBlock, "func_149713_g");
/*    */ 
/*    */   
/*    */   private static boolean directAccessValid = true;
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setLightOpacity(aji block, int opacity) {
/* 27 */     if (directAccessValid) {
/*    */       
/*    */       try {
/*    */         
/* 31 */         block.g(opacity);
/*    */         
/*    */         return;
/* 34 */       } catch (IllegalAccessError e) {
/*    */         
/* 36 */         directAccessValid = false;
/*    */         
/* 38 */         if (!ForgeBlock_setLightOpacity.exists()) {
/* 39 */           throw e;
/*    */         }
/*    */       } 
/*    */     }
/* 43 */     Reflector.callVoid(block, ForgeBlock_setLightOpacity, new Object[] { Integer.valueOf(opacity) });
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\BlockUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */