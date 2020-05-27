package optifine.xdelta;

import java.io.IOException;

public interface SeekableSource {
  void seek(long paramLong) throws IOException;
  
  int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
  
  void close() throws IOException;
  
  long length() throws IOException;
}


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\xdelta\SeekableSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */