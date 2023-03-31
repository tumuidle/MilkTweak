package me.h3xadecimal.milktweak.gui

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.util.text.StringTextComponent

class MainUI : Screen(StringTextComponent("MilkTweak")) {
    lateinit var spawnButton: Button
    lateinit var teleportButton: Button

    lateinit var resourceWorldButton: Button
    lateinit var homeButton: Button

    lateinit var protectionButton: Button
    lateinit var announceButton: Button

    override fun init() {
        // LINE 1
        spawnButton = addButton(Button(
            width / 2 - 105, 60, 100, 20, StringTextComponent("主城")
        ) { minecraft!!.player!!.sendChatMessage("/spawn") })
        teleportButton = addButton(Button(
            width / 2 + 5, 60, 100, 20, StringTextComponent("传送")
        ) { minecraft!!.displayGuiScreen(GuiTeleport()) })

        // LINE 2
        resourceWorldButton = addButton(Button(
            width/2 - 105, 85, 100, 20, StringTextComponent("资源世界")
        ) { minecraft!!.displayGuiScreen(GuiResourceWorld())})
        homeButton = addButton(Button(
            width/2 + 5, 85, 100, 20, StringTextComponent("回家")
        ) { minecraft!!.player!!.sendChatMessage("/home home") })

        // LINE 3
        announceButton = addButton(Button(
            width/2 + 5, 110, 100, 20, StringTextComponent("信息")
        ) { minecraft!!.displayGuiScreen(GuiAnnounce()) })
    }

    override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        this.renderBackground(matrixStack)

        val fr = Minecraft.getInstance().fontRenderer
        fr.drawString(
            matrixStack, "MilkTweak",
            (width / 2 - fr.getStringWidth("MilkTweak") / 2).toFloat(), 30f, 0xFFFFFF)
        fr.drawString(matrixStack, "Made by H3xadecimal",
            (width / 2 - fr.getStringWidth("Made by H3xadeicmal")).toFloat(), 40f, 0xFFFFFF)

        super.render(matrixStack, mouseX, mouseY, partialTicks)
    }

    override fun isPauseScreen(): Boolean {
        return false
    }
}