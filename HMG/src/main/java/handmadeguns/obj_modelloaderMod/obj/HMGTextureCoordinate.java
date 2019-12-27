package handmadeguns.obj_modelloaderMod.obj;

public class HMGTextureCoordinate
{
    public float u, v, w;

    public HMGTextureCoordinate(float u, float v)
    {
        this(u, v, 0F);
    }

    public HMGTextureCoordinate(float u, float v, float w)
    {
        this.u = u;
        this.v = v;
        this.w = w;
    }
}