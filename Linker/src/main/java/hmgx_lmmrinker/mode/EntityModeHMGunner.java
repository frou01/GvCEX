package hmgx_lmmrinker.mode;

import handmadeguns.items.guns.HMGItem_Unified_Guns;
import hmgx_lmmrinker.AI.EntityAIAttackHMGun;
import hmgx_lmmrinker.AI.EntityAIFollow_with_Gun;
import littleMaidMobX.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EntityModeHMGunner extends LMM_EntityModeBase {
    public static final String MODENAME="HMGunner";
    public Integer MODEVAL=2;
    protected EntityAIFollow_with_Gun mAifollow;
    protected EntityAIAttackHMGun mAiAttackGun;
    /**
     * ������
     *
     * @param pEntity
     */
    public EntityModeHMGunner(LMM_EntityLittleMaid pEntity) {
        super(pEntity);
//        System.out.println(pEntity);
    }

    @Override
    public int priority() {
        return 360;
    }

    @Override
    public void addEntityMode(EntityAITasks pDefaultMove, EntityAITasks pDefaultTargeting) {
        mAifollow = new EntityAIFollow_with_Gun(owner,1.0f,25,1600,400);
        mAiAttackGun = new EntityAIAttackHMGun(owner);
        EntityAITasks[] ltasks = new EntityAITasks[2];
        ltasks[0] = new EntityAITasks(owner.aiProfiler);
        // default
        ltasks[0].addTask(1, owner.aiSwiming);
        ltasks[0].addTask(2, new LMM_EntityAIWait(owner));
        ltasks[0].addTask(5, owner.aiJumpTo);
        ltasks[0].addTask(6, owner.aiFindBlock);
        ltasks[0].addTask(6, mAifollow);
//		ltasks[0].addTask(8, aiPanic);
        ltasks[0].addTask(7, owner.aiBeg);
        ltasks[0].addTask(8, owner.aiBegMove);
        ltasks[0].addTask(9, mAiAttackGun);
        ltasks[0].addTask(20, owner.aiAvoidPlayer);
        ltasks[0].addTask(21, owner.aiFreeRain);
        ltasks[0].addTask(22, owner.aiCollectItem);
        // �ړ��pAI
        ltasks[0].addTask(30, owner.aiTracer);
        ltasks[0].addTask(31, owner.aiFollow);
        ltasks[0].addTask(32, owner.aiWander);
        ltasks[0].addTask(33, new EntityAILeapAtTarget(owner, 0.3F));
        // Mutex�̉e�����Ȃ�����s��
        ltasks[0].addTask(40, owner.aiCloseDoor);
        ltasks[0].addTask(41, owner.aiOpenDoor);
        ltasks[0].addTask(42, owner.aiRestrictRain);
        // ��̓����P��
        ltasks[0].addTask(51, new EntityAIWatchClosest(owner, EntityLivingBase.class, 10F));
        ltasks[0].addTask(52, new EntityAILookIdle(owner));
        ltasks[1] = new EntityAITasks(owner.aiProfiler);

//		ltasks[1].addTask(1, new EntityAIOwnerHurtByTarget(owner));
//		ltasks[1].addTask(2, new EntityAIOwnerHurtTarget(owner));
        ltasks[1].addTask(3, new LMM_EntityAIHurtByTarget(owner, true));
        ltasks[1].addTask(4, new LMM_EntityAINearestAttackableTarget(owner, EntityLivingBase.class, 0, true));
        owner.addMaidMode(ltasks, MODENAME, MODEVAL);
//        System.out.println("" + owner.maidModeIndexList);
    }
    @Override
    public boolean setMode(int pMode) {

        if(pMode == MODEVAL){
            //�W���퓬AI�͖�����
            owner.aiAttack.setEnable(false);
            owner.aiShooting.setEnable(false);
            owner.setBloodsuck(false);
            owner.aiFollow.setEnable(false);
            this.mAiAttackGun.setEnable(true);
            return true;
        }

        return false;
    }
    @Override
    public boolean changeMode(EntityPlayer pentityplayer) {
        ItemStack litemstack = this.owner.maidInventory.getStackInSlot(0);

        if(litemstack != null && litemstack.getItem() instanceof HMGItem_Unified_Guns) {
//            System.out.println("debug");
            owner.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(120.0D);
            mAiAttackGun.setEnable(true);
            this.owner.setMaidMode(MODEVAL);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public int getNextEquipItem(int pMode) {
        if(pMode == MODEVAL){
            int li;
            ItemStack litemstack;
            for (li = 0; li < owner.maidInventory.maxInventorySize; li++) {
                litemstack = owner.maidInventory.getStackInSlot(li);
                if (litemstack == null) continue;
                if((litemstack.getItem()) instanceof HMGItem_Unified_Guns){
                    HMGItem_Unified_Guns gun = (HMGItem_Unified_Guns) litemstack.getItem();
                    if(litemstack.getItemDamage() != gun.getMaxDamage()||owner.maidAvatar.inventory.hasItem(gun.getcurrentMagazine(litemstack))){
//                        System.out.println("debug");
                        return li;
                    }
                }
            }
        }
        return 0;
    }
    @Override
    public boolean checkItemStack(ItemStack pItemStack) {
//        System.out.println("debug");
        return (pItemStack != null && (pItemStack.getItem() instanceof HMGItem_Unified_Guns) && pItemStack.getItemDamage() != pItemStack.getItem().getMaxDamage());
    }
    @Override
    public void onUpdate(int pMode) {
        super.onUpdate(pMode);
    }
}
