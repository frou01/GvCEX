/*     */ package shadersmod.client;
/*     */ 
/*     */ import Lang;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShaderOptionProfile
/*     */   extends ShaderOption
/*     */ {
/*  16 */   private ShaderProfile[] profiles = null;
/*  17 */   private ShaderOption[] options = null;
/*     */ 
/*     */   
/*     */   private static final String NAME_PROFILE = "<profile>";
/*     */   
/*     */   private static final String VALUE_CUSTOM = "<custom>";
/*     */ 
/*     */   
/*     */   public ShaderOptionProfile(ShaderProfile[] profiles, ShaderOption[] options) {
/*  26 */     super("<profile>", "", detectProfileName(profiles, options), getProfileNames(profiles), detectProfileName(profiles, options, true), null);
/*  27 */     this.profiles = profiles;
/*  28 */     this.options = options;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void nextValue() {
/*  37 */     super.nextValue();
/*     */     
/*  39 */     if (getValue().equals("<custom>")) {
/*  40 */       super.nextValue();
/*     */     }
/*  42 */     applyProfileOptions();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateProfile() {
/*  48 */     ShaderProfile prof = getProfile(getValue());
/*  49 */     if (prof != null)
/*     */     {
/*     */       
/*  52 */       if (ShaderUtils.matchProfile(prof, this.options, false)) {
/*     */         return;
/*     */       }
/*     */     }
/*  56 */     String val = detectProfileName(this.profiles, this.options);
/*  57 */     setValue(val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void applyProfileOptions() {
/*  65 */     ShaderProfile prof = getProfile(getValue());
/*  66 */     if (prof == null)
/*     */       return; 
/*  68 */     String[] opts = prof.getOptions();
/*  69 */     for (int i = 0; i < opts.length; i++) {
/*     */       
/*  71 */       String name = opts[i];
/*  72 */       ShaderOption so = getOption(name);
/*  73 */       if (so != null) {
/*     */ 
/*     */         
/*  76 */         String val = prof.getValue(name);
/*  77 */         so.setValue(val);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ShaderOption getOption(String name) {
/*  87 */     for (int i = 0; i < this.options.length; i++) {
/*     */       
/*  89 */       ShaderOption so = this.options[i];
/*  90 */       if (so.getName().equals(name)) {
/*  91 */         return so;
/*     */       }
/*     */     } 
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ShaderProfile getProfile(String name) {
/* 103 */     for (int i = 0; i < this.profiles.length; i++) {
/*     */       
/* 105 */       ShaderProfile prof = this.profiles[i];
/* 106 */       if (prof.getName().equals(name)) {
/* 107 */         return prof;
/*     */       }
/*     */     } 
/* 110 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNameText() {
/* 119 */     return Lang.get("of.shaders.profile");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValueText(String val) {
/* 129 */     if (val.equals("<custom>")) {
/* 130 */       return Lang.get("of.general.custom", "<custom>");
/*     */     }
/* 132 */     return Shaders.translate("profile." + val, val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValueColor(String val) {
/* 141 */     if (val.equals("<custom>")) {
/* 142 */       return "§c";
/*     */     }
/* 144 */     return "§a";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String detectProfileName(ShaderProfile[] profs, ShaderOption[] opts) {
/* 152 */     return detectProfileName(profs, opts, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String detectProfileName(ShaderProfile[] profs, ShaderOption[] opts, boolean def) {
/* 162 */     ShaderProfile prof = ShaderUtils.detectProfile(profs, opts, def);
/*     */     
/* 164 */     if (prof == null) {
/* 165 */       return "<custom>";
/*     */     }
/* 167 */     return prof.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] getProfileNames(ShaderProfile[] profs) {
/* 176 */     List<String> list = new ArrayList<String>();
/*     */     
/* 178 */     for (int i = 0; i < profs.length; i++) {
/*     */       
/* 180 */       ShaderProfile prof = profs[i];
/* 181 */       list.add(prof.getName());
/*     */     } 
/*     */     
/* 184 */     list.add("<custom>");
/*     */     
/* 186 */     String[] names = list.<String>toArray(new String[list.size()]);
/*     */     
/* 188 */     return names;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\ShaderOptionProfile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */