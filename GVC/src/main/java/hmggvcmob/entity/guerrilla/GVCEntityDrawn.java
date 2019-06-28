package hmggvcmob.entity.guerrilla;

import handmadeguns.entity.bullets.HMGEntityBulletExprode;
import hmggvcmob.GVCMobPlus;
import hmggvcmob.entity.GVCEx;
import hmvehicle.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.cfg_defgravitycof;
import static hmvehicle.Utils.CalculateGunElevationAngle;
import static java.lang.Math.*;
import static java.lang.Math.abs;
import static net.minecraft.util.MathHelper.wrapAngleTo180_float;

public class GVCEntityDrawn extends EntityGBase {
    // public int type;
    public float bodyrotationYaw;
    public float bodyrotationPitch;
    public float bodyrotationRoll;
    public float prevbodyrotationYaw;
    public float prevbodyrotationPitch;
    public float turretrotationYaw;
    public float turretrotationPitch;
    public float prevangletime;
    public double angletime;
    public float rote;
    public int fireCycle2 = 80;
    public int cooltime2;
    public int remainBomb = 4;
    public boolean dir = true;
    public int soundtick;
    public float th;
    public int reEntercnt;

    public GVCEntityDrawn(World par1World)
    {
        super(par1World);
        this.setSize(3F, 2F);
        viewWide = 3f;
        //this.tasks.addTask(1, new AIEntityInvasionFlag(this, 1.0D));
        //  this.tasks.addTask(2, new AIEntityAIWander(this, 1.0D));

    }

    public double getMountedYOffset() {
        return 2.0D;
    }
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(26, new Float((int)0));
        this.dataWatcher.addObject(27, new Float((int)0));
        this.dataWatcher.addObject(28, new Float((int)0));
        this.dataWatcher.addObject(29, new Float((int)0));
        this.dataWatcher.addObject(30, new Float(0));
        this.dataWatcher.addObject(23, new Float((int)0));
    }

    public void setTurretrotationYaw(float floats) {
        this.dataWatcher.updateObject(26, Float.valueOf(floats));
    }
    public float getTurretrotationYaw() {
        return this.dataWatcher.getWatchableObjectFloat(26);
    }
    public void setTurretrotationPitch(float floats) {
        this.dataWatcher.updateObject(27, Float.valueOf(floats));
    }
    public float getTurretrotationPitch() {
        return this.dataWatcher.getWatchableObjectFloat(27);
    }
    public void setRotationYaw(float floats) {
        this.dataWatcher.updateObject(28, Float.valueOf(floats));
    }
    public float getRotationYaw() {
        return this.dataWatcher.getWatchableObjectFloat(28);
    }
    public void setRotationPitch(float floats) {
        this.dataWatcher.updateObject(29, Float.valueOf(floats));
    }
    public float getRotationPitch() {
        return this.dataWatcher.getWatchableObjectFloat(29);
    }
    public void setRotationRoll(float floats) {
        this.dataWatcher.updateObject(23, Float.valueOf(floats));
    }
    public float getRotationRoll() {
        return this.dataWatcher.getWatchableObjectFloat(23);
    }
    public void setAngletime(float floats){
        this.dataWatcher.updateObject(30, Float.valueOf(floats));
    }
    public float getAngletime(){
        return this.dataWatcher.getWatchableObjectFloat(30);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(80.0D);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
    }

    public boolean attackEntityFrom(DamageSource source, float par2)
    {
        this.playSound("random.anvil_land", 0.5F, 1.5F);
        if(source.getEntity() instanceof EntityPlayer)
        {
            if(this.riddenByEntity != null && this.riddenByEntity == source.getEntity()){
                return false;
            }else{
                return super.attackEntityFrom(source, par2);
            }
        }else{
            return super.attackEntityFrom(source, par2);
        }

    }

    public void addRandomArmor()
    {
        super.addRandomArmor();
    }

    public void onUpdate()
    {
        double backmotionY = motionY;
        super.onUpdate();
        motionY = backmotionY;
        if(worldObj.isRemote){
            motionY = 0;
            prevbodyrotationYaw = bodyrotationYaw;
            prevbodyrotationPitch = bodyrotationPitch;
            prevbodyrotationYaw=wrapAngleTo180_float(prevbodyrotationYaw);
            this.bodyrotationYaw = getRotationYaw();
            bodyrotationYaw = wrapAngleTo180_float(bodyrotationYaw);
            if(bodyrotationYaw - prevbodyrotationYaw>180){
                prevbodyrotationYaw -= 360;
            }
            if(bodyrotationYaw - prevbodyrotationYaw<-180){
                prevbodyrotationYaw += 360;
            }
            this.bodyrotationPitch = getRotationPitch();
            prevangletime = (float) angletime;
            this.angletime = getAngletime();
            if(angletime - prevangletime>180){
                prevangletime -= 360;
            }
            if(angletime - prevangletime>-180){
                prevangletime += 360;
            }
            bodyrotationRoll = getRotationRoll();
        }else{
            for(int x = (int)this.boundingBox.minX-1;x<=this.boundingBox.maxX;x++){
                for(int y = (int)this.boundingBox.minY-1;y<=this.boundingBox.maxY+1;y++){
                    for(int z = (int)this.boundingBox.minZ-1;z<=this.boundingBox.maxZ;z++){
                        Block collidingblock = worldObj.getBlock(x,y,z);
                        if(collidingblock.getMaterial() == Material.leaves || collidingblock.getMaterial() == Material.wood || collidingblock.getMaterial() == Material.glass || collidingblock.getMaterial() == Material.cloth){
                            worldObj.setBlockToAir(x,y,z);
                        }
                    }
                }
            }
            ++this.soundtick;
            if(this.soundtick > 3 && this.th >= 1){
                this.playSound("gvcmob:gvcmob.propellerSound", 4F, th);
                this.soundtick = 0;
            }
            double movingspeed = sqrt(this.motionX * this.motionX +
                    this.motionY * this.motionY +
                    this.motionZ * this.motionZ);
            if(this.getHealth() > 0.0F) {
                Entity entity1 = this.getAttackTarget();
                if (this.CanAttack(entity1)) {
                    reEntercnt++;
                    if (reEntercnt > 300) {
                        reEntercnt = -50;
                    }
                    double d5 = entity1.posX - this.posX;
                    double d7 = entity1.posZ - this.posZ;
                    float targetrote = wrapAngleTo180_float(-(float) toDegrees(atan2(d5, d7)));
                    float AngulardifferenceYaw = wrapAngleTo180_float(bodyrotationYaw - targetrote);
                    boolean result1;
                    if (AngulardifferenceYaw<-2) {
                        this.bodyrotationYaw += 2;
                        result1 = false;
                    } else if (AngulardifferenceYaw>2) {
                        this.bodyrotationYaw -= 2;
                        result1 = false;
                    } else {
                        this.bodyrotationYaw = targetrote;
                        result1 = true;
                    }
                    boolean result2 = false;
                    this.bodyrotationPitch = wrapAngleTo180_float(this.bodyrotationPitch);
                    double agl[];
                    agl = CalculateGunElevationAngle(this, entity1, (float) (0.49 * cfg_defgravitycof), (float) movingspeed);
                    float targetpitch = wrapAngleTo180_float((float) -agl[0]);
                    result2 = agl[2] != -1;
                    float AngulardifferencePitch = wrapAngleTo180_float(bodyrotationPitch - targetpitch);
                    if(AngulardifferencePitch<-2){
                        if(bodyrotationPitch < 10){
                            bodyrotationPitch+=2;
                        }
                    } else if (AngulardifferencePitch>2) {
                        if(bodyrotationPitch > -10){
                            bodyrotationPitch-=2;
                        }
                    }else {
                        result2 = true;
                    }
                    result2 &= agl[2] != -1;
                    if (result1 && result2) this.AttackTask((EntityLivingBase) entity1,movingspeed);
                } else {
                    if (reEntercnt <= 0)
                        reEntercnt++;
                    else if(remainBomb>0)
                        this.bodyrotationYaw++;
                }
                int genY = this.worldObj.getHeightValue((int) this.posX, (int) this.posZ) + 20;
                if (this.posY < genY) {
                    th = th + 0.1f;
                    if (this.bodyrotationPitch > -45)
                        this.bodyrotationPitch -= 2;
                } else {
                    if (th < 3.8)
                        th += 0.1;
                    if (th > 3.8)
                        th -= 0.1;
                    if(this.getAttackTarget() != null) {
                        if (bodyrotationPitch > 10)
                            this.bodyrotationPitch -= 2;
                        else if (bodyrotationPitch < -10)
                            this.bodyrotationPitch += 2;
                    }else {
                        if (bodyrotationPitch > 2)
                            this.bodyrotationPitch -= 2;
                        else if (bodyrotationPitch < -2)
                            this.bodyrotationPitch += 2;
                        else
                            this.bodyrotationPitch = 0;
                    }

                }
                ++cooltime2;
            }else {
                this.motionY -= 0.032f;
                if(this.bodyrotationPitch>-90){
                    bodyrotationPitch-=2;
                }
                th--;
            }
            this.motionY -= 0.032f;
            Vec3 bodyvec = this.getbodyVector();
            this.motionX += bodyvec.xCoord * th / 32;
            this.motionY += bodyvec.yCoord * th / 32;
            this.motionZ += bodyvec.zCoord * th / 32;
            Vec3 uppervec = this.getupperVec();
            this.motionX -= uppervec.xCoord * sin(bodyrotationRoll + 90) * sin(bodyrotationPitch + 90) * movingspeed / 8;
            this.motionY -= uppervec.yCoord * movingspeed / 24;
            this.motionZ -= uppervec.zCoord * sin(bodyrotationRoll + 90) * sin(bodyrotationPitch + 90) * movingspeed / 8;
            if (this.angletime < 360) {
                this.angletime = this.angletime + (this.th) * 10;
            } else {
                this.angletime = 0;
            }
            this.motionY *= 0.92;
            this.motionX *= 0.9;
            this.motionZ *= 0.9;
            if (th > 5) {
                th = 5;
            }
            if (th < 0) {
                th = 0;
            }
            if (this.motionY < 0) {
                this.fallDistance = 0;
            }
            setTurretrotationYaw(this.turretrotationYaw);
            setTurretrotationPitch(this.bodyrotationPitch);
            setRotationYaw(bodyrotationYaw);
            setRotationPitch(bodyrotationPitch);
            setRotationRoll(bodyrotationRoll);
            setAngletime((float) angletime);
        }
    }
    public boolean CanAttack(Entity entity){
        return reEntercnt >= 0 && remainBomb > 0 && cooltime2 > this.fireCycle2 && entity != null;
    }

    public void AttackTask(EntityLivingBase entity1,double movingspeed){

        if (cooltime2 > this.fireCycle2 && remainBomb>0) {// 2
            //if ((x < 30 && z < 30))
            double xx11 = 0;
            double zz11 = 0;
            //	xx11 -= MathHelper.sin(this.rotation * 0.01745329252F) * 2.5;
            //	zz11 += MathHelper.cos(this.rotation * 0.01745329252F) * 2.5;
            xx11 -= MathHelper.sin(this.bodyrotationYaw * 0.01745329252F-1.57F) * 2;
            zz11 += MathHelper.cos(this.bodyrotationYaw * 0.01745329252F-1.57F) * 2;
            HMGEntityBulletExprode var3 = new HMGEntityBulletExprode(this.worldObj, this, 10, 0, 3, 2.0F, GVCMobPlus.cfg_blockdestory);
            var3.setLocationAndAngles(this.posX + xx11 * (remainBomb-2), this.posY - 1, this.posZ + zz11 * (remainBomb-2),
                    this.bodyrotationYaw, this.bodyrotationPitch);
            var3.gra = 0.49f;
            this.rotationYaw = this.bodyrotationYaw;
            this.rotationYawHead = this.bodyrotationYaw;
            this.rotationPitch = this.bodyrotationPitch;
            var3.setHeadingFromThrower(this, (float) movingspeed,10);
            var3.flyingSound = "gvcmob:gvcmob.bombdrops";
            var3.flyingSoundLV = 4;
            var3.flyingSoundSP = 1;
            var3.bulletTypeName = "byfrou01_Bomb";
            if (!this.worldObj.isRemote) {
                this.worldObj.spawnEntityInWorld(var3);
            }
            cooltime2 = 0;
            remainBomb--;
        }
    }

    protected void onDeathUpdate() {
        ++this.deathTicks;
        if(this.deathTicks == 1){
            //this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 0F, false);
            GVCEx ex = new GVCEx(this, 3F);
            ex.Ex();
        }
        if(this.onGround && !this.worldObj.isRemote){
            GVCEx ex = new GVCEx(this, 3F);
            ex.Ex();
            this.setDead();
            this.dropItem(new ItemStack(Blocks.gold_block, 0).getItem(), 1);
            this.dropItem(new ItemStack(Blocks.iron_block, 0).getItem(), 1);
            this.dropItem(new ItemStack(Blocks.planks, 0).getItem(), 1);
            this.dropItem(new ItemStack(Blocks.redstone_block, 0).getItem(),1);
            this.dropItem(new ItemStack(Blocks.emerald_block, 0).getItem(),5);
        }
        if (this.deathTicks == 200 && !this.worldObj.isRemote) {
            GVCEx ex = new GVCEx(this, 3F);
            ex.Ex();
            this.setDead();
            this.dropItem(new ItemStack(Blocks.gold_block, 0).getItem(), 1);
            this.dropItem(new ItemStack(Blocks.iron_block, 0).getItem(), 1);
            this.dropItem(new ItemStack(Blocks.planks, 0).getItem(), 1);
            this.dropItem(new ItemStack(Blocks.redstone_block, 0).getItem(),1);
            this.dropItem(new ItemStack(Blocks.emerald_block, 0).getItem(),5);
        }
    }

    public boolean isConverting() {
        return false;
    }

    public Vec3 getbodyVector()
    {
        float f1;
        float f2;
        float f3;
        float f4;


        f1 = MathHelper.cos(-this.bodyrotationYaw * 0.017453292F - (float)Math.PI);
        f2 = MathHelper.sin(-this.bodyrotationYaw * 0.017453292F - (float)Math.PI);
        f3 = -MathHelper.cos(-this.bodyrotationPitch * 0.017453292F);
        f4 = MathHelper.sin(-this.bodyrotationPitch * 0.017453292F);
        return Vec3.createVectorHelper((double)(f2 * f3), (double)f4, (double)(f1 * f3));
    }
    public Vec3 getupperVec()
    {
        Vec3 bodyVector     = Utils.getLook(1,bodyrotationYaw,bodyrotationPitch);
        return Utils.rotationVector_byAxisVector(bodyVector, Utils.getLook(1,bodyrotationYaw+90,0),bodyrotationRoll+90);
    }
    public boolean getCanSpawnHere()
    {
        return super.getCanSpawnHere() && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
    }
}
