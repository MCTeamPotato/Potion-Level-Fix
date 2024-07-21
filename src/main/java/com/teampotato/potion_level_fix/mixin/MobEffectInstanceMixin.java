package com.teampotato.potion_level_fix.mixin;

import com.teampotato.potion_level_fix.PotionLevelFix;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

import static com.teampotato.potion_level_fix.PotionLevelFix.*;
@Mixin(MobEffectInstance.class)
public abstract class MobEffectInstanceMixin {
    @Shadow private int amplifier;
    @Shadow public abstract String getDescriptionId();
    @Shadow private int duration;
    @Unique private Map<String, Integer> amplifierMap = new HashMap<>();

    @Inject(method = "writeDetailsTo", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;putByte(Ljava/lang/String;B)V"))
    private void redirectPutByte(CompoundTag pNbt, CallbackInfo ci) {
        pNbt.putInt("PLF:Amplifier", this.amplifier);
    }

    @ModifyVariable(method = "loadSpecifiedEffect", at = @At(value = "STORE"), ordinal = 0)
    private static int amplifierGet(int i, MobEffect pEffect, CompoundTag pNbt) {
        return pNbt.getInt("PLF:Amplifier");
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void sentAmplifier(LivingEntity pEntity, Runnable pOnExpirationRunnable, CallbackInfoReturnable<Boolean> cir) {
        if (this.duration > 0 || this.duration == -1) {
            String uuid = pEntity.getStringUUID();
            int currentAmplifier = this.amplifier;
            if (amplifierMap.containsKey(uuid)) {
                if (currentAmplifier > amplifierMap.get(uuid)) {
                    amplifierMap.put(uuid, currentAmplifier);
                    effectMap.put(this.getDescriptionId(), amplifierMap);
                }
            } else {
                amplifierMap.put(uuid, currentAmplifier);
                effectMap.put(this.getDescriptionId(), amplifierMap);
            }
        } else {
            effectMap.remove(this.getDescriptionId(), amplifierMap);
        }
    }
}
