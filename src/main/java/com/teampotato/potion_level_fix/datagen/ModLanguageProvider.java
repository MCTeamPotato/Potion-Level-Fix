package com.teampotato.potion_level_fix.datagen;

import com.google.common.collect.Lists;
import com.teampotato.potion_level_fix.PotionLevelFix;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.ArrayList;
import java.util.List;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output, String languages) {
        super(output, PotionLevelFix.MODID, languages);
    }

    private static final int[] VALUES = {
            100, 90, 50, 40, 10, 9, 5, 4, 1
    };

    private static final String[] ROMAN_NUMERALS = {
            "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
    };

    public static String generateRomanNumeral(int number) {
        if (number < 1 || number > 256) {
            throw new IllegalArgumentException("Number must be between 1 and 256");
        }

        StringBuilder roman = new StringBuilder();

        for (int i = 0; i < VALUES.length; i++) {
            while (number >= VALUES[i]) {
                roman.append(ROMAN_NUMERALS[i]);
                number -= VALUES[i];
            }
        }

        return roman.toString();
    }
    @Override
    protected void addTranslations() {
        for (int i = 11; i <= 256; i++) {
            add("enchantment.level." + i, generateRomanNumeral(i));
        }
    }
}
