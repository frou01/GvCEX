/*     */ package shadersmod.uniform;
/*     */ 
/*     */ import net.optifine.entity.model.anim.ExpressionType;
/*     */ import net.optifine.entity.model.anim.IExpression;
/*     */ import net.optifine.entity.model.anim.IExpressionBool;
/*     */ import net.optifine.entity.model.anim.IExpressionFloat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum UniformType
/*     */ {
/*  16 */   BOOL,
/*  17 */   INT,
/*  18 */   FLOAT;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShaderUniformBase makeShaderUniform(String name) {
/*  25 */     switch (this) {
/*     */       
/*     */       case BOOL:
/*  28 */         return new ShaderUniformInt(name);
/*     */       case INT:
/*  30 */         return new ShaderUniformInt(name);
/*     */       case FLOAT:
/*  32 */         return new ShaderUniformFloat(name);
/*     */     } 
/*     */     
/*  35 */     throw new RuntimeException("Unknown uniform type: " + this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateUniform(IExpression expression, ShaderUniformBase uniform) {
/*  44 */     switch (this) {
/*     */       
/*     */       case BOOL:
/*  47 */         updateUniformBool((IExpressionBool)expression, (ShaderUniformInt)uniform);
/*     */         return;
/*     */       case INT:
/*  50 */         updateUniformInt((IExpressionFloat)expression, (ShaderUniformInt)uniform);
/*     */         return;
/*     */       case FLOAT:
/*  53 */         updateUniformFloat((IExpressionFloat)expression, (ShaderUniformFloat)uniform);
/*     */         return;
/*     */     } 
/*     */     
/*  57 */     throw new RuntimeException("Unknown uniform type: " + this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateUniformBool(IExpressionBool expression, ShaderUniformInt uniform) {
/*  64 */     boolean val = expression.eval();
/*  65 */     int valInt = val ? 1 : 0;
/*  66 */     uniform.setValue(valInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateUniformInt(IExpressionFloat expression, ShaderUniformInt uniform) {
/*  73 */     int val = (int)expression.eval();
/*  74 */     uniform.setValue(val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateUniformFloat(IExpressionFloat expression, ShaderUniformFloat uniform) {
/*  81 */     float val = expression.eval();
/*  82 */     uniform.setValue(val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesExpressionType(ExpressionType expressionType) {
/*  90 */     switch (this) {
/*     */       
/*     */       case BOOL:
/*  93 */         return (expressionType == ExpressionType.BOOL);
/*     */       case INT:
/*  95 */         return (expressionType == ExpressionType.FLOAT);
/*     */       case FLOAT:
/*  97 */         return (expressionType == ExpressionType.FLOAT);
/*     */     } 
/*     */     
/* 100 */     throw new RuntimeException("Unknown uniform type: " + this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UniformType parse(String type) {
/* 108 */     UniformType[] values = values();
/* 109 */     for (int i = 0; i < values.length; i++) {
/*     */       
/* 111 */       UniformType uniformType = values[i];
/* 112 */       if (uniformType.name().toLowerCase().equals(type)) {
/* 113 */         return uniformType;
/*     */       }
/*     */     } 
/* 116 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmo\\uniform\UniformType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */