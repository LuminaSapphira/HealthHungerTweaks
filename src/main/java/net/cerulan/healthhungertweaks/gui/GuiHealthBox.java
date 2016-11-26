package net.cerulan.healthhungertweaks.gui;

import net.cerulan.healthhungertweaks.ModInfo;
import net.cerulan.healthhungertweaks.capability.HealthBoxCapabilityHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;

public class GuiHealthBox extends GuiContainer {
	
	private static final ResourceLocation texture = new ResourceLocation(ModInfo.MODID, "textures/gui/container/guihealthbox.png");
	
	EntityPlayer player;
	public GuiHealthBox(EntityPlayer player) {		
		super(new ContainerHealthBox(player));
		this.ySize=166;
		this.player = player;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		/*GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(FURNACE_GUI_TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        int l = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16);*/
		
		this.itemRender.renderItemAndEffectIntoGUI(stack, xPosition, yPosition);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRendererObj.drawString(String.valueOf(this.player.getCapability(HealthBoxCapabilityHandler.HEALTH_BOX, null).getHealthKits()[0]), 10f, 10f, 0, false);
	}

}
