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
/*     */ public class VillagerProfession
/*     */ {
/*     */   private int profession;
/*     */   private int[] careers;
/*     */   
/*     */   public VillagerProfession(int profession) {
/*  18 */     this(profession, (int[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VillagerProfession(int profession, int career) {
/*  25 */     this(profession, new int[] { career });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VillagerProfession(int profession, int[] careers) {
/*  32 */     this.profession = profession;
/*  33 */     this.careers = careers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(int prof, int car) {
/*  43 */     if (this.profession != prof) {
/*  44 */       return false;
/*     */     }
/*  46 */     if (this.careers != null)
/*     */     {
/*     */       
/*  49 */       if (!Config.equalsOne(car, this.careers)) {
/*  50 */         return false;
/*     */       }
/*     */     }
/*  53 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasCareer(int car) {
/*  60 */     if (this.careers == null) {
/*  61 */       return false;
/*     */     }
/*  63 */     return Config.equalsOne(car, this.careers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addCareer(int car) {
/*  71 */     if (this.careers == null) {
/*     */ 
/*     */       
/*  74 */       this.careers = new int[] { car };
/*     */       
/*  76 */       return true;
/*     */     } 
/*     */     
/*  79 */     if (hasCareer(car)) {
/*  80 */       return false;
/*     */     }
/*  82 */     this.careers = Config.addIntToArray(this.careers, car);
/*     */     
/*  84 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getProfession() {
/*  91 */     return this.profession;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getCareers() {
/*  98 */     return this.careers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 106 */     if (this.careers == null) {
/* 107 */       return "" + this.profession;
/*     */     }
/* 109 */     return "" + this.profession + ":" + Config.arrayToString(this.careers);
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\VillagerProfession.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */