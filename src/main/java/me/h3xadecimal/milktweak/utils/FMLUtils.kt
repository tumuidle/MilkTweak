package me.h3xadecimal.milktweak.utils

import net.minecraftforge.fml.ModContainer
import net.minecraftforge.fml.ModList

object FMLUtils {
    @JvmStatic
    fun getModContainer(id: String): ModContainer? {
        val opt = ModList.get().getModContainerById(id)
        return if (opt.isPresent) {
            return opt.get()
        } else null
    }
}