package net.cerulan.healthhungertweaks.capability.healthregen;

public interface IHealthRegenCapability {
	
	public int getTicksUntilRegenStart();
	public int getTicksUntilNextRegen();
	
	public void setTicksUntilRegenStart(int ticks);
	public void setTicksUntilNextRegen(int ticks);
	
	public void setData(int ticksStart, int ticksNext);
	
	

}
