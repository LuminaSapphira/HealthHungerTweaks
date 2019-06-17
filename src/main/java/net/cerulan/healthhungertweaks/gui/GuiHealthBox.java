package net.cerulan.healthhungertweaks.gui;

import java.io.IOException;
import java.util.ArrayList;

import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.cerulan.healthhungertweaks.ModInfo;
import net.cerulan.healthhungertweaks.capability.healthbox.HealthBoxCapabilityHandler;
import net.cerulan.healthhungertweaks.network.HealthHungerPacketHandler;
import net.cerulan.healthhungertweaks.network.MessageUseHealthKit;
import net.cerulan.healthhungertweaks.network.MessageWithdrawKits;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class GuiHealthBox extends GuiContainer {
	
	private static final ResourceLocation texture = new ResourceLocation(ModInfo.MODID, "textures/gui/container/guihealthbox.png");
	// TODO clean
	//private static final ResourceLocation k1 = new ResourceLocation(ModInfo.MODID, "textures/items/healthkit_primitive.png");
	//private static final ResourceLocation k2 = new ResourceLocation(ModInfo.MODID, "textures/items/healthkit_basic.png");
	private static final ResourceLocation k3 = new ResourceLocation(ModInfo.MODID, "textures/items/healthkit_standard.png");
	
	private TextComponentTranslation titleText;
	
	EntityPlayer player;
	public GuiHealthBox(EntityPlayer player) {		
		super(new ContainerHealthBox(player));		
		this.xSize = 176;
		this.ySize = 195;
		this.player = player;
		this.titleText = new TextComponentTranslation("container.healthbox");
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.drawDefaultBackground();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        //GlStateManager.scale(2f, 2f, 2f);
        //GL11.glScalef(2f, 2f, 2f);        
        //this.zLevel = 1f;
        
        //this.
        //this.drawTexturedModalRect(20, 20, 0, 0, 16, 16);
	}
	
	int k1x, k2x, k3x;
	int ky;
	int kits;
	ArrayList<String> strs = new ArrayList<String>();
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		strs.clear();
		kits = this.player.getCapability(HealthBoxCapabilityHandler.HEALTH_BOX, null).getHealthKitCount();			
		
		int wid3 = this.xSize / 3;
		k1x = (wid3 - 32) / 2;
		k2x = wid3 + (wid3 - 32) / 2;
		k3x = 2 * wid3 + (wid3 - 32) / 2;
		
		ky = 60;
		
		k1x++;
		k2x++;
		k3x++;
		//TODO clean
		/*
		this.mc.getTextureManager().bindTexture(k1);		
		drawModalRectWithCustomSizedTexture(k1x, ky, 0, 0, 32, 32, 32f, 32f);*/
		
		this.mc.getTextureManager().bindTexture(k3);		
		drawModalRectWithCustomSizedTexture(k2x, ky, 0, 0, 32, 32, 32f, 32f);
		
		//TODO clean
		/*this.mc.getTextureManager().bindTexture(k3);		
		drawModalRectWithCustomSizedTexture(k3x, ky, 0, 0, 32, 32, 32f, 32f);*/

		/*boolean unicodeFlag = this.fontRendererObj.getUnicodeFlag();
		this.fontRendererObj.setUnicodeFlag(true);*/
		
		//int fk1x = (wid3 - this.fontRenderer.getStringWidth(String.valueOf(kits[0]))) / 2;
		int fk2x = wid3 + (wid3 - this.fontRenderer.getStringWidth(String.valueOf(kits))) / 2;
		//int fk3x = 2 * wid3 + (wid3 - this.fontRenderer.getStringWidth(String.valueOf(kits[2]))) / 2;
		
		//fk1x++;
		fk2x++;
		//fk3x++;		
		//this.fontRenderer.drawString(String.valueOf(kits[0]), fk1x, 92f, 0, false);
		this.fontRenderer.drawString(String.valueOf(kits), fk2x, 92f, 0, false);
		//this.fontRenderer.drawString(String.valueOf(kits[2]), fk3x, 92f, 0, false);
		
		int titleX = this.fontRenderer.getStringWidth(titleText.getFormattedText());
		this.fontRenderer.drawString(titleText.getFormattedText(), (this.xSize - titleX) / 2, 8, 0);	
		
		// Draw tooltip
		
		int mx = mouseX - this.guiLeft;
		int my = mouseY - this.guiTop;
		if (getKitAtPoint(mx, my)) { 
			ITextComponent tool1 = new TextComponentString(new TextComponentTranslation("container.healthbox.tooltip1").getFormattedText()
					.replace("${item}", new TextComponentTranslation(String.format("item.health_kit_regen.name")).getFormattedText()));
			tool1.getStyle().setColor(TextFormatting.AQUA);
			
			ITextComponent tool2 = new TextComponentString(new TextComponentTranslation("container.healthbox.tooltip2").getFormattedText()
					.replace("${amount}", String.format("%1$d", kits)));
			tool2.getStyle().setColor(TextFormatting.GRAY);
			
			ITextComponent tool3 = new TextComponentString(new TextComponentTranslation("container.healthbox.tooltip3").getFormattedText()
					.replace("${removeamt}", String.format("%1$d", getRemoveAmountForKit(kits))));
			tool3.getStyle().setColor(TextFormatting.GRAY);
			
			ITextComponent tool4 = new TextComponentTranslation("container.healthbox.tooltip4");
			tool4.getStyle().setColor(TextFormatting.GRAY);
			
			strs.add(tool1.getFormattedText());
			strs.add(tool2.getFormattedText());
			strs.add(tool3.getFormattedText());
			strs.add(tool4.getFormattedText());
			
			this.drawHoveringText(strs, mx, my);
		}
		
		//this.fontRendererObj.setUnicodeFlag(unicodeFlag);
	}
	
	/**
	 * Gets how many kits to remove clamped to the available number of kits
	 * @param availableKits
	 * @return
	 */
	private int getRemoveAmountForKit(int availableKits) {
		int amount = 1;
		if (isShiftKeyDown()) {
			amount = 64;
		}
		else if (isCtrlKeyDown()) {
			amount = 10;
		}
		return MathHelper.clamp(amount, 0, availableKits);
	}
	
	/**
	 * Gets whether the cursor is hovering over a kit
	 * @param mxGui
	 * @param myGui
	 * @return
	 */
	private boolean getKitAtPoint(int mxGui, int myGui) {
		return (mxGui >= k2x && mxGui <= k2x + 32 && myGui >= ky && myGui <= ky + 32);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		int mx = mouseX - this.guiLeft, my = mouseY - this.guiTop;		
		if (mouseButton == 0) {
			if (getKitAtPoint(mx, my)) {
				int rmv = getRemoveAmountForKit(kits);
				if (rmv > 0) {
					HealthHungerTweaks.Log.info(String.format("Requesting to withdraw %1$d kits", rmv));
					HealthHungerPacketHandler.INSTANCE.sendToServer(new MessageWithdrawKits(rmv));
				}
			}
		}
		
		if (mouseButton == 1) {
			if (getKitAtPoint(mx, my)) {
				HealthHungerTweaks.Log.info(String.format("Requesting to use health kit"));
				HealthHungerPacketHandler.INSTANCE.sendToServer(new MessageUseHealthKit());
			}
		}
	}

}
