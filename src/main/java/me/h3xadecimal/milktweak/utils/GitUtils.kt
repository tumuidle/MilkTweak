package me.h3xadecimal.milktweak.utils

import me.h3xadecimal.milktweak.Milktweak
import java.util.*

object GitUtils {
    @JvmStatic
    val gitInfo = Properties().also {
        val inputStream = Milktweak::class.java.classLoader.getResourceAsStream("git.properties")

        if(inputStream != null) {
            it.load(inputStream)
        } else {
            it["git.build.version"] = "unofficial"
        }
    }

    @JvmStatic
    fun getBuildVersion(): String {
        return gitInfo["git.build.version"] as String
    }

    @JvmStatic
    fun getCommitHash(): String {
        return (gitInfo["git.commit.id.abbrev"] ?: "Unknown") as String
    }
}