package net.cerulan.healthhungertweaks.capability;

import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class HealthBoxCapability implements IHealthBoxCapability {

	int[] healthKits = new int[] { 0, 0, 0 };
	
	@Override
	public int[] getHealthKits() {
		return healthKits;
	}

	@Override
	public int getSlots() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return null;
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {		
		return null;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		return null;
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		//HealthHungerTweaks.Log.info(stack);
		if (stack != null) {
			getHealthKits()[MathHelper.clamp_int(stack.getItemDamage(), 0, 2)] += stack.stackSize;
		//	HealthHungerTweaks.Log.info("item inserted. " + getHealthKits()[0]);
		// 	TODO more kits
		}
	}

	@Override
	public void setHealthKits(int[] health) {
		this.healthKits = health;
	}

}
