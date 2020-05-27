/*    */ package shadersmod.client;
/*    */ 
/*    */ import Config;
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
/*    */ public class ShaderUtils
/*    */ {
/*    */   public static ShaderOption getShaderOption(String name, ShaderOption[] opts) {
/* 20 */     if (opts == null) {
/* 21 */       return null;
/*    */     }
/* 23 */     for (int i = 0; i < opts.length; i++) {
/*    */       
/* 25 */       ShaderOption so = opts[i];
/* 26 */       if (so.getName().equals(name)) {
/* 27 */         return so;
/*    */       }
/*    */     } 
/* 30 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ShaderProfile detectProfile(ShaderProfile[] profs, ShaderOption[] opts, boolean def) {
/* 39 */     if (profs == null) {
/* 40 */       return null;
/*    */     }
/* 42 */     for (int i = 0; i < profs.length; i++) {
/*    */       
/* 44 */       ShaderProfile prof = profs[i];
/* 45 */       if (matchProfile(prof, opts, def)) {
/* 46 */         return prof;
/*    */       }
/*    */     } 
/* 49 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean matchProfile(ShaderProfile prof, ShaderOption[] opts, boolean def) {
/* 60 */     if (prof == null)
/* 61 */       return false; 
/* 62 */     if (opts == null) {
/* 63 */       return false;
/*    */     }
/* 65 */     String[] optsProf = prof.getOptions();
/* 66 */     for (int p = 0; p < optsProf.length; p++) {
/*    */       
/* 68 */       String opt = optsProf[p];
/* 69 */       ShaderOption so = getShaderOption(opt, opts);
/* 70 */       if (so != null) {
/*    */ 
/*    */         
/* 73 */         String optVal = def ? so.getValueDefault() : so.getValue();
/* 74 */         String profVal = prof.getValue(opt);
/*    */         
/* 76 */         if (!Config.equals(optVal, profVal))
/* 77 */           return false; 
/*    */       } 
/*    */     } 
/* 80 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\ShaderUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */