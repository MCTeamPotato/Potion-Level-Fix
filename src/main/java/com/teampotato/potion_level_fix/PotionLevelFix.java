package com.teampotato.potion_level_fix;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(PotionLevelFix.MODID)
public class PotionLevelFix {
    public static final String MODID = "potion_level_fix";
    public static final Logger LOGGER = LoggerFactory.getLogger("PotionLevelFix");
    public static ModConfigSpec CONFIG;
    public static ModConfigSpec.BooleanValue EFFECT_NUMBER;
    public static ModConfigSpec.BooleanValue POTION_NUMBER;
    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
            builder.push("Potion Level Fix");
        EFFECT_NUMBER = builder
                .comment("If true, effect display using Roman numerals (vanilla); if false, display using Arabic numerals.")
                .define("Effect Number Type", true);
        POTION_NUMBER = builder
                .comment("If true, potion display using Roman numerals (vanilla); if false, display using Arabic numerals.")
                .define("Potion Number Type", true);
        builder.pop();
        CONFIG = builder.build();
    }
    public PotionLevelFix(ModContainer modContainer, IEventBus iEventBus) {
        modContainer.registerConfig(ModConfig.Type.COMMON, CONFIG);
        if (FMLLoader.getDist() == Dist.CLIENT) {
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }
    }
}
