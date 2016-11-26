package net.cerulan.healthhungertweaks.network;

import net.cerulan.healthhungertweaks.ModInfo;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class HealthHungerPacketHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.MODID);
	
	public HealthHungerPacketHandler() {
		INSTANCE.registerMessage(MessageOpenHealthBoxHandler.class, MessageOpenHealthBox.class, 0, Side.SERVER);
		INSTANCE.registerMessage(MessageSyncHealthBox.MessageSyncHealthBoxHandler.class, MessageSyncHealthBox.class, 1, Side.CLIENT);
		INSTANCE.registerMessage(MessageWithdrawKits.MessageWithdrawKitsHandler.class, MessageWithdrawKits.class, 2, Side.SERVER);
	}
}
