/*    */ package optifine;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LaunchUtils
/*    */ {
/* 14 */   private static Boolean forgeServer = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isForgeServer() {
/* 19 */     if (forgeServer == null) {
/*    */       
/*    */       try {
/*    */ 
/*    */         
/* 24 */         Class<?> cls = Class.forName("net.minecraft.launchwrapper.Launch");
/*    */         
/* 26 */         Field fieldBlackboard = cls.getField("blackboard");
/* 27 */         Map<String, Object> blackboard = (Map<String, Object>)fieldBlackboard.get(null);
/*    */         
/* 29 */         Map<String, String> launchArgs = (Map<String, String>)blackboard.get("launchArgs");
/*    */         
/* 31 */         String accessToken = launchArgs.get("--accessToken");
/*    */         
/* 33 */         String version = launchArgs.get("--version");
/*    */         
/* 35 */         boolean onServer = (accessToken == null && Utils.equals(version, "UnknownFMLProfile"));
/*    */         
/* 37 */         forgeServer = Boolean.valueOf(onServer);
/*    */       }
/* 39 */       catch (Throwable e) {
/*    */         
/* 41 */         System.out.println("Error checking Forge server: " + e.getClass().getName() + ": " + e.getMessage());
/*    */         
/* 43 */         forgeServer = Boolean.FALSE;
/*    */       } 
/*    */     }
/*    */     
/* 47 */     return forgeServer.booleanValue();
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\LaunchUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */