package net.cerulan.healthhungertweaks.client;

import org.lwjgl.input.Keyboard;

import net.cerulan.healthhungertweaks.network.HealthHungerPacketHandler;
import net.cerulan.healthhungertweaks.network.MessageOpenHealthBox;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class KeyBindings {
    public static KeyBinding openHealthBox;

    public static void load() {
    	openHealthBox = new KeyBinding("key.openhealthbox", Keyboard.KEY_GRAVE, "key.category.healthhungertweaks");
        ClientRegistry.registerKeyBinding(openHealthBox);
    }

    @SubscribeEvent
	public void playerTick(ClientTickEvent event) {
		if(event.phase == Phase.START) {
			if(openHealthBox.isKeyDown()) {				
				HealthHungerPacketHandler.INSTANCE.sendToServer(new MessageOpenHealthBox());
			}
		}
	}
}