package net.cerulan.healthhungertweaks.capability.healthregen;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class HealthRegenCapability implements IHealthRegenCapability {

	private int untilRegenStart, untilNextRegen;

	@Override
	public int getTicksUntilRegenStart() {
		return untilRegenStart;
	}

	@Override
	public int getTicksUntilNextRegen() {
		return untilNextRegen;
	}

	@Override
	public void setTicksUntilRegenStart(int ticks) {
		this.untilRegenStart = ticks;
	}
	
	@Override
	public void setTicksUntilNextRegen(int ticks) {
		this.untilNextRegen = ticks;
	}

	@Override
	public void setData(int ticksStart, int ticksNext) {
		this.untilRegenStart = ticksStart;
		this.untilNextRegen = ticksNext;
	}
	
	public static class HealthRegenStorage implements IStorage<IHealthRegenCapability> {

		@Override
		public NBTBase writeNBT(Capability<IHealthRegenCapability> capability, IHealthRegenCapability instance,
				EnumFacing side) {
			NBTTagCompound compound = new NBTTagCompound();
			compound.setInteger("ticksUntilStart", instance.getTicksUntilRegenStart());
			compound.setInteger("ticksUntilRegen", instance.getTicksUntilNextRegen());
			return compound;
		}

		@Override
		public void readNBT(Capability<IHealthRegenCapability> capability, IHealthRegenCapability instance,
				EnumFacing side, NBTBase nbt) {

			NBTTagCompound compound = (NBTTagCompound) nbt;
			instance.setData(compound.getInteger("ticksUntilStart"), compound.getInteger("ticksUntilRegen"));

		}

	}

}
