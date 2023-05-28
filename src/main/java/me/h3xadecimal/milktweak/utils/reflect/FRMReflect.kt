package me.h3xadecimal.milktweak.utils.reflect

import me.h3xadecimal.milktweak.utils.reflect.MCReflect.getFontResourceManager
import net.minecraft.client.gui.fonts.Font
import net.minecraft.client.gui.fonts.FontResourceManager
import net.minecraft.util.ResourceLocation

object FRMReflect {
    @JvmStatic
    val idToFontMap get() = FontResourceManager::class.java.getDeclaredField("field_238546_d_").get(getFontResourceManager()) as Map<ResourceLocation, Font>

    @JvmStatic
    val unicodeForcedMap get() = FontResourceManager::class.java.getDeclaredField("field_238547_f_").get(getFontResourceManager()) as Map<ResourceLocation, ResourceLocation>

    @JvmStatic
    val field_238545_c_ get() = FontResourceManager::class.java.getDeclaredField("field_238545_c_").get(
        getFontResourceManager()) as Font
}