/*    */ package shadersmod.uniform;
/*    */ 
/*    */ import net.optifine.entity.model.anim.IExpression;
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
/*    */ public class CustomUniform
/*    */ {
/*    */   private String name;
/*    */   private UniformType type;
/*    */   private IExpression expression;
/*    */   private ShaderUniformBase shaderUniform;
/*    */   
/*    */   public CustomUniform(String name, UniformType type, IExpression expression) {
/* 22 */     this.name = name;
/* 23 */     this.type = type;
/* 24 */     this.expression = expression;
/* 25 */     this.shaderUniform = type.makeShaderUniform(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setProgram(int program) {
/* 30 */     this.shaderUniform.setProgram(program);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update() {
/* 37 */     this.type.updateUniform(this.expression, this.shaderUniform);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 44 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public UniformType getType() {
/* 51 */     return this.type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IExpression getExpression() {
/* 58 */     return this.expression;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ShaderUniformBase getShaderUniform() {
/* 65 */     return this.shaderUniform;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmo\\uniform\CustomUniform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */