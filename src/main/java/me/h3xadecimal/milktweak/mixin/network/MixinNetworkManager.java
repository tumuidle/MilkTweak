package me.h3xadecimal.milktweak.mixin.network;

import io.netty.channel.ChannelHandlerContext;
import me.h3xadecimal.milktweak.MilkTweak;
import me.h3xadecimal.milktweak.events.PacketReceiveEvent;
import net.minecraft.network.IPacket;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {
    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/IPacket;)V", at = @At("HEAD"))
    public void read(ChannelHandlerContext p_channelRead0_1_, IPacket<?> p_channelRead0_2_, CallbackInfo ci) {
        try {
            PacketReceiveEvent event = new PacketReceiveEvent(p_channelRead0_2_);
            MilkTweak.INSTANCE.getEventBus().post(event);
        } catch (Throwable ignored) {}
    }
}
