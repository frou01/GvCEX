/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
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
/*     */ public class RandomMobsProperties
/*     */ {
/*  20 */   public String name = null;
/*  21 */   public String basePath = null;
/*     */   
/*  23 */   public bqx[] resourceLocations = null;
/*     */   
/*  25 */   public RandomMobsRule[] rules = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RandomMobsProperties(String path, bqx[] variants) {
/*  32 */     ConnectedParser cp = new ConnectedParser("RandomMobs");
/*     */     
/*  34 */     this.name = cp.parseName(path);
/*  35 */     this.basePath = cp.parseBasePath(path);
/*     */     
/*  37 */     this.resourceLocations = variants;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RandomMobsProperties(Properties props, String path, bqx baseResLoc) {
/*  44 */     ConnectedParser cp = new ConnectedParser("RandomMobs");
/*     */     
/*  46 */     this.name = cp.parseName(path);
/*  47 */     this.basePath = cp.parseBasePath(path);
/*     */     
/*  49 */     this.rules = parseRules(props, path, baseResLoc, cp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public bqx getTextureLocation(bqx loc, sw el) {
/*  59 */     if (this.rules != null)
/*     */     {
/*  61 */       for (int i = 0; i < this.rules.length; ) {
/*     */         
/*  63 */         RandomMobsRule rule = this.rules[i];
/*  64 */         if (!rule.matches(el)) {
/*     */           i++; continue;
/*     */         } 
/*  67 */         return rule.getTextureLocation(loc, el.randomMobsId);
/*     */       } 
/*     */     }
/*     */     
/*  71 */     if (this.resourceLocations != null) {
/*     */       
/*  73 */       int randomId = el.randomMobsId;
/*     */       
/*  75 */       int index = randomId % this.resourceLocations.length;
/*     */       
/*  77 */       return this.resourceLocations[index];
/*     */     } 
/*     */     
/*  80 */     return loc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RandomMobsRule[] parseRules(Properties props, String path, bqx baseResLoc, ConnectedParser cp) {
/*  89 */     List<RandomMobsRule> list = new ArrayList();
/*  90 */     int count = props.size();
/*  91 */     for (int i = 0; i < count; i++) {
/*     */       
/*  93 */       int index = i + 1;
/*  94 */       String valSkins = props.getProperty("skins." + index);
/*  95 */       if (valSkins != null) {
/*     */ 
/*     */         
/*  98 */         int[] skins = cp.parseIntList(valSkins);
/*  99 */         int[] weights = cp.parseIntList(props.getProperty("weights." + index));
/* 100 */         ahu[] biomes = cp.parseBiomes(props.getProperty("biomes." + index));
/* 101 */         RangeListInt heights = cp.parseRangeListInt(props.getProperty("heights." + index));
/*     */         
/* 103 */         if (heights == null) {
/* 104 */           heights = parseMinMaxHeight(props, index);
/*     */         }
/* 106 */         NbtTagValue nbtName = cp.parseNbtTagValue("name", props.getProperty("name." + index));
/*     */         
/* 108 */         VillagerProfession[] professions = cp.parseProfessions(props.getProperty("professions." + index));
/*     */         
/* 110 */         RandomMobsRule rule = new RandomMobsRule(baseResLoc, index, skins, weights, biomes, heights, nbtName, professions);
/*     */         
/* 112 */         if (rule.isValid(path))
/*     */         {
/*     */           
/* 115 */           list.add(rule); } 
/*     */       } 
/*     */     } 
/* 118 */     RandomMobsRule[] rules = list.<RandomMobsRule>toArray(new RandomMobsRule[list.size()]);
/*     */     
/* 120 */     return rules;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RangeListInt parseMinMaxHeight(Properties props, int index) {
/* 128 */     String minHeightStr = props.getProperty("minHeight." + index);
/* 129 */     String maxHeightStr = props.getProperty("maxHeight." + index);
/*     */     
/* 131 */     if (minHeightStr == null && maxHeightStr == null) {
/* 132 */       return null;
/*     */     }
/* 134 */     int minHeight = 0;
/* 135 */     if (minHeightStr != null) {
/*     */       
/* 137 */       minHeight = Config.parseInt(minHeightStr, -1);
/* 138 */       if (minHeight < 0) {
/*     */         
/* 140 */         Config.warn("Invalid minHeight: " + minHeightStr);
/* 141 */         return null;
/*     */       } 
/*     */     } 
/*     */     
/* 145 */     int maxHeight = 256;
/* 146 */     if (maxHeightStr != null) {
/*     */       
/* 148 */       maxHeight = Config.parseInt(maxHeightStr, -1);
/* 149 */       if (maxHeight < 0) {
/*     */         
/* 151 */         Config.warn("Invalid maxHeight: " + maxHeightStr);
/* 152 */         return null;
/*     */       } 
/*     */     } 
/*     */     
/* 156 */     if (maxHeight < 0) {
/*     */       
/* 158 */       Config.warn("Invalid minHeight, maxHeight: " + minHeightStr + ", " + maxHeightStr);
/* 159 */       return null;
/*     */     } 
/*     */     
/* 162 */     RangeListInt list = new RangeListInt();
/* 163 */     list.addRange(new RangeInt(minHeight, maxHeight));
/*     */     
/* 165 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String path) {
/* 173 */     if (this.resourceLocations == null && this.rules == null) {
/*     */       
/* 175 */       Config.warn("No skins specified: " + path);
/* 176 */       return false;
/*     */     } 
/*     */     
/* 179 */     if (this.rules != null)
/*     */     {
/* 181 */       for (int i = 0; i < this.rules.length; i++) {
/*     */         
/* 183 */         RandomMobsRule rule = this.rules[i];
/* 184 */         if (!rule.isValid(path)) {
/* 185 */           return false;
/*     */         }
/*     */       } 
/*     */     }
/* 189 */     if (this.resourceLocations != null)
/*     */     {
/* 191 */       for (int i = 0; i < this.resourceLocations.length; i++) {
/*     */         
/* 193 */         bqx loc = this.resourceLocations[i];
/* 194 */         if (!Config.hasResource(loc)) {
/*     */           
/* 196 */           Config.warn("Texture not found: " + loc.a());
/* 197 */           return false;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 202 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\RandomMobsProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */