package me.h3xadecimal.milktweak.gui

import com.mojang.blaze3d.matrix.MatrixStack
import me.h3xadecimal.milktweak.MilkTweak
import me.h3xadecimal.milktweak.gui.components.OptionButton
import me.h3xadecimal.milktweak.utils.RenderUtil
import net.minecraft.client.gui.screen.Screen
import net.minecraft.util.text.StringTextComponent

class GuiFunctions: Screen(StringTextComponent("Functions")) {
    companion object {
        @JvmStatic
        private var textColor = false
        @JvmStatic
        fun getTextColor(): Boolean {
            return textColor
        }

        fun handleTextColor(origin: String): String {
            return origin.replace("&", "§")
        }
    }

    lateinit var buttonTextColor: OptionButton

    override fun init() {
        buttonTextColor = addButton(OptionButton(
            width/2 - 105, 55, 100, 20, "文字染色"
        ) { textColor = buttonTextColor.configState } )
        buttonTextColor.configState = MilkTweak.INSTANCE.config.getBoolean("functions.textColor", false)
    }

    override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrixStack)
        minecraft!!.fontRenderer.drawStringWithShadow(matrixStack, "功能", RenderUtil.getCenterX(this, "功能").toFloat(), 30f, 0xFFFFFF)

        super.render(matrixStack, mouseX, mouseY, partialTicks)
    }

    override fun onClose() {
        MilkTweak.INSTANCE.config.put("functions.textColor", buttonTextColor.configState)
        MilkTweak.INSTANCE.saveConfig()
    }
}