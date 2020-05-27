/*     */ package shadersmod.client;
/*     */ 
/*     */ import Config;
/*     */ import StrUtils;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.CharArrayReader;
/*     */ import java.io.CharArrayWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.optifine.entity.model.anim.ExpressionParser;
/*     */ import net.optifine.entity.model.anim.ExpressionType;
/*     */ import net.optifine.entity.model.anim.IExpression;
/*     */ import net.optifine.entity.model.anim.IExpressionResolver;
/*     */ import net.optifine.entity.model.anim.ParseException;
/*     */ import shadersmod.common.SMCLog;
/*     */ import shadersmod.uniform.CustomUniform;
/*     */ import shadersmod.uniform.CustomUniforms;
/*     */ import shadersmod.uniform.ShaderExpressionResolver;
/*     */ import shadersmod.uniform.UniformType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShaderPackParser
/*     */ {
/*  42 */   private static final Pattern PATTERN_VERSION = Pattern.compile("^\\s*#version\\s+.*$");
/*  43 */   private static final Pattern PATTERN_INCLUDE = Pattern.compile("^\\s*#include\\s+\"([A-Za-z0-9_/\\.]+)\".*$");
/*     */   
/*  45 */   private static final Set<String> setConstNames = makeSetConstNames();
/*     */ 
/*     */ 
/*     */   
/*     */   public static ShaderOption[] parseShaderPackOptions(IShaderPack shaderPack, String[] programNames, List<Integer> listDimensions) {
/*  50 */     if (shaderPack == null) {
/*  51 */       return new ShaderOption[0];
/*     */     }
/*  53 */     Map<String, ShaderOption> mapOptions = new HashMap<String, ShaderOption>();
/*     */     
/*  55 */     collectShaderOptions(shaderPack, "/shaders", programNames, mapOptions);
/*     */     
/*  57 */     for (Iterator<Integer> it = listDimensions.iterator(); it.hasNext(); ) {
/*     */       
/*  59 */       int dimId = ((Integer)it.next()).intValue();
/*  60 */       String dirWorld = "/shaders/world" + dimId;
/*  61 */       collectShaderOptions(shaderPack, dirWorld, programNames, mapOptions);
/*     */     } 
/*     */     
/*  64 */     Collection<ShaderOption> options = mapOptions.values();
/*  65 */     ShaderOption[] sos = options.<ShaderOption>toArray(new ShaderOption[options.size()]);
/*     */     
/*  67 */     Comparator<ShaderOption> comp = new Comparator<ShaderOption>()
/*     */       {
/*     */         public int compare(ShaderOption o1, ShaderOption o2)
/*     */         {
/*  71 */           return o1.getName().compareToIgnoreCase(o2.getName());
/*     */         }
/*     */       };
/*  74 */     Arrays.sort(sos, comp);
/*     */     
/*  76 */     return sos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void collectShaderOptions(IShaderPack shaderPack, String dir, String[] programNames, Map<String, ShaderOption> mapOptions) {
/*  87 */     for (int i = 0; i < programNames.length; i++) {
/*     */       
/*  89 */       String programName = programNames[i];
/*     */       
/*  91 */       if (!programName.equals("")) {
/*     */ 
/*     */         
/*  94 */         String vsh = dir + "/" + programName + ".vsh";
/*  95 */         String fsh = dir + "/" + programName + ".fsh";
/*     */         
/*  97 */         collectShaderOptions(shaderPack, vsh, mapOptions);
/*  98 */         collectShaderOptions(shaderPack, fsh, mapOptions);
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
/*     */   private static void collectShaderOptions(IShaderPack sp, String path, Map<String, ShaderOption> mapOptions) {
/* 110 */     String[] lines = getLines(sp, path);
/* 111 */     for (int i = 0; i < lines.length; i++) {
/*     */       
/* 113 */       String line = lines[i];
/* 114 */       ShaderOption so = getShaderOption(line, path);
/* 115 */       if (so != null)
/*     */       {
/*     */         
/* 118 */         if (!so.getName().startsWith(ShaderMacros.getPrefixMacro()))
/*     */         {
/*     */           
/* 121 */           if (!so.checkUsed() || isOptionUsed(so, lines)) {
/*     */ 
/*     */             
/* 124 */             String key = so.getName();
/* 125 */             ShaderOption so2 = mapOptions.get(key);
/* 126 */             if (so2 != null) {
/*     */ 
/*     */               
/* 129 */               if (!Config.equals(so2.getValueDefault(), so.getValueDefault())) {
/*     */                 
/* 131 */                 Config.warn("Ambiguous shader option: " + so.getName());
/* 132 */                 Config.warn(" - in " + Config.arrayToString((Object[])so2.getPaths()) + ": " + so2.getValueDefault());
/* 133 */                 Config.warn(" - in " + Config.arrayToString((Object[])so.getPaths()) + ": " + so.getValueDefault());
/*     */                 
/* 135 */                 so2.setEnabled(false);
/*     */               } 
/*     */               
/* 138 */               if (so2.getDescription() == null || so2.getDescription().length() <= 0) {
/* 139 */                 so2.setDescription(so.getDescription());
/*     */               }
/* 141 */               so2.addPaths(so.getPaths());
/*     */             
/*     */             }
/*     */             else {
/*     */               
/* 146 */               mapOptions.put(key, so);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isOptionUsed(ShaderOption so, String[] lines) {
/* 157 */     for (int i = 0; i < lines.length; ) {
/*     */       
/* 159 */       String line = lines[i];
/*     */       
/* 161 */       if (!so.isUsedInLine(line)) {
/*     */         i++; continue;
/*     */       } 
/* 164 */       return true;
/*     */     } 
/*     */     
/* 167 */     return false;
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
/*     */   private static String[] getLines(IShaderPack sp, String path) {
/*     */     try {
/* 180 */       List<String> listFiles = new ArrayList<String>();
/*     */       
/* 182 */       String str = loadFile(path, sp, 0, listFiles, 0);
/*     */       
/* 184 */       if (str == null) {
/* 185 */         return new String[0];
/*     */       }
/* 187 */       ByteArrayInputStream is = new ByteArrayInputStream(str.getBytes());
/*     */       
/* 189 */       String[] lines = Config.readLines(is);
/*     */       
/* 191 */       return lines;
/*     */     }
/* 193 */     catch (IOException e) {
/*     */       
/* 195 */       Config.dbg(e.getClass().getName() + ": " + e.getMessage());
/* 196 */       return new String[0];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ShaderOption getShaderOption(String line, String path) {
/* 206 */     ShaderOption so = null;
/*     */     
/* 208 */     if (so == null)
/* 209 */       so = ShaderOptionSwitch.parseOption(line, path); 
/* 210 */     if (so == null) {
/* 211 */       so = ShaderOptionVariable.parseOption(line, path);
/*     */     }
/* 213 */     if (so != null) {
/* 214 */       return so;
/*     */     }
/* 216 */     if (so == null)
/* 217 */       so = ShaderOptionSwitchConst.parseOption(line, path); 
/* 218 */     if (so == null) {
/* 219 */       so = ShaderOptionVariableConst.parseOption(line, path);
/*     */     }
/* 221 */     if (so != null)
/*     */     {
/*     */       
/* 224 */       if (setConstNames.contains(so.getName())) {
/* 225 */         return so;
/*     */       }
/*     */     }
/* 228 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Set<String> makeSetConstNames() {
/* 233 */     Set<String> set = new HashSet<String>();
/*     */     
/* 235 */     set.add("shadowMapResolution");
/* 236 */     set.add("shadowMapFov");
/* 237 */     set.add("shadowDistance");
/* 238 */     set.add("shadowDistanceRenderMul");
/* 239 */     set.add("shadowIntervalSize");
/* 240 */     set.add("generateShadowMipmap");
/* 241 */     set.add("generateShadowColorMipmap");
/* 242 */     set.add("shadowHardwareFiltering");
/* 243 */     set.add("shadowHardwareFiltering0");
/* 244 */     set.add("shadowHardwareFiltering1");
/* 245 */     set.add("shadowtex0Mipmap");
/* 246 */     set.add("shadowtexMipmap");
/* 247 */     set.add("shadowtex1Mipmap");
/* 248 */     set.add("shadowcolor0Mipmap");
/* 249 */     set.add("shadowColor0Mipmap");
/* 250 */     set.add("shadowcolor1Mipmap");
/* 251 */     set.add("shadowColor1Mipmap");
/* 252 */     set.add("shadowtex0Nearest");
/* 253 */     set.add("shadowtexNearest");
/* 254 */     set.add("shadow0MinMagNearest");
/* 255 */     set.add("shadowtex1Nearest");
/* 256 */     set.add("shadow1MinMagNearest");
/* 257 */     set.add("shadowcolor0Nearest");
/* 258 */     set.add("shadowColor0Nearest");
/* 259 */     set.add("shadowColor0MinMagNearest");
/* 260 */     set.add("shadowcolor1Nearest");
/* 261 */     set.add("shadowColor1Nearest");
/* 262 */     set.add("shadowColor1MinMagNearest");
/* 263 */     set.add("wetnessHalflife");
/* 264 */     set.add("drynessHalflife");
/* 265 */     set.add("eyeBrightnessHalflife");
/* 266 */     set.add("centerDepthHalflife");
/* 267 */     set.add("sunPathRotation");
/* 268 */     set.add("ambientOcclusionLevel");
/* 269 */     set.add("superSamplingLevel");
/* 270 */     set.add("noiseTextureResolution");
/*     */     
/* 272 */     return set;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ShaderProfile[] parseProfiles(Properties props, ShaderOption[] shaderOptions) {
/* 281 */     String PREFIX_PROFILE = "profile.";
/*     */     
/* 283 */     List<ShaderProfile> list = new ArrayList<ShaderProfile>();
/* 284 */     Set keys = props.keySet();
/* 285 */     for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
/*     */       
/* 287 */       String key = it.next();
/*     */       
/* 289 */       if (!key.startsWith(PREFIX_PROFILE)) {
/*     */         continue;
/*     */       }
/* 292 */       String name = key.substring(PREFIX_PROFILE.length());
/* 293 */       String val = props.getProperty(key);
/*     */       
/* 295 */       Set<String> parsedProfiles = new HashSet<String>();
/* 296 */       ShaderProfile p = parseProfile(name, props, parsedProfiles, shaderOptions);
/*     */       
/* 298 */       if (p == null) {
/*     */         continue;
/*     */       }
/* 301 */       list.add(p);
/*     */     } 
/*     */     
/* 304 */     if (list.size() <= 0) {
/* 305 */       return null;
/*     */     }
/* 307 */     ShaderProfile[] profs = list.<ShaderProfile>toArray(new ShaderProfile[list.size()]);
/*     */     
/* 309 */     return profs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<String> parseOptionSliders(Properties props, ShaderOption[] shaderOptions) {
/* 317 */     Set<String> sliders = new HashSet<String>();
/* 318 */     String value = props.getProperty("sliders");
/* 319 */     if (value == null) {
/* 320 */       return sliders;
/*     */     }
/* 322 */     String[] names = Config.tokenize(value, " ");
/* 323 */     for (int i = 0; i < names.length; i++) {
/*     */       
/* 325 */       String name = names[i];
/*     */       
/* 327 */       ShaderOption so = ShaderUtils.getShaderOption(name, shaderOptions);
/* 328 */       if (so == null) {
/*     */         
/* 330 */         Config.warn("Invalid shader option: " + name);
/*     */       }
/*     */       else {
/*     */         
/* 334 */         sliders.add(name);
/*     */       } 
/*     */     } 
/* 337 */     return sliders;
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
/*     */   private static ShaderProfile parseProfile(String name, Properties props, Set<String> parsedProfiles, ShaderOption[] shaderOptions) {
/* 349 */     String PREFIX_PROFILE = "profile.";
/* 350 */     String key = PREFIX_PROFILE + name;
/*     */     
/* 352 */     if (parsedProfiles.contains(key)) {
/*     */       
/* 354 */       Config.warn("[Shaders] Profile already parsed: " + name);
/* 355 */       return null;
/*     */     } 
/*     */     
/* 358 */     parsedProfiles.add(name);
/*     */     
/* 360 */     ShaderProfile prof = new ShaderProfile(name);
/*     */     
/* 362 */     String val = props.getProperty(key);
/* 363 */     String[] parts = Config.tokenize(val, " ");
/* 364 */     for (int i = 0; i < parts.length; i++) {
/*     */       
/* 366 */       String part = parts[i];
/*     */       
/* 368 */       if (part.startsWith(PREFIX_PROFILE)) {
/*     */         
/* 370 */         String nameParent = part.substring(PREFIX_PROFILE.length());
/* 371 */         ShaderProfile profParent = parseProfile(nameParent, props, parsedProfiles, shaderOptions);
/* 372 */         if (prof != null)
/*     */         {
/*     */           
/* 375 */           prof.addOptionValues(profParent);
/* 376 */           prof.addDisabledPrograms(profParent.getDisabledPrograms());
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 381 */         String[] tokens = Config.tokenize(part, ":=");
/*     */         
/* 383 */         if (tokens.length == 1) {
/*     */           
/* 385 */           String option = tokens[0];
/* 386 */           boolean on = true;
/* 387 */           if (option.startsWith("!")) {
/*     */             
/* 389 */             on = false;
/* 390 */             option = option.substring(1);
/*     */           } 
/*     */           
/* 393 */           String PREFIX_PROGRAM = "program.";
/* 394 */           if (option.startsWith(PREFIX_PROGRAM)) {
/*     */             
/* 396 */             String program = option.substring(PREFIX_PROGRAM.length());
/* 397 */             if (!Shaders.isProgramPath(program)) {
/*     */               
/* 399 */               Config.warn("Invalid program: " + program + " in profile: " + prof.getName());
/*     */ 
/*     */             
/*     */             }
/* 403 */             else if (on) {
/* 404 */               prof.removeDisabledProgram(program);
/*     */             } else {
/* 406 */               prof.addDisabledProgram(program);
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 411 */             ShaderOption so = ShaderUtils.getShaderOption(option, shaderOptions);
/*     */             
/* 413 */             if (!(so instanceof ShaderOptionSwitch))
/*     */             {
/* 415 */               Config.warn("[Shaders] Invalid option: " + option);
/*     */             }
/*     */             else
/*     */             {
/* 419 */               prof.addOptionValue(option, String.valueOf(on));
/*     */               
/* 421 */               so.setVisible(true);
/*     */             }
/*     */           
/*     */           }
/*     */         
/* 426 */         } else if (tokens.length != 2) {
/*     */           
/* 428 */           Config.warn("[Shaders] Invalid option value: " + part);
/*     */         }
/*     */         else {
/*     */           
/* 432 */           String option = tokens[0];
/* 433 */           String value = tokens[1];
/*     */           
/* 435 */           ShaderOption so = ShaderUtils.getShaderOption(option, shaderOptions);
/* 436 */           if (so == null)
/*     */           
/* 438 */           { Config.warn("[Shaders] Invalid option: " + part);
/*     */             
/*     */              }
/*     */           
/* 442 */           else if (!so.isValidValue(value))
/*     */           
/* 444 */           { Config.warn("[Shaders] Invalid value: " + part); }
/*     */           
/*     */           else
/*     */           
/* 448 */           { so.setVisible(true);
/*     */             
/* 450 */             prof.addOptionValue(option, value); } 
/*     */         } 
/*     */       } 
/* 453 */     }  return prof;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Map<String, ScreenShaderOptions> parseGuiScreens(Properties props, ShaderProfile[] shaderProfiles, ShaderOption[] shaderOptions) {
/* 458 */     Map<String, ScreenShaderOptions> map = new HashMap<String, ScreenShaderOptions>();
/*     */     
/* 460 */     parseGuiScreen("screen", props, map, shaderProfiles, shaderOptions);
/*     */     
/* 462 */     if (map.isEmpty()) {
/* 463 */       return null;
/*     */     }
/* 465 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean parseGuiScreen(String key, Properties props, Map<String, ScreenShaderOptions> map, ShaderProfile[] shaderProfiles, ShaderOption[] shaderOptions) {
/* 471 */     String val = props.getProperty(key);
/*     */     
/* 473 */     if (val == null) {
/* 474 */       return false;
/*     */     }
/* 476 */     List<ShaderOption> list = new ArrayList<ShaderOption>();
/* 477 */     Set<String> setNames = new HashSet<String>();
/*     */     
/* 479 */     String[] opNames = Config.tokenize(val, " ");
/* 480 */     for (int i = 0; i < opNames.length; i++) {
/*     */       
/* 482 */       String opName = opNames[i];
/*     */       
/* 484 */       if (opName.equals("<empty>")) {
/*     */         
/* 486 */         list.add(null);
/*     */ 
/*     */       
/*     */       }
/* 490 */       else if (setNames.contains(opName)) {
/*     */         
/* 492 */         Config.warn("[Shaders] Duplicate option: " + opName + ", key: " + key);
/*     */       }
/*     */       else {
/*     */         
/* 496 */         setNames.add(opName);
/*     */         
/* 498 */         if (opName.equals("<profile>")) {
/*     */ 
/*     */           
/* 501 */           if (shaderProfiles == null) {
/*     */             
/* 503 */             Config.warn("[Shaders] Option profile can not be used, no profiles defined: " + opName + ", key: " + key);
/*     */           } else {
/*     */             
/* 506 */             ShaderOptionProfile optionProfile = new ShaderOptionProfile(shaderProfiles, shaderOptions);
/* 507 */             list.add(optionProfile);
/*     */           }
/*     */         
/*     */         }
/* 511 */         else if (opName.equals("*")) {
/*     */           
/* 513 */           ShaderOption soRest = new ShaderOptionRest("<rest>");
/* 514 */           list.add(soRest);
/*     */ 
/*     */         
/*     */         }
/* 518 */         else if (opName.startsWith("[") && opName.endsWith("]")) {
/*     */           
/* 520 */           String screen = StrUtils.removePrefixSuffix(opName, "[", "]");
/*     */           
/* 522 */           if (!screen.matches("^[a-zA-Z0-9_]+$")) {
/*     */             
/* 524 */             Config.warn("[Shaders] Invalid screen: " + opName + ", key: " + key);
/*     */ 
/*     */           
/*     */           }
/* 528 */           else if (!parseGuiScreen("screen." + screen, props, map, shaderProfiles, shaderOptions)) {
/*     */             
/* 530 */             Config.warn("[Shaders] Invalid screen: " + opName + ", key: " + key);
/*     */           } else {
/*     */             
/* 533 */             ShaderOptionScreen optionScreen = new ShaderOptionScreen(screen);
/* 534 */             list.add(optionScreen);
/*     */           } 
/*     */         } else {
/*     */           
/* 538 */           ShaderOption so = ShaderUtils.getShaderOption(opName, shaderOptions);
/* 539 */           if (so == null)
/*     */           
/* 541 */           { Config.warn("[Shaders] Invalid option: " + opName + ", key: " + key);
/*     */             
/* 543 */             list.add(null); }
/*     */           
/*     */           else
/*     */           
/* 547 */           { so.setVisible(true);
/*     */             
/* 549 */             list.add(so); } 
/*     */         } 
/*     */       } 
/* 552 */     }  ShaderOption[] scrOps = list.<ShaderOption>toArray(new ShaderOption[list.size()]);
/*     */     
/* 554 */     String colStr = props.getProperty(key + ".columns");
/* 555 */     int columns = Config.parseInt(colStr, 2);
/*     */     
/* 557 */     ScreenShaderOptions sso = new ScreenShaderOptions(key, scrOps, columns);
/*     */     
/* 559 */     map.put(key, sso);
/*     */     
/* 561 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedReader resolveIncludes(BufferedReader reader, String filePath, IShaderPack shaderPack, int fileIndex, List<String> listFiles, int includeLevel) throws IOException {
/* 572 */     String fileDir = "/";
/* 573 */     int pos = filePath.lastIndexOf("/");
/* 574 */     if (pos >= 0) {
/* 575 */       fileDir = filePath.substring(0, pos);
/*     */     }
/* 577 */     CharArrayWriter caw = new CharArrayWriter();
/* 578 */     int macroInsertPosition = -1;
/* 579 */     Set<String> setExtensions = new LinkedHashSet<String>();
/*     */     
/* 581 */     int lineNumber = 1;
/*     */     
/*     */     while (true) {
/* 584 */       String line = reader.readLine();
/* 585 */       if (line == null) {
/*     */         break;
/*     */       }
/* 588 */       if (macroInsertPosition < 0) {
/*     */         
/* 590 */         Matcher mv = PATTERN_VERSION.matcher(line);
/* 591 */         if (mv.matches()) {
/*     */           
/* 593 */           String strDef = ShaderMacros.getMacroLines();
/*     */           
/* 595 */           String lineA = line + "\n" + strDef;
/* 596 */           String lineB = "#line " + (lineNumber + 1) + " " + fileIndex;
/* 597 */           line = lineA + lineB;
/*     */           
/* 599 */           macroInsertPosition = caw.size() + lineA.length();
/*     */         } 
/*     */       } 
/*     */       
/* 603 */       Matcher mi = PATTERN_INCLUDE.matcher(line);
/* 604 */       if (mi.matches()) {
/*     */         
/* 606 */         String fileInc = mi.group(1);
/*     */         
/* 608 */         boolean absolute = fileInc.startsWith("/");
/* 609 */         String filePathInc = absolute ? ("/shaders" + fileInc) : (fileDir + "/" + fileInc);
/*     */         
/* 611 */         if (!listFiles.contains(filePathInc)) {
/* 612 */           listFiles.add(filePathInc);
/*     */         }
/* 614 */         int includeFileIndex = listFiles.indexOf(filePathInc) + 1;
/*     */         
/* 616 */         line = loadFile(filePathInc, shaderPack, includeFileIndex, listFiles, includeLevel);
/*     */         
/* 618 */         if (line == null) {
/* 619 */           throw new IOException("Included file not found: " + filePath);
/*     */         }
/* 621 */         if (line.endsWith("\n")) {
/* 622 */           line = line.substring(0, line.length() - 1);
/*     */         }
/* 624 */         line = "#line 1 " + includeFileIndex + "\n" + line + "\n" + "#line " + (lineNumber + 1) + " " + fileIndex;
/*     */       } 
/*     */       
/* 627 */       if (macroInsertPosition >= 0)
/*     */       {
/* 629 */         if (line.contains(ShaderMacros.getPrefixMacro())) {
/*     */           
/* 631 */           String[] lineExts = findExtensions(line, ShaderMacros.getExtensions());
/* 632 */           for (int i = 0; i < lineExts.length; i++) {
/*     */             
/* 634 */             String ext = lineExts[i];
/* 635 */             setExtensions.add(ext);
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 640 */       caw.write(line);
/* 641 */       caw.write("\n");
/*     */       
/* 643 */       lineNumber++;
/*     */     } 
/*     */ 
/*     */     
/* 647 */     char[] chars = caw.toCharArray();
/*     */     
/* 649 */     if (macroInsertPosition >= 0 && setExtensions.size() > 0) {
/*     */       
/* 651 */       StringBuilder sbExt = new StringBuilder();
/* 652 */       for (Iterator<String> it = setExtensions.iterator(); it.hasNext(); ) {
/*     */         
/* 654 */         String ext = it.next();
/* 655 */         sbExt.append("#define ");
/* 656 */         sbExt.append(ext);
/* 657 */         sbExt.append("\n");
/*     */       } 
/* 659 */       String strExt = sbExt.toString();
/*     */       
/* 661 */       StringBuilder sbAll = new StringBuilder(new String(chars));
/* 662 */       sbAll.insert(macroInsertPosition, strExt);
/* 663 */       String strAll = sbAll.toString();
/*     */       
/* 665 */       chars = strAll.toCharArray();
/*     */     } 
/*     */     
/* 668 */     CharArrayReader car = new CharArrayReader(chars);
/* 669 */     return new BufferedReader(car);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] findExtensions(String line, String[] extensions) {
/* 679 */     List<String> list = new ArrayList<String>();
/*     */     
/* 681 */     for (int i = 0; i < extensions.length; i++) {
/*     */       
/* 683 */       String ext = extensions[i];
/* 684 */       if (line.contains(ext)) {
/* 685 */         list.add(ext);
/*     */       }
/*     */     } 
/* 688 */     String[] exts = list.<String>toArray(new String[list.size()]);
/*     */     
/* 690 */     return exts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String loadFile(String filePath, IShaderPack shaderPack, int fileIndex, List<String> listFiles, int includeLevel) throws IOException {
/* 701 */     if (includeLevel >= 10) {
/* 702 */       throw new IOException("#include depth exceeded: " + includeLevel + ", file: " + filePath);
/*     */     }
/* 704 */     includeLevel++;
/*     */     
/* 706 */     InputStream in = shaderPack.getResourceAsStream(filePath);
/* 707 */     if (in == null) {
/* 708 */       return null;
/*     */     }
/* 710 */     InputStreamReader isr = new InputStreamReader(in, "ASCII");
/* 711 */     BufferedReader br = new BufferedReader(isr);
/*     */     
/* 713 */     br = resolveIncludes(br, filePath, shaderPack, fileIndex, listFiles, includeLevel);
/*     */     
/* 715 */     CharArrayWriter caw = new CharArrayWriter();
/*     */     
/*     */     while (true) {
/* 718 */       String line = br.readLine();
/* 719 */       if (line == null) {
/*     */         break;
/*     */       }
/* 722 */       caw.write(line);
/* 723 */       caw.write("\n");
/*     */     } 
/*     */ 
/*     */     
/* 727 */     return caw.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CustomUniforms parseCustomUniforms(Properties props) {
/* 736 */     String UNIFORM = "uniform";
/* 737 */     String VARIABLE = "variable";
/* 738 */     String PREFIX_UNIFORM = UNIFORM + ".";
/* 739 */     String PREFIX_VARIABLE = VARIABLE + ".";
/*     */     
/* 741 */     Map<String, IExpression> mapExpressions = new HashMap<String, IExpression>();
/* 742 */     List<CustomUniform> listUniforms = new ArrayList<CustomUniform>();
/*     */     
/* 744 */     Set keys = props.keySet();
/* 745 */     for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
/*     */       
/* 747 */       String key = it.next();
/*     */       
/* 749 */       String[] keyParts = Config.tokenize(key, ".");
/* 750 */       if (keyParts.length != 3)
/*     */         continue; 
/* 752 */       String kind = keyParts[0];
/* 753 */       String type = keyParts[1];
/* 754 */       String name = keyParts[2];
/*     */       
/* 756 */       String src = props.getProperty(key).trim();
/*     */       
/* 758 */       if (mapExpressions.containsKey(name)) {
/*     */         
/* 760 */         SMCLog.warning("Expression already defined: " + name);
/*     */         
/*     */         continue;
/*     */       } 
/* 764 */       if (!kind.equals(UNIFORM) && !kind.equals(VARIABLE)) {
/*     */         continue;
/*     */       }
/* 767 */       SMCLog.info("Custom " + kind + ": " + name);
/*     */       
/* 769 */       CustomUniform cu = parseCustomUniform(kind, name, type, src, mapExpressions);
/* 770 */       if (cu == null) {
/*     */         continue;
/*     */       }
/* 773 */       mapExpressions.put(name, cu.getExpression());
/*     */       
/* 775 */       if (kind.equals(VARIABLE)) {
/*     */         continue;
/*     */       }
/* 778 */       listUniforms.add(cu);
/*     */     } 
/*     */     
/* 781 */     if (listUniforms.size() <= 0) {
/* 782 */       return null;
/*     */     }
/* 784 */     CustomUniform[] cusArr = listUniforms.<CustomUniform>toArray(new CustomUniform[listUniforms.size()]);
/*     */     
/* 786 */     CustomUniforms cus = new CustomUniforms(cusArr);
/*     */     
/* 788 */     return cus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static CustomUniform parseCustomUniform(String kind, String name, String type, String src, Map<String, IExpression> mapExpressions) {
/*     */     try {
/* 799 */       UniformType uniformType = UniformType.parse(type);
/* 800 */       if (uniformType == null) {
/*     */         
/* 802 */         SMCLog.warning("Unknown " + kind + " type: " + uniformType);
/* 803 */         return null;
/*     */       } 
/* 805 */       ShaderExpressionResolver resolver = new ShaderExpressionResolver(mapExpressions);
/* 806 */       ExpressionParser parser = new ExpressionParser((IExpressionResolver)resolver);
/* 807 */       IExpression expr = parser.parse(src);
/* 808 */       ExpressionType expressionType = expr.getExpressionType();
/* 809 */       if (!uniformType.matchesExpressionType(expressionType)) {
/*     */         
/* 811 */         SMCLog.warning("Expression type does not match " + kind + " type, expression: " + expressionType + ", " + kind + ": " + uniformType + " " + name);
/* 812 */         return null;
/*     */       } 
/* 814 */       CustomUniform cu = new CustomUniform(name, uniformType, expr);
/*     */       
/* 816 */       return cu;
/*     */     }
/* 818 */     catch (ParseException e) {
/*     */       
/* 820 */       SMCLog.warning(e.getClass().getName() + ": " + e.getMessage());
/* 821 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\ShaderPackParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */