package com.teampotato.potion_level_fix.mixin;

import net.minecraft.server.commands.EffectCommands;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EffectCommands.class)
public class EffectCommandsMixin {
    @ModifyConstant(method = "register", constant = @Constant(intValue = 255, ordinal = 0))
    private static int amplifierChange(int constant){
        return Integer.MAX_VALUE-1;
    }

    @ModifyConstant(method = "register", constant = @Constant(intValue = 255, ordinal = 1))
    private static int amplifierInfiniteChange(int constant){
        return Integer.MAX_VALUE-1;
    }
}
