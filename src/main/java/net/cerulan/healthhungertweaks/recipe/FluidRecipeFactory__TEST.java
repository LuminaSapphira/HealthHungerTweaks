package net.cerulan.healthhungertweaks.recipe;

import com.google.gson.JsonObject;

import net.cerulan.healthhungertweaks.ModInfo;
import net.cerulan.healthhungertweaks.recipe.FluidIngredientFactory__TEST.FluidIngredient;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class FluidRecipeFactory__TEST implements IRecipeFactory {

	@Override
	public IRecipe parse(JsonContext context, JsonObject json) {
		ShapelessOreRecipe recipe = ShapelessOreRecipe.factory(context, json);
		
		return new RefinedOintmentRecipe(new ResourceLocation(ModInfo.MODID, "refined_ointment_crafting"), recipe.getIngredients(), recipe.getRecipeOutput());
	}
	
	public static class RefinedOintmentRecipe extends ShapelessOreRecipe {
		
		
		public RefinedOintmentRecipe(ResourceLocation group, NonNullList<Ingredient> input, ItemStack result) {			
			super(group, input, result);
			
		}
		
		@Override
		public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
			NonNullList<ItemStack> remaining = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);
			for (int i = 0; i < inv.getSizeInventory(); ++i) {
								
				
				ItemStack slot = inv.getStackInSlot(i);
				
				
				for (Ingredient next : getIngredients()) {
					if(next instanceof FluidIngredient && next.apply(slot)) {
						IFluidHandlerItem ni = slot.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
						ni.drain(((FluidIngredient)next).fluidStack.copy(), true);
						ItemStack ns = ni.getContainer();
						remaining.set(i, ns);
						break;
					}
				}		
				
			}
			return remaining;
		}
		
	}

}
