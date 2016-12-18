package net.cerulan.healthhungertweaks.capability.healthbox;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class HealthBoxStorage implements IStorage<IHealthBoxCapability> {

	@Override
	public NBTBase writeNBT(Capability<IHealthBoxCapability> capability, IHealthBoxCapability instance,
			EnumFacing side) {
		NBTTagIntArray ia = new NBTTagIntArray(instance.getHealthKits());
		return ia;
	}

	@Override
	public void readNBT(Capability<IHealthBoxCapability> capability, IHealthBoxCapability instance, EnumFacing side,
			NBTBase nbt) {
		
		try {
			NBTTagIntArray ia = ((NBTTagIntArray)nbt);
			System.arraycopy(ia.getIntArray(), 0, instance.getHealthKits(), 0, 3);
		}
		catch (Exception ex) {
			
		}

	}

}
