package hmggvcmob.entity.guerrilla;


import handmadeguns.entity.PlacedGunEntity;
import hmggvcmob.ai.AITankAttack;
import handmadevehicle.entity.parts.ITank;
import handmadevehicle.entity.parts.ModifiedBoundingBox;
import handmadevehicle.entity.parts.logics.LogicsBase;
import handmadevehicle.entity.parts.logics.TankBaseLogic;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;
import java.util.List;

import static hmggvcmob.GVCMobPlus.Guns_HVG;
import static hmggvcmob.GVCMobPlus.cfg_guerrillasrach;
import static hmggvcmob.event.GVCMXEntityEvent.soundedentity;
import static handmadevehicle.Utils.transformVecByQuat;
import static handmadevehicle.Utils.transformVecforMinecraft;

public class GVCEntityJeep extends EntityGBase implements ITank
{
    public float bodyrotationYaw;
    float rotationmotion;
    TankBaseLogic baseLogic = new TankBaseLogic(this,0.24f,0.7f,false,"gvcmob:gvcmob.JeepWheel");
    public Quat4d bodyRot = new Quat4d(0,0,0,1);
    private float throttle;
    ModifiedBoundingBox nboundingbox;
    AITankAttack aiTankAttack;
    public int soundtick = 0;
    Vector3d playerpos = new Vector3d(0,2,0);

