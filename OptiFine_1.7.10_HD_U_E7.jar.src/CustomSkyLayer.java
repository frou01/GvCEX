/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import org.lwjgl.opengl.GL11;
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
/*     */ public class CustomSkyLayer
/*     */ {
/*  24 */   public String source = null;
/*  25 */   private int startFadeIn = -1;
/*  26 */   private int endFadeIn = -1;
/*  27 */   private int startFadeOut = -1;
/*  28 */   private int endFadeOut = -1;
/*  29 */   private int blend = 1;
/*     */   private boolean rotate = false;
/*  31 */   private float speed = 1.0F;
/*     */   
/*  33 */   private float[] axis = DEFAULT_AXIS;
/*     */   
/*  35 */   private RangeListInt days = null;
/*  36 */   private int daysLoop = 8;
/*     */   
/*     */   private boolean weatherClear = true;
/*     */   
/*     */   private boolean weatherRain = false;
/*     */   private boolean weatherThunder = false;
/*  42 */   public ahu[] biomes = null;
/*  43 */   public RangeListInt heights = null;
/*  44 */   private float transition = 1.0F;
/*     */   
/*  46 */   private SmoothFloat smoothPositionBrightness = null;
/*     */   
/*  48 */   public int textureId = -1;
/*  49 */   private ahb lastWorld = null;
/*     */   
/*  51 */   public static final float[] DEFAULT_AXIS = new float[] { 1.0F, 0.0F, 0.0F };
/*     */   
/*     */   private static final String WEATHER_CLEAR = "clear";
/*     */   
/*     */   private static final String WEATHER_RAIN = "rain";
/*     */   
/*     */   private static final String WEATHER_THUNDER = "thunder";
/*     */ 
/*     */   
/*     */   public CustomSkyLayer(Properties props, String defSource) {
/*  61 */     ConnectedParser cp = new ConnectedParser("CustomSky");
/*     */     
/*  63 */     this.source = props.getProperty("source", defSource);
/*  64 */     this.startFadeIn = parseTime(props.getProperty("startFadeIn"));
/*  65 */     this.endFadeIn = parseTime(props.getProperty("endFadeIn"));
/*  66 */     this.startFadeOut = parseTime(props.getProperty("startFadeOut"));
/*  67 */     this.endFadeOut = parseTime(props.getProperty("endFadeOut"));
/*  68 */     this.blend = Blender.parseBlend(props.getProperty("blend"));
/*  69 */     this.rotate = parseBoolean(props.getProperty("rotate"), true);
/*  70 */     this.speed = parseFloat(props.getProperty("speed"), 1.0F);
/*  71 */     this.axis = parseAxis(props.getProperty("axis"), DEFAULT_AXIS);
/*  72 */     this.days = cp.parseRangeListInt(props.getProperty("days"));
/*  73 */     this.daysLoop = cp.parseInt(props.getProperty("daysLoop"), 8);
/*     */     
/*  75 */     List<String> weatherList = parseWeatherList(props.getProperty("weather", "clear"));
/*  76 */     this.weatherClear = weatherList.contains("clear");
/*  77 */     this.weatherRain = weatherList.contains("rain");
/*  78 */     this.weatherThunder = weatherList.contains("thunder");
/*     */     
/*  80 */     this.biomes = cp.parseBiomes(props.getProperty("biomes"));
/*  81 */     this.heights = cp.parseRangeListInt(props.getProperty("heights"));
/*  82 */     this.transition = parseFloat(props.getProperty("transition"), 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> parseWeatherList(String str) {
/*  89 */     List<String> weatherAllowedList = Arrays.asList(new String[] { "clear", "rain", "thunder" });
/*  90 */     List<String> weatherList = new ArrayList<String>();
/*  91 */     String[] weatherStrs = Config.tokenize(str, " ");
/*  92 */     for (int i = 0; i < weatherStrs.length; i++) {
/*     */       
/*  94 */       String token = weatherStrs[i];
/*  95 */       if (!weatherAllowedList.contains(token)) {
/*     */         
/*  97 */         Config.warn("Unknown weather: " + token);
/*     */       }
/*     */       else {
/*     */         
/* 101 */         weatherList.add(token);
/*     */       } 
/*     */     } 
/* 104 */     return weatherList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int parseTime(String str) {
/* 112 */     if (str == null) {
/* 113 */       return -1;
/*     */     }
/* 115 */     String[] strs = Config.tokenize(str, ":");
/* 116 */     if (strs.length != 2) {
/*     */       
/* 118 */       Config.warn("Invalid time: " + str);
/* 119 */       return -1;
/*     */     } 
/* 121 */     String hourStr = strs[0];
/* 122 */     String minStr = strs[1];
/*     */     
/* 124 */     int hour = Config.parseInt(hourStr, -1);
/* 125 */     int min = Config.parseInt(minStr, -1);
/* 126 */     if (hour < 0 || hour > 23 || min < 0 || min > 59) {
/*     */       
/* 128 */       Config.warn("Invalid time: " + str);
/* 129 */       return -1;
/*     */     } 
/*     */     
/* 132 */     hour -= 6;
/* 133 */     if (hour < 0) {
/* 134 */       hour += 24;
/*     */     }
/* 136 */     int time = hour * 1000 + (int)(min / 60.0D * 1000.0D);
/*     */     
/* 138 */     return time;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean parseBoolean(String str, boolean defVal) {
/* 146 */     if (str == null) {
/* 147 */       return defVal;
/*     */     }
/* 149 */     if (str.toLowerCase().equals("true"))
/* 150 */       return true; 
/* 151 */     if (str.toLowerCase().equals("false")) {
/* 152 */       return false;
/*     */     }
/* 154 */     Config.warn("Unknown boolean: " + str);
/*     */     
/* 156 */     return defVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float parseFloat(String str, float defVal) {
/* 165 */     if (str == null) {
/* 166 */       return defVal;
/*     */     }
/* 168 */     float val = Config.parseFloat(str, Float.MIN_VALUE);
/* 169 */     if (val == Float.MIN_VALUE) {
/*     */       
/* 171 */       Config.warn("Invalid value: " + str);
/* 172 */       return defVal;
/*     */     } 
/*     */     
/* 175 */     return val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float[] parseAxis(String str, float[] defVal) {
/* 184 */     if (str == null) {
/* 185 */       return defVal;
/*     */     }
/* 187 */     String[] strs = Config.tokenize(str, " ");
/* 188 */     if (strs.length != 3) {
/*     */       
/* 190 */       Config.warn("Invalid axis: " + str);
/* 191 */       return defVal;
/*     */     } 
/*     */     
/* 194 */     float[] fs = new float[3];
/* 195 */     for (int i = 0; i < strs.length; i++) {
/*     */       
/* 197 */       fs[i] = Config.parseFloat(strs[i], Float.MIN_VALUE);
/* 198 */       if (fs[i] == Float.MIN_VALUE) {
/*     */         
/* 200 */         Config.warn("Invalid axis: " + str);
/* 201 */         return defVal;
/*     */       } 
/* 203 */       if (fs[i] < -1.0F || fs[i] > 1.0F) {
/*     */         
/* 205 */         Config.warn("Invalid axis values: " + str);
/* 206 */         return defVal;
/*     */       } 
/*     */     } 
/*     */     
/* 210 */     float ax = fs[0];
/* 211 */     float ay = fs[1];
/* 212 */     float az = fs[2];
/*     */     
/* 214 */     if (ax * ax + ay * ay + az * az < 1.0E-5F) {
/*     */       
/* 216 */       Config.warn("Invalid axis values: " + str);
/* 217 */       return defVal;
/*     */     } 
/*     */     
/* 220 */     float[] as = { az, ay, -ax };
/*     */     
/* 222 */     return as;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String path) {
/* 231 */     if (this.source == null) {
/*     */       
/* 233 */       Config.warn("No source texture: " + path);
/* 234 */       return false;
/*     */     } 
/*     */     
/* 237 */     this.source = TextureUtils.fixResourcePath(this.source, TextureUtils.getBasePath(path));
/*     */     
/* 239 */     if (this.startFadeIn < 0 || this.endFadeIn < 0 || this.endFadeOut < 0) {
/*     */       
/* 241 */       Config.warn("Invalid times, required are: startFadeIn, endFadeIn and endFadeOut.");
/* 242 */       return false;
/*     */     } 
/*     */     
/* 245 */     int timeFadeIn = normalizeTime(this.endFadeIn - this.startFadeIn);
/*     */     
/* 247 */     if (this.startFadeOut < 0) {
/*     */       
/* 249 */       this.startFadeOut = normalizeTime(this.endFadeOut - timeFadeIn);
/*     */       
/* 251 */       if (timeBetween(this.startFadeOut, this.startFadeIn, this.endFadeIn)) {
/* 252 */         this.startFadeOut = this.endFadeIn;
/*     */       }
/*     */     } 
/* 255 */     int timeOn = normalizeTime(this.startFadeOut - this.endFadeIn);
/*     */     
/* 257 */     int timeFadeOut = normalizeTime(this.endFadeOut - this.startFadeOut);
/*     */     
/* 259 */     int timeOff = normalizeTime(this.startFadeIn - this.endFadeOut);
/*     */     
/* 261 */     int timeSum = timeFadeIn + timeOn + timeFadeOut + timeOff;
/* 262 */     if (timeSum != 24000) {
/*     */       
/* 264 */       Config.warn("Invalid fadeIn/fadeOut times, sum is not 24h: " + timeSum);
/* 265 */       return false;
/*     */     } 
/*     */     
/* 268 */     if (this.speed < 0.0F) {
/*     */       
/* 270 */       Config.warn("Invalid speed: " + this.speed);
/* 271 */       return false;
/*     */     } 
/*     */     
/* 274 */     if (this.daysLoop <= 0) {
/*     */       
/* 276 */       Config.warn("Invalid daysLoop: " + this.daysLoop);
/* 277 */       return false;
/*     */     } 
/*     */     
/* 280 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int normalizeTime(int timeMc) {
/* 288 */     while (timeMc >= 24000)
/* 289 */       timeMc -= 24000; 
/* 290 */     while (timeMc < 0) {
/* 291 */       timeMc += 24000;
/*     */     }
/* 293 */     return timeMc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(ahb world, int timeOfDay, float celestialAngle, float rainStrength, float thunderStrength) {
/* 301 */     float positionBrightness = getPositionBrightness(world);
/*     */     
/* 303 */     float weatherBrightness = getWeatherBrightness(rainStrength, thunderStrength);
/*     */     
/* 305 */     float fadeBrightness = getFadeBrightness(timeOfDay);
/*     */     
/* 307 */     float brightness = positionBrightness * weatherBrightness * fadeBrightness;
/* 308 */     brightness = Config.limit(brightness, 0.0F, 1.0F);
/*     */     
/* 310 */     if (brightness < 1.0E-4F) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 315 */     GL11.glBindTexture(3553, this.textureId);
/*     */     
/* 317 */     Blender.setupBlend(this.blend, brightness);
/*     */     
/* 319 */     GL11.glPushMatrix();
/*     */     
/* 321 */     if (this.rotate) {
/*     */ 
/*     */       
/* 324 */       float angleDayStart = 0.0F;
/* 325 */       if (this.speed != Math.round(this.speed)) {
/*     */ 
/*     */         
/* 328 */         long worldDay = (world.J() + 18000L) / 24000L;
/*     */         
/* 330 */         double anglePerDay = (this.speed % 1.0F);
/*     */         
/* 332 */         double angleDayNow = worldDay * anglePerDay;
/*     */         
/* 334 */         angleDayStart = (float)(angleDayNow % 1.0D);
/*     */       } 
/* 336 */       GL11.glRotatef(360.0F * (angleDayStart + celestialAngle * this.speed), this.axis[0], this.axis[1], this.axis[2]);
/*     */     } 
/*     */     
/* 339 */     bmh tess = bmh.a;
/*     */     
/* 341 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 342 */     GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
/* 343 */     renderSide(tess, 4);
/*     */     
/* 345 */     GL11.glPushMatrix();
/* 346 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 347 */     renderSide(tess, 1);
/* 348 */     GL11.glPopMatrix();
/*     */     
/* 350 */     GL11.glPushMatrix();
/* 351 */     GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
/* 352 */     renderSide(tess, 0);
/* 353 */     GL11.glPopMatrix();
/*     */     
/* 355 */     GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/* 356 */     renderSide(tess, 5);
/*     */     
/* 358 */     GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/* 359 */     renderSide(tess, 2);
/*     */     
/* 361 */     GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/* 362 */     renderSide(tess, 3);
/*     */     
/* 364 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getPositionBrightness(ahb world) {
/* 372 */     if (this.biomes == null && this.heights == null) {
/* 373 */       return 1.0F;
/*     */     }
/* 375 */     float positionBrightness = getPositionBrightnessRaw(world);
/*     */     
/* 377 */     if (this.smoothPositionBrightness == null) {
/* 378 */       this.smoothPositionBrightness = new SmoothFloat(positionBrightness, this.transition);
/*     */     }
/* 380 */     positionBrightness = this.smoothPositionBrightness.getSmoothValue(positionBrightness);
/*     */     
/* 382 */     return positionBrightness;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getPositionBrightnessRaw(ahb world) {
/* 390 */     sv sv = (bao.B()).i;
/* 391 */     if (sv == null) {
/* 392 */       return 0.0F;
/*     */     }
/* 394 */     double posX = ((sa)sv).s;
/* 395 */     double posY = ((sa)sv).t;
/* 396 */     double posZ = ((sa)sv).u;
/*     */     
/* 398 */     if (this.biomes != null) {
/*     */       
/* 400 */       ahu biome = world.a((int)posX, (int)posZ);
/* 401 */       if (biome == null) {
/* 402 */         return 0.0F;
/*     */       }
/* 404 */       if (!Matches.biome(biome, this.biomes)) {
/* 405 */         return 0.0F;
/*     */       }
/*     */     } 
/* 408 */     if (this.heights != null)
/*     */     {
/* 410 */       if (!this.heights.isInRange((int)posY)) {
/* 411 */         return 0.0F;
/*     */       }
/*     */     }
/* 414 */     return 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getWeatherBrightness(float rainStrength, float thunderStrength) {
/* 423 */     float clearBrightness = 1.0F - rainStrength;
/* 424 */     float rainBrightness = rainStrength - thunderStrength;
/* 425 */     float thunderBrightness = thunderStrength;
/*     */     
/* 427 */     float weatherBrightness = 0.0F;
/*     */     
/* 429 */     if (this.weatherClear)
/* 430 */       weatherBrightness += clearBrightness; 
/* 431 */     if (this.weatherRain)
/* 432 */       weatherBrightness += rainBrightness; 
/* 433 */     if (this.weatherThunder) {
/* 434 */       weatherBrightness += thunderBrightness;
/*     */     }
/* 436 */     weatherBrightness = NumUtils.limit(weatherBrightness, 0.0F, 1.0F);
/*     */     
/* 438 */     return weatherBrightness;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getFadeBrightness(int timeOfDay) {
/* 448 */     if (timeBetween(timeOfDay, this.startFadeIn, this.endFadeIn)) {
/*     */       
/* 450 */       int timeFadeIn = normalizeTime(this.endFadeIn - this.startFadeIn);
/* 451 */       int timeDiff = normalizeTime(timeOfDay - this.startFadeIn);
/* 452 */       return timeDiff / timeFadeIn;
/*     */     } 
/*     */     
/* 455 */     if (timeBetween(timeOfDay, this.endFadeIn, this.startFadeOut)) {
/* 456 */       return 1.0F;
/*     */     }
/* 458 */     if (timeBetween(timeOfDay, this.startFadeOut, this.endFadeOut)) {
/*     */       
/* 460 */       int timeFadeOut = normalizeTime(this.endFadeOut - this.startFadeOut);
/* 461 */       int timeDiff = normalizeTime(timeOfDay - this.startFadeOut);
/* 462 */       return 1.0F - timeDiff / timeFadeOut;
/*     */     } 
/*     */     
/* 465 */     return 0.0F;
/*     */   }
/*     */   
/*     */   private void renderSide(bmh tess, int side) {
/* 469 */     double tx = (side % 3) / 3.0D;
/* 470 */     double ty = (side / 3) / 2.0D;
/* 471 */     tess.b();
/* 472 */     tess.a(-100.0D, -100.0D, -100.0D, tx, ty);
/* 473 */     tess.a(-100.0D, -100.0D, 100.0D, tx, ty + 0.5D);
/* 474 */     tess.a(100.0D, -100.0D, 100.0D, tx + 0.3333333333333333D, ty + 0.5D);
/* 475 */     tess.a(100.0D, -100.0D, -100.0D, tx + 0.3333333333333333D, ty);
/* 476 */     tess.a();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActive(ahb world, int timeOfDay) {
/* 487 */     if (world != this.lastWorld) {
/*     */       
/* 489 */       this.lastWorld = world;
/* 490 */       this.smoothPositionBrightness = null;
/*     */     } 
/*     */     
/* 493 */     if (timeBetween(timeOfDay, this.endFadeOut, this.startFadeIn)) {
/* 494 */       return false;
/*     */     }
/* 496 */     if (this.days != null) {
/*     */       
/* 498 */       long time = world.J();
/* 499 */       long timeShift = time - this.startFadeIn;
/*     */       
/* 501 */       while (timeShift < 0L) {
/* 502 */         timeShift += (24000 * this.daysLoop);
/*     */       }
/* 504 */       int day = (int)(timeShift / 24000L);
/* 505 */       int dayOfLoop = day % this.daysLoop;
/*     */       
/* 507 */       if (!this.days.isInRange(dayOfLoop)) {
/* 508 */         return false;
/*     */       }
/*     */     } 
/* 511 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean timeBetween(int timeOfDay, int timeStart, int timeEnd) {
/* 521 */     if (timeStart <= timeEnd) {
/* 522 */       return (timeOfDay >= timeStart && timeOfDay <= timeEnd);
/*     */     }
/* 524 */     return (timeOfDay >= timeStart || timeOfDay <= timeEnd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 533 */     return "" + this.source + ", " + this.startFadeIn + "-" + this.endFadeIn + " " + this.startFadeOut + "-" + this.endFadeOut;
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\CustomSkyLayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */