package net.cerulan.healthhungertweaks.recipe;

import com.google.gson.JsonObject;
import net.cerulan.healthhungertweaks.HHTConfigCommon;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

import java.util.function.BooleanSupplier;

public class ConfigConditionFactory implements IConditionFactory {

    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json) {
        String key = JsonUtils.getString(json, "key");
        return () -> {
            switch (key) {
                case "health_kit":
                    return HHTConfigCommon.mending.enableHealthKit;
                default:
                    return true;
            }
        };
    }

}
