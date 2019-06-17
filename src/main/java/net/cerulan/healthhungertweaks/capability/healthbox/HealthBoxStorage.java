package net.cerulan.healthhungertweaks.capability.healthbox;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class HealthBoxStorage implements IStorage<IHealthBoxCapability> {

	@Override
	public NBTBase writeNBT(Capability<IHealthBoxCapability> capability, IHealthBoxCapability instance,
			EnumFacing side) {
		NBTTagCompound nbt = new NBTTagCompound();
		//TODO clean
		//nbt.setIntArray("kits", instance.getHealthKits());
		nbt.setInteger("kit_count", instance.getHealthKitCount());
		nbt.setInteger("cooldown", instance.getCooldown());
		return nbt;
	}

	@Override
	public void readNBT(Capability<IHealthBoxCapability> capability, IHealthBoxCapability instance, EnumFacing side,
			NBTBase nbt) {
		
		if (nbt instanceof NBTTagCompound) {
			NBTTagCompound nbtt = (NBTTagCompound)nbt;
			// Check if cap data is new or old
			int newCount = 0;
			if (nbtt.hasKey("kits")) {
				// Old
				int[] kits = nbtt.getIntArray("kits");
				newCount = kits[0] + kits[1] + kits[2];
			}
			if (nbtt.hasKey("kit_count")) {
				// new
				newCount = nbtt.getInteger("kit_count");
			}
			instance.setHealthKitCount(newCount);
			int cooldown = ((NBTTagCompound) nbt).getInteger("cooldown");
			instance.setCooldown(cooldown);
			
		}

	}

}
