package com.teampotato.potion_level_fix.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.teampotato.potion_level_fix.impl.S2CAmplifierGetter;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Optional;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @ModifyArg(method = "handleUpdateMobEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;forceAddEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)V"), index = 0)
    public MobEffectInstance modifyEffectAmplifier(MobEffectInstance instance, @Local(type = Holder.class)Holder<MobEffect> mobeffect, @Local(argsOnly = true, type = ClientboundUpdateMobEffectPacket.class)ClientboundUpdateMobEffectPacket packet) {
        int amplifier = packet instanceof S2CAmplifierGetter getter ? getter.getRealAmplifier() : packet.getEffectAmplifier();
        return new MobEffectInstance(mobeffect, packet.getEffectDurationTicks(), amplifier, packet.isEffectAmbient(), packet.isEffectVisible(), packet.effectShowsIcon(), null);
    }
}
