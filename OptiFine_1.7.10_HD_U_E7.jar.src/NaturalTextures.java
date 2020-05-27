/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class NaturalTextures
/*     */ {
/*  22 */   private static NaturalProperties[] propertiesByIndex = new NaturalProperties[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void update() {
/*  29 */     propertiesByIndex = new NaturalProperties[0];
/*     */     
/*  31 */     if (!Config.isNaturalTextures()) {
/*     */       return;
/*     */     }
/*  34 */     String fileName = "optifine/natural.properties";
/*     */ 
/*     */     
/*     */     try {
/*  38 */       bqx loc = new bqx(fileName);
/*  39 */       if (!Config.hasResource(loc)) {
/*     */         
/*  41 */         Config.dbg("NaturalTextures: configuration \"" + fileName + "\" not found");
/*  42 */         propertiesByIndex = makeDefaultProperties();
/*     */         return;
/*     */       } 
/*  45 */       InputStream in = Config.getResourceStream(loc);
/*  46 */       ArrayList<NaturalProperties> list = new ArrayList(256);
/*  47 */       String configStr = Config.readInputStream(in);
/*  48 */       in.close();
/*  49 */       String[] configLines = Config.tokenize(configStr, "\n\r");
/*  50 */       Config.dbg("Natural Textures: Parsing configuration \"" + fileName + "\"");
/*  51 */       for (int i = 0; i < configLines.length; i++) {
/*     */         
/*  53 */         String line = configLines[i].trim();
/*     */         
/*  55 */         if (!line.startsWith("#")) {
/*     */ 
/*     */           
/*  58 */           String[] strs = Config.tokenize(line, "=");
/*  59 */           if (strs.length != 2) {
/*     */             
/*  61 */             Config.warn("Natural Textures: Invalid \"" + fileName + "\" line: " + line);
/*     */           } else {
/*     */             
/*  64 */             String key = strs[0].trim();
/*  65 */             String type = strs[1].trim();
/*     */             
/*  67 */             String texName = key;
/*  68 */             bqd ts = bpz.textureMapBlocks.getIconSafe(texName);
/*  69 */             if (ts == null)
/*     */             
/*  71 */             { Config.warn("Natural Textures: Texture not found: \"" + fileName + "\" line: " + line); }
/*     */             else
/*     */             
/*  74 */             { int tileNum = ts.getIndexInMap();
/*  75 */               if (tileNum < 0)
/*     */               
/*  77 */               { Config.warn("Natural Textures: Invalid \"" + fileName + "\" line: " + line); }
/*     */               else
/*     */               
/*  80 */               { NaturalProperties props = new NaturalProperties(type);
/*  81 */                 if (props.isValid())
/*     */                 
/*     */                 { 
/*  84 */                   while (list.size() <= tileNum) {
/*  85 */                     list.add(null);
/*     */                   }
/*  87 */                   list.set(tileNum, props);
/*     */                   
/*  89 */                   Config.dbg("NaturalTextures: " + texName + " = " + type); }  }  } 
/*     */           } 
/*     */         } 
/*  92 */       }  propertiesByIndex = list.<NaturalProperties>toArray(new NaturalProperties[list.size()]);
/*     */     }
/*  94 */     catch (FileNotFoundException e) {
/*     */       
/*  96 */       Config.warn("NaturalTextures: configuration \"" + fileName + "\" not found");
/*  97 */       propertiesByIndex = makeDefaultProperties();
/*     */       
/*     */       return;
/* 100 */     } catch (Exception e) {
/*     */       
/* 102 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NaturalProperties getNaturalProperties(rf icon) {
/* 112 */     if (!(icon instanceof bqd)) {
/* 113 */       return null;
/*     */     }
/* 115 */     bqd ts = (bqd)icon;
/*     */     
/* 117 */     int tileNum = ts.getIndexInMap();
/*     */     
/* 119 */     if (tileNum < 0 || tileNum >= propertiesByIndex.length) {
/* 120 */       return null;
/*     */     }
/* 122 */     NaturalProperties props = propertiesByIndex[tileNum];
/*     */     
/* 124 */     return props;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static NaturalProperties[] makeDefaultProperties() {
/* 132 */     Config.dbg("NaturalTextures: Checking default configuration.");
/*     */     
/* 134 */     List propsList = new ArrayList();
/*     */     
/* 136 */     setIconProperties(propsList, "grass_top", "4F");
/*     */     
/* 138 */     setIconProperties(propsList, "stone", "2F");
/*     */     
/* 140 */     setIconProperties(propsList, "dirt", "4F");
/*     */     
/* 142 */     setIconProperties(propsList, "grass_side", "F");
/* 143 */     setIconProperties(propsList, "grass_side_overlay", "F");
/*     */     
/* 145 */     setIconProperties(propsList, "stone_slab_top", "F");
/*     */     
/* 147 */     setIconProperties(propsList, "bedrock", "2F");
/*     */     
/* 149 */     setIconProperties(propsList, "sand", "4F");
/*     */     
/* 151 */     setIconProperties(propsList, "gravel", "2");
/*     */     
/* 153 */     setIconProperties(propsList, "log_oak", "2F");
/*     */     
/* 155 */     setIconProperties(propsList, "log_oak_top", "4F");
/*     */     
/* 157 */     setIconProperties(propsList, "gold_ore", "2F");
/* 158 */     setIconProperties(propsList, "iron_ore", "2F");
/* 159 */     setIconProperties(propsList, "coal_ore", "2F");
/* 160 */     setIconProperties(propsList, "diamond_ore", "2F");
/* 161 */     setIconProperties(propsList, "redstone_ore", "2F");
/* 162 */     setIconProperties(propsList, "lapis_ore", "2F");
/*     */     
/* 164 */     setIconProperties(propsList, "obsidian", "4F");
/*     */     
/* 166 */     setIconProperties(propsList, "leaves_oak", "2F");
/* 167 */     setIconProperties(propsList, "leaves_oak_opaque", "2F");
/*     */     
/* 169 */     setIconProperties(propsList, "leaves_jungle", "2");
/* 170 */     setIconProperties(propsList, "leaves_jungle_opaque", "2");
/*     */     
/* 172 */     setIconProperties(propsList, "snow", "4F");
/*     */     
/* 174 */     setIconProperties(propsList, "grass_side_snowed", "F");
/*     */     
/* 176 */     setIconProperties(propsList, "cactus_side", "2F");
/*     */     
/* 178 */     setIconProperties(propsList, "clay", "4F");
/*     */     
/* 180 */     setIconProperties(propsList, "mycelium_side", "F");
/*     */     
/* 182 */     setIconProperties(propsList, "mycelium_top", "4F");
/*     */     
/* 184 */     setIconProperties(propsList, "farmland_wet", "2F");
/*     */     
/* 186 */     setIconProperties(propsList, "farmland_dry", "2F");
/*     */     
/* 188 */     setIconProperties(propsList, "netherrack", "4F");
/* 189 */     setIconProperties(propsList, "soul_sand", "4F");
/* 190 */     setIconProperties(propsList, "glowstone", "4");
/*     */     
/* 192 */     setIconProperties(propsList, "log_spruce", "2F");
/*     */     
/* 194 */     setIconProperties(propsList, "log_birch", "F");
/*     */     
/* 196 */     setIconProperties(propsList, "leaves_spruce", "2F");
/* 197 */     setIconProperties(propsList, "leaves_spruce_opaque", "2F");
/*     */     
/* 199 */     setIconProperties(propsList, "log_jungle", "2F");
/*     */     
/* 201 */     setIconProperties(propsList, "end_stone", "4");
/*     */     
/* 203 */     setIconProperties(propsList, "sandstone_top", "4");
/*     */     
/* 205 */     setIconProperties(propsList, "sandstone_bottom", "4F");
/*     */     
/* 207 */     setIconProperties(propsList, "redstone_lamp_on", "4F");
/*     */     
/* 209 */     NaturalProperties[] terrainProps = (NaturalProperties[])propsList.toArray((Object[])new NaturalProperties[propsList.size()]);
/*     */     
/* 211 */     return terrainProps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setIconProperties(List<NaturalProperties> propsList, String iconName, String propStr) {
/* 221 */     bpz terrainMap = bpz.textureMapBlocks;
/* 222 */     bqd bqd1 = terrainMap.getIconSafe(iconName);
/* 223 */     if (bqd1 == null) {
/*     */       
/* 225 */       Config.warn("*** NaturalProperties: Icon not found: " + iconName + " ***");
/*     */       
/*     */       return;
/*     */     } 
/* 229 */     if (!(bqd1 instanceof bqd)) {
/*     */       
/* 231 */       Config.warn("*** NaturalProperties: Icon is not IconStitched: " + iconName + ": " + bqd1.getClass().getName() + " ***");
/*     */       return;
/*     */     } 
/* 234 */     bqd ts = bqd1;
/*     */     
/* 236 */     int index = ts.getIndexInMap();
/*     */     
/* 238 */     if (index < 0) {
/*     */       
/* 240 */       Config.warn("*** NaturalProperties: Invalid index for icon: " + iconName + ": " + index + " ***");
/*     */       
/*     */       return;
/*     */     } 
/* 244 */     if (!Config.isFromDefaultResourcePack(new bqx("textures/blocks/" + iconName + ".png"))) {
/*     */       return;
/*     */     }
/* 247 */     while (index >= propsList.size()) {
/* 248 */       propsList.add(null);
/*     */     }
/* 250 */     NaturalProperties props = new NaturalProperties(propStr);
/*     */     
/* 252 */     propsList.set(index, props);
/*     */     
/* 254 */     Config.dbg("NaturalTextures: " + iconName + " = " + propStr);
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\NaturalTextures.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */