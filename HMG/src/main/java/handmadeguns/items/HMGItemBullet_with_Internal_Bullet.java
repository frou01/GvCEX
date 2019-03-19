package handmadeguns.items;

import handmadeguns.entity.bullets.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class HMGItemBullet_with_Internal_Bullet  extends Item {
    public int type = 0;
    public int damage = 1;
    public float explosion = 1;
    public boolean destroyBlock = false;
    public float resistance = 0.99f;
    public float acceleration = 0f;
    public float gravity_acceleration = 0.029f;
    public boolean canbounce   = false;
    public float 	 bouncerate  = 0;
    public float 	 bouncelimit = 0;
    public String modelname = "default";
    public int round = 30;

    public HMGItemBullet_with_Internal_Bullet() {
    }
    public HMGEntityBulletBase createEntityBullet(World world){
        HMGEntityBulletBase bulletBase = null;
        switch (type) {
            case 0:
                bulletBase= new HMGEntityBullet(world);
                break;
            case 1:
                bulletBase= new HMGEntityBullet_AP(world);
                break;
            case 2:
                bulletBase= new HMGEntityBullet_AT(world);
                break;
            case 3:
                bulletBase= new HMGEntityBullet_Frag(world);
                break;
            case 4:
                bulletBase= new HMGEntityBullet_HE(world);
                break;
            case 5:
                bulletBase= new HMGEntityBullet_TE(world);
                break;
            case 6:
                bulletBase= new HMGEntityBulletExprode(world);
                break;
            case 7:
                bulletBase= new HMGEntityBulletRocket(world);
                break;
        }
        if(bulletBase != null){
            bulletBase.setdamage(damage);
            bulletBase.gra =            gravity_acceleration;
            bulletBase.resistance =     resistance;
            bulletBase.acceleration =   acceleration;
            bulletBase.ex =             explosion;
            bulletBase.canex =          destroyBlock;
            bulletBase.canbounce =      canbounce;
            bulletBase.bouncerate =     bouncerate;
            bulletBase.bouncelimit =    bouncelimit;
            bulletBase.bulletTypeName =      modelname;
        }
        return bulletBase;
    }
}
