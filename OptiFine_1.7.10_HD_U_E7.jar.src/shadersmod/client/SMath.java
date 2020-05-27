/*    */ package shadersmod.client;
/*    */ 
/*    */ import java.nio.FloatBuffer;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SMath
/*    */ {
/*    */   static void multiplyMat4xMat4(float[] matOut, float[] matA, float[] matB) {
/* 11 */     for (int co = 0; co < 4; co++) {
/*    */       
/* 13 */       for (int ro = 0; ro < 4; ro++)
/*    */       {
/* 15 */         matOut[4 * co + ro] = matA[4 * co + 0] * matB[0 + ro] + matA[4 * co + 1] * matB[4 + ro] + matA[4 * co + 2] * matB[8 + ro] + matA[4 * co + 3] * matB[12 + ro];
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static void multiplyMat4xVec4(float[] vecOut, float[] matA, float[] vecB) {
/* 26 */     vecOut[0] = matA[0] * vecB[0] + matA[4] * vecB[1] + matA[8] * vecB[2] + matA[12] * vecB[3];
/* 27 */     vecOut[1] = matA[1] * vecB[0] + matA[5] * vecB[1] + matA[9] * vecB[2] + matA[13] * vecB[3];
/* 28 */     vecOut[2] = matA[2] * vecB[0] + matA[6] * vecB[1] + matA[10] * vecB[2] + matA[14] * vecB[3];
/* 29 */     vecOut[3] = matA[3] * vecB[0] + matA[7] * vecB[1] + matA[11] * vecB[2] + matA[15] * vecB[3];
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   static void invertMat4(float[] matOut, float[] m) {
/* 35 */     matOut[0] = m[5] * m[10] * m[15] - m[5] * m[11] * m[14] - m[9] * m[6] * m[15] + m[9] * m[7] * m[14] + m[13] * m[6] * m[11] - m[13] * m[7] * m[10];
/* 36 */     matOut[1] = -m[1] * m[10] * m[15] + m[1] * m[11] * m[14] + m[9] * m[2] * m[15] - m[9] * m[3] * m[14] - m[13] * m[2] * m[11] + m[13] * m[3] * m[10];
/* 37 */     matOut[2] = m[1] * m[6] * m[15] - m[1] * m[7] * m[14] - m[5] * m[2] * m[15] + m[5] * m[3] * m[14] + m[13] * m[2] * m[7] - m[13] * m[3] * m[6];
/* 38 */     matOut[3] = -m[1] * m[6] * m[11] + m[1] * m[7] * m[10] + m[5] * m[2] * m[11] - m[5] * m[3] * m[10] - m[9] * m[2] * m[7] + m[9] * m[3] * m[6];
/* 39 */     matOut[4] = -m[4] * m[10] * m[15] + m[4] * m[11] * m[14] + m[8] * m[6] * m[15] - m[8] * m[7] * m[14] - m[12] * m[6] * m[11] + m[12] * m[7] * m[10];
/* 40 */     matOut[5] = m[0] * m[10] * m[15] - m[0] * m[11] * m[14] - m[8] * m[2] * m[15] + m[8] * m[3] * m[14] + m[12] * m[2] * m[11] - m[12] * m[3] * m[10];
/* 41 */     matOut[6] = -m[0] * m[6] * m[15] + m[0] * m[7] * m[14] + m[4] * m[2] * m[15] - m[4] * m[3] * m[14] - m[12] * m[2] * m[7] + m[12] * m[3] * m[6];
/* 42 */     matOut[7] = m[0] * m[6] * m[11] - m[0] * m[7] * m[10] - m[4] * m[2] * m[11] + m[4] * m[3] * m[10] + m[8] * m[2] * m[7] - m[8] * m[3] * m[6];
/* 43 */     matOut[8] = m[4] * m[9] * m[15] - m[4] * m[11] * m[13] - m[8] * m[5] * m[15] + m[8] * m[7] * m[13] + m[12] * m[5] * m[11] - m[12] * m[7] * m[9];
/* 44 */     matOut[9] = -m[0] * m[9] * m[15] + m[0] * m[11] * m[13] + m[8] * m[1] * m[15] - m[8] * m[3] * m[13] - m[12] * m[1] * m[11] + m[12] * m[3] * m[9];
/* 45 */     matOut[10] = m[0] * m[5] * m[15] - m[0] * m[7] * m[13] - m[4] * m[1] * m[15] + m[4] * m[3] * m[13] + m[12] * m[1] * m[7] - m[12] * m[3] * m[5];
/* 46 */     matOut[11] = -m[0] * m[5] * m[11] + m[0] * m[7] * m[9] + m[4] * m[1] * m[11] - m[4] * m[3] * m[9] - m[8] * m[1] * m[7] + m[8] * m[3] * m[5];
/* 47 */     matOut[12] = -m[4] * m[9] * m[14] + m[4] * m[10] * m[13] + m[8] * m[5] * m[14] - m[8] * m[6] * m[13] - m[12] * m[5] * m[10] + m[12] * m[6] * m[9];
/* 48 */     matOut[13] = m[0] * m[9] * m[14] - m[0] * m[10] * m[13] - m[8] * m[1] * m[14] + m[8] * m[2] * m[13] + m[12] * m[1] * m[10] - m[12] * m[2] * m[9];
/* 49 */     matOut[14] = -m[0] * m[5] * m[14] + m[0] * m[6] * m[13] + m[4] * m[1] * m[14] - m[4] * m[2] * m[13] - m[12] * m[1] * m[6] + m[12] * m[2] * m[5];
/* 50 */     matOut[15] = m[0] * m[5] * m[10] - m[0] * m[6] * m[9] - m[4] * m[1] * m[10] + m[4] * m[2] * m[9] + m[8] * m[1] * m[6] - m[8] * m[2] * m[5];
/*    */     
/* 52 */     float det = m[0] * matOut[0] + m[1] * matOut[4] + m[2] * matOut[8] + m[3] * matOut[12];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 58 */     if (det != 0.0D) {
/*    */       
/* 60 */       for (int i = 0; i < 16; i++)
/*    */       {
/* 62 */         matOut[i] = matOut[i] / det;
/*    */       
/*    */       }
/*    */     }
/*    */     else {
/*    */       
/* 68 */       Arrays.fill(matOut, 0.0F);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   static void invertMat4FBFA(FloatBuffer fbInvOut, FloatBuffer fbMatIn, float[] faInv, float[] faMat) {
/* 75 */     fbMatIn.get(faMat);
/* 76 */     invertMat4(faInv, faMat);
/* 77 */     fbInvOut.put(faInv);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\shadersmod\client\SMath.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */