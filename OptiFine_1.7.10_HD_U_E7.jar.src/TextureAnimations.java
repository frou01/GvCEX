/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
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
/*     */ public class TextureAnimations
/*     */ {
/*  29 */   private static TextureAnimation[] textureAnimations = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void reset() {
/*  35 */     textureAnimations = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void update() {
/*  43 */     textureAnimations = null;
/*     */     
/*  45 */     bra[] rps = Config.getResourcePacks();
/*  46 */     textureAnimations = getTextureAnimations(rps);
/*     */     
/*  48 */     if (Config.isAnimatedTextures()) {
/*  49 */       updateAnimations();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateCustomAnimations() {
/*  56 */     if (textureAnimations == null)
/*     */       return; 
/*  58 */     if (!Config.isAnimatedTextures()) {
/*     */       return;
/*     */     }
/*  61 */     updateAnimations();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateAnimations() {
/*  69 */     if (textureAnimations == null) {
/*     */       return;
/*     */     }
/*  72 */     for (int i = 0; i < textureAnimations.length; i++) {
/*     */       
/*  74 */       TextureAnimation anim = textureAnimations[i];
/*     */       
/*  76 */       anim.updateTexture();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static TextureAnimation[] getTextureAnimations(bra[] rps) {
/*  82 */     List list = new ArrayList();
/*  83 */     for (int i = 0; i < rps.length; i++) {
/*     */       
/*  85 */       bra rp = rps[i];
/*  86 */       TextureAnimation[] tas = getTextureAnimations(rp);
/*  87 */       if (tas != null)
/*  88 */         list.addAll(Arrays.asList(tas)); 
/*     */     } 
/*  90 */     TextureAnimation[] anims = (TextureAnimation[])list.toArray((Object[])new TextureAnimation[list.size()]);
/*  91 */     return anims;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TextureAnimation[] getTextureAnimations(bra rp) {
/*  98 */     String[] animPropNames = ResUtils.collectFiles(rp, "mcpatcher/anim/", ".properties", (String[])null);
/*     */     
/* 100 */     if (animPropNames.length <= 0) {
/* 101 */       return null;
/*     */     }
/* 103 */     List<TextureAnimation> list = new ArrayList();
/* 104 */     for (int i = 0; i < animPropNames.length; i++) {
/*     */       
/* 106 */       String propName = animPropNames[i];
/* 107 */       Config.dbg("Texture animation: " + propName);
/*     */       
/*     */       try {
/* 110 */         bqx propLoc = new bqx(propName);
/* 111 */         InputStream in = rp.a(propLoc);
/*     */         
/* 113 */         Properties props = new Properties();
/* 114 */         props.load(in);
/*     */         
/* 116 */         TextureAnimation anim = makeTextureAnimation(props, propLoc);
/*     */         
/* 118 */         if (anim != null) {
/*     */ 
/*     */           
/* 121 */           bqx locDstTex = new bqx(anim.getDstTex());
/* 122 */           if (Config.getDefiningResourcePack(locDstTex) != rp)
/*     */           
/* 124 */           { Config.dbg("Skipped: " + propName + ", target texture not loaded from same resource pack"); }
/*     */           
/*     */           else
/*     */           
/* 128 */           { list.add(anim); } 
/*     */         } 
/* 130 */       } catch (FileNotFoundException e) {
/*     */         
/* 132 */         Config.warn("File not found: " + e.getMessage());
/*     */       }
/* 134 */       catch (IOException e) {
/*     */         
/* 136 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/* 139 */     TextureAnimation[] anims = list.<TextureAnimation>toArray(new TextureAnimation[list.size()]);
/* 140 */     return anims;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TextureAnimation makeTextureAnimation(Properties props, bqx propLoc) {
/* 150 */     String texFrom = props.getProperty("from");
/* 151 */     String texTo = props.getProperty("to");
/* 152 */     int x = Config.parseInt(props.getProperty("x"), -1);
/* 153 */     int y = Config.parseInt(props.getProperty("y"), -1);
/* 154 */     int width = Config.parseInt(props.getProperty("w"), -1);
/* 155 */     int height = Config.parseInt(props.getProperty("h"), -1);
/*     */     
/* 157 */     if (texFrom == null || texTo == null) {
/*     */       
/* 159 */       Config.warn("TextureAnimation: Source or target texture not specified");
/* 160 */       return null;
/*     */     } 
/* 162 */     if (x < 0 || y < 0 || width < 0 || height < 0) {
/*     */       
/* 164 */       Config.warn("TextureAnimation: Invalid coordinates");
/* 165 */       return null;
/*     */     } 
/*     */     
/* 168 */     texFrom = texFrom.trim();
/* 169 */     texTo = texTo.trim();
/*     */     
/* 171 */     String basePath = TextureUtils.getBasePath(propLoc.a());
/* 172 */     texFrom = TextureUtils.fixResourcePath(texFrom, basePath);
/* 173 */     texTo = TextureUtils.fixResourcePath(texTo, basePath);
/*     */     
/* 175 */     byte[] imageBytes = getCustomTextureData(texFrom, width);
/* 176 */     if (imageBytes == null) {
/*     */       
/* 178 */       Config.warn("TextureAnimation: Source texture not found: " + texTo);
/* 179 */       return null;
/*     */     } 
/*     */     
/* 182 */     int countPixels = imageBytes.length / 4;
/* 183 */     int countFrames = countPixels / width * height;
/* 184 */     int countPixelsAllFrames = countFrames * width * height;
/* 185 */     if (countPixels != countPixelsAllFrames) {
/*     */       
/* 187 */       Config.warn("TextureAnimation: Source texture has invalid number of frames: " + texFrom + ", frames: " + (countPixels / (width * height)));
/* 188 */       return null;
/*     */     } 
/*     */     
/* 191 */     bqx locTexTo = new bqx(texTo);
/*     */ 
/*     */     
/*     */     try {
/* 195 */       InputStream inTexTo = Config.getResourceStream(locTexTo);
/* 196 */       if (inTexTo == null) {
/*     */         
/* 198 */         Config.warn("TextureAnimation: Target texture not found: " + texTo);
/* 199 */         return null;
/*     */       } 
/* 201 */       BufferedImage imgTexTo = readTextureImage(inTexTo);
/*     */       
/* 203 */       if (x + width > imgTexTo.getWidth() || y + height > imgTexTo.getHeight()) {
/*     */         
/* 205 */         Config.warn("TextureAnimation: Animation coordinates are outside the target texture: " + texTo);
/* 206 */         return null;
/*     */       } 
/*     */       
/* 209 */       TextureAnimation anim = new TextureAnimation(texFrom, imageBytes, texTo, locTexTo, x, y, width, height, props, 1);
/*     */       
/* 211 */       return anim;
/*     */     }
/* 213 */     catch (IOException e) {
/*     */       
/* 215 */       Config.warn("TextureAnimation: Target texture not found: " + texTo);
/* 216 */       return null;
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
/*     */   public static byte[] getCustomTextureData(String imagePath, int tileWidth) {
/* 228 */     byte[] imageBytes = loadImage(imagePath, tileWidth);
/*     */     
/* 230 */     if (imageBytes == null) {
/* 231 */       imageBytes = loadImage("/anim" + imagePath, tileWidth);
/*     */     }
/* 233 */     return imageBytes;
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
/*     */   
/*     */   private static byte[] loadImage(String name, int targetWidth) {
/* 246 */     bbj options = Config.getGameSettings();
/*     */     
/*     */     try {
/* 249 */       bqx locRes = new bqx(name);
/* 250 */       InputStream in = Config.getResourceStream(locRes);
/* 251 */       if (in == null)
/* 252 */         return null; 
/* 253 */       BufferedImage image = readTextureImage(in);
/* 254 */       in.close();
/* 255 */       if (image == null)
/* 256 */         return null; 
/* 257 */       if (targetWidth > 0 && image.getWidth() != targetWidth) {
/*     */         
/* 259 */         double aspectHW = (image.getHeight() / image.getWidth());
/* 260 */         int targetHeight = (int)(targetWidth * aspectHW);
/* 261 */         image = scaleBufferedImage(image, targetWidth, targetHeight);
/*     */       } 
/*     */       
/* 264 */       int width = image.getWidth();
/* 265 */       int height = image.getHeight();
/* 266 */       int[] ai = new int[width * height];
/* 267 */       byte[] byteBuf = new byte[width * height * 4];
/* 268 */       image.getRGB(0, 0, width, height, ai, 0, width);
/* 269 */       for (int l = 0; l < ai.length; l++) {
/*     */         
/* 271 */         int alpha = ai[l] >> 24 & 0xFF;
/* 272 */         int red = ai[l] >> 16 & 0xFF;
/* 273 */         int green = ai[l] >> 8 & 0xFF;
/* 274 */         int blue = ai[l] & 0xFF;
/*     */         
/* 276 */         if (options != null && options.e) {
/*     */           
/* 278 */           int j3 = (red * 30 + green * 59 + blue * 11) / 100;
/* 279 */           int l3 = (red * 30 + green * 70) / 100;
/* 280 */           int j4 = (red * 30 + blue * 70) / 100;
/* 281 */           red = j3;
/* 282 */           green = l3;
/* 283 */           blue = j4;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 294 */         byteBuf[l * 4 + 0] = (byte)red;
/* 295 */         byteBuf[l * 4 + 1] = (byte)green;
/* 296 */         byteBuf[l * 4 + 2] = (byte)blue;
/* 297 */         byteBuf[l * 4 + 3] = (byte)alpha;
/*     */       } 
/* 299 */       return byteBuf;
/*     */     }
/* 301 */     catch (FileNotFoundException e) {
/*     */ 
/*     */       
/* 304 */       return null;
/*     */     }
/* 306 */     catch (Exception e) {
/*     */       
/* 308 */       e.printStackTrace();
/* 309 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static BufferedImage readTextureImage(InputStream par1InputStream) throws IOException {
/* 318 */     BufferedImage var2 = ImageIO.read(par1InputStream);
/* 319 */     par1InputStream.close();
/* 320 */     return var2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage scaleBufferedImage(BufferedImage image, int width, int height) {
/* 331 */     BufferedImage scaledImage = new BufferedImage(width, height, 2);
/* 332 */     Graphics2D gr = scaledImage.createGraphics();
/*     */     
/* 334 */     gr.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/* 335 */     gr.drawImage(image, 0, 0, width, height, null);
/* 336 */     return scaledImage;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\TextureAnimations.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */