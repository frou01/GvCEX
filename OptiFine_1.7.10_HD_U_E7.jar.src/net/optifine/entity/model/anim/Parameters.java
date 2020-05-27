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
/*    */ public class Parameters
/*    */   implements IParameters
/*    */ {
/*    */   private ExpressionType[] parameterTypes;
/*    */   
/*    */   public Parameters(ExpressionType[] parameterTypes) {
/* 17 */     this.parameterTypes = parameterTypes;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ExpressionType[] getParameterTypes(IExpression[] params) {
/* 25 */     return this.parameterTypes;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\net\optifine\entity\model\anim\Parameters.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */