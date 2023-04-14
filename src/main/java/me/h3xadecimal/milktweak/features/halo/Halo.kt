package me.h3xadecimal.milktweak.features.halo

import com.google.gson.Gson
import com.mojang.blaze3d.matrix.MatrixStack
import me.h3xadecimal.milktweak.MilkTweak
import me.h3xadecimal.milktweak.gui.GuiTeleport.Companion.mc
import me.h3xadecimal.milktweak.utils.HttpUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.entity.player.AbstractClientPlayerEntity
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.WorldVertexBufferUploader
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.vector.Quaternion
import net.minecraftforge.client.event.RenderPlayerEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import org.lwjgl.opengl.GL11
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import kotlin.collections.HashMap
import kotlin.collections.set


object Halo {
    @JvmStatic
    private val halo = HashMap<String, HaloType>()

    @JvmStatic
    fun loadHaloFromCloud() {
        try {
            val get = HttpUtils.get("http://api-v2.afternode.cn/static/milktweak-halo.json")
            MilkTweak.LOGGER.info(get)
            val result = Gson().fromJson(get, HashMap::class.java) as HashMap<String, String>
            for (r in result.keys) {
                try {
                    halo[r] = HaloType.valueOf(result[r]!!)
                    MilkTweak.LOGGER.info("[Halo] Player $r -> ${HaloType.valueOf(result[r]!!).name}")
                }catch (t: Throwable) {
                    MilkTweak.LOGGER.error("Cannot load halo for player $r")
                }
            }
        } catch (t: Throwable) {
            MilkTweak.LOGGER.error("Failed to load halo", t)
        }
    }

    enum class HaloType(val resource: String, open val size: Float = 0.5f, val reverse: Boolean = false) {
        None(""),
        Shiroko("shiroko", reverse=true),
        Alis("alis")
    }

    @JvmStatic
    fun get(e: PlayerEntity): HaloType {
        return halo[e.name.string] ?: HaloType.None
    }

    @JvmStatic
    fun getResource(e: PlayerEntity): ResourceLocation? {
        return ResourceLocation("milktweak", "halo/std/${get(e).resource}.png")
    }

    @JvmStatic
    fun getReverseResource(e: PlayerEntity): ResourceLocation? {
        return ResourceLocation("milktweak", "halo/reverse/${get(e).resource}.png")
    }

    @SubscribeEvent
    fun onRenderPlayer(e: RenderPlayerEvent.Pre) {
        if (halo.containsKey(e.player.name.string) && halo[e.player.name.string] != HaloType.None) {
            val entity = e.entity
            onOldRender(entity.posX, entity.posY, entity.posZ, halo[e.player.name.string]!!, e)
        }
    }

    @JvmStatic
    open fun handleMixinRender(
        entity: AbstractClientPlayerEntity,
        entityYaw: Float,
        partialTicks: Float,
        matrixStack: MatrixStack,
        buffer: IRenderTypeBuffer,
        light: Int,
        ci: CallbackInfo?
    ) {
        if (get(entity) === HaloType.None) return
        val type = get(entity)

        // Save the current matrix stack
        matrixStack.push()

        // Translate the matrix stack to the top of the entity
        matrixStack.translate(entity.lastTickPosX, entity.lastTickPosY + entity.height + 0.5, entity.lastTickPosZ)
        matrixStack.rotate(Quaternion(90.0f, 1.0f, 0.0f, 0.0f)) // rotate the image to be horizontal
        //        matrixStack.rotate(new Quaternion(entity.rotationYawHead, 0.0F, 0.0F, 1.0F));
//        matrixStack.rotate(new Quaternion(25f, 1.0f, 0.0f, 0.0f));
        matrixStack.scale(type.size, type.size, type.size) // adjust the size of the image

        // Set the texture to render
        Minecraft.getInstance().getTextureManager().bindTexture(getResource(entity))

        // Create a buffer builder for the texture
        val builder = buffer.getBuffer(RenderType.getEntityTranslucent(getResource(entity)))

        // Define the vertices and texture coordinates for the texture
        builder.pos(matrixStack.last.matrix, -0.5f, 0.0f, -0.5f).color(255, 255, 255, 255).tex(0.0f, 0.0f)
            .lightmap(light).endVertex()
        builder.pos(matrixStack.last.matrix, -0.5f, 0.0f, 0.5f).color(255, 255, 255, 255).tex(1.0f, 0.0f)
            .lightmap(light).endVertex()
        builder.pos(matrixStack.last.matrix, 0.5f, 0.0f, 0.5f).color(255, 255, 255, 255).tex(1.0f, 1.0f)
            .lightmap(light).endVertex()
        builder.pos(matrixStack.last.matrix, 0.5f, 0.0f, -0.5f).color(255, 255, 255, 255).tex(0.0f, 1.0f)
            .lightmap(light).endVertex()

        // Pop the matrix stack to restore the previous state
        matrixStack.pop()
    }

