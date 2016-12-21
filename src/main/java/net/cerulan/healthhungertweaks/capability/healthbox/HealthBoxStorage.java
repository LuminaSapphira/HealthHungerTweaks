package net.cerulan.healthhungertweaks.capability.healthbox;

import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class HealthBoxStorage implements IStorage<IHealthBoxCapability> {

	@Override
	public NBTBase writeNBT(Capability<IHealthBoxCapability> capability, IHealthBoxCapability instance,
			EnumFacing side) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setIntArray("kits", instance.getHealthKits());
		nbt.setInteger("cooldown", instance.getCooldown());
		return nbt;
	}

	@Override
	public void readNBT(Capability<IHealthBoxCapability> capability, IHealthBoxCapability instance, EnumFacing side,
			NBTBase nbt) {
		
		// Check if cap data is new or old
		if (nbt instanceof NBTTagCompound) {
			int[] kits = ((NBTTagCompound) nbt).getIntArray("kits");
			try {
				System.arraycopy(kits, 0, instance.getHealthKits(), 0, 3);
			} catch (Exception ex) {
				HealthHungerTweaks.Log.info("Error occurred when reading Health Box data.");
				ex.printStackTrace();
			}
			int cooldown = ((NBTTagCompound) nbt).getInteger("cooldown");
			instance.setCooldown(cooldown);
			
		}
		// if old, convert to new.
		else if (nbt instanceof NBTTagIntArray) {
			try {
				NBTTagIntArray ia = ((NBTTagIntArray) nbt);
				System.arraycopy(ia.getIntArray(), 0, instance.getHealthKits(), 0, 3);
				instance.setCooldown(0);
			} catch (Exception ex) {
				HealthHungerTweaks.Log.info("Error occurred when reading Health Box data.");
				ex.printStackTrace();
			}
		}

	}

}
