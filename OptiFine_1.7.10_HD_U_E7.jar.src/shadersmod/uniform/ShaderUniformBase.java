/*    */ package shadersmod.uniform;
/*    */ 
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ShaderUniformBase
/*    */ {
/*    */   private String name;
/* 14 */   private int program = -1;
/* 15 */   private int location = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ShaderUniformBase(String name) {
/* 22 */     this.name = name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setProgram(int program) {
/* 30 */     if (this.program == program) {
/*    */       return;
/*    */     }
/* 33 */     this.program = program;
/*    */     
/* 35 */     this.location = ARBShaderObjects.glGetUniformLocationARB(program, this.name);
/*    */     
/* 37 */     onProgramChanged();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract void onProgramChanged();
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 47 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getProgram() {
/* 54 */     return this.program;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLocation() {
/* 61 */     return this.location;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isDefined() {
/* 68 */     return (this.location >= 0);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmo\\uniform\ShaderUniformBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */