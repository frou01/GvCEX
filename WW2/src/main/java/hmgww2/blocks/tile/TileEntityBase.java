package hmgww2.blocks.tile;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hmgww2.Nation;
import hmgww2.entity.*;
import hmgww2.mod_GVCWW2;
import hmgww2.network.WW2PacketFlagSync;
import hmgww2.network.WW2PacketHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import static hmgww2.mod_GVCWW2.*;

/*
 * TileEntityのクラスです。
 * TileEntityは、Tick毎に特殊な動作をしたり、複雑なモデルを持ったり、
 * NBTを使ってデータを格納したり、色々な用途に使えます。
 *
 * ただしこのクラス内で行われた処理やデータは基本的にサーバ側にしかないので、
 * 同期処理についてよく考えて実装する必要があります。
 */
public class TileEntityBase extends TileEntity
{
	private int invasion;
	public boolean spawn = true;
	
	//public int mobkazu;
	//public int soldierkazu;
	//public int vkazu;
	
	public int ticks;
	public int maxs = 30;
	public int spawntime = 600;
	public int[] spawnoffset = {0,0,0};
	public int flagRange = 15;
	
	Random rnd = new Random();
	
	public Nation nation;
	public FlagType flagType;
	
	
	public TileEntityBase(){
	
	}
	
	public TileEntityBase(Nation nation,FlagType flagType,int maxs,int spawntime){
		this.nation = nation;
		this.flagType = flagType;
		this.maxs = maxs;
		this.spawntime = spawntime;
		ticks = spawntime;
	}
	public TileEntityBase(Nation nation,FlagType flagType,int maxs,int spawntime,int[] offset,int range){
		this(nation,flagType,maxs,spawntime);
		spawnoffset = offset;
		flagRange = range;
	}
	
	NationEntityList USAlist = new NationEntityList(new Class[]{EntityUSA_S.class},
			                                               new Class[]{EntityUSA_S.class,EntityUSA_Tank.class,EntityUSA_TankAA.class,EntityUSA_TankSPG.class},
			                                               new Class[]{EntityUSA_S.class,EntityUSA_TankAA.class,
					                                               EntityUSA_Fighter.class,EntityUSA_FighterA.class,
					                                               EntityUSA_Fighter.class,
					                                               EntityUSA_Fighter.class,
					                                               EntityUSA_Fighter.class,
					                                               EntityUSA_Tank.class},
			                                               new Class[]{EntityUSA_S.class,EntityUSA_S.class,EntityUSA_S.class,EntityUSA_ShipD.class},
			                                               new Class[]{EntityUSA_S.class,EntityUSA_S.class,EntityUSA_S.class,EntityUSA_Tank.class});
	
