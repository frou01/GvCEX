/*    */ package optifine.xdelta;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
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
/*    */ public class SeekableSourceInputStream
/*    */   extends InputStream
/*    */ {
/*    */   SeekableSource ss;
/*    */   
/*    */   public SeekableSourceInputStream(SeekableSource ss) {
/* 40 */     this.ss = ss;
/*    */   }
/*    */   
/*    */   public int read() throws IOException {
/* 44 */     byte[] b = new byte[1];
/* 45 */     this.ss.read(b, 0, 1);
/* 46 */     return b[0];
/*    */   }
/*    */   
/*    */   public int read(byte[] b, int off, int len) throws IOException {
/* 50 */     return this.ss.read(b, off, len);
/*    */   }
/*    */   
/*    */   public void close() throws IOException {
/* 54 */     this.ss.close();
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\xdelta\SeekableSourceInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */