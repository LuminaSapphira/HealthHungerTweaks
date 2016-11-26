package net.cerulan.healthhungertweaks.capability;

import net.minecraftforge.items.IItemHandlerModifiable;

public interface IHealthBoxCapability extends IItemHandlerModifiable {

	int[] getHealthKits();
	void setHealthKits(int[] health);
	
}
