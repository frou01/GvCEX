package handmadeguns.emb_modelloader;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.IModelCustomLoader;
import net.minecraftforge.client.model.ModelFormatException;

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
		return new MQO_MetasequoiaObject(resource);
	}
}
