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
/*    */ public class ConstantFloat
/*    */   implements IExpressionFloat
/*    */ {
/*    */   private float value;
/*    */   
/*    */   public ConstantFloat(float value) {
/* 17 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float eval() {
/* 24 */     return this.value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ExpressionType getExpressionType() {
/* 31 */     return ExpressionType.FLOAT;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 38 */     return "" + this.value;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\net\optifine\entity\model\anim\ConstantFloat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */