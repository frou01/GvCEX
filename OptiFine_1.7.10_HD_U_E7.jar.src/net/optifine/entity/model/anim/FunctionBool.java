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
/*    */ public class FunctionBool
/*    */   implements IExpressionBool
/*    */ {
/*    */   private FunctionType type;
/*    */   private IExpression[] arguments;
/*    */   
/*    */   public FunctionBool(FunctionType type, IExpression[] arguments) {
/* 19 */     this.type = type;
/* 20 */     this.arguments = arguments;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean eval() {
/* 28 */     return this.type.evalBool(this.arguments);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ExpressionType getExpressionType() {
/* 35 */     return ExpressionType.BOOL;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 42 */     return "" + this.type + "()";
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\net\optifine\entity\model\anim\FunctionBool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */