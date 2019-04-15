package handmadeguns.items;

public class GunSightInfo {
	public boolean rendercross;
	public boolean rendeMCCrcross;
	public  String[] adstexture = new String[]{"handmadeguns:handmadeguns/textures/misc/ironsight","handmadeguns:handmadeguns.textures.misc.reddot","handmadeguns:handmadeguns.textures.misc.scope"};
	
	public float scopezoom[] = new float[]{1,1,1};
//	public float scopezoombase;
//	public float scopezoomred;
//	public float scopezoomscope;
	
	public boolean[] zoomRender = new boolean[]{true,true,false};
	public boolean[] zoomRenderTexture = new boolean[]{false,true,false};
	
	public float sightattachoffset[] = new float[3];
	public boolean[] hasNightVision = new boolean[]{false,false,false};
	
	public double sightPosN[] = new double[3];
	public double sightPosR[] = new double[3];
	public double sightPosS[] = new double[3];
//	public boolean zoomren = false;
//	public boolean zoomrer = false;
//	public boolean zoomres = false;
//
//	public boolean zoomrent = false;
//	public boolean zoomrert = false;
//	public boolean zoomrest = false;
}
