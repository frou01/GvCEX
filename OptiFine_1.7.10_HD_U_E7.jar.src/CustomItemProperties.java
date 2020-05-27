/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
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
/*     */ public class CustomItemProperties
/*     */ {
/*  25 */   public String name = null;
/*  26 */   public String basePath = null;
/*     */   
/*  28 */   public int type = 1;
/*     */   
/*  30 */   public int[] items = null;
/*     */   
/*  32 */   public String texture = null;
/*     */   
/*  34 */   public Map mapTextures = null;
/*     */   
/*  36 */   public RangeListInt damage = null;
/*     */   public boolean damagePercent = false;
/*  38 */   public int damageMask = 0;
/*  39 */   public RangeListInt stackSize = null;
/*  40 */   public RangeListInt enchantmentIds = null;
/*  41 */   public RangeListInt enchantmentLevels = null;
/*  42 */   public NbtTagValue[] nbtTagValues = null;
/*     */   
/*  44 */   public int blend = 1;
/*  45 */   public int speed = 0;
/*  46 */   public int rotation = 0;
/*  47 */   public int layer = 0;
/*  48 */   public int duration = 1;
/*     */   
/*  50 */   public int weight = 0;
/*     */   
/*  52 */   public rf textureIcon = null;
/*  53 */   public Map mapTextureIcons = null;
/*     */   
/*     */   public static final int TYPE_UNKNOWN = 0;
/*     */   
/*     */   public static final int TYPE_ITEM = 1;
/*     */   
/*     */   public static final int TYPE_ENCHANTMENT = 2;
/*     */   
/*     */   public static final int TYPE_ARMOR = 3;
/*     */ 
/*     */   
/*     */   public CustomItemProperties(Properties props, String path) {
/*  65 */     this.name = parseName(path);
/*  66 */     this.basePath = parseBasePath(path);
/*     */     
/*  68 */     this.type = parseType(props.getProperty("type"));
/*     */     
/*  70 */     this.items = parseItems(props.getProperty("items"), props.getProperty("matchItems"));
/*     */     
/*  72 */     this.texture = parseTexture(props.getProperty("texture"), props.getProperty("tile"), props.getProperty("source"), path, this.basePath);
/*  73 */     this.mapTextures = parseTextures(props, this.basePath);
/*     */     
/*  75 */     String damageStr = props.getProperty("damage");
/*  76 */     if (damageStr != null) {
/*     */       
/*  78 */       this.damagePercent = damageStr.contains("%");
/*  79 */       damageStr.replace("%", "");
/*  80 */       this.damage = parseRangeListInt(damageStr);
/*  81 */       this.damageMask = parseInt(props.getProperty("damageMask"), 0);
/*     */     } 
/*  83 */     this.stackSize = parseRangeListInt(props.getProperty("stackSize"));
/*  84 */     this.enchantmentIds = parseRangeListInt(props.getProperty("enchantmentIDs"));
/*  85 */     this.enchantmentLevels = parseRangeListInt(props.getProperty("enchantmentLevels"));
/*  86 */     this.nbtTagValues = parseNbtTagValues(props);
/*     */     
/*  88 */     this.blend = Blender.parseBlend(props.getProperty("blend"));
/*  89 */     this.speed = parseInt(props.getProperty("speed"), 0);
/*  90 */     this.rotation = parseInt(props.getProperty("rotation"), 0);
/*  91 */     this.layer = parseInt(props.getProperty("layer"), 0);
/*  92 */     this.weight = parseInt(props.getProperty("weight"), 0);
/*  93 */     this.duration = parseInt(props.getProperty("duration"), 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String parseName(String path) {
/* 102 */     String str = path;
/*     */     
/* 104 */     int pos = str.lastIndexOf('/');
/* 105 */     if (pos >= 0) {
/* 106 */       str = str.substring(pos + 1);
/*     */     }
/* 108 */     int pos2 = str.lastIndexOf('.');
/* 109 */     if (pos2 >= 0) {
/* 110 */       str = str.substring(0, pos2);
/*     */     }
/* 112 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String parseBasePath(String path) {
/* 120 */     int pos = path.lastIndexOf('/');
/* 121 */     if (pos < 0) {
/* 122 */       return "";
/*     */     }
/* 124 */     return path.substring(0, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int parseType(String str) {
/* 130 */     if (str == null) {
/* 131 */       return 1;
/*     */     }
/* 133 */     if (str.equals("item"))
/* 134 */       return 1; 
/* 135 */     if (str.equals("enchantment"))
/* 136 */       return 2; 
/* 137 */     if (str.equals("armor")) {
/* 138 */       return 3;
/*     */     }
/* 140 */     Config.warn("Unknown method: " + str);
/*     */     
/* 142 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int[] parseItems(String str, String str2) {
/* 148 */     if (str == null)
/* 149 */       str = str2; 
/* 150 */     if (str == null) {
/* 151 */       return null;
/*     */     }
/* 153 */     str = str.trim();
/*     */     
/* 155 */     Set<Integer> setItemIds = new TreeSet();
/* 156 */     String[] tokens = Config.tokenize(str, " ");
/* 157 */     for (int i = 0; i < tokens.length; i++) {
/*     */       
/* 159 */       String token = tokens[i];
/*     */       
/* 161 */       int val = Config.parseInt(token, -1);
/* 162 */       if (val >= 0) {
/*     */         
/* 164 */         setItemIds.add(new Integer(val));
/*     */         
/*     */         continue;
/*     */       } 
/* 168 */       if (token.contains("-")) {
/*     */         
/* 170 */         String[] parts = Config.tokenize(token, "-");
/* 171 */         if (parts.length == 2) {
/*     */ 
/*     */           
/* 174 */           int val1 = Config.parseInt(parts[0], -1);
/* 175 */           int val2 = Config.parseInt(parts[1], -1);
/* 176 */           if (val1 >= 0 && val2 >= 0) {
/*     */             
/* 178 */             int min = Math.min(val1, val2);
/* 179 */             int max = Math.max(val1, val2);
/* 180 */             for (int x = min; x <= max; x++)
/*     */             {
/* 182 */               setItemIds.add(new Integer(x));
/*     */             }
/*     */             
/*     */             continue;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 190 */       Object itemObj = adb.e.a(token);
/* 191 */       if (!(itemObj instanceof adb)) {
/*     */         
/* 193 */         Config.dbg("Item not found: " + token);
/*     */       } else {
/*     */         
/* 196 */         adb item = (adb)itemObj;
/* 197 */         int id = adb.b(item);
/* 198 */         if (id < 0) {
/*     */ 
/*     */           
/* 201 */           Config.dbg("Item not found: " + token);
/*     */         }
/*     */         else {
/*     */           
/* 205 */           setItemIds.add(new Integer(id));
/*     */         } 
/*     */       }  continue;
/* 208 */     }  Integer[] integers = setItemIds.<Integer>toArray(new Integer[setItemIds.size()]);
/* 209 */     int[] ints = new int[integers.length];
/* 210 */     for (int j = 0; j < ints.length; j++)
/*     */     {
/* 212 */       ints[j] = integers[j].intValue();
/*     */     }
/* 214 */     return ints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String parseTexture(String texStr, String texStr2, String texStr3, String path, String basePath) {
/* 224 */     if (texStr == null)
/* 225 */       texStr = texStr2; 
/* 226 */     if (texStr == null) {
/* 227 */       texStr = texStr3;
/*     */     }
/* 229 */     if (texStr != null) {
/*     */ 
/*     */       
/* 232 */       String png = ".png";
/* 233 */       if (texStr.endsWith(png)) {
/* 234 */         texStr = texStr.substring(0, texStr.length() - png.length());
/*     */       }
/* 236 */       texStr = fixTextureName(texStr, basePath);
/*     */       
/* 238 */       return texStr;
/*     */     } 
/*     */     
/* 241 */     String str = path;
/*     */     
/* 243 */     int pos = str.lastIndexOf('/');
/* 244 */     if (pos >= 0) {
/* 245 */       str = str.substring(pos + 1);
/*     */     }
/* 247 */     int pos2 = str.lastIndexOf('.');
/* 248 */     if (pos2 >= 0) {
/* 249 */       str = str.substring(0, pos2);
/*     */     }
/* 251 */     str = fixTextureName(str, basePath);
/*     */     
/* 253 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map parseTextures(Properties props, String basePath) {
/* 259 */     String prefix = "texture.";
/* 260 */     Map mapProps = getMatchingProperties(props, prefix);
/*     */     
/* 262 */     if (mapProps.size() <= 0) {
/* 263 */       return null;
/*     */     }
/* 265 */     Set keySet = mapProps.keySet();
/* 266 */     Map<Object, Object> mapTex = new LinkedHashMap<Object, Object>();
/* 267 */     for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
/*     */       
/* 269 */       String key = it.next();
/* 270 */       String val = (String)mapProps.get(key);
/*     */       
/* 272 */       val = fixTextureName(val, basePath);
/*     */       
/* 274 */       if (key.startsWith(prefix)) {
/* 275 */         key = key.substring(prefix.length());
/*     */       }
/* 277 */       mapTex.put(key, val);
/*     */     } 
/*     */     
/* 280 */     return mapTex;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String fixTextureName(String iconName, String basePath) {
/* 286 */     iconName = TextureUtils.fixResourcePath(iconName, basePath);
/*     */     
/* 288 */     if (!iconName.startsWith(basePath) && !iconName.startsWith("textures/") && !iconName.startsWith("mcpatcher/")) {
/* 289 */       iconName = basePath + "/" + iconName;
/*     */     }
/* 291 */     if (iconName.endsWith(".png")) {
/* 292 */       iconName = iconName.substring(0, iconName.length() - 4);
/*     */     }
/* 294 */     String pathBlocks = "textures/blocks/";
/* 295 */     if (iconName.startsWith(pathBlocks)) {
/* 296 */       iconName = iconName.substring(pathBlocks.length());
/*     */     }
/* 298 */     if (iconName.startsWith("/")) {
/* 299 */       iconName = iconName.substring(1);
/*     */     }
/* 301 */     return iconName;
/*     */   }
/*     */ 
/*     */   
/*     */   private int parseInt(String str, int defVal) {
/* 306 */     if (str == null) {
/* 307 */       return defVal;
/*     */     }
/* 309 */     str = str.trim();
/* 310 */     int val = Config.parseInt(str, -2147483648);
/* 311 */     if (val == Integer.MIN_VALUE) {
/*     */       
/* 313 */       Config.warn("Invalid integer: " + str);
/* 314 */       return defVal;
/*     */     } 
/*     */     
/* 317 */     return val;
/*     */   }
/*     */ 
/*     */   
/*     */   private RangeListInt parseRangeListInt(String str) {
/* 322 */     if (str == null) {
/* 323 */       return null;
/*     */     }
/* 325 */     String[] tokens = Config.tokenize(str, " ");
/* 326 */     RangeListInt rangeList = new RangeListInt();
/* 327 */     for (int i = 0; i < tokens.length; i++) {
/*     */       
/* 329 */       String token = tokens[i];
/* 330 */       RangeInt range = parseRangeInt(token);
/* 331 */       if (range == null) {
/*     */         
/* 333 */         Config.warn("Invalid range list: " + str);
/* 334 */         return null;
/*     */       } 
/*     */       
/* 337 */       rangeList.addRange(range);
/*     */     } 
/*     */     
/* 340 */     return rangeList;
/*     */   }
/*     */ 
/*     */   
/*     */   private RangeInt parseRangeInt(String str) {
/* 345 */     if (str == null) {
/* 346 */       return null;
/*     */     }
/* 348 */     str = str.trim();
/*     */     
/* 350 */     int countMinus = str.length() - str.replace("-", "").length();
/* 351 */     if (countMinus > 1) {
/*     */       
/* 353 */       Config.warn("Invalid range: " + str);
/* 354 */       return null;
/*     */     } 
/*     */     
/* 357 */     String[] tokens = Config.tokenize(str, "- ");
/*     */     
/* 359 */     int[] vals = new int[tokens.length];
/* 360 */     for (int i = 0; i < tokens.length; i++) {
/*     */       
/* 362 */       String token = tokens[i];
/* 363 */       int val = Config.parseInt(token, -1);
/* 364 */       if (val < 0) {
/*     */         
/* 366 */         Config.warn("Invalid range: " + str);
/* 367 */         return null;
/*     */       } 
/* 369 */       vals[i] = val;
/*     */     } 
/*     */     
/* 372 */     if (vals.length == 1) {
/*     */       
/* 374 */       int val = vals[0];
/* 375 */       if (str.startsWith("-"))
/* 376 */         return new RangeInt(-1, val); 
/* 377 */       if (str.endsWith("-")) {
/* 378 */         return new RangeInt(val, -1);
/*     */       }
/* 380 */       return new RangeInt(val, val);
/*     */     } 
/*     */     
/* 383 */     if (vals.length == 2) {
/*     */ 
/*     */       
/* 386 */       int min = Math.min(vals[0], vals[1]);
/* 387 */       int max = Math.max(vals[0], vals[1]);
/* 388 */       return new RangeInt(min, max);
/*     */     } 
/*     */     
/* 391 */     Config.warn("Invalid range: " + str);
/* 392 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private NbtTagValue[] parseNbtTagValues(Properties props) {
/* 401 */     Map mapNbt = getMatchingProperties(props, "nbt.");
/*     */     
/* 403 */     if (mapNbt.size() <= 0) {
/* 404 */       return null;
/*     */     }
/* 406 */     List<NbtTagValue> listNbts = new ArrayList();
/* 407 */     Set keySet = mapNbt.keySet();
/* 408 */     for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
/*     */       
/* 410 */       String key = it.next();
/* 411 */       String val = (String)mapNbt.get(key);
/* 412 */       NbtTagValue nbt = new NbtTagValue(key, val);
/* 413 */       listNbts.add(nbt);
/*     */     } 
/*     */     
/* 416 */     NbtTagValue[] nbts = listNbts.<NbtTagValue>toArray(new NbtTagValue[listNbts.size()]);
/*     */     
/* 418 */     return nbts;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Map getMatchingProperties(Properties props, String keyPrefix) {
/* 423 */     Map<Object, Object> map = new LinkedHashMap<Object, Object>();
/* 424 */     Set keySet = props.keySet();
/* 425 */     for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
/*     */       
/* 427 */       String key = it.next();
/* 428 */       String val = props.getProperty(key);
/* 429 */       if (key.startsWith(keyPrefix))
/*     */       {
/* 431 */         map.put(key, val);
/*     */       }
/*     */     } 
/*     */     
/* 435 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String path) {
/* 444 */     if (this.name == null || this.name.length() <= 0) {
/*     */       
/* 446 */       Config.warn("No name found: " + path);
/* 447 */       return false;
/*     */     } 
/*     */     
/* 450 */     if (this.basePath == null) {
/*     */       
/* 452 */       Config.warn("No base path found: " + path);
/* 453 */       return false;
/*     */     } 
/*     */     
/* 456 */     if (this.type == 0) {
/*     */       
/* 458 */       Config.warn("No type defined: " + path);
/* 459 */       return false;
/*     */     } 
/*     */     
/* 462 */     if (this.type == 1 || this.type == 3)
/*     */     {
/* 464 */       if (this.items == null) {
/*     */         
/* 466 */         Config.warn("No items defined: " + path);
/* 467 */         return false;
/*     */       } 
/*     */     }
/*     */     
/* 471 */     if (this.texture == null && this.mapTextures == null) {
/*     */       
/* 473 */       Config.warn("No texture specified: " + path);
/* 474 */       return false;
/*     */     } 
/*     */     
/* 477 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateIcons(bpz textureMap) {
/* 487 */     if (this.texture != null)
/*     */     {
/*     */       
/* 490 */       this.textureIcon = registerIcon(this.texture, textureMap);
/*     */     }
/*     */     
/* 493 */     if (this.mapTextures != null) {
/*     */       
/* 495 */       this.mapTextureIcons = new LinkedHashMap<Object, Object>();
/* 496 */       Set keySet = this.mapTextures.keySet();
/* 497 */       for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
/*     */         
/* 499 */         String key = it.next();
/* 500 */         String val = (String)this.mapTextures.get(key);
/* 501 */         rf icon = registerIcon(val, textureMap);
/* 502 */         this.mapTextureIcons.put(key, icon);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static rf registerIcon(String tileName, bpz textureMap) {
/* 515 */     if (tileName == null) {
/* 516 */       return null;
/*     */     }
/* 518 */     String iconName = tileName;
/* 519 */     String fullName = iconName;
/*     */     
/* 521 */     if (!fullName.contains("/")) {
/* 522 */       fullName = "textures/blocks/" + fullName;
/*     */     }
/* 524 */     String fileName = fullName + ".png";
/*     */     
/* 526 */     bqx loc = new bqx(fileName);
/* 527 */     boolean exists = Config.hasResource(loc);
/* 528 */     if (!exists) {
/* 529 */       Config.warn("File not found: " + fileName);
/*     */     }
/* 531 */     rf icon = textureMap.a(iconName);
/*     */     
/* 533 */     return icon;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\CustomItemProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */