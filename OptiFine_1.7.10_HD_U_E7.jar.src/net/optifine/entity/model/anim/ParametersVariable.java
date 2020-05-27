/*     */ package net.optifine.entity.model.anim;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParametersVariable
/*     */   implements IParameters
/*     */ {
/*     */   private ExpressionType[] first;
/*     */   private ExpressionType[] repeat;
/*     */   private ExpressionType[] last;
/*  18 */   private int maxCount = Integer.MAX_VALUE;
/*     */   
/*  20 */   private static final ExpressionType[] EMPTY = new ExpressionType[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParametersVariable() {
/*  26 */     this(null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParametersVariable(ExpressionType[] first, ExpressionType[] repeat, ExpressionType[] last) {
/*  33 */     this(first, repeat, last, 2147483647);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParametersVariable(ExpressionType[] first, ExpressionType[] repeat, ExpressionType[] last, int maxCount) {
/*  40 */     this.first = normalize(first);
/*  41 */     this.repeat = normalize(repeat);
/*  42 */     this.last = normalize(last);
/*  43 */     this.maxCount = maxCount;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ExpressionType[] normalize(ExpressionType[] exprs) {
/*  49 */     if (exprs == null) {
/*  50 */       return EMPTY;
/*     */     }
/*  52 */     return exprs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExpressionType[] getFirst() {
/*  59 */     return this.first;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExpressionType[] getRepeat() {
/*  66 */     return this.repeat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExpressionType[] getLast() {
/*  73 */     return this.last;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCountRepeat() {
/*  79 */     if (this.first == null) {
/*  80 */       return 0;
/*     */     }
/*  82 */     return this.first.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExpressionType[] getParameterTypes(IExpression[] arguments) {
/*  89 */     int countFixedParams = this.first.length + this.last.length;
/*     */     
/*  91 */     int countVarArgs = arguments.length - countFixedParams;
/*     */     
/*  93 */     int countRepeat = 0;
/*  94 */     int countVarParams = 0;
/*  95 */     while (countVarParams + this.repeat.length <= countVarArgs && countFixedParams + countVarParams + this.repeat.length <= this.maxCount) {
/*     */       
/*  97 */       countRepeat++;
/*  98 */       countVarParams += this.repeat.length;
/*     */     } 
/*     */     
/* 101 */     List<ExpressionType> list = new ArrayList<ExpressionType>();
/*     */     
/* 103 */     list.addAll(Arrays.asList(this.first));
/*     */     
/* 105 */     for (int i = 0; i < countRepeat; i++)
/*     */     {
/* 107 */       list.addAll(Arrays.asList(this.repeat));
/*     */     }
/*     */     
/* 110 */     list.addAll(Arrays.asList(this.last));
/*     */     
/* 112 */     ExpressionType[] ets = list.<ExpressionType>toArray(new ExpressionType[list.size()]);
/*     */     
/* 114 */     return ets;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ParametersVariable first(ExpressionType... first) {
/* 120 */     return new ParametersVariable(first, this.repeat, this.last);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ParametersVariable repeat(ExpressionType... repeat) {
/* 126 */     return new ParametersVariable(this.first, repeat, this.last);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ParametersVariable last(ExpressionType... last) {
/* 132 */     return new ParametersVariable(this.first, this.repeat, last);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ParametersVariable maxCount(int maxCount) {
/* 138 */     return new ParametersVariable(this.first, this.repeat, this.last, maxCount);
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\net\optifine\entity\model\anim\ParametersVariable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */