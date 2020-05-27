/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IntegerCache
/*    */ {
/*    */   private static final int CACHE_SIZE = 65535;
/* 12 */   private static final Integer[] cache = makeCache(65535);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static Integer[] makeCache(int size) {
/* 20 */     Integer[] arr = new Integer[size];
/* 21 */     for (int i = 0; i < size; i++)
/*    */     {
/* 23 */       arr[i] = new Integer(i);
/*    */     }
/* 25 */     return arr;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Integer valueOf(int value) {
/* 35 */     if (value < 0 || value >= 65535) {
/* 36 */       return new Integer(value);
/*    */     }
/* 38 */     return cache[value];
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\IntegerCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */