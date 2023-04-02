package me.h3xadecimal.milktweak.gui

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.gui.AbstractGui
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.util.text.StringTextComponent

class GuiPrivateMessage: Screen(StringTextComponent("PrivateMessage")) {
    companion object {
        @JvmStatic
        var specified = false

        @JvmStatic
        var target: String? = null

        @JvmStatic
        fun handleMessage(message: String): String {
            if (specified && target != null)  {
                return "/msg $target $message"
            }
            return message
        }
    }

    lateinit var playerNameField: TextFieldWidget

    lateinit var setButton: Button
    lateinit var disableButton: Button

    override fun init() {
        playerNameField = TextFieldWidget(minecraft!!.fontRenderer, width/2-100, 45, 200, 20, StringTextComponent("Target"))
        playerNameField.setFocused2(true)
        children.add(playerNameField)

        setButton = addButton(Button(
            width/2-105, 70, 100, 20, StringTextComponent("设置")
        ) {
            target = playerNameField.text
            specified = true
        })
        disableButton = addButton(Button(
            width/2+5, 70, 100, 20, StringTextComponent("禁用")
        ) {
            specified = false
            target = ""
        } )
    }

    override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrixStack)

        val title = "私聊"
        AbstractGui.drawCenteredString(matrixStack, minecraft!!.fontRenderer, title, width/2, width/2, 20)

        playerNameField.render(matrixStack, mouseX, mouseY, partialTicks)
        super.render(matrixStack, mouseX, mouseY, partialTicks)
    }

    override fun tick() {
        disableButton.active = specified
        playerNameField.tick()
    }
}