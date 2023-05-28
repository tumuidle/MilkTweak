package me.h3xadecimal.milktweak.utils.render

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
}