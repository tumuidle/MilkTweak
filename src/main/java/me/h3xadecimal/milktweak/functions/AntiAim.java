package me.h3xadecimal.milktweak.functions;

import me.h3xadecimal.milktweak.gui.GuiMisc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AntiAim {
    private boolean b = false;

    private float yaw = 0;
    private float pitch = 0;

    public AntiAim() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void tick(LivingEvent.LivingUpdateEvent event) {
        if (GuiMisc.buttonAntiAim.getState()) {
            ClientPlayerEntity e = Minecraft.getInstance().player;
            ClientPlayNetHandler handler = Minecraft.getInstance().getConnection();
            if (e == null || handler == null) return;

            if (yaw > 180.0f) {
                yaw = -180.0f;
            } else if (yaw < -180.0f) {
                yaw = 180.0f;
            }
            pitch = 90.0f;

            e.rotationYaw = yaw;
            e.rotationPitch = pitch;
            handler.sendPacket(new CPlayerPacket.RotationPacket(yaw, pitch, e.isOnGround()));
        }
    }
}
