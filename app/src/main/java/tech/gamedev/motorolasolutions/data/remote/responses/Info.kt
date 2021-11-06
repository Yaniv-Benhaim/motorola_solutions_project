package tech.gamedev.motorolasolutions.data.remote.responses

data class Info(
    val page: Int,
    val results: Int,
    val seed: String,
    val version: String
)