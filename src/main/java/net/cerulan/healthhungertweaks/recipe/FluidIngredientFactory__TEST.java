package net.cerulan.healthhungertweaks.recipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FluidIngredientFactory__TEST implements IIngredientFactory {

	@Override
	public Ingredient parse(JsonContext context, JsonObject json) {
		Fluid fluid = FluidRegistry.getFluid(JsonUtils.getString(json, "fluid"));
		int amount = JsonUtils.getInt(json, "amount");
		return new FluidIngredient(new FluidStack(fluid, amount));
	}
	
	public static class FluidIngredient extends Ingredient {
		FluidStack fluidStack;
		
		private ItemStack[] array = null;
		private IntList itemIds = null;
		
		public FluidIngredient(FluidStack fluidstack) {
			this.fluidStack = fluidstack;
		}
		
		@Override
		@Nonnull
		public ItemStack[] getMatchingStacks() {
			if (array == null) {
				this.array = new ItemStack[] { FluidUtil.getFilledBucket(fluidStack) };
			}
			return array;
		
		}
		
		@Override
		@Nonnull
		@SideOnly(Side.CLIENT)
		public IntList getValidItemStacksPacked() {
			if (this.itemIds == null) {
				this.itemIds = new IntArrayList(1);
				this.itemIds.add(RecipeItemHelper.pack(FluidUtil.getFilledBucket(fluidStack)));
			}
			return this.itemIds;
		}
		
		@Override
		public boolean apply(@Nullable ItemStack input) {
			if (input == null) return false;
			
			IFluidHandlerItem cap = input.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			if (cap != null) {
				FluidStack fl = cap.drain(fluidStack, false);
				if (fl != null) {
					return fl.amount >= 0;
				}
			}
			
			return false;

			
		}
		
	}

}
