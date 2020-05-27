/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MatchBlock
/*     */ {
/*  12 */   private int blockId = -1;
/*     */   
/*  14 */   private int[] metadatas = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MatchBlock(int blockId) {
/*  20 */     this.blockId = blockId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MatchBlock(int blockId, int metadata) {
/*  27 */     this.blockId = blockId;
/*  28 */     if (metadata >= 0 && metadata <= 15) {
/*  29 */       this.metadatas = new int[] { metadata };
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MatchBlock(int blockId, int[] metadatas) {
/*  36 */     this.blockId = blockId;
/*  37 */     this.metadatas = metadatas;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlockId() {
/*  44 */     return this.blockId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getMetadatas() {
/*  51 */     return this.metadatas;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(int id, int metadata) {
/*  59 */     if (id != this.blockId) {
/*  60 */       return false;
/*     */     }
/*  62 */     if (this.metadatas != null) {
/*     */       
/*  64 */       boolean matchMetadata = false;
/*  65 */       for (int i = 0; i < this.metadatas.length; i++) {
/*     */         
/*  67 */         int md = this.metadatas[i];
/*  68 */         if (md == metadata) {
/*     */           
/*  70 */           matchMetadata = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*  74 */       if (!matchMetadata) {
/*  75 */         return false;
/*     */       }
/*     */     } 
/*  78 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMetadata(int metadata) {
/*  85 */     if (this.metadatas == null) {
/*     */       return;
/*     */     }
/*  88 */     if (metadata < 0 || metadata > 15) {
/*     */       return;
/*     */     }
/*  91 */     for (int i = 0; i < this.metadatas.length; i++) {
/*     */       
/*  93 */       if (this.metadatas[i] == metadata) {
/*     */         return;
/*     */       }
/*     */     } 
/*  97 */     this.metadatas = Config.addIntToArray(this.metadatas, metadata);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 105 */     return "" + this.blockId + ":" + Config.arrayToString(this.metadatas);
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\MatchBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */