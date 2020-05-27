/*    */ package optifine;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.zip.ZipEntry;
/*    */ import java.util.zip.ZipFile;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZipResourceProvider
/*    */   implements IResourceProvider
/*    */ {
/* 15 */   private ZipFile zipFile = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ZipResourceProvider(ZipFile zipFile) {
/* 23 */     this.zipFile = zipFile;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InputStream getResourceStream(String path) throws IOException {
/* 30 */     path = Utils.removePrefix(path, "/");
/*    */     
/* 32 */     ZipEntry zipEntry = this.zipFile.getEntry(path);
/* 33 */     if (zipEntry == null) {
/* 34 */       return null;
/*    */     }
/* 36 */     InputStream in = this.zipFile.getInputStream(zipEntry);
/*    */     
/* 38 */     return in;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\ZipResourceProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */