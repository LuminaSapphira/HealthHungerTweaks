package net.cerulan.healthhungertweaks.item;
import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {

	public static ItemBase itemHealthKit;
	public static ItemBase itemBandage;
	public static ItemBase itemOintment;
	public static ItemBase itemMedicalTools;
	
	public static void init() {
		itemHealthKit = register(new ItemHealthKit("health_kit").setCreativeTab(HealthHungerTweaks.sidedProxy.creativeTab).setSubtypeAmount(3));
		itemBandage = register(new ItemBase("bandage").setCreativeTab(HealthHungerTweaks.sidedProxy.creativeTab).setSubtypeAmount(2));
		itemOintment = register(new ItemBase("ointment").setCreativeTab(HealthHungerTweaks.sidedProxy.creativeTab).setSubtypeAmount(2));
		itemMedicalTools = register(new ItemBase("medical_tools").setCreativeTab(HealthHungerTweaks.sidedProxy.creativeTab));
	}

	private static <T extends Item> T register(T item) {
		GameRegistry.register(item);

		if (item instanceof ItemBase) {
			((ItemBase)item).registerItemModel();
		}

		return item;
	}

}