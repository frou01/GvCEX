/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiMessage
/*    */   extends bdw
/*    */ {
/*    */   private bdw parentScreen;
/*    */   private String messageLine1;
/*    */   private String messageLine2;
/* 18 */   private final List listLines2 = Lists.newArrayList();
/*    */   
/*    */   protected String confirmButtonText;
/*    */   
/*    */   private int ticksUntilEnable;
/*    */ 
/*    */   
/*    */   public GuiMessage(bdw parentScreen, String line1, String line2) {
/* 26 */     this.parentScreen = parentScreen;
/* 27 */     this.messageLine1 = line1;
/* 28 */     this.messageLine2 = line2;
/* 29 */     this.confirmButtonText = brp.a("gui.done", new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void b() {
/* 37 */     this.n.add(new bcj(0, this.l / 2 - 74, this.m / 6 + 96, this.confirmButtonText));
/* 38 */     this.listLines2.clear();
/* 39 */     this.listLines2.addAll(this.q.c(this.messageLine2, this.l - 50));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void a(bcb button) {
/* 44 */     Config.getMinecraft().a(this.parentScreen);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(int mouseX, int mouseY, float partialTicks) {
/* 52 */     c();
/* 53 */     a(this.q, this.messageLine1, this.l / 2, 70, 16777215);
/* 54 */     int var4 = 90;
/*    */     
/* 56 */     for (Iterator<String> var5 = this.listLines2.iterator(); var5.hasNext(); var4 += this.q.a) {
/*    */       
/* 58 */       String var6 = var5.next();
/* 59 */       a(this.q, var6, this.l / 2, var4, 16777215);
/*    */     } 
/*    */     
/* 62 */     super.a(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setButtonDelay(int ticksUntilEnable) {
/* 70 */     this.ticksUntilEnable = ticksUntilEnable;
/*    */ 
/*    */     
/* 73 */     for (Iterator<bcb> var2 = this.n.iterator(); var2.hasNext(); var3.l = false)
/*    */     {
/* 75 */       bcb var3 = var2.next();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void e() {
/* 84 */     super.e();
/*    */ 
/*    */     
/* 87 */     if (--this.ticksUntilEnable == 0)
/*    */     {
/* 89 */       for (Iterator<bcb> var1 = this.n.iterator(); var1.hasNext(); var2.l = true)
/*    */       {
/* 91 */         bcb var2 = var1.next();
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\GuiMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */