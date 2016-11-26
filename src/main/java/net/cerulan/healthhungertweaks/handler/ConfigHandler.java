package net.cerulan.healthhungertweaks.handler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

	private Configuration config;
	public ConfigHandler(Configuration config) {
		this.config = config;
	}
	
	private int maxUnrecoverableHealth;
	private boolean useDmgWhitelist;
	private List<String> damageWhitelist;
	private List<String> damageBlacklist;
	
	
	public void load() {
		maxUnrecoverableHealth = config.get("mending", "maxUnrecoverableHealth", 5, "The highest health from which the mending buff will not get applied. (If you drop below this health, you will lose the mending effect)").getInt();
		useDmgWhitelist = config.get("mending", "useDamageWhitelist", true, "Sets whether to use a whitelist or a blacklist for applying the mending effect following damage from a damage source.").getBoolean();
		String[] dmgWht = config.get("mending", "damageWhitelist", new String[] {"hotFloor", "inWall", "drown", "cactus", "fall", "flyIntoWall", "fallingBlock"},
				"Declares a whitelist of damage sources that will apply a mending effect after taking damage. Has no effect if useDamageWhitelist is false."
				+ " Available values: inFire, lightningBolt, onFire, lava, hotFloor, inWall, drown, cactus, fall,"
				+ " flyIntoWall, outOfWorld, generic, magic, wither, anvil, fallingBlock, dragonBreath").getStringList();
		damageWhitelist = Collections.unmodifiableList(Arrays.asList(dmgWht));
		
		String[] dmgBlk = config.get("mending", "damageBlacklist", new String[] {},
				"Declares a whitelist of damage sources that will apply a mending effect after taking damage. Has no effect if useDamageWhitelist is true."
				+ " Available values: inFire, lightningBolt, onFire, lava, hotFloor, inWall, drown, cactus, fall,"
				+ " flyIntoWall, outOfWorld, generic, magic, wither, anvil, fallingBlock, dragonBreath").getStringList();
		damageBlacklist = Collections.unmodifiableList(Arrays.asList(dmgBlk));
		
		config.save();
	}
	
	public boolean damageMatchesList(String dmgSrc) {
		if (useDmgWhitelist) {
			return damageWhitelist.contains(dmgSrc);
		}
		else {
			return !damageBlacklist.contains(dmgSrc);
		}
	}
	
	public int getMaxUnrecoverableHealth() { return maxUnrecoverableHealth; }
	
}
