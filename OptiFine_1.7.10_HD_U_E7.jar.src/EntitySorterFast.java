/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntitySorterFast
/*    */   implements Comparator
/*    */ {
/*    */   private sa entity;
/*    */   
/*    */   public EntitySorterFast(sa par1Entity) {
/* 14 */     this.entity = par1Entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public void prepareToSort(blo[] renderers, int countWorldRenderers) {
/* 19 */     for (int i = 0; i < countWorldRenderers; i++) {
/*    */       
/* 21 */       blo wr = renderers[i];
/* 22 */       wr.updateDistanceToEntitySquared(this.entity);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int compare(blo par1WorldRenderer, blo par2WorldRenderer) {
/* 28 */     float dist1 = par1WorldRenderer.sortDistanceToEntitySquared;
/* 29 */     float dist2 = par2WorldRenderer.sortDistanceToEntitySquared;
/* 30 */     if (dist1 == dist2)
/* 31 */       return 0; 
/* 32 */     if (dist1 > dist2) {
/* 33 */       return 1;
/*    */     }
/* 35 */     return -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public int compare(Object par1Obj, Object par2Obj) {
/* 40 */     return compare((blo)par1Obj, (blo)par2Obj);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\EntitySorterFast.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */