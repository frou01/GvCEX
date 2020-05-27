/*    */ import java.awt.Rectangle;
/*    */ import shadersmod.client.EnumShaderOption;
/*    */ import shadersmod.client.GuiButtonEnumShaderOption;
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
/*    */ public class TooltipProviderEnumShaderOptions
/*    */   implements TooltipProvider
/*    */ {
/*    */   public Rectangle getTooltipBounds(bdw guiScreen, int x, int y) {
/* 25 */     int x1 = guiScreen.l - 450;
/* 26 */     int y1 = 35;
/*    */     
/* 28 */     if (x1 < 10) {
/* 29 */       x1 = 10;
/*    */     }
/* 31 */     if (y <= y1 + 94) {
/* 32 */       y1 += 100;
/*    */     }
/* 34 */     int x2 = x1 + 150 + 150;
/* 35 */     int y2 = y1 + 84 + 10;
/*    */     
/* 37 */     return new Rectangle(x1, y1, x2 - x1, y2 - y1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isRenderBorder() {
/* 45 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getTooltipLines(bcb btn, int width) {
/* 54 */     if (!(btn instanceof GuiButtonEnumShaderOption)) {
/* 55 */       return null;
/*    */     }
/* 57 */     GuiButtonEnumShaderOption gbeso = (GuiButtonEnumShaderOption)btn;
/* 58 */     EnumShaderOption option = gbeso.getEnumShaderOption();
/* 59 */     String[] lines = getTooltipLines(option);
/*    */     
/* 61 */     return lines;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private String[] getTooltipLines(EnumShaderOption option) {
/* 70 */     return TooltipProviderOptions.getTooltipLines(option.getResourceKey());
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\TooltipProviderEnumShaderOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */