/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import javax.imageio.ImageIO;
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
/*     */ public class PlayerConfigurationParser
/*     */ {
/*  28 */   private String player = null;
/*     */ 
/*     */   
/*     */   public static final String CONFIG_ITEMS = "items";
/*     */ 
/*     */   
/*     */   public static final String ITEM_TYPE = "type";
/*     */   
/*     */   public static final String ITEM_ACTIVE = "active";
/*     */ 
/*     */   
/*     */   public PlayerConfigurationParser(String player) {
/*  40 */     this.player = player;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerConfiguration parsePlayerConfiguration(JsonElement je) {
/*  49 */     if (je == null) {
/*  50 */       throw new JsonParseException("JSON object is null, player: " + this.player);
/*     */     }
/*  52 */     JsonObject jo = (JsonObject)je;
/*     */     
/*  54 */     PlayerConfiguration pc = new PlayerConfiguration();
/*  55 */     JsonArray items = (JsonArray)jo.get("items");
/*  56 */     if (items != null)
/*     */     {
/*  58 */       for (int i = 0; i < items.size(); i++) {
/*     */         
/*  60 */         JsonObject item = (JsonObject)items.get(i);
/*     */         
/*  62 */         boolean active = Json.getBoolean(item, "active", true);
/*  63 */         if (!active) {
/*     */           continue;
/*     */         }
/*  66 */         String type = Json.getString(item, "type");
/*  67 */         if (type == null) {
/*     */           
/*  69 */           Config.warn("Item type is null, player: " + this.player);
/*     */           
/*     */           continue;
/*     */         } 
/*  73 */         String modelPath = Json.getString(item, "model");
/*     */         
/*  75 */         if (modelPath == null) {
/*  76 */           modelPath = "items/" + type + "/model.cfg";
/*     */         }
/*  78 */         PlayerItemModel model = downloadModel(modelPath);
/*     */         
/*  80 */         if (model == null) {
/*     */           continue;
/*     */         }
/*  83 */         if (!model.isUsePlayerTexture()) {
/*     */           
/*  85 */           String texturePath = Json.getString(item, "texture");
/*     */           
/*  87 */           if (texturePath == null) {
/*  88 */             texturePath = "items/" + type + "/users/" + this.player + ".png";
/*     */           }
/*  90 */           BufferedImage image = downloadTextureImage(texturePath);
/*     */           
/*  92 */           if (image == null) {
/*     */             continue;
/*     */           }
/*  95 */           model.setTextureImage(image);
/*     */           
/*  97 */           bqx loc = new bqx("optifine.net", texturePath);
/*  98 */           model.setTextureLocation(loc);
/*     */         } 
/*     */         
/* 101 */         pc.addPlayerItemModel(model);
/*     */         continue;
/*     */       } 
/*     */     }
/* 105 */     return pc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BufferedImage downloadTextureImage(String texturePath) {
/* 116 */     String textureUrl = HttpUtils.getPlayerItemsUrl() + "/" + texturePath;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 121 */       byte[] body = HttpPipeline.get(textureUrl, bao.B().O());
/*     */       
/* 123 */       BufferedImage image = ImageIO.read(new ByteArrayInputStream(body));
/*     */       
/* 125 */       return image;
/*     */     }
/* 127 */     catch (IOException e) {
/*     */       
/* 129 */       Config.warn("Error loading item texture " + texturePath + ": " + e.getClass().getName() + ": " + e.getMessage());
/* 130 */       return null;
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
/*     */   private PlayerItemModel downloadModel(String modelPath) {
/* 142 */     String modelUrl = HttpUtils.getPlayerItemsUrl() + "/" + modelPath;
/*     */ 
/*     */     
/*     */     try {
/* 146 */       byte[] bytes = HttpPipeline.get(modelUrl, bao.B().O());
/* 147 */       String jsonStr = new String(bytes, "ASCII");
/* 148 */       JsonParser jp = new JsonParser();
/* 149 */       JsonObject jo = (JsonObject)jp.parse(jsonStr);
/* 150 */       PlayerItemParser pip = new PlayerItemParser();
/* 151 */       PlayerItemModel pim = PlayerItemParser.parseItemModel(jo);
/* 152 */       return pim;
/*     */     }
/* 154 */     catch (Exception e) {
/*     */       
/* 156 */       Config.warn("Error loading item model " + modelPath + ": " + e.getClass().getName() + ": " + e.getMessage());
/* 157 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\PlayerConfigurationParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */