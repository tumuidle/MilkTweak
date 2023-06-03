package me.h3xadecimal.milktweak.mixin.patch.forge;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkInstance;
import net.minecraftforge.fml.network.NetworkRegistry;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * Forge NMSL
 */
@Mixin(value = NetworkRegistry.class, remap = false)
public class MixinNetworkRegistery {
    @Inject(method = "checkListPingCompatibilityForClient", at = @At("RETURN"), cancellable = true, remap = false)
    private static void checkListPingCompatibilityForClient(Map<ResourceLocation, Pair<String, Boolean>> incoming, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "validateChannels", at = @At("RETURN"), cancellable = true, remap = false)
    private static void validateChannels(Map<ResourceLocation, String> incoming, String originName, BiFunction<NetworkInstance, String, Boolean> testFunction, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
