package net.cerulan.healthhungertweaks;


// TODO move to config
public enum HealthKitStats {

	PRIMITIVE(2f),
	STANDARD(6f),
	ADVANCED(12f);
	
	private float health;
	HealthKitStats(float health) {
		this.health = health;
	}
	
	public float getRestored() { return health; }
	
}
