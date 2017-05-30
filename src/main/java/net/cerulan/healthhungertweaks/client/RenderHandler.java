package net.cerulan.healthhungertweaks.client;

import java.awt.Color;

import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.cerulan.healthhungertweaks.ModInfo;
import net.cerulan.healthhungertweaks.capability.healthbox.HealthBoxCapabilityHandler;
import net.cerulan.healthhungertweaks.capability.healthbox.IHealthBoxCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderHandler {

	static final ResourceLocation healthRedOverlay = new ResourceLocation(ModInfo.MODID, "textures/misc/healthoverlay.png");
	static final ResourceLocation kitCooldown = new ResourceLocation(ModInfo.MODID, "textures/misc/kit_cooldown.png");
	
	final Minecraft mc = Minecraft.getMinecraft();
	
	@SubscribeEvent
	public void onRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
		ScaledResolution scaledRes = event.getResolution();
		if (event.getType() == ElementType.HELMET) {
			// Screen Darken
			if (HealthHungerTweaks.instance.configHandler.shouldScreenDarkenWhenInjured()) {
				
				GlStateManager.disableDepth();
				GlStateManager.depthMask(false);
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
						GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
						GlStateManager.DestFactor.ZERO);

				float alpha = MathHelper
						.clamp((float) mc.player.getHealth() / (mc.player.getMaxHealth() - 5f), 0f, 1f);

				GlStateManager.color(0.7F, 0F, 0F, 1f - alpha);
				GlStateManager.disableAlpha();
				this.mc.getTextureManager().bindTexture(healthRedOverlay);
				Tessellator tessellator = Tessellator.getInstance();
				VertexBuffer vertexbuffer = tessellator.getBuffer();
				vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
				vertexbuffer.pos(0.0D, (double) scaledRes.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
				vertexbuffer.pos((double) scaledRes.getScaledWidth(), (double) scaledRes.getScaledHeight(), -90.0D)
						.tex(1.0D, 1.0D).endVertex();
				vertexbuffer.pos((double) scaledRes.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
				vertexbuffer.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
				tessellator.draw();
				GlStateManager.depthMask(true);
				GlStateManager.enableDepth();
				GlStateManager.enableAlpha();
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			}
		}
		if (event.getType() == ElementType.HEALTH) {
			// Cooldown indicator
			if (this.mc.player.hasCapability(HealthBoxCapabilityHandler.HEALTH_BOX, null)) {
				IHealthBoxCapability boxCap = this.mc.player.getCapability(HealthBoxCapabilityHandler.HEALTH_BOX,
						null);
				if (boxCap.getCooldown() > 0) {
					int x = HealthHungerTweaks.instance.configHandler.getCooldownX();
					int y = HealthHungerTweaks.instance.configHandler.getCooldownY();

					if (HealthHungerTweaks.instance.configHandler.getShowCooldownMode() >= 1) {
						// Icon
						this.mc.getTextureManager().bindTexture(kitCooldown);
						Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, 16, 16, 16, 16);
						x += 16 + 5;
						this.mc.getTextureManager().bindTexture(Gui.ICONS);
					}
					if (HealthHungerTweaks.instance.configHandler.getShowCooldownMode() == 2) {
						this.mc.ingameGUI.drawString(this.mc.fontRenderer, String.valueOf(boxCap.getCooldown() / 20 + 1),
								x, y + ((16 - this.mc.fontRenderer.FONT_HEIGHT) / 2), Color.WHITE.getRGB());
						this.mc.getTextureManager().bindTexture(Gui.ICONS);
					}
				}
			}
		}
	}
	
}
