/*    */ import java.awt.Rectangle;
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
/*    */ public class TooltipManager
/*    */ {
/*    */   private bdw guiScreen;
/*    */   private TooltipProvider tooltipProvider;
/* 23 */   private int lastMouseX = 0;
/* 24 */   private int lastMouseY = 0;
/* 25 */   private long mouseStillTime = 0L;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TooltipManager(bdw guiScreen, TooltipProvider tooltipProvider) {
/* 32 */     this.guiScreen = guiScreen;
/* 33 */     this.tooltipProvider = tooltipProvider;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawTooltips(int x, int y, List<bcb> buttonList) {
/* 39 */     if (Math.abs(x - this.lastMouseX) > 5 || Math.abs(y - this.lastMouseY) > 5) {
/*    */       
/* 41 */       this.lastMouseX = x;
/* 42 */       this.lastMouseY = y;
/* 43 */       this.mouseStillTime = System.currentTimeMillis();
/*    */       
/*    */       return;
/*    */     } 
/* 47 */     int activateDelay = 700;
/* 48 */     if (System.currentTimeMillis() < this.mouseStillTime + activateDelay) {
/*    */       return;
/*    */     }
/* 51 */     bcb btn = GuiScreenOF.getSelectedButton(x, y, buttonList);
/* 52 */     if (btn == null) {
/*    */       return;
/*    */     }
/* 55 */     Rectangle rect = this.tooltipProvider.getTooltipBounds(this.guiScreen, x, y);
/*    */     
/* 57 */     String[] lines = this.tooltipProvider.getTooltipLines(btn, rect.width);
/* 58 */     if (lines == null) {
/*    */       return;
/*    */     }
/* 61 */     if (this.tooltipProvider.isRenderBorder()) {
/*    */       
/* 63 */       int colBorder = -528449408;
/* 64 */       drawRectBorder(rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, colBorder);
/*    */     } 
/*    */     
/* 67 */     bbw.a(rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, -536870912);
/* 68 */     for (int i = 0; i < lines.length; i++) {
/*    */       
/* 70 */       String line = lines[i];
/* 71 */       int col = 14540253;
/* 72 */       if (line.endsWith("!"))
/* 73 */         col = 16719904; 
/* 74 */       bbu fontRenderer = (bao.B()).l;
/* 75 */       fontRenderer.a(line, rect.x + 5, rect.y + 5 + i * 11, col);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void drawRectBorder(int x1, int y1, int x2, int y2, int col) {
/* 82 */     bbw.a(x1, y1 - 1, x2, y1, col);
/* 83 */     bbw.a(x1, y2, x2, y2 + 1, col);
/* 84 */     bbw.a(x1 - 1, y1, x1, y2, col);
/* 85 */     bbw.a(x2, y1, x2 + 1, y2, col);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\TooltipManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */