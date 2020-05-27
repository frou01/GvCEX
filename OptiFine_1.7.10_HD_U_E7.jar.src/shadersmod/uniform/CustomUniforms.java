/*    */ package shadersmod.uniform;
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
/*    */ public class CustomUniforms
/*    */ {
/*    */   private CustomUniform[] uniforms;
/*    */   
/*    */   public CustomUniforms(CustomUniform[] uniforms) {
/* 19 */     this.uniforms = uniforms;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setProgram(int program) {
/* 25 */     for (int i = 0; i < this.uniforms.length; i++) {
/*    */       
/* 27 */       CustomUniform uniform = this.uniforms[i];
/* 28 */       uniform.setProgram(program);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void update() {
/* 35 */     for (int i = 0; i < this.uniforms.length; i++) {
/*    */       
/* 37 */       CustomUniform uniform = this.uniforms[i];
/* 38 */       uniform.update();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmo\\uniform\CustomUniforms.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */