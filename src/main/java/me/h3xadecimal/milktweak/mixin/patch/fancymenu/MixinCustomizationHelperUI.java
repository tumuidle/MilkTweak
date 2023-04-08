package me.h3xadecimal.milktweak.mixin.patch.fancymenu;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.keksuccino.fancymenu.menu.fancy.helper.CustomizationHelperUI;
import me.h3xadecimal.milktweak.gui.GuiFunctions;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CustomizationHelperUI.class, remap = false)
public class MixinCustomizationHelperUI {
    @Inject(method = "renderUnicodeWarning", at = @At("HEAD"), cancellable = true)
    private static void renderUnicodeWarning(MatrixStack matrix, Screen screen, CallbackInfo ci) {
        if (GuiFunctions.noUnicodeWarn) ci.cancel();
    }
}
