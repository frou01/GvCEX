package handmadeguns.Util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static handmadeguns.HandmadeGunsCore.cfg_ThreadHitCheck;
import static handmadevehicle.Utils.getMinecraftVecObj;
import static java.lang.Math.abs;
import static java.lang.Math.round;
import static net.minecraft.util.MathHelper.floor_double;

public class GunsUtils {
	public static Vec3 getLook(float p_70676_1_, Entity entity)
	{
		float f1;
		float f2;
		float f3;
		float f4;


		if (p_70676_1_ == 1.0F)
		{
			f1 = MathHelper.cos(-(entity instanceof EntityLivingBase ? ((EntityLivingBase)entity).rotationYawHead : entity.rotationYaw) * 0.017453292F - (float)Math.PI);
			f2 = MathHelper.sin(-(entity instanceof EntityLivingBase ? ((EntityLivingBase)entity).rotationYawHead : entity.rotationYaw) * 0.017453292F - (float)Math.PI);
			f3 = -MathHelper.cos(-entity.rotationPitch * 0.017453292F);
			f4 = MathHelper.sin(-entity.rotationPitch * 0.017453292F);
			return Vec3.createVectorHelper((double)(f2 * f3), (double)f4, (double)(f1 * f3));
		}else {
			return null;
		}
	}

	public static Vec3 RotationVector_byAxisVector(Vec3 axis, Vec3 tovec, float angle)
	{
		double axisVectorX = axis.xCoord;
		double axisVectorY = axis.yCoord;
		double axisVectorZ = axis.zCoord;
		double toVectorX = tovec.xCoord;
		double toVectorY = tovec.yCoord;
		double toVectorZ = tovec.zCoord;
		double angleRad = (double)angle / 180.0D * Math.PI;
		double sintheta = Math.sin(angleRad);
		double costheta = Math.cos(angleRad);
		double returnVectorX = (axisVectorX * axisVectorX * (1 - costheta) + costheta)               * toVectorX + (axisVectorX * axisVectorY * (1 - costheta) - axisVectorZ * sintheta) * toVectorY + (axisVectorZ * axisVectorX * (1 - costheta) + axisVectorY * sintheta) * toVectorZ;
		double returnVectorY = (axisVectorX * axisVectorY * (1 - costheta) + axisVectorZ * sintheta) * toVectorX + (axisVectorY * axisVectorY * (1 - costheta) + costheta)               * toVectorY + (axisVectorY * axisVectorZ * (1 - costheta) - axisVectorX * sintheta) * toVectorZ;
		double returnVectorZ = (axisVectorZ * axisVectorX * (1 - costheta) - axisVectorY * sintheta) * toVectorX + (axisVectorY * axisVectorZ * (1 - costheta) + axisVectorX * sintheta) * toVectorY + (axisVectorZ * axisVectorZ * (1 - costheta) + costheta)               * toVectorZ;

		return Vec3.createVectorHelper(returnVectorX, returnVectorY, returnVectorZ);
	}

