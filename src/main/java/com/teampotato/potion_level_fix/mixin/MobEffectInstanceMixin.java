package com.teampotato.potion_level_fix.mixin;

import com.teampotato.potion_level_fix.network.NetworkHandler;
import com.teampotato.potion_level_fix.network.s2c.LevelPacketS2C;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEffectInstance.class)
public abstract class MobEffectInstanceMixin {
    @Shadow private int amplifier;
    @Shadow public abstract String getDescriptionId();
    @Shadow private int duration;

    @Shadow public abstract String toString();

    @Inject(method = "writeDetailsTo", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;putByte(Ljava/lang/String;B)V"))
    private void redirectPutByte(CompoundTag pNbt, CallbackInfo ci) {
        pNbt.putInt("PLF:Amplifier", amplifier);
    }

    @ModifyVariable(method = "loadSpecifiedEffect", at = @At(value = "STORE"), ordinal = 0)
    private static int amplifierGet(int i, MobEffect pEffect, CompoundTag pNbt) {
        return pNbt.getInt("PLF:Amplifier");
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void sentAmplifier(LivingEntity pEntity, Runnable pOnExpirationRunnable, CallbackInfoReturnable<Boolean> cir){
        if (pEntity instanceof ServerPlayer serverPlayer){
            NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(
                    () -> serverPlayer),
                    LevelPacketS2C.sentEffect(getDescriptionId(), amplifier)
            );
        }
    }
}
