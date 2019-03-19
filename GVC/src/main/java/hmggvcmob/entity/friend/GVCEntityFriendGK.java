package hmggvcmob.entity.friend;

import handmadeguns.HandmadeGunsCore;
import hmggvcmob.ai.AIGKFire;
import hmggvcmob.ai.AIGKHighJump;
import hmggvcmob.ai.AIGKkick;
import hmggvcmob.entity.GVCEx;
import hmggvcmob.entity.IIRVING;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.Random;

import static hmggvcmob.GVCMobPlus.cfg_guerrillasrach;
import static hmggvcmob.util.Calculater.CalculateGunElevationAngle;

public class GVCEntityFriendGK extends EntityPMCBase implements IIRVING
{
    Random rnd = new Random();
    private int forget;
    private int TGThealth;
    public float bodyrotationYaw;
    public float bodyrotationPitch;
    public float bodyrotationRoll;
    public float prevbodyrotationYaw;
    public float prevbodyrotationPitch;
    public float prevbodyrotationRoll;
    public float turretrotationYaw;
    public float turretrotationPitch;
    public float prevangletime;
    public float angletime;
    public int ammo3;
    public int cooltime;
    public int cooltime2;
    public int cooltime3;
    public int magazine;
    public int gun_count1 = 0;
    public int reload1 = 0;
    public int reload_time1;
    public int Accumulationdamage_leg;
    public boolean staning;
    private int tgthealth;
    public int kickprogeress;
    // public int type;
    public GVCEntityFriendGK(World par1World)
    {
        super(par1World);
        this.setSize(2.5F, 4F);
        //this.tasks.addTask(1, new AIEntityInvasionFlag(this, 1.0D));
        //  this.tasks.addTask(2, new AIEntityAIWander(this, 1.0D));
        this.targetTasks.removeTask(aiSwimming);
        this.targetTasks.removeTask(AIOpenDoor);
        this.tasks.addTask(1,new AIGKkick(this,this,3.5f));
        this.tasks.addTask(2,new AIGKHighJump(this,this,3.5f));
        this.tasks.addTask(3,new AIGKFire(this,10f));
        this.tasks.addTask(4,new EntityAIWander(this,1));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(26, new Byte((byte)0));
        this.dataWatcher.addObject(27, new Byte((byte)0));
        this.dataWatcher.addObject(28, new Byte((byte)0));
        this.dataWatcher.addObject(29, new Byte((byte)0));
    }
    public double getMountedYOffset() {
        return 2.0D;
    }


    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(movespeed = 0.33000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(90.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(cfg_guerrillasrach);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
    }



    public boolean attackEntityFrom(DamageSource source, float par2)
    {

        if(source.getDamageType().equals("fall")){
            return false;
        }
        if(!staning && source.getEntity() != null){
            Accumulationdamage_leg+=par2;
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

    public void addRandomArmor()
    {
        super.addRandomArmor();
    }

    public void onUpdate()
    {
        super.onUpdate();
        if(!this.worldObj.isRemote){

            for(int x = (int)this.boundingBox.minX-1;x<=this.boundingBox.maxX;x++){
                for(int y = (int)this.boundingBox.minY-1;y<=this.boundingBox.maxY;y++){
                    for(int z = (int)this.boundingBox.minZ-1;z<=this.boundingBox.maxZ;z++){
                        Block collidingblock = worldObj.getBlock(x,y,z);
                        if(collidingblock.getMaterial() == Material.leaves || collidingblock.getMaterial() == Material.wood || collidingblock.getMaterial() == Material.glass || collidingblock.getMaterial() == Material.cloth){
                            worldObj.setBlockToAir(x,y,z);
                        }
                    }
                }
            }
            Accumulationdamage_leg--;
            if(this.getHealth() <= this.getMaxHealth()/2){
                if(this.getHealth() <= this.getMaxHealth()/4){
                    this.worldObj.spawnParticle("smoke", this.posX-2, this.posY + 2D, this.posZ+2, 0.0D, 0.0D, 0.0D);
                    this.worldObj.spawnParticle("smoke", this.posX+2, this.posY + 2D, this.posZ-1, 0.0D, 0.0D, 0.0D);
                    int rx = this.worldObj.rand.nextInt(5);
                    int rz = this.worldObj.rand.nextInt(5);
                    this.worldObj.spawnParticle("flame", this.posX-2+rx, this.posY + 2D, this.posZ-2+rz, 0.0D, 0.0D, 0.0D);
                    this.worldObj.spawnParticle("flame", this.posX-2+rx, this.posY + 2D, this.posZ-2+rz, 0.0D, 0.0D, 0.0D);
                }else{
                    this.worldObj.spawnParticle("smoke", this.posX-2, this.posY + 2D, this.posZ+2, 0.0D, 0.0D, 0.0D);
                    this.worldObj.spawnParticle("smoke", this.posX+2, this.posY + 2D, this.posZ-1, 0.0D, 0.0D, 0.0D);
                    int rx = this.worldObj.rand.nextInt(5);
                    int rz = this.worldObj.rand.nextInt(5);
                    this.worldObj.spawnParticle("smoke", this.posX-2+rx, this.posY + 2D, this.posZ-2+rz, 0.0D, 0.0D, 0.0D);
                }
            }
            if(Accumulationdamage_leg>80){
                this.setSneaking(true);
                staning = true;
            }
            if(staning){
                Accumulationdamage_leg--;
                combattask_2 = false;
                combattask_4 = false;
                getNavigator().clearPathEntity();
                kickprogeress = 0;
            }
            if(Accumulationdamage_leg<0){
                this.setSneaking(false);
                staning = false;
                Accumulationdamage_leg = 0;
            }
            this.dataWatcher.updateObject(26,new Byte(combattask_2 ? (byte)0:(byte)1));
            this.dataWatcher.updateObject(27,new Byte(combattask_4 ? (byte)0:(byte)1));
        }else {
            combattask_2 = 0 == ((Byte)this.dataWatcher.getWatchableObjectByte(26)).byteValue();
            combattask_4 = 0 == ((Byte)this.dataWatcher.getWatchableObjectByte(27)).byteValue();
            if(this.isSneaking()){
                Accumulationdamage_leg-=2;
                combattask_2 = false;
                combattask_4 = false;
                angletime = 0;
                onstopping = false;
                onstarting = false;
            }else {
                if (combattask_4) {
                    ++kickprogeress;
                    if (kickprogeress > 10) {
                        combattask_4 = false;
                        kickprogeress = 0;
                    }
                } else if (combattask_2) {
                    ++kickprogeress;
                    if (kickprogeress > 2) {
                        combattask_2 = false;
                        kickprogeress = 0;
                    }
                }
                if (this.getDistance(prevPosX,posY,prevPosZ)!=0){
                    onstarting = true;
                    isstanding = false;
                    onstopping = false;
                    int an = 8;
                    if(angletime == 20){
                        HandmadeGunsCore.proxy.playsoundat("gvcguns:gvcguns.wark",1,3,1,(float) posX,(float)posY,(float)posZ);
                    }
                    if(angletime == 100){
                        HandmadeGunsCore.proxy.playsoundat("gvcguns:gvcguns.wark",1,3,1,(float) posX,(float)posY,(float)posZ);
                    }
                    if(angletime == 80){
                        HandmadeGunsCore.proxy.playsoundat("gvcguns:gvcguns.zye",1,3,1,(float) posX,(float)posY,(float)posZ);
                    }
                    if(angletime == 160){
                        HandmadeGunsCore.proxy.playsoundat("gvcguns:gvcguns.zye",1,3,1,(float) posX,(float)posY,(float)posZ);
                    }
                    if (angletime < 160) {
                        angletime += an;
                    } else {
                        angletime = 0;
                        onstarting = false;
                    }
                } else {
                    isstanding = true;
                    angletime =0;
                    onstarting = false;
                    onstopping = true;
                }
            }
        }
    }



    protected String getLivingSound()
    {
        return "mob.cow.say";
    }

    protected void onDeathUpdate() {
        ++this.deathTicks;
        if(this.deathTicks == 1){
            //this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 0F, false);
            GVCEx ex = new GVCEx(this, 3F);
            ex.Ex();
        }
        if (this.deathTicks == 150 && !this.worldObj.isRemote) {
            this.setDead();
            this.dropItem(new ItemStack(Blocks.gold_block, 0).getItem(), 1);
            this.dropItem(new ItemStack(Blocks.redstone_block, 0).getItem(),1);
            if(rnd.nextInt(5) == 0){
                this.dropItem(new ItemStack(Blocks.beacon,0).getItem(),1);
            }
        }
    }

    public boolean isConverting() {
        return false;
    }

    public boolean isstaning() {
        return staning;
    }

    public boolean iscombattask_4() {
        return combattask_4;
    }

    public int getkickprogeress() {
        return kickprogeress;
    }

    public void setcombattask_4(boolean value) {
        combattask_4 = value;
    }

    public void setkickprogeress(int value) {
        kickprogeress = value;
    }
}