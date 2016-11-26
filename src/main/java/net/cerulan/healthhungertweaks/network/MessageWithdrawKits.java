package net.cerulan.healthhungertweaks.network;

import io.netty.buffer.ByteBuf;
import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.cerulan.healthhungertweaks.capability.HealthBoxCapabilityHandler;
import net.cerulan.healthhungertweaks.capability.IHealthBoxCapability;
import net.cerulan.healthhungertweaks.item.ModItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageWithdrawKits implements IMessage {
	
	public static class MessageWithdrawKitsHandler implements IMessageHandler<MessageWithdrawKits, IMessage>{

		@Override
		public IMessage onMessage(MessageWithdrawKits message, MessageContext ctx) {
			HealthHungerTweaks.sidedProxy.getThreadFromContext(ctx).addScheduledTask(() -> {
				IHealthBoxCapability healthbox = HealthHungerTweaks.sidedProxy.getPlayerEntity(ctx).getCapability(HealthBoxCapabilityHandler.HEALTH_BOX, null);
				if (message.amount > 0) {
					int[] kits = healthbox.getHealthKits();
					int kitindex = MathHelper.clamp_int(message.kit, 0, 2);
					int removeAmount = MathHelper.clamp_int(message.amount, 0, Math.min(kits[kitindex], 64));
					kits[kitindex] = kits[kitindex] - removeAmount;
					healthbox.setHealthKits(kits);

					EntityPlayer ply = ctx.getServerHandler().playerEntity;
					ply.worldObj.spawnEntityInWorld(new EntityItem(ply.worldObj, ply.posX, ply.posY, ply.posZ,
							new ItemStack(ModItems.itemHealthKit, removeAmount, kitindex)));

					HealthHungerPacketHandler.INSTANCE.sendTo(new MessageSyncHealthBox(healthbox.getHealthKits()),
							ctx.getServerHandler().playerEntity);
				}
			});
			return null;
		}
		
	}
	
	private int kit = 0;
	private int amount = 0;

	public MessageWithdrawKits() {}
	public MessageWithdrawKits(int kit, int amount) {
		this.kit = kit;
		this.amount = amount;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		kit = buf.readInt();
		amount = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(kit);
		buf.writeInt(amount);
	}

}
