/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiAnimationSettingsOF
/*    */   extends bdw
/*    */ {
/*    */   private bdw prevScreen;
/*    */   protected String title;
/*    */   private bbj settings;
/* 16 */   private static bbm[] enumOptions = new bbm[] { bbm.ANIMATED_WATER, bbm.ANIMATED_LAVA, bbm.ANIMATED_FIRE, bbm.ANIMATED_PORTAL, bbm.ANIMATED_REDSTONE, bbm.ANIMATED_EXPLOSION, bbm.ANIMATED_FLAME, bbm.ANIMATED_SMOKE, bbm.VOID_PARTICLES, bbm.WATER_PARTICLES, bbm.RAIN_SPLASH, bbm.PORTAL_PARTICLES, bbm.POTION_PARTICLES, bbm.DRIPPING_WATER_LAVA, bbm.ANIMATED_TERRAIN, bbm.ANIMATED_ITEMS, bbm.ANIMATED_TEXTURES, bbm.q };
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
/*    */   
/*    */   public GuiAnimationSettingsOF(bdw guiscreen, bbj gamesettings) {
/* 31 */     this.prevScreen = guiscreen;
/* 32 */     this.settings = gamesettings;
/*    */   }
/*    */ 
/*    */   
/*    */   public void b() {
/* 37 */     this.title = brp.a("of.options.animationsTitle", new Object[0]);
/* 38 */     this.n.clear();
/*    */     
/* 40 */     for (int i = 0; i < enumOptions.length; i++) {
/*    */       
/* 42 */       bbm opt = enumOptions[i];
/*    */       
/* 44 */       int x = this.l / 2 - 155 + i % 2 * 160;
/* 45 */       int y = this.m / 6 + 21 * i / 2 - 12;
/* 46 */       if (!opt.a()) {
/*    */         
/* 48 */         this.n.add(new GuiOptionButtonOF(opt.c(), x, y, opt, this.settings.c(opt)));
/*    */       } else {
/*    */         
/* 51 */         this.n.add(new GuiOptionSliderOF(opt.c(), x, y, opt));
/*    */       } 
/*    */     } 
/*    */     
/* 55 */     this.n.add(new bcb(210, this.l / 2 - 155, this.m / 6 + 168 + 11, 70, 20, Lang.get("of.options.animation.allOn")));
/* 56 */     this.n.add(new bcb(211, this.l / 2 - 155 + 80, this.m / 6 + 168 + 11, 70, 20, Lang.get("of.options.animation.allOff")));
/*    */     
/* 58 */     this.n.add(new bcj(200, this.l / 2 + 5, this.m / 6 + 168 + 11, brp.a("gui.done", new Object[0])));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void a(bcb guibutton) {
/* 63 */     if (!guibutton.l) {
/*    */       return;
/*    */     }
/* 66 */     if (guibutton.k < 200 && guibutton instanceof bcj) {
/*    */       
/* 68 */       this.settings.a(((bcj)guibutton).d(), 1);
/* 69 */       guibutton.j = this.settings.c(bbm.a(guibutton.k));
/*    */     } 
/*    */     
/* 72 */     if (guibutton.k == 200) {
/*    */       
/* 74 */       this.k.u.b();
/* 75 */       this.k.a(this.prevScreen);
/*    */     } 
/*    */     
/* 78 */     if (guibutton.k == 210)
/*    */     {
/* 80 */       this.k.u.setAllAnimations(true);
/*    */     }
/*    */     
/* 83 */     if (guibutton.k == 211)
/*    */     {
/* 85 */       this.k.u.setAllAnimations(false);
/*    */     }
/*    */     
/* 88 */     bca sr = new bca(this.k, this.k.d, this.k.e);
/* 89 */     a(this.k, sr.a(), sr.b());
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(int x, int y, float f) {
/* 94 */     c();
/* 95 */     a(this.q, this.title, this.l / 2, 15, 16777215);
/* 96 */     super.a(x, y, f);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\GuiAnimationSettingsOF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */