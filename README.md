# Music-Maker

[![Kotlin](https://img.shields.io/badge/java-21-ED8B00.svg?logo=java)](https://www.azul.com/)
[![Kotlin](https://img.shields.io/badge/kotlin-2.0.0-585DEF.svg?logo=kotlin)](http://kotlinlang.org)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.haburashi76/music-maker)](https://search.maven.org/artifact/io.github.haburashi76/music-maker)

[![GitHub](https://img.shields.io/github/license/haburashi76/music-maker)](https://www.gnu.org/licenses/gpl-3.0.html)

### Kotlin DSL for PaperMC for Note Block Music Production

---

* ### 기능
  * Music
  * NoteLine
  * Note

---

#### Gradle

```kotlin
repositories {
    mavenCentral()
}
```

```kotlin
dependencies {
    implementation("io.github.haburashi76:music-maker:<version>")
}
```

#### plugins.yml

```yaml
name: ...
version: ...
main: ...
libraries:
  - io.github.haburashi76:music-maker:<version>
```

---

### Note
* 하나의 '음'입니다.
* pitch와 NoteType의 정보를 갖습니다.
* 직접 플레이어에게 재생시킬 수도 있습니다.
- ### NoteType
  - 소리블록 아래 설치하여 변하는 소리의 종류입니다. 
  HARP, BASS 등등이 있습니다.

### NoteLine
* Note가 모인 SortedMap<Long, Note>입니다.
* SortedMap<Long, Note>에서 NoteLine을 생성할 수도 있습니다.
* 키 올리기, 박자 미루기 등등이 가능합니다.

### Music
* NoteLine이 모인 MutableList입니다.
* MusicManager을 통해 생성 가능하며
line { ... } 으로 Music에 
NoteLine을 추가할 수 있습니다.
* play(...)으로 플레이어에게 재생시킬 수 있습니다.
* 재생 시 Music 내의 모든 NoteLine이 재생됩니다.

#### 예제 코드
```kotlin
package `<package>`

import io.github.haburashi76.music_maker.MusicManager
import io.github.haburashi76.music_maker.Note
import io.github.haburashi76.music_maker.NoteType
import ...
...
...


class MyMusicMakerPlugin: JavaPlugin(), Listener {
    val music = MusicManager.music {
        line {
            //배드애플
            put(20uL, Note(Note.Pitch.RE_1, NoteType.XYLOPHONE)) // ULong 타입
            put(24uL, Note(Note.Pitch.MI_1, NoteType.XYLOPHONE)) // 실로폰
            put(28uL, Note(Note.Pitch.FA_1, NoteType.XYLOPHONE)) // 음악 시작 후 28틱째에 재생
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
        
        line {
            addNote(20uL, Note(...)) // 20틱째에 재생
            addNote(10uL, Note(...)) // 마지막 틱(20) + 10틱째에 재생
        }
    }
  
    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
    }
  
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        object : BukkitRunnable() {
            override fun run() {
                music.play(event.player, this@TestPlugin, 1L) //1틱(마크 기준)마다 다음 틱(음악 기준)
            }
        }.runTaskLater(this, 30L)
    }
}
```