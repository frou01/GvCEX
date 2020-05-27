/*     */ import java.util.HashSet;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldRendererSmooth
/*     */   extends blo
/*     */ {
/*  28 */   private WrUpdateState updateState = new WrUpdateState();
/*     */   
/*  30 */   public int activeSet = 0;
/*  31 */   public int[] activeListIndex = new int[] { 0, 0 };
/*  32 */   public int[][][] glWorkLists = new int[2][2][16];
/*  33 */   public boolean[] tempSkipRenderPass = new boolean[2];
/*     */ 
/*     */   
/*     */   public bmi tempVertexState;
/*     */ 
/*     */   
/*     */   public WorldRendererSmooth(ahb par1World, List par2List, int par3, int par4, int par5, int par6) {
/*  40 */     super(par1World, par2List, par3, par4, par5, par6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkGlWorkLists() {
/*  50 */     if (this.glWorkLists[0][0][0] > 0) {
/*     */       return;
/*     */     }
/*     */     
/*  54 */     int glWorkBase = this.renderGlobal.displayListAllocator.allocateDisplayLists(64);
/*  55 */     for (int set = 0; set < 2; set++) {
/*     */       
/*  57 */       int setBase = glWorkBase + set * 2 * 16;
/*  58 */       for (int pass = 0; pass < 2; pass++) {
/*     */         
/*  60 */         int passBase = setBase + pass * 16;
/*  61 */         for (int t = 0; t < 16; t++)
/*     */         {
/*  63 */           this.glWorkLists[set][pass][t] = passBase + t;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int px, int py, int pz) {
/*  75 */     if (this.isUpdating) {
/*  76 */       WrUpdates.finishCurrentUpdate();
/*     */     }
/*  78 */     super.a(px, py, pz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateRenderer() {
/*  87 */     if (this.a == null) {
/*     */       return;
/*     */     }
/*  90 */     updateRenderer(0L);
/*  91 */     finishUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean updateRenderer(long finishTime) {
/* 100 */     if (this.a == null) {
/* 101 */       return true;
/*     */     }
/* 103 */     this.q = false;
/*     */     
/* 105 */     if (!this.isUpdating) {
/*     */ 
/*     */       
/* 108 */       if (this.needsBoxUpdate) {
/*     */ 
/*     */ 
/*     */         
/* 112 */         GL11.glNewList(this.z + 2, 4864);
/* 113 */         bny.a(azt.a(this.i, this.j, this.k, (this.i + 16), (this.j + 16), (this.k + 16)));
/* 114 */         GL11.glEndList();
/*     */         
/* 116 */         this.needsBoxUpdate = false;
/*     */       } 
/*     */       
/* 119 */       if (Reflector.LightCache.exists()) {
/*     */ 
/*     */         
/* 122 */         Object lightCache = Reflector.getFieldValue(Reflector.LightCache_cache);
/* 123 */         Reflector.callVoid(lightCache, Reflector.LightCache_clear, new Object[0]);
/*     */         
/* 125 */         Reflector.callVoid(Reflector.BlockCoord_resetPool, new Object[0]);
/*     */       } 
/*     */       
/* 128 */       apx.a = false;
/*     */     } 
/*     */ 
/*     */     
/* 132 */     int xMin = this.c;
/* 133 */     int yMin = this.d;
/* 134 */     int zMin = this.e;
/* 135 */     int xMax = this.c + 16;
/* 136 */     int yMax = this.d + 16;
/* 137 */     int zMax = this.e + 16;
/*     */ 
/*     */     
/* 140 */     ChunkCacheOF chunkcache = null;
/* 141 */     blm renderblocks = null;
/* 142 */     HashSet<?> setOldEntityRenders = null;
/* 143 */     int viewEntityPosX = 0;
/* 144 */     int viewEntityPosY = 0;
/* 145 */     int viewEntityPosZ = 0;
/*     */     
/* 147 */     if (!this.isUpdating) {
/*     */ 
/*     */       
/* 150 */       for (int i = 0; i < 2; i++)
/*     */       {
/* 152 */         this.tempSkipRenderPass[i] = true;
/*     */       }
/*     */       
/* 155 */       this.tempVertexState = null;
/*     */       
/* 157 */       bao mc = bao.B();
/* 158 */       sv viewEntity = mc.i;
/* 159 */       viewEntityPosX = qh.c(viewEntity.s);
/* 160 */       viewEntityPosY = qh.c(viewEntity.t);
/* 161 */       viewEntityPosZ = qh.c(viewEntity.u);
/*     */       
/* 163 */       int one = 1;
/*     */       
/* 165 */       chunkcache = new ChunkCacheOF(this.a, xMin - one, yMin - one, zMin - one, xMax + one, yMax + one, zMax + one, one);
/*     */       
/* 167 */       renderblocks = new blm((ahl)chunkcache);
/*     */       
/* 169 */       Reflector.callVoid(Reflector.ForgeHooksClient_setWorldRendererRB, new Object[] { renderblocks });
/*     */       
/* 171 */       setOldEntityRenders = new HashSet();
/* 172 */       setOldEntityRenders.addAll(this.x);
/* 173 */       this.x.clear();
/*     */     } 
/*     */     
/* 176 */     if (this.isUpdating || !chunkcache.T()) {
/*     */       
/* 178 */       this.D = 0;
/*     */       
/* 180 */       if (!this.isUpdating) {
/* 181 */         chunkcache.renderStart();
/*     */       }
/* 183 */       this.A = bmh.a;
/* 184 */       boolean hasForge = Reflector.ForgeHooksClient.exists();
/*     */       
/* 186 */       checkGlWorkLists();
/*     */       
/* 188 */       for (int renderPass = 0; renderPass < 2; renderPass++) {
/*     */         
/* 190 */         boolean renderNextPass = false;
/* 191 */         boolean hasRenderedBlocks = false;
/* 192 */         boolean hasGlList = false;
/* 193 */         for (int y = yMin; y < yMax; y++) {
/*     */ 
/*     */           
/* 196 */           if (this.isUpdating) {
/*     */ 
/*     */ 
/*     */             
/* 200 */             this.isUpdating = false;
/*     */             
/* 202 */             chunkcache = this.updateState.chunkcache;
/* 203 */             renderblocks = this.updateState.renderblocks;
/*     */             
/* 205 */             Reflector.callVoid(Reflector.ForgeHooksClient_setWorldRendererRB, new Object[] { renderblocks });
/*     */             
/* 207 */             setOldEntityRenders = this.updateState.setOldEntityRenders;
/* 208 */             viewEntityPosX = this.updateState.viewEntityPosX;
/* 209 */             viewEntityPosY = this.updateState.viewEntityPosY;
/* 210 */             viewEntityPosZ = this.updateState.viewEntityPosZ;
/* 211 */             renderPass = this.updateState.renderPass;
/* 212 */             y = this.updateState.y;
/* 213 */             renderNextPass = this.updateState.flag;
/* 214 */             hasRenderedBlocks = this.updateState.hasRenderedBlocks;
/* 215 */             hasGlList = this.updateState.hasGlList;
/*     */             
/* 217 */             if (hasGlList)
/*     */             {
/*     */               
/* 220 */               preRenderBlocksSmooth(renderPass);
/*     */ 
/*     */             
/*     */             }
/*     */           
/*     */           }
/* 226 */           else if (hasGlList && finishTime != 0L && System.nanoTime() - finishTime > 0L && this.activeListIndex[renderPass] < 15) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 234 */             if (hasRenderedBlocks) {
/* 235 */               this.tempSkipRenderPass[renderPass] = false;
/*     */             }
/* 237 */             postRenderBlocksSmooth(renderPass, this.renderGlobal.renderViewEntity, false);
/*     */             
/* 239 */             this.activeListIndex[renderPass] = this.activeListIndex[renderPass] + 1;
/*     */             
/* 241 */             this.updateState.chunkcache = chunkcache;
/* 242 */             this.updateState.renderblocks = renderblocks;
/* 243 */             this.updateState.setOldEntityRenders = setOldEntityRenders;
/* 244 */             this.updateState.viewEntityPosX = viewEntityPosX;
/* 245 */             this.updateState.viewEntityPosY = viewEntityPosY;
/* 246 */             this.updateState.viewEntityPosZ = viewEntityPosZ;
/* 247 */             this.updateState.renderPass = renderPass;
/* 248 */             this.updateState.y = y;
/* 249 */             this.updateState.flag = renderNextPass;
/* 250 */             this.updateState.hasRenderedBlocks = hasRenderedBlocks;
/* 251 */             this.updateState.hasGlList = hasGlList;
/*     */ 
/*     */             
/* 254 */             this.isUpdating = true;
/* 255 */             return false;
/*     */           } 
/*     */ 
/*     */           
/* 259 */           for (int z = zMin; z < zMax; z++) {
/*     */             
/* 261 */             for (int x = xMin; x < xMax; x++) {
/*     */ 
/*     */               
/* 264 */               aji block = chunkcache.a(x, y, z);
/*     */               
/* 266 */               if (block.o() != awt.a) {
/*     */                 
/* 268 */                 if (!hasGlList) {
/*     */                   
/* 270 */                   hasGlList = true;
/*     */                   
/* 272 */                   preRenderBlocksSmooth(renderPass);
/*     */                 } 
/*     */ 
/*     */                 
/* 276 */                 boolean hasTileEntity = false;
/* 277 */                 if (hasForge) {
/* 278 */                   hasTileEntity = Reflector.callBoolean(block, Reflector.ForgeBlock_hasTileEntity, new Object[] { Integer.valueOf(chunkcache.e(x, y, z)) });
/*     */                 } else {
/* 280 */                   hasTileEntity = block.u();
/*     */                 } 
/* 282 */                 if (renderPass == 0 && hasTileEntity) {
/*     */                   
/* 284 */                   aor var25 = chunkcache.o(x, y, z);
/*     */                   
/* 286 */                   if (bmk.a.a(var25))
/*     */                   {
/* 288 */                     this.x.add(var25);
/*     */                   }
/*     */                 } 
/*     */                 
/* 292 */                 int blockPass = block.w();
/*     */                 
/* 294 */                 if (blockPass > renderPass)
/*     */                 {
/* 296 */                   renderNextPass = true;
/*     */                 }
/*     */                 
/* 299 */                 boolean canRender = (blockPass == renderPass);
/* 300 */                 if (Reflector.ForgeBlock_canRenderInPass.exists()) {
/* 301 */                   canRender = Reflector.callBoolean(block, Reflector.ForgeBlock_canRenderInPass, new Object[] { Integer.valueOf(renderPass) });
/*     */                 }
/* 303 */                 if (canRender) {
/*     */                   
/* 305 */                   hasRenderedBlocks |= renderblocks.b(block, x, y, z);
/*     */                   
/* 307 */                   if (block.b() == 0 && x == viewEntityPosX && y == viewEntityPosY && z == viewEntityPosZ) {
/*     */                     
/* 309 */                     renderblocks.a(true);
/* 310 */                     renderblocks.b(true);
/* 311 */                     renderblocks.b(block, x, y, z);
/* 312 */                     renderblocks.a(false);
/* 313 */                     renderblocks.b(false);
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 321 */         if (hasRenderedBlocks)
/*     */         {
/*     */           
/* 324 */           this.tempSkipRenderPass[renderPass] = false;
/*     */         }
/*     */         
/* 327 */         if (hasGlList) {
/*     */ 
/*     */ 
/*     */           
/* 331 */           postRenderBlocksSmooth(renderPass, this.renderGlobal.renderViewEntity, true);
/*     */         }
/*     */         else {
/*     */           
/* 335 */           hasRenderedBlocks = false;
/*     */         } 
/*     */         
/* 338 */         if (!renderNextPass) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 344 */       Reflector.callVoid(Reflector.ForgeHooksClient_setWorldRendererRB, new Object[] { null });
/*     */       
/* 346 */       chunkcache.renderFinish();
/*     */     } 
/*     */     
/* 349 */     HashSet setNewEntityRenderers = new HashSet();
/* 350 */     setNewEntityRenderers.addAll(this.x);
/* 351 */     setNewEntityRenderers.removeAll(setOldEntityRenders);
/* 352 */     this.C.addAll(setNewEntityRenderers);
/* 353 */     setOldEntityRenders.removeAll(this.x);
/* 354 */     this.C.removeAll(setOldEntityRenders);
/* 355 */     this.w = apx.a;
/* 356 */     this.B = true;
/*     */     
/* 358 */     b++;
/*     */     
/* 360 */     this.t = true;
/* 361 */     this.isVisibleFromPosition = false;
/*     */     
/* 363 */     this.m[0] = this.tempSkipRenderPass[0];
/* 364 */     this.m[1] = this.tempSkipRenderPass[1];
/*     */     
/* 366 */     this.skipAllRenderPasses = (this.m[0] && this.m[1]);
/*     */     
/* 368 */     this.y = this.tempVertexState;
/*     */     
/* 370 */     this.isUpdating = false;
/*     */     
/* 372 */     updateFinished();
/*     */     
/* 374 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void preRenderBlocksSmooth(int renderpass) {
/* 380 */     GL11.glNewList(this.glWorkLists[this.activeSet][renderpass][this.activeListIndex[renderpass]], 4864);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 385 */     this.A.setRenderingChunk(true);
/*     */     
/* 387 */     if (Config.isFastRender()) {
/*     */ 
/*     */       
/* 390 */       Reflector.callVoid(Reflector.ForgeHooksClient_onPreRenderWorld, new Object[] { this, Integer.valueOf(renderpass) });
/*     */       
/* 392 */       this.A.b();
/*     */       
/* 394 */       this.A.b(-globalChunkOffsetX, 0.0D, -globalChunkOffsetZ);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 399 */     GL11.glPushMatrix();
/* 400 */     f();
/* 401 */     float var2 = 1.000001F;
/* 402 */     GL11.glTranslatef(-8.0F, -8.0F, -8.0F);
/* 403 */     GL11.glScalef(var2, var2, var2);
/* 404 */     GL11.glTranslatef(8.0F, 8.0F, 8.0F);
/*     */     
/* 406 */     Reflector.callVoid(Reflector.ForgeHooksClient_onPreRenderWorld, new Object[] { this, Integer.valueOf(renderpass) });
/*     */     
/* 408 */     this.A.b();
/* 409 */     this.A.b(-this.c, -this.d, -this.e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postRenderBlocksSmooth(int renderpass, sv entityLiving, boolean updateFinished) {
/* 416 */     if (Config.isTranslucentBlocksFancy() && renderpass == 1 && !this.tempSkipRenderPass[renderpass]) {
/*     */       
/* 418 */       bmi tsv = this.A.a((float)entityLiving.s, (float)entityLiving.t, (float)entityLiving.u);
/*     */ 
/*     */       
/* 421 */       if (this.tempVertexState == null) {
/* 422 */         this.tempVertexState = tsv;
/*     */       } else {
/* 424 */         this.tempVertexState.addTessellatorVertexState(tsv);
/*     */       } 
/*     */     } 
/* 427 */     this.D += this.A.a();
/*     */     
/* 429 */     Reflector.callVoid(Reflector.ForgeHooksClient_onPostRenderWorld, new Object[] { this, Integer.valueOf(renderpass) });
/*     */     
/* 431 */     this.A.setRenderingChunk(false);
/*     */     
/* 433 */     if (!Config.isFastRender()) {
/* 434 */       GL11.glPopMatrix();
/*     */     }
/* 436 */     GL11.glEndList();
/* 437 */     this.A.b(0.0D, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void finishUpdate() {
/*     */     int pass;
/* 443 */     for (pass = 0; pass < 2; pass++) {
/*     */       
/* 445 */       if (!this.m[pass]) {
/*     */ 
/*     */ 
/*     */         
/* 449 */         GL11.glNewList(this.z + pass, 4864);
/* 450 */         for (int i = 0; i <= this.activeListIndex[pass]; i++) {
/*     */           
/* 452 */           int list = this.glWorkLists[this.activeSet][pass][i];
/*     */           
/* 454 */           GL11.glCallList(list);
/*     */         } 
/*     */         
/* 457 */         GL11.glEndList();
/*     */       } 
/*     */     } 
/* 460 */     if (this.activeSet == 0) {
/* 461 */       this.activeSet = 1;
/*     */     } else {
/* 463 */       this.activeSet = 0;
/*     */     } 
/* 465 */     for (pass = 0; pass < 2; pass++) {
/*     */       
/* 467 */       if (!this.m[pass])
/*     */       {
/* 469 */         for (int i = 0; i <= this.activeListIndex[pass]; i++) {
/*     */           
/* 471 */           int list = this.glWorkLists[this.activeSet][pass][i];
/*     */ 
/*     */           
/* 474 */           GL11.glNewList(list, 4864);
/* 475 */           GL11.glEndList();
/*     */         } 
/*     */       }
/*     */     } 
/* 479 */     for (pass = 0; pass < 2; pass++)
/*     */     {
/* 481 */       this.activeListIndex[pass] = 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\WorldRendererSmooth.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */