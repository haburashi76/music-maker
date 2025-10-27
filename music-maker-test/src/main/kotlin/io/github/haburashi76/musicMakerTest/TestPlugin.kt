package io.github.haburashi76.musicMakerTest

import io.github.haburashi76.music_maker.MusicManager
import io.github.haburashi76.music_maker.Note
import io.github.haburashi76.music_maker.NoteType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable


class TestPlugin: JavaPlugin(), Listener {
    val music = MusicManager.music {
        line {
            put(20uL, Note(Note.Pitch.RE_1, NoteType.XYLOPHONE))
            put(24uL, Note(Note.Pitch.MI_1, NoteType.XYLOPHONE))
            put(28uL, Note(Note.Pitch.FA_1, NoteType.XYLOPHONE))
            put(32uL, Note(Note.Pitch.SOL_1, NoteType.XYLOPHONE))
            put(36uL, Note(Note.Pitch.LA_1, NoteType.XYLOPHONE))

            put(45uL, Note(Note.Pitch.RE_2, NoteType.XYLOPHONE))
            put(49uL, Note(Note.Pitch.DO_2, NoteType.XYLOPHONE))
            put(53uL, Note(Note.Pitch.LA_1, NoteType.XYLOPHONE))

            put(62uL, Note(Note.Pitch.RE_1, NoteType.XYLOPHONE))

            put(71uL, Note(Note.Pitch.LA_1, NoteType.XYLOPHONE))
            put(75uL, Note(Note.Pitch.SOL_1, NoteType.XYLOPHONE))
            put(79uL, Note(Note.Pitch.FA_1, NoteType.XYLOPHONE))
            put(83uL, Note(Note.Pitch.MI_1, NoteType.XYLOPHONE))
        }
    }
    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
    }
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        object : BukkitRunnable() {
            override fun run() {
                music.play(event.player, this@TestPlugin, 1L)
            }
        }.runTaskLater(this, 30L)
    }
}