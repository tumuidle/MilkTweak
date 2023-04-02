package me.h3xadecimal.milktweak.gui

import com.mojang.blaze3d.matrix.MatrixStack
import me.h3xadecimal.milktweak.files.FilesManager
import me.h3xadecimal.milktweak.utils.RenderUtil
import net.minecraft.client.gui.AbstractGui
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.util.text.StringTextComponent
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.ServerChatEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

class GuiBlackList: Screen(StringTextComponent("BlackList")) {
    companion object {
        @JvmStatic
        var blacklist: ArrayList<String> = ArrayList()

        @JvmStatic
        fun validateBlacklist(target: String): Boolean {
            for (s in blacklist) {
                if (target.lowercase().contains(target)) return true
            }
            return false
        }
    }

    lateinit var nameField: TextFieldWidget
    lateinit var buttonAdd: Button
    lateinit var buttonRemove: Button

    override fun init() {
        blacklist = ArrayList()
        blacklist.addAll(FilesManager.readBlacklists())

        nameField = TextFieldWidget(minecraft!!.fontRenderer, width/2-100, 50, 200, 20, StringTextComponent("AddBlacklist"))
        buttonAdd = addButton(Button(width/2-105, 80, 100, 20, StringTextComponent("添加")){add()})
        buttonRemove = addButton(Button(width/2+5, 80, 100, 20, StringTextComponent("移除")){remove()})

        children.add(nameField)
        MinecraftForge.EVENT_BUS.register(this)
    }

    override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrixStack)
        val fr = minecraft!!.fontRenderer

        fr.drawTextWithShadow(matrixStack, StringTextComponent("聊天屏蔽"), RenderUtil.getCenterX(this, "聊天屏蔽").toFloat(), 25f, 0xFFFFFF)

        var i = buttonRemove.y + 25
        for (s in blacklist) {
            fr.drawTextWithShadow(matrixStack, StringTextComponent(s), RenderUtil.getCenterX(this, s).toFloat(), i.toFloat(), 0xFFFFFF)
            i += 20
        }

        nameField.render(matrixStack, mouseX, mouseY, partialTicks)
        super.render(matrixStack, mouseX, mouseY, partialTicks)
    }

    override fun tick() {
        if (nameField.text == "") {
            buttonAdd.active = false
            buttonRemove.active = false
        } else {
            buttonAdd.active = true
            buttonRemove.active = true
        }

        nameField.tick()
    }

    override fun onClose() {
        MinecraftForge.EVENT_BUS.unregister(this)
        try {
            FilesManager.saveBlacklists(blacklist)
        } catch (t: Throwable) {
            minecraft!!.player!!.sendStatusMessage(StringTextComponent("警告：黑名单保存失败 (${t.javaClass.simpleName}: ${t.message})"), false)
            t.printStackTrace()
        }
    }

    private fun add() {
        if (nameField.text != "") {
            blacklist.add(nameField.text.lowercase())
        }
    }

    private fun remove() {
        for (s in 0 until blacklist.size-1) {
            if (blacklist[s].lowercase() == nameField.text.lowercase()) {
                blacklist.removeAt(s)
            }
        }
    }

    @SubscribeEvent
    fun onServerChat(e: ServerChatEvent) {
        if (blacklist.contains(e.username.lowercase()) || validateBlacklist(e.message)) {
            e.isCanceled = true
        }
    }

    @SubscribeEvent
    fun onClientChatReceived(e: ClientChatReceivedEvent) {
        if (validateBlacklist(e.message.string)) e.isCanceled = true
    }
}