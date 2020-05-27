/*    */ package shadersmod.client;
/*    */ 
/*    */ import GlStateManager;
/*    */ import bao;
/*    */ import qh;
/*    */ 
/*    */ public class GuiSliderShaderOption
/*    */   extends GuiButtonShaderOption {
/*    */   private float sliderValue;
/*    */   public boolean dragging;
/* 11 */   private ShaderOption shaderOption = null;
/*    */ 
/*    */   
/*    */   public GuiSliderShaderOption(int buttonId, int x, int y, int w, int h, ShaderOption shaderOption, String text) {
/* 15 */     super(buttonId, x, y, w, h, shaderOption, text);
/* 16 */     this.sliderValue = 1.0F;
/* 17 */     this.shaderOption = shaderOption;
/* 18 */     this.sliderValue = shaderOption.getIndexNormalized();
/* 19 */     this.j = GuiShaderOptions.getButtonText(shaderOption, this.f);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int a(boolean mouseOver) {
/* 28 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void b(bao mc, int mouseX, int mouseY) {
/* 36 */     if (this.m) {
/*    */       
/* 38 */       if (this.dragging) {
/*    */         
/* 40 */         this.sliderValue = (mouseX - this.h + 4) / (this.f - 8);
/* 41 */         this.sliderValue = qh.a(this.sliderValue, 0.0F, 1.0F);
/*    */         
/* 43 */         this.shaderOption.setIndexNormalized(this.sliderValue);
/*    */         
/* 45 */         this.sliderValue = this.shaderOption.getIndexNormalized();
/*    */         
/* 47 */         this.j = GuiShaderOptions.getButtonText(this.shaderOption, this.f);
/*    */       } 
/*    */       
/* 50 */       mc.P().a(a);
/* 51 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 52 */       b(this.h + (int)(this.sliderValue * (this.f - 8)), this.i, 0, 66, 4, 20);
/* 53 */       b(this.h + (int)(this.sliderValue * (this.f - 8)) + 4, this.i, 196, 66, 4, 20);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean c(bao mc, int mouseX, int mouseY) {
/* 63 */     if (super.c(mc, mouseX, mouseY)) {
/*    */       
/* 65 */       this.sliderValue = (mouseX - this.h + 4) / (this.f - 8);
/* 66 */       this.sliderValue = qh.a(this.sliderValue, 0.0F, 1.0F);
/*    */       
/* 68 */       this.shaderOption.setIndexNormalized(this.sliderValue);
/*    */       
/* 70 */       this.j = GuiShaderOptions.getButtonText(this.shaderOption, this.f);
/*    */       
/* 72 */       this.dragging = true;
/* 73 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 77 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(int mouseX, int mouseY) {
/* 86 */     this.dragging = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void valueChanged() {
/* 96 */     this.sliderValue = this.shaderOption.getIndexNormalized();
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\GuiSliderShaderOption.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */