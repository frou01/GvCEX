/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
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
/*     */ public class StrUtils
/*     */ {
/*     */   public static boolean equalsMask(String str, String mask, char wildChar, char wildCharSingle) {
/*  18 */     if (mask == null || str == null) {
/*  19 */       return (mask == str);
/*     */     }
/*  21 */     if (mask.indexOf(wildChar) < 0) {
/*     */ 
/*     */       
/*  24 */       if (mask.indexOf(wildCharSingle) < 0) {
/*  25 */         return mask.equals(str);
/*     */       }
/*  27 */       return equalsMaskSingle(str, mask, wildCharSingle);
/*     */     } 
/*     */     
/*  30 */     List<String> tokens = new ArrayList();
/*     */     
/*  32 */     String wildCharStr = "" + wildChar;
/*     */     
/*  34 */     if (mask.startsWith(wildCharStr)) {
/*  35 */       tokens.add("");
/*     */     }
/*  37 */     StringTokenizer tok = new StringTokenizer(mask, wildCharStr);
/*  38 */     while (tok.hasMoreElements())
/*     */     {
/*  40 */       tokens.add(tok.nextToken());
/*     */     }
/*     */     
/*  43 */     if (mask.endsWith(wildCharStr)) {
/*  44 */       tokens.add("");
/*     */     }
/*     */     
/*  47 */     String startTok = tokens.get(0);
/*  48 */     if (!startsWithMaskSingle(str, startTok, wildCharSingle)) {
/*  49 */       return false;
/*     */     }
/*  51 */     String endTok = tokens.get(tokens.size() - 1);
/*  52 */     if (!endsWithMaskSingle(str, endTok, wildCharSingle)) {
/*  53 */       return false;
/*     */     }
/*  55 */     int currPos = 0;
/*  56 */     for (int i = 0; i < tokens.size(); i++) {
/*     */       
/*  58 */       String token = tokens.get(i);
/*     */       
/*  60 */       if (token.length() > 0) {
/*     */         
/*  62 */         int foundPos = indexOfMaskSingle(str, token, currPos, wildCharSingle);
/*  63 */         if (foundPos >= 0) {
/*     */ 
/*     */           
/*  66 */           currPos = foundPos + token.length();
/*     */         
/*     */         }
/*     */         else {
/*     */           
/*  71 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/*  75 */     return true;
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
/*     */   private static boolean equalsMaskSingle(String str, String mask, char wildCharSingle) {
/*  87 */     if (str == null || mask == null) {
/*  88 */       return (str == mask);
/*     */     }
/*  90 */     if (str.length() != mask.length()) {
/*  91 */       return false;
/*     */     }
/*  93 */     for (int i = 0; i < mask.length(); i++) {
/*     */       
/*  95 */       char maskChar = mask.charAt(i);
/*     */       
/*  97 */       if (maskChar != wildCharSingle)
/*     */       {
/*     */         
/* 100 */         if (str.charAt(i) != maskChar)
/*     */         {
/*     */           
/* 103 */           return false; } 
/*     */       }
/*     */     } 
/* 106 */     return true;
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
/*     */   private static int indexOfMaskSingle(String str, String mask, int startPos, char wildCharSingle) {
/* 119 */     if (str == null || mask == null) {
/* 120 */       return -1;
/*     */     }
/* 122 */     if (startPos < 0 || startPos > str.length()) {
/* 123 */       return -1;
/*     */     }
/* 125 */     if (str.length() < startPos + mask.length()) {
/* 126 */       return -1;
/*     */     }
/* 128 */     for (int i = startPos; i + mask.length() <= str.length(); i++) {
/*     */ 
/*     */       
/* 131 */       String subStr = str.substring(i, i + mask.length());
/*     */       
/* 133 */       if (equalsMaskSingle(subStr, mask, wildCharSingle)) {
/* 134 */         return i;
/*     */       }
/*     */     } 
/* 137 */     return -1;
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
/*     */   private static boolean endsWithMaskSingle(String str, String mask, char wildCharSingle) {
/* 149 */     if (str == null || mask == null) {
/* 150 */       return (str == mask);
/*     */     }
/* 152 */     if (str.length() < mask.length()) {
/* 153 */       return false;
/*     */     }
/* 155 */     String subStr = str.substring(str.length() - mask.length(), str.length());
/*     */     
/* 157 */     return equalsMaskSingle(subStr, mask, wildCharSingle);
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
/*     */   private static boolean startsWithMaskSingle(String str, String mask, char wildCharSingle) {
/* 169 */     if (str == null || mask == null) {
/* 170 */       return (str == mask);
/*     */     }
/* 172 */     if (str.length() < mask.length()) {
/* 173 */       return false;
/*     */     }
/* 175 */     String subStr = str.substring(0, mask.length());
/*     */     
/* 177 */     return equalsMaskSingle(subStr, mask, wildCharSingle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean equalsMask(String str, String[] masks, char wildChar) {
/* 188 */     for (int i = 0; i < masks.length; i++) {
/*     */       
/* 190 */       String mask = masks[i];
/* 191 */       if (equalsMask(str, mask, wildChar)) {
/* 192 */         return true;
/*     */       }
/*     */     } 
/* 195 */     return false;
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
/*     */   public static boolean equalsMask(String str, String mask, char wildChar) {
/* 208 */     if (mask == null || str == null) {
/* 209 */       return (mask == str);
/*     */     }
/* 211 */     if (mask.indexOf(wildChar) < 0) {
/* 212 */       return mask.equals(str);
/*     */     }
/* 214 */     List<String> tokens = new ArrayList();
/*     */     
/* 216 */     String wildCharStr = "" + wildChar;
/*     */     
/* 218 */     if (mask.startsWith(wildCharStr)) {
/* 219 */       tokens.add("");
/*     */     }
/* 221 */     StringTokenizer tok = new StringTokenizer(mask, wildCharStr);
/* 222 */     while (tok.hasMoreElements())
/*     */     {
/* 224 */       tokens.add(tok.nextToken());
/*     */     }
/*     */     
/* 227 */     if (mask.endsWith(wildCharStr)) {
/* 228 */       tokens.add("");
/*     */     }
/*     */     
/* 231 */     String startTok = tokens.get(0);
/* 232 */     if (!str.startsWith(startTok)) {
/* 233 */       return false;
/*     */     }
/* 235 */     String endTok = tokens.get(tokens.size() - 1);
/* 236 */     if (!str.endsWith(endTok)) {
/* 237 */       return false;
/*     */     }
/* 239 */     int currPos = 0;
/* 240 */     for (int i = 0; i < tokens.size(); i++) {
/*     */       
/* 242 */       String token = tokens.get(i);
/*     */       
/* 244 */       if (token.length() > 0) {
/*     */         
/* 246 */         int foundPos = str.indexOf(token, currPos);
/* 247 */         if (foundPos >= 0) {
/*     */ 
/*     */           
/* 250 */           currPos = foundPos + token.length();
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 255 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/* 259 */     return true;
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
/*     */   public static String[] split(String str, String separators) {
/* 272 */     if (str == null || str.length() <= 0)
/* 273 */       return new String[0]; 
/* 274 */     if (separators == null) {
/* 275 */       return new String[] { str };
/*     */     }
/* 277 */     List<String> tokens = new ArrayList();
/*     */     
/* 279 */     int startPos = 0;
/* 280 */     for (int i = 0; i < str.length(); i++) {
/*     */       
/* 282 */       char ch = str.charAt(i);
/* 283 */       if (equals(ch, separators)) {
/*     */         
/* 285 */         tokens.add(str.substring(startPos, i));
/* 286 */         startPos = i + 1;
/*     */       } 
/*     */     } 
/*     */     
/* 290 */     tokens.add(str.substring(startPos, str.length()));
/*     */     
/* 292 */     return tokens.<String>toArray(new String[tokens.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean equals(char ch, String matches) {
/* 302 */     for (int i = 0; i < matches.length(); i++) {
/*     */       
/* 304 */       if (matches.charAt(i) == ch) {
/* 305 */         return true;
/*     */       }
/*     */     } 
/* 308 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean equalsTrim(String a, String b) {
/* 317 */     if (a != null)
/* 318 */       a = a.trim(); 
/* 319 */     if (b != null)
/* 320 */       b = b.trim(); 
/* 321 */     return equals(a, b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEmpty(String string) {
/* 330 */     if (string == null)
/* 331 */       return true; 
/* 332 */     if (string.trim().length() <= 0) {
/* 333 */       return true;
/*     */     }
/* 335 */     return false;
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
/*     */   public static String stringInc(String str) {
/* 347 */     int val = parseInt(str, -1);
/* 348 */     if (val == -1)
/* 349 */       return ""; 
/* 350 */     val++;
/*     */     
/* 352 */     String test = "" + val;
/* 353 */     if (test.length() > str.length()) {
/* 354 */       return "";
/*     */     }
/* 356 */     return fillLeft("" + val, str.length(), '0');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int parseInt(String s, int defVal) {
/* 367 */     if (s == null) {
/* 368 */       return defVal;
/*     */     }
/*     */     
/*     */     try {
/* 372 */       return Integer.parseInt(s);
/*     */     }
/* 374 */     catch (NumberFormatException e) {
/*     */       
/* 376 */       return defVal;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFilled(String string) {
/* 385 */     return !isEmpty(string);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String addIfNotContains(String target, String source) {
/* 396 */     for (int i = 0; i < source.length(); i++) {
/*     */       
/* 398 */       if (target.indexOf(source.charAt(i)) < 0)
/*     */       {
/* 400 */         target = target + source.charAt(i);
/*     */       }
/*     */     } 
/* 403 */     return target;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String fillLeft(String s, int len, char fillChar) {
/* 414 */     if (s == null)
/* 415 */       s = ""; 
/* 416 */     if (s.length() >= len) {
/* 417 */       return s;
/*     */     }
/* 419 */     StringBuffer buf = new StringBuffer();
/* 420 */     int bufLen = len - s.length();
/* 421 */     while (buf.length() < bufLen)
/*     */     {
/* 423 */       buf.append(fillChar);
/*     */     }
/*     */     
/* 426 */     return buf.toString() + s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String fillRight(String s, int len, char fillChar) {
/* 437 */     if (s == null)
/* 438 */       s = ""; 
/* 439 */     if (s.length() >= len) {
/* 440 */       return s;
/*     */     }
/* 442 */     StringBuffer buf = new StringBuffer(s);
/* 443 */     while (buf.length() < len)
/*     */     {
/* 445 */       buf.append(fillChar);
/*     */     }
/*     */     
/* 448 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean equals(Object a, Object b) {
/* 459 */     if (a == b) {
/* 460 */       return true;
/*     */     }
/* 462 */     if (a != null && a.equals(b)) {
/* 463 */       return true;
/*     */     }
/* 465 */     if (b != null && b.equals(a)) {
/* 466 */       return true;
/*     */     }
/* 468 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean startsWith(String str, String[] prefixes) {
/* 475 */     if (str == null)
/* 476 */       return false; 
/* 477 */     if (prefixes == null) {
/* 478 */       return false;
/*     */     }
/* 480 */     for (int i = 0; i < prefixes.length; i++) {
/*     */       
/* 482 */       String prefix = prefixes[i];
/* 483 */       if (str.startsWith(prefix)) {
/* 484 */         return true;
/*     */       }
/*     */     } 
/* 487 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean endsWith(String str, String[] suffixes) {
/* 493 */     if (str == null)
/* 494 */       return false; 
/* 495 */     if (suffixes == null) {
/* 496 */       return false;
/*     */     }
/* 498 */     for (int i = 0; i < suffixes.length; i++) {
/*     */       
/* 500 */       String suffix = suffixes[i];
/* 501 */       if (str.endsWith(suffix)) {
/* 502 */         return true;
/*     */       }
/*     */     } 
/* 505 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removePrefix(String str, String prefix) {
/* 513 */     if (str == null || prefix == null) {
/* 514 */       return str;
/*     */     }
/* 516 */     if (str.startsWith(prefix)) {
/* 517 */       str = str.substring(prefix.length());
/*     */     }
/* 519 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removeSuffix(String str, String suffix) {
/* 527 */     if (str == null || suffix == null) {
/* 528 */       return str;
/*     */     }
/* 530 */     if (str.endsWith(suffix)) {
/* 531 */       str = str.substring(0, str.length() - suffix.length());
/*     */     }
/* 533 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String replaceSuffix(String str, String suffix, String suffixNew) {
/* 541 */     if (str == null || suffix == null) {
/* 542 */       return str;
/*     */     }
/* 544 */     if (!str.endsWith(suffix)) {
/* 545 */       return str;
/*     */     }
/* 547 */     if (suffixNew == null) {
/* 548 */       suffixNew = "";
/*     */     }
/* 550 */     str = str.substring(0, str.length() - suffix.length());
/*     */     
/* 552 */     return str + suffixNew;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String replacePrefix(String str, String prefix, String prefixNew) {
/* 560 */     if (str == null || prefix == null) {
/* 561 */       return str;
/*     */     }
/* 563 */     if (!str.startsWith(prefix)) {
/* 564 */       return str;
/*     */     }
/* 566 */     if (prefixNew == null) {
/* 567 */       prefixNew = "";
/*     */     }
/* 569 */     str = str.substring(prefix.length());
/*     */     
/* 571 */     return prefixNew + str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int findPrefix(String[] strs, String prefix) {
/* 579 */     if (strs == null || prefix == null) {
/* 580 */       return -1;
/*     */     }
/* 582 */     for (int i = 0; i < strs.length; i++) {
/*     */       
/* 584 */       String str = strs[i];
/* 585 */       if (str.startsWith(prefix)) {
/* 586 */         return i;
/*     */       }
/*     */     } 
/* 589 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int findSuffix(String[] strs, String suffix) {
/* 597 */     if (strs == null || suffix == null) {
/* 598 */       return -1;
/*     */     }
/* 600 */     for (int i = 0; i < strs.length; i++) {
/*     */       
/* 602 */       String str = strs[i];
/* 603 */       if (str.endsWith(suffix)) {
/* 604 */         return i;
/*     */       }
/*     */     } 
/* 607 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] remove(String[] strs, int start, int end) {
/* 614 */     if (strs == null) {
/* 615 */       return strs;
/*     */     }
/* 617 */     if (end <= 0 || start >= strs.length)
/* 618 */       return strs; 
/* 619 */     if (start >= end) {
/* 620 */       return strs;
/*     */     }
/* 622 */     List<String> list = new ArrayList<String>(strs.length);
/* 623 */     for (int i = 0; i < strs.length; i++) {
/*     */       
/* 625 */       String str = strs[i];
/* 626 */       if (i < start || i >= end)
/*     */       {
/*     */         
/* 629 */         list.add(str); } 
/*     */     } 
/* 631 */     String[] strsNew = list.<String>toArray(new String[list.size()]);
/*     */     
/* 633 */     return strsNew;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removeSuffix(String str, String[] suffixes) {
/* 641 */     if (str == null || suffixes == null) {
/* 642 */       return str;
/*     */     }
/* 644 */     int strLen = str.length();
/* 645 */     for (int i = 0; i < suffixes.length; i++) {
/*     */       
/* 647 */       String suffix = suffixes[i];
/* 648 */       str = removeSuffix(str, suffix);
/*     */       
/* 650 */       if (str.length() != strLen) {
/*     */         break;
/*     */       }
/*     */     } 
/* 654 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removePrefix(String str, String[] prefixes) {
/* 662 */     if (str == null || prefixes == null) {
/* 663 */       return str;
/*     */     }
/* 665 */     int strLen = str.length();
/* 666 */     for (int i = 0; i < prefixes.length; i++) {
/*     */       
/* 668 */       String prefix = prefixes[i];
/* 669 */       str = removePrefix(str, prefix);
/*     */       
/* 671 */       if (str.length() != strLen) {
/*     */         break;
/*     */       }
/*     */     } 
/* 675 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removePrefixSuffix(String str, String[] prefixes, String[] suffixes) {
/* 682 */     str = removePrefix(str, prefixes);
/* 683 */     str = removeSuffix(str, suffixes);
/* 684 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removePrefixSuffix(String str, String prefix, String suffix) {
/* 694 */     return removePrefixSuffix(str, new String[] { prefix }, new String[] { suffix });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getSegment(String str, String start, String end) {
/* 705 */     if (str == null || start == null || end == null) {
/* 706 */       return null;
/*     */     }
/* 708 */     int posStart = str.indexOf(start);
/* 709 */     if (posStart < 0) {
/* 710 */       return null;
/*     */     }
/* 712 */     int posEnd = str.indexOf(end, posStart);
/* 713 */     if (posEnd < 0) {
/* 714 */       return null;
/*     */     }
/* 716 */     return str.substring(posStart, posEnd + end.length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String addSuffixCheck(String str, String suffix) {
/* 724 */     if (str == null || suffix == null) {
/* 725 */       return str;
/*     */     }
/* 727 */     if (str.endsWith(suffix)) {
/* 728 */       return str;
/*     */     }
/* 730 */     return str + suffix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String addPrefixCheck(String str, String prefix) {
/* 738 */     if (str == null || prefix == null) {
/* 739 */       return str;
/*     */     }
/* 741 */     if (str.endsWith(prefix)) {
/* 742 */       return str;
/*     */     }
/* 744 */     return prefix + str;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\StrUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */