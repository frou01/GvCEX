/*     */ import java.util.AbstractSet;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
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
/*     */ public class NextTickHashSet
/*     */   extends AbstractSet
/*     */ {
/*  19 */   private qd longHashMap = new qd();
/*     */   
/*  21 */   private int size = 0;
/*     */   
/*  23 */   private HashSet emptySet = new HashSet();
/*     */ 
/*     */   
/*     */   public NextTickHashSet(Set<? extends E> oldSet) {
/*  27 */     addAll(oldSet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  34 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(Object obj) {
/*  42 */     if (!(obj instanceof ahs))
/*  43 */       return false; 
/*  44 */     ahs entry = (ahs)obj;
/*     */     
/*  46 */     if (entry == null) {
/*  47 */       return false;
/*     */     }
/*  49 */     long key = agu.a(entry.a >> 4, entry.c >> 4);
/*  50 */     HashSet set = (HashSet)this.longHashMap.a(key);
/*  51 */     if (set == null) {
/*  52 */       return false;
/*     */     }
/*  54 */     return set.contains(entry);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(Object obj) {
/*  62 */     if (!(obj instanceof ahs))
/*  63 */       return false; 
/*  64 */     ahs entry = (ahs)obj;
/*     */     
/*  66 */     if (entry == null) {
/*  67 */       return false;
/*     */     }
/*  69 */     long key = agu.a(entry.a >> 4, entry.c >> 4);
/*  70 */     HashSet<ahs> set = (HashSet)this.longHashMap.a(key);
/*  71 */     if (set == null) {
/*     */       
/*  73 */       set = new HashSet();
/*  74 */       this.longHashMap.a(key, set);
/*     */     } 
/*     */     
/*  77 */     boolean added = set.add(entry);
/*     */     
/*  79 */     if (added) {
/*  80 */       this.size++;
/*     */     }
/*  82 */     return added;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(Object obj) {
/*  90 */     if (!(obj instanceof ahs))
/*  91 */       return false; 
/*  92 */     ahs entry = (ahs)obj;
/*     */     
/*  94 */     if (entry == null) {
/*  95 */       return false;
/*     */     }
/*  97 */     long key = agu.a(entry.a >> 4, entry.c >> 4);
/*  98 */     HashSet set = (HashSet)this.longHashMap.a(key);
/*  99 */     if (set == null) {
/* 100 */       return false;
/*     */     }
/* 102 */     boolean removed = set.remove(entry);
/*     */     
/* 104 */     if (removed) {
/* 105 */       this.size--;
/*     */     }
/* 107 */     return removed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getNextTickEntries(int chunkX, int chunkZ) {
/* 117 */     HashSet set = getNextTickEntriesSet(chunkX, chunkZ);
/*     */     
/* 119 */     return set.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashSet getNextTickEntriesSet(int chunkX, int chunkZ) {
/* 128 */     long key = agu.a(chunkX, chunkZ);
/* 129 */     HashSet set = (HashSet)this.longHashMap.a(key);
/* 130 */     if (set == null) {
/* 131 */       set = this.emptySet;
/*     */     }
/* 133 */     return set;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator iterator() {
/* 140 */     throw new UnsupportedOperationException("Not implemented");
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\NextTickHashSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */