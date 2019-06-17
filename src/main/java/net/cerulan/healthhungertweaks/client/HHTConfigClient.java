package net.cerulan.healthhungertweaks.client;

import net.minecraftforge.common.config.Config;

@Config(modid = "healthhungertweaks", name="cerulan/healthhungertweaks_client")
public class HHTConfigClient {

	@Config.Name("client")
	@Config.Comment("Controls client-specific settings. This file should not be included in modpacks except for default user-overridable definitions.")
	public static Client client = new Client();


	public static class Client {
		@Config.Comment("Sets the mode that determines how the health kit cooldown will be displayed. 0=Not At All, 1=Only Icon, 2=Icon + Time Remaining")
		@Config.RangeInt(min = 0, max = 2)
		public int showCooldownMode = 2;

		@Config.Comment("The X Coordinate of the Cooldown Indicator")
		public int cooldownX = 5;

		@Config.Comment("The Y Coordinate of the Cooldown Indicator")
		public int cooldownY = 5;

		@Config.Comment("Whether or not the screen should darken when injured")
		public boolean screenDarken = true;


	}

}
