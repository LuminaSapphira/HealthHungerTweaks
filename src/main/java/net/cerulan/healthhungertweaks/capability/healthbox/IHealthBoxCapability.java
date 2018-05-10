package net.cerulan.healthhungertweaks.capability.healthbox;

import net.minecraftforge.items.IItemHandlerModifiable;

public interface IHealthBoxCapability extends IItemHandlerModifiable {

	int getCooldown();
	void setCooldown(int cooldown);
	//int[] getHealthKits();
	int getHealthKitCount();
	void setHealthKitCount(int count);
	//void setHealthKits(int[] health);
	
}
