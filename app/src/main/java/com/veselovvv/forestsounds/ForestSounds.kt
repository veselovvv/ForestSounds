package com.veselovvv.forestsounds

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException

private const val SOUNDS_FOLDER = "sounds"
private const val MAX_SOUNDS_NUMBER = 4

class ForestSounds(private val assets: AssetManager) {
    val sounds: List<Sound>

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
    fun release() { soundPool.release() }

    private fun loadSounds(): List<Sound> {
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
                load(sound)
                sounds.add(sound)
            } catch (ioe: IOException) {
                Log.e("ForestSounds", "$name loading is failed", ioe)
            }
        }
        return sounds
    }

    // Загрузка Sound в SoundPool:
    private fun load(sound: Sound) {
        val assetFileDescriptor: AssetFileDescriptor = assets.openFd(sound.assetPath)
        val id = soundPool.load(assetFileDescriptor, 1)
        sound.id = id
    }
}