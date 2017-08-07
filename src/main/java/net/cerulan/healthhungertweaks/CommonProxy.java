package net.cerulan.healthhungertweaks;

import net.cerulan.healthhungertweaks.capability.healthbox.HealthBoxCapabilityHandler;
import net.cerulan.healthhungertweaks.capability.healthregen.HealthRegenCapabilityHandler;
import net.cerulan.healthhungertweaks.gui.GuiHandler;
import net.cerulan.healthhungertweaks.handler.HealthHungerHandler;
import net.cerulan.healthhungertweaks.network.HealthHungerPacketHandler;
import net.cerulan.healthhungertweaks.potion.PotionSatiated;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class CommonProxy {

	public PotionSatiated potionSatiated;
	public HealthHungerPacketHandler packetHandler;
	
	protected HealthHungerHandler handler; 
	
	public CreativeTabs creativeTab;

	
	public void preInit() {
		potionSatiated = new PotionSatiated();
		
		ForgeRegistries.POTIONS.register(potionSatiated);
	}
	
	public void init() {
		handler =  new HealthHungerHandler();
		MinecraftForge.EVENT_BUS.register(handler);
		packetHandler = new HealthHungerPacketHandler();
		NetworkRegistry.INSTANCE.registerGuiHandler(HealthHungerTweaks.instance, new GuiHandler());
		
		HealthBoxCapabilityHandler healthBoxCapHand = new HealthBoxCapabilityHandler();
		healthBoxCapHand.register();
		MinecraftForge.EVENT_BUS.register(healthBoxCapHand);	
		
		HealthRegenCapabilityHandler healthRegenCapHand = new HealthRegenCapabilityHandler();
		healthRegenCapHand.register();
		MinecraftForge.EVENT_BUS.register(healthRegenCapHand);
	}

	/**
	 * Returns a side-appropriate EntityPlayer for use during message handling
	 */
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return ctx.getServerHandler().player;
	}

	/**
	 * Returns the current thread based on side during message handling, used
	 * for ensuring that the message is being handled by the main thread
	 */
	public IThreadListener getThreadFromContext(MessageContext ctx) {
		return ctx.getServerHandler().player.getServer();
	}

	public void registerItemRenderer(Item item, int meta, String id) {
	}
	
}
