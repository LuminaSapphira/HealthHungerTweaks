package net.cerulan.healthhungertweaks.network;

import io.netty.buffer.ByteBuf;
import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.cerulan.healthhungertweaks.capability.healthbox.HealthBoxCapabilityHandler;
import net.cerulan.healthhungertweaks.capability.healthbox.IHealthBoxCapability;
import net.cerulan.healthhungertweaks.capability.healthregen.HealthRegenCapabilityHandler;
import net.cerulan.healthhungertweaks.capability.healthregen.IHealthRegenCapability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageUseHealthKit implements IMessage {
	
	public static class MessageUseHealthKitHandler implements IMessageHandler<MessageUseHealthKit, IMessage>{

		@Override
		public IMessage onMessage(MessageUseHealthKit message, MessageContext ctx) {
			HealthHungerTweaks.sidedProxy.getThreadFromContext(ctx).addScheduledTask(() -> {
				EntityPlayer pl = ctx.getServerHandler().player;
				IHealthBoxCapability healthbox = pl.getCapability(HealthBoxCapabilityHandler.HEALTH_BOX, null);
				int kits = healthbox.getHealthKitCount();
				
				if (healthbox.getCooldown() == 0) {
					kits--;
					healthbox.setHealthKitCount(kits);
					healthbox.setCooldown(HealthHungerTweaks.instance.configHandler.getHealthKitCooldown());
					HealthHungerPacketHandler.INSTANCE.sendTo(new MessageSyncHealthBox(healthbox.getHealthKitCount(), healthbox.getCooldown()),
							ctx.getServerHandler().player);
					
					/*HealthKitStats stats = HealthKitStats.values()[useKit];
					float healAmount = stats.getRestored();
					healAmount = MathHelper.clamp(healAmount, 0, pl.getMaxHealth() - pl.getHealth());
					
					pl.heal(healAmount);*/
					IHealthRegenCapability regen = pl.getCapability(HealthRegenCapabilityHandler.HEALTH_REGEN, null);
					regen.setTicksUntilRegenStart(0);
					ITextComponent text = new TextComponentString(new TextComponentTranslation("healthkit.useHealthKit").getFormattedText()
							.replace("${item}", new TextComponentTranslation(String.format("item.health_kit_regen.name")).getFormattedText()));
					text.getStyle().setColor(TextFormatting.AQUA);
					pl.sendMessage(text);
				}
			});
			return null;
		}
		
	}
	
	public MessageUseHealthKit() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
	}
}
