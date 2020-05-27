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
/*    */ public class MathUtils
/*    */ {
/*    */   public static int getAverage(int[] vals) {
/* 18 */     if (vals.length <= 0) {
/* 19 */       return 0;
/*    */     }
/* 21 */     int sum = getSum(vals);
/*    */     
/* 23 */     int avg = sum / vals.length;
/*    */     
/* 25 */     return avg;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getSum(int[] vals) {
/* 33 */     if (vals.length <= 0) {
/* 34 */       return 0;
/*    */     }
/* 36 */     int sum = 0;
/* 37 */     for (int i = 0; i < vals.length; i++) {
/*    */       
/* 39 */       int val = vals[i];
/* 40 */       sum += val;
/*    */     } 
/*    */     
/* 43 */     return sum;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int roundDownToPowerOfTwo(int val) {
/* 50 */     int po2 = qh.b(val);
/*    */     
/* 52 */     if (val == po2) {
/* 53 */       return po2;
/*    */     }
/* 55 */     return po2 / 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean equalsDelta(float f1, float f2, float delta) {
/* 62 */     return (Math.abs(f1 - f2) <= delta);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static float toDeg(float angle) {
/* 69 */     return angle * 180.0F / 3.1415927F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static float toRad(float angle) {
/* 76 */     return angle / 180.0F * 3.1415927F;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\MathUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */