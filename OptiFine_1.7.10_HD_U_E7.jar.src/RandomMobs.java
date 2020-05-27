/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
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
/*     */ public class RandomMobs
/*     */ {
/*  31 */   private static Map locationProperties = new HashMap<Object, Object>();
/*     */   
/*  33 */   private static bma renderGlobal = null;
/*     */   
/*     */   private static boolean initialized = false;
/*     */   
/*  37 */   private static Random random = new Random();
/*     */   
/*     */   private static boolean working = false;
/*     */   
/*     */   public static final String SUFFIX_PNG = ".png";
/*     */   public static final String SUFFIX_PROPERTIES = ".properties";
/*     */   public static final String PREFIX_TEXTURES_ENTITY = "textures/entity/";
/*     */   public static final String PREFIX_MCPATCHER_MOB = "mcpatcher/mob/";
/*  45 */   private static final String[] DEPENDANT_SUFFIXES = new String[] { "_armor", "_eyes", "_exploding", "_shooting", "_fur", "_eyes", "_invulnerable", "_angry", "_tame", "_collar" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void entityLoaded(sa entity, ahb world) {
/*  53 */     if (!(entity instanceof sw)) {
/*     */       return;
/*     */     }
/*  56 */     if (world == null) {
/*     */       return;
/*     */     }
/*  59 */     sw el = (sw)entity;
/*     */     
/*  61 */     el.spawnPosition = new BlockPos((int)el.s, (int)el.t, (int)el.u);
/*  62 */     el.spawnBiome = world.a((int)el.s, (int)el.u);
/*     */     
/*  64 */     mt ws = Config.getWorldServer();
/*  65 */     if (ws == null) {
/*     */       return;
/*     */     }
/*  68 */     sa es = ws.a(entity.y());
/*  69 */     if (!(es instanceof sw))
/*     */       return; 
/*  71 */     sw els = (sw)es;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     UUID uuid = els.aB();
/*     */     
/*  80 */     if (el instanceof yv && es instanceof yv) {
/*  81 */       updateEntityVillager((yv)el, (yv)els);
/*     */     }
/*  83 */     long uuidLow = uuid.getLeastSignificantBits();
/*     */     
/*  85 */     int id = (int)(uuidLow & 0x7FFFFFFFL);
/*     */     
/*  87 */     el.randomMobsId = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void updateEntityVillager(yv ev, yv evs) {
/*  96 */     int profSev = evs.bZ();
/*  97 */     ev.s(profSev);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void worldChanged(ahb oldWorld, ahb newWorld) {
/* 107 */     if (newWorld != null) {
/*     */ 
/*     */       
/* 110 */       List<sa> entityList = newWorld.D();
/* 111 */       for (int e = 0; e < entityList.size(); e++) {
/*     */         
/* 113 */         sa entity = entityList.get(e);
/* 114 */         entityLoaded(entity, newWorld);
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
/*     */   public static bqx getTextureLocation(bqx loc) {
/* 126 */     if (working) {
/* 127 */       return loc;
/*     */     }
/*     */     
/*     */     try {
/* 131 */       working = true;
/*     */       
/* 133 */       if (!initialized) {
/* 134 */         initialize();
/*     */       }
/* 136 */       if (renderGlobal == null) {
/* 137 */         return loc;
/*     */       }
/* 139 */       sa entity = renderGlobal.renderedEntity;
/*     */       
/* 141 */       if (!(entity instanceof sw)) {
/* 142 */         return loc;
/*     */       }
/* 144 */       sw entityLiving = (sw)entity;
/*     */       
/* 146 */       String name = loc.a();
/* 147 */       if (!name.startsWith("textures/entity/")) {
/* 148 */         return loc;
/*     */       }
/* 150 */       RandomMobsProperties props = getProperties(loc);
/* 151 */       if (props == null) {
/* 152 */         return loc;
/*     */       }
/* 154 */       return props.getTextureLocation(loc, entityLiving);
/*     */     }
/*     */     finally {
/*     */       
/* 158 */       working = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static RandomMobsProperties getProperties(bqx loc) {
/* 168 */     String name = loc.a();
/* 169 */     RandomMobsProperties props = (RandomMobsProperties)locationProperties.get(name);
/* 170 */     if (props == null) {
/*     */       
/* 172 */       props = makeProperties(loc);
/* 173 */       locationProperties.put(name, props);
/*     */     } 
/*     */     
/* 176 */     return props;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static RandomMobsProperties makeProperties(bqx loc) {
/* 185 */     String path = loc.a();
/*     */     
/* 187 */     bqx propLoc = getPropertyLocation(loc);
/* 188 */     if (propLoc != null) {
/*     */       
/* 190 */       RandomMobsProperties props = parseProperties(propLoc, loc);
/* 191 */       if (props != null) {
/* 192 */         return props;
/*     */       }
/*     */     } 
/* 195 */     bqx[] variants = getTextureVariants(loc);
/*     */     
/* 197 */     return new RandomMobsProperties(path, variants);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static RandomMobsProperties parseProperties(bqx propLoc, bqx resLoc) {
/*     */     try {
/* 208 */       String path = propLoc.a();
/* 209 */       Config.dbg("RandomMobs: " + resLoc.a() + ", variants: " + path);
/*     */       
/* 211 */       InputStream in = Config.getResourceStream(propLoc);
/* 212 */       if (in == null) {
/*     */ 
/*     */         
/* 215 */         Config.warn("RandomMobs properties not found: " + path);
/* 216 */         return null;
/*     */       } 
/* 218 */       Properties props = new Properties();
/* 219 */       props.load(in);
/* 220 */       in.close();
/*     */       
/* 222 */       RandomMobsProperties rmp = new RandomMobsProperties(props, path, resLoc);
/* 223 */       if (!rmp.isValid(path)) {
/* 224 */         return null;
/*     */       }
/* 226 */       return rmp;
/*     */     }
/* 228 */     catch (FileNotFoundException e) {
/*     */ 
/*     */       
/* 231 */       Config.warn("RandomMobs file not found: " + resLoc.a());
/* 232 */       return null;
/*     */     }
/* 234 */     catch (IOException e) {
/*     */       
/* 236 */       e.printStackTrace();
/* 237 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static bqx getPropertyLocation(bqx loc) {
/* 247 */     bqx locMcp = getMcpatcherLocation(loc);
/* 248 */     if (locMcp == null)
/* 249 */       return null; 
/* 250 */     String domain = locMcp.b();
/* 251 */     String path = locMcp.a();
/*     */     
/* 253 */     String pathBase = path;
/* 254 */     if (pathBase.endsWith(".png")) {
/* 255 */       pathBase = pathBase.substring(0, pathBase.length() - ".png".length());
/*     */     }
/* 257 */     String pathProps = pathBase + ".properties";
/* 258 */     bqx locProps = new bqx(domain, pathProps);
/* 259 */     if (Config.hasResource(locProps)) {
/* 260 */       return locProps;
/*     */     }
/* 262 */     String pathParent = getParentPath(pathBase);
/* 263 */     if (pathParent == null) {
/* 264 */       return null;
/*     */     }
/* 266 */     bqx locParentProps = new bqx(domain, pathParent + ".properties");
/* 267 */     if (Config.hasResource(locParentProps)) {
/* 268 */       return locParentProps;
/*     */     }
/* 270 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static bqx getMcpatcherLocation(bqx loc) {
/* 275 */     String path = loc.a();
/*     */     
/* 277 */     if (!path.startsWith("textures/entity/")) {
/* 278 */       return null;
/*     */     }
/* 280 */     String pathMcp = "mcpatcher/mob/" + path.substring("textures/entity/".length());
/*     */     
/* 282 */     return new bqx(loc.b(), pathMcp);
/*     */   }
/*     */ 
/*     */   
/*     */   public static bqx getLocationIndexed(bqx loc, int index) {
/* 287 */     if (loc == null) {
/* 288 */       return null;
/*     */     }
/* 290 */     String path = loc.a();
/* 291 */     int pos = path.lastIndexOf('.');
/* 292 */     if (pos < 0) {
/* 293 */       return null;
/*     */     }
/* 295 */     String prefix = path.substring(0, pos);
/* 296 */     String suffix = path.substring(pos);
/*     */     
/* 298 */     String pathNew = prefix + index + suffix;
/* 299 */     bqx locNew = new bqx(loc.b(), pathNew);
/*     */     
/* 301 */     return locNew;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getParentPath(String path) {
/* 310 */     for (int i = 0; i < DEPENDANT_SUFFIXES.length; ) {
/*     */       
/* 312 */       String suffix = DEPENDANT_SUFFIXES[i];
/* 313 */       if (!path.endsWith(suffix)) {
/*     */         i++; continue;
/*     */       } 
/* 316 */       String pathParent = path.substring(0, path.length() - suffix.length());
/*     */       
/* 318 */       return pathParent;
/*     */     } 
/*     */     
/* 321 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static bqx[] getTextureVariants(bqx loc) {
/* 330 */     List<bqx> list = new ArrayList();
/*     */     
/* 332 */     list.add(loc);
/*     */     
/* 334 */     bqx locMcp = getMcpatcherLocation(loc);
/* 335 */     if (locMcp == null) {
/* 336 */       return null;
/*     */     }
/* 338 */     for (int i = 1; i < list.size() + 10; i++) {
/*     */       
/* 340 */       int index = i + 1;
/* 341 */       bqx locIndex = getLocationIndexed(locMcp, index);
/*     */       
/* 343 */       if (Config.hasResource(locIndex)) {
/* 344 */         list.add(locIndex);
/*     */       }
/*     */     } 
/* 347 */     if (list.size() <= 1) {
/* 348 */       return null;
/*     */     }
/* 350 */     bqx[] locs = list.<bqx>toArray(new bqx[list.size()]);
/* 351 */     Config.dbg("RandomMobs: " + loc.a() + ", variants: " + locs.length);
/*     */     
/* 353 */     return locs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void resetTextures() {
/* 361 */     locationProperties.clear();
/*     */     
/* 363 */     if (Config.isRandomMobs()) {
/* 364 */       initialize();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initialize() {
/* 372 */     renderGlobal = Config.getRenderGlobal();
/* 373 */     if (renderGlobal == null) {
/*     */       return;
/*     */     }
/* 376 */     initialized = true;
/*     */     
/* 378 */     List<String> list = new ArrayList();
/*     */     
/* 380 */     list.add("bat");
/* 381 */     list.add("blaze");
/* 382 */     list.add("cat/black");
/* 383 */     list.add("cat/ocelot");
/* 384 */     list.add("cat/red");
/* 385 */     list.add("cat/siamese");
/* 386 */     list.add("chicken");
/* 387 */     list.add("cow/cow");
/* 388 */     list.add("cow/mooshroom");
/* 389 */     list.add("creeper/creeper");
/* 390 */     list.add("enderman/enderman");
/* 391 */     list.add("enderman/enderman_eyes");
/* 392 */     list.add("ghast/ghast");
/* 393 */     list.add("ghast/ghast_shooting");
/* 394 */     list.add("iron_golem");
/* 395 */     list.add("pig/pig");
/* 396 */     list.add("sheep/sheep");
/* 397 */     list.add("sheep/sheep_fur");
/* 398 */     list.add("silverfish");
/* 399 */     list.add("skeleton/skeleton");
/* 400 */     list.add("skeleton/wither_skeleton");
/* 401 */     list.add("slime/slime");
/* 402 */     list.add("slime/magmacube");
/* 403 */     list.add("snowman");
/* 404 */     list.add("spider/cave_spider");
/* 405 */     list.add("spider/spider");
/* 406 */     list.add("spider_eyes");
/* 407 */     list.add("squid");
/* 408 */     list.add("villager/villager");
/* 409 */     list.add("villager/butcher");
/* 410 */     list.add("villager/farmer");
/* 411 */     list.add("villager/librarian");
/* 412 */     list.add("villager/priest");
/* 413 */     list.add("villager/smith");
/* 414 */     list.add("wither/wither");
/* 415 */     list.add("wither/wither_armor");
/* 416 */     list.add("wither/wither_invulnerable");
/* 417 */     list.add("wolf/wolf");
/* 418 */     list.add("wolf/wolf_angry");
/* 419 */     list.add("wolf/wolf_collar");
/* 420 */     list.add("wolf/wolf_tame");
/* 421 */     list.add("zombie_pigman");
/* 422 */     list.add("zombie/zombie");
/* 423 */     list.add("zombie/zombie_villager");
/*     */     
/* 425 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/* 427 */       String name = list.get(i);
/* 428 */       String tex = "textures/entity/" + name + ".png";
/* 429 */       bqx texLoc = new bqx(tex);
/*     */       
/* 431 */       if (!Config.hasResource(texLoc)) {
/* 432 */         Config.warn("Not found: " + texLoc);
/*     */       }
/* 434 */       getProperties(texLoc);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\RandomMobs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */