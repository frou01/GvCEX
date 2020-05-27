/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class ReflectorForge
/*     */ {
/*     */   public static void FMLClientHandler_trackBrokenTexture(bqx loc, String message) {
/*  27 */     if (Reflector.FMLClientHandler_trackBrokenTexture.exists()) {
/*     */       return;
/*     */     }
/*  30 */     Object instance = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
/*     */     
/*  32 */     Reflector.call(instance, Reflector.FMLClientHandler_trackBrokenTexture, new Object[] { loc, message });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void FMLClientHandler_trackMissingTexture(bqx loc) {
/*  38 */     if (Reflector.FMLClientHandler_trackMissingTexture.exists()) {
/*     */       return;
/*     */     }
/*  41 */     Object instance = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
/*     */     
/*  43 */     Reflector.call(instance, Reflector.FMLClientHandler_trackMissingTexture, new Object[] { loc });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void putLaunchBlackboard(String key, Object value) {
/*  50 */     Map<String, Object> blackboard = (Map)Reflector.getFieldValue(Reflector.Launch_blackboard);
/*     */     
/*  52 */     if (blackboard == null) {
/*     */       return;
/*     */     }
/*  55 */     blackboard.put(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean renderFirstPersonHand(bma renderGlobal, float partialTicks, int pass) {
/*  66 */     if (!Reflector.ForgeHooksClient_renderFirstPersonHand.exists()) {
/*  67 */       return false;
/*     */     }
/*  69 */     return Reflector.callBoolean(Reflector.ForgeHooksClient_renderFirstPersonHand, new Object[] { renderGlobal, Float.valueOf(partialTicks), Integer.valueOf(pass) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InputStream getOptiFineResourceStream(String path) {
/*  77 */     if (!Reflector.OptiFineClassTransformer_instance.exists()) {
/*  78 */       return null;
/*     */     }
/*  80 */     Object instance = Reflector.getFieldValue(Reflector.OptiFineClassTransformer_instance);
/*  81 */     if (instance == null) {
/*  82 */       return null;
/*     */     }
/*  84 */     if (path.startsWith("/")) {
/*  85 */       path = path.substring(1);
/*     */     }
/*  87 */     byte[] bytes = (byte[])Reflector.call(instance, Reflector.OptiFineClassTransformer_getOptiFineResource, new Object[] { path });
/*  88 */     if (bytes == null) {
/*  89 */       return null;
/*     */     }
/*  91 */     InputStream in = new ByteArrayInputStream(bytes);
/*     */     
/*  93 */     return in;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean blockHasTileEntity(ahb world, int x, int y, int z) {
/* 101 */     aji block = world.a(x, y, z);
/*     */     
/* 103 */     if (!Reflector.ForgeBlock_hasTileEntity.exists())
/*     */     {
/*     */       
/* 106 */       return block.u();
/*     */     }
/*     */     
/* 109 */     int metadata = world.e(x, y, z);
/*     */     
/* 111 */     return Reflector.callBoolean(block, Reflector.ForgeBlock_hasTileEntity, new Object[] { Integer.valueOf(metadata) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] getForgeModIds() {
/* 118 */     if (!Reflector.Loader.exists()) {
/* 119 */       return new String[0];
/*     */     }
/* 121 */     Object loader = Reflector.call(Reflector.Loader_instance, new Object[0]);
/* 122 */     List listActiveMods = (List)Reflector.call(loader, Reflector.Loader_getActiveModList, new Object[0]);
/* 123 */     if (listActiveMods == null) {
/* 124 */       return new String[0];
/*     */     }
/* 126 */     List<String> listModIds = new ArrayList<String>();
/* 127 */     for (Iterator it = listActiveMods.iterator(); it.hasNext(); ) {
/*     */       
/* 129 */       Object modContainer = it.next();
/*     */       
/* 131 */       if (!Reflector.ModContainer.isInstance(modContainer)) {
/*     */         continue;
/*     */       }
/* 134 */       String modId = Reflector.callString(modContainer, Reflector.ModContainer_getModId, new Object[0]);
/* 135 */       if (modId == null) {
/*     */         continue;
/*     */       }
/* 138 */       listModIds.add(modId);
/*     */     } 
/*     */     
/* 141 */     String[] modIds = listModIds.<String>toArray(new String[listModIds.size()]);
/*     */     
/* 143 */     return modIds;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\ReflectorForge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */