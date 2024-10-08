package com.teampotato.potion_level_fix;

import com.teampotato.potion_level_fix.network.NetworkHandler;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(PotionLevelFix.MODID)
@Mod.EventBusSubscriber(modid = PotionLevelFix.MODID)
public class PotionLevelFix {
    public static final String MODID = "potion_level_fix";
    public static final Logger LOGGER = LoggerFactory.getLogger("PotionLevelFix");
    public static ForgeConfigSpec CONFIG;
    public static ForgeConfigSpec.BooleanValue LANG;
    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("Potion Level Fix");
        LANG = builder
                .comment("If true, display using Roman numerals (vanilla); if false, display using Arabic numerals.")
                .define("Number type", true);
        builder.pop();
        CONFIG = builder.build();
    }
    public PotionLevelFix() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CONFIG);
        NetworkHandler.register();
    }
}
