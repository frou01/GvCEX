/*    */ package optifine.xdelta;
/*    */ 
/*    */ import java.io.IOException;
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
/*    */ public class ByteArraySeekableSource
/*    */   implements SeekableSource
/*    */ {
/*    */   byte[] source;
/* 37 */   long lastPos = 0L;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteArraySeekableSource(byte[] source) {
/* 43 */     this.source = source;
/*    */   }
/*    */   public void seek(long pos) throws IOException {
/* 46 */     this.lastPos = pos;
/*    */   }
/*    */   
/*    */   public int read(byte[] b, int off, int len) throws IOException {
/* 50 */     int maxLength = this.source.length - (int)this.lastPos;
/* 51 */     if (maxLength <= 0) {
/* 52 */       return -1;
/*    */     }
/* 54 */     if (maxLength < len) {
/* 55 */       len = maxLength;
/*    */     }
/* 57 */     System.arraycopy(this.source, (int)this.lastPos, b, off, len);
/* 58 */     this.lastPos += len;
/* 59 */     return len;
/*    */   }
/*    */   public long length() throws IOException {
/* 62 */     return this.source.length;
/*    */   }
/*    */   public void close() throws IOException {
/* 65 */     this.source = null;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\xdelta\ByteArraySeekableSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */