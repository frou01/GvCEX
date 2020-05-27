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
/*     */ public class RandomMobsRule
/*     */ {
/*  17 */   private bqx baseResLoc = null;
/*     */   
/*     */   private int index;
/*     */   
/*  21 */   private int[] skins = null;
/*     */   
/*  23 */   private bqx[] resourceLocations = null;
/*     */   
/*  25 */   private int[] weights = null;
/*  26 */   private ahu[] biomes = null;
/*  27 */   private RangeListInt heights = null;
/*  28 */   private NbtTagValue nbtName = null;
/*     */   
/*  30 */   public int[] sumWeights = null;
/*     */   
/*  32 */   public int sumAllWeights = 1;
/*     */   
/*  34 */   private VillagerProfession[] professions = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RandomMobsRule(bqx baseResLoc, int index, int[] skins, int[] weights, ahu[] biomes, RangeListInt heights, NbtTagValue nbtName, VillagerProfession[] professions) {
/*  41 */     this.baseResLoc = baseResLoc;
/*  42 */     this.index = index;
/*  43 */     this.skins = skins;
/*  44 */     this.weights = weights;
/*  45 */     this.biomes = biomes;
/*  46 */     this.heights = heights;
/*  47 */     this.nbtName = nbtName;
/*  48 */     this.professions = professions;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String path) {
/*  54 */     if (this.skins == null || this.skins.length == 0) {
/*     */       
/*  56 */       Config.warn("Invalid skins for rule: " + this.index);
/*  57 */       return false;
/*     */     } 
/*     */     
/*  60 */     if (this.resourceLocations != null) {
/*  61 */       return true;
/*     */     }
/*  63 */     this.resourceLocations = new bqx[this.skins.length];
/*     */     
/*  65 */     bqx locMcp = RandomMobs.getMcpatcherLocation(this.baseResLoc);
/*  66 */     if (locMcp == null) {
/*     */       
/*  68 */       Config.warn("Invalid path: " + this.baseResLoc.a());
/*  69 */       return false;
/*     */     } 
/*     */     
/*  72 */     for (int i = 0; i < this.resourceLocations.length; i++) {
/*     */ 
/*     */       
/*  75 */       int index = this.skins[i];
/*     */       
/*  77 */       if (index <= 1) {
/*     */         
/*  79 */         this.resourceLocations[i] = this.baseResLoc;
/*     */       }
/*     */       else {
/*     */         
/*  83 */         bqx locNew = RandomMobs.getLocationIndexed(locMcp, index);
/*  84 */         if (locNew == null) {
/*     */           
/*  86 */           Config.warn("Invalid path: " + this.baseResLoc.a());
/*  87 */           return false;
/*     */         } 
/*     */         
/*  90 */         if (!Config.hasResource(locNew)) {
/*     */           
/*  92 */           Config.warn("Texture not found: " + locNew.a());
/*  93 */           return false;
/*     */         } 
/*     */         
/*  96 */         this.resourceLocations[i] = locNew;
/*     */       } 
/*     */     } 
/*  99 */     if (this.weights != null) {
/*     */ 
/*     */       
/* 102 */       if (this.weights.length > this.resourceLocations.length) {
/*     */         
/* 104 */         Config.warn("More weights defined than skins, trimming weights: " + path);
/* 105 */         int[] weights2 = new int[this.resourceLocations.length];
/* 106 */         System.arraycopy(this.weights, 0, weights2, 0, weights2.length);
/* 107 */         this.weights = weights2;
/*     */       } 
/* 109 */       if (this.weights.length < this.resourceLocations.length) {
/*     */         
/* 111 */         Config.warn("Less weights defined than skins, expanding weights: " + path);
/* 112 */         int[] weights2 = new int[this.resourceLocations.length];
/* 113 */         System.arraycopy(this.weights, 0, weights2, 0, this.weights.length);
/* 114 */         int avgWeight = MathUtils.getAverage(this.weights);
/* 115 */         for (int k = this.weights.length; k < weights2.length; k++)
/*     */         {
/* 117 */           weights2[k] = avgWeight;
/*     */         }
/* 119 */         this.weights = weights2;
/*     */       } 
/*     */       
/* 122 */       this.sumWeights = new int[this.weights.length];
/* 123 */       int sum = 0;
/* 124 */       for (int j = 0; j < this.weights.length; j++) {
/*     */ 
/*     */         
/* 127 */         if (this.weights[j] < 0) {
/*     */           
/* 129 */           Config.warn("Invalid weight: " + this.weights[j]);
/* 130 */           return false;
/*     */         } 
/*     */         
/* 133 */         sum += this.weights[j];
/* 134 */         this.sumWeights[j] = sum;
/*     */       } 
/* 136 */       this.sumAllWeights = sum;
/*     */       
/* 138 */       if (this.sumAllWeights <= 0) {
/*     */         
/* 140 */         Config.warn("Invalid sum of all weights: " + sum);
/* 141 */         this.sumAllWeights = 1;
/*     */       } 
/*     */     } 
/*     */     
/* 145 */     if (this.professions == ConnectedParser.PROFESSIONS_INVALID) {
/*     */       
/* 147 */       Config.warn("Invalid professions or careers: " + path);
/* 148 */       return false;
/*     */     } 
/*     */     
/* 151 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(sw el) {
/* 161 */     if (this.biomes != null) {
/*     */       
/* 163 */       ahu spawnBiome = el.spawnBiome;
/* 164 */       boolean matchBiome = false;
/* 165 */       for (int i = 0; i < this.biomes.length; i++) {
/*     */         
/* 167 */         ahu biome = this.biomes[i];
/* 168 */         if (biome == spawnBiome) {
/*     */           
/* 170 */           matchBiome = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 174 */       if (!matchBiome) {
/* 175 */         return false;
/*     */       }
/*     */     } 
/* 178 */     if (this.heights != null && el.spawnPosition != null) {
/* 179 */       return this.heights.isInRange(el.spawnPosition.getY());
/*     */     }
/* 181 */     if (this.nbtName != null) {
/*     */ 
/*     */       
/* 184 */       String name = el.bH() ? el.bG() : null;
/*     */       
/* 186 */       if (!this.nbtName.matchesValue(name)) {
/* 187 */         return false;
/*     */       }
/*     */     } 
/* 190 */     if (this.professions != null)
/*     */     {
/* 192 */       if (el instanceof yv) {
/*     */         
/* 194 */         yv entityVillager = (yv)el;
/*     */         
/* 196 */         int profInt = entityVillager.bZ();
/*     */         
/* 198 */         int careerInt = 0;
/*     */         
/* 200 */         if (profInt < 0 || careerInt < 0) {
/* 201 */           return false;
/*     */         }
/* 203 */         boolean matchProfession = false;
/* 204 */         for (int i = 0; i < this.professions.length; i++) {
/*     */           
/* 206 */           VillagerProfession prof = this.professions[i];
/* 207 */           if (prof.matches(profInt, careerInt)) {
/*     */             
/* 209 */             matchProfession = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 213 */         if (!matchProfession) {
/* 214 */           return false;
/*     */         }
/*     */       } 
/*     */     }
/* 218 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public bqx getTextureLocation(bqx loc, int randomId) {
/* 229 */     if (this.resourceLocations == null || this.resourceLocations.length == 0) {
/* 230 */       return loc;
/*     */     }
/* 232 */     int index = 0;
/*     */     
/* 234 */     if (this.weights == null) {
/*     */ 
/*     */       
/* 237 */       index = randomId % this.resourceLocations.length;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 242 */       int randWeight = randomId % this.sumAllWeights;
/* 243 */       for (int i = 0; i < this.sumWeights.length; i++) {
/*     */         
/* 245 */         if (this.sumWeights[i] > randWeight) {
/*     */           
/* 247 */           index = i;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 253 */     return this.resourceLocations[index];
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\RandomMobsRule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */