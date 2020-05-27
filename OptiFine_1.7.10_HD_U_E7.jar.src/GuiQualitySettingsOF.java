/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiQualitySettingsOF
/*    */   extends bdw
/*    */ {
/*    */   private bdw prevScreen;
/*    */   protected String title;
/*    */   private bbj settings;
/* 16 */   private static bbm[] enumOptions = new bbm[] { bbm.F, bbm.MIPMAP_TYPE, bbm.G, bbm.AA_LEVEL, bbm.CLEAR_WATER, bbm.RANDOM_MOBS, bbm.BETTER_GRASS, bbm.BETTER_SNOW, bbm.CUSTOM_FONTS, bbm.CUSTOM_COLORS, bbm.SWAMP_COLORS, bbm.SMOOTH_BIOMES, bbm.CONNECTED_TEXTURES, bbm.NATURAL_TEXTURES, bbm.CUSTOM_SKY };
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
/* 28 */   private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());
/*    */ 
/*    */   
/*    */   public GuiQualitySettingsOF(bdw guiscreen, bbj gamesettings) {
/* 32 */     this.prevScreen = guiscreen;
/* 33 */     this.settings = gamesettings;
/*    */   }
/*    */ 
/*    */   
/*    */   public void b() {
/* 38 */     this.title = brp.a("of.options.qualityTitle", new Object[0]);
/* 39 */     this.n.clear();
/*    */     
/* 41 */     for (int i = 0; i < enumOptions.length; i++) {
/*    */       
/* 43 */       bbm opt = enumOptions[i];
/*    */       
/* 45 */       int x = this.l / 2 - 155 + i % 2 * 160;
/* 46 */       int y = this.m / 6 + 21 * i / 2 - 12;
/* 47 */       if (!opt.a()) {
/*    */         
/* 49 */         this.n.add(new GuiOptionButtonOF(opt.c(), x, y, opt, this.settings.c(opt)));
/*    */       } else {
/*    */         
/* 52 */         this.n.add(new GuiOptionSliderOF(opt.c(), x, y, opt));
/*    */       } 
/*    */     } 
/*    */     
/* 56 */     this.n.add(new bcb(200, this.l / 2 - 100, this.m / 6 + 168 + 11, brp.a("gui.done", new Object[0])));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void a(bcb guibutton) {
/* 61 */     if (!guibutton.l) {
/*    */       return;
/*    */     }
/* 64 */     if (guibutton.k < 200 && guibutton instanceof bcj) {
/*    */       
/* 66 */       this.settings.a(((bcj)guibutton).d(), 1);
/* 67 */       guibutton.j = this.settings.c(bbm.a(guibutton.k));
/*    */     } 
/*    */     
/* 70 */     if (guibutton.k == 200) {
/*    */       
/* 72 */       this.k.u.b();
/* 73 */       this.k.a(this.prevScreen);
/*    */     } 
/*    */     
/* 76 */     if (guibutton.k != bbm.AA_LEVEL.ordinal()) {
/*    */       
/* 78 */       bca sr = new bca(this.k, this.k.d, this.k.e);
/* 79 */       a(this.k, sr.a(), sr.b());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(int x, int y, float f) {
/* 85 */     c();
/* 86 */     a(this.q, this.title, this.l / 2, 15, 16777215);
/* 87 */     super.a(x, y, f);
/*    */     
/* 89 */     this.tooltipManager.drawTooltips(x, y, this.n);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\GuiQualitySettingsOF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */