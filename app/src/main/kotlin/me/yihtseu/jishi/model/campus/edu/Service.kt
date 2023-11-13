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
            val name: String,
            val url: String? = null
        )
    }

    fun search(): String {
        for (model in models) {
            for (control in model.controls) {
                if (control.name == "JXLDM" && control.url != null) {
                    return control.url
                }
            }
        }
        throw Exception("Data Error")
    }
}