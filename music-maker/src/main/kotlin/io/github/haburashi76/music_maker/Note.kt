package io.github.haburashi76.music_maker

import org.bukkit.SoundCategory
import org.bukkit.entity.Player
import kotlin.math.log2
import kotlin.math.pow

data class Note(var pitch: Float, var type: NoteType): Cloneable {

    object Pitch {
        fun from(timesOfRightClick: Double): Float {
            if (timesOfRightClick !in 0.0..24.0) throw IllegalArgumentException("times of right click must between 0.0 and 24.0.")
            return 2.0.pow((timesOfRightClick - 12) / 12.0).toFloat()
        }
        fun getTimesOfRightClick(pitch: Float): Double {
            if (pitch !in 0.0..2.0) throw IllegalArgumentException("pitch must between 0.0 and 2.0.")
            return (12.0 * log2(pitch)) + 12.0
        }
        const val F_SHARP_1 = 0.5f
        const val FA_SHARP_0 = F_SHARP_1
        const val G_1 = 0.529732f
        const val SOL_0 = G_1
        const val G_SHARP_1 = 0.561231f
        const val SOL_SHARP_0 = G_SHARP_1
        const val A_1 = 0.594604f
        const val LA_0 = A_1
        const val A_SHARP_1 = 0.629961f
        const val LA_SHARP_0 = A_SHARP_1
        const val B_1 = 0.667420f
        const val SI_0 = B_1
        const val C_1 = 0.707107f
        const val DO_1 = C_1
        const val C_SHARP_1 = 0.749154f
        const val DO_SHARP_1 = C_SHARP_1
        const val D_1 = 0.793701f
        const val RE_1 = D_1
        const val D_SHARP_1 = 0.840896f
        const val RE_SHARP_1 = D_SHARP_1
        const val E_1 = 0.890899f
        const val MI_1 = E_1
        const val F_1 = 0.943874f
        const val FA_1 = F_1
        const val F_SHARP_2 = 1.0f
        const val FA_SHARP_1 = F_SHARP_2
        const val G_2 = 1.059463f
        const val SOL_1 = G_2
        const val G_SHARP_2 = 1.122462f
        const val SOL_SHARP_1 = G_SHARP_2
        const val A_2 = 1.189207f
        const val LA_1 = A_2
        const val A_SHARP_2 = 1.259921f
        const val LA_SHARP_1 = A_SHARP_2
        const val B_2 = 1.334840f
        const val SI = B_2
        const val C_2 = 1.414214f
        const val DO_2 = C_2
        const val C_SHARP_2 = 1.498307f
        const val DO_SHARP_2 = C_SHARP_2
        const val D_2 = 1.587401f
        const val RE_2 = D_2
        const val D_SHARP_2 = 1.681793f
        const val RE_SHARP_2 = D_SHARP_2
        const val E_2 = 1.781797f
        const val MI_2 = E_2
        const val F_2 = 1.887749f
        const val FA_2 = F_2
        const val F_SHARP_3 = 2.0f
        const val FA_SHARP_2 = F_SHARP_3
    }

    fun play(player: Player) {
        player.world.playSound(player, type.sound, SoundCategory.MASTER, 2.0f, pitch)
    }

    public override fun clone(): Note {
        return Note(pitch, type)
    }
}