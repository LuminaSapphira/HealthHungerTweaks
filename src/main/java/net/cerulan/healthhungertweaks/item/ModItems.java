package net.cerulan.healthhungertweaks.item;
import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class ModItems {
	/*
	@GameRegistry.ObjectHolder("healthhungertweaks:health_kit")
	public static ItemBase itemHealthKit;
	
	@GameRegistry.ObjectHolder("healthhungertweaks:bandage")
	public static ItemBase itemBandage;
	
	@GameRegistry.ObjectHolder("healthhungertweaks:ointment")
	public static ItemBase itemOintment;
	*/
	
	@GameRegistry.ObjectHolder("healthhungertweaks:health_kit_regen")
	public static ItemBase itemHealthKit;
	
	@GameRegistry.ObjectHolder("healthhungertweaks:cloth_bandage")
	public static ItemBase itemBandage;
	
	@GameRegistry.ObjectHolder("healthhungertweaks:refined_ointment")
	public static ItemBase itemOintment;
	
	
	
	@GameRegistry.ObjectHolder("healthhungertweaks:medical_tools")
	public static ItemBase itemMedicalTools;
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		/*event.getRegistry().register(new ItemBase("health_kit").setCreativeTab(HealthHungerTweaks.sidedProxy.creativeTab));
		event.getRegistry().register(new ItemBase("bandage").setCreativeTab(HealthHungerTweaks.sidedProxy.creativeTab));
		event.getRegistry().register(new ItemBase("ointment").setCreativeTab(HealthHungerTweaks.sidedProxy.creativeTab));*/
		event.getRegistry().register(new ItemBase("health_kit_regen").setCreativeTab(HealthHungerTweaks.sidedProxy.creativeTab));
		event.getRegistry().register(new ItemBase("cloth_bandage").setCreativeTab(HealthHungerTweaks.sidedProxy.creativeTab));
		event.getRegistry().register(new ItemBase("refined_ointment").setCreativeTab(HealthHungerTweaks.sidedProxy.creativeTab));
		event.getRegistry().register(new ItemBase("medical_tools").setCreativeTab(HealthHungerTweaks.sidedProxy.creativeTab));
				
	}
	
	
	@SubscribeEvent
    public static void missingMapping(RegistryEvent.MissingMappings<Item> event) {
		System.out.println("fixing old mappings");
		for (RegistryEvent.MissingMappings.Mapping<Item> missing : event.getMappings()) {
			if (missing.key.getResourceDomain().equals("healthhungertweaks")) {
				if (missing.key.getResourcePath().equals("health_kit")) {
					missing.remap(itemHealthKit);
				}
				if (missing.key.getResourcePath().equals("bandage")) {
					missing.remap(itemBandage);
				}
				if (missing.key.getResourcePath().equals("ointment")) {
					missing.remap(itemOintment);
				}
			}
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void addTooltip(ItemTooltipEvent event) {
		if (event.getItemStack().getItem().getRegistryName().getResourceDomain().equals("healthhungertweaks")) {
			if (event.getItemStack().getMetadata() > 0) {
				event.getToolTip().add(new TextComponentTranslation("hht.conversion").getFormattedText());
			}
		}
	}
	
	@SubscribeEvent
	public static void initModels(ModelRegistryEvent event) {
		itemHealthKit.initModel();
		itemBandage.initModel();
		itemOintment.initModel();
		itemMedicalTools.initModel();
	}

}