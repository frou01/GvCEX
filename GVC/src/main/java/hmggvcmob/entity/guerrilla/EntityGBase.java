package hmggvcmob.entity.guerrilla;

import handmadeguns.entity.IMGGunner;
import handmadeguns.entity.PlacedGunEntity;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import hmggvcmob.GVCMobPlus;
import hmggvcmob.IflagBattler;
import hmggvcmob.ai.*;
import handmadeguns.entity.IFF;
import hmggvcmob.ai.AIHurtByTarget;
import hmggvcmob.entity.IGVCmob;
import hmggvcmob.entity.friend.EntitySoBases;
import hmggvcmob.entity.friend.GVCEntityFlag;
import hmggvcmob.tile.TileEntityFlag;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

import static hmggvcmob.GVCMobPlus.cfg_guerrillacanusePlacedGun;
import static java.lang.Math.pow;
import static net.minecraft.util.MathHelper.wrapAngleTo180_float;

public class EntityGBase extends EntityGBases {
	//AI等行動関係を共通化しておくクラス
    Random rnd = new Random();
	public int deathTicks;


	public EntityGBase(World par1World) {
		super(par1World);
//		this.targetTasks.addTask(2, new AINearestAttackableTarget(this, EntityLiving.class, 0, false));
	}
    public void onUpdate(){
		super.onUpdate();
	}
    
//    public boolean spawnhight(){
//    	boolean spawn = true;
//
//    	int var1 = MathHelper.floor_double(this.posX);
//        int var2 = MathHelper.floor_double(this.boundingBox.minY);
//        int var3 = MathHelper.floor_double(this.posZ);
//        if(var2 < this.worldObj.getHeightValue(var1, var3) - 5 && this.rand.nextInt(16) != 0){
//        	spawn = false;
//        }
//
//        return spawn;
//    }

	
	/**
	 * Returns true if the newer Entity AI code should be run
	 */


}
