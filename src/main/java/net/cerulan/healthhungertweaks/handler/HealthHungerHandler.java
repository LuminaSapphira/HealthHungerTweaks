package net.cerulan.healthhungertweaks.handler;

import net.cerulan.healthhungertweaks.HHTConfigCommon;
import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.cerulan.healthhungertweaks.capability.healthbox.HealthBoxCapabilityHandler;
import net.cerulan.healthhungertweaks.capability.healthbox.IHealthBoxCapability;
import net.cerulan.healthhungertweaks.capability.healthregen.HealthRegenCapabilityHandler;
import net.cerulan.healthhungertweaks.capability.healthregen.IHealthRegenCapability;
import net.cerulan.healthhungertweaks.potion.ModPotions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import squeek.applecore.api.food.FoodEvent;
import squeek.applecore.api.hunger.ExhaustionEvent;
import squeek.applecore.api.hunger.HealthRegenEvent;
import toughasnails.api.stat.capability.IThirst;

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
		if (HHTConfigCommon.mending.disableRegularRegen) {
			event.setResult(Result.DENY);
		}
	}
	
	// TODO Peaceful config
	
	@SubscribeEvent
	public void allowExhaustion(ExhaustionEvent.AllowExhaustion event) {
		if (event.player.isPotionActive(ModPotions.satiated)) {
			event.setResult(Result.DENY);
		}
		else {
			event.setResult(Result.DEFAULT);
		}
	}
	
	@SubscribeEvent
	public void getMaxExhaustion(ExhaustionEvent.GetMaxExhaustion event) {
		event.maxExhaustionLevel *= HHTConfigCommon.exhaustion.exhaustionModifier;
	}
	
	@SubscribeEvent
    public void onFoodEaten(FoodEvent.FoodEaten event) {
		if (HHTConfigCommon.satiation.enableSatiated) {
			if (event == null || event.foodValues == null) {
				HealthHungerTweaks.Log.fatal("Food values is null! This should not happen! Skipped applying Satiated buff!");
				return;
			}
			int ticks = event.foodValues.hunger * HHTConfigCommon.satiation.satiatedDuration;
			event.player.addPotionEffect(new PotionEffect(ModPotions.satiated, ticks, 0, false, true));
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
				if (event.player.getFoodStats().getFoodLevel() >= HHTConfigCommon.mending.minimumHunger
						&& (!Loader.isModLoaded("toughasnails") || getThirst(event.player) >= HHTConfigCommon.mending.minimumThirst) // Tough as Nails Integration
						&& event.player.getHealth() < event.player.getMaxHealth()) {
					if (untilStart > 0) {
						untilStart--;
					} else if (untilStart == 0 && untilNext > 0) {
						untilNext--;
					} else if (untilStart == 0 && untilNext == 0) {
						untilNext = HHTConfigCommon.mending.delayBetween;
						if (!event.player.world.isRemote && event.player.getHealth() < event.player.getMaxHealth()) {
							if (HHTConfigCommon.mending.usePercent) {
								event.player.heal((float)(HHTConfigCommon.mending.percentAmount * event.player.getMaxHealth()));
							}
							else {
								event.player.heal((float)(HHTConfigCommon.mending.staticAmount));
							}
						}
					}
				} else {
					untilStart = HHTConfigCommon.mending.delayUntilStart;
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
				cap.setData(HHTConfigCommon.mending.delayUntilStart, HHTConfigCommon.mending.delayBetween);
			}

		}

	}
	
	/* TOUGH AS NAILS INTEGRATION */
	
	@CapabilityInject(IThirst.class)
	private static final Capability<IThirst> THIRST = null; 
	
	private int getThirst(EntityPlayer player) {
		if (Loader.isModLoaded("toughasnails") && player.hasCapability(THIRST, null)) {
			return player.getCapability(THIRST, null).getThirst();
		}
		return 20;
	}
	
}
