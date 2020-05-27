/*    */ package shadersmod.client;
/*    */ 
/*    */ import StrUtils;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShaderPackFolder
/*    */   implements IShaderPack
/*    */ {
/*    */   protected File packFile;
/*    */   
/*    */   public ShaderPackFolder(String name, File file) {
/* 17 */     this.packFile = file;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InputStream getResourceAsStream(String resName) {
/*    */     try {
/* 31 */       String name = StrUtils.removePrefixSuffix(resName, "/", "/");
/* 32 */       File resFile = new File(this.packFile, name);
/*    */       
/* 34 */       if (!resFile.exists()) {
/* 35 */         return null;
/*    */       }
/* 37 */       return new BufferedInputStream(new FileInputStream(resFile));
/*    */     }
/* 39 */     catch (Exception excp) {
/*    */       
/* 41 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasDirectory(String name) {
/* 51 */     File resFile = new File(this.packFile, name.substring(1));
/* 52 */     if (!resFile.exists())
/* 53 */       return false; 
/* 54 */     if (!resFile.isDirectory()) {
/* 55 */       return false;
/*    */     }
/* 57 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 66 */     return this.packFile.getName();
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\ShaderPackFolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */