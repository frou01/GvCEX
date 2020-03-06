package handmadeguns.items.guns;

import com.google.common.collect.Multimap;
import handmadeguns.HMGPacketHandler;
import handmadeguns.HandmadeGunsCore;
import handmadeguns.Util.GunsUtils;
import handmadeguns.Util.StackAndSlot;
import handmadeguns.entity.HMGEntityLaser;
import handmadeguns.entity.HMGEntityLight;
import handmadeguns.entity.PlacedGunEntity;
import handmadeguns.entity.bullets.*;
import handmadeguns.items.*;
import handmadeguns.network.*;
import handmadevehicle.Utils;
import handmadevehicle.entity.EntityDummy_rider;
import handmadevehicle.entity.EntityVehicle;
import handmadevehicle.entity.parts.turrets.TurretObj;
import littleMaidMobX.LMM_EntityLittleMaid;
import littleMaidMobX.LMM_IEntityLittleMaidAvatarBase;
import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import javax.script.Invocable;
import javax.script.ScriptException;
import javax.vecmath.Vector3d;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static handmadeguns.HandmadeGunsCore.islmmloaded;
import static handmadeguns.HandmadeGunsCore.HMG_proxy;
import static handmadevehicle.Utils.*;
import static java.lang.Math.*;
import static java.lang.StrictMath.toRadians;
import static net.minecraft.util.MathHelper.wrapAngleTo180_double;
import static net.minecraft.util.MathHelper.wrapAngleTo180_float;
import static net.minecraft.world.World.MAX_ENTITY_RADIUS;

