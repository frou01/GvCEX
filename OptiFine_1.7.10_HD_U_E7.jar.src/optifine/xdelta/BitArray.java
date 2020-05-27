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
/*    */ public class BitArray
/*    */ {
/*    */   int[] implArray;
/*    */   int size;
/*    */   static final int INT_SIZE = 32;
/*    */   
/*    */   public BitArray(int size) {
/* 39 */     int implSize = size / 32 + 1;
/* 40 */     this.implArray = new int[implSize];
/*    */   }
/*    */   public void set(int pos, boolean value) {
/* 43 */     int implPos = pos / 32;
/* 44 */     int bitMask = 1 << (pos & 0x1F);
/* 45 */     if (value) {
/* 46 */       this.implArray[implPos] = this.implArray[implPos] | bitMask;
/*    */     } else {
/* 48 */       this.implArray[implPos] = this.implArray[implPos] & (bitMask ^ 0xFFFFFFFF);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean get(int pos) {
/* 53 */     int implPos = pos / 32;
/* 54 */     int bitMask = 1 << (pos & 0x1F);
/* 55 */     return ((this.implArray[implPos] & bitMask) != 0);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\xdelta\BitArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */