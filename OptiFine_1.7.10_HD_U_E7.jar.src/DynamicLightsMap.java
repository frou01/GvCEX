/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ public class DynamicLightsMap
/*    */ {
/* 17 */   private Map<Integer, DynamicLight> map = new HashMap<Integer, DynamicLight>();
/*    */   
/* 19 */   private List<DynamicLight> list = new ArrayList<DynamicLight>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean dirty = false;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DynamicLight put(int id, DynamicLight dynamicLight) {
/* 31 */     DynamicLight old = this.map.put(Integer.valueOf(id), dynamicLight);
/*    */     
/* 33 */     setDirty();
/*    */     
/* 35 */     return old;
/*    */   }
/*    */ 
/*    */   
/*    */   public DynamicLight get(int id) {
/* 40 */     return this.map.get(Integer.valueOf(id));
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 45 */     return this.map.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public DynamicLight remove(int id) {
/* 50 */     DynamicLight old = this.map.remove(Integer.valueOf(id));
/* 51 */     if (old != null) {
/* 52 */       setDirty();
/*    */     }
/* 54 */     return old;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 59 */     this.map.clear();
/* 60 */     setDirty();
/*    */   }
/*    */ 
/*    */   
/*    */   private void setDirty() {
/* 65 */     this.dirty = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<DynamicLight> valueList() {
/* 75 */     if (this.dirty) {
/*    */       
/* 77 */       this.list.clear();
/* 78 */       this.list.addAll(this.map.values());
/* 79 */       this.dirty = false;
/*    */     } 
/*    */     
/* 82 */     return this.list;
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\DynamicLightsMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */