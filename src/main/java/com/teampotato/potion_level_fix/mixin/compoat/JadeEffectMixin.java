package com.teampotato.potion_level_fix.mixin.compoat;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.teampotato.potion_level_fix.PotionLevelFix;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(targets = "snownee.jade.addon.vanilla.StatusEffectsProvider")
public class JadeEffectMixin {
    @Definition(id = "effect", local = @Local(argsOnly = true, type = MobEffectInstance.class))
    @Definition(id = "getAmplifier", method = "Lnet/minecraft/world/effect/MobEffectInstance;getAmplifier()I")
    @Expression("effect.getAmplifier() <= 9")
    @ModifyExpressionValue(method = "getEffectName", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static boolean greaterTrue(boolean original) {
        return true;
    }

    @Expression("'enchantment.level.'")
    @ModifyExpressionValue(method = "getEffectName", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static String hackI18Key(String original) {
        return PotionLevelFix.EFFECT_NUMBER.get() ? original : "";
    }
}
