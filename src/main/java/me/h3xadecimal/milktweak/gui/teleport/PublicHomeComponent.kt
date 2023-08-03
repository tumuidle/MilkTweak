package me.h3xadecimal.milktweak.gui.teleport

import me.h3xadecimal.milktweak.gui.button.GuiButton
import net.minecraft.client.Minecraft

class PublicHomeComponent(x: Int, y: Int, title: String, id: String): GuiButton(x, y, 100, 20, title, {
    Minecraft.getInstance().player!!.sendChatMessage("/phome $id")
})