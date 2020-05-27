/*     */ package shadersmod.client;
/*     */ 
/*     */ import Config;
/*     */ import ConnectedParser;
/*     */ import MatchBlock;
/*     */ import PropertiesOrdered;
/*     */ import Reflector;
/*     */ import ReflectorForge;
/*     */ import StrUtils;
/*     */ import bao;
/*     */ import bqx;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
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
/*     */ public class BlockAliases
/*     */ {
/*  30 */   private static BlockAlias[][] blockAliases = (BlockAlias[][])null;
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean updateOnResourcesReloaded;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getMappedBlockId(int blockId, int metadata) {
/*  40 */     if (blockAliases == null) {
/*  41 */       return blockId;
/*     */     }
/*  43 */     if (blockId < 0 || blockId >= blockAliases.length) {
/*  44 */       return blockId;
/*     */     }
/*  46 */     BlockAlias[] aliases = blockAliases[blockId];
/*  47 */     if (aliases == null) {
/*  48 */       return blockId;
/*     */     }
/*  50 */     for (int i = 0; i < aliases.length; i++) {
/*     */       
/*  52 */       BlockAlias ba = aliases[i];
/*  53 */       if (ba.matches(blockId, metadata)) {
/*  54 */         return ba.getBlockId();
/*     */       }
/*     */     } 
/*  57 */     return blockId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void resourcesReloaded() {
/*  65 */     if (!updateOnResourcesReloaded) {
/*     */       return;
/*     */     }
/*  68 */     updateOnResourcesReloaded = false;
/*     */     
/*  70 */     update(Shaders.shaderPack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void update(IShaderPack shaderPack) {
/*  78 */     reset();
/*     */     
/*  80 */     if (shaderPack == null) {
/*     */       return;
/*     */     }
/*  83 */     if (Reflector.Loader_getActiveModList.exists() && bao.B().R() == null) {
/*     */ 
/*     */       
/*  86 */       Config.dbg("[Shaders] Delayed loading of block mappings after resources are loaded");
/*  87 */       updateOnResourcesReloaded = true;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  92 */     List<List<BlockAlias>> listBlockAliases = new ArrayList<List<BlockAlias>>();
/*     */     
/*  94 */     String path = "/shaders/block.properties";
/*  95 */     InputStream in = shaderPack.getResourceAsStream(path);
/*     */     
/*  97 */     if (in != null) {
/*  98 */       loadBlockAliases(in, path, listBlockAliases);
/*     */     }
/* 100 */     loadModBlockAliases(listBlockAliases);
/*     */     
/* 102 */     if (listBlockAliases.size() <= 0) {
/*     */       return;
/*     */     }
/* 105 */     blockAliases = toArrays(listBlockAliases);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadModBlockAliases(List<List<BlockAlias>> listBlockAliases) {
/* 113 */     String[] modIds = ReflectorForge.getForgeModIds();
/* 114 */     for (int i = 0; i < modIds.length; i++) {
/*     */       
/* 116 */       String modId = modIds[i];
/*     */       
/*     */       try {
/* 119 */         bqx loc = new bqx(modId, "shaders/block.properties");
/* 120 */         InputStream in = Config.getResourceStream(loc);
/*     */         
/* 122 */         loadBlockAliases(in, loc.toString(), listBlockAliases);
/*     */       }
/* 124 */       catch (IOException e) {}
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
/*     */   private static void loadBlockAliases(InputStream in, String path, List<List<BlockAlias>> listBlockAliases) {
/* 136 */     if (in == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 141 */       PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 142 */       propertiesOrdered.load(in);
/* 143 */       in.close();
/*     */       
/* 145 */       Config.dbg("[Shaders] Parsing block mappings: " + path);
/*     */       
/* 147 */       ConnectedParser cp = new ConnectedParser("Shaders");
/* 148 */       Set keys = propertiesOrdered.keySet();
/* 149 */       for (Iterator<String> it = keys.iterator(); it.hasNext(); )
/*     */       {
/* 151 */         String key = it.next();
/* 152 */         String val = propertiesOrdered.getProperty(key);
/*     */         
/* 154 */         String prefix = "block.";
/* 155 */         if (!key.startsWith(prefix)) {
/*     */           
/* 157 */           Config.warn("[Shaders] Invalid block ID: " + key);
/*     */           
/*     */           continue;
/*     */         } 
/* 161 */         String blockIdStr = StrUtils.removePrefix(key, prefix);
/*     */         
/* 163 */         int blockId = Config.parseInt(blockIdStr, -1);
/* 164 */         if (blockId < 0) {
/*     */           
/* 166 */           Config.warn("[Shaders] Invalid block ID: " + key);
/*     */           
/*     */           continue;
/*     */         } 
/* 170 */         MatchBlock[] matchBlocks = cp.parseMatchBlocks(val);
/*     */         
/* 172 */         if (matchBlocks == null || matchBlocks.length < 1) {
/*     */           
/* 174 */           Config.warn("[Shaders] Invalid block ID mapping: " + key + "=" + val);
/*     */           
/*     */           continue;
/*     */         } 
/* 178 */         BlockAlias ba = new BlockAlias(blockId, matchBlocks);
/*     */         
/* 180 */         addToList(listBlockAliases, ba);
/*     */       }
/*     */     
/* 183 */     } catch (IOException e) {
/*     */       
/* 185 */       Config.warn("[Shaders] Error reading: " + path);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addToList(List<List<BlockAlias>> blocksAliases, BlockAlias ba) {
/* 195 */     int[] blockIds = ba.getMatchBlockIds();
/* 196 */     for (int i = 0; i < blockIds.length; i++) {
/*     */       
/* 198 */       int blockId = blockIds[i];
/*     */       
/* 200 */       while (blockId >= blocksAliases.size()) {
/* 201 */         blocksAliases.add(null);
/*     */       }
/* 203 */       List<BlockAlias> blockAliases = blocksAliases.get(blockId);
/*     */       
/* 205 */       if (blockAliases == null) {
/*     */         
/* 207 */         blockAliases = new ArrayList<BlockAlias>();
/* 208 */         blocksAliases.set(blockId, blockAliases);
/*     */       } 
/*     */       
/* 211 */       BlockAlias baBlock = new BlockAlias(ba.getBlockId(), ba.getMatchBlocks(blockId));
/*     */       
/* 213 */       blockAliases.add(baBlock);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static BlockAlias[][] toArrays(List<List<BlockAlias>> listBlocksAliases) {
/* 222 */     BlockAlias[][] bas = new BlockAlias[listBlocksAliases.size()][];
/* 223 */     for (int i = 0; i < bas.length; i++) {
/*     */       
/* 225 */       List<BlockAlias> listBlockAliases = listBlocksAliases.get(i);
/*     */       
/* 227 */       if (listBlockAliases != null)
/*     */       {
/*     */         
/* 230 */         bas[i] = listBlockAliases.<BlockAlias>toArray(new BlockAlias[listBlockAliases.size()]);
/*     */       }
/*     */     } 
/* 233 */     return bas;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void reset() {
/* 241 */     blockAliases = (BlockAlias[][])null;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\BlockAliases.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */