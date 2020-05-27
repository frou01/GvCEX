import java.awt.Rectangle;

public interface TooltipProvider {
  Rectangle getTooltipBounds(bdw parambdw, int paramInt1, int paramInt2);
  
  String[] getTooltipLines(bcb parambcb, int paramInt);
  
  boolean isRenderBorder();
}


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\TooltipProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */