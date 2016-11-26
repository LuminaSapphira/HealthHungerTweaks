package net.cerulan.healthhungertweaks.potion;

import net.cerulan.healthhungertweaks.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionMending extends Potion {
	private ResourceLocation icon = new ResourceLocation(ModInfo.MODID, "textures/icons/potionmending.png");

	public PotionMending() {
		super(false, 0);
		setPotionName("potion.mending");
		this.setRegistryName("mending");
	}

	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int par2) {
		if (entityLivingBaseIn.getHealth() < entityLivingBaseIn.getMaxHealth()) {
			entityLivingBaseIn.heal(1f);
		}
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration % 40 == 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		super.renderInventoryEffect(x, y, effect, mc);

		mc.renderEngine.bindTexture(icon);

		Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
		super.renderHUDEffect(x, y, effect, mc, alpha);

		mc.renderEngine.bindTexture(icon);

		Gui.drawModalRectWithCustomSizedTexture(x + 4, y + 4, 0, 0, 18, 18, 18, 18);
	}

}
