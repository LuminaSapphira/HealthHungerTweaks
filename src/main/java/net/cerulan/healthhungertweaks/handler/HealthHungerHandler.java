package net.cerulan.healthhungertweaks.handler;

import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import squeek.applecore.api.food.FoodEvent;
import squeek.applecore.api.hunger.ExhaustionEvent;
import squeek.applecore.api.hunger.HealthRegenEvent;

public class HealthHungerHandler {
	
	public HealthHungerHandler() {
		HealthHungerTweaks.Log.info("new inst handler");
	}

	@SubscribeEvent
	public void allowNormalRegen(HealthRegenEvent.AllowRegen event) {
		//HealthHungerTweaks.Log.info("asd");
		//event.setResult(Result.DENY);
		allowRegen(event);
	}
	
	@SubscribeEvent
	public void allowSaturatedRegen(HealthRegenEvent.AllowSaturatedRegen event) {
		//event.setResult(Result.DENY);
		allowRegen(event);
	}
	
	private void allowRegen(HealthRegenEvent event) {
		event.setResult(Result.DENY);
		// TODO regen Config
	}
	
	// TODO Peaceful config
	
	@SubscribeEvent
	public void allowExhaustion(ExhaustionEvent.AllowExhaustion event) {
		//HealthHungerTweaks.Log.info("checking exhaustion");
		if (event.player.isPotionActive(HealthHungerTweaks.sidedProxy.potionSatiated)) {
			event.setResult(Result.DENY);
		}
		else {
			event.setResult(Result.DEFAULT);
		}
		// TODO satiated Config
	}
	
	@SubscribeEvent
	public void getMaxExhaustion(ExhaustionEvent.GetMaxExhaustion event) {
		//HealthHungerTweaks.Log.info("new max exhaustion");
		event.maxExhaustionLevel *= 2f;
		// TODO exhaustion Config
	}
	
	@SubscribeEvent
    public void onFoodEaten(FoodEvent.FoodEaten event) {
		//HealthHungerTweaks.Log.info("here213");
		//if (!event.player.worldObj.isRemote) {
			int ticks = event.foodValues.hunger * 600;
			event.player.addPotionEffect(new PotionEffect(HealthHungerTweaks.sidedProxy.potionSatiated, ticks, 0, false, true));
		//}
	}
	
	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event) {
		
		if (event.getEntity() instanceof EntityPlayer) {
			float newHealth = event.getEntityLiving().getHealth() - event.getAmount(); 
			if (newHealth > 0 && newHealth <= HealthHungerTweaks.instance.configHandler.getMaxUnrecoverableHealth()) {
				event.getEntityLiving().removePotionEffect(HealthHungerTweaks.sidedProxy.potionMending);
			}
			else {
				if (HealthHungerTweaks.instance.configHandler.damageMatchesList(event.getSource().getDamageType())) {
					int duration = (int) event.getAmount() * 40;
					PotionEffect currentEffect = event.getEntityLiving()
							.getActivePotionEffect(HealthHungerTweaks.sidedProxy.potionMending);
					if (currentEffect != null)
						duration += currentEffect.getDuration();
					event.getEntityLiving().addPotionEffect(
							new PotionEffect(HealthHungerTweaks.sidedProxy.potionMending, duration, 0, false, true));
				} else {
					event.getEntityLiving().removePotionEffect(HealthHungerTweaks.sidedProxy.potionMending);
				}
			}
			

		}

	}
	
}
