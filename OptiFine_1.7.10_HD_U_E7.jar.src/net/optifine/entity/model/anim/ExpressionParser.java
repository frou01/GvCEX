/*     */ package net.optifine.entity.model.anim;
/*     */ 
/*     */ import Config;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Deque;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ public class ExpressionParser
/*     */ {
/*     */   private IExpressionResolver expressionResolver;
/*     */   
/*     */   public ExpressionParser(IExpressionResolver expressionResolver) {
/*  29 */     this.expressionResolver = expressionResolver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IExpressionFloat parseFloat(String str) throws ParseException {
/*  36 */     IExpression expr = parse(str);
/*  37 */     if (!(expr instanceof IExpressionFloat)) {
/*  38 */       throw new ParseException("Not a float expression: " + expr.getExpressionType());
/*     */     }
/*  40 */     return (IExpressionFloat)expr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IExpressionBool parseBool(String str) throws ParseException {
/*  47 */     IExpression expr = parse(str);
/*  48 */     if (!(expr instanceof IExpressionBool)) {
/*  49 */       throw new ParseException("Not a boolean expression: " + expr.getExpressionType());
/*     */     }
/*  51 */     return (IExpressionBool)expr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IExpression parse(String str) throws ParseException {
/*     */     try {
/*  60 */       Token[] tokens = TokenParser.parse(str);
/*  61 */       if (tokens == null) {
/*  62 */         return null;
/*     */       }
/*  64 */       Deque<Token> deque = new ArrayDeque<Token>(Arrays.asList(tokens));
/*     */       
/*  66 */       return parseInfix(deque);
/*     */     }
/*  68 */     catch (IOException e) {
/*     */       
/*  70 */       throw new ParseException(e.getMessage(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IExpression parseInfix(Deque<Token> deque) throws ParseException {
/*  81 */     if (deque.isEmpty()) {
/*  82 */       return null;
/*     */     }
/*  84 */     List<IExpression> listExpr = new LinkedList<IExpression>();
/*  85 */     List<Token> listOperTokens = new LinkedList<Token>();
/*     */     
/*  87 */     IExpression expr = parseExpression(deque);
/*  88 */     checkNull(expr, "Missing expression");
/*  89 */     listExpr.add(expr);
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/*  94 */       Token tokenOper = deque.poll();
/*  95 */       if (tokenOper == null) {
/*     */         break;
/*     */       }
/*  98 */       if (tokenOper.getType() != TokenType.OPERATOR) {
/*  99 */         throw new ParseException("Invalid operator: " + tokenOper);
/*     */       }
/* 101 */       IExpression expr2 = parseExpression(deque);
/* 102 */       checkNull(expr2, "Missing expression");
/*     */       
/* 104 */       listOperTokens.add(tokenOper);
/* 105 */       listExpr.add(expr2);
/*     */     } 
/*     */     
/* 108 */     return makeInfix(listExpr, listOperTokens);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IExpression makeInfix(List<IExpression> listExpr, List<Token> listOper) throws ParseException {
/* 120 */     List<FunctionType> listFunc = new LinkedList<FunctionType>();
/* 121 */     for (Iterator<Token> it = listOper.iterator(); it.hasNext(); ) {
/*     */       
/* 123 */       Token token = it.next();
/* 124 */       FunctionType type = FunctionType.parse(token.getText());
/* 125 */       checkNull(type, "Invalid operator: " + token);
/* 126 */       listFunc.add(type);
/*     */     } 
/*     */     
/* 129 */     return makeInfixFunc(listExpr, listFunc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IExpression makeInfixFunc(List<IExpression> listExpr, List<FunctionType> listFunc) throws ParseException {
/* 140 */     if (listExpr.size() != listFunc.size() + 1) {
/* 141 */       throw new ParseException("Invalid infix expression, expressions: " + listExpr.size() + ", operators: " + listFunc.size());
/*     */     }
/* 143 */     if (listExpr.size() == 1) {
/* 144 */       return listExpr.get(0);
/*     */     }
/* 146 */     int minPrecedence = Integer.MAX_VALUE;
/* 147 */     int maxPrecedence = Integer.MIN_VALUE;
/* 148 */     for (Iterator<FunctionType> it = listFunc.iterator(); it.hasNext(); ) {
/*     */       
/* 150 */       FunctionType type = it.next();
/* 151 */       minPrecedence = Math.min(type.getPrecedence(), minPrecedence);
/* 152 */       maxPrecedence = Math.max(type.getPrecedence(), maxPrecedence);
/*     */     } 
/*     */     
/* 155 */     if (maxPrecedence < minPrecedence || maxPrecedence - minPrecedence > 10) {
/* 156 */       throw new ParseException("Invalid infix precedence, min: " + minPrecedence + ", max: " + maxPrecedence);
/*     */     }
/* 158 */     for (int i = maxPrecedence; i >= minPrecedence; i--)
/*     */     {
/* 160 */       mergeOperators(listExpr, listFunc, i);
/*     */     }
/*     */     
/* 163 */     if (listExpr.size() != 1 || listFunc.size() != 0) {
/* 164 */       throw new ParseException("Error merging operators, expressions: " + listExpr.size() + ", operators: " + listFunc.size());
/*     */     }
/* 166 */     return listExpr.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void mergeOperators(List<IExpression> listExpr, List<FunctionType> listFuncs, int precedence) throws ParseException {
/* 176 */     for (int i = 0; i < listFuncs.size(); i++) {
/*     */       
/* 178 */       FunctionType type = listFuncs.get(i);
/*     */       
/* 180 */       if (type.getPrecedence() == precedence) {
/*     */ 
/*     */         
/* 183 */         listFuncs.remove(i);
/*     */         
/* 185 */         IExpression expr1 = listExpr.remove(i);
/* 186 */         IExpression expr2 = listExpr.remove(i);
/*     */         
/* 188 */         IExpression exprOper = makeFunction(type, new IExpression[] { expr1, expr2 });
/*     */         
/* 190 */         listExpr.add(i, exprOper);
/*     */         
/* 192 */         i--;
/*     */       } 
/*     */     } 
/*     */   }
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
/*     */   private IExpression parseExpression(Deque<Token> deque) throws ParseException {
/*     */     FunctionType type, operType;
/* 208 */     Token token = deque.poll();
/* 209 */     checkNull(token, "Missing expression");
/*     */     
/* 211 */     switch (token.getType()) {
/*     */       
/*     */       case NUMBER:
/* 214 */         return makeConstantFloat(token);
/*     */       case IDENTIFIER:
/* 216 */         type = getFunctionType(token, deque);
/* 217 */         if (type != null) {
/* 218 */           return makeFunction(type, deque);
/*     */         }
/* 220 */         return makeVariable(token);
/*     */       case BRACKET_OPEN:
/* 222 */         return makeBracketed(token, deque);
/*     */       
/*     */       case OPERATOR:
/* 225 */         operType = FunctionType.parse(token.getText());
/* 226 */         checkNull(operType, "Invalid operator: " + token);
/*     */         
/* 228 */         if (operType == FunctionType.PLUS) {
/* 229 */           return parseExpression(deque);
/*     */         }
/* 231 */         if (operType == FunctionType.MINUS) {
/*     */           
/* 233 */           IExpression exprNeg = parseExpression(deque);
/* 234 */           return makeFunction(FunctionType.NEG, new IExpression[] { exprNeg });
/*     */         } 
/*     */         
/* 237 */         if (operType == FunctionType.NOT) {
/*     */           
/* 239 */           IExpression exprNot = parseExpression(deque);
/* 240 */           return makeFunction(FunctionType.NOT, new IExpression[] { exprNot });
/*     */         } 
/*     */         break;
/*     */     } 
/* 244 */     throw new ParseException("Invalid expression: " + token);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static IExpression makeConstantFloat(Token token) throws ParseException {
/* 253 */     float val = Config.parseFloat(token.getText(), Float.NaN);
/*     */     
/* 255 */     if (val == Float.NaN) {
/* 256 */       throw new ParseException("Invalid float value: " + token);
/*     */     }
/* 258 */     return new ConstantFloat(val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private FunctionType getFunctionType(Token token, Deque<Token> deque) throws ParseException {
/* 270 */     Token tokenNext = deque.peek();
/*     */     
/* 272 */     if (tokenNext != null && tokenNext.getType() == TokenType.BRACKET_OPEN) {
/*     */ 
/*     */       
/* 275 */       FunctionType functionType = FunctionType.parse(token.getText());
/* 276 */       checkNull(functionType, "Unknown function: " + token);
/* 277 */       return functionType;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 282 */     FunctionType type = FunctionType.parse(token.getText());
/*     */     
/* 284 */     if (type == null) {
/* 285 */       return null;
/*     */     }
/* 287 */     if (type.getParameterCount(new IExpression[0]) > 0) {
/* 288 */       throw new ParseException("Missing arguments: " + type);
/*     */     }
/* 290 */     return type;
/*     */   }
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
/*     */   private IExpression makeFunction(FunctionType type, Deque<Token> deque) throws ParseException {
/* 303 */     if (type.getParameterCount(new IExpression[0]) == 0) {
/* 304 */       return makeFunction(type, new IExpression[0]);
/*     */     }
/* 306 */     Token tokenOpen = deque.poll();
/*     */     
/* 308 */     Deque<Token> dequeBracketed = getGroup(deque, TokenType.BRACKET_CLOSE, true);
/*     */     
/* 310 */     IExpression[] exprs = parseExpressions(dequeBracketed);
/*     */     
/* 312 */     return makeFunction(type, exprs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IExpression[] parseExpressions(Deque<Token> deque) throws ParseException {
/* 322 */     List<IExpression> list = new ArrayList<IExpression>();
/*     */     
/*     */     while (true) {
/* 325 */       Deque<Token> dequeArg = getGroup(deque, TokenType.COMMA, false);
/* 326 */       IExpression expr = parseInfix(dequeArg);
/* 327 */       if (expr == null) {
/*     */         break;
/*     */       }
/* 330 */       list.add(expr);
/*     */     } 
/*     */     
/* 333 */     IExpression[] exprs = list.<IExpression>toArray(new IExpression[list.size()]);
/*     */     
/* 335 */     return exprs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static IExpression makeFunction(FunctionType type, IExpression[] args) throws ParseException {
/* 347 */     ExpressionType[] funcParamTypes = type.getParameterTypes(args);
/*     */     
/* 349 */     if (args.length != funcParamTypes.length) {
/* 350 */       throw new ParseException("Invalid number of arguments, function: \"" + type.getName() + "\", count arguments: " + args.length + ", should be: " + funcParamTypes.length);
/*     */     }
/* 352 */     for (int i = 0; i < args.length; i++) {
/*     */       
/* 354 */       IExpression arg = args[i];
/* 355 */       ExpressionType argType = arg.getExpressionType();
/* 356 */       ExpressionType funcParamType = funcParamTypes[i];
/* 357 */       if (argType != funcParamType) {
/* 358 */         throw new ParseException("Invalid argument type, function: \"" + type.getName() + "\", index: " + i + ", type: " + argType + ", should be: " + funcParamType);
/*     */       }
/*     */     } 
/* 361 */     if (type.getExpressionType() == ExpressionType.FLOAT) {
/* 362 */       return new FunctionFloat(type, args);
/*     */     }
/* 364 */     if (type.getExpressionType() == ExpressionType.BOOL) {
/* 365 */       return new FunctionBool(type, args);
/*     */     }
/* 367 */     throw new ParseException("Unknown function type: " + type.getExpressionType() + ", function: " + type.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IExpression makeVariable(Token token) throws ParseException {
/* 376 */     if (this.expressionResolver == null) {
/* 377 */       throw new ParseException("Model variable not found: " + token);
/*     */     }
/* 379 */     IExpression expr = this.expressionResolver.getExpression(token.getText());
/* 380 */     if (expr == null) {
/* 381 */       throw new ParseException("Model variable not found: " + token);
/*     */     }
/* 383 */     return expr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IExpression makeBracketed(Token token, Deque<Token> deque) throws ParseException {
/* 394 */     Deque<Token> dequeBracketed = getGroup(deque, TokenType.BRACKET_CLOSE, true);
/*     */     
/* 396 */     return parseInfix(dequeBracketed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Deque<Token> getGroup(Deque<Token> deque, TokenType tokenTypeEnd, boolean tokenEndRequired) throws ParseException {
/* 406 */     Deque<Token> dequeGroup = new ArrayDeque<Token>();
/* 407 */     int level = 0;
/* 408 */     for (Iterator<Token> it = deque.iterator(); it.hasNext(); ) {
/*     */       
/* 410 */       Token token = it.next();
/*     */       
/* 412 */       it.remove();
/*     */       
/* 414 */       if (level == 0 && token.getType() == tokenTypeEnd)
/*     */       {
/*     */         
/* 417 */         return dequeGroup;
/*     */       }
/*     */       
/* 420 */       dequeGroup.add(token);
/*     */       
/* 422 */       if (token.getType() == TokenType.BRACKET_OPEN)
/* 423 */         level++; 
/* 424 */       if (token.getType() == TokenType.BRACKET_CLOSE) {
/* 425 */         level--;
/*     */       }
/*     */     } 
/* 428 */     if (tokenEndRequired) {
/* 429 */       throw new ParseException("Missing end token: " + tokenTypeEnd);
/*     */     }
/* 431 */     return dequeGroup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkNull(Object obj, String message) throws ParseException {
/* 440 */     if (obj == null)
/* 441 */       throw new ParseException(message); 
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\net\optifine\entity\model\anim\ExpressionParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */