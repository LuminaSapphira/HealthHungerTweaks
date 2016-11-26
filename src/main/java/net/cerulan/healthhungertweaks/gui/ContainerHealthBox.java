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
		this.addSlotToContainer(new SlotHealthKit(player, 0, 80, 17));

	    // Player Inventory, Slot 9-35, Slot IDs 1-27
	    for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 9; ++x) {
	            this.addSlotToContainer(new Slot(player.inventory, x + y * 9 + 9, 8 + x * 18, 84 + 24 + y * 18));
	        }
	    }

	    // Player Inventory, Slot 0-8, Slot IDs 28-36
	    for (int x = 0; x < 9; ++x) {
	        this.addSlotToContainer(new Slot(player.inventory, x, 8 + x * 18, 142 + 24 ));
	    }
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		
		// TODO shift clicking
		return null;
	}
	
}
