package hmggvcmob.entity.guerrilla;

import hmvehicle.entity.parts.IMultiTurretVehicle;
import hmvehicle.entity.parts.Iplane;
import hmvehicle.entity.parts.ModifiedBoundingBox;
import hmvehicle.entity.parts.SeatInfo;
import hmvehicle.entity.parts.logics.IbaseLogic;
import hmvehicle.entity.parts.logics.PlaneBaseLogic;
import hmvehicle.entity.parts.turrets.TurretObj;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import java.util.ArrayList;

import static hmggvcmob.GVCMobPlus.proxy;
import static hmvehicle.Utils.CalculateGunElevationAngle;
import static hmvehicle.Utils.addAllTurret;

public class GVCEntityWZ10AttackHeli extends EntityGBase implements Iplane,IMultiTurretVehicle
{
    public int homeposX;
    public int homeposY;
    public int homeposZ;
    PlaneBaseLogic baseLogic;
    TurretObj gunnerturret;
    float maxHealth = 150;
    public GVCEntityWZ10AttackHeli(World par1World) {
        super(par1World);
        this.setSize(5f, 5f);
//		nboundingbox = new ModifiedBoundingBox(-20,-20,-20,20,20,20,0,0,-6.27,2.5,5,19);
//		nboundingbox.rot.set(this.bodyRot);
//		proxy.replaceBoundingbox(this,nboundingbox);
//		((ModifiedBoundingBox)this.boundingBox).update(this.posX,this.posY,this.posZ);
        ignoreFrustumCheck = true;
        baseLogic = new PlaneBaseLogic(worldObj, this);
        baseLogic.soundname = "gvcmob:gvcmob.heli";
        baseLogic.soundpitch = 1.2f;
        baseLogic.speedfactor = 0.012f;
        baseLogic.liftfactor = 0.01f;
        baseLogic.flapliftfactor = 0;
        baseLogic.flapdragfactor = 0;
        baseLogic.geardragfactor = 0;
        baseLogic.dragfactor = 0.07f;
        baseLogic.gravity = 0.049f;
        baseLogic.stability2 = 0;
        baseLogic.stability = 0;
        baseLogic.rotmotion_reduceSpeed = 0;
        baseLogic.forced_rudder_effect = 0.99f;
        baseLogic.forced_rotmotion_reduceSpeed = 0.05f;
        baseLogic.slipresist = 0;
        baseLogic.throttle_Max = 5;
        baseLogic.unitThrottle.set(0,-1,0);
        baseLogic.brakedragfactor = 0;
        baseLogic.maxClimb = 0;
        baseLogic.maxDive = 30;
        baseLogic.minALT = 10;
        baseLogic.cruiseALT = 25;
        baseLogic.changeWeaponCycleSetting = 50;
        baseLogic.type_F_Plane_T_Heli = true;
        baseLogic.displayModernHud = true;
        
        {
            TurretObj turret = new TurretObj(worldObj);
            turret.onMotherPos = new Vector3d(0.1260, 0.9463, -6.444);
            turret.turretPitchCenterpos = new Vector3d(0, 0.8189, -6.449);
            turret.turretPitchCenterpos.sub(turret.onMotherPos);
            turret.cannonPos = new Vector3d(0.1260, 0.5770, -7.278);
            turret.cannonPos.sub(turret.onMotherPos);
            turret.turretanglelimtYawMax = 180;
            turret.turretanglelimtYawmin = -180;
            turret.turretanglelimtPitchMax = 90;
            turret.turretanglelimtPitchmin = -15;
            turret.turretspeedY = 20;
            turret.turretspeedP = 20;
//			turret.traverseSound = null;
            turret.currentEntity = this;
            turret.powor = 60;
            turret.ex = 0.1f;
            turret.cycle_setting = 4;
            turret.flashscale = 1;
            turret.flashoffset = 0;
            turret.firesound = "handmadeguns:handmadeguns.HeavyMachineGun";
            turret.spread = 5;
            turret.speed = 8;
            turret.magazineMax = 50;
            turret.magazinerem = 50;
            turret.reloadTimer = 600;
            turret.canex = false;
            turret.guntype = 2;
            gunnerturret = turret;
        }
        {
            TurretObj gun1 = new TurretObj(worldObj);
            gun1.onMotherPos = new Vector3d(2, 1.27, -0.3262);
            gun1.traverseSound = null;
            gun1.currentEntity = this;
            gun1.powor = 24;
            gun1.ex = 0.5f;
            gun1.cycle_setting = 1;
            gun1.flashscale = 1;
            gun1.flashoffset = 0;
            gun1.firesound = "handmadeguns:handmadeguns.HeavyMachineGun";
            gun1.spread = 2;
            gun1.speed = 8;
            gun1.magazineMax = 500;
            gun1.magazinerem = 500;
            gun1.reloadTimer = 1200;
            gun1.canex = false;
            gun1.guntype = 0;
            TurretObj gun2 = new TurretObj(worldObj);
            gun2.onMotherPos = new Vector3d(-2, 1.27, -0.3262);
            gun2.traverseSound = null;
            gun2.currentEntity = this;
            gun2.powor = 24;
            gun2.ex = 0.5f;
            gun2.cycle_setting = 1;
            gun2.flashscale = 1;
            gun2.flashoffset = 0;
            gun2.firesound = "handmadeguns:handmadeguns.HeavyMachineGun";
            gun2.spread = 2;
            gun2.speed = 8;
            gun2.magazineMax = 500;
            gun2.magazinerem = 500;
            gun2.reloadTimer = 1200;
            gun2.canex = false;
            gun2.guntype = 0;
            gun1.addbrother(gun2);
            
            baseLogic.mainTurret = gun1;
        }
        {
            TurretObj missile1 = new TurretObj(worldObj);
            missile1.onMotherPos = new Vector3d(2.0399, 1.0591, -0.6568);
            missile1.traverseSound = null;
            missile1.currentEntity = this;
            missile1.powor = 600;
            missile1.acceler = 0.1f;
            missile1.induction_precision = 2;
            missile1.canHoming = true;
            missile1.rock_to_Vehicle = true;
            missile1.ex = 3;
            missile1.cycle_setting = 1200;
            missile1.cycle_timer = -1;
            missile1.flashscale = 1;
            missile1.firesound = "handmadeguns:handmadeguns.firecannon";
            missile1.spread = 0;
            missile1.speed = 1;
            missile1.magazineMax = 4;
            missile1.magazinerem = 4;
            missile1.reloadTimer = -1;
            missile1.canex = true;
            missile1.fireAll = false;
            missile1.guntype = 3;
            missile1.seekerSize = 30;
    
            TurretObj missile2 = new TurretObj(worldObj);
            missile2.onMotherPos = new Vector3d(-2.0399, 1.0591, -0.6568);
            missile2.traverseSound = null;
            missile2.currentEntity = this;
            missile2.powor = 600;
            missile2.acceler = 0.1f;
            missile2.induction_precision = 2;
            missile2.canHoming = true;
            missile2.rock_to_Vehicle = true;
            missile2.ex = 3;
            missile2.cycle_setting = 1200;
            missile2.cycle_timer = -1;
            missile2.flashscale = 1;
            missile2.firesound = "handmadeguns:handmadeguns.firecannon";
            missile2.spread = 0;
            missile2.speed = 1;
            missile2.magazineMax = 4;
            missile2.magazinerem = 4;
            missile2.reloadTimer = -1;
            missile2.canex = true;
            missile2.fireAll = false;
            missile2.guntype = 3;
            missile2.seekerSize = 30;
    
            missile1.addbrother(missile2);
            baseLogic.subTurret = missile1;
        }
        
        baseLogic.riddenByEntitiesInfo = new SeatInfo[2];
        baseLogic.riddenByEntitiesInfo_zoom = new SeatInfo[2];
        baseLogic.riddenByEntities = new Entity[2];
        baseLogic.riddenByEntitiesInfo[0] = new SeatInfo();
        baseLogic.riddenByEntitiesInfo[0].pos[0] = 0.1074;
        baseLogic.riddenByEntitiesInfo[0].pos[1] = 2.584;
        baseLogic.riddenByEntitiesInfo[0].pos[2] = -4.245;
        
        baseLogic.riddenByEntitiesInfo[1] = new SeatInfo();
        baseLogic.riddenByEntitiesInfo[1].pos[0] = 0.1278;
        baseLogic.riddenByEntitiesInfo[1].pos[1] = 1.726;
        baseLogic.riddenByEntitiesInfo[1].pos[2] = -6.145;
        baseLogic.riddenByEntitiesInfo[1].hasGun = true;
        baseLogic.riddenByEntitiesInfo[1].gun = gunnerturret;
        
        baseLogic.riddenByEntitiesInfo_zoom[1] = new SeatInfo();
        baseLogic.riddenByEntitiesInfo_zoom[1].pos[0] = 0.1278;
        baseLogic.riddenByEntitiesInfo_zoom[1].pos[1] = -2 + 1.726;
        baseLogic.riddenByEntitiesInfo_zoom[1].pos[2] = -6.145;


//		baseLogic.slipresist = 4;
        
        ModifiedBoundingBox nboundingbox = new ModifiedBoundingBox(-1.5,0,-1.5,1.5,5,1.5,0,0,-6.27,2.5,5,19);
        nboundingbox.rot.set(baseLogic.bodyRot);
        proxy.replaceBoundingbox(this,nboundingbox);
        ((ModifiedBoundingBox)this.boundingBox).update(this.posX,this.posY,this.posZ);
        
    }
    
    public double getMountedYOffset() {
        return 0.6D;
    }
    
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(150);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(80.0D);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
    }
    
    public void onUpdate() {
        
        if (this.standalone()) {
            if (!worldObj.isRemote && rand.nextInt(100) == 1) {
                boolean flag = false;
                for (int id = 1; id < (baseLogic).riddenByEntities.length; id++) {
                    if((baseLogic).riddenByEntities[id] == null)flag = true;
                }
                if(flag) {
                    EntityGBase gvcEntityGuerrilla = new GVCEntityGuerrillaMG(worldObj);
                    gvcEntityGuerrilla.setLocationAndAngles(this.posX, this.posY, this.posZ, 0, 0);
                    worldObj.spawnEntityInWorld(gvcEntityGuerrilla);
                    if (!pickupEntity(gvcEntityGuerrilla, 0)) gvcEntityGuerrilla.setDead();
                }
                
            }
        }
        double[] pos = new double[]{this.posX, this.posY, this.posZ};
        double[] motion = new double[]{this.motionX, this.motionY, this.motionZ};
        boolean onground = this.onGround;
        super.onUpdate();
        this.onGround = onground;
        this.posX = pos[0];
        this.posY = pos[1];
        this.posZ = pos[2];
        this.motionX = motion[0];
        this.motionY = motion[1];
        this.motionZ = motion[2];
//		if (!this.standalone() && baseLogic.childEntities[0] != null && baseLogic.childEntities[0].riddenByEntity == null) {
//			baseLogic.throttle--;
//		}
        baseLogic.onUpdate();
    }
    
    public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
