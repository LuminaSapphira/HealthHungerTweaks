/*package net.cerulan.healthhungertweaks.recipe;

import java.util.Arrays;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;

public abstract class ShapelessRetainingRecipe extends ShapelessRecipes {
	
	public ShapelessRetainingRecipe(ItemStack output, ItemStack... inputList) {
		super(output, Arrays.asList(inputList));
	}

	/**
	 * <b>Standard implementation</b>
	 * <br />
	 * ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()]; <br /><br />
	 * 
	 * for (int i = 0; i < aitemstack.length; ++i) { <br />
	 *     ItemStack itemstack = inv.getStackInSlot(i); <br />
	 *     // [[ some condition ]] aitemstack[i] = null [[OR]] aitemstack[i] = ~some itemstack~ ;<br />
	 * } <br />
	 * 
	 * return aitemstack;
	 */
	/*@Override
	public abstract NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv);
    

}
*/