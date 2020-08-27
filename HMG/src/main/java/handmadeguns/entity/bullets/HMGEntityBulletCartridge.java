package handmadeguns.entity.bullets;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import handmadeguns.HMGAddBullets;
import handmadeguns.HandmadeGunsCore;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.io.IOException;

import static handmadeguns.network.PacketDropCartridge.fromObject;
import static handmadeguns.network.PacketDropCartridge.toObject;

public class HMGEntityBulletCartridge extends Entity implements IEntityAdditionalSpawnData
{
	public int fuse;
    
    public int cartType = 1;
    public String modelname = "default";
    public int modelid = -1;
    
    public ItemStack itemStack;
    
    public int coolDownTime = 40;


	public HMGEntityBulletCartridge(World par1World)
    {
        super(par1World);
        this.fuse = HandmadeGunsCore.cfg_Cartridgetime;
        NBTTagCompound nbt = this.getEntityData();
        nbt.setInteger("cartridgehmg", cartType);
    }

    @Override
    protected void entityInit() {

    }

    public HMGEntityBulletCartridge(World p_i1777_1_, Entity p_i1777_2_, int tttype)
    {
        super(p_i1777_1_);
        this.cartType = tttype;
        NBTTagCompound nbt = this.getEntityData();
        nbt.setInteger("cartridgehmg", cartType);
        this.fuse = HandmadeGunsCore.cfg_Cartridgetime;
        this.setSize(0.25F, 0.25F);
        if(p_i1777_2_!=null)this.setLocationAndAngles(p_i1777_2_.posX, p_i1777_2_.posY + (double)p_i1777_2_.getEyeHeight(), p_i1777_2_.posZ, p_i1777_2_.rotationYaw, p_i1777_2_.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.posY -= 0.10000000149011612D;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        float f = 0.4F;
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        this.motionY = (double)(-MathHelper.sin((this.rotationPitch) / 180.0F * (float)Math.PI) * f);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, -0.05F, 2.0F);
    }
    public HMGEntityBulletCartridge(World p_i1777_1_, Entity p_i1777_2_, int tttype,String  modelid)
    {
        super(p_i1777_1_);
        this.cartType = tttype;
        this.modelname = modelid;
        NBTTagCompound nbt = this.getEntityData();
        nbt.setInteger("cartridgehmg", cartType);
        this.fuse = HandmadeGunsCore.cfg_Cartridgetime;
        this.setSize(0.25F, 0.25F);
        this.setLocationAndAngles(p_i1777_2_.posX, p_i1777_2_.posY + (double)p_i1777_2_.getEyeHeight(), p_i1777_2_.posZ, p_i1777_2_.rotationYaw, p_i1777_2_.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.posY -= 0.10000000149011612D;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        float f = 0.4F;
        this.motionX = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        this.motionZ = (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        this.motionY = (double)(-MathHelper.sin((this.rotationPitch) / 180.0F * (float)Math.PI) * f);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, -0.05F, 2.0F);
    }
	
    public HMGEntityBulletCartridge(World par1World, double par2, double par4, double par6)
    {
        super(par1World);
        this.fuse = HandmadeGunsCore.cfg_Cartridgetime;
    }

    public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8)
    {
        float var9 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
        par1 /= (double)var9;
        par3 /= (double)var9;
        par5 /= (double)var9;
        par1 += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.1499999832361937D * (double)par8;
        par3 += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01499999832361937D * (double)par8;
        par5 += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.1499999832361937D * (double)par8;
        par1 *= (double)par7;
        par3 *= (double)par7;
        par5 *= (double)par7;
        this.motionX = par1;
        this.motionY = par3;
        this.motionZ = par5;
        float var10 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI) + 90;
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, (double)var10) * 180.0D / Math.PI);
    }
    
    
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03999999910593033D;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;
        setSize(0.5f,0.5f);

        if (this.onGround)
        {
            this.motionX *= 0.9;
            this.motionY *= 0.9;
            this.motionZ *= 0.9;
            this.motionY *= -0.5D;
            this.rotationPitch = 0;
        }
        this.coolDownTime--;
        
        
        if (this.fuse-- <= 0)
        {
            this.setDead();
        }
        else
        {
            //this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
        NBTTagCompound tag = p_70037_1_.getCompoundTag("itemStack");
        if(tag != null)itemStack = ItemStack.loadItemStackFromNBT(tag);
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        if(itemStack != null) {
            itemStack.writeToNBT(nbttagcompound);
        }
        p_70014_1_.setTag("itemStack", nbttagcompound);
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        PacketBuffer lpbuf = new PacketBuffer(buffer);
        lpbuf.writeInt(cartType);
        try {
            byte[] name = fromObject(modelname);
            lpbuf.writeInt(name.length);
            lpbuf.writeBytes(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        PacketBuffer lpbuf = new PacketBuffer(additionalData);
        cartType = lpbuf.readInt();
//        if(ttype == -1){
//            ttype = this.getEntityData().getInteger("cartridgehmg");
//        }
        byte[] temp = new byte[lpbuf.readInt()];
        lpbuf.readBytes(temp);
        try {
            modelname = (String)toObject(temp);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(worldObj.isRemote){
            if(cartType ==-1 && HMGAddBullets.indexlist != null&& !HMGAddBullets.indexlist.isEmpty() &&modelname!=null&& !modelname.isEmpty() && !modelname.equals("default") && HMGAddBullets.indexlist.containsKey(modelname)) {
                modelid = HMGAddBullets.indexlist.get(modelname);
            }
        }
    }
    
    @Override
    public void onCollideWithPlayer(EntityPlayer p_70100_1_)
    {
        if (!this.worldObj.isRemote)
        {
            boolean flag = this.coolDownTime <=0;
            
            if (flag && !p_70100_1_.inventory.addItemStackToInventory(itemStack))
            {
                flag = false;
            }
            
            if (flag)
            {
                this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                p_70100_1_.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }
}
