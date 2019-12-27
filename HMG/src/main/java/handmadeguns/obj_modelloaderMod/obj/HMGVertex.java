package handmadeguns.obj_modelloaderMod.obj;

public class HMGVertex
{
    public float x, y, z;

    public HMGVertex(float x, float y)
    {
        this(x, y, 0F);
    }

    public HMGVertex(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}