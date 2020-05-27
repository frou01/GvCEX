/*     */ import java.awt.Dimension;
/*     */ import java.awt.image.BufferedImage;
/*     */ import org.lwjgl.opengl.GL11;
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
/*     */ public class PlayerItemModel
/*     */ {
/*  24 */   private Dimension textureSize = null;
/*     */   private boolean usePlayerTexture = false;
/*  26 */   private PlayerItemRenderer[] modelRenderers = new PlayerItemRenderer[0];
/*     */   
/*  28 */   private bqx textureLocation = null;
/*  29 */   private BufferedImage textureImage = null;
/*  30 */   private bpq texture = null;
/*  31 */   private bqx locationMissing = new bqx("textures/blocks/wool_colored_red.png");
/*     */   
/*     */   public static final int ATTACH_BODY = 0;
/*     */   
/*     */   public static final int ATTACH_HEAD = 1;
/*     */   
/*     */   public static final int ATTACH_LEFT_ARM = 2;
/*     */   
/*     */   public static final int ATTACH_RIGHT_ARM = 3;
/*     */   
/*     */   public static final int ATTACH_LEFT_LEG = 4;
/*     */   
/*     */   public static final int ATTACH_RIGHT_LEG = 5;
/*     */   
/*     */   public static final int ATTACH_CAPE = 6;
/*     */ 
/*     */   
/*     */   public PlayerItemModel(Dimension textureSize, boolean usePlayerTexture, PlayerItemRenderer[] modelRenderers) {
/*  49 */     this.textureSize = textureSize;
/*  50 */     this.usePlayerTexture = usePlayerTexture;
/*  51 */     this.modelRenderers = modelRenderers;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(bhm modelBiped, blg player, float scale, float partialTicks) {
/*  56 */     bqf textureManager = Config.getTextureManager();
/*     */     
/*  58 */     if (this.usePlayerTexture) {
/*     */       
/*  60 */       textureManager.a(player.r());
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  65 */     else if (this.textureLocation != null) {
/*     */ 
/*     */       
/*  68 */       if (this.texture == null && this.textureImage != null) {
/*     */         
/*  70 */         this.texture = new bpq(this.textureImage);
/*  71 */         bao.B().P().a(this.textureLocation, (bqh)this.texture);
/*     */       } 
/*     */       
/*  74 */       textureManager.a(this.textureLocation);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  79 */       textureManager.a(this.locationMissing);
/*     */     } 
/*     */ 
/*     */     
/*  83 */     for (int i = 0; i < this.modelRenderers.length; i++) {
/*     */       
/*  85 */       PlayerItemRenderer pir = this.modelRenderers[i];
/*  86 */       GL11.glPushMatrix();
/*     */       
/*  88 */       if (player.an()) {
/*  89 */         GL11.glTranslatef(0.0F, 0.2F, 0.0F);
/*     */       }
/*  91 */       pir.render(modelBiped, scale);
/*     */       
/*  93 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static bix getAttachModel(bhm modelBiped, int attachTo) {
/* 105 */     switch (attachTo) {
/*     */       
/*     */       case 0:
/* 108 */         return modelBiped.e;
/*     */       case 1:
/* 110 */         return modelBiped.c;
/*     */       case 2:
/* 112 */         return modelBiped.g;
/*     */       case 3:
/* 114 */         return modelBiped.f;
/*     */       case 4:
/* 116 */         return modelBiped.i;
/*     */       case 5:
/* 118 */         return modelBiped.h;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferedImage getTextureImage() {
/* 132 */     return this.textureImage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextureImage(BufferedImage textureImage) {
/* 140 */     this.textureImage = textureImage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public bpq getTexture() {
/* 148 */     return this.texture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public bqx getTextureLocation() {
/* 156 */     return this.textureLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextureLocation(bqx textureLocation) {
/* 164 */     this.textureLocation = textureLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsePlayerTexture() {
/* 172 */     return this.usePlayerTexture;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\PlayerItemModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */