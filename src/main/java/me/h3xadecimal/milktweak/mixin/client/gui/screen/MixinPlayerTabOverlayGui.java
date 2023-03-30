package me.h3xadecimal.milktweak.mixin.client.gui.screen;

import me.h3xadecimal.milktweak.SpecialPlayerManager;
import net.minecraft.client.gui.overlay.PlayerTabOverlayGui;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerTabOverlayGui.class)
public class MixinPlayerTabOverlayGui {
    @Inject(method = "getDisplayName", at = @At("HEAD"))
    public void getDisplayName(NetworkPlayerInfo p_200262_1_, CallbackInfoReturnable<ITextComponent> cir) {
        if (SpecialPlayerManager.INSTANCE.hasDisplayName(p_200262_1_.getGameProfile().getName())) {
            cir.setReturnValue(new StringTextComponent(SpecialPlayerManager.INSTANCE.getDisplayName(p_200262_1_.getGameProfile().getName())));
        }
    }
}
