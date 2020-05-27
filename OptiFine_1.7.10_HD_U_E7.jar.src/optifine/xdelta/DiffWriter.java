package optifine.xdelta;

import java.io.IOException;

public interface DiffWriter {
  void addCopy(int paramInt1, int paramInt2) throws IOException;
  
  void addData(byte paramByte) throws IOException;
  
  void flush() throws IOException;
  
  void close() throws IOException;
}


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\xdelta\DiffWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */