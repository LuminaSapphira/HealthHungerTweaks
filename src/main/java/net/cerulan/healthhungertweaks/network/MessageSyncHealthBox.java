package net.cerulan.healthhungertweaks.network;

import io.netty.buffer.ByteBuf;
import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.cerulan.healthhungertweaks.capability.HealthBoxCapabilityHandler;
import net.cerulan.healthhungertweaks.capability.IHealthBoxCapability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncHealthBox implements IMessage{

	public static class MessageSyncHealthBoxHandler implements IMessageHandler<MessageSyncHealthBox, IMessage>{

		@Override
		public IMessage onMessage(MessageSyncHealthBox message, MessageContext ctx) {
			HealthHungerTweaks.sidedProxy.getThreadFromContext(ctx).addScheduledTask(() -> {
				EntityPlayer pl = HealthHungerTweaks.sidedProxy.getPlayerEntity(ctx);
				IHealthBoxCapability cap = pl.getCapability(HealthBoxCapabilityHandler.HEALTH_BOX, null);
				cap.setHealthKits(message.health);
				
			});
			return null;
		}
		
	}
	
	private int[] health;
	
	public MessageSyncHealthBox() {
		
	}
	
	public MessageSyncHealthBox(int[] health) {
		this.health = health;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.health = new int[] {buf.readInt(), buf.readInt(), buf.readInt()};
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		for (int i = 0; i < 3; i++) {
			buf.writeInt(health[i]);
			
			// TODO Low priority: dynamic # of kits
		}
	}

}
