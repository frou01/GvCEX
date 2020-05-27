/*    */ import java.awt.Rectangle;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TooltipProviderOptions
/*    */   implements TooltipProvider
/*    */ {
/*    */   public Rectangle getTooltipBounds(bdw guiScreen, int x, int y) {
/* 26 */     int x1 = guiScreen.l / 2 - 150;
/* 27 */     int y1 = guiScreen.m / 6 - 7;
/*    */     
/* 29 */     if (y <= y1 + 98) {
/* 30 */       y1 += 105;
/*    */     }
/* 32 */     int x2 = x1 + 150 + 150;
/* 33 */     int y2 = y1 + 84 + 10;
/*    */     
/* 35 */     return new Rectangle(x1, y1, x2 - x1, y2 - y1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isRenderBorder() {
/* 43 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getTooltipLines(bcb btn, int width) {
/* 52 */     if (!(btn instanceof IOptionControl)) {
/* 53 */       return null;
/*    */     }
/* 55 */     IOptionControl ctl = (IOptionControl)btn;
/* 56 */     bbm option = ctl.getOption();
/* 57 */     String[] lines = getTooltipLines(option.d());
/*    */     
/* 59 */     return lines;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String[] getTooltipLines(String key) {
/* 66 */     List<String> list = new ArrayList<String>();
/* 67 */     for (int i = 0; i < 10; i++) {
/*    */       
/* 69 */       String lineKey = key + ".tooltip." + (i + 1);
/* 70 */       String line = Lang.get(lineKey, null);
/* 71 */       if (line == null) {
/*    */         break;
/*    */       }
/* 74 */       list.add(line);
/*    */     } 
/*    */     
/* 77 */     if (list.size() <= 0) {
/* 78 */       return null;
/*    */     }
/* 80 */     String[] lines = list.<String>toArray(new String[list.size()]);
/*    */     
/* 82 */     return lines;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\TooltipProviderOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */