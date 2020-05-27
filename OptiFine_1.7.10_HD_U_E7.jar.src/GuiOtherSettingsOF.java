/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiOtherSettingsOF
/*     */   extends bdw
/*     */   implements bcv
/*     */ {
/*     */   private bdw prevScreen;
/*     */   protected String title;
/*     */   private bbj settings;
/*  18 */   private static bbm[] enumOptions = new bbm[] { bbm.LAGOMETER, bbm.PROFILER, bbm.SHOW_FPS, bbm.AUTOSAVE_TICKS, bbm.WEATHER, bbm.TIME, bbm.x, bbm.FULLSCREEN_MODE, bbm.h };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  27 */   private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());
/*     */ 
/*     */   
/*     */   public GuiOtherSettingsOF(bdw guiscreen, bbj gamesettings) {
/*  31 */     this.prevScreen = guiscreen;
/*  32 */     this.settings = gamesettings;
/*     */   }
/*     */ 
/*     */   
/*     */   public void b() {
/*  37 */     this.title = brp.a("of.options.otherTitle", new Object[0]);
/*  38 */     this.n.clear();
/*     */     
/*  40 */     for (int i = 0; i < enumOptions.length; i++) {
/*     */       
/*  42 */       bbm enumoptions = enumOptions[i];
/*     */       
/*  44 */       int x = this.l / 2 - 155 + i % 2 * 160;
/*  45 */       int y = this.m / 6 + 21 * i / 2 - 12;
/*  46 */       if (!enumoptions.a()) {
/*     */         
/*  48 */         this.n.add(new GuiOptionButtonOF(enumoptions.c(), x, y, enumoptions, this.settings.c(enumoptions)));
/*     */       } else {
/*     */         
/*  51 */         this.n.add(new GuiOptionSliderOF(enumoptions.c(), x, y, enumoptions));
/*     */       } 
/*     */     } 
/*     */     
/*  55 */     this.n.add(new bcb(210, this.l / 2 - 100, this.m / 6 + 168 + 11 - 44, brp.a("of.options.other.reset", new Object[0])));
/*     */     
/*  57 */     this.n.add(new bcb(200, this.l / 2 - 100, this.m / 6 + 168 + 11, brp.a("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(bcb guibutton) {
/*  62 */     if (!guibutton.l) {
/*     */       return;
/*     */     }
/*  65 */     if (guibutton.k < 200 && guibutton instanceof bcj) {
/*     */       
/*  67 */       this.settings.a(((bcj)guibutton).d(), 1);
/*  68 */       guibutton.j = this.settings.c(bbm.a(guibutton.k));
/*     */     } 
/*     */     
/*  71 */     if (guibutton.k == 200) {
/*     */       
/*  73 */       this.k.u.b();
/*  74 */       this.k.a(this.prevScreen);
/*     */     } 
/*     */     
/*  77 */     if (guibutton.k == 210) {
/*     */       
/*  79 */       this.k.u.b();
/*  80 */       bcw guiyesno = new bcw(this, brp.a("of.message.other.reset", new Object[0]), "", 9999);
/*  81 */       this.k.a((bdw)guiyesno);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(boolean flag, int i) {
/*  88 */     if (flag) {
/*  89 */       this.k.u.resetSettings();
/*     */     }
/*  91 */     this.k.a(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(int x, int y, float f) {
/*  96 */     c();
/*  97 */     a(this.q, this.title, this.l / 2, 15, 16777215);
/*  98 */     super.a(x, y, f);
/*     */     
/* 100 */     this.tooltipManager.drawTooltips(x, y, this.n);
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\GuiOtherSettingsOF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */