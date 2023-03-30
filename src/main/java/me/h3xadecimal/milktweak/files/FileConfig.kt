package me.h3xadecimal.milktweak.files

import com.google.gson.Gson
import org.apache.commons.io.IOUtils
import java.io.BufferedOutputStream
import java.io.File
import java.io.ObjectInput
import java.nio.charset.StandardCharsets
import java.nio.file.Files

class FileConfig private constructor(private val map: HashMap<String, Any>) {
    companion object {
        @JvmStatic
        fun load(file: File): FileConfig {
            val bytes = IOUtils.toByteArray(Files.newInputStream(file.toPath()))
            val str = String(bytes, StandardCharsets.UTF_8)
            return FileConfig(Gson().fromJson(str, HashMap::class.java) as HashMap<String, Any>)
        }

        @JvmStatic
        fun empty(): FileConfig {
            return FileConfig(HashMap())
        }
    }

    override fun toString(): String {
        return Gson().toJson(map)
    }

    fun save(file: File) {
        val bos = BufferedOutputStream(Files.newOutputStream(file.toPath()))
        bos.write(toString().toByteArray(StandardCharsets.UTF_8))
        bos.close()
    }

    fun contains(key: String): Boolean {
        return map.contains(key)
    }

    fun get(key: String): Any? {
        return map[key]
    }

    fun put(key: String, value: Any) {
        map[key] = value
    }

    fun getString(key: String): String? {
        if (contains(key) && get(key) is String) {
            return get(key) as String
        }
        return null
    }

    fun getBoolean(key: String): Boolean? {
        if (contains(key) && get(key) is Boolean) {
            return get(key) as Boolean
        }
        return null
    }
}