/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import shadersmod.client.Shaders;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CrashReporter
/*     */ {
/*     */   public static void onCrashReport(b crashReport, k category) {
/*     */     try {
/*  25 */       bbj settings = Config.getGameSettings();
/*  26 */       if (settings == null)
/*     */         return; 
/*  28 */       if (!settings.r) {
/*     */         return;
/*     */       }
/*     */       
/*  32 */       Throwable cause = crashReport.b();
/*  33 */       if (cause == null)
/*     */         return; 
/*  35 */       if (cause.getClass() == Throwable.class)
/*     */         return; 
/*  37 */       if (cause.getClass().getName().contains(".fml.client.SplashProgress")) {
/*     */         return;
/*     */       }
/*  40 */       extendCrashReport(category);
/*     */       
/*  42 */       String url = "http://optifine.net/crashReport";
/*  43 */       String reportStr = makeReport(crashReport);
/*  44 */       byte[] content = reportStr.getBytes("ASCII");
/*  45 */       IFileUploadListener listener = new IFileUploadListener()
/*     */         {
/*     */           public void fileUploadFinished(String url, byte[] content, Throwable exception) {}
/*     */         };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  54 */       Map<Object, Object> headers = new HashMap<Object, Object>();
/*  55 */       headers.put("OF-Version", Config.getVersion());
/*  56 */       headers.put("OF-Summary", makeSummary(crashReport));
/*     */       
/*  58 */       FileUploadThread fut = new FileUploadThread(url, headers, content, listener);
/*  59 */       fut.setPriority(10);
/*  60 */       fut.start();
/*     */       
/*  62 */       Thread.sleep(1000L);
/*     */     }
/*  64 */     catch (Exception e) {
/*     */       
/*  66 */       Config.dbg(e.getClass().getName() + ": " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String makeReport(b crashReport) {
/*  76 */     StringBuffer sb = new StringBuffer();
/*  77 */     sb.append("OptiFineVersion: " + Config.getVersion() + "\n");
/*  78 */     sb.append("Summary: " + makeSummary(crashReport) + "\n");
/*  79 */     sb.append("\n");
/*  80 */     sb.append(crashReport.e());
/*  81 */     sb.append("\n");
/*     */     
/*  83 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String makeSummary(b crashReport) {
/*  92 */     Throwable t = crashReport.b();
/*  93 */     if (t == null) {
/*  94 */       return "Unknown";
/*     */     }
/*  96 */     StackTraceElement[] traces = t.getStackTrace();
/*  97 */     String firstTrace = "unknown";
/*  98 */     if (traces.length > 0) {
/*  99 */       firstTrace = traces[0].toString().trim();
/*     */     }
/* 101 */     String sum = t.getClass().getName() + ": " + t.getMessage() + " (" + crashReport.a() + ")" + " [" + firstTrace + "]";
/*     */     
/* 103 */     return sum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void extendCrashReport(k cat) {
/* 111 */     cat.a("OptiFine Version", Config.getVersion());
/*     */     
/* 113 */     if (Config.getGameSettings() != null) {
/*     */       
/* 115 */       cat.a("Render Distance Chunks", "" + Config.getChunkViewDistance());
/* 116 */       cat.a("Mipmaps", "" + Config.getMipmapLevels());
/* 117 */       cat.a("Anisotropic Filtering", "" + Config.getAnisotropicFilterLevel());
/* 118 */       cat.a("Antialiasing", "" + Config.getAntialiasingLevel());
/* 119 */       cat.a("Multitexture", "" + Config.isMultiTexture());
/*     */     } 
/* 121 */     cat.a("Shaders", "" + Shaders.getShaderPackName());
/* 122 */     cat.a("OpenGlVersion", "" + Config.openGlVersion);
/* 123 */     cat.a("OpenGlRenderer", "" + Config.openGlRenderer);
/* 124 */     cat.a("OpenGlVendor", "" + Config.openGlVendor);
/* 125 */     cat.a("CpuCount", "" + Config.getAvailableProcessors());
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\CrashReporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */