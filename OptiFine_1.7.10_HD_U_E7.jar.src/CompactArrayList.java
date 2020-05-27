/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CompactArrayList
/*     */ {
/*  13 */   private ArrayList list = null;
/*  14 */   private int initialCapacity = 0;
/*  15 */   private float loadFactor = 1.0F;
/*  16 */   private int countValid = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompactArrayList() {
/*  22 */     this(10, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompactArrayList(int initialCapacity) {
/*  29 */     this(initialCapacity, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompactArrayList(int initialCapacity, float loadFactor) {
/*  36 */     this.list = new ArrayList(initialCapacity);
/*  37 */     this.initialCapacity = initialCapacity;
/*  38 */     this.loadFactor = loadFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(int index, Object element) {
/*  48 */     if (element != null) {
/*  49 */       this.countValid++;
/*     */     }
/*  51 */     this.list.add(index, element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(Object element) {
/*  60 */     if (element != null) {
/*  61 */       this.countValid++;
/*     */     }
/*  63 */     return this.list.add(element);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object set(int index, Object element) {
/*  69 */     Object oldElement = this.list.set(index, element);
/*     */     
/*  71 */     if (element != oldElement) {
/*     */ 
/*     */       
/*  74 */       if (oldElement == null) {
/*  75 */         this.countValid++;
/*     */       }
/*  77 */       if (element == null) {
/*  78 */         this.countValid--;
/*     */       }
/*     */     } 
/*  81 */     return oldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object remove(int index) {
/*  90 */     Object oldElement = this.list.remove(index);
/*     */     
/*  92 */     if (oldElement != null) {
/*  93 */       this.countValid--;
/*     */     }
/*  95 */     return oldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 103 */     this.list.clear();
/* 104 */     this.countValid = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void compact() {
/* 112 */     if (this.countValid <= 0 && this.list.size() <= 0) {
/*     */       
/* 114 */       clear();
/*     */       
/*     */       return;
/*     */     } 
/* 118 */     if (this.list.size() <= this.initialCapacity) {
/*     */       return;
/*     */     }
/* 121 */     float currentLoadFactor = this.countValid * 1.0F / this.list.size();
/*     */     
/* 123 */     if (currentLoadFactor > this.loadFactor) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 128 */     int dstIndex = 0;
/* 129 */     for (int srcIndex = 0; srcIndex < this.list.size(); srcIndex++) {
/*     */       
/* 131 */       Object wr = this.list.get(srcIndex);
/* 132 */       if (wr != null) {
/*     */ 
/*     */         
/* 135 */         if (srcIndex != dstIndex) {
/* 136 */           this.list.set(dstIndex, wr);
/*     */         }
/* 138 */         dstIndex++;
/*     */       } 
/*     */     } 
/* 141 */     for (int i = this.list.size() - 1; i >= dstIndex; i--)
/*     */     {
/* 143 */       this.list.remove(i);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(Object elem) {
/* 153 */     return this.list.contains(elem);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(int index) {
/* 162 */     return this.list.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 170 */     return this.list.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 178 */     return this.list.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCountValid() {
/* 185 */     return this.countValid;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\CompactArrayList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */