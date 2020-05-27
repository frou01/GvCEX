/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RangeListInt
/*    */ {
/* 10 */   private RangeInt[] ranges = new RangeInt[0];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RangeListInt() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RangeListInt(RangeInt ri) {
/* 22 */     addRange(ri);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addRange(RangeInt ri) {
/* 29 */     this.ranges = (RangeInt[])Config.addObjectToArray((Object[])this.ranges, ri);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isInRange(int val) {
/* 37 */     for (int i = 0; i < this.ranges.length; i++) {
/*    */       
/* 39 */       RangeInt ri = this.ranges[i];
/* 40 */       if (ri.isInRange(val)) {
/* 41 */         return true;
/*    */       }
/*    */     } 
/* 44 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCountRanges() {
/* 51 */     return this.ranges.length;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RangeInt getRange(int i) {
/* 58 */     return this.ranges[i];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 66 */     StringBuffer sb = new StringBuffer();
/* 67 */     sb.append("[");
/* 68 */     for (int i = 0; i < this.ranges.length; i++) {
/*    */       
/* 70 */       RangeInt ri = this.ranges[i];
/* 71 */       if (i > 0) {
/* 72 */         sb.append(", ");
/*    */       }
/* 74 */       sb.append(ri.toString());
/*    */     } 
/*    */     
/* 77 */     sb.append("]");
/*    */     
/* 79 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\RangeListInt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */