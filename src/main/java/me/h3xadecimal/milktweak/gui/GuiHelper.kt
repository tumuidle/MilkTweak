package me.h3xadecimal.milktweak.gui

import com.mojang.blaze3d.matrix.MatrixStack
import me.h3xadecimal.milktweak.utils.ItemUtils
import me.h3xadecimal.milktweak.utils.RenderUtil
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.text.StringTextComponent

class GuiHelper: Screen(StringTextComponent("Helper")) {
    lateinit var buttonDisplayNbt: Button
    lateinit var buttonDisplayBlockNbt: Button

    override fun init() {
        buttonDisplayNbt = addButton(Button(
            width/2 - 105, 55, 100, 20, StringTextComponent("查询当前物品NBT")
        ) {
            displayNbt()
            closeScreen()
        } )
        buttonDisplayBlockNbt = addButton(Button(
            width/2 + 5, 55, 100, 20, StringTextComponent("查询面向方块NBT")
        ) {
            displayRayTraceNbt()
            closeScreen()
        })
    }

    override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrixStack)
        minecraft!!.fontRenderer.drawStringWithShadow(matrixStack, "辅助功能", RenderUtil.getCenterX(this, "辅助功能").toFloat(), 30f, 0xFFFFFF)

        super.render(matrixStack, mouseX, mouseY, partialTicks)
    }

    private fun displayNbt() {
        val stack = minecraft!!.player!!.getHeldItem(Hand.MAIN_HAND)
        ItemUtils.displayItemNbt(stack)
    }

    private fun displayRayTraceNbt() {
        val rayTraceResult = ItemUtils.minecraft.player!!.pick(20.0, 0f, false)
        val pos = (rayTraceResult as BlockRayTraceResult).pos
        val state = ItemUtils.minecraft.world!!.getBlockState(pos)
        val stack = state.block.getItem(ItemUtils.minecraft.world!!, pos, state)
        ItemUtils.displayItemNbt(stack)
    }
}