    public GVCEntityJeep(World par1World)
    {
        super(par1World);
        this.setSize(3.0F, 2F);
        nboundingbox = new ModifiedBoundingBox(boundingBox.minX,boundingBox.minY,boundingBox.minZ,boundingBox.maxX,boundingBox.maxY,boundingBox.maxZ,
                                                      0,1.25,-1.5,
                                                      3,2.5,4);
        nboundingbox.rot.set(this.bodyRot);
        proxy_HMVehicle.replaceBoundingbox(this,nboundingbox);
        aiTankAttack = new AITankAttack(this,1600,400);
        this.tasks.addTask(1,aiTankAttack);
        canuseAlreadyPlacedGun = false;
    }
    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            Vector3d temp = new Vector3d(this.posX,this.posY,this.posZ);
            Vector3d tempplayerPos = new Vector3d(playerpos);
            tempplayerPos = transformVecByQuat(tempplayerPos,baseLogic.bodyRot);
            transformVecforMinecraft(tempplayerPos);
            temp.add(tempplayerPos);
            this.riddenByEntity.setPosition(temp.x,
                    temp.y,
                    temp.z);
            this.riddenByEntity.posX = temp.x;
            this.riddenByEntity.posY = temp.y;
            this.riddenByEntity.posZ = temp.z;
        }
    }
    public static float getMobScale() {
		return 0.25f;
	}

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(movespeed = 0.3d);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(cfg_guerrillasrach);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
    }
    
    public void addRandomArmor()
    {
        super.addRandomArmor();

        ItemStack gunstack = new ItemStack((Item)Guns_HVG.get(rand.nextInt(Guns_HVG.size())));
        PlacedGunEntity gunEntity = new PlacedGunEntity(worldObj, gunstack);
        gunEntity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        gunEntity.issummonbyMob = true;
        worldObj.spawnEntityInWorld(gunEntity);
        this.riddenByEntity = gunEntity;
        gunEntity.ridingEntity = this;
        GVCEntityGuerrillaMG entityskeleton = new GVCEntityGuerrillaMG(this.worldObj);
        entityskeleton.canuseAlreadyPlacedGun = false;
        entityskeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        this.worldObj.spawnEntityInWorld(entityskeleton);
        gunEntity.riddenByEntity = entityskeleton;
        entityskeleton.ridingEntity = gunEntity;
//        if(this.worldObj.rand.nextInt(9)== 0 && !Guns_HVG.isEmpty())
//        {
//        }
//        else{
//            GVCEntityGuerrillaMG entityskeleton = new GVCEntityGuerrillaMG(this.worldObj);
//            entityskeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
//            entityskeleton.addRandomArmor();
//            this.worldObj.spawnEntityInWorld(entityskeleton);
//            entityskeleton.mountEntity(this);
//        }
    }
    
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
    {
        par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
        addRandomArmor();
        return par1EntityLivingData;
    }
    
    protected void dropFewItems(boolean par1, int par2)
    {
        int var3;
        int var4;

        var3 = this.rand.nextInt(1);

        for (var4 = 0; var4 < var3; ++var4)
        {
        	this.entityDropItem(new ItemStack(Items.iron_ingot, 5), 0.0F);
        }

        var3 = this.rand.nextInt(1);
        for (var4 = 0; var4 < var3; ++var4)
        {
        	this.entityDropItem(new ItemStack(Blocks.emerald_block, 1), 0.0F);
        }

        var3 = this.rand.nextInt(3);
        for (var4 = 0; var4 < var3; ++var4)
        {
        	this.entityDropItem(new ItemStack(Blocks.redstone_block, 1), 0.0F);
        }
    }

    public void onUpdate()
    {
    	super.onUpdate();

        super.onUpdate();
        this.stepHeight = 1.5f;
        if(!this.worldObj.isRemote){
            baseLogic.updateServer();
            this.setAttackTarget(riddenByEntity instanceof EntityLiving ? ((EntityLiving) riddenByEntity).getAttackTarget():null);
            ++this.soundtick;
            if (this.soundtick > 10) {
                this.playSound("gvcmob:gvcmob.tank", 1.20F, 1.0F);
                if(this.getEntityData().getFloat("GunshotLevel")<0.1)
                    soundedentity.add(this);
                this.getEntityData().setFloat("GunshotLevel",1);
                this.soundtick = 0;
            }
        }else{
            baseLogic.updateClient();


            this.renderYawOffset = rotationYaw;
            this.prevRenderYawOffset = prevRotationYaw;
        }
        baseLogic.updateCommon();
        ((ModifiedBoundingBox)this.boundingBox).rot.set(baseLogic.bodyRot);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
    }

    public void jump(){

    }
	public boolean isConverting() {
		return false;
	}
    public boolean attackEntityFrom(DamageSource source, float par2)
    {
        if(this.riddenByEntity != null) {
            if(this.riddenByEntity.riddenByEntity == source.getEntity() || this.riddenByEntity == source.getEntity())
                return false;
        }
        return super.attackEntityFrom(source,par2);
    }
    @Override
    protected void collideWithNearbyEntities()
    {
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(1.20000000298023224D, 0.0D, 1.20000000298023224D));

        if (list != null && !list.isEmpty())
        {
            for (int i = 0; i < list.size(); ++i)
            {
                Entity entity = (Entity)list.get(i);
                if(entity instanceof EntityGBase && !(entity instanceof ITank))
                    if(this.riddenByEntity == null){
                        this.riddenByEntity = entity;
                        entity.ridingEntity = this;
                    }else if(this.riddenByEntity instanceof PlacedGunEntity && this.riddenByEntity.riddenByEntity == null){
                        this.riddenByEntity.riddenByEntity = entity;
                        entity.ridingEntity = this.riddenByEntity;
                    }

            }
        }
    }
    protected String getLivingSound()
    {
        return "mob.skeleton.say";
    }

    protected String getHurtSound()
    {
        return "mob.skeleton.hurt";
    }

    protected String getDeathSound()
    {
        return "mob.skeleton.death";
    }

    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
    {
        this.playSound("mob.skeleton.step", 0.15F, 1.0F);
    }
    @Override
    public float getviewWide() {
        return viewWide;
    }

    @Override
    public boolean canSeeTarget(Entity target) {
        boolean flag;
        flag = canhearsound(target);
        if (!flag) {
            Vec3 lookVec = getLookVec();
            Vec3 toTGTvec = Vec3.createVectorHelper(target.posX - posX, target.posY + target.getEyeHeight() - (posY + getEyeHeight()), target.posZ - posZ);
            toTGTvec = toTGTvec.normalize();
            return lookVec.squareDistanceTo(toTGTvec) < getviewWide() * 1f;
        }else{
            return true;
        }
    }
    public double getMountedYOffset() {
        if(riddenByEntity instanceof PlacedGunEntity && ((PlacedGunEntity) riddenByEntity).gunItem != null){
            return 2.0D - ((PlacedGunEntity) riddenByEntity).gunItem.gunInfo.yoffset;
        }
        return 1.0D;
    }
    public void applyEntityCollision(Entity entity){
        if(!(riddenByEntity == entity || riddenByEntity != null && riddenByEntity.riddenByEntity == entity)) super.applyEntityCollision(entity);
    }
    @Override
    public boolean canhearsound(Entity target) {
        boolean flag;
        double dist = getDistanceToEntity(target);
        flag = dist < target.getEntityData().getFloat("GunshotLevel") * 10;
        return flag;
    }


    @Override
    public void setspawnedtile(TileEntity flag) {
        spawnedtile = flag;
    }


    @Override
    public boolean is_this_entity_friend(Entity entity) {
        return false;
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
        return bodyrotationYaw;
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(26, Float.valueOf(0));
        this.dataWatcher.addObject(27, Float.valueOf(0));
        this.dataWatcher.addObject(28, Float.valueOf(0));
        this.dataWatcher.addObject(29, Float.valueOf(0));
        this.dataWatcher.addObject(30, Float.valueOf(0));
    }
    public void setRotationYaw(float floats) {
        this.dataWatcher.updateObject(26, Float.valueOf(floats));
    }
    public float getRotationYaw() {
        return this.dataWatcher.getWatchableObjectFloat(26);
    }

    public void setRotationPitch(float floats) {
        this.dataWatcher.updateObject(27, Float.valueOf(floats));
    }
    public float getRotationPitch() {
        return this.dataWatcher.getWatchableObjectFloat(27);
    }

    public void setRotationRoll(float floats) {
        this.dataWatcher.updateObject(28, Float.valueOf(floats));
    }
    public float getRotationRoll() {
        return this.dataWatcher.getWatchableObjectFloat(28);
    }

    public void setTurretrotationYaw(float floats) {
        this.dataWatcher.updateObject(29, Float.valueOf(floats));
    }
    public float getTurretrotationYaw() {
        return this.dataWatcher.getWatchableObjectFloat(29);
    }

    public void setTurretrotationPitch(float floats) {
        this.dataWatcher.updateObject(30, Float.valueOf(floats));
    }
    public float getTurretrotationPitch() {
        return this.dataWatcher.getWatchableObjectFloat(30);
    }
    
    @Override
    public void setrotationYawmotion(float value) {
        rotationmotion = value;
    }

    @Override
    public void setBodyRot(Quat4d rot) {
        bodyRot.set(rot);
    }

    @Override
    public float getthrottle() {
        return throttle;
    }

    @Override
    public void setthrottle(float value) {
        throttle = value;
    }


    @Override
    public void subFireToTarget(Entity target) {

    }

    @Override
    public void mainFireToTarget(Entity target) {

    }
    
    @Override
    public void mainFire() {
    
    }
    
    @Override
    public void subFire() {
    
    }
    
    @Override
    public TurretObj getMainTurret() {
        return null;
    }

    @Override
    public TurretObj[] getTurrets() {
        return new TurretObj[0];
    }

    @Override
    public LogicsBase getBaseLogic() {
        return baseLogic;
    }

    public void moveFlying(float p_70060_1_, float p_70060_2_, float p_70060_3_){
        baseLogic.moveFlying(p_70060_1_,p_70060_2_,p_70060_3_);
    }
    
    public void setPosition(double x, double y, double z)
    {
        super.setPosition(x,y,z);
        if(baseLogic != null)baseLogic.setPosition(x,y,z);
    }
    
    @Override
    public int getMobMode() {
        return 0;
    }
    
    @Override
    public double[] getTargetpos() {
        return new double[0];
    }
    
    @Override
    public boolean standalone() {
        return true;
    }
    
    
    @Override
    public void moveEntity(double x, double y, double z){
        baseLogic.moveEntity(x,y,z);
    }
    
    @Override
    public void updateFallState_public(double stepHeight, boolean onground){
        this.updateFallState(stepHeight,onground);
    }
    
    @Override
    public void func_145775_I_public() {
        this.func_145775_I();
    }
}
