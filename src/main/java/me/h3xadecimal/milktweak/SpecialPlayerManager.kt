package me.h3xadecimal.milktweak

import java.util.HashMap

object SpecialPlayerManager {
    private val map = HashMap<String, String>()

    private const val STAFF_PREFIX = "§7[§cSTAFF§7]§r"
    private const val DEV_PREFIX = "§7[§aDEV§7]§r"

    init {
        map["TumuIDLE"] = "$DEV_PREFIX TumuIDLE"
        map["Dev"] = "丁真"
        map["NIUNAI_milk"] = "$STAFF_PREFIX NIUNAI_milk"
    }

    fun hasDisplayName(name: String): Boolean {
        return map.containsKey(name.lowercase())
    }

    fun getDisplayName(name: String): String {
        return map[name.lowercase()] ?: name
    }
}