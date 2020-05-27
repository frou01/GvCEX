/*     */ package optifine.xdelta;
/*     */ 
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDiffWriter
/*     */   implements DiffWriter
/*     */ {
/*  42 */   byte[] buf = new byte[256]; int buflen = 0;
/*     */ 
/*     */   
/*     */   protected boolean debug = false;
/*     */   
/*  47 */   DataOutputStream output = null;
/*     */   
/*     */   public GDiffWriter(DataOutputStream os) throws IOException {
/*  50 */     this.output = os;
/*     */     
/*  52 */     this.output.writeByte(209);
/*  53 */     this.output.writeByte(255);
/*  54 */     this.output.writeByte(209);
/*  55 */     this.output.writeByte(255);
/*  56 */     this.output.writeByte(4);
/*     */   }
/*     */   public void setDebug(boolean flag) {
/*  59 */     this.debug = flag;
/*     */   }
/*     */   public void addCopy(int offset, int length) throws IOException {
/*  62 */     if (this.buflen > 0) {
/*  63 */       writeBuf();
/*     */     }
/*     */     
/*  66 */     if (this.debug) {
/*  67 */       System.err.println("COPY off: " + offset + ", len: " + length);
/*     */     }
/*     */ 
/*     */     
/*  71 */     if (offset > Integer.MAX_VALUE) {
/*     */       
/*  73 */       this.output.writeByte(255);
/*     */     }
/*  75 */     else if (offset < 65536) {
/*  76 */       if (length < 256) {
/*     */         
/*  78 */         this.output.writeByte(249);
/*  79 */         this.output.writeShort(offset);
/*  80 */         this.output.writeByte(length);
/*  81 */       } else if (length > 65535) {
/*     */         
/*  83 */         this.output.writeByte(251);
/*  84 */         this.output.writeShort(offset);
/*  85 */         this.output.writeInt(length);
/*     */       } else {
/*     */         
/*  88 */         this.output.writeByte(250);
/*  89 */         this.output.writeShort(offset);
/*  90 */         this.output.writeShort(length);
/*     */       }
/*     */     
/*  93 */     } else if (length < 256) {
/*     */       
/*  95 */       this.output.writeByte(252);
/*  96 */       this.output.writeInt(offset);
/*  97 */       this.output.writeByte(length);
/*  98 */     } else if (length > 65535) {
/*     */       
/* 100 */       this.output.writeByte(254);
/* 101 */       this.output.writeInt(offset);
/* 102 */       this.output.writeInt(length);
/*     */     } else {
/*     */       
/* 105 */       this.output.writeByte(253);
/* 106 */       this.output.writeInt(offset);
/* 107 */       this.output.writeShort(length);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addData(byte b) throws IOException {
/* 113 */     if (this.buflen >= 246)
/* 114 */       writeBuf(); 
/* 115 */     this.buf[this.buflen] = b; this.buflen++;
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeBuf() throws IOException {
/* 120 */     if (this.debug) {
/* 121 */       System.err.print("DATA:");
/* 122 */       for (int ix = 0; ix < this.buflen; ix++) {
/* 123 */         if (this.buf[ix] == 10) {
/* 124 */           System.err.print("\\n");
/*     */         } else {
/* 126 */           System.err.print(String.valueOf((char)this.buf[ix]));
/*     */         } 
/* 128 */       }  System.err.println("");
/*     */     } 
/*     */     
/* 131 */     if (this.buflen > 0) {
/*     */       
/* 133 */       this.output.writeByte(this.buflen);
/* 134 */       this.output.write(this.buf, 0, this.buflen);
/*     */     } 
/* 136 */     this.buflen = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/* 141 */     if (this.buflen > 0)
/* 142 */       writeBuf(); 
/* 143 */     this.buflen = 0;
/* 144 */     this.output.flush();
/*     */   } public void close() throws IOException {
/* 146 */     flush();
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\xdelta\GDiffWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */