package me.h3xadecimal.milktweak.mixin;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.IPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SEntityHeadLookPacket;
import net.minecraft.network.play.server.SPlayerLookPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetHandler.class)
public class MixinClientPlayNetHandler {
    @Shadow private ClientWorld world;

    @Shadow private boolean doneLoadingTerrain;

    @Inject(method = "handlePlayerPosLook", at = @At("HEAD"))
    public void handlePlayerPosLook(SPlayerPositionLookPacket packetIn, CallbackInfo ci) {
        boolean flag = false;
        double d0 = packetIn.getX();
        double d1 = packetIn.getY();
        double d2 = packetIn.getZ();
        float f = packetIn.getYaw();
        float f1 = packetIn.getPitch();

        ClientPlayerEntity entityplayer = Minecraft.getInstance().player;
        if (entityplayer == null) return;

        if (packetIn.getFlags().contains(SPlayerPositionLookPacket.Flags.Y)) {
            d1 += entityplayer.getPosY();
        }
        if (packetIn.getFlags().contains(SPlayerPositionLookPacket.Flags.Z)) {
            d2 += entityplayer.getPosZ();
        }

        if (flag) {
            Minecraft.getInstance().player.setPositionAndRotation(packetIn.getX(), packetIn.getY(), packetIn.getZ(), entityplayer.rotationYaw, entityplayer.rotationPitch);
        }
    }
}
