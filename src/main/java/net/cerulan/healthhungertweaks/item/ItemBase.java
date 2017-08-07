package net.cerulan.healthhungertweaks.item;

import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBase extends Item {
	protected String name;
	
	int subtypes = 1;

	public ItemBase(String name) {
		this.name = name;
		setUnlocalizedName(name);
		setRegistryName(name);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack) + "." + stack.getMetadata();
	}
	
	public ItemBase setSubtypeAmount(int amt) {
		this.setHasSubtypes(true);
		this.subtypes = amt;
		return this;
	}

	public void registerItemModel() {
		for (int i = 0; i < subtypes; i++) {
			HealthHungerTweaks.sidedProxy.registerItemRenderer(this, i, name);
		}
		
	}

	@Override
	public ItemBase setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		for (int i = 0; i < subtypes; i++) {
			subItems.add(new ItemStack(this, 1, i));
		}
	}
}
