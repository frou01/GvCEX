/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import shadersmod.client.GuiButtonShaderOption;
/*     */ import shadersmod.client.ShaderOption;
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
/*     */ 
/*     */ public class TooltipProviderShaderOptions
/*     */   extends TooltipProviderOptions
/*     */ {
/*     */   public String[] getTooltipLines(bcb btn, int width) {
/*  29 */     if (!(btn instanceof GuiButtonShaderOption)) {
/*  30 */       return null;
/*     */     }
/*  32 */     GuiButtonShaderOption btnSo = (GuiButtonShaderOption)btn;
/*  33 */     ShaderOption so = btnSo.getShaderOption();
/*  34 */     String[] lines = makeTooltipLines(so, width);
/*     */     
/*  36 */     return lines;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] makeTooltipLines(ShaderOption so, int width) {
/*  44 */     if (so instanceof shadersmod.client.ShaderOptionProfile) {
/*  45 */       return null;
/*     */     }
/*  47 */     String name = so.getNameText();
/*     */     
/*  49 */     String desc = Config.normalize(so.getDescriptionText()).trim();
/*  50 */     String[] descs = splitDescription(desc);
/*     */     
/*  52 */     bbj settings = Config.getGameSettings();
/*     */     
/*  54 */     String id = null;
/*  55 */     if (!name.equals(so.getName()) && settings.v) {
/*  56 */       id = "§8" + Lang.get("of.general.id") + ": " + so.getName();
/*     */     }
/*  58 */     String source = null;
/*  59 */     if (so.getPaths() != null && settings.v) {
/*  60 */       source = "§8" + Lang.get("of.general.from") + ": " + Config.arrayToString((Object[])so.getPaths());
/*     */     }
/*  62 */     String def = null;
/*  63 */     if (so.getValueDefault() != null && settings.v) {
/*     */       
/*  65 */       String defVal = so.isEnabled() ? so.getValueText(so.getValueDefault()) : Lang.get("of.general.ambiguous");
/*  66 */       def = "§8" + Lang.getDefault() + ": " + defVal;
/*     */     } 
/*     */     
/*  69 */     List<String> list = new ArrayList<String>();
/*  70 */     list.add(name);
/*  71 */     list.addAll(Arrays.asList(descs));
/*  72 */     if (id != null)
/*  73 */       list.add(id); 
/*  74 */     if (source != null) {
/*  75 */       list.add(source);
/*     */     }
/*  77 */     if (def != null) {
/*  78 */       list.add(def);
/*     */     }
/*  80 */     String[] lines = makeTooltipLines(width, list);
/*     */     
/*  82 */     return lines;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] splitDescription(String desc) {
/*  90 */     if (desc.length() <= 0) {
/*  91 */       return new String[0];
/*     */     }
/*  93 */     desc = StrUtils.removePrefix(desc, "//");
/*     */     
/*  95 */     String[] descs = desc.split("\\. ");
/*     */     
/*  97 */     for (int i = 0; i < descs.length; i++) {
/*     */       
/*  99 */       descs[i] = "- " + descs[i].trim();
/* 100 */       descs[i] = StrUtils.removeSuffix(descs[i], ".");
/*     */     } 
/*     */     
/* 103 */     return descs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] makeTooltipLines(int width, List<String> args) {
/* 110 */     bbu fr = (Config.getMinecraft()).l;
/* 111 */     List<String> list = new ArrayList<String>();
/* 112 */     for (int i = 0; i < args.size(); i++) {
/*     */       
/* 114 */       String arg = args.get(i);
/* 115 */       if (arg != null && arg.length() > 0) {
/*     */         
/* 117 */         List<String> parts = fr.c(arg, width);
/* 118 */         for (Iterator<String> it = parts.iterator(); it.hasNext(); ) {
/*     */           
/* 120 */           String part = it.next();
/* 121 */           list.add(part);
/*     */         } 
/*     */       } 
/*     */     } 
/* 125 */     String[] lines = list.<String>toArray(new String[list.size()]);
/*     */     
/* 127 */     return lines;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\TooltipProviderShaderOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */