package com.teampotato.potion_level_fix.mixin.client.compoat;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
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
    @Definition(id = "mobEffectInstance", local = @Local(argsOnly = true, type = MobEffectInstance.class))
    @Definition(id = "getAmplifier", method = "Lnet/minecraft/world/effect/MobEffectInstance;getAmplifier()I")
    @Expression("mobEffectInstance.getAmplifier() <= 9")
    @ModifyExpressionValue(method = "getEffectName", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static boolean greaterTrue(boolean original) {
        return true;
    }

    @Expression("'enchantment.level.'")
    @ModifyExpressionValue(method = "getEffectName", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static String hackI18Key(String original) {
        return PotionLevelFix.CONFIG.effectNumberType ? original : "";
    }
}
