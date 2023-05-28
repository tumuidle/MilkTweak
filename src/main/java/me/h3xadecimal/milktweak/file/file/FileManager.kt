package me.h3xadecimal.milktweak.file.file

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import net.minecraft.client.Minecraft
import java.io.File

object FileManager {
    private val mc get() = Minecraft.getInstance();

    val folder = File(mc.gameDir, "MilkTweak")
    val fontsFolder = File(folder, "fonts")

    val PRETTY_GSON: Gson = GsonBuilder().setPrettyPrinting().create()

    fun init() {
        if (!folder.exists()) folder.mkdirs()
        if (!fontsFolder.exists()) fontsFolder.mkdirs()
    }
}