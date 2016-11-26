package net.cerulan.healthhungertweaks.gui;

import java.io.IOException;
import java.util.ArrayList;

import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.cerulan.healthhungertweaks.ModInfo;
import net.cerulan.healthhungertweaks.capability.HealthBoxCapabilityHandler;
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
	private static final ResourceLocation k1 = new ResourceLocation(ModInfo.MODID, "textures/items/healthkit_primitive.png");
	private static final ResourceLocation k2 = new ResourceLocation(ModInfo.MODID, "textures/items/healthkit_basic.png");
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
	int[] kits;
	ArrayList<String> strs = new ArrayList<String>();
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		strs.clear();
		kits = this.player.getCapability(HealthBoxCapabilityHandler.HEALTH_BOX, null).getHealthKits();			
		
		int wid3 = this.xSize / 3;
		k1x = (wid3 - 32) / 2;
		k2x = wid3 + (wid3 - 32) / 2;
		k3x = 2 * wid3 + (wid3 - 32) / 2;
		
		ky = 60;
		
		k1x++;
		k2x++;
		k3x++;
		
		this.mc.getTextureManager().bindTexture(k1);		
		drawModalRectWithCustomSizedTexture(k1x, ky, 0, 0, 32, 32, 32f, 32f);
		
		this.mc.getTextureManager().bindTexture(k2);		
		drawModalRectWithCustomSizedTexture(k2x, ky, 0, 0, 32, 32, 32f, 32f);
		
		this.mc.getTextureManager().bindTexture(k3);		
		drawModalRectWithCustomSizedTexture(k3x, ky, 0, 0, 32, 32, 32f, 32f);

		/*boolean unicodeFlag = this.fontRendererObj.getUnicodeFlag();
		this.fontRendererObj.setUnicodeFlag(true);*/
		
		int fk1x = (wid3 - this.fontRendererObj.getStringWidth(String.valueOf(kits[0]))) / 2;
		int fk2x = wid3 + (wid3 - this.fontRendererObj.getStringWidth(String.valueOf(kits[1]))) / 2;
		int fk3x = 2 * wid3 + (wid3 - this.fontRendererObj.getStringWidth(String.valueOf(kits[2]))) / 2;
		
		fk1x++;
		fk2x++;
		fk3x++;		
		
		this.fontRendererObj.drawString(String.valueOf(kits[0]), fk1x, 92f, 0, false);
		this.fontRendererObj.drawString(String.valueOf(kits[1]), fk2x, 92f, 0, false);
		this.fontRendererObj.drawString(String.valueOf(kits[2]), fk3x, 92f, 0, false);
		
		int titleX = this.fontRendererObj.getStringWidth(titleText.getFormattedText());
		this.fontRendererObj.drawString(titleText.getFormattedText(), (this.xSize - titleX) / 2, 8, 0);	
		
		// Draw tooltip
		
		int mx = mouseX - this.guiLeft;
		int my = mouseY - this.guiTop;
		int kit = getKitAtPoint(mx, my);
		if (kit != -1) { 
			ITextComponent tool1 = new TextComponentString(new TextComponentTranslation("container.healthbox.tooltip1").getFormattedText()
					.replace("${item}", new TextComponentTranslation(String.format("item.healthKit.%1$d.name", kit)).getFormattedText()));
			tool1.getStyle().setColor(TextFormatting.AQUA);
			
			ITextComponent tool2 = new TextComponentString(new TextComponentTranslation("container.healthbox.tooltip2").getFormattedText()
					.replace("${amount}", String.format("%1$d", kits[kit])));
			tool2.getStyle().setColor(TextFormatting.GRAY);
			
			ITextComponent tool3 = new TextComponentString(new TextComponentTranslation("container.healthbox.tooltip3").getFormattedText()
					.replace("${removeamt}", String.format("%1$d", getRemoveAmountForKit(kits[kit]))));
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
	
	private int getRemoveAmountForKit(int amountForKit) {
		int amount = 1;
		if (isShiftKeyDown()) {
			amount = 64;
		}
		else if (isCtrlKeyDown()) {
			amount = 10;
		}
		return MathHelper.clamp_int(amount, 0, amountForKit);
	}
	
	private int getKitAtPoint(int mxGui, int myGui) {
		if (mxGui >= k1x && mxGui <= k1x + 32 && myGui >= ky && myGui <= ky + 32) {
			return 0;
		}
		else if (mxGui >= k2x && mxGui <= k2x + 32 && myGui >= ky && myGui <= ky + 32) {
			return 1;
		}
		else if (mxGui >= k3x && mxGui <= k3x + 32 && myGui >= ky && myGui <= ky + 32) {
			return 2;
		}
		else {
			return -1;
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		int mx = mouseX - this.guiLeft, my = mouseY - this.guiTop;		
		if (mouseButton == 0) {
			int kit = getKitAtPoint(mx, my);
			if (kit != -1) {
				int rmv = getRemoveAmountForKit(kits[kit]);
				if (rmv > 0) {
					HealthHungerTweaks.Log.info(String.format("Requesting to withdraw %1$d from kit %2$d", rmv, kit));
					HealthHungerPacketHandler.INSTANCE.sendToServer(new MessageWithdrawKits(kit, rmv));
				}
			}
		}
		
		if (mouseButton == 1) {
			int kit = getKitAtPoint(mx, my);
			if (kit != -1) {
				HealthHungerTweaks.Log.info(String.format("Requesting to use kit %1$d", kit));
				HealthHungerPacketHandler.INSTANCE.sendToServer(new MessageUseHealthKit(kit));
			}
		}
	}

}
