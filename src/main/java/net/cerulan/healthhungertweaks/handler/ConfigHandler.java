package net.cerulan.healthhungertweaks.handler;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

	private Configuration config;
	public ConfigHandler(Configuration config) {
		this.config = config;
	}
	
	private boolean doSatiation;
	private int satiatedDuration;
	private double exhaustionModifier;
	private int kitCooldown;
	
	private int showCooldownMode;
	private int cdX, cdY;
	private boolean screenDarkenWhenInjure;
	
	private boolean disableRegularRegen;
	private int delayUntilStart;
	private int delayBetweenTicks;
	private int minimumHunger;
	private int minimumThirst; // Integration with ToughAsNails
	
	
	public void load() {
		showCooldownMode = config.get("client", "showCooldownMode", 2, "Sets the mode that determines how the health kit cooldown will be displayed. 0=Not At All, 1=Only Icon, 2=Icon + Time Remaining").getInt();
		cdX = config.get("client", "cooldownX", 5, "The X Coordinate of the Cooldown Indicator").getInt();
		cdY = config.get("client", "cooldownY", 5, "The Y Coordinate of the Cooldown Indicator").getInt();
		screenDarkenWhenInjure = config.get("client", "screenDarken", true, "Whether or not the screen should darken when injured").getBoolean();
		
		doSatiation = config.get("satiated", "enableSatiated", true, "Toggles whether eating food gives a satiated effect that disabled food drain for the duration.").getBoolean();
		satiatedDuration = config.get("satiated", "satiatedDuration", 600, "This value will be multipled to the food value of the food to get the duration (in ticks) of the satiated effect.").getInt();
		
		exhaustionModifier = config.get("exhaustion", "exhaustionModifier", 2.0, "An exhaustion modifier that will be multiplied to the default maximum exhausion. Higher values mean slower food drain.").getDouble();
		
		disableRegularRegen = config.get("mending", "disableRegularRegen", true, "Toggles whether regular regen (from food) should be disabled, and players must use health kits. Recommended if food is made easier.").getBoolean();
		delayUntilStart = config.get("mending", "delayUntilStart", 200, "The delay (in ticks) before a player will begin to regenerate health. Negative values disable this functionality, forcing players to rely solely on health kits or potions.").getInt();
		delayBetweenTicks = config.get("mending", "delayBetweenTicks", 10, "The delay (in ticks) between each half-heart restored.").getInt();
		
		minimumHunger = config.get("mending", "minimumHunger", 6, "The minimum hunger (in half-shanks) necessary to be able to heal.").getInt();
		
		minimumThirst = config.get("mending", "minimumThirst", 6, "ToughAsNails integration: The minimum thirst (in half-drops) necessary to be able to heal. No effect if ToughAsNails is not installed.").getInt();
		
		kitCooldown = config.get("mending", "healthKitCooldown", 600, "The cooldown period (in ticks) while a player may not use a health kit after previously using one.").getInt();
		
		
		
		config.save();
	}
	
	public double getExhaustionModifier() { return exhaustionModifier; }
	
	public boolean shouldSate() { return doSatiation; }
	
	public int getSatiatedDuration() { return satiatedDuration; }
	
	public boolean shouldDisableRegularRegen() { return disableRegularRegen; }
	
	public int getHealthKitCooldown() { return this.kitCooldown; }
	
	public int getShowCooldownMode() { return this.showCooldownMode; }
	
	public int getCooldownX() { return this.cdX; }
	public int getCooldownY() { return this.cdY; }
	
	public boolean shouldScreenDarkenWhenInjured() { return this.screenDarkenWhenInjure; }

	public int getDelayUntilStart() {
		return delayUntilStart;
	}

	public int getDelayBetweenTicks() {
		return delayBetweenTicks;
	}

	public int getMinimumHunger() {
		return minimumHunger;
	}
	
	public int getMinimumThirst() {
		return minimumThirst;
	}
	
}
