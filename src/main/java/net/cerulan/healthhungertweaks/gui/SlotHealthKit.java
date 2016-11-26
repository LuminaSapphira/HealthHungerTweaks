package net.cerulan.healthhungertweaks.gui;

import net.cerulan.healthhungertweaks.capability.HealthBoxCapabilityHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class SlotHealthKit extends SlotItemHandler {

	private EntityPlayer player;
	
	public SlotHealthKit(EntityPlayer player, int index, int xPosition, int yPosition) {
		super(player.getCapability(HealthBoxCapabilityHandler.HEALTH_BOX, null), index, xPosition, yPosition);
		this.player = player;
	}
	
	@Override
	public boolean isItemValid(ItemStack valid) {
		return valid.getItem() == Items.DIAMOND;
	}

}
