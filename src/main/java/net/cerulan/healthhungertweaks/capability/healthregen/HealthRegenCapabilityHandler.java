package net.cerulan.healthhungertweaks.capability.healthregen;

import net.cerulan.healthhungertweaks.ModInfo;
import net.cerulan.healthhungertweaks.network.HealthHungerPacketHandler;
import net.cerulan.healthhungertweaks.network.MessageSyncHealthRegen;
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
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class HealthRegenCapabilityHandler {

	@CapabilityInject(IHealthRegenCapability.class)
    public static final Capability<IHealthRegenCapability> HEALTH_REGEN = null;
	
	class ProviderHealthRegen implements ICapabilitySerializable<NBTBase> {

		private IHealthRegenCapability instance = HEALTH_REGEN.getDefaultInstance();
		
		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			if (HEALTH_REGEN != null && capability == HEALTH_REGEN) return true;
			return false;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if (hasCapability(capability, facing)) return HEALTH_REGEN.cast(instance);
			return null;
		}

		@Override
		public NBTBase serializeNBT() {
			return HEALTH_REGEN.getStorage().writeNBT(HEALTH_REGEN, instance, null);
		}

		@Override
		public void deserializeNBT(NBTBase nbt) {
			HEALTH_REGEN.getStorage().readNBT(HEALTH_REGEN, instance, null, nbt);			
		}

	}
	
	@SubscribeEvent
	public void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) {
			event.addCapability(new ResourceLocation(ModInfo.MODID, "health_regen"), new ProviderHealthRegen());
		}
	}
	
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
		if(!event.player.world.isRemote) {
			EntityPlayerMP player = (EntityPlayerMP)event.player;
			int ticksUntilStart = player.getCapability(HEALTH_REGEN, null).getTicksUntilRegenStart();
			int ticksUntilRegen = player.getCapability(HEALTH_REGEN, null).getTicksUntilNextRegen();
			boolean kitRegen = player.getCapability(HEALTH_REGEN, null).isKitRegen();
			HealthHungerPacketHandler.INSTANCE.sendTo(new MessageSyncHealthRegen(ticksUntilStart, ticksUntilRegen, kitRegen), player);
		}
	}
	
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event) {
		if (event.isWasDeath()) {
			IHealthRegenCapability cap = event.getOriginal().getCapability(HEALTH_REGEN, null);
			event.getEntityPlayer().getCapability(HEALTH_REGEN, null).setData(cap.getTicksUntilRegenStart(), cap.getTicksUntilNextRegen());
		}
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof EntityPlayerMP && !event.getEntity().world.isRemote) {
			IHealthRegenCapability cap = event.getEntity().getCapability(HEALTH_REGEN, null);
			int ticksUntilStart = cap.getTicksUntilRegenStart();
			int ticksUntilRegen = cap.getTicksUntilNextRegen();
			boolean kitRegen = cap.isKitRegen();
			HealthHungerPacketHandler.INSTANCE.sendTo(new MessageSyncHealthRegen(ticksUntilStart, ticksUntilRegen, kitRegen), (EntityPlayerMP)event.getEntity());
		}
	}
	
	public void register() {
		CapabilityManager.INSTANCE.register(IHealthRegenCapability.class, new HealthRegenCapability.HealthRegenStorage(), HealthRegenCapability::new);
	}
	
	
}
