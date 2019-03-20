package hmgww2.entity;


import hmggvcmob.entity.GVCEntityChild;
import hmggvcmob.entity.ImultiRideableVehicle;
import hmggvcmob.entity.Iplane;
import hmggvcmob.entity.PlaneBaseLogic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import javax.vecmath.Quat4d;

public class EntityJPN_FighterBase extends EntityJPNBase implements ImultiRideableVehicle,Iplane
{
	PlaneBaseLogic baseLogic;
	
	
	public int rocket = 2;
	public float maxhealth = 150;
	public float angletime;
	public int fireCycle1;
	public int cooltime;
	public int magazine;
	public float throttle;
	
	public EntityJPN_FighterBase(World par1World)
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
		baseLogic.speedfactor = 0.02f;
		baseLogic.liftfactor = 0.06f;
		baseLogic.dragfactor = 0.0003f;
	}
	
	@Override
	protected void entityInit() {
	}
	
	public double getMountedYOffset() {
		return 0.6D;
	}
	
	
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(120.0D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(80.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
	}
	
	
	public boolean attackEntityFrom(DamageSource source, float par2)
	{
		if(source.getEntity() == this){
			return false;
		}
		if(isRidingEntity(source.getEntity())){
			return false;
		}
		if(par2 <= 10){
			this.playSound("random.anvil_land", 0.5F, 1F);
			return false;
		}else if(par2 > 10 && par2 <= 49){
			this.playSound("random.anvil_land", 0.5F, 1.5F);
			par2 = par2 /2;
		}
		return super.attackEntityFrom(source, par2);
		
	}
	
	public void onUpdate()
	{
		super.onUpdate();
		baseLogic.onUpdate();
	}
	public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_)
	{
		baseLogic.setVelocity(p_70016_1_,p_70016_3_,p_70016_5_);
	}
	@Override
	public int getfirecyclesettings1() {
		return 0;
	}
	
	@Override
	public int getfirecycleprogress1() {
		return 0;
	}
	
	@Override
	public int getfirecyclesettings2() {
		return 0;
	}
	
	@Override
	public int getfirecycleprogress2() {
		return 0;
	}
	
	@Override
	public float getturretrotationYaw() {
		return 0;
	}
	
	@Override
	public float getbodyrotationYaw() {
		return baseLogic.bodyrotationYaw;
	}
	
	@Override
	public float getthrottle() {
		return (float) baseLogic.throttle;
	}
	
	@Override
	public void setBodyRot(Quat4d quat4d) {
		baseLogic.bodyRot = quat4d;
	}
	
	@Override
	public void setthrottle(float th) {
		baseLogic.throttle = th;
	}
	
	@Override
	public void setTrigger(boolean trig1, boolean trig2) {
		baseLogic.trigger1 = trig1;
		baseLogic.trigger2 = trig2;
	}
	
	@Override
	public void initseat() {
	
	}
	
	@Override
	public GVCEntityChild[] getChilds() {
		return baseLogic.childEntities;
	}
	
	@Override
	public void addChild(GVCEntityChild seat) {
		if(seat.idinmasterEntityt != -1 && seat.idinmasterEntityt < baseLogic.childEntities.length){
			baseLogic.childEntities[seat.idinmasterEntityt] = seat;
			baseLogic.childEntities[seat.idinmasterEntityt].master = this;
		}
	}
	
	@Override
	public boolean isRidingEntity(Entity entity) {
		for(int i = 0; i < baseLogic.childEntities.length; i++){
			if(baseLogic.childEntities[i] != null){
				if(baseLogic.childEntities[i].riddenByEntity == entity)return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isChild(Entity entity){
		for(Entity achild:baseLogic.childEntities){
			if(entity == achild)return true;
		}
		return entity == this;
	}
	
	@Override
	public int getpilotseatid() {
		return 0;
	}
	
	public void applyEntityCollision(Entity p_70108_1_)
	{
	}
	@Override
	public boolean shouldRenderInPass(int pass)
	{
		return pass == 1 || pass==0;
	}
	
	@Override
	public PlaneBaseLogic getBaseLogic() {
		return baseLogic;
	}
	
	protected void onDeathUpdate() {
		++this.deathTicks;
		if(this.deathTicks == 1){
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 0F, false);
		}
		if(this.onGround && !this.worldObj.isRemote){
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3.5F, false);
			this.setDead();
			Item i = new ItemStack(Blocks.gold_block, 0).getItem();
			this.dropItem(i, 2);
		}
		if (this.deathTicks == 200 && !this.worldObj.isRemote) {
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3.5F, false);
			this.setDead();
			Item i = new ItemStack(Blocks.gold_block, 0).getItem();
			this.dropItem(i, 2);
		}
	}
	
	public boolean isConverting() {
		return false;
	}
}
