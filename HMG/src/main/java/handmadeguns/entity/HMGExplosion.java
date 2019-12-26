package handmadeguns.entity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;
import java.util.*;

import static java.lang.Math.abs;

public class HMGExplosion extends Explosion {
	private static final Random explosionRNG = new Random();
	public HMGExplosion(World p_i1948_1_, Entity p_i1948_2_, double p_i1948_3_, double p_i1948_5_, double p_i1948_7_, float p_i1948_9_) {
		super(p_i1948_1_, p_i1948_2_, p_i1948_3_, p_i1948_5_, p_i1948_7_, p_i1948_9_);
		this.worldObj = p_i1948_1_;
	}

	private World worldObj;

	private Map field_77288_k = new HashMap();

	public void doExplosionA()
	{
		float f = this.explosionSize;
		HashSet hashset = new HashSet();
		int i;
		int j;
		int k;
		double d5;
		double d6;
		double d7;

		int field_77289_h = 16;
		for (i = 0; i < field_77289_h; ++i)
		{
			for (j = 0; j < field_77289_h; ++j)
			{
				for (k = 0; k < field_77289_h; ++k)
				{
					if (i == 0 || i == field_77289_h - 1 || j == 0 || j == field_77289_h - 1 || k == 0 || k == field_77289_h - 1)
					{
						double d0 = (double)((float)i / ((float) field_77289_h - 1.0F) * 2.0F - 1.0F);
						double d1 = (double)((float)j / ((float) field_77289_h - 1.0F) * 2.0F - 1.0F);
						double d2 = (double)((float)k / ((float) field_77289_h - 1.0F) * 2.0F - 1.0F);
						double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
						d0 /= d3;
						d1 /= d3;
						d2 /= d3;
						float f1 = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
						d5 = this.explosionX;
						d6 = this.explosionY;
						d7 = this.explosionZ;

						for (float f2 = 0.3F; f1 > 0.0F; f1 -= f2 * 0.75F)
						{
							int j1 = MathHelper.floor_double(d5);
							int k1 = MathHelper.floor_double(d6);
							int l1 = MathHelper.floor_double(d7);
							Block block = this.worldObj.getBlock(j1, k1, l1);

							if (block.getMaterial() != Material.air)
							{
								float f3 = block.getExplosionResistance(this.exploder, worldObj, j1, k1, l1, explosionX, explosionY, explosionZ);
								f1 -= (f3 + 0.3F) * f2;
							}

							if (f1 > 0.0F)
							{
								hashset.add(new ChunkPosition(j1, k1, l1));
							}

							d5 += d0 * (double)f2;
							d6 += d1 * (double)f2;
							d7 += d2 * (double)f2;
						}
					}
				}
			}
		}

		this.affectedBlockPositions.addAll(hashset);
		this.explosionSize *= 2.0F;
		i = MathHelper.floor_double(this.explosionX - (double)this.explosionSize - 1.0D);
		j = MathHelper.floor_double(this.explosionX + (double)this.explosionSize + 1.0D);
		k = MathHelper.floor_double(this.explosionY - (double)this.explosionSize - 1.0D);
		int i2 = MathHelper.floor_double(this.explosionY + (double)this.explosionSize + 1.0D);
		int l = MathHelper.floor_double(this.explosionZ - (double)this.explosionSize - 1.0D);
		int j2 = MathHelper.floor_double(this.explosionZ + (double)this.explosionSize + 1.0D);
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, AxisAlignedBB.getBoundingBox((double)i, (double)k, (double)l, (double)j, (double)i2, (double)j2));
		net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.worldObj, this, list, this.explosionSize);
		Vec3 vec3 = Vec3.createVectorHelper(this.explosionX, this.explosionY, this.explosionZ);

		for (int i1 = 0; i1 < list.size(); ++i1)
		{
			Entity entity = (Entity)list.get(i1);
			double d4 = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / (double)this.explosionSize;

			if (d4 <= 1.0D)
			{
				d5 = entity.posX - this.explosionX;
				d6 = entity.posY + (double)entity.getEyeHeight() - this.explosionY;
				d7 = entity.posZ - this.explosionZ;
				double d9 = (double)MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7);

				if (d9 != 0.0D)
				{
					d5 /= d9;
					d6 /= d9;
					d7 /= d9;
					double d10 = (double)this.worldObj.getBlockDensity(vec3, entity.boundingBox);
					double d11 = (1.0D - d4) * d10;
					entity.attackEntityFrom(DamageSource.setExplosionSource(this), (float)((int)((d11 * d11 + d11) / 2.0D * 8.0D * (double)this.explosionSize + 1.0D)));
					double d8 = EnchantmentProtection.func_92092_a(entity, d11);
					entity.addVelocity(d5 * d8,
							d6 * d8,
							d7 * d8);

					if (entity instanceof EntityPlayer)
					{
						this.field_77288_k.put((EntityPlayer)entity, Vec3.createVectorHelper(d5 * d11, d6 * d11, d7 * d11));
					}
				}
			}
		}

		this.explosionSize = f;
	}

	/**
	 * Does the second part of the explosion (sound, particles, drop spawn)
	 */
	public void doExplosionB(boolean p_77279_1_)
	{
		this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);

		if (this.explosionSize >= 2.0F && this.isSmoking)
		{
			this.worldObj.spawnParticle("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
		}
		else
		{
			this.worldObj.spawnParticle("largeexplode", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
		}

		Iterator iterator;
		ChunkPosition chunkposition;
		int i;
		int j;
		int k;
		Block block;

		if (this.isSmoking)
		{
			iterator = this.affectedBlockPositions.iterator();

			while (iterator.hasNext())
			{
				chunkposition = (ChunkPosition)iterator.next();
				i = chunkposition.chunkPosX;
				j = chunkposition.chunkPosY;
				k = chunkposition.chunkPosZ;
				block = this.worldObj.getBlock(i, j, k);

				if (p_77279_1_)
				{
					double d0 = (double)((float)i + this.worldObj.rand.nextFloat());
					double d1 = (double)((float)j + this.worldObj.rand.nextFloat());
					double d2 = (double)((float)k + this.worldObj.rand.nextFloat());
					double d3 = d0 - this.explosionX;
					double d4 = d1 - this.explosionY;
					double d5 = d2 - this.explosionZ;
					double d6 = (double)MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
					d3 /= d6;
					d4 /= d6;
					d5 /= d6;
					double d7 = 0.5D / (d6 / (double)this.explosionSize + 0.1D);
					d7 *= (double)(this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
					d3 *= d7;
					d4 *= d7;
					d5 *= d7;
					this.worldObj.spawnParticle("explode", (d0 + this.explosionX * 1.0D) / 2.0D, (d1 + this.explosionY * 1.0D) / 2.0D, (d2 + this.explosionZ * 1.0D) / 2.0D, d3, d4, d5);
					this.worldObj.spawnParticle("smoke", d0, d1, d2, d3, d4, d5);
				}

				if (block.getMaterial() != Material.air)
				{
					Vector3d zeroToBlockVec = new Vector3d(i+0.5 - this.explosionX,
							j+0.5 - this.explosionY,
							k+0.5 - this.explosionZ);

					if(zeroToBlockVec.lengthSquared() < explosionSize/10 || block != Blocks.grass && block != Blocks.dirt && block != Blocks.sand && block != Blocks.cobblestone){
						if (block.canDropFromExplosion(this))
						{
							block.dropBlockAsItemWithChance(this.worldObj, i, j, k, this.worldObj.getBlockMetadata(i, j, k), 1.0F / this.explosionSize, 0);
						}


						block.onBlockExploded(this.worldObj, i, j, k, this);
					}else {
						HMGEntityFallingBlockModified entityFallingBlock = new HMGEntityFallingBlockModified(worldObj,i + 0.5, j + 0.5, k + 0.5,block,worldObj.getBlockMetadata(i,j,k));
						zeroToBlockVec.scale(0.1 * explosionSize/(1+zeroToBlockVec.lengthSquared()));
						entityFallingBlock.motionX = zeroToBlockVec.x;
						entityFallingBlock.motionY = zeroToBlockVec.length() + zeroToBlockVec.y;
						entityFallingBlock.motionZ = zeroToBlockVec.z;
						worldObj.spawnEntityInWorld(entityFallingBlock);
						worldObj.setBlockToAir(i, j, k);
					}
				}
			}
		}

		if (this.isFlaming)
		{
			iterator = this.affectedBlockPositions.iterator();

			while (iterator.hasNext())
			{
				chunkposition = (ChunkPosition)iterator.next();
				i = chunkposition.chunkPosX;
				j = chunkposition.chunkPosY;
				k = chunkposition.chunkPosZ;
				block = this.worldObj.getBlock(i, j, k);
				Block block1 = this.worldObj.getBlock(i, j - 1, k);

				if (block.getMaterial() == Material.air && block1.func_149730_j() && this.explosionRNG.nextInt(3) == 0)
				{
					this.worldObj.setBlock(i, j, k, Blocks.fire);
				}
			}
		}
	}
	public Map func_77277_b()
	{
		return this.field_77288_k;
	}
}
