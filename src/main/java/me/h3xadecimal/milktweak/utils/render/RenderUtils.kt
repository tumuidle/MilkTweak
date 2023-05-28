package me.h3xadecimal.milktweak.utils.render

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.platform.GlStateManager
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.AbstractGui
import org.lwjgl.opengl.GL11.*

object RenderUtils
{
    @JvmStatic
    fun drawLine(x: Double, y: Double, x1: Double, y1: Double, width: Float) {
        glDisable(GL_TEXTURE_2D)
        glLineWidth(width)
        glBegin(GL_LINES)
        glVertex2d(x, y)
        glVertex2d(x1, y1)
        glEnd()
        glEnable(GL_TEXTURE_2D)
    }

    @JvmStatic
    fun drawRect(stack: MatrixStack, x: Int, y: Int, width: Int, height: Int, color: Int) {
        Minecraft.getInstance().getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION)
        AbstractGui.fill(stack, x, y, x + width, y + height, color)
    }
}