    @Suppress("DEPRECATION")
    fun onRender(x: Double, y: Double, z: Double, type: HaloType, event: RenderPlayerEvent.Pre) {
        val texture = ResourceLocation("milktweak", "halo/std/${type.resource}.png")
        val textureReverse = if (type.reverse) {
            texture
        } else {
            ResourceLocation("milktweak", "halo/reverse/${type.resource}.png")
        }

        val matrixStack = event.matrixStack
        val buffer = event.buffers
        val light = event.light
        val player = event.entity as PlayerEntity
        val minecraft = Minecraft.getInstance()
        val textureWidth: Int = 128
        val textureHeight: Int = 128
        matrixStack.push()
        matrixStack.translate(x, y + player.height + 0.2, z)
        matrixStack.rotate(minecraft.renderManager.cameraOrientation)
        event.matrixStack.rotate(Quaternion(90.0F, 1.0F, 0.0F, 0.0F)) // rotate the image to be horizontal
//        event.matrixStack.rotate(Quaternion(event.player.rotationYawHead, 0.0F, 0.0F, 1.0F))
//        event.matrixStack.rotate(Quaternion(25f, 1.0f, 0.0f, 0.0f))
        event.matrixStack.scale(type.size, type.size, type.size) // adjust the size of the image
        minecraft.getTextureManager().bindTexture(texture)
        val builder = buffer.getBuffer(RenderType.getText(texture))
        builder.pos(matrixStack.last.matrix, -textureWidth / 2.0f, 0.0f, 0.0f).color(255, 255, 255, 255).tex(0.0f, 0.0f)
            .lightmap(light).endVertex()
        builder.pos(matrixStack.last.matrix, textureWidth / 2.0f, 0.0f, 0.0f).color(255, 255, 255, 255).tex(1.0f, 0.0f)
            .lightmap(light).endVertex()
        builder.pos(matrixStack.last.matrix, textureWidth / 2.0f, textureHeight.toFloat(), 0.0f)
            .color(255, 255, 255, 255).tex(1.0f, 1.0f).lightmap(light).endVertex()
        builder.pos(matrixStack.last.matrix, -textureWidth / 2.0f, textureHeight.toFloat(), 0.0f)
            .color(255, 255, 255, 255).tex(0.0f, 1.0f).lightmap(light).endVertex()
        matrixStack.pop()
    }

    @Suppress("DEPRECATION")
    fun onOldRender(x: Double, y: Double, z: Double, type: HaloType, event: RenderPlayerEvent) {
        val res = ResourceLocation("milktweak", "halo/std/${type.resource}.png")
        val stack = event.matrixStack
        val buf = event.buffers

        mc.textureManager.bindTexture(res)
        stack.push()
        stack.translate(x, y+event.entity.height+0.2, z)
        stack.rotate(Quaternion(90.0f, 1.0f, 0.0f, 0.0f))
        val tessellator = Tessellator.getInstance()
        val builder = tessellator.buffer
        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX)
        builder.pos(-1.0, -1.0, 0.0).tex(0.0f, 0.0f).endVertex()
        builder.pos(-1.0, 1.0, 0.0).tex(0.0f, 1.0f).endVertex()
        builder.pos(1.0, 1.0, 0.0).tex(1.0f, 1.0f).endVertex()
        builder.pos(1.0, -1.0, 0.0).tex(1.0f, 0.0f).endVertex()
        builder.finishDrawing()
        WorldVertexBufferUploader.draw(builder)
        stack.pop()
    }
}