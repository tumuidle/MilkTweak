package me.h3xadecimal.milktweak.gui

import com.mojang.blaze3d.matrix.MatrixStack
import me.h3xadecimal.milktweak.MilkTweak
import me.h3xadecimal.milktweak.utils.RenderUtil
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.util.text.StringTextComponent

class GuiResourceWorld: Screen(StringTextComponent("ResourceWorld")) {
    lateinit var nether: Button
    lateinit var end: Button

    override fun init() {
        nether = addButton(Button(width/2-50, 55, 100, 20, StringTextComponent("地狱")) {
            minecraft!!.player!!.sendChatMessage("/resourceworld tp nether")
        })
        end = addButton(Button(width/2-50, 80, 100, 20, StringTextComponent("末地")) {
            minecraft!!.player!!.sendChatMessage("/resourceworld tp end")
        })

        super.init()
    }

    override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        val title = "资源世界"
        val tip = "由于服务器权限限制，无法直接传送到主世界"

        minecraft!!.fontRenderer.drawTextWithShadow(matrixStack, StringTextComponent(title), RenderUtil.getCenterX(this, title).toFloat(), 20f, 0xffffff)
        minecraft!!.fontRenderer.drawTextWithShadow(matrixStack, StringTextComponent(tip), RenderUtil.getCenterX(this, tip).toFloat(), 30f, 0xffffff)

        super.render(matrixStack, mouseX, mouseY, partialTicks)
    }
}