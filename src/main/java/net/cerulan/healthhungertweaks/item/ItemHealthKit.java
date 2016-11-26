package net.cerulan.healthhungertweaks.item;

import java.util.List;

import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemHealthKit extends ItemBase {

	public ItemHealthKit(String name) {
		super(name);
		this.setSubtypeAmount(3);
	}

}
