package hmgww2.render.tile;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import hmgww2.mod_GVCWW2;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderTileFlag implements ISimpleBlockRenderingHandler {
	/**インベントリ内でブロックをレンダリングするメソッド。もしshouldRender3DInInventoryがfalseなら空でもいいかも。**/
	
	
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/blocks/tile/flag.obj"));
	 private ResourceLocation texture = new ResourceLocation( "hmgww2:textures/blocks/tile/flag_jpn.png");
	 protected TileEntityRendererDispatcher field_147501_a;
	 
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		if (modelId == this.getRenderId()) {
			float f = 0;
			f = this.renderAlumiAnvil(block, renderer, metadata, f, 0.75F, 0.25F, 0.75F, false);
			f = this.renderAlumiAnvil(block, renderer, metadata, f, 0.5F, 0.0625F, 0.625F, false);
			f = this.renderAlumiAnvil(block, renderer, metadata, f, 0.25F, 0.3125F, 0.5F, false);
			this.renderAlumiAnvil(block, renderer, metadata, f, 0.625F, 0.375F, 1.0F, false);
		}
	}

	/**ワールド内でブロックをレンダリングするメソッド**/
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
			RenderBlocks renderer) {
		if (modelId == this.getRenderId()) {
			/*レンダリングする立方体のサイズを決めるメソッド。
			* setRenderBounds(始点X, 始点Y, 始点Z, 終点X, 終点Y, 終点Z)*/
			renderer.setRenderBounds(0.1D, 0.0D, 0.1D, 0.9D, 0.2D, 0.9D);
			/*レンダリングするメソッド。
			* renderStandardBlock(block, x, y, z);*/
			renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0.25D, 0.2D, 0.25D, 0.75D, 0.3D, 0.75D);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0.3D, 0.3D, 0.2D, 0.7D, 0.6D, 0.8D);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0.2D, 0.6D, 0.0D, 0.8D, 1.0D, 1.0D);
			renderer.renderStandardBlock(block, x, y, z);
			
			Minecraft minecraft = Minecraft.getMinecraft();
	    	
	        GL11.glPushMatrix();
	        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	        GL11.glTranslatef((float)x + 0.5F, (float)y + 0F, (float)z + 0.5F);
	       // GL11.glScalef(1.0F, -1.0F, -1.0F);
	        //GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
	        //GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
	        //this.bindTexture(texture);
	        TextureManager texturemanager = field_147501_a.field_147553_e;

	        if (texturemanager != null)
	        {
	            texturemanager.bindTexture(texture);
	        }
	        tankk.renderPart("mat1");
	        /*if(p_147500_1_.spawn){
	        	tankk.renderPart("mat3");
	        }*/
	       // GL11.glTranslatef(0, -(p_147500_1_.getInvasionSet()*0.005F), 0);
	        tankk.renderPart("mat2");
	        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	        GL11.glPopMatrix();
			
			return true;
		}
		return false;
	}

	@Override
	/**インベントリ内で3Dレンダリングするか否かを返すメソッド**/
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	/**自身のレンダーIDを返すメソッド**/
	@Override
	public int getRenderId() {
		return mod_GVCWW2.bi_flag_jpn_id;
	}

	protected float renderAlumiAnvil(Block block, RenderBlocks renderer, int metadata, float sides1, float sides2,
			float sides3, float sides4, boolean flg) {
		
		
		/**詳しい説明はカット。ここは難しいため、基本的にはshouldRender3DInInventoryをfalseにすることをお勧めする。**/
		if (flg) {
			float f = sides2;
			sides2 = sides4;
			sides4 = f;
		}
		sides2 /= 2.0F;
		sides4 /= 2.0F;
		renderer.setRenderBounds((double) (0.5F - sides2), (double) sides1, (double) (0.5F - sides4),
				(double) (0.5F + sides2), (double) (sides1 + sides3), (double) (0.5F + sides4));
		Tessellator tessellator = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		return sides1 + sides3;
	}
}