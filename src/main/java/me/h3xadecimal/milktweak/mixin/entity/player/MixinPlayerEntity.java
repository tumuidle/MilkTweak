package me.h3xadecimal.milktweak.mixin.entity.player;

import com.mojang.authlib.GameProfile;
import me.h3xadecimal.milktweak.SpecialPlayerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity {

    @Shadow public abstract GameProfile getGameProfile();

    @Inject(method = "getDisplayName", at = @At("HEAD"), cancellable = true)
    public void getDisplayName(CallbackInfoReturnable<ITextComponent> cir) {
        if (SpecialPlayerManager.INSTANCE.hasDisplayName(getGameProfile().getName())) {
            cir.setReturnValue(new StringTextComponent(SpecialPlayerManager.INSTANCE.getDisplayName(getGameProfile().getName())));
        }
    }
}
