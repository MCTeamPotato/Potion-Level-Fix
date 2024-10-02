package com.teampotato.potion_level_fix.network.s2c;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record LevelPacketS2C(String id, int amplifier) {
    public static LevelPacketS2C sentEffect(String id, int amplifier){
        return new LevelPacketS2C(id, amplifier);
    }

    public static void encode(LevelPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeUtf(packet.id);
        friendlyByteBuf.writeInt(packet.amplifier);
    }

    public static LevelPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new LevelPacketS2C(friendlyByteBuf.readUtf(), friendlyByteBuf.readInt());
    }

    public static void handle(LevelPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            if (localPlayer == null) return;
            CompoundTag persistentData = localPlayer.getPersistentData();
            ListTag listTag = new ListTag();

            if (persistentData.contains("PLF:Amplifier")){
                listTag = persistentData.getList("PLF:Amplifier", 10);
            }

            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putInt(packet.id, packet.amplifier);
            for (int size = 0; size < listTag.size(); size++){
                CompoundTag tag = (CompoundTag) listTag.get(size);
                if (tag.contains(packet.id)){
                    listTag.set(size, compoundTag);
                    localPlayer.getPersistentData().put("PLF:Amplifier", listTag);
                    return;
                }
            }
            listTag.add(compoundTag);
            localPlayer.getPersistentData().put("PLF:Amplifier", listTag);
        });
        ctx.get().setPacketHandled(true);
    }
}
