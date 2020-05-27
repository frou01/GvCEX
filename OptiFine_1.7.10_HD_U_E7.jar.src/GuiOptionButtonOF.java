/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiOptionButtonOF
/*    */   extends bcj
/*    */   implements IOptionControl
/*    */ {
/* 14 */   private bbm option = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GuiOptionButtonOF(int id, int x, int y, bbm option, String text) {
/* 20 */     super(id, x, y, option, text);
/* 21 */     this.option = option;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public bbm getOption() {
/* 29 */     return this.option;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\GuiOptionButtonOF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */