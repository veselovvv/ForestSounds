package com.veselovvv.forestsounds

import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException

class ForestSounds(private val assets: AssetManager) {
    private val sounds: List<Sound>

    // SoundPool для управления звуками:
    private val soundPool = SoundPool.Builder().setMaxStreams(MAX_SOUNDS_NUMBER).build()

    init {
        sounds = loadSounds()
    }

    // Воспроизведение звуков:
    fun play(sound: Sound) {
        sound.id?.let {
            soundPool.play(it, 1.0f, 1.0f, 1, 0, 1.0f)
        }
    }

    // Функция освобождения SoundPool:
    fun release() = soundPool.release()

    fun getSounds() = sounds

    fun loadSounds(): List<Sound> {
        val soundNames: Array<String>

        try {
            soundNames = assets.list(SOUNDS_FOLDER)!!
        } catch (exception: Exception) {
            return emptyList()
        }

        val sounds = mutableListOf<Sound>()

        soundNames.forEach { name ->
            val assetPath = "$SOUNDS_FOLDER/$name"
            val sound = Sound(assetPath)

            try {
                // Загрузка Sound в SoundPool:
                val assetFileDescriptor = assets.openFd(sound.assetPath)
                sound.id = soundPool.load(assetFileDescriptor, 1)
                sounds.add(sound)
            } catch (ioe: IOException) {
                Log.e("ForestSounds", "$name loading is failed", ioe)
            }
        }
        return sounds
    }

    companion object {
        private const val SOUNDS_FOLDER = "sounds"
        private const val MAX_SOUNDS_NUMBER = 4
    }
}