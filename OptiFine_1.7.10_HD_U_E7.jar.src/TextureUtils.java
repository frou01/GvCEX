/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.IntBuffer;
/*     */ import javax.imageio.ImageIO;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import shadersmod.client.MultiTexID;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextureUtils
/*     */ {
/*     */   public static final String texGrassTop = "grass_top";
/*     */   public static final String texStone = "stone";
/*     */   public static final String texDirt = "dirt";
/*     */   public static final String texGrassSide = "grass_side";
/*     */   public static final String texStoneslabSide = "stone_slab_side";
/*     */   public static final String texStoneslabTop = "stone_slab_top";
/*     */   public static final String texBedrock = "bedrock";
/*     */   public static final String texSand = "sand";
/*     */   public static final String texGravel = "gravel";
/*     */   public static final String texLogOak = "log_oak";
/*     */   public static final String texLogOakTop = "log_oak_top";
/*     */   public static final String texGoldOre = "gold_ore";
/*     */   public static final String texIronOre = "iron_ore";
/*     */   public static final String texCoalOre = "coal_ore";
/*     */   public static final String texObsidian = "obsidian";
/*     */   public static final String texGrassSideOverlay = "grass_side_overlay";
/*     */   public static final String texSnow = "snow";
/*     */   public static final String texGrassSideSnowed = "grass_side_snowed";
/*     */   public static final String texMyceliumSide = "mycelium_side";
/*     */   public static final String texMyceliumTop = "mycelium_top";
/*     */   public static final String texDiamondOre = "diamond_ore";
/*     */   public static final String texRedstoneOre = "redstone_ore";
/*     */   public static final String texLapisOre = "lapis_ore";
/*     */   public static final String texLeavesOak = "leaves_oak";
/*     */   public static final String texLeavesOakOpaque = "leaves_oak_opaque";
/*     */   public static final String texLeavesJungle = "leaves_jungle";
/*     */   public static final String texLeavesJungleOpaque = "leaves_jungle_opaque";
/*     */   public static final String texCactusSide = "cactus_side";
/*     */   public static final String texClay = "clay";
/*     */   public static final String texFarmlandWet = "farmland_wet";
/*     */   public static final String texFarmlandDry = "farmland_dry";
/*     */   public static final String texNetherrack = "netherrack";
/*     */   public static final String texSoulSand = "soul_sand";
/*     */   public static final String texGlowstone = "glowstone";
/*     */   public static final String texLogSpruce = "log_spruce";
/*     */   public static final String texLogBirch = "log_birch";
/*     */   public static final String texLeavesSpruce = "leaves_spruce";
/*     */   public static final String texLeavesSpruceOpaque = "leaves_spruce_opaque";
/*     */   public static final String texLogJungle = "log_jungle";
/*     */   public static final String texEndStone = "end_stone";
/*     */   public static final String texSandstoneTop = "sandstone_top";
/*     */   public static final String texSandstoneBottom = "sandstone_bottom";
/*     */   public static final String texRedstoneLampOff = "redstone_lamp_off";
/*     */   public static final String texRedstoneLampOn = "redstone_lamp_on";
/*     */   public static final String texWaterStill = "water_still";
/*     */   public static final String texWaterFlow = "water_flow";
/*     */   public static final String texLavaStill = "lava_still";
/*     */   public static final String texLavaFlow = "lava_flow";
/*     */   public static final String texFireLayer0 = "fire_layer_0";
/*     */   public static final String texFireLayer1 = "fire_layer_1";
/*     */   public static final String texPortal = "portal";
/*     */   public static final String texGlass = "glass";
/*     */   public static final String texGlassPaneTop = "glass_pane_top";
/*     */   public static final String texCompass = "compass";
/*     */   public static final String texClock = "clock";
/*     */   public static rf iconGrassTop;
/*     */   public static rf iconGrassSide;
/*     */   public static rf iconGrassSideOverlay;
/*     */   public static rf iconSnow;
/*     */   public static rf iconGrassSideSnowed;
/*     */   public static rf iconMyceliumSide;
/*     */   public static rf iconMyceliumTop;
/*     */   public static rf iconWaterStill;
/*     */   public static rf iconWaterFlow;
/*     */   public static rf iconLavaStill;
/*     */   public static rf iconLavaFlow;
/*     */   public static rf iconPortal;
/*     */   public static rf iconFireLayer0;
/*     */   public static rf iconFireLayer1;
/*     */   public static rf iconGlass;
/*     */   public static rf iconGlassPaneTop;
/*     */   public static rf iconCompass;
/*     */   public static rf iconClock;
/* 119 */   private static IntBuffer staticBuffer = ban.f(256);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void update() {
/* 126 */     bpz mapBlocks = bpz.textureMapBlocks;
/* 127 */     if (mapBlocks == null) {
/*     */       return;
/*     */     }
/* 130 */     iconGrassTop = (rf)mapBlocks.getIconSafe("grass_top");
/* 131 */     iconGrassSide = (rf)mapBlocks.getIconSafe("grass_side");
/* 132 */     iconGrassSideOverlay = (rf)mapBlocks.getIconSafe("grass_side_overlay");
/* 133 */     iconSnow = (rf)mapBlocks.getIconSafe("snow");
/* 134 */     iconGrassSideSnowed = (rf)mapBlocks.getIconSafe("grass_side_snowed");
/* 135 */     iconMyceliumSide = (rf)mapBlocks.getIconSafe("mycelium_side");
/* 136 */     iconMyceliumTop = (rf)mapBlocks.getIconSafe("mycelium_top");
/*     */     
/* 138 */     iconWaterStill = (rf)mapBlocks.getIconSafe("water_still");
/* 139 */     iconWaterFlow = (rf)mapBlocks.getIconSafe("water_flow");
/* 140 */     iconLavaStill = (rf)mapBlocks.getIconSafe("lava_still");
/* 141 */     iconLavaFlow = (rf)mapBlocks.getIconSafe("lava_flow");
/* 142 */     iconFireLayer0 = (rf)mapBlocks.getIconSafe("fire_layer_0");
/* 143 */     iconFireLayer1 = (rf)mapBlocks.getIconSafe("fire_layer_1");
/* 144 */     iconPortal = (rf)mapBlocks.getIconSafe("portal");
/*     */     
/* 146 */     iconGlass = (rf)mapBlocks.getIconSafe("glass");
/* 147 */     iconGlassPaneTop = (rf)mapBlocks.getIconSafe("glass_pane_top");
/*     */     
/* 149 */     bpz mapItems = bpz.textureMapItems;
/* 150 */     if (mapItems == null)
/*     */       return; 
/* 152 */     iconCompass = (rf)mapItems.getIconSafe("compass");
/* 153 */     iconClock = (rf)mapItems.getIconSafe("clock");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage fixTextureDimensions(String name, BufferedImage bi) {
/* 163 */     if (name.startsWith("/mob/zombie") || name.startsWith("/mob/pigzombie")) {
/*     */ 
/*     */       
/* 166 */       int width = bi.getWidth();
/* 167 */       int height = bi.getHeight();
/*     */       
/* 169 */       if (width == height * 2) {
/*     */ 
/*     */         
/* 172 */         BufferedImage scaledImage = new BufferedImage(width, height * 2, 2);
/* 173 */         Graphics2D gr = scaledImage.createGraphics();
/*     */         
/* 175 */         gr.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/* 176 */         gr.drawImage(bi, 0, 0, width, height, null);
/* 177 */         return scaledImage;
/*     */       } 
/*     */     } 
/*     */     
/* 181 */     return bi;
/*     */   }
/*     */ 
/*     */   
/*     */   public static bqd getTextureAtlasSprite(rf icon) {
/* 186 */     if (icon instanceof bqd) {
/* 187 */       return (bqd)icon;
/*     */     }
/* 189 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int ceilPowerOfTwo(int val) {
/* 197 */     int i = 1;
/* 198 */     while (i < val)
/*     */     {
/* 200 */       i *= 2;
/*     */     }
/* 202 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getPowerOfTwo(int val) {
/* 210 */     int i = 1;
/* 211 */     int po2 = 0;
/* 212 */     while (i < val) {
/*     */       
/* 214 */       i *= 2;
/* 215 */       po2++;
/*     */     } 
/* 217 */     return po2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int twoToPower(int power) {
/* 225 */     int val = 1;
/* 226 */     for (int i = 0; i < power; i++)
/*     */     {
/* 228 */       val *= 2;
/*     */     }
/* 230 */     return val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void refreshBlockTextures() {
/* 238 */     Config.dbg("*** Reloading block textures ***");
/* 239 */     WrUpdates.finishCurrentUpdate();
/*     */     
/* 241 */     bpz.textureMapBlocks.loadTextureSafe(Config.getResourceManager());
/*     */     
/* 243 */     update();
/* 244 */     NaturalTextures.update();
/*     */     
/* 246 */     bpz.textureMapBlocks.d();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static bqh getTexture(String path) {
/* 254 */     return getTexture(new bqx(path));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static bqh getTexture(bqx loc) {
/* 262 */     bqh tex = Config.getTextureManager().b(loc);
/*     */     
/* 264 */     if (tex != null) {
/* 265 */       return tex;
/*     */     }
/* 267 */     if (!Config.hasResource(loc)) {
/* 268 */       return null;
/*     */     }
/* 270 */     bpu bpu = new bpu(loc);
/* 271 */     Config.getTextureManager().a(loc, (bqh)bpu);
/*     */     
/* 273 */     return (bqh)bpu;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void resourcesReloaded(bqy rm) {
/* 281 */     if (bpz.textureMapBlocks == null) {
/*     */       return;
/*     */     }
/* 284 */     Config.dbg("*** Reloading custom textures ***");
/*     */     
/* 286 */     CustomSky.reset();
/* 287 */     TextureAnimations.reset();
/* 288 */     WrUpdates.finishCurrentUpdate();
/*     */ 
/*     */ 
/*     */     
/* 292 */     update();
/* 293 */     NaturalTextures.update();
/*     */     
/* 295 */     TextureAnimations.update();
/* 296 */     CustomColorizer.update();
/* 297 */     CustomSky.update();
/* 298 */     RandomMobs.resetTextures();
/*     */     
/* 300 */     Shaders.resourcesReloaded();
/*     */     
/* 302 */     Lang.resourcesReloaded();
/*     */     
/* 304 */     Config.updateTexturePackClouds();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 309 */     Config.getTextureManager().e();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void refreshTextureMaps(bqy rm) {
/* 315 */     bpz.textureMapBlocks.loadTextureSafe(rm);
/* 316 */     bpz.textureMapItems.loadTextureSafe(rm);
/*     */ 
/*     */ 
/*     */     
/* 320 */     update();
/* 321 */     NaturalTextures.update();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerResourceListener() {
/* 327 */     bqy rm = Config.getResourceManager();
/* 328 */     if (rm instanceof bqv) {
/*     */       
/* 330 */       bqv rrm = (bqv)rm;
/*     */       
/* 332 */       bqz rl = new bqz()
/*     */         {
/*     */           
/*     */           public void a(bqy var1)
/*     */           {
/* 337 */             TextureUtils.resourcesReloaded(var1);
/*     */           }
/*     */         };
/*     */       
/* 341 */       rrm.a(rl);
/*     */     } 
/*     */     
/* 344 */     bqk tto = new bqk()
/*     */       {
/*     */         
/*     */         public void e()
/*     */         {
/* 349 */           TextureAnimations.updateCustomAnimations();
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void a(bqy var1) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public int b() {
/* 361 */           return 0;
/*     */         }
/*     */ 
/*     */         
/*     */         public MultiTexID getMultiTexID() {
/* 366 */           return null;
/*     */         }
/*     */       };
/* 369 */     bqx ttol = new bqx("optifine/TickableTextures");
/* 370 */     Config.getTextureManager().a(ttol, tto);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static bqx fixResourceLocation(bqx loc, String basePath) {
/* 378 */     if (!loc.b().equals("minecraft")) {
/* 379 */       return loc;
/*     */     }
/* 381 */     String path = loc.a();
/* 382 */     String pathFixed = fixResourcePath(path, basePath);
/* 383 */     if (pathFixed != path) {
/* 384 */       loc = new bqx(loc.b(), pathFixed);
/*     */     }
/* 386 */     return loc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String fixResourcePath(String path, String basePath) {
/* 394 */     String strAssMc = "assets/minecraft/";
/* 395 */     if (path.startsWith(strAssMc)) {
/*     */       
/* 397 */       path = path.substring(strAssMc.length());
/* 398 */       return path;
/*     */     } 
/*     */     
/* 401 */     if (path.startsWith("./")) {
/*     */ 
/*     */       
/* 404 */       path = path.substring(2);
/*     */       
/* 406 */       if (!basePath.endsWith("/")) {
/* 407 */         basePath = basePath + "/";
/*     */       }
/* 409 */       path = basePath + path;
/* 410 */       return path;
/*     */     } 
/*     */     
/* 413 */     if (path.startsWith("/~")) {
/* 414 */       path = path.substring(1);
/*     */     }
/* 416 */     String strMcpatcher = "mcpatcher/";
/* 417 */     if (path.startsWith("~/")) {
/*     */ 
/*     */       
/* 420 */       path = path.substring(2);
/*     */       
/* 422 */       path = strMcpatcher + path;
/* 423 */       return path;
/*     */     } 
/*     */     
/* 426 */     if (path.startsWith("/")) {
/*     */ 
/*     */       
/* 429 */       path = strMcpatcher + path.substring(1);
/* 430 */       return path;
/*     */     } 
/*     */     
/* 433 */     return path;
/*     */   }
/*     */   
/*     */   public static String getBasePath(String path) {
/* 437 */     int pos = path.lastIndexOf('/');
/* 438 */     if (pos < 0) {
/* 439 */       return "";
/*     */     }
/* 441 */     return path.substring(0, pos);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage readBufferedImage(InputStream is) throws IOException {
/*     */     BufferedImage var1;
/*     */     try {
/* 552 */       var1 = ImageIO.read(is);
/*     */     }
/*     */     finally {
/*     */       
/* 556 */       IOUtils.closeQuietly(is);
/*     */     } 
/* 558 */     return var1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static BufferedImage scaleImage(BufferedImage bi, int w2) {
/* 563 */     int w = bi.getWidth();
/* 564 */     int h = bi.getHeight();
/*     */     
/* 566 */     int h2 = h * w2 / w;
/*     */     
/* 568 */     BufferedImage bi2 = new BufferedImage(w2, h2, 2);
/* 569 */     Graphics2D g2 = bi2.createGraphics();
/*     */     
/* 571 */     Object method = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
/*     */     
/* 573 */     if (w2 < w || w2 % w != 0) {
/* 574 */       method = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
/*     */     }
/* 576 */     g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, method);
/* 577 */     g2.drawImage(bi, 0, 0, w2, h2, null);
/*     */     
/* 579 */     return bi2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getGLMaximumTextureSize() {
/* 587 */     for (int i = 65536; i > 0; i >>= 1) {
/*     */       
/* 589 */       GL11.glTexImage2D(32868, 0, 6408, i, i, 0, 6408, 5121, (IntBuffer)null);
/*     */ 
/*     */       
/* 592 */       int err = GL11.glGetError();
/*     */       
/* 594 */       int width = GL11.glGetTexLevelParameteri(32868, 0, 4096);
/* 595 */       if (width != 0) {
/* 596 */         return i;
/*     */       }
/*     */     } 
/* 599 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\TextureUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */