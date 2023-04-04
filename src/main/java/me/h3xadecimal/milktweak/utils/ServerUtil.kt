package me.h3xadecimal.milktweak.utils

import net.minecraft.client.Minecraft

class ServerUtil {
    enum class PermissionLevel {
        PLAYER,
        OPERATOR_MIN,
        OPERATOR_LESS,
        OPERATOR_HIGH,
        OPERATOR_HIGHEST
    }

    companion object {
        // 调试专用
        @JvmStatic
        fun sendCommand(command: String, level: PermissionLevel) {
            var permission = when (level.name.lowercase()) {
                "player" -> 0
                "operator_min" -> 1
                "operator_less" -> 2
                "operator_high" -> 3
                "operator_highest" -> 4
                else -> 0
            }

            val player = Minecraft.getInstance().player ?: return
            println("SEND_COMMAND GET_ENTITY")
            val commandManager = (player.server ?: return).commandManager
            println("SEND_COMMAND GET_SERVER")
            val source = player.commandSource.withPermissionLevel(permission)
            commandManager.handleCommand(source, command)
            println("Sending command \"$command\" with permisison level $permission")
        }
    }
}