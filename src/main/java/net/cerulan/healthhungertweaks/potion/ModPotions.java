package net.cerulan.healthhungertweaks.potion;

import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class ModPotions {
	
	@GameRegistry.ObjectHolder("healthhungertweaks:satiated")
	public static PotionSatiated satiated;
	
	@SubscribeEvent
	public static void registerPotions(RegistryEvent.Register<Potion> event) {
		HealthHungerTweaks.Log.info("Registering Satiated Potion");
		event.getRegistry().register(new PotionSatiated());
	}

}
