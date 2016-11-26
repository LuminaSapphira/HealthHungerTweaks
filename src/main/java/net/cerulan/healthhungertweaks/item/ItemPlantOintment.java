package net.cerulan.healthhungertweaks.item;

import java.util.List;

import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPlantOintment extends ItemBase {

	public ItemPlantOintment(String name) {
		super(name);
		this.setHasSubtypes(true);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack) + "." + stack.getMetadata();
	}
	
	@Override
	public void registerItemModel() {
		HealthHungerTweaks.sidedProxy.registerItemRenderer(this, 0, name);
		HealthHungerTweaks.sidedProxy.registerItemRenderer(this, 1, name);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		for (int i = 0; i < 2; i++) {
			subItems.add(new ItemStack(itemIn, 1, i));
		}
	}

}
