package com.teampotato.potion_level_fix;

import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod(PotionLevelFix.MODID)
public class PotionLevelFix {
    public static final String MODID = "potion_level_fix";
    public static final Logger LOGGER = LoggerFactory.getLogger("PotionLevelFix");
    public static Map<String, Map<String, Integer>> PLFAmplifier = new HashMap<>();
}
