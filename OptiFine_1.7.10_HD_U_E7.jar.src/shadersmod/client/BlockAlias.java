/*     */ package shadersmod.client;
/*     */ 
/*     */ import Config;
/*     */ import MatchBlock;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public class BlockAlias
/*     */ {
/*     */   private int blockId;
/*     */   private MatchBlock[] matchBlocks;
/*     */   
/*     */   public BlockAlias(int blockId, MatchBlock[] matchBlocks) {
/*  32 */     this.blockId = blockId;
/*  33 */     this.matchBlocks = matchBlocks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlockId() {
/*  40 */     return this.blockId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(int id, int metadata) {
/*  49 */     for (int i = 0; i < this.matchBlocks.length; i++) {
/*     */       
/*  51 */       MatchBlock matchBlock = this.matchBlocks[i];
/*  52 */       if (matchBlock.matches(id, metadata)) {
/*  53 */         return true;
/*     */       }
/*     */     } 
/*  56 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getMatchBlockIds() {
/*  63 */     Set<Integer> blockIdSet = new HashSet<Integer>();
/*  64 */     for (int i = 0; i < this.matchBlocks.length; i++) {
/*     */       
/*  66 */       MatchBlock matchBlock = this.matchBlocks[i];
/*  67 */       int blockId = matchBlock.getBlockId();
/*  68 */       blockIdSet.add(Integer.valueOf(blockId));
/*     */     } 
/*     */     
/*  71 */     Integer[] blockIdsArr = blockIdSet.<Integer>toArray(new Integer[blockIdSet.size()]);
/*  72 */     int[] blockIds = Config.toPrimitive(blockIdsArr);
/*     */     
/*  74 */     return blockIds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MatchBlock[] getMatchBlocks(int matchBlockId) {
/*  81 */     List<MatchBlock> listMatchBlock = new ArrayList<MatchBlock>();
/*  82 */     for (int i = 0; i < this.matchBlocks.length; i++) {
/*     */       
/*  84 */       MatchBlock mb = this.matchBlocks[i];
/*  85 */       if (mb.getBlockId() == matchBlockId)
/*     */       {
/*     */         
/*  88 */         listMatchBlock.add(mb);
/*     */       }
/*     */     } 
/*  91 */     MatchBlock[] mbs = listMatchBlock.<MatchBlock>toArray(new MatchBlock[listMatchBlock.size()]);
/*     */     
/*  93 */     return mbs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 101 */     return "block." + this.blockId + "=" + Config.arrayToString((Object[])this.matchBlocks);
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\BlockAlias.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */