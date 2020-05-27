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
/*     */ public class SmoothFloat
/*     */ {
/*     */   private float valueLast;
/*     */   private final float timeFadeUpSec;
/*     */   private final float timeFadeDownSec;
/*     */   private long timeLastMs;
/*     */   
/*     */   public SmoothFloat(float valueLast, float timeFadeSec) {
/*  23 */     this(valueLast, timeFadeSec, timeFadeSec);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SmoothFloat(float valueLast, float timeFadeUpSec, float timeFadeDownSec) {
/*  30 */     this.valueLast = valueLast;
/*  31 */     this.timeFadeUpSec = timeFadeUpSec;
/*  32 */     this.timeFadeDownSec = timeFadeDownSec;
/*  33 */     this.timeLastMs = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getValueLast() {
/*  40 */     return this.valueLast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getTimeFadeUpSec() {
/*  47 */     return this.timeFadeUpSec;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getTimeFadeDownSec() {
/*  54 */     return this.timeFadeDownSec;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTimeLastMs() {
/*  61 */     return this.timeLastMs;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSmoothValue(float value) {
/*  67 */     long timeNowMs = System.currentTimeMillis();
/*     */     
/*  69 */     float valPrev = this.valueLast;
/*  70 */     long timePrevMs = this.timeLastMs;
/*     */     
/*  72 */     float timeDeltaSec = (float)(timeNowMs - timePrevMs) / 1000.0F;
/*     */     
/*  74 */     float timeFadeSec = (value >= valPrev) ? this.timeFadeUpSec : this.timeFadeDownSec;
/*     */     
/*  76 */     float valSmooth = getSmoothValue(valPrev, value, timeDeltaSec, timeFadeSec);
/*     */     
/*  78 */     this.valueLast = valSmooth;
/*  79 */     this.timeLastMs = timeNowMs;
/*     */     
/*  81 */     return valSmooth;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getSmoothValue(float valPrev, float value, float timeDeltaSec, float timeFadeSec) {
/*     */     float valSmooth;
/*  88 */     if (timeDeltaSec <= 0.0F) {
/*  89 */       return valPrev;
/*     */     }
/*  91 */     float valDelta = value - valPrev;
/*     */ 
/*     */     
/*  94 */     if (timeFadeSec > 0.0F && timeDeltaSec < timeFadeSec && Math.abs(valDelta) > 1.0E-6F) {
/*     */       
/*  96 */       float countUpdates = timeFadeSec / timeDeltaSec;
/*     */       
/*  98 */       float k1 = 4.61F;
/*  99 */       float k2 = 0.13F;
/* 100 */       float k3 = 10.0F;
/*     */       
/* 102 */       float kCorr = k1 - 1.0F / (k2 + countUpdates / k3);
/*     */       
/* 104 */       float kTime = timeDeltaSec / timeFadeSec * kCorr;
/* 105 */       kTime = NumUtils.limit(kTime, 0.0F, 1.0F);
/*     */       
/* 107 */       valSmooth = valPrev + valDelta * kTime;
/*     */     }
/*     */     else {
/*     */       
/* 111 */       valSmooth = value;
/*     */     } 
/*     */     
/* 114 */     return valSmooth;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\SmoothFloat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */