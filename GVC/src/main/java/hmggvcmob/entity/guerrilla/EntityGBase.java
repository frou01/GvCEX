package hmggvcmob.entity.guerrilla;

import net.minecraft.world.World;

import java.util.Random;

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
