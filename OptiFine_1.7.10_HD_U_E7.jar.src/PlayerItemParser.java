/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.awt.Dimension;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerItemParser
/*     */ {
/*  31 */   private static JsonParser jsonParser = new JsonParser();
/*     */   
/*     */   public static final String ITEM_TYPE = "type";
/*     */   
/*     */   public static final String ITEM_TEXTURE_SIZE = "textureSize";
/*     */   
/*     */   public static final String ITEM_USE_PLAYER_TEXTURE = "usePlayerTexture";
/*     */   
/*     */   public static final String ITEM_MODELS = "models";
/*     */   
/*     */   public static final String MODEL_ID = "id";
/*     */   
/*     */   public static final String MODEL_BASE_ID = "baseId";
/*     */   
/*     */   public static final String MODEL_TYPE = "type";
/*     */   
/*     */   public static final String MODEL_ATTACH_TO = "attachTo";
/*     */   public static final String MODEL_INVERT_AXIS = "invertAxis";
/*     */   public static final String MODEL_MIRROR_TEXTURE = "mirrorTexture";
/*     */   public static final String MODEL_TRANSLATE = "translate";
/*     */   public static final String MODEL_ROTATE = "rotate";
/*     */   public static final String MODEL_SCALE = "scale";
/*     */   public static final String MODEL_BOXES = "boxes";
/*     */   public static final String MODEL_SPRITES = "sprites";
/*     */   public static final String MODEL_SUBMODEL = "submodel";
/*     */   public static final String MODEL_SUBMODELS = "submodels";
/*     */   public static final String BOX_TEXTURE_OFFSET = "textureOffset";
/*     */   public static final String BOX_COORDINATES = "coordinates";
/*     */   public static final String BOX_SIZE_ADD = "sizeAdd";
/*     */   public static final String ITEM_TYPE_MODEL = "PlayerItem";
/*     */   public static final String MODEL_TYPE_BOX = "ModelBox";
/*     */   
/*     */   public static PlayerItemModel parseItemModel(JsonObject obj) {
/*  64 */     String type = Json.getString(obj, "type");
/*     */     
/*  66 */     if (!Config.equals(type, "PlayerItem")) {
/*  67 */       throw new JsonParseException("Unknown model type: " + type);
/*     */     }
/*  69 */     int[] textureSize = Json.parseIntArray(obj.get("textureSize"), 2);
/*  70 */     checkNull(textureSize, "Missing texture size");
/*     */     
/*  72 */     Dimension textureDim = new Dimension(textureSize[0], textureSize[1]);
/*     */     
/*  74 */     boolean usePlayerTexture = Json.getBoolean(obj, "usePlayerTexture", false);
/*     */     
/*  76 */     JsonArray models = (JsonArray)obj.get("models");
/*  77 */     checkNull(models, "Missing elements");
/*     */     
/*  79 */     Map<Object, Object> mapModelJsons = new HashMap<Object, Object>();
/*     */     
/*  81 */     List listModels = new ArrayList();
/*  82 */     List listAttachTos = new ArrayList();
/*  83 */     int i = 0; while (true) { JsonObject elem; if (i < models.size())
/*     */       
/*  85 */       { elem = (JsonObject)models.get(i);
/*     */         
/*  87 */         String baseId = Json.getString(elem, "baseId");
/*  88 */         if (baseId != null)
/*     */         
/*  90 */         { JsonObject baseObj = (JsonObject)mapModelJsons.get(baseId);
/*  91 */           if (baseObj == null)
/*     */           
/*  93 */           { Config.warn("BaseID not found: " + baseId); }
/*     */           
/*     */           else
/*     */           
/*  97 */           { Set<Map.Entry<String, JsonElement>> setEntries = baseObj.entrySet();
/*  98 */             for (Iterator<Map.Entry<String, JsonElement>> iterator = setEntries.iterator(); iterator.hasNext(); ) {
/*     */               
/* 100 */               Map.Entry<String, JsonElement> entry = iterator.next();
/* 101 */               if (!elem.has(entry.getKey())) {
/* 102 */                 elem.add(entry.getKey(), entry.getValue());
/*     */               }
/*     */             } 
/*     */             
/* 106 */             String id = Json.getString(elem, "id"); }  continue; }  } else { break; }  String str = Json.getString(elem, "id");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       i++; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     PlayerItemRenderer[] modelRenderers = (PlayerItemRenderer[])listModels.toArray((Object[])new PlayerItemRenderer[listModels.size()]);
/*     */     
/* 124 */     return new PlayerItemModel(textureDim, usePlayerTexture, modelRenderers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkNull(Object obj, String msg) {
/* 133 */     if (obj == null) {
/* 134 */       throw new JsonParseException(msg);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static bqx makeResourceLocation(String texture) {
/* 143 */     int pos = texture.indexOf(':');
/* 144 */     if (pos < 0) {
/* 145 */       return new bqx(texture);
/*     */     }
/* 147 */     String domain = texture.substring(0, pos);
/* 148 */     String path = texture.substring(pos + 1);
/* 149 */     return new bqx(domain, path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int parseAttachModel(String attachModelStr) {
/* 158 */     String str = attachModelStr;
/* 159 */     if (str == null) {
/* 160 */       return 0;
/*     */     }
/* 162 */     if (str.equals("body")) {
/* 163 */       return 0;
/*     */     }
/* 165 */     if (str.equals("head")) {
/* 166 */       return 1;
/*     */     }
/* 168 */     if (str.equals("leftArm")) {
/* 169 */       return 2;
/*     */     }
/* 171 */     if (str.equals("rightArm")) {
/* 172 */       return 3;
/*     */     }
/* 174 */     if (str.equals("leftLeg")) {
/* 175 */       return 4;
/*     */     }
/* 177 */     if (str.equals("rightLeg")) {
/* 178 */       return 5;
/*     */     }
/* 180 */     if (str.equals("cape")) {
/* 181 */       return 6;
/*     */     }
/* 183 */     Config.warn("Unknown attachModel: " + str);
/* 184 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static PlayerItemRenderer parseItemRenderer(JsonObject elem, Dimension textureDim) {
/* 194 */     String type = Json.getString(elem, "type");
/*     */     
/* 196 */     if (!Config.equals(type, "ModelBox")) {
/*     */ 
/*     */       
/* 199 */       Config.warn("Unknown model type: " + type);
/* 200 */       return null;
/*     */     } 
/*     */     
/* 203 */     String attachToStr = Json.getString(elem, "attachTo");
/* 204 */     int attachTo = parseAttachModel(attachToStr);
/*     */     
/* 206 */     float scale = Json.getFloat(elem, "scale", 1.0F);
/*     */     
/* 208 */     bhr modelBase = new ModelPlayerItem();
/* 209 */     modelBase.t = textureDim.width;
/* 210 */     modelBase.u = textureDim.height;
/*     */     
/* 212 */     bix mr = parseModelRenderer(elem, modelBase);
/*     */     
/* 214 */     PlayerItemRenderer pir = new PlayerItemRenderer(attachTo, scale, mr);
/*     */     
/* 216 */     return pir;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static bix parseModelRenderer(JsonObject elem, bhr modelBase) {
/* 225 */     bix mr = new bix(modelBase);
/*     */     
/* 227 */     String invertAxis = Json.getString(elem, "invertAxis", "").toLowerCase();
/* 228 */     boolean invertX = invertAxis.contains("x");
/* 229 */     boolean invertY = invertAxis.contains("y");
/* 230 */     boolean invertZ = invertAxis.contains("z");
/*     */     
/* 232 */     float[] translate = Json.parseFloatArray(elem.get("translate"), 3, new float[3]);
/* 233 */     if (invertX)
/* 234 */       translate[0] = -translate[0]; 
/* 235 */     if (invertY)
/* 236 */       translate[1] = -translate[1]; 
/* 237 */     if (invertZ) {
/* 238 */       translate[2] = -translate[2];
/*     */     }
/* 240 */     float[] rotateAngles = Json.parseFloatArray(elem.get("rotate"), 3, new float[3]);
/* 241 */     for (int i = 0; i < rotateAngles.length; i++)
/*     */     {
/*     */       
/* 244 */       rotateAngles[i] = rotateAngles[i] / 180.0F * 3.1415927F;
/*     */     }
/* 246 */     if (invertX)
/* 247 */       rotateAngles[0] = -rotateAngles[0]; 
/* 248 */     if (invertY)
/* 249 */       rotateAngles[1] = -rotateAngles[1]; 
/* 250 */     if (invertZ) {
/* 251 */       rotateAngles[2] = -rotateAngles[2];
/*     */     }
/* 253 */     mr.a(translate[0], translate[1], translate[2]);
/*     */     
/* 255 */     mr.f = rotateAngles[0];
/* 256 */     mr.g = rotateAngles[1];
/* 257 */     mr.h = rotateAngles[2];
/*     */     
/* 259 */     String mirrorTexture = Json.getString(elem, "mirrorTexture", "").toLowerCase();
/* 260 */     boolean invertU = mirrorTexture.contains("u");
/* 261 */     boolean invertV = mirrorTexture.contains("v");
/* 262 */     if (invertU)
/* 263 */       mr.i = true; 
/* 264 */     if (invertV) {
/* 265 */       mr.mirrorV = true;
/*     */     }
/* 267 */     JsonArray boxes = elem.getAsJsonArray("boxes");
/*     */     
/* 269 */     if (boxes != null)
/*     */     {
/* 271 */       for (int j = 0; j < boxes.size(); j++) {
/*     */         
/* 273 */         JsonObject box = boxes.get(j).getAsJsonObject();
/*     */         
/* 275 */         int[] textureOffset = Json.parseIntArray(box.get("textureOffset"), 2);
/* 276 */         if (textureOffset == null) {
/* 277 */           throw new JsonParseException("Texture offset not specified");
/*     */         }
/* 279 */         float[] coordinates = Json.parseFloatArray(box.get("coordinates"), 6);
/* 280 */         if (coordinates == null) {
/* 281 */           throw new JsonParseException("Coordinates not specified");
/*     */         }
/* 283 */         if (invertX)
/* 284 */           coordinates[0] = -coordinates[0] - coordinates[3]; 
/* 285 */         if (invertY)
/* 286 */           coordinates[1] = -coordinates[1] - coordinates[4]; 
/* 287 */         if (invertZ) {
/* 288 */           coordinates[2] = -coordinates[2] - coordinates[5];
/*     */         }
/* 290 */         float sizeAdd = Json.getFloat(box, "sizeAdd", 0.0F);
/*     */         
/* 292 */         mr.a(textureOffset[0], textureOffset[1]);
/*     */         
/* 294 */         mr.a(coordinates[0], coordinates[1], coordinates[2], (int)coordinates[3], (int)coordinates[4], (int)coordinates[5], sizeAdd);
/*     */       } 
/*     */     }
/*     */     
/* 298 */     JsonArray sprites = elem.getAsJsonArray("sprites");
/*     */     
/* 300 */     if (sprites != null)
/*     */     {
/* 302 */       for (int j = 0; j < sprites.size(); j++) {
/*     */         
/* 304 */         JsonObject sprite = sprites.get(j).getAsJsonObject();
/*     */         
/* 306 */         int[] textureOffset = Json.parseIntArray(sprite.get("textureOffset"), 2);
/* 307 */         if (textureOffset == null) {
/* 308 */           throw new JsonParseException("Texture offset not specified");
/*     */         }
/* 310 */         float[] coordinates = Json.parseFloatArray(sprite.get("coordinates"), 6);
/* 311 */         if (coordinates == null) {
/* 312 */           throw new JsonParseException("Coordinates not specified");
/*     */         }
/* 314 */         if (invertX)
/* 315 */           coordinates[0] = -coordinates[0] - coordinates[3]; 
/* 316 */         if (invertY)
/* 317 */           coordinates[1] = -coordinates[1] - coordinates[4]; 
/* 318 */         if (invertZ) {
/* 319 */           coordinates[2] = -coordinates[2] - coordinates[5];
/*     */         }
/* 321 */         float sizeAdd = Json.getFloat(sprite, "sizeAdd", 0.0F);
/*     */         
/* 323 */         mr.a(textureOffset[0], textureOffset[1]);
/*     */         
/* 325 */         mr.addSprite(coordinates[0], coordinates[1], coordinates[2], (int)coordinates[3], (int)coordinates[4], (int)coordinates[5], sizeAdd);
/*     */       } 
/*     */     }
/*     */     
/* 329 */     JsonObject submodel = (JsonObject)elem.get("submodel");
/* 330 */     if (submodel != null) {
/*     */       
/* 332 */       bix subMr = parseModelRenderer(submodel, modelBase);
/* 333 */       mr.a(subMr);
/*     */     } 
/*     */     
/* 336 */     JsonArray submodels = (JsonArray)elem.get("submodels");
/* 337 */     if (submodels != null)
/*     */     {
/* 339 */       for (int j = 0; j < submodels.size(); j++) {
/*     */         
/* 341 */         JsonObject sm = (JsonObject)submodels.get(j);
/* 342 */         bix subMr = parseModelRenderer(sm, modelBase);
/* 343 */         mr.a(subMr);
/*     */       } 
/*     */     }
/*     */     
/* 347 */     return mr;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\PlayerItemParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */