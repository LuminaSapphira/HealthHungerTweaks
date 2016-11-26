package net.cerulan.healthhungertweaks;

import net.cerulan.healthhungertweaks.client.KeyBindings;
import net.cerulan.healthhungertweaks.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {

	public ClientProxy() {
		creativeTab = new CreativeTabs(ModInfo.MODID) {

				@Override
				@SideOnly(Side.CLIENT)
				public Item getTabIconItem() {
					return ModItems.itemHealthKit;
				}
				
			};
		
	}
	
	@Override
	public void init() {
		super.init();
		KeyBindings.load();
		KeyBindings bindings = new KeyBindings();
		MinecraftForge.EVENT_BUS.register(bindings);
	}
	
	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx));
	}

	@Override
	public IThreadListener getThreadFromContext(MessageContext ctx) {
		return (ctx.side.isClient() ? Minecraft.getMinecraft() : super.getThreadFromContext(ctx));
	}
	
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(ModInfo.MODID + ":" + id + "." + meta, "inventory"));
	}
	
}
