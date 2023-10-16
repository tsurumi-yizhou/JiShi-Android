package me.yihtseu.jishi.model.campus.edu

import kotlinx.serialization.Serializable

@Serializable
data class Service(
    val models: List<Model>
) {
    @Serializable
    data class Model(
        val controls: List<Control>
    ) {
        @Serializable
        data class Control(
            val url: String
        )
    }
}