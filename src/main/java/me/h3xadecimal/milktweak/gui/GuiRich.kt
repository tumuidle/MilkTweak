package me.h3xadecimal.milktweak.gui

import com.mojang.blaze3d.matrix.MatrixStack
import me.h3xadecimal.milktweak.utils.RenderUtil
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.util.text.StringTextComponent

// 富哥666
class GuiRich: Screen(StringTextComponent("Rich")) {
    lateinit var buttonHeadDb: Button
    lateinit var buttonPrefixMgr: Button

    override fun init() {
        buttonHeadDb = addButton(Button(
            width/2 - 105, 60, 100, 20, StringTextComponent("头颅商店")
        ) { minecraft!!.player!!.sendChatMessage("/hdb") } )
        buttonPrefixMgr = addButton(Button(
            width/2 + 5, 60, 100, 20, StringTextComponent("称号管理")
        ) { minecraft!!.player!!.sendChatMessage("/plt open") } )
    }

    override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrixStack)

        minecraft!!.fontRenderer.drawTextWithShadow(matrixStack, StringTextComponent("富哥"), RenderUtil.getCenterX(this, "富哥").toFloat(), 15f, 0xFFFFFF)

        super.render(matrixStack, mouseX, mouseY, partialTicks)
    }
}