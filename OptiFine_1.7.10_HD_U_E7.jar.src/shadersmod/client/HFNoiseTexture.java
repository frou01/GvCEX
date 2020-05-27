/*     */ package shadersmod.client;
/*     */ 
/*     */ import GlStateManager;
/*     */ import java.nio.ByteBuffer;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
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
/*     */ public class HFNoiseTexture
/*     */   implements ICustomTexture
/*     */ {
/*  23 */   private int texID = GL11.glGenTextures();
/*  24 */   private int textureUnit = 15;
/*     */   public HFNoiseTexture(int width, int height) {
/*  26 */     byte[] image = genHFNoiseImage(width, height);
/*  27 */     ByteBuffer data = BufferUtils.createByteBuffer(image.length);
/*  28 */     data.put(image);
/*  29 */     data.flip();
/*     */ 
/*     */     
/*  32 */     GlStateManager.bindTexture(this.texID);
/*  33 */     GL11.glTexImage2D(3553, 0, 6407, width, height, 0, 6407, 5121, data);
/*  34 */     GL11.glTexParameteri(3553, 10242, 10497);
/*  35 */     GL11.glTexParameteri(3553, 10243, 10497);
/*  36 */     GL11.glTexParameteri(3553, 10240, 9729);
/*  37 */     GL11.glTexParameteri(3553, 10241, 9729);
/*     */     
/*  39 */     GlStateManager.bindTexture(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getID() {
/*  44 */     return this.texID;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteTexture() {
/*  50 */     GlStateManager.deleteTexture(this.texID);
/*  51 */     this.texID = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int random(int seed) {
/*  57 */     seed ^= seed << 13;
/*  58 */     seed ^= seed >> 17;
/*  59 */     seed ^= seed << 5;
/*  60 */     return seed;
/*     */   }
/*     */ 
/*     */   
/*     */   private byte random(int x, int y, int z) {
/*  65 */     int seed = (random(x) + random(y * 19)) * random(z * 23) - z;
/*  66 */     return (byte)(random(seed) % 128);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] genHFNoiseImage(int width, int height) {
/*  75 */     byte[] image = new byte[width * height * 3];
/*  76 */     int index = 0;
/*     */     
/*  78 */     for (int y = 0; y < height; y++) {
/*     */       
/*  80 */       for (int x = 0; x < width; x++) {
/*     */         
/*  82 */         for (int z = 1; z < 4; z++)
/*     */         {
/*  84 */           image[index++] = random(x, y, z);
/*     */         }
/*     */       } 
/*     */     } 
/*  88 */     return image;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextureId() {
/*  94 */     return this.texID;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextureUnit() {
/* 100 */     return this.textureUnit;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\HFNoiseTexture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */