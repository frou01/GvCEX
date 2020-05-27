/*      */ import java.awt.Dimension;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.lang.reflect.Array;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.imageio.ImageIO;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.lwjgl.LWJGLException;
/*      */ import org.lwjgl.Sys;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import org.lwjgl.opengl.DisplayMode;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GL30;
/*      */ import org.lwjgl.opengl.GLContext;
/*      */ import org.lwjgl.opengl.PixelFormat;
/*      */ import org.lwjgl.util.glu.GLU;
/*      */ import shadersmod.client.Shaders;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Config
/*      */ {
/*      */   public static final String OF_NAME = "OptiFine";
/*      */   public static final String MC_VERSION = "1.7.10";
/*      */   public static final String OF_EDITION = "HD_U";
/*      */   public static final String OF_RELEASE = "E7";
/*      */   public static final String VERSION = "OptiFine_1.7.10_HD_U_E7";
/*   89 */   private static String newRelease = null;
/*      */   
/*      */   private static boolean notify64BitJava = false;
/*   92 */   public static String openGlVersion = null;
/*   93 */   public static String openGlRenderer = null;
/*   94 */   public static String openGlVendor = null;
/*   95 */   public static String[] openGlExtensions = null;
/*      */   
/*   97 */   public static GlVersion glVersion = null;
/*   98 */   public static GlVersion glslVersion = null;
/*   99 */   public static int minecraftVersionInt = -1;
/*      */   
/*      */   public static boolean fancyFogAvailable = false;
/*      */   
/*      */   public static boolean occlusionAvailable = false;
/*  104 */   private static bbj gameSettings = null;
/*      */   
/*  106 */   private static bao minecraft = bao.B();
/*      */   
/*      */   private static boolean initialized = false;
/*      */   
/*  110 */   private static Thread minecraftThread = null;
/*      */   
/*  112 */   private static DisplayMode desktopDisplayMode = null;
/*      */   
/*  114 */   private static DisplayMode[] displayModes = null;
/*      */   
/*  116 */   private static int antialiasingLevel = 0;
/*      */   
/*  118 */   private static int availableProcessors = 0;
/*      */   
/*      */   public static boolean zoomMode = false;
/*      */   
/*  122 */   private static int texturePackClouds = 0;
/*      */   
/*      */   public static boolean waterOpacityChanged = false;
/*      */   
/*      */   private static boolean fullscreenModeChecked = false;
/*      */   
/*      */   private static boolean desktopModeChecked = false;
/*  129 */   private static bqp defaultResourcePackLazy = null;
/*      */   
/*  131 */   public static final Float DEF_ALPHA_FUNC_LEVEL = Float.valueOf(0.1F);
/*      */   
/*  133 */   private static final Logger LOGGER = LogManager.getLogger();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float renderPartialTicks;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getVersion() {
/*  149 */     return "OptiFine_1.7.10_HD_U_E7";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getVersionDebug() {
/*  157 */     StringBuffer sb = new StringBuffer(32);
/*      */     
/*  159 */     if (isDynamicLights()) {
/*      */       
/*  161 */       sb.append("DL: ");
/*  162 */       sb.append(String.valueOf(DynamicLights.getCount()));
/*  163 */       sb.append(", ");
/*      */     } 
/*      */     
/*  166 */     sb.append("OptiFine_1.7.10_HD_U_E7");
/*      */     
/*  168 */     String shaderPack = Shaders.getShaderPackName();
/*  169 */     if (shaderPack != null) {
/*      */       
/*  171 */       sb.append(", ");
/*  172 */       sb.append(shaderPack);
/*      */     } 
/*      */     
/*  175 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void initGameSettings(bbj settings) {
/*  185 */     if (gameSettings != null) {
/*      */       return;
/*      */     }
/*  188 */     gameSettings = settings;
/*      */     
/*  190 */     desktopDisplayMode = Display.getDesktopDisplayMode();
/*      */     
/*  192 */     updateAvailableProcessors();
/*      */     
/*  194 */     ReflectorForge.putLaunchBlackboard("optifine.ForgeSplashCompatible", Boolean.TRUE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void initDisplay() {
/*  204 */     checkInitialized();
/*      */     
/*  206 */     antialiasingLevel = gameSettings.ofAaLevel;
/*      */     
/*  208 */     checkDisplaySettings();
/*      */     
/*  210 */     checkDisplayMode();
/*      */     
/*  212 */     minecraftThread = Thread.currentThread();
/*      */     
/*  214 */     updateThreadPriorities();
/*      */     
/*  216 */     Shaders.startup(bao.B());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void checkInitialized() {
/*  225 */     if (initialized) {
/*      */       return;
/*      */     }
/*  228 */     if (!Display.isCreated()) {
/*      */       return;
/*      */     }
/*  231 */     initialized = true;
/*      */     
/*  233 */     checkOpenGlCaps();
/*      */     
/*  235 */     startVersionCheckThread();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkOpenGlCaps() {
/*  244 */     log("");
/*  245 */     log(getVersion());
/*  246 */     log("Build: " + getBuild());
/*  247 */     log("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"));
/*  248 */     log("Java: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
/*  249 */     log("VM: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor"));
/*  250 */     log("LWJGL: " + Sys.getVersion());
/*      */     
/*  252 */     openGlVersion = GL11.glGetString(7938);
/*  253 */     openGlRenderer = GL11.glGetString(7937);
/*  254 */     openGlVendor = GL11.glGetString(7936);
/*  255 */     log("OpenGL: " + openGlRenderer + ", version " + openGlVersion + ", " + openGlVendor);
/*  256 */     log("OpenGL Version: " + getOpenGlVersionString());
/*      */     
/*  258 */     if (!(GLContext.getCapabilities()).OpenGL12) {
/*  259 */       log("OpenGL Mipmap levels: Not available (GL12.GL_TEXTURE_MAX_LEVEL)");
/*      */     }
/*  261 */     fancyFogAvailable = (GLContext.getCapabilities()).GL_NV_fog_distance;
/*  262 */     if (!fancyFogAvailable) {
/*  263 */       log("OpenGL Fancy fog: Not available (GL_NV_fog_distance)");
/*      */     }
/*  265 */     occlusionAvailable = (GLContext.getCapabilities()).GL_ARB_occlusion_query;
/*  266 */     if (!occlusionAvailable) {
/*  267 */       log("OpenGL Occlussion culling: Not available (GL_ARB_occlusion_query)");
/*      */     }
/*  269 */     int maxTexSize = TextureUtils.getGLMaximumTextureSize();
/*  270 */     dbg("Maximum texture size: " + maxTexSize + "x" + maxTexSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getBuild() {
/*      */     try {
/*  281 */       InputStream in = Config.class.getResourceAsStream("/buildof.txt");
/*  282 */       if (in == null) {
/*  283 */         return null;
/*      */       }
/*  285 */       String build = readLines(in)[0];
/*      */       
/*  287 */       return build;
/*      */     }
/*  289 */     catch (Exception e) {
/*      */       
/*  291 */       warn("" + e.getClass().getName() + ": " + e.getMessage());
/*  292 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static boolean isFancyFogAvailable() {
/*  297 */     return fancyFogAvailable;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isOcclusionAvailable() {
/*  302 */     return occlusionAvailable;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getMinecraftVersionInt() {
/*  307 */     if (minecraftVersionInt < 0) {
/*      */       
/*  309 */       String[] verStrs = tokenize("1.7.10", ".");
/*  310 */       int ver = 0;
/*      */       
/*  312 */       if (verStrs.length > 0)
/*  313 */         ver += 10000 * parseInt(verStrs[0], 0); 
/*  314 */       if (verStrs.length > 1)
/*  315 */         ver += 100 * parseInt(verStrs[1], 0); 
/*  316 */       if (verStrs.length > 2) {
/*  317 */         ver += 1 * parseInt(verStrs[2], 0);
/*      */       }
/*  319 */       minecraftVersionInt = ver;
/*      */     } 
/*      */     
/*  322 */     return minecraftVersionInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getOpenGlVersionString() {
/*  329 */     GlVersion ver = getGlVersion();
/*      */     
/*  331 */     String verStr = "" + ver.getMajor() + "." + ver.getMinor() + "." + ver.getRelease();
/*      */     
/*  333 */     return verStr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static GlVersion getGlVersionLwjgl() {
/*  341 */     if ((GLContext.getCapabilities()).OpenGL44)
/*  342 */       return new GlVersion(4, 4); 
/*  343 */     if ((GLContext.getCapabilities()).OpenGL43)
/*  344 */       return new GlVersion(4, 3); 
/*  345 */     if ((GLContext.getCapabilities()).OpenGL42)
/*  346 */       return new GlVersion(4, 2); 
/*  347 */     if ((GLContext.getCapabilities()).OpenGL41)
/*  348 */       return new GlVersion(4, 1); 
/*  349 */     if ((GLContext.getCapabilities()).OpenGL40) {
/*  350 */       return new GlVersion(4, 0);
/*      */     }
/*  352 */     if ((GLContext.getCapabilities()).OpenGL33)
/*  353 */       return new GlVersion(3, 3); 
/*  354 */     if ((GLContext.getCapabilities()).OpenGL32)
/*  355 */       return new GlVersion(3, 2); 
/*  356 */     if ((GLContext.getCapabilities()).OpenGL31)
/*  357 */       return new GlVersion(3, 1); 
/*  358 */     if ((GLContext.getCapabilities()).OpenGL30) {
/*  359 */       return new GlVersion(3, 0);
/*      */     }
/*  361 */     if ((GLContext.getCapabilities()).OpenGL21)
/*  362 */       return new GlVersion(2, 1); 
/*  363 */     if ((GLContext.getCapabilities()).OpenGL20) {
/*  364 */       return new GlVersion(2, 0);
/*      */     }
/*  366 */     if ((GLContext.getCapabilities()).OpenGL15)
/*  367 */       return new GlVersion(1, 5); 
/*  368 */     if ((GLContext.getCapabilities()).OpenGL14)
/*  369 */       return new GlVersion(1, 4); 
/*  370 */     if ((GLContext.getCapabilities()).OpenGL13)
/*  371 */       return new GlVersion(1, 3); 
/*  372 */     if ((GLContext.getCapabilities()).OpenGL12)
/*  373 */       return new GlVersion(1, 2); 
/*  374 */     if ((GLContext.getCapabilities()).OpenGL11) {
/*  375 */       return new GlVersion(1, 1);
/*      */     }
/*  377 */     return new GlVersion(1, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static GlVersion getGlVersion() {
/*  382 */     if (glVersion == null) {
/*      */       
/*  384 */       String verStr = GL11.glGetString(7938);
/*  385 */       glVersion = parseGlVersion(verStr, null);
/*      */       
/*  387 */       if (glVersion == null) {
/*  388 */         glVersion = getGlVersionLwjgl();
/*      */       }
/*  390 */       if (glVersion == null) {
/*  391 */         glVersion = new GlVersion(1, 0);
/*      */       }
/*      */     } 
/*  394 */     return glVersion;
/*      */   }
/*      */ 
/*      */   
/*      */   public static GlVersion getGlslVersion() {
/*  399 */     if (glslVersion == null) {
/*      */       
/*  401 */       String verStr = GL11.glGetString(35724);
/*  402 */       glslVersion = parseGlVersion(verStr, null);
/*      */       
/*  404 */       if (glslVersion == null) {
/*  405 */         glslVersion = new GlVersion(1, 10);
/*      */       }
/*      */     } 
/*  408 */     return glslVersion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static GlVersion parseGlVersion(String versionString, GlVersion def) {
/*      */     try {
/*  416 */       if (versionString == null) {
/*  417 */         return def;
/*      */       }
/*  419 */       Pattern REGEXP_VERSION = Pattern.compile("([0-9]+)\\.([0-9]+)(\\.([0-9]+))?(.+)?");
/*      */       
/*  421 */       Matcher matcher = REGEXP_VERSION.matcher(versionString);
/*  422 */       if (!matcher.matches()) {
/*  423 */         return def;
/*      */       }
/*  425 */       int major = Integer.parseInt(matcher.group(1));
/*  426 */       int minor = Integer.parseInt(matcher.group(2));
/*  427 */       int release = (matcher.group(4) != null) ? Integer.parseInt(matcher.group(4)) : 0;
/*  428 */       String suffix = matcher.group(5);
/*      */       
/*  430 */       return new GlVersion(major, minor, release, suffix);
/*      */     }
/*  432 */     catch (Exception e) {
/*      */       
/*  434 */       e.printStackTrace();
/*      */       
/*  436 */       return def;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] getOpenGlExtensions() {
/*  443 */     if (openGlExtensions == null) {
/*  444 */       openGlExtensions = detectOpenGlExtensions();
/*      */     }
/*  446 */     return openGlExtensions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] detectOpenGlExtensions() {
/*      */     try {
/*  454 */       GlVersion ver = getGlVersion();
/*      */       
/*  456 */       if (ver.getMajor() >= 3) {
/*      */         
/*  458 */         int countExt = GL11.glGetInteger(33309);
/*  459 */         if (countExt > 0)
/*      */         {
/*  461 */           String[] exts = new String[countExt];
/*  462 */           for (int i = 0; i < countExt; i++)
/*      */           {
/*  464 */             exts[i] = GL30.glGetStringi(7939, i);
/*      */           }
/*      */           
/*  467 */           return exts;
/*      */         }
/*      */       
/*      */       } 
/*  471 */     } catch (Exception e) {
/*      */       
/*  473 */       e.printStackTrace();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  479 */       String extStr = GL11.glGetString(7939);
/*  480 */       String[] exts = extStr.split(" ");
/*      */       
/*  482 */       return exts;
/*      */     }
/*  484 */     catch (Exception e) {
/*      */       
/*  486 */       e.printStackTrace();
/*      */ 
/*      */       
/*  489 */       return new String[0];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateThreadPriorities() {
/*  497 */     updateAvailableProcessors();
/*      */     
/*  499 */     int ELEVATED_PRIORITY = 8;
/*      */     
/*  501 */     if (isSingleProcessor()) {
/*      */ 
/*      */       
/*  504 */       if (isSmoothWorld())
/*      */       {
/*      */         
/*  507 */         minecraftThread.setPriority(10);
/*  508 */         setThreadPriority("Server thread", 1);
/*      */       
/*      */       }
/*      */       else
/*      */       {
/*  513 */         minecraftThread.setPriority(5);
/*  514 */         setThreadPriority("Server thread", 5);
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  521 */       minecraftThread.setPriority(10);
/*  522 */       setThreadPriority("Server thread", 5);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setThreadPriority(String prefix, int priority) {
/*      */     try {
/*  531 */       ThreadGroup tg = Thread.currentThread().getThreadGroup();
/*  532 */       if (tg == null)
/*      */         return; 
/*  534 */       int num = (tg.activeCount() + 10) * 2;
/*  535 */       Thread[] ts = new Thread[num];
/*  536 */       tg.enumerate(ts, false);
/*      */       
/*  538 */       for (int i = 0; i < ts.length; i++) {
/*      */         
/*  540 */         Thread t = ts[i];
/*  541 */         if (t != null)
/*      */         {
/*      */           
/*  544 */           if (t.getName().startsWith(prefix))
/*      */           {
/*  546 */             t.setPriority(priority);
/*      */           }
/*      */         }
/*      */       } 
/*  550 */     } catch (Throwable e) {
/*      */ 
/*      */       
/*  553 */       warn(e.getClass().getName() + ": " + e.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isMinecraftThread() {
/*  561 */     return (Thread.currentThread() == minecraftThread);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void startVersionCheckThread() {
/*  568 */     VersionCheckThread vct = new VersionCheckThread();
/*  569 */     vct.start();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isMipmaps() {
/*  578 */     return (gameSettings.H > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getMipmapLevels() {
/*  587 */     return gameSettings.H;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getMipmapType() {
/*  597 */     switch (gameSettings.ofMipmapType) {
/*      */ 
/*      */ 
/*      */       
/*      */       case 0:
/*  602 */         return 9986;
/*      */       case 1:
/*  604 */         return 9986;
/*      */       case 2:
/*  606 */         if (isMultiTexture()) {
/*  607 */           return 9985;
/*      */         }
/*  609 */         return 9986;
/*      */       case 3:
/*  611 */         if (isMultiTexture()) {
/*  612 */           return 9987;
/*      */         }
/*  614 */         return 9986;
/*      */     } 
/*      */ 
/*      */     
/*  618 */     return 9986;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isUseAlphaFunc() {
/*  627 */     float alphaFuncLevel = getAlphaFuncLevel();
/*      */     
/*  629 */     return (alphaFuncLevel > DEF_ALPHA_FUNC_LEVEL.floatValue() + 1.0E-5F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float getAlphaFuncLevel() {
/*  636 */     return DEF_ALPHA_FUNC_LEVEL.floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isFogFancy() {
/*  643 */     if (!isFancyFogAvailable()) {
/*  644 */       return false;
/*      */     }
/*  646 */     return (gameSettings.ofFogType == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isFogFast() {
/*  653 */     return (gameSettings.ofFogType == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isFogOff() {
/*  660 */     return (gameSettings.ofFogType == 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float getFogStart() {
/*  668 */     return gameSettings.ofFogStart;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isOcclusionEnabled() {
/*  676 */     return gameSettings.f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isOcclusionFancy() {
/*  683 */     if (!isOcclusionEnabled()) {
/*  684 */       return false;
/*      */     }
/*  686 */     return gameSettings.ofOcclusionFancy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLoadChunksFar() {
/*  694 */     return gameSettings.ofLoadFar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getPreloadedChunks() {
/*  701 */     return gameSettings.ofPreloadedChunks;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void dbg(String s) {
/*  706 */     LOGGER.info("[OptiFine] " + s);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void warn(String s) {
/*  711 */     LOGGER.warn("[OptiFine] " + s);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void error(String s) {
/*  716 */     LOGGER.error("[OptiFine] " + s);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void log(String s) {
/*  721 */     dbg(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getUpdatesPerFrame() {
/*  731 */     return gameSettings.ofChunkUpdates;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDynamicUpdates() {
/*  739 */     return gameSettings.ofChunkUpdatesDynamic;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isRainFancy() {
/*  746 */     if (gameSettings.ofRain == 0) {
/*  747 */       return gameSettings.i;
/*      */     }
/*  749 */     return (gameSettings.ofRain == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isWaterFancy() {
/*  756 */     if (gameSettings.ofWater == 0) {
/*  757 */       return gameSettings.i;
/*      */     }
/*  759 */     return (gameSettings.ofWater == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isRainOff() {
/*  766 */     return (gameSettings.ofRain == 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isCloudsFancy() {
/*  774 */     if (gameSettings.ofClouds != 0) {
/*  775 */       return (gameSettings.ofClouds == 2);
/*      */     }
/*  777 */     if (isShaders())
/*      */     {
/*  779 */       if (!Shaders.shaderPackClouds.isDefault()) {
/*  780 */         return Shaders.shaderPackClouds.isFancy();
/*      */       }
/*      */     }
/*  783 */     if (texturePackClouds != 0) {
/*  784 */       return (texturePackClouds == 2);
/*      */     }
/*  786 */     return gameSettings.i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isCloudsOff() {
/*  794 */     if (gameSettings.ofClouds != 0) {
/*  795 */       return (gameSettings.ofClouds == 3);
/*      */     }
/*  797 */     if (isShaders())
/*      */     {
/*  799 */       if (!Shaders.shaderPackClouds.isDefault()) {
/*  800 */         return Shaders.shaderPackClouds.isOff();
/*      */       }
/*      */     }
/*  803 */     if (texturePackClouds != 0) {
/*  804 */       return (texturePackClouds == 3);
/*      */     }
/*  806 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateTexturePackClouds() {
/*  812 */     texturePackClouds = 0;
/*      */     
/*  814 */     bqy rm = getResourceManager();
/*  815 */     if (rm == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  821 */       InputStream in = rm.a(new bqx("mcpatcher/color.properties")).b();
/*  822 */       if (in == null)
/*      */         return; 
/*  824 */       Properties props = new Properties();
/*  825 */       props.load(in);
/*      */       
/*  827 */       in.close();
/*      */       
/*  829 */       String cloudStr = props.getProperty("clouds");
/*  830 */       if (cloudStr == null) {
/*      */         return;
/*      */       }
/*  833 */       dbg("Texture pack clouds: " + cloudStr);
/*  834 */       cloudStr = cloudStr.toLowerCase();
/*  835 */       if (cloudStr.equals("fast"))
/*  836 */         texturePackClouds = 1; 
/*  837 */       if (cloudStr.equals("fancy"))
/*  838 */         texturePackClouds = 2; 
/*  839 */       if (cloudStr.equals("off")) {
/*  840 */         texturePackClouds = 3;
/*      */       }
/*  842 */     } catch (Exception e) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isTreesFancy() {
/*  861 */     if (gameSettings.ofTrees == 0) {
/*  862 */       return gameSettings.i;
/*      */     }
/*  864 */     return (gameSettings.ofTrees == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isGrassFancy() {
/*  878 */     if (gameSettings.ofGrass == 0) {
/*  879 */       return gameSettings.i;
/*      */     }
/*  881 */     return (gameSettings.ofGrass == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDroppedItemsFancy() {
/*  888 */     if (gameSettings.ofDroppedItems == 0) {
/*  889 */       return gameSettings.i;
/*      */     }
/*  891 */     return (gameSettings.ofDroppedItems == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int limit(int val, int min, int max) {
/*  898 */     if (val < min)
/*  899 */       return min; 
/*  900 */     if (val > max) {
/*  901 */       return max;
/*      */     }
/*  903 */     return val;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float limit(float val, float min, float max) {
/*  910 */     if (val < min)
/*  911 */       return min; 
/*  912 */     if (val > max) {
/*  913 */       return max;
/*      */     }
/*  915 */     return val;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double limit(double val, double min, double max) {
/*  922 */     if (val < min)
/*  923 */       return min; 
/*  924 */     if (val > max) {
/*  925 */       return max;
/*      */     }
/*  927 */     return val;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float limitTo1(float val) {
/*  934 */     if (val < 0.0F)
/*  935 */       return 0.0F; 
/*  936 */     if (val > 1.0F) {
/*  937 */       return 1.0F;
/*      */     }
/*  939 */     return val;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedWater() {
/*  946 */     return (gameSettings.ofAnimatedWater != 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isGeneratedWater() {
/*  953 */     return (gameSettings.ofAnimatedWater == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedPortal() {
/*  960 */     return gameSettings.ofAnimatedPortal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedLava() {
/*  967 */     return (gameSettings.ofAnimatedLava != 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isGeneratedLava() {
/*  974 */     return (gameSettings.ofAnimatedLava == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedFire() {
/*  981 */     return gameSettings.ofAnimatedFire;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedRedstone() {
/*  988 */     return gameSettings.ofAnimatedRedstone;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedExplosion() {
/*  995 */     return gameSettings.ofAnimatedExplosion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedFlame() {
/* 1002 */     return gameSettings.ofAnimatedFlame;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedSmoke() {
/* 1009 */     return gameSettings.ofAnimatedSmoke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isVoidParticles() {
/* 1016 */     return gameSettings.ofVoidParticles;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isWaterParticles() {
/* 1023 */     return gameSettings.ofWaterParticles;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isRainSplash() {
/* 1030 */     return gameSettings.ofRainSplash;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isPortalParticles() {
/* 1037 */     return gameSettings.ofPortalParticles;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isPotionParticles() {
/* 1044 */     return gameSettings.ofPotionParticles;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDepthFog() {
/* 1060 */     return gameSettings.ofDepthFog;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float getAmbientOcclusionLevel() {
/* 1069 */     if (isShaders())
/*      */     {
/*      */       
/* 1072 */       if (Shaders.aoLevel >= 0.0F) {
/* 1073 */         return Shaders.aoLevel;
/*      */       }
/*      */     }
/* 1076 */     return gameSettings.ofAoLevel;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String listToString(List list) {
/* 1081 */     return listToString(list, ", ");
/*      */   }
/*      */ 
/*      */   
/*      */   public static String listToString(List list, String separator) {
/* 1086 */     if (list == null) {
/* 1087 */       return "";
/*      */     }
/* 1089 */     StringBuffer buf = new StringBuffer(list.size() * 5);
/* 1090 */     for (int i = 0; i < list.size(); i++) {
/*      */       
/* 1092 */       Object obj = list.get(i);
/* 1093 */       if (i > 0) {
/* 1094 */         buf.append(separator);
/*      */       }
/* 1096 */       buf.append(String.valueOf(obj));
/*      */     } 
/* 1098 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public static String arrayToString(Object[] arr) {
/* 1103 */     return arrayToString(arr, ", ");
/*      */   }
/*      */ 
/*      */   
/*      */   public static String arrayToString(Object[] arr, String separator) {
/* 1108 */     if (arr == null) {
/* 1109 */       return "";
/*      */     }
/* 1111 */     StringBuffer buf = new StringBuffer(arr.length * 5);
/* 1112 */     for (int i = 0; i < arr.length; i++) {
/*      */       
/* 1114 */       Object obj = arr[i];
/* 1115 */       if (i > 0) {
/* 1116 */         buf.append(separator);
/*      */       }
/* 1118 */       buf.append(String.valueOf(obj));
/*      */     } 
/* 1120 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public static String arrayToString(int[] arr) {
/* 1125 */     return arrayToString(arr, ", ");
/*      */   }
/*      */ 
/*      */   
/*      */   public static String arrayToString(int[] arr, String separator) {
/* 1130 */     if (arr == null) {
/* 1131 */       return "";
/*      */     }
/* 1133 */     StringBuffer buf = new StringBuffer(arr.length * 5);
/* 1134 */     for (int i = 0; i < arr.length; i++) {
/*      */       
/* 1136 */       int x = arr[i];
/* 1137 */       if (i > 0) {
/* 1138 */         buf.append(separator);
/*      */       }
/* 1140 */       buf.append(String.valueOf(x));
/*      */     } 
/* 1142 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static bao getMinecraft() {
/* 1150 */     return minecraft;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static bqf getTextureManager() {
/* 1158 */     return minecraft.P();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static bqy getResourceManager() {
/* 1166 */     return minecraft.Q();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static InputStream getResourceStream(bqx location) throws IOException {
/* 1175 */     return getResourceStream(minecraft.Q(), location);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static InputStream getResourceStream(bqy resourceManager, bqx location) throws IOException {
/* 1184 */     bqw res = resourceManager.a(location);
/* 1185 */     if (res == null) {
/* 1186 */       return null;
/*      */     }
/* 1188 */     return res.b();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static bqw getResource(bqx location) throws IOException {
/* 1198 */     return minecraft.Q().a(location);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean hasResource(bqx location) {
/*      */     try {
/* 1210 */       bqw res = getResource(location);
/*      */       
/* 1212 */       return (res != null);
/*      */     }
/* 1214 */     catch (IOException e) {
/*      */       
/* 1216 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean hasResource(bqy resourceManager, bqx location) {
/*      */     try {
/* 1228 */       bqw res = resourceManager.a(location);
/*      */       
/* 1230 */       return (res != null);
/*      */     }
/* 1232 */     catch (IOException e) {
/*      */       
/* 1234 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static bra[] getResourcePacks() {
/* 1247 */     brc rep = minecraft.R();
/* 1248 */     List entries = rep.c();
/* 1249 */     List<bra> list = new ArrayList();
/* 1250 */     for (Iterator<brf> it = entries.iterator(); it.hasNext(); ) {
/*      */       
/* 1252 */       brf entry = it.next();
/* 1253 */       list.add(entry.c());
/*      */     } 
/*      */     
/* 1256 */     if (rep.e() != null) {
/* 1257 */       list.add(rep.e());
/*      */     }
/* 1259 */     bra[] rps = list.<bra>toArray(new bra[list.size()]);
/* 1260 */     return rps;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getResourcePackNames() {
/* 1268 */     if (minecraft.R() == null) {
/* 1269 */       return "";
/*      */     }
/* 1271 */     bra[] rps = getResourcePacks();
/*      */     
/* 1273 */     if (rps.length <= 0) {
/* 1274 */       return getDefaultResourcePack().b();
/*      */     }
/* 1276 */     String[] names = new String[rps.length];
/* 1277 */     for (int i = 0; i < rps.length; i++)
/*      */     {
/* 1279 */       names[i] = rps[i].b();
/*      */     }
/* 1281 */     String nameStr = arrayToString((Object[])names);
/* 1282 */     return nameStr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static bra getDefaultResourcePack() {
/* 1289 */     return (minecraft.R()).b;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFromDefaultResourcePack(bqx loc) {
/* 1294 */     bra rp = getDefiningResourcePack(loc);
/* 1295 */     return (rp == getDefaultResourcePack());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static bra getDefiningResourcePack(bqx loc) {
/* 1303 */     bra[] rps = getResourcePacks();
/* 1304 */     for (int i = rps.length - 1; i >= 0; i--) {
/*      */       
/* 1306 */       bra rp = rps[i];
/*      */       
/* 1308 */       if (rp.b(loc)) {
/* 1309 */         return rp;
/*      */       }
/*      */     } 
/* 1312 */     if (getDefaultResourcePack().b(loc)) {
/* 1313 */       return getDefaultResourcePack();
/*      */     }
/* 1315 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static bma getRenderGlobal() {
/* 1323 */     if (minecraft == null) {
/* 1324 */       return null;
/*      */     }
/* 1326 */     return minecraft.g;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getMaxDynamicTileWidth() {
/* 1334 */     return 64;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static rf getSideGrassTexture(ahl blockAccess, int x, int y, int z, int side, rf icon) {
/*      */     amd amd;
/* 1343 */     if (!isBetterGrass())
/*      */     {
/*      */       
/* 1346 */       return icon;
/*      */     }
/*      */     
/* 1349 */     rf fullIcon = TextureUtils.iconGrassTop;
/*      */     
/* 1351 */     alh alh = ajn.c;
/*      */     
/* 1353 */     if (icon == TextureUtils.iconMyceliumSide) {
/*      */       
/* 1355 */       fullIcon = TextureUtils.iconMyceliumTop;
/*      */       
/* 1357 */       amd = ajn.bh;
/*      */     } 
/*      */     
/* 1360 */     if (isBetterGrassFancy()) {
/*      */ 
/*      */ 
/*      */       
/* 1364 */       y--;
/* 1365 */       switch (side) {
/*      */ 
/*      */         
/*      */         case 2:
/* 1369 */           z--;
/*      */           break;
/*      */         
/*      */         case 3:
/* 1373 */           z++;
/*      */           break;
/*      */         
/*      */         case 4:
/* 1377 */           x--;
/*      */           break;
/*      */         
/*      */         case 5:
/* 1381 */           x++;
/*      */           break;
/*      */       } 
/*      */       
/* 1385 */       aji block = blockAccess.a(x, y, z);
/*      */       
/* 1387 */       if (block != amd)
/*      */       {
/*      */         
/* 1390 */         return icon;
/*      */       }
/*      */     } 
/*      */     
/* 1394 */     return fullIcon;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static rf getSideSnowGrassTexture(ahl blockAccess, int x, int y, int z, int side) {
/* 1402 */     if (!isBetterGrass())
/*      */     {
/*      */       
/* 1405 */       return TextureUtils.iconGrassSideSnowed;
/*      */     }
/* 1407 */     if (isBetterGrassFancy()) {
/*      */ 
/*      */ 
/*      */       
/* 1411 */       switch (side) {
/*      */ 
/*      */         
/*      */         case 2:
/* 1415 */           z--;
/*      */           break;
/*      */         
/*      */         case 3:
/* 1419 */           z++;
/*      */           break;
/*      */         
/*      */         case 4:
/* 1423 */           x--;
/*      */           break;
/*      */         
/*      */         case 5:
/* 1427 */           x++;
/*      */           break;
/*      */       } 
/*      */       
/* 1431 */       aji block = blockAccess.a(x, y, z);
/* 1432 */       if (block != ajn.aC && block != ajn.aE)
/*      */       {
/*      */         
/* 1435 */         return TextureUtils.iconGrassSideSnowed;
/*      */       }
/*      */     } 
/*      */     
/* 1439 */     return TextureUtils.iconSnow;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isBetterGrass() {
/* 1446 */     return (gameSettings.ofBetterGrass != 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isBetterGrassFancy() {
/* 1453 */     return (gameSettings.ofBetterGrass == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isWeatherEnabled() {
/* 1460 */     return gameSettings.ofWeather;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSkyEnabled() {
/* 1467 */     return gameSettings.ofSky;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSunMoonEnabled() {
/* 1474 */     return gameSettings.ofSunMoon;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSunTexture() {
/* 1482 */     if (!isSunMoonEnabled()) {
/* 1483 */       return false;
/*      */     }
/* 1485 */     if (isShaders())
/*      */     {
/* 1487 */       if (!Shaders.isSun()) {
/* 1488 */         return false;
/*      */       }
/*      */     }
/* 1491 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isMoonTexture() {
/* 1499 */     if (!isSunMoonEnabled()) {
/* 1500 */       return false;
/*      */     }
/* 1502 */     if (isShaders())
/*      */     {
/* 1504 */       if (!Shaders.isMoon()) {
/* 1505 */         return false;
/*      */       }
/*      */     }
/* 1508 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isVignetteEnabled() {
/* 1516 */     if (isShaders())
/*      */     {
/* 1518 */       if (!Shaders.isVignette()) {
/* 1519 */         return false;
/*      */       }
/*      */     }
/* 1522 */     if (gameSettings.ofVignette == 0) {
/* 1523 */       return gameSettings.i;
/*      */     }
/* 1525 */     return (gameSettings.ofVignette == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isStarsEnabled() {
/* 1532 */     return gameSettings.ofStars;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sleep(long ms) {
/*      */     try {
/* 1541 */       Thread.sleep(ms);
/*      */     }
/* 1543 */     catch (InterruptedException e) {
/*      */       
/* 1545 */       e.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isTimeDayOnly() {
/* 1553 */     return (gameSettings.ofTime == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isTimeDefault() {
/* 1560 */     return (gameSettings.ofTime == 0 || gameSettings.ofTime == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isTimeNightOnly() {
/* 1567 */     return (gameSettings.ofTime == 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isClearWater() {
/* 1574 */     return gameSettings.ofClearWater;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getAnisotropicFilterLevel() {
/* 1581 */     return gameSettings.I;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAnisotropicFiltering() {
/* 1589 */     return (getAnisotropicFilterLevel() > 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getAntialiasingLevel() {
/* 1597 */     return antialiasingLevel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAntialiasing() {
/* 1605 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAntialiasingConfigured() {
/* 1613 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isMultiTexture() {
/* 1621 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean between(int val, int min, int max) {
/* 1637 */     return (val >= min && val <= max);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDrippingWaterLava() {
/* 1644 */     return gameSettings.ofDrippingWaterLava;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isBetterSnow() {
/* 1651 */     return gameSettings.ofBetterSnow;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Dimension getFullscreenDimension() {
/* 1659 */     if (desktopDisplayMode == null) {
/* 1660 */       return null;
/*      */     }
/* 1662 */     if (gameSettings == null) {
/* 1663 */       return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
/*      */     }
/* 1665 */     String dimStr = gameSettings.ofFullscreenMode;
/* 1666 */     if (dimStr.equals("Default")) {
/* 1667 */       return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
/*      */     }
/* 1669 */     String[] dimStrs = tokenize(dimStr, " x");
/* 1670 */     if (dimStrs.length < 2) {
/* 1671 */       return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
/*      */     }
/* 1673 */     return new Dimension(parseInt(dimStrs[0], -1), parseInt(dimStrs[1], -1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int parseInt(String str, int defVal) {
/*      */     try {
/* 1681 */       if (str == null) {
/* 1682 */         return defVal;
/*      */       }
/* 1684 */       str = str.trim();
/*      */       
/* 1686 */       return Integer.parseInt(str);
/*      */     }
/* 1688 */     catch (NumberFormatException e) {
/*      */       
/* 1690 */       return defVal;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float parseFloat(String str, float defVal) {
/*      */     try {
/* 1699 */       if (str == null) {
/* 1700 */         return defVal;
/*      */       }
/* 1702 */       str = str.trim();
/*      */       
/* 1704 */       return Float.parseFloat(str);
/*      */     }
/* 1706 */     catch (NumberFormatException e) {
/*      */       
/* 1708 */       return defVal;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean parseBoolean(String str, boolean defVal) {
/*      */     try {
/* 1717 */       if (str == null) {
/* 1718 */         return defVal;
/*      */       }
/* 1720 */       str = str.trim();
/*      */       
/* 1722 */       return Boolean.parseBoolean(str);
/*      */     }
/* 1724 */     catch (NumberFormatException e) {
/*      */       
/* 1726 */       return defVal;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] tokenize(String str, String delim) {
/* 1734 */     StringTokenizer tok = new StringTokenizer(str, delim);
/* 1735 */     List<String> list = new ArrayList();
/* 1736 */     while (tok.hasMoreTokens()) {
/*      */       
/* 1738 */       String token = tok.nextToken();
/* 1739 */       list.add(token);
/*      */     } 
/* 1741 */     String[] strs = list.<String>toArray(new String[list.size()]);
/* 1742 */     return strs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DisplayMode getDesktopDisplayMode() {
/* 1750 */     return desktopDisplayMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DisplayMode[] getDisplayModes() {
/* 1758 */     if (displayModes == null) {
/*      */       
/*      */       try {
/*      */ 
/*      */         
/* 1763 */         DisplayMode[] modes = Display.getAvailableDisplayModes();
/*      */         
/* 1765 */         Set<Dimension> setDimensions = getDisplayModeDimensions(modes);
/* 1766 */         List<DisplayMode> list = new ArrayList();
/* 1767 */         for (Iterator<Dimension> it = setDimensions.iterator(); it.hasNext(); ) {
/*      */           
/* 1769 */           Dimension dim = it.next();
/* 1770 */           DisplayMode[] dimModes = getDisplayModes(modes, dim);
/* 1771 */           DisplayMode dm = getDisplayMode(dimModes, desktopDisplayMode);
/* 1772 */           if (dm != null) {
/* 1773 */             list.add(dm);
/*      */           }
/*      */         } 
/* 1776 */         DisplayMode[] fsModes = list.<DisplayMode>toArray(new DisplayMode[list.size()]);
/*      */         
/* 1778 */         Arrays.sort(fsModes, new DisplayModeComparator());
/*      */         
/* 1780 */         return fsModes;
/*      */       }
/* 1782 */       catch (Exception e) {
/*      */         
/* 1784 */         e.printStackTrace();
/* 1785 */         displayModes = new DisplayMode[] { desktopDisplayMode };
/*      */       } 
/*      */     }
/*      */     
/* 1789 */     return displayModes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DisplayMode getLargestDisplayMode() {
/* 1796 */     DisplayMode[] modes = getDisplayModes();
/* 1797 */     if (modes == null || modes.length < 1) {
/* 1798 */       return desktopDisplayMode;
/*      */     }
/* 1800 */     DisplayMode mode = modes[modes.length - 1];
/*      */     
/* 1802 */     if (desktopDisplayMode.getWidth() > mode.getWidth()) {
/* 1803 */       return desktopDisplayMode;
/*      */     }
/* 1805 */     if (desktopDisplayMode.getWidth() == mode.getWidth() && desktopDisplayMode.getHeight() > mode.getHeight()) {
/* 1806 */       return desktopDisplayMode;
/*      */     }
/* 1808 */     return mode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Set<Dimension> getDisplayModeDimensions(DisplayMode[] modes) {
/* 1816 */     Set<Dimension> set = new HashSet<Dimension>();
/* 1817 */     for (int i = 0; i < modes.length; i++) {
/*      */       
/* 1819 */       DisplayMode mode = modes[i];
/* 1820 */       Dimension dim = new Dimension(mode.getWidth(), mode.getHeight());
/* 1821 */       set.add(dim);
/*      */     } 
/*      */     
/* 1824 */     return set;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static DisplayMode[] getDisplayModes(DisplayMode[] modes, Dimension dim) {
/* 1831 */     List<DisplayMode> list = new ArrayList();
/* 1832 */     for (int i = 0; i < modes.length; i++) {
/*      */       
/* 1834 */       DisplayMode mode = modes[i];
/*      */       
/* 1836 */       if (mode.getWidth() == dim.getWidth())
/*      */       {
/* 1838 */         if (mode.getHeight() == dim.getHeight())
/*      */         {
/*      */           
/* 1841 */           list.add(mode); } 
/*      */       }
/*      */     } 
/* 1844 */     DisplayMode[] dimModes = list.<DisplayMode>toArray(new DisplayMode[list.size()]);
/*      */     
/* 1846 */     return dimModes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static DisplayMode getDisplayMode(DisplayMode[] modes, DisplayMode desktopMode) {
/* 1854 */     if (desktopMode != null)
/*      */     {
/*      */       
/* 1857 */       for (int i = 0; i < modes.length; i++) {
/*      */         
/* 1859 */         DisplayMode mode = modes[i];
/*      */         
/* 1861 */         if (mode.getBitsPerPixel() == desktopMode.getBitsPerPixel())
/*      */         {
/*      */           
/* 1864 */           if (mode.getFrequency() == desktopMode.getFrequency())
/*      */           {
/*      */             
/* 1867 */             return mode;
/*      */           }
/*      */         }
/*      */       } 
/*      */     }
/* 1872 */     if (modes.length <= 0) {
/* 1873 */       return null;
/*      */     }
/* 1875 */     Arrays.sort(modes, new DisplayModeComparator());
/*      */     
/* 1877 */     return modes[modes.length - 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] getDisplayModeNames() {
/* 1884 */     DisplayMode[] modes = getDisplayModes();
/* 1885 */     String[] names = new String[modes.length];
/* 1886 */     for (int i = 0; i < modes.length; i++) {
/*      */       
/* 1888 */       DisplayMode mode = modes[i];
/* 1889 */       String name = "" + mode.getWidth() + "x" + mode.getHeight();
/* 1890 */       names[i] = name;
/*      */     } 
/* 1892 */     return names;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DisplayMode getDisplayMode(Dimension dim) throws LWJGLException {
/* 1901 */     DisplayMode[] modes = getDisplayModes();
/* 1902 */     for (int i = 0; i < modes.length; i++) {
/*      */       
/* 1904 */       DisplayMode dm = modes[i];
/*      */       
/* 1906 */       if (dm.getWidth() == dim.width)
/*      */       {
/* 1908 */         if (dm.getHeight() == dim.height)
/*      */         {
/*      */           
/* 1911 */           return dm; } 
/*      */       }
/*      */     } 
/* 1914 */     return desktopDisplayMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedTerrain() {
/* 1921 */     return gameSettings.ofAnimatedTerrain;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedItems() {
/* 1928 */     return gameSettings.ofAnimatedItems;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedTextures() {
/* 1935 */     return gameSettings.ofAnimatedTextures;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSwampColors() {
/* 1942 */     return gameSettings.ofSwampColors;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isRandomMobs() {
/* 1949 */     return gameSettings.ofRandomMobs;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void checkGlError(String loc) {
/* 1955 */     int i = GL11.glGetError();
/* 1956 */     if (i != 0) {
/*      */       
/* 1958 */       String text = GLU.gluErrorString(i);
/* 1959 */       error("OpenGlError: " + i + " (" + text + "), at: " + loc);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSmoothBiomes() {
/* 1967 */     return gameSettings.ofSmoothBiomes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isCustomColors() {
/* 1974 */     return gameSettings.ofCustomColors;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isCustomSky() {
/* 1981 */     return gameSettings.ofCustomSky;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isCustomFonts() {
/* 1988 */     return gameSettings.ofCustomFonts;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isShowCapes() {
/* 1995 */     return gameSettings.ofShowCapes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isConnectedTextures() {
/* 2002 */     return (gameSettings.ofConnectedTextures != 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNaturalTextures() {
/* 2009 */     return gameSettings.ofNaturalTextures;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isConnectedTexturesFancy() {
/* 2016 */     return (gameSettings.ofConnectedTextures == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isFastRender() {
/* 2024 */     return gameSettings.ofFastRender;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isTranslucentBlocksFancy() {
/* 2031 */     return (gameSettings.ofTranslucentBlocks == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isShaders() {
/* 2037 */     return Shaders.shaderPackLoaded;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] readLines(File file) throws IOException {
/* 2045 */     FileInputStream fis = new FileInputStream(file);
/* 2046 */     return readLines(fis);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] readLines(InputStream is) throws IOException {
/* 2053 */     List<String> list = new ArrayList();
/*      */     
/* 2055 */     InputStreamReader isr = new InputStreamReader(is, "ASCII");
/* 2056 */     BufferedReader br = new BufferedReader(isr);
/*      */     
/*      */     while (true) {
/* 2059 */       String line = br.readLine();
/* 2060 */       if (line == null)
/*      */         break; 
/* 2062 */       list.add(line);
/*      */     } 
/*      */ 
/*      */     
/* 2066 */     String[] lines = list.<String>toArray(new String[list.size()]);
/*      */     
/* 2068 */     return lines;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String readFile(File file) throws IOException {
/* 2076 */     FileInputStream fin = new FileInputStream(file);
/* 2077 */     return readInputStream(fin, "ASCII");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String readInputStream(InputStream in) throws IOException {
/* 2085 */     return readInputStream(in, "ASCII");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String readInputStream(InputStream in, String encoding) throws IOException {
/* 2093 */     InputStreamReader inr = new InputStreamReader(in, encoding);
/* 2094 */     BufferedReader br = new BufferedReader(inr);
/* 2095 */     StringBuffer sb = new StringBuffer();
/*      */     
/*      */     while (true) {
/* 2098 */       String line = br.readLine();
/* 2099 */       if (line == null)
/*      */         break; 
/* 2101 */       sb.append(line);
/* 2102 */       sb.append("\n");
/*      */     } 
/*      */ 
/*      */     
/* 2106 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] readAll(InputStream is) throws IOException {
/* 2114 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 2115 */     byte[] buf = new byte[1024];
/*      */     
/*      */     while (true) {
/* 2118 */       int len = is.read(buf);
/* 2119 */       if (len < 0)
/*      */         break; 
/* 2121 */       baos.write(buf, 0, len);
/*      */     } 
/*      */ 
/*      */     
/* 2125 */     is.close();
/*      */     
/* 2127 */     byte[] bytes = baos.toByteArray();
/*      */     
/* 2129 */     return bytes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static bbj getGameSettings() {
/* 2137 */     return gameSettings;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getNewRelease() {
/* 2144 */     return newRelease;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setNewRelease(String newRelease) {
/* 2151 */     Config.newRelease = newRelease;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int compareRelease(String rel1, String rel2) {
/* 2162 */     String[] rels1 = splitRelease(rel1);
/* 2163 */     String[] rels2 = splitRelease(rel2);
/*      */     
/* 2165 */     String branch1 = rels1[0];
/* 2166 */     String branch2 = rels2[0];
/* 2167 */     if (!branch1.equals(branch2)) {
/* 2168 */       return branch1.compareTo(branch2);
/*      */     }
/* 2170 */     int rev1 = parseInt(rels1[1], -1);
/* 2171 */     int rev2 = parseInt(rels2[1], -1);
/* 2172 */     if (rev1 != rev2) {
/* 2173 */       return rev1 - rev2;
/*      */     }
/* 2175 */     String suf1 = rels1[2];
/* 2176 */     String suf2 = rels2[2];
/*      */     
/* 2178 */     if (!suf1.equals(suf2)) {
/*      */ 
/*      */       
/* 2181 */       if (suf1.isEmpty())
/* 2182 */         return 1; 
/* 2183 */       if (suf2.isEmpty()) {
/* 2184 */         return -1;
/*      */       }
/*      */     } 
/* 2187 */     return suf1.compareTo(suf2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] splitRelease(String relStr) {
/* 2196 */     if (relStr == null || relStr.length() <= 0) {
/* 2197 */       return new String[] { "", "", "" };
/*      */     }
/* 2199 */     Pattern p = Pattern.compile("([A-Z])([0-9]+)(.*)");
/* 2200 */     Matcher m = p.matcher(relStr);
/* 2201 */     if (!m.matches()) {
/* 2202 */       return new String[] { "", "", "" };
/*      */     }
/* 2204 */     String branch = normalize(m.group(1));
/* 2205 */     String revision = normalize(m.group(2));
/* 2206 */     String suffix = normalize(m.group(3));
/* 2207 */     return new String[] { branch, revision, suffix };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int intHash(int x) {
/* 2215 */     x = x ^ 0x3D ^ x >> 16;
/* 2216 */     x += x << 3;
/* 2217 */     x ^= x >> 4;
/* 2218 */     x *= 668265261;
/* 2219 */     x ^= x >> 15;
/* 2220 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getRandom(int x, int y, int z, int face) {
/* 2232 */     int rand = intHash(face + 37);
/* 2233 */     rand = intHash(rand + x);
/* 2234 */     rand = intHash(rand + z);
/* 2235 */     rand = intHash(rand + y);
/*      */     
/* 2237 */     return rand;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static mt getWorldServer() {
/* 2246 */     bjf bjf = minecraft.f;
/* 2247 */     if (bjf == null) {
/* 2248 */       return null;
/*      */     }
/* 2250 */     if (!minecraft.F()) {
/* 2251 */       return null;
/*      */     }
/* 2253 */     bsx is = minecraft.H();
/* 2254 */     if (is == null) {
/* 2255 */       return null;
/*      */     }
/* 2257 */     aqo wp = ((ahb)bjf).t;
/* 2258 */     if (wp == null) {
/* 2259 */       return null;
/*      */     }
/* 2261 */     int wd = wp.i;
/*      */ 
/*      */     
/*      */     try {
/* 2265 */       mt ws = is.a(wd);
/*      */       
/* 2267 */       return ws;
/*      */     }
/* 2269 */     catch (NullPointerException e) {
/*      */ 
/*      */       
/* 2272 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getAvailableProcessors() {
/* 2281 */     return availableProcessors;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateAvailableProcessors() {
/* 2289 */     availableProcessors = Runtime.getRuntime().availableProcessors();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSingleProcessor() {
/* 2297 */     return (getAvailableProcessors() <= 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSmoothWorld() {
/* 2305 */     if (!isSingleProcessor()) {
/* 2306 */       return false;
/*      */     }
/* 2308 */     return gameSettings.ofSmoothWorld;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLazyChunkLoading() {
/* 2316 */     return gameSettings.ofLazyChunkLoading;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getChunkViewDistance() {
/* 2324 */     if (gameSettings == null) {
/* 2325 */       return 10;
/*      */     }
/* 2327 */     int chunkDistance = gameSettings.c;
/*      */     
/* 2329 */     return chunkDistance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(Object o1, Object o2) {
/* 2339 */     if (o1 == o2) {
/* 2340 */       return true;
/*      */     }
/* 2342 */     if (o1 == null) {
/* 2343 */       return false;
/*      */     }
/* 2345 */     return o1.equals(o2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equalsOne(Object a, Object[] bs) {
/* 2353 */     if (bs == null) {
/* 2354 */       return false;
/*      */     }
/* 2356 */     for (int i = 0; i < bs.length; i++) {
/*      */       
/* 2358 */       Object b = bs[i];
/* 2359 */       if (equals(a, b)) {
/* 2360 */         return true;
/*      */       }
/*      */     } 
/* 2363 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equalsOne(int val, int[] vals) {
/* 2370 */     for (int i = 0; i < vals.length; i++) {
/*      */       
/* 2372 */       if (vals[i] == val) {
/* 2373 */         return true;
/*      */       }
/*      */     } 
/* 2376 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSameOne(Object a, Object[] bs) {
/* 2384 */     if (bs == null) {
/* 2385 */       return false;
/*      */     }
/* 2387 */     for (int i = 0; i < bs.length; i++) {
/*      */       
/* 2389 */       Object b = bs[i];
/* 2390 */       if (a == b) {
/* 2391 */         return true;
/*      */       }
/*      */     } 
/* 2394 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String normalize(String s) {
/* 2401 */     if (s == null) {
/* 2402 */       return "";
/*      */     }
/* 2404 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void checkDisplaySettings() {
/* 2412 */     int samples = getAntialiasingLevel();
/* 2413 */     if (samples > 0) {
/*      */       
/* 2415 */       DisplayMode displayMode = Display.getDisplayMode();
/* 2416 */       dbg("FSAA Samples: " + samples);
/*      */ 
/*      */       
/*      */       try {
/* 2420 */         Display.destroy();
/*      */         
/* 2422 */         Display.setDisplayMode(displayMode);
/*      */         
/* 2424 */         Display.create((new PixelFormat()).withDepthBits(24).withSamples(samples));
/*      */         
/* 2426 */         Display.setResizable(false);
/* 2427 */         Display.setResizable(true);
/*      */       }
/* 2429 */       catch (LWJGLException e) {
/*      */         
/* 2431 */         warn("Error setting FSAA: " + samples + "x");
/* 2432 */         e.printStackTrace();
/*      */ 
/*      */         
/*      */         try {
/* 2436 */           Display.setDisplayMode(displayMode);
/*      */           
/* 2438 */           Display.create((new PixelFormat()).withDepthBits(24));
/*      */           
/* 2440 */           Display.setResizable(false);
/* 2441 */           Display.setResizable(true);
/*      */         }
/* 2443 */         catch (LWJGLException e2) {
/*      */           
/* 2445 */           e2.printStackTrace();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 2450 */             Display.setDisplayMode(displayMode);
/*      */             
/* 2452 */             Display.create();
/*      */             
/* 2454 */             Display.setResizable(false);
/* 2455 */             Display.setResizable(true);
/*      */           }
/* 2457 */           catch (LWJGLException e3) {
/*      */             
/* 2459 */             e3.printStackTrace();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2464 */       if (u.a() != v.d) {
/*      */         
/*      */         try {
/*      */           
/* 2468 */           File assetsDir = new File(minecraft.w, "assets");
/* 2469 */           ByteBuffer bufIcon16 = readIconImage(new File(assetsDir, "/icons/icon_16x16.png"));
/* 2470 */           ByteBuffer bufIcon32 = readIconImage(new File(assetsDir, "/icons/icon_32x32.png"));
/* 2471 */           ByteBuffer[] buf = { bufIcon16, bufIcon32 };
/* 2472 */           Display.setIcon(buf);
/*      */         }
/* 2474 */         catch (IOException e) {
/*      */           
/* 2476 */           warn(e.getClass().getName() + ": " + e.getMessage());
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static ByteBuffer readIconImage(File par1File) throws IOException {
/* 2485 */     BufferedImage var2 = ImageIO.read(par1File);
/* 2486 */     int[] var3 = var2.getRGB(0, 0, var2.getWidth(), var2.getHeight(), (int[])null, 0, var2.getWidth());
/* 2487 */     ByteBuffer var4 = ByteBuffer.allocate(4 * var3.length);
/* 2488 */     int[] var5 = var3;
/* 2489 */     int var6 = var3.length;
/* 2490 */     for (int var7 = 0; var7 < var6; var7++) {
/*      */       
/* 2492 */       int var8 = var5[var7];
/* 2493 */       var4.putInt(var8 << 8 | var8 >> 24 & 0xFF);
/*      */     } 
/* 2495 */     var4.flip();
/* 2496 */     return var4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void checkDisplayMode() {
/*      */     try {
/* 2507 */       if (minecraft.L())
/*      */       {
/* 2509 */         if (fullscreenModeChecked) {
/*      */           return;
/*      */         }
/* 2512 */         fullscreenModeChecked = true;
/* 2513 */         desktopModeChecked = false;
/*      */         
/* 2515 */         DisplayMode mode = Display.getDisplayMode();
/*      */         
/* 2517 */         Dimension dim = getFullscreenDimension();
/* 2518 */         if (dim == null) {
/*      */           return;
/*      */         }
/* 2521 */         if (mode.getWidth() == dim.width && mode.getHeight() == dim.height) {
/*      */           return;
/*      */         }
/* 2524 */         DisplayMode newMode = getDisplayMode(dim);
/* 2525 */         if (newMode == null) {
/*      */           return;
/*      */         }
/* 2528 */         Display.setDisplayMode(newMode);
/*      */         
/* 2530 */         minecraft.d = Display.getDisplayMode().getWidth();
/* 2531 */         minecraft.e = Display.getDisplayMode().getHeight();
/* 2532 */         if (minecraft.d <= 0)
/* 2533 */           minecraft.d = 1; 
/* 2534 */         if (minecraft.e <= 0) {
/* 2535 */           minecraft.e = 1;
/*      */         }
/* 2537 */         if (minecraft.n != null) {
/*      */           
/* 2539 */           bca sr = new bca(minecraft, minecraft.d, minecraft.e);
/* 2540 */           int sw = sr.a();
/* 2541 */           int sh = sr.b();
/* 2542 */           minecraft.n.a(minecraft, sw, sh);
/*      */         } 
/*      */         
/* 2545 */         minecraft.o = new bbo(minecraft);
/*      */         
/* 2547 */         updateFramebufferSize();
/*      */         
/* 2549 */         Display.setFullscreen(true);
/*      */         
/* 2551 */         minecraft.u.updateVSync();
/*      */         
/* 2553 */         GL11.glEnable(3553);
/*      */       }
/*      */       else
/*      */       {
/* 2557 */         if (desktopModeChecked) {
/*      */           return;
/*      */         }
/* 2560 */         desktopModeChecked = true;
/* 2561 */         fullscreenModeChecked = false;
/*      */         
/* 2563 */         minecraft.u.updateVSync();
/* 2564 */         Display.update();
/* 2565 */         GL11.glEnable(3553);
/*      */         
/* 2567 */         Display.setResizable(false);
/* 2568 */         Display.setResizable(true);
/*      */       }
/*      */     
/* 2571 */     } catch (Exception e) {
/*      */       
/* 2573 */       e.printStackTrace();
/*      */       
/* 2575 */       gameSettings.ofFullscreenMode = "Default";
/* 2576 */       gameSettings.saveOfOptions();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateFramebufferSize() {
/* 2586 */     minecraft.a().a(minecraft.d, minecraft.e);
/*      */     
/* 2588 */     if (minecraft.p != null)
/*      */     {
/* 2590 */       minecraft.p.a(minecraft.d, minecraft.e);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object[] addObjectToArray(Object[] arr, Object obj) {
/* 2603 */     if (arr == null)
/*      */     {
/*      */       
/* 2606 */       throw new NullPointerException("The given array is NULL");
/*      */     }
/*      */     
/* 2609 */     int arrLen = arr.length;
/* 2610 */     int newLen = arrLen + 1;
/*      */     
/* 2612 */     Object[] newArr = (Object[])Array.newInstance(arr.getClass().getComponentType(), newLen);
/*      */     
/* 2614 */     System.arraycopy(arr, 0, newArr, 0, arrLen);
/*      */     
/* 2616 */     newArr[arrLen] = obj;
/*      */     
/* 2618 */     return newArr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object[] addObjectToArray(Object[] arr, Object obj, int index) {
/* 2628 */     List<Object> list = new ArrayList(Arrays.asList(arr));
/*      */     
/* 2630 */     list.add(index, obj);
/*      */     
/* 2632 */     Object[] newArr = (Object[])Array.newInstance(arr.getClass().getComponentType(), list.size());
/*      */     
/* 2634 */     return list.toArray(newArr);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object[] addObjectsToArray(Object[] arr, Object[] objs) {
/* 2645 */     if (arr == null)
/*      */     {
/*      */       
/* 2648 */       throw new NullPointerException("The given array is NULL");
/*      */     }
/*      */     
/* 2651 */     if (objs.length == 0) {
/* 2652 */       return arr;
/*      */     }
/* 2654 */     int arrLen = arr.length;
/* 2655 */     int newLen = arrLen + objs.length;
/*      */     
/* 2657 */     Object[] newArr = (Object[])Array.newInstance(arr.getClass().getComponentType(), newLen);
/*      */     
/* 2659 */     System.arraycopy(arr, 0, newArr, 0, arrLen);
/*      */     
/* 2661 */     System.arraycopy(objs, 0, newArr, arrLen, objs.length);
/*      */     
/* 2663 */     return newArr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isCustomItems() {
/* 2672 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawFps() {
/* 2677 */     String debugStr = minecraft.B;
/* 2678 */     int pos = debugStr.indexOf(' ');
/* 2679 */     if (pos < 0)
/* 2680 */       pos = 0; 
/* 2681 */     String fps = debugStr.substring(0, pos);
/* 2682 */     String updates = getUpdates(minecraft.B);
/* 2683 */     int renderersActive = minecraft.g.getCountActiveRenderers();
/* 2684 */     int entities = minecraft.g.getCountEntitiesRendered();
/* 2685 */     int tileEntities = minecraft.g.getCountTileEntitiesRendered();
/*      */     
/* 2687 */     String fpsStr = fps + " fps, C: " + renderersActive + ", E: " + entities + "+" + tileEntities + ", U: " + updates;
/*      */     
/* 2689 */     minecraft.l.b(fpsStr, 2, 2, -2039584);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getUpdates(String str) {
/* 2697 */     int pos1 = str.indexOf(", ");
/* 2698 */     if (pos1 < 0)
/* 2699 */       return ""; 
/* 2700 */     pos1 += 2;
/* 2701 */     int pos2 = str.indexOf(' ', pos1);
/* 2702 */     if (pos2 < 0) {
/* 2703 */       return "";
/*      */     }
/* 2705 */     return str.substring(pos1, pos2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getBitsOs() {
/* 2713 */     String progFiles86 = System.getenv("ProgramFiles(X86)");
/*      */     
/* 2715 */     if (progFiles86 != null) {
/* 2716 */       return 64;
/*      */     }
/* 2718 */     return 32;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getBitsJre() {
/* 2725 */     String[] propNames = { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
/* 2726 */     for (int i = 0; i < propNames.length; i++) {
/*      */       
/* 2728 */       String propName = propNames[i];
/* 2729 */       String propVal = System.getProperty(propName);
/* 2730 */       if (propVal != null)
/*      */       {
/* 2732 */         if (propVal.contains("64"))
/* 2733 */           return 64; 
/*      */       }
/*      */     } 
/* 2736 */     return 32;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNotify64BitJava() {
/* 2743 */     return notify64BitJava;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setNotify64BitJava(boolean flag) {
/* 2750 */     notify64BitJava = flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isConnectedModels() {
/* 2758 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void showGuiMessage(String line1, String line2) {
/* 2764 */     GuiMessage gui = new GuiMessage(minecraft.n, line1, line2);
/* 2765 */     minecraft.a(gui);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] addIntToArray(int[] intArray, int intValue) {
/* 2772 */     return addIntsToArray(intArray, new int[] { intValue });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] addIntsToArray(int[] intArray, int[] copyFrom) {
/* 2778 */     if (intArray == null || copyFrom == null) {
/* 2779 */       throw new NullPointerException("The given array is NULL");
/*      */     }
/* 2781 */     int arrLen = intArray.length;
/* 2782 */     int newLen = arrLen + copyFrom.length;
/*      */     
/* 2784 */     int[] newArray = new int[newLen];
/*      */     
/* 2786 */     System.arraycopy(intArray, 0, newArray, 0, arrLen);
/*      */     
/* 2788 */     for (int index = 0; index < copyFrom.length; index++) {
/* 2789 */       newArray[index + arrLen] = copyFrom[index];
/*      */     }
/* 2791 */     return newArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static bpq getMojangLogoTexture(bpq texDefault) {
/*      */     try {
/* 2802 */       bqx locationMojangPng = new bqx("textures/gui/title/mojang.png");
/* 2803 */       InputStream in = getResourceStream(locationMojangPng);
/*      */       
/* 2805 */       if (in == null) {
/* 2806 */         return texDefault;
/*      */       }
/* 2808 */       BufferedImage bi = ImageIO.read(in);
/* 2809 */       if (bi == null) {
/* 2810 */         return texDefault;
/*      */       }
/* 2812 */       bpq dt = new bpq(bi);
/* 2813 */       return dt;
/*      */     }
/* 2815 */     catch (Exception e) {
/*      */       
/* 2817 */       warn(e.getClass().getName() + ": " + e.getMessage());
/*      */       
/* 2819 */       return texDefault;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void writeFile(File file, String str) throws IOException {
/* 2828 */     FileOutputStream fos = new FileOutputStream(file);
/* 2829 */     byte[] bytes = str.getBytes("ASCII");
/* 2830 */     fos.write(bytes);
/* 2831 */     fos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDynamicFov() {
/* 2838 */     return gameSettings.ofDynamicFov;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static bpz getTextureMap() {
/* 2845 */     return getMinecraft().T();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDynamicLights() {
/* 2852 */     return (gameSettings.ofDynamicLights != 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDynamicLightsFast() {
/* 2859 */     return (gameSettings.ofDynamicLights == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDynamicHandLight() {
/* 2867 */     if (!isDynamicLights()) {
/* 2868 */       return false;
/*      */     }
/* 2870 */     if (isShaders()) {
/* 2871 */       return Shaders.isDynamicHandLight();
/*      */     }
/* 2873 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] toPrimitive(Integer[] arr) {
/* 2881 */     if (arr == null) {
/* 2882 */       return null;
/*      */     }
/* 2884 */     if (arr.length == 0) {
/* 2885 */       return new int[0];
/*      */     }
/* 2887 */     int[] intArr = new int[arr.length];
/* 2888 */     for (int i = 0; i < intArr.length; i++)
/*      */     {
/* 2890 */       intArr[i] = arr[i].intValue();
/*      */     }
/*      */     
/* 2893 */     return intArr;
/*      */   }
/*      */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\Config.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */