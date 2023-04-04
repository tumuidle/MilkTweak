package me.h3xadecimal.milktweak.gui

import com.mojang.blaze3d.matrix.MatrixStack
import me.h3xadecimal.milktweak.utils.fonts.FontLoaders
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.util.text.StringTextComponent

class MainUI : Screen(StringTextComponent("MilkTweak")) {
    lateinit var spawnButton: Button
    lateinit var teleportButton: Button

    lateinit var resourceWorldButton: Button
    lateinit var homeButton: Button

    lateinit var privateMessageButton: Button
    lateinit var announceButton: Button

    lateinit var blacklistButton: Button
    lateinit var richButton: Button

    override fun init() {
        // LINE 1
        spawnButton = addButton(Button(
            width / 2 - 105, 60, 100, 20, StringTextComponent("主城")
        ) { minecraft!!.player!!.sendChatMessage("/spawn")
        closeScreen()})
        teleportButton = addButton(Button(
            width / 2 + 5, 60, 100, 20, StringTextComponent("传送")
        ) { minecraft!!.displayGuiScreen(GuiTeleport()) })

        // LINE 2
        resourceWorldButton = addButton(Button(
            width/2 - 105, 85, 100, 20, StringTextComponent("资源世界")
        ) { minecraft!!.displayGuiScreen(GuiResourceWorld())})
        homeButton = addButton(Button(
            width/2 + 5, 85, 100, 20, StringTextComponent("回家")
        ) { minecraft!!.player!!.sendChatMessage("/home home")
        closeScreen()})

        // LINE 3
        announceButton = addButton(Button(
            width/2 - 105, 110, 100, 20, StringTextComponent("信息")
        ) { minecraft!!.displayGuiScreen(GuiAnnounce()) })
        privateMessageButton = addButton(Button(
            width/2 + 5, 110, 100, 20, StringTextComponent("指定私聊")
        ) { minecraft!!.displayGuiScreen(GuiPrivateMessage()) })

        // LINE 4
        blacklistButton = addButton(Button(
            width/2 - 105, 135, 100, 20, StringTextComponent("聊天屏蔽")
        ) { minecraft!!.displayGuiScreen(GuiBlackList()) })
        richButton = addButton(Button(
            width/2 + 5, 135, 100, 20, StringTextComponent("富哥功能")
        ) { minecraft!!.displayGuiScreen(GuiRich()) })
    }

    override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        this.renderBackground(matrixStack)

        FontLoaders.Harmony40.drawCenteredString("MilkTweak", (width/2).toFloat(), 15f, 0xFFFFFF)
        FontLoaders.Harmony18.drawCenteredString("Made by H3xadecimal", (width/2).toFloat(), 33f, 0xFFFFFF)

        super.render(matrixStack, mouseX, mouseY, partialTicks)
    }

    override fun isPauseScreen(): Boolean {
        return false
    }
}