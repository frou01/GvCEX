/*    */ package shadersmod.common;
/*    */ 
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public abstract class SMCLog
/*    */ {
/*  9 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */   
/*    */   private static final String PREFIX = "[Shaders] ";
/*    */   
/*    */   public static void severe(String message) {
/* 14 */     LOGGER.error("[Shaders] " + message);
/*    */   }
/*    */   
/*    */   public static void warning(String message) {
/* 18 */     LOGGER.warn("[Shaders] " + message);
/*    */   }
/*    */   
/*    */   public static void info(String message) {
/* 22 */     LOGGER.info("[Shaders] " + message);
/*    */   }
/*    */   
/*    */   public static void fine(String message) {
/* 26 */     LOGGER.debug("[Shaders] " + message);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void severe(String format, Object... args) {
/* 31 */     String message = String.format(format, args);
/* 32 */     LOGGER.error("[Shaders] " + message);
/*    */   }
/*    */   
/*    */   public static void warning(String format, Object... args) {
/* 36 */     String message = String.format(format, args);
/* 37 */     LOGGER.warn("[Shaders] " + message);
/*    */   }
/*    */   
/*    */   public static void info(String format, Object... args) {
/* 41 */     String message = String.format(format, args);
/* 42 */     LOGGER.info("[Shaders] " + message);
/*    */   }
/*    */   
/*    */   public static void fine(String format, Object... args) {
/* 46 */     String message = String.format(format, args);
/* 47 */     LOGGER.debug("[Shaders] " + message);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\common\SMCLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */