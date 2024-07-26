package com.teampotato.potion_level_fix.mixin.compoat;

import com.teampotato.potion_level_fix.PotionLevelFix;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import snownee.jade.addon.vanilla.StatusEffectsProvider;

import java.util.Map;

@Mixin(StatusEffectsProvider.class)
public class JadeEffectMixin {
    @Inject(method = "getEffectName", at = @At(value = "RETURN"), cancellable = true, remap = false)
    private static void modifyEffectName(MobEffectInstance pEffect, CallbackInfoReturnable<Component> cir) {
        MutableComponent mutablecomponent = pEffect.getEffect().getDisplayName().copy();
        String effect = pEffect.getEffect().getDescriptionId();

        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        Map<String, Integer> map = PotionLevelFix.effectMap.get(effect);

        if((long)map.get(player.getStringUUID())+1<0) return;

        Component amplifier = Component.literal(String.valueOf((long)map.get(player.getStringUUID())+1));

        if (PotionLevelFix.LANG.get()) {
            amplifier = Component.translatable("enchantment.level." + ((long)map.get(player.getStringUUID()) + 1));
        }

        mutablecomponent.append(CommonComponents.SPACE).append(amplifier);
        cir.setReturnValue(mutablecomponent);
    }
}
