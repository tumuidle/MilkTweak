package me.h3xadecimal.milktweak.features

import me.h3xadecimal.milktweak.MilkTweak
import me.h3xadecimal.milktweak.events.PacketReceiveEvent
import me.h3xadecimal.milktweak.utils.Timer
import net.minecraft.client.Minecraft
import net.minecraft.network.play.server.SPlayerListItemPacket
import net.minecraft.util.text.StringTextComponent
import net.minecraftforge.event.TickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue


object Protections {
    abstract class Protection {
        fun install() {
            MilkTweak.INSTANCE.eventBus.register(this)
        }
        fun uninstall() {
            MilkTweak.INSTANCE.eventBus.unregister(this)
        }
        abstract fun refresh()
    }

    val mc: Minecraft get() = Minecraft.getInstance()

    object AntiVanish: Protection() {
        private val timer = Timer()
        private val toLookUp: Queue<UUID> = ConcurrentLinkedQueue()

        @SubscribeEvent
        fun onUpdate(e: TickEvent) {
            if (timer.hasPassedS(5)) {
                val lookUp = toLookUp.poll()
                if (lookUp != null) {
                    try {
                        val player = mc.world!!.getPlayerByUuid(lookUp)
                        val name = if (player == null) {
                            null
                        } else {
                            player.name
                        }
                        if (name != null) {
                            mc.ingameGUI.chatGUI.printChatMessage(StringTextComponent("§7[§bMilkTweak§7]§r 检测到玩家消失：$name"))
                        }
                    } catch (ignored: Exception) { }
                    timer.reset()
                }
            }
        }

        @SubscribeEvent
        fun onPacketReceive(e: PacketReceiveEvent) {
            if (e.packet is SPlayerListItemPacket) {
                val pkt = e.packet
                if (pkt.action == SPlayerListItemPacket.Action.UPDATE_LATENCY) {
                    for (addPlayerData in pkt.entries) {
                        try {
                            if (mc.connection!!.getPlayerInfo(addPlayerData.profile.id) != null
                            ) {
                                continue
                            }
                            this.toLookUp.add(addPlayerData.profile.id)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }

        override fun refresh() {
            if (MilkTweak.INSTANCE.config.getBoolean("AntiVanish") == true) install() else uninstall()
        }
    }
}