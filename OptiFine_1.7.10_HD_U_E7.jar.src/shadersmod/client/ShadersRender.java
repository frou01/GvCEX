/*     */ package shadersmod.client;
/*     */ 
/*     */ import Config;
/*     */ import GlStateManager;
/*     */ import Reflector;
/*     */ import apo;
/*     */ import baj;
/*     */ import bam;
/*     */ import bao;
/*     */ import blo;
/*     */ import blt;
/*     */ import bly;
/*     */ import bma;
/*     */ import bmh;
/*     */ import bmv;
/*     */ import bmx;
/*     */ import bmy;
/*     */ import bpz;
/*     */ import bqx;
/*     */ import java.nio.IntBuffer;
/*     */ import org.lwjgl.opengl.EXTFramebufferObject;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ import org.lwjgl.opengl.GL30;
/*     */ import sv;
/*     */ import yz;
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
/*     */ public class ShadersRender
/*     */ {
/*  50 */   private static final bqx END_PORTAL_TEXTURE = new bqx("textures/entity/end_portal.png");
/*     */ 
/*     */   
/*     */   public static void setFrustrumPosition(bmx frustrum, double x, double y, double z) {
/*  54 */     frustrum.a(x, y, z);
/*     */   }
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
/*     */   public static void beginTerrainSolid() {
/*  94 */     if (Shaders.isRenderingWorld) {
/*     */       
/*  96 */       Shaders.fogEnabled = true;
/*  97 */       Shaders.useProgram(7);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginTerrainCutoutMipped() {
/* 103 */     if (Shaders.isRenderingWorld)
/*     */     {
/* 105 */       Shaders.useProgram(7);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginTerrainCutout() {
/* 111 */     if (Shaders.isRenderingWorld)
/*     */     {
/* 113 */       Shaders.useProgram(7);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endTerrain() {
/* 119 */     if (Shaders.isRenderingWorld)
/*     */     {
/* 121 */       Shaders.useProgram(3);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginTranslucent() {
/* 127 */     if (Shaders.isRenderingWorld) {
/*     */       
/* 129 */       if (Shaders.usedDepthBuffers >= 2) {
/*     */ 
/*     */         
/* 132 */         GlStateManager.setActiveTexture(33995);
/* 133 */         Shaders.checkGLError("pre copy depth");
/* 134 */         GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.renderWidth, Shaders.renderHeight);
/* 135 */         Shaders.checkGLError("copy depth");
/* 136 */         GlStateManager.setActiveTexture(33984);
/*     */       } 
/* 138 */       Shaders.useProgram(12);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endTranslucent() {
/* 144 */     if (Shaders.isRenderingWorld)
/*     */     {
/* 146 */       Shaders.useProgram(3);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderHand0(blt er, float par1, int par2) {
/* 153 */     if (!Shaders.isShadowPass) {
/*     */ 
/*     */       
/* 156 */       boolean blockTranslucentMain = Shaders.isItemToRenderMainTranslucent();
/*     */       
/* 158 */       if (!blockTranslucentMain) {
/*     */ 
/*     */         
/* 161 */         Shaders.readCenterDepth();
/* 162 */         Shaders.beginHand(false);
/* 163 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */         
/* 165 */         er.renderHand(par1, par2, true, false, false);
/* 166 */         Shaders.endHand();
/*     */         
/* 168 */         Shaders.setHandRenderedMain(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderHand1(blt er, float par1, int par2) {
/* 175 */     if (!Shaders.isShadowPass)
/*     */     {
/*     */       
/* 178 */       if (!Shaders.isHandRenderedMain()) {
/*     */ 
/*     */         
/* 181 */         Shaders.readCenterDepth();
/* 182 */         GlStateManager.enableBlend();
/* 183 */         Shaders.beginHand(true);
/* 184 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */         
/* 186 */         er.renderHand(par1, par2, true, false, true);
/* 187 */         Shaders.endHand();
/*     */         
/* 189 */         Shaders.setHandRenderedMain(true);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderItemFP(bly itemRenderer, float par1, boolean renderTranslucent) {
/* 197 */     Shaders.setRenderingFirstPersonHand(true);
/*     */     
/* 199 */     GlStateManager.depthMask(true);
/*     */ 
/*     */     
/* 202 */     if (renderTranslucent) {
/*     */ 
/*     */       
/* 205 */       GlStateManager.depthFunc(519);
/* 206 */       GL11.glPushMatrix();
/* 207 */       IntBuffer drawBuffers = Shaders.activeDrawBuffers;
/* 208 */       Shaders.setDrawBuffers(Shaders.drawBuffersNone);
/*     */       
/* 210 */       Shaders.renderItemKeepDepthMask = true;
/* 211 */       itemRenderer.a(par1);
/* 212 */       Shaders.renderItemKeepDepthMask = false;
/* 213 */       Shaders.setDrawBuffers(drawBuffers);
/* 214 */       GL11.glPopMatrix();
/*     */     } 
/*     */     
/* 217 */     GlStateManager.depthFunc(515);
/*     */ 
/*     */     
/* 220 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 221 */     itemRenderer.a(par1);
/*     */     
/* 223 */     Shaders.setRenderingFirstPersonHand(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderFPOverlay(blt er, float par1, int par2) {
/* 228 */     if (!Shaders.isShadowPass) {
/*     */       
/* 230 */       Shaders.beginFPOverlay();
/* 231 */       er.renderHand(par1, par2, false, true, false);
/* 232 */       Shaders.endFPOverlay();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginBlockDamage() {
/* 238 */     if (Shaders.isRenderingWorld) {
/*     */       
/* 240 */       Shaders.useProgram(11);
/* 241 */       if (Shaders.programsID[11] == Shaders.programsID[7]) {
/*     */         
/* 243 */         Shaders.setDrawBuffers(Shaders.drawBuffersColorAtt0);
/* 244 */         GlStateManager.depthMask(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endBlockDamage() {
/* 251 */     if (Shaders.isRenderingWorld) {
/*     */       
/* 253 */       GlStateManager.depthMask(true);
/* 254 */       Shaders.useProgram(3);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void renderShadowMap(blt entityRenderer, int pass, float partialTicks, long finishTimeNano) {
/* 259 */     if (Shaders.usedShadowDepthBuffers > 0 && --Shaders.shadowPassCounter <= 0) {
/*     */ 
/*     */       
/* 262 */       bao mc = bao.B();
/* 263 */       mc.z.c("shadow pass");
/*     */       
/* 265 */       bma renderGlobal = mc.g;
/* 266 */       Shaders.isShadowPass = true;
/* 267 */       Shaders.shadowPassCounter = Shaders.shadowPassInterval;
/* 268 */       Shaders.preShadowPassThirdPersonView = mc.u.aw;
/* 269 */       mc.u.aw = 1;
/*     */       
/* 271 */       Shaders.checkGLError("pre shadow");
/* 272 */       GL11.glMatrixMode(5889);
/* 273 */       GL11.glPushMatrix();
/* 274 */       GL11.glMatrixMode(5888);
/* 275 */       GL11.glPushMatrix();
/*     */       
/* 277 */       mc.z.c("shadow clear");
/* 278 */       EXTFramebufferObject.glBindFramebufferEXT(36160, Shaders.sfb);
/* 279 */       Shaders.checkGLError("shadow bind sfb");
/* 280 */       Shaders.useProgram(30);
/*     */       
/* 282 */       mc.z.c("shadow camera");
/* 283 */       entityRenderer.a(partialTicks, 2);
/* 284 */       Shaders.setCameraShadow(partialTicks);
/* 285 */       baj.a((yz)mc.h, (mc.u.aw == 2));
/* 286 */       Shaders.checkGLError("shadow camera");
/*     */ 
/*     */       
/* 289 */       GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
/* 290 */       Shaders.checkGLError("shadow drawbuffers");
/* 291 */       GL11.glReadBuffer(0);
/* 292 */       Shaders.checkGLError("shadow readbuffer");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 298 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, Shaders.sfbDepthTextures.get(0), 0);
/* 299 */       if (Shaders.usedShadowColorBuffers != 0)
/*     */       {
/* 301 */         EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064, 3553, Shaders.sfbColorTextures.get(0), 0);
/*     */       }
/* 303 */       Shaders.checkFramebufferStatus("shadow fb");
/* 304 */       GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 305 */       GL11.glClear((Shaders.usedShadowColorBuffers != 0) ? 16640 : 256);
/* 306 */       Shaders.checkGLError("shadow clear");
/*     */       
/* 308 */       mc.z.c("shadow frustum");
/* 309 */       bmy clippingHelper = ClippingHelperShadow.getInstance();
/* 310 */       mc.z.c("shadow culling");
/* 311 */       bmx frustum = new bmx(clippingHelper);
/*     */ 
/*     */ 
/*     */       
/* 315 */       sv viewEntity = mc.i;
/* 316 */       double viewPosX = viewEntity.S + (viewEntity.s - viewEntity.S) * partialTicks;
/* 317 */       double viewPosY = viewEntity.T + (viewEntity.t - viewEntity.T) * partialTicks;
/* 318 */       double viewPosZ = viewEntity.U + (viewEntity.u - viewEntity.U) * partialTicks;
/* 319 */       frustum.a(viewPosX, viewPosY, viewPosZ);
/*     */       
/* 321 */       GlStateManager.shadeModel(7425);
/* 322 */       GlStateManager.enableDepth();
/* 323 */       GlStateManager.depthFunc(515);
/* 324 */       GlStateManager.depthMask(true);
/* 325 */       GlStateManager.colorMask(true, true, true, true);
/*     */       
/* 327 */       GlStateManager.disableCull();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 332 */       mc.z.c("shadow prepareterrain");
/* 333 */       mc.P().a(bpz.b);
/*     */       
/* 335 */       mc.z.c("shadow setupterrain");
/* 336 */       int frameCount = 0;
/* 337 */       frameCount = entityRenderer.frameCount;
/* 338 */       entityRenderer.frameCount = frameCount + 1;
/*     */ 
/*     */       
/* 341 */       blo[] worldRenderers = renderGlobal.v;
/* 342 */       for (int i = 0; i < worldRenderers.length; i++) {
/*     */         
/* 344 */         if (!worldRenderers[i].d())
/*     */         {
/* 346 */           (worldRenderers[i]).l = true;
/*     */         }
/*     */       } 
/* 349 */       mc.z.c("shadow updatechunks");
/*     */ 
/*     */       
/* 352 */       mc.z.c("shadow terrain");
/*     */       
/* 354 */       GlStateManager.matrixMode(5888);
/* 355 */       GlStateManager.pushMatrix();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 361 */       GlStateManager.enableAlpha();
/*     */       
/* 363 */       renderGlobal.renderAllSortedRenderers(0, partialTicks);
/* 364 */       Shaders.checkGLError("shadow terrain cutoutmipped");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 370 */       GlStateManager.shadeModel(7424);
/* 371 */       GlStateManager.alphaFunc(516, 0.1F);
/*     */       
/* 373 */       GlStateManager.matrixMode(5888);
/* 374 */       GlStateManager.popMatrix();
/* 375 */       GlStateManager.pushMatrix();
/*     */       
/* 377 */       mc.z.c("shadow entities");
/*     */ 
/*     */       
/* 380 */       if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
/* 381 */         Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(0) });
/*     */       }
/* 383 */       bam.b();
/* 384 */       renderGlobal.a(viewEntity, (bmv)frustum, partialTicks);
/* 385 */       bam.a();
/* 386 */       Shaders.checkGLError("shadow entities");
/*     */       
/* 388 */       GlStateManager.matrixMode(5888);
/* 389 */       GlStateManager.popMatrix();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 395 */       GlStateManager.depthMask(true);
/*     */       
/* 397 */       GlStateManager.disableBlend();
/* 398 */       GlStateManager.enableCull();
/* 399 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 400 */       GlStateManager.alphaFunc(516, 0.1F);
/*     */       
/* 402 */       if (Shaders.usedShadowDepthBuffers >= 2) {
/*     */ 
/*     */         
/* 405 */         GlStateManager.setActiveTexture(33989);
/* 406 */         Shaders.checkGLError("pre copy shadow depth");
/* 407 */         GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.shadowMapWidth, Shaders.shadowMapHeight);
/* 408 */         Shaders.checkGLError("copy shadow depth");
/* 409 */         GlStateManager.setActiveTexture(33984);
/*     */       } 
/*     */ 
/*     */       
/* 413 */       GlStateManager.disableBlend();
/* 414 */       GlStateManager.depthMask(true);
/* 415 */       mc.P().a(bpz.b);
/* 416 */       GlStateManager.shadeModel(7425);
/* 417 */       Shaders.checkGLError("shadow pre-translucent");
/* 418 */       GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
/* 419 */       Shaders.checkGLError("shadow drawbuffers pre-translucent");
/* 420 */       Shaders.checkFramebufferStatus("shadow pre-translucent");
/*     */       
/* 422 */       if (Shaders.isRenderShadowTranslucent()) {
/*     */         
/* 424 */         mc.z.c("shadow translucent");
/*     */ 
/*     */         
/* 427 */         renderGlobal.renderAllSortedRenderers(1, partialTicks);
/* 428 */         Shaders.checkGLError("shadow translucent");
/*     */       } 
/*     */ 
/*     */       
/* 432 */       if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
/*     */         
/* 434 */         bam.b();
/* 435 */         Reflector.call(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(1) });
/* 436 */         renderGlobal.a(viewEntity, (bmv)frustum, partialTicks);
/* 437 */         Reflector.call(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(-1) });
/* 438 */         bam.a();
/* 439 */         Shaders.checkGLError("shadow entities 1");
/*     */       } 
/*     */       
/* 442 */       GlStateManager.shadeModel(7424);
/* 443 */       GlStateManager.depthMask(true);
/* 444 */       GlStateManager.enableCull();
/* 445 */       GlStateManager.disableBlend();
/*     */ 
/*     */ 
/*     */       
/* 449 */       GL11.glFlush();
/* 450 */       Shaders.checkGLError("shadow flush");
/*     */       
/* 452 */       Shaders.isShadowPass = false;
/* 453 */       mc.u.aw = Shaders.preShadowPassThirdPersonView;
/*     */       
/* 455 */       mc.z.c("shadow postprocess");
/*     */       
/* 457 */       if (Shaders.hasGlGenMipmap) {
/*     */         
/* 459 */         if (Shaders.usedShadowDepthBuffers >= 1) {
/*     */           
/* 461 */           if (Shaders.shadowMipmapEnabled[0]) {
/*     */             
/* 463 */             GlStateManager.setActiveTexture(33988);
/* 464 */             GlStateManager.bindTexture(Shaders.sfbDepthTextures.get(0));
/* 465 */             GL30.glGenerateMipmap(3553);
/* 466 */             GL11.glTexParameteri(3553, 10241, Shaders.shadowFilterNearest[0] ? 9984 : 9987);
/*     */           } 
/* 468 */           if (Shaders.usedShadowDepthBuffers >= 2)
/*     */           {
/* 470 */             if (Shaders.shadowMipmapEnabled[1]) {
/*     */               
/* 472 */               GlStateManager.setActiveTexture(33989);
/* 473 */               GlStateManager.bindTexture(Shaders.sfbDepthTextures.get(1));
/* 474 */               GL30.glGenerateMipmap(3553);
/* 475 */               GL11.glTexParameteri(3553, 10241, Shaders.shadowFilterNearest[1] ? 9984 : 9987);
/*     */             } 
/*     */           }
/* 478 */           GlStateManager.setActiveTexture(33984);
/*     */         } 
/* 480 */         if (Shaders.usedShadowColorBuffers >= 1) {
/*     */           
/* 482 */           if (Shaders.shadowColorMipmapEnabled[0]) {
/*     */             
/* 484 */             GlStateManager.setActiveTexture(33997);
/* 485 */             GlStateManager.bindTexture(Shaders.sfbColorTextures.get(0));
/* 486 */             GL30.glGenerateMipmap(3553);
/* 487 */             GL11.glTexParameteri(3553, 10241, Shaders.shadowColorFilterNearest[0] ? 9984 : 9987);
/*     */           } 
/* 489 */           if (Shaders.usedShadowColorBuffers >= 2)
/*     */           {
/* 491 */             if (Shaders.shadowColorMipmapEnabled[1]) {
/*     */               
/* 493 */               GlStateManager.setActiveTexture(33998);
/* 494 */               GlStateManager.bindTexture(Shaders.sfbColorTextures.get(1));
/* 495 */               GL30.glGenerateMipmap(3553);
/* 496 */               GL11.glTexParameteri(3553, 10241, Shaders.shadowColorFilterNearest[1] ? 9984 : 9987);
/*     */             } 
/*     */           }
/* 499 */           GlStateManager.setActiveTexture(33984);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 505 */       Shaders.checkGLError("shadow postprocess");
/* 506 */       EXTFramebufferObject.glBindFramebufferEXT(36160, Shaders.dfb);
/* 507 */       GL11.glViewport(0, 0, Shaders.renderWidth, Shaders.renderHeight);
/* 508 */       Shaders.activeDrawBuffers = null;
/* 509 */       mc.P().a(bpz.b);
/* 510 */       Shaders.useProgram(7);
/* 511 */       GL11.glMatrixMode(5888);
/* 512 */       GL11.glPopMatrix();
/* 513 */       GL11.glMatrixMode(5889);
/* 514 */       GL11.glPopMatrix();
/* 515 */       GL11.glMatrixMode(5888);
/* 516 */       Shaders.checkGLError("shadow end");
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
/*     */   public static void beaconBeamBegin() {
/* 570 */     Shaders.useProgram(14);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void beaconBeamStartQuad1() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void beaconBeamStartQuad2() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void beaconBeamDraw1() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void beaconBeamDraw2() {
/* 598 */     GlStateManager.disableBlend();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderEnchantedGlintBegin() {
/* 603 */     Shaders.useProgram(17);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderEnchantedGlintEnd() {
/* 608 */     if (Shaders.isRenderingWorld) {
/*     */ 
/*     */       
/* 611 */       if (Shaders.isRenderingFirstPersonHand() && Shaders.isRenderBothHands()) {
/* 612 */         Shaders.useProgram(19);
/*     */       } else {
/* 614 */         Shaders.useProgram(16);
/*     */       } 
/*     */     } else {
/* 617 */       Shaders.useProgram(0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean renderEndPortal(apo te, double x, double y, double z, float partialTicks, int destroyStage, float offset) {
/* 622 */     if (!Shaders.isShadowPass && Shaders.programsID[Shaders.activeProgram] == 0) {
/* 623 */       return false;
/*     */     }
/* 625 */     GlStateManager.disableLighting();
/*     */     
/* 627 */     Config.getTextureManager().a(END_PORTAL_TEXTURE);
/*     */     
/* 629 */     bmh vertexbuffer = bmh.a;
/* 630 */     vertexbuffer.a(7);
/*     */     
/* 632 */     float col = 0.5F;
/* 633 */     float r = col * 0.15F;
/* 634 */     float g = col * 0.3F;
/* 635 */     float b = col * 0.4F;
/* 636 */     float u0 = 0.0F;
/* 637 */     float u1 = 0.2F;
/* 638 */     float v0 = u0;
/* 639 */     float v1 = u1;
/* 640 */     float du = (float)(System.currentTimeMillis() % 100000L) / 100000.0F;
/* 641 */     float dv = du;
/* 642 */     float dy = offset;
/* 643 */     int lu = 240;
/* 644 */     int lv = lu;
/*     */     
/* 646 */     vertexbuffer.a(r, g, b, 1.0F);
/* 647 */     vertexbuffer.b(lu << 16 | lv);
/* 648 */     vertexbuffer.a(x, y + dy, z + 1.0D, (u0 + du), (v0 + dv));
/* 649 */     vertexbuffer.a(x + 1.0D, y + dy, z + 1.0D, (u0 + du), (v1 + dv));
/* 650 */     vertexbuffer.a(x + 1.0D, y + dy, z, (u1 + du), (v1 + dv));
/* 651 */     vertexbuffer.a(x, y + dy, z, (u1 + du), (v0 + dv));
/*     */     
/* 653 */     vertexbuffer.a();
/*     */     
/* 655 */     GlStateManager.enableLighting();
/*     */     
/* 657 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\ShadersRender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */