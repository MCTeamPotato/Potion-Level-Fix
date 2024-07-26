package com.teampotato.potion_level_fix.mixin.compoat;

import com.teampotato.potion_level_fix.PotionLevelFix;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Pseudo
@Mixin(targets = "snownee.jade.addon.vanilla.StatusEffectsProvider")
public class JadeEffectMixin {
    @Inject(method = "getEffectName", at = @At(value = "RETURN"), cancellable = true, remap = false)
    private static void modifyEffectName(MobEffectInstance pEffect, CallbackInfoReturnable<Component> cir) {
        MutableComponent mutablecomponent = pEffect.getEffect().getDisplayName().copy();
        Component amplifier = Component.literal(String.valueOf(pEffect.getAmplifier()+1));
        if (PotionLevelFix.LANG.get()) {
            amplifier = Component.translatable("enchantment.level." + (pEffect.getAmplifier() + 1));
        }
        if (pEffect.getAmplifier() < 0) amplifier = Component.literal("↑ ↑ ↑");
        mutablecomponent.append(CommonComponents.SPACE).append(amplifier);
        cir.setReturnValue(mutablecomponent);
    }
}
