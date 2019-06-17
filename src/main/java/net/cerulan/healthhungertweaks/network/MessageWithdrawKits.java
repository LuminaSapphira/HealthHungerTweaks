package net.cerulan.healthhungertweaks.network;

import io.netty.buffer.ByteBuf;
import net.cerulan.healthhungertweaks.HHTConfigCommon;
import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.cerulan.healthhungertweaks.capability.healthbox.HealthBoxCapabilityHandler;
import net.cerulan.healthhungertweaks.capability.healthbox.IHealthBoxCapability;
import net.cerulan.healthhungertweaks.item.ModItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageWithdrawKits implements IMessage {
	
	public static class MessageWithdrawKitsHandler implements IMessageHandler<MessageWithdrawKits, IMessage>{

		@Override
		public IMessage onMessage(MessageWithdrawKits message, MessageContext ctx) {
			if (!HHTConfigCommon.mending.enableHealthKit) return null;
			HealthHungerTweaks.sidedProxy.getThreadFromContext(ctx).addScheduledTask(() -> {
				IHealthBoxCapability healthbox = HealthHungerTweaks.sidedProxy.getPlayerEntity(ctx).getCapability(HealthBoxCapabilityHandler.HEALTH_BOX, null);
				if (message.amount > 0) {
					int kits = healthbox.getHealthKitCount();;
					int removeAmount = MathHelper.clamp(message.amount, 0, Math.min(kits, 64));
					kits -= removeAmount;
					healthbox.setHealthKitCount(kits);

					EntityPlayer ply = ctx.getServerHandler().player;
					ply.world.spawnEntity(new EntityItem(ply.world, ply.posX, ply.posY, ply.posZ,
							new ItemStack(ModItems.itemHealthKit, removeAmount)));

					HealthHungerPacketHandler.INSTANCE.sendTo(new MessageSyncHealthBox(healthbox.getHealthKitCount(), healthbox.getCooldown()),
							ctx.getServerHandler().player);
				}
			});
			return null;
		}
		
	}
	
	private int amount = 0;

	public MessageWithdrawKits() {}
	public MessageWithdrawKits(int amount) {
		this.amount = amount;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		amount = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(amount);
	}

}
