/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomSky
/*     */ {
/*  26 */   private static CustomSkyLayer[][] worldSkyLayers = (CustomSkyLayer[][])null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void reset() {
/*  33 */     worldSkyLayers = (CustomSkyLayer[][])null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void update() {
/*  41 */     reset();
/*     */     
/*  43 */     if (!Config.isCustomSky()) {
/*     */       return;
/*     */     }
/*  46 */     worldSkyLayers = readCustomSkies();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static CustomSkyLayer[][] readCustomSkies() {
/*  54 */     CustomSkyLayer[][] wsls = new CustomSkyLayer[10][0];
/*  55 */     String prefix = "mcpatcher/sky/world";
/*  56 */     int lastWorldId = -1;
/*     */     
/*  58 */     for (int w = 0; w < wsls.length; w++) {
/*     */       
/*  60 */       String worldPrefix = prefix + w + "/sky";
/*  61 */       List<CustomSkyLayer> listSkyLayers = new ArrayList();
/*  62 */       for (int j = 1; j < 1000; j++) {
/*     */         
/*  64 */         String path = worldPrefix + j + ".properties";
/*     */         
/*     */         try {
/*  67 */           bqx locPath = new bqx(path);
/*  68 */           InputStream in = Config.getResourceStream(locPath);
/*  69 */           if (in == null) {
/*     */             break;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*  75 */           Properties props = new Properties();
/*  76 */           props.load(in);
/*  77 */           in.close();
/*     */           
/*  79 */           Config.dbg("CustomSky properties: " + path);
/*     */           
/*  81 */           String defSource = worldPrefix + j + ".png";
/*  82 */           CustomSkyLayer sl = new CustomSkyLayer(props, defSource);
/*     */           
/*  84 */           if (sl.isValid(path)) {
/*     */ 
/*     */             
/*  87 */             bqx locSource = new bqx(sl.source);
/*  88 */             bqh tex = TextureUtils.getTexture(locSource);
/*  89 */             if (tex == null)
/*     */             
/*  91 */             { Config.log("CustomSky: Texture not found: " + locSource); }
/*     */             else
/*     */             
/*  94 */             { sl.textureId = tex.b();
/*     */               
/*  96 */               listSkyLayers.add(sl);
/*     */               
/*  98 */               in.close(); } 
/*     */           } 
/* 100 */         } catch (FileNotFoundException e) {
/*     */ 
/*     */           
/*     */           break;
/*     */         }
/* 105 */         catch (IOException e) {
/*     */           
/* 107 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */       
/* 111 */       if (listSkyLayers.size() > 0) {
/*     */ 
/*     */         
/* 114 */         CustomSkyLayer[] sls = listSkyLayers.<CustomSkyLayer>toArray(new CustomSkyLayer[listSkyLayers.size()]);
/*     */         
/* 116 */         wsls[w] = sls;
/*     */         
/* 118 */         lastWorldId = w;
/*     */       } 
/*     */     } 
/*     */     
/* 122 */     if (lastWorldId < 0) {
/* 123 */       return (CustomSkyLayer[][])null;
/*     */     }
/* 125 */     int worldCount = lastWorldId + 1;
/* 126 */     CustomSkyLayer[][] wslsTrim = new CustomSkyLayer[worldCount][0];
/* 127 */     for (int i = 0; i < wslsTrim.length; i++)
/*     */     {
/* 129 */       wslsTrim[i] = wsls[i];
/*     */     }
/*     */     
/* 132 */     return wslsTrim;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderSky(ahb world, bqf re, float partialTicks) {
/* 140 */     if (worldSkyLayers == null) {
/*     */       return;
/*     */     }
/* 143 */     int dimId = world.t.i;
/* 144 */     if (dimId < 0 || dimId >= worldSkyLayers.length) {
/*     */       return;
/*     */     }
/* 147 */     CustomSkyLayer[] sls = worldSkyLayers[dimId];
/* 148 */     if (sls == null) {
/*     */       return;
/*     */     }
/* 151 */     long time = world.J();
/* 152 */     int timeOfDay = (int)(time % 24000L);
/*     */     
/* 154 */     float celestialAngle = world.c(partialTicks);
/* 155 */     float rainStrength = world.j(partialTicks);
/* 156 */     float thunderStrength = world.h(partialTicks);
/*     */     
/* 158 */     if (rainStrength > 0.0F) {
/* 159 */       thunderStrength /= rainStrength;
/*     */     }
/* 161 */     for (int i = 0; i < sls.length; i++) {
/*     */       
/* 163 */       CustomSkyLayer sl = sls[i];
/* 164 */       if (sl.isActive(world, timeOfDay)) {
/* 165 */         sl.render(world, timeOfDay, celestialAngle, rainStrength, thunderStrength);
/*     */       }
/*     */     } 
/* 168 */     float rainBrightness = 1.0F - rainStrength;
/* 169 */     Blender.clearBlend(rainBrightness);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasSkyLayers(ahb world) {
/* 179 */     if (worldSkyLayers == null) {
/* 180 */       return false;
/*     */     }
/* 182 */     int dimId = world.t.i;
/* 183 */     if (dimId < 0 || dimId >= worldSkyLayers.length) {
/* 184 */       return false;
/*     */     }
/* 186 */     CustomSkyLayer[] sls = worldSkyLayers[dimId];
/* 187 */     if (sls == null) {
/* 188 */       return false;
/*     */     }
/* 190 */     return (sls.length > 0);
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\CustomSky.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */