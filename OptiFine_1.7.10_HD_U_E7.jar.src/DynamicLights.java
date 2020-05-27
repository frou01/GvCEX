/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class DynamicLights
/*     */ {
/*  30 */   private static DynamicLightsMap mapDynamicLights = new DynamicLightsMap();
/*     */   
/*  32 */   private static long timeUpdateMs = 0L;
/*     */   
/*     */   private static final double MAX_DIST = 7.5D;
/*     */   
/*     */   private static final double MAX_DIST_SQ = 56.25D;
/*     */   
/*     */   private static final int LIGHT_LEVEL_MAX = 15;
/*     */   
/*     */   private static final int LIGHT_LEVEL_FIRE = 15;
/*     */   private static final int LIGHT_LEVEL_BLAZE = 10;
/*     */   private static final int LIGHT_LEVEL_MAGMA_CUBE = 8;
/*     */   private static final int LIGHT_LEVEL_MAGMA_CUBE_CORE = 13;
/*     */   private static final int LIGHT_LEVEL_GLOWSTONE_DUST = 8;
/*     */   private static final int LIGHT_LEVEL_PRISMARINE_CRYSTALS = 8;
/*  46 */   private static ReflectorField ItemBlock_block = Reflector.getReflectorField(abh.class, aji.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void entityAdded(sa entityIn, bma renderGlobal) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void entityRemoved(sa entityIn, bma renderGlobal) {
/*  63 */     synchronized (mapDynamicLights) {
/*     */ 
/*     */       
/*  66 */       DynamicLight dynamicLight = mapDynamicLights.remove(entityIn.y());
/*     */       
/*  68 */       if (dynamicLight != null) {
/*  69 */         dynamicLight.updateLitChunks(renderGlobal);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void update(bma renderGlobal) {
/*  78 */     long timeNowMs = System.currentTimeMillis();
/*  79 */     if (timeNowMs < timeUpdateMs + 50L)
/*     */       return; 
/*  81 */     timeUpdateMs = timeNowMs;
/*     */     
/*  83 */     synchronized (mapDynamicLights) {
/*     */ 
/*     */       
/*  86 */       updateMapDynamicLights(renderGlobal);
/*     */       
/*  88 */       if (mapDynamicLights.size() <= 0) {
/*     */         return;
/*     */       }
/*  91 */       List<DynamicLight> dynamicLights = mapDynamicLights.valueList();
/*  92 */       for (int i = 0; i < dynamicLights.size(); i++) {
/*     */         
/*  94 */         DynamicLight dynamicLight = dynamicLights.get(i);
/*     */         
/*  96 */         dynamicLight.update(renderGlobal);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void updateMapDynamicLights(bma renderGlobal) {
/* 105 */     bjf bjf = renderGlobal.r;
/* 106 */     if (bjf == null)
/*     */       return; 
/* 108 */     List<sa> entities = bjf.D();
/* 109 */     for (Iterator<sa> it = entities.iterator(); it.hasNext(); ) {
/*     */       
/* 111 */       sa entity = it.next();
/* 112 */       int lightLevel = getLightLevel(entity);
/* 113 */       if (lightLevel > 0) {
/*     */ 
/*     */         
/* 116 */         int i = entity.y();
/* 117 */         DynamicLight dynamicLight1 = mapDynamicLights.get(i);
/* 118 */         if (dynamicLight1 == null) {
/*     */ 
/*     */           
/* 121 */           dynamicLight1 = new DynamicLight(entity);
/* 122 */           mapDynamicLights.put(i, dynamicLight1);
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 128 */       int key = entity.y();
/* 129 */       DynamicLight dynamicLight = mapDynamicLights.remove(key);
/*     */       
/* 131 */       if (dynamicLight != null) {
/* 132 */         dynamicLight.updateLitChunks(renderGlobal);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getCombinedLight(int x, int y, int z, int combinedLight) {
/* 143 */     double lightPlayer = getLightLevel(x, y, z);
/* 144 */     combinedLight = getCombinedLight(lightPlayer, combinedLight);
/* 145 */     return combinedLight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getCombinedLight(sa entity, int combinedLight) {
/* 154 */     double lightPlayer = getLightLevel(entity);
/* 155 */     combinedLight = getCombinedLight(lightPlayer, combinedLight);
/* 156 */     return combinedLight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getCombinedLight(double lightPlayer, int combinedLight) {
/* 165 */     if (lightPlayer > 0.0D) {
/*     */ 
/*     */       
/* 168 */       int lightPlayerFF = (int)(lightPlayer * 16.0D);
/* 169 */       int lightBlockFF = combinedLight & 0xFF;
/* 170 */       if (lightPlayerFF > lightBlockFF) {
/*     */         
/* 172 */         combinedLight &= 0xFFFFFF00;
/* 173 */         combinedLight |= lightPlayerFF;
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     return combinedLight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double getLightLevel(int x, int y, int z) {
/* 185 */     double lightLevelMax = 0.0D;
/*     */     
/* 187 */     synchronized (mapDynamicLights) {
/*     */ 
/*     */ 
/*     */       
/* 191 */       List<DynamicLight> dynamicLights = mapDynamicLights.valueList();
/* 192 */       for (int i = 0; i < dynamicLights.size(); i++) {
/*     */         
/* 194 */         DynamicLight dynamicLight = dynamicLights.get(i);
/* 195 */         int dynamicLightLevel = dynamicLight.getLastLightLevel();
/* 196 */         if (dynamicLightLevel > 0) {
/*     */           
/* 198 */           double px = dynamicLight.getLastPosX();
/* 199 */           double py = dynamicLight.getLastPosY();
/* 200 */           double pz = dynamicLight.getLastPosZ();
/*     */           
/* 202 */           double dx = x - px;
/* 203 */           double dy = y - py;
/* 204 */           double dz = z - pz;
/*     */           
/* 206 */           double distSq = dx * dx + dy * dy + dz * dz;
/*     */           
/* 208 */           if (dynamicLight.isUnderwater() && !Config.isClearWater()) {
/*     */             
/* 210 */             dynamicLightLevel = Config.limit(dynamicLightLevel - 2, 0, 15);
/* 211 */             distSq *= 2.0D;
/*     */           } 
/*     */           
/* 214 */           if (distSq <= 56.25D) {
/*     */ 
/*     */             
/* 217 */             double dist = Math.sqrt(distSq);
/*     */             
/* 219 */             double light = 1.0D - dist / 7.5D;
/* 220 */             double lightLevel = light * dynamicLightLevel;
/*     */             
/* 222 */             if (lightLevel > lightLevelMax)
/* 223 */               lightLevelMax = lightLevel; 
/*     */           } 
/*     */         } 
/*     */       } 
/* 227 */     }  double lightPlayer = Config.limit(lightLevelMax, 0.0D, 15.0D);
/*     */     
/* 229 */     return lightPlayer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLightLevel(add itemStack) {
/* 237 */     if (itemStack == null) {
/* 238 */       return 0;
/*     */     }
/* 240 */     adb item = itemStack.b();
/* 241 */     if (item instanceof abh) {
/*     */       
/* 243 */       abh itemBlock = (abh)item;
/* 244 */       aji block = (aji)Reflector.getFieldValue(itemBlock, ItemBlock_block);
/* 245 */       if (block != null) {
/* 246 */         return block.m();
/*     */       }
/*     */     } 
/* 249 */     if (item == ade.at) {
/* 250 */       return ajn.l.m();
/*     */     }
/* 252 */     if (item == ade.bj || item == ade.br) {
/* 253 */       return 10;
/*     */     }
/* 255 */     if (item == ade.aO) {
/* 256 */       return 8;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 261 */     if (item == ade.bs) {
/* 262 */       return 8;
/*     */     }
/* 264 */     if (item == ade.bN) {
/* 265 */       return ajn.bJ.m() / 2;
/*     */     }
/* 267 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLightLevel(sa entity) {
/* 275 */     if (entity == (Config.getMinecraft()).i)
/*     */     {
/*     */       
/* 278 */       if (!Config.isDynamicHandLight()) {
/* 279 */         return 0;
/*     */       }
/*     */     }
/* 282 */     if (entity.al()) {
/* 283 */       return 15;
/*     */     }
/* 285 */     if (entity instanceof ze) {
/* 286 */       return 15;
/*     */     }
/* 288 */     if (entity instanceof xw) {
/* 289 */       return 15;
/*     */     }
/* 291 */     if (entity instanceof xx) {
/*     */       
/* 293 */       xx entityBlaze = (xx)entity;
/*     */       
/* 295 */       if (entityBlaze.bZ()) {
/* 296 */         return 15;
/*     */       }
/* 298 */       return 10;
/*     */     } 
/*     */     
/* 301 */     if (entity instanceof yf) {
/*     */       
/* 303 */       yf emc = (yf)entity;
/*     */       
/* 305 */       if (emc.i > 0.6D) {
/* 306 */         return 13;
/*     */       }
/* 308 */       return 8;
/*     */     } 
/*     */     
/* 311 */     if (entity instanceof xz) {
/*     */       
/* 313 */       xz entityCreeper = (xz)entity;
/*     */       
/* 315 */       if (entityCreeper.cb() > 0) {
/* 316 */         return 15;
/*     */       }
/*     */     } 
/* 319 */     if (entity instanceof sv) {
/*     */       
/* 321 */       sv player = (sv)entity;
/*     */       
/* 323 */       add stackMain = player.be();
/* 324 */       int levelMain = getLightLevel(stackMain);
/*     */       
/* 326 */       add stackHead = player.q(4);
/* 327 */       int levelHead = getLightLevel(stackHead);
/*     */       
/* 329 */       return Math.max(levelMain, levelHead);
/*     */     } 
/*     */     
/* 332 */     if (entity instanceof xk) {
/*     */       
/* 334 */       xk entityItem = (xk)entity;
/* 335 */       add itemStack = getItemStack(entityItem);
/* 336 */       return getLightLevel(itemStack);
/*     */     } 
/*     */     
/* 339 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeLights(bma renderGlobal) {
/* 347 */     synchronized (mapDynamicLights) {
/*     */ 
/*     */       
/* 350 */       List<DynamicLight> dynamicLights = mapDynamicLights.valueList();
/* 351 */       for (int i = 0; i < dynamicLights.size(); i++) {
/*     */         
/* 353 */         DynamicLight dynamicLight = dynamicLights.get(i);
/*     */         
/* 355 */         dynamicLight.updateLitChunks(renderGlobal);
/*     */       } 
/*     */       
/* 358 */       dynamicLights.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clear() {
/* 367 */     synchronized (mapDynamicLights) {
/*     */       
/* 369 */       mapDynamicLights.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getCount() {
/* 378 */     synchronized (mapDynamicLights) {
/*     */       
/* 380 */       return mapDynamicLights.size();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static add getItemStack(xk entityItem) {
/* 388 */     add itemstack = entityItem.z().f(10);
/*     */     
/* 390 */     return itemstack;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\DynamicLights.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */