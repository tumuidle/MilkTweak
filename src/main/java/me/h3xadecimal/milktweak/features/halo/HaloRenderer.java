package me.h3xadecimal.milktweak.features.halo;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class HaloRenderer extends PlayerRenderer {
    public HaloRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(@NotNull AbstractClientPlayerEntity entity, float entityYaw, float partialTicks, @NotNull MatrixStack matrixStack, @NotNull IRenderTypeBuffer buffer, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLightIn);
        if (Halo.get(entity) == Halo.HaloType.None) return;

        // Save the current matrix stack
        matrixStack.push();

        // Translate the matrix stack to the top of the entity
        matrixStack.translate(0.0, entity.getHeight() + 0.5, 0.0);

        // Set the texture to render
        Minecraft.getInstance().getTextureManager().bindTexture(Halo.getResource(entity));

        // Create a buffer builder for the texture
        IVertexBuilder builder = buffer.getBuffer(RenderType.getEntityTranslucent(new ResourceLocation("mymod", "textures/entity/cow_overlay.png")));

        // Define the vertices and texture coordinates for the texture
        builder.pos(-0.5, 0.0, -0.5).tex(0.0f, 0.0f).endVertex();
        builder.pos(-0.5, 0.0, 0.5).tex(0.0f, 1.0f).endVertex();
        builder.pos(0.5, 0.0, 0.5).tex(1.0f, 1.0f).endVertex();
        builder.pos(0.5, 0.0, -0.5).tex(1.0f, 0.0f).endVertex();

        // Pop the matrix stack to restore the previous state
        matrixStack.pop();
    }
}
