/*    */ package net.optifine.entity.model.anim;
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
/*    */ public class Token
/*    */ {
/*    */   private TokenType type;
/*    */   private String text;
/*    */   
/*    */   public Token(TokenType type, String text) {
/* 21 */     this.type = type;
/* 22 */     this.text = text;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TokenType getType() {
/* 29 */     return this.type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getText() {
/* 36 */     return this.text;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 44 */     return this.text;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\net\optifine\entity\model\anim\Token.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */