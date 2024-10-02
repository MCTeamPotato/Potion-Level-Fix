package com.teampotato.potion_level_fix.network;

import com.teampotato.potion_level_fix.PotionLevelFix;
import com.teampotato.potion_level_fix.network.s2c.LevelPacketS2C;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class NetworkHandler {
    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(PotionLevelFix.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void register() {
        CHANNEL.registerMessage(packetId++, LevelPacketS2C.class, LevelPacketS2C::encode, LevelPacketS2C::decode, LevelPacketS2C::handle);
    }
}
