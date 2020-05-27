/*     */ package shadersmod.client;
/*     */ 
/*     */ import TextureUtils;
/*     */ import bpp;
/*     */ import bqi;
/*     */ import bqy;
/*     */ import brv;
/*     */ import brw;
/*     */ import bsa;
/*     */ import bsb;
/*     */ import bsc;
/*     */ import bsd;
/*     */ import bse;
/*     */ import bsf;
/*     */ import bsg;
/*     */ import bsh;
/*     */ import bsi;
/*     */ import bsj;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import shadersmod.common.SMCLog;
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
/*     */ public class SimpleShaderTexture
/*     */   extends bpp
/*     */ {
/*     */   private String texturePath;
/*  44 */   private static final brw METADATA_SERIALIZER = makeMetadataSerializer();
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleShaderTexture(String texturePath) {
/*  49 */     this.texturePath = texturePath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(bqy resourceManager) throws IOException {
/*  57 */     c();
/*     */     
/*  59 */     InputStream inputStream = Shaders.getShaderPackResourceStream(this.texturePath);
/*     */     
/*  61 */     if (inputStream == null) {
/*  62 */       throw new FileNotFoundException("Shader texture not found: " + this.texturePath);
/*     */     }
/*     */     
/*     */     try {
/*  66 */       BufferedImage bufferedimage = TextureUtils.readBufferedImage(inputStream);
/*     */       
/*  68 */       bsi tms = loadTextureMetadataSection();
/*     */       
/*  70 */       bqi.a(b(), bufferedimage, tms.a(), tms.b());
/*     */     }
/*     */     finally {
/*     */       
/*  74 */       IOUtils.closeQuietly(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private bsi loadTextureMetadataSection() {
/*  83 */     String pathMeta = this.texturePath + ".mcmeta";
/*  84 */     String sectionName = "texture";
/*  85 */     InputStream inMeta = Shaders.getShaderPackResourceStream(pathMeta);
/*     */     
/*  87 */     if (inMeta != null) {
/*     */       
/*  89 */       brw ms = METADATA_SERIALIZER;
/*  90 */       BufferedReader brMeta = new BufferedReader(new InputStreamReader(inMeta));
/*     */       
/*     */       try {
/*  93 */         JsonObject jsonMeta = (new JsonParser()).parse(brMeta).getAsJsonObject();
/*  94 */         bsi meta = (bsi)ms.a(sectionName, jsonMeta);
/*  95 */         if (meta != null) {
/*  96 */           return meta;
/*     */         }
/*  98 */       } catch (RuntimeException re) {
/*     */         
/* 100 */         SMCLog.warning("Error reading metadata: " + pathMeta);
/* 101 */         SMCLog.warning("" + re.getClass().getName() + ": " + re.getMessage());
/*     */       }
/*     */       finally {
/*     */         
/* 105 */         IOUtils.closeQuietly(brMeta);
/* 106 */         IOUtils.closeQuietly(inMeta);
/*     */       } 
/*     */     } 
/*     */     
/* 110 */     return new bsi(false, false, new ArrayList());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static brw makeMetadataSerializer() {
/* 117 */     brw ms = new brw();
/* 118 */     ms.a((brv)new bsj(), bsi.class);
/* 119 */     ms.a((brv)new bsd(), bsc.class);
/* 120 */     ms.a((brv)new bsb(), bsa.class);
/* 121 */     ms.a((brv)new bsh(), bsg.class);
/* 122 */     ms.a((brv)new bsf(), bse.class);
/* 123 */     return ms;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\SimpleShaderTexture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */