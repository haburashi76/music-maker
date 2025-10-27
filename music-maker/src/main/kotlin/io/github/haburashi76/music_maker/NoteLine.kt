package io.github.haburashi76.music_maker

import java.util.SortedMap

class NoteLine internal constructor(
    private val notes: SortedMap<ULong, Note> = sortedMapOf()
): Cloneable, SortedMap<ULong, Note> by notes {

    companion object {
        fun from(map: Map<ULong, Note>): NoteLine {
            val newNoteLine = NoteLine()
            map.forEach {
                newNoteLine.putIfAbsent(it.key, it.value)
            }
            return newNoteLine
        }
    }

    public override fun clone(): NoteLine {
        val newMap = sortedMapOf<ULong, Note>()
        notes.forEach {
            newMap[it.key] = it.value.clone()
        }
        return NoteLine(newMap)
    }

    fun subLine(from: ULong, to: ULong): NoteLine = NoteLine(notes.subMap(from, to))

    fun shift(offset: Long): NoteLine {
        if (any { it.key.toLong() + offset < 0 }) throw IllegalArgumentException("Slots cannot be negative.")
        val shifted = mapKeys { (it.key.toLong() + offset).toULong() }.toSortedMap()
        notes.clear()
        notes.putAll(shifted)
        return this
    }

    fun shiftSoThatFirstKeyIsZero(): NoteLine {
        val shifted = mapKeys { it.key - firstKey() }.toSortedMap()
        notes.clear()
        notes.putAll(shifted)
        return this
    }

    fun moveUpKey(offset: Double): NoteLine {
        if (any { Note.Pitch.getTimesOfRightClick(it.value.pitch) + offset !in 0.0..24.0 })
            throw IllegalArgumentException("times of right click cannot be less than 0 or greater than 24.")
        forEach { it.value.pitch =
            Note.Pitch.from(Note.Pitch.getTimesOfRightClick(it.value.pitch) + offset)
        }
        return this
    }

    fun repeat(times: Int, interval: ULong = 1UL): NoteLine {
        val cycle = clone().shiftSoThatFirstKeyIsZero()
        val newNoteLine = clone()
        if (interval < 1u) throw IllegalArgumentException("interval cannot be less than 1.")
        repeat(times) {
            val last = newNoteLine.lastKey()
            cycle.forEach { (slot, note) ->
                newNoteLine[slot + last + interval] = note.clone()
            }
        }
        return newNoteLine
    }
}