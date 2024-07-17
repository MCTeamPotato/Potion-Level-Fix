package com.teampotato.potion_level_fix.mixin;

import com.teampotato.potion_level_fix.PotionLevelFix;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(MobEffectInstance.class)
public abstract class MobEffectInstanceMixin {
    @Shadow
    private int amplifier;
    @Shadow public abstract String getDescriptionId();

    @Redirect(method = "writeDetailsTo", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;putByte(Ljava/lang/String;B)V"))
    private void redirectPutByte(CompoundTag pNbt, String key, byte value) {
        pNbt.putInt("Amplifier", this.amplifier);
    }

    @ModifyVariable(method = "loadSpecifiedEffect", at = @At(value = "STORE"), ordinal = 0)
    private static int amplifierGet(int i, MobEffect pEffect, CompoundTag pNbt) {
        return pNbt.getInt("Amplifier");
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void sentAmplifier(LivingEntity pEntity, Runnable pOnExpirationRunnable, CallbackInfoReturnable<Boolean> cir) {
        if (this.amplifier >= 0) {
            Map<String, Integer> map = new HashMap<>();
            map.put(pEntity.getStringUUID(), this.amplifier);
            PotionLevelFix.PLFAmplifier.put(this.getDescriptionId(), map);
        }
    }
}
