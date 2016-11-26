package net.cerulan.healthhungertweaks.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageOpenHealthBox implements IMessage {

	public MessageOpenHealthBox() {
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}

}
