/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
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
/*     */ public class CustomItems
/*     */ {
/*  45 */   private static CustomItemProperties[][] itemProperties = (CustomItemProperties[][])null;
/*     */   
/*  47 */   private static Map mapPotionIds = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateIcons(bpz textureMap) {
/*  56 */     itemProperties = (CustomItemProperties[][])null;
/*     */     
/*  58 */     if (!Config.isCustomItems()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  66 */     bra[] rps = Config.getResourcePacks();
/*     */ 
/*     */     
/*  69 */     for (int i = rps.length - 1; i >= 0; i--) {
/*     */       
/*  71 */       bra rp = rps[i];
/*  72 */       updateIcons(textureMap, rp);
/*     */     } 
/*     */     
/*  75 */     updateIcons(textureMap, Config.getDefaultResourcePack());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateIcons(bpz textureMap, bra rp) {
/*  84 */     String[] names = collectFiles(rp, "mcpatcher/cit/", ".properties");
/*     */ 
/*     */     
/*  87 */     Map mapAutoProperties = makeAutoImageProperties(rp);
/*     */     
/*  89 */     if (mapAutoProperties.size() > 0) {
/*     */       
/*  91 */       Set keySetAuto = mapAutoProperties.keySet();
/*  92 */       String[] keysAuto = (String[])keySetAuto.toArray((Object[])new String[keySetAuto.size()]);
/*  93 */       names = (String[])Config.addObjectsToArray((Object[])names, (Object[])keysAuto);
/*     */     } 
/*     */     
/*  96 */     Arrays.sort((Object[])names);
/*     */     
/*  98 */     List itemList = makePropertyList(itemProperties);
/*     */     
/* 100 */     for (int i = 0; i < names.length; i++) {
/*     */       
/* 102 */       String name = names[i];
/* 103 */       Config.dbg("CustomItems: " + name);
/*     */       
/*     */       try {
/* 106 */         CustomItemProperties cip = null;
/*     */         
/* 108 */         if (mapAutoProperties.containsKey(name))
/*     */         {
/* 110 */           cip = (CustomItemProperties)mapAutoProperties.get(name);
/*     */         }
/* 112 */         if (cip == null)
/*     */         
/* 114 */         { bqx locFile = new bqx(name);
/* 115 */           InputStream in = rp.a(locFile);
/* 116 */           if (in == null)
/*     */           
/*     */           { 
/* 119 */             Config.warn("CustomItems file not found: " + name); }
/*     */           else
/*     */           
/* 122 */           { Properties props = new Properties();
/* 123 */             props.load(in);
/* 124 */             cip = new CustomItemProperties(props, name);
/*     */             
/* 126 */             if (cip.isValid(name))
/*     */             
/*     */             { 
/* 129 */               cip.updateIcons(textureMap);
/*     */               
/* 131 */               addToItemList(cip, itemList); }  }  continue; }  if (cip.isValid(name)) { cip.updateIcons(textureMap); addToItemList(cip, itemList); }
/*     */       
/* 133 */       } catch (FileNotFoundException e) {
/*     */ 
/*     */         
/* 136 */         Config.warn("CustomItems file not found: " + name);
/*     */         
/*     */         continue;
/* 139 */       } catch (IOException e) {
/*     */         
/* 141 */         e.printStackTrace();
/*     */         
/*     */         continue;
/*     */       } 
/*     */     } 
/* 146 */     itemProperties = propertyListToArray(itemList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map makeAutoImageProperties(bra rp) {
/* 156 */     Map<Object, Object> map = new HashMap<Object, Object>();
/* 157 */     map.putAll(makePotionImageProperties(rp, false));
/* 158 */     map.putAll(makePotionImageProperties(rp, true));
/* 159 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map makePotionImageProperties(bra rp, boolean splash) {
/* 167 */     Map<Object, Object> map = new HashMap<Object, Object>();
/*     */     
/* 169 */     String prefix = "mcpatcher/cit/potion/";
/* 170 */     if (splash) {
/* 171 */       prefix = prefix + "splash/";
/*     */     } else {
/* 173 */       prefix = prefix + "normal/";
/*     */     } 
/* 175 */     String suffix = ".png";
/* 176 */     String[] names = collectFiles(rp, prefix, suffix);
/* 177 */     for (int i = 0; i < names.length; i++) {
/*     */       
/* 179 */       String path = names[i];
/* 180 */       String name = path;
/*     */       
/* 182 */       if (!name.startsWith(prefix) || !name.endsWith(suffix)) {
/*     */         
/* 184 */         Config.warn("Invalid potion name: " + name);
/*     */       }
/*     */       else {
/*     */         
/* 188 */         name = name.substring(prefix.length(), name.length() - suffix.length());
/* 189 */         Properties props = makePotionProperties(name, splash, path);
/* 190 */         if (props != null) {
/*     */ 
/*     */           
/* 193 */           String pathProp = path.substring(0, path.length() - suffix.length()) + ".properties";
/*     */           
/* 195 */           CustomItemProperties cip = new CustomItemProperties(props, pathProp);
/* 196 */           map.put(pathProp, cip);
/*     */         } 
/*     */       } 
/* 199 */     }  return map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Properties makePotionProperties(String name, boolean splash, String path) {
/* 209 */     if (name.equals("empty") && !splash) {
/*     */ 
/*     */       
/* 212 */       int itemId = adb.b(ade.bo);
/* 213 */       Properties properties = new Properties();
/* 214 */       properties.put("type", "item");
/* 215 */       properties.put("items", "" + itemId);
/*     */       
/* 217 */       return properties;
/*     */     } 
/*     */     
/* 220 */     int potionItemId = adb.b((adb)ade.bn);
/*     */     
/* 222 */     int[] damages = (int[])getMapPotionIds().get(name);
/*     */     
/* 224 */     if (damages == null) {
/*     */       
/* 226 */       Config.warn("Potion not found for image: " + path);
/* 227 */       return null;
/*     */     } 
/* 229 */     StringBuffer bufDamage = new StringBuffer();
/* 230 */     for (int i = 0; i < damages.length; i++) {
/*     */       
/* 232 */       int damage = damages[i];
/*     */       
/* 234 */       if (splash) {
/* 235 */         damage |= 0x4000;
/*     */       }
/* 237 */       if (i > 0) {
/* 238 */         bufDamage.append(" ");
/*     */       }
/* 240 */       bufDamage.append(damage);
/*     */     } 
/*     */     
/* 243 */     int damageMask = 16447;
/*     */     
/* 245 */     Properties props = new Properties();
/* 246 */     props.put("type", "item");
/* 247 */     props.put("items", "" + potionItemId);
/* 248 */     props.put("damage", "" + bufDamage.toString());
/* 249 */     props.put("damageMask", "" + damageMask);
/*     */     
/* 251 */     return props;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Map getMapPotionIds() {
/* 256 */     if (mapPotionIds == null) {
/*     */       
/* 258 */       mapPotionIds = new LinkedHashMap<Object, Object>();
/*     */       
/* 260 */       mapPotionIds.put("water", new int[] { 0 });
/* 261 */       mapPotionIds.put("awkward", new int[] { 16 });
/* 262 */       mapPotionIds.put("thick", new int[] { 32 });
/* 263 */       mapPotionIds.put("potent", new int[] { 48 });
/*     */       
/* 265 */       mapPotionIds.put("regeneration", getPotionIds(1));
/* 266 */       mapPotionIds.put("moveSpeed", getPotionIds(2));
/* 267 */       mapPotionIds.put("fireResistance", getPotionIds(3));
/* 268 */       mapPotionIds.put("poison", getPotionIds(4));
/* 269 */       mapPotionIds.put("heal", getPotionIds(5));
/* 270 */       mapPotionIds.put("nightVision", getPotionIds(6));
/*     */       
/* 272 */       mapPotionIds.put("clear", getPotionIds(7));
/* 273 */       mapPotionIds.put("bungling", getPotionIds(23));
/* 274 */       mapPotionIds.put("charming", getPotionIds(39));
/* 275 */       mapPotionIds.put("rank", getPotionIds(55));
/*     */       
/* 277 */       mapPotionIds.put("weakness", getPotionIds(8));
/* 278 */       mapPotionIds.put("damageBoost", getPotionIds(9));
/* 279 */       mapPotionIds.put("moveSlowdown", getPotionIds(10));
/*     */       
/* 281 */       mapPotionIds.put("diffuse", getPotionIds(11));
/* 282 */       mapPotionIds.put("smooth", getPotionIds(27));
/* 283 */       mapPotionIds.put("refined", getPotionIds(43));
/* 284 */       mapPotionIds.put("acrid", getPotionIds(59));
/*     */       
/* 286 */       mapPotionIds.put("harm", getPotionIds(12));
/* 287 */       mapPotionIds.put("waterBreathing", getPotionIds(13));
/* 288 */       mapPotionIds.put("invisibility", getPotionIds(14));
/*     */       
/* 290 */       mapPotionIds.put("thin", getPotionIds(15));
/* 291 */       mapPotionIds.put("debonair", getPotionIds(31));
/* 292 */       mapPotionIds.put("sparkling", getPotionIds(47));
/* 293 */       mapPotionIds.put("stinky", getPotionIds(63));
/*     */     } 
/*     */     
/* 296 */     return mapPotionIds;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] getPotionIds(int baseId) {
/* 301 */     return new int[] { baseId, baseId + 16, baseId + 32, baseId + 48 };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getPotionNameDamage(String name) {
/* 310 */     String fullName = "potion." + name;
/*     */     
/* 312 */     rv[] effectPotions = rv.a;
/* 313 */     for (int i = 0; i < effectPotions.length; i++) {
/*     */       
/* 315 */       rv potion = effectPotions[i];
/* 316 */       if (potion != null) {
/*     */         
/* 318 */         String potionName = potion.a();
/* 319 */         if (fullName.equals(potionName))
/* 320 */           return potion.c(); 
/*     */       } 
/*     */     } 
/* 323 */     return -1;
/*     */   }
/*     */   
/*     */   private static List makePropertyList(CustomItemProperties[][] propsArr) {
/* 327 */     List<List> list = new ArrayList();
/*     */     
/* 329 */     if (propsArr != null)
/*     */     {
/* 331 */       for (int i = 0; i < propsArr.length; i++) {
/*     */         
/* 333 */         CustomItemProperties[] props = propsArr[i];
/* 334 */         List propList = null;
/* 335 */         if (props != null) {
/* 336 */           propList = new ArrayList(Arrays.asList((Object[])props));
/*     */         }
/* 338 */         list.add(propList);
/*     */       } 
/*     */     }
/*     */     
/* 342 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] collectFiles(bra rp, String prefix, String suffix) {
/* 348 */     if (rp instanceof bqp) {
/* 349 */       return collectFilesDefault(rp);
/*     */     }
/* 351 */     if (!(rp instanceof bqn))
/* 352 */       return new String[0]; 
/* 353 */     bqn arp = (bqn)rp;
/*     */     
/* 355 */     File tpFile = ResourceUtils.getResourcePackFile(arp);
/* 356 */     if (tpFile == null) {
/* 357 */       return new String[0];
/*     */     }
/* 359 */     if (tpFile.isDirectory()) {
/* 360 */       return collectFilesFolder(tpFile, "", prefix, suffix);
/*     */     }
/* 362 */     if (tpFile.isFile()) {
/* 363 */       return collectFilesZIP(tpFile, prefix, suffix);
/*     */     }
/* 365 */     return new String[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] collectFilesDefault(bra rp) {
/* 374 */     return new String[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] collectFilesFolder(File tpFile, String basePath, String prefix, String suffix) {
/* 385 */     List<String> list = new ArrayList();
/* 386 */     String prefixAssets = "assets/minecraft/";
/* 387 */     File[] files = tpFile.listFiles();
/* 388 */     if (files == null) {
/* 389 */       return new String[0];
/*     */     }
/* 391 */     for (int i = 0; i < files.length; i++) {
/*     */       
/* 393 */       File file = files[i];
/*     */       
/* 395 */       if (file.isFile()) {
/*     */         
/* 397 */         String name = basePath + file.getName();
/*     */         
/* 399 */         if (name.startsWith(prefixAssets))
/*     */         {
/*     */           
/* 402 */           name = name.substring(prefixAssets.length());
/*     */           
/* 404 */           if (name.startsWith(prefix))
/*     */           {
/* 406 */             if (name.endsWith(suffix))
/*     */             {
/*     */               
/* 409 */               list.add(name);
/*     */             }
/*     */           }
/*     */         }
/*     */       
/* 414 */       } else if (file.isDirectory()) {
/*     */         
/* 416 */         String dirPath = basePath + file.getName() + "/";
/* 417 */         String[] arrayOfString = collectFilesFolder(file, dirPath, prefix, suffix);
/* 418 */         for (int n = 0; n < arrayOfString.length; n++) {
/*     */           
/* 420 */           String name = arrayOfString[n];
/* 421 */           list.add(name);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 428 */     String[] names = list.<String>toArray(new String[list.size()]);
/* 429 */     return names;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] collectFilesZIP(File tpFile, String prefix, String suffix) {
/* 439 */     List<String> list = new ArrayList();
/* 440 */     String prefixAssets = "assets/minecraft/";
/*     */     
/*     */     try {
/* 443 */       ZipFile zf = new ZipFile(tpFile);
/* 444 */       Enumeration<? extends ZipEntry> en = zf.entries();
/* 445 */       while (en.hasMoreElements()) {
/*     */         
/* 447 */         ZipEntry ze = en.nextElement();
/* 448 */         String name = ze.getName();
/*     */         
/* 450 */         if (!name.startsWith(prefixAssets)) {
/*     */           continue;
/*     */         }
/* 453 */         name = name.substring(prefixAssets.length());
/*     */         
/* 455 */         if (!name.startsWith(prefix))
/*     */           continue; 
/* 457 */         if (!name.endsWith(suffix)) {
/*     */           continue;
/*     */         }
/* 460 */         list.add(name);
/*     */       } 
/* 462 */       zf.close();
/*     */       
/* 464 */       String[] names = list.<String>toArray(new String[list.size()]);
/* 465 */       return names;
/*     */     }
/* 467 */     catch (IOException e) {
/*     */       
/* 469 */       e.printStackTrace();
/* 470 */       return new String[0];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static CustomItemProperties[][] propertyListToArray(List<List> list) {
/* 479 */     CustomItemProperties[][] propArr = new CustomItemProperties[list.size()][];
/* 480 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/* 482 */       List subList = list.get(i);
/* 483 */       if (subList != null) {
/*     */ 
/*     */         
/* 486 */         CustomItemProperties[] subArr = (CustomItemProperties[])subList.toArray((Object[])new CustomItemProperties[subList.size()]);
/*     */         
/* 488 */         Arrays.sort(subArr, new CustomItemsComparator());
/*     */         
/* 490 */         propArr[i] = subArr;
/*     */       } 
/*     */     } 
/* 493 */     return propArr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addToItemList(CustomItemProperties cp, List itemList) {
/* 502 */     if (cp.items == null) {
/*     */       return;
/*     */     }
/* 505 */     for (int i = 0; i < cp.items.length; i++) {
/*     */       
/* 507 */       int itemId = cp.items[i];
/* 508 */       if (itemId <= 0) {
/*     */         
/* 510 */         Config.warn("Invalid item ID: " + itemId);
/*     */       } else {
/*     */         
/* 513 */         addToList(cp, itemList, itemId);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addToList(CustomItemProperties cp, List<List> list, int id) {
/* 524 */     while (id >= list.size()) {
/* 525 */       list.add(null);
/*     */     }
/* 527 */     List<CustomItemProperties> subList = list.get(id);
/* 528 */     if (subList == null) {
/*     */       
/* 530 */       subList = new ArrayList();
/* 531 */       list.set(id, subList);
/*     */     } 
/*     */     
/* 534 */     subList.add(cp);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static rf getCustomItemTexture(add itemStack, rf icon) {
/* 540 */     if (itemProperties == null) {
/* 541 */       return icon;
/*     */     }
/* 543 */     if (itemStack == null)
/* 544 */       return icon; 
/* 545 */     adb item = itemStack.b();
/*     */     
/* 547 */     int itemId = adb.b(item);
/* 548 */     if (itemId >= 0 && itemId < itemProperties.length) {
/*     */       
/* 550 */       CustomItemProperties[] cips = itemProperties[itemId];
/* 551 */       if (cips != null)
/*     */       {
/* 553 */         for (int i = 0; i < cips.length; i++) {
/*     */           
/* 555 */           CustomItemProperties cip = cips[i];
/* 556 */           rf iconNew = getCustomItemTexture(cip, itemStack, icon);
/* 557 */           if (iconNew != null) {
/* 558 */             return iconNew;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 563 */     return icon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static rf getCustomPotionTexture(adp item, int damage) {
/* 573 */     if (itemProperties == null) {
/* 574 */       return null;
/*     */     }
/* 576 */     int itemId = adb.b((adb)item);
/* 577 */     if (itemId >= 0 && itemId < itemProperties.length) {
/*     */       
/* 579 */       CustomItemProperties[] cips = itemProperties[itemId];
/* 580 */       if (cips != null)
/*     */       {
/* 582 */         for (int i = 0; i < cips.length; i++) {
/*     */           
/* 584 */           CustomItemProperties cip = cips[i];
/* 585 */           rf iconNew = getCustomPotionTexture(cip, item, damage);
/* 586 */           if (iconNew != null) {
/* 587 */             return iconNew;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 592 */     return null;
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
/*     */   private static rf getCustomPotionTexture(CustomItemProperties cip, adp item, int damage) {
/* 604 */     if (cip.damage != null) {
/*     */ 
/*     */       
/* 607 */       if (cip.damageMask != 0) {
/* 608 */         damage &= cip.damageMask;
/*     */       }
/* 610 */       if (!cip.damage.isInRange(damage)) {
/* 611 */         return null;
/*     */       }
/*     */     } 
/* 614 */     return cip.textureIcon;
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
/*     */   private static rf getCustomItemTexture(CustomItemProperties cip, add itemStack, rf icon) {
/* 626 */     adb item = itemStack.b();
/*     */     
/* 628 */     if (cip.damage != null) {
/*     */       
/* 630 */       int damage = itemStack.k();
/*     */       
/* 632 */       if (cip.damageMask != 0) {
/* 633 */         damage &= cip.damageMask;
/*     */       }
/* 635 */       int damageMax = item.o();
/* 636 */       if (cip.damagePercent) {
/* 637 */         damage = (int)((damage * 100) / damageMax);
/*     */       }
/* 639 */       if (!cip.damage.isInRange(damage)) {
/* 640 */         return null;
/*     */       }
/*     */     } 
/* 643 */     if (cip.stackSize != null)
/*     */     {
/* 645 */       if (!cip.stackSize.isInRange(itemStack.b)) {
/* 646 */         return null;
/*     */       }
/*     */     }
/* 649 */     if (cip.enchantmentIds != null) {
/*     */       
/* 651 */       int[] ids = getEnchantmentIds(itemStack);
/* 652 */       boolean idMatch = false;
/* 653 */       for (int i = 0; i < ids.length; i++) {
/*     */         
/* 655 */         int id = ids[i];
/* 656 */         if (cip.enchantmentIds.isInRange(id)) {
/*     */           
/* 658 */           idMatch = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 662 */       if (!idMatch) {
/* 663 */         return null;
/*     */       }
/*     */     } 
/* 666 */     if (cip.enchantmentLevels != null) {
/*     */       
/* 668 */       int[] levels = getEnchantmentLevels(itemStack);
/* 669 */       boolean levelMatch = false;
/* 670 */       for (int i = 0; i < levels.length; i++) {
/*     */         
/* 672 */         int level = levels[i];
/* 673 */         if (cip.enchantmentLevels.isInRange(level)) {
/*     */           
/* 675 */           levelMatch = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 679 */       if (!levelMatch) {
/* 680 */         return null;
/*     */       }
/*     */     } 
/* 683 */     if (cip.nbtTagValues != null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 688 */     return cip.textureIcon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] getEnchantmentIds(add itemStack) {
/* 696 */     Map map = afv.a(itemStack);
/* 697 */     Set keySet = map.keySet();
/* 698 */     int[] ids = new int[keySet.size()];
/* 699 */     int index = 0;
/* 700 */     for (Iterator<Integer> it = keySet.iterator(); it.hasNext(); ) {
/*     */       
/* 702 */       Integer id = it.next();
/* 703 */       ids[index] = id.intValue();
/* 704 */       index++;
/*     */     } 
/* 706 */     return ids;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] getEnchantmentLevels(add itemStack) {
/* 714 */     Map map = afv.a(itemStack);
/* 715 */     Collection values = map.values();
/* 716 */     int[] levels = new int[values.size()];
/* 717 */     int index = 0;
/* 718 */     for (Iterator<Integer> it = values.iterator(); it.hasNext(); ) {
/*     */       
/* 720 */       Integer level = it.next();
/* 721 */       levels[index] = level.intValue();
/* 722 */       index++;
/*     */     } 
/* 724 */     return levels;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static bqx getLocationItemGlint(add par2ItemStack, bqx resItemGlint) {
/* 734 */     return resItemGlint;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\CustomItems.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */