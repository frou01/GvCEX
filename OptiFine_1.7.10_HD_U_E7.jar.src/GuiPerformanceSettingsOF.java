/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiPerformanceSettingsOF
/*    */   extends bdw
/*    */ {
/*    */   private bdw prevScreen;
/*    */   protected String title;
/*    */   private bbj settings;
/* 15 */   private static bbm[] enumOptions = new bbm[] { bbm.SMOOTH_FPS, bbm.SMOOTH_WORLD, bbm.LOAD_FAR, bbm.PRELOADED_CHUNKS, bbm.CHUNK_UPDATES, bbm.CHUNK_UPDATES_DYNAMIC, bbm.FAST_MATH, bbm.LAZY_CHUNK_LOADING, bbm.FAST_RENDER };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());
/*    */ 
/*    */   
/*    */   public GuiPerformanceSettingsOF(bdw guiscreen, bbj gamesettings) {
/* 28 */     this.prevScreen = guiscreen;
/* 29 */     this.settings = gamesettings;
/*    */   }
/*    */ 
/*    */   
/*    */   public void b() {
/* 34 */     this.title = brp.a("of.options.performanceTitle", new Object[0]);
/* 35 */     this.n.clear();
/*    */     
/* 37 */     for (int i = 0; i < enumOptions.length; i++) {
/*    */       
/* 39 */       bbm enumoptions = enumOptions[i];
/*    */       
/* 41 */       int x = this.l / 2 - 155 + i % 2 * 160;
/* 42 */       int y = this.m / 6 + 21 * i / 2 - 12;
/* 43 */       if (!enumoptions.a()) {
/*    */         
/* 45 */         this.n.add(new GuiOptionButtonOF(enumoptions.c(), x, y, enumoptions, this.settings.c(enumoptions)));
/*    */       } else {
/*    */         
/* 48 */         this.n.add(new GuiOptionSliderOF(enumoptions.c(), x, y, enumoptions));
/*    */       } 
/*    */     } 
/*    */     
/* 52 */     this.n.add(new bcb(200, this.l / 2 - 100, this.m / 6 + 168 + 11, brp.a("gui.done", new Object[0])));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void a(bcb guibutton) {
/* 57 */     if (!guibutton.l) {
/*    */       return;
/*    */     }
/* 60 */     if (guibutton.k < 200 && guibutton instanceof bcj) {
/*    */       
/* 62 */       this.settings.a(((bcj)guibutton).d(), 1);
/* 63 */       guibutton.j = this.settings.c(bbm.a(guibutton.k));
/*    */     } 
/*    */     
/* 66 */     if (guibutton.k == 200) {
/*    */       
/* 68 */       this.k.u.b();
/* 69 */       this.k.a(this.prevScreen);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(int x, int y, float f) {
/* 75 */     c();
/* 76 */     a(this.q, this.title, this.l / 2, 15, 16777215);
/* 77 */     super.a(x, y, f);
/*    */     
/* 79 */     this.tooltipManager.drawTooltips(x, y, this.n);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\GuiPerformanceSettingsOF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */