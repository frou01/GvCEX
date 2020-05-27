/*    */ import java.util.HashSet;
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
/*    */ public class WrUpdateState
/*    */ {
/* 14 */   public ChunkCacheOF chunkcache = null;
/* 15 */   public blm renderblocks = null;
/* 16 */   public HashSet setOldEntityRenders = null;
/* 17 */   int viewEntityPosX = 0;
/* 18 */   int viewEntityPosY = 0;
/* 19 */   int viewEntityPosZ = 0;
/* 20 */   public int renderPass = 0;
/* 21 */   public int y = 0;
/*    */   public boolean flag = false;
/*    */   public boolean hasRenderedBlocks = false;
/*    */   public boolean hasGlList = false;
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\WrUpdateState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */