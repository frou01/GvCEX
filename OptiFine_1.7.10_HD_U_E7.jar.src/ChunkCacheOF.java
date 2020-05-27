/*     */ import java.util.Arrays;
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
/*     */ public class ChunkCacheOF
/*     */   extends ahr
/*     */ {
/*     */   private ahl blockAccess;
/*     */   private BlockPos position;
/*     */   private int[] combinedLights;
/*     */   private aji[] blocks;
/*  27 */   private static ArrayCache cacheCombinedLights = new ArrayCache(int.class, 16);
/*  28 */   private static ArrayCache cacheBlocks = new ArrayCache(aji.class, 16);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int ARRAY_SIZE = 8000;
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkCacheOF(ahb world, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, int subIn) {
/*  37 */     super(world, xMin, yMin, zMin, xMax, yMax, zMax, subIn);
/*     */     
/*  39 */     this.blockAccess = (ahl)world;
/*     */     
/*  41 */     this.position = new BlockPos(xMin - subIn, yMin - subIn, zMin - subIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int c(int x, int y, int z, int lightValue) {
/*  49 */     if (this.combinedLights == null) {
/*  50 */       return getLightBrightnessForSkyBlocksRaw(x, y, z, lightValue);
/*     */     }
/*  52 */     int index = getPositionIndex(x, y, z);
/*     */     
/*  54 */     if (index < 0 || index >= this.combinedLights.length) {
/*  55 */       return getLightBrightnessForSkyBlocksRaw(x, y, z, lightValue);
/*     */     }
/*  57 */     int light = this.combinedLights[index];
/*     */     
/*  59 */     if (light == -1) {
/*     */       
/*  61 */       light = getLightBrightnessForSkyBlocksRaw(x, y, z, lightValue);
/*  62 */       this.combinedLights[index] = light;
/*     */     } 
/*     */     
/*  65 */     return light;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getLightBrightnessForSkyBlocksRaw(int x, int y, int z, int lightValue) {
/*  71 */     int light = this.blockAccess.c(x, y, z, lightValue);
/*     */     
/*  73 */     if (Config.isDynamicLights())
/*     */     {
/*  75 */       if (!a(x, y, z).c()) {
/*  76 */         light = DynamicLights.getCombinedLight(x, y, z, light);
/*     */       }
/*     */     }
/*  79 */     return light;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public aji a(int x, int y, int z) {
/*  87 */     if (this.blocks == null) {
/*  88 */       return this.blockAccess.a(x, y, z);
/*     */     }
/*  90 */     int index = getPositionIndex(x, y, z);
/*     */     
/*  92 */     if (index < 0 || index >= this.blocks.length) {
/*  93 */       return this.blockAccess.a(x, y, z);
/*     */     }
/*  95 */     aji block = this.blocks[index];
/*     */     
/*  97 */     if (block == null) {
/*     */       
/*  99 */       block = this.blockAccess.a(x, y, z);
/* 100 */       this.blocks[index] = block;
/*     */     } 
/*     */     
/* 103 */     return block;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getPositionIndex(int x, int y, int z) {
/* 110 */     int i = x - this.position.getX();
/* 111 */     int j = y - this.position.getY();
/* 112 */     int k = z - this.position.getZ();
/*     */     
/* 114 */     if (i < 0 || j < 0 || k < 0 || i >= 20 || j >= 20 || k >= 20) {
/* 115 */       return -1;
/*     */     }
/* 117 */     return i * 400 + k * 20 + j;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderStart() {
/* 124 */     if (this.combinedLights == null) {
/* 125 */       this.combinedLights = (int[])cacheCombinedLights.allocate(8000);
/*     */     }
/* 127 */     Arrays.fill(this.combinedLights, -1);
/*     */     
/* 129 */     if (this.blocks == null) {
/* 130 */       this.blocks = (aji[])cacheBlocks.allocate(8000);
/*     */     }
/* 132 */     Arrays.fill((Object[])this.blocks, (Object)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderFinish() {
/* 140 */     cacheCombinedLights.free(this.combinedLights);
/* 141 */     this.combinedLights = null;
/*     */     
/* 143 */     cacheBlocks.free(this.blocks);
/* 144 */     this.blocks = null;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\ChunkCacheOF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */