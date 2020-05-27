/*     */ package shadersmod.uniform;
/*     */ 
/*     */ import bao;
/*     */ import bnn;
/*     */ import net.optifine.entity.model.anim.ExpressionType;
/*     */ import net.optifine.entity.model.anim.IExpressionBool;
/*     */ import sv;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum ShaderParameterBool
/*     */   implements IExpressionBool
/*     */ {
/*  18 */   IS_ALIVE("is_alive"),
/*  19 */   IS_BURNING("is_burning"),
/*  20 */   IS_CHILD("is_child"),
/*  21 */   IS_GLOWING("is_glowing"),
/*  22 */   IS_HURT("is_hurt"),
/*  23 */   IS_IN_LAVA("is_in_lava"),
/*  24 */   IS_IN_WATER("is_in_water"),
/*  25 */   IS_INVISIBLE("is_invisible"),
/*  26 */   IS_ON_GROUND("is_on_ground"),
/*  27 */   IS_RIDDEN("is_ridden"),
/*  28 */   IS_RIDING("is_riding"),
/*  29 */   IS_SNEAKING("is_sneaking"),
/*  30 */   IS_SPRINTING("is_sprinting"),
/*  31 */   IS_WET("is_wet");
/*     */   
/*     */   private String name;
/*     */   
/*     */   static {
/*  36 */     VALUES = values();
/*     */   }
/*     */   private bnn renderManager;
/*     */   private static final ShaderParameterBool[] VALUES;
/*     */   
/*     */   ShaderParameterBool(String name) {
/*  42 */     this.name = name;
/*  43 */     this.renderManager = bnn.a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  50 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExpressionType getExpressionType() {
/*  57 */     return ExpressionType.BOOL;
/*     */   }
/*     */   
/*     */   public boolean eval() {
/*  61 */     sv sv = (bao.B()).i;
/*  62 */     if (sv instanceof sv) {
/*     */       
/*  64 */       sv entity = sv;
/*     */       
/*  66 */       switch (this) {
/*     */         
/*     */         case IS_ALIVE:
/*  69 */           return entity.Z();
/*     */         case IS_BURNING:
/*  71 */           return entity.al();
/*     */         case IS_CHILD:
/*  73 */           return entity.f();
/*     */         
/*     */         case IS_GLOWING:
/*  76 */           return false;
/*     */         case IS_HURT:
/*  78 */           return (entity.ax > 0);
/*     */         case IS_IN_LAVA:
/*  80 */           return entity.P();
/*     */         case IS_IN_WATER:
/*  82 */           return entity.M();
/*     */         case IS_INVISIBLE:
/*  84 */           return entity.ap();
/*     */         case IS_ON_GROUND:
/*  86 */           return entity.D;
/*     */         case IS_RIDDEN:
/*  88 */           return (entity.l != null);
/*     */         case IS_RIDING:
/*  90 */           return entity.am();
/*     */         case IS_SNEAKING:
/*  92 */           return entity.an();
/*     */         case IS_SPRINTING:
/*  94 */           return entity.ao();
/*     */         case IS_WET:
/*  96 */           return entity.L();
/*     */       } 
/*     */     
/*     */     } 
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ShaderParameterBool parse(String str) {
/* 109 */     if (str == null) {
/* 110 */       return null;
/*     */     }
/* 112 */     for (int i = 0; i < VALUES.length; i++) {
/*     */       
/* 114 */       ShaderParameterBool type = VALUES[i];
/* 115 */       if (type.getName().equals(str)) {
/* 116 */         return type;
/*     */       }
/*     */     } 
/* 119 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmo\\uniform\ShaderParameterBool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */