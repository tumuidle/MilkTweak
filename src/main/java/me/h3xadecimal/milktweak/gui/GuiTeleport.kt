package me.h3xadecimal.milktweak.gui

import com.mojang.blaze3d.matrix.MatrixStack
import me.h3xadecimal.milktweak.MilkTweak
import me.h3xadecimal.milktweak.gui.components.PublicHomeButton
import me.h3xadecimal.milktweak.utils.RenderUtil
import me.h3xadecimal.milktweak.utils.fonts.FontLoaders
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screen.Screen
import net.minecraft.util.text.StringTextComponent

class GuiTeleport: Screen(StringTextComponent("Teleport")) {
    companion object {
        val mc: Minecraft get() = Minecraft.getInstance()

        const val SUBTITLE_Y = 50f
        const val BUTTON_DELTA = 22f

        @JvmStatic
        val ser2 = HashMap<String, String>()
        @JvmStatic
        val ser3 = HashMap<String, String>()

        init {
            ser2["the_xiaoxiang.xiaoheita"] = "小黑塔"

            ser3["gouzi846.dianwancheng"] = "电玩城"
        }
    }

    private val X_SER2 get() = (width / 2 - 250).toFloat()
    private val X_SER3 get() = (width / 2 - 100).toFloat()

    override fun init() {
        var i = SUBTITLE_Y + 10

        for (b in ser2.keys) {
            addButton(PublicHomeButton(X_SER3.toInt(), i.toInt(), StringTextComponent(ser2[b]!!), b, this))
        }
        i = SUBTITLE_Y + 10

        for (b in ser3.keys) {
            addButton(PublicHomeButton(X_SER3.toInt(), i.toInt(), StringTextComponent(ser3[b]!!), b, this))
            i += BUTTON_DELTA
        }

        i = SUBTITLE_Y + 10
    }

    override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        this.renderBackground(matrixStack)

        val title = "公用传送点"
        mc.fontRenderer.drawTextWithShadow(matrixStack, StringTextComponent(title), RenderUtil.getCenterX(this, title).toFloat(), 20f, 0xffffff)

        val subtitle = "由服务器玩家创建"
        mc.fontRenderer.drawTextWithShadow(matrixStack, StringTextComponent(subtitle), RenderUtil.getCenterX(this, subtitle).toFloat(), 27f, 0xffffff)

        val titleSer2 ="二服"
        mc.fontRenderer.drawTextWithShadow(matrixStack, StringTextComponent(titleSer2), X_SER2, SUBTITLE_Y, 0xffffff)

        val titleSer3 ="三服"
        mc.fontRenderer.drawTextWithShadow(matrixStack, StringTextComponent(titleSer3), X_SER3, SUBTITLE_Y, 0xffffff)

        super.render(matrixStack, mouseX, mouseY, partialTicks)
    }

    private fun teleport(name: String) {
        minecraft!!.player!!.sendChatMessage("/phome $name")
    }
}