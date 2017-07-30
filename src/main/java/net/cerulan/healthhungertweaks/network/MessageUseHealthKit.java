package net.cerulan.healthhungertweaks.network;

import io.netty.buffer.ByteBuf;
import net.cerulan.healthhungertweaks.HealthHungerTweaks;
import net.cerulan.healthhungertweaks.HealthKitStats;
import net.cerulan.healthhungertweaks.capability.healthbox.HealthBoxCapabilityHandler;
import net.cerulan.healthhungertweaks.capability.healthbox.IHealthBoxCapability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
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
				int[] kits = healthbox.getHealthKits();
				int useKit = -1;
				
				if (message.kit == -1 && healthbox.getCooldown() == 0) {
					int prelimKit = -1;
					// TODO config health values
					float health = pl.getHealth();
					if (health >= pl.getMaxHealth() - HealthKitStats.PRIMITIVE.getRestored() ) {
						prelimKit = 0;
					}
					else if (health >= pl.getMaxHealth() - HealthKitStats.STANDARD.getRestored()) {
						prelimKit = 1;
					}
					else if (health > 0f) {
						prelimKit = 2;
					}
					final int maxkit = 2;
					final int minkit = 0;
					boolean foundkit = false;
					int resultkit = -1;
					for (int searchkit = prelimKit; searchkit <= maxkit && !foundkit; searchkit++) {
						if (kits[searchkit] > 0) {
							foundkit = true;
							resultkit = searchkit;
						}						
					}
					if (!foundkit) {
						for (int searchkit = prelimKit; searchkit >= minkit && !foundkit; searchkit--) {
							if (kits[searchkit] > 0) {
								foundkit = true;
								resultkit = searchkit;
							}
						}
					}
					
					useKit = resultkit;
					
				}
				else if ((message.kit >= 0 && message.kit <= 2) && healthbox.getCooldown() == 0) {
					if (kits[message.kit] > 0) useKit = message.kit;
				}
				
				if (useKit != -1) {
					kits[useKit] = kits[useKit] - 1;
					healthbox.setHealthKits(kits);
					healthbox.setCooldown(HealthHungerTweaks.instance.configHandler.getHealthKitCooldown());
					HealthHungerPacketHandler.INSTANCE.sendTo(new MessageSyncHealthBox(healthbox.getHealthKits(), healthbox.getCooldown()),
							ctx.getServerHandler().player);
					
					HealthKitStats stats = HealthKitStats.values()[useKit];
					float healAmount = stats.getRestored();
					healAmount = MathHelper.clamp(healAmount, 0, pl.getMaxHealth() - pl.getHealth());
					pl.heal(healAmount);
					ITextComponent text = new TextComponentString(new TextComponentTranslation("healthkit.useHealthKit").getFormattedText()
							.replace("${item}", new TextComponentTranslation(String.format("item.health_kit.%1$d.name", useKit)).getFormattedText()));
					text.getStyle().setColor(TextFormatting.AQUA);
					pl.sendMessage(text);
				}
			});
			return null;
		}
		
	}

	// kit index, or -1 for auto
	private int kit;
	
	public MessageUseHealthKit() {
	}
	
	/**
	 * Create a new instance of MessageUseHealthKit
	 * @param kit The kit index, or -1 for auto select
	 */
	public MessageUseHealthKit(int kit) {
		this.kit = kit;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		kit = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(kit);
	}

}
