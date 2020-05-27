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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiScreenOF
/*    */   extends bdw
/*    */ {
/*    */   protected void actionPerformedRightClick(bcb button) {}
/*    */   
/*    */   protected void a(int mouseX, int mouseY, int mouseButton) {
/* 29 */     super.a(mouseX, mouseY, mouseButton);
/*    */     
/* 31 */     if (mouseButton == 1) {
/*    */       
/* 33 */       bcb btn = getSelectedButton(mouseX, mouseY, this.n);
/* 34 */       if (btn != null)
/*    */       {
/*    */         
/* 37 */         if (btn.l) {
/*    */ 
/*    */           
/* 40 */           btn.a(this.k.X());
/* 41 */           actionPerformedRightClick(btn);
/*    */         } 
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static bcb getSelectedButton(int x, int y, List<bcb> listButtons) {
/* 53 */     for (int i = 0; i < listButtons.size(); i++) {
/*    */       
/* 55 */       bcb btn = listButtons.get(i);
/*    */       
/* 57 */       if (btn.m) {
/*    */ 
/*    */         
/* 60 */         int btnWidth = bef.getButtonWidth(btn);
/* 61 */         int btnHeight = bef.getButtonHeight(btn);
/*    */         
/* 63 */         if (x >= btn.h && y >= btn.i && x < btn.h + btnWidth && y < btn.i + btnHeight)
/* 64 */           return btn; 
/*    */       } 
/*    */     } 
/* 67 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\GuiScreenOF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */