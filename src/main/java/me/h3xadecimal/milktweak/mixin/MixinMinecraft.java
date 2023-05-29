package me.h3xadecimal.milktweak.mixin;

import me.h3xadecimal.milktweak.functions.AntiAim;
import net.minecraft.client.Minecraft;
import net.minecraft.profiler.LongTickDetector;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(boolean isDebug, LongTickDetector detector, CallbackInfo ci) {
    }
}
