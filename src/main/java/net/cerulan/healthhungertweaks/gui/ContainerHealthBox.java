package net.cerulan.healthhungertweaks.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHealthBox extends Container {
	
	private EntityPlayer player;
	//public HealthKitInventory inventory;
	
	public ContainerHealthBox(EntityPlayer player) {
		this.player = player;
		
		// Input, Slot 0
		this.addSlotToContainer(new SlotHealthKit(player, 0, 80, 25));

	    // Player Inventory, Slot 9-35, Slot IDs 1-27
	    for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 9; ++x) {
	            this.addSlotToContainer(new Slot(player.inventory, x + y * 9 + 9, 8 + x * 18, 84 + 29 +y * 18));
	        }
	    }

	    // Player Inventory, Slot 0-8, Slot IDs 28-36
	    for (int x = 0; x < 9; ++x) {
	        this.addSlotToContainer(new Slot(player.inventory, x, 8 + x * 18, 29 + 142 ));
	    }
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack previous = null;
	    Slot slot = (Slot) this.inventorySlots.get(index);

	    if (slot != null && slot.getHasStack()) {
	        ItemStack current = slot.getStack();
	        previous = current.copy();
	        
			if (index < 1) {
				// From box to player (shouldn't occur)
				if (!this.mergeItemStack(current, 1, 37, true))
					return null;
			} else {
				// From player to box
				if (!this.mergeItemStack(current, 0, 1, false))
					return null;
			}

	        if (current.stackSize == 0)
	            slot.putStack((ItemStack) null);
	        else
	            slot.onSlotChanged();

	        if (current.stackSize == previous.stackSize)
	            return null;
	        slot.onPickupFromSlot(playerIn, current);
	    }
	    return previous;
	}
	
}
