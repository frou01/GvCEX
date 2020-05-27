/*    */ import java.io.File;
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
/*    */ public class ResourceUtils
/*    */ {
/* 16 */   private static ReflectorClass ForgeAbstractResourcePack = new ReflectorClass(bqn.class);
/* 17 */   private static ReflectorField ForgeAbstractResourcePack_resourcePackFile = new ReflectorField(ForgeAbstractResourcePack, "field_110597_b");
/*    */   
/*    */   private static boolean directAccessValid = true;
/*    */   
/*    */   public static File getResourcePackFile(bqn arp) {
/* 22 */     if (directAccessValid) {
/*    */       
/*    */       try {
/*    */         
/* 26 */         return arp.a;
/*    */       }
/* 28 */       catch (IllegalAccessError e) {
/*    */         
/* 30 */         directAccessValid = false;
/*    */         
/* 32 */         if (!ForgeAbstractResourcePack_resourcePackFile.exists()) {
/* 33 */           throw e;
/*    */         }
/*    */       } 
/*    */     }
/* 37 */     return (File)Reflector.getFieldValue(arp, ForgeAbstractResourcePack_resourcePackFile);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\ResourceUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */