/*    */ import java.util.Comparator;
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
/*    */ public class CustomItemsComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/* 16 */     CustomItemProperties p1 = (CustomItemProperties)o1;
/* 17 */     CustomItemProperties p2 = (CustomItemProperties)o2;
/*    */     
/* 19 */     if (p1.weight != p2.weight) {
/* 20 */       return p2.weight - p1.weight;
/*    */     }
/* 22 */     if (!Config.equals(p1.basePath, p2.basePath)) {
/* 23 */       return p1.basePath.compareTo(p2.basePath);
/*    */     }
/* 25 */     return p1.name.compareTo(p2.name);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\CustomItemsComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */