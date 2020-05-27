/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
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
/*     */ public class DynamicLight
/*     */ {
/*  22 */   private sa entity = null;
/*     */   
/*  24 */   private double offsetY = 0.0D;
/*     */   
/*  26 */   private double lastPosX = -2.147483648E9D;
/*  27 */   private double lastPosY = -2.147483648E9D;
/*  28 */   private double lastPosZ = -2.147483648E9D;
/*     */   
/*  30 */   private int lastLightLevel = 0;
/*     */   
/*     */   private boolean underwater = false;
/*     */   
/*  34 */   private long timeCheckMs = 0L;
/*     */   
/*  36 */   private Set<BlockPos> setLitChunkPos = new HashSet<BlockPos>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DynamicLight(sa entity) {
/*  43 */     this.entity = entity;
/*  44 */     this.offsetY = entity.g();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(bma renderGlobal) {
/*  52 */     if (Config.isDynamicLightsFast()) {
/*     */ 
/*     */       
/*  55 */       long timeNowMs = System.currentTimeMillis();
/*  56 */       if (timeNowMs < this.timeCheckMs + 500L) {
/*     */         return;
/*     */       }
/*  59 */       this.timeCheckMs = timeNowMs;
/*     */     } 
/*     */     
/*  62 */     double posX = this.entity.s - 0.5D;
/*  63 */     double posY = this.entity.t - 0.5D + this.offsetY;
/*  64 */     double posZ = this.entity.u - 0.5D;
/*     */     
/*  66 */     int lightLevel = DynamicLights.getLightLevel(this.entity);
/*     */     
/*  68 */     double dx = posX - this.lastPosX;
/*  69 */     double dy = posY - this.lastPosY;
/*  70 */     double dz = posZ - this.lastPosZ;
/*  71 */     double delta = 0.1D;
/*     */     
/*  73 */     if (Math.abs(dx) <= delta && Math.abs(dy) <= delta && Math.abs(dz) <= delta && this.lastLightLevel == lightLevel) {
/*     */       return;
/*     */     }
/*  76 */     this.lastPosX = posX;
/*  77 */     this.lastPosY = posY;
/*  78 */     this.lastPosZ = posZ;
/*  79 */     this.lastLightLevel = lightLevel;
/*     */     
/*  81 */     this.underwater = false;
/*  82 */     bjf bjf = renderGlobal.r;
/*  83 */     if (bjf != null) {
/*     */       
/*  85 */       aji block = bjf.a(qh.c(posX), qh.c(posY), qh.c(posZ));
/*  86 */       this.underwater = (block == ajn.j);
/*     */     } 
/*     */     
/*  89 */     Set<BlockPos> setNewPos = new HashSet<BlockPos>();
/*  90 */     if (lightLevel > 0) {
/*     */ 
/*     */       
/*  93 */       cr dirX = ((qh.c(posX) & 0xF) >= 8) ? cr.e : cr.f;
/*  94 */       cr dirY = ((qh.c(posY) & 0xF) >= 8) ? cr.b : cr.a;
/*  95 */       cr dirZ = ((qh.c(posZ) & 0xF) >= 8) ? cr.d : cr.c;
/*     */       
/*  97 */       dirX = getOpposite(dirX);
/*     */       
/*  99 */       BlockPos pos = new BlockPos(qh.c(posX), qh.c(posY), qh.c(posZ));
/* 100 */       BlockPos chunkView = new BlockPos(qh.a(pos.getX(), 16) * 16, qh.a(pos.getY(), 16) * 16, qh.a(pos.getZ(), 16) * 16);
/* 101 */       BlockPos chunkX = getRenderChunk(chunkView, dirX);
/* 102 */       BlockPos chunkZ = getRenderChunk(chunkView, dirZ);
/* 103 */       BlockPos chunkXZ = getRenderChunk(chunkX, dirZ);
/* 104 */       BlockPos chunkY = getRenderChunk(chunkView, dirY);
/* 105 */       BlockPos chunkYX = getRenderChunk(chunkY, dirX);
/* 106 */       BlockPos chunkYZ = getRenderChunk(chunkY, dirZ);
/* 107 */       BlockPos chunkYXZ = getRenderChunk(chunkYX, dirZ);
/*     */       
/* 109 */       updateChunkLight(renderGlobal, chunkView, this.setLitChunkPos, setNewPos);
/* 110 */       updateChunkLight(renderGlobal, chunkX, this.setLitChunkPos, setNewPos);
/* 111 */       updateChunkLight(renderGlobal, chunkZ, this.setLitChunkPos, setNewPos);
/* 112 */       updateChunkLight(renderGlobal, chunkXZ, this.setLitChunkPos, setNewPos);
/* 113 */       updateChunkLight(renderGlobal, chunkY, this.setLitChunkPos, setNewPos);
/* 114 */       updateChunkLight(renderGlobal, chunkYX, this.setLitChunkPos, setNewPos);
/* 115 */       updateChunkLight(renderGlobal, chunkYZ, this.setLitChunkPos, setNewPos);
/* 116 */       updateChunkLight(renderGlobal, chunkYXZ, this.setLitChunkPos, setNewPos);
/*     */     } 
/*     */     
/* 119 */     updateLitChunks(renderGlobal);
/*     */     
/* 121 */     this.setLitChunkPos = setNewPos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private cr getOpposite(cr facing) {
/* 128 */     switch (facing) {
/*     */       
/*     */       case a:
/* 131 */         return cr.b;
/*     */       case b:
/* 133 */         return cr.a;
/*     */       
/*     */       case c:
/* 136 */         return cr.d;
/*     */       case d:
/* 138 */         return cr.c;
/*     */       
/*     */       case e:
/* 141 */         return cr.f;
/*     */       case f:
/* 143 */         return cr.e;
/*     */     } 
/*     */     
/* 146 */     return cr.a;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static BlockPos getRenderChunk(BlockPos pos, cr facing) {
/* 152 */     return new BlockPos(pos.getX() + facing.c() * 16, pos.getY() + facing.d() * 16, pos.getZ() + facing.e() * 16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateChunkLight(bma renderGlobal, BlockPos pos, Set<BlockPos> setPrevPos, Set<BlockPos> setNewPos) {
/* 159 */     if (pos == null) {
/*     */       return;
/*     */     }
/* 162 */     renderGlobal.a(pos.getX() + 8, pos.getY() + 8, pos.getZ() + 8);
/*     */     
/* 164 */     if (setPrevPos != null) {
/* 165 */       setPrevPos.remove(pos);
/*     */     }
/* 167 */     if (setNewPos != null) {
/* 168 */       setNewPos.add(pos);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateLitChunks(bma renderGlobal) {
/* 176 */     for (Iterator<BlockPos> it = this.setLitChunkPos.iterator(); it.hasNext(); ) {
/*     */       
/* 178 */       BlockPos posOld = it.next();
/* 179 */       updateChunkLight(renderGlobal, posOld, null, null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public sa getEntity() {
/* 187 */     return this.entity;
/*     */   }
/*     */   
/*     */   public double getLastPosX() {
/* 191 */     return this.lastPosX;
/*     */   }
/*     */   
/*     */   public double getLastPosY() {
/* 195 */     return this.lastPosY;
/*     */   }
/*     */   
/*     */   public double getLastPosZ() {
/* 199 */     return this.lastPosZ;
/*     */   }
/*     */   
/*     */   public int getLastLightLevel() {
/* 203 */     return this.lastLightLevel;
/*     */   }
/*     */   
/*     */   public boolean isUnderwater() {
/* 207 */     return this.underwater;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getOffsetY() {
/* 214 */     return this.offsetY;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 219 */     return "Entity: " + this.entity + ", offsetY: " + this.offsetY;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\DynamicLight.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */