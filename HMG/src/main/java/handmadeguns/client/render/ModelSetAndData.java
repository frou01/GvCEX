package handmadeguns.client.render;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

public class ModelSetAndData {
    public IModelCustom model;
    public ResourceLocation texture;
    public float scale;
    public ModelSetAndData(IModelCustom modeldata,ResourceLocation texturedata,float scalesetting){
        model = modeldata;
        texture = texturedata;
        scale = scalesetting;
    }
}
