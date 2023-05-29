package me.h3xadecimal.milktweak.gui

import com.mojang.blaze3d.matrix.MatrixStack
import me.h3xadecimal.milktweak.functions.AntiAim
import me.h3xadecimal.milktweak.gui.button.OptionButton
import me.h3xadecimal.milktweak.utils.font.FontLoaders
import net.minecraft.client.gui.screen.Screen
import net.minecraft.util.text.StringTextComponent

object GuiMisc: Screen(StringTextComponent("MilkTweakMisc")) {
    private var aa = AntiAim()

    lateinit var buttonAntiAim: OptionButton

    init {
        buttonAntiAim = addButton(OptionButton(getCenterX()-105, 50, 20, 100, "低头转", {} ,"antiaim"))
    }

    override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrixStack)
        FontLoaders.Regular40.drawCenteredString("杂项",  getCenterX().toFloat(), 20f, 0xFFFFFF)

        super.render(matrixStack, mouseX, mouseY, partialTicks)
    }

    private fun getCenterX(): Int {
        return width/2
    }
}