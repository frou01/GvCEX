/*    */ import java.lang.reflect.Array;
/*    */ import java.util.ArrayDeque;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArrayCache
/*    */ {
/* 15 */   private Class elementClass = null;
/*    */   
/* 17 */   private int maxCacheSize = 0;
/*    */   
/* 19 */   private ArrayDeque cache = new ArrayDeque();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ArrayCache(Class elementClass, int maxCacheSize) {
/* 25 */     this.elementClass = elementClass;
/* 26 */     this.maxCacheSize = maxCacheSize;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized Object allocate(int size) {
/* 32 */     Object arr = this.cache.pollLast();
/*    */     
/* 34 */     if (arr == null || Array.getLength(arr) < size)
/*    */     {
/* 36 */       arr = Array.newInstance(this.elementClass, size);
/*    */     }
/*    */ 
/*    */     
/* 40 */     return arr;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void free(Object arr) {
/* 46 */     if (arr == null) {
/*    */       return;
/*    */     }
/* 49 */     Class<?> cls = arr.getClass();
/* 50 */     if (cls.getComponentType() != this.elementClass) {
/* 51 */       throw new IllegalArgumentException("Wrong component type");
/*    */     }
/* 53 */     if (this.cache.size() >= this.maxCacheSize) {
/*    */       return;
/*    */     }
/* 56 */     this.cache.add(arr);
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\ArrayCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */