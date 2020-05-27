/*    */ package shadersmod.uniform;
/*    */ 
/*    */ import ahu;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.optifine.entity.model.anim.ConstantFloat;
/*    */ import net.optifine.entity.model.anim.IExpression;
/*    */ import net.optifine.entity.model.anim.IExpressionResolver;
/*    */ import shadersmod.common.SMCLog;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShaderExpressionResolver
/*    */   implements IExpressionResolver
/*    */ {
/* 22 */   private Map<String, IExpression> mapExpressions = new HashMap<String, IExpression>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ShaderExpressionResolver(Map<String, IExpression> map) {
/* 29 */     registerExpressions();
/*    */     
/* 31 */     Set<String> keys = map.keySet();
/* 32 */     for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
/*    */       
/* 34 */       String name = it.next();
/* 35 */       IExpression expr = map.get(name);
/* 36 */       registerExpression(name, expr);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void registerExpressions() {
/* 45 */     ShaderParameterFloat[] spfs = ShaderParameterFloat.values();
/* 46 */     for (int i = 0; i < spfs.length; i++) {
/*    */       
/* 48 */       ShaderParameterFloat spf = spfs[i];
/* 49 */       this.mapExpressions.put(spf.getName(), spf);
/*    */     } 
/*    */     
/* 52 */     ShaderParameterBool[] spbs = ShaderParameterBool.values();
/* 53 */     for (int j = 0; j < spbs.length; j++) {
/*    */       
/* 55 */       ShaderParameterBool spb = spbs[j];
/* 56 */       this.mapExpressions.put(spb.getName(), spb);
/*    */     } 
/*    */     
/* 59 */     ahu[] biomeList = ahu.n();
/* 60 */     for (int k = 0; k < biomeList.length; k++) {
/*    */       
/* 62 */       ahu biome = biomeList[k];
/* 63 */       if (biome != null) {
/*    */         
/* 65 */         String name = biome.af.trim();
/* 66 */         name = "BIOME_" + name.toUpperCase().replace(' ', '_');
/* 67 */         int id = biome.ay;
/* 68 */         ConstantFloat constantFloat = new ConstantFloat(id);
/* 69 */         registerExpression(name, (IExpression)constantFloat);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean registerExpression(String name, IExpression expr) {
/* 79 */     if (this.mapExpressions.containsKey(name)) {
/*    */       
/* 81 */       SMCLog.warning("Expression already defined: " + name);
/* 82 */       return false;
/*    */     } 
/* 84 */     this.mapExpressions.put(name, expr);
/*    */     
/* 86 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IExpression getExpression(String name) {
/* 93 */     return this.mapExpressions.get(name);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasExpression(String name) {
/* 99 */     return this.mapExpressions.containsKey(name);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmo\\uniform\ShaderExpressionResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */