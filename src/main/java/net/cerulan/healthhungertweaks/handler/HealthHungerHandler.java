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
import squeek.applecore.api.AppleCoreAPI;
import squeek.applecore.api.food.FoodEvent;
import squeek.applecore.api.hunger.ExhaustionEvent;
import squeek.applecore.api.hunger.HealthRegenEvent;
import toughasnails.api.stat.capability.IThirst;

public class HealthHungerHandler {	
	
	public HealthHungerHandler() {
	}

	@SubscribeEvent
	public void allowNormalRegen(HealthRegenEvent.AllowRegen event) {
		allowRegen(event);
	}
	
	@SubscribeEvent
	public void allowSaturatedRegen(HealthRegenEvent.AllowSaturatedRegen event) {
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
				boolean kitRegen = hRegenCap.isKitRegen();

				if (event.player.getHealth() < event.player.getMaxHealth()) {
					if (kitRegen) {
						if (untilNext > 0) untilNext--;
						else if (untilNext == 0) {
							HHTConfigCommon.Mending.RegenKit regenKitConfig = HHTConfigCommon.mending.regenKit;
							healPlayer(event.player, regenKitConfig.usePercent, regenKitConfig.percentAmount, regenKitConfig.staticAmount);
							untilNext = regenKitConfig.delayBetween;
						}
					}
					else if (event.player.getFoodStats().getFoodLevel() >= HHTConfigCommon.mending.minimumHunger
							&& (!Loader.isModLoaded("toughasnails") || getThirst(event.player) >= HHTConfigCommon.mending.minimumThirst) // Tough as Nails Integration
					) {


						if (untilStart > 0) {
							untilStart--;
						} else if (untilStart == 0 && untilNext > 0) {
							untilNext--;
						} else if (untilStart == 0 && untilNext == 0) {
							int maxHunger = AppleCoreAPI.accessor.getMaxHunger(event.player);
							int missing = maxHunger - event.player.getFoodStats().getFoodLevel();
							untilNext = HHTConfigCommon.mending.delayBetween;
							if (HHTConfigCommon.mending.scaling.useScalingDelay) {
								untilNext += HHTConfigCommon.mending.scaling.additionalDelayPerHungerMissing * missing;
							}
							healPlayer(event.player, HHTConfigCommon.mending.usePercent, HHTConfigCommon.mending.percentAmount, HHTConfigCommon.mending.staticAmount);
						}
					} else {
						untilStart = HHTConfigCommon.mending.delayUntilStart;
					}
				} else {
					untilStart = HHTConfigCommon.mending.delayUntilStart;
					kitRegen = false;
				}

				hRegenCap.setData(untilStart, untilNext);
				hRegenCap.setKitRegen(kitRegen);
			}
		}
	}

	private void healPlayer(EntityPlayer player, boolean usePercent, double percentAmount, double staticAmount) {
		if (!player.world.isRemote && player.getHealth() < player.getMaxHealth()) {
			if (usePercent) {
				player.heal((float)(percentAmount * player.getMaxHealth()));
			}
			else {
				player.heal((float)(staticAmount));
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event) {
		
		if (event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.getEntity(); 
			if (player.hasCapability(HealthRegenCapabilityHandler.HEALTH_REGEN, null)) {
				IHealthRegenCapability cap = player.getCapability(HealthRegenCapabilityHandler.HEALTH_REGEN, null);
				int maxHunger = AppleCoreAPI.accessor.getMaxHunger(player);
				int missing = maxHunger - player.getFoodStats().getFoodLevel();
				int untilNext = HHTConfigCommon.mending.delayBetween;
				if (HHTConfigCommon.mending.scaling.useScalingDelay) {
					untilNext += HHTConfigCommon.mending.scaling.additionalDelayPerHungerMissing * missing;
				}
				cap.setData(HHTConfigCommon.mending.delayUntilStart, untilNext);
				cap.setKitRegen(false);
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
