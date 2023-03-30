package me.h3xadecimal.milktweak.mixin.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.h3xadecimal.milktweak.utils.RenderUtil;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DisconnectedScreen.class)
public abstract class MixinDisconnectedScreen extends Screen {

    @Shadow private IBidiRenderer field_243289_b;

    @Shadow private int textHeight;

    protected MixinDisconnectedScreen(ITextComponent titleIn) {
        super(titleIn);
    }

    /**
     * @author H3xadecimal
     * @reason 喜报
     */
    @Overwrite
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawImage(new ResourceLocation("milktweak", "xibao"), 0, 0, width, height);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, this.height / 2 - textHeight / 2 - 9 * 2, 11184810);
        field_243289_b.func_241863_a(matrixStack, this.width / 2, this.height / 2 - textHeight / 2);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
