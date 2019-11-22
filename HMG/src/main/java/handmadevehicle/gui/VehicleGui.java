package handmadevehicle.gui;

import handmadeguns.gui.HMGContainerInventoryItem;
import handmadevehicle.gui.container.VehicleContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class VehicleGui extends GuiContainer {
	private static final ResourceLocation field_147017_u = new ResourceLocation("handmadeguns:textures/gui/guitest.png");
	VehicleContainer containerInventoryItem;
	public VehicleGui(VehicleContainer p_i1072_1_) {
		super(p_i1072_1_);
		containerInventoryItem = p_i1072_1_;
		this.ySize = 222;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(field_147017_u);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, 18);//è„ïîï™ï`âÊ
		int y;
		for(y = 0;y < containerInventoryItem.numRows + 8;y++)
			this.drawTexturedModalRect(k, l + y * 18, 0, 4, this.xSize, 18);//ì‡ïîîwåi

		this.drawTexturedModalRect(k, l + y * 18 + 18, 0, 162, this.xSize, 18);//â∫í[

		for(Object obj : containerInventoryItem.inventorySlots){
			Slot slot = (Slot) obj;
			this.drawTexturedModalRect(k + slot.xDisplayPosition-1, l + slot.yDisplayPosition-1,
					7, 83, 18, 18);
		}

	}
}
