package me.h3xadecimal.milktweak.gui.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.h3xadecimal.milktweak.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.awt.Color;

public class GuiButton extends net.minecraft.client.gui.widget.button.Button{
    private static final Color color = new Color(120, 120, 120, 138);
    private static final Color colorHovered = new Color(83, 83, 83, 138);

    public GuiButton(int x, int y, int width, int height, String title, IPressable pressedAction) {
        super(x, y, width, height, new StringTextComponent(title), pressedAction);
    }

    @Override
    public void renderWidget(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (isHovered()) {
            RenderUtils.drawRect(matrixStack, x, y, width, height, colorHovered.getRGB());
        } else {
            RenderUtils.drawRect(matrixStack, x, y, width, height, color.getRGB());
        }

        FontRenderer fr = Minecraft.getInstance().fontRenderer;
        fr.drawStringWithShadow(matrixStack, getMessage().getString(), x+(width-fr.getStringWidth(getMessage().getString()))/2, y+height/2, 0xFFFFFF);

        if (this.isHovered()) {
            this.renderToolTip(matrixStack, mouseX, mouseY);
        }
    }
}
