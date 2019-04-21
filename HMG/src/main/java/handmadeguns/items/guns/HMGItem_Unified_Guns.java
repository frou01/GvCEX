package handmadeguns.items.guns;

import com.google.common.collect.Multimap;
import handmadeguns.HMGPacketHandler;
import handmadeguns.HandmadeGunsCore;
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
import javax.script.ScriptException;
import java.util.List;
import java.util.Random;

import static handmadeguns.HandmadeGunsCore.islmmloaded;
import static handmadeguns.HandmadeGunsCore.proxy;
import static java.lang.Math.abs;
import static java.lang.StrictMath.toRadians;
import static net.minecraft.util.MathHelper.wrapAngleTo180_float;

public class HMGItem_Unified_Guns extends Item {
    public GunInfo gunInfo = new GunInfo();
    public GunTemp guntemp;
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
        String powor = String
                .valueOf(gunInfo.power + EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack));
        String speed = String.valueOf(gunInfo.speed);
        String bure = String.valueOf(gunInfo.spread_setting);
        String recoil = String.valueOf(gunInfo.recoil);
        String retime = String.valueOf(gunInfo.reloadtime);
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
        if(gunInfo.needfix){
            par3List.add(EnumChatFormatting.WHITE + "cannot handhold Shot. Press " + proxy.getFixkey() + " while pointing block");
        }else
        if(gunInfo.canfix){
            par3List.add(EnumChatFormatting.WHITE + "can Fix. Press " + proxy.getFixkey() + " while pointing block");
        }
    }
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag){
        guntemp = new GunTemp();
        guntemp.invocable = (Invocable) gunInfo.script;
        if(islmmloaded && entity instanceof LMM_IEntityLittleMaidAvatarBase){
            return;
        }
        if(entity!=null && flag){
            checkTags(itemstack);
            NBTTagCompound nbt = itemstack.getTagCompound();
            guntemp.tempspread = gunInfo.spread_setting;
            if(HandmadeGunsCore.Key_ADS(entity)){
                guntemp.tempspread = guntemp.tempspread * gunInfo.ads_spread_cof;
            }
            guntemp.tempspreadDiffusion =  nbt.getFloat("Diffusion");
            if(guntemp.tempspreadDiffusion > gunInfo.spreadDiffusionMax)
                guntemp.tempspreadDiffusion = gunInfo.spreadDiffusionMax;
            guntemp.tempspreadDiffusion-= gunInfo.spreadDiffusionReduceRate;
            if(guntemp.tempspreadDiffusion < gunInfo.spreadDiffusionmin)
                guntemp.tempspreadDiffusion = gunInfo.spreadDiffusionmin;
            guntemp.tempspread += gunInfo.spread_setting * guntemp.tempspreadDiffusion;
    
    
            guntemp.sound = gunInfo.soundbase;
            guntemp.soundlevel = gunInfo.soundbaselevel;
            guntemp.muzzle = gunInfo.muzzleflash;
    
            gunInfo.posGetter.sightPos = gunInfo.sightPosN;
            
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
            {
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
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(!world.isRemote) {
                    float walkedDist = entity.distanceWalkedModified - nbt.getFloat("prevdistanceWalkedModified");
                    float headShakeDist;
                    if(entity instanceof EntityLivingBase){
                        headShakeDist = abs(wrapAngleTo180_float(nbt.getFloat("prevRotationYawHead") - entity.getRotationYawHead()))
                                                + abs(wrapAngleTo180_float(nbt.getFloat("prevRotationPitch") - entity.rotationPitch));
//                        System.out.println("debug" + headShakeDist);
                        nbt.setFloat("prevRotationYawHead",entity.getRotationYawHead());
                        nbt.setFloat("prevRotationPitch",entity.rotationPitch);
                    }else {
                        headShakeDist = abs(wrapAngleTo180_float(nbt.getFloat("prevRotationYawHead") - entity.rotationYaw))
                                                + abs(wrapAngleTo180_float(nbt.getFloat("prevRotationPitch") - entity.rotationPitch));
                        nbt.setFloat("prevRotationYawHead",entity.rotationYaw);
                        nbt.setFloat("prevRotationPitch",entity.rotationPitch);
                    }
                    guntemp.tempspreadDiffusion += headShakeDist * gunInfo.spreadDiffusionHeadRate;
                    guntemp.tempspreadDiffusion += walkedDist * gunInfo.spreadDiffusionWalkRate;
                    if (!canFixflag || (entity.distanceWalkedModified != nbt.getFloat("prevdistanceWalkedModified"))) {
                        nbt.setFloat("prevdistanceWalkedModified", entity.distanceWalkedModified);
                        nbt.setBoolean("HMGfixed", false);
                    }
                }
                if(entity instanceof EntityPlayer){
                    gunInfo.canceler = false;
                    try {
                        if(guntemp.invocable!= null)
                            guntemp.invocable.invokeFunction("update_onplayer",this,itemstack,nbt,entity);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    if(!gunInfo.canceler) {
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
                                if (guntemp.items != null && guntemp.items[4] != null && guntemp.items[4].getItem() instanceof HMGItem_Unified_Guns) {
                                    checkTags(guntemp.items[4]);
                                    if (((HMGItem_Unified_Guns) guntemp.items[4].getItem()).getburstCount(guntemp.items[4].getTagCompound().getInteger("HMGMode")) != -1) {
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
                                    if (mode >= gunInfo.burstcount.size() || mode >= gunInfo.rates.size()) {
                                        mode = 0;
                                    }
                                    nbt.setInteger("HMGMode", mode);
                                    HMGPacketHandler.INSTANCE.sendToServer(new PacketChangeModeHeldItem(entity, mode));
                                    nbt.setBoolean("Modekeyispressed", true);
                                }
                            } else {
                                nbt.setBoolean("Modekeyispressed", false);
                            }
                            if (gunInfo.canlock) {
                                if (guntemp.islockingblock) {
                                    proxy.spawnParticles(new PacketSpawnParticle(guntemp.LockedPosX + 0.5, guntemp.LockedPosY + 0.5, guntemp.LockedPosZ + 0.5, 2));
                                }
                                if (guntemp.islockingentity && guntemp.TGT != null) {
                                    proxy.spawnParticles(new PacketSpawnParticle(guntemp.TGT.posX, guntemp.TGT.posY + guntemp.TGT.height / 2, guntemp.TGT.posZ, 2));
                                }
                            }
                        } else {
                            if (gunInfo.canlock && nbt.getBoolean("SeekerOpened")) {
                                lockon(itemstack,world,entity,nbt);
                            }
                        }
                    }
                }else if(islmmloaded && entity instanceof LMM_EntityLittleMaid){
                    gunInfo.canceler = false;
                    try {
                        if(guntemp.invocable!= null)
                            guntemp.invocable.invokeFunction("update_onmaid",this,itemstack,nbt,entity);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    if(!gunInfo.canceler && (((LMM_EntityLittleMaid) entity).isUsingItem()))
                        nbt.setBoolean("IsTriggered",true);
                }
                if(entity instanceof EntityLiving) {
                    gunInfo.canceler = false;
                    try {
                        if(guntemp.invocable!= null)
                            guntemp.invocable.invokeFunction("update_onliving",this,itemstack,nbt,entity);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    if(!gunInfo.canceler) {
                        nbt.setBoolean("SeekerOpened", false);
                        if (gunInfo.canlock) {
                            guntemp.TGT = ((EntityLiving) entity).getAttackTarget();
                            if (guntemp.TGT != null) {
                                guntemp.islockingentity = true;
                            }
                        }
                    }
                }
                if(entity instanceof PlacedGunEntity){
                    gunInfo.canceler = false;
                    try {
                        if(guntemp.invocable!= null)
                            guntemp.invocable.invokeFunction("update_onplacedGun",this,itemstack,nbt,entity);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    if(!gunInfo.canceler) {
                        if (gunInfo.canlock && nbt.getBoolean("SeekerOpened")) {
                            lockon(itemstack,world,entity,nbt);
                        }
                    }
                }
                try {
                    if(guntemp.invocable!= null)
                        guntemp.invocable.invokeFunction("update_all",this,itemstack,nbt,entity);
                } catch (ScriptException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                if (getMaxDamage() == itemstack.getItemDamage() && nbt.getBoolean("Recoiled")) {
                    try {
                        if(guntemp.invocable!= null)
                            guntemp.invocable.invokeFunction("startreload",this,itemstack,nbt,entity);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    nbt.setBoolean("IsReloading", true);
                    proceedreload(itemstack, world, entity, nbt, i);
                }
                if (!gunInfo.rates.isEmpty() && gunInfo.rates.size()>mode)
                    gunInfo.cycle = gunInfo.rates.get(mode);
                boolean recoiled = nbt.getBoolean("Recoiled");
                if (!recoiled) {
                    nbt.setBoolean("Recoiled", true);
                }
                boolean cocking = nbt.getBoolean("Cocking");
                int cockingtime = nbt.getInteger("CockingTime");
                if (!cocking && gunInfo.needcock) {
                    try {
                        if(guntemp.invocable!= null)
                            guntemp.invocable.invokeFunction("proceedcock",this,itemstack,nbt,entity);
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
                            for(int carts = 0; carts < gunInfo.cartentityCnt; carts++) {
                                HMGEntityBulletCartridge var8;
                                if (!gunInfo.hascustomcartridgemodel)
                                    var8 = new HMGEntityBulletCartridge(world, entity, gunInfo.cartType);
                                else
                                    var8 = new HMGEntityBulletCartridge(world, entity, -1, gunInfo.bulletmodelCart);
                                world.spawnEntityInWorld(var8);
                            }
                        }
                    }
                }
                boolean is_Bolt_shooting_position = cycleBolt(itemstack) && (!gunInfo.needcock || nbt.getBoolean("Cocking"));
                boolean isbulletremaining = (this.getMaxDamage() - itemstack.getItemDamage() > 0);
                {
                    if (nbt.getBoolean("IsTriggered")){
                        if((!gunInfo.needfix || nbt.getBoolean("HMGfixed"))){
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
                                        fireProcess(itemstack, world, entity,nbt);
                                        nbt.setBoolean("Recoiled", false);
                                        resetBolt(itemstack);
                                        nbt.setBoolean("Cocking", false);
                                    }
                                }
                            }
                        }
                    } else {
                        if(nbt.getBoolean("TriggerBacked") && gunInfo.chargeType){
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
                if(!world.isRemote && gunInfo.canlock && nbt.getBoolean("SeekerOpened")) {
                    nbt.setBoolean("islockedentity", guntemp.islockingentity);
                    if(guntemp.TGT!= null) nbt.setInteger("TGT", guntemp.TGT.getEntityId());
                    if (guntemp.islockingblock) {
                        nbt.setInteger("LockedPosX", guntemp.LockedPosX);
                        nbt.setInteger("LockedPosY", guntemp.LockedPosY);
                        nbt.setInteger("LockedPosZ", guntemp.LockedPosZ);
                    }
                    nbt.setBoolean("islockedblock", guntemp.islockingblock);
                }else {
                    if(guntemp.TGT == null || guntemp.TGT.isDead){
                        nbt.setInteger("TGT", -1);
                    }
                }
                try {
                    if (guntemp.items != null) {
                        for (int i1 = 0; i1 < guntemp.items.length; i1++) {
                            if (guntemp.items[i1] != null && guntemp.items[i1].getItemDamage() > guntemp.items[i1].getMaxDamage()) {
                                guntemp.items[i1].stackSize--;
                            }
                            if (guntemp.items[i1] != null && guntemp.items[i1].stackSize <= 0) {
                                guntemp.items[i1] = null;
                            }
                        }
                        if(!world.isRemote) {
                            NBTTagList tags = (NBTTagList) nbt.getTag("Items");
                            int compressedID = 0;
                            if(tags != null) {
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
            if(!world.isRemote)nbt.setFloat("Diffusion", guntemp.tempspreadDiffusion);
        }else if(itemstack != null){
            checkTags(itemstack);
            NBTTagCompound tagCompound = itemstack.getTagCompound();
            tagCompound.setInteger("RloadTime",0);//持っていなければリロード初期化
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
            if (gunInfo.canlockEntity && movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && movingobjectposition.entityHit != null) {
                if (!guntemp.islockingentity || (entity.ridingEntity == null || entity.ridingEntity != movingobjectposition.entityHit) && guntemp.TGT != movingobjectposition.entityHit) {
                    entity.worldObj.playSoundAtEntity(entity, gunInfo.lockSound_entity, 1f, gunInfo.lockpitch_entity);
                    guntemp.TGT = movingobjectposition.entityHit;
                    guntemp.islockingentity = true;
                    guntemp.islockingblock = false;
                }
            } else  if (gunInfo.canlockBlock && movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && (
                    guntemp.LockedPosX != movingobjectposition.blockX || guntemp.LockedPosY != movingobjectposition.blockY || guntemp.LockedPosZ != movingobjectposition.blockZ)) {
                entity.worldObj.playSoundAtEntity(entity, gunInfo.lockSound_block, 1f, gunInfo.lockpitch_block);
                guntemp.LockedPosX = movingobjectposition.blockX;
                guntemp.LockedPosY = movingobjectposition.blockY;
                guntemp.LockedPosZ = movingobjectposition.blockZ;
                guntemp.islockingblock = true;
                guntemp.islockingentity = false;
                guntemp.TGT = null;
            }
        }
    }
    public void fireProcess(ItemStack itemstack, World world, Entity entity, NBTTagCompound nbt){

        if(!world.isRemote) {
            guntemp.tempspreadDiffusion+= gunInfo.spreadDiffusionRate;
            try {
                if(guntemp.invocable!= null)
                    guntemp.invocable.invokeFunction("prefire",this,itemstack,nbt,entity);
            } catch (ScriptException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            this.Flash(itemstack, world, entity);
            entity.getEntityData().setFloat("GunshotLevel", guntemp.soundlevel);
            proxy.playerSounded(entity);
//            world.playSoundEffect(entity.posX,entity.posY,entity.posZ, sound, soundlevel, soundspeed);
            HMGPacketHandler.INSTANCE.sendToAll(new PacketPlaysound(entity, guntemp.sound, gunInfo.soundspeed, guntemp.soundlevel));
    
    
            damageMgazine(itemstack,entity);
            if (HandmadeGunsCore.cfg_canEjectCartridge && gunInfo.dropcart) {
                HMGEntityBulletCartridge var8;
                if(!gunInfo.hascustomcartridgemodel)
                    var8 = new HMGEntityBulletCartridge(world,entity, gunInfo.cartType);
                else
                    var8 = new HMGEntityBulletCartridge(world,entity,-1, gunInfo.bulletmodelCart);
                for(int carts = 0; carts < gunInfo.cartentityCnt; carts++)
                    world.spawnEntityInWorld(var8);
            }
            HMGEntityBulletBase[] bullet = null;
            if (gunInfo.guntype < 5 && guntemp.items != null && guntemp.items[5] != null) {
                try {
                    switch (gunInfo.guntype) {
                        case 0:
                            if (guntemp.items[5].getItem() instanceof HMGItemBullet_AP){
                                bullet = FireBulletAP(world, entity);
                            }else if (guntemp.items[5].getItem() instanceof HMGItemBullet_Frag){
                                bullet = FireBulletFrag(world, entity);
                            }else if (guntemp.items[5].getItem() instanceof HMGItemBullet_AT){
                                bullet = FireBulletAT(world, entity);
                            }else if (guntemp.items[5].getItem() instanceof HMGItemBullet_TE){
                                bullet = FireBullet(world, entity);
                            }
                            break;
                        case 1:
                            if (guntemp.items[5].getItem() instanceof HMGItemBullet_AP){
                                bullet = FireBulletAP(world, entity);
                            }else if (guntemp.items[5].getItem() instanceof HMGItemBullet_Frag){
                                bullet = FireBulletFrag(world, entity);
                            }else if (guntemp.items[5].getItem() instanceof HMGItemBullet_AT){
                                bullet = FireBulletAT(world, entity);
                            }else if (guntemp.items[5].getItem() instanceof HMGItemBullet_TE){
                                bullet = FireBullet(world, entity);
                            }
                            break;
                        case 2:
                            if (guntemp.items[5].getItem() instanceof HMGItemBullet_AP){
                                bullet = FireBulletGL(world, entity);
                            }else if (guntemp.items[5].getItem() instanceof HMGItemBullet_Frag){
                                bullet = FireBulletGL(world, entity);
                            }else if (guntemp.items[5].getItem() instanceof HMGItemBullet_AT){
                                bullet = FireBulletGL(world, entity);
                            }else if (guntemp.items[5].getItem() instanceof HMGItemBullet_TE){
                                bullet = FireBulletTE(world, entity);
                            }
                            break;
                        case 3:
                            if (guntemp.items[5].getItem() instanceof HMGItemBullet_AP){
                                bullet = FireBulletRPG(world, entity);
                            }else if (guntemp.items[5].getItem() instanceof HMGItemBullet_Frag){
                                bullet = FireBulletRPG(world, entity);
                            }else if (guntemp.items[5].getItem() instanceof HMGItemBullet_AT){
                                bullet = FireBulletRPG(world, entity);
                            }else if (guntemp.items[5].getItem() instanceof HMGItemBullet_TE){
                                bullet = FireBulletTE(world, entity);
                            }
                            break;
                        case 4:
                            if (guntemp.items[5].getItem() instanceof HMGItemBullet_AP){
                                bullet = FireBulletAP(world, entity);
                            }else if (guntemp.items[5].getItem() instanceof HMGItemBullet_Frag){
                                bullet = FireBulletHE(world, entity);
                            }else if (guntemp.items[5].getItem() instanceof HMGItemBullet_AT){
                                bullet = FireBullet(world, entity);
                            }else if (guntemp.items[5].getItem() instanceof HMGItemBullet_TE){
                                bullet = FireBullet(world, entity);
                            }
                            break;
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else {
                switch (gunInfo.guntype) {
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
                    case 10:
                        bullet = FireBulletTorp(world, entity);
                        break;
                }
            }

            if(bullet !=null){
                for(HMGEntityBulletBase bulletBase :bullet) {
                    bulletBase.knockbackXZ = gunInfo.knockback;
                    bulletBase.knockbackY = gunInfo.knockbackY;
                    bulletBase.gra = gunInfo.gra;
                    bulletBase.bouncerate = gunInfo.bouncerate;
                    bulletBase.bouncelimit = gunInfo.bouncelimit;
                    bulletBase.fuse = gunInfo.fuse;
                    bulletBase.canbounce = gunInfo.canbounce;
                    bulletBase.resistance = gunInfo.resistance;
                    bulletBase.acceleration = gunInfo.acceleration;

                    if (guntemp.islockingentity) {
                        bulletBase.homingEntity = guntemp.TGT;
                        bulletBase.induction_precision = gunInfo.induction_precision;
                    } else if (guntemp.islockingblock) {
                        bulletBase.lockedBlockPos = Vec3.createVectorHelper(guntemp.LockedPosX, guntemp.LockedPosY, guntemp.LockedPosZ);
                        bulletBase.induction_precision = gunInfo.induction_precision;
                    }
                    if (entity instanceof PlacedGunEntity) {
                        Vec3 vec = Vec3.createVectorHelper(gunInfo.posGetter.barrelpos[0], gunInfo.posGetter.barrelpos[1], gunInfo.posGetter.barrelpos[2]);
                        vec = vec.addVector( - gunInfo.posGetter.turretRotationPitchPoint[0], - gunInfo.posGetter.turretRotationPitchPoint[1], - gunInfo.posGetter.turretRotationPitchPoint[2]);
                        vec.rotateAroundX(-(float) toRadians(entity.rotationPitch));
                        vec = vec.addVector(   gunInfo.posGetter.turretRotationPitchPoint[0],   gunInfo.posGetter.turretRotationPitchPoint[1],   gunInfo.posGetter.turretRotationPitchPoint[2]);
                        vec = vec.addVector( - gunInfo.posGetter.turretRotationYawPoint[0], - gunInfo.posGetter.turretRotationYawPoint[1], - gunInfo.posGetter.turretRotationYawPoint[2]);
                        vec.rotateAroundY(-(float) toRadians(((PlacedGunEntity)entity).rotationYawGun - ((PlacedGunEntity)entity).baserotationYaw));
                        vec = vec.addVector(   gunInfo.posGetter.turretRotationYawPoint[0],   gunInfo.posGetter.turretRotationYawPoint[1],   gunInfo.posGetter.turretRotationYawPoint[2]);
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
            if(guntemp.invocable!= null)
                guntemp.invocable.invokeFunction("fireout",this,itemstack,nbt,entity);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        //public�C���q�IrightClickDelayTimer�I�ӂ����₪���Ă��I
        if(!gunInfo.needfix||(par1ItemStack.getTagCompound() != null && par1ItemStack.getTagCompound().getBoolean("HMGfixed"))) {
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
            Vec3 vec = Vec3.createVectorHelper(gunInfo.posGetter.barrelpos[0], gunInfo.posGetter.barrelpos[1], gunInfo.posGetter.barrelpos[2]);
            vec = vec.addVector( - gunInfo.posGetter.turretRotationPitchPoint[0], - gunInfo.posGetter.turretRotationPitchPoint[1], - gunInfo.posGetter.turretRotationPitchPoint[2]);
            vec.rotateAroundX(-(float) toRadians(entity.rotationPitch));
            vec = vec.addVector(   gunInfo.posGetter.turretRotationPitchPoint[0],   gunInfo.posGetter.turretRotationPitchPoint[1],   gunInfo.posGetter.turretRotationPitchPoint[2]);
            vec = vec.addVector( - gunInfo.posGetter.turretRotationYawPoint[0], - gunInfo.posGetter.turretRotationYawPoint[1], - gunInfo.posGetter.turretRotationYawPoint[2]);
            vec.rotateAroundY(-(float) toRadians(((PlacedGunEntity)entity).rotationYawGun - ((PlacedGunEntity)entity).baserotationYaw));
            vec = vec.addVector(   gunInfo.posGetter.turretRotationYawPoint[0],   gunInfo.posGetter.turretRotationYawPoint[1],   gunInfo.posGetter.turretRotationYawPoint[2]);
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
        if (HandmadeGunsCore.cfg_muzzleflash && guntemp.muzzle && gunInfo.muzzleflash) {
            if(!par2World.isRemote) {
                PacketSpawnParticle packet;
                if(gunInfo.flashname != null){
                    packet = new PacketSpawnParticle(entity.posX + ix, entity.posY + iy, entity.posZ + iz, (entity instanceof EntityLivingBase ? ((EntityLivingBase)entity).rotationYawHead : entity.rotationYaw),entity.rotationPitch,0, gunInfo.flashname,true);
                    packet.id = 100;
                }else {
                    packet = new PacketSpawnParticle(entity.posX + ix, entity.posY + iy, entity.posZ + iz, (entity instanceof EntityLivingBase ? ((EntityLivingBase)entity).rotationYawHead : entity.rotationYaw),entity.rotationPitch,0, 100);
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
        nbt.setFloat("rotex",nbt.getFloat("rotex") + gunInfo.mat31rotex);
        nbt.setFloat("rotey",nbt.getFloat("rotey") + gunInfo.mat31rotey);
        nbt.setFloat("rotez",nbt.getFloat("rotez") + gunInfo.mat31rotez);
        pItemstack.getTagCompound().setByte("Bolt", getCycleCount(pItemstack));
    }
    public byte getCycleCount(ItemStack pItemstack) {
        return (byte) (gunInfo.cycle/2);
    }
    public void proceedreload(ItemStack itemstack , World world , Entity entity , NBTTagCompound nbt, int i){
        try {
            if(guntemp.invocable!= null)
                guntemp.invocable.invokeFunction("proceedreload",this,itemstack,nbt,entity);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) itemstack.getItem();
        int reloadti = nbt.getInteger("RloadTime");
        if ((itemstack.getItemDamage() == itemstack.getMaxDamage() || (gunInfo.magazine instanceof HMGItemBullet_with_Internal_Bullet && guntemp.items[5] == null)) &&
                ((islmmloaded && entity instanceof LMM_EntityLittleMaid && ((LMM_EntityLittleMaid) entity).maidInventory.hasItem(gunInfo.magazine))
                        || (entity instanceof EntityPlayer && ((EntityPlayer)entity).inventory.hasItem(gunInfo.magazine))
                        || (entity instanceof PlacedGunEntity && entity.riddenByEntity instanceof EntityPlayer && ((EntityPlayer)entity.riddenByEntity).inventory.hasItem(gunInfo.magazine))
                        || !(entity instanceof PlacedGunEntity || entity instanceof EntityPlayer || (islmmloaded && entity instanceof LMM_EntityLittleMaid))
                        || (entity instanceof PlacedGunEntity && !(entity.riddenByEntity == null || entity.riddenByEntity instanceof EntityPlayer || (islmmloaded && entity.riddenByEntity instanceof LMM_EntityLittleMaid)))
                        || gunInfo.magazine == null)

                ) {
            if (!world.isRemote && reloadti == 2) {
                HMGPacketHandler.INSTANCE.sendToAll(new PacketPlaysound(entity, gunInfo.soundre, gunInfo.soundrelevel, gunInfo.soundrespeed,true));
            }
            ++reloadti;
        }
        if (reloadti >= gunInfo.reloadtime) {
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

        if (gunInfo.isOneuse && entity instanceof EntityPlayer) {
            par1ItemStack.stackSize--;
            if (i != -1 && par1ItemStack.stackSize == 0)
                ((EntityPlayer) entity).inventory.setInventorySlotContents(i, null);
        }else
        if (gunInfo.isOneuse && entity.riddenByEntity instanceof EntityPlayer) {
            par1ItemStack.stackSize--;
            if (i != -1 && par1ItemStack.stackSize == 0)
                ((EntityPlayer) entity.riddenByEntity).inventory.setInventorySlotContents(i, null);
        }
        if (!linfinity) {
            int l;
            for(l = 0; l< gunInfo.magazineItemCount; l++) {
                if( entity instanceof EntityPlayer) {
                    if(!((EntityPlayer) entity).inventory.consumeInventoryItem(gunInfo.magazine)) {
                        break;
                    }
//                    System.out.println("debug" + this.getMaxDamage() * (l - 1) / magazineCount);
                } else if( entity.riddenByEntity instanceof EntityPlayer) {
                    if(!((EntityPlayer) entity.riddenByEntity).inventory.consumeInventoryItem(gunInfo.magazine)) {
                        break;
                    }
//                    System.out.println("debug" + this.getMaxDamage() * (l - 1) / magazineCount);
                } else if(islmmloaded && entity instanceof LMM_EntityLittleMaid){
                    if(!((LMM_EntityLittleMaid) entity).maidInventory.consumeInventoryItem(gunInfo.magazine)) {
                        break;
                    }
                }
            }
            par1ItemStack.setItemDamage((int)(this.getMaxDamage() * (1f - (float)l / (float) gunInfo.magazineItemCount)));
        }
        if(!par2World.isRemote){
            if(gunInfo.dropMagEntity && HandmadeGunsCore.cfg_canEjectCartridge) {
                for(int l = 0; l< gunInfo.magentityCnt; l++) {
                    HMGEntityBulletCartridge var8;
                    if (!gunInfo.hascustommagemodel) {
                        var8 = new HMGEntityBulletCartridge(par2World, entity, gunInfo.magType);
                    } else {
                        var8 = new HMGEntityBulletCartridge(par2World, entity, -1, gunInfo.bulletmodelMAG);
                    }

                    par2World.spawnEntityInWorld(var8);
                }
            }
        }
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
    }
    public int getburstCount(int mode){
        if(!gunInfo.burstcount.isEmpty() && gunInfo.burstcount.size()>mode) {
            return gunInfo.burstcount.get(mode);
        }else{
            return -1;
        }
    }
    public HMGEntityBulletBase[] FireBullet( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[gunInfo.pellet];
        for(int i = 0; i < gunInfo.pellet; i++){
            bulletinstances[i] = new HMGEntityBullet(par2World, par3Entity,
                    gunInfo.power, gunInfo.speed, guntemp.tempspread, gunInfo.bulletmodelN);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletAP( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[gunInfo.pellet];
        for(int i = 0; i < gunInfo.pellet; i++){
            bulletinstances[i] = new HMGEntityBullet_AP(par2World, par3Entity,
                    gunInfo.power, gunInfo.speed, guntemp.tempspread, gunInfo.bulletmodelAP);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletFrag( World par2World, Entity par3Entity){
        if(gunInfo.guntype == 1) {
            HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[1];
            for (int i = 0; i < 1; i++) {
                bulletinstances[i] = new HMGEntityBullet_Frag(par2World, par3Entity,
                        gunInfo.power, gunInfo.speed, guntemp.tempspread, gunInfo.bulletmodelFrag);
            }
            return bulletinstances;
        }else {
            HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[gunInfo.pellet];
            for(int i = 0; i < gunInfo.pellet; i++){
                bulletinstances[i] = new HMGEntityBullet_Frag(par2World, par3Entity,
                        gunInfo.power, gunInfo.speed, guntemp.tempspread, gunInfo.bulletmodelFrag);
            }
            return bulletinstances;
        }
    }
    public HMGEntityBulletBase[] FireBulletAT( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[gunInfo.pellet];
        for(int i = 0; i < gunInfo.pellet; i++){
            bulletinstances[i] = new HMGEntityBullet_AT(par2World, par3Entity,
                    gunInfo.power, gunInfo.speed, guntemp.tempspread, gunInfo.bulletmodelAT);
        }
        return bulletinstances;
    }



    public HMGEntityBulletBase[] FireBulletGL( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[gunInfo.pellet];
        for(int i = 0; i < gunInfo.pellet; i++){
            bulletinstances[i] = new HMGEntityBulletExprode(par2World, par3Entity,
                    gunInfo.power, gunInfo.speed, guntemp.tempspread, gunInfo.ex, gunInfo.destroyBlock, gunInfo.bulletmodelGL);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletTE( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[gunInfo.pellet];
        for(int i = 0; i < gunInfo.pellet; i++){
            bulletinstances[i] = new HMGEntityBullet_TE(par2World, par3Entity,
                    gunInfo.power, gunInfo.speed, guntemp.tempspread, 2, gunInfo.destroyBlock, gunInfo.bulletmodelTE);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletRPG( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[gunInfo.pellet];
        for(int i = 0; i < gunInfo.pellet; i++){
            bulletinstances[i] = new HMGEntityBulletRocket(par2World, par3Entity,
                    gunInfo.power, gunInfo.speed, guntemp.tempspread, gunInfo.ex, gunInfo.destroyBlock, gunInfo.bulletmodelRPG);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletHE( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[gunInfo.pellet];
        for(int i = 0; i < gunInfo.pellet; i++){
            bulletinstances[i] = new HMGEntityBullet_HE(par2World, par3Entity,
                    gunInfo.power, gunInfo.speed, guntemp.tempspread, gunInfo.bulletmodelHE);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletFrame( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[gunInfo.pellet];
        for(int i = 0; i < gunInfo.pellet; i++){
            bulletinstances[i] = new HMGEntityBullet_Flame(par2World, par3Entity,
                    gunInfo.power, gunInfo.speed, guntemp.tempspread, gunInfo.bulletmodelN);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletTorp( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[gunInfo.pellet];
        for(int i = 0; i < gunInfo.pellet; i++){
            bulletinstances[i] = new HMGEntityBulletTorp(par2World, par3Entity,
                                                                gunInfo.power, gunInfo.speed, guntemp.tempspread, gunInfo.ex, gunInfo.destroyBlock, gunInfo.bulletmodelRPG);
        }
        return bulletinstances;
    }
    public void damageMgazine(ItemStack par1ItemStack , Entity par3Entity){
        if(par3Entity instanceof EntityLivingBase) {
            if (gunInfo.magazine instanceof HMGItemBullet_with_Internal_Bullet) {
                if (guntemp.items[5] != null) guntemp.items[5].damageItem(1, (EntityLivingBase) par3Entity);
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
    
    public Multimap getAttributeModifiers(ItemStack itemstack)
    {
        guntemp = new GunTemp();
        guntemp.items = new ItemStack[6];
        if(itemstack.getTagCompound() != null)try {
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
        multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(gunInfo.field_110179_h,"GunMoveFactor", gunInfo.motion-1,2));
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
                ItemStack itemstackattach;
                itemstackattach = guntemp.items[1];
                if (itemstackattach == null) {
                    return gunInfo.sightPosN;
                } else {
                    if (itemstackattach.getItem() instanceof HMGItemSightBase && ((HMGItemSightBase) itemstackattach.getItem()).needgunoffset) {
                        double onads_modelPosX = 0;
                        double onads_modelPosY = 0;
                        double onads_modelPosZ = 0;
                        if (itemstackattach.getItem() instanceof HMGItemSightBase) {
                            onads_modelPosX = (gunInfo.sightattachoffset[0] + ((HMGItemSightBase) itemstackattach.getItem()).gunoffset[0]) * gunInfo.modelscale * gunInfo.inworldScale * 0.4;
                            onads_modelPosY = (gunInfo.sightattachoffset[1] + ((HMGItemSightBase) itemstackattach.getItem()).gunoffset[1]) * gunInfo.modelscale * gunInfo.inworldScale * 0.4;
                            onads_modelPosZ = (gunInfo.sightattachoffset[2] + ((HMGItemSightBase) itemstackattach.getItem()).gunoffset[2]) * gunInfo.modelscale * gunInfo.inworldScale * 0.4;
                        }
                        return new double[]{onads_modelPosX, onads_modelPosY, onads_modelPosZ};
                    } else if (itemstackattach.getItem() instanceof HMGItemAttachment_reddot) {
                        return gunInfo.sightPosR;
                    } else if (itemstackattach.getItem() instanceof HMGItemAttachment_scope) {
                        return gunInfo.sightPosS;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return gunInfo.sightPosN;
    }
}
