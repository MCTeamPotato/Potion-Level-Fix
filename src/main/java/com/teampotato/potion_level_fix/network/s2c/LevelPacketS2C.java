package com.teampotato.potion_level_fix.network.s2c;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record LevelPacketS2C(int level) {
    public static LevelPacketS2C sentLevel(int level){
        return new LevelPacketS2C(level);
    }

    public static void encode(LevelPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(packet.level);
    }

    public static LevelPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new LevelPacketS2C(friendlyByteBuf.readInt());
    }

    public static void handle(LevelPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            localPlayer.getPersistentData().putInt("PLF:Amplifier", packet.level);
        });
        ctx.get().setPacketHandled(true);
    }
}
