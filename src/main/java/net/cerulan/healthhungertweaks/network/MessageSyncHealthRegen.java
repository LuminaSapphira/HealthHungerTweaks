package net.cerulan.healthhungertweaks.network;

import io.netty.buffer.ByteBuf;
import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.cerulan.healthhungertweaks.capability.healthregen.HealthRegenCapabilityHandler;
import net.cerulan.healthhungertweaks.capability.healthregen.IHealthRegenCapability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncHealthRegen implements IMessage{

	public static class MessageSyncHealthRegenHandler implements IMessageHandler<MessageSyncHealthRegen, IMessage>{

		@Override
		public IMessage onMessage(MessageSyncHealthRegen message, MessageContext ctx) {
			HealthHungerTweaks.sidedProxy.getThreadFromContext(ctx).addScheduledTask(() -> {
				EntityPlayer pl = HealthHungerTweaks.sidedProxy.getPlayerEntity(ctx);
				IHealthRegenCapability cap = pl.getCapability(HealthRegenCapabilityHandler.HEALTH_REGEN, null);
				cap.setData(message.untilStart, message.untilNext);
				
			});
			return null;
		}
		
	}
	
	private int untilStart, untilNext;
	
	public MessageSyncHealthRegen() {
		
	}
	
	public MessageSyncHealthRegen(int untilStart, int untilNext) {
		this.untilStart = untilStart;
		this.untilNext = untilNext;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.untilStart = buf.readInt();
		this.untilNext = buf.readInt();
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(untilStart);
		buf.writeInt(untilNext);
	}

}