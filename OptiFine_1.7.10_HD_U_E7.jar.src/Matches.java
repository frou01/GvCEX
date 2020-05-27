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
/*    */ public class Matches
/*    */ {
/*    */   public static boolean block(int blockId, int metadata, MatchBlock[] matchBlocks) {
/* 19 */     if (matchBlocks == null) {
/* 20 */       return true;
/*    */     }
/* 22 */     for (int i = 0; i < matchBlocks.length; i++) {
/*    */       
/* 24 */       MatchBlock mb = matchBlocks[i];
/* 25 */       if (mb.matches(blockId, metadata)) {
/* 26 */         return true;
/*    */       }
/*    */     } 
/* 29 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean blockId(int blockId, MatchBlock[] matchBlocks) {
/* 38 */     if (matchBlocks == null) {
/* 39 */       return true;
/*    */     }
/* 41 */     for (int i = 0; i < matchBlocks.length; i++) {
/*    */       
/* 43 */       MatchBlock mb = matchBlocks[i];
/* 44 */       if (mb.getBlockId() == blockId) {
/* 45 */         return true;
/*    */       }
/*    */     } 
/* 48 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean metadata(int metadata, int[] metadatas) {
/* 54 */     if (metadatas == null) {
/* 55 */       return true;
/*    */     }
/* 57 */     for (int i = 0; i < metadatas.length; i++) {
/*    */       
/* 59 */       if (metadatas[i] == metadata) {
/* 60 */         return true;
/*    */       }
/*    */     } 
/* 63 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean sprite(bqd sprite, bqd[] sprites) {
/* 72 */     if (sprites == null) {
/* 73 */       return true;
/*    */     }
/* 75 */     for (int i = 0; i < sprites.length; i++) {
/*    */       
/* 77 */       if (sprites[i] == sprite) {
/* 78 */         return true;
/*    */       }
/*    */     } 
/* 81 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean biome(ahu biome, ahu[] biomes) {
/* 90 */     if (biomes == null) {
/* 91 */       return true;
/*    */     }
/* 93 */     for (int i = 0; i < biomes.length; i++) {
/*    */       
/* 95 */       if (biomes[i] == biome) {
/* 96 */         return true;
/*    */       }
/*    */     } 
/* 99 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\Matches.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */