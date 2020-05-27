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
/*    */ public class EntityUtils
/*    */ {
/* 17 */   private static ReflectorClass ForgeEntityLivingBase = new ReflectorClass(sv.class);
/* 18 */   private static ReflectorField ForgeEntityLivingBase_entityAge = new ReflectorField(ForgeEntityLivingBase, "field_70708_bq");
/*    */   
/*    */   private static boolean directEntityAge = true;
/* 21 */   private static ReflectorClass ForgeEntityLiving = new ReflectorClass(sw.class);
/* 22 */   private static ReflectorMethod ForgeEntityLiving_despawnEntity = new ReflectorMethod(ForgeEntityLiving, "func_70623_bb");
/*    */   
/*    */   private static boolean directDespawnEntity = true;
/*    */   
/*    */   public static int getEntityAge(sv elb) {
/* 27 */     if (directEntityAge) {
/*    */       
/*    */       try {
/*    */         
/* 31 */         return elb.aU;
/*    */       }
/* 33 */       catch (IllegalAccessError e) {
/*    */         
/* 35 */         directEntityAge = false;
/*    */         
/* 37 */         if (!ForgeEntityLivingBase_entityAge.exists()) {
/* 38 */           throw e;
/*    */         }
/*    */       } 
/*    */     }
/* 42 */     return ((Integer)Reflector.getFieldValue(elb, ForgeEntityLivingBase_entityAge)).intValue();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setEntityAge(sv elb, int age) {
/* 51 */     if (directEntityAge) {
/*    */       
/*    */       try {
/*    */         
/* 55 */         elb.aU = age;
/*    */         
/*    */         return;
/* 58 */       } catch (IllegalAccessError e) {
/*    */         
/* 60 */         directEntityAge = false;
/*    */         
/* 62 */         if (!ForgeEntityLivingBase_entityAge.exists()) {
/* 63 */           throw e;
/*    */         }
/*    */       } 
/*    */     }
/* 67 */     Reflector.setFieldValue(elb, ForgeEntityLivingBase_entityAge, Integer.valueOf(age));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void despawnEntity(sw el) {
/* 75 */     if (directDespawnEntity) {
/*    */       
/*    */       try {
/*    */         
/* 79 */         el.w();
/*    */         
/*    */         return;
/* 82 */       } catch (IllegalAccessError e) {
/*    */         
/* 84 */         directDespawnEntity = false;
/*    */         
/* 86 */         if (!ForgeEntityLiving_despawnEntity.exists()) {
/* 87 */           throw e;
/*    */         }
/*    */       } 
/*    */     }
/* 91 */     Reflector.callVoid(el, ForgeEntityLiving_despawnEntity, new Object[0]);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\EntityUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */