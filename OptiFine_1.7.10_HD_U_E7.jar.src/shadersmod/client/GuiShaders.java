/*     */ package shadersmod.client;
/*     */ 
/*     */ import Config;
/*     */ import Lang;
/*     */ import TooltipManager;
/*     */ import TooltipProvider;
/*     */ import TooltipProviderEnumShaderOptions;
/*     */ import bao;
/*     */ import bbj;
/*     */ import bbu;
/*     */ import bcb;
/*     */ import bdw;
/*     */ import brp;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.util.Iterator;
/*     */ import org.lwjgl.Sys;
/*     */ 
/*     */ public class GuiShaders
/*     */   extends bdw
/*     */ {
/*     */   protected bdw parentGui;
/*  24 */   protected String screenTitle = "Shaders";
/*     */   
/*  26 */   private TooltipManager tooltipManager = new TooltipManager(this, (TooltipProvider)new TooltipProviderEnumShaderOptions());
/*     */   
/*  28 */   private int updateTimer = -1;
/*     */ 
/*     */   
/*     */   private GuiSlotShaders shaderList;
/*     */   
/*     */   private boolean saved = false;
/*     */   
/*     */   private bbu fontRendererObj;
/*     */   
/*  37 */   private static float[] QUALITY_MULTIPLIERS = new float[] { 0.5F, 0.70710677F, 1.0F, 1.4142135F, 2.0F };
/*  38 */   private static String[] QUALITY_MULTIPLIER_NAMES = new String[] { "0.5x", "0.7x", "1x", "1.5x", "2x" };
/*  39 */   private static float[] HAND_DEPTH_VALUES = new float[] { 0.0625F, 0.125F, 0.25F };
/*  40 */   private static String[] HAND_DEPTH_NAMES = new String[] { "0.5x", "1x", "2x" }; public static final int EnumOS_UNKNOWN = 0;
/*     */   public static final int EnumOS_WINDOWS = 1;
/*     */   
/*     */   public GuiShaders(bdw par1GuiScreen, bbj par2GameSettings) {
/*  44 */     this.parentGui = par1GuiScreen;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int EnumOS_OSX = 2;
/*     */   
/*     */   public static final int EnumOS_SOLARIS = 3;
/*     */   public static final int EnumOS_LINUX = 4;
/*     */   
/*     */   public void b() {
/*  54 */     this.fontRendererObj = this.q;
/*     */     
/*  56 */     this.screenTitle = brp.a("of.options.shadersTitle", new Object[0]);
/*     */     
/*  58 */     if (Shaders.shadersConfig == null) {
/*  59 */       Shaders.loadConfig();
/*     */     }
/*  61 */     int btnWidth = 120;
/*  62 */     int btnHeight = 20;
/*  63 */     int btnX = this.l - btnWidth - 10;
/*  64 */     int baseY = 30;
/*  65 */     int stepY = 20;
/*     */     
/*  67 */     int shaderListWidth = this.l - btnWidth - 20;
/*  68 */     this.shaderList = new GuiSlotShaders(this, shaderListWidth, this.m, baseY, this.m - 50, 16);
/*  69 */     this.shaderList.d(7, 8);
/*     */     
/*  71 */     this.n.add(new GuiButtonEnumShaderOption(EnumShaderOption.ANTIALIASING, btnX, 0 * stepY + baseY, btnWidth, btnHeight));
/*  72 */     this.n.add(new GuiButtonEnumShaderOption(EnumShaderOption.NORMAL_MAP, btnX, 1 * stepY + baseY, btnWidth, btnHeight));
/*  73 */     this.n.add(new GuiButtonEnumShaderOption(EnumShaderOption.SPECULAR_MAP, btnX, 2 * stepY + baseY, btnWidth, btnHeight));
/*  74 */     this.n.add(new GuiButtonEnumShaderOption(EnumShaderOption.RENDER_RES_MUL, btnX, 3 * stepY + baseY, btnWidth, btnHeight));
/*  75 */     this.n.add(new GuiButtonEnumShaderOption(EnumShaderOption.SHADOW_RES_MUL, btnX, 4 * stepY + baseY, btnWidth, btnHeight));
/*  76 */     this.n.add(new GuiButtonEnumShaderOption(EnumShaderOption.HAND_DEPTH_MUL, btnX, 5 * stepY + baseY, btnWidth, btnHeight));
/*     */     
/*  78 */     this.n.add(new GuiButtonEnumShaderOption(EnumShaderOption.OLD_HAND_LIGHT, btnX, 6 * stepY + baseY, btnWidth, btnHeight));
/*  79 */     this.n.add(new GuiButtonEnumShaderOption(EnumShaderOption.OLD_LIGHTING, btnX, 7 * stepY + baseY, btnWidth, btnHeight));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     int btnFolderWidth = Math.min(150, shaderListWidth / 2 - 10);
/*     */     
/*  88 */     this.n.add(new bcb(201, shaderListWidth / 4 - btnFolderWidth / 2, this.m - 25, btnFolderWidth, btnHeight, Lang.get("of.options.shaders.shadersFolder")));
/*     */     
/*  90 */     this.n.add(new bcb(202, shaderListWidth / 4 * 3 - btnFolderWidth / 2, this.m - 25, btnFolderWidth, btnHeight, brp.a("gui.done", new Object[0])));
/*     */     
/*  92 */     this.n.add(new bcb(203, btnX, this.m - 25, btnWidth, btnHeight, Lang.get("of.options.shaders.shaderOptions")));
/*     */     
/*  94 */     updateButtons();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateButtons() {
/*  99 */     boolean shaderActive = Config.isShaders();
/* 100 */     for (Iterator<bcb> it = this.n.iterator(); it.hasNext(); ) {
/*     */       
/* 102 */       bcb button = it.next();
/*     */       
/* 104 */       if (button.k == 201 || button.k == 202) {
/*     */         continue;
/*     */       }
/* 107 */       if (button.k == EnumShaderOption.ANTIALIASING.ordinal()) {
/*     */         continue;
/*     */       }
/* 110 */       button.l = shaderActive;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void k() {
/* 117 */     super.k();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(bcb button) {
/*     */     float val, values[];
/*     */     String[] names;
/*     */     int index;
/* 127 */     if (!button.l) {
/*     */       return;
/*     */     }
/* 130 */     if (!(button instanceof GuiButtonEnumShaderOption)) {
/*     */       String var2; boolean var8; File confshader; GuiShaderOptions gui;
/* 132 */       switch (button.k) {
/*     */         
/*     */         case 201:
/* 135 */           switch (getOSType()) {
/*     */             
/*     */             case 2:
/*     */               
/*     */               try {
/*     */                 
/* 141 */                 Runtime.getRuntime().exec(new String[] { "/usr/bin/open", Shaders.shaderpacksdir.getAbsolutePath() });
/*     */                 
/*     */                 return;
/* 144 */               } catch (IOException var7) {
/*     */                 
/* 146 */                 var7.printStackTrace();
/*     */                 break;
/*     */               } 
/*     */ 
/*     */             
/*     */             case 1:
/* 152 */               var2 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] { Shaders.shaderpacksdir.getAbsolutePath() });
/*     */ 
/*     */               
/*     */               try {
/* 156 */                 Runtime.getRuntime().exec(var2);
/*     */                 
/*     */                 return;
/* 159 */               } catch (IOException var6) {
/*     */                 
/* 161 */                 var6.printStackTrace();
/*     */                 break;
/*     */               } 
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 168 */           var8 = false;
/*     */ 
/*     */           
/*     */           try {
/* 172 */             Class<?> var3 = Class.forName("java.awt.Desktop");
/* 173 */             Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 174 */             var3.getMethod("browse", new Class[] { URI.class }).invoke(var4, new Object[] { (new File(this.k.w, Shaders.shaderpacksdirname)).toURI() });
/*     */           }
/* 176 */           catch (Throwable var5) {
/*     */             
/* 178 */             var5.printStackTrace();
/* 179 */             var8 = true;
/*     */           } 
/*     */           
/* 182 */           if (var8) {
/*     */             
/* 184 */             Config.dbg("Opening via system class!");
/* 185 */             Sys.openURL("file://" + Shaders.shaderpacksdir.getAbsolutePath());
/*     */           } 
/*     */           return;
/*     */         
/*     */         case 202:
/* 190 */           confshader = new File(Shaders.shadersdir, "current.cfg");
/*     */           
/* 192 */           Shaders.storeConfig();
/* 193 */           this.saved = true;
/*     */           
/* 195 */           this.k.a(this.parentGui);
/*     */           return;
/*     */         
/*     */         case 203:
/* 199 */           gui = new GuiShaderOptions(this, Config.getGameSettings());
/* 200 */           Config.getMinecraft().a((bdw)gui);
/*     */           return;
/*     */       } 
/*     */       
/* 204 */       this.shaderList.a(button);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 210 */     GuiButtonEnumShaderOption gbeso = (GuiButtonEnumShaderOption)button;
/* 211 */     switch (gbeso.getEnumShaderOption()) {
/*     */       
/*     */       case ANTIALIASING:
/* 214 */         Shaders.nextAntialiasingLevel();
/* 215 */         Shaders.uninit();
/*     */         break;
/*     */       
/*     */       case NORMAL_MAP:
/* 219 */         Shaders.configNormalMap = !Shaders.configNormalMap;
/*     */         
/* 221 */         Shaders.uninit();
/* 222 */         this.k.C();
/*     */         break;
/*     */       
/*     */       case SPECULAR_MAP:
/* 226 */         Shaders.configSpecularMap = !Shaders.configSpecularMap;
/*     */         
/* 228 */         Shaders.uninit();
/* 229 */         this.k.C();
/*     */         break;
/*     */ 
/*     */       
/*     */       case RENDER_RES_MUL:
/* 234 */         val = Shaders.configRenderResMul;
/* 235 */         values = QUALITY_MULTIPLIERS;
/* 236 */         names = QUALITY_MULTIPLIER_NAMES;
/* 237 */         index = getValueIndex(val, values);
/* 238 */         if (r()) {
/*     */           
/* 240 */           index--;
/* 241 */           if (index < 0) {
/* 242 */             index = values.length - 1;
/*     */           }
/*     */         } else {
/*     */           
/* 246 */           index++;
/* 247 */           if (index >= values.length)
/* 248 */             index = 0; 
/*     */         } 
/* 250 */         Shaders.configRenderResMul = values[index];
/*     */         
/* 252 */         Shaders.uninit();
/* 253 */         Shaders.scheduleResize();
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case SHADOW_RES_MUL:
/* 259 */         val = Shaders.configShadowResMul;
/* 260 */         values = QUALITY_MULTIPLIERS;
/* 261 */         names = QUALITY_MULTIPLIER_NAMES;
/* 262 */         index = getValueIndex(val, values);
/* 263 */         if (r()) {
/*     */           
/* 265 */           index--;
/* 266 */           if (index < 0) {
/* 267 */             index = values.length - 1;
/*     */           }
/*     */         } else {
/*     */           
/* 271 */           index++;
/* 272 */           if (index >= values.length)
/* 273 */             index = 0; 
/*     */         } 
/* 275 */         Shaders.configShadowResMul = values[index];
/*     */         
/* 277 */         Shaders.uninit();
/* 278 */         Shaders.scheduleResizeShadow();
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case HAND_DEPTH_MUL:
/* 284 */         val = Shaders.configHandDepthMul;
/* 285 */         values = HAND_DEPTH_VALUES;
/* 286 */         names = HAND_DEPTH_NAMES;
/* 287 */         index = getValueIndex(val, values);
/* 288 */         if (r()) {
/*     */           
/* 290 */           index--;
/* 291 */           if (index < 0) {
/* 292 */             index = values.length - 1;
/*     */           }
/*     */         } else {
/*     */           
/* 296 */           index++;
/* 297 */           if (index >= values.length)
/* 298 */             index = 0; 
/*     */         } 
/* 300 */         Shaders.configHandDepthMul = values[index];
/*     */         
/* 302 */         Shaders.uninit();
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case OLD_HAND_LIGHT:
/* 308 */         Shaders.configOldHandLight.nextValue();
/*     */         
/* 310 */         Shaders.uninit();
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case OLD_LIGHTING:
/* 316 */         Shaders.configOldLighting.nextValue();
/* 317 */         Shaders.updateBlockLightLevel();
/* 318 */         Shaders.uninit();
/*     */         
/* 320 */         this.k.C();
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case TWEAK_BLOCK_DAMAGE:
/* 326 */         Shaders.configTweakBlockDamage = !Shaders.configTweakBlockDamage;
/*     */         break;
/*     */       
/*     */       case CLOUD_SHADOW:
/* 330 */         Shaders.configCloudShadow = !Shaders.configCloudShadow;
/*     */         break;
/*     */ 
/*     */       
/*     */       case TEX_MIN_FIL_B:
/* 335 */         Shaders.configTexMinFilB = (Shaders.configTexMinFilB + 1) % 3;
/* 336 */         Shaders.configTexMinFilN = Shaders.configTexMinFilS = Shaders.configTexMinFilB;
/* 337 */         button.j = "Tex Min: " + Shaders.texMinFilDesc[Shaders.configTexMinFilB];
/* 338 */         ShadersTex.updateTextureMinMagFilter();
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case TEX_MAG_FIL_N:
/* 344 */         Shaders.configTexMagFilN = (Shaders.configTexMagFilN + 1) % 2;
/* 345 */         button.j = "Tex_n Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilN];
/* 346 */         ShadersTex.updateTextureMinMagFilter();
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case TEX_MAG_FIL_S:
/* 352 */         Shaders.configTexMagFilS = (Shaders.configTexMagFilS + 1) % 2;
/* 353 */         button.j = "Tex_s Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilS];
/* 354 */         ShadersTex.updateTextureMinMagFilter();
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case SHADOW_CLIP_FRUSTRUM:
/* 360 */         Shaders.configShadowClipFrustrum = !Shaders.configShadowClipFrustrum;
/* 361 */         button.j = "ShadowClipFrustrum: " + toStringOnOff(Shaders.configShadowClipFrustrum);
/* 362 */         ShadersTex.updateTextureMinMagFilter();
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 368 */     gbeso.updateButtonText();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void m() {
/* 377 */     super.m();
/*     */     
/* 379 */     if (!this.saved) {
/* 380 */       Shaders.storeConfig();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float partialTicks) {
/* 390 */     c();
/*     */     
/* 392 */     this.shaderList.a(mouseX, mouseY, partialTicks);
/*     */     
/* 394 */     if (this.updateTimer <= 0) {
/*     */       
/* 396 */       this.shaderList.updateList();
/* 397 */       this.updateTimer += 20;
/*     */     } 
/*     */     
/* 400 */     a(this.fontRendererObj, this.screenTitle + " ", this.l / 2, 15, 16777215);
/*     */     
/* 402 */     String info = "OpenGL: " + Shaders.glVersionString + ", " + Shaders.glVendorString + ", " + Shaders.glRendererString;
/* 403 */     int infoWidth = this.fontRendererObj.a(info);
/* 404 */     if (infoWidth < this.l - 5) {
/* 405 */       a(this.fontRendererObj, info, this.l / 2, this.m - 40, 8421504);
/*     */     } else {
/* 407 */       b(this.fontRendererObj, info, 5, this.m - 40, 8421504);
/*     */     } 
/* 409 */     super.a(mouseX, mouseY, partialTicks);
/*     */     
/* 411 */     this.tooltipManager.drawTooltips(mouseX, mouseY, this.n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 420 */     super.e();
/* 421 */     this.updateTimer--;
/*     */   }
/*     */ 
/*     */   
/*     */   public bao getMc() {
/* 426 */     return this.k;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCenteredString(String text, int x, int y, int color) {
/* 431 */     a(this.fontRendererObj, text, x, y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toStringOnOff(boolean value) {
/* 436 */     String on = Lang.getOn();
/* 437 */     String off = Lang.getOff();
/* 438 */     return value ? on : off;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toStringAa(int value) {
/* 444 */     if (value == 2) {
/* 445 */       return "FXAA 2x";
/*     */     }
/* 447 */     if (value == 4) {
/* 448 */       return "FXAA 4x";
/*     */     }
/* 450 */     return Lang.getOff();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toStringValue(float val, float[] values, String[] names) {
/* 455 */     int index = getValueIndex(val, values);
/*     */     
/* 457 */     return names[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getValueIndex(float val, float[] values) {
/* 462 */     for (int i = 0; i < values.length; i++) {
/*     */       
/* 464 */       float value = values[i];
/* 465 */       if (value >= val) {
/* 466 */         return i;
/*     */       }
/*     */     } 
/* 469 */     return values.length - 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toStringQuality(float val) {
/* 474 */     return toStringValue(val, QUALITY_MULTIPLIERS, QUALITY_MULTIPLIER_NAMES);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toStringHandDepth(float val) {
/* 479 */     return toStringValue(val, HAND_DEPTH_VALUES, HAND_DEPTH_NAMES);
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
/*     */   public static int getOSType() {
/* 491 */     String osName = System.getProperty("os.name").toLowerCase();
/* 492 */     if (osName.contains("win"))
/* 493 */       return 1; 
/* 494 */     if (osName.contains("mac"))
/* 495 */       return 2; 
/* 496 */     if (osName.contains("solaris"))
/* 497 */       return 3; 
/* 498 */     if (osName.contains("sunos"))
/* 499 */       return 3; 
/* 500 */     if (osName.contains("linux"))
/* 501 */       return 4; 
/* 502 */     if (osName.contains("unix")) {
/* 503 */       return 4;
/*     */     }
/* 505 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\GuiShaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */