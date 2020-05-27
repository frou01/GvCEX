/*    */ package optifine;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.List;
/*    */ import net.minecraft.launchwrapper.ITweaker;
/*    */ import net.minecraft.launchwrapper.LaunchClassLoader;
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
/*    */ public class OptiFineForgeTweaker
/*    */   implements ITweaker
/*    */ {
/*    */   public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
/* 25 */     dbg("OptiFineForgeTweaker: acceptOptions");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void injectIntoClassLoader(LaunchClassLoader classLoader) {
/* 31 */     if (LaunchUtils.isForgeServer()) {
/*    */       
/* 33 */       dbg("OptiFineForgeTweaker: Forge server detected, skipping class transformer");
/*    */       
/*    */       return;
/*    */     } 
/* 37 */     dbg("OptiFineForgeTweaker: injectIntoClassLoader");
/* 38 */     classLoader.registerTransformer("optifine.OptiFineClassTransformer");
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLaunchTarget() {
/* 43 */     dbg("OptiFineForgeTweaker: getLaunchTarget");
/* 44 */     return "net.minecraft.client.main.Main";
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getLaunchArguments() {
/* 49 */     dbg("OptiFineForgeTweaker: getLaunchArguments");
/*    */     
/* 51 */     return new String[0];
/*    */   }
/*    */ 
/*    */   
/*    */   private static void dbg(String str) {
/* 56 */     System.out.println(str);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\OptiFineForgeTweaker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */