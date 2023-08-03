package me.h3xadecimal.milktweak.gui.teleport

import com.mojang.blaze3d.matrix.MatrixStack
import me.h3xadecimal.milktweak.gui.button.GuiButton
import me.h3xadecimal.milktweak.utils.font.FontLoaders
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screen.Screen
import net.minecraft.util.text.StringTextComponent

class UiTeleportMenu: Screen(StringTextComponent("MTTeleportMain")) {
    private val mc get() = Minecraft.getInstance()

    private lateinit var btnSpawn: GuiButton

    private lateinit var btnRwNether: GuiButton
    private lateinit var btnRwEnd: GuiButton

    private lateinit var btnTeleportS2: GuiButton

    override fun init() {
        btnSpawn = addButton(GuiButton(getCenterX()-50, 50, 100, 20, "主城") { mc.player!!.sendChatMessage("/spawn") })

        btnRwNether = addButton(GuiButton(getCenterX()-105, 80, 100, 20, "资源-下界") { mc.player!!.sendChatMessage("/rw tp nether") })
        btnRwEnd = addButton(GuiButton(getCenterX()+5, 80, 100, 20, "资源-末地") { mc.player!!.sendChatMessage("/rw tp nether") } )

        btnTeleportS2 = addButton(GuiButton(getCenterX()-50, 110, 100, 20, "二服") {
            mc.displayGuiScreen(UiTeleportS2())
        })
    }

    override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrixStack)

        mc.fontRenderer.drawString(matrixStack, "传送", getCenterX().toFloat(), 20f, 0xFFFFFF)

        super.render(matrixStack, mouseX, mouseY, partialTicks)
    }

    private fun getCenterX(): Int {
        return width/2
    }

    override fun isPauseScreen(): Boolean = false
}