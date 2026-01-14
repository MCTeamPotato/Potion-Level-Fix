package com.teampotato.potion_level_fix.mixin;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(MobEffectInstance.Details.class)
public class MobEffectInstanceDetailsMixin {
    @ModifyArg(method = "lambda$static$1",
            at = @At(value = "INVOKE",
                    target = "Lcom/mojang/serialization/codecs/RecordCodecBuilder$Instance;group(Lcom/mojang/datafixers/kinds/App;Lcom/mojang/datafixers/kinds/App;Lcom/mojang/datafixers/kinds/App;Lcom/mojang/datafixers/kinds/App;Lcom/mojang/datafixers/kinds/App;Lcom/mojang/datafixers/kinds/App;Lcom/mojang/datafixers/kinds/App;)Lcom/mojang/datafixers/Products$P7;"),
            index = 0)
    private static App<?, ?> modifyByte(App<?, ?> par1) {
        return Codec.INT.optionalFieldOf("amplifier", 0).forGetter(MobEffectInstance.Details::amplifier);
    }
}
