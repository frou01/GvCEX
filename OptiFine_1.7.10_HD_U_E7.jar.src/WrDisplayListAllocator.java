/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class WrDisplayListAllocator
/*    */ {
/* 15 */   private List<WrDisplayListBlock> listBlocks = new ArrayList<WrDisplayListBlock>();
/*    */   
/* 17 */   private WrDisplayListBlock currentBlock = null;
/*    */   
/* 19 */   private int blockIndex = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int allocateDisplayLists(int len) {
/* 27 */     if (len <= 0 || len > 16384) {
/* 28 */       throw new IllegalArgumentException("Invalid display list length: " + len);
/*    */     }
/* 30 */     if (this.currentBlock == null || !this.currentBlock.canAllocate(len)) {
/*    */ 
/*    */       
/* 33 */       if (this.blockIndex + 1 < this.listBlocks.size()) {
/*    */ 
/*    */         
/* 36 */         this.blockIndex++;
/* 37 */         this.currentBlock = this.listBlocks.get(this.blockIndex);
/*    */ 
/*    */       
/*    */       }
/*    */       else {
/*    */ 
/*    */         
/* 44 */         this.currentBlock = new WrDisplayListBlock();
/*    */         
/* 46 */         this.blockIndex = this.listBlocks.size();
/* 47 */         this.listBlocks.add(this.currentBlock);
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/* 52 */       if (!this.currentBlock.canAllocate(len)) {
/* 53 */         throw new IllegalArgumentException("Can not allocate: " + len);
/*    */       }
/*    */     } 
/* 56 */     return this.currentBlock.allocate(len);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetAllocatedLists() {
/* 65 */     this.currentBlock = null;
/* 66 */     this.blockIndex = -1;
/*    */     
/* 68 */     for (int i = 0; i < this.listBlocks.size(); i++) {
/*    */       
/* 70 */       WrDisplayListBlock block = this.listBlocks.get(i);
/* 71 */       block.reset();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void deleteDisplayLists() {
/* 81 */     for (int i = 0; i < this.listBlocks.size(); i++) {
/*    */       
/* 83 */       WrDisplayListBlock block = this.listBlocks.get(i);
/* 84 */       block.deleteDisplayLists();
/*    */     } 
/*    */     
/* 87 */     this.listBlocks.clear();
/*    */     
/* 89 */     this.currentBlock = null;
/* 90 */     this.blockIndex = -1;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\WrDisplayListAllocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */