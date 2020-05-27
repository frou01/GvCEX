/*    */ import java.awt.Graphics;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.awt.image.ImageObserver;
/*    */ import org.apache.commons.io.FilenameUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CapeUtils
/*    */ {
/*    */   public static void downloadCape(blg player) {
/* 27 */     String username = player.getNameClear();
/*    */     
/* 29 */     if (username != null && !username.isEmpty() && !username.contains("\000")) {
/*    */       
/* 31 */       String ofCapeUrl = "http://s.optifine.net/capes/" + username + ".png";
/*    */       
/* 33 */       String mptHash = FilenameUtils.getBaseName(ofCapeUrl);
/* 34 */       bqx rl = new bqx("capeof/" + mptHash);
/* 35 */       bqf textureManager = bao.B().P();
/*    */       
/* 37 */       bqh tex = textureManager.b(rl);
/* 38 */       if (tex != null)
/*    */       {
/*    */         
/* 41 */         if (tex instanceof bpr) {
/*    */           
/* 43 */           bpr tdid = (bpr)tex;
/*    */           
/* 45 */           if (tdid.imageFound != null) {
/*    */ 
/*    */             
/* 48 */             if (tdid.imageFound.booleanValue())
/*    */             {
/*    */               
/* 51 */               player.setLocationOfCape(rl);
/*    */             }
/*    */             
/*    */             return;
/*    */           } 
/*    */         } 
/*    */       }
/*    */       
/* 59 */       CapeImageBuffer cib = new CapeImageBuffer(player, rl);
/*    */       
/* 61 */       bpr textureCape = new bpr(null, ofCapeUrl, null, (blx)cib);
/* 62 */       textureCape.pipeline = true;
/* 63 */       textureManager.a(rl, (bqh)textureCape);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static BufferedImage parseCape(BufferedImage img) {
/* 71 */     int imageWidth = 64;
/* 72 */     int imageHeight = 32;
/*    */     
/* 74 */     BufferedImage srcImg = img;
/* 75 */     int srcWidth = srcImg.getWidth();
/* 76 */     int srcHeight = srcImg.getHeight();
/* 77 */     while (imageWidth < srcWidth || imageHeight < srcHeight) {
/*    */       
/* 79 */       imageWidth *= 2;
/* 80 */       imageHeight *= 2;
/*    */     } 
/*    */ 
/*    */     
/* 84 */     BufferedImage imgNew = new BufferedImage(imageWidth, imageHeight, 2);
/* 85 */     Graphics g = imgNew.getGraphics();
/* 86 */     g.drawImage(img, 0, 0, (ImageObserver)null);
/* 87 */     g.dispose();
/* 88 */     return imgNew;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\CapeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */