package hmggvcmob.ai;

import handmadeguns.HMGPacketHandler;
import handmadeguns.entity.IFF;
import handmadeguns.entity.bullets.HMGEntityBullet;
import handmadeguns.entity.bullets.HMGEntityBulletExprode;
import handmadeguns.network.PacketSpawnParticle;
import hmggvcmob.SlowPathFinder.WorldForPathfind;
import hmggvcmob.entity.IIRVING;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;

import java.util.Random;

import static handmadeguns.HandmadeGunsCore.cfg_defgravitycof;
import static hmvehicle.Utils.CalculateGunElevationAngle;
import static net.minecraft.util.MathHelper.wrapAngleTo180_float;

public class AIGKFire extends EntityAIBase{
    private EntityLiving IRVING_body;//����
    private IFF IFF;//����
    private IIRVING IRVING_SPdata;
    public WorldForPathfind worldForPathfind;
    private EntityLivingBase target;//�^�[�Q�b�g
    private Random rnd;
    private int forget = 0;//�Y���܂�
    private boolean fEnable = true;
    public int angletime;
    private int cycleprogress1;
    private int cycleprogress2;
    private int remainbullet1 = 0;
    private int reloading1 = 0;

    private int attackTime = 0;

    private float inaccuracy = 40f;
    public AIGKFire(EntityLiving guerrilla){
        this.IRVING_body = guerrilla;
        IFF = (handmadeguns.entity.IFF) guerrilla;
        if(guerrilla instanceof IIRVING)
            this.IRVING_SPdata = (IIRVING) guerrilla;
        rnd = new Random();
        worldForPathfind = new WorldForPathfind(guerrilla.worldObj);
    }
    public AIGKFire(EntityLiving guerrilla,float inaccuracy){
        this(guerrilla);
        this.inaccuracy = inaccuracy;
    }
    public boolean shouldExecute() {
        attackTime--;
        EntityLivingBase entityliving = IRVING_body.getAttackTarget();
        if (!fEnable || entityliving == null || entityliving.isDead||forget > 1200|| IRVING_SPdata.isstaning()) {
            target = null;
            forget = 0;
            return false;
        } else {
            target = entityliving;
            return true;
        }
    }
    @Override
    public void updateTask() {
        if(attackTime<0)attackTime = rnd.nextInt(100);
        IRVING_body.getLookHelper().setLookPositionWithEntity(target,90,90);

        if(attackTime>20){
            if(IRVING_body.onGround || IRVING_body.isWet())
                IRVING_body.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(IRVING_body,(int)target.posX,(int)target.posY,(int)target.posZ,80f,true, false, false, true),1);
        }else {
            IRVING_body.getNavigator().clearPathEntity();
        }

        double d5 = target.posX - IRVING_body.posX;
        double d7 = target.posZ - IRVING_body.posZ;
        double d1 = IRVING_body.posY - (target.posY) + 2;
        double d3 = (double) MathHelper.sqrt_double(d5 * d5 + d7 * d7);
        float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
        if (cycleprogress1 > 80) {// 2
            boolean flag = IRVING_body.getEntitySenses().canSee(target);
            if(flag){
                double xx11 = 0;
                double zz11 = 0;
                xx11 -= MathHelper.sin(IRVING_body.rotationYawHead * 0.01745329252F-1.57F) * 1;
                zz11 += MathHelper.cos(IRVING_body.rotationYawHead  * 0.01745329252F-1.57F) * 1;
                float turretrotationYaw = -((float) Math.atan2(d5-xx11, d7-zz11)) * 180.0F / (float) Math.PI;
                HMGEntityBulletExprode var3 = new HMGEntityBulletExprode(IRVING_body.worldObj, IRVING_body,120,3,3);
                var3.gra = (float) (1f/cfg_defgravitycof)*0.05f;
                var3.canbounce = false;
                float targetpitch = wrapAngleTo180_float(-(float)CalculateGunElevationAngle(IRVING_body,target, (float) (var3.gra*cfg_defgravitycof),2.0F)[0]);
                var3.setLocationAndAngles(IRVING_body.posX + xx11, IRVING_body.posY + 3.8D, IRVING_body.posZ + zz11,
                        IRVING_body.rotationYaw, targetpitch);
                var3.setHeadingFromThrower(targetpitch, turretrotationYaw, 0, 2, inaccuracy/8);
                if (!IRVING_body.worldObj.isRemote) {
                    IRVING_body.worldObj.spawnEntityInWorld(var3);
                }
                HMGPacketHandler.INSTANCE.sendToAll(new PacketSpawnParticle(IRVING_body.posX - xx11, IRVING_body.posY + 3.8, IRVING_body.posZ - zz11, 0));
                IRVING_body.playSound("gvcguns:gvcguns.firegrenade", 5.0F, 0.8F);
            }
            if (!IRVING_body.worldObj.isRemote) {
                cycleprogress1=0;
            }
        } else {
            if (!IRVING_body.worldObj.isRemote) {
                ++cycleprogress1;
            }
        }
        if (cycleprogress2 > 4) {// 2
            if( remainbullet1>0) {
                remainbullet1--;
                //if (flag)
                double xx11 = 0;
                double zz11 = 0;
                xx11 -= MathHelper.sin(IRVING_body.rotationYawHead  * 0.01745329252F + 1.57F) * 1;
                zz11 += MathHelper.cos(IRVING_body.rotationYawHead  * 0.01745329252F + 1.57F) * 1;
                float turretrotationYaw = -((float) Math.atan2(d5-xx11, d7-zz11)) * 180.0F / (float) Math.PI;
                float targetpitch = wrapAngleTo180_float(-(float) CalculateGunElevationAngle(IRVING_body, target, (float) (0.05 * cfg_defgravitycof), 8.0F)[0]);
                HMGEntityBullet var3 = new HMGEntityBullet(IRVING_body.worldObj, IRVING_body, 9, 8F, 3.0F);
                var3.gra = 0.05f;
                var3.setLocationAndAngles(IRVING_body.posX + xx11, IRVING_body.posY + IRVING_body.getEyeHeight(), IRVING_body.posZ + zz11,
                        IRVING_body.rotationYaw, IRVING_body.rotationPitch);
                var3.setHeadingFromThrower(targetpitch, turretrotationYaw, 0, 8, inaccuracy);
                if (!IRVING_body.worldObj.isRemote) {
                    IRVING_body.worldObj.spawnEntityInWorld(var3);
                }
                HMGPacketHandler.INSTANCE.sendToAll(new PacketSpawnParticle(IRVING_body.posX + xx11, IRVING_body.posY + IRVING_body.getEyeHeight(), IRVING_body.posZ + zz11, 0));
                IRVING_body.playSound("gvcguns:gvcguns.fire", 5.0F, 0.5F);
                if (!IRVING_body.worldObj.isRemote) {
                    cycleprogress2 = 0;
                }
            }else {
                if(reloading1>30) {
                    remainbullet1 = 10;
                    reloading1 = 0;
                } else reloading1++;
            }
        } else {
            if (!IRVING_body.worldObj.isRemote) {
                ++cycleprogress2;
            }
        }
    }
}
