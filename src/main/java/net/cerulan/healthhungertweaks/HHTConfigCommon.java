package net.cerulan.healthhungertweaks;

import net.minecraftforge.common.config.Config;

@Config(modid = "healthhungertweaks", name="cerulan/healthhungertweaks_common.cfg")
public class HHTConfigCommon {
	
	@Config.Name("exhaustion")
	@Config.Comment("Controls the vanilla Minecraft hunger system.")
	public static Exhaustion exhaustion = new Exhaustion();
	
	@Config.Name("mending")
	@Config.Comment("Settings related to mending, the health regeneration after taking damage. Set delayUntilStart to -1 to disable this functionality.")
	public static Mending mending = new Mending();
	
	
	public static class Exhaustion {
		
		@Config.Comment("A modifier value that adjusts the maximum hunger exhaustion level before losing a saturation or hunger point. Higher values mean slower food drain. 2 is given as default, but in packs with creative-mode flight, a lower value might be better.")
		@Config.RangeDouble(min = 0.1)
		public double exhaustionModifier = 2.0;
		
	}
	
	public static class Mending {
		@Config.Comment("While mending, this is the delay in ticks between each regeneration.")
		@Config.RangeInt(min = 0)
		public int delayBetween = 10;
		
		@Config.Comment("The delay (in ticks) before a player will begin to regenerate health after taking damage (taking damage during this duration will reset the countdown). Negative values disable mending, forcing players to rely solely on health kits or potions.")
		@Config.RangeInt(min = -1)
		public int delayUntilStart = 200;
		
		@Config.Comment("Toggles whether regular regeneration from food should be disabled, and players must rely on the mending effect or use health kits. (This also makes food easier as saturation will not be consumed to restore health).")
		public boolean disableRegularRegen = true;
		
		@Config.Comment("The cooldown period (in ticks) while a player may not use a health kit after previously using one.")
		@Config.RangeInt(min = 0)
		public int healthKitCooldown = 100;
		
		@Config.Comment("The minimum hunger point value necessary to be able to heal with the mending effect.")
		@Config.RangeInt(min = 0, max = 20)
		public int minimumHunger = 6;
		
		@Config.Comment("ToughAsNails integration: The minimum thirst point value necessary to be able to heal. No effect if ToughAsNails is not installed.")		
		public int minimumThirst = 16;
		
		@Config.Comment("The percent (0 - 1 = 0% - 100%) of maximum health to restore each regeneration if usePercent is enabled.")
		@Config.RangeDouble(min = 0.01, max = 1)
		public double percentAmount = 0.05;
		
		@Config.Comment("The amount of health points to restore each regeneration if usePercent is disabled.")
		@Config.RangeDouble(min = 0)
		public double staticAmount = 1;
		
		@Config.Comment("Regeneration will restore a percentage of maximum health, rather than a flat value.")
		public boolean usePercent = true;
	}
	
	public static class Satiation {
		
		@Config.Comment("Toggles whether eating food gives a satiated effect that disables food drain for its duration.")
		public boolean enableSatiated = true;
		
		@Config.Comment("This value will be multiplied to the amount of hunger points restored to get the duration of the satiated effect (in ticks).")
		@Config.RangeInt(min = 1)
		public int satiatedDuration = 600;
		
	}
	
}
