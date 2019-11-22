package hmggvcmob.render;

import cpw.mods.fml.client.FMLClientHandler;
import handmadeguns.client.render.ModelSetAndData;
import hmggvcmob.tile.TileEntityFlag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

import static handmadeguns.HandmadeGunsCore.textureOffsetU;
import static java.lang.StrictMath.atan;
import static java.lang.StrictMath.atan2;
import static java.lang.StrictMath.toDegrees;

public class RenderTileFlag extends TileEntitySpecialRenderer {
    
    private IModelCustom flagmodel = AdvancedModelLoader.loadModel(new ResourceLocation("gvcmob:textures/model/flagmodel.mqo"));
    public void renderTileEntityAt(TileEntityFlag tileEntity, double v, double v1, double v2, float v3) {
        if(tileEntity.campObj != null) {
            if(tileEntity.campObj.modelSetAndData == null){
                tileEntity.campObj.modelSetAndData = new ModelSetAndData(tileEntity.campObj.campBlockObjModel,tileEntity.campObj.campBlockTextureModel);
            }
            flagmodel = tileEntity.campObj.modelSetAndData.model;
            this.bindEntityTexture(tileEntity);
            GL11.glDepthMask(true);
            GL11.glPushMatrix();
            GL11.glTranslatef((float) v + 0.5f, (float) v1 + 1f, (float) v2 + 0.5f);
            GL11.glScalef(4, 4, 4);
            GL11.glRotatef((float) toDegrees(atan2(tileEntity.xCoord, tileEntity.zCoord)) + 90, 0, 1, 0);
            flagmodel.renderPart("obj2");
            flagmodel.renderPart("obj4");
            flagmodel.renderPart("obj5");
            textureOffsetU = (float)(tileEntity.campObj.maxFlagHeight - tileEntity.flagHeight)/(float)tileEntity.campObj.maxFlagHeight * 0.156f;
            flagmodel.renderPart("obj5a");
            textureOffsetU = 0;
            GL11.glScalef(1f/4, 1f/4, 1f/4);
            GL11.glTranslatef(0.7f, 0.8f, 0.5f);
            GL11.glRotatef(-90, 0, 1, 0);
            GL11.glRotatef(180, 0, 0, 1);
            GL11.glScalef(1f/128, 1f/128, 1f/128);
            renderString("next Spawn : " + tileEntity.respawncycle/20);
            GL11.glTranslatef(0, -16, 0);
            renderString(tileEntity.campObj.campName);
            GL11.glPopMatrix();

        }
    
    
    }

    private void bindEntityTexture(TileEntityFlag tileEntity) {
        TextureManager texturemanager = this.field_147501_a.field_147553_e;
        texturemanager.bindTexture(tileEntity.campObj.modelSetAndData.texture);
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double v, double v1, double v2, float v3) {
        this.renderTileEntityAt((TileEntityFlag)tileEntity,v,v1,v2,v3);
    }

    public void renderString(String renderObj){

        Minecraft minecraft = FMLClientHandler.instance().getClient();
        FontRenderer fontrenderer = minecraft.fontRenderer;
        Tessellator tessellator = Tessellator.instance;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(false);
        int width = fontrenderer.getStringWidth(renderObj) / 2;
        GL11.glEnable(GL11.GL_BLEND);
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
        tessellator.addVertex((double)(-width - 1), -1.0D, 0.0D);
        tessellator.addVertex((double)(-width - 1), 8.0D, 0.0D);
        tessellator.addVertex((double)(width + 1), 8.0D, 0.0D);
        tessellator.addVertex((double)(width + 1), -1.0D, 0.0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        fontrenderer.drawString(renderObj,-width,0,0xFFFFFF);
    }
}
