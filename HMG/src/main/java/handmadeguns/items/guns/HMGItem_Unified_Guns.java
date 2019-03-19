package handmadeguns.items.guns;

import com.google.common.collect.Multimap;
import handmadeguns.HMGPacketHandler;
import handmadeguns.HandmadeGunsCore;
import handmadeguns.Util.PlaceGunShooterPosGetter;
import handmadeguns.entity.HMGEntityLaser;
import handmadeguns.entity.HMGEntityLight;
import handmadeguns.entity.PlacedGunEntity;
import handmadeguns.entity.bullets.*;
import handmadeguns.items.*;
import handmadeguns.network.*;
import littleMaidMobX.LMM_EntityLittleMaid;
import littleMaidMobX.LMM_IEntityLittleMaidAvatarBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static handmadeguns.HandmadeGunsCore.islmmloaded;
import static handmadeguns.HandmadeGunsCore.proxy;
import static java.lang.StrictMath.toRadians;

public class HMGItem_Unified_Guns extends Item {
    public static final UUID field_110179_h = UUID.fromString("254F543F-8B6F-407F-931B-4B76FEB8BA0D");
    public int reloadtime;


    public int powor;
    public float speed;
    private float tempspread;
    public float spread_setting;
    public float ads_spread_cof = 0.5f;
    public double recoil;
    public double recoil_sneak;
    public float scopezoom;
    public int canuseclass = 0;
    public int guntype;

    public int cycle;
    public float ex = 2.5F;
    public boolean canex;
    public String soundre= "handmadeguns:handmadeguns.reload";
    public float soundrelevel = 1.0f;
    public boolean canobj;
    public boolean rendercross;

    public boolean semi;
    public String soundco = "handmadeguns:handmadeguns.cooking";

    public  String adstexture = "handmadeguns:handmadeguns/textures/misc/ironsight";


    public float scopezoombase;
    public float scopezoomred;
    public float scopezoomscope;
    private String sound= "handmadeguns:handmadeguns.fire";
    private float soundlevel;
    public float soundspeed = 1.0F;
    public String soundbase= "handmadeguns:handmadeguns.fire";
    public float soundbaselevel = 4.0f;
    public String soundsu= "handmadeguns:handmadeguns.supu";
    public float soundsuplevel = 1.0f;
    
    public String lockSound_entity = "handmadeguns:handmadeguns.lockon";
    public String lockSound_block = "handmadeguns:handmadeguns.lockon";
    public float lockpitch_entity = 1;
    public float lockpitch_block = 0.5f;
    
    
    public Item magazine;

    public ArrayList<Item> magazines = new ArrayList<Item>();//�����}�K�W���p

    private ItemStack magazineStack;//�ꎞ�p�����ǃ��\�b�h�܂����ŋ��L�������̂�
    public int magazineItemCount = 1;
    public  String adstexturer = "handmadeguns:handmadeguns.textures.misc.reddot";
    public  String adstextures = "handmadeguns:handmadeguns.textures.misc.scope";

    public boolean zoomren = false;
    public boolean zoomrer = false;
    public boolean zoomres = false;

    public boolean zoomrent = false;
    public boolean zoomrert = false;
    public boolean zoomrest = false;

    public double motion = 1D;
    public boolean muzzleflash = true;
    private boolean muzzle = true;

    public boolean mat22 = false;
    public float mat22rotationx = 90F;
    public float mat22rotationy = 0F;
    public float mat22rotationz = 0F;
    public float mat22offsetx = 0F;
    public float mat22offsety = 1.5F;
    public float mat22offsetz = 2F;

    public float mat25rotationx = 0F;
    public float mat25rotationy = 0F;
    public float mat25rotationz = -90F;
    public float mat25offsetx = 0F;
    public float mat25offsety = 0.75F;
    public float mat25offsetz = 1.1F;
    public float soundrespeed = 1.0F;
    public int cocktime = 20;
    public boolean needcock = false;

    public float Sprintrotationx = 20F;
    public float Sprintrotationy = 60F;
    public float Sprintrotationz = 0F;
    public float Sprintoffsetx = 0.5F;
    public float Sprintoffsety = 0.0F;
    public float Sprintoffsetz = 0.5F;

    public int shotgun_pellet = 1;

    //01/27
    public float gra = 0.029F;

    public float jump = 0;
    //01/27
    public boolean all_jump = false;
    public boolean cock_left = false;
    public boolean mat25 = false;
    public boolean mat2 = false;

    //02/14
    public int cartType;
    public int magType;
    public int magentityCnt;
    public int cartentityCnt = 1;
    public boolean dropcart = true;
    public boolean cart_cocked = false;
    public boolean dropMagEntity = true;
    public boolean remat31 = true;


    //0307
    public String soundunder_gl= "handmadeguns:handmadeguns.cooking";
    public String soundunder_sg= "handmadeguns:handmadeguns.cooking";
    public int under_gl_power = 20;
    public boolean under_gl_canbounce = true;
    public int under_gl_fuse = -1;
    public float under_gl_speed = 2;
    public float under_gl_bure = 5;
    public double under_gl_recoil = 5;
    public float under_gl_gra = 0.01F;
    public int under_sg_power = 4;
    public float under_sg_speed = 3;
    public float under_sg_bure = 20;
    public double under_sg_recoil = 5;
    public float under_sg_gra = 0.029F;
    public float attackDamage = 1;
    private float foruseattackDamage = 1;
    public boolean hasAttachRestriction = false;
    public ArrayList<String> attachwhitelist = new ArrayList<String>();
    public boolean useundergunsmodel = false;
    public float underoffsetpx;
    public float underoffsetpy;
    public float underoffsetpz;
    public float underrotationx;
    public float underrotationy;
    public float underrotationz;

    public float onunderoffsetpx;
    public float onunderoffsetpy;
    public float onunderoffsetpz;
    public float onunderrotationx;
    public float onunderrotationy;
    public float onunderrotationz;

    public float modelscale = 1;
    public float inworldScale = 1;

    public boolean reloadanim = false;
    public ArrayList<Float[]> reloadanimation = new ArrayList<Float[]>();
    public boolean nodrawmat35 = false;


    public boolean hascustombulletmodel = false;
    public boolean hascustomcartridgemodel = false;
    public boolean hascustommagemodel = false;
    public String bulletmodelN = "default";
    public String bulletmodelAR = "default";
    public String bulletmodelAP = "default";
    public String bulletmodelAT = "default";
    public String bulletmodelFrag = "default";
    public String bulletmodelHE = "default";
    public String bulletmodelTE = "default";
    public String bulletmodelCart = "default";
    public String bulletmodelGL = "default";
    public String bulletmodelRPG = "byfrou01_Rocket";
    public String bulletmodelMAG = "default";
    public double knockback = 0.1;
    public double knockbackY =0.1;
    public ArrayList<Integer> burstcount = new ArrayList<Integer>();
    public ArrayList<Integer> rates = new ArrayList<Integer>();
    public boolean canbounce = false;
    public int fuse = 0;
    public float bouncerate = 0.3f;
    public float bouncelimit = 90;
    public boolean userenderscript = false;
    public ScriptEngine renderscript;
    public ScriptEngine script;
    Invocable invocable;

    public float mat31rotex;
    public float mat31rotey;
    public float mat31rotez;
    public boolean isOneuse = false;
    public boolean guerrila_can_use = true;
    public boolean isinRoot = true;
    public boolean soldiercanstorage = true;
    public boolean use_internal_secondary;
    public ItemStack[] items = new ItemStack[6];
    public float acceleration;
    public boolean canlock = false;
    public boolean canlockBlock = false;
    public boolean canlockEntity = false;
    public float induction_precision;
    public String flashname = null;
    public int flashfuse = 1;
    public float flashScale = 1;
    Entity TGT;
    int LockedPosX;
    int LockedPosY;
    int LockedPosZ;
    boolean islockingentity;
    boolean islockingblock;
    public float resistance = 0.99f;
    public boolean canfix;
    public boolean needfix;
    public boolean fixAsEntity;

    public float sightattachoffset[] = new float[3];

    public PlaceGunShooterPosGetter posGetter;
    public float yoffset;
    public double sightPosN[] = new double[3];
    public double sightPosR[] = new double[3];
    public double sightPosS[] = new double[3];

    public boolean canceler;
    public boolean chargeType;
    public boolean[] hasNightVision = new boolean[]{false,false,false};

    public float onTurretScale = 1.0f;
    public boolean restrictTurretMoveSpeed;
    public float turretMoveSpeedP;
    public float turretMoveSpeedY;
    public float turreboxW;
    public float turreboxH;
    public int turretMaxHP;
    public boolean restrictTurretAngle = false;
    public float turretanglelimtMxP = 0;
    public float turretanglelimtMxY = 0;
    public float turretanglelimtmnP = 0;
    public float turretanglelimtmnY = 0;

    public HMGItem_Unified_Guns(){
        posGetter = new PlaceGunShooterPosGetter();
    }
    public HMGItem_Unified_Guns(int p, float s, float b, double r, int rt, float at, float cz, float czr, float czs, int cy, String sd, String sds, String sdre,
                                boolean rc, int ri, ResourceLocation tx, String aads, String aadsr, String aadss, Item ma, Item masg, Item magl, boolean cano) {
        this();
        this.maxStackSize = 1;
        this.attackDamage = at;
        // this.retime = 30;
        this.powor = p;
        this.speed = s;
        this.spread_setting = b;
        this.recoil = r;
        this.recoil_sneak = r/2;
        this.reloadtime = rt;
        this.scopezoom = cz;
        this.scopezoombase = cz;
        this.scopezoomred = czr;
        this.scopezoomscope = czs;
        this.sound = sd;
        this.soundbase = sd;
        this.soundsu = sds;
        this.soundre = sdre;
        this.cycle = cy;
        this.rendercross = rc;
        this.adstexture = aads;
        this.adstexturer = aadsr;
        this.adstextures = aadss;
        this.magazine = ma;
        magazines.add(ma);
        this.canobj = cano;
        setFull3D();
    }
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        String powor = String
                .valueOf(this.powor + EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack));
        String speed = String.valueOf(this.speed);
        String bure = String.valueOf(this.spread_setting);
        String recoil = String.valueOf(this.recoil);
        String retime = String.valueOf(this.reloadtime);
        String nokori = String.valueOf(getMaxDamage() - par1ItemStack.getItemDamage());

        par3List.add(EnumChatFormatting.RED + "RemainingBullet " + StatCollector.translateToLocal(nokori));
        par3List.add(EnumChatFormatting.WHITE + "FireDamege " + "+" + StatCollector.translateToLocal(powor));
        par3List.add(EnumChatFormatting.WHITE + "BulletSpeed " + "+" + StatCollector.translateToLocal(speed));
        par3List.add(EnumChatFormatting.WHITE + "BulletSpread " + "+" + StatCollector.translateToLocal(bure));
        par3List.add(EnumChatFormatting.WHITE + "Recoil " + "+" + StatCollector.translateToLocal(recoil));
        par3List.add(EnumChatFormatting.YELLOW + "ReloadTime " + "+" + StatCollector.translateToLocal(retime));
        // par3List.add(EnumChatFormatting.YELLOW + "MagazimeType " +
        // StatCollector.translateToLocal("ARMagazine"));
        if (!(this.scopezoom == 1.0f)) {
            String scopezoom = String.valueOf(this.scopezoom);
            par3List.add(EnumChatFormatting.WHITE + "ScopeZoom " + "x" + StatCollector.translateToLocal(scopezoom));
        }
        if(this.needfix){
            par3List.add(EnumChatFormatting.WHITE + "cannot handhold Shot");
        }else
        if(this.canfix){
            par3List.add(EnumChatFormatting.WHITE + "can Fix");
        }
    }
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag){
        invocable = (Invocable)script;
        if(islmmloaded && entity instanceof LMM_IEntityLittleMaidAvatarBase){
            return;
        }
        if(entity!=null && flag){
            tempspread = spread_setting;
            if(HandmadeGunsCore.Key_ADS(entity)){
                tempspread = tempspread * ads_spread_cof;
            }
            if(HandmadeGunsCore.cfg_Flash){
                int xTile = (int) entity.lastTickPosX-1;
                int yTile = (int) entity.lastTickPosY-1;
                int zTile = (int) entity.lastTickPosZ-1;
                world.func_147451_t(xTile, yTile, zTile);
                world.func_147451_t(xTile - 1, yTile, zTile);
                world.func_147451_t(xTile + 1, yTile, zTile);
                world.func_147451_t(xTile, yTile - 1, zTile);
                world.func_147451_t(xTile, yTile + 1, zTile);
                world.func_147451_t(xTile, yTile, zTile - 1);
                world.func_147451_t(xTile, yTile, zTile + 1);
            }
            checkTags(itemstack);
            {
                NBTTagCompound nbt = itemstack.getTagCompound();
                islockingentity = nbt.getBoolean("islockedentity");
                TGT = world.getEntityByID(nbt.getInteger("TGT"));
                islockingblock = nbt.getBoolean("islockedblock");
                LockedPosX = nbt.getInteger("LockedPosX");
                LockedPosY = nbt.getInteger("LockedPosY");
                LockedPosZ = nbt.getInteger("LockedPosZ");
                int mode = nbt.getInteger("HMGMode");

                bindattaches(itemstack, world, entity);
                boolean canFixflag = canfix;
                try {
                    if (items != null && items[4] != null && items[4].getItem() instanceof HMGItemAttachment_grip) {
                        canFixflag |= ((HMGItemAttachment_grip) items[4].getItem()).isbase;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(!world.isRemote) {
                    if (!canFixflag || (entity.distanceWalkedModified != nbt.getFloat("prevdistanceWalkedModified"))) {
                        nbt.setBoolean("HMGfixed", false);
                    }
                    if(canfix) {
                        nbt.setFloat("prevdistanceWalkedModified", entity.distanceWalkedModified);
                    }
                }
                if(entity instanceof EntityPlayer){
                    canceler = false;
                    try {
                        if(invocable!= null)
                            invocable.invokeFunction("update_onplayer",this,itemstack,nbt,entity);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    if(!canceler) {
                        if (entity.isSneaking() || nbt.getBoolean("HMGfixed")) {
                            nbt.setBoolean("set_up", true);
                            nbt.setInteger("set_up_cnt", 3);
                        }
                        if (world.isRemote) {
                            if (i != -1) {
                                proxy.force_render_item_position(itemstack, i);
                            }
                            if (proxy.Reloadkeyispressed()) {
                                HMGPacketHandler.INSTANCE.sendToServer(new PacketreturnMgazineItem(entity.getEntityId()));
                                nbt.setInteger("RloadTime", 0);
                            }
                            if (proxy.Attachmentkeyispressed()) {
                                HMGPacketHandler.INSTANCE.sendToServer(new PacketOpenGui(0, entity.getEntityId()));
                            }
                            if (proxy.lightkeydown()) {
                                nbt.setBoolean("SeekerOpened", !nbt.getBoolean("SeekerOpened"));
                                HMGPacketHandler.INSTANCE.sendToServer(new PacketSeekerOpen(entity.getEntityId()));
                            }
                            if (canFixflag && proxy.fixkeydown()) {
                                nbt.setBoolean("HMGfixed", !nbt.getBoolean("HMGfixed"));
                                HMGPacketHandler.INSTANCE.sendToServer(new PacketFixGun(entity.getEntityId()));
                            }
                            try {
                                if (items != null && items[4] != null && items[4].getItem() instanceof HMGItem_Unified_Guns) {
                                    checkTags(items[4]);
                                    if (((HMGItem_Unified_Guns) items[4].getItem()).getburstCount(items[4].getTagCompound().getInteger("HMGMode")) != -1) {
                                        if (proxy.Fclick())
                                            HMGPacketHandler.INSTANCE.sendToServer(new PacketTriggerUnder(entity.getEntityId()));
                                    } else if (proxy.Fclick_no_stopper()) {
                                        HMGPacketHandler.INSTANCE.sendToServer(new PacketTriggerUnder(entity.getEntityId()));
                                    }
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            if (i != -1 && proxy.Modekeyispressed()) {
                                if (!nbt.getBoolean("Modekeyispressed")) {
                                    mode++;
                                    if (mode >= burstcount.size() || mode >= rates.size()) {
                                        mode = 0;
                                    }
                                    nbt.setInteger("HMGMode", mode);
                                    HMGPacketHandler.INSTANCE.sendToServer(new PacketChangeModeHeldItem(entity, mode));
                                    nbt.setBoolean("Modekeyispressed", true);
                                }
                            } else {
                                nbt.setBoolean("Modekeyispressed", false);
                            }
                            if (this.canlock) {
                                if (islockingblock) {
                                    proxy.spawnParticles(new PacketSpawnParticle(LockedPosX + 0.5, LockedPosY + 0.5, LockedPosZ + 0.5, 2));
                                }
                                if (islockingentity && TGT != null) {
                                    proxy.spawnParticles(new PacketSpawnParticle(TGT.posX, TGT.posY + TGT.height / 2, TGT.posZ, 2));
                                }
                            }
                        } else {
                            if (this.canlock && nbt.getBoolean("SeekerOpened")) {
                                lockon(itemstack,world,entity,nbt);
                            }
                        }
                    }
                }else if(islmmloaded && entity instanceof LMM_EntityLittleMaid){
                    canceler = false;
                    try {
                        if(invocable!= null)
                            invocable.invokeFunction("update_onmaid",this,itemstack,nbt,entity);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    if(!canceler && (((LMM_EntityLittleMaid) entity).isUsingItem()))
                        nbt.setBoolean("IsTriggered",true);
                }
                if(entity instanceof EntityLiving) {
                    canceler = false;
                    try {
                        if(invocable!= null)
                            invocable.invokeFunction("update_onliving",this,itemstack,nbt,entity);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    if(!canceler) {
                        nbt.setBoolean("SeekerOpened", false);
                        if (this.canlock) {
                            TGT = ((EntityLiving) entity).getAttackTarget();
                            if (TGT != null) {
                                islockingentity = true;
                            }
                        }
                    }
                }
                if(entity instanceof PlacedGunEntity){
                    canceler = false;
                    try {
                        if(invocable!= null)
                            invocable.invokeFunction("update_onplacedGun",this,itemstack,nbt,entity);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    if(!canceler) {
                        if (this.canlock && nbt.getBoolean("SeekerOpened")) {
                            lockon(itemstack,world,entity,nbt);
                        }
                    }
                }
                try {
                    if(invocable!= null)
                        invocable.invokeFunction("update_all",this,itemstack,nbt,entity);
                } catch (ScriptException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                if (getMaxDamage() == itemstack.getItemDamage() && nbt.getBoolean("Recoiled")) {
                    try {
                        if(invocable!= null)
                            invocable.invokeFunction("startreload",this,itemstack,nbt,entity);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    nbt.setBoolean("IsReloading", true);
                    proceedreload(itemstack, world, entity, nbt, i);
                }else if(magazine instanceof HMGItemBullet_with_Internal_Bullet && items != null && items[5] == null){
                    try {
                        if(invocable!= null)
                            invocable.invokeFunction("startreload",this,itemstack,nbt,entity);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    nbt.setBoolean("IsReloading", true);
                    proceedreload(itemstack, world, entity, nbt, i);
                }
                if (!rates.isEmpty() && rates.size()>mode)
                    this.cycle = rates.get(mode);
                boolean recoiled = nbt.getBoolean("Recoiled");
                if (!recoiled) {
                    nbt.setBoolean("Recoiled", true);
                }
                boolean cocking = nbt.getBoolean("Cocking");
                int cockingtime = nbt.getInteger("CockingTime");
                if (!cocking && needcock) {
                    try {
                        if(invocable!= null)
                            invocable.invokeFunction("proceedcock",this,itemstack,nbt,entity);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    if (cockingtime == 0) {
                        world.playSoundEffect(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ, soundco, 1.0F, 1.0f);
                    }
                    ++cockingtime;
                    nbt.setInteger("CockingTime", cockingtime);
                    if (cockingtime >= cocktime) {
                        nbt.setInteger("CockingTime", 0);
                        nbt.setBoolean("Cocking", true);
                        if (HandmadeGunsCore.cfg_canEjectCartridge && cart_cocked) {
                            for(int carts = 0;carts < cartentityCnt;carts++) {
                                HMGEntityBulletCartridge var8;
                                if (!hascustomcartridgemodel)
                                    var8 = new HMGEntityBulletCartridge(world, entity, cartType);
                                else
                                    var8 = new HMGEntityBulletCartridge(world, entity, -1, bulletmodelCart);
                                world.spawnEntityInWorld(var8);
                            }
                        }
                    }
                }
                boolean is_Bolt_shooting_position = cycleBolt(itemstack) && (!needcock || nbt.getBoolean("Cocking"));
                boolean isbulletremaining = (this.getMaxDamage() - itemstack.getItemDamage() > 0);
                {
                    if (nbt.getBoolean("IsTriggered")){
                        if((!needfix || nbt.getBoolean("HMGfixed"))){
                            if (!nbt.getBoolean("TriggerBacked")) {
                                if (getburstCount(mode) == 0) {
                                    nbt.setBoolean("Bursting", false);
                                } else {
                                    if (getburstCount(mode) != -1 && !chargeType) {
                                        if (is_Bolt_shooting_position && !nbt.getBoolean("Bursting")) {
                                            nbt.setBoolean("Bursting", true);
                                            nbt.setInteger("RemainBurstround", getburstCount(mode));
                                        }
                                        //�Z�~�I�[�gor�o�[�X�g�Ȃ̂ŘA�˒�~
                                        nbt.setBoolean("TriggerBacked", true);
                                    }
                                    if (getburstCount(mode) != -1 && chargeType) {
                                        //�`���[�W�^�C�v�i�������������C�j�Ȃ̂Ńg���K�[���ꂽ�t���O��true
                                        nbt.setBoolean("TriggerBacked", true);
                                    }
                                    if (!chargeType && !nbt.getBoolean("Bursting") && is_Bolt_shooting_position && isbulletremaining) {
                                        fireProcess(itemstack, world, entity,nbt);
                                        nbt.setBoolean("Recoiled", false);
                                        resetBolt(itemstack);
                                        nbt.setBoolean("Cocking", false);
                                    }
                                }
                            }
                        }
                    } else {
                        if(nbt.getBoolean("TriggerBacked") && chargeType){
                            if (is_Bolt_shooting_position && !nbt.getBoolean("Bursting")) {
                                nbt.setBoolean("Bursting", true);
                                nbt.setInteger("RemainBurstround", getburstCount(mode));
                            }
                        }
                        nbt.setBoolean("TriggerBacked", false);
                    }
                    if (is_Bolt_shooting_position && isbulletremaining && nbt.getBoolean("Bursting")) {
                        nbt.setInteger("RemainBurstround", nbt.getInteger("RemainBurstround") - 1);
                        if (nbt.getInteger("RemainBurstround") < 0) {
                            nbt.setBoolean("Bursting", false);
                            nbt.setBoolean("Cocking", false);
                        } else {
                            fireProcess(itemstack, world, entity,nbt);
                            nbt.setBoolean("Recoiled", false);
                            resetBolt(itemstack);
                        }
                    }
                }
                nbt.setBoolean("IsTriggered", false);//�g���K�[�����������ǂ����̔���p�Ȃ̂�false�ɖ߂��Ă���
                if (nbt.getInteger("set_up_cnt") > 0) {
                    nbt.setInteger("set_up_cnt", nbt.getInteger("set_up_cnt") - 1);
                } else {
                    nbt.setBoolean("set_up", false);
                }
//                if(entity instanceof EntityPlayerMP) {
//                    updateCheckinghSlot((EntityPlayerMP) entity, itemstack);
//                }
                if(!world.isRemote && this.canlock && nbt.getBoolean("SeekerOpened")) {
                    nbt.setBoolean("islockedentity", islockingentity);
                    if(TGT!= null) nbt.setInteger("TGT", TGT.getEntityId());
                    if (islockingblock) {
                        nbt.setInteger("LockedPosX", LockedPosX);
                        nbt.setInteger("LockedPosY", LockedPosY);
                        nbt.setInteger("LockedPosZ", LockedPosZ);
                    }
                    nbt.setBoolean("islockedblock", islockingblock);
                }else {
                    if(TGT == null || TGT.isDead){
                        nbt.setInteger("TGT", -1);
                    }
                }
                try {
                    if (items != null) {
                        for (int i1 = 0; i1 < items.length; i1++) {
                            if (items[i1] != null && items[i1].getItemDamage() > items[i1].getMaxDamage()) {
                                items[i1].stackSize--;
                            }
                            if (items[i1] != null && items[i1].stackSize <= 0) {
                                items[i1] = null;
                            }
                        }
                        if(!world.isRemote) {
                            NBTTagList tags = (NBTTagList) nbt.getTag("Items");
                            int compressedID = 0;
                            if(tags != null) {
                                for (int itemid = 0; itemid < items.length; itemid++) {
                                    if (items[itemid] != null && items[itemid].getItem() != null) {
                                        NBTTagCompound compound = new NBTTagCompound();
                                        compound.setByte("Slot", (byte) itemid);
                                        items[itemid].writeToNBT(compound);
                                        tags.func_150304_a(compressedID, compound);
                                        compressedID++;
                                    }
//                                    if (items[itemid] != null && items[itemid].getItem() != null) {
//                                        NBTTagCompound compound = new NBTTagCompound();
//                                        compound.setByte("Slot", (byte) itemid);
//                                        items[itemid].writeToNBT(compound);
//                                        if(tags.tagCount() >= 6) {
//                                            if(itemid == 4 && items[4] != null && items[4].getItem() instanceof HMGItem_Unified_Guns){
//                                                System.out.println("" + items[4].getItemDamage());
//                                            }
//                                            tags.func_150304_a(itemid, compound);
//                                        }else {
//                                            tags.appendTag(compound);
//                                        }
//                                    }
                                }
                                if(compressedID > 6 && compressedID < tags.tagCount()){
                                    for(int removeid = compressedID;removeid < tags.tagCount();removeid ++){
                                        System.out.println("debug" + compressedID);
                                        tags.removeTag(removeid);
                                    }
                                }
                                nbt.setTag("Items", tags);
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                //�}�K�W���̃X�^�b�N��nbt�ɕۑ�����d�l�ɕύX�\��
            }
        }
    }
    public void lockon(ItemStack itemstack, World world, Entity entity, NBTTagCompound nbt){
        Vec3 vec3 = Vec3.createVectorHelper(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
        Vec3 playerlook = entity instanceof EntityLivingBase?((EntityLivingBase)entity).getLook(1.0f):entity instanceof PlacedGunEntity?((PlacedGunEntity)entity).getLook(1.0f):null;
        playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
        Vec3 vec31 = Vec3.createVectorHelper(entity.posX + playerlook.xCoord, entity.posY + entity.getEyeHeight() + playerlook.yCoord, entity.posZ + playerlook.zCoord);
        MovingObjectPosition movingobjectposition = entity.worldObj.func_147447_a(vec3, vec31, false, true, false);
        Block hitblock;
        Random rand = new Random();
        while (movingobjectposition != null) {
            hitblock = entity.worldObj.getBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
            if ((hitblock.getMaterial() == Material.plants) || ((
                                                                        hitblock.getMaterial() == Material.glass ||
                                                                                hitblock instanceof BlockFence ||
                                                                                hitblock instanceof BlockFenceGate ||
                                                                                hitblock == Blocks.iron_bars) && rand.nextInt(5) <= 1)) {
                Vec3 penerater = movingobjectposition.hitVec.normalize();
                vec3 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord + penerater.xCoord, movingobjectposition.hitVec.yCoord + penerater.yCoord, movingobjectposition.hitVec.zCoord + penerater.zCoord);
                vec31 = Vec3.createVectorHelper(entity.posX + playerlook.xCoord, entity.posY + entity.getEyeHeight() + playerlook.yCoord, entity.posZ + playerlook.zCoord);
                movingobjectposition = entity.worldObj.func_147447_a(vec3, vec31, false, true, false);
            } else {
                break;
            }
        }
        vec3 = Vec3.createVectorHelper(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
        vec31 = Vec3.createVectorHelper(entity.posX + playerlook.xCoord, entity.posY + entity.getEyeHeight() + playerlook.yCoord, entity.posZ + playerlook.zCoord);
        if (movingobjectposition != null) {
            vec31 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }
        Entity rentity = null;
        List list = entity.worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.addCoord(playerlook.xCoord, playerlook.yCoord, playerlook.zCoord).expand(1.0D, 1.0D, 1.0D));
        double d0 = 0.0D;
        double d1 = 0;
        for (int i1 = 0; i1 < list.size(); ++i1) {
            Entity entity1 = (Entity) list.get(i1);
            if (entity1.canBeCollidedWith() && (entity1 != entity)) {
                float f = 0.5F;
                AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double) f, (double) f, (double) f);
                MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);
            
                if (movingobjectposition1 != null) {
                    d1 = vec3.distanceTo(movingobjectposition1.hitVec);
                
                    if (d1 < d0 || d0 == 0.0D) {
                        rentity = entity1;
                        d0 = d1;
                    }
                }
            }
        }
    
        if (rentity != null) {
            d1 = vec3.distanceTo(vec31);
            vec3.xCoord = vec3.xCoord + (vec31.xCoord - vec3.xCoord) * d0 / d1;
            vec3.yCoord = vec3.yCoord + (vec31.yCoord - vec3.yCoord) * d0 / d1;
            vec3.zCoord = vec3.zCoord + (vec31.zCoord - vec3.zCoord) * d0 / d1;
        
            movingobjectposition = new MovingObjectPosition(rentity);
            movingobjectposition.hitVec = vec3;
        }
        if (movingobjectposition != null) {
            if (canlockEntity && movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && movingobjectposition.entityHit != null) {
                if (!islockingentity || (entity.ridingEntity == null || entity.ridingEntity != movingobjectposition.entityHit) && TGT != movingobjectposition.entityHit) {
                    entity.worldObj.playSoundAtEntity(entity, lockSound_entity, 1f, lockpitch_entity);
                    TGT = movingobjectposition.entityHit;
                    islockingentity = true;
                    islockingblock = false;
                }
            } else  if (canlockBlock && movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && (
                    LockedPosX != movingobjectposition.blockX || LockedPosY != movingobjectposition.blockY || LockedPosZ != movingobjectposition.blockZ)) {
                entity.worldObj.playSoundAtEntity(entity, lockSound_block, 1f,lockpitch_block);
                LockedPosX = movingobjectposition.blockX;
                LockedPosY = movingobjectposition.blockY;
                LockedPosZ = movingobjectposition.blockZ;
                islockingblock = true;
                islockingentity = false;
                TGT = null;
            }
        }
    }
    public void fireProcess(ItemStack itemstack, World world, Entity entity, NBTTagCompound nbt){

        if(!world.isRemote) {
            try {
                if(invocable!= null)
                    invocable.invokeFunction("prefire",this,itemstack,nbt,entity);
            } catch (ScriptException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            this.Flash(itemstack, world, entity);
            entity.getEntityData().setFloat("GunshotLevel",soundlevel);
            proxy.playerSounded(entity);
//            world.playSoundEffect(entity.posX,entity.posY,entity.posZ, sound, soundlevel, soundspeed);
            HMGPacketHandler.INSTANCE.sendToAll(new PacketPlaysound(entity,sound,soundspeed,soundlevel));
    
    
            damageMgazine(itemstack,entity);
            if (HandmadeGunsCore.cfg_canEjectCartridge &&this.dropcart) {
                HMGEntityBulletCartridge var8;
                if(!hascustomcartridgemodel)
                    var8 = new HMGEntityBulletCartridge(world,entity, cartType);
                else
                    var8 = new HMGEntityBulletCartridge(world,entity,-1, bulletmodelCart);
                for(int carts = 0;carts < cartentityCnt;carts++)
                    world.spawnEntityInWorld(var8);
            }
            HMGEntityBulletBase[] bullet = null;
            if (guntype < 5 && items != null && items[5] != null) {
                try {
                    switch (guntype) {
                        case 0:
                            if (items[5].getItem() instanceof HMGItemBullet_AP){
                                bullet = FireBulletAP(world, entity);
                            }else if (items[5].getItem() instanceof HMGItemBullet_Frag){
                                bullet = FireBulletFrag(world, entity);
                            }else if (items[5].getItem() instanceof HMGItemBullet_AT){
                                bullet = FireBulletAT(world, entity);
                            }else if (items[5].getItem() instanceof HMGItemBullet_TE){
                                bullet = FireBullet(world, entity);
                            }
                            break;
                        case 1:
                            if (items[5].getItem() instanceof HMGItemBullet_AP){
                                bullet = FireBulletAP(world, entity);
                            }else if (items[5].getItem() instanceof HMGItemBullet_Frag){
                                bullet = FireBulletFrag(world, entity);
                            }else if (items[5].getItem() instanceof HMGItemBullet_AT){
                                bullet = FireBulletAT(world, entity);
                            }else if (items[5].getItem() instanceof HMGItemBullet_TE){
                                bullet = FireBullet(world, entity);
                            }
                            break;
                        case 2:
                            if (items[5].getItem() instanceof HMGItemBullet_AP){
                                bullet = FireBulletGL(world, entity);
                            }else if (items[5].getItem() instanceof HMGItemBullet_Frag){
                                bullet = FireBulletGL(world, entity);
                            }else if (items[5].getItem() instanceof HMGItemBullet_AT){
                                bullet = FireBulletGL(world, entity);
                            }else if (items[5].getItem() instanceof HMGItemBullet_TE){
                                bullet = FireBulletTE(world, entity);
                            }
                            break;
                        case 3:
                            if (items[5].getItem() instanceof HMGItemBullet_AP){
                                bullet = FireBulletRPG(world, entity);
                            }else if (items[5].getItem() instanceof HMGItemBullet_Frag){
                                bullet = FireBulletRPG(world, entity);
                            }else if (items[5].getItem() instanceof HMGItemBullet_AT){
                                bullet = FireBulletRPG(world, entity);
                            }else if (items[5].getItem() instanceof HMGItemBullet_TE){
                                bullet = FireBulletTE(world, entity);
                            }
                            break;
                        case 4:
                            if (items[5].getItem() instanceof HMGItemBullet_AP){
                                bullet = FireBulletAP(world, entity);
                            }else if (items[5].getItem() instanceof HMGItemBullet_Frag){
                                bullet = FireBulletHE(world, entity);
                            }else if (items[5].getItem() instanceof HMGItemBullet_AT){
                                bullet = FireBullet(world, entity);
                            }else if (items[5].getItem() instanceof HMGItemBullet_TE){
                                bullet = FireBullet(world, entity);
                            }
                            break;
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else {
                switch (guntype) {
                    case 0:
                    case 4:
                    case 1:
                        bullet = FireBullet(world, entity);
                        break;
                    case 2:
                        bullet = FireBulletGL(world, entity);
                        break;
                    case 3:
                        bullet = FireBulletRPG(world, entity);
                        break;
                    case 5:
                        bullet = FireBulletFrame(world, entity);
                        break;
                    case 6:
                        bullet = FireBulletAP(world, entity);
                        break;
                    case 7:
                        bullet = FireBulletFrag(world, entity);
                        break;
                    case 8:
                        bullet = FireBulletAT(world, entity);
                        break;
                    case 9:
                        bullet = FireBulletTE(world, entity);
                        break;
                }
            }

            if(bullet !=null){
                for(HMGEntityBulletBase bulletBase :bullet) {
                    bulletBase.knockbackXZ = knockback;
                    bulletBase.knockbackY = knockbackY;
                    bulletBase.gra = gra;
                    bulletBase.bouncerate = bouncerate;
                    bulletBase.bouncelimit = bouncelimit;
                    bulletBase.fuse = fuse;
                    bulletBase.canbounce = this.canbounce;
                    bulletBase.resistance = resistance;
                    bulletBase.acceleration = acceleration;

                    if (islockingentity) {
                        bulletBase.homingEntity = TGT;
                        bulletBase.induction_precision = induction_precision;
                    } else if (islockingblock) {
                        bulletBase.lockedBlockPos = Vec3.createVectorHelper(LockedPosX, LockedPosY, LockedPosZ);
                        bulletBase.induction_precision = induction_precision;
                    }
                    if (entity instanceof PlacedGunEntity) {
                        Vec3 vec = Vec3.createVectorHelper(posGetter.barrelpos[0],posGetter.barrelpos[1],posGetter.barrelpos[2]);
                        vec = vec.addVector( - posGetter.turretRotationPitchPoint[0], - posGetter.turretRotationPitchPoint[1], - posGetter.turretRotationPitchPoint[2]);
                        vec.rotateAroundX(-(float) toRadians(entity.rotationPitch));
                        vec = vec.addVector(   posGetter.turretRotationPitchPoint[0],   posGetter.turretRotationPitchPoint[1],   posGetter.turretRotationPitchPoint[2]);
                        vec = vec.addVector( - posGetter.turretRotationYawPoint[0], - posGetter.turretRotationYawPoint[1], - posGetter.turretRotationYawPoint[2]);
                        vec.rotateAroundY(-(float) toRadians(((PlacedGunEntity)entity).rotationYawGun - ((PlacedGunEntity)entity).baserotationYaw));
                        vec = vec.addVector(   posGetter.turretRotationYawPoint[0],   posGetter.turretRotationYawPoint[1],   posGetter.turretRotationYawPoint[2]);
                        vec.rotateAroundY(-(float) toRadians(((PlacedGunEntity)entity).baserotationYaw));
                        double ix = 0;
                        double iy = 0;
                        double iz = 0;
                        ix = vec.xCoord;
                        iy = vec.yCoord;
                        iz = vec.zCoord;
                        if(entity.riddenByEntity != null)bulletBase.thrower = entity.riddenByEntity;
                        bulletBase.setLocationAndAngles(entity.posX + ix, entity.posY + entity.getEyeHeight() + iy, entity.posZ + iz, ((PlacedGunEntity) entity).rotationYawGun,entity.rotationPitch);
                    }
                    world.spawnEntityInWorld(bulletBase);
                }
            }
        }

        if(!nbt.getBoolean("HMGfixed"))
            if(entity instanceof EntityPlayerMP) HMGPacketHandler.INSTANCE.sendTo(new PacketRecoil(), (EntityPlayerMP) entity);
        try {
            if(invocable!= null)
                invocable.invokeFunction("fireout",this,itemstack,nbt,entity);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        //private�C���q�IrightClickDelayTimer�I�ӂ����₪���Ă��I
        if(!needfix||(par1ItemStack.getTagCompound() != null && par1ItemStack.getTagCompound().getBoolean("HMGfixed"))) {
            proxy.resetRightclicktimer();
            par1ItemStack.getTagCompound().setBoolean("IsTriggered", true);
            par1ItemStack.getTagCompound().setBoolean("set_up", true);
            par1ItemStack.getTagCompound().setInteger("set_up_cnt", 10);
        }else {
            par1ItemStack.getTagCompound().setBoolean("set_up", false);
            par1ItemStack.getTagCompound().setInteger("set_up_cnt", 3);
        }
        return par1ItemStack;
    }
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {
    }
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        return false;
    }
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
        return false;
    }
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 7200;
    }

    public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_)
    {
        return 0.8F;
    }
    public boolean isFull3D()
    {
        return true;
    }
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return null;
    }
    public boolean func_150897_b(Block p_150897_1_)
    {
        return p_150897_1_ == Blocks.web;
    }
    public boolean isWeaponReload(ItemStack itemstack, EntityPlayer entityplayer) {
        return itemstack.getItemDamage() == this.getMaxDamage();
    }
    public boolean isWeaponFullAuto(ItemStack itemstack) {
        return false;
    }





    public void Flash(ItemStack par1ItemStack, World par2World, Entity entity){

        double ix = 0;
        double iy = 0;
        double iz = 0;
        if(entity instanceof PlacedGunEntity) {
            Vec3 vec = Vec3.createVectorHelper(posGetter.barrelpos[0],posGetter.barrelpos[1],posGetter.barrelpos[2]);
            vec = vec.addVector( - posGetter.turretRotationPitchPoint[0], - posGetter.turretRotationPitchPoint[1], - posGetter.turretRotationPitchPoint[2]);
            vec.rotateAroundX(-(float) toRadians(entity.rotationPitch));
            vec = vec.addVector(   posGetter.turretRotationPitchPoint[0],   posGetter.turretRotationPitchPoint[1],   posGetter.turretRotationPitchPoint[2]);
            vec = vec.addVector( - posGetter.turretRotationYawPoint[0], - posGetter.turretRotationYawPoint[1], - posGetter.turretRotationYawPoint[2]);
            vec.rotateAroundY(-(float) toRadians(((PlacedGunEntity)entity).rotationYawGun - ((PlacedGunEntity)entity).baserotationYaw));
            vec = vec.addVector(   posGetter.turretRotationYawPoint[0],   posGetter.turretRotationYawPoint[1],   posGetter.turretRotationYawPoint[2]);
            vec.rotateAroundY(-(float) toRadians(((PlacedGunEntity)entity).baserotationYaw));
            ix = vec.xCoord;
            iy = vec.yCoord + entity.getEyeHeight();
            iz = vec.zCoord;
        }else {
            float f1 = (entity instanceof EntityLivingBase ? ((EntityLivingBase) entity).rotationYawHead : entity.rotationYaw) * (2 * (float) Math.PI / 360);
            float f2 = entity.rotationPitch * (2 * (float) Math.PI / 360);
            //if (entity.isSneaking())
            if (!HandmadeGunsCore.Key_ADS(entity)) {
                ix -= MathHelper.sin(f1) * MathHelper.cos(f2) + MathHelper.cos(-f1) * 0.2;
                iy = entity.getEyeHeight() - 0.1 - MathHelper.sin(f2);
                iz += MathHelper.cos(f1) * MathHelper.cos(f2) + MathHelper.sin(-f1) * 0.2;
            } else {
                ix -= MathHelper.sin(f1) * MathHelper.cos(f2);
                iy = entity.getEyeHeight() - 0.1 - MathHelper.sin(f2);
                iz += MathHelper.cos(f1) * MathHelper.cos(f2);
            }
        }
        if (HandmadeGunsCore.cfg_muzzleflash && this.muzzle && this.muzzleflash) {
            if(!par2World.isRemote) {
                PacketSpawnParticle packet;
                if(flashname != null){
                    packet = new PacketSpawnParticle(entity.posX + ix, entity.posY + iy, entity.posZ + iz, (entity instanceof EntityLivingBase ? ((EntityLivingBase)entity).rotationYawHead : entity.rotationYaw),entity.rotationPitch,0,flashname,true);
                    packet.id = 100;
                }else {
                    packet = new PacketSpawnParticle(entity.posX + ix, entity.posY + iy, entity.posZ + iz, (entity instanceof EntityLivingBase ? ((EntityLivingBase)entity).rotationYawHead : entity.rotationYaw),entity.rotationPitch,0, 100);
                }
                packet.scale = flashScale;
                packet.fuse = flashfuse;
                HMGPacketHandler.INSTANCE.sendToAll(packet);
            }
        }
    }
    public boolean checkTags(ItemStack pitemstack) {
        if (pitemstack.hasTagCompound()) {
				/*NBTTagCompound lnbt = pitemstack.getTagCompound();
				byte lre = lnbt.getByte("RloadTime");
				if(lre != 0){
					return false;
				}else{

				}*/
            return true;
        }
        NBTTagCompound ltags = new NBTTagCompound();
        pitemstack.setTagCompound(ltags);
        ltags.setInteger("Reload", 0x0000);
        ltags.setByte("Bolt", (byte)0);
        ltags.setBoolean("SeekerOpened",true);
        return false;
    }
    protected boolean cycleBolt(ItemStack pItemstack) {
        NBTTagCompound lnbt = pItemstack.getTagCompound();
        byte lb = lnbt.getByte("Bolt");
        if (lb <= 0) {
//				if (pReset) resetBolt(pItemstack);
            byte lb2 = lnbt.getByte("Recoilcool");
            if(lb2<=0) {
                return true;
            }else {
                lnbt.setByte("Recoilcool", --lb2);
                return false;
            }
        } else {
            lnbt.setByte("Bolt", --lb);
            return false;
        }
    }
    protected void resetBolt(ItemStack pItemstack) {
        NBTTagCompound nbt = pItemstack.getTagCompound();
        nbt.setFloat("rotex",nbt.getFloat("rotex") + mat31rotex);
        nbt.setFloat("rotey",nbt.getFloat("rotey") + mat31rotey);
        nbt.setFloat("rotez",nbt.getFloat("rotez") + mat31rotez);
        pItemstack.getTagCompound().setByte("Bolt", getCycleCount(pItemstack));
    }
    public byte getCycleCount(ItemStack pItemstack) {
        return (byte) (cycle/2);
    }
    public void proceedreload(ItemStack itemstack , World world , Entity entity , NBTTagCompound nbt, int i){
        try {
            if(invocable!= null)
                invocable.invokeFunction("proceedreload",this,itemstack,nbt,entity);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) itemstack.getItem();
        int reloadti = nbt.getInteger("RloadTime");
        if ((itemstack.getItemDamage() == itemstack.getMaxDamage() || (magazine instanceof HMGItemBullet_with_Internal_Bullet && items[5] == null)) &&
                ((islmmloaded && entity instanceof LMM_EntityLittleMaid && ((LMM_EntityLittleMaid) entity).maidInventory.hasItem(magazine))
                        || (entity instanceof EntityPlayer && ((EntityPlayer)entity).inventory.hasItem(this.magazine))
                        || (entity instanceof PlacedGunEntity && entity.riddenByEntity instanceof EntityPlayer && ((EntityPlayer)entity.riddenByEntity).inventory.hasItem(this.magazine))
                        || !(entity instanceof PlacedGunEntity || entity instanceof EntityPlayer || (islmmloaded && entity instanceof LMM_EntityLittleMaid))
                        || (entity instanceof PlacedGunEntity && !(entity.riddenByEntity == null || entity.riddenByEntity instanceof EntityPlayer || (islmmloaded && entity.riddenByEntity instanceof LMM_EntityLittleMaid)))
                        || magazine == null)

                ) {
            if (reloadti == 2) {
                world.playSoundAtEntity(entity, soundre, soundrelevel, soundrespeed);
            }
            ++reloadti;
        }
        if (reloadti >= gun.reloadtime) {
            resetReload(itemstack, world, entity,i);
            nbt.setBoolean("IsReloading",false);
            nbt.setInteger("RloadTime", 0);
            nbt.setBoolean("Bursting", false);
            nbt.setInteger("RemainBurstround", getburstCount(nbt.getInteger("HMGMode")));
        }else if(nbt.getBoolean("IsReloading")){
            nbt.setInteger("RloadTime", reloadti);
        }
    }
    public void resetReload(ItemStack par1ItemStack, World par2World, Entity entity, int i) {
        //�����[�h�I��
        boolean linfinity = false;

        if (isOneuse && entity instanceof EntityPlayer) {
            par1ItemStack.stackSize--;
            if (i != -1 && par1ItemStack.stackSize == 0)
                ((EntityPlayer) entity).inventory.setInventorySlotContents(i, null);
        }else
        if (isOneuse && entity.riddenByEntity instanceof EntityPlayer) {
            par1ItemStack.stackSize--;
            if (i != -1 && par1ItemStack.stackSize == 0)
                ((EntityPlayer) entity.riddenByEntity).inventory.setInventorySlotContents(i, null);
        }
        if (!linfinity) {
            int l;
            for(l = 0; l<magazineItemCount; l++) {
                if( entity instanceof EntityPlayer) {
                    if(!((EntityPlayer) entity).inventory.consumeInventoryItem(this.magazine)) {
                        break;
                    }
//                    System.out.println("debug" + this.getMaxDamage() * (l - 1) / magazineCount);
                } else if( entity.riddenByEntity instanceof EntityPlayer) {
                    if(!((EntityPlayer) entity.riddenByEntity).inventory.consumeInventoryItem(this.magazine)) {
                        break;
                    }
//                    System.out.println("debug" + this.getMaxDamage() * (l - 1) / magazineCount);
                } else if(islmmloaded && entity instanceof LMM_EntityLittleMaid){
                    if(!((LMM_EntityLittleMaid) entity).maidInventory.consumeInventoryItem(this.magazine)) {
                        break;
                    }
                }
            }
            par1ItemStack.setItemDamage((int)(this.getMaxDamage() * (1f - (float)l / (float)magazineItemCount)));
        }
        if(!par2World.isRemote){
            if(dropMagEntity && HandmadeGunsCore.cfg_canEjectCartridge) {
                for(int l = 0; l< magentityCnt; l++) {
                    HMGEntityBulletCartridge var8;
                    if (!hascustommagemodel) {
                        var8 = new HMGEntityBulletCartridge(par2World, entity, magType);
                    } else {
                        var8 = new HMGEntityBulletCartridge(par2World, entity, -1, bulletmodelMAG);
                    }

                    par2World.spawnEntityInWorld(var8);
                }
            }
        }
    }
    public void bindattaches(ItemStack itemstack, World world, Entity entity){
        //�e�C���x���g���̃A�b�v�f�[�g
        items = null;
        try {
            foruseattackDamage = attackDamage;
            NBTTagList tags = (NBTTagList) itemstack.getTagCompound().getTag("Items");
            if (tags != null) {

                items = new ItemStack[6];
                for (int i = 0; i < tags.tagCount(); i++)//133
                {
                    NBTTagCompound tagCompound = tags.getCompoundTagAt(i);
                    int slot = tagCompound.getByte("Slot");
                    if (slot >= 0 && slot < items.length && items[slot] == null) {
                        items[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
                    }
                }
                ItemStack itemstackattach;
                itemstackattach = items[1];
                if(itemstackattach == null) {
                    posGetter.sightPos = sightPosN;
                    if(hasNightVision[0]){
                        if(entity instanceof EntityLivingBase && HandmadeGunsCore.Key_ADS(entity))((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.nightVision.id, 1, 1));
                    }
                }else {
                    if (itemstackattach.getItem() instanceof HMGItemSightBase){
                        if(((HMGItemSightBase) itemstackattach.getItem()).needgunoffset) {
                            float onads_modelPosX = 0;
                            float onads_modelPosY = 0;
                            float onads_modelPosZ = 0;
                            if (itemstackattach.getItem() instanceof HMGItemSightBase) {
                                onads_modelPosX = (sightattachoffset[0] + ((HMGItemSightBase) itemstackattach.getItem()).gunoffset[0]) * modelscale * inworldScale * onTurretScale;
                                onads_modelPosY = (sightattachoffset[1] + ((HMGItemSightBase) itemstackattach.getItem()).gunoffset[1]) * modelscale * inworldScale * onTurretScale;
                                onads_modelPosZ = (sightattachoffset[2] + ((HMGItemSightBase) itemstackattach.getItem()).gunoffset[2]) * modelscale * inworldScale * onTurretScale;
                            }
                            posGetter.sightPos = new double[]{onads_modelPosX, onads_modelPosY, onads_modelPosZ};
                            if(((HMGItemSightBase) itemstackattach.getItem()).isnightvision){
                                if(entity instanceof EntityLivingBase && HandmadeGunsCore.Key_ADS(entity))((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.nightVision.id, 1, 1));
                            }
                        }else if(itemstackattach.getItem() instanceof HMGItemAttachment_reddot){
                            if(hasNightVision[1]){
                                if(entity instanceof EntityLivingBase && HandmadeGunsCore.Key_ADS(entity))((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.nightVision.id, 1, 1));
                            }
                            posGetter.sightPos = sightPosR;
                        }else if(itemstackattach.getItem() instanceof HMGItemAttachment_scope){
                            if(hasNightVision[2]){
                                if(entity instanceof EntityLivingBase && HandmadeGunsCore.Key_ADS(entity))((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.nightVision.id, 1, 1));
                            }
                            posGetter.sightPos = sightPosS;
                        }
                    }
                }
                itemstackattach = items[2];
                if (itemstackattach != null && itemstackattach.getItem() instanceof HMGItemAttachment_laser) {
                    if (world.isRemote) {
                        Vec3 vec3 = Vec3.createVectorHelper(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
                        Vec3 playerlook = getLook(1.0f,entity);
                        playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
                        Vec3 vec31 = Vec3.createVectorHelper(entity.posX + playerlook.xCoord, entity.posY + entity.getEyeHeight() + playerlook.yCoord, entity.posZ + playerlook.zCoord);
                        MovingObjectPosition movingobjectposition = entity.worldObj.func_147447_a(vec3, vec31, false, true, false);
                        Block hitblock;
                        Random rand = new Random();
                        vec3 = Vec3.createVectorHelper(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
                        vec31 = Vec3.createVectorHelper(entity.posX + playerlook.xCoord, entity.posY + entity.getEyeHeight() + playerlook.yCoord, entity.posZ + playerlook.zCoord);
                        if (movingobjectposition != null) {
                            vec31 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
                        }
                        Entity rentity = null;
                        List list = entity.worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.addCoord(playerlook.xCoord, playerlook.yCoord, playerlook.zCoord).expand(1.0D, 1.0D, 1.0D));
                        double d0 = 0.0D;
                        double d1 = 0;
                        for (int i1 = 0; i1 < list.size(); ++i1) {
                            Entity entity1 = (Entity) list.get(i1);
                            if (entity1.canBeCollidedWith() && (entity1 != entity)) {
                                float f = 0.3F;
                                AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double) f, (double) f, (double) f);
                                MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);

                                if (movingobjectposition1 != null) {
                                    d1 = vec3.distanceTo(movingobjectposition1.hitVec);

                                    if (d1 < d0 || d0 == 0.0D) {
                                        rentity = entity1;
                                        d0 = d1;
                                    }
                                }
                            }
                        }

                        if (rentity != null) {
                            d1 = vec3.distanceTo(vec31);
                            vec3.xCoord = vec3.xCoord + (vec31.xCoord - vec3.xCoord) * d0 / d1;
                            vec3.yCoord = vec3.yCoord + (vec31.yCoord - vec3.yCoord) * d0 / d1;
                            vec3.zCoord = vec3.zCoord + (vec31.zCoord - vec3.zCoord) * d0 / d1;

                            movingobjectposition = new MovingObjectPosition(rentity);
                            movingobjectposition.hitVec = vec3;
                        }
                        if (movingobjectposition != null) {
                            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && movingobjectposition.entityHit != null) {
                                if (entity.ridingEntity == null || entity.ridingEntity != movingobjectposition.entityHit) {
                                    HMGEntityLaser var8 = new HMGEntityLaser(world,entity, 0.01F);
                                    var8.posX = movingobjectposition.hitVec.xCoord;
                                    var8.posY = movingobjectposition.hitVec.yCoord;
                                    var8.posZ = movingobjectposition.hitVec.zCoord;
                                    world.spawnEntityInWorld(var8);
                                }
                            } else {
                                if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
//									System.out.println("debug" + movingobjectposition);
                                    HMGEntityLaser var8 = new HMGEntityLaser(world,entity, 0.01F);
                                    var8.posX = movingobjectposition.hitVec.xCoord;
                                    var8.posY = movingobjectposition.hitVec.yCoord;
                                    var8.posZ = movingobjectposition.hitVec.zCoord;
                                    world.spawnEntityInWorld(var8);
                                }
                            }
                        }
                    }

                } else if (itemstackattach != null && itemstackattach.getItem() instanceof HMGItemAttachment_light) {
                    if (world.isRemote) {
                        HMGEntityLight var8 = new HMGEntityLight(world, entity, 2.0F);
                        world.spawnEntityInWorld(var8);
                    }
                }
                itemstackattach = items[3];
                if (itemstackattach != null && itemstackattach.getItem() instanceof HMGItemAttachment_Suppressor) {
                    this.sound = this.soundsu;
                    this.soundlevel = this.soundsuplevel;
                    this.muzzle = false;
                } else if (itemstackattach != null && itemstackattach.getItem() instanceof HMGXItemGun_Sword) {
                    foruseattackDamage = ((HMGXItemGun_Sword) itemstackattach.getItem()).attackDamage;
                    this.sound = this.soundbase;
                    this.soundlevel = this.soundbaselevel;
                    this.muzzle = muzzleflash;
                }else {
                    this.sound = this.soundbase;
                    this.soundlevel = this.soundbaselevel;
                    this.muzzle = muzzleflash;
                }
                if (items[4] != null && items[4].getItem() instanceof HMGItemAttachment_grip) {
                    if(HandmadeGunsCore.Key_ADS(entity)){
                        tempspread *= ((HMGItemAttachment_grip) items[4].getItem()).reduceSpreadLevel_ADS;
                    }else {
                        tempspread *= ((HMGItemAttachment_grip) items[4].getItem()).reduceSpreadLevel;
                    }
                } else if(items[4] != null && items[4].getItem() instanceof HMGItem_Unified_Guns) {
                    double ix = 0;
                    double iy = 0;
                    double iz = 0;
                    float f1 = entity.getRotationYawHead() * (2 * (float) Math.PI / 360);
                    float f2 = entity.rotationPitch * (2 * (float) Math.PI / 360);
                    if (!HandmadeGunsCore.Key_ADS(entity)) {
                        ix -= MathHelper.sin(f1) * MathHelper.cos(f2) * underoffsetpz / 4 + MathHelper.cos(-f1) * (-underoffsetpx / 4);
                        iy += -MathHelper.sin(f2) * underoffsetpz / 4 + MathHelper.cos(f2) * underoffsetpy / 4;
                        iz += MathHelper.cos(f1) * MathHelper.cos(f2) * underoffsetpz / 4 + MathHelper.sin(-f1) * (-underoffsetpx / 4);
                    } else {
                        ix -= MathHelper.sin(f1) * MathHelper.cos(f2) * underoffsetpz / 4 + MathHelper.cos(-f1) * (-underoffsetpx / 4);
                        iy += -MathHelper.sin(f2) * underoffsetpz / 4 + MathHelper.cos(f2) * underoffsetpy / 4;
                        iz += MathHelper.cos(f1) * MathHelper.cos(f2) * underoffsetpz / 4 + MathHelper.sin(-f1) * (-underoffsetpx / 4);
                    }
                    (entity).posX += ix;
                    (entity).posY += iy;
                    (entity).posZ += iz;
                    items[4].getItem().onUpdate(items[4], world, entity, -1, true);
                    ( entity).posX -= ix;
                    ( entity).posY -= iy;
                    ( entity).posZ -= iz;
                } else if (items[4] != null && items[4].getItem() instanceof HMGXItemGun_Sword) {
                    foruseattackDamage = ((HMGXItemGun_Sword) items[4].getItem()).attackDamage;
                }
            }else {
                this.sound = this.soundbase;
                this.soundlevel = this.soundbaselevel;
                this.muzzle = muzzleflash;

                posGetter.sightPos = sightPosN;
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    public int getburstCount(int mode){
        if(!burstcount.isEmpty() && burstcount.size()>mode) {
            return burstcount.get(mode);
        }else{
            return -1;
        }
    }
    public HMGEntityBulletBase[] FireBullet( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[shotgun_pellet];
        for(int i = 0;i < shotgun_pellet ; i++){
            bulletinstances[i] = new HMGEntityBullet(par2World, par3Entity,
                    this.powor, speed, tempspread, bulletmodelN);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletAP( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[shotgun_pellet];
        for(int i = 0;i < shotgun_pellet ; i++){
            bulletinstances[i] = new HMGEntityBullet_AP(par2World, par3Entity,
                    this.powor, speed, tempspread, bulletmodelAP);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletFrag( World par2World, Entity par3Entity){
        if(guntype == 1) {
            HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[1];
            for (int i = 0; i < 1; i++) {
                bulletinstances[i] = new HMGEntityBullet_Frag(par2World, par3Entity,
                        this.powor, speed, tempspread, bulletmodelFrag);
            }
            return bulletinstances;
        }else {
            HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[shotgun_pellet];
            for(int i = 0;i < shotgun_pellet ; i++){
                bulletinstances[i] = new HMGEntityBullet_Frag(par2World, par3Entity,
                        this.powor, speed, tempspread, bulletmodelFrag);
            }
            return bulletinstances;
        }
    }
    public HMGEntityBulletBase[] FireBulletAT( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[shotgun_pellet];
        for(int i = 0;i < shotgun_pellet ; i++){
            bulletinstances[i] = new HMGEntityBullet_AT(par2World, par3Entity,
                    this.powor, speed, tempspread, bulletmodelAT);
        }
        return bulletinstances;
    }



    public HMGEntityBulletBase[] FireBulletGL( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[shotgun_pellet];
        for(int i = 0;i < shotgun_pellet ; i++){
            bulletinstances[i] = new HMGEntityBulletExprode(par2World, par3Entity,
                    this.powor, speed, tempspread, ex, canex, bulletmodelGL);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletTE( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[shotgun_pellet];
        for(int i = 0;i < shotgun_pellet ; i++){
            bulletinstances[i] = new HMGEntityBullet_TE(par2World, par3Entity,
                    this.powor, speed, tempspread, 2, canex, bulletmodelTE);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletRPG( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[shotgun_pellet];
        for(int i = 0;i < shotgun_pellet ; i++){
            bulletinstances[i] = new HMGEntityBulletRocket(par2World, par3Entity,
                    this.powor, speed, tempspread, ex, canex, bulletmodelRPG);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletHE( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[shotgun_pellet];
        for(int i = 0;i < shotgun_pellet ; i++){
            bulletinstances[i] = new HMGEntityBullet_HE(par2World, par3Entity,
                    this.powor, speed, tempspread, bulletmodelHE);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletFrame( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[shotgun_pellet];
        for(int i = 0;i < shotgun_pellet ; i++){
            bulletinstances[i] = new HMGEntityBullet_Flame(par2World, par3Entity,
                    this.powor, speed, tempspread, bulletmodelN);
        }
        return bulletinstances;
    }
    public void damageMgazine(ItemStack par1ItemStack , Entity par3Entity){
        if(par3Entity instanceof EntityLivingBase) {
            if (this.magazine instanceof HMGItemBullet_with_Internal_Bullet) {
                if (items[5] != null) items[5].damageItem(1, (EntityLivingBase) par3Entity);
            } else
                par1ItemStack.damageItem(1, (EntityLivingBase) par3Entity);
        }else{
            if (par1ItemStack.isItemStackDamageable())
            {
                if (par1ItemStack.attemptDamageItem(1, itemRand))
                {
                    --par1ItemStack.stackSize;

                    if (par1ItemStack.stackSize < 0)
                    {
                        par1ItemStack.stackSize = 0;
                    }

                    par1ItemStack.setItemDamage(0);;
//                    System.out.println("debug" + par1ItemStack.getItemDamage());
                }
            }
        }
    }
    public static Vec3 getLook(float p_70676_1_,Entity entity)
    {
        float f1;
        float f2;
        float f3;
        float f4;



        f1 = MathHelper.cos(-(entity instanceof EntityLivingBase ? ((EntityLivingBase)entity).rotationYawHead : (entity instanceof PlacedGunEntity ?((PlacedGunEntity) entity).rotationYawGun:entity.rotationYaw)) * 0.017453292F - (float)Math.PI);
        f2 = MathHelper.sin(-(entity instanceof EntityLivingBase ? ((EntityLivingBase)entity).rotationYawHead : (entity instanceof PlacedGunEntity ?((PlacedGunEntity) entity).rotationYawGun:entity.rotationYaw)) * 0.017453292F - (float)Math.PI);
        f3 = -MathHelper.cos(-entity.rotationPitch * 0.017453292F);
        f4 = MathHelper.sin(-entity.rotationPitch * 0.017453292F);
        return Vec3.createVectorHelper((double)(f2 * f3), (double)f4, (double)(f1 * f3));
    }
    public Multimap getAttributeModifiers(ItemStack itemstack)
    {
        items = new ItemStack[6];
        if(itemstack.getTagCompound() != null)try {
            foruseattackDamage = attackDamage;
            NBTTagList tags = (NBTTagList) itemstack.getTagCompound().getTag("Items");
            if (tags != null) {
                for (int i = 0; i < tags.tagCount(); i++)//133
                {
                    NBTTagCompound tagCompound = tags.getCompoundTagAt(i);
                    int slot = tagCompound.getByte("Slot");
                    if (slot >= 0 && slot < items.length) {
                        items[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
                    }
                }
                ItemStack itemstackattach;
                itemstackattach = items[1];
                itemstackattach = items[2];
                itemstackattach = items[3];
                if (itemstackattach != null && itemstackattach.getItem() instanceof HMGXItemGun_Sword) {
                    foruseattackDamage = ((HMGXItemGun_Sword) itemstackattach.getItem()).attackDamage;
                }
                itemstackattach = items[4];
                if (itemstackattach != null && itemstackattach.getItem() instanceof HMGXItemGun_Sword) {
                    foruseattackDamage = ((HMGXItemGun_Sword) itemstackattach.getItem()).attackDamage;
                }
            }else {
                this.sound = this.soundbase;
                this.soundlevel = this.soundbaselevel;
                this.muzzle = muzzleflash;

                posGetter.sightPos = sightPosN;
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)this.foruseattackDamage, 0));
        multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(field_110179_h,"GunMoveFactor",motion-1,2));
        return multimap;
    }
    public void setmodelADSPosAndRotation(double px,double py,double pz){
        sightPosN = new double[]{(-px + 0.694f)*0.2 * inworldScale,(-py + 1.8f)*0.2 * inworldScale,-pz*0.2 * inworldScale};
    }
    public void setADSoffsetRed(double px,double py,double pz){
        sightPosR = new double[]{(-px + 0.694f)*0.2 * inworldScale,(-py + 1.8f)*0.2 * inworldScale,-pz*0.2 * inworldScale};
    }
    public void setADSoffsetScope(double px,double py,double pz){
        sightPosS = new double[]{(-px + 0.694f)*0.2 * inworldScale,(-py + 1.8f)*0.2 * inworldScale,-pz*0.2 * inworldScale};
    }
    public double[] getSeatpos(ItemStack itemStack){
        items = new ItemStack[6];
        try {
            foruseattackDamage = attackDamage;
            NBTTagList tags = (NBTTagList) itemStack.getTagCompound().getTag("Items");
            if (tags != null) {
                for (int i = 0; i < tags.tagCount(); i++)//133
                {
                    NBTTagCompound tagCompound = tags.getCompoundTagAt(i);
                    int slot = tagCompound.getByte("Slot");
                    if (slot >= 0 && slot < items.length) {
                        items[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
                    }
                }
                ItemStack itemstackattach;
                itemstackattach = items[1];
                if (itemstackattach == null) {
                    return sightPosN;
                } else {
                    if (itemstackattach.getItem() instanceof HMGItemSightBase && ((HMGItemSightBase) itemstackattach.getItem()).needgunoffset) {
                        double onads_modelPosX = 0;
                        double onads_modelPosY = 0;
                        double onads_modelPosZ = 0;
                        if (itemstackattach.getItem() instanceof HMGItemSightBase) {
                            onads_modelPosX = (sightattachoffset[0] + ((HMGItemSightBase) itemstackattach.getItem()).gunoffset[0]) * modelscale * inworldScale * 0.4;
                            onads_modelPosY = (sightattachoffset[1] + ((HMGItemSightBase) itemstackattach.getItem()).gunoffset[1]) * modelscale * inworldScale * 0.4;
                            onads_modelPosZ = (sightattachoffset[2] + ((HMGItemSightBase) itemstackattach.getItem()).gunoffset[2]) * modelscale * inworldScale * 0.4;
                        }
                        return new double[]{onads_modelPosX, onads_modelPosY, onads_modelPosZ};
                    } else if (itemstackattach.getItem() instanceof HMGItemAttachment_reddot) {
                        return sightPosR;
                    } else if (itemstackattach.getItem() instanceof HMGItemAttachment_scope) {
                        return sightPosS;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return sightPosN;
    }
}
