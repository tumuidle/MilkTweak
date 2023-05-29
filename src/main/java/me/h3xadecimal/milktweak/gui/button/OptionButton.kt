package me.h3xadecimal.milktweak.gui.button

import com.mojang.blaze3d.matrix.MatrixStack
import me.h3xadecimal.milktweak.file.file.FileManager
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.util.text.StringTextComponent

class OptionButton(x: Int, y: Int, width: Int, height:Int, private val title: String, action: Button.IPressable, val configKey: String): GuiButton(x, y, width, height, title, action) {
    private var state = false

    init {
        if (FileManager.cfg.containsKey(configKey) && FileManager.cfg[configKey] is Boolean) state = FileManager.cfg[configKey] as Boolean
    }

    override fun onPress() {
        FileManager.cfg[configKey] = state
        super.onPress()
        FileManager.saveConfig()
    }

    override fun renderWidget(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        if (state) {
            message = StringTextComponent("$title: §a启用")
        } else {
            message = StringTextComponent("$title: §c禁用")
        }

        super.renderWidget(matrixStack, mouseX, mouseY, partialTicks)
    }
}