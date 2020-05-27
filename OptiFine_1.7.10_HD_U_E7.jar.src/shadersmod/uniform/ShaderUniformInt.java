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
/*    */ public class ShaderUniformInt
/*    */   extends ShaderUniformBase
/*    */ {
/* 14 */   private int value = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ShaderUniformInt(String name) {
/* 21 */     super(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onProgramChanged() {
/* 28 */     this.value = -1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setValue(int value) {
/* 36 */     if (getLocation() < 0) {
/*    */       return;
/*    */     }
/* 39 */     if (this.value == value) {
/*    */       return;
/*    */     }
/* 42 */     ARBShaderObjects.glUniform1iARB(getLocation(), value);
/*    */     
/* 44 */     Shaders.checkGLError(getName());
/*    */     
/* 46 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getValue() {
/* 53 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmo\\uniform\ShaderUniformInt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */