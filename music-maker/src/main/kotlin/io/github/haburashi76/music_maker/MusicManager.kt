package io.github.haburashi76.music_maker

import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

object MusicManager {

    fun music(vararg noteLine: NoteLine, modify: Music.() -> Unit): Music {
        val result = Music(noteLine.toMutableList())
        modify(result)
        return result
    }
    fun music(list: List<NoteLine> = mutableListOf(), modify: Music.() -> Unit): Music {
        val result = Music(list.toMutableList())
        modify(result)
        return result
    }

    fun play(player: Player, music: Music, plugin: Plugin, tickPeriod: Long = 2L): BukkitTask {
        return object : BukkitRunnable() {
            private var i = 0
            override fun run() {
                music.forEach { line ->
                    line.forEach { entry ->
                        if (i.toULong() == entry.key) {
                            entry.value.play(player)
                        }
                    }
                }
                if (music.all { it.lastKey() < i.toULong() }) cancel()
                i++
            }
        }.runTaskTimer(plugin, 0L, tickPeriod)
    }
}