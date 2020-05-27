/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.zip.ZipEntry;
/*      */ import java.util.zip.ZipFile;
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
/*      */ public class ConnectedTextures
/*      */ {
/*   41 */   private static ConnectedProperties[][] blockProperties = (ConnectedProperties[][])null;
/*   42 */   private static ConnectedProperties[][] tileProperties = (ConnectedProperties[][])null;
/*      */   
/*      */   private static boolean multipass = false;
/*      */   
/*      */   private static final int BOTTOM = 0;
/*      */   
/*      */   private static final int TOP = 1;
/*      */   
/*      */   private static final int EAST = 2;
/*      */   private static final int WEST = 3;
/*      */   private static final int NORTH = 4;
/*      */   private static final int SOUTH = 5;
/*      */   private static final int Y_NEG = 0;
/*      */   private static final int Y_POS = 1;
/*      */   private static final int Z_NEG = 2;
/*      */   private static final int Z_POS = 3;
/*      */   private static final int X_NEG = 4;
/*      */   private static final int X_POS = 5;
/*      */   private static final int Y_AXIS = 0;
/*      */   private static final int Z_AXIS = 1;
/*      */   private static final int X_AXIS = 2;
/*   63 */   private static final String[] propSuffixes = new String[] { "", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
/*      */ 
/*      */ 
/*      */   
/*   67 */   private static final int[] ctmIndexes = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 0, 0, 0, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 0, 0, 0, 0, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 0, 0, 0, 0, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 0, 0, 0, 0, 0 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static rf getConnectedTexture(ahl blockAccess, aji block, int x, int y, int z, int side, rf icon) {
/*   77 */     if (blockAccess == null) {
/*   78 */       return icon;
/*      */     }
/*   80 */     rf newIcon = getConnectedTextureSingle(blockAccess, block, x, y, z, side, icon, true);
/*      */     
/*   82 */     if (!multipass) {
/*   83 */       return newIcon;
/*      */     }
/*   85 */     if (newIcon == icon) {
/*   86 */       return newIcon;
/*      */     }
/*   88 */     rf mpIcon = newIcon;
/*   89 */     for (int i = 0; i < 3; i++) {
/*      */       
/*   91 */       rf newMpIcon = getConnectedTextureSingle(blockAccess, block, x, y, z, side, mpIcon, false);
/*      */       
/*   93 */       if (newMpIcon == mpIcon) {
/*      */         break;
/*      */       }
/*   96 */       mpIcon = newMpIcon;
/*      */     } 
/*   98 */     return mpIcon;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static rf getConnectedTextureSingle(ahl blockAccess, aji block, int x, int y, int z, int side, rf icon, boolean checkBlocks) {
/*  104 */     if (!(icon instanceof bqd)) {
/*  105 */       return icon;
/*      */     }
/*  107 */     bqd ts = (bqd)icon;
/*  108 */     int iconId = ts.getIndexInMap();
/*      */     
/*  110 */     int metadata = -1;
/*      */     
/*  112 */     if (tileProperties != null && bmh.a.defaultTexture)
/*      */     {
/*  114 */       if (iconId >= 0 && iconId < tileProperties.length) {
/*      */         
/*  116 */         ConnectedProperties[] cps = tileProperties[iconId];
/*  117 */         if (cps != null) {
/*      */           
/*  119 */           if (metadata < 0) {
/*  120 */             metadata = blockAccess.e(x, y, z);
/*      */           }
/*  122 */           rf newIcon = getConnectedTexture(cps, blockAccess, block, x, y, z, side, (rf)ts, metadata);
/*  123 */           if (newIcon != null) {
/*  124 */             return newIcon;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*  129 */     if (blockProperties != null && checkBlocks) {
/*      */       
/*  131 */       int blockId = aji.b(block);
/*  132 */       if (blockId >= 0 && blockId < blockProperties.length) {
/*      */         
/*  134 */         ConnectedProperties[] cps = blockProperties[blockId];
/*  135 */         if (cps != null) {
/*      */           
/*  137 */           if (metadata < 0) {
/*  138 */             metadata = blockAccess.e(x, y, z);
/*      */           }
/*  140 */           rf newIcon = getConnectedTexture(cps, blockAccess, block, x, y, z, side, (rf)ts, metadata);
/*  141 */           if (newIcon != null) {
/*  142 */             return newIcon;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*  147 */     return icon;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ConnectedProperties getConnectedProperties(ahl blockAccess, aji block, int x, int y, int z, int side, rf icon) {
/*  154 */     if (blockAccess == null) {
/*  155 */       return null;
/*      */     }
/*  157 */     if (!(icon instanceof bqd)) {
/*  158 */       return null;
/*      */     }
/*  160 */     bqd ts = (bqd)icon;
/*  161 */     int iconId = ts.getIndexInMap();
/*      */     
/*  163 */     int metadata = -1;
/*      */     
/*  165 */     if (tileProperties != null && bmh.a.defaultTexture)
/*      */     {
/*  167 */       if (iconId >= 0 && iconId < tileProperties.length) {
/*      */         
/*  169 */         ConnectedProperties[] cps = tileProperties[iconId];
/*  170 */         if (cps != null) {
/*      */           
/*  172 */           if (metadata < 0) {
/*  173 */             metadata = blockAccess.e(x, y, z);
/*      */           }
/*  175 */           ConnectedProperties cp = getConnectedProperties(cps, blockAccess, block, x, y, z, side, (rf)ts, metadata);
/*  176 */           if (cp != null) {
/*  177 */             return cp;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*  182 */     if (blockProperties != null) {
/*      */       
/*  184 */       int blockId = aji.b(block);
/*  185 */       if (blockId >= 0 && blockId < blockProperties.length) {
/*      */         
/*  187 */         ConnectedProperties[] cps = blockProperties[blockId];
/*  188 */         if (cps != null) {
/*      */           
/*  190 */           if (metadata < 0) {
/*  191 */             metadata = blockAccess.e(x, y, z);
/*      */           }
/*  193 */           ConnectedProperties cp = getConnectedProperties(cps, blockAccess, block, x, y, z, side, (rf)ts, metadata);
/*  194 */           if (cp != null) {
/*  195 */             return cp;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*  200 */     return null;
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
/*      */   
/*      */   private static rf getConnectedTexture(ConnectedProperties[] cps, ahl blockAccess, aji block, int x, int y, int z, int side, rf icon, int metadata) {
/*  214 */     for (int i = 0; i < cps.length; i++) {
/*      */       
/*  216 */       ConnectedProperties cp = cps[i];
/*  217 */       if (cp != null) {
/*      */         
/*  219 */         rf newIcon = getConnectedTexture(cp, blockAccess, block, x, y, z, side, icon, metadata);
/*  220 */         if (newIcon != null)
/*  221 */           return newIcon; 
/*      */       } 
/*      */     } 
/*  224 */     return null;
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
/*      */ 
/*      */   
/*      */   private static ConnectedProperties getConnectedProperties(ConnectedProperties[] cps, ahl blockAccess, aji block, int x, int y, int z, int side, rf icon, int metadata) {
/*  239 */     for (int i = 0; i < cps.length; i++) {
/*      */       
/*  241 */       ConnectedProperties cp = cps[i];
/*  242 */       if (cp != null) {
/*      */         
/*  244 */         rf newIcon = getConnectedTexture(cp, blockAccess, block, x, y, z, side, icon, metadata);
/*  245 */         if (newIcon != null)
/*  246 */           return cp; 
/*      */       } 
/*      */     } 
/*  249 */     return null;
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
/*      */ 
/*      */   
/*      */   private static rf getConnectedTexture(ConnectedProperties cp, ahl blockAccess, aji block, int x, int y, int z, int side, rf icon, int metadata) {
/*  264 */     if (cp.heights != null && !cp.heights.isInRange(y)) {
/*  265 */       return null;
/*      */     }
/*  267 */     if (cp.biomes != null) {
/*      */       
/*  269 */       ahu blockBiome = blockAccess.a(x, z);
/*  270 */       boolean biomeOk = false;
/*  271 */       for (int i = 0; i < cp.biomes.length; i++) {
/*      */         
/*  273 */         ahu biome = cp.biomes[i];
/*  274 */         if (blockBiome == biome) {
/*      */           
/*  276 */           biomeOk = true;
/*      */           break;
/*      */         } 
/*      */       } 
/*  280 */       if (!biomeOk) {
/*  281 */         return null;
/*      */       }
/*      */     } 
/*      */     
/*  285 */     int vertAxis = 0;
/*      */     
/*  287 */     int metadataCheck = metadata;
/*      */ 
/*      */     
/*  290 */     if (block instanceof ang) {
/*      */ 
/*      */       
/*  293 */       vertAxis = getWoodAxis(side, metadata);
/*      */ 
/*      */       
/*  296 */       metadataCheck &= 0x3;
/*      */     } 
/*      */     
/*  299 */     if (block instanceof amx) {
/*      */ 
/*      */       
/*  302 */       vertAxis = getQuartzAxis(side, metadata);
/*      */ 
/*      */       
/*  305 */       if (metadataCheck > 2) {
/*  306 */         metadataCheck = 2;
/*      */       }
/*      */     } 
/*  309 */     if (side >= 0)
/*      */     {
/*      */       
/*  312 */       if (cp.faces != 63) {
/*      */         
/*  314 */         int sideCheck = side;
/*  315 */         if (vertAxis != 0) {
/*  316 */           sideCheck = fixSideByAxis(side, vertAxis);
/*      */         }
/*  318 */         if ((1 << sideCheck & cp.faces) == 0) {
/*  319 */           return null;
/*      */         }
/*      */       } 
/*      */     }
/*  323 */     if (cp.metadatas != null) {
/*      */       
/*  325 */       int[] mds = cp.metadatas;
/*  326 */       boolean metadataFound = false;
/*  327 */       for (int i = 0; i < mds.length; i++) {
/*      */         
/*  329 */         if (mds[i] == metadataCheck) {
/*      */           
/*  331 */           metadataFound = true;
/*      */           break;
/*      */         } 
/*      */       } 
/*  335 */       if (!metadataFound) {
/*  336 */         return null;
/*      */       }
/*      */     } 
/*  339 */     switch (cp.method) {
/*      */       
/*      */       case 1:
/*  342 */         return getConnectedTextureCtm(cp, blockAccess, block, x, y, z, side, icon, metadata);
/*      */       case 2:
/*  344 */         return getConnectedTextureHorizontal(cp, blockAccess, block, x, y, z, vertAxis, side, icon, metadata);
/*      */       case 6:
/*  346 */         return getConnectedTextureVertical(cp, blockAccess, block, x, y, z, vertAxis, side, icon, metadata);
/*      */       case 3:
/*  348 */         return getConnectedTextureTop(cp, blockAccess, block, x, y, z, vertAxis, side, icon, metadata);
/*      */       case 4:
/*  350 */         return getConnectedTextureRandom(cp, blockAccess, block, x, y, z, side);
/*      */       case 5:
/*  352 */         return getConnectedTextureRepeat(cp, x, y, z, side);
/*      */       case 7:
/*  354 */         return getConnectedTextureFixed(cp);
/*      */       case 8:
/*  356 */         return getConnectedTextureHorizontalVertical(cp, blockAccess, block, x, y, z, vertAxis, side, icon, metadata);
/*      */       case 9:
/*  358 */         return getConnectedTextureVerticalHorizontal(cp, blockAccess, block, x, y, z, vertAxis, side, icon, metadata);
/*      */     } 
/*      */     
/*  361 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int fixSideByAxis(int side, int vertAxis) {
/*  371 */     switch (vertAxis) {
/*      */ 
/*      */       
/*      */       case 0:
/*  375 */         return side;
/*      */       
/*      */       case 1:
/*  378 */         switch (side) {
/*      */           
/*      */           case 0:
/*  381 */             return 2;
/*      */           case 1:
/*  383 */             return 3;
/*      */           case 2:
/*  385 */             return 1;
/*      */           case 3:
/*  387 */             return 0;
/*      */         } 
/*  389 */         return side;
/*      */ 
/*      */       
/*      */       case 2:
/*  393 */         switch (side) {
/*      */           
/*      */           case 0:
/*  396 */             return 4;
/*      */           case 1:
/*  398 */             return 5;
/*      */           case 4:
/*  400 */             return 1;
/*      */           case 5:
/*  402 */             return 0;
/*      */         } 
/*  404 */         return side;
/*      */     } 
/*      */ 
/*      */     
/*  408 */     return side;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getWoodAxis(int side, int metadata) {
/*  417 */     int orient = (metadata & 0xC) >> 2;
/*  418 */     switch (orient) {
/*      */ 
/*      */       
/*      */       case 1:
/*  422 */         return 2;
/*      */       
/*      */       case 2:
/*  425 */         return 1;
/*      */     } 
/*      */     
/*  428 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getQuartzAxis(int side, int metadata) {
/*  435 */     switch (metadata) {
/*      */ 
/*      */       
/*      */       case 3:
/*  439 */         return 2;
/*      */       
/*      */       case 4:
/*  442 */         return 1;
/*      */     } 
/*      */     
/*  445 */     return 0;
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
/*      */ 
/*      */   
/*      */   private static rf getConnectedTextureRandom(ConnectedProperties cp, ahl blockAccess, aji block, int x, int y, int z, int side) {
/*  460 */     if (cp.tileIcons.length == 1) {
/*  461 */       return cp.tileIcons[0];
/*      */     }
/*  463 */     int face = side / cp.symmetry * cp.symmetry;
/*      */     
/*  465 */     if (cp.linked) {
/*      */ 
/*      */       
/*  468 */       int yDown = y - 1;
/*  469 */       aji blockDown = blockAccess.a(x, yDown, z);
/*      */       
/*  471 */       while (blockDown == block) {
/*      */ 
/*      */         
/*  474 */         y = yDown;
/*      */         
/*  476 */         yDown = y - 1;
/*      */         
/*  478 */         if (yDown < 0) {
/*      */           break;
/*      */         }
/*  481 */         blockDown = blockAccess.a(x, yDown, z);
/*      */       } 
/*      */     } 
/*      */     
/*  485 */     int rand = Config.getRandom(x, y, z, face) & Integer.MAX_VALUE;
/*      */     
/*  487 */     int index = 0;
/*      */     
/*  489 */     if (cp.weights == null) {
/*      */ 
/*      */       
/*  492 */       index = rand % cp.tileIcons.length;
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  497 */       int randWeight = rand % cp.sumAllWeights;
/*      */       
/*  499 */       int[] sumWeights = cp.sumWeights;
/*  500 */       for (int i = 0; i < sumWeights.length; i++) {
/*      */         
/*  502 */         if (randWeight < sumWeights[i]) {
/*      */           
/*  504 */           index = i;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  510 */     return cp.tileIcons[index];
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
/*      */ 
/*      */   
/*      */   private static rf getConnectedTextureFixed(ConnectedProperties cp) {
/*  525 */     return cp.tileIcons[0];
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
/*      */ 
/*      */ 
/*      */   
/*      */   private static rf getConnectedTextureRepeat(ConnectedProperties cp, int x, int y, int z, int side) {
/*  541 */     if (cp.tileIcons.length == 1) {
/*  542 */       return cp.tileIcons[0];
/*      */     }
/*  544 */     int nx = 0;
/*  545 */     int ny = 0;
/*  546 */     switch (side) {
/*      */       
/*      */       case 0:
/*  549 */         nx = x;
/*  550 */         ny = z;
/*      */         break;
/*      */       case 1:
/*  553 */         nx = x;
/*  554 */         ny = z;
/*      */         break;
/*      */       case 2:
/*  557 */         nx = -x - 1;
/*  558 */         ny = -y;
/*      */         break;
/*      */       case 3:
/*  561 */         nx = x;
/*  562 */         ny = -y;
/*      */         break;
/*      */       case 4:
/*  565 */         nx = z;
/*  566 */         ny = -y;
/*      */         break;
/*      */       case 5:
/*  569 */         nx = -z - 1;
/*  570 */         ny = -y;
/*      */         break;
/*      */     } 
/*      */     
/*  574 */     nx %= cp.width;
/*  575 */     ny %= cp.height;
/*      */     
/*  577 */     if (nx < 0)
/*  578 */       nx += cp.width; 
/*  579 */     if (ny < 0) {
/*  580 */       ny += cp.height;
/*      */     }
/*  582 */     int index = ny * cp.width + nx;
/*      */     
/*  584 */     return cp.tileIcons[index];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static rf getConnectedTextureCtm(ConnectedProperties cp, ahl blockAccess, aji block, int x, int y, int z, int side, rf icon, int metadata) {
/*  595 */     boolean[] borders = new boolean[6];
/*      */     
/*  597 */     switch (side) {
/*      */       
/*      */       case 0:
/*      */       case 1:
/*  601 */         borders[0] = isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
/*  602 */         borders[1] = isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
/*  603 */         borders[2] = isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
/*  604 */         borders[3] = isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
/*      */         break;
/*      */       case 2:
/*  607 */         borders[0] = isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
/*  608 */         borders[1] = isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
/*  609 */         borders[2] = isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
/*  610 */         borders[3] = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
/*      */         break;
/*      */       case 3:
/*  613 */         borders[0] = isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
/*  614 */         borders[1] = isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
/*  615 */         borders[2] = isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
/*  616 */         borders[3] = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
/*      */         break;
/*      */       case 4:
/*  619 */         borders[0] = isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
/*  620 */         borders[1] = isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
/*  621 */         borders[2] = isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
/*  622 */         borders[3] = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
/*      */         break;
/*      */       case 5:
/*  625 */         borders[0] = isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
/*  626 */         borders[1] = isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
/*  627 */         borders[2] = isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
/*  628 */         borders[3] = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
/*      */         break;
/*      */     } 
/*      */     
/*  632 */     int index = 0;
/*      */     
/*  634 */     if ((borders[0] & (!borders[1] ? 1 : 0) & (!borders[2] ? 1 : 0) & (!borders[3] ? 1 : 0)) != 0) {
/*  635 */       index = 3;
/*  636 */     } else if (((!borders[0] ? 1 : 0) & borders[1] & (!borders[2] ? 1 : 0) & (!borders[3] ? 1 : 0)) != 0) {
/*  637 */       index = 1;
/*  638 */     } else if (((!borders[0] ? 1 : 0) & (!borders[1] ? 1 : 0) & borders[2] & (!borders[3] ? 1 : 0)) != 0) {
/*  639 */       index = 12;
/*  640 */     } else if (((!borders[0] ? 1 : 0) & (!borders[1] ? 1 : 0) & (!borders[2] ? 1 : 0) & borders[3]) != 0) {
/*  641 */       index = 36;
/*  642 */     } else if ((borders[0] & borders[1] & (!borders[2] ? 1 : 0) & (!borders[3] ? 1 : 0)) != 0) {
/*  643 */       index = 2;
/*  644 */     } else if (((!borders[0] ? 1 : 0) & (!borders[1] ? 1 : 0) & borders[2] & borders[3]) != 0) {
/*  645 */       index = 24;
/*  646 */     } else if ((borders[0] & (!borders[1] ? 1 : 0) & borders[2] & (!borders[3] ? 1 : 0)) != 0) {
/*  647 */       index = 15;
/*  648 */     } else if ((borders[0] & (!borders[1] ? 1 : 0) & (!borders[2] ? 1 : 0) & borders[3]) != 0) {
/*  649 */       index = 39;
/*  650 */     } else if (((!borders[0] ? 1 : 0) & borders[1] & borders[2] & (!borders[3] ? 1 : 0)) != 0) {
/*  651 */       index = 13;
/*  652 */     } else if (((!borders[0] ? 1 : 0) & borders[1] & (!borders[2] ? 1 : 0) & borders[3]) != 0) {
/*  653 */       index = 37;
/*  654 */     } else if (((!borders[0] ? 1 : 0) & borders[1] & borders[2] & borders[3]) != 0) {
/*  655 */       index = 25;
/*  656 */     } else if ((borders[0] & (!borders[1] ? 1 : 0) & borders[2] & borders[3]) != 0) {
/*  657 */       index = 27;
/*  658 */     } else if ((borders[0] & borders[1] & (!borders[2] ? 1 : 0) & borders[3]) != 0) {
/*  659 */       index = 38;
/*  660 */     } else if ((borders[0] & borders[1] & borders[2] & (!borders[3] ? 1 : 0)) != 0) {
/*  661 */       index = 14;
/*  662 */     } else if ((borders[0] & borders[1] & borders[2] & borders[3]) != 0) {
/*  663 */       index = 26;
/*      */     } 
/*  665 */     if (index == 0) {
/*  666 */       return cp.tileIcons[index];
/*      */     }
/*  668 */     if (!Config.isConnectedTexturesFancy()) {
/*  669 */       return cp.tileIcons[index];
/*      */     }
/*  671 */     boolean[] edges = new boolean[6];
/*  672 */     switch (side) {
/*      */       
/*      */       case 0:
/*      */       case 1:
/*  676 */         edges[0] = !isNeighbour(cp, blockAccess, block, x + 1, y, z + 1, side, icon, metadata);
/*  677 */         edges[1] = !isNeighbour(cp, blockAccess, block, x - 1, y, z + 1, side, icon, metadata);
/*  678 */         edges[2] = !isNeighbour(cp, blockAccess, block, x + 1, y, z - 1, side, icon, metadata);
/*  679 */         edges[3] = !isNeighbour(cp, blockAccess, block, x - 1, y, z - 1, side, icon, metadata);
/*      */         break;
/*      */       case 2:
/*  682 */         edges[0] = !isNeighbour(cp, blockAccess, block, x - 1, y - 1, z, side, icon, metadata);
/*  683 */         edges[1] = !isNeighbour(cp, blockAccess, block, x + 1, y - 1, z, side, icon, metadata);
/*  684 */         edges[2] = !isNeighbour(cp, blockAccess, block, x - 1, y + 1, z, side, icon, metadata);
/*  685 */         edges[3] = !isNeighbour(cp, blockAccess, block, x + 1, y + 1, z, side, icon, metadata);
/*      */         break;
/*      */       case 3:
/*  688 */         edges[0] = !isNeighbour(cp, blockAccess, block, x + 1, y - 1, z, side, icon, metadata);
/*  689 */         edges[1] = !isNeighbour(cp, blockAccess, block, x - 1, y - 1, z, side, icon, metadata);
/*  690 */         edges[2] = !isNeighbour(cp, blockAccess, block, x + 1, y + 1, z, side, icon, metadata);
/*  691 */         edges[3] = !isNeighbour(cp, blockAccess, block, x - 1, y + 1, z, side, icon, metadata);
/*      */         break;
/*      */       case 4:
/*  694 */         edges[0] = !isNeighbour(cp, blockAccess, block, x, y - 1, z + 1, side, icon, metadata);
/*  695 */         edges[1] = !isNeighbour(cp, blockAccess, block, x, y - 1, z - 1, side, icon, metadata);
/*  696 */         edges[2] = !isNeighbour(cp, blockAccess, block, x, y + 1, z + 1, side, icon, metadata);
/*  697 */         edges[3] = !isNeighbour(cp, blockAccess, block, x, y + 1, z - 1, side, icon, metadata);
/*      */         break;
/*      */       case 5:
/*  700 */         edges[0] = !isNeighbour(cp, blockAccess, block, x, y - 1, z - 1, side, icon, metadata);
/*  701 */         edges[1] = !isNeighbour(cp, blockAccess, block, x, y - 1, z + 1, side, icon, metadata);
/*  702 */         edges[2] = !isNeighbour(cp, blockAccess, block, x, y + 1, z - 1, side, icon, metadata);
/*  703 */         edges[3] = !isNeighbour(cp, blockAccess, block, x, y + 1, z + 1, side, icon, metadata);
/*      */         break;
/*      */     } 
/*      */     
/*  707 */     if (index == 13 && edges[0]) {
/*  708 */       index = 4;
/*  709 */     } else if (index == 15 && edges[1]) {
/*  710 */       index = 5;
/*  711 */     } else if (index == 37 && edges[2]) {
/*  712 */       index = 16;
/*  713 */     } else if (index == 39 && edges[3]) {
/*  714 */       index = 17;
/*  715 */     } else if (index == 14 && edges[0] && edges[1]) {
/*  716 */       index = 7;
/*  717 */     } else if (index == 25 && edges[0] && edges[2]) {
/*  718 */       index = 6;
/*  719 */     } else if (index == 27 && edges[3] && edges[1]) {
/*  720 */       index = 19;
/*  721 */     } else if (index == 38 && edges[3] && edges[2]) {
/*  722 */       index = 18;
/*  723 */     } else if (index == 14 && !edges[0] && edges[1]) {
/*  724 */       index = 31;
/*  725 */     } else if (index == 25 && edges[0] && !edges[2]) {
/*  726 */       index = 30;
/*  727 */     } else if (index == 27 && !edges[3] && edges[1]) {
/*  728 */       index = 41;
/*  729 */     } else if (index == 38 && edges[3] && !edges[2]) {
/*  730 */       index = 40;
/*  731 */     } else if (index == 14 && edges[0] && !edges[1]) {
/*  732 */       index = 29;
/*  733 */     } else if (index == 25 && !edges[0] && edges[2]) {
/*  734 */       index = 28;
/*  735 */     } else if (index == 27 && edges[3] && !edges[1]) {
/*  736 */       index = 43;
/*  737 */     } else if (index == 38 && !edges[3] && edges[2]) {
/*  738 */       index = 42;
/*  739 */     } else if (index == 26 && edges[0] && edges[1] && edges[2] && edges[3]) {
/*  740 */       index = 46;
/*  741 */     } else if (index == 26 && !edges[0] && edges[1] && edges[2] && edges[3]) {
/*  742 */       index = 9;
/*  743 */     } else if (index == 26 && edges[0] && !edges[1] && edges[2] && edges[3]) {
/*  744 */       index = 21;
/*  745 */     } else if (index == 26 && edges[0] && edges[1] && !edges[2] && edges[3]) {
/*  746 */       index = 8;
/*  747 */     } else if (index == 26 && edges[0] && edges[1] && edges[2] && !edges[3]) {
/*  748 */       index = 20;
/*  749 */     } else if (index == 26 && edges[0] && edges[1] && !edges[2] && !edges[3]) {
/*  750 */       index = 11;
/*  751 */     } else if (index == 26 && !edges[0] && !edges[1] && edges[2] && edges[3]) {
/*  752 */       index = 22;
/*  753 */     } else if (index == 26 && !edges[0] && edges[1] && !edges[2] && edges[3]) {
/*  754 */       index = 23;
/*  755 */     } else if (index == 26 && edges[0] && !edges[1] && edges[2] && !edges[3]) {
/*  756 */       index = 10;
/*  757 */     } else if (index == 26 && edges[0] && !edges[1] && !edges[2] && edges[3]) {
/*  758 */       index = 34;
/*  759 */     } else if (index == 26 && !edges[0] && edges[1] && edges[2] && !edges[3]) {
/*  760 */       index = 35;
/*  761 */     } else if (index == 26 && edges[0] && !edges[1] && !edges[2] && !edges[3]) {
/*  762 */       index = 32;
/*  763 */     } else if (index == 26 && !edges[0] && edges[1] && !edges[2] && !edges[3]) {
/*  764 */       index = 33;
/*  765 */     } else if (index == 26 && !edges[0] && !edges[1] && edges[2] && !edges[3]) {
/*  766 */       index = 44;
/*  767 */     } else if (index == 26 && !edges[0] && !edges[1] && !edges[2] && edges[3]) {
/*  768 */       index = 45;
/*      */     } 
/*  770 */     return cp.tileIcons[index];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isNeighbour(ConnectedProperties cp, ahl iblockaccess, aji block, int x, int y, int z, int side, rf icon, int metadata) {
/*  777 */     aji neighbourBlock = iblockaccess.a(x, y, z);
/*      */     
/*  779 */     if (cp.connect == 2) {
/*      */       rf neighbourIcon;
/*  781 */       if (neighbourBlock == null) {
/*  782 */         return false;
/*      */       }
/*  784 */       int neighbourMetadata = iblockaccess.e(x, y, z);
/*      */ 
/*      */ 
/*      */       
/*  788 */       if (side >= 0) {
/*  789 */         neighbourIcon = neighbourBlock.a(side, neighbourMetadata);
/*      */       } else {
/*  791 */         neighbourIcon = neighbourBlock.a(1, neighbourMetadata);
/*  792 */       }  return (neighbourIcon == icon);
/*      */     } 
/*      */     
/*  795 */     if (cp.connect == 3) {
/*      */       
/*  797 */       if (neighbourBlock == null) {
/*  798 */         return false;
/*      */       }
/*  800 */       return (neighbourBlock.o() == block.o());
/*      */     } 
/*      */     
/*  803 */     return (neighbourBlock == block && iblockaccess.e(x, y, z) == metadata);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static rf getConnectedTextureHorizontal(ConnectedProperties cp, ahl blockAccess, aji block, int x, int y, int z, int vertAxis, int side, rf icon, int metadata) {
/*  811 */     boolean left = false;
/*  812 */     boolean right = false;
/*      */     
/*  814 */     switch (vertAxis) {
/*      */ 
/*      */ 
/*      */       
/*      */       case 0:
/*  819 */         switch (side) {
/*      */ 
/*      */           
/*      */           case 0:
/*      */           case 1:
/*  824 */             return null;
/*      */           case 2:
/*  826 */             left = isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
/*  827 */             right = isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
/*      */             break;
/*      */           case 3:
/*  830 */             left = isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
/*  831 */             right = isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
/*      */             break;
/*      */           case 4:
/*  834 */             left = isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
/*  835 */             right = isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
/*      */             break;
/*      */           case 5:
/*  838 */             left = isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
/*  839 */             right = isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
/*      */             break;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 1:
/*  846 */         switch (side) {
/*      */ 
/*      */           
/*      */           case 2:
/*      */           case 3:
/*  851 */             return null;
/*      */           case 0:
/*  853 */             left = isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
/*  854 */             right = isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
/*      */             break;
/*      */           case 1:
/*  857 */             left = isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
/*  858 */             right = isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
/*      */             break;
/*      */           case 4:
/*  861 */             left = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
/*  862 */             right = isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
/*      */             break;
/*      */           case 5:
/*  865 */             left = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
/*  866 */             right = isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
/*      */             break;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 2:
/*  873 */         switch (side) {
/*      */ 
/*      */           
/*      */           case 4:
/*      */           case 5:
/*  878 */             return null;
/*      */           case 2:
/*  880 */             left = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
/*  881 */             right = isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
/*      */             break;
/*      */           case 3:
/*  884 */             left = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
/*  885 */             right = isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
/*      */             break;
/*      */           case 0:
/*  888 */             left = isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
/*  889 */             right = isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
/*      */             break;
/*      */           case 1:
/*  892 */             left = isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
/*  893 */             right = isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
/*      */             break;
/*      */         } 
/*      */         
/*      */         break;
/*      */     } 
/*  899 */     int index = 3;
/*      */     
/*  901 */     if (left) {
/*      */       
/*  903 */       if (right)
/*      */       {
/*      */         
/*  906 */         index = 1;
/*      */       
/*      */       }
/*      */       else
/*      */       {
/*  911 */         index = 2;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  916 */     else if (right) {
/*      */ 
/*      */       
/*  919 */       index = 0;
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  924 */       index = 3;
/*      */     } 
/*      */ 
/*      */     
/*  928 */     return cp.tileIcons[index];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static rf getConnectedTextureVertical(ConnectedProperties cp, ahl blockAccess, aji block, int x, int y, int z, int vertAxis, int side, rf icon, int metadata) {
/*  936 */     boolean bottom = false;
/*  937 */     boolean top = false;
/*      */     
/*  939 */     switch (vertAxis) {
/*      */ 
/*      */       
/*      */       case 0:
/*  943 */         if (side == 1 || side == 0) {
/*  944 */           return null;
/*      */         }
/*  946 */         bottom = isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
/*  947 */         top = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
/*      */         break;
/*      */       
/*      */       case 1:
/*  951 */         if (side == 3 || side == 2) {
/*  952 */           return null;
/*      */         }
/*  954 */         bottom = isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
/*  955 */         top = isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
/*      */         break;
/*      */       
/*      */       case 2:
/*  959 */         if (side == 5 || side == 4) {
/*  960 */           return null;
/*      */         }
/*  962 */         bottom = isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
/*  963 */         top = isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
/*      */         break;
/*      */     } 
/*      */     
/*  967 */     int index = 3;
/*      */     
/*  969 */     if (bottom) {
/*      */       
/*  971 */       if (top)
/*      */       {
/*      */         
/*  974 */         index = 1;
/*      */       
/*      */       }
/*      */       else
/*      */       {
/*  979 */         index = 2;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  984 */     else if (top) {
/*      */ 
/*      */       
/*  987 */       index = 0;
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  992 */       index = 3;
/*      */     } 
/*      */ 
/*      */     
/*  996 */     return cp.tileIcons[index];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static rf getConnectedTextureHorizontalVertical(ConnectedProperties cp, ahl blockAccess, aji block, int x, int y, int z, int vertAxis, int side, rf icon, int metadata) {
/* 1004 */     rf[] tileIcons = cp.tileIcons;
/*      */     
/* 1006 */     rf iconH = getConnectedTextureHorizontal(cp, blockAccess, block, x, y, z, vertAxis, side, icon, metadata);
/*      */     
/* 1008 */     if (iconH != null && iconH != icon && iconH != tileIcons[3]) {
/* 1009 */       return iconH;
/*      */     }
/* 1011 */     rf iconV = getConnectedTextureVertical(cp, blockAccess, block, x, y, z, vertAxis, side, icon, metadata);
/*      */ 
/*      */     
/* 1014 */     if (iconV == tileIcons[0])
/* 1015 */       return tileIcons[4]; 
/* 1016 */     if (iconV == tileIcons[1])
/* 1017 */       return tileIcons[5]; 
/* 1018 */     if (iconV == tileIcons[2]) {
/* 1019 */       return tileIcons[6];
/*      */     }
/* 1021 */     return iconV;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static rf getConnectedTextureVerticalHorizontal(ConnectedProperties cp, ahl blockAccess, aji block, int x, int y, int z, int vertAxis, int side, rf icon, int metadata) {
/* 1030 */     rf[] tileIcons = cp.tileIcons;
/*      */     
/* 1032 */     rf iconV = getConnectedTextureVertical(cp, blockAccess, block, x, y, z, vertAxis, side, icon, metadata);
/*      */     
/* 1034 */     if (iconV != null && iconV != icon && iconV != tileIcons[3]) {
/* 1035 */       return iconV;
/*      */     }
/* 1037 */     rf iconH = getConnectedTextureHorizontal(cp, blockAccess, block, x, y, z, vertAxis, side, icon, metadata);
/*      */ 
/*      */     
/* 1040 */     if (iconH == tileIcons[0])
/* 1041 */       return tileIcons[4]; 
/* 1042 */     if (iconH == tileIcons[1])
/* 1043 */       return tileIcons[5]; 
/* 1044 */     if (iconH == tileIcons[2]) {
/* 1045 */       return tileIcons[6];
/*      */     }
/* 1047 */     return iconH;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static rf getConnectedTextureTop(ConnectedProperties cp, ahl blockAccess, aji block, int x, int y, int z, int vertAxis, int side, rf icon, int metadata) {
/* 1054 */     boolean top = false;
/* 1055 */     switch (vertAxis) {
/*      */ 
/*      */       
/*      */       case 0:
/* 1059 */         if (side == 1 || side == 0) {
/* 1060 */           return null;
/*      */         }
/* 1062 */         top = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
/*      */         break;
/*      */       
/*      */       case 1:
/* 1066 */         if (side == 3 || side == 2) {
/* 1067 */           return null;
/*      */         }
/* 1069 */         top = isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
/*      */         break;
/*      */       
/*      */       case 2:
/* 1073 */         if (side == 5 || side == 4) {
/* 1074 */           return null;
/*      */         }
/* 1076 */         top = isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
/*      */         break;
/*      */     } 
/*      */     
/* 1080 */     if (top)
/*      */     {
/*      */       
/* 1083 */       return cp.tileIcons[0];
/*      */     }
/*      */     
/* 1086 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateIcons(bpz textureMap) {
/* 1096 */     blockProperties = (ConnectedProperties[][])null;
/* 1097 */     tileProperties = (ConnectedProperties[][])null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1105 */     bra[] rps = Config.getResourcePacks();
/*      */ 
/*      */     
/* 1108 */     for (int i = rps.length - 1; i >= 0; i--) {
/*      */       
/* 1110 */       bra rp = rps[i];
/* 1111 */       updateIcons(textureMap, rp);
/*      */     } 
/*      */     
/* 1114 */     updateIcons(textureMap, Config.getDefaultResourcePack());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateIcons(bpz textureMap, bra rp) {
/* 1124 */     String[] names = collectFiles(rp, "mcpatcher/ctm/", ".properties");
/*      */     
/* 1126 */     Arrays.sort((Object[])names);
/*      */     
/* 1128 */     List tileList = makePropertyList(tileProperties);
/* 1129 */     List blockList = makePropertyList(blockProperties);
/*      */     
/* 1131 */     for (int i = 0; i < names.length; i++) {
/*      */       
/* 1133 */       String name = names[i];
/* 1134 */       Config.dbg("ConnectedTextures: " + name);
/*      */       
/*      */       try {
/* 1137 */         bqx locFile = new bqx(name);
/* 1138 */         InputStream in = rp.a(locFile);
/* 1139 */         if (in == null) {
/*      */ 
/*      */           
/* 1142 */           Config.warn("ConnectedTextures file not found: " + name);
/*      */         } else {
/*      */           
/* 1145 */           Properties props = new Properties();
/* 1146 */           props.load(in);
/* 1147 */           ConnectedProperties cp = new ConnectedProperties(props, name);
/* 1148 */           if (cp.isValid(name))
/*      */           
/*      */           { 
/* 1151 */             cp.updateIcons(textureMap);
/*      */             
/* 1153 */             addToTileList(cp, tileList);
/* 1154 */             addToBlockList(cp, blockList); } 
/*      */         } 
/* 1156 */       } catch (FileNotFoundException e) {
/*      */ 
/*      */         
/* 1159 */         Config.warn("ConnectedTextures file not found: " + name);
/*      */       
/*      */       }
/* 1162 */       catch (IOException e) {
/*      */         
/* 1164 */         e.printStackTrace();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1169 */     blockProperties = propertyListToArray(blockList);
/* 1170 */     tileProperties = propertyListToArray(tileList);
/*      */     
/* 1172 */     multipass = detectMultipass();
/* 1173 */     Config.dbg("Multipass connected textures: " + multipass);
/*      */   }
/*      */ 
/*      */   
/*      */   private static List makePropertyList(ConnectedProperties[][] propsArr) {
/* 1178 */     List<List> list = new ArrayList();
/*      */     
/* 1180 */     if (propsArr != null)
/*      */     {
/* 1182 */       for (int i = 0; i < propsArr.length; i++) {
/*      */         
/* 1184 */         ConnectedProperties[] props = propsArr[i];
/* 1185 */         List propList = null;
/* 1186 */         if (props != null) {
/* 1187 */           propList = new ArrayList(Arrays.asList((Object[])props));
/*      */         }
/* 1189 */         list.add(propList);
/*      */       } 
/*      */     }
/*      */     
/* 1193 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean detectMultipass() {
/* 1201 */     List propList = new ArrayList(); int i;
/* 1202 */     for (i = 0; i < tileProperties.length; i++) {
/*      */       
/* 1204 */       ConnectedProperties[] cps = tileProperties[i];
/* 1205 */       if (cps != null)
/*      */       {
/* 1207 */         propList.addAll(Arrays.asList(cps)); } 
/*      */     } 
/* 1209 */     for (i = 0; i < blockProperties.length; i++) {
/*      */       
/* 1211 */       ConnectedProperties[] cps = blockProperties[i];
/* 1212 */       if (cps != null)
/*      */       {
/* 1214 */         propList.addAll(Arrays.asList(cps)); } 
/*      */     } 
/* 1216 */     ConnectedProperties[] props = (ConnectedProperties[])propList.toArray((Object[])new ConnectedProperties[propList.size()]);
/*      */     
/* 1218 */     Set matchIconSet = new HashSet();
/* 1219 */     Set<?> tileIconSet = new HashSet();
/* 1220 */     for (int j = 0; j < props.length; j++) {
/*      */       
/* 1222 */       ConnectedProperties cp = props[j];
/*      */       
/* 1224 */       if (cp.matchTileIcons != null) {
/* 1225 */         matchIconSet.addAll(Arrays.asList(cp.matchTileIcons));
/*      */       }
/* 1227 */       if (cp.tileIcons != null)
/* 1228 */         tileIconSet.addAll(Arrays.asList(cp.tileIcons)); 
/*      */     } 
/* 1230 */     matchIconSet.retainAll(tileIconSet);
/* 1231 */     return !matchIconSet.isEmpty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ConnectedProperties[][] propertyListToArray(List<List> list) {
/* 1239 */     ConnectedProperties[][] propArr = new ConnectedProperties[list.size()][];
/* 1240 */     for (int i = 0; i < list.size(); i++) {
/*      */       
/* 1242 */       List subList = list.get(i);
/* 1243 */       if (subList != null) {
/*      */ 
/*      */         
/* 1246 */         ConnectedProperties[] subArr = (ConnectedProperties[])subList.toArray((Object[])new ConnectedProperties[subList.size()]);
/*      */         
/* 1248 */         propArr[i] = subArr;
/*      */       } 
/*      */     } 
/* 1251 */     return propArr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void addToTileList(ConnectedProperties cp, List tileList) {
/* 1260 */     if (cp.matchTileIcons == null) {
/*      */       return;
/*      */     }
/* 1263 */     for (int i = 0; i < cp.matchTileIcons.length; i++) {
/*      */       
/* 1265 */       rf icon = cp.matchTileIcons[i];
/* 1266 */       if (!(icon instanceof bqd)) {
/*      */         
/* 1268 */         Config.warn("IIcon is not TextureAtlasSprite: " + icon + ", name: " + icon.g());
/*      */       } else {
/*      */         
/* 1271 */         bqd ts = (bqd)icon;
/* 1272 */         int tileId = ts.getIndexInMap();
/* 1273 */         if (tileId < 0) {
/*      */           
/* 1275 */           Config.warn("Invalid tile ID: " + tileId + ", icon: " + ts.g());
/*      */         } else {
/*      */           
/* 1278 */           addToList(cp, tileList, tileId);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void addToBlockList(ConnectedProperties cp, List blockList) {
/* 1288 */     if (cp.matchBlocks == null) {
/*      */       return;
/*      */     }
/* 1291 */     for (int i = 0; i < cp.matchBlocks.length; i++) {
/*      */       
/* 1293 */       int blockId = cp.matchBlocks[i];
/* 1294 */       if (blockId < 0) {
/*      */         
/* 1296 */         Config.warn("Invalid block ID: " + blockId);
/*      */       } else {
/*      */         
/* 1299 */         addToList(cp, blockList, blockId);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void addToList(ConnectedProperties cp, List<List> list, int id) {
/* 1310 */     while (id >= list.size()) {
/* 1311 */       list.add(null);
/*      */     }
/* 1313 */     List<ConnectedProperties> subList = list.get(id);
/* 1314 */     if (subList == null) {
/*      */       
/* 1316 */       subList = new ArrayList();
/* 1317 */       list.set(id, subList);
/*      */     } 
/*      */     
/* 1320 */     subList.add(cp);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] collectFiles(bra rp, String prefix, String suffix) {
/* 1326 */     if (rp instanceof bqp) {
/* 1327 */       return collectFilesDefault(rp);
/*      */     }
/* 1329 */     if (!(rp instanceof bqn))
/* 1330 */       return new String[0]; 
/* 1331 */     bqn arp = (bqn)rp;
/*      */     
/* 1333 */     File tpFile = ResourceUtils.getResourcePackFile(arp);
/* 1334 */     if (tpFile == null) {
/* 1335 */       return new String[0];
/*      */     }
/* 1337 */     if (tpFile.isDirectory()) {
/* 1338 */       return collectFilesFolder(tpFile, "", prefix, suffix);
/*      */     }
/* 1340 */     if (tpFile.isFile()) {
/* 1341 */       return collectFilesZIP(tpFile, prefix, suffix);
/*      */     }
/* 1343 */     return new String[0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] collectFilesDefault(bra rp) {
/* 1351 */     List<String> list = new ArrayList();
/*      */     
/* 1353 */     String[] names = getDefaultCtmPaths();
/* 1354 */     for (int i = 0; i < names.length; i++) {
/*      */       
/* 1356 */       String name = names[i];
/* 1357 */       bqx loc = new bqx(name);
/* 1358 */       if (rp.b(loc)) {
/* 1359 */         list.add(name);
/*      */       }
/*      */     } 
/* 1362 */     String[] nameArr = list.<String>toArray(new String[list.size()]);
/* 1363 */     return nameArr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] getDefaultCtmPaths() {
/* 1370 */     List<String> list = new ArrayList();
/*      */ 
/*      */     
/* 1373 */     String defPath = "mcpatcher/ctm/default/";
/*      */     
/* 1375 */     if (Config.isFromDefaultResourcePack(new bqx("textures/blocks/glass.png"))) {
/*      */       
/* 1377 */       list.add(defPath + "glass.properties");
/* 1378 */       list.add(defPath + "glasspane.properties");
/*      */     } 
/*      */     
/* 1381 */     if (Config.isFromDefaultResourcePack(new bqx("textures/blocks/bookshelf.png")))
/*      */     {
/* 1383 */       list.add(defPath + "bookshelf.properties");
/*      */     }
/*      */     
/* 1386 */     if (Config.isFromDefaultResourcePack(new bqx("textures/blocks/sandstone_normal.png")))
/*      */     {
/* 1388 */       list.add(defPath + "sandstone.properties");
/*      */     }
/*      */     
/* 1391 */     String[] colors = { "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black" };
/*      */     
/* 1393 */     for (int i = 0; i < colors.length; i++) {
/*      */       
/* 1395 */       String color = colors[i];
/*      */       
/* 1397 */       if (Config.isFromDefaultResourcePack(new bqx("textures/blocks/glass_" + color + ".png"))) {
/*      */         
/* 1399 */         list.add(defPath + i + "_glass_" + color + "/glass_" + color + ".properties");
/* 1400 */         list.add(defPath + i + "_glass_" + color + "/glass_pane_" + color + ".properties");
/*      */       } 
/*      */     } 
/* 1403 */     String[] paths = list.<String>toArray(new String[list.size()]);
/*      */     
/* 1405 */     return paths;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] collectFilesFolder(File tpFile, String basePath, String prefix, String suffix) {
/* 1415 */     List<String> list = new ArrayList();
/* 1416 */     String prefixAssets = "assets/minecraft/";
/* 1417 */     File[] files = tpFile.listFiles();
/* 1418 */     if (files == null) {
/* 1419 */       return new String[0];
/*      */     }
/* 1421 */     for (int i = 0; i < files.length; i++) {
/*      */       
/* 1423 */       File file = files[i];
/*      */       
/* 1425 */       if (file.isFile()) {
/*      */         
/* 1427 */         String name = basePath + file.getName();
/*      */         
/* 1429 */         if (name.startsWith(prefixAssets))
/*      */         {
/*      */           
/* 1432 */           name = name.substring(prefixAssets.length());
/*      */           
/* 1434 */           if (name.startsWith(prefix))
/*      */           {
/* 1436 */             if (name.endsWith(suffix))
/*      */             {
/*      */               
/* 1439 */               list.add(name);
/*      */             }
/*      */           }
/*      */         }
/*      */       
/* 1444 */       } else if (file.isDirectory()) {
/*      */         
/* 1446 */         String dirPath = basePath + file.getName() + "/";
/* 1447 */         String[] arrayOfString = collectFilesFolder(file, dirPath, prefix, suffix);
/* 1448 */         for (int n = 0; n < arrayOfString.length; n++) {
/*      */           
/* 1450 */           String name = arrayOfString[n];
/* 1451 */           list.add(name);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1458 */     String[] names = list.<String>toArray(new String[list.size()]);
/* 1459 */     return names;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] collectFilesZIP(File tpFile, String prefix, String suffix) {
/* 1469 */     List<String> list = new ArrayList();
/* 1470 */     String prefixAssets = "assets/minecraft/";
/*      */     
/*      */     try {
/* 1473 */       ZipFile zf = new ZipFile(tpFile);
/* 1474 */       Enumeration<? extends ZipEntry> en = zf.entries();
/* 1475 */       while (en.hasMoreElements()) {
/*      */         
/* 1477 */         ZipEntry ze = en.nextElement();
/* 1478 */         String name = ze.getName();
/*      */         
/* 1480 */         if (!name.startsWith(prefixAssets)) {
/*      */           continue;
/*      */         }
/* 1483 */         name = name.substring(prefixAssets.length());
/*      */         
/* 1485 */         if (!name.startsWith(prefix))
/*      */           continue; 
/* 1487 */         if (!name.endsWith(suffix)) {
/*      */           continue;
/*      */         }
/* 1490 */         list.add(name);
/*      */       } 
/* 1492 */       zf.close();
/*      */       
/* 1494 */       String[] names = list.<String>toArray(new String[list.size()]);
/* 1495 */       return names;
/*      */     }
/* 1497 */     catch (IOException e) {
/*      */       
/* 1499 */       e.printStackTrace();
/* 1500 */       return new String[0];
/*      */     } 
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
/*      */   public static int getPaneTextureIndex(boolean linkP, boolean linkN, boolean linkYp, boolean linkYn) {
/* 1514 */     if (linkN && linkP) {
/*      */ 
/*      */       
/* 1517 */       if (linkYp) {
/*      */         
/* 1519 */         if (linkYn)
/*      */         {
/*      */           
/* 1522 */           return 34;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1527 */         return 50;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1532 */       if (linkYn)
/*      */       {
/*      */         
/* 1535 */         return 18;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1540 */       return 2;
/*      */     } 
/*      */ 
/*      */     
/* 1544 */     if (linkN && !linkP) {
/*      */ 
/*      */       
/* 1547 */       if (linkYp) {
/*      */         
/* 1549 */         if (linkYn)
/*      */         {
/*      */           
/* 1552 */           return 35;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1557 */         return 51;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1562 */       if (linkYn)
/*      */       {
/*      */         
/* 1565 */         return 19;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1570 */       return 3;
/*      */     } 
/*      */ 
/*      */     
/* 1574 */     if (!linkN && linkP) {
/*      */ 
/*      */       
/* 1577 */       if (linkYp) {
/*      */         
/* 1579 */         if (linkYn)
/*      */         {
/*      */           
/* 1582 */           return 33;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1587 */         return 49;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1592 */       if (linkYn)
/*      */       {
/*      */         
/* 1595 */         return 17;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1600 */       return 1;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1607 */     if (linkYp) {
/*      */       
/* 1609 */       if (linkYn)
/*      */       {
/*      */         
/* 1612 */         return 32;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1617 */       return 48;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1622 */     if (linkYn)
/*      */     {
/*      */       
/* 1625 */       return 16;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1630 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getReversePaneTextureIndex(int texNum) {
/* 1641 */     int col = texNum % 16;
/* 1642 */     if (col == 1)
/* 1643 */       return texNum + 2; 
/* 1644 */     if (col == 3) {
/* 1645 */       return texNum - 2;
/*      */     }
/* 1647 */     return texNum;
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
/*      */   public static rf getCtmTexture(ConnectedProperties cp, int ctmIndex, rf icon) {
/* 1659 */     if (cp.method != 1) {
/* 1660 */       return icon;
/*      */     }
/* 1662 */     if (ctmIndex < 0 || ctmIndex >= ctmIndexes.length)
/* 1663 */       return icon; 
/* 1664 */     int index = ctmIndexes[ctmIndex];
/*      */     
/* 1666 */     rf[] ctmIcons = cp.tileIcons;
/* 1667 */     if (index < 0 || index >= ctmIcons.length) {
/* 1668 */       return icon;
/*      */     }
/* 1670 */     return ctmIcons[index];
/*      */   }
/*      */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\ConnectedTextures.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */