/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.lwjgl.opengl.Pbuffer;
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
/*     */ public class WrUpdateThread
/*     */   extends Thread
/*     */ {
/*  17 */   private Pbuffer pbuffer = null;
/*  18 */   private Object lock = new Object();
/*  19 */   private List updateList = new LinkedList();
/*  20 */   private List updatedList = new LinkedList();
/*  21 */   private int updateCount = 0;
/*     */   
/*  23 */   private bmh mainTessellator = bmh.a;
/*  24 */   private bmh threadTessellator = new bmh(2097152);
/*     */   
/*     */   private boolean working = false;
/*     */   
/*  28 */   private WorldRendererThreaded currentRenderer = null;
/*     */   
/*     */   private boolean canWork = false;
/*     */   
/*     */   private boolean canWorkToEndOfUpdate = false;
/*     */   private boolean terminated = false;
/*     */   private static final int MAX_UPDATE_CAPACITY = 10;
/*     */   
/*     */   private class ThreadUpdateControl
/*     */     implements IWrUpdateControl
/*     */   {
/*  39 */     private IWrUpdateControl updateControl = null;
/*     */     private boolean paused = false;
/*     */     
/*     */     public void pause() {
/*  43 */       if (!this.paused) {
/*     */         
/*  45 */         this.paused = true;
/*  46 */         this.updateControl.pause();
/*     */         
/*  48 */         bmh.a = WrUpdateThread.this.mainTessellator;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void resume() {
/*  53 */       if (this.paused) {
/*     */         
/*  55 */         this.paused = false;
/*     */         
/*  57 */         bmh.a = WrUpdateThread.this.threadTessellator;
/*  58 */         this.updateControl.resume();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void setUpdateControl(IWrUpdateControl updateControl) {
/*  63 */       this.updateControl = updateControl;
/*     */     }
/*     */     
/*     */     private ThreadUpdateControl() {} }
/*     */   
/*     */   private class ThreadUpdateListener implements IWrUpdateListener {
/*  69 */     private WrUpdateThread.ThreadUpdateControl tuc = new WrUpdateThread.ThreadUpdateControl();
/*     */     
/*     */     public void updating(IWrUpdateControl uc) {
/*  72 */       this.tuc.setUpdateControl(uc);
/*  73 */       WrUpdateThread.this.checkCanWork(this.tuc);
/*     */     }
/*     */ 
/*     */     
/*     */     private ThreadUpdateListener() {}
/*     */   }
/*     */   
/*     */   public WrUpdateThread(Pbuffer pbuffer) {
/*  81 */     super("WrUpdateThread");
/*  82 */     this.pbuffer = pbuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/*  92 */       this.pbuffer.makeCurrent();
/*     */     }
/*  94 */     catch (Exception e) {
/*     */       
/*  96 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  99 */     IWrUpdateListener updateListener = new ThreadUpdateListener();
/*     */     
/* 101 */     while (!Thread.interrupted() && !this.terminated) {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 106 */         WorldRendererThreaded wr = getRendererToUpdate();
/*     */         
/* 108 */         if (wr == null) {
/*     */           return;
/*     */         }
/* 111 */         checkCanWork(null);
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 116 */           this.currentRenderer = wr;
/*     */           
/* 118 */           bmh.a = this.threadTessellator;
/* 119 */           wr.updateRenderer(updateListener);
/*     */         }
/*     */         finally {
/*     */           
/* 123 */           bmh.a = this.mainTessellator;
/*     */         } 
/*     */         
/* 126 */         rendererUpdated(wr);
/*     */       }
/* 128 */       catch (Exception e) {
/*     */         
/* 130 */         e.printStackTrace();
/*     */         
/* 132 */         if (this.currentRenderer != null) {
/*     */           
/* 134 */           this.currentRenderer.isUpdating = false;
/* 135 */           this.currentRenderer.q = true;
/*     */         } 
/*     */         
/* 138 */         this.currentRenderer = null;
/* 139 */         this.working = false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addRendererToUpdate(blo wr, boolean first) {
/* 149 */     synchronized (this.lock) {
/*     */       
/* 151 */       if (wr.isUpdating) {
/* 152 */         throw new IllegalArgumentException("Renderer already updating");
/*     */       }
/*     */       
/* 155 */       if (first) {
/* 156 */         this.updateList.add(0, wr);
/*     */       } else {
/* 158 */         this.updateList.add(wr);
/*     */       } 
/* 160 */       wr.isUpdating = true;
/*     */       
/* 162 */       this.lock.notifyAll();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WorldRendererThreaded getRendererToUpdate() {
/* 171 */     synchronized (this.lock) {
/*     */       
/* 173 */       while (this.updateList.size() <= 0) {
/*     */ 
/*     */         
/*     */         try {
/*     */           
/* 178 */           this.lock.wait(2000L);
/*     */           
/* 180 */           if (this.terminated) {
/* 181 */             return null;
/*     */           }
/* 183 */         } catch (InterruptedException e) {}
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 188 */       WorldRendererThreaded wrt = this.updateList.remove(0);
/*     */       
/* 190 */       this.lock.notifyAll();
/*     */       
/* 192 */       return wrt;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasWorkToDo() {
/* 201 */     synchronized (this.lock) {
/*     */ 
/*     */       
/* 204 */       if (this.updateList.size() > 0) {
/* 205 */         return true;
/*     */       }
/* 207 */       if (this.currentRenderer != null) {
/* 208 */         return true;
/*     */       }
/* 210 */       return this.working;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUpdateCapacity() {
/* 217 */     synchronized (this.lock) {
/*     */       
/* 219 */       if (this.updateList.size() > 10)
/* 220 */         return 0; 
/* 221 */       return 10 - this.updateList.size();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rendererUpdated(blo wr) {
/* 229 */     synchronized (this.lock) {
/*     */ 
/*     */       
/* 232 */       this.updatedList.add(wr);
/*     */       
/* 234 */       this.updateCount++;
/* 235 */       this.currentRenderer = null;
/* 236 */       this.working = false;
/*     */       
/* 238 */       this.lock.notifyAll();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void finishUpdatedRenderers() {
/* 246 */     synchronized (this.lock) {
/*     */       
/* 248 */       for (int i = 0; i < this.updatedList.size(); i++) {
/*     */         
/* 250 */         WorldRendererThreaded wr = this.updatedList.get(i);
/* 251 */         wr.finishUpdate();
/*     */         
/* 253 */         wr.isUpdating = false;
/*     */       } 
/*     */       
/* 256 */       this.updatedList.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pause() {
/* 265 */     synchronized (this.lock) {
/*     */ 
/*     */ 
/*     */       
/* 269 */       this.canWork = false;
/* 270 */       this.canWorkToEndOfUpdate = false;
/*     */       
/* 272 */       this.lock.notifyAll();
/*     */       
/* 274 */       while (this.working) {
/*     */ 
/*     */         
/*     */         try {
/*     */           
/* 279 */           this.lock.wait();
/*     */         }
/* 281 */         catch (InterruptedException e) {}
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 287 */       finishUpdatedRenderers();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void unpause() {
/* 294 */     synchronized (this.lock) {
/*     */ 
/*     */ 
/*     */       
/* 298 */       if (this.working)
/* 299 */         Config.warn("UpdateThread still working in unpause()!!!"); 
/* 300 */       this.canWork = true;
/* 301 */       this.canWorkToEndOfUpdate = false;
/*     */       
/* 303 */       this.lock.notifyAll();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unpauseToEndOfUpdate() {
/* 311 */     synchronized (this.lock) {
/*     */ 
/*     */ 
/*     */       
/* 315 */       if (this.working) {
/* 316 */         Config.warn("UpdateThread still working in unpause()!!!");
/*     */       }
/* 318 */       if (this.currentRenderer == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 324 */       while (this.currentRenderer != null) {
/*     */         
/* 326 */         this.canWork = false;
/* 327 */         this.canWorkToEndOfUpdate = true;
/*     */         
/* 329 */         this.lock.notifyAll();
/*     */ 
/*     */         
/*     */         try {
/* 333 */           this.lock.wait();
/*     */         }
/* 335 */         catch (InterruptedException e) {}
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 340 */       pause();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkCanWork(IWrUpdateControl uc) {
/* 348 */     Thread.yield();
/*     */     
/* 350 */     synchronized (this.lock) {
/*     */ 
/*     */ 
/*     */       
/* 354 */       while (!this.canWork) {
/*     */         
/* 356 */         if (this.canWorkToEndOfUpdate && this.currentRenderer != null) {
/*     */           break;
/*     */         }
/* 359 */         if (uc != null)
/* 360 */           uc.pause(); 
/* 361 */         this.working = false;
/*     */         
/* 363 */         this.lock.notifyAll();
/*     */ 
/*     */         
/*     */         try {
/* 367 */           this.lock.wait();
/*     */         }
/* 369 */         catch (InterruptedException e) {}
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 374 */       this.working = true;
/* 375 */       if (uc != null) {
/* 376 */         uc.resume();
/*     */       }
/* 378 */       this.lock.notifyAll();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearAllUpdates() {
/* 386 */     synchronized (this.lock) {
/*     */ 
/*     */       
/* 389 */       unpauseToEndOfUpdate();
/*     */       
/* 391 */       for (int i = 0; i < this.updateList.size(); i++) {
/*     */         
/* 393 */         blo wr = this.updateList.get(i);
/* 394 */         wr.q = true;
/* 395 */         wr.isUpdating = false;
/*     */       } 
/*     */       
/* 398 */       this.updateList.clear();
/*     */       
/* 400 */       this.lock.notifyAll();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPendingUpdatesCount() {
/* 408 */     synchronized (this.lock) {
/*     */       
/* 410 */       int count = this.updateList.size();
/* 411 */       if (this.currentRenderer != null) {
/* 412 */         count++;
/*     */       }
/* 414 */       return count;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int resetUpdateCount() {
/* 422 */     synchronized (this.lock) {
/*     */       
/* 424 */       int count = this.updateCount;
/* 425 */       this.updateCount = 0;
/*     */       
/* 427 */       return count;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void terminate() {
/* 435 */     this.terminated = true;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\WrUpdateThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */