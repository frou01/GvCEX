package hmggvcmob.render.model;

import net.minecraft.client.model.ModelBiped;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GVCModelFlag extends ModelBiped
{
    public GVCModelFlag()
    {
        this(0.0F, false);
    }

    protected GVCModelFlag(float par1, float par2, int par3, int par4)
    {
        super(par1, par2, par3, par4);
    }

    public GVCModelFlag(float par1, boolean par2)
    {
        super(par1, 0.0F, 64, par2 ? 32 : 64);
    }

    
}
