/*    */ package optifine.xdelta;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.RandomAccessFile;
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
/*    */ public class RandomAccessFileSeekableSource
/*    */   implements SeekableSource
/*    */ {
/*    */   RandomAccessFile raf;
/*    */   
/*    */   public RandomAccessFileSeekableSource(RandomAccessFile raf) {
/* 41 */     this.raf = raf;
/*    */   }
/*    */   public void seek(long pos) throws IOException {
/* 44 */     this.raf.seek(pos);
/*    */   }
/*    */   
/*    */   public int read(byte[] b, int off, int len) throws IOException {
/* 48 */     return this.raf.read(b, off, len);
/*    */   }
/*    */   public long length() throws IOException {
/* 51 */     return this.raf.length();
/*    */   }
/*    */   
/*    */   public void close() throws IOException {
/* 55 */     this.raf.close();
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\xdelta\RandomAccessFileSeekableSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */