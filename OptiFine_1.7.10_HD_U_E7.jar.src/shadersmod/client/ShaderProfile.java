/*     */ package shadersmod.client;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShaderProfile
/*     */ {
/*  17 */   private String name = null;
/*  18 */   private Map<String, String> mapOptionValues = new LinkedHashMap<String, String>();
/*  19 */   private Set<String> disabledPrograms = new LinkedHashSet<String>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShaderProfile(String name) {
/*  25 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  32 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOptionValue(String option, String value) {
/*  38 */     this.mapOptionValues.put(option, value);
/*     */   }
/*     */   
/*     */   public void addOptionValues(ShaderProfile prof) {
/*  42 */     if (prof == null) {
/*     */       return;
/*     */     }
/*  45 */     this.mapOptionValues.putAll(prof.mapOptionValues);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyOptionValues(ShaderOption[] options) {
/*  52 */     for (int i = 0; i < options.length; i++) {
/*     */       
/*  54 */       ShaderOption so = options[i];
/*  55 */       String key = so.getName();
/*  56 */       String val = this.mapOptionValues.get(key);
/*  57 */       if (val != null)
/*     */       {
/*     */         
/*  60 */         so.setValue(val);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getOptions() {
/*  68 */     Set<String> keys = this.mapOptionValues.keySet();
/*     */     
/*  70 */     String[] opts = keys.<String>toArray(new String[keys.size()]);
/*     */     
/*  72 */     return opts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue(String key) {
/*  80 */     return this.mapOptionValues.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDisabledProgram(String program) {
/*  87 */     this.disabledPrograms.add(program);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeDisabledProgram(String program) {
/*  94 */     this.disabledPrograms.remove(program);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<String> getDisabledPrograms() {
/* 101 */     return new LinkedHashSet<String>(this.disabledPrograms);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDisabledPrograms(Collection<String> programs) {
/* 108 */     this.disabledPrograms.addAll(programs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isProgramDisabled(String program) {
/* 116 */     return this.disabledPrograms.contains(program);
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\ShaderProfile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */