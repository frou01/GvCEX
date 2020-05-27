/*    */ package shadersmod.client;
/*    */ 
/*    */ import StrUtils;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.zip.ZipEntry;
/*    */ import java.util.zip.ZipFile;
/*    */ 
/*    */ 
/*    */ public class ShaderPackZip
/*    */   implements IShaderPack
/*    */ {
/*    */   protected File packFile;
/*    */   protected ZipFile packZipFile;
/*    */   
/*    */   public ShaderPackZip(String name, File file) {
/* 18 */     this.packFile = file;
/* 19 */     this.packZipFile = null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() {
/* 25 */     if (this.packZipFile == null) {
/*    */       return;
/*    */     }
/*    */     
/*    */     try {
/* 30 */       this.packZipFile.close();
/*    */     }
/* 32 */     catch (Exception excp) {}
/*    */ 
/*    */ 
/*    */     
/* 36 */     this.packZipFile = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InputStream getResourceAsStream(String resName) {
/*    */     try {
/* 45 */       if (this.packZipFile == null) {
/* 46 */         this.packZipFile = new ZipFile(this.packFile);
/*    */       }
/* 48 */       String name = StrUtils.removePrefix(resName, "/");
/* 49 */       ZipEntry entry = this.packZipFile.getEntry(name);
/* 50 */       if (entry == null) {
/* 51 */         return null;
/*    */       }
/* 53 */       return this.packZipFile.getInputStream(entry);
/*    */     }
/* 55 */     catch (Exception excp) {
/*    */       
/* 57 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasDirectory(String resName) {
/*    */     try {
/* 69 */       if (this.packZipFile == null) {
/* 70 */         this.packZipFile = new ZipFile(this.packFile);
/*    */       }
/*    */       
/* 73 */       String name = StrUtils.removePrefix(resName, "/");
/* 74 */       ZipEntry entry = this.packZipFile.getEntry(name);
/* 75 */       if (entry == null) {
/* 76 */         return false;
/*    */       }
/* 78 */       return true;
/*    */     }
/* 80 */     catch (IOException e) {
/*    */       
/* 82 */       return false;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 92 */     return this.packFile.getName();
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\ShaderPackZip.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */