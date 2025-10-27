package io.github.haburashi76.music_maker

import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class Music internal constructor(
    private val lines: MutableList<NoteLine> = mutableListOf()
): MutableList<NoteLine> by lines {

    fun line(modify: NoteLine.() -> Unit): NoteLine {
        val line = NoteLine()
        modify(line)
        add(line)
        return line
    }

    fun play(player: Player, plugin: Plugin, tickPeriod: Long = 2L) {
        MusicManager.play(player, this, plugin, tickPeriod)
    }
}