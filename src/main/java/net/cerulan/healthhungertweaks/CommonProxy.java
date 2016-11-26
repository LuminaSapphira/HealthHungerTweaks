package net.cerulan.healthhungertweaks;

import net.cerulan.healthhungertweaks.capability.HealthBoxCapabilityHandler;
import net.cerulan.healthhungertweaks.gui.GuiHandler;
import net.cerulan.healthhungertweaks.handler.HealthHungerHandler;
import net.cerulan.healthhungertweaks.network.HealthHungerPacketHandler;
import net.cerulan.healthhungertweaks.potion.PotionMending;
import net.cerulan.healthhungertweaks.potion.PotionSatiated;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

	public PotionSatiated potionSatiated;
	public PotionMending potionMending;
	public HealthHungerPacketHandler packetHandler;
	
	protected HealthHungerHandler handler; 
	
	public void preInit() {
		potionSatiated = new PotionSatiated();
		potionMending = new PotionMending();
		
		GameRegistry.register(potionSatiated);
		GameRegistry.register(potionMending);
	}
	
	public void init() {
		HealthHungerTweaks.Log.info("hht prox Init");
		handler =  new HealthHungerHandler();
		MinecraftForge.EVENT_BUS.register(handler);
		packetHandler = new HealthHungerPacketHandler();
		NetworkRegistry.INSTANCE.registerGuiHandler(HealthHungerTweaks.instance, new GuiHandler());
		
		HealthBoxCapabilityHandler healthBoxCapHand = new HealthBoxCapabilityHandler();
		healthBoxCapHand.register();
		MinecraftForge.EVENT_BUS.register(healthBoxCapHand);		
	}

	/**
	 * Returns a side-appropriate EntityPlayer for use during message handling
	 */
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity;
	}

	/**
	 * Returns the current thread based on side during message handling, used
	 * for ensuring that the message is being handled by the main thread
	 */
	public IThreadListener getThreadFromContext(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity.getServer();
	}
	
}
