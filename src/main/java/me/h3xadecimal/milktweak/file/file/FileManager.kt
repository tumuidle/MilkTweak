package me.h3xadecimal.milktweak.file.file

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import me.h3xadecimal.milktweak.Milktweak
import net.minecraft.client.Minecraft
import org.apache.commons.io.IOUtils
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.nio.charset.StandardCharsets
import java.nio.file.Files

object FileManager {
    private val mc get() = Minecraft.getInstance();

    val folder = File(mc.gameDir, "MilkTweak")
    val fontsFolder = File(folder, "fonts")
    val cfgFile = File(folder, "config.json")

    val PRETTY_GSON: Gson = GsonBuilder().setPrettyPrinting().create()

    var cfg = HashMap<String, Any>()
    private set

    fun init() {
        if (!folder.exists()) folder.mkdirs()
        if (!fontsFolder.exists()) fontsFolder.mkdirs()
        if (!cfgFile.exists()) cfgFile.createNewFile()

        loadConfig()
    }

    fun saveConfig() {
        Milktweak.LOGGER.info("Saving configuration")
        val bos = BufferedOutputStream(Files.newOutputStream(cfgFile.toPath()))
        val json = PRETTY_GSON.toJson(cfg)
        bos.write(json.toByteArray(StandardCharsets.UTF_8))
        bos.close()
    }

    private fun loadConfig() {
        val bytes = IOUtils.toByteArray(FileInputStream(cfgFile)) ?: return
        cfg = Gson().fromJson(String(bytes), cfg::class.java) ?: return
    }
}