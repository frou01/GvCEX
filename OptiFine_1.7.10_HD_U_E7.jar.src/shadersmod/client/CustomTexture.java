/*    */ package shadersmod.client;
/*    */ 
/*    */ import bqh;
/*    */ import bqi;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomTexture
/*    */   implements ICustomTexture
/*    */ {
/* 14 */   private int textureUnit = -1;
/* 15 */   private String path = null;
/* 16 */   private bqh texture = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CustomTexture(int textureUnit, String path, bqh texture) {
/* 26 */     this.textureUnit = textureUnit;
/* 27 */     this.path = path;
/* 28 */     this.texture = texture;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTextureUnit() {
/* 36 */     return this.textureUnit;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPath() {
/* 43 */     return this.path;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public bqh getTexture() {
/* 50 */     return this.texture;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTextureId() {
/* 58 */     return this.texture.b();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void deleteTexture() {
/* 66 */     bqi.a(this.texture.b());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 74 */     return "textureUnit: " + this.textureUnit + ", path: " + this.path + ", glTextureId: " + this.texture.b();
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\CustomTexture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */