package com.teampotato.potion_level_fix.datagen;

import com.teampotato.potion_level_fix.PotionLevelFix;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = PotionLevelFix.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        net.minecraft.data.DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        Minecraft minecraft = Minecraft.getInstance();
        Map<String, LanguageInfo> languages = minecraft.getLanguageManager().getLanguages();
        boolean client = event.includeClient();

        languages.forEach(
                (pString, pLanguageInfo)-> {
                    generator.addProvider(client, new ModLanguageProvider(output, pString));
                }
        );
    }
}
