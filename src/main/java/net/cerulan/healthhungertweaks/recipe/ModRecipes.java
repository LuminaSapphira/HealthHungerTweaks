package net.cerulan.healthhungertweaks.recipe;

public class ModRecipes {

	public static void init() {
		//GameRegistry.addShapedRecipe(output, params)
		/*class BasicOintmentRecipe extends ShapelessRetainingRecipe {

			public BasicOintmentRecipe(ItemStack output, ItemStack... inputList) {
				super(output, inputList);
			}

			@Override
			public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
				NonNullList<ItemStack> remaining = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);

				for (int i = 0; i < inv.getSizeInventory(); ++i) {
					ItemStack itemstack = inv.getStackInSlot(i);
					if (itemstack.isItemEqual(new ItemStack(Items.BOWL))) {
						remaining.set(i, new ItemStack(Items.BOWL, 1));
					}				
				}
				return remaining;
			}
			
		}
		
		class RefinedOintmentRecipe extends ShapelessRetainingRecipe {

			public RefinedOintmentRecipe(ItemStack output, ItemStack... inputList) {
				super(output, inputList);
			}

			@Override
			public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
				NonNullList<ItemStack> remaining = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);
				for (int i = 0; i < inv.getSizeInventory(); ++i) {
					ItemStack itemstack = inv.getStackInSlot(i);
					if (itemstack.isItemEqual(new ItemStack(Items.WATER_BUCKET))) {
						remaining.set(i, new ItemStack(Items.BUCKET, 1));					
					}
				}
				return remaining;
			}
			
		}*/
		/*
		GameRegistry.addRecipe(new BasicOintmentRecipe(new ItemStack(ModItems.itemOintment, 2, 0), new ItemStack(Items.WHEAT_SEEDS, 1), new ItemStack(Items.WHEAT_SEEDS, 1), new ItemStack(Items.WHEAT_SEEDS, 1), new ItemStack(Items.BOWL, 1)));
		GameRegistry.addRecipe(new BasicOintmentRecipe(new ItemStack(ModItems.itemOintment, 3, 0), new ItemStack(Blocks.CACTUS, 1), new ItemStack(Items.BOWL, 1)));
		GameRegistry.addRecipe(new BasicOintmentRecipe(new ItemStack(ModItems.itemOintment, 3, 0), new ItemStack(Blocks.RED_FLOWER, 1), new ItemStack(Items.BOWL, 1)));
		GameRegistry.addRecipe(new BasicOintmentRecipe(new ItemStack(ModItems.itemOintment, 3, 0), new ItemStack(Blocks.YELLOW_FLOWER, 1), new ItemStack(Items.BOWL, 1)));
		GameRegistry.addRecipe(new RefinedOintmentRecipe(new ItemStack(ModItems.itemOintment, 4, 1), new ItemStack(Items.DYE, 1, 2), new ItemStack(Items.WATER_BUCKET, 1)));
		
		// TODO find more oredict tags
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.itemMedicalTools, 1), Items.SHEARS, "ingotIron", "ingotIron"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.itemBandage, 3, 0), "stickWood", "stickWood", "stickWood"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.itemBandage, 3, 1), Blocks.WOOL, Items.PAPER));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.itemHealthKit, 3, 0), Blocks.DIRT, Blocks.DIRT, Blocks.DIRT, new ItemStack(ModItems.itemBandage, 1, 0)));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ItemStack(ModItems.itemHealthKit, 3, 1), " L ", "wpw", " L ", 'L', "logWood", 'w', new ItemStack(ModItems.itemBandage, 1, 0), 'p', new ItemStack(ModItems.itemOintment, 1, 0) ));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ItemStack(ModItems.itemHealthKit, 3, 2), " M ", "bpb", " I ", 'M', ModItems.itemMedicalTools, 'I', "ingotIron", 'b', new ItemStack(ModItems.itemBandage, 1, 1), 'p', new ItemStack(ModItems.itemOintment, 1, 1) ));*/
		
		
	}
	
}
