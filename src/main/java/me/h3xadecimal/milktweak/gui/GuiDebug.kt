package me.h3xadecimal.milktweak.gui

import com.mojang.blaze3d.matrix.MatrixStack
import me.h3xadecimal.milktweak.utils.ServerUtil
import me.h3xadecimal.milktweak.utils.fonts.FontLoaders
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.util.text.StringTextComponent

/**
 * 以下功能仅在runClient中可用，通常不会显示进入此处的按钮
 */
class GuiDebug: Screen(StringTextComponent("Debug")) {
    lateinit var fieldInputCommand: TextFieldWidget
    lateinit var buttonExecuteCommand: Button

    override fun init() {
        fieldInputCommand = TextFieldWidget(minecraft!!.fontRenderer, width/2-100, 55, 100, 20, StringTextComponent("Command"))
        children.add(fieldInputCommand)
        buttonExecuteCommand = addButton(Button(
            width/2-100, 80, 100, 20, StringTextComponent("Execute Command")
        ) { executeCommand() } )
    }

    override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrixStack)
        FontLoaders.Harmony40.drawCenteredString("MilkTweak Debug", (width/2).toFloat(), 30f, 0xFFFFFF)

        fieldInputCommand.render(matrixStack, mouseX, mouseY, partialTicks)
        super.render(matrixStack, mouseX, mouseY, partialTicks)
    }

    override fun tick() {
        buttonExecuteCommand.active = fieldInputCommand.text.isNotEmpty()
    }

    private fun executeCommand() {
        if (fieldInputCommand.text.isNotEmpty()) {
            ServerUtil.sendCommand(fieldInputCommand.text, ServerUtil.PermissionLevel.OPERATOR_HIGHEST)
            closeScreen()
        }
    }
}