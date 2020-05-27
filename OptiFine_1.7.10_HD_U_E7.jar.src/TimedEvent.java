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
/*    */ 
/*    */ public class TimedEvent
/*    */ {
/* 15 */   private static Map<String, Long> mapEventTimes = new HashMap<String, Long>();
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isActive(String name, long timeIntervalMs) {
/* 20 */     synchronized (mapEventTimes) {
/*    */       
/* 22 */       long timeNowMs = System.currentTimeMillis();
/*    */       
/* 24 */       Long timeLastMsObj = mapEventTimes.get(name);
/* 25 */       if (timeLastMsObj == null) {
/*    */         
/* 27 */         timeLastMsObj = new Long(timeNowMs);
/* 28 */         mapEventTimes.put(name, timeLastMsObj);
/*    */       } 
/*    */       
/* 31 */       long timeLastMs = timeLastMsObj.longValue();
/* 32 */       if (timeNowMs < timeLastMs + timeIntervalMs) {
/* 33 */         return false;
/*    */       }
/* 35 */       mapEventTimes.put(name, new Long(timeNowMs));
/*    */       
/* 37 */       return true;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\TimedEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */