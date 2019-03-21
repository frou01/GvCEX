package hmgww2.entity;


import hmggvcmob.entity.PlaneBaseLogic;
import hmgww2.mod_GVCWW2;
import hmgww2.network.WW2MessageKeyPressed;
import hmgww2.network.WW2PacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityUSSR_Fighter extends EntityUSSR_FighterBase
{
	// public int type;
	
    public EntityUSSR_Fighter(World par1World)
    {
	    super(par1World);
	    this.setSize(5f, 5f);
//		nboundingbox = new ModifiedBoundingBox(-20,-20,-20,20,20,20,0,0,-6.27,2.5,5,19);
//		nboundingbox.rot.set(this.bodyRot);
//		proxy.replaceBoundingbox(this,nboundingbox);
//		((ModifiedBoundingBox)this.boundingBox).updateOBB(this.posX,this.posY,this.posZ);
	    ignoreFrustumCheck = true;
	    this.fireCycle1 = 1;
	    baseLogic = new PlaneBaseLogic(worldObj,this);
	    baseLogic.speedfactor = 0.06f;
	    baseLogic.throttle_gearDown = 1.7f;
	    baseLogic.throttle_Max = 4.0f;
	    baseLogic.liftfactor = 0.3f;
	    baseLogic.dragfactor = 0.04f;
	    baseLogic.rollspeed = 0.2f;
	    baseLogic.pitchspeed = 0.2f;
	    baseLogic.yawspeed = 0.2f;
	    baseLogic.stability = 350;
	    baseLogic.soundname = "hmgww2:hmgww2.sound_pera";
     
    }
    
}
