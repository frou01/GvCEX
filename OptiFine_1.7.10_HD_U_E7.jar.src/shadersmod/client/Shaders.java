/*      */ package shadersmod.client;
/*      */ 
/*      */ import Config;
/*      */ import CustomColorizer;
/*      */ import GlStateManager;
/*      */ import Lang;
/*      */ import PropertiesOrdered;
/*      */ import Reflector;
/*      */ import StrUtils;
/*      */ import abh;
/*      */ import adb;
/*      */ import add;
/*      */ import ahb;
/*      */ import aji;
/*      */ import aor;
/*      */ import awt;
/*      */ import azw;
/*      */ import bam;
/*      */ import bao;
/*      */ import bbj;
/*      */ import bhr;
/*      */ import bix;
/*      */ import blm;
/*      */ import blt;
/*      */ import bmh;
/*      */ import bnn;
/*      */ import bno;
/*      */ import boh;
/*      */ import bpz;
/*      */ import bqh;
/*      */ import buu;
/*      */ import fj;
/*      */ import fq;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FileReader;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.Reader;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.IdentityHashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import org.apache.commons.io.IOUtils;
/*      */ import org.lwjgl.BufferUtils;
/*      */ import org.lwjgl.opengl.ARBShaderObjects;
/*      */ import org.lwjgl.opengl.ARBVertexShader;
/*      */ import org.lwjgl.opengl.ContextCapabilities;
/*      */ import org.lwjgl.opengl.EXTFramebufferObject;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GL20;
/*      */ import org.lwjgl.opengl.GL30;
/*      */ import org.lwjgl.opengl.GLContext;
/*      */ import org.lwjgl.util.glu.GLU;
/*      */ import rv;
/*      */ import sa;
/*      */ import sg;
/*      */ import shadersmod.common.SMCLog;
/*      */ import shadersmod.uniform.CustomUniforms;
/*      */ import shadersmod.uniform.LegacyUniforms;
/*      */ import shadersmod.uniform.ShaderUniformFloat4;
/*      */ import shadersmod.uniform.ShaderUniformInt;
/*      */ import shadersmod.uniform.Smoother;
/*      */ import sv;
/*      */ import yz;
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
/*      */ public class Shaders
/*      */ {
/*      */   static bao mc;
/*      */   static blt entityRenderer;
/*      */   public static boolean isInitializedOnce = false;
/*      */   public static boolean isShaderPackInitialized = false;
/*      */   public static ContextCapabilities capabilities;
/*      */   public static String glVersionString;
/*      */   public static String glVendorString;
/*      */   public static String glRendererString;
/*      */   public static boolean hasGlGenMipmap = false;
/*      */   public static boolean hasForge = false;
/*  113 */   public static int numberResetDisplayList = 0;
/*      */   
/*      */   static boolean needResetModels = false;
/*  116 */   private static int renderDisplayWidth = 0;
/*  117 */   private static int renderDisplayHeight = 0;
/*  118 */   public static int renderWidth = 0;
/*  119 */   public static int renderHeight = 0;
/*      */   
/*      */   public static boolean isRenderingWorld = false;
/*      */   
/*      */   public static boolean isRenderingSky = false;
/*      */   
/*      */   public static boolean isCompositeRendered = false;
/*      */   public static boolean isRenderingDfb = false;
/*      */   public static boolean isShadowPass = false;
/*      */   public static boolean isSleeping;
/*      */   private static boolean isRenderingFirstPersonHand;
/*      */   private static boolean isHandRenderedMain;
/*      */   public static boolean renderItemKeepDepthMask = false;
/*      */   public static boolean itemToRenderMainTranslucent = false;
/*  133 */   static float[] sunPosition = new float[4];
/*  134 */   static float[] moonPosition = new float[4];
/*  135 */   static float[] shadowLightPosition = new float[4];
/*  136 */   static float[] upPosition = new float[4];
/*  137 */   static float[] shadowLightPositionVector = new float[4];
/*      */   
/*  139 */   static float[] upPosModelView = new float[] { 0.0F, 100.0F, 0.0F, 0.0F };
/*  140 */   static float[] sunPosModelView = new float[] { 0.0F, 100.0F, 0.0F, 0.0F };
/*  141 */   static float[] moonPosModelView = new float[] { 0.0F, -100.0F, 0.0F, 0.0F };
/*  142 */   private static float[] tempMat = new float[16];
/*      */   
/*      */   static float clearColorR;
/*      */   
/*      */   static float clearColorG;
/*      */   
/*      */   static float clearColorB;
/*      */   static float skyColorR;
/*      */   static float skyColorG;
/*      */   static float skyColorB;
/*  152 */   static long worldTime = 0L;
/*  153 */   static long lastWorldTime = 0L;
/*  154 */   static long diffWorldTime = 0L;
/*  155 */   static float celestialAngle = 0.0F;
/*  156 */   static float sunAngle = 0.0F;
/*  157 */   static float shadowAngle = 0.0F;
/*  158 */   static int moonPhase = 0;
/*      */   
/*  160 */   static long systemTime = 0L;
/*  161 */   static long lastSystemTime = 0L;
/*  162 */   static long diffSystemTime = 0L;
/*      */   
/*  164 */   static int frameCounter = 0;
/*  165 */   static float frameTime = 0.0F;
/*  166 */   static float frameTimeCounter = 0.0F;
/*      */   
/*  168 */   static int systemTimeInt32 = 0;
/*      */   
/*  170 */   static float rainStrength = 0.0F;
/*  171 */   static float wetness = 0.0F;
/*  172 */   public static float wetnessHalfLife = 600.0F;
/*  173 */   public static float drynessHalfLife = 200.0F;
/*  174 */   public static float eyeBrightnessHalflife = 10.0F;
/*      */   static boolean usewetness = false;
/*  176 */   static int isEyeInWater = 0;
/*  177 */   static int eyeBrightness = 0;
/*  178 */   static float eyeBrightnessFadeX = 0.0F;
/*  179 */   static float eyeBrightnessFadeY = 0.0F;
/*  180 */   static float eyePosY = 0.0F;
/*  181 */   static float centerDepth = 0.0F;
/*  182 */   static float centerDepthSmooth = 0.0F;
/*  183 */   static float centerDepthSmoothHalflife = 1.0F;
/*      */   static boolean centerDepthSmoothEnabled = false;
/*  185 */   static int superSamplingLevel = 1;
/*  186 */   static float nightVision = 0.0F;
/*  187 */   static float blindness = 0.0F;
/*      */   
/*      */   static boolean updateChunksErrorRecorded = false;
/*      */   
/*      */   static boolean lightmapEnabled = false;
/*      */   
/*      */   static boolean fogEnabled = true;
/*  194 */   public static int entityAttrib = 10;
/*  195 */   public static int midTexCoordAttrib = 11;
/*  196 */   public static int tangentAttrib = 12;
/*      */   
/*      */   public static boolean useEntityAttrib = false;
/*      */   public static boolean useMidTexCoordAttrib = false;
/*      */   public static boolean useMultiTexCoord3Attrib = false;
/*      */   public static boolean useTangentAttrib = false;
/*      */   public static boolean progUseEntityAttrib = false;
/*      */   public static boolean progUseMidTexCoordAttrib = false;
/*      */   public static boolean progUseTangentAttrib = false;
/*  205 */   public static int atlasSizeX = 0; public static int atlasSizeY = 0;
/*      */   
/*      */   public static boolean useEntityColor = true;
/*  208 */   public static ShaderUniformFloat4 uniformEntityColor = new ShaderUniformFloat4("entityColor");
/*  209 */   public static ShaderUniformInt uniformEntityId = new ShaderUniformInt("entityId");
/*  210 */   public static ShaderUniformInt uniformBlockEntityId = new ShaderUniformInt("blockEntityId");
/*      */   
/*      */   static double previousCameraPositionX;
/*      */   static double previousCameraPositionY;
/*      */   static double previousCameraPositionZ;
/*      */   static double cameraPositionX;
/*      */   static double cameraPositionY;
/*      */   static double cameraPositionZ;
/*  218 */   static int shadowPassInterval = 0;
/*      */   public static boolean needResizeShadow = false;
/*  220 */   static int shadowMapWidth = 1024;
/*  221 */   static int shadowMapHeight = 1024;
/*  222 */   static int spShadowMapWidth = 1024;
/*  223 */   static int spShadowMapHeight = 1024;
/*  224 */   static float shadowMapFOV = 90.0F;
/*  225 */   static float shadowMapHalfPlane = 160.0F;
/*      */   static boolean shadowMapIsOrtho = true;
/*  227 */   static float shadowDistanceRenderMul = -1.0F;
/*      */   
/*  229 */   static int shadowPassCounter = 0;
/*      */   
/*      */   static int preShadowPassThirdPersonView;
/*      */   
/*      */   public static boolean shouldSkipDefaultShadow = false;
/*      */   
/*      */   static boolean waterShadowEnabled = false;
/*      */   
/*      */   static final int MaxDrawBuffers = 8;
/*      */   
/*      */   static final int MaxColorBuffers = 8;
/*      */   
/*      */   static final int MaxDepthBuffers = 3;
/*      */   static final int MaxShadowColorBuffers = 8;
/*      */   static final int MaxShadowDepthBuffers = 2;
/*  244 */   static int usedColorBuffers = 0;
/*  245 */   static int usedDepthBuffers = 0;
/*  246 */   static int usedShadowColorBuffers = 0;
/*  247 */   static int usedShadowDepthBuffers = 0;
/*  248 */   static int usedColorAttachs = 0;
/*  249 */   static int usedDrawBuffers = 0;
/*      */   
/*  251 */   static int dfb = 0;
/*  252 */   static int sfb = 0;
/*      */   
/*  254 */   private static int[] gbuffersFormat = new int[8];
/*  255 */   public static boolean[] gbuffersClear = new boolean[8];
/*      */ 
/*      */ 
/*      */   
/*  259 */   public static int activeProgram = 0;
/*      */   
/*      */   public static final int ProgramNone = 0;
/*      */   
/*      */   public static final int ProgramBasic = 1;
/*      */   
/*      */   public static final int ProgramTextured = 2;
/*      */   
/*      */   public static final int ProgramTexturedLit = 3;
/*      */   
/*      */   public static final int ProgramSkyBasic = 4;
/*      */   
/*      */   public static final int ProgramSkyTextured = 5;
/*      */   public static final int ProgramClouds = 6;
/*      */   public static final int ProgramTerrain = 7;
/*      */   public static final int ProgramTerrainSolid = 8;
/*      */   public static final int ProgramTerrainCutoutMip = 9;
/*      */   public static final int ProgramTerrainCutout = 10;
/*      */   public static final int ProgramDamagedBlock = 11;
/*      */   public static final int ProgramWater = 12;
/*      */   public static final int ProgramBlock = 13;
/*      */   public static final int ProgramBeaconBeam = 14;
/*      */   public static final int ProgramItem = 15;
/*      */   public static final int ProgramEntities = 16;
/*      */   public static final int ProgramArmorGlint = 17;
/*      */   public static final int ProgramSpiderEyes = 18;
/*      */   public static final int ProgramHand = 19;
/*      */   public static final int ProgramWeather = 20;
/*      */   public static final int ProgramComposite = 21;
/*      */   public static final int ProgramComposite1 = 22;
/*      */   public static final int ProgramComposite2 = 23;
/*      */   public static final int ProgramComposite3 = 24;
/*      */   public static final int ProgramComposite4 = 25;
/*      */   public static final int ProgramComposite5 = 26;
/*      */   public static final int ProgramComposite6 = 27;
/*      */   public static final int ProgramComposite7 = 28;
/*      */   public static final int ProgramFinal = 29;
/*      */   public static final int ProgramShadow = 30;
/*      */   public static final int ProgramShadowSolid = 31;
/*      */   public static final int ProgramShadowCutout = 32;
/*      */   public static final int ProgramDeferred = 33;
/*      */   public static final int ProgramDeferred1 = 34;
/*      */   public static final int ProgramDeferred2 = 35;
/*      */   public static final int ProgramDeferred3 = 36;
/*      */   public static final int ProgramDeferred4 = 37;
/*      */   public static final int ProgramDeferred5 = 38;
/*      */   public static final int ProgramDeferred6 = 39;
/*      */   public static final int ProgramDeferred7 = 40;
/*      */   public static final int ProgramHandWater = 41;
/*      */   public static final int ProgramDeferredLast = 42;
/*      */   public static final int ProgramCompositeLast = 43;
/*      */   public static final int ProgramCount = 44;
/*      */   public static final int MaxCompositePasses = 8;
/*      */   public static final int MaxDeferredPasses = 8;
/*  313 */   private static final String[] programNames = new String[] { "", "gbuffers_basic", "gbuffers_textured", "gbuffers_textured_lit", "gbuffers_skybasic", "gbuffers_skytextured", "gbuffers_clouds", "gbuffers_terrain", "gbuffers_terrain_solid", "gbuffers_terrain_cutout_mip", "gbuffers_terrain_cutout", "gbuffers_damagedblock", "gbuffers_water", "gbuffers_block", "gbuffers_beaconbeam", "gbuffers_item", "gbuffers_entities", "gbuffers_armor_glint", "gbuffers_spidereyes", "gbuffers_hand", "gbuffers_weather", "composite", "composite1", "composite2", "composite3", "composite4", "composite5", "composite6", "composite7", "final", "shadow", "shadow_solid", "shadow_cutout", "deferred", "deferred1", "deferred2", "deferred3", "deferred4", "deferred5", "deferred6", "deferred7", "gbuffers_hand_water", "deferred_last", "composite_last" };
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
/*  360 */   private static final int[] programBackups = new int[] { 0, 0, 1, 2, 1, 2, 2, 3, 7, 7, 7, 7, 7, 7, 2, 3, 3, 2, 2, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 30, 30, 0, 0, 0, 0, 0, 0, 0, 0, 19, 0, 0 };
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
/*      */   
/*  408 */   static int[] programsID = new int[44];
/*  409 */   private static int[] programsRef = new int[44];
/*  410 */   private static int programIDCopyDepth = 0;
/*      */   
/*      */   private static boolean hasDeferredPrograms = false;
/*  413 */   public static String[] programsDrawBufSettings = new String[44];
/*  414 */   private static String newDrawBufSetting = null;
/*  415 */   static IntBuffer[] programsDrawBuffers = new IntBuffer[44];
/*  416 */   static IntBuffer activeDrawBuffers = null;
/*      */   
/*  418 */   private static String[] programsColorAtmSettings = new String[44];
/*  419 */   private static String newColorAtmSetting = null;
/*  420 */   private static String activeColorAtmSettings = null;
/*      */   
/*  422 */   private static int[] programsCompositeMipmapSetting = new int[44];
/*  423 */   private static int newCompositeMipmapSetting = 0;
/*  424 */   private static int activeCompositeMipmapSetting = 0;
/*      */   
/*  426 */   public static Properties loadedShaders = null;
/*  427 */   public static Properties shadersConfig = null;
/*      */ 
/*      */   
/*  430 */   public static bqh defaultTexture = null;
/*      */   public static boolean normalMapEnabled = false;
/*  432 */   public static boolean[] shadowHardwareFilteringEnabled = new boolean[2];
/*  433 */   public static boolean[] shadowMipmapEnabled = new boolean[2];
/*  434 */   public static boolean[] shadowFilterNearest = new boolean[2];
/*  435 */   public static boolean[] shadowColorMipmapEnabled = new boolean[8];
/*  436 */   public static boolean[] shadowColorFilterNearest = new boolean[8];
/*      */   
/*      */   public static boolean configTweakBlockDamage = false;
/*      */   
/*      */   public static boolean configCloudShadow = false;
/*      */   
/*  442 */   public static float configHandDepthMul = 0.125F;
/*  443 */   public static float configRenderResMul = 1.0F;
/*  444 */   public static float configShadowResMul = 1.0F;
/*  445 */   public static int configTexMinFilB = 0;
/*  446 */   public static int configTexMinFilN = 0;
/*  447 */   public static int configTexMinFilS = 0;
/*  448 */   public static int configTexMagFilB = 0;
/*  449 */   public static int configTexMagFilN = 0;
/*  450 */   public static int configTexMagFilS = 0;
/*      */   public static boolean configShadowClipFrustrum = true;
/*      */   public static boolean configNormalMap = true;
/*      */   public static boolean configSpecularMap = true;
/*  454 */   public static PropertyDefaultTrueFalse configOldLighting = new PropertyDefaultTrueFalse("oldLighting", "Classic Lighting", 0);
/*  455 */   public static PropertyDefaultTrueFalse configOldHandLight = new PropertyDefaultTrueFalse("oldHandLight", "Old Hand Light", 0);
/*  456 */   public static int configAntialiasingLevel = 0;
/*      */   
/*      */   public static final int texMinFilRange = 3;
/*      */   
/*      */   public static final int texMagFilRange = 2;
/*  461 */   public static final String[] texMinFilDesc = new String[] { "Nearest", "Nearest-Nearest", "Nearest-Linear" };
/*  462 */   public static final String[] texMagFilDesc = new String[] { "Nearest", "Linear" };
/*  463 */   public static final int[] texMinFilValue = new int[] { 9728, 9984, 9986 };
/*  464 */   public static final int[] texMagFilValue = new int[] { 9728, 9729 };
/*      */ 
/*      */   
/*  467 */   static IShaderPack shaderPack = null;
/*      */   public static boolean shaderPackLoaded = false;
/*      */   static File currentshader;
/*      */   static String currentshadername;
/*  471 */   public static String packNameNone = "OFF";
/*  472 */   static String packNameDefault = "(internal)";
/*  473 */   static String shaderpacksdirname = "shaderpacks";
/*  474 */   static String optionsfilename = "optionsshaders.txt";
/*  475 */   static File shadersdir = new File((bao.B()).w, "shaders");
/*  476 */   static File shaderpacksdir = new File((bao.B()).w, shaderpacksdirname);
/*  477 */   static File configFile = new File((bao.B()).w, optionsfilename);
/*      */   
/*  479 */   static ShaderOption[] shaderPackOptions = null;
/*      */   
/*  481 */   static Set<String> shaderPackOptionSliders = null;
/*      */   
/*  483 */   static ShaderProfile[] shaderPackProfiles = null;
/*      */   
/*  485 */   static Map<String, ScreenShaderOptions> shaderPackGuiScreens = null;
/*      */   
/*  487 */   public static PropertyDefaultFastFancyOff shaderPackClouds = new PropertyDefaultFastFancyOff("clouds", "Clouds", 0);
/*  488 */   public static PropertyDefaultTrueFalse shaderPackOldLighting = new PropertyDefaultTrueFalse("oldLighting", "Classic Lighting", 0);
/*  489 */   public static PropertyDefaultTrueFalse shaderPackOldHandLight = new PropertyDefaultTrueFalse("oldHandLight", "Old Hand Light", 0);
/*  490 */   public static PropertyDefaultTrueFalse shaderPackDynamicHandLight = new PropertyDefaultTrueFalse("dynamicHandLight", "Dynamic Hand Light", 0);
/*  491 */   public static PropertyDefaultTrueFalse shaderPackShadowTranslucent = new PropertyDefaultTrueFalse("shadowTranslucent", "Shadow Translucent", 0);
/*  492 */   public static PropertyDefaultTrueFalse shaderPackUnderwaterOverlay = new PropertyDefaultTrueFalse("underwaterOverlay", "Underwater Overlay", 0);
/*  493 */   public static PropertyDefaultTrueFalse shaderPackSun = new PropertyDefaultTrueFalse("sun", "Sun", 0);
/*  494 */   public static PropertyDefaultTrueFalse shaderPackMoon = new PropertyDefaultTrueFalse("moon", "Moon", 0);
/*  495 */   public static PropertyDefaultTrueFalse shaderPackVignette = new PropertyDefaultTrueFalse("vignette", "Vignette", 0);
/*      */   
/*  497 */   public static PropertyDefaultTrueFalse shaderPackBackFaceSolid = new PropertyDefaultTrueFalse("backFace.solid", "Back-face Solid", 0);
/*  498 */   public static PropertyDefaultTrueFalse shaderPackBackFaceCutout = new PropertyDefaultTrueFalse("backFace.cutout", "Back-face Cutout", 0);
/*  499 */   public static PropertyDefaultTrueFalse shaderPackBackFaceCutoutMipped = new PropertyDefaultTrueFalse("backFace.cutoutMipped", "Back-face Cutout Mipped", 0);
/*  500 */   public static PropertyDefaultTrueFalse shaderPackBackFaceTranslucent = new PropertyDefaultTrueFalse("backFace.translucent", "Back-face Translucent", 0);
/*      */   
/*  502 */   private static Map<String, String> shaderPackResources = new HashMap<String, String>();
/*      */   
/*  504 */   private static ahb currentWorld = null;
/*      */   
/*  506 */   private static List<Integer> shaderPackDimensions = new ArrayList<Integer>();
/*      */   
/*  508 */   private static CustomTexture[] customTexturesGbuffers = null;
/*  509 */   private static CustomTexture[] customTexturesComposite = null;
/*  510 */   private static CustomTexture[] customTexturesDeferred = null;
/*      */   
/*  512 */   private static String noiseTexturePath = null;
/*      */   
/*  514 */   private static CustomUniforms customUniforms = null;
/*      */   
/*      */   private static final int STAGE_GBUFFERS = 0;
/*      */   private static final int STAGE_COMPOSITE = 1;
/*      */   private static final int STAGE_DEFERRED = 2;
/*  519 */   private static final String[] STAGE_NAMES = new String[] { "gbuffers", "composite", "deferred" };
/*      */   
/*      */   public static final boolean enableShadersOption = true;
/*      */   private static final boolean enableShadersDebug = true;
/*  523 */   private static final boolean saveFinalShaders = System.getProperty("shaders.debug.save", "false").equals("true");
/*      */   
/*  525 */   public static float blockLightLevel05 = 0.5F;
/*  526 */   public static float blockLightLevel06 = 0.6F;
/*  527 */   public static float blockLightLevel08 = 0.8F;
/*      */ 
/*      */   
/*  530 */   public static float aoLevel = -1.0F;
/*      */   
/*  532 */   public static float sunPathRotation = 0.0F;
/*  533 */   public static float shadowAngleInterval = 0.0F;
/*  534 */   public static int fogMode = 0;
/*      */   public static float fogColorR;
/*  536 */   public static float shadowIntervalSize = 2.0F; public static float fogColorG; public static float fogColorB;
/*  537 */   public static int terrainIconSize = 16;
/*  538 */   public static int[] terrainTextureSize = new int[2];
/*      */   
/*      */   private static ICustomTexture noiseTexture;
/*      */   private static boolean noiseTextureEnabled = false;
/*  542 */   private static int noiseTextureResolution = 256;
/*      */   
/*  544 */   static final int[] dfbColorTexturesA = new int[16];
/*  545 */   static final int[] colorTexturesToggle = new int[8];
/*  546 */   static final int[] colorTextureTextureImageUnit = new int[] { 0, 1, 2, 3, 7, 8, 9, 10 };
/*  547 */   static final boolean[][] programsToggleColorTextures = new boolean[44][8];
/*      */ 
/*      */   
/*      */   private static final int bigBufferSize = 2548;
/*      */ 
/*      */   
/*  553 */   private static final ByteBuffer bigBuffer = (ByteBuffer)BufferUtils.createByteBuffer(2548).limit(0);
/*      */   
/*  555 */   static final float[] faProjection = new float[16];
/*  556 */   static final float[] faProjectionInverse = new float[16];
/*  557 */   static final float[] faModelView = new float[16];
/*  558 */   static final float[] faModelViewInverse = new float[16];
/*  559 */   static final float[] faShadowProjection = new float[16];
/*  560 */   static final float[] faShadowProjectionInverse = new float[16];
/*  561 */   static final float[] faShadowModelView = new float[16];
/*  562 */   static final float[] faShadowModelViewInverse = new float[16];
/*      */   
/*  564 */   static final FloatBuffer projection = nextFloatBuffer(16);
/*  565 */   static final FloatBuffer projectionInverse = nextFloatBuffer(16);
/*  566 */   static final FloatBuffer modelView = nextFloatBuffer(16);
/*  567 */   static final FloatBuffer modelViewInverse = nextFloatBuffer(16);
/*  568 */   static final FloatBuffer shadowProjection = nextFloatBuffer(16);
/*  569 */   static final FloatBuffer shadowProjectionInverse = nextFloatBuffer(16);
/*  570 */   static final FloatBuffer shadowModelView = nextFloatBuffer(16);
/*  571 */   static final FloatBuffer shadowModelViewInverse = nextFloatBuffer(16);
/*      */   
/*  573 */   static final FloatBuffer previousProjection = nextFloatBuffer(16);
/*  574 */   static final FloatBuffer previousModelView = nextFloatBuffer(16);
/*  575 */   static final FloatBuffer tempMatrixDirectBuffer = nextFloatBuffer(16);
/*  576 */   static final FloatBuffer tempDirectFloatBuffer = nextFloatBuffer(16);
/*      */   
/*  578 */   static final IntBuffer dfbColorTextures = nextIntBuffer(16);
/*      */   
/*  580 */   static final IntBuffer dfbDepthTextures = nextIntBuffer(3);
/*  581 */   static final IntBuffer sfbColorTextures = nextIntBuffer(8);
/*  582 */   static final IntBuffer sfbDepthTextures = nextIntBuffer(2);
/*      */   
/*  584 */   static final IntBuffer dfbDrawBuffers = nextIntBuffer(8);
/*  585 */   static final IntBuffer sfbDrawBuffers = nextIntBuffer(8);
/*  586 */   static final IntBuffer drawBuffersNone = nextIntBuffer(8);
/*  587 */   static final IntBuffer drawBuffersAll = nextIntBuffer(8);
/*  588 */   static final IntBuffer drawBuffersClear0 = nextIntBuffer(8);
/*  589 */   static final IntBuffer drawBuffersClear1 = nextIntBuffer(8);
/*  590 */   static final IntBuffer drawBuffersClearColor = nextIntBuffer(8);
/*  591 */   static final IntBuffer drawBuffersColorAtt0 = nextIntBuffer(8);
/*      */   
/*  593 */   static final IntBuffer[] drawBuffersBuffer = nextIntBufferArray(44, 8);
/*      */   
/*      */   static Map<aji, Integer> mapBlockToEntityData;
/*      */ 
/*      */   
/*      */   static {
/*  599 */     drawBuffersNone.limit(0);
/*  600 */     drawBuffersColorAtt0.put(36064).position(0).limit(1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ByteBuffer nextByteBuffer(int size) {
/*  610 */     ByteBuffer buffer = bigBuffer;
/*  611 */     int pos = buffer.limit();
/*  612 */     buffer.position(pos).limit(pos + size);
/*  613 */     return buffer.slice();
/*      */   }
/*      */ 
/*      */   
/*      */   private static IntBuffer nextIntBuffer(int size) {
/*  618 */     ByteBuffer buffer = bigBuffer;
/*  619 */     int pos = buffer.limit();
/*  620 */     buffer.position(pos).limit(pos + size * 4);
/*  621 */     return buffer.asIntBuffer();
/*      */   }
/*      */ 
/*      */   
/*      */   private static FloatBuffer nextFloatBuffer(int size) {
/*  626 */     ByteBuffer buffer = bigBuffer;
/*  627 */     int pos = buffer.limit();
/*  628 */     buffer.position(pos).limit(pos + size * 4);
/*  629 */     return buffer.asFloatBuffer();
/*      */   }
/*      */ 
/*      */   
/*      */   private static IntBuffer[] nextIntBufferArray(int count, int size) {
/*  634 */     IntBuffer[] aib = new IntBuffer[count];
/*  635 */     for (int i = 0; i < count; i++)
/*  636 */       aib[i] = nextIntBuffer(size); 
/*  637 */     return aib;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void loadConfig() {
/*  642 */     SMCLog.info("Load ShadersMod configuration.");
/*      */     
/*      */     try {
/*  645 */       if (!shaderpacksdir.exists()) {
/*  646 */         shaderpacksdir.mkdir();
/*      */       }
/*  648 */     } catch (Exception e) {
/*      */       
/*  650 */       SMCLog.severe("Failed to open the shaderpacks directory: " + shaderpacksdir);
/*      */     } 
/*      */     
/*  653 */     shadersConfig = (Properties)new PropertiesOrdered();
/*  654 */     shadersConfig.setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), "");
/*  655 */     if (configFile.exists()) {
/*      */       
/*      */       try {
/*      */         
/*  659 */         FileReader reader = new FileReader(configFile);
/*  660 */         shadersConfig.load(reader);
/*  661 */         reader.close();
/*      */       }
/*  663 */       catch (Exception e) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  668 */     if (!configFile.exists()) {
/*      */       
/*      */       try {
/*      */         
/*  672 */         storeConfig();
/*      */       }
/*  674 */       catch (Exception e) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  679 */     EnumShaderOption[] ops = EnumShaderOption.values();
/*  680 */     for (int i = 0; i < ops.length; i++) {
/*      */       
/*  682 */       EnumShaderOption op = ops[i];
/*  683 */       String key = op.getPropertyKey();
/*  684 */       String def = op.getValueDefault();
/*  685 */       String val = shadersConfig.getProperty(key, def);
/*  686 */       setEnumShaderOption(op, val);
/*      */     } 
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
/*  705 */     loadShaderPack();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setEnumShaderOption(EnumShaderOption eso, String str) {
/*  711 */     if (str == null) {
/*  712 */       str = eso.getValueDefault();
/*      */     }
/*  714 */     switch (eso) {
/*      */       
/*      */       case ANTIALIASING:
/*  717 */         configAntialiasingLevel = Config.parseInt(str, 0);
/*      */         return;
/*      */       case NORMAL_MAP:
/*  720 */         configNormalMap = Config.parseBoolean(str, true);
/*      */         return;
/*      */       case SPECULAR_MAP:
/*  723 */         configSpecularMap = Config.parseBoolean(str, true);
/*      */         return;
/*      */       case RENDER_RES_MUL:
/*  726 */         configRenderResMul = Config.parseFloat(str, 1.0F);
/*      */         return;
/*      */       case SHADOW_RES_MUL:
/*  729 */         configShadowResMul = Config.parseFloat(str, 1.0F);
/*      */         return;
/*      */       case HAND_DEPTH_MUL:
/*  732 */         configHandDepthMul = Config.parseFloat(str, 0.125F);
/*      */         return;
/*      */       case CLOUD_SHADOW:
/*  735 */         configCloudShadow = Config.parseBoolean(str, true);
/*      */         return;
/*      */       case OLD_HAND_LIGHT:
/*  738 */         configOldHandLight.setPropertyValue(str);
/*      */         return;
/*      */       case OLD_LIGHTING:
/*  741 */         configOldLighting.setPropertyValue(str);
/*      */         return;
/*      */       case SHADER_PACK:
/*  744 */         currentshadername = str;
/*      */         return;
/*      */       
/*      */       case TWEAK_BLOCK_DAMAGE:
/*  748 */         configTweakBlockDamage = Config.parseBoolean(str, true);
/*      */         return;
/*      */       case SHADOW_CLIP_FRUSTRUM:
/*  751 */         configShadowClipFrustrum = Config.parseBoolean(str, true);
/*      */         return;
/*      */       case TEX_MIN_FIL_B:
/*  754 */         configTexMinFilB = Config.parseInt(str, 0);
/*      */         return;
/*      */       case TEX_MIN_FIL_N:
/*  757 */         configTexMinFilN = Config.parseInt(str, 0);
/*      */         return;
/*      */       case TEX_MIN_FIL_S:
/*  760 */         configTexMinFilS = Config.parseInt(str, 0);
/*      */         return;
/*      */       case TEX_MAG_FIL_B:
/*  763 */         configTexMagFilB = Config.parseInt(str, 0);
/*      */         return;
/*      */       case TEX_MAG_FIL_N:
/*  766 */         configTexMagFilB = Config.parseInt(str, 0);
/*      */         return;
/*      */       case TEX_MAG_FIL_S:
/*  769 */         configTexMagFilB = Config.parseInt(str, 0);
/*      */         return;
/*      */     } 
/*      */     
/*  773 */     throw new IllegalArgumentException("Unknown option: " + eso);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeConfig() {
/*  779 */     SMCLog.info("Save ShadersMod configuration.");
/*      */     
/*  781 */     if (shadersConfig == null) {
/*  782 */       shadersConfig = (Properties)new PropertiesOrdered();
/*      */     }
/*  784 */     EnumShaderOption[] ops = EnumShaderOption.values();
/*  785 */     for (int i = 0; i < ops.length; i++) {
/*      */       
/*  787 */       EnumShaderOption op = ops[i];
/*  788 */       String key = op.getPropertyKey();
/*  789 */       String val = getEnumShaderOption(op);
/*  790 */       shadersConfig.setProperty(key, val);
/*      */     } 
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
/*      */     try {
/*  810 */       FileWriter writer = new FileWriter(configFile);
/*  811 */       shadersConfig.store(writer, (String)null);
/*  812 */       writer.close();
/*      */     }
/*  814 */     catch (Exception ex) {
/*      */       
/*  816 */       SMCLog.severe("Error saving configuration: " + ex.getClass().getName() + ": " + ex.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getEnumShaderOption(EnumShaderOption eso) {
/*  822 */     switch (eso) {
/*      */       
/*      */       case ANTIALIASING:
/*  825 */         return Integer.toString(configAntialiasingLevel);
/*      */       case NORMAL_MAP:
/*  827 */         return Boolean.toString(configNormalMap);
/*      */       case SPECULAR_MAP:
/*  829 */         return Boolean.toString(configSpecularMap);
/*      */       case RENDER_RES_MUL:
/*  831 */         return Float.toString(configRenderResMul);
/*      */       case SHADOW_RES_MUL:
/*  833 */         return Float.toString(configShadowResMul);
/*      */       case HAND_DEPTH_MUL:
/*  835 */         return Float.toString(configHandDepthMul);
/*      */       case CLOUD_SHADOW:
/*  837 */         return Boolean.toString(configCloudShadow);
/*      */       case OLD_HAND_LIGHT:
/*  839 */         return configOldHandLight.getPropertyValue();
/*      */       case OLD_LIGHTING:
/*  841 */         return configOldLighting.getPropertyValue();
/*      */       case SHADER_PACK:
/*  843 */         return currentshadername;
/*      */       
/*      */       case TWEAK_BLOCK_DAMAGE:
/*  846 */         return Boolean.toString(configTweakBlockDamage);
/*      */       case SHADOW_CLIP_FRUSTRUM:
/*  848 */         return Boolean.toString(configShadowClipFrustrum);
/*      */       case TEX_MIN_FIL_B:
/*  850 */         return Integer.toString(configTexMinFilB);
/*      */       case TEX_MIN_FIL_N:
/*  852 */         return Integer.toString(configTexMinFilN);
/*      */       case TEX_MIN_FIL_S:
/*  854 */         return Integer.toString(configTexMinFilS);
/*      */       case TEX_MAG_FIL_B:
/*  856 */         return Integer.toString(configTexMagFilB);
/*      */       case TEX_MAG_FIL_N:
/*  858 */         return Integer.toString(configTexMagFilB);
/*      */       case TEX_MAG_FIL_S:
/*  860 */         return Integer.toString(configTexMagFilB);
/*      */     } 
/*      */     
/*  863 */     throw new IllegalArgumentException("Unknown option: " + eso);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setShaderPack(String par1name) {
/*  870 */     currentshadername = par1name;
/*  871 */     shadersConfig.setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), par1name);
/*  872 */     loadShaderPack();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void loadShaderPack() {
/*  878 */     boolean shaderPackLoadedPrev = shaderPackLoaded;
/*  879 */     boolean oldLightingPrev = isOldLighting();
/*      */     
/*  881 */     shaderPackLoaded = false;
/*      */     
/*  883 */     if (shaderPack != null) {
/*      */       
/*  885 */       shaderPack.close();
/*  886 */       shaderPack = null;
/*  887 */       shaderPackResources.clear();
/*  888 */       shaderPackDimensions.clear();
/*  889 */       shaderPackOptions = null;
/*  890 */       shaderPackOptionSliders = null;
/*  891 */       shaderPackProfiles = null;
/*  892 */       shaderPackGuiScreens = null;
/*  893 */       shaderPackClouds.resetValue();
/*  894 */       shaderPackOldHandLight.resetValue();
/*  895 */       shaderPackDynamicHandLight.resetValue();
/*  896 */       shaderPackOldLighting.resetValue();
/*  897 */       resetCustomTextures();
/*  898 */       noiseTexturePath = null;
/*      */     } 
/*      */     
/*  901 */     boolean shadersBlocked = false;
/*  902 */     if (Config.isAntialiasing()) {
/*      */       
/*  904 */       SMCLog.info("Shaders can not be loaded, Antialiasing is enabled: " + Config.getAntialiasingLevel() + "x");
/*  905 */       shadersBlocked = true;
/*      */     } 
/*  907 */     if (Config.isAnisotropicFiltering()) {
/*      */       
/*  909 */       SMCLog.info("Shaders can not be loaded, Anisotropic Filtering is enabled: " + Config.getAnisotropicFilterLevel() + "x");
/*  910 */       shadersBlocked = true;
/*      */     } 
/*  912 */     if (Config.isFastRender()) {
/*      */       
/*  914 */       SMCLog.info("Shaders can not be loaded, Fast Render is enabled.");
/*  915 */       shadersBlocked = true;
/*      */     } 
/*      */     
/*  918 */     String packName = shadersConfig.getProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), packNameDefault);
/*  919 */     if (!packName.isEmpty() && !packName.equals(packNameNone) && !shadersBlocked)
/*      */     {
/*  921 */       if (packName.equals(packNameDefault)) {
/*      */         
/*  923 */         shaderPack = new ShaderPackDefault();
/*  924 */         shaderPackLoaded = true;
/*      */       } else {
/*      */ 
/*      */         
/*      */         try {
/*      */           
/*  930 */           File packFile = new File(shaderpacksdir, packName);
/*  931 */           if (packFile.isDirectory())
/*      */           {
/*  933 */             shaderPack = new ShaderPackFolder(packName, packFile);
/*  934 */             shaderPackLoaded = true;
/*      */           }
/*  936 */           else if (packFile.isFile() && packName.toLowerCase().endsWith(".zip"))
/*      */           {
/*  938 */             shaderPack = new ShaderPackZip(packName, packFile);
/*  939 */             shaderPackLoaded = true;
/*      */           }
/*      */         
/*  942 */         } catch (Exception e) {}
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  947 */     if (shaderPack != null) {
/*      */       
/*  949 */       SMCLog.info("Loaded shaderpack: " + getShaderPackName());
/*      */     }
/*      */     else {
/*      */       
/*  953 */       SMCLog.info("No shaderpack loaded.");
/*  954 */       shaderPack = new ShaderPackNone();
/*      */     } 
/*      */     
/*  957 */     loadShaderPackResources();
/*      */     
/*  959 */     loadShaderPackDimensions();
/*      */     
/*  961 */     shaderPackOptions = loadShaderPackOptions();
/*      */     
/*  963 */     loadShaderPackProperties();
/*      */     
/*  965 */     boolean formatChanged = (shaderPackLoaded != shaderPackLoadedPrev);
/*  966 */     boolean oldLightingChanged = (isOldLighting() != oldLightingPrev);
/*  967 */     if (formatChanged || oldLightingChanged)
/*      */     {
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
/*  980 */       if (mc.p != null) {
/*      */ 
/*      */         
/*  983 */         mc.p.setFxaaShader(0);
/*      */         
/*  985 */         mc.c();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void loadShaderPackDimensions() {
/*  995 */     shaderPackDimensions.clear();
/*      */     
/*  997 */     for (int i = -128; i <= 128; i++) {
/*      */       
/*  999 */       String worldDir = "/shaders/world" + i;
/* 1000 */       if (shaderPack.hasDirectory(worldDir)) {
/* 1001 */         shaderPackDimensions.add(Integer.valueOf(i));
/*      */       }
/*      */     } 
/* 1004 */     if (shaderPackDimensions.size() > 0) {
/*      */       
/* 1006 */       Integer[] ids = shaderPackDimensions.<Integer>toArray(new Integer[shaderPackDimensions.size()]);
/* 1007 */       Config.dbg("[Shaders] Worlds: " + Config.arrayToString((Object[])ids));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void loadShaderPackProperties() {
/* 1017 */     shaderPackClouds.resetValue();
/* 1018 */     shaderPackOldHandLight.resetValue();
/* 1019 */     shaderPackDynamicHandLight.resetValue();
/* 1020 */     shaderPackOldLighting.resetValue();
/* 1021 */     shaderPackShadowTranslucent.resetValue();
/* 1022 */     shaderPackUnderwaterOverlay.resetValue();
/* 1023 */     shaderPackSun.resetValue();
/* 1024 */     shaderPackMoon.resetValue();
/* 1025 */     shaderPackVignette.resetValue();
/* 1026 */     shaderPackBackFaceSolid.resetValue();
/* 1027 */     shaderPackBackFaceCutout.resetValue();
/* 1028 */     shaderPackBackFaceCutoutMipped.resetValue();
/* 1029 */     shaderPackBackFaceTranslucent.resetValue();
/* 1030 */     BlockAliases.reset();
/* 1031 */     customUniforms = null;
/* 1032 */     LegacyUniforms.reset();
/*      */     
/* 1034 */     if (shaderPack == null) {
/*      */       return;
/*      */     }
/* 1037 */     BlockAliases.update(shaderPack);
/*      */     
/* 1039 */     String path = "/shaders/shaders.properties";
/*      */     
/*      */     try {
/* 1042 */       InputStream in = shaderPack.getResourceAsStream(path);
/* 1043 */       if (in == null)
/*      */         return; 
/* 1045 */       PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 1046 */       propertiesOrdered.load(in);
/* 1047 */       in.close();
/*      */       
/* 1049 */       shaderPackClouds.loadFrom((Properties)propertiesOrdered);
/* 1050 */       shaderPackOldHandLight.loadFrom((Properties)propertiesOrdered);
/* 1051 */       shaderPackDynamicHandLight.loadFrom((Properties)propertiesOrdered);
/* 1052 */       shaderPackOldLighting.loadFrom((Properties)propertiesOrdered);
/* 1053 */       shaderPackShadowTranslucent.loadFrom((Properties)propertiesOrdered);
/* 1054 */       shaderPackUnderwaterOverlay.loadFrom((Properties)propertiesOrdered);
/* 1055 */       shaderPackSun.loadFrom((Properties)propertiesOrdered);
/* 1056 */       shaderPackVignette.loadFrom((Properties)propertiesOrdered);
/* 1057 */       shaderPackMoon.loadFrom((Properties)propertiesOrdered);
/* 1058 */       shaderPackBackFaceSolid.loadFrom((Properties)propertiesOrdered);
/* 1059 */       shaderPackBackFaceCutout.loadFrom((Properties)propertiesOrdered);
/* 1060 */       shaderPackBackFaceCutoutMipped.loadFrom((Properties)propertiesOrdered);
/* 1061 */       shaderPackBackFaceTranslucent.loadFrom((Properties)propertiesOrdered);
/*      */       
/* 1063 */       shaderPackOptionSliders = ShaderPackParser.parseOptionSliders((Properties)propertiesOrdered, shaderPackOptions);
/*      */       
/* 1065 */       shaderPackProfiles = ShaderPackParser.parseProfiles((Properties)propertiesOrdered, shaderPackOptions);
/*      */       
/* 1067 */       shaderPackGuiScreens = ShaderPackParser.parseGuiScreens((Properties)propertiesOrdered, shaderPackProfiles, shaderPackOptions);
/*      */       
/* 1069 */       customTexturesGbuffers = loadCustomTextures((Properties)propertiesOrdered, 0);
/* 1070 */       customTexturesComposite = loadCustomTextures((Properties)propertiesOrdered, 1);
/* 1071 */       customTexturesDeferred = loadCustomTextures((Properties)propertiesOrdered, 2);
/*      */       
/* 1073 */       noiseTexturePath = propertiesOrdered.getProperty("texture.noise");
/* 1074 */       if (noiseTexturePath != null) {
/* 1075 */         noiseTextureEnabled = true;
/*      */       }
/* 1077 */       customUniforms = ShaderPackParser.parseCustomUniforms((Properties)propertiesOrdered);
/*      */     }
/* 1079 */     catch (IOException e) {
/*      */       
/* 1081 */       Config.warn("[Shaders] Error reading: " + path);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static CustomTexture[] loadCustomTextures(Properties props, int stage) {
/* 1090 */     String PREFIX_TEXTURE = "texture." + STAGE_NAMES[stage] + ".";
/* 1091 */     Set keys = props.keySet();
/* 1092 */     List<CustomTexture> list = new ArrayList<CustomTexture>();
/* 1093 */     for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
/*      */       
/* 1095 */       String key = it.next();
/* 1096 */       if (!key.startsWith(PREFIX_TEXTURE)) {
/*      */         continue;
/*      */       }
/* 1099 */       String name = key.substring(PREFIX_TEXTURE.length());
/*      */       
/* 1101 */       String path = props.getProperty(key).trim();
/*      */       
/* 1103 */       int index = getTextureIndex(stage, name);
/*      */       
/* 1105 */       if (index < 0) {
/*      */         
/* 1107 */         SMCLog.warning("Invalid texture name: " + key);
/*      */         
/*      */         continue;
/*      */       } 
/* 1111 */       CustomTexture ct = loadCustomTexture(index, path);
/*      */       
/* 1113 */       if (ct != null) {
/* 1114 */         list.add(ct);
/*      */       }
/*      */     } 
/* 1117 */     if (list.size() <= 0) {
/* 1118 */       return null;
/*      */     }
/* 1120 */     CustomTexture[] cts = list.<CustomTexture>toArray(new CustomTexture[list.size()]);
/*      */     
/* 1122 */     return cts;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static CustomTexture loadCustomTexture(int index, String path) {
/* 1128 */     if (path == null) {
/* 1129 */       return null;
/*      */     }
/* 1131 */     path = path.trim();
/*      */     
/* 1133 */     if (path.indexOf('.') < 0) {
/* 1134 */       path = path + ".png";
/*      */     }
/*      */     
/*      */     try {
/* 1138 */       String pathFull = "shaders/" + StrUtils.removePrefix(path, "/");
/* 1139 */       InputStream in = shaderPack.getResourceAsStream(pathFull);
/* 1140 */       if (in == null) {
/*      */         
/* 1142 */         SMCLog.warning("Texture not found: " + path);
/* 1143 */         return null;
/*      */       } 
/* 1145 */       IOUtils.closeQuietly(in);
/*      */       
/* 1147 */       SimpleShaderTexture tex = new SimpleShaderTexture(pathFull);
/*      */       
/* 1149 */       tex.a(mc.Q());
/*      */       
/* 1151 */       CustomTexture ct = new CustomTexture(index, pathFull, (bqh)tex);
/*      */       
/* 1153 */       return ct;
/*      */     }
/* 1155 */     catch (IOException e) {
/*      */       
/* 1157 */       SMCLog.warning("Error loading texture: " + path);
/* 1158 */       SMCLog.warning("" + e.getClass().getName() + ": " + e.getMessage());
/* 1159 */       return null;
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
/*      */   private static int getTextureIndex(int stage, String name) {
/* 1171 */     if (stage == 0) {
/*      */       
/* 1173 */       if (name.equals("texture"))
/* 1174 */         return 0; 
/* 1175 */       if (name.equals("lightmap"))
/* 1176 */         return 1; 
/* 1177 */       if (name.equals("normals"))
/* 1178 */         return 2; 
/* 1179 */       if (name.equals("specular"))
/* 1180 */         return 3; 
/* 1181 */       if (name.equals("shadowtex0") || name.equals("watershadow"))
/* 1182 */         return 4; 
/* 1183 */       if (name.equals("shadow"))
/* 1184 */         return waterShadowEnabled ? 5 : 4; 
/* 1185 */       if (name.equals("shadowtex1"))
/* 1186 */         return 5; 
/* 1187 */       if (name.equals("depthtex0"))
/* 1188 */         return 6; 
/* 1189 */       if (name.equals("gaux1"))
/* 1190 */         return 7; 
/* 1191 */       if (name.equals("gaux2"))
/* 1192 */         return 8; 
/* 1193 */       if (name.equals("gaux3"))
/* 1194 */         return 9; 
/* 1195 */       if (name.equals("gaux4"))
/* 1196 */         return 10; 
/* 1197 */       if (name.equals("depthtex1"))
/* 1198 */         return 12; 
/* 1199 */       if (name.equals("shadowcolor0") || name.equals("shadowcolor"))
/* 1200 */         return 13; 
/* 1201 */       if (name.equals("shadowcolor1"))
/* 1202 */         return 14; 
/* 1203 */       if (name.equals("noisetex")) {
/* 1204 */         return 15;
/*      */       }
/*      */     } 
/* 1207 */     if (stage == 1 || stage == 2) {
/*      */       
/* 1209 */       if (name.equals("colortex0") || name.equals("colortex0"))
/* 1210 */         return 0; 
/* 1211 */       if (name.equals("colortex1") || name.equals("gdepth"))
/* 1212 */         return 1; 
/* 1213 */       if (name.equals("colortex2") || name.equals("gnormal"))
/* 1214 */         return 2; 
/* 1215 */       if (name.equals("colortex3") || name.equals("composite"))
/* 1216 */         return 3; 
/* 1217 */       if (name.equals("shadowtex0") || name.equals("watershadow"))
/* 1218 */         return 4; 
/* 1219 */       if (name.equals("shadow"))
/* 1220 */         return waterShadowEnabled ? 5 : 4; 
/* 1221 */       if (name.equals("shadowtex1"))
/* 1222 */         return 5; 
/* 1223 */       if (name.equals("depthtex0") || name.equals("gdepthtex"))
/* 1224 */         return 6; 
/* 1225 */       if (name.equals("colortex4") || name.equals("gaux1"))
/* 1226 */         return 7; 
/* 1227 */       if (name.equals("colortex5") || name.equals("gaux2"))
/* 1228 */         return 8; 
/* 1229 */       if (name.equals("colortex6") || name.equals("gaux3"))
/* 1230 */         return 9; 
/* 1231 */       if (name.equals("colortex7") || name.equals("gaux4"))
/* 1232 */         return 10; 
/* 1233 */       if (name.equals("depthtex1"))
/* 1234 */         return 11; 
/* 1235 */       if (name.equals("depthtex2"))
/* 1236 */         return 12; 
/* 1237 */       if (name.equals("shadowcolor0") || name.equals("shadowcolor"))
/* 1238 */         return 13; 
/* 1239 */       if (name.equals("shadowcolor1"))
/* 1240 */         return 14; 
/* 1241 */       if (name.equals("noisetex")) {
/* 1242 */         return 15;
/*      */       }
/*      */     } 
/* 1245 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void bindCustomTextures(CustomTexture[] cts) {
/* 1253 */     if (cts == null) {
/*      */       return;
/*      */     }
/* 1256 */     for (int i = 0; i < cts.length; i++) {
/*      */       
/* 1258 */       CustomTexture ct = cts[i];
/*      */       
/* 1260 */       GlStateManager.setActiveTexture(33984 + ct.getTextureUnit());
/*      */       
/* 1262 */       bqh tex = ct.getTexture();
/*      */       
/* 1264 */       GlStateManager.bindTexture(tex.b());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void resetCustomTextures() {
/* 1273 */     deleteCustomTextures(customTexturesGbuffers);
/* 1274 */     deleteCustomTextures(customTexturesComposite);
/* 1275 */     deleteCustomTextures(customTexturesDeferred);
/*      */     
/* 1277 */     customTexturesGbuffers = null;
/* 1278 */     customTexturesComposite = null;
/* 1279 */     customTexturesDeferred = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void deleteCustomTextures(CustomTexture[] cts) {
/* 1287 */     if (cts == null) {
/*      */       return;
/*      */     }
/* 1290 */     for (int i = 0; i < cts.length; i++) {
/*      */       
/* 1292 */       CustomTexture ct = cts[i];
/* 1293 */       ct.deleteTexture();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShaderOption[] getShaderPackOptions(String screenName) {
/* 1301 */     ShaderOption[] ops = (ShaderOption[])shaderPackOptions.clone();
/*      */     
/* 1303 */     if (shaderPackGuiScreens == null) {
/*      */ 
/*      */       
/* 1306 */       if (shaderPackProfiles != null) {
/*      */         
/* 1308 */         ShaderOptionProfile optionProfile = new ShaderOptionProfile(shaderPackProfiles, ops);
/* 1309 */         ops = (ShaderOption[])Config.addObjectToArray((Object[])ops, optionProfile, 0);
/*      */       } 
/*      */       
/* 1312 */       ops = getVisibleOptions(ops);
/*      */       
/* 1314 */       return ops;
/*      */     } 
/*      */     
/* 1317 */     String key = (screenName != null) ? ("screen." + screenName) : "screen";
/* 1318 */     ScreenShaderOptions sso = shaderPackGuiScreens.get(key);
/* 1319 */     if (sso == null) {
/* 1320 */       return new ShaderOption[0];
/*      */     }
/* 1322 */     ShaderOption[] sos = sso.getShaderOptions();
/*      */     
/* 1324 */     List<ShaderOption> list = new ArrayList<ShaderOption>();
/* 1325 */     for (int i = 0; i < sos.length; i++) {
/*      */       
/* 1327 */       ShaderOption so = sos[i];
/*      */       
/* 1329 */       if (so == null) {
/*      */         
/* 1331 */         list.add(null);
/*      */ 
/*      */       
/*      */       }
/* 1335 */       else if (so instanceof ShaderOptionRest) {
/*      */         
/* 1337 */         ShaderOption[] restOps = getShaderOptionsRest(shaderPackGuiScreens, ops);
/* 1338 */         list.addAll(Arrays.asList(restOps));
/*      */       }
/*      */       else {
/*      */         
/* 1342 */         list.add(so);
/*      */       } 
/*      */     } 
/* 1345 */     ShaderOption[] sosExp = list.<ShaderOption>toArray(new ShaderOption[list.size()]);
/*      */     
/* 1347 */     return sosExp;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getShaderPackColumns(String screenName, int def) {
/* 1352 */     String key = (screenName != null) ? ("screen." + screenName) : "screen";
/* 1353 */     if (shaderPackGuiScreens == null)
/* 1354 */       return def; 
/* 1355 */     ScreenShaderOptions sso = shaderPackGuiScreens.get(key);
/* 1356 */     if (sso == null) {
/* 1357 */       return def;
/*      */     }
/* 1359 */     return sso.getColumns();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ShaderOption[] getShaderOptionsRest(Map<String, ScreenShaderOptions> mapScreens, ShaderOption[] ops) {
/* 1367 */     Set<String> setNames = new HashSet<String>();
/* 1368 */     Set<String> keys = mapScreens.keySet();
/* 1369 */     for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
/*      */       
/* 1371 */       String key = it.next();
/* 1372 */       ScreenShaderOptions sso = mapScreens.get(key);
/* 1373 */       ShaderOption[] arrayOfShaderOption = sso.getShaderOptions();
/* 1374 */       for (int v = 0; v < arrayOfShaderOption.length; v++) {
/*      */         
/* 1376 */         ShaderOption so = arrayOfShaderOption[v];
/* 1377 */         if (so != null)
/*      */         {
/*      */           
/* 1380 */           setNames.add(so.getName());
/*      */         }
/*      */       } 
/*      */     } 
/* 1384 */     List<ShaderOption> list = new ArrayList<ShaderOption>();
/* 1385 */     for (int i = 0; i < ops.length; i++) {
/*      */       
/* 1387 */       ShaderOption so = ops[i];
/*      */       
/* 1389 */       if (so.isVisible()) {
/*      */ 
/*      */         
/* 1392 */         String name = so.getName();
/* 1393 */         if (!setNames.contains(name))
/*      */         {
/*      */           
/* 1396 */           list.add(so); } 
/*      */       } 
/*      */     } 
/* 1399 */     ShaderOption[] sos = list.<ShaderOption>toArray(new ShaderOption[list.size()]);
/*      */     
/* 1401 */     return sos;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShaderOption getShaderOption(String name) {
/* 1411 */     return ShaderUtils.getShaderOption(name, shaderPackOptions);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShaderOption[] getShaderPackOptions() {
/* 1418 */     return shaderPackOptions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isShaderPackOptionSlider(String name) {
/* 1426 */     if (shaderPackOptionSliders == null) {
/* 1427 */       return false;
/*      */     }
/* 1429 */     return shaderPackOptionSliders.contains(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ShaderOption[] getVisibleOptions(ShaderOption[] ops) {
/* 1438 */     List<ShaderOption> list = new ArrayList<ShaderOption>();
/* 1439 */     for (int i = 0; i < ops.length; i++) {
/*      */       
/* 1441 */       ShaderOption so = ops[i];
/* 1442 */       if (so.isVisible())
/*      */       {
/*      */         
/* 1445 */         list.add(so);
/*      */       }
/*      */     } 
/* 1448 */     ShaderOption[] sos = list.<ShaderOption>toArray(new ShaderOption[list.size()]);
/*      */     
/* 1450 */     return sos;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveShaderPackOptions() {
/* 1456 */     saveShaderPackOptions(shaderPackOptions, shaderPack);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void saveShaderPackOptions(ShaderOption[] sos, IShaderPack sp) {
/* 1461 */     Properties props = new Properties();
/*      */     
/* 1463 */     if (shaderPackOptions != null)
/*      */     {
/* 1465 */       for (int i = 0; i < sos.length; i++) {
/*      */         
/* 1467 */         ShaderOption so = sos[i];
/*      */         
/* 1469 */         if (so.isChanged())
/*      */         {
/*      */           
/* 1472 */           if (so.isEnabled())
/*      */           {
/*      */             
/* 1475 */             props.setProperty(so.getName(), so.getValue());
/*      */           }
/*      */         }
/*      */       } 
/*      */     }
/*      */     try {
/* 1481 */       saveOptionProperties(sp, props);
/*      */     }
/* 1483 */     catch (IOException e) {
/*      */       
/* 1485 */       Config.warn("[Shaders] Error saving configuration for " + shaderPack.getName());
/* 1486 */       e.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void saveOptionProperties(IShaderPack sp, Properties props) throws IOException {
/* 1496 */     String path = shaderpacksdirname + "/" + sp.getName() + ".txt";
/* 1497 */     File propFile = new File((bao.B()).w, path);
/*      */     
/* 1499 */     if (props.isEmpty()) {
/*      */       
/* 1501 */       propFile.delete();
/*      */       
/*      */       return;
/*      */     } 
/* 1505 */     FileOutputStream fos = new FileOutputStream(propFile);
/* 1506 */     props.store(fos, (String)null);
/* 1507 */     fos.flush();
/* 1508 */     fos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ShaderOption[] loadShaderPackOptions() {
/*      */     try {
/* 1517 */       ShaderOption[] sos = ShaderPackParser.parseShaderPackOptions(shaderPack, programNames, shaderPackDimensions);
/* 1518 */       Properties props = loadOptionProperties(shaderPack);
/* 1519 */       for (int i = 0; i < sos.length; i++) {
/*      */         
/* 1521 */         ShaderOption so = sos[i];
/* 1522 */         String val = props.getProperty(so.getName());
/* 1523 */         if (val != null) {
/*      */ 
/*      */           
/* 1526 */           so.resetValue();
/*      */           
/* 1528 */           if (!so.setValue(val))
/* 1529 */             Config.warn("[Shaders] Invalid value, option: " + so.getName() + ", value: " + val); 
/*      */         } 
/*      */       } 
/* 1532 */       return sos;
/*      */     }
/* 1534 */     catch (IOException e) {
/*      */       
/* 1536 */       Config.warn("[Shaders] Error reading configuration for " + shaderPack.getName());
/* 1537 */       e.printStackTrace();
/* 1538 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Properties loadOptionProperties(IShaderPack sp) throws IOException {
/* 1547 */     Properties props = new Properties();
/*      */     
/* 1549 */     String path = shaderpacksdirname + "/" + sp.getName() + ".txt";
/* 1550 */     File propFile = new File((bao.B()).w, path);
/*      */     
/* 1552 */     if (!propFile.exists() || !propFile.isFile() || !propFile.canRead()) {
/* 1553 */       return props;
/*      */     }
/* 1555 */     FileInputStream fis = new FileInputStream(propFile);
/* 1556 */     props.load(fis);
/* 1557 */     fis.close();
/*      */     
/* 1559 */     return props;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShaderOption[] getChangedOptions(ShaderOption[] ops) {
/* 1567 */     List<ShaderOption> list = new ArrayList<ShaderOption>();
/* 1568 */     for (int i = 0; i < ops.length; i++) {
/*      */       
/* 1570 */       ShaderOption op = ops[i];
/*      */       
/* 1572 */       if (op.isEnabled())
/*      */       {
/*      */         
/* 1575 */         if (op.isChanged())
/*      */         {
/*      */           
/* 1578 */           list.add(op); } 
/*      */       }
/*      */     } 
/* 1581 */     ShaderOption[] cops = list.<ShaderOption>toArray(new ShaderOption[list.size()]);
/*      */     
/* 1583 */     return cops;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String applyOptions(String line, ShaderOption[] ops) {
/* 1591 */     if (ops == null || ops.length <= 0) {
/* 1592 */       return line;
/*      */     }
/* 1594 */     for (int i = 0; i < ops.length; ) {
/*      */       
/* 1596 */       ShaderOption op = ops[i];
/* 1597 */       String opName = op.getName();
/*      */       
/* 1599 */       if (!op.matchesLine(line)) {
/*      */         i++; continue;
/*      */       } 
/* 1602 */       line = op.getSourceLine();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1607 */     return line;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static ArrayList listOfShaders() {
/* 1727 */     ArrayList<String> list = new ArrayList<String>();
/* 1728 */     list.add(packNameNone);
/* 1729 */     list.add(packNameDefault);
/*      */ 
/*      */     
/*      */     try {
/* 1733 */       if (!shaderpacksdir.exists()) {
/* 1734 */         shaderpacksdir.mkdir();
/*      */       }
/* 1736 */       File[] listOfFiles = shaderpacksdir.listFiles();
/* 1737 */       for (int i = 0; i < listOfFiles.length; i++) {
/*      */         
/* 1739 */         File file = listOfFiles[i];
/* 1740 */         String name = file.getName();
/* 1741 */         if (file.isDirectory()) {
/*      */ 
/*      */           
/* 1744 */           if (!name.equals("debug")) {
/*      */ 
/*      */             
/* 1747 */             File subDir = new File(file, "shaders");
/* 1748 */             if (subDir.exists() && subDir.isDirectory())
/* 1749 */               list.add(name); 
/*      */           } 
/* 1751 */         } else if (file.isFile()) {
/*      */           
/* 1753 */           if (name.toLowerCase().endsWith(".zip")) {
/* 1754 */             list.add(name);
/*      */           }
/*      */         } 
/*      */       } 
/* 1758 */     } catch (Exception e) {}
/*      */ 
/*      */     
/* 1761 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   static String versiontostring(int vv) {
/* 1766 */     String vs = Integer.toString(vv);
/* 1767 */     return Integer.toString(Integer.parseInt(vs.substring(1, 3))) + "." + Integer.toString(Integer.parseInt(vs.substring(3, 5))) + "." + Integer.toString(Integer.parseInt(vs.substring(5)));
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
/*      */   static void checkOptifine() {}
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
/*      */   public static int checkFramebufferStatus(String location) {
/* 1799 */     int status = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/* 1800 */     if (status != 36053)
/* 1801 */       System.err.format("FramebufferStatus 0x%04X at %s\n", new Object[] { Integer.valueOf(status), location }); 
/* 1802 */     return status;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int checkGLError(String location) {
/* 1807 */     int errorCode = GL11.glGetError();
/* 1808 */     if (errorCode != 0) {
/*      */       
/* 1810 */       boolean skipPrint = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1818 */       if (!skipPrint)
/*      */       {
/* 1820 */         if (errorCode == 1286) {
/*      */           
/* 1822 */           int status = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/* 1823 */           System.err.format("GL error 0x%04X: %s (Fb status 0x%04X) at %s\n", new Object[] { Integer.valueOf(errorCode), GLU.gluErrorString(errorCode), Integer.valueOf(status), location });
/*      */         }
/*      */         else {
/*      */           
/* 1827 */           System.err.format("GL error 0x%04X: %s at %s\n", new Object[] { Integer.valueOf(errorCode), GLU.gluErrorString(errorCode), location });
/*      */         } 
/*      */       }
/*      */     } 
/* 1831 */     return errorCode;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int checkGLError(String location, String info) {
/* 1836 */     int errorCode = GL11.glGetError();
/* 1837 */     if (errorCode != 0)
/*      */     {
/* 1839 */       System.err.format("GL error 0x%04x: %s at %s %s\n", new Object[] { Integer.valueOf(errorCode), GLU.gluErrorString(errorCode), location, info });
/*      */     }
/* 1841 */     return errorCode;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int checkGLError(String location, String info1, String info2) {
/* 1846 */     int errorCode = GL11.glGetError();
/* 1847 */     if (errorCode != 0)
/*      */     {
/* 1849 */       System.err.format("GL error 0x%04x: %s at %s %s %s\n", new Object[] { Integer.valueOf(errorCode), GLU.gluErrorString(errorCode), location, info1, info2 });
/*      */     }
/* 1851 */     return errorCode;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void printChat(String str) {
/* 1856 */     mc.r.b().a((fj)new fq(str));
/*      */   }
/*      */ 
/*      */   
/*      */   private static void printChatAndLogError(String str) {
/* 1861 */     SMCLog.severe(str);
/* 1862 */     mc.r.b().a((fj)new fq(str));
/*      */   }
/*      */ 
/*      */   
/*      */   public static void printIntBuffer(String title, IntBuffer buf) {
/* 1867 */     StringBuilder sb = new StringBuilder(128);
/* 1868 */     sb.append(title).append(" [pos ").append(buf.position()).append(" lim ").append(buf.limit()).append(" cap ").append(buf.capacity()).append(" :");
/*      */ 
/*      */ 
/*      */     
/* 1872 */     for (int lim = buf.limit(), i = 0; i < lim; i++)
/* 1873 */       sb.append(" ").append(buf.get(i)); 
/* 1874 */     sb.append("]");
/* 1875 */     SMCLog.info(sb.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void startup(bao mc) {
/* 1881 */     checkShadersModInstalled();
/*      */     
/* 1883 */     Shaders.mc = mc;
/* 1884 */     mc = bao.B();
/* 1885 */     capabilities = GLContext.getCapabilities();
/* 1886 */     glVersionString = GL11.glGetString(7938);
/* 1887 */     glVendorString = GL11.glGetString(7936);
/* 1888 */     glRendererString = GL11.glGetString(7937);
/* 1889 */     SMCLog.info("ShadersMod version: 2.4.12");
/* 1890 */     SMCLog.info("OpenGL Version: " + glVersionString);
/* 1891 */     SMCLog.info("Vendor:  " + glVendorString);
/* 1892 */     SMCLog.info("Renderer: " + glRendererString);
/* 1893 */     SMCLog.info("Capabilities: " + (capabilities.OpenGL20 ? " 2.0 " : " - ") + (capabilities.OpenGL21 ? " 2.1 " : " - ") + (capabilities.OpenGL30 ? " 3.0 " : " - ") + (capabilities.OpenGL32 ? " 3.2 " : " - ") + (capabilities.OpenGL40 ? " 4.0 " : " - "));
/*      */ 
/*      */     
/* 1896 */     SMCLog.info("GL_MAX_DRAW_BUFFERS: " + GL11.glGetInteger(34852));
/* 1897 */     SMCLog.info("GL_MAX_COLOR_ATTACHMENTS_EXT: " + GL11.glGetInteger(36063));
/* 1898 */     SMCLog.info("GL_MAX_TEXTURE_IMAGE_UNITS: " + GL11.glGetInteger(34930));
/* 1899 */     hasGlGenMipmap = capabilities.OpenGL30;
/* 1900 */     loadConfig();
/*      */   }
/*      */ 
/*      */   
/*      */   private static String toStringYN(boolean b) {
/* 1905 */     return b ? "Y" : "N";
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateBlockLightLevel() {
/* 1910 */     if (isOldLighting()) {
/*      */       
/* 1912 */       blockLightLevel05 = 0.5F;
/* 1913 */       blockLightLevel06 = 0.6F;
/* 1914 */       blockLightLevel08 = 0.8F;
/*      */     }
/*      */     else {
/*      */       
/* 1918 */       blockLightLevel05 = 1.0F;
/* 1919 */       blockLightLevel06 = 1.0F;
/* 1920 */       blockLightLevel08 = 1.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isOldHandLight() {
/* 1930 */     if (!configOldHandLight.isDefault()) {
/* 1931 */       return configOldHandLight.isTrue();
/*      */     }
/* 1933 */     if (!shaderPackOldHandLight.isDefault()) {
/* 1934 */       return shaderPackOldHandLight.isTrue();
/*      */     }
/* 1936 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDynamicHandLight() {
/* 1945 */     if (!shaderPackDynamicHandLight.isDefault()) {
/* 1946 */       return shaderPackDynamicHandLight.isTrue();
/*      */     }
/* 1948 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isOldLighting() {
/* 1957 */     if (!configOldLighting.isDefault()) {
/* 1958 */       return configOldLighting.isTrue();
/*      */     }
/* 1960 */     if (!shaderPackOldLighting.isDefault()) {
/* 1961 */       return shaderPackOldLighting.isTrue();
/*      */     }
/* 1963 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isRenderShadowTranslucent() {
/* 1972 */     if (shaderPackShadowTranslucent.isFalse()) {
/* 1973 */       return false;
/*      */     }
/* 1975 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isUnderwaterOverlay() {
/* 1984 */     if (shaderPackUnderwaterOverlay.isFalse()) {
/* 1985 */       return false;
/*      */     }
/* 1987 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSun() {
/* 1995 */     if (shaderPackSun.isFalse()) {
/* 1996 */       return false;
/*      */     }
/* 1998 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isMoon() {
/* 2006 */     if (shaderPackMoon.isFalse()) {
/* 2007 */       return false;
/*      */     }
/* 2009 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isVignette() {
/* 2016 */     if (shaderPackVignette.isFalse()) {
/* 2017 */       return false;
/*      */     }
/* 2019 */     return true;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void init() {
/*      */     boolean firstInit;
/* 2048 */     if (!isInitializedOnce) {
/*      */       
/* 2050 */       isInitializedOnce = true;
/* 2051 */       firstInit = true;
/*      */     }
/*      */     else {
/*      */       
/* 2055 */       firstInit = false;
/*      */     } 
/* 2057 */     if (!isShaderPackInitialized) {
/*      */       
/* 2059 */       checkGLError("Shaders.init pre");
/*      */       
/* 2061 */       if (getShaderPackName() != null);
/*      */ 
/*      */ 
/*      */       
/* 2065 */       if (!capabilities.OpenGL20)
/*      */       {
/* 2067 */         printChatAndLogError("No OpenGL 2.0");
/*      */       }
/* 2069 */       if (!capabilities.GL_EXT_framebuffer_object)
/*      */       {
/* 2071 */         printChatAndLogError("No EXT_framebuffer_object");
/*      */       }
/*      */       
/* 2074 */       dfbDrawBuffers.position(0).limit(8);
/* 2075 */       dfbColorTextures.position(0).limit(16);
/*      */       
/* 2077 */       dfbDepthTextures.position(0).limit(3);
/* 2078 */       sfbDrawBuffers.position(0).limit(8);
/* 2079 */       sfbDepthTextures.position(0).limit(2);
/* 2080 */       sfbColorTextures.position(0).limit(8);
/*      */       
/* 2082 */       usedColorBuffers = 4;
/* 2083 */       usedDepthBuffers = 1;
/* 2084 */       usedShadowColorBuffers = 0;
/* 2085 */       usedShadowDepthBuffers = 0;
/* 2086 */       usedColorAttachs = 1;
/* 2087 */       usedDrawBuffers = 1;
/* 2088 */       Arrays.fill(gbuffersFormat, 6408);
/* 2089 */       Arrays.fill(gbuffersClear, true);
/* 2090 */       Arrays.fill(shadowHardwareFilteringEnabled, false);
/* 2091 */       Arrays.fill(shadowMipmapEnabled, false);
/* 2092 */       Arrays.fill(shadowFilterNearest, false);
/* 2093 */       Arrays.fill(shadowColorMipmapEnabled, false);
/* 2094 */       Arrays.fill(shadowColorFilterNearest, false);
/* 2095 */       centerDepthSmoothEnabled = false;
/* 2096 */       noiseTextureEnabled = false;
/* 2097 */       sunPathRotation = 0.0F;
/* 2098 */       shadowIntervalSize = 2.0F;
/* 2099 */       shadowMapWidth = 1024;
/* 2100 */       shadowMapHeight = 1024;
/* 2101 */       spShadowMapWidth = 1024;
/* 2102 */       spShadowMapHeight = 1024;
/* 2103 */       shadowMapFOV = 90.0F;
/* 2104 */       shadowMapHalfPlane = 160.0F;
/* 2105 */       shadowMapIsOrtho = true;
/* 2106 */       shadowDistanceRenderMul = -1.0F;
/* 2107 */       aoLevel = -1.0F;
/* 2108 */       useEntityAttrib = false;
/* 2109 */       useMidTexCoordAttrib = false;
/* 2110 */       useMultiTexCoord3Attrib = false;
/* 2111 */       useTangentAttrib = false;
/* 2112 */       waterShadowEnabled = false;
/* 2113 */       updateChunksErrorRecorded = false;
/* 2114 */       updateBlockLightLevel();
/*      */       
/* 2116 */       Smoother.reset();
/* 2117 */       LegacyUniforms.reset();
/*      */ 
/*      */       
/* 2120 */       ShaderProfile activeProfile = ShaderUtils.detectProfile(shaderPackProfiles, shaderPackOptions, false);
/*      */       
/* 2122 */       String worldPrefix = "";
/* 2123 */       if (currentWorld != null) {
/*      */         
/* 2125 */         int dimId = currentWorld.t.i;
/* 2126 */         if (shaderPackDimensions.contains(Integer.valueOf(dimId))) {
/* 2127 */           worldPrefix = "world" + dimId + "/";
/*      */         }
/*      */       } 
/* 2130 */       if (saveFinalShaders) {
/* 2131 */         clearDirectory(new File(shaderpacksdir, "debug"));
/*      */       }
/* 2133 */       for (int i = 0; i < 44; i++) {
/*      */         
/* 2135 */         String programName = programNames[i];
/*      */         
/* 2137 */         if (programName.equals("")) {
/*      */           
/* 2139 */           programsRef[i] = 0; programsID[i] = 0;
/* 2140 */           programsDrawBufSettings[i] = null;
/* 2141 */           programsColorAtmSettings[i] = null;
/* 2142 */           programsCompositeMipmapSetting[i] = 0;
/*      */         }
/*      */         else {
/*      */           
/* 2146 */           newDrawBufSetting = null;
/* 2147 */           newColorAtmSetting = null;
/* 2148 */           newCompositeMipmapSetting = 0;
/*      */           
/* 2150 */           String programPath = worldPrefix + programName;
/*      */           
/* 2152 */           if (activeProfile != null)
/*      */           {
/* 2154 */             if (activeProfile.isProgramDisabled(programPath)) {
/*      */               
/* 2156 */               SMCLog.info("Program disabled: " + programPath);
/* 2157 */               programName = "<disabled>";
/* 2158 */               programPath = worldPrefix + programName;
/*      */             } 
/*      */           }
/*      */           
/* 2162 */           String programFullPath = "/shaders/" + programPath;
/* 2163 */           int pr = setupProgram(i, programFullPath + ".vsh", programFullPath + ".fsh");
/*      */           
/* 2165 */           if (pr > 0) {
/* 2166 */             SMCLog.info("Program loaded: " + programPath);
/*      */           }
/* 2168 */           programsRef[i] = pr; programsID[i] = pr;
/* 2169 */           programsDrawBufSettings[i] = (pr != 0) ? newDrawBufSetting : null;
/* 2170 */           programsColorAtmSettings[i] = (pr != 0) ? newColorAtmSetting : null;
/* 2171 */           programsCompositeMipmapSetting[i] = (pr != 0) ? newCompositeMipmapSetting : 0;
/*      */         } 
/*      */       } 
/*      */       
/* 2175 */       int maxDrawBuffers = GL11.glGetInteger(34852);
/* 2176 */       Map<String, IntBuffer> drawBuffersMap = new HashMap<String, IntBuffer>();
/* 2177 */       for (int p = 0; p < 44; p++) {
/*      */         
/* 2179 */         Arrays.fill(programsToggleColorTextures[p], false);
/* 2180 */         if (p == 29) {
/*      */           
/* 2182 */           programsDrawBuffers[p] = null;
/*      */         }
/* 2184 */         else if (programsID[p] == 0) {
/*      */           
/* 2186 */           if (p == 30)
/*      */           {
/* 2188 */             programsDrawBuffers[p] = drawBuffersNone;
/*      */           }
/*      */           else
/*      */           {
/* 2192 */             programsDrawBuffers[p] = drawBuffersColorAtt0;
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 2197 */           String str = programsDrawBufSettings[p];
/* 2198 */           if (str != null) {
/*      */             
/* 2200 */             IntBuffer intbuf = drawBuffersBuffer[p];
/* 2201 */             int numDB = str.length();
/* 2202 */             if (numDB > usedDrawBuffers)
/*      */             {
/* 2204 */               usedDrawBuffers = numDB;
/*      */             }
/* 2206 */             if (numDB > maxDrawBuffers)
/*      */             {
/* 2208 */               numDB = maxDrawBuffers;
/*      */             }
/* 2210 */             programsDrawBuffers[p] = intbuf;
/* 2211 */             intbuf.limit(numDB);
/* 2212 */             for (int k = 0; k < numDB; k++)
/*      */             {
/* 2214 */               int drawBuffer = 0;
/* 2215 */               if (str.length() > k) {
/*      */                 
/* 2217 */                 int ca = str.charAt(k) - 48;
/* 2218 */                 if (p != 30) {
/*      */                   
/* 2220 */                   if (ca >= 0 && ca <= 7)
/*      */                   {
/* 2222 */                     programsToggleColorTextures[p][ca] = true;
/* 2223 */                     drawBuffer = ca + 36064;
/* 2224 */                     if (ca > usedColorAttachs)
/*      */                     {
/* 2226 */                       usedColorAttachs = ca;
/*      */                     }
/* 2228 */                     if (ca > usedColorBuffers)
/*      */                     {
/* 2230 */                       usedColorBuffers = ca;
/*      */                     
/*      */                     }
/*      */                   }
/*      */                 
/*      */                 }
/* 2236 */                 else if (ca >= 0 && ca <= 1) {
/*      */                   
/* 2238 */                   drawBuffer = ca + 36064;
/* 2239 */                   if (ca > usedShadowColorBuffers)
/*      */                   {
/* 2241 */                     usedShadowColorBuffers = ca;
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */               
/* 2246 */               intbuf.put(k, drawBuffer);
/*      */             
/*      */             }
/*      */           
/*      */           }
/* 2251 */           else if (p != 30 && p != 31 && p != 32) {
/*      */             
/* 2253 */             programsDrawBuffers[p] = dfbDrawBuffers;
/* 2254 */             usedDrawBuffers = usedColorBuffers;
/* 2255 */             Arrays.fill(programsToggleColorTextures[p], 0, usedColorBuffers, true);
/*      */           }
/*      */           else {
/*      */             
/* 2259 */             programsDrawBuffers[p] = sfbDrawBuffers;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2269 */       hasDeferredPrograms = false;
/* 2270 */       for (int cp = 0; cp < 8; cp++) {
/*      */         
/* 2272 */         if (programsID[33 + cp] != 0) {
/*      */           
/* 2274 */           hasDeferredPrograms = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */       
/* 2280 */       usedColorAttachs = usedColorBuffers;
/*      */       
/* 2282 */       shadowPassInterval = (usedShadowDepthBuffers > 0) ? 1 : 0;
/* 2283 */       shouldSkipDefaultShadow = (usedShadowDepthBuffers > 0);
/*      */ 
/*      */       
/* 2286 */       SMCLog.info("usedColorBuffers: " + usedColorBuffers);
/* 2287 */       SMCLog.info("usedDepthBuffers: " + usedDepthBuffers);
/* 2288 */       SMCLog.info("usedShadowColorBuffers: " + usedShadowColorBuffers);
/* 2289 */       SMCLog.info("usedShadowDepthBuffers: " + usedShadowDepthBuffers);
/* 2290 */       SMCLog.info("usedColorAttachs: " + usedColorAttachs);
/* 2291 */       SMCLog.info("usedDrawBuffers: " + usedDrawBuffers);
/*      */       
/* 2293 */       dfbDrawBuffers.position(0).limit(usedDrawBuffers);
/* 2294 */       dfbColorTextures.position(0).limit(usedColorBuffers * 2);
/*      */       
/*      */       int j;
/*      */       
/* 2298 */       for (j = 0; j < usedDrawBuffers; j++)
/*      */       {
/* 2300 */         dfbDrawBuffers.put(j, 36064 + j);
/*      */       }
/*      */       
/* 2303 */       if (usedDrawBuffers > maxDrawBuffers)
/*      */       {
/* 2305 */         printChatAndLogError("[Shaders] Error: Not enough draw buffers, needed: " + usedDrawBuffers + ", available: " + maxDrawBuffers);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2311 */       sfbDrawBuffers.position(0).limit(usedShadowColorBuffers);
/* 2312 */       for (j = 0; j < usedShadowColorBuffers; j++)
/*      */       {
/* 2314 */         sfbDrawBuffers.put(j, 36064 + j);
/*      */       }
/*      */ 
/*      */       
/* 2318 */       for (j = 0; j < 44; j++) {
/*      */         
/* 2320 */         int n = j;
/* 2321 */         while (programsID[n] == 0 && programBackups[n] != n)
/* 2322 */           n = programBackups[n]; 
/* 2323 */         if (n != j && j != 30) {
/*      */           
/* 2325 */           programsID[j] = programsID[n];
/* 2326 */           programsDrawBufSettings[j] = programsDrawBufSettings[n];
/* 2327 */           programsDrawBuffers[j] = programsDrawBuffers[n];
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2336 */       resize();
/*      */ 
/*      */       
/* 2339 */       resizeShadow();
/*      */       
/* 2341 */       if (noiseTextureEnabled) {
/* 2342 */         setupNoiseTexture();
/*      */       }
/* 2344 */       if (defaultTexture == null) {
/* 2345 */         defaultTexture = ShadersTex.createDefaultTexture();
/*      */       }
/*      */ 
/*      */       
/* 2349 */       GlStateManager.pushMatrix();
/* 2350 */       GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
/* 2351 */       preCelestialRotate();
/* 2352 */       postCelestialRotate();
/* 2353 */       GlStateManager.popMatrix();
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
/* 2364 */       isShaderPackInitialized = true;
/*      */       
/* 2366 */       loadEntityDataMap();
/*      */       
/* 2368 */       resetDisplayList();
/* 2369 */       if (!firstInit);
/*      */ 
/*      */ 
/*      */       
/* 2373 */       checkGLError("Shaders.init");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void resetDisplayList() {
/* 2379 */     numberResetDisplayList++;
/*      */     
/* 2381 */     needResetModels = true;
/* 2382 */     SMCLog.info("Reset world renderers");
/* 2383 */     mc.g.a();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void resetDisplayListModels() {
/* 2389 */     if (needResetModels) {
/*      */       
/* 2391 */       needResetModels = false;
/* 2392 */       SMCLog.info("Reset model renderers");
/* 2393 */       Iterator<bno> it = bnn.a.getEntityRenderMap().values().iterator();
/* 2394 */       while (it.hasNext()) {
/*      */         
/* 2396 */         bno ren = it.next();
/* 2397 */         if (ren instanceof boh) {
/*      */           
/* 2399 */           boh rle = (boh)ren;
/*      */           
/* 2401 */           resetDisplayListModel(rle.getMainModel());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void resetDisplayListModel(bhr model) {
/* 2409 */     if (model != null) {
/*      */       
/* 2411 */       Iterator it = model.r.iterator();
/* 2412 */       while (it.hasNext()) {
/*      */         
/* 2414 */         Object obj = it.next();
/* 2415 */         if (obj instanceof bix)
/*      */         {
/* 2417 */           resetDisplayListModelRenderer((bix)obj);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void resetDisplayListModelRenderer(bix mrr) {
/* 2424 */     mrr.resetDisplayList();
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
/* 2435 */     if (mrr.m != null)
/*      */     {
/* 2437 */       for (int i = 0, n = mrr.m.size(); i < n; i++)
/*      */       {
/* 2439 */         resetDisplayListModelRenderer(mrr.m.get(i));
/*      */       }
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
/*      */   private static int setupProgram(int program, String vShaderPath, String fShaderPath) {
/* 2529 */     checkGLError("pre setupProgram");
/* 2530 */     int programid = ARBShaderObjects.glCreateProgramObjectARB();
/* 2531 */     checkGLError("create");
/* 2532 */     if (programid != 0) {
/*      */       
/* 2534 */       progUseEntityAttrib = false;
/* 2535 */       progUseMidTexCoordAttrib = false;
/* 2536 */       progUseTangentAttrib = false;
/* 2537 */       int vShader = createVertShader(vShaderPath);
/* 2538 */       int fShader = createFragShader(fShaderPath);
/* 2539 */       checkGLError("create");
/* 2540 */       if (vShader != 0 || fShader != 0) {
/*      */         
/* 2542 */         if (vShader != 0) {
/*      */           
/* 2544 */           ARBShaderObjects.glAttachObjectARB(programid, vShader);
/* 2545 */           checkGLError("attach");
/*      */         } 
/* 2547 */         if (fShader != 0) {
/*      */           
/* 2549 */           ARBShaderObjects.glAttachObjectARB(programid, fShader);
/* 2550 */           checkGLError("attach");
/*      */         } 
/* 2552 */         if (progUseEntityAttrib) {
/*      */           
/* 2554 */           ARBVertexShader.glBindAttribLocationARB(programid, entityAttrib, "mc_Entity");
/* 2555 */           checkGLError("mc_Entity");
/*      */         } 
/* 2557 */         if (progUseMidTexCoordAttrib) {
/*      */           
/* 2559 */           ARBVertexShader.glBindAttribLocationARB(programid, midTexCoordAttrib, "mc_midTexCoord");
/* 2560 */           checkGLError("mc_midTexCoord");
/*      */         } 
/* 2562 */         if (progUseTangentAttrib) {
/*      */           
/* 2564 */           ARBVertexShader.glBindAttribLocationARB(programid, tangentAttrib, "at_tangent");
/* 2565 */           checkGLError("at_tangent");
/*      */         } 
/* 2567 */         ARBShaderObjects.glLinkProgramARB(programid);
/*      */         
/* 2569 */         if (GL20.glGetProgrami(programid, 35714) != 1) {
/* 2570 */           SMCLog.severe("Error linking program: " + programid);
/*      */         }
/* 2572 */         printLogInfo(programid, vShaderPath + ", " + fShaderPath);
/*      */         
/* 2574 */         if (vShader != 0) {
/*      */           
/* 2576 */           ARBShaderObjects.glDetachObjectARB(programid, vShader);
/* 2577 */           ARBShaderObjects.glDeleteObjectARB(vShader);
/*      */         } 
/* 2579 */         if (fShader != 0) {
/*      */           
/* 2581 */           ARBShaderObjects.glDetachObjectARB(programid, fShader);
/* 2582 */           ARBShaderObjects.glDeleteObjectARB(fShader);
/*      */         } 
/* 2584 */         programsID[program] = programid;
/* 2585 */         useProgram(program);
/* 2586 */         ARBShaderObjects.glValidateProgramARB(programid);
/* 2587 */         useProgram(0);
/* 2588 */         printLogInfo(programid, vShaderPath + ", " + fShaderPath);
/* 2589 */         int valid = GL20.glGetProgrami(programid, 35715);
/* 2590 */         if (valid != 1)
/*      */         {
/* 2592 */           String Q = "\"";
/* 2593 */           printChatAndLogError("[Shaders] Error: Invalid program " + Q + programNames[program] + Q);
/* 2594 */           ARBShaderObjects.glDeleteObjectARB(programid);
/* 2595 */           programid = 0;
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 2600 */         ARBShaderObjects.glDeleteObjectARB(programid);
/* 2601 */         programid = 0;
/*      */       } 
/*      */     } 
/* 2604 */     return programid;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int createVertShader(String filename) {
/* 2609 */     int vertShader = ARBShaderObjects.glCreateShaderObjectARB(35633);
/* 2610 */     if (vertShader == 0)
/*      */     {
/* 2612 */       return 0;
/*      */     }
/* 2614 */     StringBuilder vertexCode = new StringBuilder(131072);
/*      */     
/* 2616 */     BufferedReader reader = null;
/*      */     
/*      */     try {
/* 2619 */       reader = new BufferedReader(getShaderReader(filename));
/*      */     }
/* 2621 */     catch (Exception e) {
/*      */ 
/*      */       
/* 2624 */       ARBShaderObjects.glDeleteObjectARB(vertShader);
/* 2625 */       return 0;
/*      */     } 
/*      */     
/* 2628 */     ShaderOption[] activeOptions = getChangedOptions(shaderPackOptions);
/*      */     
/* 2630 */     List<String> listFiles = new ArrayList<String>();
/*      */     
/* 2632 */     if (reader != null) {
/*      */       
/*      */       try {
/*      */ 
/*      */         
/* 2637 */         reader = ShaderPackParser.resolveIncludes(reader, filename, shaderPack, 0, listFiles, 0);
/*      */ 
/*      */         
/*      */         while (true) {
/* 2641 */           String line = reader.readLine();
/* 2642 */           if (line == null) {
/*      */             break;
/*      */           }
/* 2645 */           line = applyOptions(line, activeOptions);
/*      */           
/* 2647 */           vertexCode.append(line).append('\n');
/*      */           
/* 2649 */           ShaderLine sl = ShaderParser.parseLine(line);
/*      */           
/* 2651 */           if (sl == null) {
/*      */             continue;
/*      */           }
/* 2654 */           if (sl.isAttribute("mc_Entity")) {
/*      */             
/* 2656 */             useEntityAttrib = true;
/* 2657 */             progUseEntityAttrib = true;
/*      */             continue;
/*      */           } 
/* 2660 */           if (sl.isAttribute("mc_midTexCoord")) {
/*      */             
/* 2662 */             useMidTexCoordAttrib = true;
/* 2663 */             progUseMidTexCoordAttrib = true;
/*      */             continue;
/*      */           } 
/* 2666 */           if (line.contains("gl_MultiTexCoord3")) {
/*      */             
/* 2668 */             useMultiTexCoord3Attrib = true;
/*      */             continue;
/*      */           } 
/* 2671 */           if (sl.isAttribute("at_tangent")) {
/*      */             
/* 2673 */             useTangentAttrib = true;
/* 2674 */             progUseTangentAttrib = true;
/*      */           } 
/*      */         } 
/* 2677 */         reader.close();
/*      */       }
/* 2679 */       catch (Exception e) {
/*      */         
/* 2681 */         SMCLog.severe("Couldn't read " + filename + "!");
/* 2682 */         e.printStackTrace();
/* 2683 */         ARBShaderObjects.glDeleteObjectARB(vertShader);
/* 2684 */         return 0;
/*      */       } 
/*      */     }
/*      */     
/* 2688 */     if (saveFinalShaders) {
/* 2689 */       saveShader(filename, vertexCode.toString());
/*      */     }
/* 2691 */     ARBShaderObjects.glShaderSourceARB(vertShader, vertexCode);
/* 2692 */     ARBShaderObjects.glCompileShaderARB(vertShader);
/*      */     
/* 2694 */     if (GL20.glGetShaderi(vertShader, 35713) != 1) {
/* 2695 */       SMCLog.severe("Error compiling vertex shader: " + filename);
/*      */     }
/*      */     
/* 2698 */     printShaderLogInfo(vertShader, filename, listFiles);
/* 2699 */     return vertShader;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int createFragShader(String filename) {
/* 2704 */     int fragShader = ARBShaderObjects.glCreateShaderObjectARB(35632);
/* 2705 */     if (fragShader == 0)
/*      */     {
/* 2707 */       return 0;
/*      */     }
/* 2709 */     StringBuilder fragCode = new StringBuilder(131072);
/*      */     
/* 2711 */     BufferedReader reader = null;
/*      */     
/*      */     try {
/* 2714 */       reader = new BufferedReader(getShaderReader(filename));
/*      */     }
/* 2716 */     catch (Exception e) {
/*      */ 
/*      */       
/* 2719 */       ARBShaderObjects.glDeleteObjectARB(fragShader);
/* 2720 */       return 0;
/*      */     } 
/*      */     
/* 2723 */     ShaderOption[] activeOptions = getChangedOptions(shaderPackOptions);
/*      */     
/* 2725 */     List<String> listFiles = new ArrayList<String>();
/*      */     
/* 2727 */     if (reader != null) {
/*      */       
/*      */       try {
/*      */ 
/*      */         
/* 2732 */         reader = ShaderPackParser.resolveIncludes(reader, filename, shaderPack, 0, listFiles, 0);
/*      */ 
/*      */         
/*      */         while (true) {
/* 2736 */           String line = reader.readLine();
/* 2737 */           if (line == null) {
/*      */             break;
/*      */           }
/* 2740 */           line = applyOptions(line, activeOptions);
/*      */           
/* 2742 */           fragCode.append(line).append('\n');
/*      */           
/* 2744 */           ShaderLine sl = ShaderParser.parseLine(line);
/*      */           
/* 2746 */           if (sl == null) {
/*      */             continue;
/*      */           }
/* 2749 */           if (sl.isUniform()) {
/*      */             
/* 2751 */             String uniform = sl.getName();
/*      */             
/*      */             int index;
/* 2754 */             if ((index = ShaderParser.getShadowDepthIndex(uniform)) >= 0) {
/* 2755 */               usedShadowDepthBuffers = Math.max(usedShadowDepthBuffers, index + 1); continue;
/*      */             } 
/* 2757 */             if ((index = ShaderParser.getShadowColorIndex(uniform)) >= 0) {
/* 2758 */               usedShadowColorBuffers = Math.max(usedShadowColorBuffers, index + 1); continue;
/*      */             } 
/* 2760 */             if ((index = ShaderParser.getDepthIndex(uniform)) >= 0) {
/* 2761 */               usedDepthBuffers = Math.max(usedDepthBuffers, index + 1); continue;
/*      */             } 
/* 2763 */             if (uniform.equals("gdepth") && gbuffersFormat[1] == 6408) {
/* 2764 */               gbuffersFormat[1] = 34836; continue;
/*      */             } 
/* 2766 */             if ((index = ShaderParser.getColorIndex(uniform)) >= 0) {
/* 2767 */               usedColorBuffers = Math.max(usedColorBuffers, index + 1); continue;
/*      */             } 
/* 2769 */             if (uniform.equals("centerDepthSmooth"))
/* 2770 */               centerDepthSmoothEnabled = true; 
/*      */             continue;
/*      */           } 
/* 2773 */           if (sl.isConstInt("shadowMapResolution") || sl.isProperty("SHADOWRES")) {
/*      */             
/* 2775 */             spShadowMapWidth = spShadowMapHeight = sl.getValueInt();
/* 2776 */             shadowMapWidth = shadowMapHeight = Math.round(spShadowMapWidth * configShadowResMul);
/* 2777 */             SMCLog.info("Shadow map resolution: " + spShadowMapWidth);
/*      */             continue;
/*      */           } 
/* 2780 */           if (sl.isConstFloat("shadowMapFov") || sl.isProperty("SHADOWFOV")) {
/*      */             
/* 2782 */             shadowMapFOV = sl.getValueFloat();
/* 2783 */             shadowMapIsOrtho = false;
/* 2784 */             SMCLog.info("Shadow map field of view: " + shadowMapFOV);
/*      */             continue;
/*      */           } 
/* 2787 */           if (sl.isConstFloat("shadowDistance") || sl.isProperty("SHADOWHPL")) {
/*      */             
/* 2789 */             shadowMapHalfPlane = sl.getValueFloat();
/* 2790 */             shadowMapIsOrtho = true;
/* 2791 */             SMCLog.info("Shadow map distance: " + shadowMapHalfPlane); continue;
/*      */           } 
/* 2793 */           if (sl.isConstFloat("shadowDistanceRenderMul")) {
/*      */             
/* 2795 */             shadowDistanceRenderMul = sl.getValueFloat();
/* 2796 */             SMCLog.info("Shadow distance render mul: " + shadowDistanceRenderMul); continue;
/*      */           } 
/* 2798 */           if (sl.isConstFloat("shadowIntervalSize")) {
/*      */             
/* 2800 */             shadowIntervalSize = sl.getValueFloat();
/* 2801 */             SMCLog.info("Shadow map interval size: " + shadowIntervalSize); continue;
/*      */           } 
/* 2803 */           if (sl.isConstBool("generateShadowMipmap", true)) {
/*      */             
/* 2805 */             Arrays.fill(shadowMipmapEnabled, true);
/* 2806 */             SMCLog.info("Generate shadow mipmap"); continue;
/*      */           } 
/* 2808 */           if (sl.isConstBool("generateShadowColorMipmap", true)) {
/*      */             
/* 2810 */             Arrays.fill(shadowColorMipmapEnabled, true);
/* 2811 */             SMCLog.info("Generate shadow color mipmap");
/*      */             continue;
/*      */           } 
/* 2814 */           if (sl.isConstBool("shadowHardwareFiltering", true)) {
/*      */             
/* 2816 */             Arrays.fill(shadowHardwareFilteringEnabled, true);
/* 2817 */             SMCLog.info("Hardware shadow filtering enabled."); continue;
/*      */           } 
/* 2819 */           if (sl.isConstBool("shadowHardwareFiltering0", true)) {
/*      */             
/* 2821 */             shadowHardwareFilteringEnabled[0] = true;
/* 2822 */             SMCLog.info("shadowHardwareFiltering0"); continue;
/*      */           } 
/* 2824 */           if (sl.isConstBool("shadowHardwareFiltering1", true)) {
/*      */             
/* 2826 */             shadowHardwareFilteringEnabled[1] = true;
/* 2827 */             SMCLog.info("shadowHardwareFiltering1");
/*      */             continue;
/*      */           } 
/* 2830 */           if (sl.isConstBool("shadowtex0Mipmap", "shadowtexMipmap", true)) {
/*      */             
/* 2832 */             shadowMipmapEnabled[0] = true;
/* 2833 */             SMCLog.info("shadowtex0Mipmap"); continue;
/*      */           } 
/* 2835 */           if (sl.isConstBool("shadowtex1Mipmap", true)) {
/*      */             
/* 2837 */             shadowMipmapEnabled[1] = true;
/* 2838 */             SMCLog.info("shadowtex1Mipmap");
/*      */             continue;
/*      */           } 
/* 2841 */           if (sl.isConstBool("shadowcolor0Mipmap", "shadowColor0Mipmap", true)) {
/*      */             
/* 2843 */             shadowColorMipmapEnabled[0] = true;
/* 2844 */             SMCLog.info("shadowcolor0Mipmap"); continue;
/*      */           } 
/* 2846 */           if (sl.isConstBool("shadowcolor1Mipmap", "shadowColor1Mipmap", true)) {
/*      */             
/* 2848 */             shadowColorMipmapEnabled[1] = true;
/* 2849 */             SMCLog.info("shadowcolor1Mipmap"); continue;
/*      */           } 
/* 2851 */           if (sl.isConstBool("shadowtex0Nearest", "shadowtexNearest", "shadow0MinMagNearest", true)) {
/*      */             
/* 2853 */             shadowFilterNearest[0] = true;
/* 2854 */             SMCLog.info("shadowtex0Nearest"); continue;
/*      */           } 
/* 2856 */           if (sl.isConstBool("shadowtex1Nearest", "shadow1MinMagNearest", true)) {
/*      */             
/* 2858 */             shadowFilterNearest[1] = true;
/* 2859 */             SMCLog.info("shadowtex1Nearest"); continue;
/*      */           } 
/* 2861 */           if (sl.isConstBool("shadowcolor0Nearest", "shadowColor0Nearest", "shadowColor0MinMagNearest", true)) {
/*      */             
/* 2863 */             shadowColorFilterNearest[0] = true;
/* 2864 */             SMCLog.info("shadowcolor0Nearest"); continue;
/*      */           } 
/* 2866 */           if (sl.isConstBool("shadowcolor1Nearest", "shadowColor1Nearest", "shadowColor1MinMagNearest", true)) {
/*      */             
/* 2868 */             shadowColorFilterNearest[1] = true;
/* 2869 */             SMCLog.info("shadowcolor1Nearest");
/*      */             continue;
/*      */           } 
/* 2872 */           if (sl.isConstFloat("wetnessHalflife") || sl.isProperty("WETNESSHL")) {
/*      */             
/* 2874 */             wetnessHalfLife = sl.getValueFloat();
/* 2875 */             SMCLog.info("Wetness halflife: " + wetnessHalfLife);
/*      */             continue;
/*      */           } 
/* 2878 */           if (sl.isConstFloat("drynessHalflife") || sl.isProperty("DRYNESSHL")) {
/*      */             
/* 2880 */             drynessHalfLife = sl.getValueFloat();
/* 2881 */             SMCLog.info("Dryness halflife: " + drynessHalfLife);
/*      */             continue;
/*      */           } 
/* 2884 */           if (sl.isConstFloat("eyeBrightnessHalflife")) {
/*      */             
/* 2886 */             eyeBrightnessHalflife = sl.getValueFloat();
/* 2887 */             SMCLog.info("Eye brightness halflife: " + eyeBrightnessHalflife);
/*      */             continue;
/*      */           } 
/* 2890 */           if (sl.isConstFloat("centerDepthHalflife")) {
/*      */             
/* 2892 */             centerDepthSmoothHalflife = sl.getValueFloat();
/* 2893 */             SMCLog.info("Center depth halflife: " + centerDepthSmoothHalflife);
/*      */             continue;
/*      */           } 
/* 2896 */           if (sl.isConstFloat("sunPathRotation")) {
/*      */             
/* 2898 */             sunPathRotation = sl.getValueFloat();
/* 2899 */             SMCLog.info("Sun path rotation: " + sunPathRotation);
/*      */             continue;
/*      */           } 
/* 2902 */           if (sl.isConstFloat("ambientOcclusionLevel")) {
/*      */             
/* 2904 */             aoLevel = Config.limit(sl.getValueFloat(), 0.0F, 1.0F);
/* 2905 */             SMCLog.info("AO Level: " + aoLevel);
/*      */             continue;
/*      */           } 
/* 2908 */           if (sl.isConstInt("superSamplingLevel")) {
/*      */             
/* 2910 */             int ssaa = sl.getValueInt();
/* 2911 */             if (ssaa > 1) {
/*      */               
/* 2913 */               SMCLog.info("Super sampling level: " + ssaa + "x");
/* 2914 */               superSamplingLevel = ssaa;
/*      */               
/*      */               continue;
/*      */             } 
/* 2918 */             superSamplingLevel = 1;
/*      */             
/*      */             continue;
/*      */           } 
/* 2922 */           if (sl.isConstInt("noiseTextureResolution")) {
/*      */             
/* 2924 */             noiseTextureResolution = sl.getValueInt();
/* 2925 */             noiseTextureEnabled = true;
/* 2926 */             SMCLog.info("Noise texture enabled");
/* 2927 */             SMCLog.info("Noise texture resolution: " + noiseTextureResolution);
/*      */             continue;
/*      */           } 
/* 2930 */           if (sl.isConstIntSuffix("Format")) {
/*      */             
/* 2932 */             String name = StrUtils.removeSuffix(sl.getName(), "Format");
/* 2933 */             String value = sl.getValue();
/* 2934 */             int bufferindex = getBufferIndexFromString(name);
/* 2935 */             int format = getTextureFormatFromString(value);
/* 2936 */             if (bufferindex >= 0 && format != 0) {
/*      */               
/* 2938 */               gbuffersFormat[bufferindex] = format;
/* 2939 */               SMCLog.info("%s format: %s", new Object[] { name, value });
/*      */             } 
/*      */             continue;
/*      */           } 
/* 2943 */           if (sl.isConstBoolSuffix("Clear", false)) {
/*      */             
/* 2945 */             if (ShaderParser.isComposite(filename) || ShaderParser.isDeferred(filename)) {
/*      */               
/* 2947 */               String name = StrUtils.removeSuffix(sl.getName(), "Clear");
/* 2948 */               int bufferindex = getBufferIndexFromString(name);
/* 2949 */               if (bufferindex >= 0) {
/*      */                 
/* 2951 */                 gbuffersClear[bufferindex] = false;
/* 2952 */                 SMCLog.info("%s clear disabled", new Object[] { name });
/*      */               } 
/*      */             }  continue;
/*      */           } 
/* 2956 */           if (sl.isProperty("GAUX4FORMAT", "RGBA32F")) {
/*      */             
/* 2958 */             gbuffersFormat[7] = 34836;
/* 2959 */             SMCLog.info("gaux4 format : RGB32AF"); continue;
/*      */           } 
/* 2961 */           if (sl.isProperty("GAUX4FORMAT", "RGB32F")) {
/*      */             
/* 2963 */             gbuffersFormat[7] = 34837;
/* 2964 */             SMCLog.info("gaux4 format : RGB32F"); continue;
/*      */           } 
/* 2966 */           if (sl.isProperty("GAUX4FORMAT", "RGB16")) {
/*      */             
/* 2968 */             gbuffersFormat[7] = 32852;
/* 2969 */             SMCLog.info("gaux4 format : RGB16");
/*      */             continue;
/*      */           } 
/* 2972 */           if (sl.isConstBoolSuffix("MipmapEnabled", true)) {
/*      */             
/* 2974 */             if (ShaderParser.isComposite(filename) || ShaderParser.isDeferred(filename) || ShaderParser.isFinal(filename)) {
/*      */               
/* 2976 */               String name = StrUtils.removeSuffix(sl.getName(), "MipmapEnabled");
/* 2977 */               int bufferindex = getBufferIndexFromString(name);
/* 2978 */               if (bufferindex >= 0) {
/*      */                 
/* 2980 */                 newCompositeMipmapSetting |= 1 << bufferindex;
/* 2981 */                 SMCLog.info("%s mipmap enabled", new Object[] { name });
/*      */               } 
/*      */             }  continue;
/*      */           } 
/* 2985 */           if (sl.isProperty("DRAWBUFFERS")) {
/*      */             
/* 2987 */             String val = sl.getValue();
/* 2988 */             if (ShaderParser.isValidDrawBuffers(val)) {
/* 2989 */               newDrawBufSetting = val; continue;
/*      */             } 
/* 2991 */             SMCLog.warning("Invalid draw buffers: " + val);
/*      */           } 
/*      */         } 
/*      */         
/* 2995 */         reader.close();
/*      */       }
/* 2997 */       catch (Exception e) {
/*      */         
/* 2999 */         SMCLog.severe("Couldn't read " + filename + "!");
/* 3000 */         e.printStackTrace();
/* 3001 */         ARBShaderObjects.glDeleteObjectARB(fragShader);
/* 3002 */         return 0;
/*      */       } 
/*      */     }
/*      */     
/* 3006 */     if (saveFinalShaders) {
/* 3007 */       saveShader(filename, fragCode.toString());
/*      */     }
/* 3009 */     ARBShaderObjects.glShaderSourceARB(fragShader, fragCode);
/* 3010 */     ARBShaderObjects.glCompileShaderARB(fragShader);
/*      */     
/* 3012 */     if (GL20.glGetShaderi(fragShader, 35713) != 1) {
/* 3013 */       SMCLog.severe("Error compiling fragment shader: " + filename);
/*      */     }
/*      */     
/* 3016 */     printShaderLogInfo(fragShader, filename, listFiles);
/* 3017 */     return fragShader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Reader getShaderReader(String filename) {
/* 3027 */     Reader r = ShadersBuiltIn.getShaderReader(filename);
/* 3028 */     if (r != null) {
/* 3029 */       return r;
/*      */     }
/* 3031 */     return new InputStreamReader(shaderPack.getResourceAsStream(filename));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void saveShader(String filename, String code) {
/*      */     try {
/* 3043 */       File file = new File(shaderpacksdir, "debug/" + filename);
/*      */       
/* 3045 */       file.getParentFile().mkdirs();
/*      */       
/* 3047 */       Config.writeFile(file, code);
/*      */     }
/* 3049 */     catch (IOException e) {
/*      */       
/* 3051 */       Config.warn("Error saving: " + filename);
/* 3052 */       e.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void clearDirectory(File dir) {
/* 3059 */     if (!dir.exists()) {
/*      */       return;
/*      */     }
/* 3062 */     if (!dir.isDirectory()) {
/*      */       return;
/*      */     }
/* 3065 */     File[] files = dir.listFiles();
/* 3066 */     if (files == null) {
/*      */       return;
/*      */     }
/* 3069 */     for (int i = 0; i < files.length; i++) {
/*      */       
/* 3071 */       File file = files[i];
/* 3072 */       if (file.isDirectory()) {
/* 3073 */         clearDirectory(file);
/*      */       }
/* 3075 */       file.delete();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean printLogInfo(int obj, String name) {
/* 3081 */     IntBuffer iVal = BufferUtils.createIntBuffer(1);
/* 3082 */     ARBShaderObjects.glGetObjectParameterARB(obj, 35716, iVal);
/*      */     
/* 3084 */     int length = iVal.get();
/* 3085 */     if (length > 1) {
/*      */       
/* 3087 */       ByteBuffer infoLog = BufferUtils.createByteBuffer(length);
/* 3088 */       iVal.flip();
/* 3089 */       ARBShaderObjects.glGetInfoLogARB(obj, iVal, infoLog);
/* 3090 */       byte[] infoBytes = new byte[length];
/* 3091 */       infoLog.get(infoBytes);
/* 3092 */       if (infoBytes[length - 1] == 0)
/* 3093 */         infoBytes[length - 1] = 10; 
/* 3094 */       String out = new String(infoBytes);
/* 3095 */       SMCLog.info("Info log: " + name + "\n" + out);
/* 3096 */       return false;
/*      */     } 
/* 3098 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean printShaderLogInfo(int shader, String name, List<String> listFiles) {
/* 3103 */     IntBuffer iVal = BufferUtils.createIntBuffer(1);
/* 3104 */     int length = GL20.glGetShaderi(shader, 35716);
/* 3105 */     if (length > 1) {
/*      */ 
/*      */       
/* 3108 */       for (int i = 0; i < listFiles.size(); i++) {
/*      */         
/* 3110 */         String path = listFiles.get(i);
/* 3111 */         SMCLog.info("File: " + (i + 1) + " = " + path);
/*      */       } 
/*      */       
/* 3114 */       String log = GL20.glGetShaderInfoLog(shader, length);
/* 3115 */       SMCLog.info("Shader info log: " + name + "\n" + log);
/* 3116 */       return false;
/*      */     } 
/* 3118 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setDrawBuffers(IntBuffer drawBuffers) {
/* 3123 */     if (drawBuffers == null)
/*      */     {
/* 3125 */       drawBuffers = drawBuffersNone;
/*      */     }
/* 3127 */     if (activeDrawBuffers != drawBuffers) {
/*      */ 
/*      */       
/* 3130 */       activeDrawBuffers = drawBuffers;
/* 3131 */       GL20.glDrawBuffers(drawBuffers);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void useProgram(int program) {
/* 3142 */     checkGLError("pre-useProgram");
/* 3143 */     if (isShadowPass) {
/*      */       
/* 3145 */       program = 30;
/* 3146 */       if (programsID[30] == 0) {
/*      */         
/* 3148 */         normalMapEnabled = false;
/*      */         return;
/*      */       } 
/*      */     } 
/* 3152 */     if (activeProgram == program) {
/*      */       return;
/*      */     }
/*      */     
/* 3156 */     activeProgram = program;
/* 3157 */     ARBShaderObjects.glUseProgramObjectARB(programsID[program]);
/* 3158 */     if (programsID[program] == 0) {
/*      */       
/* 3160 */       normalMapEnabled = false;
/*      */       return;
/*      */     } 
/* 3163 */     if (checkGLError("useProgram ", programNames[program]) != 0)
/*      */     {
/* 3165 */       programsID[program] = 0;
/*      */     }
/* 3167 */     IntBuffer drawBuffers = programsDrawBuffers[program];
/* 3168 */     if (isRenderingDfb) {
/*      */       
/* 3170 */       setDrawBuffers(drawBuffers);
/* 3171 */       checkGLError(programNames[program], " draw buffers = ", programsDrawBufSettings[program]);
/*      */     } 
/*      */     
/* 3174 */     activeCompositeMipmapSetting = programsCompositeMipmapSetting[program];
/*      */     
/* 3176 */     uniformEntityColor.setProgram(programsID[activeProgram]);
/* 3177 */     uniformEntityId.setProgram(programsID[activeProgram]);
/* 3178 */     uniformBlockEntityId.setProgram(programsID[activeProgram]);
/*      */     
/* 3180 */     switch (program) {
/*      */       
/*      */       case 1:
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/*      */       case 5:
/*      */       case 6:
/*      */       case 7:
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*      */       case 12:
/*      */       case 13:
/*      */       case 16:
/*      */       case 18:
/*      */       case 19:
/*      */       case 20:
/*      */       case 41:
/* 3200 */         normalMapEnabled = true;
/* 3201 */         setProgramUniform1i("texture", 0);
/* 3202 */         setProgramUniform1i("lightmap", 1);
/* 3203 */         setProgramUniform1i("normals", 2);
/* 3204 */         setProgramUniform1i("specular", 3);
/* 3205 */         setProgramUniform1i("shadow", waterShadowEnabled ? 5 : 4);
/* 3206 */         setProgramUniform1i("watershadow", 4);
/* 3207 */         setProgramUniform1i("shadowtex0", 4);
/* 3208 */         setProgramUniform1i("shadowtex1", 5);
/* 3209 */         setProgramUniform1i("depthtex0", 6);
/*      */         
/* 3211 */         if (customTexturesGbuffers != null || hasDeferredPrograms) {
/*      */           
/* 3213 */           setProgramUniform1i("gaux1", 7);
/* 3214 */           setProgramUniform1i("gaux2", 8);
/* 3215 */           setProgramUniform1i("gaux3", 9);
/* 3216 */           setProgramUniform1i("gaux4", 10);
/*      */         } 
/* 3218 */         setProgramUniform1i("depthtex1", 12);
/* 3219 */         setProgramUniform1i("shadowcolor", 13);
/* 3220 */         setProgramUniform1i("shadowcolor0", 13);
/* 3221 */         setProgramUniform1i("shadowcolor1", 14);
/* 3222 */         setProgramUniform1i("noisetex", 15);
/*      */         break;
/*      */       case 21:
/*      */       case 22:
/*      */       case 23:
/*      */       case 24:
/*      */       case 25:
/*      */       case 26:
/*      */       case 27:
/*      */       case 28:
/*      */       case 29:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*      */       case 37:
/*      */       case 38:
/*      */       case 39:
/*      */       case 40:
/*      */       case 42:
/*      */       case 43:
/* 3243 */         normalMapEnabled = false;
/* 3244 */         setProgramUniform1i("gcolor", 0);
/* 3245 */         setProgramUniform1i("gdepth", 1);
/* 3246 */         setProgramUniform1i("gnormal", 2);
/* 3247 */         setProgramUniform1i("composite", 3);
/* 3248 */         setProgramUniform1i("gaux1", 7);
/* 3249 */         setProgramUniform1i("gaux2", 8);
/* 3250 */         setProgramUniform1i("gaux3", 9);
/* 3251 */         setProgramUniform1i("gaux4", 10);
/* 3252 */         setProgramUniform1i("colortex0", 0);
/* 3253 */         setProgramUniform1i("colortex1", 1);
/* 3254 */         setProgramUniform1i("colortex2", 2);
/* 3255 */         setProgramUniform1i("colortex3", 3);
/* 3256 */         setProgramUniform1i("colortex4", 7);
/* 3257 */         setProgramUniform1i("colortex5", 8);
/* 3258 */         setProgramUniform1i("colortex6", 9);
/* 3259 */         setProgramUniform1i("colortex7", 10);
/* 3260 */         setProgramUniform1i("shadow", waterShadowEnabled ? 5 : 4);
/* 3261 */         setProgramUniform1i("watershadow", 4);
/* 3262 */         setProgramUniform1i("shadowtex0", 4);
/* 3263 */         setProgramUniform1i("shadowtex1", 5);
/* 3264 */         setProgramUniform1i("gdepthtex", 6);
/* 3265 */         setProgramUniform1i("depthtex0", 6);
/* 3266 */         setProgramUniform1i("depthtex1", 11);
/* 3267 */         setProgramUniform1i("depthtex2", 12);
/* 3268 */         setProgramUniform1i("shadowcolor", 13);
/* 3269 */         setProgramUniform1i("shadowcolor0", 13);
/* 3270 */         setProgramUniform1i("shadowcolor1", 14);
/* 3271 */         setProgramUniform1i("noisetex", 15);
/*      */         break;
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/* 3276 */         setProgramUniform1i("tex", 0);
/* 3277 */         setProgramUniform1i("texture", 0);
/* 3278 */         setProgramUniform1i("lightmap", 1);
/* 3279 */         setProgramUniform1i("normals", 2);
/* 3280 */         setProgramUniform1i("specular", 3);
/* 3281 */         setProgramUniform1i("shadow", waterShadowEnabled ? 5 : 4);
/* 3282 */         setProgramUniform1i("watershadow", 4);
/* 3283 */         setProgramUniform1i("shadowtex0", 4);
/* 3284 */         setProgramUniform1i("shadowtex1", 5);
/*      */         
/* 3286 */         if (customTexturesGbuffers != null) {
/*      */           
/* 3288 */           setProgramUniform1i("gaux1", 7);
/* 3289 */           setProgramUniform1i("gaux2", 8);
/* 3290 */           setProgramUniform1i("gaux3", 9);
/* 3291 */           setProgramUniform1i("gaux4", 10);
/*      */         } 
/* 3293 */         setProgramUniform1i("shadowcolor", 13);
/* 3294 */         setProgramUniform1i("shadowcolor0", 13);
/* 3295 */         setProgramUniform1i("shadowcolor1", 14);
/* 3296 */         setProgramUniform1i("noisetex", 15);
/*      */         break;
/*      */       default:
/* 3299 */         normalMapEnabled = false;
/*      */         break;
/*      */     } 
/*      */     
/* 3303 */     add stack = (mc.h != null) ? mc.h.be() : null;
/* 3304 */     adb item = (stack != null) ? stack.b() : null;
/* 3305 */     int itemID = -1;
/* 3306 */     aji block = null;
/* 3307 */     if (item != null) {
/*      */       
/* 3309 */       itemID = adb.e.b(item);
/* 3310 */       block = (aji)aji.c.a(itemID);
/*      */     } 
/* 3312 */     int blockLight = (block != null) ? block.m() : 0;
/*      */     
/* 3314 */     setProgramUniform1i("heldItemId", itemID);
/* 3315 */     setProgramUniform1i("heldBlockLightValue", blockLight);
/* 3316 */     setProgramUniform1i("fogMode", fogEnabled ? fogMode : 0);
/* 3317 */     setProgramUniform3f("fogColor", fogColorR, fogColorG, fogColorB);
/* 3318 */     setProgramUniform3f("skyColor", skyColorR, skyColorG, skyColorB);
/* 3319 */     setProgramUniform1i("worldTime", (int)(worldTime % 24000L));
/* 3320 */     setProgramUniform1i("worldDay", (int)(worldTime / 24000L));
/* 3321 */     setProgramUniform1i("moonPhase", moonPhase);
/* 3322 */     setProgramUniform1i("frameCounter", frameCounter);
/* 3323 */     setProgramUniform1f("frameTime", frameTime);
/* 3324 */     setProgramUniform1f("frameTimeCounter", frameTimeCounter);
/* 3325 */     setProgramUniform1f("sunAngle", sunAngle);
/* 3326 */     setProgramUniform1f("shadowAngle", shadowAngle);
/* 3327 */     setProgramUniform1f("rainStrength", rainStrength);
/* 3328 */     setProgramUniform1f("aspectRatio", renderWidth / renderHeight);
/* 3329 */     setProgramUniform1f("viewWidth", renderWidth);
/* 3330 */     setProgramUniform1f("viewHeight", renderHeight);
/* 3331 */     setProgramUniform1f("near", 0.05F);
/* 3332 */     setProgramUniform1f("far", (mc.u.c * 16));
/* 3333 */     setProgramUniform3f("sunPosition", sunPosition[0], sunPosition[1], sunPosition[2]);
/* 3334 */     setProgramUniform3f("moonPosition", moonPosition[0], moonPosition[1], moonPosition[2]);
/* 3335 */     setProgramUniform3f("shadowLightPosition", shadowLightPosition[0], shadowLightPosition[1], shadowLightPosition[2]);
/* 3336 */     setProgramUniform3f("upPosition", upPosition[0], upPosition[1], upPosition[2]);
/* 3337 */     setProgramUniform3f("previousCameraPosition", (float)previousCameraPositionX, (float)previousCameraPositionY, (float)previousCameraPositionZ);
/* 3338 */     setProgramUniform3f("cameraPosition", (float)cameraPositionX, (float)cameraPositionY, (float)cameraPositionZ);
/* 3339 */     setProgramUniformMatrix4ARB("gbufferModelView", false, modelView);
/* 3340 */     setProgramUniformMatrix4ARB("gbufferModelViewInverse", false, modelViewInverse);
/* 3341 */     setProgramUniformMatrix4ARB("gbufferPreviousProjection", false, previousProjection);
/* 3342 */     setProgramUniformMatrix4ARB("gbufferProjection", false, projection);
/* 3343 */     setProgramUniformMatrix4ARB("gbufferProjectionInverse", false, projectionInverse);
/* 3344 */     setProgramUniformMatrix4ARB("gbufferPreviousModelView", false, previousModelView);
/* 3345 */     if (usedShadowDepthBuffers > 0) {
/*      */       
/* 3347 */       setProgramUniformMatrix4ARB("shadowProjection", false, shadowProjection);
/* 3348 */       setProgramUniformMatrix4ARB("shadowProjectionInverse", false, shadowProjectionInverse);
/* 3349 */       setProgramUniformMatrix4ARB("shadowModelView", false, shadowModelView);
/* 3350 */       setProgramUniformMatrix4ARB("shadowModelViewInverse", false, shadowModelViewInverse);
/*      */     } 
/* 3352 */     setProgramUniform1f("wetness", wetness);
/* 3353 */     setProgramUniform1f("eyeAltitude", eyePosY);
/*      */ 
/*      */     
/* 3356 */     setProgramUniform2i("eyeBrightness", eyeBrightness & 0xFFFF, eyeBrightness >> 16);
/* 3357 */     setProgramUniform2i("eyeBrightnessSmooth", Math.round(eyeBrightnessFadeX), Math.round(eyeBrightnessFadeY));
/* 3358 */     setProgramUniform2i("terrainTextureSize", terrainTextureSize[0], terrainTextureSize[1]);
/* 3359 */     setProgramUniform1i("terrainIconSize", terrainIconSize);
/* 3360 */     setProgramUniform1i("isEyeInWater", isEyeInWater);
/* 3361 */     setProgramUniform1f("nightVision", nightVision);
/* 3362 */     setProgramUniform1f("blindness", blindness);
/* 3363 */     setProgramUniform1f("screenBrightness", mc.u.aG);
/* 3364 */     setProgramUniform1i("hideGUI", mc.u.av ? 1 : 0);
/* 3365 */     setProgramUniform1f("centerDepthSmooth", centerDepthSmooth);
/* 3366 */     setProgramUniform2i("atlasSize", atlasSizeX, atlasSizeY);
/*      */     
/* 3368 */     if (customUniforms != null) {
/*      */       
/* 3370 */       customUniforms.setProgram(programsID[activeProgram]);
/* 3371 */       customUniforms.update();
/*      */     } 
/* 3373 */     checkGLError("useProgram ", programNames[program]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setProgramUniform1i(String name, int x) {
/* 3378 */     int gp = programsID[activeProgram];
/* 3379 */     if (gp != 0) {
/*      */       
/* 3381 */       int uniform = ARBShaderObjects.glGetUniformLocationARB(gp, name);
/* 3382 */       ARBShaderObjects.glUniform1iARB(uniform, x);
/*      */       
/* 3384 */       if (isCustomUniforms()) {
/* 3385 */         LegacyUniforms.setInt(name, x);
/*      */       }
/*      */       
/* 3388 */       checkGLError(programNames[activeProgram], name);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setProgramUniform2i(String name, int x, int y) {
/* 3394 */     int gp = programsID[activeProgram];
/* 3395 */     if (gp != 0) {
/*      */       
/* 3397 */       int uniform = ARBShaderObjects.glGetUniformLocationARB(gp, name);
/* 3398 */       ARBShaderObjects.glUniform2iARB(uniform, x, y);
/*      */       
/* 3400 */       if (isCustomUniforms()) {
/* 3401 */         LegacyUniforms.setIntXy(name, x, y);
/*      */       }
/*      */       
/* 3404 */       checkGLError(programNames[activeProgram], name);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setProgramUniform1f(String name, float x) {
/* 3410 */     int gp = programsID[activeProgram];
/* 3411 */     if (gp != 0) {
/*      */       
/* 3413 */       int uniform = ARBShaderObjects.glGetUniformLocationARB(gp, name);
/* 3414 */       ARBShaderObjects.glUniform1fARB(uniform, x);
/*      */       
/* 3416 */       if (isCustomUniforms()) {
/* 3417 */         LegacyUniforms.setFloat(name, x);
/*      */       }
/*      */       
/* 3420 */       checkGLError(programNames[activeProgram], name);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setProgramUniform3f(String name, float x, float y, float z) {
/* 3426 */     int gp = programsID[activeProgram];
/* 3427 */     if (gp != 0) {
/*      */       
/* 3429 */       int uniform = ARBShaderObjects.glGetUniformLocationARB(gp, name);
/* 3430 */       ARBShaderObjects.glUniform3fARB(uniform, x, y, z);
/*      */       
/* 3432 */       if (isCustomUniforms())
/*      */       {
/* 3434 */         if (name.endsWith("Color")) {
/* 3435 */           LegacyUniforms.setFloatRgb(name, x, y, z);
/*      */         } else {
/* 3437 */           LegacyUniforms.setFloatXyz(name, x, y, z);
/*      */         } 
/*      */       }
/*      */       
/* 3441 */       checkGLError(programNames[activeProgram], name);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setProgramUniformMatrix4ARB(String name, boolean transpose, FloatBuffer matrix) {
/* 3447 */     int gp = programsID[activeProgram];
/* 3448 */     if (gp != 0 && matrix != null) {
/*      */       
/* 3450 */       int uniform = ARBShaderObjects.glGetUniformLocationARB(gp, name);
/* 3451 */       ARBShaderObjects.glUniformMatrix4ARB(uniform, transpose, matrix);
/*      */       
/* 3453 */       checkGLError(programNames[activeProgram], name);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getBufferIndexFromString(String name) {
/* 3459 */     if (name.equals("colortex0") || name.equals("gcolor"))
/* 3460 */       return 0; 
/* 3461 */     if (name.equals("colortex1") || name.equals("gdepth"))
/* 3462 */       return 1; 
/* 3463 */     if (name.equals("colortex2") || name.equals("gnormal"))
/* 3464 */       return 2; 
/* 3465 */     if (name.equals("colortex3") || name.equals("composite"))
/* 3466 */       return 3; 
/* 3467 */     if (name.equals("colortex4") || name.equals("gaux1"))
/* 3468 */       return 4; 
/* 3469 */     if (name.equals("colortex5") || name.equals("gaux2"))
/* 3470 */       return 5; 
/* 3471 */     if (name.equals("colortex6") || name.equals("gaux3"))
/* 3472 */       return 6; 
/* 3473 */     if (name.equals("colortex7") || name.equals("gaux4")) {
/* 3474 */       return 7;
/*      */     }
/* 3476 */     return -1;
/*      */   }
/*      */   
/* 3479 */   private static final String[] formatNames = new String[] { "R8", "RG8", "RGB8", "RGBA8", "R8_SNORM", "RG8_SNORM", "RGB8_SNORM", "RGBA8_SNORM", "R16", "RG16", "RGB16", "RGBA16", "R16_SNORM", "RG16_SNORM", "RGB16_SNORM", "RGBA16_SNORM", "R16F", "RG16F", "RGB16F", "RGBA16F", "R32F", "RG32F", "RGB32F", "RGBA32F", "R32I", "RG32I", "RGB32I", "RGBA32I", "R32UI", "RG32UI", "RGB32UI", "RGBA32UI", "R3_G3_B2", "RGB5_A1", "RGB10_A2", "R11F_G11F_B10F", "RGB9_E5" };
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
/* 3492 */   private static final int[] formatIds = new int[] { 33321, 33323, 32849, 32856, 36756, 36757, 36758, 36759, 33322, 33324, 32852, 32859, 36760, 36761, 36762, 36763, 33325, 33327, 34843, 34842, 33326, 33328, 34837, 34836, 33333, 33339, 36227, 36226, 33334, 33340, 36209, 36208, 10768, 32855, 32857, 35898, 35901 };
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
/*      */   private static int getTextureFormatFromString(String par) {
/* 3509 */     par = par.trim();
/*      */     
/* 3511 */     for (int i = 0; i < formatNames.length; i++) {
/*      */       
/* 3513 */       String name = formatNames[i];
/* 3514 */       if (par.equals(name)) {
/* 3515 */         return formatIds[i];
/*      */       }
/*      */     } 
/* 3518 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setupNoiseTexture() {
/* 3524 */     if (noiseTexture == null && noiseTexturePath != null) {
/* 3525 */       noiseTexture = loadCustomTexture(15, noiseTexturePath);
/*      */     }
/* 3527 */     if (noiseTexture == null)
/* 3528 */       noiseTexture = new HFNoiseTexture(noiseTextureResolution, noiseTextureResolution); 
/*      */   }
/*      */   
/* 3531 */   private static final Pattern patternLoadEntityDataMap = Pattern.compile("\\s*([\\w:]+)\\s*=\\s*([-]?\\d+)\\s*");
/*      */ 
/*      */   
/*      */   private static void loadEntityDataMap() {
/* 3535 */     mapBlockToEntityData = new IdentityHashMap<aji, Integer>(300);
/*      */     
/* 3537 */     if (mapBlockToEntityData.isEmpty()) {
/*      */       
/* 3539 */       Iterator<String> it = aji.c.b().iterator();
/* 3540 */       while (it.hasNext()) {
/*      */         
/* 3542 */         String key = it.next();
/* 3543 */         aji block = (aji)aji.c.a(key);
/* 3544 */         int id = aji.c.b(block);
/*      */         
/* 3546 */         mapBlockToEntityData.put(block, Integer.valueOf(id));
/*      */       } 
/*      */     } 
/*      */     
/* 3550 */     BufferedReader reader = null;
/*      */     
/*      */     try {
/* 3553 */       reader = new BufferedReader(new InputStreamReader(shaderPack.getResourceAsStream("/mc_Entity_x.txt")));
/*      */     }
/* 3555 */     catch (Exception e) {}
/*      */ 
/*      */     
/* 3558 */     if (reader != null)
/*      */       try {
/*      */         String line;
/*      */         
/* 3562 */         while ((line = reader.readLine()) != null)
/*      */         {
/* 3564 */           Matcher m = patternLoadEntityDataMap.matcher(line);
/* 3565 */           if (m.matches()) {
/*      */             
/* 3567 */             String name = m.group(1);
/* 3568 */             String value = m.group(2);
/* 3569 */             int id = Integer.parseInt(value);
/* 3570 */             aji block = aji.b(name);
/* 3571 */             if (block != null) {
/*      */               
/* 3573 */               mapBlockToEntityData.put(block, Integer.valueOf(id));
/*      */               
/*      */               continue;
/*      */             } 
/*      */             
/* 3578 */             SMCLog.warning("Unknown block name %s", new Object[] { name });
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/* 3583 */           SMCLog.warning("unmatched %s\n", new Object[] { line });
/*      */         }
/*      */       
/*      */       }
/* 3587 */       catch (Exception e) {
/*      */         
/* 3589 */         SMCLog.warning("Error parsing mc_Entity_x.txt");
/*      */       }  
/* 3591 */     if (reader != null) {
/*      */       
/*      */       try {
/* 3594 */         reader.close();
/*      */       }
/* 3596 */       catch (Exception e) {}
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static IntBuffer fillIntBufferZero(IntBuffer buf) {
/* 3603 */     int limit = buf.limit();
/* 3604 */     for (int i = buf.position(); i < limit; i++)
/* 3605 */       buf.put(i, 0); 
/* 3606 */     return buf;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void uninit() {
/* 3611 */     if (isShaderPackInitialized) {
/*      */       
/* 3613 */       checkGLError("Shaders.uninit pre");
/* 3614 */       for (int i = 0; i < 44; i++) {
/*      */         
/* 3616 */         if (programsRef[i] != 0) {
/*      */           
/* 3618 */           ARBShaderObjects.glDeleteObjectARB(programsRef[i]);
/* 3619 */           checkGLError("del programRef");
/*      */         } 
/* 3621 */         programsRef[i] = 0;
/* 3622 */         programsID[i] = 0;
/* 3623 */         programsDrawBufSettings[i] = null;
/* 3624 */         programsDrawBuffers[i] = null;
/* 3625 */         programsCompositeMipmapSetting[i] = 0;
/*      */       } 
/* 3627 */       hasDeferredPrograms = false;
/* 3628 */       if (dfb != 0) {
/*      */         
/* 3630 */         EXTFramebufferObject.glDeleteFramebuffersEXT(dfb);
/* 3631 */         dfb = 0;
/* 3632 */         checkGLError("del dfb");
/*      */       } 
/* 3634 */       if (sfb != 0) {
/*      */         
/* 3636 */         EXTFramebufferObject.glDeleteFramebuffersEXT(sfb);
/* 3637 */         sfb = 0;
/* 3638 */         checkGLError("del sfb");
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3645 */       if (dfbDepthTextures != null) {
/*      */         
/* 3647 */         GlStateManager.deleteTextures(dfbDepthTextures);
/* 3648 */         fillIntBufferZero(dfbDepthTextures);
/* 3649 */         checkGLError("del dfbDepthTextures");
/*      */       } 
/* 3651 */       if (dfbColorTextures != null) {
/*      */         
/* 3653 */         GlStateManager.deleteTextures(dfbColorTextures);
/* 3654 */         fillIntBufferZero(dfbColorTextures);
/* 3655 */         checkGLError("del dfbTextures");
/*      */       } 
/* 3657 */       if (sfbDepthTextures != null) {
/*      */         
/* 3659 */         GlStateManager.deleteTextures(sfbDepthTextures);
/* 3660 */         fillIntBufferZero(sfbDepthTextures);
/* 3661 */         checkGLError("del shadow depth");
/*      */       } 
/* 3663 */       if (sfbColorTextures != null) {
/*      */         
/* 3665 */         GlStateManager.deleteTextures(sfbColorTextures);
/* 3666 */         fillIntBufferZero(sfbColorTextures);
/* 3667 */         checkGLError("del shadow color");
/*      */       } 
/* 3669 */       if (dfbDrawBuffers != null)
/*      */       {
/* 3671 */         fillIntBufferZero(dfbDrawBuffers);
/*      */       }
/* 3673 */       if (noiseTexture != null) {
/*      */         
/* 3675 */         noiseTexture.deleteTexture();
/* 3676 */         noiseTexture = null;
/*      */       } 
/*      */       
/* 3679 */       SMCLog.info("Uninit");
/*      */       
/* 3681 */       shadowPassInterval = 0;
/* 3682 */       shouldSkipDefaultShadow = false;
/* 3683 */       isShaderPackInitialized = false;
/* 3684 */       checkGLError("Shaders.uninit");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void scheduleResize() {
/* 3690 */     renderDisplayHeight = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void scheduleResizeShadow() {
/* 3695 */     needResizeShadow = true;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void resize() {
/* 3700 */     renderDisplayWidth = mc.d;
/* 3701 */     renderDisplayHeight = mc.e;
/*      */ 
/*      */     
/* 3704 */     renderWidth = Math.round(renderDisplayWidth * configRenderResMul);
/* 3705 */     renderHeight = Math.round(renderDisplayHeight * configRenderResMul);
/* 3706 */     setupFrameBuffer();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void resizeShadow() {
/* 3711 */     needResizeShadow = false;
/* 3712 */     shadowMapWidth = Math.round(spShadowMapWidth * configShadowResMul);
/* 3713 */     shadowMapHeight = Math.round(spShadowMapHeight * configShadowResMul);
/* 3714 */     setupShadowFrameBuffer();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setupFrameBuffer() {
/* 3719 */     if (dfb != 0) {
/*      */       
/* 3721 */       EXTFramebufferObject.glDeleteFramebuffersEXT(dfb);
/* 3722 */       GlStateManager.deleteTextures(dfbDepthTextures);
/* 3723 */       GlStateManager.deleteTextures(dfbColorTextures);
/*      */     } 
/*      */     
/* 3726 */     dfb = EXTFramebufferObject.glGenFramebuffersEXT();
/* 3727 */     GL11.glGenTextures((IntBuffer)dfbDepthTextures.clear().limit(usedDepthBuffers));
/* 3728 */     GL11.glGenTextures((IntBuffer)dfbColorTextures.clear().limit(16));
/*      */     
/* 3730 */     dfbDepthTextures.position(0);
/* 3731 */     dfbColorTextures.position(0);
/* 3732 */     dfbColorTextures.get(dfbColorTexturesA).position(0);
/*      */     
/* 3734 */     EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
/* 3735 */     GL20.glDrawBuffers(0);
/* 3736 */     GL11.glReadBuffer(0);
/*      */     int i;
/* 3738 */     for (i = 0; i < usedDepthBuffers; i++) {
/*      */       
/* 3740 */       GlStateManager.bindTexture(dfbDepthTextures.get(i));
/* 3741 */       GL11.glTexParameteri(3553, 10242, 10496);
/* 3742 */       GL11.glTexParameteri(3553, 10243, 10496);
/* 3743 */       GL11.glTexParameteri(3553, 10241, 9728);
/* 3744 */       GL11.glTexParameteri(3553, 10240, 9728);
/* 3745 */       GL11.glTexParameteri(3553, 34891, 6409);
/* 3746 */       GL11.glTexImage2D(3553, 0, 6402, renderWidth, renderHeight, 0, 6402, 5126, (FloatBuffer)null);
/*      */     } 
/*      */     
/* 3749 */     EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, dfbDepthTextures.get(0), 0);
/* 3750 */     GL20.glDrawBuffers(dfbDrawBuffers);
/* 3751 */     GL11.glReadBuffer(0);
/* 3752 */     checkGLError("FT d");
/*      */     
/* 3754 */     for (i = 0; i < usedColorBuffers; i++) {
/*      */       
/* 3756 */       GlStateManager.bindTexture(dfbColorTexturesA[i]);
/*      */       
/* 3758 */       GL11.glTexParameteri(3553, 10242, 10496);
/* 3759 */       GL11.glTexParameteri(3553, 10243, 10496);
/* 3760 */       GL11.glTexParameteri(3553, 10241, 9729);
/* 3761 */       GL11.glTexParameteri(3553, 10240, 9729);
/* 3762 */       GL11.glTexImage2D(3553, 0, gbuffersFormat[i], renderWidth, renderHeight, 0, 32993, 33639, (ByteBuffer)null);
/* 3763 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + i, 3553, dfbColorTexturesA[i], 0);
/* 3764 */       checkGLError("FT c");
/*      */     } 
/* 3766 */     for (i = 0; i < usedColorBuffers; i++) {
/*      */       
/* 3768 */       GlStateManager.bindTexture(dfbColorTexturesA[8 + i]);
/*      */       
/* 3770 */       GL11.glTexParameteri(3553, 10242, 10496);
/* 3771 */       GL11.glTexParameteri(3553, 10243, 10496);
/* 3772 */       GL11.glTexParameteri(3553, 10241, 9729);
/* 3773 */       GL11.glTexParameteri(3553, 10240, 9729);
/* 3774 */       GL11.glTexImage2D(3553, 0, gbuffersFormat[i], renderWidth, renderHeight, 0, 32993, 33639, (ByteBuffer)null);
/* 3775 */       checkGLError("FT ca");
/*      */     } 
/*      */     
/* 3778 */     int status = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/*      */     
/* 3780 */     if (status == 36058) {
/*      */       
/* 3782 */       printChatAndLogError("[Shaders] Error: Failed framebuffer incomplete formats");
/* 3783 */       for (int j = 0; j < usedColorBuffers; j++) {
/*      */         
/* 3785 */         GlStateManager.bindTexture(dfbColorTextures.get(j));
/* 3786 */         GL11.glTexImage2D(3553, 0, 6408, renderWidth, renderHeight, 0, 32993, 33639, (ByteBuffer)null);
/* 3787 */         EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + j, 3553, dfbColorTextures.get(j), 0);
/* 3788 */         checkGLError("FT c");
/*      */       } 
/* 3790 */       status = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/* 3791 */       if (status == 36053)
/*      */       {
/* 3793 */         SMCLog.info("complete");
/*      */       }
/*      */     } 
/*      */     
/* 3797 */     GlStateManager.bindTexture(0);
/*      */     
/* 3799 */     if (status != 36053) {
/*      */       
/* 3801 */       printChatAndLogError("[Shaders] Error: Failed creating framebuffer! (Status " + status + ")");
/*      */     }
/*      */     else {
/*      */       
/* 3805 */       SMCLog.info("Framebuffer created.");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setupShadowFrameBuffer() {
/* 3811 */     if (usedShadowDepthBuffers == 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 3816 */     if (sfb != 0) {
/*      */       
/* 3818 */       EXTFramebufferObject.glDeleteFramebuffersEXT(sfb);
/* 3819 */       GlStateManager.deleteTextures(sfbDepthTextures);
/* 3820 */       GlStateManager.deleteTextures(sfbColorTextures);
/*      */     } 
/*      */     
/* 3823 */     sfb = EXTFramebufferObject.glGenFramebuffersEXT();
/* 3824 */     EXTFramebufferObject.glBindFramebufferEXT(36160, sfb);
/* 3825 */     GL11.glDrawBuffer(0);
/* 3826 */     GL11.glReadBuffer(0);
/*      */     
/* 3828 */     GL11.glGenTextures((IntBuffer)sfbDepthTextures.clear().limit(usedShadowDepthBuffers));
/* 3829 */     GL11.glGenTextures((IntBuffer)sfbColorTextures.clear().limit(usedShadowColorBuffers));
/*      */ 
/*      */     
/* 3832 */     sfbDepthTextures.position(0);
/* 3833 */     sfbColorTextures.position(0);
/*      */     
/*      */     int i;
/* 3836 */     for (i = 0; i < usedShadowDepthBuffers; i++) {
/*      */       
/* 3838 */       GlStateManager.bindTexture(sfbDepthTextures.get(i));
/* 3839 */       GL11.glTexParameterf(3553, 10242, 10496.0F);
/* 3840 */       GL11.glTexParameterf(3553, 10243, 10496.0F);
/* 3841 */       int filter = shadowFilterNearest[i] ? 9728 : 9729;
/* 3842 */       GL11.glTexParameteri(3553, 10241, filter);
/* 3843 */       GL11.glTexParameteri(3553, 10240, filter);
/* 3844 */       if (shadowHardwareFilteringEnabled[i])
/* 3845 */         GL11.glTexParameteri(3553, 34892, 34894); 
/* 3846 */       GL11.glTexImage2D(3553, 0, 6402, shadowMapWidth, shadowMapHeight, 0, 6402, 5126, (FloatBuffer)null);
/*      */     } 
/* 3848 */     EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, sfbDepthTextures.get(0), 0);
/* 3849 */     checkGLError("FT sd");
/*      */ 
/*      */     
/* 3852 */     for (i = 0; i < usedShadowColorBuffers; i++) {
/*      */       
/* 3854 */       GlStateManager.bindTexture(sfbColorTextures.get(i));
/* 3855 */       GL11.glTexParameterf(3553, 10242, 10496.0F);
/* 3856 */       GL11.glTexParameterf(3553, 10243, 10496.0F);
/* 3857 */       int filter = shadowColorFilterNearest[i] ? 9728 : 9729;
/* 3858 */       GL11.glTexParameteri(3553, 10241, filter);
/* 3859 */       GL11.glTexParameteri(3553, 10240, filter);
/* 3860 */       GL11.glTexImage2D(3553, 0, 6408, shadowMapWidth, shadowMapHeight, 0, 32993, 33639, (ByteBuffer)null);
/* 3861 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + i, 3553, sfbColorTextures.get(i), 0);
/* 3862 */       checkGLError("FT sc");
/*      */     } 
/*      */     
/* 3865 */     GlStateManager.bindTexture(0);
/*      */     
/* 3867 */     if (usedShadowColorBuffers > 0)
/*      */     {
/*      */       
/* 3870 */       GL20.glDrawBuffers(sfbDrawBuffers);
/*      */     }
/*      */     
/* 3873 */     int status = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/* 3874 */     if (status != 36053) {
/*      */       
/* 3876 */       printChatAndLogError("[Shaders] Error: Failed creating shadow framebuffer! (Status " + status + ")");
/*      */     }
/*      */     else {
/*      */       
/* 3880 */       SMCLog.info("Shadow framebuffer created.");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void beginRender(bao minecraft, float partialTicks, long finishTimeNano) {
/* 3902 */     checkGLError("pre beginRender");
/*      */ 
/*      */     
/* 3905 */     checkWorldChanged((ahb)mc.f);
/*      */     
/* 3907 */     mc = minecraft;
/* 3908 */     mc.z.a("init");
/* 3909 */     entityRenderer = mc.p;
/*      */     
/* 3911 */     if (!isShaderPackInitialized) {
/*      */       
/*      */       try {
/*      */         
/* 3915 */         init();
/*      */       }
/* 3917 */       catch (IllegalStateException e) {
/*      */ 
/*      */         
/* 3920 */         if (Config.normalize(e.getMessage()).equals("Function is not supported")) {
/*      */ 
/*      */           
/* 3923 */           printChatAndLogError("[Shaders] Error: " + e.getMessage());
/*      */           
/* 3925 */           e.printStackTrace();
/*      */           
/* 3927 */           setShaderPack(packNameNone);
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     }
/* 3933 */     if (mc.d != renderDisplayWidth || mc.e != renderDisplayHeight)
/*      */     {
/* 3935 */       resize();
/*      */     }
/* 3937 */     if (needResizeShadow)
/*      */     {
/* 3939 */       resizeShadow();
/*      */     }
/*      */     
/* 3942 */     worldTime = mc.f.J();
/* 3943 */     diffWorldTime = (worldTime - lastWorldTime) % 24000L;
/* 3944 */     if (diffWorldTime < 0L)
/* 3945 */       diffWorldTime += 24000L; 
/* 3946 */     lastWorldTime = worldTime;
/* 3947 */     moonPhase = mc.f.x();
/*      */ 
/*      */     
/* 3950 */     frameCounter++;
/*      */ 
/*      */     
/* 3953 */     if (frameCounter >= 720720) {
/* 3954 */       frameCounter = 0;
/*      */     }
/* 3956 */     systemTime = System.currentTimeMillis();
/*      */     
/* 3958 */     if (lastSystemTime == 0L) {
/* 3959 */       lastSystemTime = systemTime;
/*      */     }
/* 3961 */     diffSystemTime = systemTime - lastSystemTime;
/* 3962 */     lastSystemTime = systemTime;
/*      */     
/* 3964 */     frameTime = (float)diffSystemTime / 1000.0F;
/*      */     
/* 3966 */     frameTimeCounter += frameTime;
/* 3967 */     frameTimeCounter %= 3600.0F;
/*      */     
/* 3969 */     rainStrength = minecraft.f.j(partialTicks);
/*      */     
/* 3971 */     float fadeScalar = (float)diffSystemTime * 0.01F;
/*      */     
/* 3973 */     float temp1 = (float)Math.exp(Math.log(0.5D) * fadeScalar / ((wetness < rainStrength) ? drynessHalfLife : wetnessHalfLife));
/* 3974 */     wetness = wetness * temp1 + rainStrength * (1.0F - temp1);
/*      */ 
/*      */     
/* 3977 */     sv renderViewEntity = mc.i;
/* 3978 */     if (renderViewEntity != null) {
/*      */       
/* 3980 */       isSleeping = renderViewEntity.bm();
/* 3981 */       eyePosY = (float)renderViewEntity.t * partialTicks + (float)renderViewEntity.T * (1.0F - partialTicks);
/* 3982 */       eyeBrightness = renderViewEntity.c(partialTicks);
/*      */ 
/*      */       
/* 3985 */       float f1 = (float)diffSystemTime * 0.01F;
/* 3986 */       float temp2 = (float)Math.exp(Math.log(0.5D) * f1 / eyeBrightnessHalflife);
/* 3987 */       eyeBrightnessFadeX = eyeBrightnessFadeX * temp2 + (eyeBrightness & 0xFFFF) * (1.0F - temp2);
/* 3988 */       eyeBrightnessFadeY = eyeBrightnessFadeY * temp2 + (eyeBrightness >> 16) * (1.0F - temp2);
/*      */ 
/*      */       
/* 3991 */       isEyeInWater = 0;
/* 3992 */       if (mc.u.aw == 0 && !isSleeping)
/*      */       {
/* 3994 */         if (renderViewEntity.a(awt.h)) {
/* 3995 */           isEyeInWater = 1;
/* 3996 */         } else if (renderViewEntity.a(awt.i)) {
/* 3997 */           isEyeInWater = 2;
/*      */         } 
/*      */       }
/* 4000 */       if (mc.h != null) {
/*      */ 
/*      */         
/* 4003 */         nightVision = 0.0F;
/* 4004 */         if (mc.h.a(rv.r)) {
/* 4005 */           nightVision = (Config.getMinecraft()).p.a((yz)mc.h, partialTicks);
/*      */         }
/* 4007 */         blindness = 0.0F;
/* 4008 */         if (mc.h.a(rv.q)) {
/*      */           
/* 4010 */           int blindnessTicks = mc.h.b(rv.q).b();
/* 4011 */           blindness = Config.limit(blindnessTicks / 20.0F, 0.0F, 1.0F);
/*      */         } 
/*      */       } 
/*      */       
/* 4015 */       azw skyColorV = mc.f.a((sa)renderViewEntity, partialTicks);
/*      */       
/* 4017 */       skyColorV = CustomColorizer.getWorldSkyColor(skyColorV, currentWorld, (sa)renderViewEntity, partialTicks);
/*      */       
/* 4019 */       skyColorR = (float)skyColorV.a;
/* 4020 */       skyColorG = (float)skyColorV.b;
/* 4021 */       skyColorB = (float)skyColorV.c;
/*      */     } 
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
/* 4045 */     isRenderingWorld = true;
/* 4046 */     isCompositeRendered = false;
/* 4047 */     isHandRenderedMain = false;
/*      */     
/* 4049 */     if (usedShadowDepthBuffers >= 1) {
/*      */       
/* 4051 */       GlStateManager.setActiveTexture(33988);
/* 4052 */       GlStateManager.bindTexture(sfbDepthTextures.get(0));
/* 4053 */       if (usedShadowDepthBuffers >= 2) {
/*      */         
/* 4055 */         GlStateManager.setActiveTexture(33989);
/* 4056 */         GlStateManager.bindTexture(sfbDepthTextures.get(1));
/*      */       } 
/*      */     } 
/*      */     
/* 4060 */     GlStateManager.setActiveTexture(33984); int i;
/* 4061 */     for (i = 0; i < usedColorBuffers; i++) {
/*      */       
/* 4063 */       GlStateManager.bindTexture(dfbColorTexturesA[i]);
/* 4064 */       GL11.glTexParameteri(3553, 10240, 9729);
/* 4065 */       GL11.glTexParameteri(3553, 10241, 9729);
/* 4066 */       GlStateManager.bindTexture(dfbColorTexturesA[8 + i]);
/* 4067 */       GL11.glTexParameteri(3553, 10240, 9729);
/* 4068 */       GL11.glTexParameteri(3553, 10241, 9729);
/*      */     } 
/* 4070 */     GlStateManager.bindTexture(0);
/* 4071 */     for (i = 0; i < 4 && 4 + i < usedColorBuffers; i++) {
/*      */       
/* 4073 */       GlStateManager.setActiveTexture(33991 + i);
/* 4074 */       GlStateManager.bindTexture(dfbColorTextures.get(4 + i));
/*      */     } 
/*      */     
/* 4077 */     GlStateManager.setActiveTexture(33990);
/* 4078 */     GlStateManager.bindTexture(dfbDepthTextures.get(0));
/*      */     
/* 4080 */     if (usedDepthBuffers >= 2) {
/*      */       
/* 4082 */       GlStateManager.setActiveTexture(33995);
/* 4083 */       GlStateManager.bindTexture(dfbDepthTextures.get(1));
/*      */       
/* 4085 */       if (usedDepthBuffers >= 3) {
/*      */         
/* 4087 */         GlStateManager.setActiveTexture(33996);
/* 4088 */         GlStateManager.bindTexture(dfbDepthTextures.get(2));
/*      */       } 
/*      */     } 
/*      */     
/* 4092 */     for (i = 0; i < usedShadowColorBuffers; i++) {
/*      */       
/* 4094 */       GlStateManager.setActiveTexture(33997 + i);
/* 4095 */       GlStateManager.bindTexture(sfbColorTextures.get(i));
/*      */     } 
/*      */     
/* 4098 */     if (noiseTextureEnabled) {
/*      */       
/* 4100 */       GlStateManager.setActiveTexture(33984 + noiseTexture.getTextureUnit());
/* 4101 */       GlStateManager.bindTexture(noiseTexture.getTextureId());
/*      */     } 
/*      */ 
/*      */     
/* 4105 */     bindCustomTextures(customTexturesGbuffers);
/*      */     
/* 4107 */     GlStateManager.setActiveTexture(33984);
/*      */     
/* 4109 */     previousCameraPositionX = cameraPositionX;
/* 4110 */     previousCameraPositionY = cameraPositionY;
/* 4111 */     previousCameraPositionZ = cameraPositionZ;
/*      */     
/* 4113 */     previousProjection.position(0);
/* 4114 */     projection.position(0);
/* 4115 */     previousProjection.put(projection);
/* 4116 */     previousProjection.position(0);
/* 4117 */     projection.position(0);
/* 4118 */     previousModelView.position(0);
/* 4119 */     modelView.position(0);
/* 4120 */     previousModelView.put(modelView);
/* 4121 */     previousModelView.position(0);
/* 4122 */     modelView.position(0);
/*      */     
/* 4124 */     checkGLError("beginRender");
/*      */ 
/*      */ 
/*      */     
/* 4128 */     ShadersRender.renderShadowMap(entityRenderer, 0, partialTicks, finishTimeNano);
/*      */     
/* 4130 */     mc.z.b();
/*      */     
/* 4132 */     EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4139 */     for (i = 0; i < usedColorBuffers; i++) {
/*      */       
/* 4141 */       colorTexturesToggle[i] = 0;
/* 4142 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + i, 3553, dfbColorTexturesA[i], 0);
/*      */     } 
/*      */     
/* 4145 */     checkGLError("end beginRender");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkWorldChanged(ahb world) {
/* 4154 */     if (currentWorld == world) {
/*      */       return;
/*      */     }
/* 4157 */     ahb oldWorld = currentWorld;
/*      */     
/* 4159 */     currentWorld = world;
/*      */     
/* 4161 */     if (oldWorld != null && world != null) {
/*      */ 
/*      */       
/* 4164 */       int dimIdOld = oldWorld.t.i;
/* 4165 */       int dimIdNew = world.t.i;
/*      */       
/* 4167 */       boolean dimShadersOld = shaderPackDimensions.contains(Integer.valueOf(dimIdOld));
/* 4168 */       boolean dimShadersNew = shaderPackDimensions.contains(Integer.valueOf(dimIdNew));
/*      */       
/* 4170 */       if (dimShadersOld || dimShadersNew) {
/* 4171 */         uninit();
/*      */       }
/*      */     } 
/* 4174 */     Smoother.reset();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginRenderPass(int pass, float partialTicks, long finishTimeNano) {
/* 4179 */     if (isShadowPass) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 4185 */     EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
/* 4186 */     GL11.glViewport(0, 0, renderWidth, renderHeight);
/* 4187 */     activeDrawBuffers = null;
/* 4188 */     ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
/* 4189 */     useProgram(2);
/* 4190 */     checkGLError("end beginRenderPass");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setViewport(int vx, int vy, int vw, int vh) {
/* 4196 */     GlStateManager.colorMask(true, true, true, true);
/* 4197 */     if (isShadowPass) {
/*      */       
/* 4199 */       GL11.glViewport(0, 0, shadowMapWidth, shadowMapHeight);
/*      */     }
/*      */     else {
/*      */       
/* 4203 */       GL11.glViewport(0, 0, renderWidth, renderHeight);
/* 4204 */       EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
/* 4205 */       isRenderingDfb = true;
/* 4206 */       GlStateManager.enableCull();
/* 4207 */       GlStateManager.enableDepth();
/* 4208 */       setDrawBuffers(drawBuffersNone);
/* 4209 */       useProgram(2);
/* 4210 */       checkGLError("beginRenderPass");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static int setFogMode(int val) {
/* 4216 */     return fogMode = val;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFogColor(float r, float g, float b) {
/* 4221 */     fogColorR = r;
/* 4222 */     fogColorG = g;
/* 4223 */     fogColorB = b;
/* 4224 */     setProgramUniform3f("fogColor", fogColorR, fogColorG, fogColorB);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setClearColor(float red, float green, float blue, float alpha) {
/* 4229 */     GlStateManager.clearColor(red, green, blue, alpha);
/* 4230 */     clearColorR = red;
/* 4231 */     clearColorG = green;
/* 4232 */     clearColorB = blue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void clearRenderBuffer() {
/* 4241 */     if (isShadowPass) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4248 */       checkGLError("shadow clear pre");
/* 4249 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, sfbDepthTextures.get(0), 0);
/* 4250 */       GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
/*      */       
/* 4252 */       GL20.glDrawBuffers(programsDrawBuffers[30]);
/* 4253 */       checkFramebufferStatus("shadow clear");
/* 4254 */       GL11.glClear(16640);
/* 4255 */       checkGLError("shadow clear");
/*      */       return;
/*      */     } 
/* 4258 */     checkGLError("clear pre");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4267 */     if (gbuffersClear[0]) {
/*      */       
/* 4269 */       GL20.glDrawBuffers(36064);
/*      */ 
/*      */       
/* 4272 */       GL11.glClear(16384);
/*      */     } 
/*      */ 
/*      */     
/* 4276 */     if (gbuffersClear[1]) {
/*      */       
/* 4278 */       GL20.glDrawBuffers(36065);
/* 4279 */       GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 4280 */       GL11.glClear(16384);
/*      */     } 
/*      */ 
/*      */     
/* 4284 */     for (int i = 2; i < usedColorBuffers; i++) {
/*      */ 
/*      */       
/* 4287 */       if (gbuffersClear[i]) {
/*      */ 
/*      */         
/* 4290 */         GL20.glDrawBuffers(36064 + i);
/* 4291 */         GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 4292 */         GL11.glClear(16384);
/*      */       } 
/*      */     } 
/* 4295 */     setDrawBuffers(dfbDrawBuffers);
/* 4296 */     checkFramebufferStatus("clear");
/* 4297 */     checkGLError("clear");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setCamera(float partialTicks) {
/* 4302 */     sv sv = mc.i;
/*      */     
/* 4304 */     double x = ((sa)sv).S + (((sa)sv).s - ((sa)sv).S) * partialTicks;
/* 4305 */     double y = ((sa)sv).T + (((sa)sv).t - ((sa)sv).T) * partialTicks;
/* 4306 */     double z = ((sa)sv).U + (((sa)sv).u - ((sa)sv).U) * partialTicks;
/*      */     
/* 4308 */     cameraPositionX = x;
/* 4309 */     cameraPositionY = y;
/* 4310 */     cameraPositionZ = z;
/*      */     
/* 4312 */     GL11.glGetFloat(2983, (FloatBuffer)projection.position(0));
/* 4313 */     SMath.invertMat4FBFA((FloatBuffer)projectionInverse.position(0), (FloatBuffer)projection.position(0), faProjectionInverse, faProjection);
/* 4314 */     projection.position(0);
/* 4315 */     projectionInverse.position(0);
/*      */     
/* 4317 */     GL11.glGetFloat(2982, (FloatBuffer)modelView.position(0));
/* 4318 */     SMath.invertMat4FBFA((FloatBuffer)modelViewInverse.position(0), (FloatBuffer)modelView.position(0), faModelViewInverse, faModelView);
/* 4319 */     modelView.position(0);
/* 4320 */     modelViewInverse.position(0);
/*      */     
/* 4322 */     checkGLError("setCamera");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setCameraShadow(float partialTicks) {
/* 4327 */     sv sv = mc.i;
/* 4328 */     double x = ((sa)sv).S + (((sa)sv).s - ((sa)sv).S) * partialTicks;
/* 4329 */     double y = ((sa)sv).T + (((sa)sv).t - ((sa)sv).T) * partialTicks;
/* 4330 */     double z = ((sa)sv).U + (((sa)sv).u - ((sa)sv).U) * partialTicks;
/*      */     
/* 4332 */     cameraPositionX = x;
/* 4333 */     cameraPositionY = y;
/* 4334 */     cameraPositionZ = z;
/*      */     
/* 4336 */     GL11.glGetFloat(2983, (FloatBuffer)projection.position(0));
/* 4337 */     SMath.invertMat4FBFA((FloatBuffer)projectionInverse.position(0), (FloatBuffer)projection.position(0), faProjectionInverse, faProjection);
/* 4338 */     projection.position(0);
/* 4339 */     projectionInverse.position(0);
/*      */     
/* 4341 */     GL11.glGetFloat(2982, (FloatBuffer)modelView.position(0));
/* 4342 */     SMath.invertMat4FBFA((FloatBuffer)modelViewInverse.position(0), (FloatBuffer)modelView.position(0), faModelViewInverse, faModelView);
/* 4343 */     modelView.position(0);
/* 4344 */     modelViewInverse.position(0);
/*      */     
/* 4346 */     GL11.glViewport(0, 0, shadowMapWidth, shadowMapHeight);
/*      */     
/* 4348 */     GL11.glMatrixMode(5889);
/* 4349 */     GL11.glLoadIdentity();
/*      */     
/* 4351 */     if (shadowMapIsOrtho) {
/*      */       
/* 4353 */       GL11.glOrtho(-shadowMapHalfPlane, shadowMapHalfPlane, -shadowMapHalfPlane, shadowMapHalfPlane, 0.05000000074505806D, 256.0D);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 4358 */       GLU.gluPerspective(shadowMapFOV, shadowMapWidth / shadowMapHeight, 0.05F, 256.0F);
/*      */     } 
/*      */     
/* 4361 */     GL11.glMatrixMode(5888);
/* 4362 */     GL11.glLoadIdentity();
/* 4363 */     GL11.glTranslatef(0.0F, 0.0F, -100.0F);
/* 4364 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 4365 */     celestialAngle = mc.f.c(partialTicks);
/* 4366 */     sunAngle = (celestialAngle < 0.75F) ? (celestialAngle + 0.25F) : (celestialAngle - 0.75F);
/* 4367 */     float angle = celestialAngle * -360.0F;
/* 4368 */     float angleInterval = (shadowAngleInterval > 0.0F) ? (angle % shadowAngleInterval - shadowAngleInterval * 0.5F) : 0.0F;
/* 4369 */     if (sunAngle <= 0.5D) {
/*      */ 
/*      */       
/* 4372 */       GL11.glRotatef(angle - angleInterval, 0.0F, 0.0F, 1.0F);
/* 4373 */       GL11.glRotatef(sunPathRotation, 1.0F, 0.0F, 0.0F);
/* 4374 */       shadowAngle = sunAngle;
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 4379 */       GL11.glRotatef(angle + 180.0F - angleInterval, 0.0F, 0.0F, 1.0F);
/* 4380 */       GL11.glRotatef(sunPathRotation, 1.0F, 0.0F, 0.0F);
/* 4381 */       shadowAngle = sunAngle - 0.5F;
/*      */     } 
/* 4383 */     if (shadowMapIsOrtho) {
/*      */ 
/*      */       
/* 4386 */       float trans = shadowIntervalSize;
/* 4387 */       float trans2 = trans / 2.0F;
/* 4388 */       GL11.glTranslatef((float)x % trans - trans2, (float)y % trans - trans2, (float)z % trans - trans2);
/*      */     } 
/*      */ 
/*      */     
/* 4392 */     float raSun = sunAngle * 6.2831855F;
/* 4393 */     float x1 = (float)Math.cos(raSun);
/* 4394 */     float y1 = (float)Math.sin(raSun);
/* 4395 */     float raTilt = sunPathRotation * 6.2831855F;
/* 4396 */     float x2 = x1;
/* 4397 */     float y2 = y1 * (float)Math.cos(raTilt);
/* 4398 */     float z2 = y1 * (float)Math.sin(raTilt);
/* 4399 */     if (sunAngle > 0.5D) {
/*      */       
/* 4401 */       x2 = -x2;
/* 4402 */       y2 = -y2;
/* 4403 */       z2 = -z2;
/*      */     } 
/* 4405 */     shadowLightPositionVector[0] = x2;
/* 4406 */     shadowLightPositionVector[1] = y2;
/* 4407 */     shadowLightPositionVector[2] = z2;
/* 4408 */     shadowLightPositionVector[3] = 0.0F;
/*      */     
/* 4410 */     GL11.glGetFloat(2983, (FloatBuffer)shadowProjection.position(0));
/* 4411 */     SMath.invertMat4FBFA((FloatBuffer)shadowProjectionInverse.position(0), (FloatBuffer)shadowProjection.position(0), faShadowProjectionInverse, faShadowProjection);
/* 4412 */     shadowProjection.position(0);
/* 4413 */     shadowProjectionInverse.position(0);
/*      */     
/* 4415 */     GL11.glGetFloat(2982, (FloatBuffer)shadowModelView.position(0));
/* 4416 */     SMath.invertMat4FBFA((FloatBuffer)shadowModelViewInverse.position(0), (FloatBuffer)shadowModelView.position(0), faShadowModelViewInverse, faShadowModelView);
/* 4417 */     shadowModelView.position(0);
/* 4418 */     shadowModelViewInverse.position(0);
/*      */     
/* 4420 */     setProgramUniformMatrix4ARB("gbufferProjection", false, projection);
/* 4421 */     setProgramUniformMatrix4ARB("gbufferProjectionInverse", false, projectionInverse);
/* 4422 */     setProgramUniformMatrix4ARB("gbufferPreviousProjection", false, previousProjection);
/* 4423 */     setProgramUniformMatrix4ARB("gbufferModelView", false, modelView);
/* 4424 */     setProgramUniformMatrix4ARB("gbufferModelViewInverse", false, modelViewInverse);
/* 4425 */     setProgramUniformMatrix4ARB("gbufferPreviousModelView", false, previousModelView);
/* 4426 */     setProgramUniformMatrix4ARB("shadowProjection", false, shadowProjection);
/* 4427 */     setProgramUniformMatrix4ARB("shadowProjectionInverse", false, shadowProjectionInverse);
/* 4428 */     setProgramUniformMatrix4ARB("shadowModelView", false, shadowModelView);
/* 4429 */     setProgramUniformMatrix4ARB("shadowModelViewInverse", false, shadowModelViewInverse);
/*      */ 
/*      */     
/* 4432 */     mc.u.aw = 1;
/* 4433 */     checkGLError("setCamera");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void preCelestialRotate() {
/* 4439 */     GL11.glRotatef(sunPathRotation * 1.0F, 0.0F, 0.0F, 1.0F);
/* 4440 */     checkGLError("preCelestialRotate");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void postCelestialRotate() {
/* 4447 */     FloatBuffer modelView = tempMatrixDirectBuffer;
/* 4448 */     modelView.clear();
/* 4449 */     GL11.glGetFloat(2982, modelView);
/* 4450 */     modelView.get(tempMat, 0, 16);
/* 4451 */     SMath.multiplyMat4xVec4(sunPosition, tempMat, sunPosModelView);
/* 4452 */     SMath.multiplyMat4xVec4(moonPosition, tempMat, moonPosModelView);
/* 4453 */     System.arraycopy((shadowAngle == sunAngle) ? sunPosition : moonPosition, 0, shadowLightPosition, 0, 3);
/*      */     
/* 4455 */     setProgramUniform3f("sunPosition", sunPosition[0], sunPosition[1], sunPosition[2]);
/* 4456 */     setProgramUniform3f("moonPosition", moonPosition[0], moonPosition[1], moonPosition[2]);
/* 4457 */     setProgramUniform3f("shadowLightPosition", shadowLightPosition[0], shadowLightPosition[1], shadowLightPosition[2]);
/* 4458 */     checkGLError("postCelestialRotate");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setUpPosition() {
/* 4464 */     FloatBuffer modelView = tempMatrixDirectBuffer;
/* 4465 */     modelView.clear();
/* 4466 */     GL11.glGetFloat(2982, modelView);
/* 4467 */     modelView.get(tempMat, 0, 16);
/* 4468 */     SMath.multiplyMat4xVec4(upPosition, tempMat, upPosModelView);
/*      */     
/* 4470 */     setProgramUniform3f("upPosition", upPosition[0], upPosition[1], upPosition[2]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void genCompositeMipmap() {
/* 4475 */     if (hasGlGenMipmap) {
/*      */       
/* 4477 */       for (int i = 0; i < usedColorBuffers; i++) {
/*      */         
/* 4479 */         if ((activeCompositeMipmapSetting & 1 << i) != 0) {
/*      */           
/* 4481 */           GlStateManager.setActiveTexture(33984 + colorTextureTextureImageUnit[i]);
/* 4482 */           GL11.glTexParameteri(3553, 10241, 9987);
/* 4483 */           GL30.glGenerateMipmap(3553);
/*      */         } 
/*      */       } 
/* 4486 */       GlStateManager.setActiveTexture(33984);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawComposite() {
/* 4492 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 4493 */     GL11.glBegin(7);
/* 4494 */     GL11.glTexCoord2f(0.0F, 0.0F);
/* 4495 */     GL11.glVertex3f(0.0F, 0.0F, 0.0F);
/* 4496 */     GL11.glTexCoord2f(1.0F, 0.0F);
/* 4497 */     GL11.glVertex3f(1.0F, 0.0F, 0.0F);
/* 4498 */     GL11.glTexCoord2f(1.0F, 1.0F);
/* 4499 */     GL11.glVertex3f(1.0F, 1.0F, 0.0F);
/* 4500 */     GL11.glTexCoord2f(0.0F, 1.0F);
/* 4501 */     GL11.glVertex3f(0.0F, 1.0F, 0.0F);
/* 4502 */     GL11.glEnd();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderDeferred() {
/* 4507 */     if (!hasDeferredPrograms) {
/*      */       return;
/*      */     }
/* 4510 */     checkGLError("pre-render Deferred");
/* 4511 */     renderComposites(33, 8, false);
/*      */     
/* 4513 */     mc.P().a(bpz.b);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderCompositeFinal() {
/* 4518 */     checkGLError("pre-render CompositeFinal");
/* 4519 */     renderComposites(21, 8, true);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void renderComposites(int programBase, int countPrograms, boolean renderFinal) {
/* 4524 */     if (isShadowPass) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4535 */     GL11.glPushMatrix();
/* 4536 */     GL11.glLoadIdentity();
/* 4537 */     GL11.glMatrixMode(5889);
/* 4538 */     GL11.glPushMatrix();
/* 4539 */     GL11.glLoadIdentity();
/* 4540 */     GL11.glOrtho(0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 1.0D);
/*      */     
/* 4542 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 4543 */     GlStateManager.enableTexture2D();
/* 4544 */     GlStateManager.disableAlpha();
/* 4545 */     GlStateManager.disableBlend();
/* 4546 */     GlStateManager.enableDepth();
/* 4547 */     GlStateManager.depthFunc(519);
/* 4548 */     GlStateManager.depthMask(false);
/* 4549 */     GlStateManager.disableLighting();
/*      */ 
/*      */     
/* 4552 */     if (usedShadowDepthBuffers >= 1) {
/*      */       
/* 4554 */       GlStateManager.setActiveTexture(33988);
/* 4555 */       GlStateManager.bindTexture(sfbDepthTextures.get(0));
/* 4556 */       if (usedShadowDepthBuffers >= 2) {
/*      */         
/* 4558 */         GlStateManager.setActiveTexture(33989);
/* 4559 */         GlStateManager.bindTexture(sfbDepthTextures.get(1));
/*      */       } 
/*      */     } 
/*      */     int i;
/* 4563 */     for (i = 0; i < usedColorBuffers; i++) {
/*      */       
/* 4565 */       GlStateManager.setActiveTexture(33984 + colorTextureTextureImageUnit[i]);
/* 4566 */       GlStateManager.bindTexture(dfbColorTexturesA[i]);
/*      */     } 
/*      */     
/* 4569 */     GlStateManager.setActiveTexture(33990);
/* 4570 */     GlStateManager.bindTexture(dfbDepthTextures.get(0));
/*      */     
/* 4572 */     if (usedDepthBuffers >= 2) {
/*      */       
/* 4574 */       GlStateManager.setActiveTexture(33995);
/* 4575 */       GlStateManager.bindTexture(dfbDepthTextures.get(1));
/*      */       
/* 4577 */       if (usedDepthBuffers >= 3) {
/*      */         
/* 4579 */         GlStateManager.setActiveTexture(33996);
/* 4580 */         GlStateManager.bindTexture(dfbDepthTextures.get(2));
/*      */       } 
/*      */     } 
/*      */     
/* 4584 */     for (i = 0; i < usedShadowColorBuffers; i++) {
/*      */       
/* 4586 */       GlStateManager.setActiveTexture(33997 + i);
/* 4587 */       GlStateManager.bindTexture(sfbColorTextures.get(i));
/*      */     } 
/*      */     
/* 4590 */     if (noiseTextureEnabled) {
/*      */       
/* 4592 */       GlStateManager.setActiveTexture(33984 + noiseTexture.getTextureUnit());
/* 4593 */       GlStateManager.bindTexture(noiseTexture.getTextureId());
/*      */     } 
/*      */ 
/*      */     
/* 4597 */     if (renderFinal) {
/* 4598 */       bindCustomTextures(customTexturesComposite);
/*      */     } else {
/* 4600 */       bindCustomTextures(customTexturesDeferred);
/*      */     } 
/* 4602 */     GlStateManager.setActiveTexture(33984);
/*      */     
/* 4604 */     boolean enableAltBuffers = true;
/*      */ 
/*      */ 
/*      */     
/* 4608 */     for (int j = 0; j < usedColorBuffers; j++)
/*      */     {
/* 4610 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + j, 3553, dfbColorTexturesA[8 + j], 0);
/*      */     }
/*      */ 
/*      */     
/* 4614 */     EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, dfbDepthTextures.get(0), 0);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4619 */     GL20.glDrawBuffers(dfbDrawBuffers);
/* 4620 */     checkGLError("pre-composite");
/*      */     
/* 4622 */     for (int cp = 0; cp < countPrograms; cp++) {
/*      */       
/* 4624 */       if (programsID[programBase + cp] != 0) {
/*      */         
/* 4626 */         useProgram(programBase + cp);
/* 4627 */         checkGLError(programNames[programBase + cp]);
/* 4628 */         if (activeCompositeMipmapSetting != 0) {
/* 4629 */           genCompositeMipmap();
/*      */         }
/* 4631 */         drawComposite();
/*      */ 
/*      */ 
/*      */         
/* 4635 */         for (int k = 0; k < usedColorBuffers; k++) {
/*      */           
/* 4637 */           if (programsToggleColorTextures[programBase + cp][k]) {
/*      */             
/* 4639 */             int t0 = colorTexturesToggle[k];
/* 4640 */             int t1 = colorTexturesToggle[k] = 8 - t0;
/* 4641 */             GlStateManager.setActiveTexture(33984 + colorTextureTextureImageUnit[k]);
/* 4642 */             GlStateManager.bindTexture(dfbColorTexturesA[t1 + k]);
/* 4643 */             EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + k, 3553, dfbColorTexturesA[t0 + k], 0);
/*      */           } 
/*      */         } 
/* 4646 */         GlStateManager.setActiveTexture(33984);
/*      */       } 
/*      */     } 
/*      */     
/* 4650 */     checkGLError("composite");
/*      */ 
/*      */     
/* 4653 */     int programLast = renderFinal ? 43 : 42;
/* 4654 */     if (programsID[programLast] != 0) {
/*      */ 
/*      */       
/* 4657 */       useProgram(programLast);
/* 4658 */       checkGLError(programNames[programLast]);
/*      */       
/* 4660 */       if (activeCompositeMipmapSetting != 0) {
/* 4661 */         genCompositeMipmap();
/*      */       }
/* 4663 */       drawComposite();
/*      */ 
/*      */ 
/*      */       
/* 4667 */       for (int k = 0; k < usedColorBuffers; k++) {
/*      */         
/* 4669 */         if (programsToggleColorTextures[programLast][k]) {
/*      */           
/* 4671 */           int t0 = colorTexturesToggle[k];
/* 4672 */           int t1 = colorTexturesToggle[k] = 8 - t0;
/* 4673 */           GlStateManager.setActiveTexture(33984 + colorTextureTextureImageUnit[k]);
/* 4674 */           GlStateManager.bindTexture(dfbColorTexturesA[t1 + k]);
/* 4675 */           EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + k, 3553, dfbColorTexturesA[t0 + k], 0);
/*      */         } 
/*      */       } 
/* 4678 */       GlStateManager.setActiveTexture(33984);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4686 */     if (renderFinal) {
/* 4687 */       renderFinal();
/*      */     }
/*      */     
/* 4690 */     if (renderFinal) {
/* 4691 */       isCompositeRendered = true;
/*      */     }
/*      */     
/* 4694 */     if (!renderFinal) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4699 */       for (int k = 0; k < usedColorBuffers; k++)
/*      */       {
/* 4701 */         EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + k, 3553, dfbColorTexturesA[k], 0);
/*      */       }
/*      */ 
/*      */       
/* 4705 */       setDrawBuffers(programsDrawBuffers[12]);
/*      */     } 
/*      */ 
/*      */     
/* 4709 */     GlStateManager.enableLighting();
/* 4710 */     GlStateManager.enableTexture2D();
/* 4711 */     GlStateManager.enableAlpha();
/* 4712 */     GlStateManager.enableBlend();
/* 4713 */     GlStateManager.depthFunc(515);
/* 4714 */     GlStateManager.depthMask(true);
/*      */ 
/*      */     
/* 4717 */     GL11.glPopMatrix();
/* 4718 */     GL11.glMatrixMode(5888);
/* 4719 */     GL11.glPopMatrix();
/*      */     
/* 4721 */     useProgram(0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void renderFinal() {
/* 4727 */     isRenderingDfb = false;
/* 4728 */     mc.a().a(true);
/* 4729 */     buu.a(GlStateManager.GL_FRAMEBUFFER, GlStateManager.GL_COLOR_ATTACHMENT0, 3553, (mc.a()).g, 0);
/* 4730 */     GL11.glViewport(0, 0, mc.d, mc.e);
/* 4731 */     if (blt.a) {
/*      */       
/* 4733 */       boolean maskR = (blt.b != 0);
/* 4734 */       GlStateManager.colorMask(maskR, !maskR, !maskR, true);
/*      */     } 
/* 4736 */     GlStateManager.depthMask(true);
/* 4737 */     GL11.glClearColor(clearColorR, clearColorG, clearColorB, 1.0F);
/* 4738 */     GL11.glClear(16640);
/* 4739 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 4740 */     GlStateManager.enableTexture2D();
/* 4741 */     GlStateManager.disableAlpha();
/* 4742 */     GlStateManager.disableBlend();
/* 4743 */     GlStateManager.enableDepth();
/* 4744 */     GlStateManager.depthFunc(519);
/* 4745 */     GlStateManager.depthMask(false);
/*      */ 
/*      */     
/* 4748 */     checkGLError("pre-final");
/* 4749 */     useProgram(29);
/* 4750 */     checkGLError("final");
/* 4751 */     if (activeCompositeMipmapSetting != 0)
/* 4752 */       genCompositeMipmap(); 
/* 4753 */     drawComposite();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4761 */     checkGLError("renderCompositeFinal");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endRender() {
/* 4766 */     if (isShadowPass) {
/*      */ 
/*      */       
/* 4769 */       checkGLError("shadow endRender");
/*      */       
/*      */       return;
/*      */     } 
/* 4773 */     if (!isCompositeRendered)
/* 4774 */       renderCompositeFinal(); 
/* 4775 */     isRenderingWorld = false;
/*      */     
/* 4777 */     GlStateManager.colorMask(true, true, true, true);
/*      */     
/* 4779 */     useProgram(0);
/* 4780 */     bam.a();
/* 4781 */     checkGLError("endRender end");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void beginSky() {
/* 4788 */     isRenderingSky = true;
/* 4789 */     fogEnabled = true;
/* 4790 */     setDrawBuffers(dfbDrawBuffers);
/* 4791 */     useProgram(5);
/* 4792 */     pushEntity(-2, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setSkyColor(azw v3color) {
/* 4797 */     skyColorR = (float)v3color.a;
/* 4798 */     skyColorG = (float)v3color.b;
/* 4799 */     skyColorB = (float)v3color.c;
/* 4800 */     setProgramUniform3f("skyColor", skyColorR, skyColorG, skyColorB);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawHorizon() {
/* 4806 */     bmh tess = bmh.a;
/* 4807 */     float farDistance = (mc.u.c * 16);
/* 4808 */     double xzq = farDistance * 0.9238D;
/* 4809 */     double xzp = farDistance * 0.3826D;
/* 4810 */     double xzn = -xzp;
/* 4811 */     double xzm = -xzq;
/* 4812 */     double top = 16.0D;
/* 4813 */     double bot = -cameraPositionY;
/*      */     
/* 4815 */     tess.b();
/*      */     
/* 4817 */     tess.a(xzn, bot, xzm);
/* 4818 */     tess.a(xzn, top, xzm);
/* 4819 */     tess.a(xzm, top, xzn);
/* 4820 */     tess.a(xzm, bot, xzn);
/*      */     
/* 4822 */     tess.a(xzm, bot, xzn);
/* 4823 */     tess.a(xzm, top, xzn);
/* 4824 */     tess.a(xzm, top, xzp);
/* 4825 */     tess.a(xzm, bot, xzp);
/*      */     
/* 4827 */     tess.a(xzm, bot, xzp);
/* 4828 */     tess.a(xzm, top, xzp);
/* 4829 */     tess.a(xzn, top, xzp);
/* 4830 */     tess.a(xzn, bot, xzp);
/*      */     
/* 4832 */     tess.a(xzn, bot, xzp);
/* 4833 */     tess.a(xzn, top, xzp);
/* 4834 */     tess.a(xzp, top, xzq);
/* 4835 */     tess.a(xzp, bot, xzq);
/*      */     
/* 4837 */     tess.a(xzp, bot, xzq);
/* 4838 */     tess.a(xzp, top, xzq);
/* 4839 */     tess.a(xzq, top, xzp);
/* 4840 */     tess.a(xzq, bot, xzp);
/*      */     
/* 4842 */     tess.a(xzq, bot, xzp);
/* 4843 */     tess.a(xzq, top, xzp);
/* 4844 */     tess.a(xzq, top, xzn);
/* 4845 */     tess.a(xzq, bot, xzn);
/*      */     
/* 4847 */     tess.a(xzq, bot, xzn);
/* 4848 */     tess.a(xzq, top, xzn);
/* 4849 */     tess.a(xzp, top, xzm);
/* 4850 */     tess.a(xzp, bot, xzm);
/*      */     
/* 4852 */     tess.a(xzp, bot, xzm);
/* 4853 */     tess.a(xzp, top, xzm);
/* 4854 */     tess.a(xzn, top, xzm);
/* 4855 */     tess.a(xzn, bot, xzm);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4862 */     tess.a();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void preSkyList() {
/* 4868 */     setUpPosition();
/*      */     
/* 4870 */     GL11.glColor3f(fogColorR, fogColorG, fogColorB);
/*      */ 
/*      */ 
/*      */     
/* 4874 */     drawHorizon();
/*      */ 
/*      */     
/* 4877 */     GL11.glColor3f(skyColorR, skyColorG, skyColorB);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endSky() {
/* 4882 */     isRenderingSky = false;
/* 4883 */     setDrawBuffers(dfbDrawBuffers);
/* 4884 */     useProgram(lightmapEnabled ? 3 : 2);
/* 4885 */     popEntity();
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
/*      */   public static void beginUpdateChunks() {
/* 4898 */     checkGLError("beginUpdateChunks1");
/* 4899 */     checkFramebufferStatus("beginUpdateChunks1");
/* 4900 */     if (!isShadowPass)
/*      */     {
/* 4902 */       useProgram(7);
/*      */     }
/* 4904 */     checkGLError("beginUpdateChunks2");
/* 4905 */     checkFramebufferStatus("beginUpdateChunks2");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void endUpdateChunks() {
/* 4911 */     checkGLError("endUpdateChunks1");
/* 4912 */     checkFramebufferStatus("endUpdateChunks1");
/* 4913 */     if (!isShadowPass)
/*      */     {
/* 4915 */       useProgram(7);
/*      */     }
/* 4917 */     checkGLError("endUpdateChunks2");
/* 4918 */     checkFramebufferStatus("endUpdateChunks2");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean shouldRenderClouds(bbj gs) {
/* 4924 */     if (!shaderPackLoaded) {
/* 4925 */       return true;
/*      */     }
/* 4927 */     checkGLError("shouldRenderClouds");
/* 4928 */     return isShadowPass ? configCloudShadow : gs.k;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginClouds() {
/* 4933 */     fogEnabled = true;
/* 4934 */     pushEntity(-3, 0);
/* 4935 */     useProgram(6);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endClouds() {
/* 4940 */     disableFog();
/* 4941 */     popEntity();
/* 4942 */     useProgram(lightmapEnabled ? 3 : 2);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginTerrain() {
/* 4947 */     if (isRenderingWorld) {
/*      */       
/* 4949 */       if (isShadowPass)
/*      */       {
/* 4951 */         GL11.glDisable(2884);
/*      */       }
/* 4953 */       fogEnabled = true;
/* 4954 */       useProgram(7);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endTerrain() {
/* 4960 */     if (isRenderingWorld) {
/*      */       
/* 4962 */       if (isShadowPass)
/*      */       {
/* 4964 */         GL11.glEnable(2884);
/*      */       }
/* 4966 */       useProgram(lightmapEnabled ? 3 : 2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginEntities() {
/* 4972 */     if (isRenderingWorld) {
/*      */       
/* 4974 */       useProgram(16);
/*      */       
/* 4976 */       resetDisplayListModels();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void nextEntity(sa entity) {
/* 4982 */     if (isRenderingWorld) {
/*      */       
/* 4984 */       useProgram(16);
/* 4985 */       setEntityId(entity);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setEntityId(sa entity) {
/* 4991 */     if (isRenderingWorld && !isShadowPass)
/*      */     {
/* 4993 */       if (uniformEntityId.isDefined()) {
/*      */         
/* 4995 */         int id = sg.a(entity);
/* 4996 */         uniformEntityId.setValue(id);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginSpiderEyes() {
/* 5003 */     if (isRenderingWorld)
/*      */     {
/* 5005 */       if (programsID[18] != programsID[0]) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5011 */         useProgram(18);
/* 5012 */         GlStateManager.enableAlpha();
/* 5013 */         GlStateManager.alphaFunc(516, 0.0F);
/* 5014 */         GlStateManager.blendFunc(770, 771);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endSpiderEyes() {
/* 5021 */     if (isRenderingWorld)
/*      */     {
/* 5023 */       if (programsID[18] != programsID[0]) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5029 */         useProgram(16);
/* 5030 */         GlStateManager.disableAlpha();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endEntities() {
/* 5037 */     if (isRenderingWorld)
/*      */     {
/* 5039 */       useProgram(lightmapEnabled ? 3 : 2);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setEntityColor(float r, float g, float b, float a) {
/* 5045 */     if (isRenderingWorld && !isShadowPass)
/*      */     {
/* 5047 */       uniformEntityColor.setValue(r, g, b, a);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginLivingDamage() {
/* 5053 */     if (isRenderingWorld) {
/*      */       
/* 5055 */       ShadersTex.bindTexture(defaultTexture);
/* 5056 */       if (!isShadowPass)
/*      */       {
/*      */         
/* 5059 */         setDrawBuffers(drawBuffersColorAtt0);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endLivingDamage() {
/* 5066 */     if (isRenderingWorld)
/*      */     {
/* 5068 */       if (!isShadowPass)
/*      */       {
/*      */         
/* 5071 */         setDrawBuffers(programsDrawBuffers[16]);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginBlockEntities() {
/* 5078 */     if (isRenderingWorld) {
/*      */       
/* 5080 */       checkGLError("beginBlockEntities");
/* 5081 */       useProgram(13);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void nextBlockEntity(aor tileEntity) {
/* 5086 */     if (isRenderingWorld) {
/*      */       
/* 5088 */       checkGLError("nextBlockEntity");
/* 5089 */       useProgram(13);
/* 5090 */       setBlockEntityId(tileEntity);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setBlockEntityId(aor tileEntity) {
/* 5096 */     if (isRenderingWorld && !isShadowPass)
/*      */     {
/* 5098 */       if (uniformBlockEntityId.isDefined()) {
/*      */         
/* 5100 */         aji block = tileEntity.q();
/* 5101 */         int blockId = aji.b(block);
/* 5102 */         uniformBlockEntityId.setValue(blockId);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endBlockEntities() {
/* 5109 */     if (isRenderingWorld) {
/*      */       
/* 5111 */       checkGLError("endBlockEntities");
/* 5112 */       useProgram(lightmapEnabled ? 3 : 2);
/* 5113 */       ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void beginBlockDestroyProgress() {
/* 5121 */     if (isRenderingWorld) {
/*      */       
/* 5123 */       useProgram(7);
/* 5124 */       if (configTweakBlockDamage) {
/*      */         
/* 5126 */         setDrawBuffers(drawBuffersColorAtt0);
/* 5127 */         GL11.glDepthMask(false);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void endBlockDestroyProgress() {
/* 5136 */     if (isRenderingWorld) {
/*      */       
/* 5138 */       GL11.glDepthMask(true);
/* 5139 */       useProgram(3);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void beginLitParticles() {
/* 5147 */     useProgram(3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void beginParticles() {
/* 5155 */     useProgram(2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void endParticles() {
/* 5162 */     useProgram(3);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void readCenterDepth() {
/* 5167 */     if (!isShadowPass)
/*      */     {
/*      */       
/* 5170 */       if (centerDepthSmoothEnabled) {
/*      */         
/* 5172 */         tempDirectFloatBuffer.clear();
/* 5173 */         GL11.glReadPixels(renderWidth / 2, renderHeight / 2, 1, 1, 6402, 5126, tempDirectFloatBuffer);
/* 5174 */         centerDepth = tempDirectFloatBuffer.get(0);
/*      */ 
/*      */         
/* 5177 */         float fadeScalar = (float)diffSystemTime * 0.01F;
/* 5178 */         float fadeFactor = (float)Math.exp(Math.log(0.5D) * fadeScalar / centerDepthSmoothHalflife);
/* 5179 */         centerDepthSmooth = centerDepthSmooth * fadeFactor + centerDepth * (1.0F - fadeFactor);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginWeather() {
/* 5186 */     if (!isShadowPass) {
/*      */       
/* 5188 */       if (usedDepthBuffers >= 3) {
/*      */ 
/*      */         
/* 5191 */         GlStateManager.setActiveTexture(33996);
/* 5192 */         GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, renderWidth, renderHeight);
/* 5193 */         GlStateManager.setActiveTexture(33984);
/*      */       } 
/*      */       
/* 5196 */       GlStateManager.enableDepth();
/* 5197 */       GlStateManager.enableBlend();
/* 5198 */       GlStateManager.blendFunc(770, 771);
/* 5199 */       GlStateManager.enableAlpha();
/* 5200 */       useProgram(20);
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
/*      */   public static void endWeather() {
/* 5212 */     GlStateManager.disableBlend();
/* 5213 */     useProgram(3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void preWater() {
/* 5223 */     if (usedDepthBuffers >= 2) {
/*      */ 
/*      */       
/* 5226 */       GlStateManager.setActiveTexture(33995);
/* 5227 */       checkGLError("pre copy depth");
/* 5228 */       GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, renderWidth, renderHeight);
/* 5229 */       checkGLError("copy depth");
/* 5230 */       GlStateManager.setActiveTexture(33984);
/*      */     } 
/* 5232 */     ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void beginWater() {
/* 5258 */     if (isRenderingWorld)
/*      */     {
/* 5260 */       if (!isShadowPass) {
/*      */ 
/*      */         
/* 5263 */         renderDeferred();
/*      */         
/* 5265 */         useProgram(12);
/* 5266 */         GlStateManager.enableBlend();
/* 5267 */         GlStateManager.depthMask(true);
/*      */       }
/*      */       else {
/*      */         
/* 5271 */         GlStateManager.depthMask(true);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endWater() {
/* 5278 */     if (isRenderingWorld) {
/*      */       
/* 5280 */       if (isShadowPass);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5288 */       useProgram(lightmapEnabled ? 3 : 2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginProjectRedHalo() {
/* 5294 */     if (isRenderingWorld)
/*      */     {
/* 5296 */       useProgram(1);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void endProjectRedHalo() {
/* 5301 */     if (isRenderingWorld)
/*      */     {
/* 5303 */       useProgram(3);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void applyHandDepth() {
/* 5309 */     if (configHandDepthMul != 1.0D)
/*      */     {
/* 5311 */       GL11.glScaled(1.0D, 1.0D, configHandDepthMul);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void beginHand(boolean translucent) {
/* 5319 */     GL11.glMatrixMode(5888);
/* 5320 */     GL11.glPushMatrix();
/* 5321 */     GL11.glMatrixMode(5889);
/* 5322 */     GL11.glPushMatrix();
/* 5323 */     GL11.glMatrixMode(5888);
/*      */     
/* 5325 */     if (translucent) {
/* 5326 */       useProgram(41);
/*      */     } else {
/* 5328 */       useProgram(19);
/*      */     } 
/* 5330 */     checkGLError("beginHand");
/* 5331 */     checkFramebufferStatus("beginHand");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void endHand() {
/* 5337 */     checkGLError("pre endHand");
/* 5338 */     checkFramebufferStatus("pre endHand");
/* 5339 */     GL11.glMatrixMode(5889);
/* 5340 */     GL11.glPopMatrix();
/* 5341 */     GL11.glMatrixMode(5888);
/* 5342 */     GL11.glPopMatrix();
/* 5343 */     GlStateManager.blendFunc(770, 771);
/*      */     
/* 5345 */     checkGLError("endHand");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginFPOverlay() {
/* 5350 */     GlStateManager.disableLighting();
/* 5351 */     GlStateManager.disableBlend();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void endFPOverlay() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glEnableWrapper(int cap) {
/* 5366 */     GL11.glEnable(cap);
/* 5367 */     if (cap == 3553) {
/* 5368 */       enableTexture2D();
/* 5369 */     } else if (cap == 2912) {
/* 5370 */       enableFog();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void glDisableWrapper(int cap) {
/* 5375 */     GL11.glDisable(cap);
/* 5376 */     if (cap == 3553) {
/* 5377 */       disableTexture2D();
/* 5378 */     } else if (cap == 2912) {
/* 5379 */       disableFog();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void sglEnableT2D(int cap) {
/* 5384 */     GL11.glEnable(cap);
/* 5385 */     enableTexture2D();
/*      */   }
/*      */   
/*      */   public static void sglDisableT2D(int cap) {
/* 5389 */     GL11.glDisable(cap);
/* 5390 */     disableTexture2D();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void sglEnableFog(int cap) {
/* 5395 */     GL11.glEnable(cap);
/* 5396 */     enableFog();
/*      */   }
/*      */   
/*      */   public static void sglDisableFog(int cap) {
/* 5400 */     GL11.glDisable(cap);
/* 5401 */     disableFog();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableTexture2D() {
/* 5406 */     if (isRenderingSky) {
/*      */       
/* 5408 */       useProgram(5);
/*      */     }
/* 5410 */     else if (activeProgram == 1) {
/*      */       
/* 5412 */       useProgram(lightmapEnabled ? 3 : 2);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void disableTexture2D() {
/* 5417 */     if (isRenderingSky) {
/*      */       
/* 5419 */       useProgram(4);
/*      */     }
/* 5421 */     else if (activeProgram == 2 || activeProgram == 3) {
/*      */       
/* 5423 */       useProgram(1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginLeash() {
/* 5429 */     useProgram(1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endLeash() {
/* 5434 */     useProgram(16);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableFog() {
/* 5439 */     fogEnabled = true;
/* 5440 */     setProgramUniform1i("fogMode", fogMode);
/*      */   }
/*      */   
/*      */   public static void disableFog() {
/* 5444 */     fogEnabled = false;
/* 5445 */     setProgramUniform1i("fogMode", 0);
/*      */   }
/*      */   
/*      */   public static void setFog(int fogMode) {
/* 5449 */     GlStateManager.setFog(fogMode);
/* 5450 */     Shaders.fogMode = fogMode;
/* 5451 */     if (fogEnabled) {
/* 5452 */       setProgramUniform1i("fogMode", fogMode);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void sglFogi(int pname, int param) {
/* 5457 */     GL11.glFogi(pname, param);
/* 5458 */     if (pname == 2917) {
/*      */       
/* 5460 */       fogMode = param;
/* 5461 */       if (fogEnabled) {
/* 5462 */         setProgramUniform1i("fogMode", fogMode);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void enableLightmap() {
/* 5468 */     lightmapEnabled = true;
/* 5469 */     if (activeProgram == 2)
/*      */     {
/* 5471 */       useProgram(3);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableLightmap() {
/* 5477 */     lightmapEnabled = false;
/* 5478 */     if (activeProgram == 3)
/*      */     {
/* 5480 */       useProgram(2);
/*      */     }
/*      */   }
/*      */   
/* 5484 */   public static int[] entityData = new int[32];
/* 5485 */   public static int entityDataIndex = 0;
/*      */ 
/*      */   
/*      */   public static int getEntityData() {
/* 5489 */     return entityData[entityDataIndex * 2];
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getEntityData2() {
/* 5494 */     return entityData[entityDataIndex * 2 + 1];
/*      */   }
/*      */ 
/*      */   
/*      */   public static int setEntityData1(int data1) {
/* 5499 */     entityData[entityDataIndex * 2] = entityData[entityDataIndex * 2] & 0xFFFF | data1 << 16;
/* 5500 */     return data1;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int setEntityData2(int data2) {
/* 5505 */     entityData[entityDataIndex * 2 + 1] = entityData[entityDataIndex * 2 + 1] & 0xFFFF0000 | data2 & 0xFFFF;
/* 5506 */     return data2;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void pushEntity(int data0, int data1) {
/* 5511 */     entityDataIndex++;
/* 5512 */     entityData[entityDataIndex * 2] = data0 & 0xFFFF | data1 << 16;
/* 5513 */     entityData[entityDataIndex * 2 + 1] = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void pushEntity(int data0) {
/* 5518 */     entityDataIndex++;
/* 5519 */     entityData[entityDataIndex * 2] = data0 & 0xFFFF;
/* 5520 */     entityData[entityDataIndex * 2 + 1] = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void pushEntity(aji block) {
/* 5526 */     int blockId = aji.c.b(block);
/* 5527 */     int metadata = 0;
/*      */     
/* 5529 */     blockId = BlockAliases.getMappedBlockId(blockId, metadata);
/*      */     
/* 5531 */     entityDataIndex++;
/* 5532 */     entityData[entityDataIndex * 2] = blockId & 0xFFFF | block.b() << 16;
/* 5533 */     entityData[entityDataIndex * 2 + 1] = metadata;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void pushEntity(blm rb, aji block, int x, int y, int z) {
/* 5539 */     int blockId = aji.c.b(block);
/* 5540 */     int metadata = rb.a.e(x, y, z);
/*      */     
/* 5542 */     blockId = BlockAliases.getMappedBlockId(blockId, metadata);
/*      */     
/* 5544 */     entityDataIndex++;
/* 5545 */     entityData[entityDataIndex * 2] = blockId & 0xFFFF | block.b() << 16;
/* 5546 */     entityData[entityDataIndex * 2 + 1] = metadata;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void popEntity() {
/* 5551 */     entityData[entityDataIndex * 2] = 0;
/* 5552 */     entityData[entityDataIndex * 2 + 1] = 0;
/* 5553 */     entityDataIndex--;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void mcProfilerEndSection() {
/* 5558 */     mc.z.b();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getShaderPackName() {
/* 5566 */     if (shaderPack == null) {
/* 5567 */       return null;
/*      */     }
/* 5569 */     if (shaderPack instanceof ShaderPackNone) {
/* 5570 */       return null;
/*      */     }
/* 5572 */     return shaderPack.getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static InputStream getShaderPackResourceStream(String path) {
/* 5580 */     if (shaderPack == null) {
/* 5581 */       return null;
/*      */     }
/* 5583 */     return shaderPack.getResourceAsStream(path);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void nextAntialiasingLevel() {
/* 5589 */     configAntialiasingLevel += 2;
/*      */     
/* 5591 */     configAntialiasingLevel = configAntialiasingLevel / 2 * 2;
/*      */     
/* 5593 */     if (configAntialiasingLevel > 4) {
/* 5594 */       configAntialiasingLevel = 0;
/*      */     }
/* 5596 */     configAntialiasingLevel = Config.limit(configAntialiasingLevel, 0, 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void checkShadersModInstalled() {
/* 5604 */     Class<?> cls = null;
/*      */ 
/*      */     
/*      */     try {
/* 5608 */       cls = Class.forName("shadersmod.transform.SMCClassTransformer");
/*      */     }
/* 5610 */     catch (Throwable e) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 5616 */       cls = Class.forName("shadersmodcore.transform.SMCClassTransformer");
/*      */     }
/* 5618 */     catch (Throwable e) {}
/*      */ 
/*      */ 
/*      */     
/* 5622 */     if (cls != null) {
/* 5623 */       throw new RuntimeException("Shaders Mod detected. Please remove it, OptiFine has built-in support for shaders.");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void resourcesReloaded() {
/* 5632 */     loadShaderPackResources();
/*      */     
/* 5634 */     if (shaderPackLoaded) {
/* 5635 */       BlockAliases.resourcesReloaded();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void loadShaderPackResources() {
/* 5643 */     shaderPackResources = new HashMap<String, String>();
/*      */     
/* 5645 */     if (!shaderPackLoaded) {
/*      */       return;
/*      */     }
/* 5648 */     List<String> listFiles = new ArrayList<String>();
/* 5649 */     String PREFIX = "/shaders/lang/";
/* 5650 */     String EN_US = "en_US";
/* 5651 */     String SUFFIX = ".lang";
/* 5652 */     listFiles.add(PREFIX + EN_US + SUFFIX);
/* 5653 */     if (!(Config.getGameSettings()).aK.equals(EN_US)) {
/* 5654 */       listFiles.add(PREFIX + (Config.getGameSettings()).aK + SUFFIX);
/*      */     }
/*      */     
/*      */     try {
/* 5658 */       for (Iterator<String> it = listFiles.iterator(); it.hasNext(); ) {
/*      */         
/* 5660 */         String file = it.next();
/* 5661 */         InputStream in = shaderPack.getResourceAsStream(file);
/* 5662 */         if (in == null) {
/*      */           continue;
/*      */         }
/* 5665 */         Properties props = new Properties();
/* 5666 */         Lang.loadLocaleData(in, props);
/* 5667 */         in.close();
/*      */         
/* 5669 */         Set keys = props.keySet();
/* 5670 */         for (Iterator<String> itp = keys.iterator(); itp.hasNext(); )
/*      */         {
/* 5672 */           String key = itp.next();
/* 5673 */           String value = props.getProperty(key);
/* 5674 */           shaderPackResources.put(key, value);
/*      */         }
/*      */       
/*      */       } 
/* 5678 */     } catch (IOException e) {
/*      */       
/* 5680 */       e.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String translate(String key, String def) {
/* 5688 */     String str = shaderPackResources.get(key);
/* 5689 */     if (str == null) {
/* 5690 */       return def;
/*      */     }
/* 5692 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isProgramPath(String program) {
/* 5700 */     if (program == null) {
/* 5701 */       return false;
/*      */     }
/* 5703 */     if (program.length() <= 0) {
/* 5704 */       return false;
/*      */     }
/* 5706 */     int pos = program.lastIndexOf("/");
/* 5707 */     if (pos >= 0) {
/* 5708 */       program = program.substring(pos + 1);
/*      */     }
/* 5710 */     return Arrays.<String>asList(programNames).contains(program);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setItemToRenderMain(add itemToRenderMain) {
/* 5718 */     itemToRenderMainTranslucent = isTranslucentBlock(itemToRenderMain);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isItemToRenderMainTranslucent() {
/* 5725 */     return itemToRenderMainTranslucent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isTranslucentBlock(add stack) {
/* 5734 */     if (stack == null) {
/* 5735 */       return false;
/*      */     }
/* 5737 */     adb item = stack.b();
/* 5738 */     if (item == null) {
/* 5739 */       return false;
/*      */     }
/* 5741 */     if (!(item instanceof abh))
/* 5742 */       return false; 
/* 5743 */     abh itemBlock = (abh)item;
/*      */     
/* 5745 */     aji block = (aji)Reflector.getFieldValue(item, Reflector.ItemBlock_block);
/* 5746 */     if (block == null) {
/* 5747 */       return false;
/*      */     }
/* 5749 */     int renderPass = block.w();
/*      */     
/* 5751 */     return (renderPass != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isRenderBothHands() {
/* 5758 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isHandRenderedMain() {
/* 5765 */     return isHandRenderedMain;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setHandRenderedMain(boolean isHandRenderedMain) {
/* 5772 */     Shaders.isHandRenderedMain = isHandRenderedMain;
/*      */   }
/*      */ 
/*      */   
/*      */   public static float getShadowRenderDistance() {
/* 5777 */     if (shadowDistanceRenderMul < 0.0F) {
/* 5778 */       return -1.0F;
/*      */     }
/* 5780 */     return shadowMapHalfPlane * shadowDistanceRenderMul;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setRenderingFirstPersonHand(boolean flag) {
/* 5788 */     isRenderingFirstPersonHand = flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isRenderingFirstPersonHand() {
/* 5796 */     return isRenderingFirstPersonHand;
/*      */   }
/*      */   
/*      */   public static void beginBeacon() {
/* 5800 */     if (isRenderingWorld)
/*      */     {
/* 5802 */       useProgram(14);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void endBeacon() {
/* 5807 */     if (isRenderingWorld)
/*      */     {
/* 5809 */       useProgram(13);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ahb getCurrentWorld() {
/* 5817 */     return currentWorld;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static azw getCameraPosition() {
/* 5823 */     return azw.a(cameraPositionX, cameraPositionY, cameraPositionZ);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isCustomUniforms() {
/* 5829 */     return (customUniforms != null);
/*      */   }
/*      */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\Shaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */