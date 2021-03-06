package hmgww2.render.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hmgww2.blocks.tile.TileEntityBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class TileRenderFlag extends TileEntitySpecialRenderer {
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/blocks/tile/flag.obj"));
	private ResourceLocation texture[] = new ResourceLocation[]{new ResourceLocation("hmgww2:textures/blocks/tile/flag_usa.png"),
			new ResourceLocation("hmgww2:textures/blocks/tile/flag_rus.png"),
			new ResourceLocation("hmgww2:textures/blocks/tile/flag_ger.png"),
			new ResourceLocation("hmgww2:textures/blocks/tile/flag_jpn.png"),};
	private static final String __OBFID = "CL_00000965";

	public TileRenderFlag() {
	}

	public void renderTileEntityAt(TileEntityBase tileEntity, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
		try {

			GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glTranslatef((float) p_147500_2_ + 0.5F, (float) p_147500_4_ + 0F, (float) p_147500_6_ + 0.5F);
			// GL11.glScalef(1.0F, -1.0F, -1.0F);
			//GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
			//GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
			this.bindTexture(texture[tileEntity.nation.ordinal()]);
			tankk.renderPart("mat1");
			if (tileEntity.spawn) {
				tankk.renderPart("mat3");
			}
			GL11.glTranslatef(0, -(tileEntity.getInvasion() * 0.005F), 0);
			tankk.renderPart("mat2");
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
		this.renderTileEntityAt((TileEntityBase) p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
	}
}