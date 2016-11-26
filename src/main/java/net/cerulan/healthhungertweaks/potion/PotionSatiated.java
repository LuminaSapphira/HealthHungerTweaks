package net.cerulan.healthhungertweaks.potion;

import net.cerulan.healthhungertweaks.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionSatiated extends Potion {
	private ResourceLocation icon = new ResourceLocation(ModInfo.MODID, "textures/icons/potionsatiated.png");
	public PotionSatiated() {
		super(false, 0);
		setPotionName("potion.satiated");
		this.setRegistryName("satiated");
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
	public boolean shouldRenderHUD(PotionEffect effect) {
		return false;
	}
	
}
