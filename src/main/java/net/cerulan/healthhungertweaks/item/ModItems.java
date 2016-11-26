package net.cerulan.healthhungertweaks.item;
import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {

	public static ItemBase itemHealthKit;
	public static ItemBase itemWoodFiberBandage;
	
	public static void init() {
		itemHealthKit = register(new ItemHealthKit("healthKit").setCreativeTab(HealthHungerTweaks.sidedProxy.creativeTab));
		itemWoodFiberBandage = register(new ItemBase("woodFiberBandage").setCreativeTab(HealthHungerTweaks.sidedProxy.creativeTab));
	}

	private static <T extends Item> T register(T item) {
		GameRegistry.register(item);

		if (item instanceof ItemBase) {
			((ItemBase)item).registerItemModel();
		}

		return item;
	}

}