package me.h3xadecimal.milktweak.events

import net.minecraft.network.IPacket
import net.minecraftforge.eventbus.api.Event

data class PacketReceiveEvent(val packet: IPacket<*>): Event()
