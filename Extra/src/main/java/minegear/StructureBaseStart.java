package minegear;

import DungeonGeneratorBase.ComponentDungeonBase;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;

import java.util.Random;


public class StructureBaseStart extends StructureStart {


	boolean east = false;
	boolean west = false;
	boolean noth = false;
	boolean sout = false;


	public StructureBaseStart() {
		super();
	}

	Random rnd;

	boolean course;//true X:false Z
	int length;
	public StructureBaseStart(World par1World, Random par2Random, int par3, int par4) {
		super(par3, par4);
		rnd =par2Random;
		course = rnd.nextBoolean();
//		System.out.println("x = " + par3*16 + " , z = " + par4*16 + " , length" + length + " , course " + (course?"X":"Z"));
		components.add(new ComponentDungeonSmallBase(rnd,par3*16,par4*16));

		this.updateBoundingBox();
	}
}
