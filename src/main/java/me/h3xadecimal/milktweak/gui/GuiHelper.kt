package me.h3xadecimal.milktweak.gui

import com.mojang.blaze3d.matrix.MatrixStack
import me.h3xadecimal.milktweak.utils.RenderUtil
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.util.Hand
import net.minecraft.util.text.StringTextComponent

class GuiHelper: Screen(StringTextComponent("Helper")) {
    lateinit var buttonDisplayNbt: Button

    override fun init() {
        buttonDisplayNbt = addButton(Button(
            width/2 - 105, 55, 100, 20, StringTextComponent("查询当前物品NBT")
        ) {
            displayNbt()
            closeScreen()
        } )
    }

    override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrixStack)
        minecraft!!.fontRenderer.drawStringWithShadow(matrixStack, "辅助功能", RenderUtil.getCenterX(this, "辅助功能").toFloat(), 30f, 0xFFFFFF)

        super.render(matrixStack, mouseX, mouseY, partialTicks)
    }

    private fun displayNbt() {
        val stack = minecraft!!.player!!.getHeldItem(Hand.MAIN_HAND)
        try {
            val tag = stack.tag
            if (tag == null) {
                minecraft!!.ingameGUI.chatGUI.printChatMessage(StringTextComponent("当前物品没有NBT标签"))
            }
            for (key in tag!!.keySet()) {
                val value = if (tag.get(key) != null) {
                    tag.get(key)!!.toFormattedComponent().string
                } else {
                    "null"
                }
                minecraft!!.ingameGUI.chatGUI.printChatMessage(StringTextComponent("键 §a$key§r: $value"))
            }
        } catch (t: Throwable) {
            minecraft!!.ingameGUI.chatGUI.printChatMessage(StringTextComponent("NBT查询失败：${t.javaClass.simpleName}(${t.message})"))
        }
    }
}