	NationEntityList USSRlist = new NationEntityList(new Class[]{EntityUSSR_S.class},
			                                                new Class[]{EntityUSSR_S.class,EntityUSSR_Tank.class,EntityUSSR_TankAA.class,EntityUSSR_TankSPG.class,EntityUSSR_TankH.class},
			                                                new Class[]{EntityUSSR_S.class,EntityUSSR_TankAA.class,
					                                                EntityUSSR_Fighter.class,EntityUSSR_FighterA.class,
					                                                EntityUSSR_Fighter.class,
					                                                EntityUSSR_Fighter.class,
					                                                EntityUSSR_Fighter.class,
					                                                EntityUSSR_Tank.class},
			                                                new Class[]{EntityUSSR_S.class,EntityUSSR_S.class,EntityUSSR_S.class,EntityUSSR_TankH.class},
			                                                new Class[]{EntityUSSR_S.class,EntityUSSR_S.class,EntityUSSR_S.class,EntityUSSR_Tank.class});
	NationEntityList GERlist = new NationEntityList(new Class[]{EntityGER_S.class},
			                                               new Class[]{EntityGER_S.class,EntityGER_Tank.class,EntityGER_TankAA.class,EntityGER_TankSPG.class,EntityGER_TankH.class},
			                                               new Class[]{EntityGER_S.class,EntityGER_TankAA.class,
					                                               EntityGER_Fighter.class,EntityGER_FighterA.class,
					                                               EntityGER_Fighter.class,
					                                               EntityGER_Fighter.class,
					                                               EntityGER_Fighter.class,
					                                               EntityGER_Tank.class},
			                                               new Class[]{EntityGER_S.class,EntityGER_S.class,EntityGER_S.class,EntityGER_ShipSUB.class},
			                                               new Class[]{EntityGER_S.class,EntityGER_S.class,EntityGER_S.class,EntityGER_Tank.class});
	NationEntityList JPNlist = new NationEntityList(new Class[]{EntityJPN_S.class},
			                                               new Class[]{EntityJPN_S.class,EntityJPN_Tank.class,EntityJPN_TankAA.class,EntityJPN_TankSPG.class},
			                                               new Class[]{EntityJPN_S.class,EntityJPN_TankAA.class,
					                                               EntityJPN_Fighter.class,EntityJPN_FighterA.class,
					                                               EntityJPN_Fighter.class,
					                                               EntityJPN_Fighter.class,
					                                               EntityJPN_Fighter.class,
					                                               EntityJPN_Tank.class},
			                                               new Class[]{EntityJPN_S.class,EntityJPN_S.class,EntityJPN_S.class,EntityJPN_ShipD.class},
			                                               new Class[]{EntityJPN_S.class,EntityJPN_S.class,EntityJPN_S.class,EntityJPN_Tank.class});
	
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		setInvasionSet(par1NBTTagCompound.getInteger("InvasionSet"));
		this.spawn = par1NBTTagCompound.getBoolean("SpawnSoldier");
		this.nation = Nation.values()[par1NBTTagCompound.getInteger("nation")];
		this.flagType = FlagType.values()[par1NBTTagCompound.getInteger("flagType")];
		this.flagRange = par1NBTTagCompound.getInteger("flagRange");
		this.maxs = par1NBTTagCompound.getInteger("maxs");
		this.spawnoffset[0] = par1NBTTagCompound.getInteger("spawnoffset1");
		this.spawnoffset[1] = par1NBTTagCompound.getInteger("spawnoffset2");
		this.spawnoffset[2] = par1NBTTagCompound.getInteger("spawnoffset3");
	}
	
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("InvasionSet", getInvasion());
		par1NBTTagCompound.setBoolean("SpawnSoldier", spawn);
		par1NBTTagCompound.setInteger("nation", nation.ordinal());
		par1NBTTagCompound.setInteger("flagType", flagType.ordinal());
		par1NBTTagCompound.setInteger("flagRange", flagRange);
		par1NBTTagCompound.setInteger("maxs", maxs);
		par1NBTTagCompound.setInteger("spawnoffset1", spawnoffset[0]);
		par1NBTTagCompound.setInteger("spawnoffset2", spawnoffset[1]);
		par1NBTTagCompound.setInteger("spawnoffset3", spawnoffset[2]);
	}
	
	/*
		* パケットの送信・受信処理。
		* カスタムパケットは使わず、バニラのパケット送受信処理を使用。
		*/
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		this.writeToNBT(nbtTagCompound);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTagCompound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.func_148857_g());
	}/**/
	
	
	public void updateEntity()
	{
		super.updateEntity();
		{
			Block nowBlock = worldObj.getBlock(xCoord,
					yCoord,
					zCoord);
			
			Block must_block = null;
			switch (flagType) {
				case Barrack:
					must_block = BarrackBlocks[nation.ordinal()];
					break;
				case Factory:
					must_block = FactoryBlocks[nation.ordinal()];
					break;
				case AirBase:
					must_block = AirBaseBlocks[nation.ordinal()];
					break;
				case Port:
					must_block = PortBlocks[nation.ordinal()];
					break;
				case Fort:
					break;
			}
			if(nowBlock != must_block){
				worldObj.setBlock(xCoord,
						yCoord,
						zCoord,must_block);
			}
		}
		
		if(!worldObj.isRemote) {
			++ticks;
			int k = xCoord;
			int l = yCoord;
			int i = zCoord;
			
			WW2PacketFlagSync packetFlagSync = new WW2PacketFlagSync();
			packetFlagSync.x = xCoord;
			packetFlagSync.y = yCoord;
			packetFlagSync.z = zCoord;
			packetFlagSync.inv = getInvasion();
			packetFlagSync.nation = nation;
			WW2PacketHandler.INSTANCE.sendToAll(packetFlagSync);
			AxisAlignedBB axisalignedbb2 = AxisAlignedBB.getBoundingBox(
					(double) (k), (double) (l), (double) (i),
					(double) (k), (double) (l + 1), (double) (i))
					                               .expand(flagRange, 128, flagRange);
			List llist1 = this.worldObj.getEntitiesWithinAABBExcludingEntity(null, axisalignedbb2);
			int nearEnemySize = 0;
			int[] solSize_nation = new int[4];
			int nearAlliesSize = 0;
			if (llist1 != null) {
				for (int lj = 0; lj < llist1.size(); lj++) {
					Entity entity1 = (Entity) llist1.get(lj);
					if (entity1.canBeCollidedWith()) {
						if (entity1 instanceof EntityBases) {
							if (((EntityBases) entity1).getnation() != nation) {
								++nearEnemySize;
								solSize_nation[((EntityBases) entity1).getnation().ordinal()]++;
							} else {
								++nearAlliesSize;
							}
						}
					}
				}
			}
			if ((ticks >= spawntime)) {//周辺の兵士の数がスポーン最大数以下ならスポーン処理
				if((nearEnemySize <= 0 || nearEnemySize < nearAlliesSize) && nearAlliesSize + nearEnemySize < maxs){
					NationEntityList nationEntityList = null;
					switch (nation) {
						case USA: {
							nationEntityList = USAlist;
						}
						break;
						case USSR: {
							nationEntityList = USSRlist;
						}
						break;
						case GER: {
							nationEntityList = GERlist;
						}
						break;
						case JPN: {
							nationEntityList = JPNlist;
						}
						break;
					}
					Class[] classes = null;
					switch (flagType) {
						case Barrack:
							classes = nationEntityList.Barrack;
							break;
						case Factory:
							classes = nationEntityList.Factory;
							break;
						case AirBase:
							classes = nationEntityList.AirBase;
							break;
						case Port:
							classes = nationEntityList.Port;
							break;
						case Fort:
							classes = nationEntityList.Fort;
							break;
					}
					try {
						for (int ii = 0; ii < 5; ++ii) {
							EntityBases entity_willSpawn = (EntityBases) classes[rnd.nextInt(classes.length)].getConstructor(World.class).newInstance(worldObj);
							int ix = rnd.nextInt(10) - 5 + spawnoffset[0];
							int iz = rnd.nextInt(10) - 5 + spawnoffset[1];
							int iy = (entity_willSpawn instanceof EntityBases_Plane?60:0) + spawnoffset[2];
							entity_willSpawn.setLocationAndAngles(this.xCoord + ix, this.yCoord + iy, this.zCoord + 0.5 + iz, rnd.nextInt(360) - 180, 0.0F);
							{//search flag
								double disttoflag = -1;
								TileEntityBase targetflag = null;
								for (int temp = 0; temp < worldObj.loadedTileEntityList.size(); temp++) {
									Object aLoadedTileEntity = worldObj.loadedTileEntityList.get(temp);
									if (aLoadedTileEntity instanceof TileEntityBase && nation != ((TileEntityBase) aLoadedTileEntity).getnation()) {
										TileEntityBase temptile = (TileEntityBase) aLoadedTileEntity;
										double tempdisttoflag = this.getDistanceFrom(temptile.xCoord, temptile.yCoord, temptile.zCoord);
										if (disttoflag == -1 || tempdisttoflag < disttoflag) {
//											System.out.println("debug" + temptile);
											disttoflag = tempdisttoflag;
											targetflag = temptile;
										}
									}
									
								}
								(entity_willSpawn).mode = 1;
								if (targetflag != null && rnd.nextBoolean()) {
									(entity_willSpawn).homeposX = (int) targetflag.xCoord;
									(entity_willSpawn).homeposY = (int) targetflag.yCoord;
									(entity_willSpawn).homeposZ = (int) targetflag.zCoord;
								} else {
									(entity_willSpawn).homeposX = (int) this.xCoord;
									(entity_willSpawn).homeposY = (int) this.yCoord;
									(entity_willSpawn).homeposZ = (int) this.zCoord;
								}
							}
							
							entity_willSpawn.addRandomArmor();
							worldObj.spawnEntityInWorld(entity_willSpawn);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					ticks = 0;
//			if(sorv){
//			}else{
//				if(ticks >= spawntime){
//					if(soldierkazu <= maxs){
//						{
//							int x = 0;
//							int y = 0;
//							int z = 0;
//							SpawnEntity(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.xCoord + x - 128, this.yCoord + y - 32, this.zCoord + z - 128);
//							ticks = 0;
//						}
//					}else{
//						ticks = 0;
//					}
//				}
//				if(ticks2 >= spawntimev){
//					if(vkazu <= maxv && soldierkazu <= maxs){
//						{
//							int x = 0;
//							int y = 0;
//							int z = 0;
//							SpawnEntity2(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.xCoord + x - 128, this.yCoord + y - 32, this.zCoord + z - 128);
//							ticks2 = 0;
//						}
//					}else{
//						ticks2 = 0;
//					}
//				}
//			}
				}else {
					ticks = 0;
				}
			}
			
			if (nearEnemySize == 0) {
				this.setInvasionSet(0);
			} else if (nearAlliesSize > nearEnemySize) {
				if (this.getInvasion() >= -1) {
					this.setInvasionSet(getInvasion() - 1);
				}
			} else {
				this.setInvasionSet(getInvasion() + 1);
			}
			if (this.getInvasion() >= 400) {
				int tempsize = -1;
				int nationID = -1;
				for (int i1 = 0; i1 < solSize_nation.length; i1++) {
					if (tempsize < solSize_nation[i1]) {
						nationID = i1;
						tempsize = solSize_nation[i1];
					}
				}
				if (nationID != -1) {
					Block willSetBlock = null;
					switch (flagType) {
						case Barrack:
							willSetBlock = BarrackBlocks[nationID];
							break;
						case Factory:
							willSetBlock = FactoryBlocks[nationID];
							break;
						case AirBase:
							willSetBlock = AirBaseBlocks[nationID];
							break;
						case Port:
							willSetBlock = PortBlocks[nationID];
							break;
						case Fort:
							break;
					}
					if (willSetBlock != null) {
						worldObj.setBlock(xCoord, yCoord, zCoord, willSetBlock);
					}
				}
			}
		}
	}
	
	public int getInvasion() {
		return invasion;
	}
	
	public void setInvasionSet(int i) {
		this.invasion = i;
	}
	
	
	public Nation getnation(){
		return nation;
	}
	
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		AxisAlignedBB bb = INFINITE_EXTENT_AABB;
		Block type = getBlockType();
		if (type != null && type != Blocks.beacon)
		{
			AxisAlignedBB cbb = type.getCollisionBoundingBoxFromPool(worldObj, xCoord, yCoord, zCoord);
			if (cbb != null)
			{
				bb = cbb;
			}
			bb = bb.expand(3,3,3);
		}
		return bb;
	}
}