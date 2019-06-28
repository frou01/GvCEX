
package hmggvcmob.render;

import cpw.mods.fml.client.FMLClientHandler;
import handmadeguns.client.render.HMGGunParts_Motion;
import handmadeguns.client.render.HMGGunParts_Motion_PosAndRotation;
import handmadeguns.client.render.HMGGunParts_Motions;
import handmadeguns.client.render.HMGParts;
import hmggvcmob.entity.EntityMGAX55;
import hmvehicle.entity.parts.ModifiedBoundingBox;
import hmvehicle.entity.parts.OBB;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import javax.vecmath.Vector3d;

import java.util.ArrayList;

import static hmvehicle.CLProxy.drawOutlinedBoundingBox;
import static java.lang.Math.abs;
import static net.minecraft.client.renderer.entity.RenderManager.debugBoundingBox;

public class GVCRenderMGM extends Render {

	private static final ResourceLocation skeletonTexturesz = new ResourceLocation("gvcmob:textures/model/MGG.png");
	private static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("gvcmob:textures/model/MGG.mqo"));

	float[] LlegFemurCenter = new float[3];
	float[] RlegFemurCenter = new float[3];

	float[] LlegAnkleCenter = new float[3];
	float[] RlegAnkleCenter = new float[3];

	public ArrayList<HMGParts> Partslist = new ArrayList<HMGParts>();
	private HMGGunParts_Motions onforwardmotions = new HMGGunParts_Motions();
	private HMGGunParts_Motions onjumpedmotions = new HMGGunParts_Motions();
	Vector3d sliderVec = new Vector3d(0,-0.50585,0.8636849925705795);

	public GVCRenderMGM() {
		int tester = 10;
		HMGParts LLegFemur = new HMGParts();
		LLegFemur.partsname = "LLegFemur";
		LLegFemur.AddRenderinfDef(2.4f,3.3f,-2.35f,30 - tester,0,0);
		LLegFemur.AddRenderinfsneak(0,0,0,12 - tester,0,0);
		LLegFemur.AddMotionKeyonSneaking(
				0,0,0,0,30 - tester,0,0,
				30,0,0,0,12 - tester,0,0);
		LLegFemur.AddRenderinfjump(0,0,0,40 - tester,0,0);

		LLegFemur.AddMotionKeyonForward(
				0,0,0,0,30 - tester,0,0,
				6,0,0,0,30 - tester,0,0);
		LLegFemur.AddMotionKeyonForward(
				6,0,0,0,30 - tester,0,0,
				12,0,0,0,20 - tester,0,0);
		LLegFemur.AddMotionKeyonForward(
				12,0,0,0,20 - tester,0,0,
				18,0,0,0,30 - tester,0,0);
		LLegFemur.AddMotionKeyonForward(
				18,0,0,0,30 - tester,0,0,
				24,0,0,0,40 - tester,0,0);

		LLegFemur.AddMotionKeyonForward(
				24,0,0,0,40 - tester,0,0,
				30,0,0,0,30 - tester,0,0);
		LLegFemur.AddMotionKeyonForward(
				30,0,0,0,30 - tester,0,0,
				36,0,0,0,20 - tester,0,0);
		LLegFemur.AddMotionKeyonForward(
				36,0,0,0,20 - tester,0,0,
				42,0,0,0,30 - tester,0,0);
		LLegFemur.AddMotionKeyonForward(
				42,0,0,0,30 - tester,0,0,
				48,0,0,0,40 - tester,0,0);


		LLegFemur.AddMotionKeyonLeft(
				0,0,0,0,30 - tester,0,0,
				8,0,0,0,5 - tester,20,-5);

		LLegFemur.AddMotionKeyonLeft(
				8,0,0,0,5 - tester,20,-5,
				16,0,0,0,5 - tester,40,-10);

		LLegFemur.AddMotionKeyonLeft(
				16,0,0,0,5 - tester,40,-10,
				24,0,0,0,30 - tester,40,-10);

		LLegFemur.AddMotionKeyonLeft(
				24,0,0,0,30 - tester,40,0,
				32,0,0,0,30 - tester,40,10);

		LLegFemur.AddMotionKeyonLeft(
				32,0,0,0,30 - tester,40,10,
				40,0,0,0,30 - tester,20,5);

		LLegFemur.AddMotionKeyonLeft(
				40,0,0,0,30 - tester,20,5,
				48,0,0,0,30 - tester,0,0);



		LLegFemur.AddMotionKeyonRight(
				0, 0,0,0,30 - tester,0,0,
				8,0,0,0, 30 - tester,20,5);

		LLegFemur.AddMotionKeyonRight(
				8,0,0,0,30 - tester,20,5,
				16,0,0,0,30 - tester,40,10);

		LLegFemur.AddMotionKeyonRight(
				16,0,0,0,30 - tester,40,10,
				24,0,0,0,30 - tester,40,0);

		LLegFemur.AddMotionKeyonRight(
				24, 0,0,0,30 - tester,40,0,
				32,0,0,0, 5 - tester,40,-10);

		LLegFemur.AddMotionKeyonRight(
				32,0,0,0,5 - tester,40,-10,
				40,0,0,0,5 - tester,20,-5);

		LLegFemur.AddMotionKeyonRight(
				40,0,0,0,5 - tester,20,-5,
				48,0,0,0,30 - tester,0,0);

		LLegFemur.AddMotionKeyonBack(
				0,0,0,0,30 - tester,0,0,
				4,0,0,0,30 - tester,0,0);
		LLegFemur.AddMotionKeyonBack(
				4,0,0,0,30 - tester,0,0,
				12,0,0,0,20 - tester,0,0);
		LLegFemur.AddMotionKeyonBack(
				12,0,0,0,20 - tester,0,0,
				16,0,0,0,30 - tester,0,0);
		LLegFemur.AddMotionKeyonBack(
				16,0,0,0,30 - tester,0,0,
				24,0,0,0,40 - tester,0,0);

		LLegFemur.AddMotionKeyonBack(
				24,0,0,0,40 - tester,0,0,
				30,0,0,0,30 - tester,0,0);
		LLegFemur.AddMotionKeyonBack(
				30,0,0,0,30 - tester,0,0,
				36,0,0,0,20 - tester,0,0);
		LLegFemur.AddMotionKeyonBack(
				36,0,0,0,20 - tester,0,0,
				42,0,0,0,30 - tester,0,0);
		LLegFemur.AddMotionKeyonBack(
				42,0,0,0,30 - tester,0,0,
				48,0,0,0,40 - tester,0,0);

		LLegFemur.AddMotionKeyonJumped(
				0,0,0,0,40 - tester,0,0,
				6,0,0,0,20 - tester,0,0);
		LLegFemur.AddMotionKeyonJumped(
				6,0,0,0,20 - tester,0,0,
				12,0,0,0,30 - tester,0,0);

		HMGParts LlegKnee = new HMGParts();
		LlegKnee.partsname = "LLegKnee";
		LlegKnee.AddRenderinfDef(2.52f,1.3f,-1.8f,20,0,0);
		LlegKnee.AddRenderinfsneak(0,0,0,0,0,0);
		LlegKnee.AddMotionKeyonSneaking(
				0,0,0,0,20,0,0,
				30,0,0,0,0,0,0);
		LlegKnee.AddRenderinfjump(0,0,0,30,0,0);

		LlegKnee.AddMotionKeyonForward(
				0,0,0,0,20,0,0,
				6,0,0,0,20,0,0);
		LlegKnee.AddMotionKeyonForward(
				6,0,0,0,20,0,0,
				12,0,0,0,0,0,0);
		LlegKnee.AddMotionKeyonForward(
				12,0,0,0,0,0,0,
				18,0,0,0,20,0,0);
		LlegKnee.AddMotionKeyonForward(
				18,0,0,0,20,0,0,
				24,0,0,0,30,0,0);

		LlegKnee.AddMotionKeyonForward(
				24,0,0,0,30,0,0,
				30,0,0,0,20,0,0);
		LlegKnee.AddMotionKeyonForward(
				30,0,0,0,20,0,0,
				36,0,0,0,0,0,0);
		LlegKnee.AddMotionKeyonForward(
				36,0,0,0,0,0,0,
				42,0,0,0,20,0,0);
		LlegKnee.AddMotionKeyonForward(
				42,0,0,0,20,0,0,
				48,0,0,0,30,0,0);



		LlegKnee.AddMotionKeyonBack(
				0,0,0,0,20,0,0,
				4,0,0,0,20,0,0);
		LlegKnee.AddMotionKeyonBack(
				4,0,0,0,20,0,0,
				12,0,0,0,0,0,0);
		LlegKnee.AddMotionKeyonBack(
				12,0,0,0,0,0,0,
				16,0,0,0,0,0,0);
		LlegKnee.AddMotionKeyonBack(
				16,0,0,0,0,0,0,
				24,0,0,0,20,0,0);

		LlegKnee.AddMotionKeyonBack(
				24,0,0,0,20,0,0,
				28,0,0,0,20,0,0);
		LlegKnee.AddMotionKeyonBack(
				28,0,0,0,20,0,0,
				36,0,0,0,0,0,0);
		LlegKnee.AddMotionKeyonBack(
				36,0,0,0,0,0,0,
				40,0,0,0,0,0,0);
		LlegKnee.AddMotionKeyonBack(
				40,0,0,0,0,0,0,
				48,0,0,0,20,0,0);


		LlegKnee.AddMotionKeyonJumped(
				0,0,0,0,30,0,0,
				6,0,0,0,15,0,0);
		LlegKnee.AddMotionKeyonJumped(
				6,0,0,0,15,0,0,
				12,0,0,0,20,0,0);
		LLegFemur.childs.add(LlegKnee);


		HMGParts LlegCrus = new HMGParts();
		LlegCrus.partsname = "LLegCrus";
		LlegCrus.AddRenderinfDef(2.52f,1.3f,-1.8f,0,0,0);
		LlegKnee.childs.add(LlegCrus);
		HMGParts LlegDump = new HMGParts();
		LlegDump.partsname = "LDamper";
		LlegDump.AddRenderinfDef(2.52f,1.5f,-2.66f,0,0,0);
		LlegDump.AddRenderinfsneak(0,0,0,75,0,0);
		LlegDump.AddMotionKeyonSneaking(
				0,0,0,0,0,0,0,
				30,0,0,0,75,0,0);
		LlegKnee.childs.add(LlegDump);

		HMGParts LlegAnkle = new HMGParts();
		LlegAnkle.partsname = "LLegAnkle";
		LlegAnkle.AddRenderinfDef(2.52f,1.3f,-1.7f,0,0,0);


		LlegAnkle.AddMotionKeyonForward(
				0,0,0,0,0,0,0,
				8,0,0,-1.7f,0,0,0);
		LlegAnkle.AddMotionKeyonForward(
				8,0,0,-1.7f,0,0,0,
				12,0,0,1,0,0,0);
		LlegAnkle.AddMotionKeyonForward(
				12,0,0,1,0,0,0,
				20,0,0,0,0,0,0);
		LlegAnkle.AddMotionKeyonForward(
				20,0,0,0,0,0,0,
				24,0,0,-1.7f,0,0,0);

		LlegAnkle.AddMotionKeyonForward(
				24,0,0,-1.7f,0,0,0,
				30,0,0,-1.7f,0,0,0);
		LlegAnkle.AddMotionKeyonForward(
				30,0,0,-1.7f,0,0,0,
				36,0,0,1,0,0,0);
		LlegAnkle.AddMotionKeyonForward(
				36,0,0,1,0,0,0,
				42,0,0,0,0,0,0);
		LlegAnkle.AddMotionKeyonForward(
				42,0,0,0,0,0,0,
				48,0,0,-1.7f,0,0,0);


		LlegAnkle.AddMotionKeyonBack(
				0,0,0,0,0,0,0,
				8,0,0,-1.7f,0,0,0);
		LlegAnkle.AddMotionKeyonBack(
				8,0,0,-1.7f,0,0,0,
				12,0,0,1,0,0,0);
		LlegAnkle.AddMotionKeyonBack(
				12,0,0,1,0,0,0,
				20,0,0,0,0,0,0);
		LlegAnkle.AddMotionKeyonBack(
				20,0,0,0,0,0,0,
				24,0,0,-1.7f,0,0,0);

		LlegAnkle.AddMotionKeyonBack(
				24,0,0,-1.7f,0,0,0,
				30,0,0,0,0,0,0);
		LlegAnkle.AddMotionKeyonBack(
				30,0,0,0,0,0,0,
				36,0,0,1,0,0,0);
		LlegAnkle.AddMotionKeyonBack(
				36,0,0,1,0,0,0,
				42,0,0,-1.7f,0,0,0);
		LlegAnkle.AddMotionKeyonBack(
				42,0,0,-1.7f,0,0,0,
				48,0,0,-1.7f,0,0,0);

		LlegKnee.childs.add(LlegAnkle);

		HMGParts LlegNail = new HMGParts();
		LlegNail.partsname = "LNail";
		LlegNail.AddRenderinfDef(0,0,0,0,0,0);
		LlegAnkle.childs.add(LlegNail);
		Partslist.add(LLegFemur);


		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


		HMGParts RLegFemur = new HMGParts();
		RLegFemur.partsname = "RLegFemur";
		RLegFemur.AddRenderinfDef(-2.4f,3.3f,-2.35f,30 - tester,0,0);
		RLegFemur.AddRenderinfsneak(0,0,0,12 - tester,0,0);
		RLegFemur.AddMotionKeyonSneaking(
				0,0,0,0,30 - tester,0,0,
				30,0,0,0,12 - tester,0,0);
		RLegFemur.AddRenderinfjump(0,0,0,40 - tester,0,0);

		RLegFemur.AddMotionKeyonForward(
				0,0,0,0,30 - tester,0,0,
				8,0,0,0,30 - tester,0,0);
		RLegFemur.AddMotionKeyonForward(
				8,0,0,0,30 - tester,0,0,
				12,0,0,0,40 - tester,0,0);
		RLegFemur.AddMotionKeyonForward(
				12,0,0,0,40 - tester,0,0,
				20,0,0,0,30 - tester,0,0);
		RLegFemur.AddMotionKeyonForward(
				20,0,0,0,30 - tester,0,0,
				24,0,0,0,20 - tester,0,0);

		RLegFemur.AddMotionKeyonForward(
				24,0,0,0,20 - tester,0,0,
				30,0,0,0,30 - tester,0,0);
		RLegFemur.AddMotionKeyonForward(
				30,0,0,0,30 - tester,0,0,
				36,0,0,0,40 - tester,0,0);
		RLegFemur.AddMotionKeyonForward(
				36,0,0,0,40 - tester,0,0,
				42,0,0,0,30 - tester,0,0);
		RLegFemur.AddMotionKeyonForward(
				42,0,0,0,30 - tester,0,0,
				48,0,0,0,20 - tester,0,0);





		RLegFemur.AddMotionKeyonLeft(
				0,0,0,0,30 - tester,0,0,
				8,0,0,0,30 - tester,-20,5);

		RLegFemur.AddMotionKeyonLeft(
				8,0,0,0,30 - tester,-20,5,
				16,0,0,0,30 - tester,-40,10);

		RLegFemur.AddMotionKeyonLeft(
				16,0,0,0,30 - tester,-40,10,
				24,0,0,0,30 - tester,-40,0);

		RLegFemur.AddMotionKeyonLeft(
				24,0,0,0,30 - tester,-40,0,
				32,0,0,0,5 - tester,-40,-10);

		RLegFemur.AddMotionKeyonLeft(
				32,0,0,0,5 - tester,-40,-10,
				40,0,0,0,5 - tester,-20,-5);

		RLegFemur.AddMotionKeyonLeft(
				40,0,0,0,5 - tester,-20,-5,
				48,0,0,0,30 - tester,0,0);



		RLegFemur.AddMotionKeyonRight(
				0, 0,0,0,30 - tester,0,0,
				8,0,0,0, 5 - tester,-20,-5);

		RLegFemur.AddMotionKeyonRight(
				8,0,0,0,5 - tester,-20,-5,
				16,0,0,0,5 - tester,-40,-10);

		RLegFemur.AddMotionKeyonRight(
				16,0,0,0,5 - tester,-40,-10,
				24,0,0,0,30 - tester,-40,0);

		RLegFemur.AddMotionKeyonRight(
				24, 0,0,0,30 - tester,-40,0,
				32,0,0,0, 30 - tester,-40,10);

		RLegFemur.AddMotionKeyonRight(
				32,0,0,0,30 - tester,-40,10,
				40,0,0,0,30 - tester,-20,5);

		RLegFemur.AddMotionKeyonRight(
				40,0,0,0,30 - tester,-20,5,
				48,0,0,0,30 - tester,0,0);



		RLegFemur.AddMotionKeyonBack(
				0,0,0,0,30 - tester,0,0,
				4,0,0,0,80 - tester,0,0);
		RLegFemur.AddMotionKeyonBack(
				4,0,0,0,80 - tester,0,0,
				12,0,0,0,40 - tester,0,0);
		RLegFemur.AddMotionKeyonBack(
				12,0,0,0,40 - tester,0,0,
				16,0,0,0,30 - tester,0,0);
		RLegFemur.AddMotionKeyonBack(
				16,0,0,0,30 - tester,0,0,
				24,0,0,0,20 - tester,0,0);

		RLegFemur.AddMotionKeyonBack(
				24,0,0,0,20 - tester,0,0,
				28,0,0,0,30 - tester,0,0);
		RLegFemur.AddMotionKeyonBack(
				28,0,0,0,30 - tester,0,0,
				36,0,0,0,40 - tester,0,0);
		RLegFemur.AddMotionKeyonBack(
				36,0,0,0,40 - tester,0,0,
				40,0,0,0,30 - tester,0,0);
		RLegFemur.AddMotionKeyonBack(
				40,0,0,0,30 - tester,0,0,
				48,0,0,0,20 - tester,0,0);


		RLegFemur.AddMotionKeyonJumped(
				0,0,0,0,40 - tester,0,0,
				6,0,0,0,20 - tester,0,0);
		RLegFemur.AddMotionKeyonJumped(
				6,0,0,0,20 - tester,0,0,
				12,0,0,0,30 - tester,0,0);

		HMGParts RLegKnee = new HMGParts();
		RLegKnee.partsname = "RLegKnee";
		RLegKnee.AddRenderinfDef(-2.52f,1.3f,-1.8f,20,0,0);
		RLegKnee.AddRenderinfsneak(0,0,0,0,0,0);
		RLegKnee.AddMotionKeyonSneaking(
				0,0,0,0,20,0,0,
				30,0,0,0,0,0,0);
		RLegKnee.AddRenderinfjump(0,0,0,30,0,0);

		RLegKnee.AddMotionKeyonForward(
				0,0,0,0,20,0,0,
				6,0,0,0,20,0,0);
		RLegKnee.AddMotionKeyonForward(
				6,0,0,0,20,0,0,
				12,0,0,0,30,0,0);
		RLegKnee.AddMotionKeyonForward(
				12,0,0,0,30,0,0,
				18,0,0,0,20,0,0);
		RLegKnee.AddMotionKeyonForward(
				18,0,0,0,20,0,0,
				24,0,0,0,0,0,0);

		RLegKnee.AddMotionKeyonForward(
				24,0,0,0,0,0,0,
				30,0,0,0,20,0,0);
		RLegKnee.AddMotionKeyonForward(
				30,0,0,0,20,0,0,
				36,0,0,0,30,0,0);
		RLegKnee.AddMotionKeyonForward(
				36,0,0,0,30,0,0,
				42,0,0,0,20,0,0);
		RLegKnee.AddMotionKeyonForward(
				42,0,0,0,20,0,0,
				48,0,0,0,0,0,0);



		RLegKnee.AddMotionKeyonBack(
				0,0,0,0,20,0,0,
				4,0,0,0,0,0,0);
		RLegKnee.AddMotionKeyonBack(
				4,0,0,0,0,0,0,
				12,0,0,0,20,0,0);
		RLegKnee.AddMotionKeyonBack(
				12,0,0,0,20,0,0,
				16,0,0,0,20,0,0);
		RLegKnee.AddMotionKeyonBack(
				16,0,0,0,20,0,0,
				24,0,0,0,0,0,0);

		RLegKnee.AddMotionKeyonBack(
				24,0,0,0,0,0,0,
				28,0,0,0,0,0,0);
		RLegKnee.AddMotionKeyonBack(
				28,0,0,0,0,0,0,
				36,0,0,0,20,0,0);
		RLegKnee.AddMotionKeyonBack(
				36,0,0,0,20,0,0,
				40,0,0,0,20,0,0);
		RLegKnee.AddMotionKeyonBack(
				40,0,0,0,20,0,0,
				48,0,0,0,0,0,0);


		RLegKnee.AddMotionKeyonJumped(
				0,0,0,0,30,0,0,
				6,0,0,0,15,0,0);
		RLegKnee.AddMotionKeyonJumped(
				6,0,0,0,15,0,0,
				12,0,0,0,20,0,0);

		RLegFemur.childs.add(RLegKnee);

		HMGParts RlegCrus = new HMGParts();
		RlegCrus.partsname = "RLegCrus";
		RlegCrus.AddRenderinfDef(-2.52f,1.3f,-1.8f,0,0,0);
		RLegKnee.childs.add(RlegCrus);
		HMGParts RlegDump = new HMGParts();
		RlegDump.partsname = "RDamper";
		RlegDump.AddRenderinfDef(2.52f,1.5f,-2.66f,0,0,0);
		RlegDump.AddRenderinfsneak(0,0,0,75,0,0);
		RlegDump.AddMotionKeyonSneaking(
				0,0,0,0,0,0,0,
				30,0,0,0,75,0,0);
		RLegKnee.childs.add(RlegDump);


		HMGParts RLegAnkle = new HMGParts();
		RLegAnkle.partsname = "RLegAnkle";
		RLegAnkle.AddRenderinfDef(-2.52f,5.3f,-1.7f,0,0,0);


		RLegAnkle.AddMotionKeyonForward(
				0,0,0,0,0,0,0,
				8,0,0,0,0,0,0);
		RLegAnkle.AddMotionKeyonForward(
				8,0,0,0,0,0,0,
				12,0,0,-1.7f,0,0,0);
		RLegAnkle.AddMotionKeyonForward(
				12,0,0,-1.7f,0,0,0,
				20,0,0,-1.7f,0,0,0);
		RLegAnkle.AddMotionKeyonForward(
				20,0,0,-1.7f,0,0,0,
				24,0,0,1,0,0,0);

		RLegAnkle.AddMotionKeyonForward(
				24,0,0,1,0,0,0,
				30,0,0,0,0,0,0);
		RLegAnkle.AddMotionKeyonForward(
				30,0,0,0,0,0,0,
				36,0,0,-1.7f,0,0,0);
		RLegAnkle.AddMotionKeyonForward(
				36,0,0,-1.7f,0,0,0,
				42,0,0,-1.7f,0,0,0);
		RLegAnkle.AddMotionKeyonForward(
				42,0,0,-1.7f,0,0,0,
				48,0,0,1,0,0,0);


		RLegAnkle.AddMotionKeyonBack(
				0,0,0,0,0,0,0,
				8,0,0,0,0,0,0);
		RLegAnkle.AddMotionKeyonBack(
				8,0,0,0,0,0,0,
				12,0,0,-1.7f,0,0,0);
		RLegAnkle.AddMotionKeyonBack(
				12,0,0,-1.7f,0,0,0,
				20,0,0,-1.7f,0,0,0);
		RLegAnkle.AddMotionKeyonBack(
				20,0,0,-1.7f,0,0,0,
				24,0,0,1,0,0,0);

		RLegAnkle.AddMotionKeyonBack(
				24,0,0,1,0,0,0,
				30,0,0,-1.7f,0,0,0);
		RLegAnkle.AddMotionKeyonBack(
				30,0,0,-1.7f,0,0,0,
				36,0,0,-1.7f,0,0,0);
		RLegAnkle.AddMotionKeyonBack(
				36,0,0,-1.7f,0,0,0,
				42,0,0,0,0,0,0);
		RLegAnkle.AddMotionKeyonBack(
				42,0,0,0,0,0,0,
				48,0,0,1,0,0,0);

		RLegKnee.childs.add(RLegAnkle);

		HMGParts RLegNail = new HMGParts();
		RLegNail.partsname = "RNail";
		RLegNail.AddRenderinfDef(0,0,0,0,0,0);
		RLegAnkle.childs.add(RLegNail);
		Partslist.add(RLegFemur);

		AddMotionKeyonJumped(
				0,0,3,0,0,0,0,
				6,0,-2.0f,0,0,0,0);
		AddMotionKeyonJumped(
				6,0,-2.0f,0,0,0,0,
				12,0,0,0,0,0,0);


		AddMotionKeyonForward(
				0,0,0,0,0,0,0,
				8,0,0,0,0,0,0);
		AddMotionKeyonForward(
				8,0,0,0,0,0,0,
				12,0,0.5f,0,0,0,0);
		AddMotionKeyonForward(
				12,0,0.5f,0,0,0,0,
				20,0,0,0,0,0,0);
		AddMotionKeyonForward(
				20,0,0,0,0,0,0,
				24,0,0.5f,0,0,0,0);

		AddMotionKeyonForward(
				24,0,0.5f,0,0,0,0,
				28,0,0,0,0,0,0);
		AddMotionKeyonForward(
				28,0,0,0,0,0,0,
				36,0,0.5f,0,0,0,0);
		AddMotionKeyonForward(
				36,0,0.5f,0,0,0,0,
				40,0,0,0,0,0,0);
		AddMotionKeyonForward(
				40,0,0,0,0,0,0,
				48,0,0.5f,0,0,0,0);

		LlegFemurCenter[0] = 2.4f;
		LlegFemurCenter[1] = 5.3f;
		LlegFemurCenter[2] = -1.7f;

		LlegAnkleCenter[0] = 2.52f;
		LlegAnkleCenter[1] = 4;
		LlegAnkleCenter[2] = -2;//max20deg;

		RlegFemurCenter[0] = -2.4f;
		RlegFemurCenter[1] = 5.3f;
		RlegFemurCenter[2] = -1.7f;

		RlegAnkleCenter[0] = -2.52f;
		RlegAnkleCenter[1] = 4;
		RlegAnkleCenter[2] = -2;//max20deg;


	}

	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
						 float p_76986_9_) {
		this.doRender((EntityMGAX55) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	float xsxs;
	public void doRender(EntityMGAX55 entity, double p_76986_2_, double p_76986_4_, double p_76986_6_,
                         float entityYaw, float partialTicks) {
		this.bindEntityTexture(entity);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_ + 3.5f, (float) p_76986_6_);
		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glRotatef(180 - (entity.bodyrotationYaw), 0.0F, 1.0F, 0.0F);
		
		GL11.glPushMatrix();
		
		

		renderLegForward(entity);

		model.renderPart("LLegJoint");
		model.renderPart("RLegJoint");
		model.renderPart("crotch&Body");
		model.renderPart("WeaponModuleArm");
		model.renderPart("WeaponModule");
		model.renderPart("Radome");
		model.renderPart("RadomeHead");
		model.renderPart("RailGunArm");
		GL11.glPushMatrix();
		GL11.glTranslatef((float) -EntityMGAX55.gunpos[0][0], (float) EntityMGAX55.gunpos[0][1], (float) EntityMGAX55.gunpos[0][2]);
		GL11.glRotatef(-entity.RailGUNrotationYaw, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entity.RailGUNrotationPitch, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef((float) EntityMGAX55.gunpos[0][0], -(float) EntityMGAX55.gunpos[0][1], -(float) EntityMGAX55.gunpos[0][2]);
		model.renderPart("RailGun");
		GL11.glPopMatrix();


		if(entity.riddenByEntity != FMLClientHandler.instance().getClientPlayerEntity() ||  FMLClientHandler.instance().getClient().gameSettings.thirdPersonView != 0) {
			GL11.glPushMatrix();
			GL11.glTranslatef((float) EntityMGAX55.gunpos[5][0], (float) EntityMGAX55.gunpos[5][1], (float) EntityMGAX55.gunpos[5][2]);
			GL11.glRotatef(-entity.headrotationYaw, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(entity.headrotationPitch, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(-(float) EntityMGAX55.gunpos[5][0], -(float) EntityMGAX55.gunpos[5][1], -(float) EntityMGAX55.gunpos[5][2]);
			model.renderPart("Head");
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		if(debugBoundingBox && entity.nboundingbox != null){
			GL11.glDepthMask(false);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
			ModifiedBoundingBox axisalignedbb = entity.nboundingbox;
			GL11.glTranslatef(0,-3f,0);
			drawOutlinedBoundingBox(axisalignedbb, 16777215);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glDepthMask(true);
		}
		GL11.glPopMatrix();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}
	public void renderLegForward(EntityMGAX55 entityMGM){
		if(entityMGM.legmodeJump){
			GL11.glTranslatef(0,3,0);
			for (HMGParts part : Partslist) {
				GL11.glPushMatrix();
				renderPartJumping(entityMGM, part);
				GL11.glPopMatrix();
			}
		}else
		if(entityMGM.legmodeJumpstop){
			HMGGunParts_Motion_PosAndRotation motion = onjumpedmotions.getpartsMotion(entityMGM.legStatejumped);
			if(motion != null)
				GL11.glTranslatef(motion.posX,motion.posY,motion.posZ);
			for (HMGParts part : Partslist) {
				GL11.glPushMatrix();
				renderPartJumped(entityMGM, part);
				GL11.glPopMatrix();
			}
		}else
		if(entityMGM.legSneak_CTRL || entityMGM.legSneak_State) {
			GL11.glTranslatef(0,-3.15f * (entityMGM.legSneak_State_Progress)/30,0);
			for (HMGParts part : Partslist) {
				GL11.glPushMatrix();
				renderPartSneak(entityMGM, part);
				GL11.glPopMatrix();
			}
		}else if(entityMGM.legmodeForward) {
			HMGGunParts_Motion_PosAndRotation motion = onforwardmotions.getpartsMotion(entityMGM.legStateFoward);
			if(motion != null)
				GL11.glTranslatef(motion.posX,motion.posY,motion.posZ);
			for (HMGParts part : Partslist) {
				GL11.glPushMatrix();
				renderPartForward(entityMGM, part);
				GL11.glPopMatrix();
			}
		}else if(entityMGM.legmodeBack) {
			HMGGunParts_Motion_PosAndRotation motion = onforwardmotions.getpartsMotion(entityMGM.legStateBack);
			if(motion != null)
				GL11.glTranslatef(motion.posX,motion.posY,motion.posZ);
			for (HMGParts part : Partslist) {
				GL11.glPushMatrix();
				renderPartBack(entityMGM, part);
				GL11.glPopMatrix();
			}
		}else if(entityMGM.legmodeRight) {
			for (HMGParts part : Partslist) {
				GL11.glPushMatrix();
				renderPartRight(entityMGM, part);
				GL11.glPopMatrix();
			}
		}else if(entityMGM.legmodeLeft) {
			for (HMGParts part : Partslist) {
				GL11.glPushMatrix();
				renderPartLeft(entityMGM, part);
				GL11.glPopMatrix();
			}
		}else if(entityMGM.legmodeturnRight) {
			for (HMGParts part : Partslist) {
				GL11.glPushMatrix();
				renderPartTurnRight(entityMGM, part);
				GL11.glPopMatrix();
			}
		}else if(entityMGM.legmodeturnLeft) {
			for (HMGParts part : Partslist) {
				GL11.glPushMatrix();
				renderPartTurnLeft(entityMGM, part);
				GL11.glPopMatrix();
			}
		}else{
			for(HMGParts part:Partslist) {
				GL11.glPushMatrix();
				renderPart(entityMGM, part);
				GL11.glPopMatrix();
			}
		}
	}

	public void renderPartForward(EntityMGAX55 entityMGM, HMGParts hmgParts){

		HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation = hmgParts.getRenderinfOfDef();
		HMGGunParts_Motion_PosAndRotation OffsetAndRotation = hmgParts.getforwardmotion(entityMGM.legStateFoward);
		if(OffsetAndRotation != null) {
			GL11.glTranslatef(OffsetAndRotation.posX, OffsetAndRotation.posY, OffsetAndRotation.posZ);
			GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
			GL11.glRotatef(OffsetAndRotation.rotationY, 0, 1, 0);
			GL11.glRotatef(OffsetAndRotation.rotationX, 1, 0, 0);
			GL11.glRotatef(OffsetAndRotation.rotationZ, 0, 0, 1);
			GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
		}else {
			GL11.glTranslatef(rotationCenterAndRotation.posX,rotationCenterAndRotation.posY,rotationCenterAndRotation.posZ);
			GL11.glRotatef(rotationCenterAndRotation.rotationY,0,1,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationX,1,0,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationZ,0,0,1);
			GL11.glTranslatef(-rotationCenterAndRotation.posX,-rotationCenterAndRotation.posY,-rotationCenterAndRotation.posZ);
		}
		model.renderPart(hmgParts.partsname);
		for(HMGParts childpart:hmgParts.childs){
			GL11.glPushMatrix();
			renderPartForward(entityMGM,childpart);
			GL11.glPopMatrix();
		}
	}
	public void renderPartLeft(EntityMGAX55 entityMGM, HMGParts hmgParts){

		HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation = hmgParts.getRenderinfOfDef();
		HMGGunParts_Motion_PosAndRotation OffsetAndRotation = hmgParts.getleftmotion(entityMGM.legStateLeft);
		if(OffsetAndRotation != null) {
			GL11.glTranslatef(OffsetAndRotation.posX, OffsetAndRotation.posY, OffsetAndRotation.posZ);
			GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
			GL11.glRotatef(OffsetAndRotation.rotationY, 0, 1, 0);
			GL11.glRotatef(OffsetAndRotation.rotationX, 1, 0, 0);
			GL11.glRotatef(OffsetAndRotation.rotationZ, 0, 0, 1);
			GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
		}else {
			GL11.glTranslatef(rotationCenterAndRotation.posX,rotationCenterAndRotation.posY,rotationCenterAndRotation.posZ);
			GL11.glRotatef(rotationCenterAndRotation.rotationY,0,1,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationX,1,0,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationZ,0,0,1);
			GL11.glTranslatef(-rotationCenterAndRotation.posX,-rotationCenterAndRotation.posY,-rotationCenterAndRotation.posZ);
		}
		model.renderPart(hmgParts.partsname);
		for(HMGParts childpart:hmgParts.childs){
			GL11.glPushMatrix();
			renderPartLeft(entityMGM,childpart);
			GL11.glPopMatrix();
		}
	}
	public void renderPartRight(EntityMGAX55 entityMGM, HMGParts hmgParts){

		HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation = hmgParts.getRenderinfOfDef();
		HMGGunParts_Motion_PosAndRotation OffsetAndRotation = hmgParts.getrightmotion(entityMGM.legStateRight);
		if(OffsetAndRotation != null) {
			GL11.glTranslatef(OffsetAndRotation.posX, OffsetAndRotation.posY, OffsetAndRotation.posZ);
			GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
			GL11.glRotatef(OffsetAndRotation.rotationY, 0, 1, 0);
			GL11.glRotatef(OffsetAndRotation.rotationX, 1, 0, 0);
			GL11.glRotatef(OffsetAndRotation.rotationZ, 0, 0, 1);
			GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
		}else {
			GL11.glTranslatef(rotationCenterAndRotation.posX,rotationCenterAndRotation.posY,rotationCenterAndRotation.posZ);
			GL11.glRotatef(rotationCenterAndRotation.rotationY,0,1,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationX,1,0,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationZ,0,0,1);
			GL11.glTranslatef(-rotationCenterAndRotation.posX,-rotationCenterAndRotation.posY,-rotationCenterAndRotation.posZ);
		}
		model.renderPart(hmgParts.partsname);
		for(HMGParts childpart:hmgParts.childs){
			GL11.glPushMatrix();
			renderPartRight(entityMGM,childpart);
			GL11.glPopMatrix();
		}
	}
	public void renderPartTurnLeft(EntityMGAX55 entityMGM, HMGParts hmgParts){

		HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation = hmgParts.getRenderinfOfDef();
		HMGGunParts_Motion_PosAndRotation OffsetAndRotation = hmgParts.getrightmotion(entityMGM.legStateTurnLeft);
		if(OffsetAndRotation != null) {
			GL11.glTranslatef(OffsetAndRotation.posX, OffsetAndRotation.posY, OffsetAndRotation.posZ);
			GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
			GL11.glRotatef(OffsetAndRotation.rotationY, 0, 1, 0);
			GL11.glRotatef(OffsetAndRotation.rotationX, 1, 0, 0);
			GL11.glRotatef(OffsetAndRotation.rotationZ, 0, 0, 1);
			GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
		}else {
			GL11.glTranslatef(rotationCenterAndRotation.posX,rotationCenterAndRotation.posY,rotationCenterAndRotation.posZ);
			GL11.glRotatef(rotationCenterAndRotation.rotationY,0,1,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationX,1,0,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationZ,0,0,1);
			GL11.glTranslatef(-rotationCenterAndRotation.posX,-rotationCenterAndRotation.posY,-rotationCenterAndRotation.posZ);
		}
		model.renderPart(hmgParts.partsname);
		for(HMGParts childpart:hmgParts.childs){
			GL11.glPushMatrix();
			renderPartTurnLeft(entityMGM,childpart);
			GL11.glPopMatrix();
		}
	}
	public void renderPartTurnRight(EntityMGAX55 entityMGM, HMGParts hmgParts){

		HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation = hmgParts.getRenderinfOfDef();
		HMGGunParts_Motion_PosAndRotation OffsetAndRotation = hmgParts.getleftmotion(entityMGM.legStateTurnRight);
		if(OffsetAndRotation != null) {
			GL11.glTranslatef(OffsetAndRotation.posX, OffsetAndRotation.posY, OffsetAndRotation.posZ);
			GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
			GL11.glRotatef(OffsetAndRotation.rotationY, 0, 1, 0);
			GL11.glRotatef(OffsetAndRotation.rotationX, 1, 0, 0);
			GL11.glRotatef(OffsetAndRotation.rotationZ, 0, 0, 1);
			GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
		}else {
			GL11.glTranslatef(rotationCenterAndRotation.posX,rotationCenterAndRotation.posY,rotationCenterAndRotation.posZ);
			GL11.glRotatef(rotationCenterAndRotation.rotationY,0,1,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationX,1,0,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationZ,0,0,1);
			GL11.glTranslatef(-rotationCenterAndRotation.posX,-rotationCenterAndRotation.posY,-rotationCenterAndRotation.posZ);
		}
		model.renderPart(hmgParts.partsname);
		for(HMGParts childpart:hmgParts.childs){
			GL11.glPushMatrix();
			renderPartTurnRight(entityMGM,childpart);
			GL11.glPopMatrix();
		}
	}
	public void renderPartBack(EntityMGAX55 entityMGM, HMGParts hmgParts){

		HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation = hmgParts.getRenderinfOfDef();
		HMGGunParts_Motion_PosAndRotation OffsetAndRotation = hmgParts.getbackmotion(entityMGM.legStateBack);
		if(OffsetAndRotation != null) {
			GL11.glTranslatef(OffsetAndRotation.posX, OffsetAndRotation.posY, OffsetAndRotation.posZ);
			GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
			GL11.glRotatef(OffsetAndRotation.rotationY, 0, 1, 0);
			GL11.glRotatef(OffsetAndRotation.rotationX, 1, 0, 0);
			GL11.glRotatef(OffsetAndRotation.rotationZ, 0, 0, 1);
			GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
		}else {
			GL11.glTranslatef(rotationCenterAndRotation.posX,rotationCenterAndRotation.posY,rotationCenterAndRotation.posZ);
			GL11.glRotatef(rotationCenterAndRotation.rotationY,0,1,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationX,1,0,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationZ,0,0,1);
			GL11.glTranslatef(-rotationCenterAndRotation.posX,-rotationCenterAndRotation.posY,-rotationCenterAndRotation.posZ);
		}
		model.renderPart(hmgParts.partsname);
		for(HMGParts childpart:hmgParts.childs){
			GL11.glPushMatrix();
			renderPartBack(entityMGM,childpart);
			GL11.glPopMatrix();
		}
	}
	public void renderPartJumping(EntityMGAX55 entityMGM, HMGParts hmgParts){

		HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation = hmgParts.getRenderinfOfDef();
		HMGGunParts_Motion_PosAndRotation OffsetAndRotation = hmgParts.getRenderinfOfJumping();
		if(OffsetAndRotation != null) {
			GL11.glTranslatef(OffsetAndRotation.posX, OffsetAndRotation.posY, OffsetAndRotation.posZ);
			GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
			GL11.glRotatef(OffsetAndRotation.rotationY, 0, 1, 0);
			GL11.glRotatef(OffsetAndRotation.rotationX, 1, 0, 0);
			GL11.glRotatef(OffsetAndRotation.rotationZ, 0, 0, 1);
			GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
		}else {
			GL11.glTranslatef(rotationCenterAndRotation.posX,rotationCenterAndRotation.posY,rotationCenterAndRotation.posZ);
			GL11.glRotatef(rotationCenterAndRotation.rotationY,0,1,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationX,1,0,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationZ,0,0,1);
			GL11.glTranslatef(-rotationCenterAndRotation.posX,-rotationCenterAndRotation.posY,-rotationCenterAndRotation.posZ);
		}
		model.renderPart(hmgParts.partsname);
		for(HMGParts childpart:hmgParts.childs){
			GL11.glPushMatrix();
			renderPartJumping(entityMGM,childpart);
			GL11.glPopMatrix();
		}
	}
	public void renderPartJumped(EntityMGAX55 entityMGM, HMGParts hmgParts){

		HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation = hmgParts.getRenderinfOfDef();
		HMGGunParts_Motion_PosAndRotation OffsetAndRotation = hmgParts.getjumpedmotion(entityMGM.legStatejumped);
		if(OffsetAndRotation != null) {
			GL11.glTranslatef(OffsetAndRotation.posX, OffsetAndRotation.posY, OffsetAndRotation.posZ);
			GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
			GL11.glRotatef(OffsetAndRotation.rotationY, 0, 1, 0);
			GL11.glRotatef(OffsetAndRotation.rotationX, 1, 0, 0);
			GL11.glRotatef(OffsetAndRotation.rotationZ, 0, 0, 1);
			GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
		}else {
			GL11.glTranslatef(rotationCenterAndRotation.posX,rotationCenterAndRotation.posY,rotationCenterAndRotation.posZ);
			GL11.glRotatef(rotationCenterAndRotation.rotationY,0,1,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationX,1,0,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationZ,0,0,1);
			GL11.glTranslatef(-rotationCenterAndRotation.posX,-rotationCenterAndRotation.posY,-rotationCenterAndRotation.posZ);
		}
		model.renderPart(hmgParts.partsname);
		for(HMGParts childpart:hmgParts.childs){
			GL11.glPushMatrix();
			renderPartJumped(entityMGM,childpart);
			GL11.glPopMatrix();
		}
	}
	public void renderPartSneak(EntityMGAX55 entityMGM, HMGParts hmgParts){

		HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation = hmgParts.getRenderinfOfDef();
		HMGGunParts_Motion_PosAndRotation OffsetAndRotation = hmgParts.getsneakingmotion(entityMGM.legSneak_State_Progress);
		if(OffsetAndRotation != null) {
			GL11.glTranslatef(OffsetAndRotation.posX, OffsetAndRotation.posY, OffsetAndRotation.posZ);
			GL11.glTranslatef(rotationCenterAndRotation.posX, rotationCenterAndRotation.posY, rotationCenterAndRotation.posZ);
			GL11.glRotatef(OffsetAndRotation.rotationY, 0, 1, 0);
			GL11.glRotatef(OffsetAndRotation.rotationX, 1, 0, 0);
			GL11.glRotatef(OffsetAndRotation.rotationZ, 0, 0, 1);
			GL11.glTranslatef(-rotationCenterAndRotation.posX, -rotationCenterAndRotation.posY, -rotationCenterAndRotation.posZ);
		}else {
			GL11.glTranslatef(rotationCenterAndRotation.posX,rotationCenterAndRotation.posY,rotationCenterAndRotation.posZ);
			GL11.glRotatef(rotationCenterAndRotation.rotationY,0,1,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationX,1,0,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationZ,0,0,1);
			GL11.glTranslatef(-rotationCenterAndRotation.posX,-rotationCenterAndRotation.posY,-rotationCenterAndRotation.posZ);
		}
		model.renderPart(hmgParts.partsname);
		for(HMGParts childpart:hmgParts.childs){
			GL11.glPushMatrix();
			renderPartSneak(entityMGM,childpart);
			GL11.glPopMatrix();
		}
	}
	public void renderPart(EntityMGAX55 entityMGM, HMGParts hmgParts){

		HMGGunParts_Motion_PosAndRotation rotationCenterAndRotation = hmgParts.getRenderinfOfDef();
		{
			GL11.glTranslatef(rotationCenterAndRotation.posX,rotationCenterAndRotation.posY,rotationCenterAndRotation.posZ);
			GL11.glRotatef(rotationCenterAndRotation.rotationY,0,1,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationX,1,0,0);
			GL11.glRotatef(rotationCenterAndRotation.rotationZ,0,0,1);
			GL11.glTranslatef(-rotationCenterAndRotation.posX,-rotationCenterAndRotation.posY,-rotationCenterAndRotation.posZ);
		}
		model.renderPart(hmgParts.partsname);
		for(HMGParts childpart:hmgParts.childs){
			GL11.glPushMatrix();
			renderPart(entityMGM,childpart);
			GL11.glPopMatrix();
		}
	}
	public void AddMotionKeyonForward(int   startflame,
									  float startcenterX,
									  float startcenterY,
									  float startcenterZ,
									  float startrotationX,
									  float startrotationY,
									  float startrotationZ,
									  int   endflame,
									  float endcenterX,
									  float endcenterY,
									  float endcenterZ,
									  float endrotationX,
									  float endrotationY,
									  float endrotationZ){
		HMGGunParts_Motion motion = new HMGGunParts_Motion();
		motion.startflame =         startflame;
		motion.startposX =          startcenterX;
		motion.startposY =          startcenterY;
		motion.startposZ =          startcenterZ;
		motion.startrotationX =     startrotationX;
		motion.startrotationY =     startrotationY;
		motion.startrotationZ =     startrotationZ;
		motion.endflame =         endflame;
		motion.endposX =          endcenterX;
		motion.endposY =          endcenterY;
		motion.endposZ =          endcenterZ;
		motion.endrotationX =     endrotationX;
		motion.endrotationY =     endrotationY;
		motion.endrotationZ =     endrotationZ;
		motion.setup();
		onforwardmotions.addmotion(motion);
	}
	public void AddMotionKeyonJumped(int   startflame,
									  float startcenterX,
									  float startcenterY,
									  float startcenterZ,
									  float startrotationX,
									  float startrotationY,
									  float startrotationZ,
									  int   endflame,
									  float endcenterX,
									  float endcenterY,
									  float endcenterZ,
									  float endrotationX,
									  float endrotationY,
									  float endrotationZ){
		HMGGunParts_Motion motion = new HMGGunParts_Motion();
		motion.startflame =         startflame;
		motion.startposX =          startcenterX;
		motion.startposY =          startcenterY;
		motion.startposZ =          startcenterZ;
		motion.startrotationX =     startrotationX;
		motion.startrotationY =     startrotationY;
		motion.startrotationZ =     startrotationZ;
		motion.endflame =         endflame;
		motion.endposX =          endcenterX;
		motion.endposY =          endcenterY;
		motion.endposZ =          endcenterZ;
		motion.endrotationX =     endrotationX;
		motion.endrotationY =     endrotationY;
		motion.endrotationZ =     endrotationZ;
		motion.setup();
		onjumpedmotions.addmotion(motion);
	}
	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving) {
		return this.skeletonTexturesz;
	}

}
