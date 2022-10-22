package com.veselovvv.forestsounds

import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class SoundViewModelTest {
    private lateinit var forestSounds: ForestSounds
    private lateinit var sound: Sound
    private lateinit var testSubject: SoundViewModel

    @Before
    fun setUp() {
        forestSounds = mock(ForestSounds::class.java)
        sound = Sound("assetPath")
        testSubject = SoundViewModel(forestSounds)
        testSubject.sound = sound
    }

    @Test
    fun exposesSoundNameAsTitle() {
        assertThat(testSubject.title, `is`(sound.name))
    }

    @Test
    fun callsForestSoundsPlayOnButtonClicked() {
        testSubject.onButtonClicked()
        verify(forestSounds).play(sound)
    }
}