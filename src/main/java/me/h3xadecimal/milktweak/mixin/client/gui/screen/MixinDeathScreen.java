package me.h3xadecimal.milktweak.mixin.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;

@Mixin(DeathScreen.class)
public abstract class MixinDeathScreen extends Screen {
    @Shadow @Final private ITextComponent causeOfDeath;

    @Shadow private ITextComponent field_243285_p;

    @Shadow @Nullable protected abstract Style func_238623_a_(int p_238623_1_);

    protected MixinDeathScreen(ITextComponent titleIn) {
        super(titleIn);
    }

    /**
     * @author H3xadecimal
     * @reason null
     */
    @Overwrite
    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.fillGradient(matrixStack, 0, 0, this.width, this.height, 1615855616, -1602211792);
        RenderSystem.pushMatrix();
        RenderSystem.scalef(2.0F, 2.0F, 2.0F);
        drawCenteredString(matrixStack, this.font, "被撅力（悲）", this.width / 2 / 2, 30, 16777215);
        RenderSystem.popMatrix();
        if (causeOfDeath != null) {
            drawCenteredString(matrixStack, this.font, this.causeOfDeath, this.width / 2, 85, 16777215);
        }

        drawCenteredString(matrixStack, this.font, field_243285_p, this.width / 2, 100, 16777215);
        if (this.causeOfDeath != null && mouseY > 85 && mouseY < 85 + 9) {
            Style style = func_238623_a_(mouseX);
            this.renderComponentHoverEffect(matrixStack, style, mouseX, mouseY);
        }

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
