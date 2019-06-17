package net.cerulan.healthhungertweaks.network;

import net.cerulan.healthhungertweaks.ModInfo;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class HealthHungerPacketHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.MODID);
	private static int id = 1;
	
	public HealthHungerPacketHandler() {
		INSTANCE.registerMessage(MessageOpenHealthBoxHandler.class, MessageOpenHealthBox.class, id++, Side.SERVER);
		INSTANCE.registerMessage(MessageSyncHealthBox.MessageSyncHealthBoxHandler.class, MessageSyncHealthBox.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(MessageWithdrawKits.MessageWithdrawKitsHandler.class, MessageWithdrawKits.class, id++, Side.SERVER);
		INSTANCE.registerMessage(MessageUseHealthKit.MessageUseHealthKitHandler.class, MessageUseHealthKit.class, id++, Side.SERVER);
		INSTANCE.registerMessage(MessageSyncHealthRegen.MessageSyncHealthRegenHandler.class, MessageSyncHealthRegen.class, id++, Side.CLIENT);
	}
}
