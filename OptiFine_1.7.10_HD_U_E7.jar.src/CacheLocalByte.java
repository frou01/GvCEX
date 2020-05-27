/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CacheLocalByte
/*    */ {
/* 12 */   private int maxX = 18;
/* 13 */   private int maxY = 128;
/* 14 */   private int maxZ = 18;
/*    */   
/* 16 */   private int offsetX = 0;
/* 17 */   private int offsetY = 0;
/* 18 */   private int offsetZ = 0;
/*    */   
/* 20 */   private byte[][][] cache = (byte[][][])null;
/*    */   
/* 22 */   private byte[] lastZs = null;
/* 23 */   private int lastDz = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CacheLocalByte(int maxX, int maxY, int maxZ) {
/* 29 */     this.maxX = maxX;
/* 30 */     this.maxY = maxY;
/* 31 */     this.maxZ = maxZ;
/* 32 */     this.cache = new byte[maxX][maxY][maxZ];
/* 33 */     resetCache();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetCache() {
/* 40 */     for (int x = 0; x < this.maxX; x++) {
/*    */       
/* 42 */       byte[][] ys = this.cache[x];
/* 43 */       for (int y = 0; y < this.maxY; y++) {
/*    */         
/* 45 */         byte[] zs = ys[y];
/* 46 */         for (int z = 0; z < this.maxZ; z++)
/*    */         {
/* 48 */           zs[z] = -1;
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setOffset(int x, int y, int z) {
/* 60 */     this.offsetX = x;
/* 61 */     this.offsetY = y;
/* 62 */     this.offsetZ = z;
/* 63 */     resetCache();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte get(int x, int y, int z) {
/*    */     try {
/* 75 */       this.lastZs = this.cache[x - this.offsetX][y - this.offsetY];
/* 76 */       this.lastDz = z - this.offsetZ;
/* 77 */       return this.lastZs[this.lastDz];
/*    */     }
/* 79 */     catch (ArrayIndexOutOfBoundsException e) {
/*    */       
/* 81 */       e.printStackTrace();
/*    */ 
/*    */       
/* 84 */       return -1;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLast(byte val) {
/*    */     try {
/* 93 */       this.lastZs[this.lastDz] = val;
/*    */     }
/* 95 */     catch (Exception e) {
/*    */       
/* 97 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\CacheLocalByte.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */