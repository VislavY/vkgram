package ru.vyapps.vkgram.vk_api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonalInformation(
    val languages: List<String> = emptyList(),
    @SerialName("political") val politicalViews: Int = 0,
    val religion: String = "",
    @SerialName("life_main") val mainInLife: Int = 0,
    @SerialName("people_main") val mainInPeoples: Int = 0,
    @SerialName("smoking") val viewsOnSmoking: Int = 0,
    @SerialName("alcohol") val viewsOnAlcohol: Int = 0,
    @SerialName("inspired_by") val inspiredBy: String = ""
)
