package com.teampotato.potion_level_fix.datagen;

import com.teampotato.potion_level_fix.PotionLevelFix;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = PotionLevelFix.MODID)
public class DataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        net.minecraft.data.DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        boolean client = event.includeClient();
        generator.addProvider(client, new ModLanguageProvider(output, "en_us"));
    }
}
