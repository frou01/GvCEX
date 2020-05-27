/*    */ import java.util.Comparator;
/*    */ import org.lwjgl.opengl.DisplayMode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DisplayModeComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/* 16 */     DisplayMode dm1 = (DisplayMode)o1;
/* 17 */     DisplayMode dm2 = (DisplayMode)o2;
/*    */     
/* 19 */     if (dm1.getWidth() != dm2.getWidth()) {
/* 20 */       return dm1.getWidth() - dm2.getWidth();
/*    */     }
/* 22 */     if (dm1.getHeight() != dm2.getHeight()) {
/* 23 */       return dm1.getHeight() - dm2.getHeight();
/*    */     }
/* 25 */     if (dm1.getBitsPerPixel() != dm2.getBitsPerPixel()) {
/* 26 */       return dm1.getBitsPerPixel() - dm2.getBitsPerPixel();
/*    */     }
/* 28 */     if (dm1.getFrequency() != dm2.getFrequency()) {
/* 29 */       return dm1.getFrequency() - dm2.getFrequency();
/*    */     }
/* 31 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\DisplayModeComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */