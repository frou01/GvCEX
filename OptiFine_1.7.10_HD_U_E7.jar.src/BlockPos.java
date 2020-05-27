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
/*    */ public class BlockPos
/*    */ {
/*    */   private final int x;
/*    */   private final int y;
/*    */   private final int z;
/*    */   
/*    */   public BlockPos(int x, int y, int z) {
/* 24 */     this.x = x;
/* 25 */     this.y = y;
/* 26 */     this.z = z;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getX() {
/* 33 */     return this.x;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getY() {
/* 40 */     return this.y;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getZ() {
/* 47 */     return this.z;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockPos add(BlockPos pos) {
/* 55 */     return new BlockPos(this.x + pos.x, this.y + pos.y, this.z + pos.z);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockPos sub(BlockPos pos) {
/* 63 */     return new BlockPos(this.x - pos.x, this.y - pos.y, this.z - pos.z);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 70 */     if (this == obj)
/*    */     {
/* 72 */       return true;
/*    */     }
/* 74 */     if (!(obj instanceof BlockPos))
/*    */     {
/* 76 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 80 */     BlockPos blockPos = (BlockPos)obj;
/* 81 */     return (getX() != blockPos.getX()) ? false : ((getY() != blockPos.getY()) ? false : ((getZ() == blockPos.getZ())));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 88 */     return (getY() + getZ() * 31) * 31 + getX();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 93 */     return "" + this.x + ", " + this.y + ", " + this.z;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\BlockPos.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */