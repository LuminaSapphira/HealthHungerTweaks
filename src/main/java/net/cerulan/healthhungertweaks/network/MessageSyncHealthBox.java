package net.cerulan.healthhungertweaks.network;

import io.netty.buffer.ByteBuf;
import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.cerulan.healthhungertweaks.capability.healthbox.HealthBoxCapabilityHandler;
import net.cerulan.healthhungertweaks.capability.healthbox.IHealthBoxCapability;
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
				cap.setHealthKitCount(message.health);
				cap.setCooldown(message.cooldown);
				
			});
			return null;
		}
		
	}
	
	private int health;
	private int cooldown;
	
	public MessageSyncHealthBox() {
		
	}
	
	public MessageSyncHealthBox(int health, int cooldown) {
		this.health = health;
		this.cooldown = cooldown;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.health = buf.readInt();
		this.cooldown = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(health);
		buf.writeInt(cooldown);
	}

}
