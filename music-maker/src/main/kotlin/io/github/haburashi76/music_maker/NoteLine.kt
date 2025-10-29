package io.github.haburashi76.music_maker

import java.util.SortedMap

class NoteLine private constructor(
    private val notes: SortedMap<ULong, Note> = sortedMapOf()
): Cloneable, SortedMap<ULong, Note> by notes {

    companion object {
        @Deprecated("from은 SortedMap에서 가져온다는 의미인데 SortedMap 없이 " +
                "생성하고 싶을수도 있으니까. make 함수 인자에 SortedMap 넣으면 똑같음")
        fun from(map: SortedMap<ULong, Note>, modify: NoteLine.() -> Unit): NoteLine {
            val newNoteLine = NoteLine(map)
            modify(newNoteLine)
            return newNoteLine
        }

        fun make(map: SortedMap<ULong, Note> = sortedMapOf(), modify: NoteLine.() -> Unit): NoteLine {
            val newNoteLine = NoteLine(map)
            modify(newNoteLine)
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

    fun shiftSoThatFirstKeyIsZero(): NoteLine = shift(-lastKey().toLong())

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

    fun addNote(delay: ULong, note: Note): NoteLine = apply {
        if (isEmpty()) {
            put(delay, note)
        } else {
            put(lastKey() + delay, note)
        }
    }

}