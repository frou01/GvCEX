package handmadeguns.emb_modelloader;

import handmadeguns.client.render.IModelCustom_HMG;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.IModelCustomLoader;
import net.minecraftforge.client.model.ModelFormatException;

import static handmadeguns.ClientProxyHMG.modelList;

public class MQO_ModelLoader implements IModelCustomLoader
{
	@Override
	public String getType()
	{
		return "Metasequoia model";
	}

	private static final String[] types = { "mqo" };

	@Override
	public String[] getSuffixes()
	{
		return types;
	}

	public IModelCustom loadInstance(ResourceLocation resource) throws ModelFormatException
	{
		for(IModelCustom_HMG model:modelList){
			if(model.toString().equals(resource.toString()))return model;
		}
		return new MQO_MetasequoiaObject(resource);
	}
}
