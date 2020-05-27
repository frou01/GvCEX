/*     */ package optifine.xdelta;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EratosthenesPrimes
/*     */ {
/*     */   static BitArray sieve;
/*  52 */   static int lastInit = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void reset() {
/*  59 */     sieve = null;
/*  60 */     lastInit = -1;
/*     */   }
/*     */   
/*     */   public static synchronized void init(int maxNumber) {
/*  64 */     if (maxNumber <= lastInit) {
/*     */       return;
/*     */     }
/*  67 */     int sqrt = (int)Math.ceil(Math.sqrt(maxNumber));
/*  68 */     lastInit = maxNumber;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     maxNumber >>= 1;
/*  75 */     maxNumber++;
/*  76 */     sqrt >>= 1;
/*  77 */     sqrt++;
/*     */     
/*  79 */     sieve = new BitArray(maxNumber + 1);
/*     */ 
/*     */     
/*  82 */     sieve.set(0, true);
/*     */     
/*  84 */     for (int i = 1; i <= sqrt; i++) {
/*  85 */       if (!sieve.get(i)) {
/*     */ 
/*     */         
/*  88 */         int currentPrime = (i << 1) + 1;
/*     */         
/*  90 */         for (int j = i * ((i << 1) + 2); j <= maxNumber; j += currentPrime) {
/*  91 */           sieve.set(j, true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized int[] getPrimes(int maxNumber) {
/* 100 */     int primesNo = primesCount(maxNumber);
/* 101 */     if (primesNo <= 0) {
/* 102 */       return new int[0];
/*     */     }
/* 104 */     if (maxNumber == 2) {
/* 105 */       return new int[] { 2 };
/*     */     }
/* 107 */     init(maxNumber);
/* 108 */     int[] primes = new int[primesNo];
/* 109 */     int maxNumber_2 = maxNumber - 1 >> 1;
/* 110 */     int prime = 0;
/* 111 */     primes[prime++] = 2;
/* 112 */     for (int i = 1; i <= maxNumber_2; i++) {
/* 113 */       if (!sieve.get(i))
/*     */       {
/* 115 */         primes[prime++] = (i << 1) + 1;
/*     */       }
/*     */     } 
/* 118 */     return primes;
/*     */   }
/*     */   
/*     */   public static synchronized int primesCount(int number) {
/* 122 */     if (number < 2) {
/* 123 */       return 0;
/*     */     }
/* 125 */     init(number);
/* 126 */     int maxNumber_2 = number - 1 >> 1;
/*     */     
/* 128 */     int primesNo = 1;
/* 129 */     for (int i = 1; i <= maxNumber_2; i++) {
/* 130 */       if (!sieve.get(i))
/*     */       {
/* 132 */         primesNo++;
/*     */       }
/*     */     } 
/* 135 */     return primesNo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized int belowOrEqual(int number) {
/* 143 */     if (number < 2) return -1; 
/* 144 */     if (number == 2) return 2; 
/* 145 */     init(number);
/* 146 */     int maxNumber_2 = number - 1 >> 1;
/* 147 */     for (int i = maxNumber_2; i > 0; i--) {
/* 148 */       if (!sieve.get(i)) {
/* 149 */         return (i << 1) + 1;
/*     */       }
/*     */     } 
/* 152 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int below(int number) {
/* 158 */     return belowOrEqual(number - 1);
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\xdelta\EratosthenesPrimes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */