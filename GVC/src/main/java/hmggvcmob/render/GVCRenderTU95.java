package hmggvcmob.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GVCRenderTU95 extends Render {

    private static final ResourceLocation texture = new ResourceLocation("gvcmob:textures/model/Tu-95.png");
    private static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("gvcmob:textures/model/Tu-95.mqo"));
    float[][] proppos = new float[4][3];
    public GVCRenderTU95(){
        proppos[0][0] = 187.03f;
        proppos[0][1] = 1.00f;
        proppos[0][2] = 31.5f;

        proppos[1][0] = 97.64f;
        proppos[1][1] = -0.75f;
        proppos[1][2] = 80.68f;

        proppos[2][0] = -97.64f;
        proppos[2][1] = -0.75f;
        proppos[2][2] = 80.68f;

        proppos[3][0] = -187.03f;
        proppos[3][1] = 1.00f;
        proppos[3][2] = 31.5f;
    }
    @Override
    public void doRender(Entity entity, double posX, double posY, double posZ, float yaw, float partialTicks) {
        this.bindEntityTexture(entity);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) posX, (float) posY, (float) posZ);
        GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);

        GL11.glScalef(6.689189189189189f,6.689189189189189f,6.689189189189189f);
        GL11.glScalef(2,2,2);
        GL11.glRotatef((entity.rotationYaw +(entity.rotationYaw - entity.prevRotationYaw) * partialTicks), 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180.0F - (entity.rotationPitch +(entity.rotationPitch - entity.prevRotationPitch) * partialTicks), 0.0F, 1.0F, 0.0F);

        GL11.glShadeModel(GL11.GL_SMOOTH);

        model.renderPart("body");
        model.renderPart("canopi");

        for(int id = 0;id < 4;id++) {
            GL11.glPushMatrix();
            model.renderPart("prop" + (id+1));
            GL11.glTranslatef(proppos[id][0]/100, proppos[id][1]/100, proppos[id][2]/100);
            GL11.glRotatef(360 * partialTicks,0,0,1);
            GL11.glTranslatef(-proppos[id][0]/100, -proppos[id][1]/100, -proppos[id][2]/100);
            model.renderPart("prop" + (id+1) + "right");
            GL11.glTranslatef(proppos[id][0]/100, proppos[id][1]/100, proppos[id][2]/100);
            GL11.glRotatef(360 * -partialTicks*2,0,0,1);
            GL11.glTranslatef(-proppos[id][0]/100, -proppos[id][1]/100, -proppos[id][2]/100);
            model.renderPart("prop" + (id+1) + "left");
            GL11.glPopMatrix();
        }

        GL11.glPopMatrix();



    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return texture;
    }
}
