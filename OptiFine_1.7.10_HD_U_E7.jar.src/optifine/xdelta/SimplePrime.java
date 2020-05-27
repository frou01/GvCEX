/*    */ package optifine.xdelta;
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
/*    */ public class SimplePrime
/*    */ {
/*    */   public static long belowOrEqual(long number) {
/* 45 */     if (number < 2L)
/* 46 */       return 0L; 
/* 47 */     if (number == 2L) {
/* 48 */       return 2L;
/*    */     }
/* 50 */     if ((number & 0x1L) == 0L) {
/* 51 */       number--;
/*    */     }
/* 53 */     while (!testPrime(number)) {
/* 54 */       number -= 2L;
/* 55 */       if (number <= 2L) {
/* 56 */         return 2L;
/*    */       }
/*    */     } 
/* 59 */     return number;
/*    */   }
/*    */   
/*    */   public static long aboveOrEqual(long number) {
/* 63 */     if (number <= 2L) {
/* 64 */       return 2L;
/*    */     }
/* 66 */     if ((number & 0x1L) == 0L) {
/* 67 */       number++;
/*    */     }
/* 69 */     while (!testPrime(number)) {
/* 70 */       number += 2L;
/* 71 */       if (number < 0L) {
/* 72 */         return 0L;
/*    */       }
/*    */     } 
/* 75 */     return number;
/*    */   }
/*    */   
/*    */   public static boolean testPrime(long number) {
/* 79 */     if (number == 2L) {
/* 80 */       return true;
/*    */     }
/* 82 */     if (number < 2L) {
/* 83 */       return false;
/*    */     }
/* 85 */     if ((number & 0x1L) == 0L) {
/* 86 */       return false;
/*    */     }
/* 88 */     long sqrt = (long)Math.floor(Math.sqrt(number));
/* 89 */     for (long i = 3L; i <= sqrt; i += 2L) {
/* 90 */       if (number % i == 0L) {
/* 91 */         return false;
/*    */       }
/*    */     } 
/* 94 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\xdelta\SimplePrime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */