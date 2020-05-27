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
/*    */ public class GlVersion
/*    */ {
/*    */   private int major;
/*    */   private int minor;
/*    */   private int release;
/*    */   private String suffix;
/*    */   
/*    */   public GlVersion(int major, int minor) {
/* 20 */     this(major, minor, 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GlVersion(int major, int minor, int release) {
/* 27 */     this(major, minor, release, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GlVersion(int major, int minor, int release, String suffix) {
/* 34 */     this.major = major;
/* 35 */     this.minor = minor;
/* 36 */     this.release = release;
/* 37 */     this.suffix = suffix;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMajor() {
/* 43 */     return this.major;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMinor() {
/* 49 */     return this.minor;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRelease() {
/* 55 */     return this.release;
/*    */   }
/*    */ 
/*    */   
/*    */   public int toInt() {
/* 60 */     if (this.minor > 9) {
/* 61 */       return this.major * 100 + this.minor;
/*    */     }
/* 63 */     if (this.release > 9) {
/* 64 */       return this.major * 100 + this.minor * 10 + 9;
/*    */     }
/* 66 */     return this.major * 100 + this.minor * 10 + this.release;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 75 */     if (this.suffix == null) {
/* 76 */       return "" + this.major + "." + this.minor + "." + this.release;
/*    */     }
/* 78 */     return "" + this.major + "." + this.minor + "." + this.release + this.suffix;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\GlVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */