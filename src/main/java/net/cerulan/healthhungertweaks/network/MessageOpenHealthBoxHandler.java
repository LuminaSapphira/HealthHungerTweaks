package net.cerulan.healthhungertweaks.network;

import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageOpenHealthBoxHandler implements IMessageHandler<MessageOpenHealthBox, IMessage> {

	@Override
	public IMessage onMessage(MessageOpenHealthBox message, MessageContext ctx) {
		ctx.getServerHandler().playerEntity.openGui(HealthHungerTweaks.instance, 0, ctx.getServerHandler().playerEntity.worldObj, 0, 0, 0);
		return null;
	}

}
