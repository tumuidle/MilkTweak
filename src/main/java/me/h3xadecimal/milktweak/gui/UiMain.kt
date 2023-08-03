package me.h3xadecimal.milktweak.gui

import com.mojang.blaze3d.matrix.MatrixStack
import me.h3xadecimal.milktweak.gui.button.GuiButton
import me.h3xadecimal.milktweak.utils.GitUtils
import me.h3xadecimal.milktweak.utils.font.FontLoaders
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screen.Screen
import net.minecraft.util.text.StringTextComponent

class UiMain: Screen(StringTextComponent("MilkTweakMain")) {
    private val mc get() = Minecraft.getInstance()
    override fun init() {
    }

    override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrixStack)
        FontLoaders.Arial40.drawCenteredString("MilkTweak v2",  getCenterX().toFloat(), 20f, 0xFFFFFF)

        FontLoaders.Arial18.drawCenteredString("git-${GitUtils.getCommitHash()} BuildVer.${GitUtils.getBuildVersion()}", (mc.currentScreen!!.width/2).toFloat(), (mc.currentScreen!!.height-20).toFloat(), 0xFFFFFF)
        super.render(matrixStack, mouseX, mouseY, partialTicks)
    }

    private fun getCenterX(): Int {
        return width/2
    }

    override fun isPauseScreen(): Boolean {
        return false
    }
}