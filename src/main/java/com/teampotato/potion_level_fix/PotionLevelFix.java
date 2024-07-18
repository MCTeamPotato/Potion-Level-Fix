package com.teampotato.potion_level_fix;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Mod(PotionLevelFix.MODID)
@Mod.EventBusSubscriber(modid = PotionLevelFix.MODID)
public class PotionLevelFix {
    public static final String MODID = "potion_level_fix";
    public static final Logger LOGGER = LoggerFactory.getLogger("PotionLevelFix");
    //  EffectID PlayerUUID EffectAmplifier
    public static Map<String, Map<String, Integer>> PLFAmplifier = new HashMap<>(); //  Bad codes🙏

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
    }
}
