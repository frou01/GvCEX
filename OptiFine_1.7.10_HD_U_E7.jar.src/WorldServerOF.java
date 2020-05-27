/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldServerOF
/*     */   extends mt
/*     */ {
/*     */   private NextTickHashSet pendingTickListEntriesHashSet;
/*     */   private TreeSet pendingTickListEntriesTreeSet;
/*  51 */   private List pendingTickListEntriesThisTick = new ArrayList();
/*     */   
/*  53 */   private int lastViewDistance = 0;
/*     */   
/*     */   private boolean allChunksTicked = false;
/*     */   
/*  57 */   public Set setChunkCoordsToTickOnce = new HashSet();
/*     */   
/*  59 */   private Set limitedChunkSet = new HashSet();
/*     */   
/*  61 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldServerOF(MinecraftServer par1MinecraftServer, azc par2iSaveHandler, String par3Str, int par4, ahj par5WorldSettings, qi par6Profiler) {
/*  69 */     super(par1MinecraftServer, par2iSaveHandler, par3Str, par4, par5WorldSettings, par6Profiler);
/*     */     
/*  71 */     fixSetNextTicks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(ahj par1WorldSettings) {
/*  80 */     super.a(par1WorldSettings);
/*     */     
/*  82 */     fixSetNextTicks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fixSetNextTicks() {
/*     */     try {
/*  93 */       Field[] fields = mt.class.getDeclaredFields();
/*     */       
/*  95 */       int posSet = findField(fields, Set.class, 0);
/*  96 */       int posTreeSet = findField(fields, TreeSet.class, posSet);
/*  97 */       int posList = findField(fields, List.class, posTreeSet);
/*     */       
/*  99 */       if (posSet >= 0 && posTreeSet >= 0 && posList >= 0) {
/*     */         
/* 101 */         Field fieldSet = fields[posSet];
/* 102 */         Field fieldTreeSet = fields[posTreeSet];
/* 103 */         Field fieldList = fields[posList];
/*     */         
/* 105 */         fieldSet.setAccessible(true);
/* 106 */         fieldTreeSet.setAccessible(true);
/* 107 */         fieldList.setAccessible(true);
/*     */         
/* 109 */         this.pendingTickListEntriesTreeSet = (TreeSet)fieldTreeSet.get(this);
/* 110 */         this.pendingTickListEntriesThisTick = (List)fieldList.get(this);
/*     */         
/* 112 */         Set oldSet = (Set)fieldSet.get(this);
/*     */         
/* 114 */         if (oldSet instanceof NextTickHashSet) {
/*     */           return;
/*     */         }
/* 117 */         this.pendingTickListEntriesHashSet = new NextTickHashSet(oldSet);
/* 118 */         fieldSet.set(this, this.pendingTickListEntriesHashSet);
/*     */         
/* 120 */         Config.dbg("WorldServer.nextTickSet updated");
/*     */         
/*     */         return;
/*     */       } 
/* 124 */       Config.warn("Error updating WorldServer.nextTickSet");
/*     */     }
/* 126 */     catch (Exception e) {
/*     */       
/* 128 */       Config.warn("Error setting WorldServer.nextTickSet: " + e.getMessage());
/*     */     } 
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
/*     */   private int findField(Field[] fields, Class<?> cls, int startPos) {
/* 141 */     if (startPos < 0) {
/* 142 */       return -1;
/*     */     }
/* 144 */     for (int i = startPos; i < fields.length; i++) {
/*     */       
/* 146 */       Field field = fields[i];
/* 147 */       if (field.getType() == cls) {
/* 148 */         return i;
/*     */       }
/*     */     } 
/* 151 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List a(apx par1Chunk, boolean par2) {
/* 158 */     if (this.pendingTickListEntriesHashSet == null || this.pendingTickListEntriesTreeSet == null || this.pendingTickListEntriesThisTick == null) {
/* 159 */       return super.a(par1Chunk, par2);
/*     */     }
/*     */ 
/*     */     
/* 163 */     ArrayList<ahs> var3 = null;
/* 164 */     agu var4 = par1Chunk.l();
/* 165 */     int var5 = (var4.a << 4) - 2;
/* 166 */     int var6 = var5 + 16 + 2;
/* 167 */     int var7 = (var4.b << 4) - 2;
/* 168 */     int var8 = var7 + 16 + 2;
/*     */     
/* 170 */     for (int var9 = 0; var9 < 2; var9++) {
/*     */       Iterator<ahs> var10;
/*     */ 
/*     */       
/* 174 */       if (var9 == 0) {
/*     */ 
/*     */         
/* 177 */         Set setAll = new TreeSet();
/* 178 */         for (int dx = -1; dx <= 1; dx++) {
/*     */           
/* 180 */           for (int dz = -1; dz <= 1; dz++) {
/*     */             
/* 182 */             Set set = this.pendingTickListEntriesHashSet.getNextTickEntriesSet(var4.a + dx, var4.b + dz);
/* 183 */             setAll.addAll(set);
/*     */           } 
/*     */         } 
/*     */         
/* 187 */         var10 = setAll.iterator();
/*     */       }
/*     */       else {
/*     */         
/* 191 */         var10 = this.pendingTickListEntriesThisTick.iterator();
/*     */         
/* 193 */         if (!this.pendingTickListEntriesThisTick.isEmpty())
/*     */         {
/* 195 */           logger.debug("toBeTicked = " + this.pendingTickListEntriesThisTick.size());
/*     */         }
/*     */       } 
/*     */       
/* 199 */       while (var10.hasNext()) {
/*     */         
/* 201 */         ahs var11 = var10.next();
/*     */         
/* 203 */         if (var11.a >= var5 && var11.a < var6 && var11.c >= var7 && var11.c < var8) {
/*     */           
/* 205 */           if (par2) {
/*     */ 
/*     */             
/* 208 */             this.pendingTickListEntriesHashSet.remove(var11);
/* 209 */             this.pendingTickListEntriesTreeSet.remove(var11);
/*     */             
/* 211 */             var10.remove();
/*     */           } 
/*     */           
/* 214 */           if (var3 == null)
/*     */           {
/* 216 */             var3 = new ArrayList();
/*     */           }
/*     */           
/* 219 */           var3.add(var11);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 224 */     return var3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/* 234 */     super.b();
/*     */     
/* 236 */     if (!Config.isTimeDefault()) {
/* 237 */       fixWorldTime();
/*     */     }
/* 239 */     if (Config.waterOpacityChanged) {
/*     */       
/* 241 */       Config.waterOpacityChanged = false;
/*     */       
/* 243 */       ClearWater.updateWaterOpacity(Config.getGameSettings(), (ahb)this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void o() {
/* 254 */     if (!Config.isWeatherEnabled())
/*     */     {
/*     */       
/* 257 */       fixWorldWeather();
/*     */     }
/*     */     
/* 260 */     super.o();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fixWorldWeather() {
/* 268 */     if (this.x.p() || this.x.n()) {
/*     */ 
/*     */       
/* 271 */       this.x.g(0);
/* 272 */       this.x.b(false);
/* 273 */       k(0.0F);
/*     */       
/* 275 */       this.x.f(0);
/* 276 */       this.x.a(false);
/* 277 */       i(0.0F);
/*     */ 
/*     */       
/* 280 */       q().ah().a((ft)new gv(2, 0.0F));
/*     */       
/* 282 */       q().ah().a((ft)new gv(7, 0.0F));
/*     */       
/* 284 */       q().ah().a((ft)new gv(8, 0.0F));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fixWorldTime() {
/* 292 */     if (this.x.r().a() != 1) {
/*     */       return;
/*     */     }
/* 295 */     long time = J();
/* 296 */     long timeOfDay = time % 24000L;
/* 297 */     if (Config.isTimeDayOnly()) {
/*     */       
/* 299 */       if (timeOfDay <= 1000L)
/* 300 */         b(time - timeOfDay + 1001L); 
/* 301 */       if (timeOfDay >= 11000L)
/* 302 */         b(time - timeOfDay + 24001L); 
/*     */     } 
/* 304 */     if (Config.isTimeNightOnly()) {
/*     */       
/* 306 */       if (timeOfDay <= 14000L)
/* 307 */         b(time - timeOfDay + 14001L); 
/* 308 */       if (timeOfDay >= 22000L) {
/* 309 */         b(time - timeOfDay + 24000L + 14001L);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void g(sa par1Entity) {
/* 317 */     if (canSkipEntityUpdate(par1Entity))
/*     */     {
/*     */       
/* 320 */       if (par1Entity instanceof sv) {
/*     */         
/* 322 */         sv elb = (sv)par1Entity;
/*     */         
/* 324 */         int entityAge = EntityUtils.getEntityAge(elb);
/* 325 */         entityAge++;
/*     */         
/* 327 */         if (elb instanceof yg) {
/*     */           
/* 329 */           float brightness = elb.d(1.0F);
/* 330 */           if (brightness > 0.5F) {
/* 331 */             entityAge += 2;
/*     */           }
/*     */         } 
/* 334 */         EntityUtils.setEntityAge(elb, entityAge);
/*     */         
/* 336 */         if (elb instanceof sw) {
/*     */           
/* 338 */           sw el = (sw)elb;
/*     */           
/* 340 */           EntityUtils.despawnEntity(el);
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */     }
/*     */     
/* 347 */     super.g(par1Entity);
/*     */     
/* 349 */     if (Config.isSmoothWorld()) {
/* 350 */       Thread.currentThread(); Thread.yield();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canSkipEntityUpdate(sa entity) {
/* 359 */     if (!(entity instanceof sv)) {
/* 360 */       return false;
/*     */     }
/* 362 */     sv entityLiving = (sv)entity;
/*     */     
/* 364 */     if (entityLiving.f())
/* 365 */       return false; 
/* 366 */     if (entityLiving.ax > 0) {
/* 367 */       return false;
/*     */     }
/* 369 */     if (entity.aa < 20) {
/* 370 */       return false;
/*     */     }
/* 372 */     if (this.h.size() != 1) {
/* 373 */       return false;
/*     */     }
/* 375 */     sa player = this.h.get(0);
/*     */ 
/*     */     
/* 378 */     double dx = Math.max(Math.abs(entity.s - player.s) - 16.0D, 0.0D);
/* 379 */     double dz = Math.max(Math.abs(entity.u - player.u) - 16.0D, 0.0D);
/* 380 */     double distSq = dx * dx + dz * dz;
/* 381 */     if (entity.a(distSq)) {
/* 382 */       return false;
/*     */     }
/* 384 */     return true;
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
/*     */   protected void C() {
/* 396 */     super.C();
/*     */     
/* 398 */     this.limitedChunkSet.clear();
/*     */     
/* 400 */     int viewDistance = p();
/*     */     
/* 402 */     if (viewDistance <= 10) {
/*     */       return;
/*     */     }
/* 405 */     if (viewDistance != this.lastViewDistance) {
/*     */       
/* 407 */       this.lastViewDistance = viewDistance;
/* 408 */       this.allChunksTicked = false;
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 414 */     if (!this.allChunksTicked) {
/*     */       
/* 416 */       this.allChunksTicked = true;
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 424 */     for (int i = 0; i < this.h.size(); i++) {
/*     */       
/* 426 */       yz player = this.h.get(i);
/* 427 */       int pcx = qh.c(player.s / 16.0D);
/* 428 */       int pcz = qh.c(player.u / 16.0D);
/* 429 */       int dist = 10;
/*     */       
/* 431 */       for (int cx = -dist; cx <= dist; cx++) {
/*     */         
/* 433 */         for (int cz = -dist; cz <= dist; cz++)
/*     */         {
/* 435 */           this.limitedChunkSet.add(new agu(cx + pcx, cz + pcz));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 440 */     if (this.setChunkCoordsToTickOnce.size() > 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 445 */       this.limitedChunkSet.addAll(this.setChunkCoordsToTickOnce);
/* 446 */       this.setChunkCoordsToTickOnce.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChunkToTickOnce(int cx, int cz) {
/* 457 */     int viewDistance = p();
/*     */     
/* 459 */     if (viewDistance <= 10) {
/*     */       return;
/*     */     }
/* 462 */     this.setChunkCoordsToTickOnce.add(new agu(cx, cz));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void g() {
/* 472 */     Set oldSet = this.F;
/*     */     
/* 474 */     if (this.limitedChunkSet.size() > 0) {
/* 475 */       this.F = this.limitedChunkSet;
/*     */     }
/* 477 */     super.g();
/*     */     
/* 479 */     this.F = oldSet;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\WorldServerOF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */