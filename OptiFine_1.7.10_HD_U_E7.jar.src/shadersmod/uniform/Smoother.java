/*    */ package shadersmod.uniform;
/*    */ 
/*    */ import SmoothFloat;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Smoother
/*    */ {
/* 17 */   private static Map<Integer, SmoothFloat> mapSmoothValues = new HashMap<Integer, SmoothFloat>();
/*    */ 
/*    */ 
/*    */   
/*    */   public static float getSmoothValue(int id, float value, float timeFadeUpSec, float timeFadeDownSec) {
/* 22 */     synchronized (mapSmoothValues) {
/*    */       
/* 24 */       Integer key = Integer.valueOf(id);
/* 25 */       SmoothFloat sf = mapSmoothValues.get(key);
/* 26 */       if (sf == null) {
/*    */         
/* 28 */         sf = new SmoothFloat(value, timeFadeUpSec, timeFadeDownSec);
/* 29 */         mapSmoothValues.put(key, sf);
/*    */       } 
/*    */       
/* 32 */       float valueSmooth = sf.getSmoothValue(value);
/*    */       
/* 34 */       return valueSmooth;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void reset() {
/* 41 */     synchronized (mapSmoothValues) {
/*    */       
/* 43 */       mapSmoothValues.clear();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmo\\uniform\Smoother.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */