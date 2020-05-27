/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.lang3.StringEscapeUtils;
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
/*     */ 
/*     */ 
/*     */ public class NbtTagValue
/*     */ {
/*  28 */   private String[] parents = null;
/*  29 */   private String name = null;
/*     */   private boolean negative = false;
/*  31 */   private int type = 0;
/*  32 */   private String value = null;
/*  33 */   private int valueFormat = 0;
/*     */   
/*     */   private static final int TYPE_TEXT = 0;
/*     */   
/*     */   private static final int TYPE_PATTERN = 1;
/*     */   
/*     */   private static final int TYPE_IPATTERN = 2;
/*     */   
/*     */   private static final int TYPE_REGEX = 3;
/*     */   private static final int TYPE_IREGEX = 4;
/*     */   private static final String PREFIX_PATTERN = "pattern:";
/*     */   private static final String PREFIX_IPATTERN = "ipattern:";
/*     */   private static final String PREFIX_REGEX = "regex:";
/*     */   private static final String PREFIX_IREGEX = "iregex:";
/*     */   private static final int FORMAT_DEFAULT = 0;
/*     */   private static final int FORMAT_HEX_COLOR = 1;
/*     */   private static final String PREFIX_HEX_COLOR = "#";
/*  50 */   private static final Pattern PATTERN_HEX_COLOR = Pattern.compile("^#[0-9a-f]{6}+$");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NbtTagValue(String tag, String value) {
/*  59 */     String[] names = Config.tokenize(tag, ".");
/*  60 */     this.parents = Arrays.<String>copyOfRange(names, 0, names.length - 1);
/*  61 */     this.name = names[names.length - 1];
/*     */     
/*  63 */     if (value.startsWith("!")) {
/*     */       
/*  65 */       this.negative = true;
/*  66 */       value = value.substring(1);
/*     */     } 
/*     */     
/*  69 */     if (value.startsWith("pattern:")) {
/*     */       
/*  71 */       this.type = 1;
/*  72 */       value = value.substring("pattern:".length());
/*     */     }
/*  74 */     else if (value.startsWith("ipattern:")) {
/*     */       
/*  76 */       this.type = 2;
/*  77 */       value = value.substring("ipattern:".length()).toLowerCase();
/*     */     }
/*  79 */     else if (value.startsWith("regex:")) {
/*     */       
/*  81 */       this.type = 3;
/*  82 */       value = value.substring("regex:".length());
/*     */     }
/*  84 */     else if (value.startsWith("iregex:")) {
/*     */       
/*  86 */       this.type = 4;
/*  87 */       value = value.substring("iregex:".length()).toLowerCase();
/*     */     }
/*     */     else {
/*     */       
/*  91 */       this.type = 0;
/*     */     } 
/*     */     
/*  94 */     value = StringEscapeUtils.unescapeJava(value);
/*     */     
/*  96 */     if (this.type == 0)
/*     */     {
/*  98 */       if (PATTERN_HEX_COLOR.matcher(value).matches()) {
/*  99 */         this.valueFormat = 1;
/*     */       }
/*     */     }
/* 102 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(dh nbt) {
/* 113 */     if (this.negative) {
/* 114 */       return !matchesCompound(nbt);
/*     */     }
/* 116 */     return matchesCompound(nbt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesCompound(dh nbt) {
/* 126 */     if (nbt == null) {
/* 127 */       return false;
/*     */     }
/* 129 */     dh dh1 = nbt;
/* 130 */     for (int i = 0; i < this.parents.length; i++) {
/*     */       
/* 132 */       String tag = this.parents[i];
/*     */       
/* 134 */       dy = getChildTag((dy)dh1, tag);
/*     */       
/* 136 */       if (dy == null) {
/* 137 */         return false;
/*     */       }
/*     */     } 
/* 140 */     if (this.name.equals("*")) {
/* 141 */       return matchesAnyChild(dy);
/*     */     }
/* 143 */     dy dy = getChildTag(dy, this.name);
/*     */     
/* 145 */     if (dy == null) {
/* 146 */       return false;
/*     */     }
/* 148 */     if (matchesBase(dy)) {
/* 149 */       return true;
/*     */     }
/* 151 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesAnyChild(dy tagBase) {
/* 161 */     if (tagBase instanceof dh) {
/*     */       
/* 163 */       dh tagCompound = (dh)tagBase;
/* 164 */       Set nbtKeySet = tagCompound.c();
/* 165 */       for (Iterator<String> it = nbtKeySet.iterator(); it.hasNext(); ) {
/*     */         
/* 167 */         String key = it.next();
/* 168 */         dy nbtBase = tagCompound.a(key);
/* 169 */         if (matchesBase(nbtBase)) {
/* 170 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 174 */     if (tagBase instanceof dq) {
/*     */       
/* 176 */       dq tagList = (dq)tagBase;
/* 177 */       int count = tagList.c();
/* 178 */       for (int i = 0; i < count; i++) {
/*     */         
/* 180 */         dh dh = tagList.b(i);
/* 181 */         if (matchesBase((dy)dh)) {
/* 182 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 186 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static dy getChildTag(dy tagBase, String tag) {
/* 196 */     if (tagBase instanceof dh) {
/*     */       
/* 198 */       dh tagCompound = (dh)tagBase;
/* 199 */       return tagCompound.a(tag);
/*     */     } 
/*     */     
/* 202 */     if (tagBase instanceof dq) {
/*     */       
/* 204 */       dq tagList = (dq)tagBase;
/*     */       
/* 206 */       if (tag.equals("count")) {
/* 207 */         return (dy)new dp(tagList.c());
/*     */       }
/* 209 */       int index = Config.parseInt(tag, -1);
/* 210 */       if (index < 0) {
/* 211 */         return null;
/*     */       }
/* 213 */       return (dy)tagList.b(index);
/*     */     } 
/*     */     
/* 216 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesBase(dy nbtBase) {
/* 224 */     if (nbtBase == null)
/* 225 */       return false; 
/* 226 */     String nbtValue = getNbtString(nbtBase, this.valueFormat);
/*     */     
/* 228 */     return matchesValue(nbtValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesValue(String nbtValue) {
/* 237 */     if (nbtValue == null) {
/* 238 */       return false;
/*     */     }
/* 240 */     switch (this.type) {
/*     */       
/*     */       case 0:
/* 243 */         return nbtValue.equals(this.value);
/*     */       case 1:
/* 245 */         return matchesPattern(nbtValue, this.value);
/*     */       case 2:
/* 247 */         return matchesPattern(nbtValue.toLowerCase(), this.value);
/*     */       case 3:
/* 249 */         return matchesRegex(nbtValue, this.value);
/*     */       case 4:
/* 251 */         return matchesRegex(nbtValue.toLowerCase(), this.value);
/*     */     } 
/*     */     
/* 254 */     throw new IllegalArgumentException("Unknown NbtTagValue type: " + this.type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesPattern(String str, String pattern) {
/* 261 */     return StrUtils.equalsMask(str, pattern, '*', '?');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesRegex(String str, String regex) {
/* 269 */     return str.matches(regex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getNbtString(dy nbtBase, int format) {
/* 278 */     if (nbtBase == null) {
/* 279 */       return null;
/*     */     }
/* 281 */     if (nbtBase instanceof dx) {
/*     */       
/* 283 */       dx nbtString = (dx)nbtBase;
/* 284 */       return nbtString.a_();
/*     */     } 
/* 286 */     if (nbtBase instanceof dp) {
/*     */       
/* 288 */       dp i = (dp)nbtBase;
/*     */       
/* 290 */       if (format == 1) {
/* 291 */         return "#" + StrUtils.fillLeft(Integer.toHexString(i.d()), 6, '0');
/*     */       }
/* 293 */       return Integer.toString(i.d());
/*     */     } 
/* 295 */     if (nbtBase instanceof dg) {
/*     */       
/* 297 */       dg b = (dg)nbtBase;
/* 298 */       return Byte.toString(b.f());
/*     */     } 
/* 300 */     if (nbtBase instanceof dw) {
/*     */       
/* 302 */       dw s = (dw)nbtBase;
/* 303 */       return Short.toString(s.e());
/*     */     } 
/* 305 */     if (nbtBase instanceof dr) {
/*     */       
/* 307 */       dr l = (dr)nbtBase;
/* 308 */       return Long.toString(l.c());
/*     */     } 
/* 310 */     if (nbtBase instanceof dm) {
/*     */       
/* 312 */       dm f = (dm)nbtBase;
/* 313 */       return Float.toString(f.h());
/*     */     } 
/* 315 */     if (nbtBase instanceof dk) {
/*     */       
/* 317 */       dk d = (dk)nbtBase;
/* 318 */       return Double.toString(d.g());
/*     */     } 
/*     */     
/* 321 */     return nbtBase.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 329 */     StringBuffer sb = new StringBuffer();
/* 330 */     for (int i = 0; i < this.parents.length; i++) {
/*     */       
/* 332 */       String parent = this.parents[i];
/* 333 */       if (i > 0) {
/* 334 */         sb.append(".");
/*     */       }
/* 336 */       sb.append(parent);
/*     */     } 
/*     */     
/* 339 */     if (sb.length() > 0) {
/* 340 */       sb.append(".");
/*     */     }
/* 342 */     sb.append(this.name);
/* 343 */     sb.append(" = ");
/* 344 */     sb.append(this.value);
/*     */     
/* 346 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\NbtTagValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */