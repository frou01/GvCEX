/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NaturalProperties
/*    */ {
/* 12 */   public int rotation = 1;
/*    */ 
/*    */   
/*    */   public boolean flip = false;
/*    */ 
/*    */ 
/*    */   
/*    */   public NaturalProperties(String type) {
/* 20 */     if (type.equals("4")) {
/*    */       
/* 22 */       this.rotation = 4;
/*    */       return;
/*    */     } 
/* 25 */     if (type.equals("2")) {
/*    */       
/* 27 */       this.rotation = 2;
/*    */       return;
/*    */     } 
/* 30 */     if (type.equals("F")) {
/*    */       
/* 32 */       this.flip = true;
/*    */       return;
/*    */     } 
/* 35 */     if (type.equals("4F")) {
/*    */       
/* 37 */       this.rotation = 4;
/* 38 */       this.flip = true;
/*    */       return;
/*    */     } 
/* 41 */     if (type.equals("2F")) {
/*    */       
/* 43 */       this.rotation = 2;
/* 44 */       this.flip = true;
/*    */       
/*    */       return;
/*    */     } 
/* 48 */     Config.warn("NaturalTextures: Unknown type: " + type);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isValid() {
/* 54 */     if (this.rotation == 2 || this.rotation == 4)
/* 55 */       return true; 
/* 56 */     if (this.flip) {
/* 57 */       return true;
/*    */     }
/* 59 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\NaturalProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */