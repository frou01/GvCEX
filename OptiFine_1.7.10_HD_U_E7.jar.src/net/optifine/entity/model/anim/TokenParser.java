/*    */ package net.optifine.entity.model.anim;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.PushbackReader;
/*    */ import java.io.Reader;
/*    */ import java.io.StringReader;
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
/*    */ public class TokenParser
/*    */ {
/*    */   public static Token[] parse(String str) throws IOException, ParseException {
/* 20 */     Reader r = new StringReader(str);
/* 21 */     PushbackReader pr = new PushbackReader(r);
/* 22 */     List<Token> list = new ArrayList<Token>();
/*    */     
/*    */     while (true) {
/* 25 */       int i = pr.read();
/* 26 */       if (i < 0) {
/*    */         break;
/*    */       }
/* 29 */       char ch = (char)i;
/*    */       
/* 31 */       if (Character.isWhitespace(ch)) {
/*    */         continue;
/*    */       }
/* 34 */       TokenType type = TokenType.getTypeByFirstChar(ch);
/* 35 */       if (type == null) {
/* 36 */         throw new ParseException("Invalid character: '" + ch + "', in: " + str);
/*    */       }
/* 38 */       Token token = readToken(ch, type, pr);
/*    */       
/* 40 */       list.add(token);
/*    */     } 
/*    */     
/* 43 */     Token[] tokens = list.<Token>toArray(new Token[list.size()]);
/*    */     
/* 45 */     return tokens;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static Token readToken(char chFirst, TokenType type, PushbackReader pr) throws IOException {
/* 56 */     StringBuffer sb = new StringBuffer();
/* 57 */     sb.append(chFirst);
/*    */     
/*    */     while (true) {
/* 60 */       int i = pr.read();
/* 61 */       if (i < 0) {
/*    */         break;
/*    */       }
/* 64 */       char ch = (char)i;
/*    */       
/* 66 */       if (!type.hasCharNext(ch)) {
/*    */         
/* 68 */         pr.unread(ch);
/*    */         
/*    */         break;
/*    */       } 
/* 72 */       sb.append(ch);
/*    */     } 
/*    */     
/* 75 */     return new Token(type, sb.toString());
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\net\optifine\entity\model\anim\TokenParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */