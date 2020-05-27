import java.util.List;

public interface IWrUpdater {
  void initialize();
  
  blo makeWorldRenderer(ahb paramahb, List paramList, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  void preRender(bma parambma, sv paramsv);
  
  void postRender();
  
  boolean updateRenderers(bma parambma, sv paramsv, boolean paramBoolean);
  
  void resumeBackgroundUpdates();
  
  void pauseBackgroundUpdates();
  
  void finishCurrentUpdate();
  
  void clearAllUpdates();
  
  void terminate();
}


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\IWrUpdater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */