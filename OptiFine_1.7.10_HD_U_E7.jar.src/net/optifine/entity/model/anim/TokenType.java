/*    */ package net.optifine.entity.model.anim;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum TokenType
/*    */ {
/* 11 */   IDENTIFIER("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_:."),
/* 12 */   NUMBER("0123456789", "0123456789."),
/* 13 */   OPERATOR("+-*/%!&|<>=", "&|="),
/* 14 */   COMMA(","),
/* 15 */   BRACKET_OPEN("("),
/* 16 */   BRACKET_CLOSE(")");
/*    */   private String charsFirst;
/*    */   private String charsNext;
/*    */   public static final TokenType[] VALUES;
/*    */   
/*    */   private static class Const {
/*    */     static final String ALPHAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
/*    */     static final String DIGITS = "0123456789";
/*    */   }
/*    */   
/*    */   static {
/* 27 */     VALUES = values();
/*    */   }
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
/*    */   TokenType(String charsFirst, String charsNext) {
/* 41 */     this.charsFirst = charsFirst;
/* 42 */     this.charsNext = charsNext;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCharsFirst() {
/* 49 */     return this.charsFirst;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCharsNext() {
/* 56 */     return this.charsNext;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static TokenType getTypeByFirstChar(char ch) {
/* 64 */     for (int i = 0; i < VALUES.length; i++) {
/*    */       
/* 66 */       TokenType type = VALUES[i];
/* 67 */       if (type.getCharsFirst().indexOf(ch) >= 0) {
/* 68 */         return type;
/*    */       }
/*    */     } 
/* 71 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasCharNext(char ch) {
/* 80 */     return (this.charsNext.indexOf(ch) >= 0);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\net\optifine\entity\model\anim\TokenType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */