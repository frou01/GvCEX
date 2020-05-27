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
/*    */ public class WrDisplayListBlock
/*    */ {
/* 13 */   private int start = -1;
/* 14 */   private int used = -1;
/* 15 */   private int end = -1;
/*    */ 
/*    */ 
/*    */   
/*    */   public static final int BLOCK_LENGTH = 16384;
/*    */ 
/*    */ 
/*    */   
/*    */   public WrDisplayListBlock() {
/* 24 */     this.start = ban.a(16384);
/* 25 */     this.used = this.start;
/* 26 */     this.end = this.start + 16384;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canAllocate(int len) {
/* 31 */     return (this.used + len < this.end);
/*    */   }
/*    */ 
/*    */   
/*    */   public int allocate(int len) {
/* 36 */     if (!canAllocate(len)) {
/* 37 */       return -1;
/*    */     }
/* 39 */     int allocated = this.used;
/*    */     
/* 41 */     this.used += len;
/*    */     
/* 43 */     return allocated;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void reset() {
/* 49 */     this.used = this.start;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void deleteDisplayLists() {
/* 57 */     ban.b(this.start);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getStart() {
/* 64 */     return this.start;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getUsed() {
/* 71 */     return this.used;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEnd() {
/* 78 */     return this.end;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\WrDisplayListBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */