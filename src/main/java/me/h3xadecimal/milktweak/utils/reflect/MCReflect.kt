package me.h3xadecimal.milktweak.utils.reflect

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.fonts.Font
import net.minecraft.client.gui.fonts.FontResourceManager
import net.minecraft.util.ResourceLocation

object MCReflect {
    @JvmStatic
    fun getFontResourceManager(): FontResourceManager {
        val field = Minecraft::class.java.getDeclaredField("fontResourceMananger")
        return field.get(Minecraft.getInstance()) as FontResourceManager
    }


}