package handmadeguns.client.render;

import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadeguns.tile.TileMounter;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderTileMounter extends TileEntitySpecialRenderer {
    private static final IModelCustom basemodel = AdvancedModelLoader.loadModel(new ResourceLocation("handmadeguns:textures/model/rack.obj"));
    private static final ResourceLocation basetexture = new ResourceLocation("textures/blocks/planks_oak.png");
    EntityItem entItem = null;
    public float[][] gunpos = new float[4][3];
    public RenderTileMounter(){
        gunpos[0][0]=0;
        gunpos[0][1]=0.6f;
        gunpos[0][2]=-0.3f;

        gunpos[1][0]=0;
        gunpos[1][1]=0.5f;
        gunpos[1][2]=0.0f;

        gunpos[2][0]=0;
        gunpos[2][1]=0.4f;
        gunpos[2][2]=0.3f;
    }
    public void renderTileEntityAt(TileMounter tileEntity, double x, double y, double z, float p_147500_8_) {

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        TextureManager texturemanager = this.field_147501_a.field_147553_e;
        texturemanager.bindTexture(basetexture);
        int i = tileEntity.getWorldObj().getLightBrightnessForSkyBlocks(tileEntity.xCoord, tileEntity.yCoord+1, tileEntity.zCoord, 0);
        int j = i % 65536;
        int k = i / 65536;
        float lastBrightnessX = OpenGlHelper.lastBrightnessX;
        float lastBrightnessY = OpenGlHelper.lastBrightnessY;
        GL11.glDepthMask(true);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j / 1.0F, (float)k / 1.0F);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x+0.5f, (float)y, (float)z+0.5f);
        i = tileEntity.getBlockMetadata();
        short short1 = 0;
//        System.out.println("debug " + i);
        if (i == 2)
        {
            short1 = 180;
        }

        if (i == 3)
        {
            short1 = 0;
        }

        if (i == 4)
        {
            short1 = -90;
        }

        if (i == 5)
        {
            short1 = 90;
        }
        GL11.glRotatef((float)short1, 0.0F, 1.0F, 0.0F);
        basemodel.renderPart("mat1");
        for(int id = 0;id < tileEntity.getSizeInventory();id++){
            ItemStack stack = tileEntity.getStackInSlot(id);
            if(stack != null) {
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                entItem = new EntityItem(tileEntity.getWorldObj(), x, y, z, stack);
                GL11.glPushMatrix();
                this.entItem.hoverStart = 0.0F;
                GL11.glTranslatef(gunpos[id][0], gunpos[id][1], gunpos[id][2]);
                if(stack.getItem() instanceof HMGItem_Unified_Guns)GL11.glRotatef(90,0,1,0);
                RenderManager.instance.renderEntityWithPosYaw(this.entItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                GL11.glPopMatrix();
            }
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }
        GL11.glPopMatrix();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
    }
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float p_147500_8_) {
        this.renderTileEntityAt((TileMounter) tileEntity,x,y,z,p_147500_8_);
    }
}