//		baseLogic.setVelocity(p_70016_1_,p_70016_3_,p_70016_5_);
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
    
    public boolean interact(EntityPlayer p_70085_1_) {
        return false;
    }
    
    
    @Override
    public int getpilotseatid() {
        return 0;
    }
    @Override
    public void applyEntityCollision(Entity p_70108_1_) {
        boolean flag = p_70108_1_.riddenByEntity != this && p_70108_1_.ridingEntity != this;
        flag &= !isRidingEntity(this);
        if (flag)
        {
            double d0 = p_70108_1_.posX - this.posX;
            double d1 = p_70108_1_.posZ - this.posZ;
            double d2 = MathHelper.abs_max(d0, d1);
            
            if (d2 >= 0.009999999776482582D)
            {
                d2 = (double)MathHelper.sqrt_double(d2);
                d0 /= d2;
                d1 /= d2;
                double d3 = 1.0D / d2;
                
                if (d3 > 1.0D)
                {
                    d3 = 1.0D;
                }
                
                d0 *= d3;
                d1 *= d3;
                d0 *= 0.05000000074505806D;
                d1 *= 0.05000000074505806D;
                d0 *= (double)(1.0F - this.entityCollisionReduction);
                d1 *= (double)(1.0F - this.entityCollisionReduction);
                this.addVelocity(-d0, 0.0D, -d1);
                p_70108_1_.addVelocity(d0, 0.0D, d1);
            }
        }
    }
    
    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 1 || pass == 0;
    }
    
    @Override
    public IbaseLogic getBaseLogic() {
        return baseLogic;
    }
    
    protected void onDeathUpdate() {
        ++this.deathTicks;
        if(this.deathTicks > 40) {
            if (worldObj.isRemote) {
                for (int i = 0; i < 5; i++) {
                    worldObj.spawnParticle("smoke",
                            this.posX + (float) (rand.nextInt(30) - 15) / 10,
                            this.posY + (float) (rand.nextInt(30) - 15) / 10 + 1.5f,
                            this.posZ + (float) (rand.nextInt(30) - 15) / 10,
                            0.0D, 0.2D, 0.0D);
                    worldObj.spawnParticle("cloud",
                            this.posX + (float) (rand.nextInt(30) - 15) / 10,
                            this.posY + (float) (rand.nextInt(30) - 15) / 10 + 1.5f,
                            this.posZ + (float) (rand.nextInt(30) - 15) / 10,
                            0.0D, 0.3D, 0.0D);
                }
            }
        }
        if (this.deathTicks >= 140) {
            for (int i = 0; i < 15; i++) {
                worldObj.spawnParticle("flame",
                        this.posX + (float) (rand.nextInt(20) - 10) / 10,
                        this.posY + (float) (rand.nextInt(20) - 10) / 10,
                        this.posZ + (float) (rand.nextInt(20) - 10) / 10,
                        (rand.nextInt(20) - 10) / 100,
                        (rand.nextInt(20) - 10) / 100,
                        (rand.nextInt(20) - 10) / 100 );
                worldObj.spawnParticle("smoke",
                        this.posX + (float) (rand.nextInt(30) - 15) / 10,
                        this.posY + (float) (rand.nextInt(30) - 15) / 10,
                        this.posZ + (float) (rand.nextInt(30) - 15) / 10,
                        (rand.nextInt(20) - 10) / 100,
                        (rand.nextInt(20) - 10) / 100,
                        (rand.nextInt(20) - 10) / 100 );
                worldObj.spawnParticle("cloud",
                        this.posX + (float) (rand.nextInt(30) - 15) / 10,
                        this.posY + (float) (rand.nextInt(30) - 15) / 10,
                        this.posZ + (float) (rand.nextInt(30) - 15) / 10,
                        (rand.nextInt(20) - 10) / 100,
                        (rand.nextInt(20) - 10) / 100,
                        (rand.nextInt(20) - 10) / 100 );
            }
            if(this.deathTicks == 150)
                this.setDead();
        }
    }
    
    public boolean isConverting() {
        return false;
    }
    
    public boolean canBePushed() {
        return false;
    }
    
    public void moveFlying(float p_70060_1_, float p_70060_2_, float p_70060_3_) {
    }
    
    
    
    public void jump() {
    
    }
    
    public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_) {
    
    }
    
    @Override
    public int getMobMode() {
        return 0;
    }
    
    @Override
    public double[] getTargetpos() {
        return new double[]{this.posX,this.posY,this.posZ};
    }
    
    @Override
    public boolean standalone() {
        return true;
    }
    public void setPosition(double x, double y, double z)
    {
        if(baseLogic != null)baseLogic.setPosition(x,y,z);
    }
    
    @Override
    public TurretObj[] getmainTurrets() {
        if(baseLogic.mainTurrets == null) {
            ArrayList<TurretObj> turrets = new ArrayList<TurretObj>();
            addAllTurret(turrets, baseLogic.mainTurret);
            baseLogic.mainTurrets = turrets.toArray(new TurretObj[turrets.size()]);
        }
        return baseLogic.mainTurrets;
    }
    
    @Override
    public TurretObj[] getsubTurrets() {
        if(baseLogic.subTurrets == null) {
            ArrayList<TurretObj> turrets = new ArrayList<TurretObj>();
            addAllTurret(turrets, baseLogic.subTurret);
            baseLogic.subTurrets = turrets.toArray(new TurretObj[turrets.size()]);
        }
        return baseLogic.subTurrets;
    }
    
    @Override
    public TurretObj[] getTurrets() {
        if(baseLogic.turrets == null) {
            ArrayList<TurretObj> turrets = new ArrayList<TurretObj>();
            addAllTurret(turrets, baseLogic.mainTurret);
            addAllTurret(turrets, baseLogic.subTurret);
            baseLogic.turrets = turrets.toArray(new TurretObj[turrets.size()]);
        }
        return baseLogic.turrets;
    }
}