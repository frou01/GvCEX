/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import javax.imageio.ImageIO;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CustomColorizer
/*      */ {
/*   44 */   private static int[] grassColors = null;
/*      */   
/*   46 */   private static int[] waterColors = null;
/*      */   
/*   48 */   private static int[] foliageColors = null;
/*   49 */   private static int[] foliagePineColors = null;
/*   50 */   private static int[] foliageBirchColors = null;
/*      */   
/*   52 */   private static int[] swampFoliageColors = null;
/*   53 */   private static int[] swampGrassColors = null;
/*      */ 
/*      */   
/*   56 */   private static int[][] blockPalettes = (int[][])null;
/*      */   
/*   58 */   private static int[][] paletteColors = (int[][])null;
/*      */   
/*   60 */   private static int[] skyColors = null;
/*      */   
/*   62 */   private static int[] fogColors = null;
/*      */   
/*   64 */   private static int[] underwaterColors = null;
/*      */   
/*   66 */   private static float[][][] lightMapsColorsRgb = (float[][][])null;
/*      */   
/*   68 */   private static int[] lightMapsHeight = null;
/*      */   
/*   70 */   private static float[][] sunRgbs = new float[16][3];
/*   71 */   private static float[][] torchRgbs = new float[16][3];
/*      */   
/*   73 */   private static int[] redstoneColors = null;
/*      */   
/*   75 */   private static int[] stemColors = null;
/*      */ 
/*      */ 
/*      */   
/*   79 */   private static int[] myceliumParticleColors = null;
/*      */   
/*      */   private static boolean useDefaultColorMultiplier = true;
/*      */   
/*   83 */   private static int particleWaterColor = -1;
/*   84 */   private static int particlePortalColor = -1;
/*   85 */   private static int lilyPadColor = -1;
/*   86 */   private static azw fogColorNether = null;
/*   87 */   private static azw fogColorEnd = null;
/*   88 */   private static azw skyColorEnd = null;
/*      */   
/*   90 */   private static int[] textColors = null;
/*      */   
/*      */   private static final int TYPE_NONE = 0;
/*      */   
/*      */   private static final int TYPE_GRASS = 1;
/*      */   private static final int TYPE_FOLIAGE = 2;
/*   96 */   private static Random random = new Random();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void update() {
/*  103 */     grassColors = null;
/*  104 */     waterColors = null;
/*  105 */     foliageColors = null;
/*  106 */     foliageBirchColors = null;
/*  107 */     foliagePineColors = null;
/*  108 */     swampGrassColors = null;
/*  109 */     swampFoliageColors = null;
/*  110 */     skyColors = null;
/*  111 */     fogColors = null;
/*  112 */     underwaterColors = null;
/*  113 */     redstoneColors = null;
/*  114 */     stemColors = null;
/*  115 */     myceliumParticleColors = null;
/*  116 */     lightMapsColorsRgb = (float[][][])null;
/*  117 */     lightMapsHeight = null;
/*      */     
/*  119 */     lilyPadColor = -1;
/*  120 */     particleWaterColor = -1;
/*  121 */     particlePortalColor = -1;
/*  122 */     fogColorNether = null;
/*  123 */     fogColorEnd = null;
/*  124 */     skyColorEnd = null;
/*      */     
/*  126 */     textColors = null;
/*  127 */     blockPalettes = (int[][])null;
/*  128 */     paletteColors = (int[][])null;
/*  129 */     useDefaultColorMultiplier = true;
/*      */     
/*  131 */     String mcpColormap = "mcpatcher/colormap/";
/*      */     
/*  133 */     grassColors = getCustomColors("textures/colormap/grass.png", 65536);
/*  134 */     foliageColors = getCustomColors("textures/colormap/foliage.png", 65536);
/*      */     
/*  136 */     String[] waterPaths = { "water.png", "watercolorX.png" };
/*  137 */     waterColors = getCustomColors(mcpColormap, waterPaths, 65536);
/*      */     
/*  139 */     if (!Config.isCustomColors()) {
/*      */       return;
/*      */     }
/*  142 */     String[] pinePaths = { "pine.png", "pinecolor.png" };
/*  143 */     foliagePineColors = getCustomColors(mcpColormap, pinePaths, 65536);
/*  144 */     String[] birchPaths = { "birch.png", "birchcolor.png" };
/*  145 */     foliageBirchColors = getCustomColors(mcpColormap, birchPaths, 65536);
/*      */     
/*  147 */     String[] swampGrassPaths = { "swampgrass.png", "swampgrasscolor.png" };
/*  148 */     swampGrassColors = getCustomColors(mcpColormap, swampGrassPaths, 65536);
/*  149 */     String[] swampFoliagePaths = { "swampfoliage.png", "swampfoliagecolor.png" };
/*  150 */     swampFoliageColors = getCustomColors(mcpColormap, swampFoliagePaths, 65536);
/*      */     
/*  152 */     String[] sky0Paths = { "sky0.png", "skycolor0.png" };
/*  153 */     skyColors = getCustomColors(mcpColormap, sky0Paths, 65536);
/*  154 */     String[] fog0Paths = { "fog0.png", "fogcolor0.png" };
/*  155 */     fogColors = getCustomColors(mcpColormap, fog0Paths, 65536);
/*  156 */     String[] underwaterPaths = { "underwater.png", "underwatercolor.png" };
/*  157 */     underwaterColors = getCustomColors(mcpColormap, underwaterPaths, 65536);
/*      */     
/*  159 */     String[] redstonePaths = { "redstone.png", "redstonecolor.png" };
/*  160 */     redstoneColors = getCustomColors(mcpColormap, redstonePaths, 16);
/*  161 */     String[] stemPaths = { "stem.png", "stemcolor.png" };
/*  162 */     stemColors = getCustomColors(mcpColormap, stemPaths, 8);
/*      */     
/*  164 */     String[] myceliumPaths = { "myceliumparticle.png", "myceliumparticlecolor.png" };
/*  165 */     myceliumParticleColors = getCustomColors(mcpColormap, myceliumPaths, -1);
/*      */     
/*  167 */     int[][] lightMapsColors = new int[3][];
/*  168 */     lightMapsColorsRgb = new float[3][][];
/*  169 */     lightMapsHeight = new int[3];
/*  170 */     for (int i = 0; i < lightMapsColors.length; i++) {
/*      */       
/*  172 */       String path = "mcpatcher/lightmap/world" + (i - 1) + ".png";
/*  173 */       lightMapsColors[i] = getCustomColors(path, -1);
/*  174 */       if (lightMapsColors[i] != null) {
/*  175 */         lightMapsColorsRgb[i] = toRgb(lightMapsColors[i]);
/*      */       }
/*  177 */       lightMapsHeight[i] = getTextureHeight(path, 32);
/*      */     } 
/*      */     
/*  180 */     readColorProperties("mcpatcher/color.properties");
/*      */     
/*  182 */     updateUseDefaultColorMultiplier();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getTextureHeight(String path, int defHeight) {
/*      */     try {
/*  192 */       InputStream in = Config.getResourceStream(new bqx(path));
/*  193 */       if (in == null)
/*  194 */         return defHeight; 
/*  195 */       BufferedImage bi = ImageIO.read(in);
/*  196 */       if (bi == null) {
/*  197 */         return defHeight;
/*      */       }
/*  199 */       return bi.getHeight();
/*      */     }
/*  201 */     catch (IOException e) {
/*      */       
/*  203 */       return defHeight;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static float[][] toRgb(int[] cols) {
/*  212 */     float[][] colsRgb = new float[cols.length][3];
/*  213 */     for (int i = 0; i < cols.length; i++) {
/*      */       
/*  215 */       int col = cols[i];
/*  216 */       float rf = (col >> 16 & 0xFF) / 255.0F;
/*  217 */       float gf = (col >> 8 & 0xFF) / 255.0F;
/*  218 */       float bf = (col & 0xFF) / 255.0F;
/*  219 */       float[] colRgb = colsRgb[i];
/*  220 */       colRgb[0] = rf;
/*  221 */       colRgb[1] = gf;
/*  222 */       colRgb[2] = bf;
/*      */     } 
/*      */     
/*  225 */     return colsRgb;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readColorProperties(String fileName) {
/*      */     try {
/*  235 */       bqx loc = new bqx(fileName);
/*  236 */       InputStream in = Config.getResourceStream(loc);
/*  237 */       if (in == null)
/*      */         return; 
/*  239 */       Config.log("Loading " + fileName);
/*  240 */       Properties props = new Properties();
/*  241 */       props.load(in);
/*      */       
/*  243 */       lilyPadColor = readColor(props, "lilypad");
/*  244 */       particleWaterColor = readColor(props, new String[] { "particle.water", "drop.water" });
/*  245 */       particlePortalColor = readColor(props, "particle.portal");
/*      */       
/*  247 */       fogColorNether = readColorVec3(props, "fog.nether");
/*  248 */       fogColorEnd = readColorVec3(props, "fog.end");
/*  249 */       skyColorEnd = readColorVec3(props, "sky.end");
/*      */       
/*  251 */       textColors = readTextColors(props, fileName, "text.code.", "Text");
/*      */       
/*  253 */       readCustomPalettes(props, fileName);
/*      */     }
/*  255 */     catch (FileNotFoundException e) {
/*      */ 
/*      */       
/*      */       return;
/*      */     }
/*  260 */     catch (IOException e) {
/*      */       
/*  262 */       e.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readCustomPalettes(Properties props, String fileName) {
/*  273 */     blockPalettes = new int[256][1];
/*  274 */     for (int i = 0; i < 256; i++)
/*      */     {
/*  276 */       blockPalettes[i][0] = -1;
/*      */     }
/*      */     
/*  279 */     String palettePrefix = "palette.block.";
/*  280 */     Map<Object, Object> map = new HashMap<Object, Object>();
/*  281 */     Set keys = props.keySet();
/*  282 */     for (Iterator<String> iter = keys.iterator(); iter.hasNext(); ) {
/*      */       
/*  284 */       String key = iter.next();
/*  285 */       String value = props.getProperty(key);
/*  286 */       if (!key.startsWith(palettePrefix)) {
/*      */         continue;
/*      */       }
/*  289 */       map.put(key, value);
/*      */     } 
/*      */     
/*  292 */     String[] propNames = (String[])map.keySet().toArray((Object[])new String[map.size()]);
/*  293 */     paletteColors = new int[propNames.length][];
/*  294 */     for (int j = 0; j < propNames.length; j++) {
/*      */       
/*  296 */       String name = propNames[j];
/*  297 */       String value = props.getProperty(name);
/*  298 */       Config.log("Block palette: " + name + " = " + value);
/*  299 */       String path = name.substring(palettePrefix.length());
/*      */       
/*  301 */       String basePath = TextureUtils.getBasePath(fileName);
/*  302 */       path = TextureUtils.fixResourcePath(path, basePath);
/*      */       
/*  304 */       int[] colors = getCustomColors(path, 65536);
/*  305 */       paletteColors[j] = colors;
/*      */       
/*  307 */       String[] indexStrs = Config.tokenize(value, " ,;");
/*  308 */       for (int ix = 0; ix < indexStrs.length; ix++) {
/*      */         
/*  310 */         String blockStr = indexStrs[ix];
/*      */         
/*  312 */         int metadata = -1;
/*  313 */         if (blockStr.contains(":")) {
/*      */           
/*  315 */           String[] blockStrs = Config.tokenize(blockStr, ":");
/*  316 */           blockStr = blockStrs[0];
/*  317 */           String metadataStr = blockStrs[1];
/*  318 */           metadata = Config.parseInt(metadataStr, -1);
/*      */           
/*  320 */           if (metadata < 0 || metadata > 15) {
/*      */             
/*  322 */             Config.log("Invalid block metadata: " + blockStr + " in palette: " + name);
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/*  327 */         int blockIndex = Config.parseInt(blockStr, -1);
/*  328 */         if (blockIndex < 0 || blockIndex > 255) {
/*      */           
/*  330 */           Config.log("Invalid block index: " + blockIndex + " in palette: " + name);
/*      */ 
/*      */         
/*      */         }
/*  334 */         else if (blockIndex != aji.b((aji)ajn.c) && blockIndex != aji.b((aji)ajn.H) && blockIndex != aji.b((aji)ajn.t) && blockIndex != aji.b(ajn.bd)) {
/*      */ 
/*      */ 
/*      */           
/*  338 */           if (metadata == -1) {
/*      */ 
/*      */             
/*  341 */             blockPalettes[blockIndex][0] = j;
/*      */           
/*      */           }
/*      */           else {
/*      */             
/*  346 */             if ((blockPalettes[blockIndex]).length < 16) {
/*      */ 
/*      */               
/*  349 */               blockPalettes[blockIndex] = new int[16];
/*  350 */               Arrays.fill(blockPalettes[blockIndex], -1);
/*      */             } 
/*      */             
/*  353 */             blockPalettes[blockIndex][metadata] = j;
/*      */           } 
/*      */         } 
/*      */         continue;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int readColor(Properties props, String[] names) {
/*  366 */     for (int i = 0; i < names.length; i++) {
/*      */       
/*  368 */       String name = names[i];
/*  369 */       int col = readColor(props, name);
/*  370 */       if (col >= 0) {
/*  371 */         return col;
/*      */       }
/*      */     } 
/*  374 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int readColor(Properties props, String name) {
/*  384 */     String str = props.getProperty(name);
/*  385 */     if (str == null) {
/*  386 */       return -1;
/*      */     }
/*      */     
/*      */     try {
/*  390 */       int val = Integer.parseInt(str, 16) & 0xFFFFFF;
/*  391 */       Config.log("Custom color: " + name + " = " + str);
/*      */       
/*  393 */       return val;
/*      */     }
/*  395 */     catch (NumberFormatException e) {
/*      */       
/*  397 */       Config.log("Invalid custom color: " + name + " = " + str);
/*  398 */       return -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static azw readColorVec3(Properties props, String name) {
/*  408 */     int col = readColor(props, name);
/*  409 */     if (col < 0) {
/*  410 */       return null;
/*      */     }
/*  412 */     int red = col >> 16 & 0xFF;
/*  413 */     int green = col >> 8 & 0xFF;
/*  414 */     int blue = col & 0xFF;
/*      */     
/*  416 */     float redF = red / 255.0F;
/*  417 */     float greenF = green / 255.0F;
/*  418 */     float blueF = blue / 255.0F;
/*      */     
/*  420 */     return azw.a(redF, greenF, blueF);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int[] getCustomColors(String basePath, String[] paths, int length) {
/*  430 */     for (int i = 0; i < paths.length; i++) {
/*      */       
/*  432 */       String path = paths[i];
/*      */       
/*  434 */       path = basePath + path;
/*      */       
/*  436 */       int[] cols = getCustomColors(path, length);
/*  437 */       if (cols != null) {
/*  438 */         return cols;
/*      */       }
/*      */     } 
/*  441 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int[] getCustomColors(String path, int length) {
/*      */     try {
/*  453 */       bqx loc = new bqx(path);
/*  454 */       InputStream in = Config.getResourceStream(loc);
/*  455 */       if (in == null) {
/*  456 */         return null;
/*      */       }
/*  458 */       int[] colors = bqi.a(Config.getResourceManager(), loc);
/*  459 */       if (colors == null) {
/*  460 */         return null;
/*      */       }
/*  462 */       if (length > 0 && colors.length != length) {
/*      */         
/*  464 */         Config.log("Invalid custom colors length: " + colors.length + ", path: " + path);
/*  465 */         return null;
/*      */       } 
/*      */       
/*  468 */       Config.log("Loading custom colors: " + path);
/*      */       
/*  470 */       return colors;
/*      */     }
/*  472 */     catch (FileNotFoundException e) {
/*      */ 
/*      */       
/*  475 */       return null;
/*      */     }
/*  477 */     catch (IOException e) {
/*      */       
/*  479 */       e.printStackTrace();
/*  480 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateUseDefaultColorMultiplier() {
/*  488 */     useDefaultColorMultiplier = (foliageBirchColors == null && foliagePineColors == null && swampGrassColors == null && swampFoliageColors == null && blockPalettes == null && Config.isSwampColors() && Config.isSmoothBiomes());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getColorMultiplier(aji block, ahl blockAccess, int x, int y, int z) {
/*  498 */     if (useDefaultColorMultiplier) {
/*  499 */       return block.d(blockAccess, x, y, z);
/*      */     }
/*  501 */     int[] colors = null;
/*  502 */     int[] swampColors = null;
/*      */     
/*  504 */     if (blockPalettes != null) {
/*      */       
/*  506 */       int blockId = aji.b(block);
/*  507 */       if (blockId >= 0 && blockId < 256) {
/*      */         
/*  509 */         int[] metadataPals = blockPalettes[blockId];
/*  510 */         int paletteIx = -1;
/*      */         
/*  512 */         if (metadataPals.length > 1) {
/*      */ 
/*      */           
/*  515 */           int i = blockAccess.e(x, y, z);
/*  516 */           paletteIx = metadataPals[i];
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  521 */           paletteIx = metadataPals[0];
/*      */         } 
/*  523 */         if (paletteIx >= 0) {
/*  524 */           colors = paletteColors[paletteIx];
/*      */         }
/*      */       } 
/*  527 */       if (colors != null) {
/*      */ 
/*      */         
/*  530 */         if (Config.isSmoothBiomes()) {
/*  531 */           return getSmoothColorMultiplier(block, blockAccess, x, y, z, colors, colors, 0, 0);
/*      */         }
/*  533 */         return getCustomColor(colors, blockAccess, x, y, z);
/*      */       } 
/*      */     } 
/*      */     
/*  537 */     boolean useSwampColors = Config.isSwampColors();
/*  538 */     boolean smoothColors = false;
/*  539 */     int type = 0;
/*  540 */     int metadata = 0;
/*      */     
/*  542 */     if (block == ajn.c || block == ajn.H) {
/*      */ 
/*      */       
/*  545 */       type = 1;
/*  546 */       smoothColors = Config.isSmoothBiomes();
/*      */       
/*  548 */       colors = grassColors;
/*      */       
/*  550 */       if (useSwampColors) {
/*  551 */         swampColors = swampGrassColors;
/*      */       } else {
/*  553 */         swampColors = colors;
/*      */       } 
/*  555 */     } else if (block == ajn.t) {
/*      */ 
/*      */       
/*  558 */       type = 2;
/*  559 */       smoothColors = Config.isSmoothBiomes();
/*      */       
/*  561 */       metadata = blockAccess.e(x, y, z);
/*  562 */       if ((metadata & 0x3) == 1) {
/*      */ 
/*      */         
/*  565 */         colors = foliagePineColors;
/*      */       }
/*  567 */       else if ((metadata & 0x3) == 2) {
/*      */ 
/*      */         
/*  570 */         colors = foliageBirchColors;
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  575 */         colors = foliageColors;
/*      */         
/*  577 */         if (useSwampColors) {
/*  578 */           swampColors = swampFoliageColors;
/*      */         } else {
/*  580 */           swampColors = colors;
/*      */         } 
/*      */       } 
/*  583 */     } else if (block == ajn.bd) {
/*      */ 
/*      */       
/*  586 */       type = 2;
/*  587 */       smoothColors = Config.isSmoothBiomes();
/*      */       
/*  589 */       colors = foliageColors;
/*      */       
/*  591 */       if (useSwampColors) {
/*  592 */         swampColors = swampFoliageColors;
/*      */       } else {
/*  594 */         swampColors = colors;
/*      */       } 
/*      */     } 
/*  597 */     if (smoothColors) {
/*  598 */       return getSmoothColorMultiplier(block, blockAccess, x, y, z, colors, swampColors, type, metadata);
/*      */     }
/*  600 */     if (swampColors != colors)
/*      */     {
/*      */ 
/*      */       
/*  604 */       if (blockAccess.a(x, z) == ahu.u) {
/*  605 */         colors = swampColors;
/*      */       }
/*      */     }
/*  608 */     if (colors != null)
/*      */     {
/*      */       
/*  611 */       return getCustomColor(colors, blockAccess, x, y, z);
/*      */     }
/*      */     
/*  614 */     return block.d(blockAccess, x, y, z);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getSmoothColorMultiplier(aji block, ahl blockAccess, int x, int y, int z, int[] colors, int[] swampColors, int type, int metadata) {
/*  622 */     int sumRed = 0;
/*  623 */     int sumGreen = 0;
/*  624 */     int sumBlue = 0;
/*  625 */     for (int ix = x - 1; ix <= x + 1; ix++) {
/*      */       
/*  627 */       for (int iz = z - 1; iz <= z + 1; iz++) {
/*      */         
/*  629 */         int[] cols = colors;
/*      */         
/*  631 */         if (swampColors != cols)
/*      */         {
/*      */ 
/*      */           
/*  635 */           if (blockAccess.a(ix, iz) == ahu.u) {
/*  636 */             cols = swampColors;
/*      */           }
/*      */         }
/*  639 */         int col = 0;
/*  640 */         if (cols == null) {
/*      */           
/*  642 */           switch (type) {
/*      */ 
/*      */             
/*      */             case 1:
/*  646 */               col = blockAccess.a(ix, iz).b(x, y, z);
/*      */               break;
/*      */             case 2:
/*  649 */               if ((metadata & 0x3) == 1) {
/*  650 */                 col = agx.a(); break;
/*  651 */               }  if ((metadata & 0x3) == 2) {
/*  652 */                 col = agx.b();
/*      */                 break;
/*      */               } 
/*  655 */               col = blockAccess.a(ix, iz).c(x, y, z);
/*      */               break;
/*      */             default:
/*  658 */               col = block.d(blockAccess, ix, y, iz);
/*      */               break;
/*      */           } 
/*      */         } else {
/*  662 */           col = getCustomColor(cols, blockAccess, ix, y, iz);
/*      */         } 
/*  664 */         sumRed += col >> 16 & 0xFF;
/*  665 */         sumGreen += col >> 8 & 0xFF;
/*  666 */         sumBlue += col & 0xFF;
/*      */       } 
/*      */     } 
/*      */     
/*  670 */     int r = sumRed / 9;
/*  671 */     int g = sumGreen / 9;
/*  672 */     int b = sumBlue / 9;
/*      */     
/*  674 */     return r << 16 | g << 8 | b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getFluidColor(aji block, ahl blockAccess, int x, int y, int z) {
/*  687 */     if (block.o() != awt.h) {
/*  688 */       return block.d(blockAccess, x, y, z);
/*      */     }
/*  690 */     if (waterColors != null) {
/*      */       
/*  692 */       if (Config.isSmoothBiomes()) {
/*  693 */         return getSmoothColor(waterColors, blockAccess, x, y, z, 3, 1);
/*      */       }
/*  695 */       return getCustomColor(waterColors, blockAccess, x, y, z);
/*      */     } 
/*      */     
/*  698 */     if (!Config.isSwampColors()) {
/*  699 */       return 16777215;
/*      */     }
/*  701 */     return block.d(blockAccess, x, y, z);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getCustomColor(int[] colors, ahl blockAccess, int x, int y, int z) {
/*  713 */     ahu bgb = blockAccess.a(x, z);
/*  714 */     double temperature = qh.a(bgb.a(x, y, z), 0.0F, 1.0F);
/*  715 */     double rainfall = qh.a(bgb.i(), 0.0F, 1.0F);
/*      */     
/*  717 */     rainfall *= temperature;
/*  718 */     int cx = (int)((1.0D - temperature) * 255.0D);
/*  719 */     int cy = (int)((1.0D - rainfall) * 255.0D);
/*  720 */     return colors[cy << 8 | cx] & 0xFFFFFF;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updatePortalFX(bkm fx) {
/*  727 */     if (particlePortalColor < 0) {
/*      */       return;
/*      */     }
/*  730 */     int col = particlePortalColor;
/*  731 */     int red = col >> 16 & 0xFF;
/*  732 */     int green = col >> 8 & 0xFF;
/*  733 */     int blue = col & 0xFF;
/*      */     
/*  735 */     float redF = red / 255.0F;
/*  736 */     float greenF = green / 255.0F;
/*  737 */     float blueF = blue / 255.0F;
/*      */     
/*  739 */     fx.b(redF, greenF, blueF);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateMyceliumFX(bkm fx) {
/*  746 */     if (myceliumParticleColors == null) {
/*      */       return;
/*      */     }
/*  749 */     int col = myceliumParticleColors[random.nextInt(myceliumParticleColors.length)];
/*      */     
/*  751 */     int red = col >> 16 & 0xFF;
/*  752 */     int green = col >> 8 & 0xFF;
/*  753 */     int blue = col & 0xFF;
/*      */     
/*  755 */     float redF = red / 255.0F;
/*  756 */     float greenF = green / 255.0F;
/*  757 */     float blueF = blue / 255.0F;
/*      */     
/*  759 */     fx.b(redF, greenF, blueF);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateReddustFX(bkm fx, ahl blockAccess, double x, double y, double z) {
/*  767 */     if (redstoneColors == null) {
/*      */       return;
/*      */     }
/*  770 */     int level = blockAccess.e((int)x, (int)y, (int)z);
/*      */     
/*  772 */     int col = getRedstoneColor(level);
/*  773 */     if (col == -1) {
/*      */       return;
/*      */     }
/*  776 */     int red = col >> 16 & 0xFF;
/*  777 */     int green = col >> 8 & 0xFF;
/*  778 */     int blue = col & 0xFF;
/*      */     
/*  780 */     float redF = red / 255.0F;
/*  781 */     float greenF = green / 255.0F;
/*  782 */     float blueF = blue / 255.0F;
/*      */     
/*  784 */     fx.b(redF, greenF, blueF);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getRedstoneColor(int level) {
/*  793 */     if (redstoneColors == null) {
/*  794 */       return -1;
/*      */     }
/*  796 */     if (level < 0 || level > 15) {
/*  797 */       return -1;
/*      */     }
/*  799 */     return redstoneColors[level] & 0xFFFFFF;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateWaterFX(bkm fx, ahl blockAccess) {
/*  807 */     if (waterColors == null) {
/*      */       return;
/*      */     }
/*  810 */     int x = (int)fx.s;
/*  811 */     int y = (int)fx.t;
/*  812 */     int z = (int)fx.u;
/*      */     
/*  814 */     int col = getFluidColor(ajn.j, blockAccess, x, y, z);
/*  815 */     int red = col >> 16 & 0xFF;
/*  816 */     int green = col >> 8 & 0xFF;
/*  817 */     int blue = col & 0xFF;
/*      */     
/*  819 */     float redF = red / 255.0F;
/*  820 */     float greenF = green / 255.0F;
/*  821 */     float blueF = blue / 255.0F;
/*      */     
/*  823 */     if (particleWaterColor >= 0) {
/*      */       
/*  825 */       int redDrop = particleWaterColor >> 16 & 0xFF;
/*  826 */       int greenDrop = particleWaterColor >> 8 & 0xFF;
/*  827 */       int blueDrop = particleWaterColor & 0xFF;
/*      */       
/*  829 */       redF *= redDrop / 255.0F;
/*  830 */       greenF *= greenDrop / 255.0F;
/*  831 */       blueF *= blueDrop / 255.0F;
/*      */     } 
/*      */     
/*  834 */     fx.b(redF, greenF, blueF);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getLilypadColor() {
/*  841 */     if (lilyPadColor < 0) {
/*  842 */       return ajn.bi.D();
/*      */     }
/*  844 */     return lilyPadColor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static azw getFogColorNether(azw col) {
/*  851 */     if (fogColorNether == null) {
/*  852 */       return col;
/*      */     }
/*  854 */     return fogColorNether;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static azw getFogColorEnd(azw col) {
/*  861 */     if (fogColorEnd == null) {
/*  862 */       return col;
/*      */     }
/*  864 */     return fogColorEnd;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static azw getSkyColorEnd(azw col) {
/*  871 */     if (skyColorEnd == null) {
/*  872 */       return col;
/*      */     }
/*  874 */     return skyColorEnd;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static azw getSkyColor(azw skyColor3d, ahl blockAccess, double x, double y, double z) {
/*  881 */     if (skyColors == null) {
/*  882 */       return skyColor3d;
/*      */     }
/*  884 */     int col = getSmoothColor(skyColors, blockAccess, x, y, z, 10, 1);
/*      */     
/*  886 */     int red = col >> 16 & 0xFF;
/*  887 */     int green = col >> 8 & 0xFF;
/*  888 */     int blue = col & 0xFF;
/*      */     
/*  890 */     float redF = red / 255.0F;
/*  891 */     float greenF = green / 255.0F;
/*  892 */     float blueF = blue / 255.0F;
/*      */ 
/*      */     
/*  895 */     float cRed = (float)skyColor3d.a / 0.5F;
/*      */     
/*  897 */     float cGreen = (float)skyColor3d.b / 0.66275F;
/*      */     
/*  899 */     float cBlue = (float)skyColor3d.c;
/*      */     
/*  901 */     redF *= cRed;
/*  902 */     greenF *= cGreen;
/*  903 */     blueF *= cBlue;
/*      */     
/*  905 */     return azw.a(redF, greenF, blueF);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static azw getFogColor(azw fogColor3d, ahl blockAccess, double x, double y, double z) {
/*  911 */     if (fogColors == null) {
/*  912 */       return fogColor3d;
/*      */     }
/*  914 */     int col = getSmoothColor(fogColors, blockAccess, x, y, z, 10, 1);
/*      */     
/*  916 */     int red = col >> 16 & 0xFF;
/*  917 */     int green = col >> 8 & 0xFF;
/*  918 */     int blue = col & 0xFF;
/*      */     
/*  920 */     float redF = red / 255.0F;
/*  921 */     float greenF = green / 255.0F;
/*  922 */     float blueF = blue / 255.0F;
/*      */ 
/*      */     
/*  925 */     float cRed = (float)fogColor3d.a / 0.753F;
/*      */     
/*  927 */     float cGreen = (float)fogColor3d.b / 0.8471F;
/*      */     
/*  929 */     float cBlue = (float)fogColor3d.c;
/*      */     
/*  931 */     redF *= cRed;
/*  932 */     greenF *= cGreen;
/*  933 */     blueF *= cBlue;
/*      */     
/*  935 */     return azw.a(redF, greenF, blueF);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int[] readTextColors(Properties props, String fileName, String prefix, String logName) {
/*  942 */     int[] colors = new int[32];
/*  943 */     Arrays.fill(colors, -1);
/*  944 */     int countColors = 0;
/*      */     
/*  946 */     Set keys = props.keySet();
/*  947 */     for (Iterator<String> iter = keys.iterator(); iter.hasNext(); ) {
/*      */       
/*  949 */       String key = iter.next();
/*  950 */       String value = props.getProperty(key);
/*  951 */       if (!key.startsWith(prefix)) {
/*      */         continue;
/*      */       }
/*  954 */       String name = StrUtils.removePrefix(key, prefix);
/*      */       
/*  956 */       int code = Config.parseInt(name, -1);
/*  957 */       int color = parseColor(value);
/*  958 */       if (code < 0 || code >= colors.length || color < 0) {
/*      */         
/*  960 */         warn("Invalid color: " + key + " = " + value);
/*      */         
/*      */         continue;
/*      */       } 
/*  964 */       colors[code] = color;
/*      */       
/*  966 */       countColors++;
/*      */     } 
/*      */     
/*  969 */     if (countColors <= 0) {
/*  970 */       return null;
/*      */     }
/*  972 */     dbg(logName + " colors: " + countColors);
/*      */     
/*  974 */     return colors;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getTextColor(int index, int color) {
/*  982 */     if (textColors == null) {
/*  983 */       return color;
/*      */     }
/*  985 */     if (index < 0 || index >= textColors.length) {
/*  986 */       return color;
/*      */     }
/*  988 */     int customColor = textColors[index];
/*      */     
/*  990 */     if (customColor < 0) {
/*  991 */       return color;
/*      */     }
/*  993 */     return customColor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseColor(String str) {
/* 1003 */     if (str == null) {
/* 1004 */       return -1;
/*      */     }
/* 1006 */     str = str.trim();
/*      */ 
/*      */     
/*      */     try {
/* 1010 */       int val = Integer.parseInt(str, 16) & 0xFFFFFF;
/*      */       
/* 1012 */       return val;
/*      */     }
/* 1014 */     catch (NumberFormatException e) {
/*      */       
/* 1016 */       return -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static azw getUnderwaterColor(ahl blockAccess, double x, double y, double z) {
/* 1023 */     if (underwaterColors == null) {
/* 1024 */       return null;
/*      */     }
/* 1026 */     int col = getSmoothColor(underwaterColors, blockAccess, x, y, z, 10, 1);
/*      */     
/* 1028 */     int red = col >> 16 & 0xFF;
/* 1029 */     int green = col >> 8 & 0xFF;
/* 1030 */     int blue = col & 0xFF;
/*      */     
/* 1032 */     float redF = red / 255.0F;
/* 1033 */     float greenF = green / 255.0F;
/* 1034 */     float blueF = blue / 255.0F;
/*      */     
/* 1036 */     return azw.a(redF, greenF, blueF);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getSmoothColor(int[] colors, ahl blockAccess, double x, double y, double z, int samples, int step) {
/* 1043 */     if (colors == null) {
/* 1044 */       return -1;
/*      */     }
/* 1046 */     int x0 = (int)Math.floor(x);
/* 1047 */     int y0 = (int)Math.floor(y);
/* 1048 */     int z0 = (int)Math.floor(z);
/* 1049 */     int n = samples * step / 2;
/* 1050 */     int sumRed = 0;
/* 1051 */     int sumGreen = 0;
/* 1052 */     int sumBlue = 0;
/* 1053 */     int count = 0; int ix;
/* 1054 */     for (ix = x0 - n; ix <= x0 + n; ix += step) {
/*      */       int iz;
/* 1056 */       for (iz = z0 - n; iz <= z0 + n; iz += step) {
/*      */ 
/*      */         
/* 1059 */         int col = getCustomColor(colors, blockAccess, ix, y0, iz);
/* 1060 */         sumRed += col >> 16 & 0xFF;
/* 1061 */         sumGreen += col >> 8 & 0xFF;
/* 1062 */         sumBlue += col & 0xFF;
/* 1063 */         count++;
/*      */       } 
/*      */     } 
/*      */     
/* 1067 */     int r = sumRed / count;
/* 1068 */     int g = sumGreen / count;
/* 1069 */     int b = sumBlue / count;
/*      */     
/* 1071 */     return r << 16 | g << 8 | b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int mixColors(int c1, int c2, float w1) {
/* 1078 */     if (w1 <= 0.0F)
/* 1079 */       return c2; 
/* 1080 */     if (w1 >= 1.0F) {
/* 1081 */       return c1;
/*      */     }
/* 1083 */     float w2 = 1.0F - w1;
/*      */     
/* 1085 */     int r1 = c1 >> 16 & 0xFF;
/* 1086 */     int g1 = c1 >> 8 & 0xFF;
/* 1087 */     int b1 = c1 & 0xFF;
/*      */     
/* 1089 */     int r2 = c2 >> 16 & 0xFF;
/* 1090 */     int g2 = c2 >> 8 & 0xFF;
/* 1091 */     int b2 = c2 & 0xFF;
/*      */     
/* 1093 */     int r = (int)(r1 * w1 + r2 * w2);
/* 1094 */     int g = (int)(g1 * w1 + g2 * w2);
/* 1095 */     int b = (int)(b1 * w1 + b2 * w2);
/*      */     
/* 1097 */     return r << 16 | g << 8 | b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int averageColor(int c1, int c2) {
/* 1104 */     int r1 = c1 >> 16 & 0xFF;
/* 1105 */     int g1 = c1 >> 8 & 0xFF;
/* 1106 */     int b1 = c1 & 0xFF;
/*      */     
/* 1108 */     int r2 = c2 >> 16 & 0xFF;
/* 1109 */     int g2 = c2 >> 8 & 0xFF;
/* 1110 */     int b2 = c2 & 0xFF;
/*      */     
/* 1112 */     int r = (r1 + r2) / 2;
/* 1113 */     int g = (g1 + g2) / 2;
/* 1114 */     int b = (b1 + b2) / 2;
/*      */     
/* 1116 */     return r << 16 | g << 8 | b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getStemColorMultiplier(anu blockStem, ahl blockAccess, int x, int y, int z) {
/* 1127 */     if (stemColors == null) {
/* 1128 */       return blockStem.d(blockAccess, x, y, z);
/*      */     }
/* 1130 */     int level = blockAccess.e(x, y, z);
/* 1131 */     if (level < 0)
/* 1132 */       level = 0; 
/* 1133 */     if (level >= stemColors.length) {
/* 1134 */       level = stemColors.length - 1;
/*      */     }
/* 1136 */     return stemColors[level];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean updateLightmap(ahb world, float torchFlickerX, int[] lmColors, boolean nightvision) {
/* 1145 */     if (world == null)
/* 1146 */       return false; 
/* 1147 */     if (lightMapsColorsRgb == null) {
/* 1148 */       return false;
/*      */     }
/* 1150 */     if (!Config.isCustomColors()) {
/* 1151 */       return false;
/*      */     }
/* 1153 */     int worldType = world.t.i;
/* 1154 */     if (worldType < -1 || worldType > 1) {
/* 1155 */       return false;
/*      */     }
/* 1157 */     int lightMapIndex = worldType + 1;
/* 1158 */     float[][] lightMapRgb = lightMapsColorsRgb[lightMapIndex];
/* 1159 */     if (lightMapRgb == null) {
/* 1160 */       return false;
/*      */     }
/* 1162 */     int height = lightMapsHeight[lightMapIndex];
/*      */     
/* 1164 */     if (nightvision && height < 64) {
/* 1165 */       return false;
/*      */     }
/* 1167 */     int width = lightMapRgb.length / height;
/* 1168 */     if (width < 16) {
/*      */       
/* 1170 */       Config.warn("Invalid lightmap width: " + width + " for: /environment/lightmap" + worldType + ".png");
/* 1171 */       lightMapsColorsRgb[lightMapIndex] = (float[][])null;
/* 1172 */       return false;
/*      */     } 
/*      */     
/* 1175 */     int startIndex = 0;
/* 1176 */     if (nightvision) {
/* 1177 */       startIndex = width * 16 * 2;
/*      */     }
/* 1179 */     float sun = 1.1666666F * (world.b(1.0F) - 0.2F);
/* 1180 */     if (world.q > 0)
/* 1181 */       sun = 1.0F; 
/* 1182 */     sun = Config.limitTo1(sun);
/* 1183 */     float sunX = sun * (width - 1);
/* 1184 */     float torchX = Config.limitTo1(torchFlickerX + 0.5F) * (width - 1);
/* 1185 */     float gamma = Config.limitTo1((Config.getGameSettings()).aG);
/* 1186 */     boolean hasGamma = (gamma > 1.0E-4F);
/*      */     
/* 1188 */     getLightMapColumn(lightMapRgb, sunX, startIndex, width, sunRgbs);
/* 1189 */     getLightMapColumn(lightMapRgb, torchX, startIndex + 16 * width, width, torchRgbs);
/*      */     
/* 1191 */     float[] rgb = new float[3];
/*      */     
/* 1193 */     for (int is = 0; is < 16; is++) {
/*      */       
/* 1195 */       for (int it = 0; it < 16; it++) {
/*      */         
/* 1197 */         for (int ic = 0; ic < 3; ic++) {
/*      */           
/* 1199 */           float comp = Config.limitTo1(sunRgbs[is][ic] + torchRgbs[it][ic]);
/* 1200 */           if (hasGamma) {
/*      */             
/* 1202 */             float cg = 1.0F - comp;
/* 1203 */             cg = 1.0F - cg * cg * cg * cg;
/* 1204 */             comp = gamma * cg + (1.0F - gamma) * comp;
/*      */           } 
/* 1206 */           rgb[ic] = comp;
/*      */         } 
/* 1208 */         int r = (int)(rgb[0] * 255.0F);
/* 1209 */         int g = (int)(rgb[1] * 255.0F);
/* 1210 */         int b = (int)(rgb[2] * 255.0F);
/*      */         
/* 1212 */         lmColors[is * 16 + it] = 0xFF000000 | r << 16 | g << 8 | b;
/*      */       } 
/*      */     } 
/*      */     
/* 1216 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void getLightMapColumn(float[][] origMap, float x, int offset, int width, float[][] colRgb) {
/* 1228 */     int xLow = (int)Math.floor(x);
/* 1229 */     int xHigh = (int)Math.ceil(x);
/* 1230 */     if (xLow == xHigh) {
/*      */ 
/*      */       
/* 1233 */       for (int i = 0; i < 16; i++) {
/*      */         
/* 1235 */         float[] rgbLow = origMap[offset + i * width + xLow];
/* 1236 */         float[] rgb = colRgb[i];
/* 1237 */         for (int j = 0; j < 3; j++)
/*      */         {
/* 1239 */           rgb[j] = rgbLow[j];
/*      */         }
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/* 1245 */     float dLow = 1.0F - x - xLow;
/* 1246 */     float dHigh = 1.0F - xHigh - x;
/*      */     
/* 1248 */     for (int y = 0; y < 16; y++) {
/*      */       
/* 1250 */       float[] rgbLow = origMap[offset + y * width + xLow];
/* 1251 */       float[] rgbHigh = origMap[offset + y * width + xHigh];
/* 1252 */       float[] rgb = colRgb[y];
/*      */       
/* 1254 */       for (int i = 0; i < 3; i++)
/*      */       {
/* 1256 */         rgb[i] = rgbLow[i] * dLow + rgbHigh[i] * dHigh;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static azw getWorldFogColor(azw fogVec, bjf world, float partialTicks) {
/*      */     bao mc;
/* 1268 */     int worldType = world.t.i;
/* 1269 */     switch (worldType) {
/*      */ 
/*      */       
/*      */       case -1:
/* 1273 */         fogVec = getFogColorNether(fogVec);
/*      */         break;
/*      */       
/*      */       case 0:
/* 1277 */         mc = bao.B();
/* 1278 */         fogVec = getFogColor(fogVec, (ahl)mc.f, mc.i.s, mc.i.t + 1.0D, mc.i.u);
/*      */         break;
/*      */       
/*      */       case 1:
/* 1282 */         fogVec = getFogColorEnd(fogVec);
/*      */         break;
/*      */     } 
/*      */     
/* 1286 */     return fogVec;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static azw getWorldSkyColor(azw skyVec, ahb world, sa renderViewEntity, float partialTicks) {
/*      */     bao mc;
/* 1296 */     int worldType = world.t.i;
/* 1297 */     switch (worldType) {
/*      */ 
/*      */       
/*      */       case 0:
/* 1301 */         mc = bao.B();
/* 1302 */         skyVec = getSkyColor(skyVec, (ahl)mc.f, mc.i.s, mc.i.t + 1.0D, mc.i.u);
/*      */         break;
/*      */       
/*      */       case 1:
/* 1306 */         skyVec = getSkyColorEnd(skyVec);
/*      */         break;
/*      */     } 
/*      */     
/* 1310 */     return skyVec;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void dbg(String str) {
/* 1317 */     Config.dbg("CustomColors: " + str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void warn(String str) {
/* 1324 */     Config.warn("CustomColors: " + str);
/*      */   }
/*      */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\CustomColorizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */