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
/*    */ public class RangeInt
/*    */ {
/*    */   private int min;
/*    */   private int max;
/*    */   
/*    */   public RangeInt(int min, int max) {
/* 22 */     this.min = Math.min(min, max);
/* 23 */     this.max = Math.max(min, max);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isInRange(int val) {
/* 32 */     if (val < this.min) {
/* 33 */       return false;
/*    */     }
/* 35 */     if (val > this.max) {
/* 36 */       return false;
/*    */     }
/* 38 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMin() {
/* 45 */     return this.min;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMax() {
/* 52 */     return this.max;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 60 */     return "min: " + this.min + ", max: " + this.max;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\RangeInt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */