package me.h3xadecimal.milktweak.features

import net.mcreator.milkservermod.MilkservermodModElements
import net.mcreator.milkservermod.MilkservermodModVariables
import net.mcreator.milkservermod.MilkservermodModVariables.PlayerVariables
import net.minecraft.command.arguments.EntityArgument.entity
import net.minecraft.entity.player.PlayerEntity


object Money {
    lateinit var elements: MilkservermodModElements

    /**
     * 鬼知道有没有用哦
     */
    @JvmStatic
    fun payMoney(e: PlayerEntity, count: Int) {
        e.world.server!!.commandManager.handleCommand(e.commandSource.withFeedbackDisabled().withPermissionLevel(4),
            "pay " +
                    (e.getCapability(MilkservermodModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(PlayerVariables()) as PlayerVariables).PlayerNameSave +
                    " " + (e.getCapability(MilkservermodModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(PlayerVariables()) as PlayerVariables).PayMoneyText
        )
    }
}