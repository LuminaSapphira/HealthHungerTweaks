package net.cerulan.healthhungertweaks.capability.healthbox;

import net.minecraftforge.items.IItemHandlerModifiable;

public interface IHealthBoxCapability extends IItemHandlerModifiable {

	int getCooldown();
	void setCooldown(int cooldown);
	int[] getHealthKits();
	void setHealthKits(int[] health);
	
}
