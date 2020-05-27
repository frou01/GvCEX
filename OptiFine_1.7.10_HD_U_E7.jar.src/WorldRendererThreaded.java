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
/*     */ public class WorldRendererThreaded
/*     */   extends blo
/*     */ {
/*     */   private int glRenderListWork;
/*     */   private int glRenderListBoundingBox;
/*  30 */   public boolean[] tempSkipRenderPass = new boolean[2];
/*     */   public bmi tempVertexState;
/*  32 */   private bmh tessellatorWork = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldRendererThreaded(ahb par1World, List par2List, int par3, int par4, int par5, int par6) {
/*  38 */     super(par1World, par2List, par3, par4, par5, par6);
/*     */     
/*  40 */     bma renderGlobal = (bao.B()).g;
/*     */     
/*  42 */     this.glRenderListWork = renderGlobal.displayListAllocator.allocateDisplayLists(2);
/*     */     
/*  44 */     this.glRenderListBoundingBox = this.z + 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int px, int py, int pz) {
/*  53 */     if (this.isUpdating) {
/*  54 */       WrUpdates.finishCurrentUpdate();
/*     */     }
/*  56 */     super.a(px, py, pz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateRenderer() {
/*  65 */     if (this.a == null) {
/*     */       return;
/*     */     }
/*  68 */     updateRenderer((IWrUpdateListener)null);
/*     */     
/*  70 */     finishUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateRenderer(IWrUpdateListener updateListener) {
/*  76 */     if (this.a == null) {
/*     */       return;
/*     */     }
/*     */     
/*  80 */     this.q = false;
/*     */ 
/*     */     
/*  83 */     int xMin = this.c;
/*  84 */     int yMin = this.d;
/*  85 */     int zMin = this.e;
/*  86 */     int xMax = this.c + 16;
/*  87 */     int yMax = this.d + 16;
/*  88 */     int zMax = this.e + 16;
/*     */     
/*  90 */     for (int i = 0; i < this.tempSkipRenderPass.length; i++)
/*     */     {
/*  92 */       this.tempSkipRenderPass[i] = true;
/*     */     }
/*     */     
/*  95 */     apx.a = false;
/*  96 */     HashSet<?> hashset = new HashSet();
/*  97 */     hashset.addAll(this.x);
/*  98 */     this.x.clear();
/*  99 */     bao var9 = bao.B();
/* 100 */     sv var10 = var9.i;
/* 101 */     int viewEntityPosX = qh.c(var10.s);
/* 102 */     int viewEntityPosY = qh.c(var10.t);
/* 103 */     int viewEntityPosZ = qh.c(var10.u);
/* 104 */     int one = 1;
/*     */     
/* 106 */     ChunkCacheOF chunkcache = new ChunkCacheOF(this.a, xMin - one, yMin - one, zMin - one, xMax + one, yMax + one, zMax + one, one);
/*     */     
/* 108 */     if (!chunkcache.T()) {
/*     */       
/* 110 */       b++;
/*     */       
/* 112 */       chunkcache.renderStart();
/*     */       
/* 114 */       blm renderblocks = new blm((ahl)chunkcache);
/*     */       
/* 116 */       Reflector.callVoid(Reflector.ForgeHooksClient_setWorldRendererRB, new Object[] { renderblocks });
/*     */       
/* 118 */       this.D = 0;
/* 119 */       this.tempVertexState = null;
/*     */       
/* 121 */       this.A = bmh.a;
/* 122 */       boolean hasForge = Reflector.ForgeHooksClient.exists();
/*     */       
/* 124 */       WrUpdateControl uc = new WrUpdateControl();
/*     */       
/* 126 */       for (int renderPass = 0; renderPass < 2; renderPass++) {
/*     */ 
/*     */         
/* 129 */         uc.setRenderPass(renderPass);
/*     */         
/* 131 */         boolean renderNextPass = false;
/* 132 */         boolean hasRenderedBlocks = false;
/* 133 */         boolean hasGlList = false;
/* 134 */         for (int y = yMin; y < yMax; y++) {
/*     */ 
/*     */           
/* 137 */           if (hasRenderedBlocks && updateListener != null) {
/*     */             
/* 139 */             updateListener.updating(uc);
/*     */             
/* 141 */             this.A = bmh.a;
/*     */           } 
/*     */           
/* 144 */           for (int z = zMin; z < zMax; z++) {
/*     */             
/* 146 */             for (int x = xMin; x < xMax; x++) {
/*     */ 
/*     */               
/* 149 */               aji block = chunkcache.a(x, y, z);
/*     */               
/* 151 */               if (block.o() != awt.a) {
/*     */                 
/* 153 */                 if (!hasGlList) {
/*     */                   
/* 155 */                   hasGlList = true;
/*     */                   
/* 157 */                   preRenderBlocksThreaded(renderPass);
/*     */                 } 
/*     */ 
/*     */                 
/* 161 */                 boolean hasTileEntity = false;
/* 162 */                 if (hasForge) {
/* 163 */                   hasTileEntity = Reflector.callBoolean(block, Reflector.ForgeBlock_hasTileEntity, new Object[] { Integer.valueOf(chunkcache.e(x, y, z)) });
/*     */                 } else {
/* 165 */                   hasTileEntity = block.u();
/*     */                 } 
/* 167 */                 if (renderPass == 0 && hasTileEntity) {
/*     */                   
/* 169 */                   aor var25 = chunkcache.o(x, y, z);
/*     */                   
/* 171 */                   if (bmk.a.a(var25))
/*     */                   {
/* 173 */                     this.x.add(var25);
/*     */                   }
/*     */                 } 
/*     */                 
/* 177 */                 int blockPass = block.w();
/*     */                 
/* 179 */                 if (blockPass > renderPass)
/*     */                 {
/* 181 */                   renderNextPass = true;
/*     */                 }
/*     */                 
/* 184 */                 boolean canRender = (blockPass == renderPass);
/* 185 */                 if (Reflector.ForgeBlock_canRenderInPass.exists()) {
/* 186 */                   canRender = Reflector.callBoolean(block, Reflector.ForgeBlock_canRenderInPass, new Object[] { Integer.valueOf(renderPass) });
/*     */                 }
/* 188 */                 if (canRender) {
/*     */                   
/* 190 */                   hasRenderedBlocks |= renderblocks.b(block, x, y, z);
/*     */                   
/* 192 */                   if (block.b() == 0 && x == viewEntityPosX && y == viewEntityPosY && z == viewEntityPosZ) {
/*     */                     
/* 194 */                     renderblocks.a(true);
/* 195 */                     renderblocks.b(true);
/* 196 */                     renderblocks.b(block, x, y, z);
/* 197 */                     renderblocks.a(false);
/* 198 */                     renderblocks.b(false);
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 206 */         if (hasRenderedBlocks)
/*     */         {
/*     */           
/* 209 */           this.tempSkipRenderPass[renderPass] = false;
/*     */         }
/*     */         
/* 212 */         if (hasGlList) {
/*     */ 
/*     */           
/* 215 */           if (updateListener != null) {
/* 216 */             updateListener.updating(uc);
/*     */           }
/* 218 */           this.A = bmh.a;
/*     */ 
/*     */           
/* 221 */           postRenderBlocksThreaded(renderPass, this.renderGlobal.renderViewEntity);
/*     */         }
/*     */         else {
/*     */           
/* 225 */           hasRenderedBlocks = false;
/*     */         } 
/*     */         
/* 228 */         if (!renderNextPass) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 234 */       Reflector.callVoid(Reflector.ForgeHooksClient_setWorldRendererRB, new Object[] { null });
/*     */       
/* 236 */       chunkcache.renderFinish();
/*     */     } 
/*     */ 
/*     */     
/* 240 */     HashSet hashset1 = new HashSet();
/* 241 */     hashset1.addAll(this.x);
/* 242 */     hashset1.removeAll(hashset);
/* 243 */     this.C.addAll(hashset1);
/* 244 */     hashset.removeAll(this.x);
/* 245 */     this.C.removeAll(hashset);
/* 246 */     this.w = apx.a;
/* 247 */     this.B = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void preRenderBlocksThreaded(int renderpass) {
/* 253 */     GL11.glNewList(this.glRenderListWork + renderpass, 4864);
/*     */     
/* 255 */     this.A.setRenderingChunk(true);
/*     */     
/* 257 */     if (Config.isFastRender()) {
/*     */ 
/*     */       
/* 260 */       Reflector.callVoid(Reflector.ForgeHooksClient_onPreRenderWorld, new Object[] { this, Integer.valueOf(renderpass) });
/*     */       
/* 262 */       this.A.b();
/*     */       
/* 264 */       this.A.b(-globalChunkOffsetX, 0.0D, -globalChunkOffsetZ);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 269 */     GL11.glPushMatrix();
/* 270 */     f();
/* 271 */     float var2 = 1.000001F;
/* 272 */     GL11.glTranslatef(-8.0F, -8.0F, -8.0F);
/* 273 */     GL11.glScalef(var2, var2, var2);
/* 274 */     GL11.glTranslatef(8.0F, 8.0F, 8.0F);
/*     */     
/* 276 */     Reflector.callVoid(Reflector.ForgeHooksClient_onPreRenderWorld, new Object[] { this, Integer.valueOf(renderpass) });
/*     */     
/* 278 */     this.A.b();
/* 279 */     this.A.b(-this.c, -this.d, -this.e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postRenderBlocksThreaded(int renderpass, sv entityLiving) {
/* 286 */     if (Config.isTranslucentBlocksFancy() && renderpass == 1 && !this.tempSkipRenderPass[renderpass])
/*     */     {
/*     */       
/* 289 */       this.tempVertexState = this.A.a((float)entityLiving.s, (float)entityLiving.t, (float)entityLiving.u);
/*     */     }
/*     */     
/* 292 */     this.D += this.A.a();
/*     */     
/* 294 */     Reflector.callVoid(Reflector.ForgeHooksClient_onPostRenderWorld, new Object[] { this, Integer.valueOf(renderpass) });
/*     */     
/* 296 */     this.A.setRenderingChunk(false);
/*     */     
/* 298 */     if (!Config.isFastRender()) {
/* 299 */       GL11.glPopMatrix();
/*     */     }
/* 301 */     GL11.glEndList();
/* 302 */     this.A.b(0.0D, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void finishUpdate() {
/* 309 */     int temp = this.z;
/* 310 */     this.z = this.glRenderListWork;
/* 311 */     this.glRenderListWork = temp;
/*     */     
/*     */     int i;
/*     */     
/* 315 */     for (i = 0; i < 2; i++) {
/*     */       
/* 317 */       if (!this.m[i]) {
/*     */ 
/*     */         
/* 320 */         GL11.glNewList(this.glRenderListWork + i, 4864);
/* 321 */         GL11.glEndList();
/*     */       } 
/*     */     } 
/* 324 */     for (i = 0; i < 2; i++)
/*     */     {
/* 326 */       this.m[i] = this.tempSkipRenderPass[i];
/*     */     }
/*     */     
/* 329 */     this.skipAllRenderPasses = (this.m[0] && this.m[1]);
/*     */     
/* 331 */     if (this.needsBoxUpdate && !d()) {
/*     */ 
/*     */ 
/*     */       
/* 335 */       GL11.glNewList(this.glRenderListBoundingBox, 4864);
/* 336 */       bny.a(azt.a(this.i, this.j, this.k, (this.i + 16), (this.j + 16), (this.k + 16)));
/* 337 */       GL11.glEndList();
/*     */       
/* 339 */       this.needsBoxUpdate = false;
/*     */     } 
/*     */     
/* 342 */     this.y = this.tempVertexState;
/*     */     
/* 344 */     this.t = true;
/* 345 */     this.isVisibleFromPosition = false;
/*     */     
/* 347 */     if (Reflector.LightCache.exists()) {
/*     */ 
/*     */       
/* 350 */       Object lightCache = Reflector.getFieldValue(Reflector.LightCache_cache);
/* 351 */       Reflector.callVoid(lightCache, Reflector.LightCache_clear, new Object[0]);
/*     */       
/* 353 */       Reflector.callVoid(Reflector.BlockCoord_resetPool, new Object[0]);
/*     */     } 
/*     */     
/* 356 */     updateFinished();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void c() {
/* 367 */     GL11.glCallList(this.glRenderListBoundingBox);
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\WorldRendererThreaded.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */