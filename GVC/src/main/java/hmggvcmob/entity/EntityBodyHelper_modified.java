package hmggvcmob.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class EntityBodyHelper_modified
{
    /** Instance of EntityLiving. */
    private EntityLivingBase theLiving;
    private int field_75666_b;
    private float field_75667_c;
    private static final String __OBFID = "CL_00001570";

    public EntityBodyHelper_modified(EntityLivingBase p_i1611_1_)
    {
        this.theLiving = p_i1611_1_;
    }

    public void func_75664_a()
    {
        {
            float f = 75.0F;

            if (Math.abs(this.theLiving.rotationYawHead - this.field_75667_c) > 15.0F)
            {
                this.field_75666_b = 0;
                this.field_75667_c = this.theLiving.rotationYawHead;
            }
            else
            {
                ++this.field_75666_b;
                boolean flag = true;

                if (this.field_75666_b > 10)
                {
                    f = Math.max(1.0F - (float)(this.field_75666_b - 10) / 10.0F, 0.0F) * 75.0F;
                }
            }

            this.theLiving.renderYawOffset = this.func_75665_a(this.theLiving.rotationYawHead, this.theLiving.renderYawOffset, f);
        }
    }

    private float func_75665_a(float p_75665_1_, float p_75665_2_, float p_75665_3_)
    {
        float f3 = MathHelper.wrapAngleTo180_float(p_75665_1_ - p_75665_2_);

        if (f3 < -p_75665_3_)
        {
            f3 = -p_75665_3_;
        }

        if (f3 >= p_75665_3_)
        {
            f3 = p_75665_3_;
        }

        return p_75665_1_ - f3;
    }
}