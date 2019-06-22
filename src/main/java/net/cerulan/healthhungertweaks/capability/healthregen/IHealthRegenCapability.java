package net.cerulan.healthhungertweaks.capability.healthregen;

public interface IHealthRegenCapability {
	
	int getTicksUntilRegenStart();
	int getTicksUntilNextRegen();
	boolean isKitRegen();
	
	void setTicksUntilRegenStart(int ticks);
	void setTicksUntilNextRegen(int ticks);
	void setKitRegen(boolean value);
	
	void setData(int ticksStart, int ticksNext);
	
	

}
