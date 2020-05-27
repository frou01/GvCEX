/*     */ import java.lang.reflect.Field;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import shadersmod.client.Shaders;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemRendererOF
/*     */   extends bly
/*     */ {
/*  30 */   private bao mc = null;
/*     */   
/*  32 */   private blm renderBlocksIr = null;
/*  33 */   private static final bqx RES_ITEM_GLINT = new bqx("textures/misc/enchanted_item_glint.png");
/*     */   
/*  35 */   private static Field ItemRenderer_renderBlockInstance = Reflector.getField(bly.class, blm.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemRendererOF(bao par1Minecraft) {
/*  42 */     super(par1Minecraft);
/*  43 */     this.mc = par1Minecraft;
/*     */ 
/*     */     
/*  46 */     if (ItemRenderer_renderBlockInstance == null) {
/*  47 */       Config.error("ItemRenderOF not initialized");
/*     */     }
/*     */     
/*     */     try {
/*  51 */       this.renderBlocksIr = (blm)ItemRenderer_renderBlockInstance.get(this);
/*     */     }
/*  53 */     catch (IllegalAccessException e) {
/*     */ 
/*     */       
/*  56 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(sv par1EntityLivingBase, add par2ItemStack, int par3) {
/*  65 */     GL11.glPushMatrix();
/*  66 */     bqf var4 = this.mc.P();
/*  67 */     adb var5 = par2ItemStack.b();
/*  68 */     aji var6 = aji.a(var5);
/*     */     
/*  70 */     if (par2ItemStack != null && var6 != null && var6.w() != 0) {
/*     */       
/*  72 */       GL11.glEnable(3042);
/*  73 */       GL11.glEnable(2884);
/*  74 */       buu.c(770, 771, 1, 0);
/*     */     } 
/*     */ 
/*     */     
/*  78 */     Object type = null;
/*  79 */     Object customRenderer = null;
/*  80 */     if (Reflector.MinecraftForgeClient_getItemRenderer.exists()) {
/*     */       
/*  82 */       type = Reflector.getFieldValue(Reflector.ItemRenderType_EQUIPPED);
/*  83 */       customRenderer = Reflector.call(Reflector.MinecraftForgeClient_getItemRenderer, new Object[] { par2ItemStack, type });
/*     */     } 
/*  85 */     if (customRenderer != null) {
/*     */       
/*  87 */       bqf texturemanager = var4;
/*  88 */       texturemanager.a(texturemanager.a(par2ItemStack.d()));
/*  89 */       Reflector.callVoid(Reflector.ForgeHooksClient_renderEquippedItem, new Object[] { type, customRenderer, this.renderBlocksIr, par1EntityLivingBase, par2ItemStack });
/*     */     
/*     */     }
/*  92 */     else if (par2ItemStack.d() == 0 && var5 instanceof abh && blm.a(var6.b())) {
/*     */       
/*  94 */       var4.a(var4.a(0));
/*     */       
/*  96 */       if (par2ItemStack != null && var6 != null && var6.w() != 0)
/*     */       {
/*     */         
/*  99 */         if (!Config.isShaders() || !Shaders.renderItemKeepDepthMask)
/*     */         {
/* 101 */           GL11.glDepthMask(false); } 
/* 102 */         this.renderBlocksIr.a(var6, par2ItemStack.k(), 1.0F);
/* 103 */         GL11.glDepthMask(true);
/*     */       }
/*     */       else
/*     */       {
/* 107 */         this.renderBlocksIr.a(var6, par2ItemStack.k(), 1.0F);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 112 */       rf var7 = par1EntityLivingBase.b(par2ItemStack, par3);
/*     */       
/* 114 */       if (var7 == null) {
/*     */         
/* 116 */         GL11.glPopMatrix();
/*     */         
/*     */         return;
/*     */       } 
/* 120 */       var4.a(var4.a(par2ItemStack.d()));
/* 121 */       bqi.a(false, false, 1.0F);
/* 122 */       bmh var8 = bmh.a;
/* 123 */       float var9 = var7.c();
/* 124 */       float var10 = var7.d();
/* 125 */       float var11 = var7.e();
/* 126 */       float var12 = var7.f();
/* 127 */       float var13 = 0.0F;
/* 128 */       float var14 = 0.3F;
/* 129 */       GL11.glEnable(32826);
/* 130 */       GL11.glTranslatef(-var13, -var14, 0.0F);
/* 131 */       float var15 = 1.5F;
/* 132 */       GL11.glScalef(var15, var15, var15);
/* 133 */       GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
/* 134 */       GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
/* 135 */       GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
/* 136 */       a(var8, var10, var11, var9, var12, var7.a(), var7.b(), 0.0625F);
/*     */ 
/*     */       
/* 139 */       boolean renderEffect = false;
/* 140 */       if (Reflector.ForgeItemStack_hasEffect.exists()) {
/* 141 */         renderEffect = Reflector.callBoolean(par2ItemStack, Reflector.ForgeItemStack_hasEffect, new Object[] { Integer.valueOf(par3) });
/*     */       } else {
/* 143 */         renderEffect = (par2ItemStack.v() && par3 == 0);
/*     */       } 
/* 145 */       if (renderEffect) {
/*     */         
/* 147 */         GL11.glDepthFunc(514);
/* 148 */         GL11.glDisable(2896);
/* 149 */         var4.a(RES_ITEM_GLINT);
/* 150 */         GL11.glEnable(3042);
/* 151 */         buu.c(768, 1, 1, 0);
/* 152 */         float var16 = 0.76F;
/* 153 */         GL11.glColor4f(0.5F * var16, 0.25F * var16, 0.8F * var16, 1.0F);
/* 154 */         GL11.glMatrixMode(5890);
/* 155 */         GL11.glPushMatrix();
/* 156 */         float var17 = 0.125F;
/* 157 */         GL11.glScalef(var17, var17, var17);
/* 158 */         float var18 = (float)(bao.K() % 3000L) / 3000.0F * 8.0F;
/* 159 */         GL11.glTranslatef(var18, 0.0F, 0.0F);
/* 160 */         GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
/*     */         
/* 162 */         a(var8, 0.0F, 0.0F, 1.0F, 1.0F, 16, 16, 0.0625F);
/* 163 */         GL11.glPopMatrix();
/* 164 */         GL11.glPushMatrix();
/* 165 */         GL11.glScalef(var17, var17, var17);
/* 166 */         var18 = (float)(bao.K() % 4873L) / 4873.0F * 8.0F;
/* 167 */         GL11.glTranslatef(-var18, 0.0F, 0.0F);
/* 168 */         GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
/*     */         
/* 170 */         a(var8, 0.0F, 0.0F, 1.0F, 1.0F, 16, 16, 0.0625F);
/* 171 */         GL11.glPopMatrix();
/* 172 */         GL11.glMatrixMode(5888);
/* 173 */         GL11.glDisable(3042);
/* 174 */         GL11.glEnable(2896);
/* 175 */         GL11.glDepthFunc(515);
/*     */       } 
/*     */       
/* 178 */       GL11.glDisable(32826);
/* 179 */       var4.a(var4.a(par2ItemStack.d()));
/* 180 */       bqi.b();
/*     */     } 
/*     */     
/* 183 */     if (par2ItemStack != null && var6 != null && var6.w() != 0)
/*     */     {
/* 185 */       GL11.glDisable(3042);
/*     */     }
/*     */     
/* 188 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(float par1) {
/* 195 */     this.mc.f.renderItemInFirstPerson = true;
/*     */     
/* 197 */     super.a(par1);
/*     */     
/* 199 */     this.mc.f.renderItemInFirstPerson = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a() {
/* 206 */     super.a();
/*     */     
/* 208 */     if (Config.isShaders())
/* 209 */       Shaders.setItemToRenderMain((add)Reflector.getFieldValue(this, Reflector.ItemRenderer_itemToRender)); 
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\ItemRendererOF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */