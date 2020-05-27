/*     */ package shadersmod.client;
/*     */ 
/*     */ import Config;
/*     */ import GuiScreenOF;
/*     */ import Lang;
/*     */ import TooltipManager;
/*     */ import TooltipProvider;
/*     */ import TooltipProviderShaderOptions;
/*     */ import bbj;
/*     */ import bbu;
/*     */ import bcb;
/*     */ import bdw;
/*     */ import brp;
/*     */ import java.util.Iterator;
/*     */ import qh;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiShaderOptions
/*     */   extends GuiScreenOF
/*     */ {
/*     */   private bdw prevScreen;
/*     */   protected String title;
/*     */   private bbj settings;
/*  27 */   private TooltipManager tooltipManager = new TooltipManager((bdw)this, (TooltipProvider)new TooltipProviderShaderOptions());
/*     */   
/*  29 */   private String screenName = null;
/*  30 */   private String screenText = null;
/*     */ 
/*     */   
/*     */   private boolean changed = false;
/*     */   
/*     */   public static final String OPTION_PROFILE = "<profile>";
/*     */   
/*     */   public static final String OPTION_EMPTY = "<empty>";
/*     */   
/*     */   public static final String OPTION_REST = "*";
/*     */   
/*     */   private bbu fontRendererObj;
/*     */ 
/*     */   
/*     */   public GuiShaderOptions(bdw guiscreen, bbj gamesettings) {
/*  45 */     this.title = "Shader Options";
/*  46 */     this.prevScreen = guiscreen;
/*  47 */     this.settings = gamesettings;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiShaderOptions(bdw guiscreen, bbj gamesettings, String screenName) {
/*  54 */     this(guiscreen, gamesettings);
/*  55 */     this.screenName = screenName;
/*  56 */     if (screenName != null) {
/*  57 */       this.screenText = Shaders.translate("screen." + screenName, screenName);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void b() {
/*  63 */     this.fontRendererObj = this.q;
/*     */     
/*  65 */     this.title = brp.a("of.options.shaderOptionsTitle", new Object[0]);
/*     */     
/*  67 */     int baseId = 100;
/*     */     
/*  69 */     int baseX = 0;
/*  70 */     int baseY = 30;
/*  71 */     int stepY = 20;
/*  72 */     int btnWidth = 120;
/*  73 */     int btnHeight = 20;
/*  74 */     int columns = Shaders.getShaderPackColumns(this.screenName, 2);
/*     */     
/*  76 */     ShaderOption[] ops = Shaders.getShaderPackOptions(this.screenName);
/*  77 */     if (ops != null) {
/*     */ 
/*     */       
/*  80 */       int colsMin = qh.f(ops.length / 9.0D);
/*  81 */       if (columns < colsMin) {
/*  82 */         columns = colsMin;
/*     */       }
/*  84 */       for (int i = 0; i < ops.length; i++) {
/*     */         
/*  86 */         ShaderOption so = ops[i];
/*     */         
/*  88 */         if (so != null)
/*     */         {
/*     */           
/*  91 */           if (so.isVisible()) {
/*     */             GuiButtonShaderOption btn;
/*     */             
/*  94 */             int col = i % columns;
/*  95 */             int row = i / columns;
/*  96 */             int colWidth = Math.min(this.l / columns, 200);
/*  97 */             baseX = (this.l - colWidth * columns) / 2;
/*  98 */             int x = col * colWidth + 5 + baseX;
/*  99 */             int y = baseY + row * stepY;
/* 100 */             int w = colWidth - 10;
/* 101 */             int h = btnHeight;
/* 102 */             String text = getButtonText(so, w);
/*     */ 
/*     */             
/* 105 */             if (Shaders.isShaderPackOptionSlider(so.getName())) {
/* 106 */               btn = new GuiSliderShaderOption(baseId + i, x, y, w, h, so, text);
/*     */             } else {
/* 108 */               btn = new GuiButtonShaderOption(baseId + i, x, y, w, h, so, text);
/*     */             } 
/* 110 */             btn.l = so.isEnabled();
/* 111 */             this.n.add(btn);
/*     */           }  } 
/*     */       } 
/*     */     } 
/* 115 */     this.n.add(new bcb(201, this.l / 2 - btnWidth - 20, this.m / 6 + 168 + 11, btnWidth, btnHeight, brp.a("controls.reset", new Object[0])));
/*     */     
/* 117 */     this.n.add(new bcb(200, this.l / 2 + 20, this.m / 6 + 168 + 11, btnWidth, btnHeight, brp.a("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getButtonText(ShaderOption so, int btnWidth) {
/* 127 */     String labelName = so.getNameText();
/*     */     
/* 129 */     if (so instanceof ShaderOptionScreen) {
/*     */       
/* 131 */       ShaderOptionScreen soScr = (ShaderOptionScreen)so;
/* 132 */       return labelName + "...";
/*     */     } 
/* 134 */     bbu fr = (Config.getMinecraft()).l;
/*     */     
/* 136 */     int lenSuffix = fr.a(": " + Lang.getOff()) + 5;
/* 137 */     while (fr.a(labelName) + lenSuffix >= btnWidth && labelName.length() > 0) {
/* 138 */       labelName = labelName.substring(0, labelName.length() - 1);
/*     */     }
/* 140 */     String col = so.isChanged() ? so.getValueColor(so.getValue()) : "";
/* 141 */     String labelValue = so.getValueText(so.getValue());
/* 142 */     return labelName + ": " + col + labelValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(bcb guibutton) {
/* 150 */     if (!guibutton.l) {
/*     */       return;
/*     */     }
/* 153 */     if (guibutton.k < 200 && guibutton instanceof GuiButtonShaderOption) {
/*     */       
/* 155 */       GuiButtonShaderOption btnSo = (GuiButtonShaderOption)guibutton;
/* 156 */       ShaderOption so = btnSo.getShaderOption();
/*     */       
/* 158 */       if (so instanceof ShaderOptionScreen) {
/*     */         
/* 160 */         String screenName = so.getName();
/* 161 */         GuiShaderOptions scr = new GuiShaderOptions((bdw)this, this.settings, screenName);
/* 162 */         this.k.a((bdw)scr);
/*     */         
/*     */         return;
/*     */       } 
/* 166 */       if (r()) {
/* 167 */         so.resetValue();
/*     */       } else {
/* 169 */         so.nextValue();
/*     */       } 
/* 171 */       updateAllButtons();
/*     */       
/* 173 */       this.changed = true;
/*     */     } 
/*     */     
/* 176 */     if (guibutton.k == 201) {
/*     */ 
/*     */       
/* 179 */       ShaderOption[] opts = Shaders.getChangedOptions(Shaders.getShaderPackOptions());
/* 180 */       for (int i = 0; i < opts.length; i++) {
/*     */         
/* 182 */         ShaderOption opt = opts[i];
/*     */         
/* 184 */         opt.resetValue();
/*     */         
/* 186 */         this.changed = true;
/*     */       } 
/*     */       
/* 189 */       updateAllButtons();
/*     */     } 
/*     */     
/* 192 */     if (guibutton.k == 200) {
/*     */ 
/*     */       
/* 195 */       if (this.changed) {
/*     */ 
/*     */         
/* 198 */         Shaders.saveShaderPackOptions();
/* 199 */         this.changed = false;
/*     */         
/* 201 */         Shaders.uninit();
/*     */       } 
/*     */       
/* 204 */       this.k.a(this.prevScreen);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformedRightClick(bcb btn) {
/* 213 */     if (btn instanceof GuiButtonShaderOption) {
/*     */       
/* 215 */       GuiButtonShaderOption btnSo = (GuiButtonShaderOption)btn;
/*     */       
/* 217 */       ShaderOption so = btnSo.getShaderOption();
/*     */       
/* 219 */       if (r()) {
/* 220 */         so.resetValue();
/*     */       } else {
/* 222 */         so.prevValue();
/*     */       } 
/* 224 */       updateAllButtons();
/*     */       
/* 226 */       this.changed = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void m() {
/* 235 */     super.m();
/*     */     
/* 237 */     if (this.changed) {
/*     */ 
/*     */       
/* 240 */       Shaders.saveShaderPackOptions();
/* 241 */       this.changed = false;
/*     */       
/* 243 */       Shaders.uninit();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateAllButtons() {
/* 250 */     for (Iterator<bcb> it = this.n.iterator(); it.hasNext(); ) {
/*     */       
/* 252 */       bcb btn = it.next();
/*     */       
/* 254 */       if (btn instanceof GuiButtonShaderOption) {
/*     */         
/* 256 */         GuiButtonShaderOption gbso = (GuiButtonShaderOption)btn;
/* 257 */         ShaderOption opt = gbso.getShaderOption();
/*     */         
/* 259 */         if (opt instanceof ShaderOptionProfile) {
/*     */           
/* 261 */           ShaderOptionProfile optProf = (ShaderOptionProfile)opt;
/* 262 */           optProf.updateProfile();
/*     */         } 
/* 264 */         gbso.j = getButtonText(opt, gbso.b());
/*     */         
/* 266 */         gbso.valueChanged();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int x, int y, float f) {
/* 274 */     c();
/*     */     
/* 276 */     if (this.screenText != null) {
/* 277 */       a(this.fontRendererObj, this.screenText, this.l / 2, 15, 16777215);
/*     */     } else {
/* 279 */       a(this.fontRendererObj, this.title, this.l / 2, 15, 16777215);
/*     */     } 
/* 281 */     super.a(x, y, f);
/*     */     
/* 283 */     this.tooltipManager.drawTooltips(x, y, this.n);
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\GuiShaderOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */