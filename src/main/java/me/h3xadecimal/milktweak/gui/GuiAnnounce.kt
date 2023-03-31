package me.h3xadecimal.milktweak.gui

import com.mojang.blaze3d.matrix.MatrixStack
import me.h3xadecimal.milktweak.utils.RenderUtil
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screen.Screen
import net.minecraft.util.text.StringTextComponent

class GuiAnnounce: Screen(StringTextComponent("Announce")) {
    companion object {
        @JvmStatic
        val announce = arrayOf("")
    }

    override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        val mc = Minecraft.getInstance() ?: return
        val fr = mc.fontRenderer

        // 标题
        fr.drawTextWithShadow(matrixStack, StringTextComponent("信息"), RenderUtil.getCenterX(this, "信息").toFloat(), 10f, 0xFFFFFF)

        var i = 20f
        // 正文
        for (a in announce) {
            fr.drawTextWithShadow(matrixStack, StringTextComponent(a), RenderUtil.getCenterX(this, a).toFloat(), i, 0xFFFFFF)
            i += 10
        }
    }
}