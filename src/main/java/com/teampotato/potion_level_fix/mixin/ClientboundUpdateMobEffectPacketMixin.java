package com.teampotato.potion_level_fix.mixin;

import com.teampotato.potion_level_fix.impl.S2CAmplifierGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ClientboundUpdateMobEffectPacket.class)
public class ClientboundUpdateMobEffectPacketMixin implements S2CAmplifierGetter {
    @Unique private int potionLevelFix$realAmplifier = 0;

    @Inject(method = "<init>(ILnet/minecraft/world/effect/MobEffectInstance;Z)V", at = @At("TAIL"))
    public void resetInitAmplifier(int entityId, MobEffectInstance mobEffectInstance, boolean blend, CallbackInfo ci) {
        potionLevelFix$realAmplifier = mobEffectInstance.getAmplifier();
    }

    @Inject(method = "<init>(Lnet/minecraft/network/RegistryFriendlyByteBuf;)V", at = @At("TAIL"))
    public void resetBufAmplifier(RegistryFriendlyByteBuf buffer, CallbackInfo ci) {
        potionLevelFix$realAmplifier = buffer.readInt();
    }

    @Inject(method = "write", at = @At("TAIL"))
    public void writeAmplifier(RegistryFriendlyByteBuf buffer, CallbackInfo ci) {
        buffer.writeInt(potionLevelFix$realAmplifier);
    }

    @Override
    public int getRealAmplifier() {
        return potionLevelFix$realAmplifier;
    }
}
