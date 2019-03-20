package hmgww2.entity;


import hmggvcmob.entity.GVCEx;
import hmgww2.mod_GVCWW2;
import hmgww2.network.WW2MessageKeyPressed;
import hmgww2.network.WW2PacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityGER_Tank extends EntityGER_TankBase
{
	// public int type;
	
    public EntityGER_Tank(World par1World)
    {
        super(par1World);
        this.setSize(4F, 2.5F);
        
    }
}
