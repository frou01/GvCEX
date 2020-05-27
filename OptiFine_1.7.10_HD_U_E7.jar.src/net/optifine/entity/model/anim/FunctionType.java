/*     */ package net.optifine.entity.model.anim;
/*     */ 
/*     */ import Config;
/*     */ import MathUtils;
/*     */ import bao;
/*     */ import bjf;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import qh;
/*     */ import shadersmod.uniform.Smoother;
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
/*     */ public enum FunctionType
/*     */ {
/*  24 */   PLUS(10, ExpressionType.FLOAT, "+", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  25 */   MINUS(10, ExpressionType.FLOAT, "-", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  26 */   MUL(11, ExpressionType.FLOAT, "*", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  27 */   DIV(11, ExpressionType.FLOAT, "/", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  28 */   MOD(11, ExpressionType.FLOAT, "%", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  29 */   NEG(12, ExpressionType.FLOAT, "neg", new ExpressionType[] { ExpressionType.FLOAT
/*     */     }),
/*  31 */   PI(ExpressionType.FLOAT, "pi", new ExpressionType[0]),
/*  32 */   SIN(ExpressionType.FLOAT, "sin", new ExpressionType[] { ExpressionType.FLOAT }),
/*  33 */   COS(ExpressionType.FLOAT, "cos", new ExpressionType[] { ExpressionType.FLOAT }),
/*  34 */   ASIN(ExpressionType.FLOAT, "asin", new ExpressionType[] { ExpressionType.FLOAT }),
/*  35 */   ACOS(ExpressionType.FLOAT, "acos", new ExpressionType[] { ExpressionType.FLOAT }),
/*  36 */   TAN(ExpressionType.FLOAT, "tan", new ExpressionType[] { ExpressionType.FLOAT }),
/*  37 */   ATAN(ExpressionType.FLOAT, "atan", new ExpressionType[] { ExpressionType.FLOAT }),
/*  38 */   ATAN2(ExpressionType.FLOAT, "atan2", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  39 */   TORAD(ExpressionType.FLOAT, "torad", new ExpressionType[] { ExpressionType.FLOAT }),
/*  40 */   TODEG(ExpressionType.FLOAT, "todeg", new ExpressionType[] { ExpressionType.FLOAT
/*     */     }),
/*  42 */   MIN(ExpressionType.FLOAT, "min", (new ParametersVariable()).first(new ExpressionType[] { ExpressionType.FLOAT }).repeat(new ExpressionType[] { ExpressionType.FLOAT })),
/*  43 */   MAX(ExpressionType.FLOAT, "max", (new ParametersVariable()).first(new ExpressionType[] { ExpressionType.FLOAT }).repeat(new ExpressionType[] { ExpressionType.FLOAT })),
/*  44 */   CLAMP(ExpressionType.FLOAT, "clamp", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT
/*     */     }),
/*  46 */   ABS(ExpressionType.FLOAT, "abs", new ExpressionType[] { ExpressionType.FLOAT }),
/*  47 */   FLOOR(ExpressionType.FLOAT, "floor", new ExpressionType[] { ExpressionType.FLOAT }),
/*  48 */   CEIL(ExpressionType.FLOAT, "ceil", new ExpressionType[] { ExpressionType.FLOAT }),
/*  49 */   EXP(ExpressionType.FLOAT, "exp", new ExpressionType[] { ExpressionType.FLOAT }),
/*  50 */   FRAC(ExpressionType.FLOAT, "frac", new ExpressionType[] { ExpressionType.FLOAT }),
/*  51 */   LOG(ExpressionType.FLOAT, "log", new ExpressionType[] { ExpressionType.FLOAT }),
/*  52 */   POW(ExpressionType.FLOAT, "pow", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  53 */   RANDOM(ExpressionType.FLOAT, "random", new ExpressionType[0]),
/*  54 */   ROUND(ExpressionType.FLOAT, "round", new ExpressionType[] { ExpressionType.FLOAT }),
/*  55 */   SIGNUM(ExpressionType.FLOAT, "signum", new ExpressionType[] { ExpressionType.FLOAT }),
/*  56 */   SQRT(ExpressionType.FLOAT, "sqrt", new ExpressionType[] { ExpressionType.FLOAT }),
/*  57 */   FMOD(ExpressionType.FLOAT, "fmod", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT
/*     */     }),
/*  59 */   TIME(ExpressionType.FLOAT, "time", new ExpressionType[0]),
/*     */   
/*  61 */   IF(ExpressionType.FLOAT, "if", (new ParametersVariable()).first(new ExpressionType[] { ExpressionType.BOOL, ExpressionType.FLOAT }).repeat(new ExpressionType[] { ExpressionType.BOOL, ExpressionType.FLOAT }).last(new ExpressionType[] { ExpressionType.FLOAT
/*     */       })),
/*  63 */   NOT(12, ExpressionType.BOOL, "!", new ExpressionType[] { ExpressionType.BOOL }),
/*  64 */   AND(3, ExpressionType.BOOL, "&&", new ExpressionType[] { ExpressionType.BOOL, ExpressionType.BOOL }),
/*  65 */   OR(2, ExpressionType.BOOL, "||", new ExpressionType[] { ExpressionType.BOOL, ExpressionType.BOOL
/*     */     }),
/*  67 */   GREATER(8, ExpressionType.BOOL, ">", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  68 */   GREATER_OR_EQUAL(8, ExpressionType.BOOL, ">=", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  69 */   SMALLER(8, ExpressionType.BOOL, "<", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  70 */   SMALLER_OR_EQUAL(8, ExpressionType.BOOL, "<=", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  71 */   EQUAL(7, ExpressionType.BOOL, "==", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  72 */   NOT_EQUAL(7, ExpressionType.BOOL, "!=", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  73 */   BETWEEN(7, ExpressionType.BOOL, "between", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  74 */   EQUALS(7, ExpressionType.BOOL, "equals", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  75 */   IN(ExpressionType.BOOL, "in", (new ParametersVariable()).first(new ExpressionType[] { ExpressionType.FLOAT }).repeat(new ExpressionType[] { ExpressionType.FLOAT }).last(new ExpressionType[] { ExpressionType.FLOAT })),
/*  76 */   SMOOTH(ExpressionType.FLOAT, "smooth", (new ParametersVariable()).first(new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }).repeat(new ExpressionType[] { ExpressionType.FLOAT }).maxCount(4)),
/*     */   
/*  78 */   TRUE(ExpressionType.BOOL, "true", new ExpressionType[0]),
/*  79 */   FALSE(ExpressionType.BOOL, "false", new ExpressionType[0]);
/*     */   
/*     */   private int precedence;
/*     */   
/*     */   private ExpressionType expressionType;
/*     */   private String name;
/*     */   
/*     */   static {
/*  87 */     VALUES = values();
/*     */     
/*  89 */     mapSmooth = new HashMap<Integer, Float>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IParameters parameters;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FunctionType[] VALUES;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final Map<Integer, Float> mapSmooth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   FunctionType(int precedence, ExpressionType expressionType, String name, IParameters parameters) {
/* 116 */     this.precedence = precedence;
/* 117 */     this.expressionType = expressionType;
/* 118 */     this.name = name;
/* 119 */     this.parameters = parameters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 126 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPrecedence() {
/* 133 */     return this.precedence;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExpressionType getExpressionType() {
/* 140 */     return this.expressionType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IParameters getParameters() {
/* 147 */     return this.parameters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getParameterCount(IExpression[] arguments) {
/* 155 */     return (this.parameters.getParameterTypes(arguments)).length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExpressionType[] getParameterTypes(IExpression[] arguments) {
/* 163 */     return this.parameters.getParameterTypes(arguments);
/*     */   }
/*     */   
/*     */   public float evalFloat(IExpression[] args) {
/*     */     float modX, modY, valFrac, fmodX, fmodY;
/*     */     bao mc;
/*     */     bjf bjf;
/*     */     int countChecks, i, id;
/*     */     float valRaw, valFadeUp, valFadeDown, valSmooth;
/* 172 */     switch (this) {
/*     */       
/*     */       case PLUS:
/* 175 */         return evalFloat(args, 0) + evalFloat(args, 1);
/*     */       case MINUS:
/* 177 */         return evalFloat(args, 0) - evalFloat(args, 1);
/*     */       case MUL:
/* 179 */         return evalFloat(args, 0) * evalFloat(args, 1);
/*     */       case DIV:
/* 181 */         return evalFloat(args, 0) / evalFloat(args, 1);
/*     */       case MOD:
/* 183 */         modX = evalFloat(args, 0);
/* 184 */         modY = evalFloat(args, 1);
/* 185 */         return modX - modY * (int)(modX / modY);
/*     */       case NEG:
/* 187 */         return -evalFloat(args, 0);
/*     */       case PI:
/* 189 */         return 3.1415927F;
/*     */       case SIN:
/* 191 */         return qh.a(evalFloat(args, 0));
/*     */       case COS:
/* 193 */         return qh.b(evalFloat(args, 0));
/*     */       case ASIN:
/* 195 */         return (float)Math.asin(evalFloat(args, 0));
/*     */       case ACOS:
/* 197 */         return (float)Math.acos(evalFloat(args, 0));
/*     */       case TAN:
/* 199 */         return (float)Math.tan(evalFloat(args, 0));
/*     */       case ATAN:
/* 201 */         return (float)Math.atan(evalFloat(args, 0));
/*     */       case ATAN2:
/* 203 */         return (float)Math.atan2(evalFloat(args, 0), evalFloat(args, 1));
/*     */       case TORAD:
/* 205 */         return MathUtils.toRad(evalFloat(args, 0));
/*     */       case TODEG:
/* 207 */         return MathUtils.toDeg(evalFloat(args, 0));
/*     */       case MIN:
/* 209 */         return getMin(args);
/*     */       case MAX:
/* 211 */         return getMax(args);
/*     */       case CLAMP:
/* 213 */         return qh.a(evalFloat(args, 0), evalFloat(args, 1), evalFloat(args, 2));
/*     */       case ABS:
/* 215 */         return qh.e(evalFloat(args, 0));
/*     */       case EXP:
/* 217 */         return (float)Math.exp(evalFloat(args, 0));
/*     */       case FLOOR:
/* 219 */         return qh.d(evalFloat(args, 0));
/*     */       case CEIL:
/* 221 */         return qh.f(evalFloat(args, 0));
/*     */       case FRAC:
/* 223 */         valFrac = evalFloat(args, 0);
/* 224 */         return valFrac - qh.d(valFrac);
/*     */       case LOG:
/* 226 */         return (float)Math.log(evalFloat(args, 0));
/*     */       case POW:
/* 228 */         return (float)Math.pow(evalFloat(args, 0), evalFloat(args, 1));
/*     */       case RANDOM:
/* 230 */         return (float)Math.random();
/*     */       case ROUND:
/* 232 */         return Math.round(evalFloat(args, 0));
/*     */       case SIGNUM:
/* 234 */         return Math.signum(evalFloat(args, 0));
/*     */       case SQRT:
/* 236 */         return qh.c(evalFloat(args, 0));
/*     */       case FMOD:
/* 238 */         fmodX = evalFloat(args, 0);
/* 239 */         fmodY = evalFloat(args, 1);
/* 240 */         return fmodX - fmodY * qh.d(fmodX / fmodY);
/*     */       case TIME:
/* 242 */         mc = bao.B();
/* 243 */         bjf = mc.f;
/* 244 */         if (bjf == null) {
/* 245 */           return 0.0F;
/*     */         }
/* 247 */         return (float)(bjf.I() % 24000L) + Config.renderPartialTicks;
/*     */       case IF:
/* 249 */         countChecks = (args.length - 1) / 2;
/* 250 */         for (i = 0; i < countChecks; i++) {
/*     */           
/* 252 */           int index = i * 2;
/* 253 */           if (evalBool(args, index)) {
/* 254 */             return evalFloat(args, index + 1);
/*     */           }
/*     */         } 
/* 257 */         return evalFloat(args, countChecks * 2);
/*     */       case SMOOTH:
/* 259 */         id = (int)evalFloat(args, 0);
/* 260 */         valRaw = evalFloat(args, 1);
/* 261 */         valFadeUp = (args.length > 2) ? evalFloat(args, 2) : 1.0F;
/* 262 */         valFadeDown = (args.length > 3) ? evalFloat(args, 3) : valFadeUp;
/* 263 */         valSmooth = Smoother.getSmoothValue(id, valRaw, valFadeUp, valFadeDown);
/* 264 */         return valSmooth;
/*     */     } 
/*     */     
/* 267 */     Config.warn("Unknown function type: " + this);
/* 268 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getMin(IExpression[] exprs) {
/* 276 */     if (exprs.length == 2) {
/* 277 */       return Math.min(evalFloat(exprs, 0), evalFloat(exprs, 1));
/*     */     }
/* 279 */     float valMin = evalFloat(exprs, 0);
/*     */     
/* 281 */     for (int i = 1; i < exprs.length; i++) {
/*     */       
/* 283 */       float valExpr = evalFloat(exprs, i);
/* 284 */       if (valExpr < valMin) {
/* 285 */         valMin = valExpr;
/*     */       }
/*     */     } 
/* 288 */     return valMin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getMax(IExpression[] exprs) {
/* 295 */     if (exprs.length == 2) {
/* 296 */       return Math.max(evalFloat(exprs, 0), evalFloat(exprs, 1));
/*     */     }
/* 298 */     float valMax = evalFloat(exprs, 0);
/*     */     
/* 300 */     for (int i = 1; i < exprs.length; i++) {
/*     */       
/* 302 */       float valExpr = evalFloat(exprs, i);
/* 303 */       if (valExpr > valMax) {
/* 304 */         valMax = valExpr;
/*     */       }
/*     */     } 
/* 307 */     return valMax;
/*     */   }
/*     */ 
/*     */   
/*     */   private static float evalFloat(IExpression[] exprs, int index) {
/* 312 */     IExpressionFloat ef = (IExpressionFloat)exprs[index];
/* 313 */     float val = ef.eval();
/* 314 */     return val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean evalBool(IExpression[] args) {
/*     */     float val, diff, delta, valIn;
/*     */     int i;
/* 324 */     switch (this) {
/*     */       
/*     */       case TRUE:
/* 327 */         return true;
/*     */       case FALSE:
/* 329 */         return false;
/*     */       case NOT:
/* 331 */         return !evalBool(args, 0);
/*     */       case AND:
/* 333 */         return (evalBool(args, 0) && evalBool(args, 1));
/*     */       case OR:
/* 335 */         return (evalBool(args, 0) || evalBool(args, 1));
/*     */       case GREATER:
/* 337 */         return (evalFloat(args, 0) > evalFloat(args, 1));
/*     */       case GREATER_OR_EQUAL:
/* 339 */         return (evalFloat(args, 0) >= evalFloat(args, 1));
/*     */       case SMALLER:
/* 341 */         return (evalFloat(args, 0) < evalFloat(args, 1));
/*     */       case SMALLER_OR_EQUAL:
/* 343 */         return (evalFloat(args, 0) <= evalFloat(args, 1));
/*     */       case EQUAL:
/* 345 */         return (evalFloat(args, 0) == evalFloat(args, 1));
/*     */       case NOT_EQUAL:
/* 347 */         return (evalFloat(args, 0) != evalFloat(args, 1));
/*     */       case BETWEEN:
/* 349 */         val = evalFloat(args, 0);
/* 350 */         return (val >= evalFloat(args, 1) && val <= evalFloat(args, 2));
/*     */       case EQUALS:
/* 352 */         diff = evalFloat(args, 0) - evalFloat(args, 1);
/* 353 */         delta = evalFloat(args, 2);
/* 354 */         return (Math.abs(diff) <= delta);
/*     */       case IN:
/* 356 */         valIn = evalFloat(args, 0);
/* 357 */         for (i = 1; i < args.length; i++) {
/*     */           
/* 359 */           float valCheck = evalFloat(args, i);
/* 360 */           if (valIn == valCheck) {
/* 361 */             return true;
/*     */           }
/*     */         } 
/* 364 */         return false;
/*     */     } 
/*     */     
/* 367 */     Config.warn("Unknown function type: " + this);
/* 368 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean evalBool(IExpression[] exprs, int index) {
/* 373 */     IExpressionBool eb = (IExpressionBool)exprs[index];
/* 374 */     boolean val = eb.eval();
/* 375 */     return val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FunctionType parse(String str) {
/* 383 */     for (int i = 0; i < VALUES.length; i++) {
/*     */       
/* 385 */       FunctionType ef = VALUES[i];
/* 386 */       if (ef.getName().equals(str)) {
/* 387 */         return ef;
/*     */       }
/*     */     } 
/* 390 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\net\optifine\entity\model\anim\FunctionType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */