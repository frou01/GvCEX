/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
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
/*     */ public class ConnectedParser
/*     */ {
/*  20 */   private String context = null;
/*     */   
/*  22 */   public static final VillagerProfession[] PROFESSIONS_INVALID = new VillagerProfession[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConnectedParser(String context) {
/*  29 */     this.context = context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String parseName(String path) {
/*  38 */     String str = path;
/*     */     
/*  40 */     int pos = str.lastIndexOf('/');
/*  41 */     if (pos >= 0) {
/*  42 */       str = str.substring(pos + 1);
/*     */     }
/*  44 */     int pos2 = str.lastIndexOf('.');
/*  45 */     if (pos2 >= 0) {
/*  46 */       str = str.substring(0, pos2);
/*     */     }
/*  48 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String parseBasePath(String path) {
/*  56 */     int pos = path.lastIndexOf('/');
/*  57 */     if (pos < 0) {
/*  58 */       return "";
/*     */     }
/*  60 */     return path.substring(0, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MatchBlock[] parseMatchBlocks(String propMatchBlocks) {
/*  66 */     if (propMatchBlocks == null) {
/*  67 */       return null;
/*     */     }
/*  69 */     List list = new ArrayList();
/*  70 */     String[] blockStrs = Config.tokenize(propMatchBlocks, " ");
/*  71 */     for (int i = 0; i < blockStrs.length; i++) {
/*     */       
/*  73 */       String blockStr = blockStrs[i];
/*  74 */       MatchBlock[] arrayOfMatchBlock = parseMatchBlock(blockStr);
/*  75 */       if (arrayOfMatchBlock != null)
/*     */       {
/*     */         
/*  78 */         list.addAll(Arrays.asList(arrayOfMatchBlock));
/*     */       }
/*     */     } 
/*  81 */     MatchBlock[] mbs = (MatchBlock[])list.toArray((Object[])new MatchBlock[list.size()]);
/*     */     
/*  83 */     return mbs;
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
/*     */   public MatchBlock[] parseMatchBlock(String blockStr) {
/* 113 */     if (blockStr == null) {
/* 114 */       return null;
/*     */     }
/* 116 */     blockStr = blockStr.trim();
/*     */     
/* 118 */     if (blockStr.length() <= 0) {
/* 119 */       return null;
/*     */     }
/* 121 */     String[] parts = Config.tokenize(blockStr, ":");
/*     */     
/* 123 */     String domain = "minecraft";
/* 124 */     int blockIndex = 0;
/*     */     
/* 126 */     if (parts.length > 1 && isFullBlockName(parts)) {
/*     */       
/* 128 */       domain = parts[0];
/* 129 */       blockIndex = 1;
/*     */     }
/*     */     else {
/*     */       
/* 133 */       domain = "minecraft";
/* 134 */       blockIndex = 0;
/*     */     } 
/*     */     
/* 137 */     String blockPart = parts[blockIndex];
/* 138 */     String[] params = Arrays.<String>copyOfRange(parts, blockIndex + 1, parts.length);
/*     */     
/* 140 */     aji[] blocks = parseBlockPart(domain, blockPart);
/*     */     
/* 142 */     if (blocks == null) {
/* 143 */       return null;
/*     */     }
/* 145 */     MatchBlock[] datas = new MatchBlock[blocks.length];
/* 146 */     for (int i = 0; i < blocks.length; i++) {
/*     */       
/* 148 */       aji block = blocks[i];
/* 149 */       int blockId = aji.b(block);
/*     */       
/* 151 */       int[] metadatas = null;
/* 152 */       if (params.length > 0) {
/*     */         
/* 154 */         metadatas = parseBlockMetadatas(block, params);
/*     */         
/* 156 */         if (metadatas == null) {
/* 157 */           return null;
/*     */         }
/*     */       } 
/* 160 */       MatchBlock bd = new MatchBlock(blockId, metadatas);
/* 161 */       datas[i] = bd;
/*     */     } 
/*     */     
/* 164 */     return datas;
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
/*     */   public boolean isFullBlockName(String[] parts) {
/* 176 */     if (parts.length < 2) {
/* 177 */       return false;
/*     */     }
/* 179 */     String part1 = parts[1];
/* 180 */     if (part1.length() < 1) {
/* 181 */       return false;
/*     */     }
/* 183 */     if (startsWithDigit(part1)) {
/* 184 */       return false;
/*     */     }
/* 186 */     if (part1.contains("=")) {
/* 187 */       return false;
/*     */     }
/* 189 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean startsWithDigit(String str) {
/* 197 */     if (str == null) {
/* 198 */       return false;
/*     */     }
/* 200 */     if (str.length() < 1) {
/* 201 */       return false;
/*     */     }
/* 203 */     char ch = str.charAt(0);
/*     */     
/* 205 */     return Character.isDigit(ch);
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
/*     */   public aji[] parseBlockPart(String domain, String blockPart) {
/* 217 */     if (startsWithDigit(blockPart)) {
/*     */ 
/*     */       
/* 220 */       int[] ids = parseIntList(blockPart);
/* 221 */       if (ids == null)
/* 222 */         return null; 
/* 223 */       aji[] arrayOfAji = new aji[ids.length];
/* 224 */       for (int i = 0; i < ids.length; i++) {
/*     */         
/* 226 */         int id = ids[i];
/* 227 */         aji aji = aji.e(id);
/* 228 */         if (aji == null) {
/*     */           
/* 230 */           warn("Block not found for id: " + id);
/* 231 */           return null;
/*     */         } 
/* 233 */         arrayOfAji[i] = aji;
/*     */       } 
/* 235 */       return arrayOfAji;
/*     */     } 
/*     */     
/* 238 */     String fullName = domain + ":" + blockPart;
/* 239 */     aji block = aji.b(fullName);
/* 240 */     if (block == null) {
/*     */       
/* 242 */       warn("Block not found for name: " + fullName);
/* 243 */       return null;
/*     */     } 
/*     */     
/* 246 */     aji[] blocks = { block };
/*     */     
/* 248 */     return blocks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] parseBlockMetadatas(aji block, String[] params) {
/* 258 */     if (params.length <= 0) {
/* 259 */       return null;
/*     */     }
/* 261 */     String param0 = params[0];
/* 262 */     if (startsWithDigit(param0)) {
/*     */ 
/*     */       
/* 265 */       int[] mds = parseIntList(param0);
/* 266 */       return mds;
/*     */     } 
/*     */     
/* 269 */     warn("Invalid block metadata: " + param0);
/* 270 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ahu[] parseBiomes(String str) {
/* 279 */     if (str == null) {
/* 280 */       return null;
/*     */     }
/* 282 */     str = str.trim();
/*     */     
/* 284 */     boolean negative = false;
/* 285 */     if (str.startsWith("!")) {
/*     */       
/* 287 */       negative = true;
/* 288 */       str = str.substring(1);
/*     */     } 
/*     */     
/* 291 */     String[] biomeNames = Config.tokenize(str, " ");
/* 292 */     List<ahu> list = new ArrayList();
/* 293 */     for (int i = 0; i < biomeNames.length; i++) {
/*     */       
/* 295 */       String biomeName = biomeNames[i];
/* 296 */       ahu biome = findBiome(biomeName);
/* 297 */       if (biome == null) {
/*     */         
/* 299 */         warn("Biome not found: " + biomeName);
/*     */       } else {
/*     */         
/* 302 */         list.add(biome);
/*     */       } 
/*     */     } 
/* 305 */     if (negative) {
/*     */       
/* 307 */       List<ahu> listAllBiomes = new ArrayList<ahu>(Arrays.asList(ahu.n()));
/* 308 */       listAllBiomes.removeAll(list);
/* 309 */       list = listAllBiomes;
/*     */     } 
/*     */     
/* 312 */     ahu[] biomeArr = list.<ahu>toArray(new ahu[list.size()]);
/* 313 */     return biomeArr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ahu findBiome(String biomeName) {
/* 322 */     biomeName = biomeName.toLowerCase();
/*     */     
/* 324 */     if (biomeName.equals("nether")) {
/* 325 */       return ahu.w;
/*     */     }
/* 327 */     ahu[] biomeList = ahu.n();
/* 328 */     for (int i = 0; i < biomeList.length; i++) {
/*     */       
/* 330 */       ahu biome = biomeList[i];
/* 331 */       if (biome != null) {
/*     */         
/* 333 */         String name = biome.af.replace(" ", "").toLowerCase();
/* 334 */         if (name.equals(biomeName))
/* 335 */           return biome; 
/*     */       } 
/*     */     } 
/* 338 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int parseInt(String str) {
/* 348 */     if (str == null) {
/* 349 */       return -1;
/*     */     }
/* 351 */     str = str.trim();
/*     */     
/* 353 */     int num = Config.parseInt(str, -1);
/* 354 */     if (num < 0) {
/* 355 */       warn("Invalid number: " + str);
/*     */     }
/* 357 */     return num;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int parseInt(String str, int defVal) {
/* 365 */     if (str == null) {
/* 366 */       return defVal;
/*     */     }
/* 368 */     str = str.trim();
/*     */     
/* 370 */     int num = Config.parseInt(str, -1);
/* 371 */     if (num < 0) {
/*     */       
/* 373 */       warn("Invalid number: " + str);
/* 374 */       return defVal;
/*     */     } 
/*     */     
/* 377 */     return num;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] parseIntList(String str) {
/* 386 */     if (str == null) {
/* 387 */       return null;
/*     */     }
/* 389 */     List<Integer> list = new ArrayList();
/* 390 */     String[] intStrs = Config.tokenize(str, " ,");
/* 391 */     for (int i = 0; i < intStrs.length; i++) {
/*     */       
/* 393 */       String intStr = intStrs[i];
/*     */       
/* 395 */       if (intStr.contains("-")) {
/*     */         
/* 397 */         String[] subStrs = Config.tokenize(intStr, "-");
/* 398 */         if (subStrs.length != 2) {
/*     */           
/* 400 */           warn("Invalid interval: " + intStr + ", when parsing: " + str);
/*     */         } else {
/*     */           
/* 403 */           int min = Config.parseInt(subStrs[0], -1);
/* 404 */           int max = Config.parseInt(subStrs[1], -1);
/* 405 */           if (min < 0 || max < 0 || min > max) {
/*     */             
/* 407 */             warn("Invalid interval: " + intStr + ", when parsing: " + str);
/*     */           }
/*     */           else {
/*     */             
/* 411 */             for (int n = min; n <= max; n++)
/*     */             {
/* 413 */               list.add(Integer.valueOf(n));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 419 */         int val = Config.parseInt(intStr, -1);
/* 420 */         if (val < 0) {
/*     */           
/* 422 */           warn("Invalid number: " + intStr + ", when parsing: " + str);
/*     */         }
/*     */         else {
/*     */           
/* 426 */           list.add(Integer.valueOf(val));
/*     */         } 
/*     */       } 
/* 429 */     }  int[] ints = new int[list.size()];
/* 430 */     for (int j = 0; j < ints.length; j++)
/*     */     {
/* 432 */       ints[j] = ((Integer)list.get(j)).intValue();
/*     */     }
/*     */     
/* 435 */     return ints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dbg(String str) {
/* 443 */     Config.dbg("" + this.context + ": " + str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void warn(String str) {
/* 450 */     Config.warn("" + this.context + ": " + str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RangeListInt parseRangeListInt(String str) {
/* 459 */     if (str == null) {
/* 460 */       return null;
/*     */     }
/* 462 */     RangeListInt list = new RangeListInt();
/* 463 */     String[] parts = Config.tokenize(str, " ,");
/* 464 */     for (int i = 0; i < parts.length; i++) {
/*     */       
/* 466 */       String part = parts[i];
/* 467 */       RangeInt ri = parseRangeInt(part);
/* 468 */       if (ri == null) {
/* 469 */         return null;
/*     */       }
/* 471 */       list.addRange(ri);
/*     */     } 
/*     */     
/* 474 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RangeInt parseRangeInt(String str) {
/* 483 */     if (str == null) {
/* 484 */       return null;
/*     */     }
/* 486 */     if (str.indexOf('-') >= 0) {
/*     */       
/* 488 */       String[] parts = Config.tokenize(str, "-");
/* 489 */       if (parts.length != 2) {
/*     */         
/* 491 */         warn("Invalid range: " + str);
/* 492 */         return null;
/*     */       } 
/* 494 */       int min = Config.parseInt(parts[0], -1);
/* 495 */       int max = Config.parseInt(parts[1], -1);
/* 496 */       if (min < 0 || max < 0) {
/*     */         
/* 498 */         warn("Invalid range: " + str);
/* 499 */         return null;
/*     */       } 
/*     */       
/* 502 */       return new RangeInt(min, max);
/*     */     } 
/*     */     
/* 505 */     int val = Config.parseInt(str, -1);
/* 506 */     if (val < 0) {
/*     */       
/* 508 */       warn("Invalid integer: " + str);
/* 509 */       return null;
/*     */     } 
/*     */     
/* 512 */     return new RangeInt(val, val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean parseBoolean(String str) {
/* 520 */     if (str == null) {
/* 521 */       return false;
/*     */     }
/* 523 */     String strLower = str.toLowerCase().trim();
/*     */     
/* 525 */     return strLower.equals("true");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean parseBooleanObject(String str) {
/* 534 */     if (str == null) {
/* 535 */       return null;
/*     */     }
/* 537 */     String strLower = str.toLowerCase().trim();
/*     */     
/* 539 */     if (strLower.equals("true")) {
/* 540 */       return Boolean.TRUE;
/*     */     }
/* 542 */     if (strLower.equals("false")) {
/* 543 */       return Boolean.FALSE;
/*     */     }
/* 545 */     warn("Invalid boolean: " + str);
/* 546 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int parseColor(String str, int defVal) {
/* 556 */     if (str == null) {
/* 557 */       return defVal;
/*     */     }
/* 559 */     str = str.trim();
/*     */ 
/*     */     
/*     */     try {
/* 563 */       int val = Integer.parseInt(str, 16) & 0xFFFFFF;
/*     */       
/* 565 */       return val;
/*     */     }
/* 567 */     catch (NumberFormatException e) {
/*     */       
/* 569 */       return defVal;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NbtTagValue parseNbtTagValue(String path, String value) {
/* 577 */     if (path == null || value == null) {
/* 578 */       return null;
/*     */     }
/* 580 */     return new NbtTagValue(path, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VillagerProfession[] parseProfessions(String profStr) {
/* 589 */     if (profStr == null) {
/* 590 */       return null;
/*     */     }
/* 592 */     List<VillagerProfession> list = new ArrayList<VillagerProfession>();
/* 593 */     String[] tokens = Config.tokenize(profStr, " ");
/* 594 */     for (int i = 0; i < tokens.length; i++) {
/*     */       
/* 596 */       String str = tokens[i];
/* 597 */       VillagerProfession prof = parseProfession(str);
/* 598 */       if (prof == null) {
/*     */         
/* 600 */         warn("Invalid profession: " + str);
/* 601 */         return PROFESSIONS_INVALID;
/*     */       } 
/*     */       
/* 604 */       list.add(prof);
/*     */     } 
/*     */     
/* 607 */     if (list.isEmpty()) {
/* 608 */       return null;
/*     */     }
/* 610 */     VillagerProfession[] arr = list.<VillagerProfession>toArray(new VillagerProfession[list.size()]);
/*     */     
/* 612 */     return arr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private VillagerProfession parseProfession(String str) {
/* 621 */     str = str.toLowerCase();
/*     */     
/* 623 */     String[] parts = Config.tokenize(str, ":");
/* 624 */     if (parts.length > 2) {
/* 625 */       return null;
/*     */     }
/* 627 */     String profStr = parts[0];
/* 628 */     String carStr = null;
/* 629 */     if (parts.length > 1) {
/* 630 */       carStr = parts[1];
/*     */     }
/* 632 */     int prof = parseProfessionId(profStr);
/* 633 */     if (prof < 0) {
/* 634 */       return null;
/*     */     }
/* 636 */     int[] cars = null;
/* 637 */     if (carStr != null) {
/*     */       
/* 639 */       cars = parseCareerIds(prof, carStr);
/* 640 */       if (cars == null) {
/* 641 */         return null;
/*     */       }
/*     */     } 
/* 644 */     return new VillagerProfession(prof, cars);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int parseProfessionId(String str) {
/* 652 */     int id = Config.parseInt(str, -1);
/* 653 */     if (id >= 0) {
/* 654 */       return id;
/*     */     }
/* 656 */     if (str.equals("farmer"))
/* 657 */       return 0; 
/* 658 */     if (str.equals("librarian"))
/* 659 */       return 1; 
/* 660 */     if (str.equals("priest"))
/* 661 */       return 2; 
/* 662 */     if (str.equals("blacksmith"))
/* 663 */       return 3; 
/* 664 */     if (str.equals("butcher"))
/* 665 */       return 4; 
/* 666 */     if (str.equals("nitwit")) {
/* 667 */       return 5;
/*     */     }
/* 669 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] parseCareerIds(int prof, String str) {
/* 676 */     Set<Integer> set = new HashSet<Integer>();
/*     */     
/* 678 */     String[] parts = Config.tokenize(str, ",");
/* 679 */     for (int i = 0; i < parts.length; i++) {
/*     */       
/* 681 */       String part = parts[i];
/* 682 */       int id = parseCareerId(prof, part);
/* 683 */       if (id < 0) {
/* 684 */         return null;
/*     */       }
/* 686 */       set.add(Integer.valueOf(id));
/*     */     } 
/*     */     
/* 689 */     Integer[] integerArr = set.<Integer>toArray(new Integer[set.size()]);
/*     */     
/* 691 */     int[] arr = new int[integerArr.length];
/* 692 */     for (int j = 0; j < arr.length; j++)
/*     */     {
/* 694 */       arr[j] = integerArr[j].intValue();
/*     */     }
/*     */     
/* 697 */     return arr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int parseCareerId(int prof, String str) {
/* 705 */     int id = Config.parseInt(str, -1);
/* 706 */     if (id >= 0) {
/* 707 */       return id;
/*     */     }
/* 709 */     if (prof == 0) {
/*     */       
/* 711 */       if (str.equals("farmer"))
/* 712 */         return 1; 
/* 713 */       if (str.equals("fisherman"))
/* 714 */         return 2; 
/* 715 */       if (str.equals("shepherd"))
/* 716 */         return 3; 
/* 717 */       if (str.equals("fletcher")) {
/* 718 */         return 4;
/*     */       }
/*     */     } 
/* 721 */     if (prof == 1) {
/*     */       
/* 723 */       if (str.equals("librarian"))
/* 724 */         return 1; 
/* 725 */       if (str.equals("cartographer")) {
/* 726 */         return 2;
/*     */       }
/*     */     } 
/* 729 */     if (prof == 2)
/*     */     {
/* 731 */       if (str.equals("cleric")) {
/* 732 */         return 1;
/*     */       }
/*     */     }
/* 735 */     if (prof == 3) {
/*     */       
/* 737 */       if (str.equals("armor"))
/* 738 */         return 1; 
/* 739 */       if (str.equals("weapon"))
/* 740 */         return 2; 
/* 741 */       if (str.equals("tool")) {
/* 742 */         return 3;
/*     */       }
/*     */     } 
/* 745 */     if (prof == 4) {
/*     */       
/* 747 */       if (str.equals("butcher"))
/* 748 */         return 1; 
/* 749 */       if (str.equals("leather"))
/* 750 */         return 2; 
/*     */     } 
/* 752 */     if (prof == 5)
/*     */     {
/*     */       
/* 755 */       if (str.equals("nitwit")) {
/* 756 */         return 1;
/*     */       }
/*     */     }
/* 759 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\ConnectedParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */