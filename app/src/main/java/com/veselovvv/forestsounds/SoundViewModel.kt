package com.veselovvv.forestsounds

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class SoundViewModel(private val forestSounds: ForestSounds) : BaseObservable() {

    fun onButtonClicked() {
        sound?.let {
            forestSounds.play(it)
        }
    }

    var sound: Sound? = null
        set(sound) {
            field = sound
            // Оповестить класс привязки о том, что Bindable-свойства объектов обновлены:
            notifyChange()
        }

    @get:Bindable
    val title: String?
        get() = sound?.name
}