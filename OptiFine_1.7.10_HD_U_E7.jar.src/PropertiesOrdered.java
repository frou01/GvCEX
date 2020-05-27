/*    */ import java.util.Collections;
/*    */ import java.util.Enumeration;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Properties;
/*    */ import java.util.Set;
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
/*    */ 
/*    */ public class PropertiesOrdered
/*    */   extends Properties
/*    */ {
/* 20 */   private Set<Object> keysOrdered = new LinkedHashSet();
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
/*    */ 
/*    */   
/*    */   public synchronized Object put(Object key, Object value) {
/* 34 */     this.keysOrdered.add(key);
/*    */     
/* 36 */     return super.put(key, value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<Object> keySet() {
/* 44 */     Set<Object> keysParent = super.keySet();
/*    */     
/* 46 */     this.keysOrdered.retainAll(keysParent);
/*    */     
/* 48 */     return Collections.unmodifiableSet(this.keysOrdered);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized Enumeration<Object> keys() {
/* 56 */     return Collections.enumeration(keySet());
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\PropertiesOrdered.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */