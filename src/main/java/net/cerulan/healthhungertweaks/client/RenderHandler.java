package net.cerulan.healthhungertweaks.client;

import net.cerulan.healthhungertweaks.ModInfo;
import net.minecraft.client.Minecraft;
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
	
	final Minecraft mc = Minecraft.getMinecraft();
	
	@SubscribeEvent
	public void onRenderGameOverlayPost(RenderGameOverlayEvent.Pre event) {
		if (event.getType() == ElementType.HELMET) {
			ScaledResolution scaledRes = event.getResolution();
			GlStateManager.disableDepth();
			GlStateManager.depthMask(false);
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
					GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
					GlStateManager.DestFactor.ZERO);
			
			float alpha = MathHelper.clamp_float((float)mc.thePlayer.getHealth() / (mc.thePlayer.getMaxHealth() - 5f), 0f, 1f); 
					
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
	
}
