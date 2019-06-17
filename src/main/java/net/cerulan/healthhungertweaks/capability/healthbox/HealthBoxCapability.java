package net.cerulan.healthhungertweaks.capability.healthbox;

import net.minecraft.item.ItemStack;

public class HealthBoxCapability implements IHealthBoxCapability {

	int healthKits = 0;
	int cooldown = 0;
	
	@Override
	public int getHealthKitCount() {
		return healthKits;
	}

	@Override
	public int getSlots() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {		
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		if (stack != ItemStack.EMPTY) {
			healthKits += stack.getCount();
		// 	TODO more kits
		}
	}

	@Override
	public void setHealthKitCount(int health) {
		this.healthKits = health;
	}

	@Override
	public int getCooldown() {
		return this.cooldown;
	}

	@Override
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	@Override
	public int getSlotLimit(int slot) {
		return 64;
	}

}
