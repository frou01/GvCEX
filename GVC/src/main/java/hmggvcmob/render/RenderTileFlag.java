package hmggvcmob.render;

import hmggvcmob.tile.TileEntityFlag;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

import static java.lang.StrictMath.atan;
import static java.lang.StrictMath.atan2;
import static java.lang.StrictMath.toDegrees;

public class RenderTileFlag extends TileEntitySpecialRenderer {
    
    private static final IModelCustom flagmodel = AdvancedModelLoader.loadModel(new ResourceLocation("gvcmob:textures/model/flagmodel.mqo"));
    public void renderTileEntityAt(TileEntityFlag tileEntity, double v, double v1, double v2, float v3) {
        this.bindEntityTexture(tileEntity);
        GL11.glDepthMask(true);
        int i = tileEntity.getWorldObj().getLightBrightnessForSkyBlocks(tileEntity.xCoord, tileEntity.yCoord+1, tileEntity.zCoord, 0);
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j / 1.0F, (float)k / 1.0F);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)v+0.5f, (float)v1+1f, (float)v2+0.5f);
        GL11.glScalef(4,4,4);
        GL11.glRotatef((float) toDegrees(atan2(tileEntity.xCoord,tileEntity.zCoord))+90,0,1,0);
        flagmodel.renderPart("obj2");
        flagmodel.renderPart("obj5");
        GL11.glTranslatef(0,0.20f,0);
        GL11.glRotatef(-tileEntity.flagHeight*0.003f,0,0,1);
        GL11.glTranslatef(0,-0.20f,0);
        flagmodel.renderPart("obj4");
        GL11.glPopMatrix();
    
        
    
    
    }

    private void bindEntityTexture(TileEntityFlag tileEntity) {
        TextureManager texturemanager = this.field_147501_a.field_147553_e;
        texturemanager.bindTexture(tileEntity.flagtexture);
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double v, double v1, double v2, float v3) {
        this.renderTileEntityAt((TileEntityFlag)tileEntity,v,v1,v2,v3);
    }
}
