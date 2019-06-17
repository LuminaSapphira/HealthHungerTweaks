package net.cerulan.healthhungertweaks.client;

import net.cerulan.healthhungertweaks.HHTConfigCommon;
import org.lwjgl.input.Keyboard;

import net.cerulan.healthhungertweaks.network.HealthHungerPacketHandler;
import net.cerulan.healthhungertweaks.network.MessageOpenHealthBox;
import net.cerulan.healthhungertweaks.network.MessageUseHealthKit;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyBindings {
    public static KeyBinding openHealthBox;
    public static KeyBinding useHealthKit;

    public static void load() {
    	if (HHTConfigCommon.mending.enableHealthKit) {
			openHealthBox = new KeyBinding("key.openhealthbox", Keyboard.KEY_GRAVE, "key.category.healthhungertweaks");
			ClientRegistry.registerKeyBinding(openHealthBox);

			useHealthKit = new KeyBinding("key.usehealthkit", Keyboard.KEY_C, "key.category.healthhungertweaks");
			ClientRegistry.registerKeyBinding(useHealthKit);
		}
    }
    
    @SubscribeEvent    
	public void keyEvent(KeyInputEvent event) {
		if (HHTConfigCommon.mending.enableHealthKit) {
			if (openHealthBox.isPressed()) {
				HealthHungerPacketHandler.INSTANCE.sendToServer(new MessageOpenHealthBox());
			}
			if (useHealthKit.isPressed()) {
				HealthHungerPacketHandler.INSTANCE.sendToServer(new MessageUseHealthKit());
			}
		}
	}
}