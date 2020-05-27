/*     */ package optifine.xdelta;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public class Checksum
/*     */ {
/*     */   public static final int BASE = 65521;
/*     */   public static final int S = 16;
/*     */   public static boolean debug = false;
/*     */   protected int[] hashtable;
/*     */   protected long[] checksums;
/*     */   protected int prime;
/*  48 */   protected static final char[] single_hash = new char[] {
/*     */       
/*  50 */       '병', '뭥', '䋂', '?', '陦', '䌛', '蔄', '', 
/*  51 */       '捹', '푠', '켔', '叏', '?', '?', 'ወ', '', 
/*  52 */       '', '⎔', '┍', '?', 'ꙸ', 'ʯ', 'ꗆ', '约', 
/*  53 */       '뙅', '쭍', '쑋', '', '鿦', '孜', '㗵', '瀚', 
/*  54 */       '∏', '永', 'ᩖ', '䲣', 'ￆ', '녒', '赡', '穘', 
/*  55 */       '逥', '謽', '뼏', '閣', '', '섧', '㯭', '㈋', 
/*  56 */       '럳', '恔', '㌼', '펃', '腔', '剂', '不', 'ઔ', 
/*  57 */       '瀨', '蚉', '㨢', 'ঀ', 'ᡇ', '냱', '魜', '䅶', 
/*  58 */       '롘', '핂', 'Ὤ', '⒗', '橚', '龩', '豚', '睃', 
/*  59 */       'ꢩ', '騂', '䤘', '䎌', '쎈', '鸫', '䲭', 'ƶ', 
/*  60 */       '꬙', '', '㙟', 'Ẳ', 'ञ', '篸', '窎', '刧', 
/*  61 */       '', '⁴', '䔣', '', 'ƣ', 'ᘽ', '㬮', '⡽', 
/*  62 */       '广', 'ꁣ', '넴', '辮', '庎', '랷', '䕈', '὚', 
/*  63 */       '節', '稤', '透', '䋜', '챩', 'ʠ', 'ଢ', '?', 
/*  64 */       '燾', '౽', 'ᜲ', 'ᅙ', '쬉', '', 'ፑ', '勩', 
/*  65 */       '', '婏', '쌖', '毹', '覔', '띴', '弾', '', 
/*  66 */       '㩡', '', '찢', '鴆', '⦜', '৥', 'Ử', '兏', 
/*  67 */       '赓', 'Ꙑ', '屮', '앷', '祘', '熬', '褖', '魏', 
/*  68 */       'Ⰹ', '刑', '', '쪪', '', '⡿', '窔', 'ꭉ', 
/*  69 */       '館', '爢', '', '휚', 'Ã', '᩶', '', '쀷', 
/*  70 */       '興', '尭', '?', '', '୅', 'ᗎ', '詾', 'ﲭ', 
/*  71 */       'ꨭ', '䭜', '퐮', '뉑', '遾', '驇', '즦', '?', 
/*  72 */       '࡞', '㗎', 'ꅓ', '繻', '鼋', '▪', '嶟', '쁍', 
/*  73 */       '討', '⡵', '䨜', '⥟', '᎓', '', '酸', 'ཛ', 
/*  74 */       '墳', '莴', '₂', '爝', '摢', 'ͨ', '柢', '蘤', 
/*  75 */       '᥍', '⋶', '磻', '枑', '눸', '댲', '牶', '', 
/*  76 */       '䟬', '䔄', 'ꥡ', '鿈', '㿜', '됓', 'z', 'ࠆ', 
/*  77 */       '瑘', '闆', '첪', 'ᣖ', '', 'ᬆ', '', '偐', 
/*  78 */       '죨', '', '쁌', '', '餯', '깄', '弛', 'ᄓ', 
/*  79 */       '᜸', '?', '᧪', 'ⴳ', '隘', '⿩', '㈿', '췢', 
/*  80 */       '浱', '', '뚗', 'ⱏ', '䍳', '鄂', 'ݝ', '踥', 
/*  81 */       'ᙲ', '', '櫋', '蛌', 'ᡮ', '鐔', '홴', '톥'
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long queryChecksum(byte[] buf, int len) {
/*  89 */     int high = 0, low = 0;
/*  90 */     for (int i = 0; i < len; i++) {
/*  91 */       low += single_hash[buf[i] + 128];
/*  92 */       high += low;
/*     */     } 
/*  94 */     return ((high & 0xFFFF) << 16 | low & 0xFFFF);
/*     */   }
/*     */   
/*     */   public static long incrementChecksum(long checksum, byte out, byte in) {
/*  98 */     char old_c = single_hash[out + 128];
/*  99 */     char new_c = single_hash[in + 128];
/* 100 */     int low = (int)(checksum & 0xFFFFL) - old_c + new_c & 0xFFFF;
/* 101 */     int high = (int)(checksum >> 16L) - old_c * 16 + low & 0xFFFF;
/* 102 */     return (high << 16 | low & 0xFFFF);
/*     */   }
/*     */   
/*     */   public static int generateHash(long checksum) {
/* 106 */     long high = checksum >> 16L & 0xFFFFL;
/* 107 */     long low = checksum & 0xFFFFL;
/* 108 */     long it = (high >> 2L) + (low << 3L) + (high << 16L);
/* 109 */     int hash = (int)(it ^ high ^ low);
/* 110 */     return (hash > 0) ? hash : -hash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void generateChecksums(File sourceFile, int length) throws IOException {
/* 121 */     FileInputStream fis = new FileInputStream(sourceFile);
/*     */     try {
/* 123 */       generateChecksums(fis, length);
/* 124 */     } catch (IOException e) {
/* 125 */       throw e;
/*     */     } finally {
/* 127 */       fis.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void generateChecksums(InputStream sis, int length) throws IOException {
/* 133 */     InputStream is = new BufferedInputStream(sis);
/*     */     
/* 135 */     int checksumcount = length / 16;
/* 136 */     if (debug) {
/* 137 */       System.out.println("generating checksum array of size " + checksumcount);
/*     */     }
/* 139 */     this.checksums = new long[checksumcount];
/* 140 */     this.hashtable = new int[checksumcount];
/* 141 */     this.prime = findClosestPrime(checksumcount);
/*     */     
/* 143 */     if (debug) {
/* 144 */       System.out.println("using prime " + this.prime);
/*     */     }
/*     */     int i;
/* 147 */     for (i = 0; i < checksumcount; i++) {
/*     */       
/* 149 */       byte[] buf = new byte[16];
/*     */       
/* 151 */       is.read(buf, 0, 16);
/*     */       
/* 153 */       this.checksums[i] = queryChecksum(buf, 16);
/*     */     } 
/*     */ 
/*     */     
/* 157 */     for (i = 0; i < checksumcount; ) { this.hashtable[i] = -1; i++; }
/*     */     
/* 159 */     for (i = 0; i < checksumcount; i++) {
/* 160 */       int hash = generateHash(this.checksums[i]) % this.prime;
/* 161 */       if (debug)
/* 162 */         System.out.println("checking with hash: " + hash); 
/* 163 */       if (this.hashtable[hash] != -1) {
/* 164 */         if (debug)
/* 165 */           System.out.println("hash table collision for index " + i); 
/*     */       } else {
/* 167 */         this.hashtable[hash] = i;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int findChecksumIndex(long checksum) {
/* 175 */     return this.hashtable[generateHash(checksum) % this.prime];
/*     */   }
/*     */   
/*     */   private static int findClosestPrime(int size) {
/* 179 */     int prime = (int)SimplePrime.belowOrEqual((size - 1));
/*     */     
/* 181 */     return (prime < 2) ? 1 : prime;
/*     */   }
/*     */   
/*     */   private String printIntArray(int[] a) {
/* 185 */     String result = "";
/* 186 */     result = String.valueOf(result) + "[";
/* 187 */     for (int i = 0; i < a.length; i++) {
/* 188 */       result = String.valueOf(result) + a[i];
/* 189 */       if (i != a.length - 1) {
/* 190 */         result = String.valueOf(result) + ",";
/*     */       } else {
/* 192 */         result = String.valueOf(result) + "]";
/*     */       } 
/* 194 */     }  return result;
/*     */   }
/*     */   
/*     */   private String printLongArray(long[] a) {
/* 198 */     String result = "";
/* 199 */     result = String.valueOf(result) + "[";
/* 200 */     for (int i = 0; i < a.length; i++) {
/* 201 */       result = String.valueOf(result) + a[i];
/* 202 */       if (i != a.length - 1) {
/* 203 */         result = String.valueOf(result) + ",";
/*     */       } else {
/* 205 */         result = String.valueOf(result) + "]";
/*     */       } 
/* 207 */     }  return result;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\xdelta\Checksum.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */