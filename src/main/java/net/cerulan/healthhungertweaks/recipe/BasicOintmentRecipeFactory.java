package net.cerulan.healthhungertweaks.recipe;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.cerulan.healthhungertweaks.ModInfo;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class BasicOintmentRecipeFactory implements IRecipeFactory {

	@Override
	public IRecipe parse(JsonContext context, JsonObject json) {
		ShapelessOreRecipe recipe = ShapelessOreRecipe.factory(context, json);
		
		List<Ingredient> retainIng = new ArrayList<Ingredient>();
		
		for (JsonElement ele : JsonUtils.getJsonArray(json, "retained_ingredients")) {
			retainIng.add(CraftingHelper.getIngredient(ele, context));
		}
		
		return new BasicOintmentRecipe(new ResourceLocation(ModInfo.MODID, "basic_ointment_crafting"), recipe.getIngredients(), recipe.getRecipeOutput(), retainIng);
	}
	
	public static class BasicOintmentRecipe extends ShapelessOreRecipe {
		
		List<Ingredient> toRetain;
		
		public BasicOintmentRecipe(ResourceLocation group, NonNullList<Ingredient> input, ItemStack result, List<Ingredient> toRetain) {			
			super(group, input, result);
			this.toRetain = toRetain;
		}
		
		@Override
		public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
			NonNullList<ItemStack> remaining = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);
			for (int i = 0; i < inv.getSizeInventory(); ++i) {
								
				
				ItemStack slot = inv.getStackInSlot(i);
				
				
				for (Ingredient next : toRetain) {
					if(next.apply(slot)) {
						ItemStack copy = slot.copy();
						copy.setCount(1);
						remaining.set(i, copy);
						break;
					}
				}		
				
			}
			return remaining;
		}
		
	}

}
