package net.cerulan.healthhungertweaks.handler;

import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.cerulan.healthhungertweaks.capability.healthbox.HealthBoxCapabilityHandler;
import net.cerulan.healthhungertweaks.capability.healthbox.IHealthBoxCapability;
import net.cerulan.healthhungertweaks.capability.healthregen.HealthRegenCapabilityHandler;
import net.cerulan.healthhungertweaks.capability.healthregen.IHealthRegenCapability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import squeek.applecore.api.food.FoodEvent;
import squeek.applecore.api.hunger.ExhaustionEvent;
import squeek.applecore.api.hunger.HealthRegenEvent;

public class HealthHungerHandler {
	
	public HealthHungerHandler() {
	}

	@SubscribeEvent
	public void allowNormalRegen(HealthRegenEvent.AllowRegen event) {
		//event.setResult(Result.DENY);
		allowRegen(event);
	}
	
	@SubscribeEvent
	public void allowSaturatedRegen(HealthRegenEvent.AllowSaturatedRegen event) {
		//event.setResult(Result.DENY);
		allowRegen(event);
	}
	
	private void allowRegen(HealthRegenEvent event) {
		//if (HealthHungerTweaks.instance.configHandler.shouldDisableRegularRegen()) {
			event.setResult(Result.DENY);
		//}
	}
	
	// TODO Peaceful config
	
	@SubscribeEvent
	public void allowExhaustion(ExhaustionEvent.AllowExhaustion event) {
		if (event.player.isPotionActive(HealthHungerTweaks.sidedProxy.potionSatiated)) {
			event.setResult(Result.DENY);
		}
		else {
			event.setResult(Result.DEFAULT);
		}
	}
	
	@SubscribeEvent
	public void getMaxExhaustion(ExhaustionEvent.GetMaxExhaustion event) {
		event.maxExhaustionLevel *= HealthHungerTweaks.instance.configHandler.getExhaustionModifier();
	}
	
	@SubscribeEvent
    public void onFoodEaten(FoodEvent.FoodEaten event) {
		if (HealthHungerTweaks.instance.configHandler.shouldSate()) {
			int ticks = event.foodValues.hunger * HealthHungerTweaks.instance.configHandler.getSatiatedDuration();
			event.player.addPotionEffect(new PotionEffect(HealthHungerTweaks.sidedProxy.potionSatiated, ticks, 0, false, true));
		}
	}
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			if (event.player.hasCapability(HealthBoxCapabilityHandler.HEALTH_BOX, null)) {
				IHealthBoxCapability hBoxCap = event.player.getCapability(HealthBoxCapabilityHandler.HEALTH_BOX,
						null);
				if (hBoxCap.getCooldown() > 0) {
					hBoxCap.setCooldown(hBoxCap.getCooldown() - 1);
				}
			}
			if (event.player.hasCapability(HealthRegenCapabilityHandler.HEALTH_REGEN, null)) {

				IHealthRegenCapability hRegenCap = event.player.getCapability(HealthRegenCapabilityHandler.HEALTH_REGEN,
						null);

				int untilStart = hRegenCap.getTicksUntilRegenStart();
				int untilNext = hRegenCap.getTicksUntilNextRegen();
				if (event.player.getFoodStats().getFoodLevel() > 6
						/* TODO config values */ && event.player.getHealth() < event.player.getMaxHealth()) {
					if (untilStart > 0) {
						// HealthHungerTweaks.Log.info("here");
						untilStart--;
					} else if (untilStart == 0 && untilNext > 0) {
						untilNext--;
					} else if (untilStart == 0 && untilNext == 0) {
						untilNext = 10; // TODO Config value
						if (!event.player.worldObj.isRemote && event.player.getHealth() < event.player.getMaxHealth()) {
							event.player.heal(1f);
						}
					}
				} else {
					untilStart = 200; // TODO config value
				}
				hRegenCap.setData(untilStart, untilNext);
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event) {
		
		if (event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.getEntity(); 
			if (player.hasCapability(HealthRegenCapabilityHandler.HEALTH_REGEN, null)) {
				IHealthRegenCapability cap = player.getCapability(HealthRegenCapabilityHandler.HEALTH_REGEN, null);
				cap.setData(200, 10); // TODO config value
				//HealthHungerTweaks.Log.info("Damage At: " + System.currentTimeMillis());
			}
			
			
			// Old Logic... was kinda buggy
			/*float newHealth = event.getEntityLiving().getHealth() - event.getAmount(); 
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
			}*/
			

		}

	}
	
}
