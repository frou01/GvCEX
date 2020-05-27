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
/*    */ public class NumUtils
/*    */ {
/*    */   public static float limit(float val, float min, float max) {
/* 15 */     if (val < min)
/* 16 */       return min; 
/* 17 */     if (val > max) {
/* 18 */       return max;
/*    */     }
/* 20 */     return val;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\NumUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */