package handmadeguns.entity.bullets;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.List;

//import littleMaidMobX.LMM_EntityLittleMaid;
//import littleMaidMobX.LMM_EntityLittleMaidAvatar;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import handmadeguns.HMGAddBullets;
import handmadeguns.HMGPacketHandler;
import handmadeguns.Util.SoundInfo;
import handmadeguns.Util.TrailInfo;
import handmadeguns.Util.sendEntitydata;
import handmadeguns.entity.EntityHasMaster;
import handmadeguns.entity.PlacedGunEntity;
import handmadeguns.entity.SpHitCheckEntity;
import handmadeguns.network.PacketFixClientbullet;
import handmadeguns.network.PacketSpawnParticle;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.*;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import static handmadeguns.HMGAddBullets.soundlist;
import static handmadeguns.HandmadeGunsCore.*;
import static handmadeguns.network.PacketShotBullet.fromObject;
import static handmadeguns.network.PacketShotBullet.toObject;
import static java.lang.Math.*;

public class HMGEntityBulletBase extends Entity implements IEntityAdditionalSpawnData
{
    private static final boolean isDebugMessage = false;
	public Entity thrower;
	protected Block inBlock;
    public boolean noex;
    public Entity hitedentity;
    protected int xTile = -1;
    protected int yTile = -1;
    protected int zTile = -1;
    private Block inTile;
    public boolean inGround;
    protected double damage;
    public double knockbackXZ = 0.0001;
    public double knockbackY = 0;
    public float resistance = 0.9999f;
    public float acceleration = 0f;
    public String flyingSound = "handmadeguns:handmadeguns.bulletflyby";
    public float flyingSoundLV = 2f;
    public float flyingSoundSP = 1f;
    public float flyingSoundminspeed = 3f;
    public float flyingSoundmaxdist = 3f;
    //public int fuse;

    /**
     * Is the entity that throws this 'thing' (snowball, ender pearl, eye of ender or potion)
     */
    protected int ticksInGround;
    private int ticksInAir;
    public int fuse=0;
	
	protected int Bdamege;
	public float ex;
	public boolean canex = cfg_blockdestroy;
	public boolean canbounce = false;
    public float bouncerate = 0.2f;
    public float bouncelimit = 45;
	public float gra = 0.029f;

    public String bulletTypeName = "default";
    public int modelid = -1;
    Vec3 lockedpos;
    public Entity homingEntity;
    public Vec3 lockedBlockPos;
    public float induction_precision = 0.1f;
    public float seekerwidth = 15f;
    public int soundcool;
    public double disttoPlayer = -1;
    public double prevdisttoPlayer = -1;

    public double[] prepos = new double[3];

    public boolean trail = false;
    public int traillength;
    public float   trailWidth         = 0.2f;
    public String  trailtexture      = null;
    public String smoketexture = null;
    public float  smokeWidth         = 1f;
    public int     smoketime          = 10;
    public boolean trailglow = true;
    public boolean smokeglow = true;
    
    public boolean soundstoped = true;


    //int i = mod_IFN_GuerrillaVsCommandGuns.RPGExplosiontime;
	protected void entityInit() {

	}

	public HMGEntityBulletBase(World par1World)
    {
        super(par1World);
        renderDistanceWeight = 4096;
        if (worldObj != null) {
            isImmuneToFire = !worldObj.isRemote;
        }
        this.setSize(0.25F, 0.25F);
        //this.fuse = 30;
    }

    public HMGEntityBulletBase(World par1World, Entity par2Entity, int damege, float bspeed, float bure,String bulletTypeName)
    {
        super(par1World);
        this.thrower = par2Entity;
        this.setSize(0.25F, 0.25F);
        this.setLocationAndAngles(par2Entity.posX, par2Entity.posY + (double)par2Entity.getEyeHeight()*0.85, par2Entity.posZ, (par2Entity instanceof EntityLivingBase ? ((EntityLivingBase)par2Entity).rotationYawHead : par2Entity.rotationYaw), par2Entity.rotationPitch);
        Vec3 look = getLook(1.0f,par2Entity);
        if(look != null) {
            this.posX = par2Entity.posX + look.xCoord;
            this.posY = par2Entity.posY + look.yCoord + par2Entity.getEyeHeight();
            this.posZ = par2Entity.posZ + look.zCoord;
            this.motionX = look.xCoord;
            this.motionZ = look.yCoord;
            this.motionY = look.zCoord;
        }
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        //this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.func_70182_d(), 1.0F);
        //this.fuse = 30;
        this.Bdamege = damege;
        //this.Bspeed = bspeed;
        //this.Bure = bure;
        setHeadingFromThrower(thrower,thrower.rotationPitch,(thrower instanceof EntityLivingBase ? ((EntityLivingBase)thrower).rotationYawHead : thrower.rotationYaw),0,bspeed,bure);
        this.bulletTypeName = bulletTypeName;
    };
	public void setdamage(int value){
        Bdamege = value;
    }
    public void setcanex(boolean value){
        canex = value;
    }

    public HMGEntityBulletBase(World par1World, double par2, double par4, double par6)
    {

        super(par1World);
        this.ticksInGround = 0;
        this.setSize(0.25F, 0.25F);
        this.setPosition(par2, par4, par6);
        this.yOffset = 0.0F;
        //this.fuse = 30;
    }

    public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8)
    {
        par8 /=2;
        float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
        par1 /= (double)f2;
        par3 /= (double)f2;
        par5 /= (double)f2;
        par1 += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * (double)par8;
        par3 += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * (double)par8;
        par5 += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * (double)par8;
        par1 *= (double)par7;
        par3 *= (double)par7;
        par5 *= (double)par7;
        this.motionX = par1;
        this.motionY = par3;
        this.motionZ = par5;
        float f3 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
        this.prevRotationYaw = this.rotationYaw = (float)(atan2(par1, par5) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(atan2(par3, (double)f3) * 180.0D / Math.PI);
        this.ticksInGround = 0;
    }
    
    @Override
	public void setVelocity(double par1, double par3, double par5) {
		this.motionX = par1;
		this.motionY = par3;
		this.motionZ = par5;
		
		{
			float var7 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
			this.prevRotationYaw = this.rotationYaw = (float)(atan2(par1, par5) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float)(atan2(par3, (double)var7) * 180.0D / Math.PI);
			this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			this.ticksInGround = 0;
		}
	}

    public void setHeadingFromThrower(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy)
    {
        Vec3 look = getLook(1.0f,entityThrower);
        if(look == null) {
        }else {
//            this.motionX = (look.xCoord+this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.00249999994 * (double)inaccuracy) * velocity;
//            this.motionY = (look.yCoord+this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.00249999994 * (double)inaccuracy) * velocity;
//            this.motionZ = (look.zCoord+this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.00249999994 * (double)inaccuracy) * velocity;
            this.setThrowableHeading(look.xCoord, look.yCoord, look.zCoord, velocity, inaccuracy);
            this.prevRotationPitch = this.rotationPitch = entityThrower.rotationPitch;
        }
    }

    public void setHeadingFromThrower(EntityLivingBase entityThrower, float velocity, float inaccuracy)
    {
        Vec3 look = getLook(1.0f,entityThrower);
        if(look == null) {
        }else {
            this.setThrowableHeading(look.xCoord, look.yCoord, look.zCoord, velocity, inaccuracy);
            this.prevRotationPitch = this.rotationPitch = entityThrower.rotationPitch;
        }
    }
    public void setHeadingFromThrower(float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy)
    {
//            this.motionX = (look.xCoord+this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.00249999994 * (double)inaccuracy) * velocity;
//            this.motionY = (look.yCoord+this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.00249999994 * (double)inaccuracy) * velocity;
//            this.motionZ = (look.zCoord+this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.00249999994 * (double)inaccuracy) * velocity;
        Vec3 look = getLook(1.0f,rotationYawIn,rotationPitchIn);
         this.setThrowableHeading(look.xCoord, look.yCoord, look.zCoord, velocity, inaccuracy);
    }
    public void setHeadingFromThrower(float rotationPitchIn, float rotationYawIn, float velocity, float inaccuracy)
    {
//            this.motionX = (look.xCoord+this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.00249999994 * (double)inaccuracy) * velocity;
//            this.motionY = (look.yCoord+this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.00249999994 * (double)inaccuracy) * velocity;
//            this.motionZ = (look.zCoord+this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.00249999994 * (double)inaccuracy) * velocity;
        Vec3 look = getLook(1.0f,rotationYawIn,rotationPitchIn);
         this.setThrowableHeading(look.xCoord, look.yCoord, look.zCoord, velocity, inaccuracy);
    }
    
    
    /*protected float func_70182_d()
    {
        return 5F;
    }*/
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float par1)
    {
        return 15728880;
    }
    
    public float getBrightness(float par1)
    {
        return 15.0F;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
    }

    
    
    public void onUpdate() {
        super.onUpdate();

        if(worldObj.isRemote){
            if(smoketexture != null) {
                int length = 5;
                for(int i=0;i<length;i++) {
                    PacketSpawnParticle packet = new PacketSpawnParticle(posX + motionX/length * i, posY + motionY/length * i, posZ + motionZ/length * i, 0, 0, 0, 1);
                    packet.name = smoketexture;
                    packet.scale = smokeWidth;
                    packet.fuse = smoketime;
                    if (smokeglow) packet.id += 100;
                    proxy.spawnParticles(packet);
                }
            }
            if(soundstoped && motionX * motionX + motionY * motionY + motionZ * motionZ > flyingSoundminspeed * flyingSoundminspeed && flyingSound != null && getDistanceSqToEntity(proxy.getEntityPlayerInstance()) < flyingSoundmaxdist*flyingSoundmaxdist){
                proxy.playsoundatBullet(flyingSound,flyingSoundLV,flyingSoundSP,flyingSoundminspeed,flyingSoundmaxdist,this,true);
                soundstoped = false;
            }
        }
        if(Double.isNaN( this.motionX ) || Double.isNaN( this.motionY ) || Double.isNaN( this.motionZ )){
            this.motionX =this.motionY =this.motionZ =0;
        }
        if(this.thrower  == null){
            setDead();
        }
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        if(this.onGround){
            this.motionX *= 0.3;
            this.motionY *= 0.3;
            this.motionZ *= 0.3;
        }
        this.setSize(0.25F, 0.25F);
        if(!inGround){
            float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float)(atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(atan2(this.motionY, (double)f) * 180.0D / Math.PI);
        }
        if(!worldObj.isRemote) {
            Block block = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
            if (block.getMaterial() != Material.air) {
                block.setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
                AxisAlignedBB axisalignedbb = block.getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);

                if (axisalignedbb != null && axisalignedbb.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ))) {
                    this.inGround = true;
                }
            }
        }
        fuse--;
        if(fuse==0){
            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            onImpact(new MovingObjectPosition((int)posX,(int)posY,(int)posZ,-1,Vec3.createVectorHelper(this.posX,this.posY,this.posZ),true));
            setDead();
            return;
        }
        if (this.inGround)
        {
            if (this.worldObj.getBlock(this.xTile, this.yTile, this.zTile) == this.inBlock)
            {
                ticksInAir = 0;
                this.motionX=
                        this.motionY=
                                this.motionZ=0;
                if(lockedpos != null){
                    this.posX = lockedpos.xCoord;
                    this.posY = lockedpos.yCoord;
                    this.posZ = lockedpos.zCoord;
                }
                if(fuse<0){
                    setDead();
                }
            }
            else
            {
                this.inGround = false;
                this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        } else {
            Vec3 backupmotion = Vec3.createVectorHelper(motionX,motionY,motionZ);
            Vec3 backuppos = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            ++this.ticksInAir;

            double remainingMovelength = backupmotion.lengthVector();
            Vec3 hitedpos = null;
            Vec3 remainingMoveVec = Vec3.createVectorHelper(motionX,motionY,motionZ);
            Vec3 lastpos = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            boolean changemotionflag = false;
            int breakcnt = 0;
            while(remainingMovelength>0.1) {
                Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
                Vec3 vec31 = Vec3.createVectorHelper(this.posX + remainingMoveVec.xCoord, this.posY + remainingMoveVec.yCoord, this.posZ + remainingMoveVec.zCoord);
                MovingObjectPosition movingobjectposition = this.worldObj.func_147447_a(vec3, vec31, false, true, false);
                Block hitblock;
                while (movingobjectposition != null) {
                    hitblock = this.worldObj.getBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
                    if ((hitblock.getMaterial() == Material.plants) || (hitblock.getMaterial() == Material.leaves) || ((
                            hitblock.getMaterial() == Material.glass ||
                                    hitblock instanceof BlockFence ||
                                    hitblock instanceof BlockFenceGate ||
                                    hitblock == Blocks.iron_bars) && rand.nextInt(5) < 2)) {
                        if (breakcnt > 100) {
                            break;
                        }
                        breakcnt++;
                        Vec3 penerater = Vec3.createVectorHelper(remainingMoveVec.xCoord, remainingMoveVec.yCoord, remainingMoveVec.zCoord);
                        penerater = penerater.normalize();
                        boolean flag =
                                ((this.posX + remainingMoveVec.xCoord - movingobjectposition.hitVec.xCoord)<0 && (this.posX + remainingMoveVec.xCoord - movingobjectposition.hitVec.xCoord-penerater.xCoord)>0) || ((this.posX + remainingMoveVec.xCoord - movingobjectposition.hitVec.xCoord)>0 && (this.posX + remainingMoveVec.xCoord - movingobjectposition.hitVec.xCoord-penerater.xCoord)<0) &&
                                ((this.posY + remainingMoveVec.yCoord - movingobjectposition.hitVec.yCoord)<0 && (this.posY + remainingMoveVec.yCoord - movingobjectposition.hitVec.yCoord-penerater.yCoord)>0) || ((this.posY + remainingMoveVec.yCoord - movingobjectposition.hitVec.yCoord)>0 && (this.posY + remainingMoveVec.yCoord - movingobjectposition.hitVec.yCoord-penerater.yCoord)<0) &&
                                ((this.posZ + remainingMoveVec.zCoord - movingobjectposition.hitVec.zCoord)<0 && (this.posZ + remainingMoveVec.zCoord - movingobjectposition.hitVec.zCoord-penerater.zCoord)>0) || ((this.posZ + remainingMoveVec.zCoord - movingobjectposition.hitVec.zCoord)>0 && (this.posZ + remainingMoveVec.zCoord - movingobjectposition.hitVec.zCoord-penerater.zCoord)<0);
                        if(flag){
                            movingobjectposition = null;
                            break;
                        }
                        vec3 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord + penerater.xCoord, movingobjectposition.hitVec.yCoord + penerater.yCoord, movingobjectposition.hitVec.zCoord + penerater.zCoord);
                        vec31 = Vec3.createVectorHelper(this.posX + remainingMoveVec.xCoord, this.posY + remainingMoveVec.yCoord, this.posZ + remainingMoveVec.zCoord);
                        movingobjectposition = this.worldObj.func_147447_a(vec3, vec31, false, true, false);
                    } else {
                        break;
                    }
                }
                breakcnt++;
                if (breakcnt > 50) {//50回も反射するって無いやろお前…
                    inGround = true;
                    System.out.println("debug1" + hitedpos);
                    System.out.println("debug2" + lastpos);
                    System.out.println("debug3" + remainingMoveVec);
                    break;
                }
                vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
                vec31 = Vec3.createVectorHelper(this.posX + remainingMoveVec.xCoord, this.posY + remainingMoveVec.yCoord, this.posZ + remainingMoveVec.zCoord);
                if (movingobjectposition != null) {
                    vec31 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
                }
                float f2;
                float f4;
                double d0 = 0.0D;
                Entity entity = null;
                List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(remainingMoveVec.xCoord, remainingMoveVec.yCoord, remainingMoveVec.zCoord).expand(1.0D, 1.0D, 1.0D));
                double d1 = 0;
                for (int j = 0; j < list.size(); ++j) {
                    Entity entity1 = (Entity) list.get(j);

                    if (entity1.canBeCollidedWith() && (ticksInAir>8 ||
                            (iscandamageentity(entity1))
                    )) {
                        float f = 0.3F;
                        AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double) f, (double) f, (double) f);
                        MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);

                        if (movingobjectposition1 != null) {
                            d1 = vec3.distanceTo(movingobjectposition1.hitVec);

                            if (d1 < d0 || d0 == 0.0D) {
                                entity = entity1;
                                d0 = d1;
                            }
                        }
                    }
                }

                if (entity != null) {
                    d1 = vec3.distanceTo(vec31);
                    vec3.xCoord = vec3.xCoord + (vec31.xCoord - vec3.xCoord) * d0 / d1;
                    vec3.yCoord = vec3.yCoord + (vec31.yCoord - vec3.yCoord) * d0 / d1;
                    vec3.zCoord = vec3.zCoord + (vec31.zCoord - vec3.zCoord) * d0 / d1;

                    movingobjectposition = new MovingObjectPosition(entity);
                    movingobjectposition.hitVec = vec3;
                }
                int hitside = -1;
                Vec3 tohitposVec = Vec3.createVectorHelper(remainingMoveVec.xCoord,remainingMoveVec.yCoord,remainingMoveVec.zCoord);
                if (movingobjectposition != null) {
                    if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && movingobjectposition.entityHit != null) {
                        tohitposVec.xCoord = (double) ((float) (movingobjectposition.hitVec.xCoord - this.posX));
                        tohitposVec.yCoord = (double) ((float) (movingobjectposition.hitVec.yCoord - this.posY));
                        tohitposVec.zCoord = (double) ((float) (movingobjectposition.hitVec.zCoord - this.posZ));
                        Vec3 temp = tohitposVec.normalize();
                        hitedpos = movingobjectposition.hitVec;
                        hitedpos.xCoord += temp.xCoord*2;
                        hitedpos.yCoord += temp.yCoord*2;
                        hitedpos.zCoord += temp.zCoord*2;
                        if(canbounce && !isDead){
                            if(tohitposVec.lengthVector() >0.1) {
                                this.onImpact(movingobjectposition);
                                tohitposVec.xCoord += temp.xCoord;
                                tohitposVec.yCoord += temp.yCoord;
                                tohitposVec.zCoord += temp.zCoord;
                                motionX *= 0.5;
                                motionY *= 0.5;
                                motionZ *= 0.5;
                                changemotionflag = true;
                            }else {
                                this.posX = hitedpos.xCoord;
                                this.posY = hitedpos.yCoord;
                                this.posZ = hitedpos.zCoord;
                            }
                        }else {
                            this.onImpact(movingobjectposition);
                            if(this instanceof HMGEntityBullet_AP){
                                tohitposVec.xCoord += temp.xCoord*1.4;
                                tohitposVec.yCoord += temp.yCoord*1.4;
                                tohitposVec.zCoord += temp.zCoord*1.4;
                                remainingMoveVec.xCoord -= tohitposVec.xCoord;
                                remainingMoveVec.yCoord -= tohitposVec.yCoord;
                                remainingMoveVec.zCoord -= tohitposVec.zCoord;
                            }else {
                                remainingMoveVec.xCoord = 0;
                                remainingMoveVec.yCoord = 0;
                                remainingMoveVec.zCoord = 0;
                            }
                        }
                    } else if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                        this.onImpact(movingobjectposition);
                        this.xTile = movingobjectposition.blockX;
                        this.yTile = movingobjectposition.blockY;
                        this.zTile = movingobjectposition.blockZ;
                        this.inBlock = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
                        hitside = movingobjectposition.sideHit;
                        tohitposVec.xCoord = (double) ((float) (movingobjectposition.hitVec.xCoord - this.posX));
                        tohitposVec.yCoord = (double) ((float) (movingobjectposition.hitVec.yCoord - this.posY));
                        tohitposVec.zCoord = (double) ((float) (movingobjectposition.hitVec.zCoord - this.posZ));

                        f2 = MathHelper.sqrt_double(remainingMoveVec.xCoord * remainingMoveVec.xCoord + remainingMoveVec.zCoord * remainingMoveVec.zCoord + remainingMoveVec.yCoord * remainingMoveVec.yCoord);
                        movingobjectposition.hitVec.xCoord = movingobjectposition.hitVec.xCoord - remainingMoveVec.xCoord / f2 * 0.25;
                        movingobjectposition.hitVec.yCoord = movingobjectposition.hitVec.yCoord - remainingMoveVec.yCoord / f2 * 0.25;
                        movingobjectposition.hitVec.zCoord = movingobjectposition.hitVec.zCoord - remainingMoveVec.zCoord / f2 * 0.25;
                        this.inGround = true;
                        hitedpos = movingobjectposition.hitVec;
                        lockedpos = movingobjectposition.hitVec;
                        if (canbounce) {
                            switch (hitside) {
                                case 0:
                                case 1://Y面
                                    if (atan2(sqrt(motionY * motionY), sqrt(motionX * motionX + motionZ * motionZ)) > toRadians(bouncelimit))
                                        canbounce = false;
                                    break;
                                case 2:
                                case 3://Z面
                                    if (atan2(sqrt(motionZ * motionZ), sqrt(motionX * motionX + motionY * motionY)) > toRadians(bouncelimit))
                                        canbounce = false;
                                    break;
                                case 4:
                                case 5://X MAN
                                    if (atan2(sqrt(motionX * motionX), sqrt(motionY * motionY + motionZ * motionZ)) > toRadians(bouncelimit))
                                        canbounce = false;
                                    break;
                            }
                        }
                        if (this.inBlock.getMaterial() != Material.air) {
                            this.inBlock.onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
                        }
                    }
                    remainingMoveVec.xCoord = remainingMoveVec.xCoord - tohitposVec.xCoord;
                    remainingMoveVec.yCoord = remainingMoveVec.yCoord - tohitposVec.yCoord;
                    remainingMoveVec.zCoord = remainingMoveVec.zCoord - tohitposVec.zCoord;
                    if (canbounce) {
                        switch (hitside) {
                            case 0:
                            case 1:
                                remainingMoveVec.yCoord = -remainingMoveVec.yCoord * bouncerate;
                                motionY = -motionY * bouncerate;
                                changemotionflag = true;
                                break;
                            case 2:
                            case 3:
                                remainingMoveVec.zCoord = -remainingMoveVec.zCoord * bouncerate;
                                motionZ = -motionZ * bouncerate;
                                changemotionflag = true;
                                break;
                            case 4:
                            case 5:
                                remainingMoveVec.xCoord = -remainingMoveVec.xCoord * bouncerate;
                                motionX = -motionX * bouncerate;
                                changemotionflag = true;
                                break;
                        }
                        inGround = false;
                    }else {
                        remainingMoveVec.xCoord =
                                remainingMoveVec.yCoord =
                                        remainingMoveVec.zCoord =0;
                    }
                    lastpos = Vec3.createVectorHelper(this.posX + tohitposVec.xCoord,
                            this.posY + tohitposVec.yCoord,
                            this.posZ + tohitposVec.zCoord);
                }else {
                    lastpos = Vec3.createVectorHelper(this.posX + tohitposVec.xCoord,
                            this.posY + tohitposVec.yCoord,
                            this.posZ + tohitposVec.zCoord);
                    remainingMoveVec = Vec3.createVectorHelper(0,0,0);
                }
                remainingMovelength = remainingMoveVec.lengthVector();
                if(hitedpos != null) {
                    this.posX = hitedpos.xCoord;
                    this.posY = hitedpos.yCoord;
                    this.posZ = hitedpos.zCoord;
                }
            }
            this.posX = lastpos.xCoord;
            this.posY = lastpos.yCoord;
            this.posZ = lastpos.zCoord;
            if(changemotionflag) HMGPacketHandler.INSTANCE.sendToAll(new PacketFixClientbullet(this.getEntityId(), this));
//            if(inGround && canbounce){
//                this.motionX = backupmotion.xCoord;
//                this.motionY = backupmotion.yCoord;
//                this.motionZ = backupmotion.zCoord;
//                switch (hitside) {
//                    case 0:
//                    case 1:
//                        this.motionY = -backupmotion.yCoord * bouncerate;
//                        break;
//                    case 2:
//                    case 3:
//                        this.motionZ = -backupmotion.zCoord * bouncerate;
//                        break;
//                    case 4:
//                    case 5:
//                        this.motionX = -backupmotion.xCoord * bouncerate;
//                        break;
//                }
//                if(!this.worldObj.isRemote) HMGPacketHandler.INSTANCE.sendToAll(new PacketFixClientbullet(this.getEntityId(),this));
//                //方向転換したのでモーション値をクライアントに送信
//                inGround = false;
//                hitedpos = null;
//            }
            float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float) (atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

            for (this.rotationPitch = (float) (atan2(this.motionY, (double) f2) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
                ;
            }

            while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
                this.prevRotationPitch += 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
                this.prevRotationYaw -= 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
                this.prevRotationYaw += 360.0F;
            }

            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
            float f3 = resistance;

            if (this.isInWater()) {
                for (int l = 0; l < 4; ++l) {
                    float f4 = 0.25F;
                    this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double) f4, this.posY - this.motionY * (double) f4, this.posZ - this.motionZ * (double) f4, this.motionX, this.motionY, this.motionZ);
                }
                f3 *= 0.8F;
            }

            if (this.isWet()) {
                this.extinguish();
            }
            this.motionX *= (double) f3;
            this.motionY *= (double) f3;
            this.motionZ *= (double) f3;
            f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ + this.motionY * this.motionY);
            if(f2 > 0) {
                this.motionX += motionX / f2 * acceleration;
                this.motionY += motionY / f2 * acceleration;
                this.motionZ += motionZ / f2 * acceleration;
//                worldObj.playSoundAtEntity(this, "handmadeguns:handmadeguns." + flyingSound,flyingSoundLV, flyingSoundSP);
            }
            f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ + this.motionY * this.motionY);
            if(homingEntity != null) {
                Vec3 course = Vec3.createVectorHelper(homingEntity.posX - this.posX, homingEntity.posY + homingEntity.height/2 - this.posY, homingEntity.posZ - this.posZ);
                course = course.normalize();
                backupmotion = Vec3.createVectorHelper(motionX,motionY,motionZ);
                backupmotion = backupmotion.normalize();
                Vec3 axis = backupmotion.crossProduct(course);
                double deg = toDegrees(acos(backupmotion.dotProduct(course)));
                if(abs(deg)<induction_precision) backupmotion = course;
                else backupmotion = RotationVector_byAxisVector(axis,backupmotion, induction_precision);
                this.motionX = backupmotion.xCoord * f2;
                this.motionY = backupmotion.yCoord * f2;
                this.motionZ = backupmotion.zCoord * f2;
                if(worldObj.isRemote && homingEntity == FMLClientHandler.instance().getClient().thePlayer && soundcool<0) {
                    proxy.playsoundat("handmadeguns:handmadeguns.warn",1,1,1,(float) homingEntity.posX,(float)homingEntity.posY,(float)homingEntity.posZ);
                    worldObj.playSoundAtEntity(homingEntity,"handmadeguns:handmadeguns.warn", 1, 1);
                    soundcool = 4;
                }
                soundcool--;
                NBTTagCompound targetnbt = homingEntity.getEntityData();
                if(targetnbt !=null){
                    targetnbt.setBoolean("behome",true);
                    if(targetnbt.getBoolean("flare")) homingEntity =null;
                }
                if(!worldObj.isRemote) HMGPacketHandler.INSTANCE.sendToAll(new PacketFixClientbullet(this.getEntityId(),this));

                if(abs(deg)>seekerwidth)homingEntity = null;
            }
            if(lockedBlockPos != null){
                Vec3 course = Vec3.createVectorHelper(lockedBlockPos.xCoord + 0.5 - this.posX,lockedBlockPos.yCoord + 0.5 - this.posY,lockedBlockPos.zCoord + 0.5 - this.posZ);
                course = course.normalize();
                backupmotion = Vec3.createVectorHelper(motionX,motionY,motionZ);
                backupmotion = backupmotion.normalize();
                Vec3 axis = backupmotion.crossProduct(course);
                double deg = toDegrees(acos(backupmotion.dotProduct(course)));
                if(abs(deg)<induction_precision) backupmotion = course;
                else backupmotion = RotationVector_byAxisVector(axis,backupmotion, induction_precision);
                this.motionX = backupmotion.xCoord * f2;
                this.motionY = backupmotion.yCoord * f2;
                this.motionZ = backupmotion.zCoord * f2;
                HMGPacketHandler.INSTANCE.sendToAll(new PacketFixClientbullet(this.getEntityId(),this));

                if(abs(deg)>seekerwidth)lockedBlockPos = null;
            }
            this.motionY -= (double) gra * cfg_defgravitycof;
            if(inGround && hitedpos != null) {
                this.posX = hitedpos.xCoord;
                this.posY = hitedpos.yCoord;
                this.posZ = hitedpos.zCoord;
                if(inGround) {
                    this.motionX =
                            this.motionY =
                                    this.motionZ = 0;
                    if (!this.worldObj.isRemote)
                        HMGPacketHandler.INSTANCE.sendToAll(new PacketFixClientbullet(this.getEntityId(), this));
                }
            }
        }
        this.func_145775_I();
        this.setPosition(this.posX, this.posY, this.posZ);
//        System.out.println(" " + this + "  " + inGround);
        if(!worldObj.isRemote){
            int chunkxPosition = (int) (this.posX)/16;
            int chunkzPosition = (int) (this.posZ)/16;
            int chunkxPosition2 = (int) (this.posX)/16;
            int chunkzPosition2 = (int) (this.posZ)/16;
//            System.out.println("debug x" + (this.posX + motionX) + " , z" + (this.posZ + motionZ) + " , chunkx" + chunkxPosition + " , chunkz" + chunkzPosition);
            if(!((WorldServer) worldObj).getChunkProvider().chunkExists(chunkxPosition,chunkzPosition)){
//                System.out.println("debug : killed");
                setDead();
            }if(!((WorldServer) worldObj).getChunkProvider().chunkExists(chunkxPosition2,chunkzPosition2)){
//                System.out.println("debug : killed");
                setDead();
            }
        }else{
            if(trail) {
                PacketSpawnParticle packetSpawnParticle = new PacketSpawnParticle(lastTickPosX, lastTickPosY, lastTickPosZ,
                        posX - lastTickPosX,
                        posY - lastTickPosY,
                        posZ - lastTickPosZ, 3);
                packetSpawnParticle.trailwidth = trailWidth;
                packetSpawnParticle.name = trailtexture;
                packetSpawnParticle.fuse = traillength;
                if (trailglow) packetSpawnParticle.id += 100;
                proxy.spawnParticles(packetSpawnParticle);
            }
        }
    }

    public Entity getThrower() {
        return this.thrower;
    }


    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition var1)
    {
        if(worldObj.isRemote && var1.hitVec.distanceTo(Vec3.createVectorHelper(proxy.getEntityPlayerInstance().posX,proxy.getEntityPlayerInstance().posY,proxy.getEntityPlayerInstance().posZ))<5 && var1.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK){
            proxy.playsoundat("handmadeguns:handmadeguns.Ricochet",1,1,1,var1.hitVec.xCoord,var1.hitVec.yCoord,var1.hitVec.zCoord);
        }
    }
    
    
    @Override
	public void setDead() {
		super.setDead();
    }

    public void onBlockDestroyed(int blockX, int blockY, int blockZ) {
		//int bid = worldObj.getBlock(blockX, blockY, blockZ);
		int bmd = worldObj.getBlockMetadata(blockX, blockY, blockZ);
		Block block = worldObj.getBlock(blockX, blockY, blockZ);
		if(block == null) {
			return;
		}
		worldObj.playAuxSFX(2001, blockX, blockY, blockZ, (bmd  << 12));
		boolean flag = worldObj.setBlockToAir(blockX, blockY, blockZ);
		if (block != null && flag) {
			block.onBlockDestroyedByPlayer(worldObj, blockX, blockY, blockZ, bmd);
			
		}
	}
    

	public boolean checkDestroyBlock(MovingObjectPosition var1, int pX, int pY, int pZ, Block pBlock, int pMetadata) {
		if ((pBlock.getMaterial() == Material.glass)
				|| (pBlock instanceof BlockFlowerPot)
				|| (pBlock instanceof BlockTNT)
				|| (pBlock instanceof BlockDoublePlant)
				) {
			return true;
		}
		return false;
	}
    

	public boolean onBreakBlock(MovingObjectPosition var1, int pX, int pY, int pZ, Block pBlock, int pMetadata) {
		this.Debug("destroy: %d, %d, %d", pX, pY, pZ);
		if (pBlock instanceof BlockTNT) {
			removeBlock(pX, pY, pZ, pBlock, pMetadata);
			pBlock.onBlockDestroyedByExplosion(worldObj, pX, pY, pZ, new Explosion(worldObj, getThrower(), pX, pY, pZ, 0.0F));
			return true;
		} else {
			removeBlock(pX, pY, pZ, pBlock, pMetadata);
			pBlock.onBlockDestroyedByPlayer(worldObj, pX, pY, pZ, pMetadata);
			//this.entityDropItem(new ItemStack(pBlock), 1);
			return false;
		}
	}
	
	public static void Debug(String pText, Object... pData) {
		if (isDebugMessage) {
			System.out.println(String.format("GunsBase-" + pText, pData));
		}
	}
	

	protected void removeBlock(int pX, int pY, int pZ, Block pBlock, int pMetadata) {
		worldObj.playAuxSFX(2001, pX, pY, pZ, Block.getIdFromBlock(pBlock) + (pMetadata << 12));
		worldObj.setBlockToAir(pX, pY, pZ);	
	}


    public void explode(double x,double y,double z,float level,boolean candestroy)
    {
        noex = true;
    	if(!worldObj.isRemote){
            this.worldObj.createExplosion(thrower,x,y,z, level, candestroy);
        }

        
    }
    public void writeSpawnData(ByteBuf buffer){
        PacketBuffer lpbuf = new PacketBuffer(buffer);
        lpbuf.writeFloat(bouncerate);
        lpbuf.writeFloat(bouncelimit);
        lpbuf.writeFloat(gra);
        lpbuf.writeFloat(acceleration);
        lpbuf.writeFloat(resistance);
        lpbuf.writeInt(fuse);
        lpbuf.writeBoolean(canbounce);
        try {
            byte[] typename = fromObject(bulletTypeName);
            lpbuf.writeInt(typename.length);
            lpbuf.writeBytes(typename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendEntitydata data = new sendEntitydata(this);
        try {
            lpbuf.writeInt(fromObject(data).length);
            lpbuf.writeBytes(fromObject(data));
        } catch (NotSerializableException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        lpbuf.writeInt(fuse);
        if(thrower != null) {
            lpbuf.writeInt(thrower.getEntityId());
        }else{
            lpbuf.writeInt(-1);
        }

    }
    public void readSpawnData(ByteBuf additionalData){
        PacketBuffer lpbuf = new PacketBuffer(additionalData);
        bouncerate = lpbuf.readFloat();
        bouncelimit = lpbuf.readFloat();
        gra = lpbuf.readFloat();
        acceleration = lpbuf.readFloat();
        resistance = lpbuf.readFloat();
        fuse = lpbuf.readInt();
        canbounce = lpbuf.readBoolean();
        byte[] temp = new byte[lpbuf.readInt()];
        lpbuf.readBytes(temp);
        try {
            bulletTypeName = (String) toObject(temp);
//            System.out.println("debug" + modelname);
            if(HMGAddBullets.indexlist != null&& !HMGAddBullets.indexlist.isEmpty() && bulletTypeName !=null&& !bulletTypeName.isEmpty() && !bulletTypeName.equals("default")) {
                modelid = HMGAddBullets.indexlist.get(bulletTypeName);
                SoundInfo tempsoundinfo = soundlist.get(modelid);
                if(tempsoundinfo != null) {
                    flyingSound = tempsoundinfo.sound;
                    flyingSoundLV = tempsoundinfo.LV;
                    flyingSoundSP = tempsoundinfo.SP;
                    flyingSoundminspeed = tempsoundinfo.MinBltSP;
                    flyingSoundmaxdist = tempsoundinfo.MaxDist;
                }
                TrailInfo temptrailinfo = HMGAddBullets.trailsettings.get(modelid);
                if(temptrailinfo != null){
                    trail = temptrailinfo.enabletrai && rand.nextFloat()<=temptrailinfo.trailProbability;
                    traillength = temptrailinfo.traillength;
                    trailWidth = temptrailinfo.trailWidth;
                    trailtexture = temptrailinfo.trailtexture;
                    smoketexture  = temptrailinfo.smoketexture;
                    smokeWidth = temptrailinfo.smokeWidth;
                    smoketime = temptrailinfo.smoketime;

                    trailglow = temptrailinfo.trailglow;
                    smokeglow = temptrailinfo.smokeglow;
                }
                if(HMGAddBullets.modellist.get(modelid) == null)modelid = -1;
//                System.out.println("modelid " + modelid);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        temp = new byte[lpbuf.readInt()];
        lpbuf.readBytes(temp);
        try{
            sendEntitydata data = (sendEntitydata) toObject(temp);
            this.motionX = data.motionX;
            this.motionY = data.motionY;
            this.motionZ = data.motionZ;
        }catch (OptionalDataException e){
            e.printStackTrace();
        }catch (StreamCorruptedException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        this.fuse = lpbuf.readInt();
        thrower = worldObj.getEntityByID(lpbuf.readInt());
    }
    public static Vec3 getLook(float p_70676_1_,Entity entity)
    {
        float f1;
        float f2;
        float f3;
        float f4;


        if (p_70676_1_ == 1.0F)
        {
            f1 = MathHelper.cos(-(entity instanceof EntityLivingBase ? ((EntityLivingBase)entity).rotationYawHead : entity.rotationYaw) * 0.017453292F - (float)Math.PI);
            f2 = MathHelper.sin(-(entity instanceof EntityLivingBase ? ((EntityLivingBase)entity).rotationYawHead : entity.rotationYaw) * 0.017453292F - (float)Math.PI);
            f3 = -MathHelper.cos(-entity.rotationPitch * 0.017453292F);
            f4 = MathHelper.sin(-entity.rotationPitch * 0.017453292F);
            return Vec3.createVectorHelper((double)(f2 * f3), (double)f4, (double)(f1 * f3));
        }else {
            return null;
        }
    }
    public Vec3 getLook(float p_70676_1_,float rotationYawin,float rotationPitchin)
    {
        float f1;
        float f2;
        float f3;
        float f4;

        if (p_70676_1_ == 1.0F)
        {
            f1 = MathHelper.cos(-rotationYawin * 0.017453292F - (float)Math.PI);
            f2 = MathHelper.sin(-rotationYawin * 0.017453292F - (float)Math.PI);
            f3 = -MathHelper.cos(-rotationPitchin * 0.017453292F);
            f4 = MathHelper.sin(-rotationPitchin * 0.017453292F);
            return Vec3.createVectorHelper((double)(f2 * f3), (double)f4, (double)(f1 * f3));
        }
        else
        {
            f1 = MathHelper.cos(-rotationYawin * 0.017453292F - (float)Math.PI);
            f2 = MathHelper.sin(-rotationYawin * 0.017453292F - (float)Math.PI);
            f3 = -MathHelper.cos(-rotationPitchin * 0.017453292F);
            f4 = MathHelper.sin(-rotationPitchin * 0.017453292F);
            return Vec3.createVectorHelper((double)(f2 * f3)*p_70676_1_, (double)f4*p_70676_1_, (double)(f1 * f3)*p_70676_1_);
        }
    }
    static Vec3 RotationVector_byAxisVector(Vec3 axis,Vec3 tovec, float angle)
    {
        double axisVectorX = axis.xCoord;
        double axisVectorY = axis.yCoord;
        double axisVectorZ = axis.zCoord;
        double toVectorX = tovec.xCoord;
        double toVectorY = tovec.yCoord;
        double toVectorZ = tovec.zCoord;
        double angleRad = (double)angle / 180.0D * Math.PI;
        double sintheta = Math.sin(angleRad);
        double costheta = Math.cos(angleRad);
        double returnVectorX = (axisVectorX * axisVectorX * (1 - costheta) + costheta)               * toVectorX + (axisVectorX * axisVectorY * (1 - costheta) - axisVectorZ * sintheta) * toVectorY + (axisVectorZ * axisVectorX * (1 - costheta) + axisVectorY * sintheta) * toVectorZ;
        double returnVectorY = (axisVectorX * axisVectorY * (1 - costheta) + axisVectorZ * sintheta) * toVectorX + (axisVectorY * axisVectorY * (1 - costheta) + costheta)               * toVectorY + (axisVectorY * axisVectorZ * (1 - costheta) - axisVectorX * sintheta) * toVectorZ;
        double returnVectorZ = (axisVectorZ * axisVectorX * (1 - costheta) - axisVectorY * sintheta) * toVectorX + (axisVectorY * axisVectorZ * (1 - costheta) + axisVectorX * sintheta) * toVectorY + (axisVectorZ * axisVectorZ * (1 - costheta) + costheta)               * toVectorZ;

        return Vec3.createVectorHelper(returnVectorX, returnVectorY, returnVectorZ);
    }
    private boolean iscandamageentity(Entity entity){
        if(entity != thrower) {
            if(entity instanceof SpHitCheckEntity){
                if (((SpHitCheckEntity)entity).isRidingEntity(thrower))
                    return false;
            }
            if(entity instanceof EntityHasMaster && ((EntityHasMaster) entity).getmaster() instanceof SpHitCheckEntity && (((SpHitCheckEntity) ((EntityHasMaster) entity).getmaster()).isRidingEntity(entity) || ((SpHitCheckEntity) ((EntityHasMaster) entity).getmaster()).isRidingEntity(thrower)))return false;
            if(entity.riddenByEntity == thrower
                    || entity.ridingEntity == thrower){
                return false;
            }
            if(entity.riddenByEntity != null && entity.riddenByEntity.riddenByEntity == thrower)return false;
        }else {
            return false;
        }
        return true;
    }

    @Override
    public boolean writeToNBTOptional(NBTTagCompound p_70039_1_)
    {

        return false;
    }
}