	public static MovingObjectPosition getmovingobjectPosition_forBlock(World worldObj, Vec3 start, Vec3 end){
		return getmovingobjectPosition_forBlock(worldObj, start, end,3);
	}
	static int penerateCnt;
	public static MovingObjectPosition getmovingobjectPosition_forBlock(World worldObj, final Vec3 start, final Vec3 end,int penerateCnt_in){
		MovingObjectPosition errorVal = new MovingObjectPosition((int)(start.xCoord),(int)(start.yCoord),(int)(start.zCoord),0,start);
		penerateCnt = penerateCnt_in;
		if (!Double.isNaN(start.xCoord) && !Double.isNaN(start.yCoord) && !Double.isNaN(start.zCoord))
		{
			if (!Double.isNaN(end.xCoord) && !Double.isNaN(end.yCoord) && !Double.isNaN(end.zCoord))
			{
				final int startX = floor_double(start.xCoord);
				final int startY = floor_double(start.yCoord);
				final int startZ = floor_double(start.zCoord);
//				System.out.println("debug" + startX);
				Block block = worldObj.getBlock(startX, startY, startZ);
				int cnt = worldObj.getBlockMetadata(startX, startY, startZ);

				if (block.getCollisionBoundingBoxFromPool(worldObj, startX, startY, startZ) != null && isCollidableBlock(block) && block.canCollideCheck(cnt, false))
				{
					//開始地点に無いかチェック
					MovingObjectPosition movingobjectposition = block.collisionRayTrace(worldObj, startX, startY, startZ, start, end);

					if (movingobjectposition != null)
					{
						return movingobjectposition;
					}
				}


				return checkBlockAndCheckHit(worldObj, start,end);
			}
			else
			{
				return errorVal;
			}
		}
		else
		{
			return errorVal;
		}
	}
	//	public static MovingObjectPosition checkBlockAndCheckHit(
//			final World worldObj,
//			final Vec3 start,
//			final Vec3 end){
//		int endX = floor_double(end.xCoord);
//		int endY = floor_double(end.yCoord);
//		int endZ = floor_double(end.zCoord);
//		int startX = floor_double(start.xCoord);
//		int startY = floor_double(start.yCoord);
//		int startZ = floor_double(start.zCoord);
//		int cnt = 0;
//
//		while (startX != endX || startY != endY || startZ != endZ) {
//
//			boolean needXCheck = true;
//			boolean needYCheck = true;
//			boolean needZCheck = true;
//			double nextX = 999.0D;
//			double nextY = 999.0D;
//			double nextZ = 999.0D;
//
//			if (endX > startX) {
//				nextX = (double) startX + 1.0D;
//			} else if (endX < startX) {
//				nextX = (double) startX + 0.0D;
//			} else {
//				needXCheck = false;
//			}
//
//			if (endY > startY) {
//				nextY = (double) startY + 1.0D;
//			} else if (endY < startY) {
//				nextY = (double) startY + 0.0D;
//			} else {
//				needYCheck = false;
//			}
//
//			if (endZ > startZ) {
//				nextZ = (double) startZ + 1.0D;
//			} else if (endZ < startZ) {
//				nextZ = (double) startZ + 0.0D;
//			} else {
//				needZCheck = false;
//			}
//
//			double moveScaleX = 999.0D;
//			double moveScaleZ = 999.0D;
//			double moveScaleY = 999.0D;
//			Vector3d checkLineVec = new Vector3d(
//					end.xCoord - start.xCoord,
//					end.yCoord - start.yCoord,
//					end.zCoord - start.zCoord);
//
//
//			if (needXCheck) {
//				moveScaleX = (nextX - start.xCoord) / checkLineVec.x;
//			}
//
//			if (needYCheck) {
//				moveScaleZ = (nextY - start.yCoord) / checkLineVec.y;
//			}
//
//			if (needZCheck) {
//				moveScaleY = (nextZ - start.zCoord) / checkLineVec.z;
//			}
//			byte b0;//移動方向
//
//			//探索点の移動がもっとも小さい軸に沿って探索基準ベクトルを移動させる
//			if (moveScaleX < moveScaleZ && moveScaleX < moveScaleY) {
//				if (endX > startX) {
//					b0 = 4;
//				} else {
//					b0 = 5;
//				}
//
//				start.xCoord = nextX;
//				start.yCoord += checkLineVec.y * moveScaleX;
//				start.zCoord += checkLineVec.z * moveScaleX;
//			} else if (moveScaleZ < moveScaleY) {
//				if (endY > startY) {
//					b0 = 0;
//				} else {
//					b0 = 1;
//				}
//
//				start.xCoord += checkLineVec.x * moveScaleZ;
//				start.yCoord = nextY;
//				start.zCoord += checkLineVec.z * moveScaleZ;
//			} else {
//				if (endZ > startZ) {
//					b0 = 2;
//				} else {
//					b0 = 3;
//				}
//
//				start.xCoord += checkLineVec.x * moveScaleY;
//				start.yCoord += checkLineVec.y * moveScaleY;
//				start.zCoord = nextZ;
//			}
//
//			Vec3 vec32 = Vec3.createVectorHelper(start.xCoord, start.yCoord, start.zCoord);
//			startX = (int) (vec32.xCoord = floor_double(start.xCoord));
//
//			if (b0 == 5) {
//				--startX;
//				++vec32.xCoord;
//			}
//
//			startY = (int) (vec32.yCoord = floor_double(start.yCoord));
//
//			if (b0 == 1) {
//				--startY;
//				++vec32.yCoord;
//			}
//
//			startZ = (int) (vec32.zCoord = floor_double(start.zCoord));
//
//			if (b0 == 3) {
//				--startZ;
//				++vec32.zCoord;
//			}
//
//			Block block1 = worldObj.getBlock(startX, startY, startZ);
//			int l1 = worldObj.getBlockMetadata(startX, startY, startZ);
//
//			if (isCollidableBlock(block1) && block1.getCollisionBoundingBoxFromPool(worldObj, startX, startY, startZ) != null) {
//				if (block1.canCollideCheck(l1, false)) {
//					//ブロックに当たる箇所があるかチェック
//					//同時に当たる点を確認
//					MovingObjectPosition movingobjectposition1 = block1.collisionRayTrace(worldObj, startX, startY, startZ, start, end);
//
//					if (movingobjectposition1 != null) {
//						return movingobjectposition1;
//					}
//				}
////	                    else
////	                    {
//////	                        movingobjectposition2 = new MovingObjectPosition(startX, startY, startZ, b0, start, false);
////	                    }
//			}
//		}
//
//		return null;
//	}
	public static MovingObjectPosition checkBlockAndCheckHit(
			final World worldObj,
			final Vec3 start,
			final Vec3 end){
		int endX = floor_double(end.xCoord);
		int endY = floor_double(end.yCoord);
		int endZ = floor_double(end.zCoord);
		int startX = floor_double(start.xCoord);
		int startY = floor_double(start.yCoord);
		int startZ = floor_double(start.zCoord);

		Block block = worldObj.getBlock(startX, startY, startZ);
		int k1 = worldObj.getBlockMetadata(startX, startY, startZ);

		if (block.getCollisionBoundingBoxFromPool(worldObj, startX, startY, startZ) != null && isCollidableBlock(block) && block.canCollideCheck(k1, false))
		{
			//開始地点に無いかチェック

			MovingObjectPosition movingobjectposition = block.collisionRayTrace(worldObj, startX, startY, startZ, start, end);

			if (movingobjectposition != null) {
				return movingobjectposition;
			}
		}

		Vector3d startVec = new Vector3d(start.xCoord,
				start.yCoord,
				start.zCoord);
		Vector3d end__Vec = new Vector3d(end.xCoord,
				end.yCoord,
				end.zCoord);

		Vector3d checkLineVec = new Vector3d(
				end.xCoord - start.xCoord,
				end.yCoord - start.yCoord,
				end.zCoord - start.zCoord);
		if(!cfg_ThreadHitCheck){
			int timer = (int) (checkLineVec.x + checkLineVec.y + checkLineVec.z + 3);//処理するブロックはもっとも多くてもこの量を超えない...ハズ
			while (timer > 0 && (startX != endX || startY != endY || startZ != endZ)) {
				timer--;
				boolean needXCheck = true;
				boolean needYCheck = true;
				boolean needZCheck = true;
				double nextX = 999.0D;
				double nextY = 999.0D;
				double nextZ = 999.0D;

				if (endX > startX) {
					nextX = (double) startX + 1.0D;
				} else if (endX < startX) {
					nextX = (double) startX + 0.0D;
				} else {
					needXCheck = false;
				}

				if (endY > startY) {
					nextY = (double) startY + 1.0D;
				} else if (endY < startY) {
					nextY = (double) startY + 0.0D;
				} else {
					needYCheck = false;
				}

				if (endZ > startZ) {
					nextZ = (double) startZ + 1.0D;
				} else if (endZ < startZ) {
					nextZ = (double) startZ + 0.0D;
				} else {
					needZCheck = false;
				}

				double moveScaleX = 999.0D;
				double moveScaleZ = 999.0D;
				double moveScaleY = 999.0D;
				checkLineVec.set(
						end.xCoord - start.xCoord,
						end.yCoord - start.yCoord,
						end.zCoord - start.zCoord);


				if (needXCheck) {
					moveScaleX = (nextX - start.xCoord) / checkLineVec.x;
				}

				if (needYCheck) {
					moveScaleZ = (nextY - start.yCoord) / checkLineVec.y;
				}

				if (needZCheck) {
					moveScaleY = (nextZ - start.zCoord) / checkLineVec.z;
				}
				byte b0;//移動方向

				//探索点の移動がもっとも小さい軸に沿って探索基準ベクトルを移動させる
				if (moveScaleX < moveScaleZ && moveScaleX < moveScaleY) {
					if (endX > startX) {
						b0 = 4;
					} else {
						b0 = 5;
					}

					start.xCoord = nextX;
					start.yCoord += checkLineVec.y * moveScaleX;
					start.zCoord += checkLineVec.z * moveScaleX;
				} else if (moveScaleZ < moveScaleY) {
					if (endY > startY) {
						b0 = 0;
					} else {
						b0 = 1;
					}

					start.xCoord += checkLineVec.x * moveScaleZ;
					start.yCoord = nextY;
					start.zCoord += checkLineVec.z * moveScaleZ;
				} else {
					if (endZ > startZ) {
						b0 = 2;
					} else {
						b0 = 3;
					}

					start.xCoord += checkLineVec.x * moveScaleY;
					start.yCoord += checkLineVec.y * moveScaleY;
					start.zCoord = nextZ;
				}

				Vec3 vec32 = Vec3.createVectorHelper(start.xCoord, start.yCoord, start.zCoord);
				startX = (int) (vec32.xCoord = floor_double(start.xCoord));

				if (b0 == 5) {
					--startX;
					++vec32.xCoord;
				}

				startY = (int) (vec32.yCoord = floor_double(start.yCoord));

				if (b0 == 1) {
					--startY;
					++vec32.yCoord;
				}

				startZ = (int) (vec32.zCoord = floor_double(start.zCoord));

				if (b0 == 3) {
					--startZ;
					++vec32.zCoord;
				}

				Block block1 = worldObj.getBlock(startX, startY, startZ);
				int l1 = worldObj.getBlockMetadata(startX, startY, startZ);

				if (isCollidableBlock(block1) && block1.getCollisionBoundingBoxFromPool(worldObj, startX, startY, startZ) != null) {
					if (block1.canCollideCheck(l1, false)) {
						//ブロックに当たる箇所があるかチェック
						//同時に当たる点を確認
						MovingObjectPosition movingobjectposition1 = block1.collisionRayTrace(worldObj, startX, startY, startZ, start, end);

						if (movingobjectposition1 != null) {
							return movingobjectposition1;
						}
					}
//	                    else
//	                    {
////	                        movingobjectposition2 = new MovingObjectPosition(startX, startY, startZ, b0, start, false);
//	                    }
				}
			}
			return null;
		}else {

			Vector3d oneLoopCheckVec = new Vector3d(checkLineVec);

			int loopNum = -1;

			int splitLength = 10;
			if (abs(checkLineVec.x) > abs(checkLineVec.y) && abs(checkLineVec.x) > abs(checkLineVec.z)) {
				oneLoopCheckVec.scale(splitLength / abs(checkLineVec.x));
				oneLoopCheckVec.x = round(oneLoopCheckVec.x);

				loopNum = abs(floor_double(end__Vec.x - startVec.x))/splitLength;
			} else if (abs(checkLineVec.y) > abs(checkLineVec.x) && abs(checkLineVec.y) > abs(checkLineVec.z)) {
				oneLoopCheckVec.scale(1 / abs(checkLineVec.y));
				oneLoopCheckVec.y = round(oneLoopCheckVec.y);

				loopNum = abs(floor_double(end__Vec.y - startVec.y))/splitLength;
			} else if (abs(checkLineVec.z) > abs(checkLineVec.x) && abs(checkLineVec.z) > abs(checkLineVec.y)) {
				oneLoopCheckVec.scale(1 / abs(checkLineVec.z));
				oneLoopCheckVec.z = round(oneLoopCheckVec.z);

				loopNum = abs(floor_double(end__Vec.z - startVec.z))/splitLength;
			}
			final MovingObjectPosition_andCounter returnValue = new MovingObjectPosition_andCounter(null, loopNum);
			AtomicInteger startedNum = new AtomicInteger(0);
			AtomicInteger ended__Num = new AtomicInteger(0);
			for (int cnt = 0; cnt < loopNum; cnt++) {

				startedNum.getAndIncrement();

				int finalCnt = cnt;
				int finalLoopNum = loopNum;
				ExecutorService checkerThread = Executors.newWorkStealingPool();
				checkerThread.execute(() -> {


					try {
						Vector3d currentStartVec = new Vector3d(startVec);
						Vector3d currentEnd__Vec = new Vector3d(startVec);
						currentStartVec.scaleAdd(finalCnt, oneLoopCheckVec, startVec);
						if(finalCnt + 1 == finalLoopNum){
							currentEnd__Vec.set(end__Vec);
						}else {
							currentEnd__Vec.scaleAdd(finalCnt + 1, oneLoopCheckVec, startVec);
						}

						int[] startBlockPos = new int[]{
								floor_double(currentStartVec.x),
								floor_double(currentStartVec.y),
								floor_double(currentStartVec.z)
						};
						int[] end__BlockPos = new int[]{
								floor_double(currentEnd__Vec.x),
								floor_double(currentEnd__Vec.y),
								floor_double(currentEnd__Vec.z)
						};


//				System.out.println("start" + currentStartVec);
//				System.out.println("start" + currentEnd__Vec);
//
//				System.out.println("start" + startBlockPos[0] + " , " + startBlockPos[1] + " , " + startBlockPos[2]);
//				System.out.println("end  " + end__BlockPos[0] + " , " + end__BlockPos[1] + " , " + end__BlockPos[2]);
						int cnt2 = 0;
						synchronized (returnValue) {
							if (returnValue.cnt > finalCnt) {
								Block block1 = worldObj.getBlock(startBlockPos[0], startBlockPos[1], startBlockPos[2]);
								int l1 = worldObj.getBlockMetadata(startBlockPos[0], startBlockPos[1], startBlockPos[2]);
								if (isCollidableBlock(block1) && block1.getCollisionBoundingBoxFromPool(worldObj, startBlockPos[0], startBlockPos[1], startBlockPos[2]) != null) {
									if (block1.canCollideCheck(l1, false)) {
										//ブロックに当たる箇所があるかチェック
										//同時に当たる点を確認
										MovingObjectPosition movingobjectposition1 = block1.collisionRayTrace(worldObj, startBlockPos[0], startBlockPos[1], startBlockPos[2], start, end);

										if (movingobjectposition1 != null) {
											returnValue.overWrite(movingobjectposition1, finalCnt);
										}
									}

								}
							}
						}

						while (startBlockPos[0] != end__BlockPos[0] || startBlockPos[1] != end__BlockPos[1] || startBlockPos[2] != end__BlockPos[2]) {
							cnt2++;
							if (cnt2 == splitLength*4) {
								break;
							}
							boolean needXCheck = true;
							boolean needYCheck = true;
							boolean needZCheck = true;
							double nextX = 0;
							double nextY = 0;
							double nextZ = 0;
							checkLineVec.set(
									end__BlockPos[0] - startBlockPos[0],
									end__BlockPos[1] - startBlockPos[1],
									end__BlockPos[2] - startBlockPos[2]);

							if (checkLineVec.x > 0) {
								nextX = (double) startBlockPos[0] + 1.0D;
							} else if (checkLineVec.x < 0) {
								nextX = (double) startBlockPos[0] + 0.0D;
							} else {
								needXCheck = false;
							}

							if (checkLineVec.y > 0) {
								nextY = (double) startBlockPos[1] + 1.0D;
							} else if (checkLineVec.y < 0) {
								nextY = (double) startBlockPos[1] + 0.0D;
							} else {
								needYCheck = false;
							}

							if (checkLineVec.z > 0) {
								nextZ = (double) startBlockPos[2] + 1.0D;
							} else if (checkLineVec.z < 0) {
								nextZ = (double) startBlockPos[2] + 0.0D;
							} else {
								needZCheck = false;
							}

							double moveScaleX = 999.0D;
							double moveScaleZ = 999.0D;
							double moveScaleY = 999.0D;
							checkLineVec.set(
									currentEnd__Vec.x - currentStartVec.x,
									currentEnd__Vec.y - currentStartVec.y,
									currentEnd__Vec.z - currentStartVec.z);


							if (needXCheck) {
								moveScaleX = (nextX - currentStartVec.x) / checkLineVec.x;
							}

							if (needYCheck) {
								moveScaleZ = (nextY - currentStartVec.y) / checkLineVec.y;
							}

							if (needZCheck) {
								moveScaleY = (nextZ - currentStartVec.z) / checkLineVec.z;
							}

							byte b0;
							if (moveScaleX < moveScaleZ && moveScaleX < moveScaleY) {
								if (endX > startBlockPos[0]) {
									b0 = 4;
								} else {
									b0 = 5;
								}

								currentStartVec.x = nextX;
								currentStartVec.y += checkLineVec.y * moveScaleX;
								currentStartVec.z += checkLineVec.z * moveScaleX;
							} else if (moveScaleZ < moveScaleY) {
								if (endY > startBlockPos[1]) {
									b0 = 0;
								} else {
									b0 = 1;
								}

								currentStartVec.x += checkLineVec.x * moveScaleZ;
								currentStartVec.y = nextY;
								currentStartVec.z += checkLineVec.z * moveScaleZ;
							} else {
								if (endZ > startBlockPos[2]) {
									b0 = 2;
								} else {
									b0 = 3;
								}

								currentStartVec.x += checkLineVec.x * moveScaleY;
								currentStartVec.y += checkLineVec.y * moveScaleY;
								currentStartVec.z = nextZ;
							}

							Vec3 vec32 = Vec3.createVectorHelper(currentStartVec.x, currentStartVec.y, currentStartVec.z);
							startBlockPos[0] = (int) (vec32.xCoord = floor_double(currentStartVec.x));

							if (b0 == 5) {
								--startBlockPos[0];
								++vec32.xCoord;
							}

							startBlockPos[1] = (int) (vec32.yCoord = floor_double(currentStartVec.y));

							if (b0 == 1) {
								--startBlockPos[1];
								++vec32.yCoord;
							}

							startBlockPos[2] = (int) (vec32.zCoord = floor_double(currentStartVec.z));
							if (b0 == 3) {
								--startBlockPos[2];
								++vec32.zCoord;
							}

							synchronized (returnValue) {
								if (returnValue.cnt > finalCnt) {
									Block block1 = worldObj.getBlock(startBlockPos[0], startBlockPos[1], startBlockPos[2]);
									int l1 = worldObj.getBlockMetadata(startBlockPos[0], startBlockPos[1], startBlockPos[2]);
									if (isCollidableBlock(block1) && block1.getCollisionBoundingBoxFromPool(worldObj, startBlockPos[0], startBlockPos[1], startBlockPos[2]) != null) {
										if (block1.canCollideCheck(l1, false)) {
											//ブロックに当たる箇所があるかチェック
											//同時に当たる点を確認
											MovingObjectPosition movingobjectposition1 = block1.collisionRayTrace(worldObj, startBlockPos[0], startBlockPos[1], startBlockPos[2], start, end);

											if (movingobjectposition1 != null) {
												returnValue.overWrite(movingobjectposition1, finalCnt);
											}
										}

									}
								}
							}
//					System.out.println("start" + startBlockPos[0] + " , " + startBlockPos[1] + " , " + startBlockPos[2]);
//					System.out.println("end  " + end__BlockPos[0] + " , " + end__BlockPos[1] + " , " + end__BlockPos[2]);
						}
						ended__Num.incrementAndGet();
						checkerThread.shutdown();
					} catch (Throwable e) {
						e.printStackTrace();
					}
				});
			}

//		System.out.println("debug");



			while(startedNum.get() != ended__Num.get()){
//            System.out.println("debug startedNum" + startedNum.get());
//            System.out.println("debug ended__Num" + ended__Num.get());
			}

			return returnValue.value;
		}

	}

	public static class MovingObjectPosition_andCounter{
		MovingObjectPosition value;
		int cnt;
		public MovingObjectPosition_andCounter(MovingObjectPosition value,int cnt){
			this.value = value;
			this.cnt = cnt;
		}
		public void overWrite(MovingObjectPosition value,int cnt){
			this.value = value;
			this.cnt = cnt;
		}
	}
	public static boolean getMovingObjectPosition_forBlock_CheckEmpty(World worldObj, Vec3 start, Vec3 end, int penerateCnt_in){
		penerateCnt = penerateCnt_in;
		return checkBlockAndCheckHit(worldObj,start,end) == null;
	}

	public static boolean isCollidableBlock(Block block){
		return !((((block.getMaterial() == Material.air) || (block.getMaterial() == Material.plants) || (block.getMaterial() == Material.leaves) || (block.getMaterial() == Material.fire) || ((
				block.getMaterial() == Material.glass ||
						block instanceof BlockFence ||
						block instanceof BlockFenceGate ||
						block == Blocks.iron_bars) && --penerateCnt>0/*こっちは貫通回数減少*/))));
	}
}
