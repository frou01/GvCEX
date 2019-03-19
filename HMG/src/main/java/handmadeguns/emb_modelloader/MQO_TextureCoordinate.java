package handmadeguns.emb_modelloader;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MQO_TextureCoordinate
{
	public float u, v, w;

	public MQO_TextureCoordinate(float u, float v)
	{
		this(u, v, 0F);
	}

	public MQO_TextureCoordinate(float u, float v, float w)
	{
		this.u = u;
		this.v = v;
		this.w = w;
	}
}
