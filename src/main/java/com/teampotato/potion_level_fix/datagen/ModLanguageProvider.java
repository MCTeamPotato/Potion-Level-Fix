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

    public String intToRoman(int num) {

        String[] roman = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] nums = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder res = new StringBuilder();

        for (int i = 0; i < nums.length && num >= 0; i++) {
            while (nums[i] <= num) {
                num -= nums[i];
                res.append(roman[i]);
            }
        }
        return res.toString();
    }

    @Override
    protected void addTranslations() {
        for (int i = 11; i <= 4000; i++) {
            add("enchantment.level." + i, intToRoman(i));
        }
    }
}
