package com.teampotato.potion_level_fix.mixin;

import com.teampotato.potion_level_fix.PotionLevelFix;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(EffectRenderingInventoryScreen.class)
public abstract class EffectScreenMixin {
    @Inject(method = "getEffectName", at = @At(value = "RETURN"), cancellable = true)
    private void modifyEffectName(MobEffectInstance pEffect, CallbackInfoReturnable<Component> cir) {
        MutableComponent mutablecomponent = pEffect.getEffect().getDisplayName().copy();
        int amplifier = getAmplifier(pEffect);

        Component amplifierText = Component.literal(String.valueOf(amplifier));
        if (PotionLevelFix.LANG.get()) {
            amplifierText = Component.translatable("enchantment.level." + amplifier);
        }
        mutablecomponent.append(CommonComponents.SPACE).append(amplifierText);
        cir.setReturnValue(mutablecomponent);
    }

    private static int getAmplifier(MobEffectInstance pEffect) {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        CompoundTag persistentData = localPlayer.getPersistentData();
        ListTag listTag = new ListTag();
        int amplifier = 0;

        if (persistentData.contains("PLF:Amplifier")){
            listTag = persistentData.getList("PLF:Amplifier", 10);
        }

        for (Tag value : listTag) {
            CompoundTag tag = (CompoundTag) value;
            if (tag.contains(pEffect.getDescriptionId())) {
                amplifier = tag.getInt(pEffect.getDescriptionId()) + 1;
            }
        }
        return amplifier;
    }
}
