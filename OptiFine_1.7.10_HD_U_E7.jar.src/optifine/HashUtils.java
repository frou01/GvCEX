/*    */ package optifine;
/*    */ 
/*    */ import java.security.MessageDigest;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HashUtils
/*    */ {
/*    */   public static String getHashMd5(String data) {
/* 15 */     return getHash(data, "MD5");
/*    */   }
/*    */ 
/*    */   
/*    */   public static String getHashSha1(String data) {
/* 20 */     return getHash(data, "SHA-1");
/*    */   }
/*    */ 
/*    */   
/*    */   public static String getHashSha256(String data) {
/* 25 */     return getHash(data, "SHA-256");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getHash(String data, String digest) {
/*    */     try {
/* 32 */       byte[] array = getHash(data.getBytes("UTF-8"), digest);
/* 33 */       return toHexString(array);
/*    */     }
/* 35 */     catch (Exception e) {
/*    */       
/* 37 */       throw new RuntimeException(e.getMessage(), e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String toHexString(byte[] data) {
/* 47 */     StringBuffer sb = new StringBuffer();
/* 48 */     for (int i = 0; i < data.length; i++)
/*    */     {
/* 50 */       sb.append(Integer.toHexString(data[i] & 0xFF | 0x100).substring(1, 3));
/*    */     }
/* 52 */     return sb.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public static byte[] getHashMd5(byte[] data) throws NoSuchAlgorithmException {
/* 57 */     return getHash(data, "MD5");
/*    */   }
/*    */ 
/*    */   
/*    */   public static byte[] getHashSha1(byte[] data) throws NoSuchAlgorithmException {
/* 62 */     return getHash(data, "SHA-1");
/*    */   }
/*    */ 
/*    */   
/*    */   public static byte[] getHashSha256(byte[] data) throws NoSuchAlgorithmException {
/* 67 */     return getHash(data, "SHA-256");
/*    */   }
/*    */ 
/*    */   
/*    */   public static byte[] getHash(byte[] data, String digest) throws NoSuchAlgorithmException {
/* 72 */     MessageDigest md = MessageDigest.getInstance(digest);
/* 73 */     byte[] array = md.digest(data);
/* 74 */     return array;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\HashUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */