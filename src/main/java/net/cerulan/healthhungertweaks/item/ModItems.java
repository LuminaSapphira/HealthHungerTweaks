package net.cerulan.healthhungertweaks.item;
import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class ModItems {

	@GameRegistry.ObjectHolder("healthhungertweaks:health_kit")
	public static ItemBase itemHealthKit;
	
	@GameRegistry.ObjectHolder("healthhungertweaks:bandage")
	public static ItemBase itemBandage;
	
	@GameRegistry.ObjectHolder("healthhungertweaks:ointment")
	public static ItemBase itemOintment;
	
	@GameRegistry.ObjectHolder("healthhungertweaks:medical_tools")
	public static ItemBase itemMedicalTools;
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(new ItemHealthKit("health_kit").setCreativeTab(HealthHungerTweaks.sidedProxy.creativeTab).setSubtypeAmount(3));
		event.getRegistry().register(new ItemBase("bandage").setCreativeTab(HealthHungerTweaks.sidedProxy.creativeTab).setSubtypeAmount(2));
		event.getRegistry().register(new ItemBase("ointment").setCreativeTab(HealthHungerTweaks.sidedProxy.creativeTab).setSubtypeAmount(2));
		event.getRegistry().register(new ItemBase("medical_tools").setCreativeTab(HealthHungerTweaks.sidedProxy.creativeTab));
	}
	
	@SubscribeEvent
	public static void initModels(ModelRegistryEvent event) {
		itemHealthKit.initModel();
		itemBandage.initModel();
		itemOintment.initModel();
		itemMedicalTools.initModel();
	}

}