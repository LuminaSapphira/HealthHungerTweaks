package net.cerulan.healthhungertweaks.capability;

import net.cerulan.healthhungertweaks.ModInfo;
import net.cerulan.healthhungertweaks.network.HealthHungerPacketHandler;
import net.cerulan.healthhungertweaks.network.MessageSyncHealthBox;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HealthBoxCapabilityHandler {
	@CapabilityInject(IHealthBoxCapability.class)
    public static final Capability<IHealthBoxCapability> HEALTH_BOX = null;
	
	class ProviderHealthBox implements ICapabilitySerializable<NBTBase> {

		private IHealthBoxCapability instance = HEALTH_BOX.getDefaultInstance();
		
		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			if (HEALTH_BOX != null && capability == HEALTH_BOX) return true;
			return false;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if (hasCapability(capability, facing)) return HEALTH_BOX.cast(instance);
			return null;
		}

		@Override
		public NBTBase serializeNBT() {
			return HEALTH_BOX.getStorage().writeNBT(HEALTH_BOX, instance, null);
		}

		@Override
		public void deserializeNBT(NBTBase nbt) {
			HEALTH_BOX.getStorage().readNBT(HEALTH_BOX, instance, null, nbt);			
		}

	}
	
	@SubscribeEvent
	public void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) {
			event.addCapability(new ResourceLocation(ModInfo.MODID, "health_box"), new ProviderHealthBox());
		}
	}
	
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
		if(!event.player.worldObj.isRemote) {
			EntityPlayerMP player = (EntityPlayerMP)event.player;
			int[] health = player.getCapability(HEALTH_BOX, null).getHealthKits();
			HealthHungerPacketHandler.INSTANCE.sendTo(new MessageSyncHealthBox(health), player);
		}
	}
	
	public void register() {
		CapabilityManager.INSTANCE.register(IHealthBoxCapability.class, new HealthBoxStorage(), HealthBoxCapability.class);
	}
	
}
