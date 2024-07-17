package com.teampotato.potion_level_fix.mixin;

import com.teampotato.potion_level_fix.PotionLevelFix;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
@Mixin(EffectRenderingInventoryScreen.class)
public abstract class EffectScreenMixin {
    @Inject(method = "getEffectName", at = @At(value = "RETURN"), cancellable = true)
    private void modifyEffectName(MobEffectInstance pEffect, CallbackInfoReturnable<Component> cir) {
        MutableComponent mutablecomponent = pEffect.getEffect().getDisplayName().copy();
        String effect = pEffect.getEffect().getDescriptionId();
        Minecraft minecraft = Minecraft.getInstance();
        Map<String, Integer> map = PotionLevelFix.PLFAmplifier.get(effect);
        if (minecraft.player != null) {
            mutablecomponent.append(CommonComponents.SPACE).append(Component.translatable("enchantment.level." + (map.get(minecraft.player.getStringUUID()) + 1)));
        }
        cir.setReturnValue(mutablecomponent);
    }
}
