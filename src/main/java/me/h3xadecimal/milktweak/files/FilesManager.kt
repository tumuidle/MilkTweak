package me.h3xadecimal.milktweak.files

import com.google.gson.Gson
import net.minecraft.client.Minecraft
import java.io.BufferedOutputStream
import java.io.File
import java.io.IOException
import java.lang.IllegalArgumentException
import java.nio.charset.StandardCharsets
import java.nio.file.Files

object FilesManager {
    @JvmStatic
    val mc get() = Minecraft.getInstance()

    @JvmStatic
    val dir = File("MilkTweak")
    @JvmStatic
    val blackListFile = File(dir, "blacklist.json")
    @JvmStatic
    val mainConfig = File(dir, "config.json")

    @JvmStatic
    fun load() {
        if (!dir.exists() && !dir.mkdir()) throw IOException("Failed creating base dir")
        if (!blackListFile.exists() && !blackListFile.createNewFile()) throw IOException("Failed creating blacklist config")
    }

    @JvmStatic
    fun readBlacklists(): List<String> {
        return try {
            val read = String(Files.readAllBytes(blackListFile.toPath()), StandardCharsets.UTF_8)
            var map = HashMap<String, Any>()
            map = Gson().fromJson(read, map::class.java)
            map["blacklist"] as List<String>
        } catch (t: Throwable) {
            emptyList()
        }
    }

    @JvmStatic
    fun saveBlacklists(blacklist: List<String>) {
        val map = HashMap<String, Any>()
        map["blacklist"] = blacklist
        val str = Gson().toJson(map)
        Files.write(blackListFile.toPath(), str.toByteArray(StandardCharsets.UTF_8))
    }
    
    @JvmStatic
    fun saveConfig(config: FileConfig, file: File) {
        val bos = BufferedOutputStream(Files.newOutputStream(file.toPath()))
        bos.write(config.toString().toByteArray(StandardCharsets.UTF_8))
        bos.close()
    }
}