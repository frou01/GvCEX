package hmggvcmob.render.model;

import hmggvcmob.entity.guerrilla.GVCEntityTank;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

@SideOnly(Side.CLIENT)
public class GVCModelTankTest extends ModelBase
{
    private ModelRenderer batHead;
    /** The body box of the bat model. */
    private ModelRenderer batBody;
    /** The inner right wing box of the bat model. */
    private ModelRenderer batRightWing;
    /** The inner left wing box of the bat model. */
    private ModelRenderer batLeftWing;
    /** The outer right wing box of the bat model. */
    private ModelRenderer batOuterRightWing;
    /** The outer left wing box of the bat model. */
    private ModelRenderer batOuterLeftWing;
    private static final String __OBFID = "CL_00000830";
    private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("gvcmob:textures/test/MerkavaMk4.obj"));

    public GVCModelTankTest(IModelCustom par1)
    {
         GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        
        
         par1.renderAll();
 		//Minecraft.getMinecraft().renderEngine.bindTexture(skeletonTexturesz);
 		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
    }

    

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
    {
    	GVCEntityTank entitybat = (GVCEntityTank)p_78088_1_;
        float f6;

        /*GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        
        
        tankk.renderAll();
		//Minecraft.getMinecraft().renderEngine.bindTexture(skeletonTexturesz);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        */
    }
}