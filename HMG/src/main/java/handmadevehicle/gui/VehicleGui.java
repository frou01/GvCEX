package handmadevehicle.gui;

import handmadeguns.gui.HMGContainerInventoryItem;
import handmadevehicle.gui.container.VehicleContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class VehicleGui extends GuiContainer {
	private static final ResourceLocation field_147017_u = new ResourceLocation("textures/gui/container/generic_54.png");
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
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, containerInventoryItem.numRows * 18 + 17);
		{
			this.drawTexturedModalRect(k, l + containerInventoryItem.numRows * 18 + 17, 0, 3, this.xSize, containerInventoryItem.numRows * 18 + 30);
			this.drawTexturedModalRect(k, l + containerInventoryItem.numRows * 18 + 31, 0, 3, this.xSize, containerInventoryItem.numRows * 18 + 35);
			this.drawTexturedModalRect(k, l + containerInventoryItem.numRows * 18 + 17, 0, 17, 7 + containerInventoryItem.inventoryVehicle.getSizeInventory()%9 * 18, containerInventoryItem.numRows * 18 + 35);
		}
		this.drawTexturedModalRect(k, l + (containerInventoryItem.numRows+ (containerInventoryItem.inventoryVehicle.getSizeInventory()%9 != 0?1:0)) * 18  + 17, 0, 126, this.xSize, 96);
	}
}
