package me.h3xadecimal.milktweak.utils

import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.math.RayTraceResult
import net.minecraft.util.text.StringTextComponent

object ItemUtils {
    val minecraft: Minecraft get() = Minecraft.getInstance()

    @JvmStatic
    fun displayNbt(tag: CompoundNBT) {
        try {
            for (key in tag.keySet()) {
                val value = if (tag.get(key) != null) {
                    tag.get(key)!!.toFormattedComponent().string
                } else {
                    "null"
                }
                minecraft.ingameGUI.chatGUI.printChatMessage(StringTextComponent("键 §a$key§r: $value"))
            }
        } catch (t: Throwable) {
            minecraft.ingameGUI.chatGUI.printChatMessage(StringTextComponent("NBT查询失败：${t.javaClass.simpleName}(${t.message})"))
        }
    }

    @JvmStatic
    fun displayItemNbt(stack: ItemStack) {
        if (stack.tag == null) {
            minecraft.ingameGUI.chatGUI.printChatMessage(StringTextComponent("物品 ${stack.displayName.string} 没有NBT数据"))
            return
        }
        displayNbt(stack.tag!!)
    }
}