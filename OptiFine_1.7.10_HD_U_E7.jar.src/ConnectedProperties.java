/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
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
/*      */ public class ConnectedProperties
/*      */ {
/*   22 */   public String name = null;
/*   23 */   public String basePath = null;
/*      */   
/*   25 */   public int[] matchBlocks = null;
/*   26 */   public String[] matchTiles = null;
/*      */   
/*   28 */   public int method = 0;
/*   29 */   public String[] tiles = null;
/*   30 */   public int connect = 0;
/*   31 */   public int faces = 63;
/*   32 */   public int[] metadatas = null;
/*      */   
/*   34 */   public ahu[] biomes = null;
/*   35 */   public RangeListInt heights = null;
/*      */   
/*   37 */   public int renderPass = 0;
/*      */   
/*      */   public boolean innerSeams = false;
/*      */   
/*   41 */   public int width = 0;
/*   42 */   public int height = 0;
/*      */   
/*   44 */   public int[] weights = null;
/*   45 */   public int symmetry = 1;
/*      */   
/*      */   public boolean linked = false;
/*   48 */   public int[] sumWeights = null;
/*      */   
/*   50 */   public int sumAllWeights = 1;
/*      */   
/*   52 */   public rf[] matchTileIcons = null;
/*   53 */   public rf[] tileIcons = null;
/*      */   
/*      */   public static final int METHOD_NONE = 0;
/*      */   
/*      */   public static final int METHOD_CTM = 1;
/*      */   
/*      */   public static final int METHOD_HORIZONTAL = 2;
/*      */   
/*      */   public static final int METHOD_TOP = 3;
/*      */   
/*      */   public static final int METHOD_RANDOM = 4;
/*      */   
/*      */   public static final int METHOD_REPEAT = 5;
/*      */   
/*      */   public static final int METHOD_VERTICAL = 6;
/*      */   
/*      */   public static final int METHOD_FIXED = 7;
/*      */   
/*      */   public static final int METHOD_HORIZONTAL_VERTICAL = 8;
/*      */   public static final int METHOD_VERTICAL_HORIZONTAL = 9;
/*      */   public static final int CONNECT_NONE = 0;
/*      */   public static final int CONNECT_BLOCK = 1;
/*      */   public static final int CONNECT_TILE = 2;
/*      */   public static final int CONNECT_MATERIAL = 3;
/*      */   public static final int CONNECT_UNKNOWN = 128;
/*      */   public static final int FACE_BOTTOM = 1;
/*      */   public static final int FACE_TOP = 2;
/*      */   public static final int FACE_EAST = 4;
/*      */   public static final int FACE_WEST = 8;
/*      */   public static final int FACE_NORTH = 16;
/*      */   public static final int FACE_SOUTH = 32;
/*      */   public static final int FACE_SIDES = 60;
/*      */   public static final int FACE_ALL = 63;
/*      */   public static final int FACE_UNKNOWN = 128;
/*      */   public static final int SYMMETRY_NONE = 1;
/*      */   public static final int SYMMETRY_OPPOSITE = 2;
/*      */   public static final int SYMMETRY_ALL = 6;
/*      */   public static final int SYMMETRY_UNKNOWN = 128;
/*      */   
/*      */   public ConnectedProperties(Properties props, String path) {
/*   93 */     ConnectedParser cp = new ConnectedParser("ConnectedTextures");
/*      */     
/*   95 */     this.name = parseName(path);
/*   96 */     this.basePath = parseBasePath(path);
/*      */     
/*   98 */     this.matchBlocks = parseBlockIds(props.getProperty("matchBlocks"));
/*   99 */     this.matchTiles = parseMatchTiles(props.getProperty("matchTiles"));
/*      */     
/*  101 */     this.method = parseMethod(props.getProperty("method"));
/*  102 */     this.tiles = parseTileNames(props.getProperty("tiles"));
/*  103 */     this.connect = parseConnect(props.getProperty("connect"));
/*  104 */     this.faces = parseFaces(props.getProperty("faces"));
/*  105 */     this.metadatas = parseInts(props.getProperty("metadata"));
/*      */     
/*  107 */     this.biomes = cp.parseBiomes(props.getProperty("biomes"));
/*  108 */     this.heights = cp.parseRangeListInt(props.getProperty("heights"));
/*      */     
/*  110 */     if (this.heights == null) {
/*      */       
/*  112 */       int minHeight = cp.parseInt(props.getProperty("minHeight"), -1);
/*  113 */       int maxHeight = cp.parseInt(props.getProperty("maxHeight"), 1024);
/*  114 */       if (minHeight != -1 || maxHeight != 1024) {
/*  115 */         this.heights = new RangeListInt(new RangeInt(minHeight, maxHeight));
/*      */       }
/*      */     } 
/*  118 */     this.renderPass = parseInt(props.getProperty("renderPass"));
/*      */     
/*  120 */     this.innerSeams = parseBoolean(props.getProperty("innerSeams"));
/*      */     
/*  122 */     this.width = parseInt(props.getProperty("width"));
/*  123 */     this.height = parseInt(props.getProperty("height"));
/*      */     
/*  125 */     this.weights = parseInts(props.getProperty("weights"));
/*  126 */     this.symmetry = parseSymmetry(props.getProperty("symmetry"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String[] parseMatchTiles(String str) {
/*  135 */     if (str == null) {
/*  136 */       return null;
/*      */     }
/*  138 */     String[] names = Config.tokenize(str, " ");
/*      */     
/*  140 */     for (int i = 0; i < names.length; i++) {
/*      */       
/*  142 */       String iconName = names[i];
/*      */       
/*  144 */       if (iconName.endsWith(".png")) {
/*  145 */         iconName = iconName.substring(0, iconName.length() - 4);
/*      */       }
/*  147 */       iconName = TextureUtils.fixResourcePath(iconName, this.basePath);
/*      */       
/*  149 */       names[i] = iconName;
/*      */     } 
/*      */     
/*  152 */     return names;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String parseName(String path) {
/*  160 */     String str = path;
/*      */     
/*  162 */     int pos = str.lastIndexOf('/');
/*  163 */     if (pos >= 0) {
/*  164 */       str = str.substring(pos + 1);
/*      */     }
/*  166 */     int pos2 = str.lastIndexOf('.');
/*  167 */     if (pos2 >= 0) {
/*  168 */       str = str.substring(0, pos2);
/*      */     }
/*  170 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String parseBasePath(String path) {
/*  178 */     int pos = path.lastIndexOf('/');
/*  179 */     if (pos < 0) {
/*  180 */       return "";
/*      */     }
/*  182 */     return path.substring(0, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ahu[] parseBiomes(String str) {
/*  190 */     if (str == null) {
/*  191 */       return null;
/*      */     }
/*  193 */     String[] biomeNames = Config.tokenize(str, " ");
/*  194 */     List<ahu> list = new ArrayList();
/*  195 */     for (int i = 0; i < biomeNames.length; i++) {
/*      */       
/*  197 */       String biomeName = biomeNames[i];
/*  198 */       ahu biome = findBiome(biomeName);
/*  199 */       if (biome == null) {
/*      */         
/*  201 */         Config.warn("Biome not found: " + biomeName);
/*      */       } else {
/*      */         
/*  204 */         list.add(biome);
/*      */       } 
/*      */     } 
/*  207 */     ahu[] biomeArr = list.<ahu>toArray(new ahu[list.size()]);
/*  208 */     return biomeArr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ahu findBiome(String biomeName) {
/*  216 */     biomeName = biomeName.toLowerCase();
/*  217 */     ahu[] biomeList = ahu.n();
/*  218 */     for (int i = 0; i < biomeList.length; i++) {
/*      */       
/*  220 */       ahu biome = biomeList[i];
/*  221 */       if (biome != null) {
/*      */         
/*  223 */         String name = biome.af.replace(" ", "").toLowerCase();
/*  224 */         if (name.equals(biomeName))
/*  225 */           return biome; 
/*      */       } 
/*      */     } 
/*  228 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String[] parseTileNames(String str) {
/*  236 */     if (str == null) {
/*  237 */       return null;
/*      */     }
/*  239 */     List<String> list = new ArrayList();
/*  240 */     String[] iconStrs = Config.tokenize(str, " ,");
/*  241 */     for (int i = 0; i < iconStrs.length; i++) {
/*      */       
/*  243 */       String iconStr = iconStrs[i];
/*      */       
/*  245 */       if (iconStr.contains("-")) {
/*      */         
/*  247 */         String[] subStrs = Config.tokenize(iconStr, "-");
/*  248 */         if (subStrs.length == 2) {
/*      */           
/*  250 */           int min = Config.parseInt(subStrs[0], -1);
/*  251 */           int max = Config.parseInt(subStrs[1], -1);
/*      */           
/*  253 */           if (min >= 0 && max >= 0) {
/*      */             
/*  255 */             if (min > max) {
/*      */               
/*  257 */               Config.warn("Invalid interval: " + iconStr + ", when parsing: " + str);
/*      */             }
/*      */             else {
/*      */               
/*  261 */               for (int n = min; n <= max; n++)
/*      */               {
/*      */                 
/*  264 */                 list.add(String.valueOf(n));
/*      */               }
/*      */             } 
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/*      */       } 
/*  272 */       list.add(iconStr);
/*      */       continue;
/*      */     } 
/*  275 */     String[] names = list.<String>toArray(new String[list.size()]);
/*      */     
/*  277 */     for (int j = 0; j < names.length; j++) {
/*      */       
/*  279 */       String iconName = names[j];
/*      */       
/*  281 */       iconName = TextureUtils.fixResourcePath(iconName, this.basePath);
/*      */       
/*  283 */       if (!iconName.startsWith(this.basePath) && !iconName.startsWith("textures/") && !iconName.startsWith("mcpatcher/")) {
/*  284 */         iconName = this.basePath + "/" + iconName;
/*      */       }
/*  286 */       if (iconName.endsWith(".png")) {
/*  287 */         iconName = iconName.substring(0, iconName.length() - 4);
/*      */       }
/*  289 */       String pathBlocks = "textures/blocks/";
/*  290 */       if (iconName.startsWith(pathBlocks)) {
/*  291 */         iconName = iconName.substring(pathBlocks.length());
/*      */       }
/*  293 */       if (iconName.startsWith("/")) {
/*  294 */         iconName = iconName.substring(1);
/*      */       }
/*  296 */       names[j] = iconName;
/*      */     } 
/*      */     
/*  299 */     return names;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseInt(String str) {
/*  307 */     if (str == null) {
/*  308 */       return -1;
/*      */     }
/*  310 */     int num = Config.parseInt(str, -1);
/*  311 */     if (num < 0) {
/*  312 */       Config.warn("Invalid number: " + str);
/*      */     }
/*  314 */     return num;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseInt(String str, int defVal) {
/*  322 */     if (str == null) {
/*  323 */       return defVal;
/*      */     }
/*  325 */     int num = Config.parseInt(str, -1);
/*  326 */     if (num < 0) {
/*      */       
/*  328 */       Config.warn("Invalid number: " + str);
/*  329 */       return defVal;
/*      */     } 
/*      */     
/*  332 */     return num;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean parseBoolean(String str) {
/*  340 */     if (str == null) {
/*  341 */       return false;
/*      */     }
/*  343 */     return str.toLowerCase().equals("true");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseSymmetry(String str) {
/*  351 */     if (str == null) {
/*  352 */       return 1;
/*      */     }
/*  354 */     str = str.trim();
/*      */     
/*  356 */     if (str.equals("opposite"))
/*  357 */       return 2; 
/*  358 */     if (str.equals("all")) {
/*  359 */       return 6;
/*      */     }
/*  361 */     Config.warn("Unknown symmetry: " + str);
/*      */     
/*  363 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseFaces(String str) {
/*  372 */     if (str == null) {
/*  373 */       return 63;
/*      */     }
/*  375 */     String[] faceStrs = Config.tokenize(str, " ,");
/*  376 */     int facesMask = 0;
/*  377 */     for (int i = 0; i < faceStrs.length; i++) {
/*      */       
/*  379 */       String faceStr = faceStrs[i];
/*  380 */       int faceMask = parseFace(faceStr);
/*      */       
/*  382 */       facesMask |= faceMask;
/*      */     } 
/*  384 */     return facesMask;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseFace(String str) {
/*  393 */     str = str.toLowerCase();
/*      */     
/*  395 */     if (str.equals("bottom"))
/*  396 */       return 1; 
/*  397 */     if (str.equals("top")) {
/*  398 */       return 2;
/*      */     }
/*      */     
/*  401 */     if (str.equals("north")) {
/*  402 */       return 4;
/*      */     }
/*  404 */     if (str.equals("south")) {
/*  405 */       return 8;
/*      */     }
/*  407 */     if (str.equals("east")) {
/*  408 */       return 32;
/*      */     }
/*  410 */     if (str.equals("west")) {
/*  411 */       return 16;
/*      */     }
/*  413 */     if (str.equals("sides"))
/*  414 */       return 60; 
/*  415 */     if (str.equals("all")) {
/*  416 */       return 63;
/*      */     }
/*  418 */     Config.warn("Unknown face: " + str);
/*      */     
/*  420 */     return 128;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseConnect(String str) {
/*  428 */     if (str == null) {
/*  429 */       return 0;
/*      */     }
/*  431 */     str = str.trim();
/*      */     
/*  433 */     if (str.equals("block"))
/*  434 */       return 1; 
/*  435 */     if (str.equals("tile"))
/*  436 */       return 2; 
/*  437 */     if (str.equals("material")) {
/*  438 */       return 3;
/*      */     }
/*  440 */     Config.warn("Unknown connect: " + str);
/*      */     
/*  442 */     return 128;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int[] parseInts(String str) {
/*  450 */     if (str == null) {
/*  451 */       return null;
/*      */     }
/*  453 */     List<Integer> list = new ArrayList();
/*  454 */     String[] intStrs = Config.tokenize(str, " ,");
/*  455 */     for (int i = 0; i < intStrs.length; i++) {
/*      */       
/*  457 */       String intStr = intStrs[i];
/*      */       
/*  459 */       if (intStr.contains("-")) {
/*      */         
/*  461 */         String[] subStrs = Config.tokenize(intStr, "-");
/*  462 */         if (subStrs.length != 2) {
/*      */           
/*  464 */           Config.warn("Invalid interval: " + intStr + ", when parsing: " + str);
/*      */         } else {
/*      */           
/*  467 */           int min = Config.parseInt(subStrs[0], -1);
/*  468 */           int max = Config.parseInt(subStrs[1], -1);
/*  469 */           if (min < 0 || max < 0 || min > max) {
/*      */             
/*  471 */             Config.warn("Invalid interval: " + intStr + ", when parsing: " + str);
/*      */           }
/*      */           else {
/*      */             
/*  475 */             for (int n = min; n <= max; n++)
/*      */             {
/*  477 */               list.add(Integer.valueOf(n));
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } else {
/*      */         
/*  483 */         int val = Config.parseInt(intStr, -1);
/*  484 */         if (val < 0) {
/*      */           
/*  486 */           Config.warn("Invalid number: " + intStr + ", when parsing: " + str);
/*      */         }
/*      */         else {
/*      */           
/*  490 */           list.add(Integer.valueOf(val));
/*      */         } 
/*      */       } 
/*  493 */     }  int[] ints = new int[list.size()];
/*  494 */     for (int j = 0; j < ints.length; j++)
/*      */     {
/*  496 */       ints[j] = ((Integer)list.get(j)).intValue();
/*      */     }
/*      */     
/*  499 */     return ints;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int[] parseBlockIds(String str) {
/*  507 */     if (str == null) {
/*  508 */       return null;
/*      */     }
/*  510 */     List<Integer> list = new ArrayList();
/*  511 */     String[] intStrs = Config.tokenize(str, " ,");
/*  512 */     for (int i = 0; i < intStrs.length; i++) {
/*      */       
/*  514 */       String intStr = intStrs[i];
/*      */       
/*  516 */       if (intStr.contains("-")) {
/*      */         
/*  518 */         String[] subStrs = Config.tokenize(intStr, "-");
/*  519 */         if (subStrs.length != 2) {
/*      */           
/*  521 */           Config.warn("Invalid interval: " + intStr + ", when parsing: " + str);
/*      */         } else {
/*      */           
/*  524 */           int min = parseBlockId(subStrs[0]);
/*  525 */           int max = parseBlockId(subStrs[1]);
/*  526 */           if (min < 0 || max < 0 || min > max) {
/*      */             
/*  528 */             Config.warn("Invalid interval: " + intStr + ", when parsing: " + str);
/*      */           }
/*      */           else {
/*      */             
/*  532 */             for (int n = min; n <= max; n++)
/*      */             {
/*  534 */               list.add(Integer.valueOf(n));
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } else {
/*      */         
/*  540 */         int val = parseBlockId(intStr);
/*  541 */         if (val < 0) {
/*      */           
/*  543 */           Config.warn("Invalid block ID: " + intStr + ", when parsing: " + str);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  548 */           list.add(Integer.valueOf(val));
/*      */         } 
/*      */       } 
/*  551 */     }  int[] ints = new int[list.size()];
/*  552 */     for (int j = 0; j < ints.length; j++)
/*      */     {
/*  554 */       ints[j] = ((Integer)list.get(j)).intValue();
/*      */     }
/*      */     
/*  557 */     return ints;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseBlockId(String blockStr) {
/*  567 */     int val = Config.parseInt(blockStr, -1);
/*  568 */     if (val >= 0) {
/*  569 */       return val;
/*      */     }
/*  571 */     aji block = aji.b(blockStr);
/*  572 */     if (block != null) {
/*  573 */       return aji.b(block);
/*      */     }
/*  575 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseMethod(String str) {
/*  584 */     if (str == null) {
/*  585 */       return 1;
/*      */     }
/*  587 */     str = str.trim();
/*      */     
/*  589 */     if (str.equals("ctm") || str.equals("glass"))
/*  590 */       return 1; 
/*  591 */     if (str.equals("horizontal") || str.equals("bookshelf"))
/*  592 */       return 2; 
/*  593 */     if (str.equals("vertical"))
/*  594 */       return 6; 
/*  595 */     if (str.equals("top"))
/*  596 */       return 3; 
/*  597 */     if (str.equals("random"))
/*  598 */       return 4; 
/*  599 */     if (str.equals("repeat"))
/*  600 */       return 5; 
/*  601 */     if (str.equals("fixed"))
/*  602 */       return 7; 
/*  603 */     if (str.equals("horizontal+vertical") || str.equals("h+v"))
/*  604 */       return 8; 
/*  605 */     if (str.equals("vertical+horizontal") || str.equals("v+h")) {
/*  606 */       return 9;
/*      */     }
/*  608 */     Config.warn("Unknown method: " + str);
/*      */     
/*  610 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isValid(String path) {
/*  618 */     if (this.name == null || this.name.length() <= 0) {
/*      */       
/*  620 */       Config.warn("No name found: " + path);
/*  621 */       return false;
/*      */     } 
/*      */     
/*  624 */     if (this.basePath == null) {
/*      */       
/*  626 */       Config.warn("No base path found: " + path);
/*  627 */       return false;
/*      */     } 
/*      */     
/*  630 */     if (this.matchBlocks == null)
/*      */     {
/*      */       
/*  633 */       this.matchBlocks = detectMatchBlocks();
/*      */     }
/*  635 */     if (this.matchTiles == null)
/*      */     {
/*      */       
/*  638 */       if (this.matchBlocks == null)
/*  639 */         this.matchTiles = detectMatchTiles(); 
/*      */     }
/*  641 */     if (this.matchBlocks == null && this.matchTiles == null) {
/*      */       
/*  643 */       Config.warn("No matchBlocks or matchTiles specified: " + path);
/*  644 */       return false;
/*      */     } 
/*      */     
/*  647 */     if (this.method == 0) {
/*      */       
/*  649 */       Config.warn("No method: " + path);
/*  650 */       return false;
/*      */     } 
/*      */     
/*  653 */     if (this.tiles == null || this.tiles.length <= 0) {
/*      */       
/*  655 */       Config.warn("No tiles specified: " + path);
/*  656 */       return false;
/*      */     } 
/*      */     
/*  659 */     if (this.connect == 0) {
/*  660 */       this.connect = detectConnect();
/*      */     }
/*  662 */     if (this.connect == 128) {
/*      */       
/*  664 */       Config.warn("Invalid connect in: " + path);
/*  665 */       return false;
/*      */     } 
/*      */     
/*  668 */     if (this.renderPass > 0) {
/*      */       
/*  670 */       Config.warn("Render pass not supported: " + this.renderPass);
/*  671 */       return false;
/*      */     } 
/*      */     
/*  674 */     if ((this.faces & 0x80) != 0) {
/*      */       
/*  676 */       Config.warn("Invalid faces in: " + path);
/*  677 */       return false;
/*      */     } 
/*      */     
/*  680 */     if ((this.symmetry & 0x80) != 0) {
/*      */       
/*  682 */       Config.warn("Invalid symmetry in: " + path);
/*  683 */       return false;
/*      */     } 
/*      */     
/*  686 */     switch (this.method) {
/*      */       
/*      */       case 1:
/*  689 */         return isValidCtm(path);
/*      */       case 2:
/*  691 */         return isValidHorizontal(path);
/*      */       case 6:
/*  693 */         return isValidVertical(path);
/*      */       case 3:
/*  695 */         return isValidTop(path);
/*      */       case 4:
/*  697 */         return isValidRandom(path);
/*      */       case 5:
/*  699 */         return isValidRepeat(path);
/*      */       case 7:
/*  701 */         return isValidFixed(path);
/*      */       case 8:
/*  703 */         return isValidHorizontalVertical(path);
/*      */       case 9:
/*  705 */         return isValidVerticalHorizontal(path);
/*      */     } 
/*      */     
/*  708 */     Config.warn("Unknown method: " + path);
/*  709 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int detectConnect() {
/*  717 */     if (this.matchBlocks != null)
/*  718 */       return 1; 
/*  719 */     if (this.matchTiles != null) {
/*  720 */       return 2;
/*      */     }
/*  722 */     return 128;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] detectMatchBlocks() {
/*  729 */     if (!this.name.startsWith("block")) {
/*  730 */       return null;
/*      */     }
/*  732 */     int startPos = "block".length();
/*  733 */     int pos = startPos;
/*  734 */     while (pos < this.name.length()) {
/*      */       
/*  736 */       char ch = this.name.charAt(pos);
/*      */       
/*  738 */       if (ch < '0' || ch > '9') {
/*      */         break;
/*      */       }
/*  741 */       pos++;
/*      */     } 
/*      */     
/*  744 */     if (pos == startPos) {
/*  745 */       return null;
/*      */     }
/*  747 */     String idStr = this.name.substring(startPos, pos);
/*  748 */     int id = Config.parseInt(idStr, -1);
/*  749 */     if (id < 0) {
/*  750 */       return null;
/*      */     }
/*  752 */     return new int[] { id };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String[] detectMatchTiles() {
/*  760 */     rf icon = getIcon(this.name);
/*  761 */     if (icon == null) {
/*  762 */       return null;
/*      */     }
/*  764 */     return new String[] { this.name };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static rf getIcon(String iconName) {
/*  773 */     return (rf)bpz.textureMapBlocks.getIconSafe(iconName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidCtm(String path) {
/*  782 */     if (this.tiles == null) {
/*  783 */       this.tiles = parseTileNames("0-11 16-27 32-43 48-58");
/*      */     }
/*  785 */     if (this.tiles.length < 47) {
/*      */       
/*  787 */       Config.warn("Invalid tiles, must be at least 47: " + path);
/*  788 */       return false;
/*      */     } 
/*      */     
/*  791 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidHorizontal(String path) {
/*  800 */     if (this.tiles == null) {
/*  801 */       this.tiles = parseTileNames("12-15");
/*      */     }
/*  803 */     if (this.tiles.length != 4) {
/*      */       
/*  805 */       Config.warn("Invalid tiles, must be exactly 4: " + path);
/*  806 */       return false;
/*      */     } 
/*      */     
/*  809 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidVertical(String path) {
/*  819 */     if (this.tiles == null) {
/*      */       
/*  821 */       Config.warn("No tiles defined for vertical: " + path);
/*  822 */       return false;
/*      */     } 
/*      */     
/*  825 */     if (this.tiles.length != 4) {
/*      */       
/*  827 */       Config.warn("Invalid tiles, must be exactly 4: " + path);
/*  828 */       return false;
/*      */     } 
/*      */     
/*  831 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidHorizontalVertical(String path) {
/*  841 */     if (this.tiles == null) {
/*      */       
/*  843 */       Config.warn("No tiles defined for horizontal+vertical: " + path);
/*  844 */       return false;
/*      */     } 
/*      */     
/*  847 */     if (this.tiles.length != 7) {
/*      */       
/*  849 */       Config.warn("Invalid tiles, must be exactly 7: " + path);
/*  850 */       return false;
/*      */     } 
/*      */     
/*  853 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidVerticalHorizontal(String path) {
/*  862 */     if (this.tiles == null) {
/*      */       
/*  864 */       Config.warn("No tiles defined for vertical+horizontal: " + path);
/*  865 */       return false;
/*      */     } 
/*      */     
/*  868 */     if (this.tiles.length != 7) {
/*      */       
/*  870 */       Config.warn("Invalid tiles, must be exactly 7: " + path);
/*  871 */       return false;
/*      */     } 
/*      */     
/*  874 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidRandom(String path) {
/*  884 */     if (this.tiles == null || this.tiles.length <= 0) {
/*      */       
/*  886 */       Config.warn("Tiles not defined: " + path);
/*  887 */       return false;
/*      */     } 
/*      */     
/*  890 */     if (this.weights != null) {
/*      */       
/*  892 */       if (this.weights.length > this.tiles.length) {
/*      */         
/*  894 */         Config.warn("More weights defined than tiles, trimming weights: " + path);
/*  895 */         int[] weights2 = new int[this.tiles.length];
/*  896 */         System.arraycopy(this.weights, 0, weights2, 0, weights2.length);
/*  897 */         this.weights = weights2;
/*      */       } 
/*  899 */       if (this.weights.length < this.tiles.length) {
/*      */         
/*  901 */         Config.warn("Less weights defined than tiles, expanding weights: " + path);
/*  902 */         int[] weights2 = new int[this.tiles.length];
/*  903 */         System.arraycopy(this.weights, 0, weights2, 0, this.weights.length);
/*  904 */         int avgWeight = getAverage(this.weights);
/*  905 */         for (int j = this.weights.length; j < weights2.length; j++)
/*      */         {
/*  907 */           weights2[j] = avgWeight;
/*      */         }
/*  909 */         this.weights = weights2;
/*      */       } 
/*      */       
/*  912 */       this.sumWeights = new int[this.weights.length];
/*  913 */       int sum = 0;
/*  914 */       for (int i = 0; i < this.weights.length; i++) {
/*      */         
/*  916 */         sum += this.weights[i];
/*  917 */         this.sumWeights[i] = sum;
/*      */       } 
/*  919 */       this.sumAllWeights = sum;
/*      */       
/*  921 */       if (this.sumAllWeights <= 0) {
/*      */         
/*  923 */         Config.warn("Invalid sum of all weights: " + sum);
/*  924 */         this.sumAllWeights = 1;
/*      */       } 
/*      */     } 
/*      */     
/*  928 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int getAverage(int[] vals) {
/*  934 */     if (vals.length <= 0) {
/*  935 */       return 0;
/*      */     }
/*  937 */     int sum = 0;
/*  938 */     for (int i = 0; i < vals.length; i++) {
/*      */       
/*  940 */       int val = vals[i];
/*  941 */       sum += val;
/*      */     } 
/*  943 */     int avg = sum / vals.length;
/*      */     
/*  945 */     return avg;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidRepeat(String path) {
/*  954 */     if (this.tiles == null) {
/*      */       
/*  956 */       Config.warn("Tiles not defined: " + path);
/*  957 */       return false;
/*      */     } 
/*      */     
/*  960 */     if (this.width <= 0 || this.width > 16) {
/*      */       
/*  962 */       Config.warn("Invalid width: " + path);
/*  963 */       return false;
/*      */     } 
/*      */     
/*  966 */     if (this.height <= 0 || this.height > 16) {
/*      */       
/*  968 */       Config.warn("Invalid height: " + path);
/*  969 */       return false;
/*      */     } 
/*      */     
/*  972 */     if (this.tiles.length != this.width * this.height) {
/*      */       
/*  974 */       Config.warn("Number of tiles does not equal width x height: " + path);
/*  975 */       return false;
/*      */     } 
/*      */     
/*  978 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidFixed(String path) {
/*  987 */     if (this.tiles == null) {
/*      */       
/*  989 */       Config.warn("Tiles not defined: " + path);
/*  990 */       return false;
/*      */     } 
/*      */     
/*  993 */     if (this.tiles.length != 1) {
/*      */       
/*  995 */       Config.warn("Number of tiles should be 1 for method: fixed.");
/*  996 */       return false;
/*      */     } 
/*      */     
/*  999 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidTop(String path) {
/* 1008 */     if (this.tiles == null) {
/* 1009 */       this.tiles = parseTileNames("66");
/*      */     }
/* 1011 */     if (this.tiles.length != 1) {
/*      */       
/* 1013 */       Config.warn("Invalid tiles, must be exactly 1: " + path);
/* 1014 */       return false;
/*      */     } 
/*      */     
/* 1017 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateIcons(bpz textureMap) {
/* 1026 */     if (this.matchTiles != null)
/*      */     {
/*      */       
/* 1029 */       this.matchTileIcons = registerIcons(this.matchTiles, textureMap);
/*      */     }
/*      */     
/* 1032 */     if (this.tiles != null)
/*      */     {
/*      */       
/* 1035 */       this.tileIcons = registerIcons(this.tiles, textureMap);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static rf[] registerIcons(String[] tileNames, bpz textureMap) {
/* 1046 */     if (tileNames == null) {
/* 1047 */       return null;
/*      */     }
/* 1049 */     List<rf> iconList = new ArrayList();
/* 1050 */     for (int i = 0; i < tileNames.length; i++) {
/*      */       
/* 1052 */       String iconName = tileNames[i];
/* 1053 */       String fullName = iconName;
/*      */       
/* 1055 */       if (!fullName.contains("/")) {
/* 1056 */         fullName = "textures/blocks/" + fullName;
/*      */       }
/* 1058 */       String fileName = fullName + ".png";
/*      */       
/* 1060 */       bqx loc = new bqx(fileName);
/* 1061 */       boolean exists = Config.hasResource(loc);
/* 1062 */       if (!exists) {
/* 1063 */         Config.warn("File not found: " + fileName);
/*      */       }
/* 1065 */       rf icon = textureMap.a(iconName);
/*      */       
/* 1067 */       iconList.add(icon);
/*      */     } 
/*      */     
/* 1070 */     rf[] icons = iconList.<rf>toArray(new rf[iconList.size()]);
/* 1071 */     return icons;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1078 */     return "CTM name: " + this.name + ", basePath: " + this.basePath + ", matchBlocks: " + Config.arrayToString(this.matchBlocks) + ", matchTiles: " + Config.arrayToString((Object[])this.matchTiles);
/*      */   }
/*      */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\ConnectedProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */