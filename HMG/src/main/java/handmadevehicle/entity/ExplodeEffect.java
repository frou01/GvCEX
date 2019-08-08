package handmadevehicle.entity;

import net.minecraft.entity.Entity;

public class ExplodeEffect {

	public Entity base;
	public float[] offset = new float[3];
	public float level;
	
	public ExplodeEffect(Entity en, float f){
		this.base = en;
		this.level = f;
	}
	
	public void Ex(){
		if(this.level >= 2.0F){
			this.base.worldObj.spawnParticle("hugeexplosion", this.base.posX + offset[0], this.base.posY + offset[1], this.base.posZ + offset[2], 1.0D, 0.0D, 0.0D);
		}else{
			this.base.worldObj.spawnParticle("largeexplode", this.base.posX + offset[0], this.base.posY + offset[1], this.base.posZ + offset[2], 1.0D, 0.0D, 0.0D);
		}
        this.base.worldObj.playSoundEffect(this.base.posX + offset[0], this.base.posY + offset[1], this.base.posZ + offset[2], "random.explode",
        		4.0F, (1.0F + (this.base.worldObj.rand.nextFloat() - this.base.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
	}
	
}
