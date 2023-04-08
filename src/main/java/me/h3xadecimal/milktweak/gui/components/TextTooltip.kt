package me.h3xadecimal.milktweak.gui.components

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.util.text.StringTextComponent

class TextTooltip(val screen: Screen, val text: String): Button.ITooltip {
    override fun onTooltip(
        p_onTooltip_1_: Button,
        p_onTooltip_2_: MatrixStack,
        p_onTooltip_3_: Int,
        p_onTooltip_4_: Int
    ) {
        if (p_onTooltip_1_.active) {
            screen.renderTooltip(p_onTooltip_2_, StringTextComponent(text), p_onTooltip_3_, p_onTooltip_4_)
        }
    }
}