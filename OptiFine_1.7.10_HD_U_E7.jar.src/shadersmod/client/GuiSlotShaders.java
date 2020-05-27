/*     */ package shadersmod.client;
/*     */ 
/*     */ import Lang;
/*     */ import bcm;
/*     */ import bmh;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ class GuiSlotShaders
/*     */   extends bcm
/*     */ {
/*     */   private ArrayList shaderslist;
/*     */   private int selectedIndex;
/*  14 */   private long lastClickedCached = 0L;
/*     */   
/*     */   final GuiShaders shadersGui;
/*     */ 
/*     */   
/*     */   public GuiSlotShaders(GuiShaders par1GuiShaders, int width, int height, int top, int bottom, int slotHeight) {
/*  20 */     super(par1GuiShaders.getMc(), width, height, top, bottom, slotHeight);
/*  21 */     this.shadersGui = par1GuiShaders;
/*  22 */     updateList();
/*     */ 
/*     */     
/*  25 */     int posYSelected = this.selectedIndex * slotHeight;
/*  26 */     int wMid = (bottom - top) / 2;
/*  27 */     if (posYSelected > wMid) {
/*  28 */       f(posYSelected - wMid);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int c() {
/*  37 */     return this.a - 20;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateList() {
/*  42 */     this.shaderslist = Shaders.listOfShaders();
/*  43 */     this.selectedIndex = 0;
/*  44 */     for (int i = 0, n = this.shaderslist.size(); i < n; i++) {
/*     */       
/*  46 */       if (((String)this.shaderslist.get(i)).equals(Shaders.currentshadername)) {
/*     */         
/*  48 */         this.selectedIndex = i;
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int b() {
/*  58 */     return this.shaderslist.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(int index, boolean doubleClicked, int mouseX, int mouseY) {
/*  66 */     if (index == this.selectedIndex && this.lastClickedCached == System.currentTimeMillis()) {
/*     */       return;
/*     */     }
/*  69 */     this.selectedIndex = index;
/*     */     
/*  71 */     this.lastClickedCached = System.currentTimeMillis();
/*  72 */     Shaders.setShaderPack(this.shaderslist.get(index));
/*  73 */     Shaders.uninit();
/*     */     
/*  75 */     this.shadersGui.updateButtons();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean a(int index) {
/*  82 */     return (index == this.selectedIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int d() {
/*  89 */     return this.a - 6;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int e() {
/*  96 */     return b() * 18;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(int index, int posX, int posY, int contentY, bmh tess, int mouseX, int mouseY) {
/* 111 */     String label = this.shaderslist.get(index);
/*     */     
/* 113 */     if (label.equals(Shaders.packNameNone)) {
/* 114 */       label = Lang.get("of.options.shaders.packNone");
/* 115 */     } else if (label.equals(Shaders.packNameDefault)) {
/* 116 */       label = Lang.get("of.options.shaders.packDefault");
/*     */     } 
/* 118 */     this.shadersGui.drawCenteredString(label, this.a / 2, posY + 1, 14737632);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSelectedIndex() {
/* 126 */     return this.selectedIndex;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\GuiSlotShaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */