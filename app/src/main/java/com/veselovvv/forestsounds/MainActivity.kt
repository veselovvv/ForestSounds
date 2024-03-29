package com.veselovvv.forestsounds

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.veselovvv.forestsounds.databinding.ActivityMainBinding
import com.veselovvv.forestsounds.databinding.ListItemBinding

class MainActivity : AppCompatActivity() {
    private lateinit var forestSounds: ForestSounds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forestSounds = ForestSounds(assets)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Настройка RecyclerView:
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = SoundAdapter(forestSounds.getSounds())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        forestSounds.release() // освобождение SoundPool
    }

    // Объект SoundHolder, связанный с list_item.xml:
    private inner class SoundHolder(private val binding: ListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.viewModel = SoundViewModel(forestSounds)
        }

        fun bind(sound: Sound) {
            binding.apply {
                viewModel?.sound = sound
                // Обновить макет немедленно, поскольку данные привязки обновляются в RecyclerView:
                executePendingBindings()
            }
        }
    }

    // Адаптер, связанный с SoundHolder:
    private inner class SoundAdapter(private val sounds: List<Sound>)
        : RecyclerView.Adapter<SoundHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SoundHolder(
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        )

        override fun onBindViewHolder(holder: SoundHolder, position: Int) = holder.bind(sounds[position])
        override fun getItemCount() = sounds.size
    }
}