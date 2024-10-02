package com.teampotato.potion_level_fix.mixin;

import com.teampotato.potion_level_fix.network.NetworkHandler;
import com.teampotato.potion_level_fix.network.s2c.LevelPacketS2C;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
@Mixin(MobEffectInstance.class)
public abstract class MobEffectInstanceMixin {
    @Shadow private int amplifier;
    @Shadow public abstract String getDescriptionId();
    @Shadow private int duration;

    @Inject(method = "writeDetailsTo", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;putByte(Ljava/lang/String;B)V"))
    private void redirectPutByte(CompoundTag pNbt, CallbackInfo ci) {
        pNbt.putInt("PLF:Amplifier", amplifier);
        sentLoacl(amplifier);
    }

    @ModifyVariable(method = "loadSpecifiedEffect", at = @At(value = "STORE"), ordinal = 0)
    private static int amplifierGet(int i, MobEffect pEffect, CompoundTag pNbt) {
        return pNbt.getInt("PLF:Amplifier");
    }

    @Unique
    @OnlyIn(Dist.CLIENT)
    private void sentLoacl(int amplifier){
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        localPlayer.getPersistentData().putInt("PLF:Amplifier", amplifier);
    }
}
