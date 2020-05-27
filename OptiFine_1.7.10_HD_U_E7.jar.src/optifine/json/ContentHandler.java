package optifine.json;

import java.io.IOException;

public interface ContentHandler {
  void startJSON() throws ParseException, IOException;
  
  void endJSON() throws ParseException, IOException;
  
  boolean startObject() throws ParseException, IOException;
  
  boolean endObject() throws ParseException, IOException;
  
  boolean startObjectEntry(String paramString) throws ParseException, IOException;
  
  boolean endObjectEntry() throws ParseException, IOException;
  
  boolean startArray() throws ParseException, IOException;
  
  boolean endArray() throws ParseException, IOException;
  
  boolean primitive(Object paramObject) throws ParseException, IOException;
}


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\json\ContentHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */