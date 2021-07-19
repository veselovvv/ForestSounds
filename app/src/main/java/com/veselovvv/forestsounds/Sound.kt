package com.veselovvv.forestsounds

class Sound(val assetPath: String, var id: Int? = null) {

    val name = assetPath.split("/").last().removeSuffix(".wav")
}