public class HMGItem_Unified_Guns extends Item {
    public GunInfo gunInfo = new GunInfo();
    public GunTemp guntemp;
    public FireTemp firetemp;
    public HMGItem_Unified_Guns(){
    }
    
//    public HMGItem_Unified_Guns(int p, float s, float b, double r, int rt, float at, float cz, float czr, float czs, int cy, String sd, String sds, String sdre,
//                                boolean rc, int ri, ResourceLocation tx, String aads, String aadsr, String aadss, Item ma, Item masg, Item magl, boolean cano) {
//        this();
//        this.maxStackSize = 1;
//        gunInfo.attackDamage = at;
//        // this.retime = 30;
//        gunInfo.power = p;
//        gunInfo.speed = s;
//        gunInfo.spread_setting = b;
//        gunInfo.recoil = r;
//        gunInfo.recoil_sneak = r/2;
//        gunInfo.reloadtime = rt;
//        gunInfo.scopezoom = cz;
//        gunInfo.scopezoombase = cz;
//        gunInfo.scopezoomred = czr;
//        gunInfo.scopezoomscope = czs;
//        gunInfo.sound = sd;
//        gunInfo.soundbase = sd;
//        gunInfo.soundsu = sds;
//        gunInfo.soundre = sdre;
//        gunInfo.cycle = cy;
//        gunInfo.rendercross = rc;
//        gunInfo.adstexture = aads;
//        gunInfo.adstexturer = aadsr;
//        gunInfo.adstextures = aadss;
//        gunInfo.magazine = ma;
//        gunInfo.canobj = cano;
//        setFull3D();
//    }
    
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        checkTags(par1ItemStack);
        String powor = String
                .valueOf(gunInfo.power + EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack));
        String speed = String.valueOf(gunInfo.speed);
        String bure = String.valueOf(gunInfo.spread_setting);
        String recoil = String.valueOf(gunInfo.recoil);
        NBTTagCompound nbt = par1ItemStack.getTagCompound();
        int selecting = nbt.getInteger("get_selectingMagazine");
        if(selecting >= gunInfo.reloadTimes.length)selecting = 0;
        String retime = String.valueOf(gunInfo.reloadTimes[selecting]);
        String nokori = String.valueOf(getMaxDamage() - par1ItemStack.getItemDamage());

        par3List.add(EnumChatFormatting.RED + "Magazine Round " + StatCollector.translateToLocal(nokori));
        par3List.add(EnumChatFormatting.WHITE + "FireDamege " + "+" + StatCollector.translateToLocal(powor));
        par3List.add(EnumChatFormatting.WHITE + "BulletSpeed " + "+" + StatCollector.translateToLocal(speed));
        par3List.add(EnumChatFormatting.WHITE + "BulletSpread " + "+" + StatCollector.translateToLocal(bure));
        par3List.add(EnumChatFormatting.WHITE + "Recoil " + "+" + StatCollector.translateToLocal(recoil));
        par3List.add(EnumChatFormatting.YELLOW + "ReloadTime " + "+" + StatCollector.translateToLocal(retime));
        // par3List.add(EnumChatFormatting.YELLOW + "MagazimeType " +
        // StatCollector.translateToLocal("ARMagazine"));
        if (!(gunInfo.scopezoombase == 1.0f)) {
            String scopezoom = String.valueOf(gunInfo.scopezoombase);
            par3List.add(EnumChatFormatting.WHITE + "ScopeZoom " + "x" + StatCollector.translateToLocal(scopezoom));
        }
        if(gunInfo.needfix && gunInfo.canfix){
            par3List.add(EnumChatFormatting.WHITE + "you cannot handhold Shot.");
            par3List.add(EnumChatFormatting.WHITE + "Press " + HMG_proxy.getFixkey() + " while pointing block");
        }else
        if(gunInfo.canfix){
            par3List.add(EnumChatFormatting.WHITE + "you can put on block this.");
            par3List.add(EnumChatFormatting.WHITE + "Press " + HMG_proxy.getFixkey() + " while pointing block");
        }else {
            par3List.add(EnumChatFormatting.WHITE + "you can not shot gun by hand");
            par3List.add(EnumChatFormatting.WHITE + "you can use with vehicle");
        }
    }
    public void onUpdate_fromTurret(ItemStack itemstack, World world, Entity entity, int i, boolean flag, TurretObj turretObj){
        guntemp = new GunTemp();
        guntemp.currentConnectedTurret = turretObj;
        checkTags(itemstack);
        NBTTagCompound nbt = itemstack.getTagCompound();
        guntemp.connectedTurret = nbt.getBoolean("IsTurretStack");
        nbt.setBoolean("HMGfixed",true);
        try{
            gunProcess(itemstack,world,entity,i,flag);
        }catch (Exception e){
            e.printStackTrace();
        }
        nbt.setBoolean("HMGfixed",false);
    }
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag){

        guntemp = new GunTemp();
        guntemp.invocable = (Invocable) gunInfo.script;

        gunProcess(itemstack,world,entity,i,flag);
    }
    public void gunProcess(ItemStack itemstack, World world, Entity entity, int i, boolean flag){
        try {
            if (islmmloaded && entity instanceof LMM_IEntityLittleMaidAvatarBase) {
                return;
            }
            if (entity != null && flag) {
                if (HandmadeGunsCore.cfg_Flash) {
                    int xTile = (int) entity.lastTickPosX - 1;
                    int yTile = (int) entity.lastTickPosY - 1;
                    int zTile = (int) entity.lastTickPosZ - 1;
                    world.func_147451_t(xTile, yTile, zTile);
                    world.func_147451_t(xTile - 1, yTile, zTile);
                    world.func_147451_t(xTile + 1, yTile, zTile);
                    world.func_147451_t(xTile, yTile - 1, zTile);
                    world.func_147451_t(xTile, yTile + 1, zTile);
                    world.func_147451_t(xTile, yTile, zTile - 1);
                    world.func_147451_t(xTile, yTile, zTile + 1);
                }
                checkTags(itemstack);
                NBTTagCompound nbt = itemstack.getTagCompound();




                if (guntemp.connectedTurret = nbt.getBoolean("IsTurretStack") && guntemp.currentConnectedTurret == null) {
                    return;
                }
                if (i != -10) nbt.setBoolean("IsTurretStack", false);
                guntemp.tempspread = gunInfo.spread_setting;
                if (HandmadeGunsCore.Key_ADS(entity)) {
                    guntemp.tempspread = guntemp.tempspread * gunInfo.ads_spread_cof;
                }
                guntemp.tempspreadDiffusion = nbt.getFloat("Diffusion");
                if (guntemp.tempspreadDiffusion > gunInfo.spreadDiffusionMax)
                    guntemp.tempspreadDiffusion = gunInfo.spreadDiffusionMax;
                guntemp.tempspreadDiffusion -= gunInfo.spreadDiffusionReduceRate;
                if (guntemp.tempspreadDiffusion < gunInfo.spreadDiffusionmin)
                    guntemp.tempspreadDiffusion = gunInfo.spreadDiffusionmin;
                guntemp.tempspread += gunInfo.spread_setting * guntemp.tempspreadDiffusion;


                guntemp.sound = gunInfo.soundbase;
                guntemp.soundlevel = gunInfo.soundbaselevel;
                guntemp.muzzle = gunInfo.muzzleflash;
                guntemp.selectingMagazine = nbt.getInteger("get_selectingMagazine");
                guntemp.currentMgazine = nbt.getInteger("getcurrentMagazine");

                gunInfo.posGetter.sightPos = gunInfo.sightPosN;

                guntemp.islockingentity = nbt.getBoolean("islockedentity");
                guntemp.TGT = world.getEntityByID(nbt.getInteger("TGT"));
                guntemp.islockingblock = nbt.getBoolean("islockedblock");
                guntemp.LockedPosX = nbt.getInteger("LockedPosX");
                guntemp.LockedPosY = nbt.getInteger("LockedPosY");
                guntemp.LockedPosZ = nbt.getInteger("LockedPosZ");
                int mode = nbt.getInteger("HMGMode");

                bindattaches(itemstack, world, entity);
                boolean canFixflag = gunInfo.canfix;
                try {
                    if (guntemp.items != null && guntemp.items[4] != null && guntemp.items[4].getItem() instanceof HMGItemAttachment_grip) {
                        canFixflag |= ((HMGItemAttachment_grip) guntemp.items[4].getItem()).isbase;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!world.isRemote && guntemp.currentConnectedTurret == null) {
                    float walkedDist = entity.distanceWalkedModified - nbt.getFloat("prevdistanceWalkedModified");
                    float headShakeDist;
                    if (entity instanceof EntityLivingBase) {
                        headShakeDist = abs(wrapAngleTo180_float(nbt.getFloat("prevRotationYawHead") - entity.getRotationYawHead()))
                                + abs(wrapAngleTo180_float(nbt.getFloat("prevRotationPitch") - entity.rotationPitch));
//                        System.out.println("debug" + headShakeDist);
                        nbt.setFloat("prevRotationYawHead", entity.getRotationYawHead());
                        nbt.setFloat("prevRotationPitch", entity.rotationPitch);
                    } else {
                        headShakeDist = abs(wrapAngleTo180_float(nbt.getFloat("prevRotationYawHead") - entity.rotationYaw))
                                + abs(wrapAngleTo180_float(nbt.getFloat("prevRotationPitch") - entity.rotationPitch));
                        nbt.setFloat("prevRotationYawHead", entity.rotationYaw);
                        nbt.setFloat("prevRotationPitch", entity.rotationPitch);
                    }
                    guntemp.tempspreadDiffusion += headShakeDist * gunInfo.spreadDiffusionHeadRate;
                    guntemp.tempspreadDiffusion += walkedDist * gunInfo.spreadDiffusionWalkRate;
                    if (!canFixflag || (entity.distanceWalkedModified != nbt.getFloat("prevdistanceWalkedModified"))) {
                        nbt.setFloat("prevdistanceWalkedModified", entity.distanceWalkedModified);
                        nbt.setBoolean("HMGfixed", false);
                    }
                }
                if (entity instanceof EntityPlayer) {
                    gunInfo.canceler = false;
                    try {
                        if (guntemp.invocable != null)
                            guntemp.invocable.invokeFunction("update_onplayer", this, itemstack, nbt, entity);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    if (!gunInfo.canceler) {
                        if (entity.isSneaking() || nbt.getBoolean("HMGfixed")) {
                            nbt.setBoolean("set_up", true);
                            nbt.setInteger("set_up_cnt", 3);
                        }
                        if (world.isRemote && (i != -1) && i != -10) {
                            if(!guntemp.connectedTurret){
                                HMG_proxy.force_render_item_position(itemstack, i);
                            }
                            if (!gunInfo.isOneuse && HMG_proxy.Reloadkeyispressed()) {
                                HMGPacketHandler.INSTANCE.sendToServer(new PacketreturnMgazineItem(entity.getEntityId()));
                                nbt.setInteger("RloadTime", 0);
                            }
                            if (HMG_proxy.Attachmentkeyispressed() && ((EntityPlayer) entity).inventory.getStackInSlot(i) != null && ((EntityPlayer) entity).inventory.getStackInSlot(i).getItem() instanceof HMGItem_Unified_Guns) {
                                HMGPacketHandler.INSTANCE.sendToServer(new PacketOpenGui(0, entity.getEntityId()));
                            }
                            if (HMG_proxy.lightkeydown()) {
                                nbt.setBoolean("SeekerOpened", !nbt.getBoolean("SeekerOpened"));
                                HMGPacketHandler.INSTANCE.sendToServer(new PacketSeekerOpen(entity.getEntityId()));
                            }
                            if (canFixflag && HMG_proxy.fixkeydown()) {
                                HMGPacketHandler.INSTANCE.sendToServer(new PacketFixGun(entity.getEntityId()));
                            }
                            guntemp.currentElevation++;
                            if(guntemp.currentElevation > gunInfo.elevationOffsets.size()){
                                guntemp.currentElevation = 0;
                            }
                            try {
                                if (guntemp.items != null && guntemp.items[4] != null && guntemp.items[4].getItem() instanceof HMGItem_Unified_Guns) {
                                    checkTags(guntemp.items[4]);
                                    if (((HMGItem_Unified_Guns) guntemp.items[4].getItem()).getburstCount(guntemp.items[4].getTagCompound().getInteger("HMGMode")) != -1) {
                                        if (HMG_proxy.Fclick())
                                            HMGPacketHandler.INSTANCE.sendToServer(new PacketTriggerUnder(entity.getEntityId()));
                                    } else if (HMG_proxy.Fclick_no_stopper()) {
                                        HMGPacketHandler.INSTANCE.sendToServer(new PacketTriggerUnder(entity.getEntityId()));
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (i != -1 && i != -10 && HMG_proxy.Modekeyispressed()) {
                                mode++;
                                if (mode >= gunInfo.burstcount.size() || mode >= gunInfo.rates.size()) {
                                    mode = 0;
                                }
                                nbt.setInteger("HMGMode", mode);
                                HMGPacketHandler.INSTANCE.sendToServer(new PacketChangeModeHeldItem(entity, mode));
                            }
                            if (i != -1 && i != -10 && HMG_proxy.ChangeMagazineTypeclick()) {
                                int selecting = nbt.getInteger("get_selectingMagazine");
                                selecting++;
                                if (selecting >= gunInfo.magazine.length) {
                                    selecting = 0;
                                }
                                nbt.setInteger("get_selectingMagazine", selecting);
                                HMGPacketHandler.INSTANCE.sendToServer(new PacketChangeMagazineType(entity, selecting));
                            }
                        } else {
                            if (gunInfo.canlock && nbt.getBoolean("SeekerOpened")) {
                                lockon(itemstack, world, entity, nbt);
                            }
                        }
                    }
                } else if (islmmloaded && entity instanceof LMM_EntityLittleMaid) {
                    gunInfo.canceler = false;
                    try {
                        if (guntemp.invocable != null)
                            guntemp.invocable.invokeFunction("update_onmaid", this, itemstack, nbt, entity);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    if (!gunInfo.canceler && (((LMM_EntityLittleMaid) entity).isUsingItem()))
                        nbt.setBoolean("IsTriggered", true);
                }
                if (entity instanceof EntityLiving) {
                    gunInfo.canceler = false;
                    try {
                        if (guntemp.invocable != null)
                            guntemp.invocable.invokeFunction("update_onliving", this, itemstack, nbt, entity);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    if (!gunInfo.canceler) {
                        nbt.setBoolean("SeekerOpened", false);
                        if (gunInfo.canlock) {
                            guntemp.TGT = null;
                            if(((EntityLiving) entity).getAttackTarget() != null) {
                                lockon(itemstack, world, entity, nbt);
                            }
                        }
                    }
                }
                if (entity instanceof PlacedGunEntity) {
                    gunInfo.canceler = false;
                    try {
                        if (guntemp.invocable != null)
                            guntemp.invocable.invokeFunction("update_onplacedGun", this, itemstack, nbt, entity);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    if (!gunInfo.canceler) {
                        if (gunInfo.canlock && nbt.getBoolean("SeekerOpened")) {
                            lockon(itemstack, world, entity, nbt);
                        }
                    }
                }
                try {
                    if (guntemp.invocable != null)
                        guntemp.invocable.invokeFunction("update_all", this, itemstack, nbt, entity);
                } catch (ScriptException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                {
                    if (!gunInfo.rates.isEmpty() && gunInfo.rates.size() > mode)
                        gunInfo.cycle = gunInfo.rates.get(mode);
                    boolean cocking = nbt.getBoolean("Cocking");
                    int cockingtime = nbt.getInteger("CockingTime");
                    if (!cocking && gunInfo.needcock) {
                        try {
                            if (guntemp.invocable != null)
                                guntemp.invocable.invokeFunction("proceedcock", this, itemstack, nbt, entity);
                        } catch (ScriptException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        if (cockingtime == 0) {
                            world.playSoundEffect(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ, gunInfo.soundco, 1.0F, 1.0f);
                        }
                        ++cockingtime;
                        nbt.setInteger("CockingTime", cockingtime);
                        if (cockingtime >= gunInfo.cocktime) {
                            nbt.setInteger("CockingTime", 0);
                            nbt.setBoolean("Cocking", true);
                            if (HandmadeGunsCore.cfg_canEjectCartridge && gunInfo.cart_cocked) {
                                dropCartridge(world, entity, itemstack);
                            }
                        }
                    } else nbt.setBoolean("Cocking", true);
                    boolean is_Bolt_shooting_position = cycleBolt(itemstack) && (!gunInfo.needcock || nbt.getBoolean("Cocking"));
                    boolean isbulletremaining = remain_Bullet(itemstack) > 0;
                    if (nbt.getBoolean("IsTriggered")) {
                        if (!gunInfo.needfix || nbt.getBoolean("HMGfixed")) {
                            if (!nbt.getBoolean("TriggerBacked")) {
                                if (getburstCount(mode) == 0) {
                                    nbt.setBoolean("Bursting", false);
                                } else {
                                    if (getburstCount(mode) != -1 && !gunInfo.chargeType) {
                                        if (is_Bolt_shooting_position && !nbt.getBoolean("Bursting")) {
                                            nbt.setBoolean("Bursting", true);
                                            nbt.setInteger("RemainBurstround", getburstCount(mode));
                                        }
                                        //�Z�~�I�[�gor�o�[�X�g�Ȃ̂ŘA�˒�~
                                        nbt.setBoolean("TriggerBacked", true);
                                    }
                                    if (getburstCount(mode) != -1 && gunInfo.chargeType) {
                                        //�`���[�W�^�C�v�i�������������C�j�Ȃ̂Ńg���K�[���ꂽ�t���O��true
                                        nbt.setBoolean("TriggerBacked", true);
                                    }
                                    if (!gunInfo.chargeType && !nbt.getBoolean("Bursting") && is_Bolt_shooting_position && isbulletremaining) {
                                        fireProcess(itemstack, world, entity, nbt);
                                        resetBolt(itemstack);
                                        nbt.setBoolean("Cocking", false);
                                    }
                                }
                            }
                        }
                    } else {
                        if (nbt.getBoolean("TriggerBacked") && gunInfo.chargeType) {
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
                            fireProcess(itemstack, world, entity, nbt);
                            resetBolt(itemstack);
                        }
                    }
                }
                if (remain_Bullet(itemstack) <= 0) {
                    nbt.setInteger("CockingTime", 0);
                    nbt.setBoolean("Cocking", true);
                    try {
                        if (guntemp.invocable != null)
                            guntemp.invocable.invokeFunction("startreload", this, itemstack, nbt, entity);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    nbt.setBoolean("IsReloading", true);
                    if (!nbt.getBoolean("detached")) returnInternalMagazines(itemstack, entity);
                    proceedreload(itemstack, world, entity, nbt, i);
                }
                nbt.setBoolean("IsTriggered", false);
                if (nbt.getInteger("set_up_cnt") > 0) {
                    nbt.setInteger("set_up_cnt", nbt.getInteger("set_up_cnt") - 1);
                } else {
                    nbt.setBoolean("set_up", false);
                }
//                if(entity instanceof EntityPlayerMP) {
//                    updateCheckinghSlot((EntityPlayerMP) entity, itemstack);
//                }
                if (!world.isRemote && gunInfo.canlock && nbt.getBoolean("SeekerOpened")) {
                    nbt.setBoolean("islockedentity", guntemp.islockingentity);
                    if (guntemp.TGT != null) nbt.setInteger("TGT", guntemp.TGT.getEntityId());
                    else nbt.setInteger("TGT", -1);
                    if (guntemp.islockingblock) {
                        nbt.setInteger("LockedPosX", guntemp.LockedPosX);
                        nbt.setInteger("LockedPosY", guntemp.LockedPosY);
                        nbt.setInteger("LockedPosZ", guntemp.LockedPosZ);
                    }
                    nbt.setBoolean("islockedblock", guntemp.islockingblock);
                } else {
                    if (guntemp.TGT == null || guntemp.TGT.isDead) {
                        nbt.setInteger("TGT", -1);
                    }
                }
                try {
                    if (guntemp.items != null) {
//                        for (int i1 = 0; i1 < guntemp.items.length; i1++) {
//                            if (guntemp.items[i1] != null && guntemp.items[i1].getItemDamage() > guntemp.items[i1].getMaxDamage()) {
//                                guntemp.items[i1].stackSize--;
//                            }
//                            if (guntemp.items[i1] != null && guntemp.items[i1].stackSize <= 0) {
//                                guntemp.items[i1] = null;
//                            }
//                        }
                        if (!world.isRemote) {
                            NBTTagList tags = (NBTTagList) nbt.getTag("Items");
                            int compressedID = 0;
                            if (tags != null) {
                                for (int itemid = 0; itemid < guntemp.items.length; itemid++) {
                                    if (guntemp.items[itemid] != null && guntemp.items[itemid].getItem() != null) {
                                        NBTTagCompound compound = new NBTTagCompound();
                                        compound.setByte("Slot", (byte) itemid);
                                        guntemp.items[itemid].writeToNBT(compound);
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
                                if (compressedID > 6 && compressedID < tags.tagCount()) {
                                    for (int removeid = compressedID; removeid < tags.tagCount(); removeid++) {
                                        System.out.println("debug" + compressedID);
                                        tags.removeTag(removeid);
                                    }
                                }
                                nbt.setTag("Items", tags);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //�}�K�W���̃X�^�b�N��nbt�ɕۑ�����d�l�ɕύX�\��
                if (!world.isRemote) nbt.setFloat("Diffusion", guntemp.tempspreadDiffusion);
            } else if (itemstack != null) {
                checkTags(itemstack);
                NBTTagCompound tagCompound = itemstack.getTagCompound();
                tagCompound.setInteger("RloadTime", 0);//持っていなければリロード初期化
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void lockon(ItemStack itemstack, World worldObj, Entity entity, NBTTagCompound nbt){
        if(guntemp.currentConnectedTurret != null){
            guntemp.TGT = guntemp.currentConnectedTurret.target;
            if(guntemp.TGT != null)
                guntemp.islockingentity = true;
        }else {
            if(gunInfo.lockSound_NoStop){
                guntemp.TGT = null;
                guntemp.islockingentity = false;
            }
            try {
                Vector3d frontVec = getjavaxVecObj(getLook(1,entity.getRotationYawHead(),entity.rotationPitch));
                frontVec.scale(-1);
                Entity prevTarget = guntemp.TGT;
                guntemp.TGT = null;
                guntemp.TGT = lockOn(itemstack,worldObj,entity,frontVec);
                if(guntemp.TGT != null){
                    guntemp.islockingentity = true;
                    guntemp.islockingblock = false;
                    if(guntemp.TGT.ridingEntity != null)guntemp.TGT = guntemp.TGT.ridingEntity;
                    if(guntemp.TGT instanceof EntityDummy_rider){
                        guntemp.TGT = ((EntityDummy_rider) guntemp.TGT).linkedBaseLogic.mc_Entity;
                    }
                    guntemp.TGT.getEntityData().setBoolean("behome",true);
                }
                if(guntemp.TGT != null && (gunInfo.lockSound_NoStop || guntemp.TGT != prevTarget)){
                    entity.worldObj.playSoundAtEntity(entity, gunInfo.lockSound_entity, 1f, gunInfo.lockpitch_entity);
                }
                if(gunInfo.canlockBlock){
                    Vec3 vec3 = Vec3.createVectorHelper(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
                    Vec3 playerlook = getMinecraftVecObj(frontVec);

                    playerlook.xCoord *= -1;
                    playerlook.yCoord *= -1;
                    playerlook.zCoord *= -1;

                    playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);

                    Vec3 vec31 = Vec3.createVectorHelper(entity.posX + playerlook.xCoord, entity.posY + entity.getEyeHeight() + playerlook.yCoord, entity.posZ + playerlook.zCoord);
                    MovingObjectPosition movingobjectposition = GunsUtils.getmovingobjectPosition_forBlock(worldObj,vec3, vec31, false, true, false);//衝突するブロックを調べる
                    if(movingobjectposition != null && movingobjectposition.hitVec != null){
                        guntemp.LockedPosX = movingobjectposition.blockX;
                        guntemp.LockedPosY = movingobjectposition.blockY;
                        guntemp.LockedPosZ = movingobjectposition.blockZ;
                        guntemp.islockingblock = true;
                        guntemp.islockingentity = false;
                        guntemp.TGT = null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//            Vec3 vec3 = Vec3.createVectorHelper(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
//            Vec3 playerlook = entity instanceof EntityLivingBase ? ((EntityLivingBase) entity).getLook(1.0f) : entity instanceof PlacedGunEntity ? ((PlacedGunEntity) entity).getLook(1.0f) : null;
//            playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
//            Vec3 vec31 = Vec3.createVectorHelper(entity.posX + playerlook.xCoord, entity.posY + entity.getEyeHeight() + playerlook.yCoord, entity.posZ + playerlook.zCoord);
//            MovingObjectPosition movingobjectposition = entity.worldObj.func_147447_a(vec3, vec31, false, true, false);
//            Block hitblock;
//            Random rand = new Random();
//            while (movingobjectposition != null) {
//                hitblock = entity.worldObj.getBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
//                if ((hitblock.getMaterial() == Material.plants) || ((
//                        hitblock.getMaterial() == Material.glass ||
//                                hitblock instanceof BlockFence ||
//                                hitblock instanceof BlockFenceGate ||
//                                hitblock == Blocks.iron_bars) && rand.nextInt(5) <= 1)) {
//                    Vec3 penerater = movingobjectposition.hitVec.normalize();
//                    vec3 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord + penerater.xCoord, movingobjectposition.hitVec.yCoord + penerater.yCoord, movingobjectposition.hitVec.zCoord + penerater.zCoord);
//                    vec31 = Vec3.createVectorHelper(entity.posX + playerlook.xCoord, entity.posY + entity.getEyeHeight() + playerlook.yCoord, entity.posZ + playerlook.zCoord);
//                    movingobjectposition = entity.worldObj.func_147447_a(vec3, vec31, false, true, false);
//                } else {
//                    break;
//                }
//            }
//            vec3 = Vec3.createVectorHelper(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
//            vec31 = Vec3.createVectorHelper(entity.posX + playerlook.xCoord, entity.posY + entity.getEyeHeight() + playerlook.yCoord, entity.posZ + playerlook.zCoord);
//            if (movingobjectposition != null) {
//                vec31 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
//            }
//            Entity rentity = null;
//            List list = entity.worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.addCoord(playerlook.xCoord, playerlook.yCoord, playerlook.zCoord).expand(1.0D, 1.0D, 1.0D));
//            double d0 = 0.0D;
//            double d1 = 0;
//            for (int i1 = 0; i1 < list.size(); ++i1) {
//                Entity entity1 = (Entity) list.get(i1);
//                if (entity1.canBeCollidedWith() && (entity1 != entity)) {
//                    float f = 0.5F;
//                    AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double) f, (double) f, (double) f);
//                    MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);
//
//                    if (movingobjectposition1 != null) {
//                        d1 = vec3.distanceTo(movingobjectposition1.hitVec);
//
//                        if (d1 < d0 || d0 == 0.0D) {
//                            rentity = entity1;
//                            d0 = d1;
//                        }
//                    }
//                }
//            }
//
//            if (rentity != null) {
//                d1 = vec3.distanceTo(vec31);
//                vec3.xCoord = vec3.xCoord + (vec31.xCoord - vec3.xCoord) * d0 / d1;
//                vec3.yCoord = vec3.yCoord + (vec31.yCoord - vec3.yCoord) * d0 / d1;
//                vec3.zCoord = vec3.zCoord + (vec31.zCoord - vec3.zCoord) * d0 / d1;
//
//                movingobjectposition = new MovingObjectPosition(rentity);
//                movingobjectposition.hitVec = vec3;
//            }
//            if (movingobjectposition != null) {
//                if (gunInfo.canlockEntity && movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && movingobjectposition.entityHit != null) {
//                    if (!guntemp.islockingentity || (entity.ridingEntity == null || entity.ridingEntity != movingobjectposition.entityHit) && guntemp.TGT != movingobjectposition.entityHit) {
//                        entity.worldObj.playSoundAtEntity(entity, gunInfo.lockSound_entity, 1f, gunInfo.lockpitch_entity);
//                        guntemp.TGT = movingobjectposition.entityHit;
//                        guntemp.islockingentity = true;
//                        guntemp.islockingblock = false;
//                    }
//                } else if (gunInfo.canlockBlock && movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && (
//                        guntemp.LockedPosX != movingobjectposition.blockX || guntemp.LockedPosY != movingobjectposition.blockY || guntemp.LockedPosZ != movingobjectposition.blockZ)) {
//                    entity.worldObj.playSoundAtEntity(entity, gunInfo.lockSound_block, 1f, gunInfo.lockpitch_block);
//                    guntemp.LockedPosX = movingobjectposition.blockX;
//                    guntemp.LockedPosY = movingobjectposition.blockY;
//                    guntemp.LockedPosZ = movingobjectposition.blockZ;
//                    guntemp.islockingblock = true;
//                    guntemp.islockingentity = false;
//                    guntemp.TGT = null;
//                }
//            }
//
//            if (gunInfo.canlock) {
////                if (guntemp.islockingblock) {
////                    HMG_proxy.spawnParticles(new PacketSpawnParticle(guntemp.LockedPosX + 0.5, guntemp.LockedPosY + 0.5, guntemp.LockedPosZ + 0.5, 2));
////                }
////                if (guntemp.islockingentity && guntemp.TGT != null) {
////                    HMG_proxy.spawnParticles(new PacketSpawnParticle(guntemp.TGT.posX, guntemp.TGT.posY + guntemp.TGT.height / 2, guntemp.TGT.posZ, 2));
////                }
//            }
        }
    }
    public Entity lockOn(ItemStack itemstack,World worldObj,Entity entity,Vector3d frontVec){
        double predeg = -1;
        Entity target = null;
        if(gunInfo.canlockEntity && remain_Bullet(itemstack)>0) {
            for (Object obj : worldObj.loadedEntityList) {
                Entity aEntity = (Entity) obj;

                if (!aEntity.isDead) {
                    if (aEntity.canBeCollidedWith() &&
                            (!gunInfo.lock_to_Vehicle || aEntity.width > 1.2)) {
                        double distsq = entity.getDistanceSqToEntity(aEntity);
                        if (distsq < 16777216) {
                            Vector3d totgtvec = new Vector3d(entity.posX - aEntity.posX, entity.posY - aEntity.posY, entity.posZ - aEntity.posZ);
                            if (totgtvec.length() > 1) {
                                totgtvec.normalize();
                                if (totgtvec.y < gunInfo.lookDown) {
                                    double deg = wrapAngleTo180_double(toDegrees(totgtvec.angle(frontVec)));
                                    if (canLock(entity,aEntity) && gunInfo.seekerSize > abs(deg) && (abs(deg) < predeg || predeg == -1)) {
                                        predeg = deg;
                                        target = aEntity;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return target;
    }
    public boolean canLock(Entity my,Entity entity){
        if(!Utils.iscandamageentity(my,entity))return false;
        if(my instanceof EntityLivingBase && !((EntityLivingBase) my).canEntityBeSeen(entity))return false;
        if(my instanceof PlacedGunEntity && my.riddenByEntity instanceof EntityLivingBase && !((EntityLivingBase) my.riddenByEntity).canEntityBeSeen(entity))return false;
        double targetEntitySpeed = getEntitySpeedSQ(entity);
        return (gunInfo.lockOn_minSpeed == -1 || targetEntitySpeed > gunInfo.lockOn_minSpeed) && (gunInfo.lockOn_MaxSpeed == -1 || targetEntitySpeed < gunInfo.lockOn_MaxSpeed) &&
                (!(entity instanceof EntityVehicle) || ((gunInfo.lockOn_minThrottle == -1 || abs(((EntityVehicle) entity).getBaseLogic().throttle) > gunInfo.lockOn_minThrottle) && (gunInfo.lockOn_MaxThrottle == -1 || abs(((EntityVehicle) entity).getBaseLogic().throttle) < gunInfo.lockOn_MaxThrottle)));
    }
    public double getEntitySpeedSQ(Entity entity){
        return entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ;
    }
    public void fireProcess(ItemStack itemstack, World world, Entity entity, NBTTagCompound nbt){
    	int fireLoop = 1;
    	if(guntemp.currentConnectedTurret != null){
		    fireLoop = guntemp.currentConnectedTurret.getSyncroFireNum();
	    }
    	for(int fired = 0;fired < fireLoop;fired ++) {
		    if (!world.isRemote) {
			    guntemp.tempspreadDiffusion += gunInfo.spreadDiffusionRate * (guntemp.currentConnectedTurret == null && entity instanceof EntityLiving && entity.rotationPitch < 0 ? sin(entity.rotationPitch):1);
			    try {
				    if (guntemp.invocable != null)
					    guntemp.invocable.invokeFunction("prefire", this, itemstack, nbt, entity);
			    } catch (ScriptException e) {
				    e.printStackTrace();
			    } catch (NoSuchMethodException e) {
				    e.printStackTrace();
			    }
			    entity.getEntityData().setFloat("GunshotLevel", guntemp.soundlevel);
			    HMG_proxy.playerSounded(entity);
//            world.playSoundEffect(entity.posX,entity.posY,entity.posZ, sound, soundlevel, soundspeed);
			
			
			    damageMagazine(itemstack, entity);
			    if (HandmadeGunsCore.cfg_canEjectCartridge && gunInfo.dropcart) {
				    dropCartridge(world, entity, itemstack);
			    }
			    HMGEntityBulletBase[] bullet = null;
			    //メソッド一個追加して、弾種類で分けながらfor回したほうが絶対効率良さそう<-んな訳あるか
			    int currentBulletType = gunInfo.guntype;
			    if (gunInfo.guntype < 5 && guntemp.items != null && guntemp.items[5] != null) {
				    try {
					    switch (gunInfo.guntype) {
						    case 0:
						    case 1:
							    if (guntemp.items[5].getItem() instanceof HMGItemBullet_AP) {
								    currentBulletType = 6;
							    } else if (guntemp.items[5].getItem() instanceof HMGItemBullet_Frag) {
								    currentBulletType = 7;
							    } else if (guntemp.items[5].getItem() instanceof HMGItemBullet_AT) {
								    currentBulletType = 8;
							    }
							    break;
						    case 2:
						    case 3:
							    if (guntemp.items[5].getItem() instanceof HMGItemBullet_TE) {
								    currentBulletType = 9;
							    }
							    break;
						    case 4:
							    if (guntemp.items[5].getItem() instanceof HMGItemBullet_AP) {
								    currentBulletType = 6;
							    } else if (guntemp.items[5].getItem() instanceof HMGItemBullet_Frag) {
								    currentBulletType = 10;
							    }
							    break;
					    }
				    } catch (NullPointerException e) {
					    e.printStackTrace();
				    }
			    }
			    firetemp = new FireTemp(gunInfo);
			    int magazineBulletOption = get_Type_Option_of_currentMagzine_and_apply_magazine_Option(itemstack);
			    if (magazineBulletOption != -1) currentBulletType = magazineBulletOption;
			    float backUpRotationPitch = entity.rotationPitch;
			    if(!gunInfo.elevationOffsets.isEmpty()){
			        entity.rotationPitch += gunInfo.elevationOffsets.get(guntemp.currentElevation);
                }
			    switch (currentBulletType) {
				    case 0:
				    case 1:
				    case 4:
					    bullet = getBullet(world, entity);
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
					    bullet = getBulletAP(world, entity);
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
				    case 10:
					    bullet = FireBulletHE(world, entity);
					    break;
				    case 11:
					    bullet = FireBulletTorp(world, entity);
					    break;
			    }
			
			    if (bullet != null) {
                    if (guntemp.currentConnectedTurret != null) {
                        guntemp.currentConnectedTurret.setBulletsPos(bullet);
                    }else this.Flash(itemstack, world, entity, nbt);
				    for (HMGEntityBulletBase bulletBase : bullet) {
					    bulletBase.knockbackXZ = gunInfo.knockback;
					    bulletBase.knockbackY = gunInfo.knockbackY;
					    bulletBase.gra = gunInfo.gravity;
					    bulletBase.bouncerate = gunInfo.bouncerate;
					    bulletBase.bouncelimit = gunInfo.bouncelimit;
					    bulletBase.fuse = firetemp.fuse;
					    bulletBase.canbounce = gunInfo.canbounce;
					    bulletBase.resistance = gunInfo.resistance;
					    bulletBase.acceleration = gunInfo.acceleration;
					    bulletBase.canex = firetemp.destroyBlock;
					    if(bulletBase instanceof HMGEntityBulletTorp)((HMGEntityBulletTorp) bulletBase).draft = gunInfo.torpdraft;
					    bulletBase.damageRange = gunInfo.damagerange;
					    bulletBase.hasVT   = gunInfo.hasVT  ;
					    bulletBase.VTRange = gunInfo.VTRange;
					    bulletBase.VTWidth = gunInfo.VTWidth;
                        bulletBase.seekerwidth = gunInfo.seekerSize_bullet;
					    bulletBase.resistanceinwater = gunInfo.resistanceinWater;
                        if (guntemp.currentConnectedTurret == null) {
                            bulletBase.prevRotationYaw = bulletBase.rotationYaw = wrapAngleTo180_float(entity.getRotationYawHead());
                            bulletBase.prevRotationPitch = bulletBase.rotationPitch = entity.rotationPitch;
                        }

                        if (guntemp.currentConnectedTurret == null) {
                            if (guntemp.islockingentity) {
                                bulletBase.homingEntity = guntemp.TGT;
                                bulletBase.induction_precision = gunInfo.induction_precision;
                            } else if (guntemp.islockingblock) {
                                bulletBase.lockedBlockPos = Vec3.createVectorHelper(guntemp.LockedPosX, guntemp.LockedPosY, guntemp.LockedPosZ);
                                bulletBase.induction_precision = gunInfo.induction_precision;
                            }
                            if (entity instanceof PlacedGunEntity) {
                                setBulletPos_PlacedGun(entity, bulletBase, nbt);
                                bulletBase.prevRotationYaw = bulletBase.rotationYaw = wrapAngleTo180_float(entity.getRotationYawHead());
                                bulletBase.prevRotationPitch = bulletBase.rotationPitch = entity.rotationPitch;
                            }
                        }else {
                            if(gunInfo.canlock) {
                                if (guntemp.islockingentity) {
                                    bulletBase.homingEntity = guntemp.currentConnectedTurret.target;
                                    bulletBase.induction_precision = gunInfo.induction_precision;
                                } else if (guntemp.islockingblock) {
                                    bulletBase.lockedBlockPos = guntemp.currentConnectedTurret.lockedBlockPos;
                                    bulletBase.induction_precision = gunInfo.induction_precision;
                                }
                            }
                        }
					    world.spawnEntityInWorld(bulletBase);
				    }
			    }
			    entity.rotationPitch = backUpRotationPitch;
                HMGPacketHandler.INSTANCE.sendToAll(new PacketPlaysound(entity, guntemp.sound, gunInfo.soundspeed, guntemp.soundlevel,entity.posX,entity.posY,entity.posZ));
		    }
		
		    if (!nbt.getBoolean("HMGfixed"))
			    if (entity instanceof EntityPlayerMP)
				    HMGPacketHandler.INSTANCE.sendTo(new PacketRecoil(), (EntityPlayerMP) entity);
		    try {
			    if (guntemp.invocable != null)
				    guntemp.invocable.invokeFunction("fireout", this, itemstack, nbt, entity);
		    } catch (ScriptException e) {
			    e.printStackTrace();
		    } catch (NoSuchMethodException e) {
			    e.printStackTrace();
		    }
	    }
    }
    public void setBulletPos_PlacedGun(Entity entity,HMGEntityBulletBase bulletBase,NBTTagCompound nbt){
	    if (gunInfo.posGetter.multi_barrelpos == null) {
		    Vec3 vec = Vec3.createVectorHelper(gunInfo.posGetter.barrelpos[0], gunInfo.posGetter.barrelpos[1], gunInfo.posGetter.barrelpos[2]);
		    vec = vec.addVector(-gunInfo.posGetter.turretRotationPitchPoint[0], -gunInfo.posGetter.turretRotationPitchPoint[1], -gunInfo.posGetter.turretRotationPitchPoint[2]);
		    vec.rotateAroundX(-(float) toRadians(entity.rotationPitch));
		    vec = vec.addVector(gunInfo.posGetter.turretRotationPitchPoint[0], gunInfo.posGetter.turretRotationPitchPoint[1], gunInfo.posGetter.turretRotationPitchPoint[2]);
		    vec = vec.addVector(-gunInfo.posGetter.turretRotationYawPoint[0], -gunInfo.posGetter.turretRotationYawPoint[1], -gunInfo.posGetter.turretRotationYawPoint[2]);
		    vec.rotateAroundY(-(float) toRadians(((PlacedGunEntity) entity).rotationYawGun - ((PlacedGunEntity) entity).baserotationYaw));
		    vec = vec.addVector(gunInfo.posGetter.turretRotationYawPoint[0], gunInfo.posGetter.turretRotationYawPoint[1], gunInfo.posGetter.turretRotationYawPoint[2]);
		    vec.rotateAroundY(-(float) toRadians(((PlacedGunEntity) entity).baserotationYaw));
		    double ix = 0;
		    double iy = 0;
		    double iz = 0;
		    ix = vec.xCoord;
		    iy = vec.yCoord;
		    iz = vec.zCoord;
		    if (entity.riddenByEntity != null) bulletBase.thrower = entity.riddenByEntity;
		    bulletBase.setLocationAndAngles(entity.posX + ix, entity.posY + entity.getEyeHeight() + iy, entity.posZ + iz, ((PlacedGunEntity) entity).rotationYawGun, entity.rotationPitch);
	    } else {
		    int barrelId = nbt.getInteger("barrelId");
		    barrelId++;
		    if (barrelId >= gunInfo.posGetter.multi_barrelpos.length) barrelId = 0;
		    Vec3 vec = Vec3.createVectorHelper(gunInfo.posGetter.multi_barrelpos[barrelId][0], gunInfo.posGetter.multi_barrelpos[barrelId][1], gunInfo.posGetter.multi_barrelpos[barrelId][2]);
		    vec = vec.addVector(-gunInfo.posGetter.turretRotationPitchPoint[0], -gunInfo.posGetter.turretRotationPitchPoint[1], -gunInfo.posGetter.turretRotationPitchPoint[2]);
		    vec.rotateAroundX(-(float) toRadians(entity.rotationPitch));
		    vec = vec.addVector(gunInfo.posGetter.turretRotationPitchPoint[0], gunInfo.posGetter.turretRotationPitchPoint[1], gunInfo.posGetter.turretRotationPitchPoint[2]);
		    vec = vec.addVector(-gunInfo.posGetter.turretRotationYawPoint[0], -gunInfo.posGetter.turretRotationYawPoint[1], -gunInfo.posGetter.turretRotationYawPoint[2]);
		    vec.rotateAroundY(-(float) toRadians(((PlacedGunEntity) entity).rotationYawGun - ((PlacedGunEntity) entity).baserotationYaw));
		    vec = vec.addVector(gunInfo.posGetter.turretRotationYawPoint[0], gunInfo.posGetter.turretRotationYawPoint[1], gunInfo.posGetter.turretRotationYawPoint[2]);
		    vec.rotateAroundY(-(float) toRadians(((PlacedGunEntity) entity).baserotationYaw));
		    double ix = 0;
		    double iy = 0;
		    double iz = 0;
		    ix = vec.xCoord;
		    iy = vec.yCoord;
		    iz = vec.zCoord;
		    if (entity.riddenByEntity != null) bulletBase.thrower = entity.riddenByEntity;
		    bulletBase.setLocationAndAngles(entity.posX + ix, entity.posY + entity.getEyeHeight() + iy, entity.posZ + iz, ((PlacedGunEntity) entity).rotationYawGun, entity.rotationPitch);
		    nbt.setInteger("barrelId", barrelId);
	    }
    }
    public void dropCartridge(World world,Entity entity,ItemStack itemStack){
        HMGEntityBulletCartridge var8;
        for(int i=0;i < gunInfo.cartentityCnt;i++) {
            String currentMagCart = currentMagazine_cartridgeModelName(itemStack);
            if(currentMagCart == null){
                if (!gunInfo.hascustomcartridgemodel)
                    var8 = new HMGEntityBulletCartridge(world, entity, gunInfo.cartType);
                else {
                    var8 = new HMGEntityBulletCartridge(world, entity, -1, gunInfo.bulletmodelCart);
                }
            }else
                var8 = new HMGEntityBulletCartridge(world, entity, -1, currentMagCart);
            var8.rotationYaw += 90;
            Item cartItem = currentMagazine_cartridgeItem(itemStack);
            if(cartItem != null)var8.itemStack = new ItemStack(cartItem);
            world.spawnEntityInWorld(var8);
        }
    }
    public HMGEntityBulletBase[] getBullet(World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[gunInfo.pellet];
        for(int i = 0; i < gunInfo.pellet; i++){
            bulletinstances[i] = new HMGEntityBullet(par2World, par3Entity,
                                                            firetemp.power, firetemp.speed, guntemp.tempspread, firetemp.model != null?firetemp.model:gunInfo.bulletmodelN);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] getBulletAP(World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[gunInfo.pellet];
        for(int i = 0; i < gunInfo.pellet; i++){
            bulletinstances[i] = new HMGEntityBullet_AP(par2World, par3Entity,
                                                               firetemp.power, firetemp.speed, guntemp.tempspread, firetemp.model != null?firetemp.model:gunInfo.bulletmodelAP);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletFrag(World par2World, Entity par3Entity){
        if(gunInfo.guntype == 1) {
            HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[1];
            bulletinstances[0] = new HMGEntityBullet_Frag(par2World, par3Entity,
                                                                 firetemp.power, firetemp.speed, guntemp.tempspread, firetemp.model != null?firetemp.model:gunInfo.bulletmodelFrag);
    
            return bulletinstances;
        }else {
            HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[gunInfo.pellet];
            for(int i = 0; i < gunInfo.pellet; i++){
                bulletinstances[i] = new HMGEntityBullet_Frag(par2World, par3Entity,
                                                                     firetemp.power, firetemp.speed, guntemp.tempspread, firetemp.model != null?firetemp.model:gunInfo.bulletmodelFrag);
            }
            return bulletinstances;
        }
    }
    public HMGEntityBulletBase[] FireBulletAT(World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[gunInfo.pellet];
        for(int i = 0; i < gunInfo.pellet; i++){
            bulletinstances[i] = new HMGEntityBullet_AT(par2World, par3Entity,
                                                               firetemp.power, firetemp.speed, guntemp.tempspread, firetemp.model != null?firetemp.model:gunInfo.bulletmodelAT);
        }
        return bulletinstances;
    }
    
    
    
    public HMGEntityBulletBase[] FireBulletGL(World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[gunInfo.pellet];
        for(int i = 0; i < gunInfo.pellet; i++){
            bulletinstances[i] = new HMGEntityBulletExprode(par2World, par3Entity,
                                                                   firetemp.power, firetemp.speed, guntemp.tempspread,firetemp.exlevel,firetemp.destroyBlock, firetemp.model != null?firetemp.model:gunInfo.bulletmodelGL);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletTE(World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[gunInfo.pellet];
        for(int i = 0; i < gunInfo.pellet; i++){
            bulletinstances[i] = new HMGEntityBullet_TE(par2World, par3Entity,
                                                               firetemp.power, firetemp.speed, guntemp.tempspread,firetemp.exlevel,firetemp.destroyBlock, firetemp.model != null?firetemp.model:gunInfo.bulletmodelTE);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletRPG(World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[gunInfo.pellet];
        for(int i = 0; i < gunInfo.pellet; i++){
            bulletinstances[i] = new HMGEntityBulletRocket(par2World, par3Entity,
                                                                  firetemp.power, firetemp.speed, guntemp.tempspread,firetemp.exlevel,firetemp.destroyBlock, firetemp.model != null?firetemp.model:gunInfo.bulletmodelRPG);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletHE(World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[gunInfo.pellet];
        for(int i = 0; i < gunInfo.pellet; i++){
            bulletinstances[i] = new HMGEntityBullet_HE(par2World, par3Entity,
                                                               firetemp.power, firetemp.speed, guntemp.tempspread, firetemp.model != null?firetemp.model: gunInfo.bulletmodelHE);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletFrame(World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[gunInfo.pellet];
        for(int i = 0; i < gunInfo.pellet; i++){
            bulletinstances[i] = new HMGEntityBullet_Flame(par2World, par3Entity,
                                                                  firetemp.power, firetemp.speed, guntemp.tempspread, firetemp.model != null?firetemp.model: gunInfo.bulletmodelN);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletTorp(World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[gunInfo.pellet];
        for(int i = 0; i < gunInfo.pellet; i++){
            bulletinstances[i] = new HMGEntityBulletTorp(par2World, par3Entity,
                                                                firetemp.power, firetemp.speed, guntemp.tempspread,firetemp.exlevel,firetemp.destroyBlock, firetemp.model != null?firetemp.model:gunInfo.bulletmodelRPG);
        }
        return bulletinstances;
    }
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
    	checkTags(par1ItemStack);
        if(!gunInfo.needfix || (par1ItemStack.getTagCompound().getBoolean("HMGfixed"))) {
            HMG_proxy.resetRightclicktimer();
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
        return remain_Bullet(itemstack) <= 0;
    }
    public boolean isWeaponFullAuto(ItemStack itemstack) {
        return false;
    }





    public void Flash(ItemStack par1ItemStack, World par2World, Entity entity,NBTTagCompound nbt){

        double ix = 0;
        double iy = 0;
        double iz = 0;
        if(entity instanceof PlacedGunEntity) {
            if(gunInfo.posGetter.multi_barrelpos == null) {
                Vec3 vec = Vec3.createVectorHelper(gunInfo.posGetter.barrelpos[0], gunInfo.posGetter.barrelpos[1], gunInfo.posGetter.barrelpos[2]);
                vec = vec.addVector(-gunInfo.posGetter.turretRotationPitchPoint[0], -gunInfo.posGetter.turretRotationPitchPoint[1], -gunInfo.posGetter.turretRotationPitchPoint[2]);
                vec.rotateAroundX(-(float) toRadians(entity.rotationPitch));
                vec = vec.addVector(gunInfo.posGetter.turretRotationPitchPoint[0], gunInfo.posGetter.turretRotationPitchPoint[1], gunInfo.posGetter.turretRotationPitchPoint[2]);
                vec = vec.addVector(-gunInfo.posGetter.turretRotationYawPoint[0], -gunInfo.posGetter.turretRotationYawPoint[1], -gunInfo.posGetter.turretRotationYawPoint[2]);
                vec.rotateAroundY(-(float) toRadians(((PlacedGunEntity) entity).rotationYawGun - ((PlacedGunEntity) entity).baserotationYaw));
                vec = vec.addVector(gunInfo.posGetter.turretRotationYawPoint[0], gunInfo.posGetter.turretRotationYawPoint[1], gunInfo.posGetter.turretRotationYawPoint[2]);
                vec.rotateAroundY(-(float) toRadians(((PlacedGunEntity) entity).baserotationYaw));
                ix = vec.xCoord;
                iy = vec.yCoord + entity.getEyeHeight();
                iz = vec.zCoord;
            }else {
                int barrelId = nbt.getInteger("barrelId");
                if(barrelId >= gunInfo.posGetter.multi_barrelpos.length)barrelId = 0;
                Vec3 vec = Vec3.createVectorHelper(gunInfo.posGetter.multi_barrelpos[barrelId][0], gunInfo.posGetter.multi_barrelpos[barrelId][1], gunInfo.posGetter.multi_barrelpos[barrelId][2]);
                vec = vec.addVector(-gunInfo.posGetter.turretRotationPitchPoint[0], -gunInfo.posGetter.turretRotationPitchPoint[1], -gunInfo.posGetter.turretRotationPitchPoint[2]);
                vec.rotateAroundX(-(float) toRadians(entity.rotationPitch));
                vec = vec.addVector(gunInfo.posGetter.turretRotationPitchPoint[0], gunInfo.posGetter.turretRotationPitchPoint[1], gunInfo.posGetter.turretRotationPitchPoint[2]);
                vec = vec.addVector(-gunInfo.posGetter.turretRotationYawPoint[0], -gunInfo.posGetter.turretRotationYawPoint[1], -gunInfo.posGetter.turretRotationYawPoint[2]);
                vec.rotateAroundY(-(float) toRadians(((PlacedGunEntity) entity).rotationYawGun - ((PlacedGunEntity) entity).baserotationYaw));
                vec = vec.addVector(gunInfo.posGetter.turretRotationYawPoint[0], gunInfo.posGetter.turretRotationYawPoint[1], gunInfo.posGetter.turretRotationYawPoint[2]);
                vec.rotateAroundY(-(float) toRadians(((PlacedGunEntity) entity).baserotationYaw));
                ix = vec.xCoord;
                iy = vec.yCoord + entity.getEyeHeight();
                iz = vec.zCoord;
            }
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
        if (HandmadeGunsCore.cfg_muzzleflash && guntemp.muzzle && gunInfo.muzzleflash) {
            if(!par2World.isRemote) {
                PacketSpawnParticle packet;
                if(gunInfo.flashname != null){
                    packet = new PacketSpawnParticle(entity.posX + ix, entity.posY + iy, entity.posZ + iz, (entity instanceof EntityLivingBase ? ((EntityLivingBase)entity).rotationYawHead : entity.rotationYaw),entity.rotationPitch,0, gunInfo.flashname,true);
                    packet.id = 100;
                }else {
                    packet = new PacketSpawnParticle(entity.posX + ix, entity.posY + iy, entity.posZ + iz,
		                    (entity instanceof EntityLivingBase ? ((EntityLivingBase)entity).rotationYawHead : entity.rotationYaw),
		                    entity.rotationPitch,0, 100);
                }
                packet.scale = gunInfo.flashScale;
                packet.fuse = gunInfo.flashfuse;
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
        ltags.setBoolean("IsReloading",false);
        ltags.setInteger("Reload", 0x0000);
        ltags.setBoolean("SeekerOpened",true);
        ltags.setTag("Items", new NBTTagList());
        return false;
    }
    protected boolean cycleBolt(ItemStack pItemstack) {
        NBTTagCompound lnbt = pItemstack.getTagCompound();
        byte lb = lnbt.getByte("Bolt");
        lb--;
        if (lb <= 0) {
            lnbt.setBoolean("Recoiled", true);
            lnbt.setByte("Bolt", (byte) 0);
            return true;
        } else {
            lnbt.setBoolean("Recoiled", false);
            lnbt.setByte("Bolt", lb);
            return false;
        }
    }
    protected void resetBolt(ItemStack pItemstack) {
        NBTTagCompound nbt = pItemstack.getTagCompound();
        nbt.setFloat("rotex",nbt.getFloat("rotex") + gunInfo.mat31rotex);
        nbt.setFloat("rotey",nbt.getFloat("rotey") + gunInfo.mat31rotey);
        nbt.setFloat("rotez",nbt.getFloat("rotez") + gunInfo.mat31rotez);
        pItemstack.getTagCompound().setBoolean("Recoiled", false);
        pItemstack.getTagCompound().setByte("Bolt", getCycleCount(pItemstack));
    }
    public byte getCycleCount(ItemStack pItemstack) {
        boolean addPlusOne = gunInfo.cycle%1 > itemRand.nextFloat();
        byte temp = (byte)((gunInfo.cycle - gunInfo.cycle%1) + (addPlusOne?1:0));
        if(temp == 0){
            temp = 1;
        }
        return temp;
    }
    public void proceedreload(ItemStack itemstack , World world , Entity entity , NBTTagCompound nbt, int i){
        nbt.setInteger("getcurrentMagazine",nbt.getInteger("get_selectingMagazine"));
        try {
            if(guntemp.invocable!= null)
                guntemp.invocable.invokeFunction("proceedreload",this,itemstack,nbt,entity);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        int reloadti = nbt.getInteger("RloadTime");
        if (remain_Bullet(itemstack) <= 0 &&
                    canreloadBullets(itemstack,world,entity)
                ) {
            if (!world.isRemote && reloadti == 0) {
                HMGPacketHandler.INSTANCE.sendToAll(new PacketPlaysound(entity, gunInfo.soundre.length > nbt.getInteger("getcurrentMagazine") ? gunInfo.soundre[nbt.getInteger("getcurrentMagazine")]:gunInfo.soundre[0], gunInfo.soundrespeed , gunInfo.soundrelevel,true));
            }
            ++reloadti;
        }else {
            nbt.setBoolean("IsReloading", false);
            reloadti = 0;
        }
        if(!world.isRemote) {
            if (reloadti >= reloadTime(itemstack)) {
                resetReload(itemstack, world, entity, i);
                if (gunInfo.needFirstCock) {
                    nbt.setBoolean("cocking", false);
                } else {
                    nbt.setBoolean("cocking", true);
                }
                nbt.setBoolean("IsReloading", false);
                nbt.setInteger("RloadTime", 0);
                nbt.setBoolean("Bursting", false);
                nbt.setInteger("RemainBurstround", getburstCount(nbt.getInteger("HMGMode")));
            } else if (nbt.getBoolean("IsReloading")) {
                nbt.setInteger("RloadTime", reloadti);
            }
        }
    }
    public void resetReload(ItemStack par1ItemStack, World par2World, Entity entity, int i) {
        if(guntemp != null)guntemp.items[5] = null;//撃ち終わりに特殊弾を消去
        if(getInventory_fromEntity(entity) != null && gunInfo.isOneuse) {
            consumeInventoryItem(this, getInventory_fromEntity(entity),i);
        }
        if(par1ItemStack.stackSize > 0){
            if(gunInfo.isOneuse && getInventory_fromEntity(entity) == null){
                par1ItemStack.getTagCompound().setByte("Bolt", (byte)100);
            }
            reloadBullets(par1ItemStack, par2World, entity);
        }
//        int l;
//        for(l = 0; l< gunInfo.magazineItemCount; l++) {
//            if( entity instanceof EntityPlayer) {
//                if(!((EntityPlayer) entity).inventory.consumeInventoryItem(getcurrentMagazine(par1ItemStack))) {
//                    break;
//                }
////                    System.out.println("debug" + this.getMaxDamage() * (l - 1) / magazineCount);
//            } else if( entity.riddenByEntity instanceof EntityPlayer) {
//                if(!((EntityPlayer) entity.riddenByEntity).inventory.consumeInventoryItem(getcurrentMagazine(par1ItemStack))) {
//                    break;
//                }
////                    System.out.println("debug" + this.getMaxDamage() * (l - 1) / magazineCount);
//            } else if(islmmloaded && entity instanceof LMM_EntityLittleMaid){
//                if(!((LMM_EntityLittleMaid) entity).maidInventory.consumeInventoryItem(getcurrentMagazine(par1ItemStack))) {
//                    break;
//                }
//            }
//        }
    
    }
    public double getTerminalspeed(){
        if(gunInfo.acceleration > 0){
            //A*0.9 + 0.1 = A
            //(1.0 - 0.9) * A = 0.1
            //A = 0.1 * (1.0 - 0.9)
            //Terminal = acceleration * (1 - resistance)
            return gunInfo.acceleration/(1-gunInfo.resistance);
        }
        return 0;
    }
    
    public boolean consumeInventoryItem(Item p_146026_1_,IInventory iInventory,int i)
    {
        if(iInventory != null) {
            if(i>0){
                ItemStack stackOnSlot =  iInventory.getStackInSlot(i);
                if(stackOnSlot != null && stackOnSlot.getItem() == p_146026_1_) {
                    stackOnSlot.stackSize--;
                    if (stackOnSlot.stackSize <= 0) {
                        iInventory.setInventorySlotContents(i, null);
                    }
                    return true;
                }
            }
            int i2 = this.func_146029_c(p_146026_1_, iInventory);

            if (i2 < 0) {
                return false;
            } else {
                iInventory.getStackInSlot(i2).stackSize--;
                if (iInventory.getStackInSlot(i2).stackSize <= 0) {
                    iInventory.setInventorySlotContents(i2, null);
                }

                return true;
            }
        }
        return false;
    }
    private int func_146029_c(Item p_146029_1_,IInventory iInventory)
    {
        for (int i = 0; i < iInventory.getSizeInventory(); ++i)
        {
            if (iInventory.getStackInSlot(i) != null && iInventory.getStackInSlot(i).getItem() == p_146029_1_)
            {
                return i;
            }
        }
        
        return -1;
    }
    public int remain_Bullet(ItemStack gunStack){
        if(!currentMagzine_has_roundOption(gunStack)){
            return this.getMaxDamage() - gunStack.getItemDamage();
        }else {
            ItemStack[] magazines = get_loadedMagazineStack(gunStack);
            int remainBullets = 0;
            for(ItemStack magazinestack : magazines){
                if(magazinestack != null){
                    remainBullets += magazinestack.getMaxDamage() - magazinestack.getItemDamage();
                }
            }
            return remainBullets;
        }
        
    }
    public int max_Bullet(ItemStack gunStack){
        if(!currentMagzine_has_roundOption(gunStack)){
            return this.getMaxDamage();
        }else {
            return getcurrentMagazine(gunStack).getMaxDamage() * gunInfo.magazineItemCount;
            
        }
        
    }
    public void reloadBullets(ItemStack itemstack, World world, Entity entity){
        itemstack.getTagCompound().setBoolean("detached",false);
        IInventory inventory = getInventory_fromEntity(entity);
        if(inventory != null&&get_selectingMagazine(itemstack) != null)consumeAndSetMagazine(itemstack,world,inventory);
        else setMagazine(itemstack,world);
    }
    public boolean canreloadBullets(ItemStack itemstack, World world, Entity entity){
        if(guntemp.currentConnectedTurret != null && !guntemp.currentConnectedTurret.prefab_turret.canReloadAirBone && !guntemp.currentConnectedTurret.motherEntity.onGround){
            return false;
        }
        if(get_selectingMagazine(itemstack) == null)return true;
        IInventory inventory = getInventory_fromEntity(entity);
        if(inventory != null)return searchMagazines(itemstack,world,inventory)!= null;
        else return !(entity instanceof PlacedGunEntity) || !(entity.riddenByEntity == null || entity.riddenByEntity instanceof EntityPlayer);
    }
    public IInventory getInventory_fromEntity(Entity entity){
        if(guntemp != null && guntemp.currentConnectedTurret != null && guntemp.currentConnectedTurret.prefab_turret.useVehicleInventory && guntemp.currentConnectedTurret.connectedInventory != null){
            return guntemp.currentConnectedTurret.connectedInventory;
        }else
        if (entity instanceof EntityPlayer) {
            return ((EntityPlayer) entity).inventory;
        } else if (entity.riddenByEntity instanceof EntityPlayer){
            return ((EntityPlayer) entity.riddenByEntity).inventory;
        } else if(islmmloaded && entity instanceof LMM_EntityLittleMaid){
            return ((LMM_EntityLittleMaid) entity).maidInventory;
        } else if(islmmloaded && entity.riddenByEntity instanceof LMM_EntityLittleMaid){
            return ((LMM_EntityLittleMaid) entity.riddenByEntity).maidInventory;
        }
        return null;
    }
    public void setMagazine(ItemStack gunStack, World world){
        ItemStack[] mgazines = get_loadedMagazineStack(gunStack);
        for (int magazine_slot = 0;magazine_slot < gunInfo.magazineItemCount; magazine_slot++) {
            ItemStack stack = new ItemStack(get_selectingMagazine(gunStack),1);
            mgazines[magazine_slot] = stack;
        }
    
        if(!currentMagzine_has_roundOption(gunStack))gunStack.setItemDamage(0);
        gunStack.getTagCompound().setInteger("getcurrentMagazine", gunStack.getTagCompound().getInteger("get_selectingMagazine"));
        set_loadedMagazineStack(gunStack,mgazines);
    }
    public void consumeAndSetMagazine(ItemStack gunStack, World world, IInventory inventory){
        ItemStack[] magazines = get_loadedMagazineStack(gunStack);
        StackAndSlot[] stackAndSlots = new StackAndSlot[magazines.length];
        int magazine_slot = 0;
        StackAndSlot prevStackAndSlot = null;
        int cnt_useStackSlot = 0;
        for (;magazine_slot < gunInfo.magazineItemCount; magazine_slot++) {
            StackAndSlot stackAndSlot = searchMagazines(gunStack, world, inventory);
            if(stackAndSlot != null && stackAndSlot.stack.stackSize>0) {
                magazines[magazine_slot] = stackAndSlot.stack.copy();
                magazines[magazine_slot].stackSize = 1;
                stackAndSlot.stack.stackSize--;
                if(prevStackAndSlot == null || prevStackAndSlot.slot != stackAndSlot.slot) {
                    stackAndSlots[cnt_useStackSlot] = stackAndSlot;
                    cnt_useStackSlot++;
                }
                prevStackAndSlot = stackAndSlot;
                inventory.markDirty();
            }else break;
        }
        for(StackAndSlot stackAndSlot:stackAndSlots) {
            if(stackAndSlot != null) {
                if (stackAndSlot.stack.stackSize > 0) {
                    inventory.setInventorySlotContents(stackAndSlot.slot, stackAndSlot.stack);
                } else {
                    inventory.setInventorySlotContents(stackAndSlot.slot, null);
                }
            }
        }
        set_loadedMagazineStack(gunStack,magazines);
        gunStack.getTagCompound().setInteger("getcurrentMagazine", gunStack.getTagCompound().getInteger("get_selectingMagazine"));
    
        if(!currentMagzine_has_roundOption(gunStack))gunStack.setItemDamage((int)(this.getMaxDamage() * (1f - (float)magazine_slot / (float) gunInfo.magazineItemCount)));
    }
    public StackAndSlot searchMagazines(ItemStack gunStack, World world, IInventory inventory){
        int size = inventory.getSizeInventory();
        Item magItem = get_selectingMagazine(gunStack);
        boolean hasRoundOption = currentMagzine_has_roundOption(gunStack);
        for(int slot = 0;slot < size;slot++) {
            ItemStack itemStack = inventory.getStackInSlot(slot);
            if(itemStack != null && (!hasRoundOption || itemStack.getItemDamage() < itemStack.getMaxDamage()) && itemStack.stackSize>0){
                Item item = itemStack.getItem();
                if (item == magItem) {
                    return new StackAndSlot(slot, itemStack);
                }
                inventory.markDirty();
            }
        }
        return null;
    }
    public void bindattaches(ItemStack itemstack, World world, Entity entity){
        //�e�C���x���g���̃A�b�v�f�[�g
        try {
            gunInfo.foruseattackDamage = gunInfo.attackDamage;
            NBTTagList tags = (NBTTagList) itemstack.getTagCompound().getTag("Items");
            if (tags != null) {

                guntemp.items = new ItemStack[6];
                for (int i = 0; i < tags.tagCount(); i++)//133
                {
                    NBTTagCompound tagCompound = tags.getCompoundTagAt(i);
                    int slot = tagCompound.getByte("Slot");
                    if (slot >= 0 && slot < guntemp.items.length && guntemp.items[slot] == null) {
                        guntemp.items[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
                    }
                }
                ItemStack itemstackattach;
                itemstackattach = guntemp.items[1];
                if(itemstackattach == null) {
                    gunInfo.posGetter.sightPos = gunInfo.sightPosN;
                    if(gunInfo.hasNightVision[0]){
                        if(entity instanceof EntityLivingBase && HandmadeGunsCore.Key_ADS(entity))((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.nightVision.id, 1, 1));
                    }
                }else {
                    if (itemstackattach.getItem() instanceof HMGItemSightBase){
                        if(((HMGItemSightBase) itemstackattach.getItem()).needgunoffset) {
                            float onads_modelPosX = 0;
                            float onads_modelPosY = 0;
                            float onads_modelPosZ = 0;
                            if (itemstackattach.getItem() instanceof HMGItemSightBase) {
                                onads_modelPosX = (gunInfo.sightattachoffset[0] + ((HMGItemSightBase) itemstackattach.getItem()).gunoffset[0]) * gunInfo.modelscale * gunInfo.inworldScale * gunInfo.onTurretScale;
                                onads_modelPosY = (gunInfo.sightattachoffset[1] + ((HMGItemSightBase) itemstackattach.getItem()).gunoffset[1]) * gunInfo.modelscale * gunInfo.inworldScale * gunInfo.onTurretScale;
                                onads_modelPosZ = (gunInfo.sightattachoffset[2] + ((HMGItemSightBase) itemstackattach.getItem()).gunoffset[2]) * gunInfo.modelscale * gunInfo.inworldScale * gunInfo.onTurretScale;
                            }
                            gunInfo.posGetter.sightPos = new double[]{onads_modelPosX, onads_modelPosY, onads_modelPosZ};
                            if(((HMGItemSightBase) itemstackattach.getItem()).isnightvision){
                                if(entity instanceof EntityLivingBase && HandmadeGunsCore.Key_ADS(entity))((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.nightVision.id, 1, 1));
                            }
                        }else if(itemstackattach.getItem() instanceof HMGItemAttachment_reddot){
                            if(gunInfo.hasNightVision[1]){
                                if(entity instanceof EntityLivingBase && HandmadeGunsCore.Key_ADS(entity))((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.nightVision.id, 1, 1));
                            }
                            gunInfo.posGetter.sightPos = gunInfo.sightPosR;
                        }else if(itemstackattach.getItem() instanceof HMGItemAttachment_scope){
                            if(gunInfo.hasNightVision[2]){
                                if(entity instanceof EntityLivingBase && HandmadeGunsCore.Key_ADS(entity))((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.nightVision.id, 1, 1));
                            }
                            gunInfo.posGetter.sightPos = gunInfo.sightPosS;
                        }
                    }
                }
                itemstackattach = guntemp.items[2];
                if (itemstackattach != null && itemstackattach.getItem() instanceof HMGItemAttachment_laser) {
                    if (world.isRemote) {
                        Vec3 vec3 = Vec3.createVectorHelper(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
                        Vec3 playerlook = gunInfo.getLook(1.0f,entity);
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
                        List list = getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.addCoord(playerlook.xCoord, playerlook.yCoord, playerlook.zCoord).expand(1.0D, 1.0D, 1.0D));
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
                itemstackattach = guntemp.items[3];
                if (itemstackattach != null && itemstackattach.getItem() instanceof HMGItemAttachment_Suppressor) {
                    guntemp.sound = gunInfo.soundsu;
                    guntemp.soundlevel = gunInfo.soundsuplevel;
                    guntemp.muzzle = false;
                } else if (itemstackattach != null && itemstackattach.getItem() instanceof HMGXItemGun_Sword) {
                    gunInfo.foruseattackDamage = ((HMGXItemGun_Sword) itemstackattach.getItem()).attackDamage;
                    guntemp.sound = gunInfo.soundbase;
                    guntemp.soundlevel = gunInfo.soundbaselevel;
                    guntemp.muzzle = gunInfo.muzzleflash;
                }else {
                    guntemp.sound = gunInfo.soundbase;
                    guntemp.soundlevel = gunInfo.soundbaselevel;
                    guntemp.muzzle = gunInfo.muzzleflash;
                }
                if (guntemp.items[4] != null && guntemp.items[4].getItem() instanceof HMGItemAttachment_grip) {
                    if(HandmadeGunsCore.Key_ADS(entity)){
                        guntemp.tempspread *= ((HMGItemAttachment_grip) guntemp.items[4].getItem()).reduceSpreadLevel_ADS;
                    }else {
                        guntemp.tempspread *= ((HMGItemAttachment_grip) guntemp.items[4].getItem()).reduceSpreadLevel;
                    }
                } else if(guntemp.items[4] != null && guntemp.items[4].getItem() instanceof HMGItem_Unified_Guns) {
                    double ix = 0;
                    double iy = 0;
                    double iz = 0;
                    float f1 = entity.getRotationYawHead() * (2 * (float) Math.PI / 360);
                    float f2 = entity.rotationPitch * (2 * (float) Math.PI / 360);
                    if (!HandmadeGunsCore.Key_ADS(entity)) {
                        ix -= MathHelper.sin(f1) * MathHelper.cos(f2) * gunInfo.underoffsetpz / 4 + MathHelper.cos(-f1) * (-gunInfo.underoffsetpx / 4);
                        iy += -MathHelper.sin(f2) * gunInfo.underoffsetpz / 4 + MathHelper.cos(f2) * gunInfo.underoffsetpy / 4;
                        iz += MathHelper.cos(f1) * MathHelper.cos(f2) * gunInfo.underoffsetpz / 4 + MathHelper.sin(-f1) * (-gunInfo.underoffsetpx / 4);
                    } else {
                        ix -= MathHelper.sin(f1) * MathHelper.cos(f2) * gunInfo.underoffsetpz / 4 + MathHelper.cos(-f1) * (-gunInfo.underoffsetpx / 4);
                        iy += -MathHelper.sin(f2) * gunInfo.underoffsetpz / 4 + MathHelper.cos(f2) * gunInfo.underoffsetpy / 4;
                        iz += MathHelper.cos(f1) * MathHelper.cos(f2) * gunInfo.underoffsetpz / 4 + MathHelper.sin(-f1) * (-gunInfo.underoffsetpx / 4);
                    }
                    (entity).posX += ix;
                    (entity).posY += iy;
                    (entity).posZ += iz;
                    guntemp.items[4].getItem().onUpdate(guntemp.items[4], world, entity, -1, true);
                    ( entity).posX -= ix;
                    ( entity).posY -= iy;
                    ( entity).posZ -= iz;
                } else if (guntemp.items[4] != null && guntemp.items[4].getItem() instanceof HMGXItemGun_Sword) {
                    gunInfo.foruseattackDamage = ((HMGXItemGun_Sword) guntemp.items[4].getItem()).attackDamage;
                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        try {
            if (guntemp.items != null) {
//                        for (int i1 = 0; i1 < guntemp.items.length; i1++) {
//                            if (guntemp.items[i1] != null && guntemp.items[i1].getItemDamage() > guntemp.items[i1].getMaxDamage()) {
//                                guntemp.items[i1].stackSize--;
//                            }
//                            if (guntemp.items[i1] != null && guntemp.items[i1].stackSize <= 0) {
//                                guntemp.items[i1] = null;
//                            }
//                        }
                NBTTagCompound nbt = itemstack.getTagCompound();
                if (!world.isRemote) {
                    NBTTagList tags = (NBTTagList) nbt.getTag("Items");
                    int compressedID = 0;
                    if (tags != null) {
                        for (int itemid = 0; itemid < guntemp.items.length; itemid++) {
                            if (guntemp.items[itemid] != null && guntemp.items[itemid].getItem() != null) {
                                NBTTagCompound compound = new NBTTagCompound();
                                compound.setByte("Slot", (byte) itemid);
                                guntemp.items[itemid].writeToNBT(compound);
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
                        if (compressedID > 6 && compressedID < tags.tagCount()) {
                            for (int removeid = compressedID; removeid < tags.tagCount(); removeid++) {
                                System.out.println("debug" + compressedID);
                                tags.removeTag(removeid);
                            }
                        }
                        nbt.setTag("Items", tags);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getburstCount(int mode){
        if(!gunInfo.burstcount.isEmpty() && gunInfo.burstcount.size()>mode) {
            return gunInfo.burstcount.get(mode);
        }else{
            return -1;
        }
    }
    
    public void damageMagazine(ItemStack par1ItemStack , Entity par3Entity){
        if(!currentMagzine_has_roundOption(par1ItemStack)){
            par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() + 1);
            if(gunInfo.magazineItemCount > 1)destroy_LoadedMagazine(par1ItemStack);
            if(par1ItemStack.getItemDamage() == par1ItemStack.getMaxDamage())destroy_LoadedMagazine(par1ItemStack);
        }
        else damage_LoadedMagazine(par1ItemStack);
    }
    
    public Multimap getAttributeModifiers(ItemStack itemstack)
    {

        if(itemstack.getTagCompound() != null)try {
            guntemp = new GunTemp();
            guntemp.items = new ItemStack[6];
            gunInfo.foruseattackDamage = gunInfo.attackDamage;
            NBTTagList tags = (NBTTagList) itemstack.getTagCompound().getTag("Items");
            if (tags != null) {
                for (int i = 0; i < tags.tagCount(); i++)//133
                {
                    NBTTagCompound tagCompound = tags.getCompoundTagAt(i);
                    int slot = tagCompound.getByte("Slot");
                    if (slot >= 0 && slot < guntemp.items.length) {
                        guntemp.items[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
                    }
                }
                ItemStack itemstackattach;
                itemstackattach = guntemp.items[3];
                if (itemstackattach != null && itemstackattach.getItem() instanceof HMGXItemGun_Sword) {
                    gunInfo.foruseattackDamage = ((HMGXItemGun_Sword) itemstackattach.getItem()).attackDamage;
                }
                itemstackattach = guntemp.items[4];
                if (itemstackattach != null && itemstackattach.getItem() instanceof HMGXItemGun_Sword) {
                    gunInfo.foruseattackDamage = ((HMGXItemGun_Sword) itemstackattach.getItem()).attackDamage;
                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double) gunInfo.foruseattackDamage, 0));
        multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(gunInfo.field_110179_h,"GunMoveFactor", gunInfo.motion-1,1));
        return multimap;
    }
    public void setmodelADSPosAndRotation(double px,double py,double pz){
        gunInfo.sightPosN = new double[]{(-px + 0.694f)*0.2 * gunInfo.inworldScale,(-py + 1.8f)*0.2 * gunInfo.inworldScale,-pz*0.2 * gunInfo.inworldScale};
    }
    public void setADSoffsetRed(double px,double py,double pz){
        gunInfo.sightPosR = new double[]{(-px + 0.694f)*0.2 * gunInfo.inworldScale,(-py + 1.8f)*0.2 * gunInfo.inworldScale,-pz*0.2 * gunInfo.inworldScale};
    }
    public void setADSoffsetScope(double px,double py,double pz){
        gunInfo.sightPosS = new double[]{(-px + 0.694f)*0.2 * gunInfo.inworldScale,(-py + 1.8f)*0.2 * gunInfo.inworldScale,-pz*0.2 * gunInfo.inworldScale};
    }
    public double[] getSeatpos(ItemStack itemStack){
        if(guntemp == null)guntemp = new GunTemp();
        guntemp.items = new ItemStack[6];
        try {
            NBTTagList tags = (NBTTagList) itemStack.getTagCompound().getTag("Items");
            if (tags != null) {
                for (int i = 0; i < tags.tagCount(); i++)//133
                {
                    NBTTagCompound tagCompound = tags.getCompoundTagAt(i);
                    int slot = tagCompound.getByte("Slot");
                    if (slot >= 0 && slot < guntemp.items.length) {
                        guntemp.items[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
                    }
                }
                ItemStack itemstack_attach;
                itemstack_attach = guntemp.items[1];
                if (itemstack_attach == null) {
                    return gunInfo.sightPosN;
                } else {
                    if (itemstack_attach.getItem() instanceof HMGItemSightBase && ((HMGItemSightBase) itemstack_attach.getItem()).needgunoffset) {
                        double onads_modelPosX = 0;
                        double onads_modelPosY = 0;
                        double onads_modelPosZ = 0;
                        if (itemstack_attach.getItem() instanceof HMGItemSightBase) {
                            onads_modelPosX = (gunInfo.sightattachoffset[0] + ((HMGItemSightBase) itemstack_attach.getItem()).gunoffset[0]) * gunInfo.modelscale * gunInfo.inworldScale * 0.4;
                            onads_modelPosY = (gunInfo.sightattachoffset[1] + ((HMGItemSightBase) itemstack_attach.getItem()).gunoffset[1]) * gunInfo.modelscale * gunInfo.inworldScale * 0.4;
                            onads_modelPosZ = (gunInfo.sightattachoffset[2] + ((HMGItemSightBase) itemstack_attach.getItem()).gunoffset[2]) * gunInfo.modelscale * gunInfo.inworldScale * 0.4;
                        }
                        return new double[]{onads_modelPosX, onads_modelPosY, onads_modelPosZ};
                    } else if (itemstack_attach.getItem() instanceof HMGItemAttachment_reddot) {
                        return gunInfo.sightPosR;
                    } else if (itemstack_attach.getItem() instanceof HMGItemAttachment_scope) {
                        return gunInfo.sightPosS;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return gunInfo.sightPosN;
    }
    
    public boolean currentMagzine_has_roundOption(ItemStack itemStack){
        Item currentmagazine = getcurrentMagazine(itemStack);
        if(currentmagazine instanceof HMGItemCustomMagazine){
            return ((HMGItemCustomMagazine) currentmagazine).hasRoundOption;
        }
        return false;
    }
    public int get_Type_Option_of_currentMagzine_and_apply_magazine_Option(ItemStack itemStack){
        Item currentmagazine = getcurrentMagazine(itemStack);
        if(currentmagazine instanceof HMGItemCustomMagazine){
            firetemp.applyMagOption((HMGItemCustomMagazine) currentmagazine);
            
            
            return ((HMGItemCustomMagazine) currentmagazine).bullettype;
        }
        return -1;
    }
    public int reloadTime(ItemStack itemStack){
    	try {
    		if(guntemp == null)return 0;
			Item currentmagazine = getcurrentMagazine(itemStack);
			if(currentmagazine instanceof HMGItemCustomMagazine &&  ((HMGItemCustomMagazine) currentmagazine).hasReloadOption)return ((HMGItemCustomMagazine) currentmagazine).reloadTime;
			return guntemp.selectingMagazine < gunInfo.reloadTimes.length ? gunInfo.reloadTimes[guntemp.selectingMagazine]:gunInfo.reloadTimes[0];
		}catch (Exception e){
    		e.printStackTrace();
		}
    	return 0;
    }
//    public boolean selectingMagazine_has_roundOption(ItemStack itemStack){
//        Item selectingmagazine = get_selectingMagazine(itemStack);
//        if(selectingmagazine instanceof HMGItemCustomMagazine){
//            return ((HMGItemCustomMagazine) selectingmagazine).hasRoundOption;
//        }
//        return false;
//    }
    public boolean currentMagazine_is_autoDestroy(ItemStack itemStack){
        Item currentMagazine = getcurrentMagazine(itemStack);
        if(currentMagazine instanceof HMGItemCustomMagazine){
            return ((HMGItemCustomMagazine) currentMagazine).autoDestroy;
        }
        return false;
    }
    public String currentMagazine_magazineModel(ItemStack itemStack){
        String magmodel = null;
        Item currentMagazine = getcurrentMagazine(itemStack);
        if(currentMagazine instanceof HMGItemCustomMagazine){
            magmodel = ((HMGItemCustomMagazine) currentMagazine).magmodel;
        }
        return magmodel != null ? magmodel:gunInfo.bulletmodelMAG;
    }
    public String currentMagazine_cartridgeModelName(ItemStack itemStack){
        Item currentMagazine = getcurrentMagazine(itemStack);
        if(currentMagazine instanceof HMGItemCustomMagazine){
            return ((HMGItemCustomMagazine) currentMagazine).cartridgeModelName;
        }
        return null;
    }
    public Item currentMagazine_cartridgeItem(ItemStack itemStack){
        Item currentMagazine = getcurrentMagazine(itemStack);
        if(currentMagazine instanceof HMGItemCustomMagazine){
            return ((HMGItemCustomMagazine) currentMagazine).getCartridgeItem();
        }
        return null;
    }
    public Item getcurrentMagazine(ItemStack itemStack){
        if(gunInfo.magazine == null)return null;
        int selectingMagazine;
        if(itemStack == null){
            selectingMagazine = 0;
        }else {
            checkTags(itemStack);
            NBTTagCompound nbt = itemStack.getTagCompound();
            selectingMagazine = nbt.getInteger("getcurrentMagazine");
        }
        if(selectingMagazine >=0 && selectingMagazine < gunInfo.magazine.length){
            return gunInfo.magazine[selectingMagazine];
        }else {
            if(itemStack !=null) {
                NBTTagCompound nbt = itemStack.getTagCompound();
                nbt.setInteger("getcurrentMagazine", 0);
            }
            return gunInfo.magazine[0];
        }
    }
    public Item get_selectingMagazine(ItemStack itemStack){
        if(gunInfo.magazine == null)return null;
        int selectingMagazine;
        if(itemStack == null){
            selectingMagazine = 0;
        }else {
            checkTags(itemStack);
            NBTTagCompound nbt = itemStack.getTagCompound();
            selectingMagazine = nbt.getInteger("get_selectingMagazine");
        }
        if(selectingMagazine >=0 && selectingMagazine < gunInfo.magazine.length){
            return gunInfo.magazine[selectingMagazine];
        }else {
            if(itemStack !=null) {
                NBTTagCompound nbt = itemStack.getTagCompound();
                nbt.setInteger("get_selectingMagazine", 0);
            }
            return gunInfo.magazine[0];
        }
    }
    public ItemStack[] get_loadedMagazineStack(ItemStack itemStack){
        checkTags(itemStack);
        NBTTagCompound nbt = itemStack.getTagCompound();
        ItemStack[] itemStacks = new ItemStack[gunInfo.magazineItemCount];
        for(int i = 0;i < gunInfo.magazineItemCount; i++) {
            NBTTagCompound tag = nbt.getCompoundTag("LoadedMagazine" + i);
            if(tag != null)itemStacks[i] = ItemStack.loadItemStackFromNBT(tag);
        }
        return itemStacks;
    }
    public void set_loadedMagazineStack(ItemStack itemStack,ItemStack[] itemStacks){
        checkTags(itemStack);
        NBTTagCompound stackTagCompound = itemStack.getTagCompound();
        for(int i = 0;i < gunInfo.magazineItemCount; i++) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            if(itemStacks[i] != null) {
                itemStacks[i].writeToNBT(nbttagcompound);
            }else {
            }
            stackTagCompound.setTag("LoadedMagazine" + i, nbttagcompound);
        }
    }
    public void destroy_LoadedMagazine(ItemStack itemStack){
        ItemStack[] magazines = get_loadedMagazineStack(itemStack);
        int cnt = 0;
        for(ItemStack a_itemStack: magazines){
            if(a_itemStack != null){
                magazines[cnt] = null;
                break;
            }
            cnt ++ ;
        }
        set_loadedMagazineStack(itemStack,magazines);
    }
    public void damage_LoadedMagazine(ItemStack itemStack){
        ItemStack[] magazines = get_loadedMagazineStack(itemStack);
        int cnt = 0;
        for(ItemStack a_itemStack: magazines){
            if(a_itemStack != null && a_itemStack.getMaxDamage() > a_itemStack.getItemDamage()){
                a_itemStack.setItemDamage(a_itemStack.getItemDamage() + 1);
                if(a_itemStack.getMaxDamage() <= a_itemStack.getItemDamage() && currentMagazine_is_autoDestroy(itemStack))magazines[cnt] = null;
                break;
            }
            cnt ++ ;
        }
        set_loadedMagazineStack(itemStack,magazines);
    }
    public void detach_LoadedMagazine(ItemStack itemStack){
        ItemStack[] magazines = new ItemStack[gunInfo.magazineItemCount];
        set_loadedMagazineStack(itemStack,magazines);
        if(!currentMagzine_has_roundOption(itemStack))
            itemStack.setItemDamage(itemStack.getMaxDamage());
        itemStack.getTagCompound().setBoolean("detached",true);
    }
    public void returnInternalMagazines(ItemStack gunstack,Entity shooter){
        if(!shooter.worldObj.isRemote){
            int returnmagazineCount = (int)((float) this.gunInfo.magazineItemCount);
//                            System.out.println("debug" + returnmagazineCount);
            ItemStack[] itemStacks =  this.get_loadedMagazineStack(gunstack);
            for(int i= 0;i<returnmagazineCount;i++) {
                if(itemStacks[i] != null) {
                    if (gunInfo.dropMagEntity && HandmadeGunsCore.cfg_canEjectCartridge) {
                        HMGEntityBulletCartridge var8;
                        String magmodel = currentMagazine_magazineModel(gunstack);
                        if (magmodel == null) {
                            var8 = new HMGEntityBulletCartridge(shooter.worldObj, shooter, gunInfo.magType);
                        } else {
                            var8 = new HMGEntityBulletCartridge(shooter.worldObj, shooter, -1, magmodel);
                        }
                        var8.itemStack = itemStacks[i];
                        shooter.worldObj.spawnEntityInWorld(var8);
                    } else {
                        shooter.worldObj.spawnEntityInWorld(new EntityItem(shooter.worldObj, shooter.posX, shooter.posY, shooter.posZ, itemStacks[i]));
                    }
                }else if(!currentMagzine_has_roundOption(gunstack) && gunInfo.dropMagEntity && HandmadeGunsCore.cfg_canEjectCartridge){
                    HMGEntityBulletCartridge var8;
                    String magmodel = currentMagazine_magazineModel(gunstack);
                    if (magmodel == null) {
                        var8 = new HMGEntityBulletCartridge(shooter.worldObj, shooter, gunInfo.magType);
                    } else {
                        var8 = new HMGEntityBulletCartridge(shooter.worldObj, shooter, -1, magmodel);
                    }
                    shooter.worldObj.spawnEntityInWorld(var8);
                }
            }
            this.detach_LoadedMagazine(gunstack);
        }
    }

    public List getEntitiesWithinAABBExcludingEntity(Entity p_72839_1_, AxisAlignedBB p_72839_2_)
    {
        return this.getEntitiesWithinAABBExcludingEntity(p_72839_1_, p_72839_2_, (IEntitySelector)null);
    }

    public List getEntitiesWithinAABBExcludingEntity(Entity p_94576_1_, AxisAlignedBB p_94576_2_, IEntitySelector p_94576_3_)
    {
        ArrayList arraylist = new ArrayList();
        int i = MathHelper.floor_double((p_94576_2_.minX - MAX_ENTITY_RADIUS) / 16.0D);
        int j = MathHelper.floor_double((p_94576_2_.maxX + MAX_ENTITY_RADIUS) / 16.0D);
        int k = MathHelper.floor_double((p_94576_2_.minZ - MAX_ENTITY_RADIUS) / 16.0D);
        int l = MathHelper.floor_double((p_94576_2_.maxZ + MAX_ENTITY_RADIUS) / 16.0D);

        for (int i1 = i; i1 <= j; ++i1)
        {
            for (int j1 = k; j1 <= l; ++j1)
            {
                if (p_94576_1_.worldObj.getChunkProvider().chunkExists(i1, j1))
                {
                    getEntitiesWithinAABBForEntity(p_94576_1_.worldObj.getChunkFromChunkCoords(i1, j1),p_94576_1_, p_94576_2_, arraylist, p_94576_3_);
                }
            }
        }

        return arraylist;
    }

    public void getEntitiesWithinAABBForEntity(Chunk chunk, Entity p_76588_1_, AxisAlignedBB p_76588_2_, List p_76588_3_, IEntitySelector p_76588_4_)
    {
        int i = MathHelper.floor_double((p_76588_2_.minY - World.MAX_ENTITY_RADIUS) / 16.0D);
        int j = MathHelper.floor_double((p_76588_2_.maxY + World.MAX_ENTITY_RADIUS) / 16.0D);
        i = MathHelper.clamp_int(i, 0, chunk.entityLists.length - 1);
        j = MathHelper.clamp_int(j, 0, chunk.entityLists.length - 1);

        for (int k = i; k <= j; ++k)
        {
            List list1 = chunk.entityLists[k];

            for (int l = 0; l < list1.size(); ++l)
            {
                Entity entity1 = (Entity)list1.get(l);

                if (entity1 != p_76588_1_ && !(entity1 instanceof HMGEntityBulletBase) && entity1.boundingBox.intersectsWith(p_76588_2_) && (p_76588_4_ == null || p_76588_4_.isEntityApplicable(entity1)))
                {
                    p_76588_3_.add(entity1);
                    Entity[] aentity = entity1.getParts();

                    if (aentity != null)
                    {
                        for (int i1 = 0; i1 < aentity.length; ++i1)
                        {
                            entity1 = aentity[i1];

                            if (entity1 != p_76588_1_ && entity1.boundingBox.intersectsWith(p_76588_2_) && (p_76588_4_ == null || p_76588_4_.isEntityApplicable(entity1)))
                            {
                                p_76588_3_.add(entity1);
                            }
                        }
                    }
                }
            }
        }
    }
}
