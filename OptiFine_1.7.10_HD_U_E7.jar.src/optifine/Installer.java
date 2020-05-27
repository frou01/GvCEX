/*     */ package optifine;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Locale;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import optifine.json.JSONArray;
/*     */ import optifine.json.JSONObject;
/*     */ import optifine.json.JSONParser;
/*     */ import optifine.json.JSONWriter;
/*     */ import optifine.json.ParseException;
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
/*     */ public class Installer
/*     */ {
/*     */   public static void main(String[] args) {
/*     */     try {
/*  41 */       File dirMc = Utils.getWorkingDirectory();
/*     */       
/*  43 */       doInstall(dirMc);
/*     */     }
/*  45 */     catch (Exception e) {
/*     */ 
/*     */       
/*  48 */       String msg = e.getMessage();
/*  49 */       if (msg != null && msg.equals("QUIET")) {
/*     */         return;
/*     */       }
/*  52 */       e.printStackTrace();
/*  53 */       String str = Utils.getExceptionStackTrace(e);
/*  54 */       str = str.replace("\t", "  ");
/*  55 */       JTextArea textArea = new JTextArea(str);
/*  56 */       textArea.setEditable(false);
/*  57 */       Font f = textArea.getFont();
/*  58 */       Font f2 = new Font("Monospaced", f.getStyle(), f.getSize());
/*  59 */       textArea.setFont(f2);
/*  60 */       JScrollPane scrollPane = new JScrollPane(textArea);
/*  61 */       scrollPane.setPreferredSize(new Dimension(600, 400));
/*  62 */       JOptionPane.showMessageDialog((Component)null, scrollPane, "Error", 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void doInstall(File dirMc) throws Exception {
/*  71 */     Utils.dbg("Dir minecraft: " + dirMc);
/*     */     
/*  73 */     File dirMcLib = new File(dirMc, "libraries");
/*  74 */     Utils.dbg("Dir libraries: " + dirMcLib);
/*     */     
/*  76 */     File dirMcVers = new File(dirMc, "versions");
/*  77 */     Utils.dbg("Dir versions: " + dirMcVers);
/*     */     
/*  79 */     String ofVer = getOptiFineVersion();
/*  80 */     Utils.dbg("OptiFine Version: " + ofVer);
/*  81 */     String[] ofVers = Utils.tokenize(ofVer, "_");
/*     */     
/*  83 */     String mcVer = ofVers[1];
/*  84 */     Utils.dbg("Minecraft Version: " + mcVer);
/*     */     
/*  86 */     String ofEd = getOptiFineEdition(ofVers);
/*  87 */     Utils.dbg("OptiFine Edition: " + ofEd);
/*     */     
/*  89 */     String mcVerOf = String.valueOf(mcVer) + "-OptiFine_" + ofEd;
/*  90 */     Utils.dbg("Minecraft_OptiFine Version: " + mcVerOf);
/*     */     
/*  92 */     copyMinecraftVersion(mcVer, mcVerOf, dirMcVers);
/*     */     
/*  94 */     installOptiFineLibrary(mcVer, ofEd, dirMcLib, false);
/*     */     
/*  96 */     updateJson(dirMcVers, mcVerOf, dirMcLib, mcVer, ofEd);
/*     */     
/*  98 */     updateLauncherJson(dirMc, mcVerOf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean doExtract(File dirMc) throws Exception {
/* 106 */     Utils.dbg("Dir minecraft: " + dirMc);
/*     */     
/* 108 */     File dirMcLib = new File(dirMc, "libraries");
/* 109 */     Utils.dbg("Dir libraries: " + dirMcLib);
/*     */     
/* 111 */     File dirMcVers = new File(dirMc, "versions");
/* 112 */     Utils.dbg("Dir versions: " + dirMcVers);
/*     */     
/* 114 */     String ofVer = getOptiFineVersion();
/* 115 */     Utils.dbg("OptiFine Version: " + ofVer);
/* 116 */     String[] ofVers = Utils.tokenize(ofVer, "_");
/*     */     
/* 118 */     String mcVer = ofVers[1];
/* 119 */     Utils.dbg("Minecraft Version: " + mcVer);
/*     */     
/* 121 */     String ofEd = getOptiFineEdition(ofVers);
/* 122 */     Utils.dbg("OptiFine Edition: " + ofEd);
/*     */     
/* 124 */     String mcVerOf = String.valueOf(mcVer) + "-OptiFine_" + ofEd;
/* 125 */     Utils.dbg("Minecraft_OptiFine Version: " + mcVerOf);
/*     */     
/* 127 */     return installOptiFineLibrary(mcVer, ofEd, dirMcLib, true);
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
/*     */   private static void updateLauncherJson(File dirMc, String mcVerOf) throws IOException, ParseException {
/* 139 */     File fileJson = new File(dirMc, "launcher_profiles.json");
/*     */     
/* 141 */     if (!fileJson.exists() || !fileJson.isFile()) {
/*     */       
/* 143 */       Utils.showErrorMessage("File not found: " + fileJson);
/* 144 */       throw new RuntimeException("QUIET");
/*     */     } 
/*     */     
/* 147 */     String json = Utils.readFile(fileJson, "UTF-8");
/* 148 */     JSONParser jp = new JSONParser();
/*     */     
/* 150 */     JSONObject root = (JSONObject)jp.parse(json);
/*     */     
/* 152 */     JSONObject profiles = (JSONObject)root.get("profiles");
/*     */     
/* 154 */     JSONObject prof = (JSONObject)profiles.get("OptiFine");
/* 155 */     if (prof == null) {
/*     */ 
/*     */       
/* 158 */       prof = new JSONObject();
/* 159 */       prof.put("name", "OptiFine");
/*     */       
/* 161 */       profiles.put("OptiFine", prof);
/*     */     } 
/*     */     
/* 164 */     prof.put("lastVersionId", mcVerOf);
/*     */     
/* 166 */     root.put("selectedProfile", "OptiFine");
/*     */     
/* 168 */     FileOutputStream fosJson = new FileOutputStream(fileJson);
/* 169 */     OutputStreamWriter oswJson = new OutputStreamWriter(fosJson, "UTF-8");
/* 170 */     JSONWriter jw = new JSONWriter(oswJson);
/* 171 */     jw.writeObject(root);
/*     */     
/* 173 */     oswJson.flush();
/* 174 */     oswJson.close();
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
/*     */ 
/*     */   
/*     */   private static void updateJson(File dirMcVers, String mcVerOf, File dirMcLib, String mcVer, String ofEd) throws IOException, ParseException {
/* 189 */     File dirMcVersOf = new File(dirMcVers, mcVerOf);
/*     */     
/* 191 */     File fileJson = new File(dirMcVersOf, String.valueOf(mcVerOf) + ".json");
/* 192 */     String json = Utils.readFile(fileJson, "UTF-8");
/* 193 */     JSONParser jp = new JSONParser();
/*     */     
/* 195 */     JSONObject root = (JSONObject)jp.parse(json);
/* 196 */     root.put("id", mcVerOf);
/*     */     
/* 198 */     JSONArray libs = (JSONArray)root.get("libraries");
/*     */     
/* 200 */     root.put("inheritsFrom", mcVer);
/* 201 */     libs = new JSONArray();
/* 202 */     root.put("libraries", libs);
/*     */     
/* 204 */     String mainClass = (String)root.get("mainClass");
/*     */     
/* 206 */     if (!mainClass.startsWith("net.minecraft.launchwrapper.")) {
/*     */ 
/*     */       
/* 209 */       mainClass = "net.minecraft.launchwrapper.Launch";
/* 210 */       root.put("mainClass", mainClass);
/*     */       
/* 212 */       String args = (String)root.get("minecraftArguments");
/* 213 */       args = String.valueOf(args) + "  --tweakClass optifine.OptiFineTweaker";
/* 214 */       root.put("minecraftArguments", args);
/*     */       
/* 216 */       JSONObject libLw = new JSONObject();
/* 217 */       libLw.put("name", "net.minecraft:launchwrapper:1.12");
/* 218 */       libs.add(0, libLw);
/*     */     } 
/*     */     
/* 221 */     JSONObject libOf = new JSONObject();
/* 222 */     libOf.put("name", "optifine:OptiFine:" + mcVer + "_" + ofEd);
/* 223 */     libs.add(0, libOf);
/*     */     
/* 225 */     FileOutputStream fosJson = new FileOutputStream(fileJson);
/* 226 */     OutputStreamWriter oswJson = new OutputStreamWriter(fosJson, "UTF-8");
/* 227 */     JSONWriter jw = new JSONWriter(oswJson);
/* 228 */     jw.writeObject(root);
/*     */     
/* 230 */     oswJson.flush();
/* 231 */     oswJson.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getOptiFineEdition(String[] ofVers) {
/* 240 */     if (ofVers.length <= 2) {
/* 241 */       return "";
/*     */     }
/* 243 */     String ofEd = "";
/* 244 */     for (int i = 2; i < ofVers.length; i++) {
/*     */       
/* 246 */       if (i > 2) {
/* 247 */         ofEd = String.valueOf(ofEd) + "_";
/*     */       }
/* 249 */       ofEd = String.valueOf(ofEd) + ofVers[i];
/*     */     } 
/*     */     
/* 252 */     return ofEd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean installOptiFineLibrary(String mcVer, String ofEd, File dirMcLib, boolean selectTarget) throws Exception {
/* 262 */     File fileSrc = getOptiFineZipFile();
/*     */     
/* 264 */     File dirDest = new File(dirMcLib, "optifine/OptiFine/" + mcVer + "_" + ofEd);
/* 265 */     File fileDest = new File(dirDest, "OptiFine-" + mcVer + "_" + ofEd + ".jar");
/*     */     
/* 267 */     if (selectTarget) {
/*     */ 
/*     */       
/* 270 */       fileDest = new File(fileSrc.getParentFile(), "OptiFine_" + mcVer + "_" + ofEd + "_MOD.jar");
/*     */       
/* 272 */       JFileChooser jfc = new JFileChooser(fileDest.getParentFile());
/* 273 */       jfc.setSelectedFile(fileDest);
/* 274 */       int ret = jfc.showSaveDialog(null);
/* 275 */       if (ret != 0) {
/* 276 */         return false;
/*     */       }
/* 278 */       fileDest = jfc.getSelectedFile();
/* 279 */       if (fileDest.exists()) {
/*     */         
/* 281 */         JOptionPane.setDefaultLocale(Locale.ENGLISH);
/* 282 */         int ret2 = JOptionPane.showConfirmDialog((Component)null, "The file \"" + fileDest.getName() + "\" already exists.\nDo you want to overwrite it?", "Save", 1);
/* 283 */         if (ret2 != 0) {
/* 284 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 288 */     if (fileDest.equals(fileSrc)) {
/*     */       
/* 290 */       JOptionPane.showMessageDialog((Component)null, "Source and target file are the same.", "Save", 0);
/* 291 */       return false;
/*     */     } 
/*     */     
/* 294 */     Utils.dbg("Source: " + fileSrc);
/* 295 */     Utils.dbg("Dest: " + fileDest);
/*     */     
/* 297 */     File dirMc = dirMcLib.getParentFile();
/* 298 */     File fileBase = new File(dirMc, "versions/" + mcVer + "/" + mcVer + ".jar");
/* 299 */     if (!fileBase.exists()) {
/*     */       
/* 301 */       showMessageVersionNotFound(mcVer);
/* 302 */       throw new RuntimeException("QUIET");
/*     */     } 
/*     */     
/* 305 */     if (fileDest.getParentFile() != null) {
/* 306 */       fileDest.getParentFile().mkdirs();
/*     */     }
/* 308 */     Patcher.process(fileBase, fileSrc, fileDest);
/*     */     
/* 310 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static File getOptiFineZipFile() throws Exception {
/* 320 */     URL url = Installer.class.getProtectionDomain().getCodeSource().getLocation();
/* 321 */     Utils.dbg("URL: " + url);
/* 322 */     URI uri = url.toURI();
/* 323 */     File fileZip = new File(uri);
/* 324 */     return fileZip;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isPatchFile() throws Exception {
/* 333 */     File fileZip = getOptiFineZipFile();
/* 334 */     ZipFile zipFile = new ZipFile(fileZip);
/*     */     
/*     */     try {
/* 337 */       Enumeration<ZipEntry> entries = (Enumeration)zipFile.entries();
/* 338 */       while (entries.hasMoreElements()) {
/*     */         
/* 340 */         ZipEntry zipEntry = entries.nextElement();
/* 341 */         if (zipEntry.getName().startsWith("patch/")) {
/* 342 */           return true;
/*     */         }
/*     */       } 
/* 345 */       return false;
/*     */     }
/*     */     finally {
/*     */       
/* 349 */       zipFile.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void copyMinecraftVersion(String mcVer, String mcVerOf, File dirMcVer) throws IOException {
/* 360 */     File dirVerMc = new File(dirMcVer, mcVer);
/* 361 */     if (!dirVerMc.exists()) {
/*     */       
/* 363 */       showMessageVersionNotFound(mcVer);
/* 364 */       throw new RuntimeException("QUIET");
/*     */     } 
/*     */     
/* 367 */     File dirVerMcOf = new File(dirMcVer, mcVerOf);
/* 368 */     dirVerMcOf.mkdirs();
/*     */     
/* 370 */     Utils.dbg("Dir version MC: " + dirVerMc);
/* 371 */     Utils.dbg("Dir version MC-OF: " + dirVerMcOf);
/*     */     
/* 373 */     File fileJarMc = new File(dirVerMc, String.valueOf(mcVer) + ".jar");
/* 374 */     File fileJarMcOf = new File(dirVerMcOf, String.valueOf(mcVerOf) + ".jar");
/*     */     
/* 376 */     if (!fileJarMc.exists()) {
/*     */       
/* 378 */       showMessageVersionNotFound(mcVer);
/* 379 */       throw new RuntimeException("QUIET");
/*     */     } 
/* 381 */     Utils.copyFile(fileJarMc, fileJarMcOf);
/*     */     
/* 383 */     File fileJsonMc = new File(dirVerMc, String.valueOf(mcVer) + ".json");
/* 384 */     File fileJsonMcOf = new File(dirVerMcOf, String.valueOf(mcVerOf) + ".json");
/* 385 */     Utils.copyFile(fileJsonMc, fileJsonMcOf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void showMessageVersionNotFound(String mcVer) {
/* 393 */     Utils.showErrorMessage("Minecraft version " + mcVer + " not found.\nYou need to start the version " + mcVer + " manually once.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getOptiFineVersion() throws IOException {
/* 401 */     InputStream in = Installer.class.getResourceAsStream("/Config.class");
/*     */     
/* 403 */     if (in == null) {
/* 404 */       in = Installer.class.getResourceAsStream("/VersionThread.class");
/*     */     }
/* 406 */     return getOptiFineVersion(in);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getOptiFineVersion(ZipFile zipFile) throws IOException {
/* 413 */     ZipEntry zipEntry = zipFile.getEntry("Config.class");
/*     */     
/* 415 */     if (zipEntry == null) {
/* 416 */       zipEntry = zipFile.getEntry("VersionThread.class");
/*     */     }
/* 418 */     if (zipEntry == null) {
/* 419 */       return null;
/*     */     }
/* 421 */     InputStream in = zipFile.getInputStream(zipEntry);
/*     */     
/* 423 */     String ofVer = getOptiFineVersion(in);
/* 424 */     in.close();
/*     */     
/* 426 */     return ofVer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getOptiFineVersion(InputStream in) throws IOException {
/* 434 */     byte[] bytes = Utils.readAll(in);
/* 435 */     byte[] pattern = "OptiFine_".getBytes("ASCII");
/* 436 */     int pos = Utils.find(bytes, pattern);
/* 437 */     if (pos < 0) {
/* 438 */       return null;
/*     */     }
/* 440 */     int startPos = pos;
/* 441 */     for (pos = startPos; pos < bytes.length; ) {
/*     */       
/* 443 */       byte b = bytes[pos];
/* 444 */       if (b >= 32 && b <= 122) {
/*     */         pos++; continue;
/*     */       } 
/*     */       break;
/*     */     } 
/* 449 */     int endPos = pos;
/*     */     
/* 451 */     String ver = new String(bytes, startPos, endPos - startPos, "ASCII");
/*     */     
/* 453 */     return ver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getMinecraftVersionFromOfVersion(String ofVer) {
/* 463 */     if (ofVer == null) {
/* 464 */       return null;
/*     */     }
/* 466 */     String[] ofVers = Utils.tokenize(ofVer, "_");
/* 467 */     if (ofVers.length < 2) {
/* 468 */       return null;
/*     */     }
/* 470 */     String mcVer = ofVers[1];
/*     */     
/* 472 */     return mcVer;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\Installer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */