package dev.bugstitch.titanote.presentation.components

import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
data class PreviewRoute(
    val id:Int,
    var title:String,
    var content:String,
    var date: Long
)

@Serializable
data class EditScreen(
    val id:Int,
    var title:String,
    var content:String,
    var date: Long
)