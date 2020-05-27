/*     */ package shadersmod.client;
/*     */ 
/*     */ import Config;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import shadersmod.common.SMCLog;
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
/*     */ 
/*     */ public class ShadersBuiltIn
/*     */ {
/*     */   public static Reader getShaderReader(String filename) {
/*  25 */     if (filename.endsWith("/deferred_last.vsh"))
/*  26 */       return getCompositeShaderReader(true, true); 
/*  27 */     if (filename.endsWith("/composite_last.vsh")) {
/*  28 */       return getCompositeShaderReader(false, true);
/*     */     }
/*  30 */     if (filename.endsWith("/deferred_last.fsh"))
/*  31 */       return getCompositeShaderReader(true, false); 
/*  32 */     if (filename.endsWith("/composite_last.fsh")) {
/*  33 */       return getCompositeShaderReader(false, false);
/*     */     }
/*  35 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Reader getCompositeShaderReader(boolean deferred, boolean vertex) {
/*     */     String shader;
/*  44 */     if (!hasDeferredPrograms() && !hasSkipClear()) {
/*  45 */       return null;
/*     */     }
/*  47 */     int[] flipBuffers = getLastFlipBuffers(deferred);
/*     */     
/*  49 */     if (flipBuffers == null) {
/*  50 */       return null;
/*     */     }
/*  52 */     if (!vertex) {
/*     */       
/*  54 */       String stage = deferred ? "deferred" : "composite";
/*  55 */       SMCLog.info("flipped buffers after " + stage + ": " + Config.arrayToString(flipBuffers));
/*     */     } 
/*     */ 
/*     */     
/*  59 */     if (vertex) {
/*  60 */       shader = getCompositeVertexShader(flipBuffers);
/*     */     } else {
/*  62 */       shader = getCompositeFragmentShader(flipBuffers);
/*     */     } 
/*  64 */     return new StringReader(shader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Reader getCompositeFragmentShaderReader(boolean deferred) {
/*  73 */     if (!hasDeferredPrograms() && !hasSkipClear()) {
/*  74 */       return null;
/*     */     }
/*  76 */     int[] flipBuffers = getLastFlipBuffers(deferred);
/*     */     
/*  78 */     if (flipBuffers == null) {
/*  79 */       return null;
/*     */     }
/*  81 */     String shader = getCompositeFragmentShader(flipBuffers);
/*     */     
/*  83 */     return new StringReader(shader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean hasDeferredPrograms() {
/*  91 */     for (int i = 33; i < 41; i++) {
/*     */       
/*  93 */       if (Shaders.programsID[i] != 0) {
/*  94 */         return true;
/*     */       }
/*     */     } 
/*  97 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean hasSkipClear() {
/* 105 */     for (int i = 0; i < Shaders.gbuffersClear.length; i++) {
/*     */       
/* 107 */       if (!Shaders.gbuffersClear[i]) {
/* 108 */         return true;
/*     */       }
/*     */     } 
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getCompositeVertexShader(int[] buffers) {
/* 119 */     List<String> list = new ArrayList<String>();
/*     */     
/* 121 */     list.add("#version 120                        ");
/* 122 */     list.add("varying vec2 texcoord;              ");
/* 123 */     list.add("void main()                         ");
/* 124 */     list.add("{                                   ");
/* 125 */     list.add("  gl_Position = ftransform();       ");
/* 126 */     list.add("  texcoord = gl_MultiTexCoord0.xy;  ");
/* 127 */     list.add("}                                   ");
/*     */     
/* 129 */     return Config.listToString(list, "\n");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getCompositeFragmentShader(int[] buffers) {
/* 137 */     List<String> list = new ArrayList<String>();
/* 138 */     String drawBuffers = Config.arrayToString(buffers, "");
/*     */     
/* 140 */     list.add("#version 120                                           "); int i;
/* 141 */     for (i = 0; i < buffers.length; i++)
/*     */     {
/* 143 */       list.add("uniform sampler2D colortex" + buffers[i] + ";        ");
/*     */     }
/* 145 */     list.add("varying vec2 texcoord;                                 ");
/* 146 */     list.add("/* DRAWBUFFERS:" + drawBuffers + " */                  ");
/* 147 */     list.add("void main()                                            ");
/* 148 */     list.add("{                                                      ");
/* 149 */     for (i = 0; i < buffers.length; i++)
/*     */     {
/* 151 */       list.add("  gl_FragData[" + i + "] = texture2D(colortex" + buffers[i] + ", texcoord);     ");
/*     */     }
/* 153 */     list.add("}                                                      ");
/*     */     
/* 155 */     return Config.listToString(list, "\n");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] getLastFlipBuffers(boolean deferred) {
/* 165 */     if (deferred) {
/* 166 */       return getLastFlipBuffers(33, 8);
/*     */     }
/* 168 */     return getLastFlipBuffers(21, 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] getLastFlipBuffers(int programStart, int programCount) {
/* 176 */     List<Integer> list = new ArrayList<Integer>();
/*     */     
/* 178 */     boolean[] toggled = new boolean[8];
/*     */     
/* 180 */     for (int p = programStart; p < programStart + programCount; p++) {
/*     */ 
/*     */       
/* 183 */       if (Shaders.programsID[p] != 0) {
/*     */ 
/*     */         
/* 186 */         boolean[] togglesTexture = getProgramTogglesTexture(p);
/*     */         
/* 188 */         for (int i = 0; i < togglesTexture.length; i++) {
/*     */           
/* 190 */           boolean toggle = togglesTexture[i];
/* 191 */           if (toggle)
/* 192 */             toggled[i] = !toggled[i]; 
/*     */         } 
/*     */       } 
/*     */     } 
/* 196 */     for (int t = 0; t < toggled.length; t++) {
/*     */       
/* 198 */       boolean toggle = toggled[t];
/* 199 */       if (toggle) {
/* 200 */         list.add(new Integer(t));
/*     */       }
/*     */     } 
/* 203 */     if (list.isEmpty()) {
/* 204 */       return null;
/*     */     }
/* 206 */     Integer[] arr = list.<Integer>toArray(new Integer[list.size()]);
/*     */     
/* 208 */     return Config.toPrimitive(arr);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean[] getProgramTogglesTexture(int program) {
/* 217 */     boolean[] toggles = new boolean[8];
/* 218 */     String drawBufStr = Shaders.programsDrawBufSettings[program];
/* 219 */     if (drawBufStr == null)
/* 220 */       return toggles; 
/* 221 */     for (int i = 0; i < drawBufStr.length(); i++) {
/*     */       
/* 223 */       char ch = drawBufStr.charAt(i);
/*     */       
/* 225 */       int buf = ch - 48;
/*     */       
/* 227 */       if (buf >= 0 && buf < toggles.length)
/*     */       {
/*     */         
/* 230 */         toggles[buf] = true;
/*     */       }
/*     */     } 
/* 233 */     return toggles;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\ShadersBuiltIn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */