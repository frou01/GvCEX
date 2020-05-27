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
/*    */ public class FunctionFloat
/*    */   implements IExpressionFloat
/*    */ {
/*    */   private FunctionType type;
/*    */   private IExpression[] arguments;
/*    */   
/*    */   public FunctionFloat(FunctionType type, IExpression[] arguments) {
/* 18 */     this.type = type;
/* 19 */     this.arguments = arguments;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float eval() {
/* 27 */     return this.type.evalFloat(this.arguments);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ExpressionType getExpressionType() {
/* 34 */     return ExpressionType.FLOAT;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 42 */     return "" + this.type + "()";
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\net\optifine\entity\model\anim\FunctionFloat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */