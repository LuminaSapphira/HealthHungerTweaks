package net.cerulan.healthhungertweaks.capability.healthbox;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class HealthBoxCapability implements IHealthBoxCapability {

	int[] healthKits = new int[] { 0, 0, 0 };
	int cooldown = 0;
	
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
		if (stack != null) {
			getHealthKits()[MathHelper.clamp_int(stack.getItemDamage(), 0, 2)] += stack.stackSize;
		// 	TODO more kits
		}
	}

	@Override
	public void setHealthKits(int[] health) {
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

}
