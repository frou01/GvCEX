package handmadeguns.entity;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;

public class HMGEntityFallingBlockModified extends Entity implements IEntityAdditionalSpawnData {

	private Block block;
	public int Data;
	public int Time;
	public boolean DropItem;
	private boolean field_145808_f;
	private boolean HurtEntities;
	private int FallHurtMax;
	private float FallHurtAmount;
	public NBTTagCompound field_145810_d;
	private static final String __OBFID = "CL_00001668";

	public HMGEntityFallingBlockModified(World p_i1706_1_)
	{
		super(p_i1706_1_);
		this.DropItem = true;
		this.FallHurtMax = 40;
		this.FallHurtAmount = 2.0F;
	}

	public HMGEntityFallingBlockModified(World p_i45318_1_, double p_i45318_2_, double p_i45318_4_, double p_i45318_6_, Block p_i45318_8_)
	{
		this(p_i45318_1_, p_i45318_2_, p_i45318_4_, p_i45318_6_, p_i45318_8_, 0);
	}

	public HMGEntityFallingBlockModified(World p_i45319_1_, double p_i45319_2_, double p_i45319_4_, double p_i45319_6_, Block p_i45319_8_, int p_i45319_9_)
	{
		super(p_i45319_1_);
		this.DropItem = true;
		this.FallHurtMax = 40;
		this.FallHurtAmount = 2.0F;
		this.block = p_i45319_8_;
		this.Data = p_i45319_9_;
		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.98F);
		this.setPosition(p_i45319_2_, p_i45319_4_, p_i45319_6_);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = p_i45319_2_;
		this.prevPosY = p_i45319_4_;
		this.prevPosZ = p_i45319_6_;
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
	 * prevent them from trampling crops
	 */
	protected boolean canTriggerWalking()
	{
		return false;
	}

	protected void entityInit() {}

	/**
	 * Returns true if other Entities should be prevented from moving through this Entity.
	 */
	public boolean canBeCollidedWith()
	{
		return !this.isDead;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
		if (this.block.getMaterial() == Material.air)
		{
			this.setDead();
		}
		else
		{
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;
			++this.Time;
			this.motionY -= 0.03999999910593033D;
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.9800000190734863D;
			this.motionY *= 0.9800000190734863D;
			this.motionZ *= 0.9800000190734863D;

			if (!this.worldObj.isRemote)
			{
				int i = MathHelper.floor_double(this.posX);
				int j = MathHelper.floor_double(this.posY);
				int k = MathHelper.floor_double(this.posZ);


				if (this.onGround)
				{
					if(!BlockFalling.func_149831_e(this.worldObj, i, j - 1, k)) {
						this.motionX *= 0.699999988079071D;
						this.motionZ *= 0.699999988079071D;
					}
					this.motionY *= -0.5D;

					if (this.worldObj.getBlock(i, j, k) != Blocks.piston_extension)
					{

						if (!this.field_145808_f &&
								this.worldObj.canPlaceEntityOnSide(this.block, i, j, k, true, 1, (Entity)null, (ItemStack)null) &&
								!BlockFalling.func_149831_e(this.worldObj, i, j - 1, k) &&
								this.worldObj.setBlock(i, j, k, this.block, this.Data, 3))
						{
							this.setDead();
							if (this.block instanceof BlockFalling)
							{
								((BlockFalling)this.block).func_149828_a(this.worldObj, i, j, k, this.Data);
							}

							if (this.field_145810_d != null && this.block instanceof ITileEntityProvider)
							{
								TileEntity tileentity = this.worldObj.getTileEntity(i, j, k);

								if (tileentity != null)
								{
									NBTTagCompound nbttagcompound = new NBTTagCompound();
									tileentity.writeToNBT(nbttagcompound);
									Iterator iterator = this.field_145810_d.func_150296_c().iterator();

									while (iterator.hasNext())
									{
										String s = (String)iterator.next();
										NBTBase nbtbase = this.field_145810_d.getTag(s);

										if (!s.equals("x") && !s.equals("y") && !s.equals("z"))
										{
											nbttagcompound.setTag(s, nbtbase.copy());
										}
									}

									tileentity.readFromNBT(nbttagcompound);
									tileentity.markDirty();
								}
							}
						}
						else if(Time > 40) {
							this.setDead();
							if (this.DropItem && !this.field_145808_f) {
								this.entityDropItem(new ItemStack(this.block, 1, this.block.damageDropped(this.Data)), 0.0F);
							}
						}else {
							motionY = 0.7 + (this.posY - (j + 0.5))/3;
						}
					}
				}
				else if (this.Time > 100 && (j < 1 || j > 256) || this.Time > 600)
				{
					if (this.DropItem)
					{
						this.entityDropItem(new ItemStack(this.block, 1, this.block.damageDropped(this.Data)), 0.0F);
					}
					System.out.println("debug");

					this.setDead();
				}
			}
		}
	}

	/**
	 * Called when the mob is falling. Calculates and applies fall damage.
	 */
	protected void fall(float p_70069_1_)
	{
		if (this.HurtEntities)
		{
			int i = MathHelper.ceiling_float_int(p_70069_1_ - 1.0F);

			if (i > 0)
			{
				ArrayList arraylist = new ArrayList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox));
				boolean flag = this.block == Blocks.anvil;
				DamageSource damagesource = flag ? DamageSource.anvil : DamageSource.fallingBlock;
				Iterator iterator = arraylist.iterator();

				while (iterator.hasNext())
				{
					Entity entity = (Entity)iterator.next();
					entity.attackEntityFrom(damagesource, (float)Math.min(MathHelper.floor_float((float)i * this.FallHurtAmount), this.FallHurtMax));
				}

				if (flag && (double)this.rand.nextFloat() < 0.05000000074505806D + (double)i * 0.05D)
				{
					int j = this.Data >> 2;
					int k = this.Data & 3;
					++j;

					if (j > 2)
					{
						this.field_145808_f = true;
					}
					else
					{
						this.Data = k | j << 2;
					}
				}
			}
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		p_70014_1_.setByte("Tile", (byte)Block.getIdFromBlock(this.block));
		p_70014_1_.setInteger("TileID", Block.getIdFromBlock(this.block));
		p_70014_1_.setByte("Data", (byte)this.Data);
		p_70014_1_.setByte("Time", (byte)this.Time);
		p_70014_1_.setBoolean("DropItem", this.DropItem);
		p_70014_1_.setBoolean("HurtEntities", this.HurtEntities);
		p_70014_1_.setFloat("FallHurtAmount", this.FallHurtAmount);
		p_70014_1_.setInteger("FallHurtMax", this.FallHurtMax);

		if (this.field_145810_d != null)
		{
			p_70014_1_.setTag("TileEntityData", this.field_145810_d);
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		if (p_70037_1_.hasKey("TileID", 99))
		{
			this.block = Block.getBlockById(p_70037_1_.getInteger("TileID"));
		}
		else
		{
			this.block = Block.getBlockById(p_70037_1_.getByte("Tile") & 255);
		}

		this.Data = p_70037_1_.getByte("Data") & 255;
		this.Time = p_70037_1_.getByte("Time") & 255;

		if (p_70037_1_.hasKey("HurtEntities", 99))
		{
			this.HurtEntities = p_70037_1_.getBoolean("HurtEntities");
			this.FallHurtAmount = p_70037_1_.getFloat("FallHurtAmount");
			this.FallHurtMax = p_70037_1_.getInteger("FallHurtMax");
		}
		else if (this.block == Blocks.anvil)
		{
			this.HurtEntities = true;
		}

		if (p_70037_1_.hasKey("DropItem", 99))
		{
			this.DropItem = p_70037_1_.getBoolean("DropItem");
		}

		if (p_70037_1_.hasKey("TileEntityData", 10))
		{
			this.field_145810_d = p_70037_1_.getCompoundTag("TileEntityData");
		}

		if (this.block.getMaterial() == Material.air)
		{
			this.block = Blocks.sand;
		}
	}

	public void func_145806_a(boolean p_145806_1_)
	{
		this.HurtEntities = p_145806_1_;
	}

	public void addEntityCrashInfo(CrashReportCategory p_85029_1_)
	{
		super.addEntityCrashInfo(p_85029_1_);
		p_85029_1_.addCrashSection("Immitating block ID", Integer.valueOf(Block.getIdFromBlock(this.block)));
		p_85029_1_.addCrashSection("Immitating block data", Integer.valueOf(this.Data));
	}

	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0.0F;
	}

	@SideOnly(Side.CLIENT)
	public World func_145807_e()
	{
		return this.worldObj;
	}

	/**
	 * Return whether this entity should be rendered as on fire.
	 */
	@SideOnly(Side.CLIENT)
	public boolean canRenderOnFire()
	{
		return false;
	}

	public Block func_145805_f()
	{
		return this.block;
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeInt(Block.getIdFromBlock(block));
		buffer.writeInt(Data);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		block = Block.getBlockById(additionalData.readInt());
		Data = additionalData.readInt();
	}
}
