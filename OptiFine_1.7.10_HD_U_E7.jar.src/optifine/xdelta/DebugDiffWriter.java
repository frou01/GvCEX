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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DebugDiffWriter
/*    */   implements DiffWriter
/*    */ {
/* 42 */   byte[] buf = new byte[256]; int buflen = 0;
/*    */ 
/*    */ 
/*    */   
/*    */   public void addCopy(int offset, int length) throws IOException {
/* 47 */     if (this.buflen > 0)
/* 48 */       writeBuf(); 
/* 49 */     System.err.println("COPY off: " + offset + ", len: " + length);
/*    */   }
/*    */   
/*    */   public void addData(byte b) throws IOException {
/* 53 */     if (this.buflen < 256) {
/* 54 */       this.buf[this.buflen++] = b;
/*    */     } else {
/* 56 */       writeBuf();
/*    */     } 
/*    */   } private void writeBuf() {
/* 59 */     System.err.print("DATA: ");
/* 60 */     for (int ix = 0; ix < this.buflen; ix++) {
/* 61 */       if (this.buf[ix] == 10) {
/* 62 */         System.err.print("\\n");
/*    */       } else {
/* 64 */         System.err.print(String.valueOf((char)this.buf[ix]));
/*    */       } 
/*    */     } 
/* 67 */     System.err.println("");
/* 68 */     this.buflen = 0;
/*    */   }
/*    */   
/*    */   public void flush() throws IOException {}
/*    */   
/*    */   public void close() throws IOException {}
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\xdelta\DebugDiffWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */