/*     */ package shadersmod.client;
/*     */ 
/*     */ import Config;
/*     */ import u;
/*     */ import v;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShaderMacros
/*     */ {
/*  16 */   private static String PREFIX_MACRO = "MC_";
/*     */   
/*     */   public static final String MC_VERSION = "MC_VERSION";
/*     */   
/*     */   public static final String MC_GL_VERSION = "MC_GL_VERSION";
/*     */   
/*     */   public static final String MC_GLSL_VERSION = "MC_GLSL_VERSION";
/*     */   
/*     */   public static final String MC_OS_WINDOWS = "MC_OS_WINDOWS";
/*     */   
/*     */   public static final String MC_OS_MAC = "MC_OS_MAC";
/*     */   
/*     */   public static final String MC_OS_LINUX = "MC_OS_LINUX";
/*     */   
/*     */   public static final String MC_OS_OTHER = "MC_OS_OTHER";
/*     */   
/*     */   public static final String MC_GL_VENDOR_ATI = "MC_GL_VENDOR_ATI";
/*     */   
/*     */   public static final String MC_GL_VENDOR_INTEL = "MC_GL_VENDOR_INTEL";
/*     */   public static final String MC_GL_VENDOR_NVIDIA = "MC_GL_VENDOR_NVIDIA";
/*     */   public static final String MC_GL_VENDOR_XORG = "MC_GL_VENDOR_XORG";
/*     */   public static final String MC_GL_VENDOR_OTHER = "MC_GL_VENDOR_OTHER";
/*     */   public static final String MC_GL_RENDERER_RADEON = "MC_GL_RENDERER_RADEON";
/*     */   public static final String MC_GL_RENDERER_GEFORCE = "MC_GL_RENDERER_GEFORCE";
/*     */   public static final String MC_GL_RENDERER_QUADRO = "MC_GL_RENDERER_QUADRO";
/*     */   public static final String MC_GL_RENDERER_INTEL = "MC_GL_RENDERER_INTEL";
/*     */   public static final String MC_GL_RENDERER_GALLIUM = "MC_GL_RENDERER_GALLIUM";
/*     */   public static final String MC_GL_RENDERER_MESA = "MC_GL_RENDERER_MESA";
/*     */   public static final String MC_GL_RENDERER_OTHER = "MC_GL_RENDERER_OTHER";
/*     */   public static final String MC_FXAA_LEVEL = "MC_FXAA_LEVEL";
/*     */   public static final String MC_NORMAL_MAP = "MC_NORMAL_MAP";
/*     */   public static final String MC_SPECULAR_MAP = "MC_SPECULAR_MAP";
/*     */   public static final String MC_RENDER_QUALITY = "MC_RENDER_QUALITY";
/*     */   public static final String MC_SHADOW_QUALITY = "MC_SHADOW_QUALITY";
/*     */   public static final String MC_HAND_DEPTH = "MC_HAND_DEPTH";
/*     */   public static final String MC_OLD_HAND_LIGHT = "MC_OLD_HAND_LIGHT";
/*     */   public static final String MC_OLD_LIGHTING = "MC_OLD_LIGHTING";
/*     */   private static String[] extensionMacros;
/*     */   
/*     */   public static String getOs() {
/*  56 */     v os = u.a();
/*  57 */     switch (os) {
/*     */       
/*     */       case c:
/*  60 */         return "MC_OS_WINDOWS";
/*     */       case d:
/*  62 */         return "MC_OS_MAC";
/*     */       case a:
/*  64 */         return "MC_OS_LINUX";
/*     */     } 
/*     */     
/*  67 */     return "MC_OS_OTHER";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVendor() {
/*  73 */     String vendor = Config.openGlVendor;
/*  74 */     if (vendor == null) {
/*  75 */       return "MC_GL_VENDOR_OTHER";
/*     */     }
/*  77 */     vendor = vendor.toLowerCase();
/*     */     
/*  79 */     if (vendor.startsWith("ati"))
/*  80 */       return "MC_GL_VENDOR_ATI"; 
/*  81 */     if (vendor.startsWith("intel"))
/*  82 */       return "MC_GL_VENDOR_INTEL"; 
/*  83 */     if (vendor.startsWith("nvidia"))
/*  84 */       return "MC_GL_VENDOR_NVIDIA"; 
/*  85 */     if (vendor.startsWith("x.org")) {
/*  86 */       return "MC_GL_VENDOR_XORG";
/*     */     }
/*  88 */     return "MC_GL_VENDOR_OTHER";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getRenderer() {
/*  94 */     String renderer = Config.openGlRenderer;
/*  95 */     if (renderer == null) {
/*  96 */       return "MC_GL_RENDERER_OTHER";
/*     */     }
/*  98 */     renderer = renderer.toLowerCase();
/*     */     
/* 100 */     if (renderer.startsWith("amd"))
/* 101 */       return "MC_GL_RENDERER_RADEON"; 
/* 102 */     if (renderer.startsWith("ati"))
/* 103 */       return "MC_GL_RENDERER_RADEON"; 
/* 104 */     if (renderer.startsWith("radeon")) {
/* 105 */       return "MC_GL_RENDERER_RADEON";
/*     */     }
/* 107 */     if (renderer.startsWith("gallium")) {
/* 108 */       return "MC_GL_RENDERER_GALLIUM";
/*     */     }
/* 110 */     if (renderer.startsWith("intel")) {
/* 111 */       return "MC_GL_RENDERER_INTEL";
/*     */     }
/* 113 */     if (renderer.startsWith("geforce"))
/* 114 */       return "MC_GL_RENDERER_GEFORCE"; 
/* 115 */     if (renderer.startsWith("nvidia")) {
/* 116 */       return "MC_GL_RENDERER_GEFORCE";
/*     */     }
/* 118 */     if (renderer.startsWith("quadro"))
/* 119 */       return "MC_GL_RENDERER_QUADRO"; 
/* 120 */     if (renderer.startsWith("nvs")) {
/* 121 */       return "MC_GL_RENDERER_QUADRO";
/*     */     }
/* 123 */     if (renderer.startsWith("mesa")) {
/* 124 */       return "MC_GL_RENDERER_MESA";
/*     */     }
/* 126 */     return "MC_GL_RENDERER_OTHER";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getPrefixMacro() {
/* 132 */     return PREFIX_MACRO;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] getExtensions() {
/* 138 */     if (extensionMacros == null) {
/*     */       
/* 140 */       String[] exts = Config.getOpenGlExtensions();
/* 141 */       String[] extMacros = new String[exts.length];
/* 142 */       for (int i = 0; i < exts.length; i++)
/*     */       {
/* 144 */         extMacros[i] = PREFIX_MACRO + exts[i];
/*     */       }
/*     */       
/* 147 */       extensionMacros = extMacros;
/*     */     } 
/*     */     
/* 150 */     return extensionMacros;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getMacroLines() {
/* 157 */     StringBuilder sb = new StringBuilder();
/* 158 */     addMacroLine(sb, "MC_VERSION", Config.getMinecraftVersionInt());
/* 159 */     addMacroLine(sb, "MC_GL_VERSION " + Config.getGlVersion().toInt());
/* 160 */     addMacroLine(sb, "MC_GLSL_VERSION " + Config.getGlslVersion().toInt());
/* 161 */     addMacroLine(sb, getOs());
/* 162 */     addMacroLine(sb, getVendor());
/* 163 */     addMacroLine(sb, getRenderer());
/*     */     
/* 165 */     if (Shaders.configAntialiasingLevel > 0)
/* 166 */       addMacroLine(sb, "MC_FXAA_LEVEL", Shaders.configAntialiasingLevel); 
/* 167 */     if (Shaders.configNormalMap)
/* 168 */       addMacroLine(sb, "MC_NORMAL_MAP"); 
/* 169 */     if (Shaders.configSpecularMap) {
/* 170 */       addMacroLine(sb, "MC_SPECULAR_MAP");
/*     */     }
/* 172 */     addMacroLine(sb, "MC_RENDER_QUALITY", Shaders.configRenderResMul);
/* 173 */     addMacroLine(sb, "MC_SHADOW_QUALITY", Shaders.configShadowResMul);
/*     */     
/* 175 */     addMacroLine(sb, "MC_HAND_DEPTH", Shaders.configHandDepthMul);
/*     */     
/* 177 */     if (Shaders.isOldHandLight())
/* 178 */       addMacroLine(sb, "MC_OLD_HAND_LIGHT"); 
/* 179 */     if (Shaders.isOldLighting()) {
/* 180 */       addMacroLine(sb, "MC_OLD_LIGHTING");
/*     */     }
/* 182 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addMacroLine(StringBuilder sb, String name, int value) {
/* 188 */     sb.append("#define ");
/* 189 */     sb.append(name);
/* 190 */     sb.append(" ");
/* 191 */     sb.append(value);
/* 192 */     sb.append("\n");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addMacroLine(StringBuilder sb, String name, float value) {
/* 198 */     sb.append("#define ");
/* 199 */     sb.append(name);
/* 200 */     sb.append(" ");
/* 201 */     sb.append(value);
/* 202 */     sb.append("\n");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addMacroLine(StringBuilder sb, String name) {
/* 208 */     sb.append("#define ");
/* 209 */     sb.append(name);
/* 210 */     sb.append("\n");
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\ShaderMacros.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */