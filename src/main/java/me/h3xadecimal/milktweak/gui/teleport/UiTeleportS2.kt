package me.h3xadecimal.milktweak.gui.teleport

import com.mojang.blaze3d.matrix.MatrixStack
import me.h3xadecimal.milktweak.utils.font.FontLoaders
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screen.Screen
import net.minecraft.util.text.StringTextComponent

class UiTeleportS2: Screen(StringTextComponent("MTTeleportS2")) {
    private val mc get() = Minecraft.getInstance()
    private val teleports = mutableListOf<PublicHomeComponent>()

    override fun init() {
        teleports.add(PublicHomeComponent(getCenterX()-105, 60, "交易所", "TunYuntuwuQWQ.jys"))

        for (t in teleports) addButton(t)
    }

    override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrixStack)
        mc.fontRenderer.drawString(matrixStack, "传送", getCenterX().toFloat(), 20f, 0xFFFFFF)

        super.render(matrixStack, mouseX, mouseY, partialTicks)
    }

    private fun getCenterX(): Int {
        return width/2
    }

    override fun isPauseScreen(): Boolean = false
}