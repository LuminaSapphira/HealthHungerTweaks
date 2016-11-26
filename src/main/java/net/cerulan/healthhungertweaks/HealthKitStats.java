package net.cerulan.healthhungertweaks;


// TODO move to config
public enum HealthKitStats {

	PRIMITIVE(0f, 240),
	STANDARD(4f, 240),
	ADVANCED(14f, 240);
	
	private float health;
	private int mendingTicks;
	HealthKitStats(float health, int mending) {
		this.health = health;
		this.mendingTicks = mending;
	}
	
	public float getMaxInstantHealth() { return health; }
	public int getMendingTicks() { return mendingTicks; }
	
}
