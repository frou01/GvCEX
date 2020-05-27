/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Map;
/*     */ import org.lwjgl.opengl.GL11;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderPlayerOF
/*     */   extends bop
/*     */ {
/*     */   protected void c(sv entityLiving, float partialTicks) {
/*  31 */     super.c(entityLiving, partialTicks);
/*     */     
/*  33 */     renderEquippedItems(entityLiving, 0.0625F, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderEquippedItems(sv entityLiving, float scale, float partialTicks) {
/*  40 */     if (!Config.isShowCapes()) {
/*     */       return;
/*     */     }
/*  43 */     if (!(entityLiving instanceof blg))
/*     */       return; 
/*  45 */     blg player = (blg)entityLiving;
/*     */     
/*  47 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  48 */     GL11.glDisable(32826);
/*  49 */     GlStateManager.enableCull();
/*     */     
/*  51 */     bhm modelBipedMain = (bhm)this.i;
/*  52 */     PlayerConfigurations.renderPlayerItems(modelBipedMain, player, scale, partialTicks);
/*     */     
/*  54 */     GlStateManager.disableCull();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register() {
/*  61 */     bnn rm = bnn.a;
/*  62 */     Map<Class<yz>, RenderPlayerOF> mapRenderTypes = getMapRenderTypes(rm);
/*  63 */     if (mapRenderTypes == null) {
/*     */       
/*  65 */       Config.warn("RenderPlayerOF init() failed: RenderManager.MapRenderTypes not found");
/*     */       
/*     */       return;
/*     */     } 
/*  69 */     RenderPlayerOF rpof = new RenderPlayerOF();
/*  70 */     rpof.a(rm);
/*  71 */     mapRenderTypes.put(yz.class, rpof);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map getMapRenderTypes(bnn rm) {
/*     */     try {
/*  81 */       Field[] fields = Reflector.getFields(bnn.class, Map.class);
/*  82 */       for (int i = 0; i < fields.length; i++) {
/*     */         
/*  84 */         Field field = fields[i];
/*  85 */         Map map = (Map)field.get(rm);
/*  86 */         if (map != null) {
/*     */ 
/*     */           
/*  89 */           Object renderSteve = map.get(yz.class);
/*  90 */           if (renderSteve instanceof bop)
/*     */           {
/*     */             
/*  93 */             return map; } 
/*     */         } 
/*     */       } 
/*  96 */       return null;
/*     */     }
/*  98 */     catch (Exception e) {
/*     */       
/* 100 */       Config.warn("Error getting RenderManager.mapRenderTypes");
/* 101 */       Config.warn(e.getClass().getName() + ": " + e.getMessage());
/* 102 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\RenderPlayerOF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */