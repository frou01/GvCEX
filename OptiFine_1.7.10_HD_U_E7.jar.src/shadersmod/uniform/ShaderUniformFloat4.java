/*    */ package shadersmod.uniform;
/*    */ 
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ import shadersmod.client.Shaders;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShaderUniformFloat4
/*    */   extends ShaderUniformBase
/*    */ {
/* 15 */   private float[] values = new float[4];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ShaderUniformFloat4(String name) {
/* 22 */     super(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onProgramChanged() {
/* 29 */     this.values[0] = 0.0F;
/* 30 */     this.values[1] = 0.0F;
/* 31 */     this.values[2] = 0.0F;
/* 32 */     this.values[3] = 0.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setValue(float f0, float f1, float f2, float f3) {
/* 40 */     if (getLocation() < 0) {
/*    */       return;
/*    */     }
/* 43 */     if (this.values[0] == f0 && this.values[1] == f1 && this.values[2] == f2 && this.values[3] == f3) {
/*    */       return;
/*    */     }
/* 46 */     ARBShaderObjects.glUniform4fARB(getLocation(), f0, f1, f2, f3);
/*    */     
/* 48 */     Shaders.checkGLError(getName());
/*    */     
/* 50 */     this.values[0] = f0;
/* 51 */     this.values[1] = f1;
/* 52 */     this.values[2] = f2;
/* 53 */     this.values[3] = f3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float[] getValues() {
/* 60 */     return this.values;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmo\\uniform\ShaderUniformFloat4.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */