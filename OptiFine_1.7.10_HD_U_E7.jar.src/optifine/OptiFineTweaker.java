/*    */ package optifine;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.ArrayList;
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
/*    */ public class OptiFineTweaker
/*    */   implements ITweaker
/*    */ {
/*    */   private List<String> args;
/*    */   
/*    */   public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
/* 26 */     dbg("OptiFineTweaker: acceptOptions");
/* 27 */     this.args = new ArrayList<String>(args);
/* 28 */     this.args.add("--gameDir");
/* 29 */     this.args.add(gameDir.getAbsolutePath());
/* 30 */     this.args.add("--assetsDir");
/* 31 */     this.args.add(assetsDir.getAbsolutePath());
/* 32 */     this.args.add("--version");
/* 33 */     this.args.add(profile);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void injectIntoClassLoader(LaunchClassLoader classLoader) {
/* 39 */     dbg("OptiFineTweaker: injectIntoClassLoader");
/* 40 */     classLoader.registerTransformer("optifine.OptiFineClassTransformer");
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLaunchTarget() {
/* 45 */     dbg("OptiFineTweaker: getLaunchTarget");
/* 46 */     return "net.minecraft.client.main.Main";
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getLaunchArguments() {
/* 51 */     dbg("OptiFineTweaker: getLaunchArguments");
/* 52 */     return this.args.<String>toArray(new String[this.args.size()]);
/*    */   }
/*    */ 
/*    */   
/*    */   private static void dbg(String str) {
/* 57 */     System.out.println(str);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\OptiFineTweaker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */