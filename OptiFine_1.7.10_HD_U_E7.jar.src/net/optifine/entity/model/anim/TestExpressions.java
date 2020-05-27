/*    */ package net.optifine.entity.model.anim;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStreamReader;
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
/*    */ public class TestExpressions
/*    */ {
/*    */   public static void main(String[] args) throws Exception {
/* 18 */     ExpressionParser ep = new ExpressionParser(null);
/*    */ 
/*    */     
/*    */     while (true) {
/*    */       try {
/* 23 */         InputStreamReader ir = new InputStreamReader(System.in);
/* 24 */         BufferedReader br = new BufferedReader(ir);
/* 25 */         String line = br.readLine();
/* 26 */         if (line.length() <= 0) {
/*    */           break;
/*    */         }
/* 29 */         IExpression expr = ep.parse(line);
/* 30 */         if (expr instanceof IExpressionFloat) {
/*    */           
/* 32 */           IExpressionFloat ef = (IExpressionFloat)expr;
/* 33 */           float val = ef.eval();
/* 34 */           System.out.println("" + val);
/*    */         } 
/* 36 */         if (expr instanceof IExpressionBool)
/*    */         {
/* 38 */           IExpressionBool eb = (IExpressionBool)expr;
/* 39 */           boolean val = eb.eval();
/* 40 */           System.out.println("" + val);
/*    */         }
/*    */       
/* 43 */       } catch (Exception e) {
/*    */         
/* 45 */         e.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\net\optifine\entity\model\anim\TestExpressions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */